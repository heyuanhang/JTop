package cn.com.mjsoft.cms.appbiz.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.bean.SystemApiConfigBean;
import cn.com.mjsoft.cms.appbiz.dao.AppBizDao;
import cn.com.mjsoft.cms.appbiz.dao.vo.SystemApiConfig;
import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;

import cn.com.mjsoft.cms.behavior.InitRSABehavior;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.config.SystemRuntimeConfig;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.config.metadata.FlowMetadata;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

public class AppbizService
{
    private static Logger log = Logger.getLogger( AppbizService.class );

    private static ClusterMapAdapter APP_REG_KEY = new ClusterMapAdapter(
        "appbizService.APP_REG_KEY", String.class, String.class );

    private static ClusterMapAdapter APP_FLOW_TOKEN = new ClusterMapAdapter(
        "appbizService.APP_FLOW_TOKEN", String.class, String.class );

    private Cache apiCfgCache = new ClusterCacheAdapter( 3000, "appbizService.apiCfgCache" );

    private static AppbizService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private AppBizDao abDao;

    private AppbizService()
    {
        abDao = new AppBizDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new AppbizService();
        }
    }

    public static AppbizService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    // /////////APP核心业务//////////

    public boolean appClientKeyReg( String encKey )
    {
        // app客户端必须使用之前获取的公锁加密生成的唯一key
        String aesKey = InitRSABehavior.decryptPrivate( InitRSABehavior.getPrivateKey(), encKey );

        String testAesKey = ( String ) APP_REG_KEY.get( encKey );
        if( ( StringUtil.isStringNull( aesKey ) ) || ( StringUtil.isStringNotNull( testAesKey ) ) )
        {
            // TODO 记录某IP单位时间内注册数，若超过阈值，视为攻击行为

            return false;
        }

        // 后台注册app加密链接锁
        APP_REG_KEY.put( encKey, aesKey );

        return true;
    }

    public static String getAESKey( String encKey )
    {
        return ( String ) APP_REG_KEY.get( encKey );
    }

    public String genAppFlowToken( String encKey )
    {
        String aesKey = ( String ) APP_REG_KEY.get( encKey );
        if( StringUtil.isStringNull( aesKey ) )
        {
            return "";
        }
        String token = StringUtil.getUUIDString();

        APP_FLOW_TOKEN.put( token, token );

        return InitRSABehavior.encodeB64AES( token, aesKey );
    }

    public boolean checkAppFlowToken( String token )
    {
        if( StringUtil.isStringNull( token ) )
        {
            return false;
        }
        if( APP_FLOW_TOKEN.containsKey( token ) )
        {
            APP_FLOW_TOKEN.remove( token );

            return true;
        }
        return false;
    }

    // //////////////一般业务/////////////////////

    /**
     * 根据配置自动生成接口信息元数据
     */
    // TODO 后面将改为搜索springmvc接口
    public void initSysApiCfgForFramework( String mdn )
    {

        try
        {
            mysqlEngine.beginTransaction();

            SystemRuntimeConfig rc = SystemConfiguration.getInstance().getSystemConfig();

            Map modules = rc.getModules();

            Map apim = ( Map ) modules.get( mdn );

            Iterator iter = apim.entrySet().iterator();

            Entry entry = null;

            FlowMetadata fm = null;

            String cname = null;

            String flowPath = null;

            SystemApiConfigBean existSac = null;

            while ( iter.hasNext() )
            {
                entry = ( Entry ) iter.next();

                fm = ( FlowMetadata ) entry.getValue();

                cname = fm.getCommandName();

                flowPath = "/" + fm.getItsModule() + "/" + fm.getCommand() + ".cmd";

                existSac = abDao.queryAppCfgBeanByFlowPath( flowPath );

                try
                {
                    Class testClass = Class.forName( fm.getDisposeClazz() );

                    if( testClass != null )
                    {
                        Class superClass = testClass.getSuperclass();

                        if( superClass != null
                            && !superClass.getName().equals( ApiFlowDisposBaseFlow.class.getName() ) )
                        {
                            continue;
                        }
                    }
                }
                catch ( ClassNotFoundException e )
                {

                }

                if( existSac == null )
                {
                    SystemApiConfig sac = new SystemApiConfig();

                    sac.setApiName( cname );
                    sac.setFlowPath( flowPath );

                    abDao.save( sac );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    public void editSysApiCfg( SystemApiConfig acb )
    {

        try
        {
            mysqlEngine.beginTransaction();

            abDao.updateAppCfgById( acb );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    public void editSysApiCfgParam( String params, Long apiId )
    {

        try
        {
            mysqlEngine.beginTransaction();

            abDao.updateAppCfgParamById( params, apiId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    public void addSysApiCfg( SystemApiConfig vo )
    {
        try
        {
            mysqlEngine.beginTransaction();

            SystemApiConfigBean existSac = abDao.queryAppCfgBeanByFlowPath( vo.getFlowPath() );

            if( existSac != null )
            {
                return;
            }

            abDao.save( vo );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }
    }

    public SystemApiConfigBean retrieveSingleAppCfgBeanByPath( String path )
    {
        String key = "retrieveSingleAppCfgBeanByPath:" + path;

        SystemApiConfigBean result = ( SystemApiConfigBean ) apiCfgCache.getEntry( key );

        if( result == null )
        {
            result = abDao.queryAppCfgBeanByFlowPath( path );

            if( result == null )
            {
                result = new SystemApiConfigBean();
            }

            apiCfgCache.putEntry( key, result );
        }

        return result;
    }

    public List retrieveAllAppCfgBean()
    {
        String key = "retrieveAllAppCfgBeanByPath:";

        List result = ( List ) apiCfgCache.getEntry( key );

        if( result == null )
        {
            result = abDao.queryAllAppCfg();

            apiCfgCache.putEntry( key, result );
        }

        return result;
    }

    public void deleteSysApiCfg( List idList )
    {

        try
        {
            mysqlEngine.beginTransaction();

            Long apiId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                apiId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( apiId.longValue() < 1 )
                {
                    continue;
                }

                abDao.deleteAppCfgById( apiId );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    public Object getSysApiCfgForTag( String apiIdVar, String pn, String size, String sa, String key )
    {
        Long apiId = Long.valueOf( StringUtil.getLongValue( apiIdVar, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        List result = null;

        if( apiId.longValue() > 0 )
        {
            return abDao.queryAppCfgBeanById( apiId );
        }
        else if( StringUtil.isStringNotNull( sa ) && StringUtil.isStringNotNull( key ) )
        {
            key = SystemSafeCharUtil.decodeFromWeb( key );

            if( "name".equals( sa ) )
            {
                return abDao.queryAppCfgBeanByName( key );
            }
            else if( "flow".equals( sa ) )
            {
                return abDao.queryAppCfgBeanByFlowPath( key );
            }
        }
        else
        {
            count = abDao.queryAppCfgCount();

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = abDao.queryAllAppCfg( Long.valueOf( pageInfo.getFirstResult() ), Integer
                .valueOf( pageSize ) );

            return new Object[] { result, pageInfo };
        }

        return result;
    }

    public void clearCache()
    {
        this.apiCfgCache.clearAllEntry();
    }
}

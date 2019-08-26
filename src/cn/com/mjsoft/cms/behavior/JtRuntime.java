package cn.com.mjsoft.cms.behavior;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.cluster.dao.ClusterDao;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.service.CMSRedisDB;
import cn.com.mjsoft.cms.common.service.CommonSystemService;
import cn.com.mjsoft.cms.site.bean.CmsServerBean;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.config.InitServerCfg;
import cn.com.mjsoft.framework.config.JTopServer;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.JdbcInstrument;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.util.InitClassPathHelper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.engine.InterceptFilter;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

public class JtRuntime implements Behavior, InitServerCfg
{
    private static Logger log = Logger.getLogger( JtRuntime.class );

    public static final String CS_FILE_NAME = "config" + File.separator + "cs.properties";

    public static final String RT_FILE_NAME = "config" + File.separator + "runtime.properties";

    private static ClusterDao cluDao = new ClusterDao(
        new PersistenceEngine( new MySqlDataSource() ) );

    // cms系统信息,必须最先完成初始化
    public static final CmsServerBean cmsServer = new CmsServerBean();

    public Object operation( Object target, Object[] param )
    {
        // 核心服务运行时
        initCmsServer( cmsServer );

        // 基础业务
        CommonSystemService.getInstance().initCMSBusConfig( cmsServer );

        return null;
    }

    public JTopServer initCmsServer( CmsServerBean cmsServerBean )
    {
        // cms server
        String classesPath = null;
        try
        {
            classesPath = InitSiteGroupInfoBehavior.class.getClassLoader().getResource( "/" )
                .toURI().getPath();
        }
        catch ( URISyntaxException e1 )
        {
            e1.printStackTrace();
        }

        String osName = System.getProperty( "os.name" ).toLowerCase();

        if( osName.indexOf( "win" ) != -1 )
        {
            if( classesPath.startsWith( "/" ) )
            {
                classesPath = StringUtil.subString( classesPath, 1, classesPath.length() );
            }
        }

        classesPath = StringUtil.replaceString( classesPath, "/", File.separator, false, false );

        String root = ServletUtil.getClassPath( InitClassPathHelper.class );

        String rtFile = StringUtil.replaceString( root, "classes", CS_FILE_NAME, false, false );

        Properties cmsPro = new Properties();

        if( null != rtFile )
        {
            InputStream in = null;
            try
            {

                in = new FileInputStream( rtFile );

                cmsPro.load( in );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
            finally
            {
                if( null != in )
                {
                    try
                    {
                        in.close();
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 数据库启动连接
        Connection testDb = null;

        try
        {
            testDb = new MySqlDataSource().getConnection();
            JdbcInstrument.closeConnection( testDb );
        }
        catch ( SQLException e )
        {
            throw new FrameworkException( "数据库无法连接,请检查配置信息!", e );
        }

        if( testDb == null )
        {
            throw new FrameworkException( "数据库无法连接,请检查配置信息!" );
        }

        cmsServerBean.setDomain( cmsPro.getProperty( "back_domain" ) );
        cmsServerBean.setContext( cmsPro.getProperty( "back_context" ) );
        cmsServerBean.setPort( Integer.valueOf( StringUtil.getIntValue( cmsPro
            .getProperty( "back_port" ), 80 ) ) );

        cmsServerBean.setClusterMode( Boolean.valueOf( StringUtil.getBooleanValue( cmsPro
            .getProperty( "cluster_mode" ), false ) ) );

        cmsServerBean.setClientServer( Boolean.valueOf( StringUtil.getBooleanValue( cmsPro
            .getProperty( "client_server" ), false ) ) );

        cmsServerBean.setEncoding( StringUtil.isStringNull( ( String ) cmsPro
            .getProperty( "encoding" ) ) ? "UTF-8" : ( String ) cmsPro.getProperty( "encoding" ) );

        /*
         * cmsServerBean.setSiteRoute( Boolean.valueOf( StringUtil
         * .getBooleanValue( cmsPro.getProperty( "site_route" ), false ) ) );
         */

        cmsServerBean.setRedisIP( ( String ) cmsPro.getProperty( "redis_ip" ) );

        cmsServerBean.setActDefense( Boolean.valueOf( StringUtil.getBooleanValue( cmsPro
            .getProperty( "act_defense" ), true ) ) );

        cmsServerBean.setAllDefense( Boolean.valueOf( StringUtil.getBooleanValue( cmsPro
            .getProperty( "all_defense" ), true ) ) );

        cmsServerBean.setTplCache( Boolean.valueOf( StringUtil.getBooleanValue( cmsPro
            .getProperty( "tpl_cache" ), false ) ) );

        // init jedis

        if( cmsServerBean.getClusterMode() )
        {

            CMSRedisDB.initPool( cmsServerBean.getRedisIP(), ( String ) cmsPro
                .getProperty( "redis_port" ), ( String ) cmsPro.getProperty( "redis_password" ),
                !cmsServerBean.getClientServer() );
        }

        if( cmsPro.get( "login_page" ) == null )
        {
            cmsPro.put( "login_page", "" );
        }

        if( cmsPro.get( "spec_page" ) == null )
        {
            cmsPro.put( "spec_page", "" );
        }

        /**
         * 注册
         */
        InterceptFilter.regJTopServer( cmsServerBean );

        cmsServerBean.getExtCfg().putAll( cmsPro );

        if( cmsServerBean.getClusterMode() )
        {
            // 集群节点服务器注册
            if( cmsServerBean.getClientServer() )
            {

                Map cNode = regCMSNodeInfo( cmsServerBean );

                String info = "...................................................................................................................................\n"
                    + "......CMS Cluster NODE("
                    + cNode
                    + ") init success.....\n"
                    + "................................................................................................................................................";

                cmsServerBean.setServerId( ( Long ) cNode.get( "serverId" ) );

                System.out.println( info );
            }

            /**
             * 向redis注册集群节点信息，注意，次步骤为任何节点启动时都要执行
             */
            List<Map> cluList = cluDao.queryAllClusterServer();

            String cluServerIds = "";
            for ( Map clu : cluList )
            {
                cluServerIds += clu.get( "serverId" ) + ":";
            }

            CMSRedisDB.putMapVal( "__cluster_node_info__", "__all_cluster_id__", cluServerIds );
        }

        return cmsServerBean;
    }

    public Map regCMSNodeInfo( CmsServerBean cmsServerBean )
    {
        Map cNode = cluDao.querySingleClusterServerByHost( cmsServerBean.getDomainFullPath() );

        if( cNode.isEmpty() )
        {
            Long sId = cluDao.saveClusterServer( System.getProperty( "os.name" ) + "节点",
                cmsServerBean.getDomainFullPath(), cmsServerBean.getPort(), Constant.COMMON.ON );

            cNode = cluDao.querySingleClusterServerById( sId );
        }
        else
        {
            cluDao.updateClusterServerActiveFlag( ( Long ) cNode.get( "serverId" ),
                Constant.COMMON.ON );
        }

        cluDao.clearClusterServer();

        return cNode;
    }

}

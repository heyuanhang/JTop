package cn.com.mjsoft.cms.common.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.dao.CommonSystemDao;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.organization.dao.OrgDao;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.CmsServerBean;
import cn.com.mjsoft.cms.site.bean.SiteCloudCfgBean;
import cn.com.mjsoft.cms.site.bean.SiteDispenseServerBean;
import cn.com.mjsoft.cms.site.dao.SiteGroupDao;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.util.InitClassPathHelper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.engine.InterceptFilter;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

public class CommonSystemService
{
    private static Logger log = Logger.getLogger( CommonSystemService.class );

    public static final String CS_FILE_NAME = "config" + File.separator + "cs.properties";

    public static final String RT_FILE_NAME = "config" + File.separator + "runtime.properties";

    private static CommonSystemService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private static SecurityService securityService = SecurityService.getInstance();

    private CommonSystemDao csDao;

    private OrgDao orgDao;
    
    private SiteGroupDao siteDao;

    private CommonSystemService()
    {
        csDao = new CommonSystemDao( mysqlEngine );

        orgDao = new OrgDao( mysqlEngine );
        
        siteDao  = new SiteGroupDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new CommonSystemService();
        }
    }

    public static CommonSystemService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    @SuppressWarnings( "unchecked" )
    public Map retrieveCmsSystemRuntimeConfigInfo()
    {
        Map result = new HashMap();

        result.putAll( csDao.queryCmsSystemRuntimeConfig() );

        CmsServerBean csBean = JtRuntime.cmsServer;

        result.put( "domain", csBean.getDomain() );

        result.put( "context", csBean.getContext() );

        result.put( "port", csBean.getPort() );

        return result;
    }

    public void bulidCmsServer()
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

        String rtFile = StringUtil.replaceString( classesPath, "classes", CS_FILE_NAME, false,
            false );

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

        JtRuntime.cmsServer.setDomain( cmsPro.getProperty( "back_domain" ) );
        JtRuntime.cmsServer.setContext( cmsPro.getProperty( "back_context" ) );
        JtRuntime.cmsServer.setPort( Integer.valueOf( cmsPro.getProperty( "back_port" ) ) );

        JtRuntime.cmsServer.clear();

        Map rtCfg = retrieveCmsSystemRuntimeConfigInfo();

        JtRuntime.cmsServer.setBaiduMapDefCity( ( String ) rtCfg.get( "baiduMapDefCity" ) );

        JtRuntime.cmsServer.setDangerAccessCount( Integer.valueOf( StringUtil.getIntValue(
            ( String ) rtCfg.get( "dangerAccessCount" ), 3 ) ) );

        JtRuntime.cmsServer.setManagerIp( ( String ) rtCfg.get( "managerIp" ) );

        JtRuntime.cmsServer.setLoginTime( Integer.valueOf( StringUtil.getIntValue( ( String ) rtCfg
            .get( "loginTime" ), 30 ) ) );

        JtRuntime.cmsServer.setOpenOfficePath( ( String ) rtCfg.get( "openOfficePath" ) );

        JtRuntime.cmsServer.setOtherVCUrl( ( String ) rtCfg.get( "otherVCUrl" ) );

        JtRuntime.cmsServer.setRootOrgName( ( String ) rtCfg.get( "rootOrgName" ) );

        JtRuntime.cmsServer.getExtCfg().putAll( cmsPro );
        JtRuntime.cmsServer.getExtCfg().putAll( rtCfg );

        // 更新根机构名称
        updateRootOrgName( JtRuntime.cmsServer.getRootOrgName() );

        /**
         * 注册服务器配置
         */
        InterceptFilter.regJTopServer( JtRuntime.cmsServer );

    }

    /**
     * 获取系统全局变量
     * 
     * @return
     */
    public Object getCmsSystemRuntimeConfigTag()
    {
        return retrieveCmsSystemRuntimeConfigInfo();
    }

    /**
     * 获取系统全局变量(缓存)
     * 
     * @return
     */
    public Object getCmsSystemRuntimeInitCacheTag()
    {
        return JtRuntime.cmsServer;
    }

    public void initCMSBusConfig( CmsServerBean cmsServerBean )
    {
        Map rtCfg = csDao.queryCmsSystemRuntimeConfig();

        cmsServerBean.setBaiduMapDefCity( ( String ) rtCfg.get( "baiduMapDefCity" ) );

        cmsServerBean.setDangerAccessCount( Integer.valueOf( StringUtil.getIntValue(
            ( String ) rtCfg.get( "dangerAccessCount" ), 3 ) ) );

        cmsServerBean.setManagerIp( ( String ) rtCfg.get( "managerIp" ) );

        cmsServerBean.setLoginTime( Integer.valueOf( StringUtil.getIntValue( ( String ) rtCfg
            .get( "loginTime" ), 30 ) ) );

        cmsServerBean.setOpenOfficePath( ( String ) rtCfg.get( "openOfficePath" ) );

        cmsServerBean.setOtherVCUrl( ( String ) rtCfg.get( "otherVCUrl" ) );

        cmsServerBean.setRootOrgName( ( String ) rtCfg.get( "rootOrgName" ) );

        // 主服务器模式
        if( !cmsServerBean.getClientServer() )
        {
            // 更新根机构名称
            orgDao.updateRootOrgName( cmsServerBean.getRootOrgName() );

            // 检查root权限
            securityService.checkSysRootRoleAndUser( ( String ) cmsServerBean.getExtCfg().get(
                "init_admin" ) );
        }

        cmsServerBean.getExtCfg().putAll( rtCfg );

    }

    @SuppressWarnings( "unchecked" )
    public void editCmsRuntimeConfig( Map reqParam, String csCfgFile )
    {
        try
        {
            this.mysqlEngine.beginTransaction();

            // 系统参数

            Map valMap = new HashMap();

            valMap.put( "managerIp", ( String ) reqParam.get( "managerIp" ) );

            valMap.put( "loginTime", Integer.valueOf( StringUtil.getIntValue( ( String ) reqParam
                .get( "loginTime" ), 30 ) ) );

            valMap.put( "openOfficePath", ( String ) reqParam.get( "openOfficePath" ) );

            valMap.put( "rootOrgName", StringUtil.isStringNull( ( String ) reqParam
                .get( "rootOrgName" ) ) ? "总公司" : ( String ) reqParam.get( "rootOrgName" ) );

            valMap.put( "baiduMapDefCity", ( String ) reqParam.get( "baiduMapDefCity" ) );

            valMap.put( "dangerAccessCount", Integer.valueOf( StringUtil.getIntValue(
                ( String ) reqParam.get( "dangerAccessCount" ), 3 ) ) );

            valMap.put( "otherVCUrl", ( String ) reqParam.get( "otherVCUrl" ) );

            valMap.put( "backupDay", Integer.valueOf( StringUtil.getIntValue( ( String ) reqParam
                .get( "backupDay" ), 0 ) ) );

            valMap.put( "backupFtpId", ( String ) reqParam.get( "backupFtpId" ) );

            valMap.put( "backupHourExe", Integer.valueOf( StringUtil.getIntValue(
                ( String ) reqParam.get( "backupHourExe" ), 4 ) ) );
            
             
            valMap.put( "aldOpt", StringUtil.getIntValue( ( String ) reqParam
                .get( "aldOpt" ), 1 ) );
            
            int as =  StringUtil.getIntValue( ( String ) reqParam
                .get( "alStartHour" ), 0 );
            
            int ae =  StringUtil.getIntValue( ( String ) reqParam
                .get( "alEndHour" ), 0 );
            
            valMap.put( "alhOpt", StringUtil.getIntValue( ( String ) reqParam
                .get( "alhOpt" ), 1 ) );
            
            valMap.put( "alStartHour",as );
            
            valMap.put( "alEndHour", ae );
            
            if(as > ae && ae != 0)
            {
                valMap.put( "alEndHour", as );
            }

             csDao.updateCmsRuntimeCfg( valMap );

            valMap.clear();

            valMap.put( "domain", ( String ) reqParam.get( "domain" ) );

            valMap.put( "context", ( String ) reqParam.get( "context" ) );

            valMap.put( "port", ( String ) reqParam.get( "port" ) );

            valMap.put( "serverId", Integer.valueOf( 1 ) );

             csDao.updateCmsServerCfg( valMap );

            StringUtil.setProperty( csCfgFile, "back_domain", ( String ) reqParam.get( "domain" ) );

            StringUtil
                .setProperty( csCfgFile, "back_context", ( String ) reqParam.get( "context" ) );

            StringUtil.setProperty( csCfgFile, "back_port", ( String ) reqParam.get( "port" ) );

            this.mysqlEngine.commit();
        }
        finally
        {
            this.mysqlEngine.endTransaction();

            bulidCmsServer();
        }
    }

    public void updateRootOrgName( String name )
    {
        if( StringUtil.isStringNull( name ) )
        {
            name = "总公司";
        }

        orgDao.updateRootOrgName( name );
    }
    
    public List getAllSystemInnerUrlInfo()
    {
        Set tmp = new HashSet();
        
        List<SiteDispenseServerBean> dsbList = siteDao.queryAllDispenseServerBean();
        
        for(SiteDispenseServerBean bean :dsbList)
        {
            tmp.add( bean.getServerUrl() );
        }
        
        List<SiteCloudCfgBean> clList = siteDao.querysAllCloudCfgBean();
        
        for(SiteCloudCfgBean bean :clList)
        {
            tmp.add( bean.getAccessUrl() );
        }
        
        
        return new ArrayList(tmp);
    }

    public void mysqlBack( String adminName, String pw, String dbName, String bakFilePath )
    {

        List commend = new ArrayList();

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            String mysqlRoot = csDao.getMysqlRootBasePath();

            commend.add( mysqlRoot + "bin" + File.separator + "mysqldump" );
        }
        else
        {
            commend.add( "mysqldump" );
        }

        commend.add( "-u" );
        commend.add( adminName );
        commend.add( "-p" + pw );
        commend.add( dbName );

        String[] strs = ( String[] ) commend.toArray( new String[] {} );

        Process process = null;
        try
        {
            Runtime runtime = Runtime.getRuntime();

            process = runtime.exec( strs );

            InputStream in = process.getInputStream();
            InputStreamReader input = new InputStreamReader( in, "utf8" );
            String inStr;
            StringBuffer sb = new StringBuffer( "" );
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader( input );
            while ( ( inStr = br.readLine() ) != null )
            {
                sb.append( inStr + "\r\n" );
            }
            outStr = sb.toString();

            FileOutputStream fout = new FileOutputStream( bakFilePath );
            OutputStreamWriter writer = new OutputStreamWriter( fout, "utf8" );
            writer.write( outStr );

            writer.flush();
            // 关闭输入输出流
            in.close();
            input.close();
            br.close();
            writer.close();
            fout.close();
            log.info( "JTopCMS数据库备份已成功！" );

        }
        catch ( Exception e )
        {
            log.info( "JTopCMS数据库备份失败，请查看异常" );
            e.printStackTrace();
        }

    }

    public boolean mysqlRecovery( String adminName, String pw, String dbName, String bakFilePath )
    {
        try
        {

            Runtime rt = Runtime.getRuntime();

            // 调用mysql的cmd
            List commend = new ArrayList();

            String osName = System.getProperty( "os.name" ).toLowerCase();
            if( osName.indexOf( "win" ) != -1 )
            {
                String mysqlRoot = csDao.getMysqlRootBasePath();

                commend.add( mysqlRoot + "bin" + File.separator + "mysql" );
            }
            else
            {
                commend.add( "mysql" );
            }

            commend.add( "-u" );
            commend.add( adminName );
            commend.add( "-p" + pw );
            commend.add( dbName );

            String[] strs = ( String[] ) commend.toArray( new String[] {} );

            Process child = rt.exec( strs );
            OutputStream out = child.getOutputStream();
            String inStr;
            StringBuffer sb = new StringBuffer( "" );
            String outStr;
            BufferedReader br = new BufferedReader( new InputStreamReader( new FileInputStream(
                bakFilePath ), "utf8" ) );
            while ( ( inStr = br.readLine() ) != null )
            {
                sb.append( inStr + "\r\n" );
            }
            outStr = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter( out, "utf8" );
            writer.write( outStr );
            writer.flush();

            out.close();
            br.close();
            writer.close();
            log.info( "JTopCMS数据库还原已成功！" );

            return true;

        }
        catch ( Exception e )
        {
            log.info( "JTopCMS数据库还原失败，请查看异常" );
            e.printStackTrace();

            return false;
        }

    }

    public Object getCMSCoreVer()
    {
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

        String rtFile = StringUtil.replaceString( root, "classes", RT_FILE_NAME, false, false );

        Properties cmsPro = new Properties();

        if( null != rtFile )
        {
            InputStream in = null;
            try
            {

                in = new FileInputStream( rtFile );

                cmsPro.load( new InputStreamReader( in, "UTF-8" ) );
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

            return cmsPro.get( "cms_version" );
        }
        return "未知版本";
    }

}

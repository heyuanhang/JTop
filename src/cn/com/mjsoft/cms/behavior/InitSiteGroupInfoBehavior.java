package cn.com.mjsoft.cms.behavior;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.service.CommonSystemService;
import cn.com.mjsoft.cms.security.service.WebAuthorizationInfoHeadstream;
import cn.com.mjsoft.cms.security.service.WebMemberAuthorizationInfoHeadstream;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.cache.impl.LRUCache;

import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.authorization.AuthorizationHandler;
import cn.com.mjsoft.framework.security.filter.SecuritySessionDisposeFiletr;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.ImageUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.engine.InterceptFilter;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class InitSiteGroupInfoBehavior implements Behavior
{
    public static final String CS_FILE_NAME = "config" + File.separator + "cs.properties";

    private static final String INIT_BAT_FILE_NAME = "/WEB-INF/config/system_init.bat";

    // 站群信息可被改变
    public static final Cache siteGroupIdInfoCache = new LRUCache( 200 );

    public static final Cache siteGroupFlagInfoCache = new LRUCache( 200 );

    public static final Cache siteGroupDomainInfoCache = new LRUCache( 200 );

    public static final List siteGroupListCache = new ArrayList( 200 );

    private static SiteGroupService service = SiteGroupService.getInstance();

    public Object operation( Object target, Object[] param )
    {
        /**
         * 注册权限信息源
         */
        AuthorizationHandler.regAuthHeadstream( new WebAuthorizationInfoHeadstream(),
            new WebMemberAuthorizationInfoHeadstream() );

        /**
         * 注册站点对象
         */
        SecuritySessionDisposeFiletr.regSiteNodeImlObjClass( SiteGroupBean.class.getName() );

        ServletContext context = ( ServletContext ) param[0];

        bulidSiteGroupInfo();

        // 加载启动初始化批处理文件
        String batFile = INIT_BAT_FILE_NAME;

        String batFilePath = ServletUtil.getSiteFilePath( context ) + batFile;

        InputStream rtIn = null;
        try
        {

            if( null != batFilePath )
            {

                String initContent = ( String ) FileUtil.readTXTFileContent(
                    new File( batFilePath ), "GBK" )[0];

                if( StringUtil.isStringNotNull( JtRuntime.cmsServer.getOpenOfficePath() ) )
                {
                    initContent = StringUtil.replaceString( initContent, "${open_office_exe_path}",
                        JtRuntime.cmsServer.getOpenOfficePath(), false, false );

                    String tempBatFile = ServletUtil.getSiteFilePath( context )
                        + "/WEB-INF/config/tempInitBat.bat";

                    FileUtil.writeTXTFileContent( initContent, tempBatFile, "GBK" );

                    Runtime runtime = Runtime.getRuntime();

                    /**
                     * 启动windows环境下office
                     */
                    // TODO
                    runtime.exec( "cmd.exe /c start " + tempBatFile );
                    // FileUtil.delFile( tempBatFile );
                }
            }
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

        finally
        {
            if( null != rtIn )
            {
                try
                {
                    rtIn.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }

        System.setProperty( "java.awt.headless", "true" );

        if( !JtRuntime.cmsServer.getClientServer() )

        {
            /**
             * 生成后台入口二维码
             */
            String rootPath = SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath();

            ImageUtil.createQRCode( JtRuntime.cmsServer.getDomainFullPath() + "login_page",
                rootPath + "common" + File.separator + "logingate.png", "", 4 );
        }

        return null;
    }

    public static void bulidSiteGroupInfo()
    {

        // site group

        siteGroupIdInfoCache.clearAllEntry();
        siteGroupFlagInfoCache.clearAllEntry();
        siteGroupDomainInfoCache.clearAllEntry();

        siteGroupListCache.clear();

        List siteBeanList = service.retrieveAllSiteBean();

        SiteGroupBean bean = null;

        Map domainCacheMap = new HashMap();

        Map idCacheMap = new HashMap();

        for ( int i = 0; i < siteBeanList.size(); i++ )
        {
            bean = ( SiteGroupBean ) siteBeanList.get( i );

            service.setSiteResServerUrl( bean );

            siteGroupIdInfoCache.putEntry( bean.getSiteId(), bean );
            siteGroupFlagInfoCache.putEntry( bean.getSiteFlag(), bean );
            siteGroupDomainInfoCache.putEntry( bean.getSiteUrl(), bean );

            // 移动端
            siteGroupDomainInfoCache.putEntry( bean.getMobSiteUrl(), bean );
            siteGroupDomainInfoCache.putEntry( bean.getPadSiteUrl(), bean );

            siteGroupListCache.add( bean );

            domainCacheMap.put( bean.getSiteUrl(), bean );

            // 移动端
            domainCacheMap.put( bean.getMobSiteUrl(), bean );
            domainCacheMap.put( bean.getPadSiteUrl(), bean );

            idCacheMap.put( bean.getSiteId(), bean );

            String[] home301s = StringUtil.split( bean.getHome301Url() != null ? bean
                .getHome301Url() : "", "," );

            for ( String h301 : home301s )
            {
                idCacheMap.put( h301, bean.getSiteUrl() );
            }

        }

        InterceptFilter.regJTopSiteNodeDomain( domainCacheMap, idCacheMap, siteGroupListCache );
        
        InterceptFilter.regJTopInnerDomain( CommonSystemService.getInstance().getAllSystemInnerUrlInfo() );

        ClusterService.exeClusterMasterCMD( "cluster/cluBSGI.do" );

    }

    public static void bulidSiteGroupInfoClusterMode()
    {

        // site group

        siteGroupIdInfoCache.clearAllEntry();
        siteGroupFlagInfoCache.clearAllEntry();
        siteGroupDomainInfoCache.clearAllEntry();

        siteGroupListCache.clear();

        List siteBeanList = service.retrieveAllSiteBean();

        SiteGroupBean bean = null;

        Map domainCacheMap = new HashMap();

        Map idCacheMap = new HashMap();

        for ( int i = 0; i < siteBeanList.size(); i++ )
        {
            bean = ( SiteGroupBean ) siteBeanList.get( i );

            service.setSiteResServerUrl( bean );

            siteGroupIdInfoCache.putEntry( bean.getSiteId(), bean );
            siteGroupFlagInfoCache.putEntry( bean.getSiteFlag(), bean );
            siteGroupDomainInfoCache.putEntry( bean.getSiteUrl(), bean );

            // 移动端
            siteGroupDomainInfoCache.putEntry( bean.getMobSiteUrl(), bean );
            siteGroupDomainInfoCache.putEntry( bean.getPadSiteUrl(), bean );

            siteGroupListCache.add( bean );

            domainCacheMap.put( bean.getSiteUrl(), bean );

            // 移动端
            domainCacheMap.put( bean.getMobSiteUrl(), bean );
            domainCacheMap.put( bean.getPadSiteUrl(), bean );

            idCacheMap.put( bean.getSiteId(), bean );

            String[] home301s = StringUtil.split( bean.getHome301Url() != null ? bean
                .getHome301Url() : "", "," );

            for ( String h301 : home301s )
            {
                idCacheMap.put( h301, bean.getSiteUrl() );
            }
        }

        InterceptFilter.regJTopSiteNodeDomain( domainCacheMap, idCacheMap, siteGroupListCache );
        
        InterceptFilter.regJTopInnerDomain( CommonSystemService.getInstance().getAllSystemInnerUrlInfo() );


    }

    public static Object getEmptySiteGroupInfo()
    {
        SiteGroupBean siteBean = new SiteGroupBean();

        return siteBean;
    }
}

package cn.com.mjsoft.cms.cluster.service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.cluster.dao.ClusterDao;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class ClusterService
{
    private static Logger log = Logger.getLogger( ClusterService.class );

    private static ClusterService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private static ScheduleService scheduleService = ScheduleService.getInstance();

    private static ResourcesService resService = ResourcesService.getInstance();

    private ClusterDao clusterDao;

    private ClusterService()
    {
        clusterDao = new ClusterDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new ClusterService();
        }
    }

    public static ClusterService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void addClusterServer( String serverName, String url, Integer port, Integer active )
    {
        clusterDao.saveClusterServer( serverName, url, port, active );

        clearCache();
    }

    public void editClusterServer( Long serverId, String serverName, String url, Integer port )
    {
        clusterDao.updateClusterServer( serverId, serverName, url, port );

        clearCache();

    }

    public void deleteClusterServer( Long serverId )
    {
        clusterDao.deleteClusterServerById( serverId );

        clearCache();

    }

    public void changeClusterServerActiveFlag( Long serverId, Integer isActive )
    {
        clusterDao.updateClusterServerActiveFlag( serverId, isActive );

        clearCache();
    }

    public void checkClusterServerConnStatus()
    {

        List csList = ( List ) getClusterServerInfoForTag( "-1" );

        Map cluServer = null;

        for ( int i = 0; i < csList.size(); i++ )
        {
            cluServer = ( Map ) csList.get( i );

            String serClientServerUrl = ( String ) cluServer.get( "clusterUrl" );

            boolean connect = false;

            String url = serClientServerUrl + "cluster/cluBSGI.do?sys_test_mode=true";

            InputStream testConn = ServiceUtil.doGETMethodRequest( url );

            if( testConn != null )
            {
                connect = true;
            }

            if( connect )
            {
                clusterDao.updateClusterServerActiveFlag( ( Long ) cluServer.get( "serverId" ),
                    Constant.COMMON.ON );
            }
            else
            {
                clusterDao.updateClusterServerActiveFlag( ( Long ) cluServer.get( "serverId" ),
                    Constant.COMMON.OFF );
            }
        }

        clearCache();

    }

    /**
     * 获取集群所有节点含主服务器
     * 
     * @return
     */
    public List retrieveClusterNodeAndServerInfo()
    {

        return clusterDao.queryAllClusterServer();
    }

    /**
     * 向CMS集群节点传播上传文件(Res模式)
     * 
     * @param resBean
     */
    public void broadcastUploadFileToCluster( SiteResourceBean resBean )
    {
        if( JtRuntime.cmsServer.getClusterMode() && resBean != null )
        {

            List csList = clusterDao.queryAllClusterServer();

            Map cluServer = null;

            String serClientServerIp = null;

            Integer isActive = null;

            for ( int i = 0; i < csList.size(); i++ )
            {
                cluServer = ( Map ) csList.get( i );

                serClientServerIp = ( String ) cluServer.get( "clusterUrl" );

                isActive = ( Integer ) cluServer.get( "isActive" );

                if( Constant.COMMON.ON.equals( isActive )
                    && !JtRuntime.cmsServer.getServerId().equals(
                        ( Long ) cluServer.get( "serverId" ) ) )
                {

                    if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resBean.getResType() ) )
                    {
                        sendImageFile( resBean, serClientServerIp );

                    }
                    else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resBean.getResType() )
                        || Constant.RESOURCE.MUSIC_RES_TYPE.equals( resBean.getResType() ) )
                    {
                        sendMediaFile( resBean, serClientServerIp );

                    }
                    else if( Constant.RESOURCE.DOC_RES_TYPE.equals( resBean.getResType() )
                        || Constant.RESOURCE.ANY_RES_TYPE.equals( resBean.getResType() ) )
                    {
                        sendFileFile( resBean, serClientServerIp );

                    }
                }

            }

        }
    }

    private void sendImageFile( SiteResourceBean resBean, String serClientServerIp )
    {
        String[] paths = resBean.getFullResFilePath();

        Map fileIinfo = new HashMap();

        String rPath = StringUtil.replaceString( resBean.getResSource(), "/", File.separator,
            false, false );

        String resizeRPath = StringUtil.replaceString( resBean.getResSource(), "/", File.separator
            + "imgResize", false, false );

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( resBean.getSiteId() );

        fileIinfo.put( site.getSiteRoot() + File.separator + site.getImageRoot() + File.separator
            + rPath, paths[0] );

        String sitePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        File testFile = new File( sitePath + site.getSiteRoot() + File.separator
            + site.getImageRoot() + File.separator + resizeRPath );

        if( testFile.exists() )
        {
            fileIinfo.put( site.getSiteRoot() + File.separator + site.getImageRoot()
                + File.separator + resizeRPath, paths[1] );
        }

        FileUtil.httpPostFile( serClientServerIp + "/cluster/recFile.do", fileIinfo );
    }

    private void sendMediaFile( SiteResourceBean resBean, String serClientServerIp )
    {
        String[] paths = resBean.getFullResFilePath();

        Map fileIinfo = new HashMap();

        String rPath = StringUtil.replaceString( resBean.getResSource(), "/", File.separator,
            false, false );

        SiteResourceBean cRes = resService.retrieveSingleResourceBeanBySource( resBean.getCover() );

        if( cRes != null )
        {
            sendImageFile( cRes, serClientServerIp );
        }

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( resBean.getSiteId() );

        fileIinfo.put( site.getSiteRoot() + File.separator + site.getMediaRoot() + File.separator
            + rPath, paths[0] );

        FileUtil.httpPostFile( serClientServerIp + "/cluster/recFile.do", fileIinfo );
    }

    private void sendFileFile( SiteResourceBean resBean, String serClientServerIp )
    {
        String[] paths = resBean.getFullResFilePath();

        Map fileIinfo = new HashMap();

        String rPath = StringUtil.replaceString( resBean.getResSource(), "/", File.separator,
            false, false );

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( resBean.getSiteId() );

        fileIinfo.put( site.getSiteRoot() + File.separator + site.getFileRoot() + File.separator
            + rPath, paths[0] );

        FileUtil.httpPostFile( serClientServerIp + "/cluster/recFile.do", fileIinfo );
    }

    /**
     * 向CMS集群节点传播上传文件(文件模式)
     * 
     * @param resBean
     */
    public void broadcastFileToCluster( String relatePath, String fullPath )
    {
        if( JtRuntime.cmsServer.getClusterMode() && StringUtil.isStringNotNull( fullPath ) )
        {

            List csList = clusterDao.queryAllClusterServer();

            Map cluServer = null;

            String serClientServerIp = null;

            Integer isActive = null;

            for ( int i = 0; i < csList.size(); i++ )
            {
                cluServer = ( Map ) csList.get( i );

                serClientServerIp = ( String ) cluServer.get( "clusterUrl" );

                isActive = ( Integer ) cluServer.get( "isActive" );

                if( Constant.COMMON.ON.equals( isActive )
                    && !JtRuntime.cmsServer.getServerId().equals(
                        ( Long ) cluServer.get( "serverId" ) ) )
                {
                    sendFile( relatePath, fullPath, serClientServerIp );
                }

            }

        }
    }

    public void sendFile( String rPath, String fullPath, String serClientServerIp )
    {
        Map fileIinfo = new HashMap();

        fileIinfo.put( rPath, fullPath );

        FileUtil.httpPostFile( serClientServerIp + "/cluster/recFile.do", fileIinfo );
    }

    /**
     * 向CMS集群节点传播删除文件
     * 
     * @param resBean
     */
    public void broadcastDeleteFileToCluster( SiteResourceBean resBean )
    {
        if( JtRuntime.cmsServer.getClusterMode() && resBean != null )
        {

            List csList = clusterDao.queryAllClusterServer();

            Map cluServer = null;

            String serClientServerIp = null;

            Integer isActive = null;

            for ( int i = 0; i < csList.size(); i++ )
            {
                cluServer = ( Map ) csList.get( i );

                serClientServerIp = ( String ) cluServer.get( "clusterUrl" );

                isActive = ( Integer ) cluServer.get( "isActive" );

                if( Constant.COMMON.ON.equals( isActive )
                    && !JtRuntime.cmsServer.getServerId().equals(
                        ( Long ) cluServer.get( "serverId" ) ) )
                {

                    if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resBean.getResType() ) )
                    {
                        sendImageFile( resBean, serClientServerIp );

                    }
                    else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resBean.getResType() )
                        || Constant.RESOURCE.MUSIC_RES_TYPE.equals( resBean.getResType() ) )
                    {
                        sendMediaFile( resBean, serClientServerIp );

                    }
                    else if( Constant.RESOURCE.DOC_RES_TYPE.equals( resBean.getResType() )
                        || Constant.RESOURCE.ANY_RES_TYPE.equals( resBean.getResType() ) )
                    {
                        sendFileFile( resBean, serClientServerIp );

                    }
                }

            }

        }
    }

    public Object getClusterServerInfoForTag( String serverId )
    {
        Long serId = StringUtil.getLongValue( serverId, -1 );

        if( serId.longValue() > 0 )
        {
            return clusterDao.querySingleClusterServerById( serId );
        }
        else
        {
            List<Map> csl = clusterDao.queryAllClusterServer();

            List<Map> cl = new ArrayList<Map>();

            for ( Map node : csl )
            {
                if( ( ( Long ) node.get( "serverId" ) ).longValue() != 0l )
                {
                    cl.add( node );
                }
            }

            return cl;
        }

    }

    public static void exeClusterMasterCMD( String info )
    {
        exeClusterMasterCMD( info, "", "GET" );
    }

    public static void exeClusterMasterCMD( String info, String method )
    {
        exeClusterMasterCMD( info, "", method );
    }

    public static void exeClusterMasterCMD( String info, String params, String method )
    {
        if( JtRuntime.cmsServer.getClusterMode() )
        {
            // 放入job执行所需数据
            Map paramMap = new HashMap();
            paramMap.put( "cmdInfo", info );
            paramMap.put( "params", params );
            paramMap.put( "method", method );

            scheduleService.startExeClusterMasterCMDJob( paramMap );
        }
    }

    /**
     * 注意:ClusterService为后台服务器节点更新，前端服务不存在cache，无需通知
     * 
     */
    public static void clearCache()
    {
        ClusterDao.clearClusterServer();
    }
}

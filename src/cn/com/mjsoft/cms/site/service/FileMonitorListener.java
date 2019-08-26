package cn.com.mjsoft.cms.site.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.contentobjects.jnotify.JNotifyListener;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.dao.vo.SiteFileTransfeState;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

public class FileMonitorListener implements JNotifyListener
{
    private static SiteGroupService siteService = SiteGroupService
        .getInstance();

    private Map modData = new HashMap();

    private Map deteData = new HashMap();

    private Long gatewayId = null;

    /**
     * 对象级同步锁
     */
    private Object key = new Object();

    public Long getGatewayId()
    {
        return gatewayId;
    }

    public void setGatewayId( Long gatewayId )
    {
        this.gatewayId = gatewayId;
    }

    public void fileRenamed( int wd, String rootPath, String oldName,
        String newName )
    {
        print( "renamed " + rootPath + " : " + oldName + " -> " + newName );
    }

    public void fileModified( int wd, String rootPath, String filePath )
    {
        Date eventDate = new Date();
        String fullPath = rootPath + File.separator + filePath;
        File testDirFile = new File( fullPath );

        // 目录变跟记录不跟踪
        if( !testDirFile.isDirectory() && filePath.indexOf( "Thumbs.db" ) == -1 )
        {

            print( "modified " + rootPath + " : " + filePath );

            SiteFileTransfeState state = new SiteFileTransfeState();
            state.setEventTime( eventDate );
            state.setFileEventFlag( Constant.SITE_CHANNEL.FILE_EVENT_MODIFIED );
            state.setFullPath( fullPath );
            state.setFilePath( StringUtil.replaceString( filePath, File.separator,
                "/", false, false ) );
            state
                .setTransferStatus( Constant.SITE_CHANNEL.FILE_TRAN_STATUS_WAIT );

            addModifyState( state );
        }
    }

    public void fileDeleted( int wd, String rootPath, String filePath )
    {
        Date eventDate = new Date();
        print( "deleted " + rootPath + " : " + filePath );

        String fullPath = rootPath + File.separator + filePath;
        File testDirFile = new File( fullPath );
        if( !testDirFile.isDirectory() && filePath.indexOf( "Thumbs.db" ) == -1 )
        {
            SiteFileTransfeState state = new SiteFileTransfeState();
            state.setEventTime( eventDate );
            state.setFileEventFlag( Constant.SITE_CHANNEL.FILE_EVENT_DELETED );
            state.setFullPath( rootPath + File.separator + filePath );
            state.setFilePath( StringUtil.replaceString( filePath, File.separator,
                "/", false, false ) );
            state
                .setTransferStatus( Constant.SITE_CHANNEL.FILE_TRAN_STATUS_WAIT );

            addDeleteState( state );
        }

    }

    public void fileCreated( int wd, String rootPath, String filePath )
    {
        Date eventDate = new Date();
        print( "created " + rootPath + " : " + filePath );

        String fullPath = rootPath + File.separator + filePath;
        File testDirFile = new File( fullPath );

        // 目录变跟记录不跟踪
        if( !testDirFile.isDirectory() && filePath.indexOf( "Thumbs.db" ) == -1 )
        {
            SiteFileTransfeState state = new SiteFileTransfeState();
            state.setEventTime( eventDate );
            state.setFileEventFlag( Constant.SITE_CHANNEL.FILE_EVENT_CREATED );
            state.setFullPath( rootPath + File.separator + filePath );
            state.setFilePath( StringUtil.replaceString( filePath, File.separator,
                "/", false, false ) );
            state
                .setTransferStatus( Constant.SITE_CHANNEL.FILE_TRAN_STATUS_WAIT );

            addModifyState( state );
        }
    }

    public void collectEventForPeriod()
    {
        synchronized ( key )
        {
            modData.putAll( deteData );
            siteService.addNewSiteFileTransfeState( modData, gatewayId );
            modData.clear();
            deteData.clear();
        }
    }

    private void addModifyState( SiteFileTransfeState state )
    {
        synchronized ( key )
        {
            modData.put( "Event:" + state.getFullPath(), state );
        }
    }

    private void addDeleteState( SiteFileTransfeState state )
    {
        synchronized ( key )
        {
            deteData.put( "Event:" + state.getFullPath(), state );
        }
    }

    void print( String msg )
    {
        System.err.println( msg );
    }

}

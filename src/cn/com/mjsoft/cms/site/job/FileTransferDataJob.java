package cn.com.mjsoft.cms.site.job;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteDispenseServerBean;
import cn.com.mjsoft.cms.site.bean.SiteFileTransfeStateBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.bean.SitePublishGatewayBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;
import cn.com.mjsoft.framework.util.FtpUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class FileTransferDataJob implements Job
{
    private static Logger log = Logger.getLogger( FileTransferDataJob.class );

    private static SiteGroupService siteService = SiteGroupService
        .getInstance();

    private static Map excuteJob = new ReadWriteLockHashMap();

    public void execute( JobExecutionContext jobContent )
        throws JobExecutionException
    {
        if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        {
            log.info( "[FileTransferDataJob] ...waiting..."
                + jobContent.getJobDetail().getKey() );
            return;
        }

        FTPClient ftp = null;

        try
        {
            // 上锁
            excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

            log.info( "[FileTransferDataJob] ...execute start..."
                + jobContent.getJobDetail().getKey() );

            SitePublishGatewayBean gateway = ( SitePublishGatewayBean ) jobContent
                .getMergedJobDataMap().get( "gateway" );

            SiteGroupBean site = ( SiteGroupBean ) jobContent
                .getMergedJobDataMap().get( "site" );

            SiteDispenseServerBean server = ( SiteDispenseServerBean ) jobContent
                .getMergedJobDataMap().get( "server" );

            if( site == null || gateway == null || server == null )
            {
                log.info( "[FileTransferDataJob] 重要信息丢失,任务停止执行" );
                return;
            }

            /**
             * 此出没有分批来取数据，是因为线程是较小时间间隔的执行逻辑，发布数据逻辑每次不可能出现超大数据，所以 这里没有使用
             */
            List needTransferInfo = siteService
                .retrieveSiteFileTransfeStateBeanByGatewayId( gateway
                    .getGatewayId() );

            if( !needTransferInfo.isEmpty() )
            {
                ftp = FtpUtil.getFtpConnection( server.getServerIP(), Integer
                    .parseInt( server.getServerPort() ), server.getLoginName(),
                    server.getLoginPassword(), 30000 );

                boolean connect = false;

                if( ftp != null )
                {
                    connect = ftp.isConnected();
                }

                if( connect )// 没有成功连接则无需进行传输操作
                {
                    // FtpUtil.createDirFTP( ftp, "/" + site.getSiteRoot() );

                    SiteFileTransfeStateBean transfeStateBean = null;
                    boolean success = false;
                    StringBuffer successIds = new StringBuffer();
                    String filePath = null;

                    for ( int i = 0; i < needTransferInfo.size(); i++ )
                    {
                        transfeStateBean = ( SiteFileTransfeStateBean ) needTransferInfo
                            .get( i );
                        filePath = transfeStateBean.getFilePath();

                        if( Constant.SITE_CHANNEL.FILE_EVENT_DELETED
                            .equals( transfeStateBean.getFileEventFlag() ) )
                        {
                            success = FtpUtil.deleteFileFTP( ftp, filePath );
                        }
                        else
                        {
                            success = FtpUtil.uploadFileFTP( ftp, filePath
                                .indexOf( "/" ) != -1 ? "/"
                                + StringUtil.subString( filePath, 0, filePath
                                    .lastIndexOf( "/" ) ) : "/", null,
                                new File( transfeStateBean.getFullPath() ) );
                        }

                        if( !success )
                        {
                            // 不成功,改动记录为传输错误状态,等待下一次同步
                            siteService.updateFileTransferStatus(
                                transfeStateBean.getTransferFileId(),
                                Constant.SITE_CHANNEL.FILE_TRAN_STATUS_ERROR );
                        }
                        else
                        {
                            successIds.append(
                                transfeStateBean.getTransferFileId() ).append(
                                "," );
                        }
                    }

                    FtpUtil.closeFtpConnection( ftp );

                    ftp = null;

                    // 删除已经成功FTP活动的文件信息
                    if( successIds.length() > 0 )
                    {
                        siteService
                            .deleteSiteFileTransfeStateBeanByTransferIds( StringUtil
                                .subString( successIds.toString(), 0,
                                    successIds.toString().lastIndexOf( "," ) ) );
                    }

                    // 记录ftp连接成功状态
                    siteService.updateDispenseServerConnectStatus( server
                        .getServerId(), Constant.COMMON.ON );
                }
                else
                {
                    // 记录ftp连接失败状态
                    siteService.updateDispenseServerConnectStatus( server
                        .getServerId(), Constant.COMMON.OFF );

                }
                // siteService.deleteSiteFileTransfeStateBeanByLastTransferId(
                // ( ( SiteFileTransfeStateBean ) needTransferInfo
                // .get( needTransferInfo.size() - 1 ) )
                // .getTransferFileId(), gateway.getGatewayId() );
            }
        }
        catch ( Exception e )
        {
            log.error( e );
            // e.printStackTrace();
        }
        finally
        {
            excuteJob.remove( jobContent.getJobDetail().getKey() );

            FtpUtil.closeFtpConnection( ftp );
        }

    }
}

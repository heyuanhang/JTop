package cn.com.mjsoft.cms.content.job;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.controller.MultipleUploadController;
import cn.com.mjsoft.cms.content.dao.ContentDao;

import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.stat.controller.ClientQuerySiteVisStatController;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;
import cn.com.mjsoft.framework.security.authorization.AuthorizationHandler;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.MailAndSmsUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class PublishAndDisposeContentJob implements Job
{
    private static Logger log = Logger.getLogger( PublishAndDisposeContentJob.class );

    private static ChannelService channelService = ChannelService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    private static ResourcesService resService = ResourcesService.getInstance();

    private static StatService statService = StatService.getInstance();

    private static WeixinService weixinService = WeixinService.getInstance();

    private static Map excuteJob = new ReadWriteLockHashMap();

    public void execute( JobExecutionContext jobContent ) throws JobExecutionException
    {
        if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        {
            log.info( "[PublishAndDisposeContentJob] ...waiting..."
                + jobContent.getJobDetail().getKey() );
            return;
        }

        String uuidKey = StringUtil.getUUIDString();

        try
        {
            excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

            log.info( "[PublishAndDisposeContentJob] ...execute start..."
                + jobContent.getJobDetail().getKey() );

            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            // 以下开始进行系统内部访问
            AuthorizationHandler.setInnerAccessFlag( uuidKey );

            List siteBeanList = InitSiteGroupInfoBehavior.siteGroupListCache;

            Timestamp currTime = DateAndTimeUtil.getTodayTimestampDayAndTime();

            SiteGroupBean site = null;

            URL targetUrl = null;

            List contentInfoList = null;

            for ( int i = 0; i < siteBeanList.size(); i++ )
            {
                site = ( SiteGroupBean ) siteBeanList.get( i );
                // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

                contentInfoList = contentService.retrieveWaitPublishContentBySiteId( site
                    .getSiteId(), currTime );

                Map contentInfo = null;

                for ( int j = 0; j < contentInfoList.size(); j++ )
                {
                    contentInfo = ( Map ) contentInfoList.get( j );

                    contentService.addWaitPublishIdTemp( ( Long ) contentInfo.get( "contentId" ),
                        ( Double ) contentInfo.get( "orderIdFlag" ), ( Long ) contentInfo
                            .get( "classId" ) );

                    // 若存在待发布内容要立即进行内容主信息审核发布状态改动,以便发布静态栏目时能正确的识别
                    // 注意:要更新开始显示时间为当前时间(发布时间),因审核过程可能导致延期,故以实际发布逻辑执行时间为准
                    contentService.updateWaitPublishContentSuccessStatus( contentInfo, currTime );

                    /**
                     * 处理tag信息
                     */

                    List tagIdList = StringUtil.changeStringToList( ( String ) contentInfo
                        .get( "tagKey" ), "\\*" );

                    Long tagId = null;

                    // 先删除所有相关Tag关联
                    channelService.deleteTagRelateContentByContentId( ( Long ) contentInfo
                        .get( "contentId" ) );

                    for ( int x = 0; x < tagIdList.size(); x++ )
                    {
                        if( tagIdList.get( x ) instanceof Long )
                        {
                            tagId = ( Long ) tagIdList.get( x );
                        }
                        else
                        {
                            tagId = Long.valueOf( StringUtil.getLongValue( ( String ) tagIdList
                                .get( x ), -1 ) );
                        }

                        if( tagId.longValue() < 0 )
                        {
                            continue;
                        }

                        channelService.addTagWordRelateContent( tagId, ( Long ) contentInfo
                            .get( "contentId" ) );

                        channelService.updateTagWordRelateContentCount( tagId );
                    }
                }

                if( !contentInfoList.isEmpty() )
                {
                    log.info( "[PublishAndDisposeContentJob] 目标站存在待发内容,site:" + site.getSiteName()
                        + ", count:" + contentInfoList.size() );

                    // 默认端
                    targetUrl = new URL( cmsPath
                        + "/publish/generateContent.do?staticType=3&job=true&censor=wait&siteId="
                        + site.getSiteId() + "&currTime=" + currTime.getTime()
                        + "&innerAccessJtopSysFlag=" + uuidKey );

                    URLConnection URLconnection = targetUrl.openConnection();
                    HttpURLConnection httpConnection = ( HttpURLConnection ) URLconnection;

                    httpConnection.getInputStream();

                    // mob
                    targetUrl = new URL(
                        cmsPath
                            + "/publish/generateContent.do?mob=true&staticType=3&job=true&censor=wait&siteId="
                            + site.getSiteId() + "&currTime=" + currTime.getTime()
                            + "&innerAccessJtopSysFlag=" + uuidKey );

                    URLconnection = targetUrl.openConnection();
                    httpConnection = ( HttpURLConnection ) URLconnection;

                    httpConnection.getInputStream();

                    // pad
                    targetUrl = new URL(
                        cmsPath
                            + "/publish/generateContent.do?pad=true&staticType=3&job=true&censor=wait&siteId="
                            + site.getSiteId() + "&currTime=" + currTime.getTime()
                            + "&innerAccessJtopSysFlag=" + uuidKey );

                    URLconnection = targetUrl.openConnection();
                    httpConnection = ( HttpURLConnection ) URLconnection;

                    httpConnection.getInputStream();
                }
                else
                {
                    log.info( "[PublishAndDisposeContentJob] 目标站无待发内容,site:" + site.getSiteName() );
                }

                if( !contentInfoList.isEmpty() )
                {
                    // 存在发布数据,更新cache
                    ContentDao.releaseAllCountCache();
                    ContentService.releaseContentCache();
                }

                // 下线内容处理
                contentInfoList = contentService.retrieveWithdrawContentBySiteId( site.getSiteId(),
                    currTime );

                for ( int j = 0; j < contentInfoList.size(); j++ )
                {
                    contentInfo = ( Map ) contentInfoList.get( j );
                    // 更新发布状态以及索引
                    contentService.updateWithdrawContentSuccessStatus( contentInfo );
                }

                if( !contentInfoList.isEmpty() )
                {
                    // 存在下线数据,更新cache
                    ContentDao.releaseAllCountCache();
                    ContentService.releaseContentCache();
                }

            }

            // 清除待发布流程辅助数据
            contentService.deleteWaitPublishIdTemp();

            /**
             * 发送邮件任务放在此,处理队列中所有站点需要发送的邮件,注意：必须严格使用队列按照间隔时间发布
             * 
             */

            Map mailInfo = siteService.retrieveSingleSiteEmailSendInfo();

            if( !mailInfo.isEmpty() )
            {
                String mailId = ( String ) mailInfo.get( "mailId" );

                try
                {
                    Long siteId = ( Long ) mailInfo.get( "siteId" );

                    site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                        .getEntry( siteId );

                    if( site != null )
                    {
                        String mailHost = site.getSendMailHost();

                        String mail = site.getMail();

                        String mailUserName = site.getMailUserName();

                        String pw = site.getMailUserPW();

                        Integer sslFlag = site.getMailSSL();

                        boolean sslMode = false;
                        if( sslFlag != null )
                        {
                            sslMode = site.getMailSSL().intValue() == 1 ? true : false;
                        }

                        String sendToStr = ( String ) mailInfo.get( "sendTo" );

                        String subject = ( String ) mailInfo.get( "subject" );

                        String msg = ( String ) mailInfo.get( "mailContent" );

                        String[] sendTo = ( String[] ) StringUtil.changeStringToList( sendToStr,
                            "," ).toArray( new String[] {} );

                        MailAndSmsUtil.sendEmail( mailHost, null, sslMode,
                            Constant.SITE_CHANNEL.DEF_PAGE_CODE, sendTo, mail, mailUserName, mail,
                            pw, subject, msg );
                    }
                }
                finally
                {
                    // 即使发送不成功,一定要删除掉信息,防止阻塞
                    siteService.deleteSiteEmailSendInfo( mailId );
                }
            }

            // 注意： 此为定位任务判断时间间隔，无需使用集群时间
            Calendar currentTime = Calendar.getInstance();

            int min = currentTime.get( Calendar.MINUTE );

            if( min % 5 == 0 )
            {
                /**
                 * 处理被屏蔽客户端IP解禁,系统共用
                 */
                statService.disposeLiftBanBlackClientIpInfo( currTime );

                /**
                 * 清除过期文件
                 */
                resService.clearUselessResource();
            }

            // 预发布状态项目,到达发布时间 .
            // 改为按照小时

            // 以下为删除站点方法，测试使用,请不要开启
             siteService.deleteCMSSiteAllInfo();

            /**
             * 处理外部资源上传控制
             */
            MultipleUploadController.checkIpUpload();

            MultipleUploadController.clearBlackUploadIP();

            /**
             * 每1小时更新各站点访问量
             */

            if( min == 5 )
            {
                ClientQuerySiteVisStatController.SITE_VIS_STAT.clear();
            }

            /**
             * 强制weixin token失效
             */

            if( min == 2 || min == 15 || min == 30 || min == 45 )
            {
                WeixinService.clearTokenCache();
            }

            statService.transferVisitorStatInfoCacheToPe();

            /**
             * 微信群发
             */
            weixinService.sendAllToWeinxinServer();

            log.info( "[PublishAndDisposeContentJob] ...execute over..."
                + jobContent.getJobDetail().getKey() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            excuteJob.remove( jobContent.getJobDetail().getKey() );
            AuthorizationHandler.romoveInnerAccessFlag( uuidKey );
        }

    }
}

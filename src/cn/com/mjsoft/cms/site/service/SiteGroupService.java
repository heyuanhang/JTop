package cn.com.mjsoft.cms.site.service;

import java.io.File;
import java.io.FileFilter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.advert.bean.AdvertContentBean;
import cn.com.mjsoft.cms.advert.dao.AdvertDao;
import cn.com.mjsoft.cms.advert.service.AdvertService;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.block.dao.BlockDao;
import cn.com.mjsoft.cms.block.service.BlockService;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.controller.ListCommendTypeInfoTreeController;
import cn.com.mjsoft.cms.channel.controller.ListContentClassInfoTreeController;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;

import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.dao.ClusterDao;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.comment.dao.CommentDao;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.third.cloud.cos.COSConfig;
import cn.com.mjsoft.cms.common.third.cloud.cos.COSUtil;
import cn.com.mjsoft.cms.common.third.cloud.oss.OSSConfig;
import cn.com.mjsoft.cms.common.third.cloud.oss.OSSUtil;
import cn.com.mjsoft.cms.common.third.cloud.qiniu.QNConfig;
import cn.com.mjsoft.cms.common.third.cloud.qiniu.QNUtil;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.dao.vo.PhotoGroupInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.guestbook.dao.GuestbookDao;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.interflow.dao.InterflowDao;
import cn.com.mjsoft.cms.interflow.service.InterflowService;
import cn.com.mjsoft.cms.member.dao.MemberDao;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.dao.MetaDataDao;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.dao.OrgDao;
import cn.com.mjsoft.cms.pick.dao.PickDao;
import cn.com.mjsoft.cms.pick.service.PickService;
import cn.com.mjsoft.cms.publish.bean.PublishStatusBean;
import cn.com.mjsoft.cms.questionnaire.dao.SurveyDao;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.cms.security.dao.SecurityDao;
import cn.com.mjsoft.cms.site.bean.CmsServerBean;
import cn.com.mjsoft.cms.site.bean.SiteCloudCfgBean;
import cn.com.mjsoft.cms.site.bean.SiteDispenseServerBean;
import cn.com.mjsoft.cms.site.bean.SiteFileInfoBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.bean.SitePublishGatewayBean;
import cn.com.mjsoft.cms.site.dao.SiteGroupDao;
import cn.com.mjsoft.cms.site.dao.vo.SiteDispenseServer;
import cn.com.mjsoft.cms.site.dao.vo.SiteFileTransfeState;
import cn.com.mjsoft.cms.site.dao.vo.SiteGroup;
import cn.com.mjsoft.cms.site.dao.vo.SitePublishGateway;
import cn.com.mjsoft.cms.stat.dao.StatDao;
import cn.com.mjsoft.cms.templet.dao.TemplateDao;
import cn.com.mjsoft.cms.weixin.bean.WxAccount;
import cn.com.mjsoft.cms.weixin.dao.WeixinDao;
import cn.com.mjsoft.cms.workflow.dao.WorkFlowDao;
import cn.com.mjsoft.cms.workflow.dao.vo.Workflow;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowActor;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowStepAction;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowStepInfo;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.authorization.AuthorizationHandler;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.FtpUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

public class SiteGroupService
{
    private static Logger log = Logger.getLogger( SiteGroupService.class );

    private static final Integer QUERY_COUNT = Integer.valueOf( 1000 );// 每次处理1000个

    private static final String SPLIT_CHAR = "*";

    // private static Cache psCache = new ClusterCacheAdapter( 1000,
    // "contentService.listContentCache" );

    private static SiteGroupService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private ScheduleService scheduleService = ScheduleService.getInstance();

    private ResourcesService resService = ResourcesService.getInstance();

    private MetaDataService metaDataService = MetaDataService.getInstance();

    private ContentService contentService = ContentService.getInstance();

    private SurveyService surveyService = SurveyService.getInstance();

    private AdvertService advertService = AdvertService.getInstance();

    private InterflowService inService = InterflowService.getInstance();

    private BlockService blockService = BlockService.getInstance();

    private PickService pickService = PickService.getInstance();

    private MemberService memberService = MemberService.getInstance();

    private ChannelService channelService = ChannelService.getInstance();

    private GuestbookService gbService = GuestbookService.getInstance();

    private StatDao statDao;

    private SiteGroupDao siteGroupDao;

    private ContentDao contentDao = null;

    private ChannelDao channelDao;

    private GuestbookDao gbDao;

    private CommentDao commentDao;

    private SurveyDao surveyDao;

    private AdvertDao advertDao;

    private InterflowDao inDao;

    private OrgDao orgDao = null;

    private BlockDao blockDao;

    private TemplateDao templetDao;

    private MetaDataDao metaDataDao;

    private PickDao pickDao;

    private WorkFlowDao workFlowDao;

    private SecurityDao securityDao;

    private MemberDao memberDao;

    private WeixinDao wxDao;

    private ClusterDao clusterDao;

    private SiteGroupService()
    {
        siteGroupDao = new SiteGroupDao( mysqlEngine );

        contentDao = new ContentDao( mysqlEngine );

        orgDao = new OrgDao( mysqlEngine );

        metaDataDao = new MetaDataDao( mysqlEngine );

        channelDao = new ChannelDao( mysqlEngine );

        gbDao = new GuestbookDao( mysqlEngine );

        commentDao = new CommentDao( mysqlEngine );

        surveyDao = new SurveyDao( mysqlEngine );

        advertDao = new AdvertDao( mysqlEngine );

        inDao = new InterflowDao( mysqlEngine );

        blockDao = new BlockDao( mysqlEngine );

        templetDao = new TemplateDao( mysqlEngine );

        pickDao = new PickDao( mysqlEngine );

        workFlowDao = new WorkFlowDao( mysqlEngine );

        securityDao = new SecurityDao( mysqlEngine );

        statDao = new StatDao( mysqlEngine );

        memberDao = new MemberDao( mysqlEngine );

        wxDao = new WeixinDao( mysqlEngine );

        clusterDao = new ClusterDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new SiteGroupService();
        }
    }

    public static SiteGroupService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public List retrieveAllSiteBean()
    {
        List sitebeanList = siteGroupDao.queryAllSiteBean();

        return sitebeanList;

    }

    public SiteGroupBean retrieveSingleSiteBeanBySiteId( Long siteId )
    {
        SiteGroupBean bean = null;

        bean = siteGroupDao.querySiteBeanById( siteId );

        return bean;
    }

    public void updateSiteStaticUrl( String staticUrl, Long siteId, String flag )
    {

        //
        try
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( siteId );

            if( StringUtil.isStringNull( staticUrl ) || site == null )
            {
                return;
            }

            // 移动
            if( "pc".equals( flag ) )
            {
                if( !staticUrl.equals( site.getHomePageStaticUrl() ) )
                {
                    siteGroupDao.updateSiteStaticUrl( staticUrl, siteId );
                }
            }
            else if( "mob".equals( flag ) )
            {
                if( !staticUrl.equals( site.getMobHomePageStaticUrl() ) )
                {
                    siteGroupDao.updateMobSiteStaticUrl( staticUrl, siteId );
                }
            }
            else if( "pad".equals( flag ) )
            {
                if( !staticUrl.equals( site.getPadHomePageStaticUrl() ) )
                {
                    siteGroupDao.updatePadSiteStaticUrl( staticUrl, siteId );
                }
            }

        }
        finally
        {
            // mysqlEngine.endTransaction();

            // ( ( SiteGroupBean ) InitSiteGroupInfo.siteGroupIdInfoCache
            // .getEntry( siteId ) ).setHomePageStaticUrl( staticUrl );
        }

    }

    public void deleteSiteGroupNode( List idList )
    {
        if( idList == null || !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 主管才可操作
            Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
                .getOrgIdentity();

            SystemOrganizationBean orgBean = orgDao
                .querySingleSystemOrganizationBeanById( loginOrgId );

            if( !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
            {
                return;
            }

            Long siteId = null;

            SiteGroupBean site = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                siteId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( siteId.longValue() < 0 )
                {
                    continue;
                }

                site = siteGroupDao.querySiteBeanById( siteId );

                siteGroupDao.deleteSiteGroupNode( siteId );

                siteGroupDao.saveDeleteSiteGroupTrace( siteId, site.getSiteFlag() );// 存入trace,等待处理

                orgDao.deleteRoleRangeOrgRelateSiteBySiteAndOrg( Long.valueOf( 1 ), siteId );// 总机构去掉默认站点识别

                channelDao.deleteSiteEditorModuleBySite( siteId );

                channelDao.deleteImageratioBySiteId( siteId );

                /**
                 * 删除扩展数据
                 */
                metaDataService.deleteAndClearDefModelInfo( site.getSiteId(), site
                    .getExtDataModelId(), site.getSiteFlag() );
            }
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }
    }

    public void createNewSiteNode( SiteGroup siteGroup )
    {
        if( siteGroup == null )
        {
            return;
        }

        String siteFlag = siteGroup.getSiteFlag();

        if( "core".equalsIgnoreCase( siteFlag ) || "common".equalsIgnoreCase( siteFlag )
            || "WEB-INF".equalsIgnoreCase( siteFlag ) || "sys_temp".equalsIgnoreCase( siteFlag ) )
        {
            return;
        }

        Long siteId = null;

        try
        {
            mysqlEngine.beginTransaction();

            Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
                .getOrgIdentity();

            SystemOrganizationBean orgBean = orgDao
                .querySingleSystemOrganizationBeanById( loginOrgId );

            if( !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
            {
                return;
            }

            if( !siteGroup.getSiteUrl().endsWith( "/" ) )
            {
                siteGroup.setSiteUrl( siteGroup.getSiteUrl() + "/" );
            }

            // 移动站点
            // 初始化
            siteGroup.setMobSiteUrl( siteGroup.getSiteUrl() + "mob/" );

            siteGroup.setPadSiteUrl( siteGroup.getSiteUrl() + "pab/" );

            siteGroup.setSiteRoot( siteGroup.getSiteFlag() );// 站点跟目录和flag相同,且不可改动

            UpdateState us = siteGroupDao.saveSiteGroup( siteGroup );

            if( us.haveKey() )
            {
                /**
                 * 添加站点节点的管理员所在机构以及直系父机构必须同时拥有站点管理权
                 */
                siteId = Long.valueOf( us.getKey() );

                Long newSiteId = Long.valueOf( us.getKey() );

                // 默认的集团总机构必须加上先建站点范围

                Long parentOrgId = Long.valueOf( -1 );

                if( orgBean != null )
                {
                    orgDao.saveRoleRangeOrgRelateSite( loginOrgId, newSiteId );

                    parentOrgId = orgBean.getParentId();
                }

                while ( parentOrgId.longValue() > 0 )
                {
                    orgBean = orgDao.querySingleSystemOrganizationBeanById( parentOrgId );

                    if( orgBean != null )
                    {
                        orgDao.saveRoleRangeOrgRelateSite( parentOrgId, newSiteId );

                        parentOrgId = orgBean.getParentId();
                    }

                }

                // 创建空间主目录
                String systemRoot = SystemConfiguration.getInstance().getSystemConfig()
                    .getSystemRealPath();

                // template
                File newDir = new File( systemRoot + siteGroup.getSiteRoot() + File.separator
                    + Constant.CONTENT.TEMPLATE_BASE );

                newDir.mkdirs();

                // template temp
                newDir = new File( systemRoot + siteGroup.getSiteRoot() + File.separator
                    + Constant.CONTENT.TEMPLATE_TEMP_BASE );

                newDir.mkdirs();

                // image
                newDir = new File( systemRoot + siteGroup.getSiteRoot() + File.separator
                    + Constant.CONTENT.IMG_BASE );

                newDir.mkdirs();

                // media
                newDir = new File( systemRoot + siteGroup.getSiteRoot() + File.separator
                    + Constant.CONTENT.MEDIA_BASE );

                newDir.mkdirs();

                // file
                newDir = new File( systemRoot + siteGroup.getSiteRoot() + File.separator
                    + Constant.CONTENT.FILE_BASE );

                newDir.mkdirs();

                // html
                newDir = new File( systemRoot + siteGroup.getSiteRoot() + File.separator
                    + Constant.CONTENT.HTML_BASE );

                newDir.mkdirs();

                // 编辑器组件信息
                channelService.addNewEditorAllModuleCode( Long.valueOf( us.getKey() ) );

                // 排序信息
                siteGroupDao.updateSiteNodeOrder( siteId, Long.valueOf( 0 ) );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }

        // 索引处理任务启动

        if( siteId != null && siteId.longValue() > 0 )
        {
            scheduleService.startNewCreateContentIndexJob( siteId );
        }

    }

    public void sortSiteNode( Map params )
    {
        try
        {
            mysqlEngine.beginTransaction();

            List anBeanList = siteGroupDao.queryAllSiteBean();

            SiteGroupBean siteBean = null;

            String order = null;

            for ( int i = 0; i < anBeanList.size(); i++ )
            {
                siteBean = ( SiteGroupBean ) anBeanList.get( i );

                order = ( String ) params.get( "orderFlag-" + siteBean.getSiteId() );

                if( StringUtil.getIntValue( order, -1 ) > 0 )
                {
                    siteGroupDao.updateSiteNodeOrder( siteBean.getSiteId(), Long.valueOf( order ) );
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();

        }

    }

    public void updateSiteNodeBaseInfo( SiteGroup siteGroup )
    {
        if( siteGroup == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            if( !siteGroup.getSiteUrl().endsWith( "/" ) )
            {
                siteGroup.setSiteUrl( siteGroup.getSiteUrl() + "/" );
            }

            // 移动站点
            // if(StringUtil.isStringNull( siteGroup.getMobSiteUrl() ))
            {
                siteGroup.setMobSiteUrl( siteGroup.getSiteUrl() + "mob/" );
            }

            // if(StringUtil.isStringNull( siteGroup.getPadSiteUrl() ))
            {
                siteGroup.setPadSiteUrl( siteGroup.getSiteUrl() + "pab/" );
            }

            siteGroupDao.updateSiteGroupBaseInfo( siteGroup );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }

    }

    public void updateSiteInfoBySiteId( SiteGroup siteGroup, DataModelBean model,
        List filedBeanList, ModelPersistenceMySqlCodeBean sqlCodeBean, Map requestParams )
    {
        if( siteGroup == null || siteGroup.getSiteId().intValue() < 0 )
        {
            throw new FrameworkException( "传入的站点信息不完全" );
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 改动:不允许站点改动flag,创建后不可修改
            // // SiteGroupBean selfSiteBean = siteGroupDao
            // // .querySiteBeanById( siteGroupBean.getSiteId() );
            //
            // // if( !selfSiteBean.getSiteFlag()
            // // .equals( siteGroupBean.getSiteFlag() ) )
            // // {
            //
            // // 更新相关表的siteFlag值String currentFlag , String oldFlag
            // // siteGroupDao.updateContentClassSiteFlag( siteGroupBean
            // // .getSiteFlag(), selfSiteBean.getSiteFlag() );
            // //
            // // siteGroupDao.updateBlockTypeSiteFlag( siteGroupBean
            // // .getSiteFlag(), selfSiteBean.getSiteFlag() );
            // //
            // // siteGroupDao.updateSurveyBaseInfoSiteFlag( siteGroupBean
            // // .getSiteFlag(), selfSiteBean.getSiteFlag() );
            // //
            // // siteGroupDao.updateSurveyOptionInfoSiteFlag( siteGroupBean
            // // .getSiteFlag(), selfSiteBean.getSiteFlag() );
            //
            // // 站点根目录改变
            // // String base = SystemConfiguration.getInstance()
            // // .getSystemConfig().getSystemRealPath();
            // //
            // // File siteRoot = new File( base + selfSiteBean.getSiteRoot()
            // // );
            // // if( siteRoot.exists() )
            // // {
            // // siteRoot.renameTo( new File( base
            // // + siteGroupBean.getSiteRoot() ) );
            // // }

            // }

            if( !siteGroup.getSiteUrl().endsWith( "/" ) )
            {
                siteGroup.setSiteUrl( siteGroup.getSiteUrl() + "/" );
            }

            // 移动站点
            // if(StringUtil.isStringNull( siteGroup.getMobSiteUrl() ))
            {
                siteGroup.setMobSiteUrl( siteGroup.getSiteUrl() + "mob/" );
            }

            // if(StringUtil.isStringNull( siteGroup.getPadSiteUrl() ))
            {
                siteGroup.setPadSiteUrl( siteGroup.getSiteUrl() + "pab/" );
            }

            // 文件最小限制，至少1mb

            if( siteGroup.getImageMaxC().intValue() <= 0 )
            {
                siteGroup.setImageMaxC( Integer.valueOf( 1 ) );
            }

            if( siteGroup.getMediaMaxC().intValue() <= 0 )
            {
                siteGroup.setMediaMaxC( Integer.valueOf( 1 ) );
            }

            if( siteGroup.getFileMaxC().intValue() <= 0 )
            {
                siteGroup.setFileMaxC( Integer.valueOf( 1 ) );
            }

            if( siteGroup.getMemberExpire().intValue() <= 0 )
            {
                siteGroup.setMemberExpire( Integer.valueOf( 60 ) );
            }

            siteGroupDao.updateSiteInfoBySiteId( siteGroup );

            /**
             * 站点扩展模型
             */
            // 获取自定义模型数据
            // 确认当前是否有扩展模型
            if( siteGroup.getExtDataModelId().longValue() > 0 && model != null
                && filedBeanList != null && sqlCodeBean != null )
            {
                Integer count = contentDao.queryUserDefinedContentExist( model, siteGroup
                    .getSiteId() );

                ModelFiledInfoBean bean = null;

                List needUploadImageGroupInfoList = new ArrayList();

                List userDefineParamList = new ArrayList();

                String reUrl = null;

                Object val = null;

                for ( int j = 0; j < filedBeanList.size(); j++ )
                {
                    bean = ( ModelFiledInfoBean ) filedBeanList.get( j );
                    // 需要引入filed元数据来对不同类型字段进行对应处理

                    val = ServiceUtil.disposeDataModelFiledFromWeb( bean, requestParams,
                        needUploadImageGroupInfoList, false );

                    userDefineParamList.add( val );

                    // 单图水印处理
                    if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue()
                        && Constant.COMMON.ON.equals( bean.getNeedMark() ) )
                    {
                        // 水印处理
                        reUrl = ServiceUtil.getImageReUrl( ( String ) val );

                        // 已经加过水印的不需要再增加
                        if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                        {
                            if( ServiceUtil.disposeImageMark(
                                ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                                    .getCurrentLoginSiteInfo(), reUrl, Integer.valueOf( ServiceUtil
                                    .getImageW( ( String ) val ) ), Integer.valueOf( ServiceUtil
                                    .getImageH( ( String ) val ) ) ) )
                            {
                                // 成功加水印则更新
                                resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                            }
                        }
                    }
                }

                // 添加ID到最后位置
                userDefineParamList.add( siteGroup.getSiteId() );

                if( count.intValue() == 1 )
                {
                    // 已有数据,更新模式
                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getUpdateSql(),
                        userDefineParamList.toArray() );
                }
                else if( count.intValue() == 0 )
                {
                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );
                }

                /**
                 * 所有图集组件出现的图片入库
                 */
                // 获取原图集记录
                List oldGroupPhotoList = contentDao.queryGroupPhotoInfoByContentId( siteGroup
                    .getSiteId(), Constant.METADATA.MODEL_TYPE_SITE,
                    ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                        .getCurrentLoginSiteInfo(), true );

                // 删除所有原相关信息
                contentDao.deletePhotoGroupInfo( siteGroup.getSiteId(),
                    Constant.METADATA.MODEL_TYPE_SITE );

                // 增加本次改动中出现的所有的图片信息
                PhotoGroupInfo pgi = null;
                Set urlInfoSet = new HashSet();

                for ( int i = 0; i < needUploadImageGroupInfoList.size(); i++ )
                {
                    pgi = ( PhotoGroupInfo ) needUploadImageGroupInfoList.get( i );

                    urlInfoSet.add( pgi.getUrl() );

                    pgi.setContentId( siteGroup.getSiteId() );

                    // 模型类型
                    pgi.setModelType( Constant.METADATA.MODEL_TYPE_SITE );

                    contentDao.saveSingleGroupPhoto( pgi );

                    // 水印处理
                    if( Constant.COMMON.ON.equals( pgi.getNeedMark() ) )
                    {
                        reUrl = ServiceUtil.getImageReUrl( pgi.getUrl() );

                        // 已经加过水印的不需要再增加
                        if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                        {
                            if( ServiceUtil.disposeImageMark(
                                ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                                    .getCurrentLoginSiteInfo(), reUrl, Integer.valueOf( ServiceUtil
                                    .getImageW( pgi.getUrl() ) ), Integer.valueOf( ServiceUtil
                                    .getImageH( pgi.getUrl() ) ) ) )
                            {
                                // 成功加水印则更新
                                resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                            }
                        }
                    }
                }

                Map pgiInfo = null;
                for ( int i = 0; i < oldGroupPhotoList.size(); i++ )
                {
                    pgiInfo = ( Map ) oldGroupPhotoList.get( i );

                    if( !urlInfoSet.contains( pgiInfo.get( "cmsSysUrl" ) ) )
                    {
                        resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil
                            .getLongValue( ( String ) pgiInfo.get( "resId" ), -1 ) ),
                            Constant.COMMON.OFF );
                    }

                }

                // 清除已经存在的数据

                SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                    .getCurrentLoginSiteInfo();

                if( site != null )
                {
                    site.getExt().clear();
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }

        // InitSiteGroupInfo.siteGroupIdInfoCache.putEntry( siteGroupBean
        // .getSiteId(), dao.querySiteBeanById( siteGroupBean.getSiteId() ) );

    }

    public void addServerConfig( SiteDispenseServer serverInfo )
    {
        try
        {
            siteGroupDao.saveSiteDispenseServer( serverInfo );
        }
        finally
        {
            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }
    }

    public void editServerConfig( SiteDispenseServer serverInfo )
    {
        try
        {
            siteGroupDao.updateSiteDispenseServer( serverInfo );
        }
        finally
        {
            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }
    }

    public void deleteSiteServerConfig( Long id )
    {
        try
        {
            siteGroupDao.deleteSiteDispenseServer( id );
        }
        finally
        {
            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }

    }

    public void checkSiteFTPServerStatus( Long siteId )
    {
        List ftpServerList = siteGroupDao.queryDispenseServerBeanBySiteId( siteId );

        SiteDispenseServerBean server = null;

        FTPClient ftp = null;

        for ( int i = 0; i < ftpServerList.size(); i++ )
        {
            server = ( SiteDispenseServerBean ) ftpServerList.get( i );

            ftp = FtpUtil.getFtpConnection( server.getServerIP(), Integer.parseInt( server
                .getServerPort() ), server.getLoginName(), server.getLoginPassword(), 5000 );

            boolean connect = false;

            if( ftp != null )
            {
                connect = ftp.isConnected();
            }

            if( connect )// 成功更新状态
            {
                siteGroupDao.updateServerConnectStatus( server.getServerId(), Constant.COMMON.ON );
            }
            else
            {
                siteGroupDao.updateServerConnectStatus( server.getServerId(), Constant.COMMON.OFF );
            }
        }

    }

    public void addSitePublishGateway( SitePublishGateway gateway, SiteGroupBean currentSiteBean,
        Integer transferPeriod, Integer transferPeriodType )
    {
        gateway.setSiteId( currentSiteBean.getSiteId() );
        gateway.setSiteRoot( currentSiteBean.getSiteRoot() );

        // SystemRuntimeConfig config = SystemConfiguration.getInstance()
        // .getSystemConfig();
        //
        // File targetDir = new File( config.getSystemRealPath()
        // + currentSiteBean.getSiteRoot() + File.separator
        // + gateway.getSourcePath() );
        //
        // if( !targetDir.exists() )
        // {
        // throw new FrameworkException( "监听的目标文件夹不存在！" );
        // }
        try
        {
            mysqlEngine.beginTransaction();

            UpdateState dbState = siteGroupDao.saveSitePublishGateway( gateway );

            if( Constant.COMMON.ON.equals( gateway.getUseState() ) )
            {
                // 将同一类型的其他分发点强制变为无效

                siteGroupDao.updateSitePublishGatewayUseState( gateway.getGatewayId(), gateway
                    .getSiteId(), gateway.getTransfeType(), Constant.COMMON.OFF );

            }

            if( gateway.getUseState().intValue() == 1 && dbState.haveKey() )
            {
                gateway.setGatewayId( Long.valueOf( dbState.getKey() ) );
                // 若立即起用,立即注册文件监视,以及传输任务
                // 路径暂测试使用

                // 注册并按计划启动文件监视任务
                // scheduleService
                // .addNewFileMonitorCollectEventDataAndTransferFileJob(
                // currentSiteBean, gateway, transferPeriod,
                // transferPeriodType, Constant.COMMON.ON );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }
    }

    public void editSitePublishGateway( SitePublishGateway gateway, SiteGroupBean currentSiteBean,
        Integer transferPeriod, Integer transferPeriodType )
    {
        gateway.setSiteId( currentSiteBean.getSiteId() );
        gateway.setSiteRoot( currentSiteBean.getSiteRoot() );

        // SystemRuntimeConfig config = SystemConfiguration.getInstance()
        // .getSystemConfig();
        //
        // File targetDir = new File( config.getSystemRealPath()
        // + currentSiteBean.getSiteRoot() + File.separator
        // + gateway.getSourcePath() );
        //
        // if( !targetDir.exists() )
        // {
        // throw new FrameworkException( "监听的目标文件夹不存在！" );
        // }
        try
        {
            mysqlEngine.beginTransaction();

            siteGroupDao.editSitePublishGateway( gateway );

            if( Constant.COMMON.ON.equals( gateway.getUseState() ) )
            {
                // 将同一类型的其他分发点强制变为无效

                siteGroupDao.updateSitePublishGatewayUseState( gateway.getGatewayId(), gateway
                    .getSiteId(), gateway.getTransfeType(), Constant.COMMON.OFF );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }

        // if( gateway.getUseState().intValue() == 1 && dbState.haveKey() )
        {
            // gateway.setGatewayId( Long.valueOf( dbState.getKey() ) );
            // 若立即起用,立即注册文件监视,以及传输任务
            // TODO 路径暂测试使用

            // 注册并按计划启动文件监视任务
            // scheduleService
            // .addNewFileMonitorCollectEventDataAndTransferFileJob(
            // currentSiteBean, gateway, transferPeriod,
            // transferPeriodType, Constant.COMMON.ON );

        }
    }

    public void deleteSitePublishGateway( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long pgid = null;
            for ( int i = 0; i < idList.size(); i++ )
            {
                pgid = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( pgid.longValue() < 0 )
                {
                    continue;
                }

                siteGroupDao.deleteSitePublishGateway( pgid );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            // 重新替换相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            // 栏目缓存更新
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            // 内容缓存
            ContentDao.releaseAllCountCache();
            ContentService.releaseContentCache();
        }

        // if( gateway.getUseState().intValue() == 1 && dbState.haveKey() )
        {
            // gateway.setGatewayId( Long.valueOf( dbState.getKey() ) );
            // 若立即起用,立即注册文件监视,以及传输任务
            // TODO 路径暂测试使用

            // 注册并按计划启动文件监视任务
            // scheduleService
            // .addNewFileMonitorCollectEventDataAndTransferFileJob(
            // currentSiteBean, gateway, transferPeriod,
            // transferPeriodType, Constant.COMMON.ON );

        }
    }

    public List retrieveDispenseServerBeanBySiteFlag( Long siteId )
    {

        List beanList = null;

        if( siteId == null )
        {
            beanList = siteGroupDao.queryAllDispenseServerBean();
        }
        else
        {
            beanList = siteGroupDao.queryDispenseServerBeanBySiteId( siteId );
        }

        return beanList;

    }

    public SiteDispenseServerBean retrieveSingleDispenseServerBeanById( Long id, Long siteId )
    {

        SiteDispenseServerBean bean = siteGroupDao.querySingleDispenseServerBeanById( id, siteId );

        return bean;
    }

    public List retrievePublishGatewayBeanBySiteFlag( Long siteId )
    {

        List beanList = siteGroupDao.querysSitePublishGatewayBeanBySiteId( siteId );

        return beanList;
    }

    public SitePublishGatewayBean retrieveSinglePublishGatewayBeanById( Long gwId, Long siteId )
    {

        SitePublishGatewayBean bean = siteGroupDao
            .querysSinglePublishGatewayBeanById( gwId, siteId );

        return bean;
    }

    public void addNewCloudConfig( SiteCloudCfgBean cfg )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        cfg.setSiteId( site.getSiteId() );

        siteGroupDao.saveCloudCfgBean( cfg );

        // 重新替换相关site的cache
        SiteGroupDao.clearPSCache();

        InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

        // 栏目缓存更新
        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        ChannelDao.clearAllCache();
        ChannelService.clearContentClassCache();

        ContentDao.releaseAllCountCache();
        ContentService.releaseContentCache();
    }

    public void editCloudConfig( SiteCloudCfgBean cfg )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        cfg.setSiteId( site.getSiteId() );

        siteGroupDao.updateCloudCfgBean( cfg );

        // 重新替换相关site的cache
        SiteGroupDao.clearPSCache();

        InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

        // 栏目缓存更新
        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        ChannelDao.clearAllCache();
        ChannelService.clearContentClassCache();

        ContentDao.releaseAllCountCache();
        ContentService.releaseContentCache();
    }

    public void deleteCloudConfig( Long cloId )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        siteGroupDao.deleteCloudCfgBeanById( cloId, site.getSiteId() );

        // 重新替换相关site的cache
        SiteGroupDao.clearPSCache();

        InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

        // 栏目缓存更新
        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        ChannelDao.clearAllCache();
        ChannelService.clearContentClassCache();

        ContentDao.releaseAllCountCache();
        ContentService.releaseContentCache();
    }

    public String checkCloudConfig( Long cloId )
    {
        String base = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String etag = null;

        SiteCloudCfgBean cfgBean = siteGroupDao.querysSingleCloudCfgBean( cloId );

        if( Constant.RESOURCE.CLOUD_COS.equals( cfgBean.getCloudType() ) )
        {
            COSConfig cfg = cfgBean.toCOSCfg();

            etag = COSUtil.simpleUploadFileFromLocal( cfg,
                base + "core/style/blue/images/logo.jpg", "checkupload/testfile.jpg" );

        }
        else if( Constant.RESOURCE.CLOUD_OSS.equals( cfgBean.getCloudType() ) )
        {
            OSSConfig cfg = cfgBean.toOSSCfg();

            etag = OSSUtil.uploadFile( cfg, base + "core/style/blue/images/logo.jpg",
                "checkupload/testfile.jpg" );

        }
        else if( Constant.RESOURCE.CLOUD_QN.equals( cfgBean.getCloudType() ) )
        {
            QNConfig cfg = cfgBean.toQNCfg();

            etag = QNUtil.uploadFile( cfg, base + "core/style/blue/images/logo.jpg",
                "checkupload/testfile.jpg" );

        }

        if( StringUtil.isStringNull( etag ) || etag.toLowerCase().indexOf( "error" ) != -1 )
        {
            return etag;
        }

        return "true";

    }

    public Object getCloudCfgForTag( String cloIdv, String siteIdv )
    {
        Long cloId = StringUtil.getLongValue( cloIdv, -1 );

        Long siteId = StringUtil.getLongValue( siteIdv, -1 );

        if( cloId.longValue() > 0 )
        {
            return siteGroupDao.querysSingleCloudCfgBean( cloId );
        }
        else if( siteId.longValue() > 0 )
        {
            return siteGroupDao.querysCloudCfgBeanBySiteId( siteId );
        }

        return siteId;
    }

    /**
     * 获取站点文件夹下指定目标层的所有目录,以及文件
     * 
     * @param site 站点名
     * @param entry 需要进入的子目录
     * @param rootPath 根目录物理路径
     * @param folderFilter 目录筛选
     * @param fileFilter 文件筛选
     * @return
     */
    public List retrieveAllSiteFileInfoByPath( String sitse, String entry, String rootPath,
        FileFilter folderFilter, FileFilter fileFilter )
    {
        String fullPath = "";

        if( StringUtil.isStringNull( entry ) )
        {
            fullPath = rootPath;
        }
        else
        {
            // 禁止非法进入目录
            if( entry.indexOf( "../" ) != -1 || entry.indexOf( "..%2F" ) != -1
                || entry.indexOf( "..%2f" ) != -1 || entry.indexOf( "WEB-INF" ) != -1 )
            {
                return Collections.EMPTY_LIST;
            }

            String endEntry = StringUtil.replaceString( entry, SPLIT_CHAR, File.separator, false,
                false );

            log.info( "最终入口:" + endEntry );

            fullPath = rootPath + endEntry;

        }

        List fileResultList = new ArrayList();

        File[] folders = FileUtil.getAllFile( fullPath, folderFilter );

        File[] files = FileUtil.getAllFile( fullPath, fileFilter );

        SiteFileInfoBean bean;
        String targetPath = "";
        if( folders != null )
        {

            for ( int i = 0; i < folders.length; i++ )
            {
                bean = new SiteFileInfoBean();
                bean.setFileName( folders[i].getName() );
                bean.setLastModifyTime( DateAndTimeUtil.getFormatDate( folders[i].lastModified(),
                    DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

                bean.setCreator( "Admin" );

                targetPath = StringUtil.replaceString( folders[i].getPath(), rootPath, "", false,
                    true );

                bean.setEntry( StringUtil.replaceString( targetPath, File.separator, SPLIT_CHAR,
                    false, false ) );
                bean.setSize( folders[i].length() );
                bean.setDir( true );
                fileResultList.add( bean );
            }
        }

        if( files != null )
        {
            for ( int j = 0; j < files.length; j++ )
            {
                bean = new SiteFileInfoBean();
                bean.setFileName( files[j].getName() );
                bean.setLastModifyTime( DateAndTimeUtil.getFormatDate( files[j].lastModified(),
                    DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );
                targetPath = StringUtil.replaceString( files[j].getPath(), rootPath, "", false,
                    true );

                bean.setEntry( StringUtil.replaceString( targetPath, File.separator, SPLIT_CHAR,
                    false, false ) );

                String fileName = files[j].getName();
                String fileType = StringUtil.subString( fileName, fileName.lastIndexOf( "." ) + 1,
                    fileName.length() );

                // TODO 暂时的
                bean.setCreator( "Admin" );

                bean.setType( fileType );
                bean.setSize( files[j].length() );
                bean.setDir( false );
                fileResultList.add( bean );
            }
        }
        return fileResultList;

    }

    public void addNewSiteFileTransfeState( Map transferData, Long gatewayId )
    {
        if( transferData == null || transferData.isEmpty() )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Iterator iter = transferData.keySet().iterator();
            SiteFileTransfeState bean;
            mysqlEngine.startBatch();
            while ( iter.hasNext() )
            {
                bean = ( ( SiteFileTransfeState ) transferData.get( iter.next() ) );

                bean.setGatewayId( gatewayId );

                siteGroupDao.saveNewFileNotifyInfo( bean );
            }
            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void updateDispenseServerConnectStatus( Long serverId, Integer status )
    {
        siteGroupDao.updateServerConnectStatus( serverId, status );
    }

    public List retrieveSiteFileTransfeStateBeanByGatewayId( Long gatewayId )
    {
        return siteGroupDao.querySiteFileTransfeStateBeanByGatewayId( gatewayId );
    }

    public void deleteSiteFileTransfeStateBeanByTransferStatus( Integer status )
    {
        siteGroupDao.deleteSiteFileTransfeStateBeanByTransferStatus( status );
    }

    public void deleteSiteFileTransfeStateBeanByTransferId( List ids )
    {
        if( ids == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < ids.size(); i++ )
            {
                siteGroupDao.deleteSiteFileTransfeStateBeanByTransferId( ( Long ) ids.get( i ) );
            }
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deleteSiteFileTransfeStateBeanByLastTransferId( Long lastId, Long gatewayId )
    {
        siteGroupDao.deleteSiteFileSuccessTransfeStateBeanByLastId( lastId, gatewayId );
    }

    public void deleteSiteFileTransfeStateBeanByTransferIds( String idsFlag )
    {
        siteGroupDao.deleteSiteFileSuccessTransfeStateBeanByIdsFlag( idsFlag );
    }

    public void updateFileTransferStatus( Long transferFileId, Integer status )
    {
        siteGroupDao.updateFileTransferStatus( transferFileId, status );
    }

    public void addDefaultModelTemplate( Long siteId, Long dataModelId, String listTemplate,
        String contentTemplate )
    {
        siteGroupDao.saveModelTemplate( siteId, dataModelId, listTemplate, contentTemplate );
    }

    public void deleteDefaultModelTemplateBySiteId( Long siteId )
    {
        siteGroupDao.deleteDefaultModelTemplateBySiteId( siteId );
    }

    public void disposeDefaultModelTemplate( Long siteId, Map params )
    {

        try
        {
            mysqlEngine.beginTransaction();

            // 首先删除
            siteGroupDao.deleteDefaultModelTemplateBySiteId( siteId );

            List dataModelBeanList = metaDataService.retrieveAllDataModelBeanList(
                Constant.METADATA.MD_IS_ALL_STATE, Constant.METADATA.MODEL_TYPE_CONTENT, siteId,
                "-1" );

            DataModelBean model = null;

            String listTemplate = null;
            String contentTemplate = null;

            for ( int i = 0; i < dataModelBeanList.size(); i++ )
            {
                model = ( DataModelBean ) dataModelBeanList.get( i );

                listTemplate = ( String ) params.get( model.getDataModelId() + "-list" );

                contentTemplate = ( String ) params.get( model.getDataModelId() + "-content" );

                siteGroupDao.saveModelTemplate( siteId, model.getDataModelId(), listTemplate,
                    contentTemplate );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    /**
     * 同步所有指定文件到资源服务器或云
     * 
     * @param siteBean
     * @param gwId
     */
    public void transferAllDataByTypeAndGateway( SiteGroupBean siteBean, Long gwId )
    {
        if( siteBean == null )
        {
            return;
        }

        SitePublishGatewayBean gwBean = siteGroupDao.querysSinglePublishGatewayBeanById( gwId,
            siteBean.getSiteId() );

        if( gwBean == null || Constant.COMMON.OFF.equals( gwBean.getUseState() ) )
        {
            return;
        }

        SiteCloudCfgBean cloud = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

        SiteDispenseServerBean server = siteGroupDao
            .querySingleSiteDispenseServerBeanByserverId( gwBean.getTargetServerId() );

        if( server == null && cloud == null )
        {
            return;
        }

        String tranRoot = "";

        if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() ) )
        {
            tranRoot = Constant.CONTENT.HTML_BASE;
        }
        else if( Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE.equals( gwBean.getTransfeType() ) )
        {
            tranRoot = Constant.CONTENT.IMG_BASE;
        }
        else if( Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA.equals( gwBean.getTransfeType() ) )
        {
            tranRoot = Constant.CONTENT.MEDIA_BASE;
        }
        else if( Constant.SITE_CHANNEL.TRAN_TYPE_FILE.equals( gwBean.getTransfeType() ) )
        {
            tranRoot = Constant.CONTENT.FILE_BASE;
        }
        else if( Constant.SITE_CHANNEL.TRAN_TYPE_TEMPLATE.equals( gwBean.getTransfeType() ) )
        {
            tranRoot = Constant.CONTENT.TEMPLATE_BASE;
        }

        String siteResRoot = "";

        if( !Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() ) )
        {
            siteResRoot = siteBean.getSiteRoot() + File.separator + tranRoot;
        }

        String siteTrueResRoot = siteBean.getSiteRoot() + File.separator + tranRoot;

        String siteRootPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath()
            + siteTrueResRoot;

        List fs = new ArrayList();

        // 获取所有文件
        FileUtil.refreshFileList( siteRootPath, fs );

        // 云储存
        if( cloud != null )
        {
            String fullPath = null;

            String base = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

            String key = null;

            for ( int i = 0; i < fs.size(); i++ )
            {
                fullPath = ( ( File ) fs.get( i ) ).getAbsolutePath();

                if( Constant.SITE_CHANNEL.TRAN_TYPE_TEMPLATE.equals( gwBean.getTransfeType() ) )
                {
                    // 资源类型不需要传递模板文件
                    if( fullPath.toLowerCase().lastIndexOf( ".jsp" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".thtml" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".jspx" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".php" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".asp" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".aspx" ) != -1 )
                    {
                        continue;
                    }

                }

                key = StringUtil.replaceString( StringUtil.replaceString( fullPath, base, "" ),
                    File.separator, "/" );

                if( Constant.RESOURCE.CLOUD_COS.equals( cloud.getCloudType() ) )
                {
                    COSConfig cfg = cloud.toCOSCfg();

                    COSUtil.simpleUploadFileFromLocal( cfg, fullPath, key );
                }
                else if( Constant.RESOURCE.CLOUD_OSS.equals( cloud.getCloudType() ) )
                {
                    OSSConfig cfg = cloud.toOSSCfg();

                    OSSUtil.uploadFile( cfg, fullPath, key );
                }
                else if( Constant.RESOURCE.CLOUD_QN.equals( cloud.getCloudType() ) )
                {
                    QNConfig cfg = cloud.toQNCfg();

                    QNUtil.uploadFile( cfg, fullPath, key );

                }

                log.info( "[云存储传输成功 ：]" + fullPath );
            }
        }
        else if( Constant.SITE_CHANNEL.PROTOCOL_CURRENT_HOST.equals( server.getProtocol() ) )
        {
            String targetServerBasePath = "";

            String context = JtRuntime.cmsServer.getContext();

            File testDir = null;

            if( StringUtil.isStringNotNull( context ) )
            {
                targetServerBasePath = server.getFileRoot() + File.separator + context
                    + File.separator + siteResRoot;

                if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() ) )
                {
                    // 需要支持block特殊目录
                    testDir = new File( server.getFileRoot() + File.separator + context
                        + File.separator + siteBean.getSiteRoot() + File.separator + tranRoot
                        + File.separator + ".block" );

                    if( !testDir.exists() )
                    {
                        testDir.mkdirs();
                    }
                }
            }
            else
            {
                targetServerBasePath = server.getFileRoot() + File.separator + siteResRoot;

                if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() ) )
                {
                    // 需要支持block特殊目录
                    testDir = new File( server.getFileRoot() + File.separator
                        + siteBean.getSiteRoot() + File.separator + tranRoot + File.separator
                        + ".block" );

                    if( !testDir.exists() )
                    {
                        testDir.mkdirs();
                    }
                }
            }

            // 复制目标文件夹结构
            testDir = new File( targetServerBasePath );

            if( !testDir.exists() )
            {
                testDir.mkdirs();
            }

            if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() ) )
            {
                FileUtil.copyOnlyFolder( siteRootPath, targetServerBasePath, ".block" );
            }
            else
            {
                FileUtil.copyOnlyFolder( siteRootPath, targetServerBasePath, null );

            }

            // 本机文件
            String fullPath = null;

            String newPath = null;

            for ( int i = 0; i < fs.size(); i++ )
            {
                fullPath = ( ( File ) fs.get( i ) ).getAbsolutePath();

                if( Constant.SITE_CHANNEL.TRAN_TYPE_TEMPLATE.equals( gwBean.getTransfeType() ) )
                {
                    // 资源类型不需要传递模板文件
                    if( fullPath.toLowerCase().lastIndexOf( ".jsp" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".thtml" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".jspx" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".php" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".asp" ) != -1
                        || fullPath.toLowerCase().lastIndexOf( ".aspx" ) != -1 )
                    {
                        continue;
                    }

                }

                if( StringUtil.isStringNotNull( context ) )
                {
                    if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() )
                        && fullPath.indexOf( File.separator + ".block" + File.separator ) == -1 )
                    {
                        newPath = server.getFileRoot()
                            + File.separator
                            + context
                            + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                                + siteTrueResRoot )
                                + ( siteTrueResRoot.length() + 1 ), fullPath.length() );
                    }
                    else
                    {
                        newPath = server.getFileRoot()
                            + File.separator
                            + context
                            + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                                + siteTrueResRoot ), fullPath.length() );
                    }

                    FileUtil.copyFile( fullPath, newPath );

                    log.info( "[传输成功 ：]" + newPath );
                }
                else
                {
                    if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() )
                        && fullPath.indexOf( File.separator + ".block" + File.separator ) == -1 )
                    {
                        newPath = server.getFileRoot()
                            + File.separator
                            + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                                + siteTrueResRoot )
                                + ( siteTrueResRoot.length() + 1 ), fullPath.length() );
                    }
                    else
                    {
                        newPath = server.getFileRoot()
                            + File.separator
                            + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                                + siteTrueResRoot ), fullPath.length() );
                    }

                    FileUtil.copyFile( fullPath, newPath );

                    log.info( "[传输成功 ：]" + newPath );
                }

            }
        }
        else
        {
            // FTP和SFTP

            FTPClient ftp = FtpUtil.getFtpConnection( server.getServerIP(), Integer
                .parseInt( server.getServerPort() ), server.getLoginName(), server
                .getLoginPassword(), 30000 );

            boolean connect = false;

            if( ftp != null )
            {
                connect = ftp.isConnected();
            }

            try
            {
                if( connect )// 没有成功连接则无需进行传输操作
                {
                    String fullPath = null;

                    // String prefixPath = "";
                    String newPath = null;

                    for ( int i = 0; i < fs.size(); i++ )
                    {
                        fullPath = ( ( File ) fs.get( i ) ).getAbsolutePath();

                        if( Constant.SITE_CHANNEL.TRAN_TYPE_TEMPLATE.equals( gwBean
                            .getTransfeType() ) )
                        {
                            // 资源类型不需要传递模板文件
                            if( fullPath.toLowerCase().lastIndexOf( ".jsp" ) != -1
                                || fullPath.toLowerCase().lastIndexOf( ".thtml" ) != -1
                                || fullPath.toLowerCase().lastIndexOf( ".jspx" ) != -1
                                || fullPath.toLowerCase().lastIndexOf( ".php" ) != -1
                                || fullPath.toLowerCase().lastIndexOf( ".asp" ) != -1
                                || fullPath.toLowerCase().lastIndexOf( ".aspx" ) != -1 )
                            {
                                continue;
                            }

                        }

                        String prefix = "";

                        if( StringUtil.isStringNotNull( JtRuntime.cmsServer.getContext() ) )
                        {
                            prefix = File.separator + JtRuntime.cmsServer.getContext()
                                + File.separator;
                        }

                        if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() )
                            && fullPath.indexOf( File.separator + ".block" + File.separator ) == -1 )
                        {

                            newPath = prefix
                                + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                                    + siteTrueResRoot )
                                    + ( siteTrueResRoot.length() + 1 ), fullPath
                                    .lastIndexOf( File.separator ) );
                        }
                        else
                        {
                            newPath = prefix
                                + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                                    + siteTrueResRoot ), fullPath.lastIndexOf( File.separator ) );
                        }

                        FtpUtil.uploadFileFTP( ftp, newPath, null, ( File ) fs.get( i ) );

                        log.info( "[传输成功 ：]" + fullPath );
                    }
                }
            }
            finally
            {
                FtpUtil.closeFtpConnection( ftp );
            }
        }

    }

    /**
     * 将上传的文件分发到服务器
     * 
     * @param resList
     */
    public void transferUpdateDataToServer( List resList )
    {

        SiteResource resBean = null;

        SitePublishGatewayBean gwBean = null;

        String context = JtRuntime.cmsServer.getContext();

        String newPath = null;

        String fullPath = null;

        for ( int i = 0; i < resList.size(); i++ )
        {
            resBean = ( SiteResource ) resList.get( i );

            SiteGroupBean siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( resBean.getSiteId() );

            if( siteBean == null )
            {
                continue;
            }

            /**
             * 集群模式传播文件
             */

            SiteResourceBean res = resService.retrieveSingleResourceBeanBySource( resBean
                .getResSource() );

            if( res == null
                && ( resBean.getResSource().endsWith( ".swf" ) || resBean.getResSource().endsWith(
                    ".pdf" ) ) )
            {
                // 文库附属文件
                res = new SiteResourceBean();

                res.setSiteId( resBean.getSiteId() );
                res.setResSource( resBean.getResSource() );
                res.setResType( resBean.getResType() );
            }

            ClusterService.getInstance().broadcastUploadFileToCluster( res );

            /**
             * 云存储上传文件
             */
            resService.uploadFileToCloud( res );

            if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resBean.getResType() ) )
            {
                gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite(
                    resBean.getSiteId(), Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE );
            }
            else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resBean.getResType() )
                || Constant.RESOURCE.MUSIC_RES_TYPE.equals( resBean.getResType() ) )
            {
                gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite(
                    resBean.getSiteId(), Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA );
            }
            else if( Constant.RESOURCE.DOC_RES_TYPE.equals( resBean.getResType() )
                || Constant.RESOURCE.ANY_RES_TYPE.equals( resBean.getResType() ) )
            {
                gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite(
                    resBean.getSiteId(), Constant.SITE_CHANNEL.TRAN_TYPE_FILE );
            }

            if( gwBean == null )
            {
                continue;
            }

            SiteDispenseServerBean server = siteGroupDao
                .querySingleSiteDispenseServerBeanByserverId( gwBean.getTargetServerId() );

            if( server == null )
            {
                continue;
            }

            String rootPath = SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath();

            String tranRoot = "";

            if( Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE.equals( gwBean.getTransfeType() ) )
            {
                tranRoot = Constant.CONTENT.IMG_BASE;
            }
            else if( Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA.equals( gwBean.getTransfeType() ) )
            {
                tranRoot = Constant.CONTENT.MEDIA_BASE;
            }
            else if( Constant.SITE_CHANNEL.TRAN_TYPE_FILE.equals( gwBean.getTransfeType() ) )
            {
                tranRoot = Constant.CONTENT.FILE_BASE;
            }

            String siteResRoot = siteBean.getSiteRoot() + File.separator + tranRoot;

            String siteRootPath = rootPath + siteResRoot;

            if( Constant.SITE_CHANNEL.PROTOCOL_CURRENT_HOST.equals( server.getProtocol() ) )
            {
                fullPath = siteRootPath
                    + File.separator
                    + StringUtil.replaceString( resBean.getResSource(), "/", File.separator, false,
                        false );

                String targetServerBasePath = "";

                File testDir = null;

                String testTargetDir = null;

                if( StringUtil.isStringNotNull( context ) )
                {
                    targetServerBasePath = server.getFileRoot() + File.separator + context
                        + File.separator;
                }
                else
                {
                    targetServerBasePath = server.getFileRoot() + File.separator;
                }

                // 复制目标文件夹结构

                // 模板文件夹是否存在
                testTargetDir = targetServerBasePath
                    + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                        + siteBean.getSiteRoot() + File.separator + tranRoot ) + 1, fullPath
                        .lastIndexOf( File.separator ) );

                testDir = new File( testTargetDir );

                if( !testDir.exists() )
                {
                    testDir.mkdirs();
                }

                newPath = targetServerBasePath
                    + siteBean.getSiteRoot()
                    + File.separator
                    + tranRoot
                    + File.separator
                    + StringUtil.replaceString( resBean.getResSource(), "/", File.separator, false,
                        false );

                FileUtil.copyFile( fullPath, newPath );

                log.info( "[传输上传文件成功 ：]" + newPath );

                if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resBean.getResType() ) )
                {
                    fullPath = siteRootPath
                        + File.separator
                        + StringUtil.replaceString( StringUtil.replaceString( resBean
                            .getResSource(), "/", "/imgResize", false, false ), "/",
                            File.separator, false, false );

                    newPath = targetServerBasePath
                        + siteBean.getSiteRoot()
                        + File.separator
                        + tranRoot
                        + File.separator
                        + StringUtil.replaceString( StringUtil.replaceString( resBean
                            .getResSource(), "/", "/imgResize", false, false ), "/",
                            File.separator, false, false );

                    FileUtil.copyFile( fullPath, newPath );

                    log.info( "[传输上传文件成功 ：]" + newPath );
                }
            }
            else
            {
                // FTP和SFTP

                FTPClient ftp = FtpUtil.getFtpConnection( server.getServerIP(), Integer
                    .parseInt( server.getServerPort() ), server.getLoginName(), server
                    .getLoginPassword(), 30000 );

                boolean connect = false;

                if( ftp != null )
                {
                    connect = ftp.isConnected();
                }

                try
                {
                    if( connect )// 没有成功连接则无需进行传输操作
                    {

                        fullPath = siteRootPath
                            + File.separator
                            + StringUtil.replaceString( resBean.getResSource(), "/",
                                File.separator, false, false );

                        if( StringUtil.isStringNotNull( context ) )
                        {
                            newPath = context
                                + File.separator
                                + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                                    + siteResRoot ), fullPath.lastIndexOf( File.separator ) );
                        }
                        else
                        {
                            newPath = StringUtil.subString( fullPath, fullPath
                                .indexOf( File.separator + siteResRoot ), fullPath
                                .lastIndexOf( File.separator ) );
                        }

                        FtpUtil.uploadFileFTP( ftp, newPath, null, new File( fullPath ) );

                        log.info( "[传输成功 ：]" + fullPath );

                        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resBean.getResType() ) )
                        {
                            fullPath = siteRootPath
                                + File.separator
                                + StringUtil.replaceString( StringUtil.replaceString( resBean
                                    .getResSource(), "/", "/imgResize", false, false ), "/",
                                    File.separator, false, false );

                            if( StringUtil.isStringNotNull( context ) )
                            {
                                newPath = context
                                    + File.separator
                                    + StringUtil.subString( fullPath, fullPath
                                        .indexOf( File.separator + siteResRoot ), fullPath
                                        .lastIndexOf( File.separator ) );
                            }
                            else
                            {
                                newPath = StringUtil.subString( fullPath, fullPath
                                    .indexOf( File.separator + siteResRoot ), fullPath
                                    .lastIndexOf( File.separator ) );
                            }

                            FtpUtil.uploadFileFTP( ftp, newPath, null, new File( fullPath ) );

                            log.info( "[传输成功 ：]" + fullPath );

                        }
                    }
                }
                finally
                {
                    FtpUtil.closeFtpConnection( ftp );
                }
            }

        }

    }

    /**
     * 将生成的静态文件分发到服务器
     * 
     * @param resList
     */
    public void transferGenHtmlDataToServer( List fileInfoList, SiteGroupBean siteBean,
        PublishStatusBean status )
    {
        if( siteBean == null )
        {
            return;
        }

        String filePath = null;

        String fullPath = null;

        // 集群模式发送文件到节点
        if( JtRuntime.cmsServer.getClusterMode() )
        {

            for ( int i = 0; i < fileInfoList.size(); i++ )
            {
                filePath = ( ( String[] ) fileInfoList.get( i ) )[0];

                fullPath = ( ( String[] ) fileInfoList.get( i ) )[1];

                ClusterService.getInstance().broadcastFileToCluster(
                    siteBean.getSiteRoot() + File.separator + siteBean.getPublishRoot()
                        + File.separator + filePath, fullPath );
            }

        }

        SitePublishGatewayBean gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite(
            siteBean.getSiteId(), Constant.SITE_CHANNEL.TRAN_TYPE_HTML );

        if( gwBean == null )
        {
            return;
        }

        SiteDispenseServerBean server = siteGroupDao
            .querySingleSiteDispenseServerBeanByserverId( gwBean.getTargetServerId() );

        if( server == null )
        {
            return;
        }

        filePath = null;

        fullPath = null;

        String newPath = null;

        FTPClient ftp = null;

        if( !Constant.SITE_CHANNEL.PROTOCOL_CURRENT_HOST.equals( server.getProtocol() ) )
        {

            ftp = FtpUtil.getFtpConnection( server.getServerIP(), Integer.parseInt( server
                .getServerPort() ), server.getLoginName(), server.getLoginPassword(), 30000 );
        }

        try
        {
            for ( int i = 0; i < fileInfoList.size(); i++ )
            {
                filePath = ( ( String[] ) fileInfoList.get( i ) )[0];

                fullPath = ( ( String[] ) fileInfoList.get( i ) )[1];

                if( Constant.SITE_CHANNEL.PROTOCOL_CURRENT_HOST.equals( server.getProtocol() ) )
                {
                    String targetServerBasePath = "";

                    String context = JtRuntime.cmsServer.getContext();

                    File testDir = null;
                    if( StringUtil.isStringNotNull( context ) )
                    {
                        targetServerBasePath = server.getFileRoot() + File.separator + context
                            + File.separator;

                        if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() ) )
                        {
                            // 需要支持block特殊目录
                            testDir = new File( server.getFileRoot() + File.separator + context
                                + File.separator + siteBean.getSiteRoot() + File.separator
                                + Constant.CONTENT.HTML_BASE + File.separator + ".block" );

                            if( !testDir.exists() )
                            {
                                testDir.mkdirs();
                            }
                        }
                    }
                    else
                    {
                        targetServerBasePath = server.getFileRoot() + File.separator;

                        if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() ) )
                        {
                            // 需要支持block特殊目录
                            testDir = new File( server.getFileRoot() + File.separator
                                + siteBean.getSiteRoot() + File.separator
                                + Constant.CONTENT.HTML_BASE + File.separator + ".block" );

                            if( !testDir.exists() )
                            {
                                testDir.mkdirs();
                            }
                        }
                    }

                    // 复制目标文件夹结构
                    testDir = new File( targetServerBasePath );

                    if( !testDir.exists() )
                    {
                        testDir.mkdirs();
                    }

                    String siteTrueResRoot = siteBean.getSiteRoot() + File.separator
                        + Constant.CONTENT.HTML_BASE;

                    String siteRootPath = SystemConfiguration.getInstance().getSystemConfig()
                        .getSystemRealPath()
                        + siteTrueResRoot;

                    if( Constant.SITE_CHANNEL.TRAN_TYPE_HTML.equals( gwBean.getTransfeType() ) )
                    {
                        FileUtil.copyOnlyFolder( siteRootPath, targetServerBasePath, ".block" );
                    }
                    else
                    {
                        FileUtil.copyOnlyFolder( siteRootPath, targetServerBasePath, null );

                    }

                    if( StringUtil.isStringNotNull( context ) )
                    {
                        if( filePath.startsWith( File.separator ) )
                        {
                            newPath = server.getFileRoot() + File.separator + context + filePath;
                        }
                        else
                        {
                            newPath = server.getFileRoot() + File.separator + context
                                + File.separator + filePath;
                        }
                    }
                    else
                    {
                        if( filePath.startsWith( File.separator ) )
                        {
                            newPath = server.getFileRoot() + filePath;
                        }
                        else
                        {
                            newPath = server.getFileRoot() + File.separator + filePath;
                        }

                    }

                    FileUtil.copyFile( fullPath, newPath );

                    log.info( "[传输成功 ：]" + newPath );

                    status.setTranContentCurrent( Long.valueOf( status.getTranContentCurrent()
                        .longValue() + 1 ) );

                }
                else
                {
                    // FTP和SFTP

                    boolean connect = false;

                    if( ftp != null )
                    {
                        connect = ftp.isConnected();
                    }

                    if( connect )// 没有成功连接则无需进行传输操作
                    {
                        String prefix = "";

                        if( StringUtil.isStringNotNull( JtRuntime.cmsServer.getContext() ) )
                        {
                            prefix = File.separator + JtRuntime.cmsServer.getContext()
                                + File.separator;
                        }

                        FtpUtil.uploadFileFTP( ftp, prefix
                            + ( ( filePath.indexOf( File.separator ) != -1 ) ? StringUtil
                                .subString( filePath, 0, filePath.lastIndexOf( File.separator ) )
                                : "" ), null, new File( fullPath ) );

                        log.info( "[传输html成功 ：]" + fullPath );

                        status.setTranContentCurrent( Long.valueOf( status.getTranContentCurrent()
                            .longValue() + 1 ) );
                    }
                }
            }
        }
        finally
        {
            FtpUtil.closeFtpConnection( ftp );
        }

    }

    /**
     * 将站点资源文件(CSS,js,图片)分发到服务器
     * 
     * @param resList
     */
    public void transferSiteTemplateDataToServer( List<SiteResource> fileInfoList,
        SiteGroupBean siteBean )
    {
        if( siteBean == null )
        {
            return;
        }

        String fp = null;

        SiteResource resInfo = null;

        String systemRoot = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        // 集群模式发送文件到节点
        if( JtRuntime.cmsServer.getClusterMode() )
        {

            for ( int i = 0; i < fileInfoList.size(); i++ )
            {
                resInfo = fileInfoList.get( i );

                fp = resInfo.getResSource();

                ClusterService.getInstance().broadcastFileToCluster(
                    StringUtil.replaceString( fp, systemRoot, "", false, false ), fp );
            }

        }

        SitePublishGatewayBean gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite(
            siteBean.getSiteId(), Constant.SITE_CHANNEL.TRAN_TYPE_TEMPLATE );

        if( gwBean == null )
        {
            return;
        }

        SiteDispenseServerBean server = siteGroupDao
            .querySingleSiteDispenseServerBeanByserverId( gwBean.getTargetServerId() );

        if( server == null )
        {
            return;
        }

        String fullPath = null;

        String newPath = null;

        FTPClient ftp = null;

        if( !Constant.SITE_CHANNEL.PROTOCOL_CURRENT_HOST.equals( server.getProtocol() ) )
        {
            ftp = FtpUtil.getFtpConnection( server.getServerIP(), Integer.parseInt( server
                .getServerPort() ), server.getLoginName(), server.getLoginPassword(), 30000 );
        }

        try
        {

            for ( int i = 0; i < fileInfoList.size(); i++ )
            {
                fullPath = ( ( SiteResource ) fileInfoList.get( i ) ).getResSource();

                if( fullPath.toLowerCase().lastIndexOf( ".jsp" ) != -1
                    || fullPath.toLowerCase().lastIndexOf( ".thtml" ) != -1
                    || fullPath.toLowerCase().lastIndexOf( ".jspx" ) != -1
                    || fullPath.toLowerCase().lastIndexOf( ".php" ) != -1
                    || fullPath.toLowerCase().lastIndexOf( ".asp" ) != -1
                    || fullPath.toLowerCase().lastIndexOf( ".aspx" ) != -1 )
                {
                    continue;
                }

                if( Constant.SITE_CHANNEL.PROTOCOL_CURRENT_HOST.equals( server.getProtocol() ) )
                {
                    String targetServerBasePath = "";

                    String context = JtRuntime.cmsServer.getContext();

                    File testDir = null;

                    String testTargetDir = null;

                    if( StringUtil.isStringNotNull( context ) )
                    {
                        targetServerBasePath = server.getFileRoot() + File.separator + context
                            + File.separator;

                    }
                    else
                    {
                        targetServerBasePath = server.getFileRoot() + File.separator;

                    }

                    // 复制目标文件夹结构

                    // 模板文件夹是否存在
                    testTargetDir = targetServerBasePath
                        + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                            + siteBean.getSiteRoot() + File.separator
                            + Constant.CONTENT.TEMPLATE_BASE ) + 1, fullPath
                            .lastIndexOf( File.separator ) );

                    testDir = new File( testTargetDir );

                    if( !testDir.exists() )
                    {
                        testDir.mkdirs();
                    }

                    newPath = targetServerBasePath
                        + StringUtil.subString( fullPath, fullPath.indexOf( File.separator
                            + siteBean.getSiteRoot() + File.separator
                            + Constant.CONTENT.TEMPLATE_BASE ) + 1, fullPath.length() );

                    FileUtil.copyFile( fullPath, newPath );

                    log.info( "[传输站点模板资源文件成功 ：]" + newPath );

                }
                else
                {
                    // FTP和SFTP

                    boolean connect = false;

                    if( ftp != null )
                    {
                        connect = ftp.isConnected();
                    }

                    if( connect )// 没有成功连接则无需进行传输操作
                    {
                        String prefix = "";

                        if( StringUtil.isStringNotNull( JtRuntime.cmsServer.getContext() ) )
                        {
                            prefix = File.separator + JtRuntime.cmsServer.getContext()
                                + File.separator;
                        }

                        newPath = prefix
                            + ( ( fullPath.indexOf( File.separator ) != -1 ) ? StringUtil
                                .subString( fullPath, fullPath.indexOf( siteBean.getSiteRoot()
                                    + File.separator + Constant.CONTENT.TEMPLATE_BASE ), fullPath
                                    .lastIndexOf( File.separator ) ) : "" );

                        FtpUtil.uploadFileFTP( ftp, newPath, null, new File( fullPath ) );

                        log.info( "[传输站点模板资源文件成功 ：]" + fullPath );

                    }
                }
            }
        }
        finally
        {
            FtpUtil.closeFtpConnection( ftp );
        }

    }

    /**
     * 将上传的文件分发到服务器
     * 
     * @param resList
     */
    public void transferSiteAllFileToClusterNode( Long csId )
    {
        String root = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String fullPath = root + site.getSiteRoot();

        String fullZipPath = root + "sys_temp" + File.separator + site.getSiteRoot()
            + DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD ) + ".zip";

        FileUtil.zip( fullZipPath, fullPath );

        Map cNode = clusterDao.querySingleClusterServerById( csId );

        String serClientServerIp = ( String ) cNode.get( "clusterUrl" );

        Integer isActive = ( Integer ) cNode.get( "isActive" );

        // 集群模式发送文件到节点
        if( JtRuntime.cmsServer.getClusterMode() && Constant.COMMON.ON.equals( isActive ) )
        {
            ClusterService.getInstance().sendFile(
                StringUtil.replaceString( fullZipPath, root, "", false, false ), fullZipPath,
                serClientServerIp );

            // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

            String uuidKey = StringUtil.getUUIDString();

            AuthorizationHandler.setInnerAccessFlag( uuidKey );

            String url = serClientServerIp
                + "cluster/unzipSiteFile.do?innerAccessJtopSysFlag="
                + uuidKey
                + "&zipRelatePath="
                + SystemSafeCharUtil.encode( SystemSafeCharUtil.encode( StringUtil.replaceString(
                    fullZipPath, root, "" ) ) ) + "&targetRelatePath=" + site.getSiteRoot();

            ServiceUtil.POSTMethodRequest( url );

        }

        FileUtil.delFile( fullZipPath );
    }

    /**
     * 切换当前站点编辑器资源路径,分为全路径,相对路径,云存储路径,服务器存储路径
     * v3版本开始,将不在支持全URL本站模式路径,只存在相对路径或云存储或服务器全路径
     * 
     */
    public void changeAllResUrlOrUri( String mode )
    {

        List<ModelFiledInfoBean> efieldList = metaDataDao.queryAllEditorModelFiledInfoBeanList();

        DataModelBean model = null;

        for ( ModelFiledInfoBean field : efieldList )
        {
            model = metaDataDao.querySingleDataModelBeanById( field.getDataModelId() );

            disposeAllEditorText( model, field, mode );
        }

        // 栏目缓存更新
        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        ChannelDao.clearAllCache();
        ChannelService.clearContentClassCache();

        // 内容缓存
        ContentDao.releaseAllCountCache();
        ContentService.releaseContentCache();

    }

    public void disposeAllEditorText( DataModelBean model, ModelFiledInfoBean field, String mode )
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( siteBean == null )
        {
            return;
        }

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        List<Map> restList = metaDataDao
            .queryEditorTextByField( model, field, prevCid, QUERY_COUNT );

        String text = null;

        while ( !restList.isEmpty() )
        {
            prevCid = ( Long ) ( ( Map ) restList.get( restList.size() - 1 ) ).get( "contentId" );

            for ( Map info : restList )
            {

                text = ( String ) info.get( field.getRelateFiledName() );

                String repText = replaceResUrl( siteBean, mode, text );

                if( repText != null && !repText.equals( text ) )
                {
                    metaDataDao.updateEditorTextById( model, field, repText, ( Long ) info
                        .get( "contentId" ) );
                }

            }

            restList = metaDataDao.queryEditorTextByField( model, field, prevCid, QUERY_COUNT );
        }

    }

    /**
     * 替换编辑器文本内容
     * 
     * @param site
     * @param mode 模式=urlToUri,全路径替换为相对,模式=uriToClo:相对转为云地址 模式=cloToUri:云地址转为相对
     * @param text
     */
    public String replaceResUrl( SiteGroupBean site, String mode, String text )
    {

        if( StringUtil.isStringNull( text ) )
        {
            return null;
        }

        String surl = site.getSiteUrl();

        String repText = text;

        if( "urlToUri".equals( mode ) )
        {
            repText = StringUtil.replaceString( repText, surl + site.getSiteRoot() + "/"
                + Constant.CONTENT.IMG_BASE, "/" + site.getSiteRoot() + "/"
                + Constant.CONTENT.IMG_BASE );
            repText = StringUtil.replaceString( repText, surl + site.getSiteRoot() + "/"
                + Constant.CONTENT.MEDIA_BASE, "/" + site.getSiteRoot() + "/"
                + Constant.CONTENT.MEDIA_BASE );
            repText = StringUtil.replaceString( repText, surl + site.getSiteRoot() + "/"
                + Constant.CONTENT.FILE_BASE, "/" + site.getSiteRoot() + "/"
                + Constant.CONTENT.FILE_BASE );
        }
        else if( "uriToClo".equals( mode ) )
        {

            SitePublishGatewayBean gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite(
                site.getSiteId(), Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE );

            SiteCloudCfgBean ccBean = null;

            if( gwBean != null )
            {
                ccBean = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

                if( ccBean != null )
                {
                    repText = StringUtil.replaceString( repText, "/" + site.getSiteRoot() + "/"
                        + Constant.CONTENT.IMG_BASE, ccBean.getAccessUrl() + site.getSiteRoot()
                        + "/" + Constant.CONTENT.IMG_BASE );
                }
            }

            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( site.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA );

            if( gwBean != null )
            {
                ccBean = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

                if( ccBean != null )
                {
                    repText = StringUtil.replaceString( repText, "/" + site.getSiteRoot() + "/"
                        + Constant.CONTENT.MEDIA_BASE, ccBean.getAccessUrl() + site.getSiteRoot()
                        + "/" + Constant.CONTENT.MEDIA_BASE );

                }
            }

            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( site.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_FILE );

            if( gwBean != null )
            {
                ccBean = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

                if( ccBean != null )
                {
                    repText = StringUtil.replaceString( repText, "/" + site.getSiteRoot() + "/"
                        + Constant.CONTENT.FILE_BASE, ccBean.getAccessUrl() + site.getSiteRoot()
                        + "/" + Constant.CONTENT.FILE_BASE );
                }
            }

        }
        else if( "cloToUri".equals( mode ) )
        {
            SitePublishGatewayBean gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite(
                site.getSiteId(), Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE );

            SiteCloudCfgBean ccBean = null;

            if( gwBean != null )
            {
                ccBean = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

                if( ccBean != null )
                {
                    repText = StringUtil.replaceString( repText, ccBean.getAccessUrl()
                        + site.getSiteRoot() + "/" + Constant.CONTENT.IMG_BASE, "/"
                        + site.getSiteRoot() + "/" + Constant.CONTENT.IMG_BASE );
                }
            }

            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( site.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA );

            if( gwBean != null )
            {
                ccBean = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

                if( ccBean != null )
                {
                    repText = StringUtil.replaceString( repText, ccBean.getAccessUrl()
                        + site.getSiteRoot() + "/" + Constant.CONTENT.MEDIA_BASE, "/"
                        + site.getSiteRoot() + "/" + Constant.CONTENT.MEDIA_BASE );

                }
            }

            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( site.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_FILE );

            if( gwBean != null )
            {
                ccBean = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

                if( ccBean != null )
                {
                    repText = StringUtil.replaceString( repText, ccBean.getAccessUrl()
                        + site.getSiteRoot() + "/" + Constant.CONTENT.FILE_BASE, "/"
                        + site.getSiteRoot() + "/" + Constant.CONTENT.FILE_BASE );
                }
            }
        }

        return repText;
    }

    /**
     * 从其他站点同步配置
     * 
     * @param mode
     * @param siteId
     */
    public void copySiteConfig( String mode, Long siteId )
    {
        SiteGroupBean currSite = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( "workflow".equals( mode ) )// 工作流
        {
            List<Workflow> wl = workFlowDao.queryAllWorkflowBySiteId( siteId );

            for ( Workflow wf : wl )
            {
                wf.setSiteId( currSite.getSiteId() );

                wf.setSystemHandleTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

                wf.setUpdateDT( DateAndTimeUtil.clusterTimeMillis() );

                wf.setStep( Integer.valueOf( 1 ) );

                UpdateState us = workFlowDao.saveWorkflowMainInfo( wf );

                Long newId = us.getKey();

                if( newId > 0 )
                {

                    List<WorkflowStepInfo> stl = workFlowDao.queryWorkflowStepListByFlowId( wf
                        .getFlowId() );

                    for ( WorkflowStepInfo st : stl )
                    {
                        st.setFlowId( newId );

                        workFlowDao.saveWorkflowStepInfo( st );
                    }

                    List<WorkflowActor> fal = workFlowDao.queryWorkflowActorList( wf.getFlowId() );

                    for ( WorkflowActor fa : fal )
                    {
                        fa.setFlowId( newId );

                        workFlowDao.saveWorkflowActor( fa );
                    }

                    List<WorkflowStepAction> wal = workFlowDao.queryWorkflowActionByFlowId( wf
                        .getFlowId() );

                    for ( WorkflowStepAction al : wal )
                    {
                        al.setFlowId( newId );

                        workFlowDao.saveWorkflowAction( al );
                    }

                    // 更新工作流步骤记录
                    WorkflowService.getInstance().updateWorkflowStepCount( newId );

                    // 更新工作流时间戳
                    WorkflowService.getInstance().setWorkflowUpdateDTInfo( newId );

                }

            }

        }
        else if( "server".equals( mode ) )// 分发文件服务器
        {
            List<SiteDispenseServer> ftpServerList = siteGroupDao
                .queryDispenseServerBySiteId( siteId );

            for ( SiteDispenseServer serverInfo : ftpServerList )
            {

                serverInfo.setSiteId( currSite.getSiteId() );

                addServerConfig( serverInfo );

            }

        }
        else if( "pub".equals( mode ) )// 发布点
        {
            List<SitePublishGateway> pgList = siteGroupDao
                .querysSitePublishGatewayBySiteId( siteId );

            for ( SitePublishGateway gateway : pgList )
            {

                addSitePublishGateway( gateway, currSite, null, null );
            }

        }
        else if( "cloud".equals( mode ) )// 云存储
        {
            List<SiteCloudCfgBean> clList = siteGroupDao.querysCloudCfgBeanBySiteId( siteId );

            for ( SiteCloudCfgBean cl : clList )
            {

                cl.setSiteId( currSite.getSiteId() );

                addNewCloudConfig( cl );

            }

        }
        else if( "infosource".equals( mode ) )// 信息来源
        {
            List<SiteCloudCfgBean> clList = siteGroupDao.querysCloudCfgBeanBySiteId( siteId );

            for ( SiteCloudCfgBean cl : clList )
            {

                cl.setSiteId( currSite.getSiteId() );

                addNewCloudConfig( cl );

            }

        }
    }

    public void deleteCMSSiteAllInfo()
    {
        List deleteSiteIdList = siteGroupDao.queryAllDeleteSiteGroupTrace();

        Map sidInfo = null;

        Long siteId = null;

        String siteFlag = null;

        List classList = null;

        ContentClassBean classBean = null;

        SiteGroupBean site = null;

        DataModelBean modelBean = null;

        List allSiteModelList = null;

        for ( int i = 0; i < deleteSiteIdList.size(); i++ )
        {
            sidInfo = ( Map ) deleteSiteIdList.get( i );

            siteId = ( Long ) sidInfo.get( "siteId" );

            siteFlag = ( String ) sidInfo.get( "siteFlag" );

            siteGroupDao.deleteDeleteSiteGroupTrace( siteId );

            // 所有栏目
            classList = channelService.retrieveAllClassBeanInfoBySiteFlag( siteFlag );

            allSiteModelList = metaDataDao.queryAllDataModelBeanListByModelTypeAndSiteId(
                Constant.METADATA.MODEL_TYPE_CONTENT, siteId );

            site = new SiteGroupBean();

            site.setSiteId( siteId );
            site.setSiteFlag( siteFlag );

            for ( int j = 0; j < classList.size(); j++ )
            {
                classBean = ( ContentClassBean ) classList.get( j );

                /**
                 * 删除所有内容
                 */

                // 每一种可能的内容模型
                for ( int mb = 0; mb < allSiteModelList.size(); mb++ )
                {
                    modelBean = ( DataModelBean ) allSiteModelList.get( mb );

                    contentService.deleteAllSystemAndUserDefineContentToTrash( site, modelBean
                        .getDataModelId(), classBean.getClassId(), new ArrayList() );

                    contentService.deleteAllSystemAndUserDefineContent( modelBean.getDataModelId(),
                        classBean.getClassId() );
                }

                /**
                 * 删除所有栏目
                 */
                channelService.deleteContentClassAllInfomationNotDisposeTreeInfo( classBean );

                /**
                 * 删除分页辅助页
                 */
                channelDao.deleteClassPublishPageAssistant( classBean.getClassId() );

            }

            contentDao.deleteContentMainInfoBySiteId( siteId );

            /**
             * 删除所有专题栏目
             */
            classList = channelService.retrieveAllSpecClassBeanInfoBySiteFlag( siteFlag );

            for ( int j = 0; j < classList.size(); j++ )
            {
                classBean = ( ContentClassBean ) classList.get( j );

                channelService.deleteContentClassAllInfomationNotDisposeTreeInfo( classBean );
            }

            /**
             * 删除所有推荐位
             */

            List commTypeList = channelDao.queryContentCommendTypeBeanBySiteFlag( siteFlag );

            ContentCommendTypeBean ctBean = null;

            for ( int ct = 0; ct < commTypeList.size(); ct++ )
            {
                ctBean = ( ContentCommendTypeBean ) commTypeList.get( ct );

                contentService.deleteCommendContentColumnInfo( ctBean.getCommFlag(), null, site
                    .getSiteFlag() );

                channelDao.deleteCommendType( ctBean.getCommendTypeId() );
            }

            /**
             * 删除搜索表单数据
             */

            List formModelList = metaDataDao.queryAllDataModelBeanListByModelTypeAndSiteId(
                Constant.METADATA.MODEL_TYPE_DEF_FORM, siteId );

            DataModelBean dfm = null;

            for ( int mo = 0; mo < formModelList.size(); mo++ )
            {
                dfm = ( DataModelBean ) formModelList.get( mo );

                contentService.deleteAllDefFormContent( site, dfm.getDataModelId() );
            }

            /**
             * 删除所有留言
             */
            gbService.deleteGuestbookConfigAllInfoByIds( gbDao
                .queryAllGuestbookConfigIdList( siteId ), site );

            /**
             * 删除所有评论
             */
            commentDao.deleteCommentAllInfoBySiteId( siteId );

            /**
             * 删除所有投票
             */

            List sgIdList = surveyDao.querySurveyGroupIdListBySiteId( siteId );

            surveyService.deleteSurveyGroupInfo( sgIdList );

            /**
             * 删除所有广告
             */

            List advertPosIdList = advertDao.queryAllAdvertPosIdList( siteId );

            Long posId = null;

            for ( int ap = 0; ap < advertPosIdList.size(); ap++ )
            {

                posId = ( Long ) advertPosIdList.get( ap );

                List adList = advertService.retrieveAdvertContentBeanListByPosId( posId );

                AdvertContentBean bean = null;

                StringBuffer buf = new StringBuffer();

                for ( int ad = 0; ad < adList.size(); ad++ )
                {
                    bean = ( AdvertContentBean ) adList.get( ad );

                    buf.append( bean.getAdvertId() + "," );
                }

                // 先删除所属广告
                advertService.deleteAdvertContent( StringUtil.changeStringToList( buf.toString(),
                    "," ), site );

                advertService.deleteAdvertPositionAndParamValue( posId, site );
            }

            /**
             * 删除所有外链
             */
            List slLtIdList = inDao.queryFriendSiteLinkTypeIdList( siteId );

            inService.deleteFriendSiteType( slLtIdList );

            /**
             * 删除所有公告
             */

            inDao.deleteSiteAnnounceBySiteId( siteId );

            /**
             * 删除区块
             */
            List btIdList = blockDao.queryAllBlockTypeIdList( siteFlag );

            blockService.deleteBlockTypeById( btIdList );

            /**
             * 删除Tag
             */
            channelDao.deleteTagRelateContentBySiteId( siteId );

            channelDao.deleteTagWordInfoBySiteId( siteId );

            channelDao.deleteTagTypeInfoBySiteId( siteId );

            /**
             * 删除模板版本记录
             */
            templetDao.deleteTemplateEditionInfoBySiteId( siteId );

            /**
             * 删除模板辅助记录
             */
            templetDao.deleteTemplateHelperBySiteId( siteId );

            /**
             * 删除发布规则
             */
            // 2015:不再删除
            // publishDao.deletePublishRuleBySiteId( siteId );
            /**
             * 删除分发服务器
             */
            siteGroupDao.deleteSiteDispenseServerBySiteId( siteId );

            /**
             * 删除云存储
             */
            siteGroupDao.deleteCloudCfgBeanBySiteId( siteId );

            /**
             * 删除发布点
             */
            siteGroupDao.deleteSitePublishGatewayBySiteId( siteId );

            /**
             * 删除采集
             */
            List prList = pickDao.queryPickTaskIdBySiteId( siteId );

            pickService.deletePickContentTask( prList );

            pickDao.deletePickRuleBySiteId( siteId );

            pickService.deleteAllPickWebTrace( siteId );

            /**
             * 删除会员以及相关信息
             */
            Long memberCount = memberService.retrieveMemeberCount( siteId );

            List memberList = memberService.retrieveMemeberList( siteId, Long.valueOf( 0 ), Integer
                .valueOf( memberCount.intValue() ) );

            Map memberInfo = null;

            Long memberId = null;

            for ( int mem = 0; mem < memberList.size(); mem++ )
            {
                memberInfo = ( Map ) memberList.get( mem );

                memberId = ( Long ) memberInfo.get( "memberId" );

                if( !memberInfo.isEmpty() )
                {
                    // 删除会员信息
                    memberDao.deleteMemberById( memberId );

                    // 删除会员组关联
                    securityDao.deleteMemberRoleRealte( memberId );

                    // 删除第三方登录信息
                    memberDao.deleteMemberThirdRegInfo( memberId );

                    // 删除站内信
                    memberDao.deleteMessage( memberId );
                }
            }

            // 删除会员等级
            memberDao.deleteMemberRankBySiteId( siteId );

            // 删除会员积分信息
            memberDao.deleteMemberScoreActBySiteId( siteId );

            // 删除会员规则权限
            memberDao.deleteMemberAccRuleBySiteId( siteId );

            // 删除会员组及相关信息
            memberDao.deleteMemberRoleRelateSecBySiteId( siteId );

            memberDao.deleteMemberRoleBySiteId( siteId );

            // 删除会员权限资源
            memberDao.deleteMemberSecBySiteId( siteId );

            // 删除会员信息模板参数
            memberDao.deleteMessageTemplateParamBySiteId( siteId );

            // 删除会员信息参数
            memberDao.deleteMessageTemplateBySiteId( siteId );

            // 删除会员登录记录
            memberDao.deleteMemberLoginTraceBySiteId( siteId );

            // 删除会员栏目细粒度权限
            memberDao.deleteMemberClassSubmitAccBySiteId( siteId );

            // 删除会员栏目粗粒度权限
            memberDao.deleteMemberClassAccBySiteId( siteId );

            // 删除会员栏目细粒度权限信息
            memberDao.deleteMemberAccClassRelateRoleBySiteId( siteId );

            /**
             * 删除所有工作流信息
             */
            List wkList = workFlowDao.queryAllWorkflowFlowIdBySiteId( siteId );

            Long wkId = null;

            for ( int wk = 0; wk < wkList.size(); wk++ )
            {
                wkId = ( Long ) wkList.get( wk );

                workFlowDao.deleteWorkflowOperationByFlowId( wkId );
                workFlowDao.deleteWorkflowStepInfoByFlowId( wkId );
                workFlowDao.deleteWorkflowActorByFlowId( wkId );
                workFlowDao.deleteWorkflowActionByFlowId( wkId );
                workFlowDao.deleteWorkflowByFlowId( wkId );
            }

            /**
             * 删除微信信息
             */
            WxAccount wac = wxDao.querySingleWxConfigBySiteId( siteId );

            if( wac != null )
            {
                wxDao.deleteWXAllbyWxCode( wac.getMainId() );
            }

            /**
             * 删除模型信息
             */
            // 2015:只可删除站点私有模型
            List moList = metaDataDao.queryAllDataModelIdListBySiteIdPrivateMode( siteId,
                Constant.COMMON.ON );

            Long moId = null;

            for ( int mo = 0; mo < moList.size(); mo++ )
            {
                moId = ( Long ) moList.get( mo );

                metaDataService.deleteDataModelAllInfo( moId, siteId );

            }

            /**
             * 删除站点权限
             */
            securityDao.deleteRangeOrgRelateResAccBySiteId( siteId );

            securityDao.deleteRangeOrgRelateSiteAccBySiteId( siteId );

            securityDao.deleteOrgRelateResAccBySiteId( siteId );

            /**
             * 访问分析
             */
            statDao.deleteVisitorInfoAnalysisBySiteId( siteId );

            /**
             * 删除所有文件夹
             */
            String baseFileRoot = SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath();

            if( StringUtil.isStringNotNull( siteFlag ) && !"core".equalsIgnoreCase( siteFlag )
                && !"common".equalsIgnoreCase( siteFlag ) && !"WEB-INF".equalsIgnoreCase( siteFlag )
                && !"sys_temp".equalsIgnoreCase( siteFlag ) )
            {
                FileUtil.delFolder( baseFileRoot + File.separator + siteFlag );

                File rf = new File( baseFileRoot + File.separator + siteFlag );

                rf.delete();

                ClusterService.exeClusterMasterCMD( "cluster/deleteFolder.do", "fp=" + siteFlag,
                    Constant.COMMON.POST );
            }
        }
    }

    public void setSiteResServerUrl( SiteGroupBean site )
    {
        SitePublishGatewayBean gwBean = null;

        SiteDispenseServerBean server = null;

        String context = JtRuntime.cmsServer.getContext();

        if( StringUtil.isStringNotNull( context ) )
        {
            context = context + Constant.CONTENT.URL_SEP;
        }

        gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( site.getSiteId(),
            Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE );

        if( gwBean != null )
        {
            server = siteGroupDao.querySingleSiteDispenseServerBeanByserverId( gwBean
                .getTargetServerId() );

            if( server != null && StringUtil.isStringNotNull( server.getServerUrl() ) )
            {
                site.setImageServerUrl( server.getServerUrl() + context );
            }
        }

        gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( site.getSiteId(),
            Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA );

        if( gwBean != null )
        {
            server = siteGroupDao.querySingleSiteDispenseServerBeanByserverId( gwBean
                .getTargetServerId() );

            if( server != null && StringUtil.isStringNotNull( server.getServerUrl() ) )
            {
                site.setMediaServerUrl( server.getServerUrl() + context );
            }
        }

        gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( site.getSiteId(),
            Constant.SITE_CHANNEL.TRAN_TYPE_FILE );

        if( gwBean != null )
        {
            server = siteGroupDao.querySingleSiteDispenseServerBeanByserverId( gwBean
                .getTargetServerId() );

            if( server != null && StringUtil.isStringNotNull( server.getServerUrl() ) )
            {
                site.setFileServerUrl( server.getServerUrl() + context );
            }
        }
    }

    /**
     * 增加待发邮件发送信息
     * 
     * @param siteId
     * @param sendTo
     * @param subject
     * @param mailContent
     * @param createDT
     * @return
     */
    public UpdateState addSiteEmailSendInfo( Long siteId, String sendTo, String subject,
        String mailContent, Timestamp createDT )
    {
        return siteGroupDao.saveSiteEmailSendInfo( siteId, sendTo, subject, mailContent, createDT );
    }

    public Map retrieveSingleSiteEmailSendInfo()
    {
        return siteGroupDao.querySingleSiteEmailSendInfo();
    }

    public void deleteSiteEmailSendInfo( String mailId )
    {
        siteGroupDao.deleteSiteEmailSendInfo( mailId );
    }

    public Map retrieveSingleModelTemplate( Long siteId, Long dataModelId )
    {
        return siteGroupDao.querySingleModelTemplate( siteId, dataModelId );
    }

    public static SiteGroupBean getCurrentSiteInfoFromWebRequest( HttpServletRequest request )
    {
        SiteGroupBean site = ( SiteGroupBean ) request
            .getAttribute( "_____sys__cms__cmd__flow__current__site_____" );

        if( site != null )
        {
            return site;
        }

        String reqUrl = request.getRequestURL().toString();

        String reqUri = request.getRequestURI();

        String siteUrl = StringUtil.replaceString( reqUrl, reqUri, JtRuntime.cmsServer
            .getContextUri(), false, false );

        site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupDomainInfoCache
            .getEntry( siteUrl );

        if( site == null )
        {
            String reqServletPath = request.getServletPath();

            int endPos = reqServletPath.indexOf( "/", 1 );

            if( endPos > 0 )
            {
                String su = siteUrl + StringUtil.subString( reqServletPath, 1, endPos ) + "/";

                // 可能为内网站群
                site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupDomainInfoCache
                    .getEntry( su );
            }
        }

        return site;
    }

    public List getAllSiteNotSelfForTag()
    {
        List siteList = retrieveAllSiteBean();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        SiteGroupBean siteObj = null;

        List newSiteList = new ArrayList();

        for ( int i = 0; i < siteList.size(); i++ )
        {
            siteObj = ( SiteGroupBean ) siteList.get( i );

            if( !siteObj.getSiteId().equals( site.getSiteId() ) )
            {
                newSiteList.add( siteObj );
            }
        }

        return newSiteList;
    }

}

package cn.com.mjsoft.cms.channel.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentClassNodeInfoBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.bean.ContentTypeBean;
import cn.com.mjsoft.cms.channel.bean.MoveNodeInfoBean;
import cn.com.mjsoft.cms.channel.bean.TagWordBean;
import cn.com.mjsoft.cms.channel.controller.ListCommendTypeInfoTreeController;
import cn.com.mjsoft.cms.channel.controller.ListContentClassInfoTreeController;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
import cn.com.mjsoft.cms.channel.dao.vo.ContentClass;
import cn.com.mjsoft.cms.channel.dao.vo.ContentCommendType;
import cn.com.mjsoft.cms.channel.dao.vo.ContentType;
import cn.com.mjsoft.cms.channel.dao.vo.TagWord;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.dao.ValiDao;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.bean.ContentCommendPushInfoBean;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.dao.vo.PhotoGroupInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.dao.MetaDataDao;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.dao.SiteGroupDao;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

@SuppressWarnings( "unchecked" )
public class ChannelService
{
    private static Logger log = Logger.getLogger( ChannelService.class );

    private static String video = "<p style='text-align: center'><embed id='jtopcms_content_media_@{resId}' height='@{height}' width='@{width}' allowfullscreen='@{fs}' type='application/x-shockwave-flash' wmode='transparent' src='/core/extools/player/jwplayer/5.9/player.swf?screencolor=black&file=@{fileFullPath}&autostart=@{autostart}&allowfullscreen=@{fs}&repeat=@{repeat}'></embed><br/><br/></p>";

    private static String image = "<p style='text-align: center'><img id='jtopcms_content_image_@{resId}' name='jtopcms_content_image' src='@{fileFullPath}' alt='@{name}' height='@{height}' width='@{width}'/>&nbsp;<br><br>@{name}</p><br><br/><br>";

    private static String file = "<p><a id='jtopcms_content_file_@{resId}' href='/content/clientDf.do?id=@{resId}' >@{name}</a><br/></p>";

    private static String videoDesc = "支持显示视频以及视频引导图";

    private static String imageDesc = "支持可编辑以及裁剪处理的图片";

    private static String fileDesc = "使用隐藏地址以及直接地址的附件下载";

    public static Map cacheManager = new HashMap();

    static
    {
        cacheManager.put( "conetentClassByParentClassName", new ClusterCacheAdapter( 3500,
            "channelService.conetentClassByParentClassName" ) );
        cacheManager.put( "singleConetentClassByClassName", new ClusterCacheAdapter( 60,
            "channelService.singleConetentClassByClassName" ) );
        cacheManager.put( "retrieveSingleClassBeanInfoByClassId", new ClusterCacheAdapter( 1500,
            "channelService.retrieveSingleClassBeanInfoByClassId" ) );
        cacheManager.put( "retrieveSingleClassBeanInfoByClassFlag", new ClusterCacheAdapter( 4500,
            "channelService.retrieveSingleClassBeanInfoByClassFlag" ) );

        cacheManager.put( "retrieveClassPublishPageAssistant", new ClusterCacheAdapter( 5,
            "channelService.retrieveClassPublishPageAssistant" ) );

        cacheManager.put( "retrieveAllContentTypeBean", new ClusterCacheAdapter( 2,
            "channelService.retrieveAllContentTypeBean" ) );

        cacheManager.put( "retrieveSiteNotUseSearchFunClassId", new ClusterCacheAdapter( 50,
            "channelService.retrieveSiteNotUseSearchFunClassId" ) );

        cacheManager.put( "retrieveSiteNotUseRelateFunClassId", new ClusterCacheAdapter( 50,
            "channelService.retrieveSiteNotUseRelateFunClassId" ) );

        cacheManager.put( "retrieveAllContentCommendTypeBean", new ClusterCacheAdapter( 500,
            "channelService.retrieveAllContentCommendTypeBean" ) );

        // 推荐类型缓存,因管理模式下不需要缓存,故放在service层,只有client调用使用缓存
        cacheManager.put( "retrieveSingleContentCommendTypeBeanByTypeId", new ClusterCacheAdapter(
            3000, "channelService.retrieveSingleContentCommendTypeBeanByTypeId" ) );

    }

    private static ChannelService service = null;

    private static final String BLANK = "";

    private static final String CHANNEL_IMAGE_PATH = "UPLOAD" + File.separator + "CHANNEL"
        + File.separator;

    public static final String CLASS_ID_FLAG = "system.service.channelservice.id.flag";

    public static final String CLASS_NAME_FLAG = "system.service.channelservice.id.flag";

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private ResourcesService resService = ResourcesService.getInstance();

    private PublishService publishService = PublishService.getInstance();

    private MetaDataService metaDataService = MetaDataService.getInstance();

    private ChannelDao channelDao = null;

    private ContentDao contentDao = null;

    private MetaDataDao metaDataDao = null;

    private SiteGroupDao siteGroupDao = null;

    private ValiDao valiDao = null;

    private ChannelService()
    {
        channelDao = new ChannelDao( mysqlEngine );
        contentDao = new ContentDao( mysqlEngine );
        metaDataDao = new MetaDataDao( mysqlEngine );
        siteGroupDao = new SiteGroupDao( mysqlEngine );
        valiDao = new ValiDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new ChannelService();
        }
    }

    public static ChannelService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public ContentClassBean retrieveSingleClassBeanInfoByClassName( String className )
    {

        Cache cache = ( Cache ) cacheManager.get( "singleConetentClassByClassName" );

        ContentClassBean bean = ( ContentClassBean ) cache.getEntry( className );

        if( bean == null )
        {
            bean = channelDao.querySingleClassBeanInfoByclassName( className );
            cache.putEntry( className, bean );
        }

        return bean;
    }

    public ContentClassBean retrieveSingleClassBeanInfoByClassId( Long classId )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleClassBeanInfoByClassId" );

        String key = "retrieveSingleClassBeanInfoByClassId:" + classId;

        ContentClassBean bean = ( ContentClassBean ) cache.getEntry( key );

        if( bean == null )
        {
            bean = channelDao.querySingleClassBeanInfoByClassId( classId );

            if( bean == null )
            {
                bean = new ContentClassBean();
            }

            cache.putEntry( key, bean );
        }

        return bean;
    }

    public List retrieveConetentClassByClassNameSearchKey( String keyVal, Long parentId )
    {
        return channelDao.queryConetentClassByClassNameSearchKey( keyVal, parentId );
    }

    public ContentClassBean retrieveSingleClassBeanInfoByClassFlag( String classFlag )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleClassBeanInfoByClassFlag" );

        String key = "retrieveSingleClassBeanInfoByClassFlag:" + classFlag;

        ContentClassBean bean = ( ContentClassBean ) cache.getEntry( key );

        if( bean == null )
        {
            bean = channelDao.querySingleClassBeanInfoByClassFlag( classFlag );

            if( bean == null )
            {
                bean = new ContentClassBean();
            }

            cache.putEntry( key, bean );
        }

        return bean;
    }

    public boolean checkParentIsSpecial( Long parentId )
    {
        if( parentId.longValue() == -9999 )
        {
            return false;
        }

        ContentClass parentClass = ( ContentClass ) channelDao
            .getContentClassVoForClassID( parentId );

        if( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( parentClass.getClassType() )
            || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT.equals( parentClass.getClassType() )
            || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUB_NODE
                .equals( parentClass.getClassType() ) )
        {
            return true;
        }

        return false;
    }

    public boolean checkIsSpecialClassHaveChildClass( Long parentClassId, Integer classType )
    {
        Integer count = channelDao.queryChildCountByClassIdAndClassType( parentClassId, classType );

        if( count == null || count.intValue() == 0 )
        {
            return false;
        }

        return true;
    }

    public boolean haveSameFlag( String flag )
    {
        Integer count = valiDao.querySystemTableFlagExist( "contentclass", "classFlag", flag );

        if( count.intValue() > 0 )
        {
            return true;
        }

        return false;
    }

    public void addContentClass( ContentClass contentClass, SiteGroupBean siteBean, boolean isSpec )
        throws Exception
    {

        try
        {

            mysqlEngine.beginTransaction();

            ContentClass parentClass = ( ContentClass ) channelDao
                .getContentClassVoForClassID( contentClass.getParent() );

            Long parentClassId = contentClass.getParent();

            ContentClassBean parentBean = channelDao
                .querySingleClassBeanInfoByClassId( parentClassId );

            // 是否根栏目
            if( parentClassId.longValue() == -9999 )
            {
                contentClass.setLayer( Integer.valueOf( 1 ) );
            }
            else
            {
                contentClass.setLayer( Integer.valueOf( parentClass.getLayer().intValue() + 1 ) );
            }

            // 更新栏目节点NODE信息
            String currentLinearFlag = makeLayerNextLinearFlag( contentClass.getLayer(),
                contentClass.getParent() );

            contentClass.setLinearOrderFlag( currentLinearFlag );
            contentClass.setIsLeaf( Constant.SITE_CHANNEL.TRUE_FLAG );
            contentClass.setIsLastChild( Constant.SITE_CHANNEL.TRUE_FLAG );

            UpdateState updateState = channelDao.saveContentClass( contentClass );

            if( updateState == null || !updateState.haveKey() )
            {
                throw new FrameworkException( "新的栏目保存失败" );
            }

            contentClass.setClassId( Long.valueOf( updateState.getKey() ) );

            // 改动path
            if( parentClassId.longValue() == -9999 )
            {
                channelDao.updateContentClassPath( Long.valueOf( updateState.getKey() ), Long
                    .valueOf( updateState.getKey() ).toString() );
            }
            else
            {
                channelDao.updateContentClassPath( Long.valueOf( updateState.getKey() ), parentBean
                    .getChannelPath()
                    + "," + Long.valueOf( updateState.getKey() ).toString() );
            }

            // 增加layer cache
            addNewClassNodeLayerCache( parentClassId, Long.valueOf( updateState.getKey() ), false );

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();
            // TODO 清除栏目树 ,实现需要改进
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }
    }

    public List fetchAllConetentClassBean( Long contentId )
    {
        List allClass;

        if( contentId != null && contentId.longValue() > 0 )
        {
            allClass = channelDao.getAllContentClassBeanOrderByLinear( contentId, "" );
        }
        else
        {
            allClass = channelDao.getAllContentClassBeanOrderByLinear( "" );
        }

        return allClass;
    }

    public List fetchConetentClassByParentClassID( long classId, boolean useBean, Integer showFlag,
        String siteFlag, boolean isSpec )
    {
        List childrenClass = null;

        if( useBean )
        {
            if( Constant.SITE_CHANNEL.CHANNEL_ALL_SHOW.equals( showFlag ) )
            {
                childrenClass = channelDao.queryChildrenContentClassBeanForParentID( classId,
                    siteFlag, isSpec ? Constant.COMMON.ON : Constant.COMMON.OFF );
            }
            else
            {
                childrenClass = channelDao.getChildrenContentClassBeanForParentIDAndShowStatus(
                    classId, showFlag, siteFlag, isSpec ? Constant.COMMON.ON : Constant.COMMON.OFF );
            }
        }
        else
        {
            childrenClass = channelDao.getChildrenContentClassVoForParentID( classId, showFlag );
        }

        return childrenClass;
    }

    public Long retrieveConetentClassCountByParentClassId( Long classId, String siteFlag,
        Long classType )
    {
        return channelDao.queryChildrenContentClassCountForParentId( classId, siteFlag, classType );
    }

    public List retrieveConetentClassByParentClassId( Long classId, String siteFlag,
        Long classType, Long pos, Integer size )
    {
        List childrenClass = null;

        childrenClass = channelDao.queryChildrenContentClassBeanForParentId( classId, siteFlag,
            classType, pos, size );

        return childrenClass;
    }

    public List retrieveConetentClassByParentClassId( Long classId, String siteFlag, Long classType )
    {
        List childrenClass = null;

        childrenClass = channelDao.queryChildrenContentClassBeanForParentId( classId, siteFlag,
            classType );

        return childrenClass;
    }

    public String retrieveConetentClassIdNotSpecByParentClassId( Long classId, String siteFlag )
    {
        String childrenClass = null;

        childrenClass = channelDao
            .queryChildrenContentClassIdNotSpecForParentId( classId, siteFlag );

        return childrenClass;
    }

    public List retrieveConetentClassBeanNotSpecByParentClassId( Long classId, Long modelId,
        String siteFlag, String order )
    {

        Cache cache = ( Cache ) cacheManager.get( "conetentClassByParentClassName" );

        String key = "conetentClassByParentClass:" + classId + "|" + modelId + "|" + siteFlag + "|"
            + order;

        String orderVar = "asc";

        if( "down".equals( order ) )
        {
            orderVar = "desc";
        }

        List childrenClass = ( List ) cache.getEntry( key );

        if( childrenClass == null )
        {

            if( modelId == null || modelId.longValue() < 1 )
            {
                childrenClass = channelDao.queryChildrenContentClassBeanNotSpecForParentId(
                    classId, siteFlag, orderVar );
            }
            else
            {
                childrenClass = channelDao.queryChildrenContentClassBeanNotSpecForParentId(
                    classId, modelId, siteFlag, orderVar );
            }

            cache.putEntry( key, childrenClass );
        }

        return childrenClass;

    }

    public List retrieveAllChildConetentClassBeanNotSpecByParentLinear( String linear,
        String siteFlag, String order )
    {

        Cache cache = ( Cache ) cacheManager.get( "conetentClassByParentClassName" );

        String key = "retrieveAllChildConetentClassBeanNotSpecByParentLinear:" + linear + "|"
            + siteFlag + "|" + order;

        String orderVar = "asc";

        if( "down".equals( order ) )
        {
            orderVar = "desc";
        }

        List childrenClass = ( List ) cache.getEntry( key );

        if( childrenClass == null )
        {
            if( "root".equals( linear ) )
            {
                childrenClass = channelDao.queryAllContentClassBeanNotSpecForSite( siteFlag,
                    orderVar );
            }
            else
            {
                childrenClass = channelDao.queryAllChildrenContentClassBeanNotSpecForParentLinear(
                    linear, orderVar );
            }

            cache.putEntry( key, childrenClass );
        }

        return childrenClass;

    }

    public List retrieveConetentClassBeanSpecModeByParentClassId( Long classId, Long modelId,
        String specComm, String siteFlag, String order )
    {

        Cache cache = ( Cache ) cacheManager.get( "conetentClassByParentClassName" );

        String key = "conetentClassByParentClass:" + "spec|" + classId + "|" + modelId + "|"
            + siteFlag + "|" + specComm + "|" + order;

        String orderVar = "asc";

        if( "down".equals( order ) )
        {
            orderVar = "desc";
        }

        List childrenClass = ( List ) cache.getEntry( key );

        if( childrenClass == null )
        {

            if( modelId == null || modelId.longValue() < 1 )
            {
                if( "true".equals( specComm ) )
                {
                    childrenClass = channelDao
                        .queryChildrenContentClassBeanSpecCommModeForParentId( classId, siteFlag,
                            orderVar );
                }
                else
                {
                    childrenClass = channelDao.queryChildrenContentClassBeanSpecModeForParentId(
                        classId, siteFlag, orderVar );
                }
            }
            else
            {
                if( "true".equals( specComm ) )
                {
                    childrenClass = channelDao
                        .queryChildrenContentClassBeanSpecCommModeForParentId( classId, modelId,
                            siteFlag, orderVar );
                }
                else
                {
                    childrenClass = channelDao.queryChildrenContentClassBeanSpecModeForParentId(
                        classId, modelId, siteFlag, orderVar );
                }
            }

            cache.putEntry( key, childrenClass );
        }

        return childrenClass;

    }

    /**
     * 根据传入的ID获取对应的栏目信息,排列顺序由orderflag控制
     * 
     * @param idList
     * @return
     */
    public List retrieveClassBeanInfoBySomeIds( List idList, String orderFlag )
    {
        log.info( "retrieveClassBeanInfoBySomeIds()  idList:" + idList + " ,orderFlag" + orderFlag );

        List result = null;

        result = channelDao.queryClassBeanBySomeIds( idList, orderFlag );

        return result;
    }

    /**
     * 返回指定内容静态化状态的栏目
     * 
     * @param idList
     * @return
     */
    public List retrieveClassBeanInfoByContentPublishState( String siteFlag,
        Integer contentPublishState )
    {

        List result = null;

        result = channelDao.queryClassBeanInfoByContentPublishState( siteFlag, contentPublishState );

        return result;
    }

    /**
     * 根据传入的flag获取对应的栏目信息,排列顺序由orderflag控制
     * 
     * @param idList
     * @return
     */
    public List retrieveClassBeanInfoBySomeFlags( String[] flagArray, String orderFlag )
    {
        log.info( "retrieveClassBeanInfoBySomeFlags()  flagArray:" + flagArray + " ,orderFlag"
            + orderFlag );

        if( flagArray == null )
        {
            return Collections.EMPTY_LIST;
        }

        List result = null;

        result = channelDao.queryClassBeanBySomeFlags( flagArray, orderFlag );

        return result;
    }

    /**
     * 根据提供父节点的名字取其孩子栏目.
     * 
     * @param name
     * @param showFlag
     * @return
     */
    public List retrieveConetentClassByParentClassName( String name, Integer showFlag )
    {

        Cache cache = ( Cache ) cacheManager.get( "conetentClassByParentClassName" );

        List childrenClass = ( List ) cache.getEntry( name + "|" + showFlag );

        if( childrenClass == null )
        {
            childrenClass = channelDao.getChildrenContentClassForParentClassName( name, showFlag );
            cache.putEntry( name + "|" + showFlag, childrenClass );
        }

        return childrenClass;
    }

    public List retrieveAllRootClassBeanInfoBySiteFlag( String siteFlag )
    {
        return channelDao.queryAllRootClassBeanInfoBySiteFlag( siteFlag );
    }

    public List retrieveAllClassBeanInfoBySiteFlag( String siteFlag )
    {
        return channelDao.queryAllClassBeanInfoBySiteFlag( siteFlag );
    }

    public List retrieveAllSpecClassBeanInfoBySiteFlag( String siteFlag )
    {
        return channelDao.queryAllSpecClassBeanInfoBySiteFlag( siteFlag );
    }

    public List retrieveAllClassBeanInfoBySiteFlagAndContentPubStatus( String siteFlag,
        Integer pubStaus )
    {
        return channelDao.queryAllClassBeanInfoBySiteFlagAndContentPubStatus( siteFlag, pubStaus );
    }

    public List retrieveAllClassBeanInfoBySiteFlagAndChannelPubStatus( String siteFlag,
        Integer pubStaus )
    {
        return channelDao.queryAllClassBeanInfoBySiteFlagAndChannelPubStatus( siteFlag, pubStaus );
    }

    public List retrieveAllClassBeanInfoBySiteFlagAndSpecClassPubStatus( String siteFlag,
        Integer pubStaus )
    {
        return channelDao.queryAllClassBeanInfoBySiteFlagAndSpecClassPubStatus( siteFlag, pubStaus );
    }

    public List fetchConetentClassByLayer( long longValue )
    {
        // TODO
        return null;
    }

    public List fetchConetentClassByContentType( int intValue )
    {
        if( intValue == -9999 )
        {
            return Collections.EMPTY_LIST;
        }

        List conetentClassList = null;

        conetentClassList = channelDao.getAllContentClassOrderByLinearAndType( "", intValue );

        return conetentClassList;
    }

    public void setChannelClassStaticPageURL( String endStaticClassFilePath, Long classId )
    {
        ContentClassBean classBean = null;

        try
        {
            classBean = retrieveSingleClassBeanInfoByClassId( classId );

            if( StringUtil.isStringNotNull( endStaticClassFilePath )
                && !endStaticClassFilePath.equals( classBean.getStaticPageUrl() ) )
            {
                channelDao.updateChannelClassStaticPageURL( endStaticClassFilePath, classId );

                ChannelDao.clearAllCache();

                ListContentClassInfoTreeController.resizeSiteContentClassCache();
                ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
                clearContentClassCache();

                ContentService.releaseContentCache();
            }

        }
        finally
        {

        }

    }

    public void setChannelClassHomeStaticPageURL( String endStaticClassFilePath, Long classId )
    {

        ContentClassBean classBean = null;

        try
        {
            classBean = retrieveSingleClassBeanInfoByClassId( classId );

            if( StringUtil.isStringNotNull( endStaticClassFilePath )
                && !endStaticClassFilePath.equals( classBean.getStaticHomePageUrl() ) )
            {
                channelDao.updateChannelClassHomeStaticPageURL( endStaticClassFilePath, classId );

                ChannelDao.clearAllCache();

                ListContentClassInfoTreeController.resizeSiteContentClassCache();
                ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
                clearContentClassCache();

                ContentService.releaseContentCache();
            }
        }
        finally
        {

        }

    }

    public Long fetchAllIncludeConetentClassCountByClassID( String siteFlag, boolean isSpec,
        Integer classType )
    {
        return channelDao.queryAllIncludeConetentClassCount( siteFlag, isSpec ? Constant.COMMON.ON
            : Constant.COMMON.OFF, classType );
    }

    public List fetchAllIncludeConetentClassByClassID( String siteFlag, boolean isSpec,
        Integer classType, Long pos, Integer size )
    {
        if( classType.intValue() != -1 )
        {
            return channelDao.queryAllIncludeConetentClassBean( siteFlag, "asc", isSpec
                ? Constant.COMMON.ON : Constant.COMMON.OFF, classType, pos, size );
        }

        return channelDao.queryAllIncludeConetentClassBean( siteFlag, "asc", isSpec
            ? Constant.COMMON.ON : Constant.COMMON.OFF );
    }

    public List fetchAllIncludeConetentClassByClassID( String siteFlag, boolean isSpec,
        Integer classType )
    {
        if( classType.intValue() != -1 )
        {
            return channelDao.queryAllIncludeConetentClassBean( siteFlag, "asc", isSpec
                ? Constant.COMMON.ON : Constant.COMMON.OFF, classType );
        }

        return channelDao.queryAllIncludeConetentClassBean( siteFlag, "asc", isSpec
            ? Constant.COMMON.ON : Constant.COMMON.OFF );
    }

    public void setChannelClassSinglePageModeContentId( Long classId, Long contentId )
    {
        channelDao.updateSinglePageModeClassContentId( classId, contentId );
    }

    /**
     * 更新栏目信息以及其扩展模型信息
     * 
     * @param contentClass
     * @param requestParams
     */
    public void editContentClassByClassId( ContentClass contentClass, DataModelBean model,
        List filedBeanList, ModelPersistenceMySqlCodeBean sqlCodeBean, Map requestParams )
    {
        ContentClassBean currentClassBean = null;

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            currentClassBean = retrieveSingleClassBeanInfoByClassId( contentClass.getClassId() );

            // logo图片信息处理

            Long liResId = Long
                .valueOf( StringUtil.getLongValue( contentClass.getLogoImage(), -1 ) );

            ServiceUtil.disposeOldImageInfo( liResId, "logoImage", requestParams );

            contentClass.setLogoImage( ServiceUtil.disposeSingleImageInfo( liResId ) );

            // banner图片信息处理

            Long baResId = Long.valueOf( StringUtil.getLongValue( contentClass.getBanner(), -1 ) );

            ServiceUtil.disposeOldImageInfo( baResId, "banner", requestParams );

            contentClass.setBanner( ServiceUtil.disposeSingleImageInfo( baResId ) );

            if( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( contentClass.getClassType() ) )
            {
                // 专题分类只更新部分信息
                channelDao.updateSpecialClassById( contentClass );
            }
            else
            {
                if( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT.equals( contentClass
                    .getClassType() ) )
                {
                    // 专题使用了ajax提交,故在这里转换下utf-8
                    contentClass.decodeForSpecialSubject();
                }

                channelDao.updateContentClassById( contentClass );

                // 2015:单页内容不匹配bug处理
                if( !currentClassBean.getClassType().equals( contentClass.getClassType() ) )
                {
                    channelDao.updateSinglePageModeClassContentId( currentClassBean.getClassId(),
                        null );
                }
            }

            /**
             * 更新静态地址
             */
            // 列表
            PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( contentClass
                .getClassPublishRuleId() );

            channelDao.updateChannelClassStaticPageURL( ruleBean == null ? site.getSiteUrl()
                : ruleBean.getFullContentClassPagePublishPath( site, currentClassBean, null, null,
                    Integer.valueOf( 1 ) )[1], currentClassBean.getClassId() );

            // 频道
            ruleBean = publishService.retrieveSinglePublishRuleBean( contentClass
                .getClassHomePublishRuleId() );

            channelDao.updateChannelClassHomeStaticPageURL( ruleBean == null ? site.getSiteUrl()
                : ruleBean.getFullContentClassPagePublishPath( site, currentClassBean, null, null,
                    Integer.valueOf( 1 ) )[1], currentClassBean.getClassId() );

            /**
             * 栏目扩展模型
             */
            // 获取自定义模型数据
            // 确认当前是否有扩展模型
            if( currentClassBean.getExtDataModelId().longValue() > 0 && model != null
                && filedBeanList != null && sqlCodeBean != null )
            {
                Integer count = contentDao.queryUserDefinedContentExist( model, contentClass
                    .getClassId() );

                ModelFiledInfoBean bean = null;

                boolean isSpecSubject = false;

                if( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT.equals( contentClass
                    .getClassType() ) )
                {
                    isSpecSubject = true;
                }

                List userDefineParamList = new ArrayList();

                String reUrl = null;

                Object val = null;

                List needUploadImageGroupInfoList = new ArrayList();

                for ( int j = 0; j < filedBeanList.size(); j++ )
                {
                    bean = ( ModelFiledInfoBean ) filedBeanList.get( j );
                    // 需要引入filed元数据来对不同类型字段进行对应处理

                    val = ServiceUtil.disposeDataModelFiledFromWeb( bean, requestParams,
                        needUploadImageGroupInfoList, isSpecSubject );

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
                userDefineParamList.add( contentClass.getClassId() );

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
                List oldGroupPhotoList = contentDao.queryGroupPhotoInfoByContentId( contentClass
                    .getClassId(), Constant.METADATA.MODEL_TYPE_CHANNEL,
                    ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                        .getCurrentLoginSiteInfo(), true );

                // 删除所有原相关信息
                contentDao.deletePhotoGroupInfo( contentClass.getClassId(),
                    Constant.METADATA.MODEL_TYPE_CHANNEL );

                // 增加本次改动中出现的所有的图片信息
                PhotoGroupInfo pgi = null;
                Set urlInfoSet = new HashSet();

                for ( int i = 0; i < needUploadImageGroupInfoList.size(); i++ )
                {
                    pgi = ( PhotoGroupInfo ) needUploadImageGroupInfoList.get( i );

                    urlInfoSet.add( pgi.getUrl() );

                    pgi.setContentId( contentClass.getClassId() );

                    // 模型类型
                    pgi.setModelType( Constant.METADATA.MODEL_TYPE_CHANNEL );

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
                        resService.updateSiteResourceTraceUseStatus( Long
                            .valueOf( ( String ) pgiInfo.get( "resId" ) ), Constant.COMMON.OFF );
                    }

                }

                // 清除已经存在的数据
                currentClassBean.getExt().clear();
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // TODO 清除栏目树 ,实现需要改进
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }
    }

    /**
     * 同步配置到其他栏目
     * 
     * @param classId
     * @param copyTemplate
     * @param copyPublish
     * @param copyStaticRule
     * @param copyImageRule
     * @param copyOther
     */
    public void disposeCopyChannelConfig( Long classId, Integer range, String[] copyTemplate,
        String[] copyPublish, String[] copyStaticRule, String[] copyImageRule, String[] copyOther )
    {
        try
        {
            mysqlEngine.beginTransaction();

            ContentClassBean classBean = retrieveSingleClassBeanInfoByClassId( classId );

            if( classBean.getClassId().longValue() < 0 )
            {
                return;
            }

            // 获取目标栏目
            List targetClassBeanList = null;
            if( Constant.SITE_CHANNEL.COPY_RANGE_ALL_CHILD.equals( range ) )
            {
                targetClassBeanList = channelDao.queryContentClassBeanByPreLinear( classBean
                    .getLinearOrderFlag() );

            }
            else if( Constant.SITE_CHANNEL.COPY_RANGE_SAME_MODEL.equals( range ) )
            {
                targetClassBeanList = channelDao.queryContentClassBeanByModelId( classBean
                    .getSiteFlag(), classBean.getContentType() );
            }
            else if( Constant.SITE_CHANNEL.COPY_RANGE_SINGLE_PAGE.equals( range ) )
            {
                targetClassBeanList = channelDao.querySinglePageContentClassBean( classBean
                    .getSiteFlag() );
            }

            String configFlag = null;
            ContentClassBean targetClassBean = null;

            mysqlEngine.startBatch();
            for ( int j = 0; j < targetClassBeanList.size(); j++ )
            {
                targetClassBean = ( ContentClassBean ) targetClassBeanList.get( j );

                for ( int i = 0; i < copyTemplate.length; i++ )
                {
                    configFlag = copyTemplate[i];

                    if( Constant.SITE_CHANNEL.CHANNEL_TEMPLATE.equals( configFlag ) )
                    {
                        channelDao.updateChannelTemplate( targetClassBean.getClassId(), classBean
                            .getClassHomeTemplateUrl() );
                    }
                    else if( Constant.SITE_CHANNEL.LIST_TEMPLATE.equals( configFlag ) )
                    {
                        channelDao.updateClassTemplateUrl( targetClassBean.getClassId(), classBean
                            .getClassTemplateUrl() );
                    }
                    else if( Constant.SITE_CHANNEL.CONTENT_TEMPLATE.equals( configFlag ) )
                    {
                        channelDao.updateContentTemplateUrl( targetClassBean.getClassId(),
                            classBean.getContentTemplateUrl() );
                    }
                }

                for ( int i = 0; i < copyPublish.length; i++ )
                {
                    configFlag = copyPublish[i];

                    if( Constant.SITE_CHANNEL.PUB_CHANNEL.equals( configFlag ) )
                    {
                        channelDao.updatePublishChannelState( targetClassBean.getClassId(),
                            classBean.getClassHomeProduceType() );
                    }
                    else if( Constant.SITE_CHANNEL.PUB_LIST.equals( configFlag ) )
                    {
                        channelDao.updatePublishClassState( targetClassBean.getClassId(), classBean
                            .getClassProduceType() );
                    }
                    else if( Constant.SITE_CHANNEL.PUB_CONTENT.equals( configFlag ) )
                    {
                        channelDao.updatePublishContentState( targetClassBean.getClassId(),
                            classBean.getContentProduceType() );
                    }
                }

                for ( int i = 0; i < copyStaticRule.length; i++ )
                {
                    configFlag = copyStaticRule[i];

                    if( Constant.SITE_CHANNEL.STATIC_RULE_CHANNEL.equals( configFlag ) )
                    {
                        channelDao.updateStaticChannelRule( targetClassBean.getClassId(), classBean
                            .getClassHomePublishRuleId() );
                    }
                    else if( Constant.SITE_CHANNEL.STATIC_RULE_LIST.equals( configFlag ) )
                    {
                        channelDao.updateStaticClassRule( targetClassBean.getClassId(), classBean
                            .getClassPublishRuleId() );
                    }
                    else if( Constant.SITE_CHANNEL.STATIC_RULE_CONTENT.equals( configFlag ) )
                    {
                        channelDao.updateStaticContentRule( targetClassBean.getClassId(), classBean
                            .getContentPublishRuleId() );
                    }
                }

                for ( int i = 0; i < copyImageRule.length; i++ )
                {
                    configFlag = copyImageRule[i];

                    if( Constant.SITE_CHANNEL.GUIDE_IMG_HOME.equals( configFlag ) )
                    {
                        channelDao.updateHomeGuideImageRule( targetClassBean.getClassId(),
                            classBean.getHomeImageW(), classBean.getHomeImageH(), classBean
                                .getHomeImageDM() );
                    }
                    else if( Constant.SITE_CHANNEL.GUIDE_IMG_CHANNEL.equals( configFlag ) )
                    {
                        channelDao.updateChannelGuideImageRule( targetClassBean.getClassId(),
                            classBean.getClassImageW(), classBean.getClassImageH(), classBean
                                .getClassImageDM() );
                    }
                    else if( Constant.SITE_CHANNEL.GUIDE_IMG_CLASS.equals( configFlag ) )
                    {
                        channelDao.updateListGuideImageRule( targetClassBean.getClassId(),
                            classBean.getListImageW(), classBean.getListImageH(), classBean
                                .getListImageDM() );
                    }
                    else if( Constant.SITE_CHANNEL.GUIDE_IMG_CONTENT.equals( configFlag ) )
                    {
                        channelDao.updateContentGuideImageRule( targetClassBean.getClassId(),
                            classBean.getContentImageW(), classBean.getContentImageH(), classBean
                                .getContentImageDM() );
                    }
                    else if( Constant.SITE_CHANNEL.GUIDE_IMG_EDITOR.equals( configFlag ) )
                    {
                        channelDao.updateEditorGuideImageRule( targetClassBean.getClassId(),
                            classBean.getEditorImageW(), classBean.getEditorImageH(), classBean
                                .getEditorImageDM() );
                    }
                    else if( Constant.SITE_CHANNEL.GUIDE_EDITOR_MARK.equals( configFlag ) )
                    {
                        channelDao.updateEditorImageMark( targetClassBean.getClassId(), classBean
                            .getEditorImageMark() );
                    }
                }

                for ( int i = 0; i < copyOther.length; i++ )
                {
                    configFlag = copyOther[i];

                    if( Constant.SITE_CHANNEL.CHANNEL_SHOW_STATUS.equals( configFlag ) )
                    {
                        channelDao.updateContentShowStatus( targetClassBean.getClassId(), classBean
                            .getShowStatus() );
                    }
                    else if( Constant.SITE_CHANNEL.CHANNEL_PAGE_LIMIT.equals( configFlag ) )
                    {
                        channelDao.updateContentListPageLimit( targetClassBean.getClassId(),
                            classBean.getListPageLimit() );
                    }
                    else if( Constant.SITE_CHANNEL.CHANNEL_SYNC_PUB.equals( configFlag ) )
                    {
                        channelDao.updateContentSyncPubClass( targetClassBean.getClassId(),
                            classBean.getSyncPubClass() );
                    }
                    else if( Constant.SITE_CHANNEL.CHANNEL_SEARCH_STATUS.equals( configFlag ) )
                    {
                        channelDao.updateContentSearchStatus( targetClassBean.getClassId(),
                            classBean.getSearchStatus() );
                    }
                    else if( Constant.SITE_CHANNEL.CHANNEL_CONTENT_RANGE.equals( configFlag ) )
                    {
                        channelDao.updateContentRelateRangeType( targetClassBean.getClassId(),
                            classBean.getRelateRangeType() );
                    }
                    else if( Constant.SITE_CHANNEL.CHANNEL_MEMBER_CONTENT.equals( configFlag ) )
                    {
                        channelDao.updateMemberAddContent( targetClassBean.getClassId(), classBean
                            .getMemberAddContent() );
                    }
                    else if( Constant.SITE_CHANNEL.CHANNEL_CONTENT_MODEL.equals( configFlag ) )
                    {
                        channelDao.updateClassContentModelId( targetClassBean.getClassId(),
                            classBean.getContentType() );
                    }
                    else if( Constant.SITE_CHANNEL.CHANNEL_CONTENT_WORKFLOW.equals( configFlag ) )
                    {
                        channelDao.updateClassWorkflowId( targetClassBean.getClassId(), classBean
                            .getWorkflowId() );
                    }
                    else if( Integer.valueOf( 9 ).equals( configFlag ) )
                    {
                        channelDao.updateClassDefModelId( targetClassBean.getClassId(), classBean
                            .getExtDataModelId() );
                    }
                }

            }
            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // TODO 清除栏目树 ,实现需要改进
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }

    }

    /**
     * 删除当前栏目以及其子栏目以及所有内容模型的信息
     * 
     * @param targetClassId
     */
    public ContentClassBean deleteContentClassAndChildrenAllInfomation(
        ContentClassBean contentClassBean )
    {
        if( contentClassBean.getClassId() == null || contentClassBean.getClassId().longValue() < 1 )
        {
            throw new FrameworkException( "传入非法的栏目ID" );
        }

        // 获取当前栏目信息
        contentClassBean = channelDao.querySingleClassBeanInfoByClassId( contentClassBean
            .getClassId() );

        if( contentClassBean == null || contentClassBean.getClassId().longValue() < 1 )
        {
            log.info( "[service] deleteContentClassAndChildrenAllInfomation - 栏目已不存在，classId:"
                + contentClassBean.getClassId() );
            return null;
        }

        /**
         * 删除内容信息,防止事务重复启动和过长,删除内容和栏目当作两个事务
         */
        // 删除内容
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( contentClassBean.getSiteFlag() );

        ServiceUtil.deleteClassAllContentInfo( site, contentClassBean );

        try
        {
            mysqlEngine.beginTransaction();

            Long targetClassId = contentClassBean.getClassId();
            Long parentId = contentClassBean.getParent();

            String allChildrenId = channelDao.queryLayerCacheByClassId( targetClassId );

            int brotherCount = channelDao.queryBrotherNodeCountBySelf( targetClassId );

            String layerCache = channelDao.queryLayerCacheByClassId( targetClassId );

            /**
             * 父节点目前只有一个孩子.删除后需要将父节点设定为叶子节点状态
             */
            if( brotherCount == 0 )//
            {
                // 已经是第一层不需要改动父节点状态
                if( parentId.longValue() != -9999 )
                {
                    channelDao.updateContentClassLeafNodeState( parentId, Boolean.TRUE );
                }
            }
            else
            // 删除当前节点后至少还有一个孩子
            {
                /**
                 * 若删除的节点为最末孩子位置,需要更新第一个前兄弟的last child属性
                 */
                if( Constant.SITE_CHANNEL.TRUE_FLAG.equals( contentClassBean.getIsLastChild() ) )// 在有其他兄弟节点的情况下,此节点为最后一个孩子.
                {
                    Long brotherId = channelDao.queryUpBrotherNodeByClassIdAndTheirParentId(
                        targetClassId, parentId );

                    channelDao.updateContentClassLastChildFlag( brotherId, Boolean.TRUE );
                }
            }

            /**
             * 更改其所有祖先节点的线索表数据,从其第一个父亲节点开始
             */

            deleteAllForefatherNodeLayerByCunrrentNode( parentId, layerCache );

            /**
             * 执行最后目标节点相关信息删除
             */

            // 3.删除所有栏目信息
            channelDao.deleteContentClassAndChildrenClassByTargetId( targetClassId, allChildrenId );

            /**
             * 删除当前及其所有孩子的线索表缓存数据,并改动其所有直接祖先节点的线索表缓存数据
             */
            // 删除当前节点及孩子节点的线索表数据
            channelDao.deleteLayerCacheInfoByLayerCache( layerCache );

            /**
             * 删除扩展数据
             */

            metaDataService.deleteAndClearDefModelInfo( contentClassBean.getClassId(),
                contentClassBean.getExtDataModelId(), contentClassBean.getSiteFlag() );

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();
            // TODO
            ChannelDao.clearAllCache();
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }

        return contentClassBean;
    }

    /**
     * 删除当前栏目,不处理树节点
     * 
     * @param targetClassId
     */
    public ContentClassBean deleteContentClassAllInfomationNotDisposeTreeInfo(
        ContentClassBean contentClassBean )
    {
        if( contentClassBean.getClassId() == null || contentClassBean.getClassId().longValue() < 1 )
        {
            throw new FrameworkException( "传入非法的栏目ID" );
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 获取当前栏目信息
            contentClassBean = channelDao.querySingleClassBeanInfoByClassId( contentClassBean
                .getClassId() );

            if( contentClassBean == null || contentClassBean.getClassId().longValue() < 1 )
            {
                log.info( "[service] deleteContentClassAndChildrenAllInfomation - 栏目已不存在 " );
                return null;
            }

            Long targetClassId = contentClassBean.getClassId();

            String allChildrenId = channelDao.queryLayerCacheByClassId( targetClassId );

            String layerCache = channelDao.queryLayerCacheByClassId( targetClassId );

            /**
             * 执行最后目标节点相关信息删除
             */

            // 3.删除所有栏目信息
            channelDao.deleteContentClassAndChildrenClassByTargetId( targetClassId, allChildrenId );

            /**
             * 删除当前及其所有孩子的线索表缓存数据,并改动其所有直接祖先节点的线索表缓存数据
             */
            // 删除当前节点及孩子节点的线索表数据
            channelDao.deleteLayerCacheInfoByLayerCache( layerCache );

            /**
             * 删除扩展数据
             */

            metaDataService.deleteAndClearDefModelInfo( contentClassBean.getClassId(),
                contentClassBean.getExtDataModelId(), contentClassBean.getSiteFlag() );

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();

            ChannelDao.clearAllCache();
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }

        StatService.getInstance().deleteClassTrace( contentClassBean.getClassId() );

        return contentClassBean;
    }

    /**
     * 在所给的任何 layerCache中 删除id list中出现的id数据
     * 
     * @param layerCache
     * @param layerIdList
     * @return
     */
    private String deleteUnusefulLayerCache( String layerCache, List layerIdList )
    {
        log.info( "deleteUnusefulLayerCache()  layerCache:" + layerCache + " ,layerIdList"
            + layerIdList );

        String layerCacheTmp = layerCache.trim();

        String idInfo = "";
        String endTmp = "";
        for ( int i = 0; i < layerIdList.size(); i++ )
        {
            idInfo = ( String ) layerIdList.get( i );

            if( StringUtil.isStringNull( idInfo ) )
            {
                continue;
            }

            if( layerCacheTmp.indexOf( idInfo ) == 0 )// 在第一位置,处理字符串不同
            {
                endTmp = idInfo + " or ";
            }
            else
            {
                endTmp = " or " + idInfo;
            }

            layerCacheTmp = StringUtil.replaceString( layerCacheTmp, endTmp, "", false, false );

        }

        return layerCacheTmp + " ";
    }

    private List changeLayerCacheToIdList( String layerCache )
    {
        if( StringUtil.isStringNull( layerCache ) )
        {
            return Collections.emptyList();
        }

        List resultList = new ArrayList();

        String[] idInfos = StringUtil.split( layerCache, "or" );

        for ( int i = 0; i < idInfos.length; i++ )
        {
            resultList.add( idInfos[i].trim() );
        }

        return resultList;
    }

    /**
     * 对于传入的ID列表，若有相同的父节点，则返回true
     * 
     * @param idList
     * @return
     */
    public boolean checkContentClassBelongToSameParent( List idList )
    {

        Set singlenessSet = new HashSet();

        for ( int i = 0; i < idList.size(); i++ )
        {
            singlenessSet.add( channelDao.queryParentIdByClassId( ( Long ) idList.get( i ) ) );
        }

        if( singlenessSet.size() > 1 )
        {
            return false;
        }

        return true;

    }

    /**
     * 交换两个节点的顺序
     * 
     * @param currentClassId
     * @param targetClassId
     */
    public void swapContentClassNote( Long currentClassId, Long targetClassId )
    {

        ContentClassNodeInfoBean currentNodeInfoBean = null;
        ContentClassNodeInfoBean targetNodeInfoBean = null;

        String currentNodeLinearFlag = "";
        String tragetNodeLinearFlag = "";

        boolean currentNodeLastChildFlag = false;
        boolean targetNodeLastChildFlag = false;

        try
        {
            mysqlEngine.beginTransaction();

            currentNodeInfoBean = channelDao.queryContentClassNodeInfoByClassId( currentClassId );
            targetNodeInfoBean = channelDao.queryContentClassNodeInfoByClassId( targetClassId );

            if( currentNodeInfoBean == null )
            {
                return;
            }

            if( targetNodeInfoBean == null )
            {
                return;
            }

            currentNodeLinearFlag = currentNodeInfoBean.getLinearOrderFlag();

            tragetNodeLinearFlag = targetNodeInfoBean.getLinearOrderFlag();

            currentNodeLastChildFlag = currentNodeInfoBean.isLastChild();

            targetNodeLastChildFlag = targetNodeInfoBean.isLastChild();

            // 对于交换节点双方的孩子节点属性处理
            if( currentNodeLastChildFlag )
            {

                channelDao.updateContentClassLastChildFlag( currentClassId, Boolean.FALSE );
                channelDao.updateContentClassLastChildFlag( targetClassId, Boolean.TRUE );

            }
            else if( targetNodeLastChildFlag )
            {

                channelDao.updateContentClassLastChildFlag( targetClassId, Boolean.FALSE );
                channelDao.updateContentClassLastChildFlag( currentClassId, Boolean.TRUE );

            }

            // 交换彼此的排序信息,包括所有子孩子

            log.info( "tragetNodeLinearFlag : " + tragetNodeLinearFlag );
            log.info( "currentNodeLinearFlag : " + currentNodeLinearFlag );

            List allTargetNodeInfoList = channelDao
                .queryContentNodeInfoBeanByPreLinear( tragetNodeLinearFlag );

            List allCurrentNodeInfoList = channelDao
                .queryContentNodeInfoBeanByPreLinear( currentNodeLinearFlag );

            String linearFlag = "";
            Long classId;
            ContentClassNodeInfoBean nodeInfoBean = null;

            mysqlEngine.startBatch();
            for ( int i = 0; i < allTargetNodeInfoList.size(); i++ )
            {
                nodeInfoBean = ( ContentClassNodeInfoBean ) allTargetNodeInfoList.get( i );

                linearFlag = nodeInfoBean.getLinearOrderFlag();
                classId = nodeInfoBean.getClassId();

                log.info( "LinearFlag replace - linearFlag:" + linearFlag
                    + ", tragetNodeLinearFlag:" + tragetNodeLinearFlag + ", currentNodeLinearFlag:"
                    + currentNodeLinearFlag );

                linearFlag = StringUtil.replaceString( linearFlag, tragetNodeLinearFlag,
                    currentNodeLinearFlag, false, true );

                log.info( "linearFlag result:" + linearFlag );

                channelDao.updateContentClasslinearOrderFlagByClassId( classId, linearFlag );
            }

            for ( int j = 0; j < allCurrentNodeInfoList.size(); j++ )
            {
                nodeInfoBean = ( ContentClassNodeInfoBean ) allCurrentNodeInfoList.get( j );

                linearFlag = nodeInfoBean.getLinearOrderFlag();
                classId = nodeInfoBean.getClassId();

                log.info( "LinearFlag replace - linearFlag:" + linearFlag
                    + ", tragetNodeLinearFlag:" + tragetNodeLinearFlag + ", currentNodeLinearFlag:"
                    + currentNodeLinearFlag );

                linearFlag = StringUtil.replaceString( linearFlag, currentNodeLinearFlag,
                    tragetNodeLinearFlag, false, true );

                log.info( "linearFlag result:" + linearFlag );

                channelDao.updateContentClasslinearOrderFlagByClassId( classId, linearFlag );

            }
            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // TODO
            ChannelDao.clearAllCache();
            // TODO 清除栏目树 ,实现需要改进
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }
    }

    /**
     * 根据提供的信息，将指定栏目(包括其子栏目)移动到新的父栏目下。
     * 
     * @param moveNodeInfoBean
     */
    public int moveContentClassNodeToNewlayer( MoveNodeInfoBean moveNodeInfoBean )
    {
        log.info( "[service] moveContentClassNodeToNewlayer() [moveNodeInfoBean]:"
            + moveNodeInfoBean );

        try
        {
            mysqlEngine.beginTransaction();

            Long classId = moveNodeInfoBean.getClassId();
            Long parentId = moveNodeInfoBean.getCurrentParent();
            Long selectParentId = moveNodeInfoBean.getSelectParent();

            String unUselayerCache = channelDao.queryLayerCacheByClassId( classId );

            // if( moveNodeInfoBean.getSelectParent().longValue() == -9999 )//
            // 当前栏目将作为一级栏目,删除所有其直接祖先的layer
            // // cache信息
            // {
            //
            // }

            ContentClassNodeInfoBean selectParentNodeInfoBean = channelDao
                .queryContentClassNodeInfoByClassId( selectParentId );

            ContentClassNodeInfoBean currentNodeInfoBean = channelDao
                .queryContentClassNodeInfoByClassId( classId );

            if( selectParentNodeInfoBean == null && selectParentId.intValue() != -9999 )
            {
                log.equals( "无法获取所指定节点的信息,classId:" + selectParentId );
                throw new FrameworkException( "无法获取所指定节点的信息,classId:" + selectParentId );
            }

            if( currentNodeInfoBean == null )
            {
                log.equals( "无法获取所指定节点的信息,classId:" + classId );
                throw new FrameworkException( "无法获取所指定节点的信息,classId:" + classId );
            }

            if( selectParentId.intValue() != -9999
                && selectParentNodeInfoBean.getLinearOrderFlag().indexOf(
                    currentNodeInfoBean.getLinearOrderFlag() ) == 0
                && selectParentNodeInfoBean.getLinearOrderFlag().length() > currentNodeInfoBean
                    .getLinearOrderFlag().length() )
            {
                return -2;// 所选父节点是其孩子节点
            }

            // 需要改动相关节点属性信息
            // 1. 若其父节点只有一个孩子，父节点属性改变，若本身为最后一个孩子，改变前一个兄弟属性
            // 2.新加入节点后的，其最后一个兄弟属性改变，自身的属性改变.

            /**
             * 1.
             */
            int brotherNodeCount = channelDao.queryBrotherNodeCountBySelf( classId );

            if( brotherNodeCount == 0 )// 为零意味着所操作的节点没有兄弟,需要改变父节点的相关属性
            {
                channelDao.updateContentClassLeafNodeState( parentId, Boolean.TRUE );
            }

            boolean currentNodeIslastChild = channelDao.queryLastChildFlag( classId );

            if( currentNodeIslastChild )// 如不是操作节点是最后一个孩子,需要改变他的前一个兄弟为最后一个孩子
            {
                Long hisBrotherId = channelDao.queryUpBrotherNodeByClassIdAndTheirParentId(
                    classId, parentId );
                channelDao.updateContentClassLastChildFlag( hisBrotherId, Boolean.TRUE );

            }

            /**
             * 2.
             */
            boolean selectParentIsLeaf = false;
            String newLayerLinear = "";
            int newLayer = 1;

            if( selectParentId.intValue() != -9999 )
            {
                selectParentIsLeaf = selectParentNodeInfoBean.isLeaf();

                newLayer = selectParentNodeInfoBean.getLayer().intValue() + 1;
            }

            if( selectParentIsLeaf )// 若所选的父节点为叶子,将改变其状态
            {
                channelDao.updateContentClassLeafNodeState( selectParentId, Boolean.FALSE );
                // 操作节点的NODE属性改变

                newLayerLinear = ServiceUtil.increaseLayerLinear( selectParentNodeInfoBean
                    .getLinearOrderFlag(), true );

            }
            else
            {// 所选父节点有孩子,改变最后一个孩子的状态

                ContentClassNodeInfoBean hisNewBrotherNodeInfoBean = channelDao
                    .queryLastChildClassNodeInfoForParent( selectParentId );

                if( hisNewBrotherNodeInfoBean == null )
                {
                    log.error( "无法获取新位置的最后一个兄弟栏目,selectParentId:" + selectParentId );
                    throw new FrameworkException( "无法获取新位置的最后一个兄弟栏目,selectParentId:"
                        + selectParentId );
                }

                Long hisNewBrotherId = hisNewBrotherNodeInfoBean.getClassId();

                channelDao.updateContentClassLastChildFlag( hisNewBrotherId, Boolean.FALSE );

                // 按照其最后一个兄弟的layerLinear更新
                newLayerLinear = ServiceUtil.increaseLayerLinear( hisNewBrotherNodeInfoBean
                    .getLinearOrderFlag(), false );
            }

            channelDao.updateContentClassNodeInfoByClassId( classId, Integer.valueOf( newLayer ),
                newLayerLinear );

            // 必定为最后一个孩子
            channelDao.updateContentClassLastChildFlag( classId, Boolean.TRUE );

            /**
             * 更新其所有孩子节点的属性
             */
            String prevLinear = currentNodeInfoBean.getLinearOrderFlag();
            log.info( "原线性flag:" + prevLinear + ", 移动后的新线性flag:" + newLayerLinear );

            int prevLayer = currentNodeInfoBean.getLayer().intValue();
            log.info( "原layer:" + prevLayer + ", 移动后的新layer:" + newLayer );

            List allChildNode = channelDao.queryContentNodeInfoBeanByPreLinear( prevLinear );

            ContentClassNodeInfoBean bean = null;
            String newChildLinearFlag = "";
            int newChhildLayer;

            mysqlEngine.startBatch();
            for ( int i = 0; i < allChildNode.size(); i++ )
            {
                bean = ( ContentClassNodeInfoBean ) allChildNode.get( i );
                newChildLinearFlag = StringUtil.replaceString( bean.getLinearOrderFlag(),
                    prevLinear, newLayerLinear, false, true );

                newChhildLayer = bean.getLayer().intValue() - ( prevLayer - newLayer );
                channelDao.updateContentClassNodeInfoByClassId( bean.getClassId(), Integer
                    .valueOf( newChhildLayer ), newChildLinearFlag );

            }
            mysqlEngine.executeBatch();

            /**
             * 删除其原所有祖先节点的线索表数据,从其第一个父亲节点开始
             */
            deleteAllForefatherNodeLayerByCunrrentNode( parentId, unUselayerCache );

            /**
             * 若所移动的位置不为第一级节点,更改新的位置的所有祖先节点的线索表数据,从其第一个父亲节点开始.
             */
            if( selectParentId.intValue() != -9999 )
            {
                addNewClassNodeLayerCache( selectParentId, classId, true );
            }

            /**
             * 改变操作节点的父亲节点
             */
            channelDao.setContentClassParent( classId, selectParentId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ChannelDao.clearAllCache();
            // TODO 清除栏目树 ,实现需要改进
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }

        // 更新channelPath

        Long classId = moveNodeInfoBean.getClassId();

        ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

        List classBeanList = channelDao.queryContentClassBeanByPreLinear( classBean
            .getLinearOrderFlag() );

        for ( int i = 0; i < classBeanList.size(); i++ )
        {
            classBean = ( ContentClassBean ) classBeanList.get( i );

            String newPath = getCurrentChannelPath( classBean );

            channelDao.updateContentClassPath( classBean.getClassId(), newPath );
        }
        return 0;
    }

    private String getCurrentChannelPath( ContentClassBean classBean )
    {
        List pathList = new ArrayList();

        pathList.add( classBean.getClassId() );

        ContentClassBean parent = channelDao.querySingleClassBeanInfoByClassId( classBean
            .getParent() );

        while ( parent != null )
        {

            pathList.add( parent.getClassId() );

            parent = channelDao.querySingleClassBeanInfoByClassId( parent.getParent() );
        }

        Collections.reverse( pathList );

        StringBuilder buf = new StringBuilder();

        for ( int i = 0; i < pathList.size(); i++ )
        {
            if( i + 1 != pathList.size() )
            {
                buf.append( pathList.get( i ) + "," );
            }
            else
            {
                buf.append( pathList.get( i ) );
            }
        }

        return buf.toString();
    }

    /**
     * 更改其祖先节点的线索表数据,从parentId以及其直接祖先节点的layer cache中删除layerCache中出现的id信息
     * 
     * @param parentId
     * @param layerCache
     */
    private void deleteAllForefatherNodeLayerByCunrrentNode( Long parentId, String layerCache )
    {

        log.info( "[Service方法] deleteAllForefatherNodeLayerByCunrrentNode() [参数] parentId:"
            + parentId + " ,layerCache:" + layerCache );
        /**
         * 更改其所有祖先节点的线索表数据,从其第一个父亲节点开始
         */

        Long currParentClassId = parentId;
        Long parent = null;
        String layerCacheTmp = "";
        List layerIdList = changeLayerCacheToIdList( layerCache );

        while ( currParentClassId.longValue() != -9999 )// 如果所给的父亲ID为站点标志,不需要进行操作
        {

            List res = channelDao.getParentClassIdAndCurrentLayerInfoByChild( currParentClassId );

            Map resMap = ( Map ) res.get( 0 );

            // 更新当前layer cache
            layerCacheTmp = ( String ) resMap.get( "layerQuerySqlCache" );

            layerCacheTmp = deleteUnusefulLayerCache( layerCacheTmp, layerIdList );

            log.info( "变化后的layer cache : " + layerCacheTmp );

            channelDao.setContentClassLayerCache( currParentClassId, layerCacheTmp );

            // 若当前节点已经是第一级栏目,逻辑完成
            parent = ( Long ) resMap.get( "parent" );
            log.info( "当前父节点 : " + parent );

            currParentClassId = parent;

        }
    }

    /**
     * 生成新的节点线性信息
     * 
     * @param currentLayer
     * @param parentId
     * @return
     * @throws Exception
     */
    private String makeLayerNextLinearFlag( Integer currentLayer, Long parentId ) throws Exception
    {

        // 取父节点的最后一个子节点
        ContentClass lastContentClass = channelDao.queryLastChildClassForParentID( parentId );

        boolean noChildNodeFlag = false;
        if( lastContentClass == null )// 无孩子结点
        {
            lastContentClass = channelDao.getContentClassVoForClassID( parentId );

            if( lastContentClass == null )
            {
                if( ( parentId.longValue() == -9999 ) )
                {
                    return "001";
                }
                else
                {
                    throw new Exception( "无法取得当前栏目，ID为:" + parentId );
                }

            }

            noChildNodeFlag = true;
        }

        String linearFlag = lastContentClass.getLinearOrderFlag();
        int layer = currentLayer.intValue();
        int offset = ( layer - 1 ) * 3;

        String linearFlagStringPrefix = StringUtil.subString( linearFlag, 0, offset );

        if( noChildNodeFlag )
        {
            // 设定父节点为非叶子节点,在父节点已经有子节点的情况下，不需要改变兄弟节点叶子属性
            channelDao.updateContentClassLeafNodeState( lastContentClass.getClassId(),
                Boolean.FALSE );
            return linearFlagStringPrefix + "001";

        }

        channelDao.updateContentClassLastChildFlag( lastContentClass.getClassId(), Boolean.FALSE );

        String linearFlagString = StringUtil.subString( linearFlag, offset, offset + 3 );

        int x = Integer.valueOf( linearFlagString ).intValue();
        int nextFlag = x + 1;
        if( nextFlag >= Constant.SITE_CHANNEL.LIMIT_CLASS_SIZE )
        {
            throw new FrameworkException( "当前栏目数量已达到最大." );
        }

        String currentLinearFlag = linearFlagStringPrefix + ServiceUtil.getEndLayerFlag( nextFlag );

        return currentLinearFlag;
    }

    /**
     * 根据指定的父节点,将此targetClass的layer cache增加到其所有直接祖先中
     * 
     * @param currParentClassId
     * @param targetClassId
     */
    private void addNewClassNodeLayerCache( Long currParentClassId, Long targetClassId,
        boolean isIncludeAllChild )
    {

        log.info( "addNewClassNodeLayerCache() [参数] currParentClassId:" + currParentClassId
            + " ,targetClassId:" + targetClassId + " ,isIncludeAllChild:" + isIncludeAllChild );

        Long parent = null;

        String headLayerCahe = "";

        String endLayerCahe = "";

        if( !isIncludeAllChild )
        {
            headLayerCahe = "classId=" + targetClassId + " ";
            endLayerCahe = "or classId=" + targetClassId + " ";
        }
        else
        {
            String targetAllChildLayerCache = channelDao.queryLayerCacheByClassId( targetClassId );

            if( StringUtil.isStringNull( targetAllChildLayerCache ) )
            {
                log.error( "无法获取layer cache, classId:" + targetClassId );
                throw new FrameworkException( "无法获取layer cache, classId:" + targetClassId );
            }

            headLayerCahe = targetAllChildLayerCache;
            endLayerCahe = "or " + targetAllChildLayerCache;
        }

        log.info( "headLayerCahe:" + headLayerCahe );
        log.info( "endLayerCahe:" + endLayerCahe );

        // 如果是单节点,意味着增加节点,需要先保存这个节点的layer cache
        if( !isIncludeAllChild )
        {
            channelDao.saveContentClassLayerCache( targetClassId, headLayerCahe );
        }

        /**
         * 以下在target节点的直接祖先中增加其Layer cache
         */
        while ( currParentClassId.longValue() != -9999 )
        {

            channelDao.updateContentClassLayerCache( currParentClassId, endLayerCahe );

            List res = channelDao.getParentClassIdAndCurrentLayerInfoByChild( currParentClassId );

            Map resMap = ( Map ) res.get( 0 );
            parent = ( Long ) resMap.get( "parent" );

            log.info( "Node属性:" + resMap );

            currParentClassId = parent;
        }

    }

    public void setClassPublishPageAssistant( Long classId, String classTemplateUrl,
        Integer lastPagePos, String endPageUrl, Long ruleId )
    {
        try
        {
            mysqlEngine.beginTransaction();

            channelDao.deleteClassPublishPageAssistant( classId, classTemplateUrl );

            channelDao.insertClassPublishPageAssistant( classId, lastPagePos, classTemplateUrl,
                endPageUrl, ruleId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearPublishCache();
        }

    }

    public void createContentType( ContentType ct )
    {

        if( ct == null )
        {
            return;
        }

        try
        {
            ct.setSiteId( ( ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo() ).getSiteId() );

            channelDao.saveContentType( ct );
        }
        finally
        {
            ChannelService.clearContentTypeCache();
        }

    }

    public void editContentType( ContentType ct )
    {
        try
        {
            channelDao.updateContentType( ct );
        }
        finally
        {
            ChannelService.clearContentTypeCache();
        }

    }

    public void deleteContentType( List typeIdList )
    {
        if( typeIdList == null )
        {
            return;
        }

        try
        {
            Long typeId = null;

            for ( int i = 0; i < typeIdList.size(); i++ )
            {

                typeId = Long
                    .valueOf( StringUtil.getLongValue( ( String ) typeIdList.get( i ), -1 ) );

                channelDao.deleteContentType( typeId );

            }
        }
        finally
        {
            ChannelService.clearContentTypeCache();
        }

    }

    public Map retrieveClassPublishPageAssistant()
    {
        String key = "retrieveClassPublishPageAssistant";
        Cache cache = ( Cache ) cacheManager.get( "retrieveClassPublishPageAssistant" );
        Map resultMap = ( Map ) cache.getEntry( key );

        if( resultMap == null )
        {
            resultMap = channelDao.queryClassPublishPageAssistant();
            cache.putEntry( key, resultMap );
        }

        return resultMap;

    }

    public void setChannelClassEndStaticPageInfo( Long classId, Integer disposePageCurrentCount,
        String endPageUrl )
    {
        try
        {
            channelDao.updateChannelClassEndStaticPageInfo( classId, disposePageCurrentCount,
                endPageUrl );
        }
        finally
        {
            clearContentClassCache();

            ContentService.releaseContentCache();
        }

    }

    /**
     * 获取指定栏目标志的全路径顺序,信息
     * 
     * @param linearOrderFlag
     * @return
     */
    public List retrieveContentClassBeanByCurrentPath( String currentPath )
    {
        if( StringUtil.isStringNull( currentPath ) )
        {
            return null;
        }

        List result = channelDao.queryContentClassBeanChannelPath( currentPath );

        return result;

    }

    /**
     * 获取所有推荐类型
     * 
     * @return
     */
    public List retrieveContentCommendTypeBean( String siteFlag, Long classId, boolean showAll,
        boolean isSpec, boolean classIdNullShowAll )
    {
        String key = "retrieveAllContentCommendTypeBean:" + siteFlag + "|" + classId + "|"
            + showAll + "|" + isSpec + "|" + classIdNullShowAll;

        Cache cache = ( Cache ) cacheManager.get( "retrieveAllContentCommendTypeBean" );

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            if( classId.longValue() == -1 )// 取全部或返回空
            {
                if( isSpec )
                {
                    if( classIdNullShowAll )
                    {
                        result = channelDao.queryAllSpecInfoTypeBean( siteFlag );
                    }
                    else
                    {
                        result = new ArrayList();
                    }
                }
                else
                {
                    if( classIdNullShowAll )
                    {
                        result = channelDao.queryAllContentCommendTypeBean( siteFlag );
                    }
                    else
                    {
                        result = new ArrayList();
                    }
                }
            }
            else
            {
                if( showAll )// 取当前栏目所有可获取的推荐位
                {
                    ContentClassBean classBean = channelDao
                        .querySingleClassBeanInfoByClassId( classId );

                    String channelPath = classBean.getChannelPath();

                    // 所有可能的父栏目id
                    List classIdArray = StringUtil.changeStringToList( channelPath, "," );

                    result = channelDao.queryContentCommendTypeBeanByClassIds( siteFlag,
                        classIdArray, classId );
                }
                else
                {
                    if( isSpec )
                    {
                        result = channelDao.queryContentSpecInfoTypeBean( siteFlag, classId );
                    }
                    else
                    {
                        result = channelDao.queryContentCommendTypeBean( siteFlag, classId );
                    }
                }
            }

            cache.putEntry( key, result );
        }

        return result;
    }

    /**
     * 获取推荐类型可以选取内容的栏目
     * 
     * @return
     */
    public List retrieveContentCommendContentClassBean( String siteFlag, Long typeId )
    {
        List result = null;

        try
        {
            mysqlEngine.beginTransaction();

            ContentCommendTypeBean commTypeBean = channelDao
                .querySingleContentCommendTypeBeanByTypeId( typeId );

            Long classId = commTypeBean.getClassId();

            // 专题模式全站信息可选
            if( Constant.COMMON.ON.equals( commTypeBean.getIsSpec() ) )
            {
                classId = Long.valueOf( -9999 );
            }

            if( classId.longValue() == -9999 )
            {
                // 通用推荐位,返回全站栏目
                result = channelDao.queryAllClassBeanInfoBySiteFlag( siteFlag );
            }
            else
            {
                // 栏目推荐位

                ContentClassBean currentClassBean = channelDao
                    .querySingleClassBeanInfoByClassId( classId );

                if( Constant.COMMON.ON.equals( commTypeBean.getChildClassMode() ) )
                {
                    // 子栏目可推荐

                    result = channelDao.queryContentClassBeanByPreLinear( currentClassBean
                        .getLinearOrderFlag() );
                }
                else
                {
                    result = new ArrayList( 1 );

                    result.add( currentClassBean );

                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    /**
     * 获取所有分类类型
     * 
     * @return
     */
    public List retrieveAllContentTypeBean( Long siteId )
    {
        String key = "retrieveAllContentTypeBean:" + siteId;
        Cache cache = ( Cache ) cacheManager.get( "retrieveAllContentTypeBean" );

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            result = channelDao.queryAllContentTypeBean( siteId );

            cache.putEntry( key, result );
        }

        return result;
    }

    public ContentTypeBean retrieveSingleContentTypeBean( Long typeId )
    {
        return channelDao.querySingleContentTypeBean( typeId );
    }

    public Long addCommendTypeInfo( ContentCommendType commendType )
    {
        try
        {
            return channelDao.saveCommendTypeInfo( commendType );
        }
        finally
        {
            clearCommendCache();
        }

    }

    public void editCommendTypeInfo( ContentCommendType commendType )
    {
        try
        {
            mysqlEngine.beginTransaction();

            ContentCommendTypeBean oldCommTypeBean = channelDao
                .querySingleContentCommendTypeBeanByTypeId( commendType.getCommendTypeId() );

            if( oldCommTypeBean != null )
            {
                channelDao.updateCommendTypeInfo( commendType );

                // 更新推荐内容新的flag
                contentDao.updateCommendTypeContentCommFlagByTypeFlag( commendType.getCommFlag(),
                    oldCommTypeBean.getCommFlag() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCommendCache();
        }

    }

    public ContentCommendTypeBean retrieveSingleContentCommendTypeBeanByTypeId( Long typeId )
    {
        String key = "retrieveSingleContentCommendTypeBeanByTypeId" + typeId;

        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleContentCommendTypeBeanByTypeId" );

        ContentCommendTypeBean result = ( ContentCommendTypeBean ) cache.getEntry( key );

        if( result == null )
        {
            result = channelDao.querySingleContentCommendTypeBeanByTypeId( typeId );

            cache.putEntry( key, result );
        }

        return result;

    }

    public void deleteCommendTypeAllInfo( List typeIdList )
    {
        if( typeIdList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 检查所删除的推荐位是否为当前管理站点的推荐位,以防止非法删除
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            Long typeId = null;
            ContentCommendTypeBean commTypeBean = null;

            for ( int i = 0; i < typeIdList.size(); i++ )
            {
                if( typeIdList.get( i ) instanceof Long )
                {
                    typeId = ( Long ) typeIdList.get( i );
                }
                else
                {
                    typeId = Long.valueOf( StringUtil.getLongValue( ( String ) typeIdList.get( i ),
                        -1 ) );
                }

                if( typeId.longValue() < 0 )
                {
                    continue;
                }

                commTypeBean = channelDao.querySingleContentCommendTypeBeanByTypeId( typeId );

                // 并非此站点的推荐位,

                if( commTypeBean == null || !site.getSiteFlag().equals( commTypeBean.getSiteFlag() ) )
                {
                    continue;
                }

                List commList = contentDao.queryCommendRowInfoByCommFlag( commTypeBean
                    .getCommFlag(), commTypeBean.getSiteFlag() );

                // 删除图片
                ContentCommendPushInfoBean bean = null;

                for ( int j = 0; j < commList.size(); j++ )
                {
                    bean = ( ContentCommendPushInfoBean ) commList.get( j );

                    ServiceUtil.deleteSiteResTraceMode( Long.valueOf( StringUtil.getLongValue( bean
                        .getImgResId(), -1 ) ) );
                }

                // 删除type本身信息
                channelDao.deleteCommendType( typeId );

                // 删除对应的推荐位信息
                contentDao.deleteCommendTypeContentByTypeId( commTypeBean.getCommFlag() );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCommendCache();
        }
    }

    public Long[] retrieveSiteNotUseSearchFunClassId( String siteFlag )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveSiteNotUseSearchFunClassId" );

        String key = "retrieveSiteNotUseSearchFunClassId:" + siteFlag;

        Long[] result = ( Long[] ) cache.getEntry( key );

        if( result == null )
        {
            List classIdList = channelDao.querySiteNotUseSearchFunClassId( siteFlag );

            result = ( Long[] ) classIdList.toArray( new Long[] {} );
            cache.putEntry( key, result );
        }

        return result;
    }

    public Long[] retrieveSiteNotUseRelateFunClassId( String siteFlag )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveSiteNotUseRelateFunClassId" );

        String key = "retrieveSiteNotUseRelateFunClassId:" + siteFlag;

        Long[] result = ( Long[] ) cache.getEntry( key );

        if( result == null )
        {
            List classIdList = channelDao.querySiteNotUseRelateFunClassId( siteFlag );

            result = ( Long[] ) classIdList.toArray( new Long[] {} );
            cache.putEntry( key, result );
        }

        return result;
    }

    public List retrieveSpecContentType( Long classId )
    {
        return channelDao.querySpecContentTypeBuClassId( classId );
    }

    public void updateSpecSubjectRecommendStatus( List idList, Integer status )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long classId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                classId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( classId.longValue() < 0 )
                {
                    continue;
                }

                channelDao.updateSpecSubjectRecommendStatus( classId, status );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ChannelDao.clearAllCache();
            // TODO 清除栏目树 ,实现需要改进
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }

    }

    public void updateContentClassListProduceType( Long classId, Integer produceType )
    {
        try
        {
            mysqlEngine.beginTransaction();

            channelDao.updateContentClassListProduceType( classId, produceType );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // TODO
            ChannelDao.clearAllCache();
            // TODO 清除栏目树 ,实现需要改进
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            clearContentClassCache();

            ContentService.releaseContentCache();
        }

    }

    public List retrieveRootClassBeanBySite( String siteFlag )
    {
        return channelDao.queryRootClassBeanBySite( siteFlag );
    }

    public String retrieveContentClassIdByPreLinear( String linear, String siteFlag )
    {
        if( StringUtil.isStringNull( linear ) )
        {
            return "";
        }

        return channelDao.queryContentClassIdByPreLinear( linear, siteFlag );
    }

    public void addTagTypeInfo( String typeName, String typeFlag, Long siteId )
    {
        channelDao.saveTagType( typeName, typeFlag, siteId );
    }

    public void addTagWord( Long typeId, List tagNameList, Long clickCount )
    {
        if( tagNameList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            String creator = ( String ) SecuritySessionKeeper.getSecuritySession().getAuth()
                .getApellation();

            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            String tagName = null;

            TagWord tw = null;

            for ( int i = 0; i < tagNameList.size(); i++ )
            {
                tagName = ( String ) tagNameList.get( i );

                if( StringUtil.isStringNull( tagName ) )
                {
                    continue;
                }

                tw = new TagWord();

                tw.setTagName( tagName );
                tw.setFirstChar( StringUtil.getFirstPY( tagName.toCharArray()[0] ).toString() );
                tw.setSiteId( site.getSiteId() );
                tw.setCreator( creator );
                tw.setTagTypeId( typeId );
                tw.setClickCount( clickCount );

                channelDao.saveTagWord( tw );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ChannelDao.clearTagCache();

            ContentService.releaseTagContentCache();
        }
    }

    public void editTagWord( TagWord tw )
    {
        if( tw == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            tw.setFirstChar( StringUtil.getFirstPY( tw.getTagName().toCharArray()[0] ).toString() );

            channelDao.updateTagWord( tw );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ChannelDao.clearTagCache();

            ContentService.releaseTagContentCache();
        }
    }

    public Object getTagWordListQueryTag( String typeId, String firstChar, String pn, String size )
    {
        List result = null;

        Long tId = Long.valueOf( StringUtil.getLongValue( typeId, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Page pageInfo = null;

        Long count = null;

        if( tId.longValue() < 1 )// 取所有
        {
            if( StringUtil.isStringNotNull( firstChar ) )
            {
                count = channelDao.queryTagWordCountBySiteId( site.getSiteId(), firstChar
                    .toLowerCase() );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = channelDao.queryTagWordBeanBySiteId( site.getSiteId(), firstChar, Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
            }
            else
            {
                count = channelDao.queryTagWordCountBySiteId( site.getSiteId() );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = channelDao.queryTagWordBeanBySiteId( site.getSiteId(), Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
            }
        }
        else
        {
            if( StringUtil.isStringNotNull( firstChar ) )
            {
                count = channelDao.queryTagWordCountBySiteId( site.getSiteId(), firstChar, tId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = channelDao.queryTagWordBeanBySiteId( site.getSiteId(), firstChar, tId,
                    Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
            }
            else
            {
                count = channelDao.queryTagWordCountBySiteId( site.getSiteId(), tId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = channelDao.queryTagWordBeanBySiteId( site.getSiteId(), tId, Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
            }
        }

        return new Object[] { result, pageInfo };
    }

    /**
     * 获取Tag
     * 
     * @param tagId
     * @return
     */
    public TagWordBean getSingleTagWordQueryTag( String tagId )
    {
        Long tId = Long.valueOf( StringUtil.getLongValue( tagId, -1 ) );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( tId.longValue() < 1 )
        {
            return null;
        }

        return channelDao.querySingleTagWordBeanByTagIdNoCache( tId, site.getSiteId() );
    }

    public void editTagTypeInfo( Long typeId, String typeName, String typeFlag )
    {
        try
        {
            channelDao.updateTagType( typeId, typeName, typeFlag );
        }
        finally
        {
            ChannelDao.clearTagCache();
        }
    }

    public void deleteTagTypeAllInfo( List typeIdList )
    {
        if( typeIdList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 检查所删除的推荐位是否为当前管理站点的推荐位,以防止非法删除
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            Long typeId = null;

            for ( int i = 0; i < typeIdList.size(); i++ )
            {
                if( typeIdList.get( i ) instanceof Long )
                {
                    typeId = ( Long ) typeIdList.get( i );
                }
                else
                {
                    typeId = Long.valueOf( StringUtil.getLongValue( ( String ) typeIdList.get( i ),
                        -1 ) );
                }

                if( typeId.longValue() < 0 )
                {
                    continue;
                }

                channelDao.deleteTagTypeInfoByIdAndSiteId( typeId, site.getSiteId() );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ChannelDao.clearTagCache();
        }

    }

    public void deleteTagWordAllInfo( List tagIdList )
    {
        if( tagIdList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 检查所删除的推荐位是否为当前管理站点的推荐位,以防止非法删除
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            Long tagId = null;

            for ( int i = 0; i < tagIdList.size(); i++ )
            {
                if( tagIdList.get( i ) instanceof Long )
                {
                    tagId = ( Long ) tagIdList.get( i );
                }
                else
                {
                    tagId = Long.valueOf( StringUtil.getLongValue( ( String ) tagIdList.get( i ),
                        -1 ) );
                }

                if( tagId.longValue() < 0 )
                {
                    continue;
                }

                UpdateState us = channelDao
                    .deleteTagWordInfoByIdAndSiteId( tagId, site.getSiteId() );

                if( us.getRow() > 0 )
                {
                    // 删除关联数据
                    channelDao.deleteTagRelateContentByTagId( tagId );

                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ChannelDao.clearTagCache();
        }

    }

    /**
     * Tag分类列表
     * 
     * @param commTypeId
     * @return
     */
    public List getTagTypeListQueryTag()
    {
        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

        List result = channelDao.queryTagTypeList( siteBean.getSiteId() );

        return result;
    }

    /**
     * Tag分类
     * 
     * @param commTypeId
     * @return
     */
    public Map getSingleTagTypeQueryTag( String typeId )
    {
        Map result = null;

        Long tId = Long.valueOf( StringUtil.getLongValue( typeId, -1 ) );

        if( tId.longValue() < 1 )
        {
            return result;
        }

        result = channelDao.querySingleTagType( tId );

        return result;
    }

    /**
     * Tag辅助信息
     * 
     * @param ids
     * @return
     */
    public String getInfoTagKeyStrQueryTag( String ids )
    {
        if( ids == null )
        {
            return null;
        }

        List idList = StringUtil.changeStringToList( ids, "\\*" );

        Long tagId = null;

        StringBuffer buf = new StringBuffer();

        TagWordBean twb = null;

        for ( int j = idList.size() - 1; j >= 0; j-- )
        {
            if( idList.get( j ) instanceof String )
            {
                tagId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( j ), -1 ) );
            }
            else
            {
                tagId = ( Long ) idList.get( j );
            }

            twb = channelDao.querySingleTagBeanByTagId( tagId );

            if( twb != null )
            {
                buf.append( "[" + twb.getTagName() + "]  " );
            }
        }

        return buf.toString();
    }

    /**
     * 获取编辑器组件
     * 
     * @return
     */
    public Object getEditorModuleTag( String type )
    {
        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

        if( "".equals( type ) )
        {
            return channelDao.queryEditorModuleInfoListBySiteId( siteBean.getSiteId() );
        }
        else
        {
            return channelDao.querySingleEditorModuleInfoByType( Integer.valueOf( StringUtil
                .getIntValue( type, -1 ) ), siteBean.getSiteId() );
        }

    }

    public Map retrieveTagTypeByFlag( String typeFlag )
    {
        return channelDao.querySingleTagType( typeFlag );
    }

    public TagWordBean retrieveSingleTagWordBeanByTagId( Long tagId )
    {
        return channelDao.querySingleTagBeanByTagId( tagId );
    }

    public TagWordBean retrieveSingleTagWordBeanByTagName( String tagName )
    {
        return channelDao.querySingleTagBeanByTagName( tagName );
    }

    public Long retrieveTagWordCountBySiteId( Long siteId, String fc )
    {
        return channelDao.queryTagWordCountBySiteId( siteId, fc );
    }

    public Long retrieveTagWordCountBySiteId( Long siteId )
    {
        return channelDao.queryTagWordCountBySiteId( siteId );
    }

    public List retrieveTagWordBeanBySiteId( Long siteId, String fc, Long pos, Integer size )
    {
        return channelDao.queryTagWordBeanBySiteId( siteId, fc, pos, size );
    }

    public List retrieveTagWordBeanBySiteId( Long siteId, String fc, Long pos, Integer size,
        String order )
    {
        String of = "tagId";

        String op = "desc";

        String[] temp = StringUtil.split( order, "-" );

        if( temp.length == 2 )
        {
            if( "click".equals( temp[0] ) )
            {
                of = "clickCount";
            }

            if( "up".equals( temp[1] ) )
            {
                op = "asc";
            }
        }

        return channelDao.queryTagWordBeanBySiteId( siteId, fc, pos, size, of, op );
    }

    public List retrieveTagWordBeanBySiteId( Long siteId, Long pos, Integer size )
    {
        return channelDao.queryTagWordBeanBySiteId( siteId, pos, size );
    }

    public List retrieveTagWordBeanBySiteId( Long siteId, Long pos, Integer size, String order )
    {
        String of = "tagId";

        String op = "desc";

        String[] temp = StringUtil.split( order, "-" );

        if( temp.length == 2 )
        {
            if( "click".equals( temp[0] ) )
            {
                of = "clickCount";
            }

            if( "up".equals( temp[1] ) )
            {
                op = "asc";
            }
        }

        return channelDao.queryTagWordBeanBySiteId( siteId, pos, size, of, op );
    }

    public Long retrieveTagWordCountBySiteId( Long siteId, String fc, Long typeId )
    {
        return channelDao.queryTagWordCountBySiteId( siteId, fc, typeId );
    }

    public List queryTagWordBeanBySiteId( Long siteId, String fc, Long typeId, Long pos,
        Integer size )
    {
        return channelDao.queryTagWordBeanBySiteId( siteId, fc, typeId, pos, size );
    }

    public List retrieveTagWordBeanBySiteId( Long siteId, String fc, Long typeId, Long pos,
        Integer size, String order )
    {
        String of = "tagId";

        String op = "desc";

        String[] temp = StringUtil.split( order, "-" );

        if( temp.length == 2 )
        {
            if( "click".equals( temp[0] ) )
            {
                of = "clickCount";
            }

            if( "up".equals( temp[1] ) )
            {
                op = "asc";
            }
        }

        return channelDao.queryTagWordBeanBySiteId( siteId, fc, typeId, pos, size, of, op );
    }

    public Long retrieveTagWordCountBySiteId( Long siteId, Long typeId )
    {
        return channelDao.queryTagWordCountBySiteId( siteId, typeId );
    }

    public List queryTagWordBeanBySiteId( Long siteId, Long typeId, Long pos, Integer size )
    {
        return channelDao.queryTagWordBeanBySiteId( siteId, typeId, pos, size );
    }

    public List retrieveTagWordBeanBySiteId( Long siteId, Long typeId, Long pos, Integer size,
        String order )
    {
        String of = "tagId";

        String op = "desc";

        String[] temp = StringUtil.split( order, "-" );

        if( temp.length == 2 )
        {
            if( "click".equals( temp[0] ) )
            {
                of = "clickCount";
            }

            if( "up".equals( temp[1] ) )
            {
                op = "asc";
            }
        }

        return channelDao.queryTagWordBeanBySiteId( siteId, typeId, pos, size, of, op );
    }

    public void deleteTagRelateContentByContentId( Long cid )
    {
        channelDao.deleteTagRelateContentByContentId( cid );
    }

    public void addTagWordRelateContent( Long tagId, Long cid )
    {
        channelDao.saveTagWordRelateContent( tagId, cid );
    }

    public void updateTagWordRelateContentCount( Long tagId )
    {
        channelDao.updateTagWordRelateContentCount( tagId );
    }

    public void editSiteEditorModule( Long emId, String code, String emDesc )
    {
        channelDao.updateSiteEditorModule( emId, code, emDesc );
    }

    public void resumeSiteEditorModule( Long siteId, String type )
    {
        if( "video".equals( type ) )
        {
            Map em = channelDao.querySingleEditorModuleInfoByType( Integer.valueOf( 1 ), siteId );

            if( em.isEmpty() )
            {
                channelDao.saveEditorInfo( siteId, "视频组件", Integer.valueOf( 1 ), video, videoDesc );
            }
            else
            {
                channelDao.updateSiteEditorModuleByTypeAndSite( siteId, Integer.valueOf( 1 ),
                    video, videoDesc );
            }
        }
        else if( "image".equals( type ) )
        {
            Map em = channelDao.querySingleEditorModuleInfoByType( Integer.valueOf( 2 ), siteId );

            if( em.isEmpty() )
            {
                channelDao.saveEditorInfo( siteId, "图片组件", Integer.valueOf( 2 ), image, imageDesc );
            }
            else
            {
                channelDao.updateSiteEditorModuleByTypeAndSite( siteId, Integer.valueOf( 2 ),
                    image, imageDesc );
            }
        }
        else if( "file".equals( type ) )
        {
            Map em = channelDao.querySingleEditorModuleInfoByType( Integer.valueOf( 3 ), siteId );

            if( em.isEmpty() )
            {
                channelDao.saveEditorInfo( siteId, "附件组件", Integer.valueOf( 3 ), file, fileDesc );
            }
            else
            {
                channelDao.updateSiteEditorModuleByTypeAndSite( siteId, Integer.valueOf( 3 ), file,
                    fileDesc );
            }
        }
    }

    public String loadEditorModuleCode( Map param )
    {
        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

        Integer emType = Integer.valueOf( StringUtil.getIntValue( ( String ) param.get( "emType" ),
            -1 ) );

        Map em = channelDao.querySingleEditorModuleInfoByType( emType, siteBean.getSiteId() );

        String code = ( String ) em.get( "code" );

        String resRoot = null;

        if( Constant.SITE_CHANNEL.EDITOR_MEDIA.equals( emType ) )
        {
            resRoot = siteBean.getSiteMediaPrefixUrl();
        }
        else if( Constant.SITE_CHANNEL.EDITOR_IMAGE.equals( emType ) )
        {
            resRoot = siteBean.getSiteImagePrefixUrl();
        }
        else if( Constant.SITE_CHANNEL.EDITOR_FILE.equals( emType ) )
        {
            resRoot = siteBean.getSiteFilePrefixUrl();
        }

        String fullPath = resRoot + param.get( "path" );

        String resId = ( String ) param.get( "resId" );

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
            .valueOf( StringUtil.getLongValue( resId, -1 ) ) );

        String ew = ( String ) param.get( "w" );

        String eh = ( String ) param.get( "h" );

        // 以下视频
        String as = ( String ) param.get( "as" );

        String rp = ( String ) param.get( "rp" );

        String fs = ( String ) param.get( "fs" );

        String autoStartFlag = "@{autostart}";

        if( code.indexOf( "@{autostart}[" ) != -1 )
        {
            String autoStartFlagVar = StringUtil.subString( code, code.indexOf( "@{autostart}[" )
                + "@{autostart}[".length(), code.indexOf( "]", code.indexOf( "@{autostart}[" ) ) );

            String[] tf = StringUtil.split( autoStartFlagVar, "," );

            autoStartFlag = autoStartFlag + "[" + autoStartFlagVar + "]";

            if( "true".equals( as ) )
            {
                as = ( tf.length == 1 ? tf[0] : tf[0] );
            }
            else if( "false".equals( as ) )
            {
                as = ( tf.length == 1 ? "" : tf[1] );
            }
        }

        String fsFlag = "@{fs}";

        if( code.indexOf( "@{fs}[" ) != -1 )
        {
            String fsFlagVar = StringUtil.subString( code, code.indexOf( "@{fs}[" )
                + "@{autostart}[".length(), code.indexOf( "]", code.indexOf( "@{fs}[" ) ) );

            String[] tf = StringUtil.split( fsFlagVar, "," );

            fsFlag = fsFlag + "[" + fsFlagVar + "]";

            if( "true".equals( fs ) )
            {
                fs = ( tf.length == 1 ? tf[0] : tf[0] );
            }
            else if( "false".equals( fs ) )
            {
                fs = ( tf.length == 1 ? "" : tf[1] );
            }
        }

        String rpFlag = "@{repeat}";

        if( code.indexOf( "@{repeat}[" ) != -1 )
        {
            String rpFlagVar = StringUtil.subString( code, code.indexOf( "@{repeat}[" )
                + "@{repeat}[".length(), code.indexOf( "]", code.indexOf( "@{repeat}[" ) ) );

            String[] tf = StringUtil.split( rpFlagVar, "," );

            rpFlag = rpFlag + "[" + rpFlagVar + "]";

            if( "true".equals( rp ) )
            {
                rp = ( tf.length == 1 ? tf[0] : tf[0] );
            }
            else if( "false".equals( rp ) )
            {
                rp = ( tf.length == 1 ? "" : tf[1] );
            }
        }

        String cv = "";

        if( StringUtil.isStringNotNull( resBean.getCover() ) )
        {
            cv = siteBean.getSiteImagePrefixUrl() + resBean.getCover();
        }

        // 以下图片以及文件

        String name = resBean != null ? resBean.getResName() + "." + resBean.getFileType() : "";

        code = StringUtil.replaceString( code, "@{basePath}", siteBean.getHostMainUrl(), false,
            false );

        String siteUrl = siteBean.getSiteUrl();

        if( siteUrl.endsWith( "/" + siteBean.getSiteRoot() ) )
        {
            siteUrl = StringUtil.subString( siteUrl, 0, siteUrl.length()
                - siteBean.getSiteRoot().length() );
        }

        code = StringUtil.replaceString( code, "@{siteUrl}", siteUrl, false, false );

        code = StringUtil.replaceString( code, "@{resPath}", siteBean.getSiteTemplateUrl(), false,
            false );

        code = StringUtil.replaceString( code, "@{rootDir}", siteBean.getSiteRoot(), false, false );

        code = StringUtil.replaceString( code, "@{resId}", resId, false, false );

        code = StringUtil.replaceString( code, "@{fileFullPath}", fullPath, false, false );

        code = StringUtil.replaceString( code, "@{width}", ew, false, false );

        code = StringUtil.replaceString( code, "@{height}", eh, false, false );

        code = StringUtil.replaceString( code, autoStartFlag, as, false, false );

        code = StringUtil.replaceString( code, rpFlag, rp, false, false );

        code = StringUtil.replaceString( code, "@{cover}", cv, false, false );

        code = StringUtil.replaceString( code, fsFlag, fs, false, false );

        code = StringUtil.replaceString( code, "@{name}", name, false, false );

        return code;

    }

    public Map retrieveSingleEditorModuleInfoByType( Integer typeId, Long siteId )
    {
        return channelDao.querySingleEditorModuleInfoByType( typeId, siteId );
    }

    public void addNewEditorAllModuleCode( Long siteId )
    {
        channelDao.saveEditorInfo( siteId, "视频组件", Integer.valueOf( 1 ), video, videoDesc );

        channelDao.saveEditorInfo( siteId, "图片组件", Integer.valueOf( 2 ), image, imageDesc );

        channelDao.saveEditorInfo( siteId, "附件组件", Integer.valueOf( 3 ), file, fileDesc );
    }

    // ///////////////////////////////Image
    // ratio/////////////////////////////////

    /**
     * 获取图片尺寸
     * 
     * @return
     */
    public Object getImageRadioTag( String id )
    {
        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

        if( "all".equals( id ) )
        {
            return channelDao.queryImageratioBySiteId( siteBean.getSiteId() );
        }
        else
        {
            return channelDao.querySingleImageratioBySiteId( Long.valueOf( StringUtil.getLongValue(
                id, -1 ) ) );
        }

    }

    public void addNewImageratio( String ratioName, Integer rw, Integer rh )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        channelDao.saveImageratio( ratioName, rw, rh, site.getSiteId() );
    }

    public void editImageratio( Long irId, String ratioName, Integer rw, Integer rh )
    {
        channelDao.updateImageratio( irId, ratioName, rw, rh );
    }

    public void deleteImageratio( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long irId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof Long )
                {
                    irId = ( Long ) idList.get( i );
                }
                else
                {
                    irId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );
                }

                if( irId.longValue() < 0 )
                {
                    continue;
                }

                channelDao.deleteImageratio( irId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }
    }

    /**
     * 获取自动关联内容栏目
     * 
     * @param contentId
     * @return
     */
    public List retriveLinkClassList( Long classId, Long modelId )
    {
        List result = channelDao.queryClassListByLink( classId, modelId );

        return result;
    }
    
    public List getRelateClassListForTag( String classId )
    {
        ContentClassBean classBean = retrieveSingleClassBeanInfoByClassId( StringUtil.getLongValue( classId, -1 ) );
        
        if(classBean == null || classBean.getClassId() < 0 )
        {
            return null;
        }
        
        List result = StringUtil.changeStringToList( classBean.getRelateClassIds(), ",");

        return result;
    }
    
    

    public static void clearContentClassCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "conetentClassByParentClassName" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "singleConetentClassByClassName" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveSingleClassBeanInfoByClassId" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveSingleClassBeanInfoByClassFlag" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveSiteNotUseSearchFunClassId" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveSiteNotUseRelateFunClassId" );
        cache.clearAllEntry();

        
    }

    

    public static void clearCommendCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveAllContentCommendTypeBean" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveSingleContentCommendTypeBeanByTypeId" );
        cache.clearAllEntry();

        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
 
    }

    

    public static void clearContentTypeCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveAllContentTypeBean" );
        cache.clearAllEntry();

      
    }

     

    public static void clearPublishCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveClassPublishPageAssistant" );
        cache.clearAllEntry();
 
    }

    

}

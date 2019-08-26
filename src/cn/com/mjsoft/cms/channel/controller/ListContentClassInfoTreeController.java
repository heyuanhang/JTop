package cn.com.mjsoft.cms.channel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.channel.bean.ContentClassTreeItemBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/channel" )
public class ListContentClassInfoTreeController
{
    public static DataSource ds = new MySqlDataSource();

    private static PersistenceEngine pe = new PersistenceEngine( ds );

    private static final boolean showMode = false;

    public static Map cacheManager = new HashMap();
    static
    {
        cacheManager.put( "contentClassInfo", new ClusterCacheAdapter( 1000,
            "listContentClassInfoTreeFlow.contentClassInfo" ) );
        cacheManager.put( "treeItemInfo", new ClusterCacheAdapter( 2500,
            "listContentClassInfoTreeFlow.treeItemInfo" ) );

    }

    private static ChannelService channelService = ChannelService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/listContentClassInfoTree.do", method = { RequestMethod.POST } )
    public Object listContentClassInfoTree( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map paramsMap = ServletUtil.getRequestInfo( request );

        long siteId = StringUtil.getLongValue( ( String ) paramsMap.get( "siteId" ), -1 );

        SiteGroupBean siteBean = null;

        if( siteId > 0 )
        {
            siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( Long.valueOf( siteId ) );
        }

        if( siteBean == null )
        {
            siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();
        }

        String trashMode = ( String ) paramsMap.get( "trash" );

        String mode = ( String ) paramsMap.get( "mode" );

        int isSpecial = 0;

        if( "special".equals( mode ) )
        {
            isSpecial = 1;
        }

        Long parent = StringUtil.getLongValue( ( String ) paramsMap.get( "parentClassId" ), -1 );

        Integer layer = StringUtil.getIntValue( ( String ) paramsMap.get( "layer" ), 1 );

        // 根据需要获取栏目
        String sqlRootMode = "select *  from (select * from contentclass cc left join model mo on cc.contentType=mo.dataModelId where layer<=? and siteFlag=? and isSpecial=? and classType<5 order by linearOrderFlag asc ) res left join model_icon_config mic on res.dataModelId=mic.modelId order by linearOrderFlag asc";

        String sqlChildMode = "select *  from (select * from contentclass cc left join model mo on cc.contentType=mo.dataModelId where parent=? and siteFlag=? and isSpecial=? and classType<5 order by linearOrderFlag asc ) res left join model_icon_config mic on res.dataModelId=mic.modelId order by linearOrderFlag asc";

        String key = "listClass:" + parent + "|" + siteBean.getSiteId() + "|" + layer + "|"
            + isSpecial + "|" + trashMode + "|" + mode;

        Cache ccCache = ( Cache ) cacheManager.get( "contentClassInfo" );

        List mapList = ( List ) ccCache.getEntry( key );

        if( mapList == null )
        // if( true )
        {
            try
            {
                pe.beginTransaction();

                String sql = "";

                if( parent.longValue() == -9999 )
                {
                    // 根节点模式
                    sql = sqlRootMode;
                    mapList = pe.queryResultMap( sql, new Object[] { layer, siteBean.getSiteFlag(),
                        Integer.valueOf( isSpecial ) } );
                }
                else
                {
                    // 子节点模式
                    sql = sqlChildMode;
                    mapList = pe.queryResultMap( sql, new Object[] { parent,
                        siteBean.getSiteFlag(), Integer.valueOf( isSpecial ) } );
                }

                pe.commit();
            }
            finally
            {
                pe.endTransaction();
            }

            ccCache.putEntry( key, mapList );
        }

        // 设置当前站的栏目是否在权限授权中

        // 去除整个子树都不存在的栏目树,首先要获取那些整个子树都不存在的根栏目

        Role[] rs = SecuritySessionKeeper.getSecuritySession().getAuth().getUserRoleCopy();

        StringBuffer buf = new StringBuffer( "and roleId in (" );

        boolean haveRole = false;

        for ( int i = 0; i < rs.length; i++ )
        {
            if( i + 1 != rs.length )
            {
                buf.append( ( ( Role ) rs[i] ).getRoleID() + ", " );
            }
            else
            {
                buf.append( ( ( Role ) rs[i] ).getRoleID() );
            }

            haveRole = true;
        }

        buf.append( ")" );

        Cache itemCache = ( Cache ) cacheManager.get( "treeItemInfo" );

        String itemKey = "treeiItem:" + parent + "|" + siteBean.getSiteId() + "|" + layer + "|"
            + isSpecial + "|" + trashMode + "|" + mode + "|" + buf;

        Map hMap = ( Map ) itemCache.getEntry( itemKey );

        if( hMap == null )
        // if( true )
        {
            String sqlHaveRoleClassId = "select DISTINCT accId from role_relate_resource_acc rra where rra.siteId=? and secResId=(select secResId from securityresource where sysFlag='manage_content_jsp') "
                + ( haveRole ? buf.toString() : "" );

            String sqlHaveRoleSpecClassId = "select DISTINCT accId from role_relate_resource_acc rra where rra.siteId=? and secResId=(select secResId from securityresource where sysFlag='manage_spec_jsp') "
                + ( haveRole ? buf.toString() : "" );

            String sqlHaveRoleChannelClassId = "select DISTINCT accId from role_relate_resource_acc rra where rra.siteId=? and secResId=(select secResId from securityresource where sysFlag='edit_content_class_jsp') "
                + ( haveRole ? buf.toString() : "" );

            String sqlHaveRole = sqlHaveRoleClassId;

            if( "special".equals( mode ) )
            {
                sqlHaveRole = sqlHaveRoleSpecClassId;
            }
            else if( "channel".equals( mode ) )
            {
                sqlHaveRole = sqlHaveRoleChannelClassId;
            }

            String sqlLinearRoot = "select linearOrderFlag from contentclass where isSpecial=? and parent=-9999 and siteFlag=?";

            List allRootClassLinear = pe.querySingleCloumn( sqlLinearRoot, new Object[] {
                Integer.valueOf( isSpecial ), siteBean.getSiteFlag() }, String.class );

            Integer count = null;

            List needDeleteRootLinearList = new ArrayList();

            for ( int i = 0; i < allRootClassLinear.size(); i++ )
            {
                String sqlIsAllTreeNotInAcc = "select count(*) from contentclass where linearOrderFlag like '"
                    + allRootClassLinear.get( i ) + "%' and classId in (" + sqlHaveRole + ")";

                count = ( Integer ) pe.querySingleObject( sqlIsAllTreeNotInAcc,
                    new Object[] { siteBean.getSiteId() }, Integer.class );

                if( count.intValue() < 1 )
                {
                    needDeleteRootLinearList.add( allRootClassLinear.get( i ) );
                }
            }

            Set roleClassIdSet = new HashSet( pe.querySingleCloumn( sqlHaveRole,
                new Object[] { siteBean.getSiteId() }, Long.class ) );

            List endClassTree = new ArrayList();

            Map classMap = null;

            String linearFlag = null;

            for ( int i = 0; i < mapList.size(); i++ )
            {
                boolean show = true;

                classMap = ( Map ) mapList.get( i );

                linearFlag = ( String ) classMap.get( "linearOrderFlag" );

                for ( int j = 0; j < needDeleteRootLinearList.size(); j++ )
                {
                    if( linearFlag.startsWith( ( String ) needDeleteRootLinearList.get( j ) ) )
                    {
                        show = false;
                        break;
                    }
                }

                if( show )
                {
                    endClassTree.add( classMap );

                    if( roleClassIdSet.contains( classMap.get( "classId" ) ) )
                    {
                        classMap.put( "accIn", Boolean.TRUE );
                    }
                    else
                    {
                        classMap.put( "accIn", Boolean.FALSE );
                    }
                }

            }

            if( !showMode )
            {
                mapList = endClassTree;
            }

            hMap = new TreeMap();
            ContentClassTreeItemBean item = null;

            String targetListPage = "";
            Integer classType = null;
            boolean haveChildSpecClass = false;

            String base = JtRuntime.cmsServer.getDomainFullPath();

            // String base = config.getSystemBasePath();
            for ( int i = 0; i < mapList.size(); i++ )
            {
                classMap = ( Map ) mapList.get( i );
                targetListPage = "";
                // tree = root.addElement("tree");
                // tree.addAttribute("text",(String) m.get("channelName") );

                classType = ( Integer ) classMap.get( "classType" );

                if( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( classType ) )
                {
                    // 是否有专题分类,若没有,直接为叶子节点
                    haveChildSpecClass = channelService.checkIsSpecialClassHaveChildClass(
                        ( Long ) classMap.get( "classId" ),
                        Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL );
                    targetListPage = base + "/core/content/special/ManageSpecialSubject.jsp";
                }
                else
                {
                    if( "true".equals( trashMode ) )
                    {
                        targetListPage = base + "/core/content/ManageTrashContent.jsp";
                    }
                    else
                    {
                        // 单页业务改进,统一使用内容管理页
                        // if(
                        // Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals(
                        // classType ) )
                        // {
                        // Long singleCid = ( Long ) classMap.get(
                        // "singleContentId" );
                        //
                        // if( singleCid == null || singleCid.longValue() < 0 )
                        // {
                        // // 不存在当前单页，需要增加
                        // targetListPage = base
                        // + "/core/content/UserDefineModelGeneralAdd.jsp";
                        //
                        // }
                        // else
                        // {
                        // targetListPage = base
                        // + "/core/content/EditUserDefineModelContent.jsp";
                        // }
                        // }
                        // else
                        {
                            targetListPage = base + "/core/content/ManageGeneralContent.jsp";
                        }
                    }
                }

                item = new ContentClassTreeItemBean( ( Integer ) classMap.get( "isSpecial" ),
                    ( Long ) classMap.get( "parent" ), ( String ) classMap.get( "className" ),
                    ( String ) classMap.get( "linearOrderFlag" ), ( Integer ) classMap
                        .get( "layer" ), ( Integer ) classMap.get( "isLeaf" ), ( Long ) classMap
                        .get( "classId" ), ( Long ) classMap.get( "dataModelId" ), targetListPage,
                    ( Long ) classMap.get( "contentType" ), ( Long ) classMap
                        .get( "singleContentId" ), StringUtil.isStringNull( ( String ) classMap
                        .get( "iconFile" ) ) ? "channel_default.png" : ( String ) classMap
                        .get( "iconFile" ), ( String ) classMap.get( "ico" ) );

                if( !haveChildSpecClass
                    && Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( classType ) )
                {
                    item.setIsLeaf( Constant.COMMON.ON );
                }

                item.setClassType( ( Integer ) classMap.get( "classType" ) );
                item.setSiteId( ( Long ) classMap.get( "siteId" ) );
                item.setIsRecommend( ( Integer ) classMap.get( "isRecommend" ) );

                if( !showMode )
                {
                    item.setHaveRole( ( Boolean ) classMap.get( "accIn" ) );
                }
                else
                {
                    item.setHaveRole( Boolean.TRUE );
                }

                hMap.put( Integer.valueOf( ( i + 1 ) ), item );
            }
            itemCache.putEntry( itemKey, hMap );
        }

        return hMap;
    }

    public static void resizeSiteContentClassCache()
    {
        ( ( Cache ) cacheManager.get( "contentClassInfo" ) ).clearAllEntry();
        ( ( Cache ) cacheManager.get( "treeItemInfo" ) ).clearAllEntry();

    }

}

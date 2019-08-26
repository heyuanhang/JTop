package cn.com.mjsoft.cms.publish.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.controller.ListCommendTypeInfoTreeController;
import cn.com.mjsoft.cms.channel.controller.ListContentClassInfoTreeController;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
 
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.publish.bean.PublishPageAssistantBean;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.dao.PublishDao;
import cn.com.mjsoft.cms.publish.dao.vo.PublishRule;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class PublishService
{
    private static Logger log = Logger.getLogger( PublishService.class );

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private static PublishService service = null;

    private static ChannelService channelService = ChannelService.getInstance();

    private PublishDao publishDao = null;

    private PublishService()
    {
        publishDao = new PublishDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new PublishService();
        }
    }

    public static PublishService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void addNewPublishRule( PublishRule rule )
    {
        try
        {
            publishDao.save( rule );
        }
        finally
        {
            PublishDao.clearCache();
        }
    }

    public void editNewPublishRule( PublishRule rule )
    {
        if( rule == null )
        {
            return;
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            if( rule.getSiteId().equals( site.getSiteId() ) )
            {
                publishDao.updatePublishRule( rule );
            }
        }
        finally
        {
            PublishDao.clearCache();
        }

    }

    public PublishRuleBean retrieveSinglePublishRuleBean( Long ruleId )
    {
        return publishDao.querySinglePublishRuleBeanByRuleId( ruleId );
    }

    public List retrievePublishRuleBeanByType( Integer type, Long siteId )
    {
        if( type != null && type.intValue() == -9999 )
        {
            return publishDao.queryAllPublishRuleBean( siteId );
        }

        return publishDao.queryPublishRuleBeanByType( type, siteId );
    }

    public List retrievePublishRuleBeanByType( Integer type )
    {
        if( type != null && type.intValue() == -9999 )
        {
            return publishDao.queryAllPublishRuleBean();
        }

        return publishDao.queryPublishRuleBeanByType( type );
    }

    public void deletePublishRuleByIds( List idList )
    {
        if( idList == null )
        {
            return;
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            Long ruleId = null;

            PublishRuleBean rule = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                ruleId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( ruleId.longValue() < 0 )
                {
                    continue;
                }

                rule = publishDao.querySinglePublishRuleBeanByRuleId( ruleId );

                Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

                // 2015-5:解决已删除外站规则，所有总机构管理员可删除

                if( rule.getSiteId().equals( site.getSiteId() ) || "001".equals( auth.getOrgCode() ) )
                {

                    publishDao.deletePublishRuleById( ruleId );

                }

                // 删除全系统栏目引用

                if( Constant.SITE_CHANNEL.STATIC_RULE_LIST.equals( rule.getType().toString() ) )
                {
                    publishDao.updateClassTemplateUrlPublishRuleIdForAllClass( Long.valueOf( -1 ),
                        ruleId );
                }
                else if( Constant.SITE_CHANNEL.STATIC_RULE_CHANNEL.equals( rule.getType()
                    .toString() ) )
                {
                    publishDao.updateClassHomeTemplateUrlPublishRuleIdForAllClass( Long
                        .valueOf( -1 ), ruleId );
                }
                else if( Constant.SITE_CHANNEL.STATIC_RULE_CONTENT.equals( rule.getType()
                    .toString() ) )
                {
                    publishDao.updateContentTemplateUrlPublishRuleIdForAllClass(
                        Long.valueOf( -1 ), ruleId );
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            PublishDao.clearCache();

            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();

            ContentService.releaseContentCache();
        }

    }

    /**
     * * 标签所用发布业务 * 1.nextQuery:页面分页,上一页 <br>
     * 2.prevQuery:页面分页,下一页 <br>
     * 3.nextQueryActionUrl:系统内部静态化操作所使用下一页地址 <br>
     * 4.nextStaticPage prevStaticPage:由系统传入静态分页地址,此地址为生成上下一页的标准
     * 
     * @param pageContext 标签对象
     * @param site 站点对象
     * @param classId 栏目ID
     * @param classBean 栏目对象
     * @param targetClassId 最终发布规则栏目ID,默认为传入的classId,若为多classId模式,此Id为独立指定
     * @param pageInfo 分页对象
     * @param page 分页指令
     * @param queryCod 分页参数
     */
    public void htmlTagPage( PageContext pageContext, SiteGroupBean site, Long classId,
        ContentClassBean classBean, Long targetClassId, Page pageInfo, String page, String queryCod )
    {

        /**
         * 发布逻辑<br>
         * 1.nextQuery:页面分页,上一页 <br>
         * 2.prevQuery:页面分页,下一页 <br>
         * 3.nextQueryActionUrl:系统内部静态化操作所使用下一页地址 <br>
         * 4.nextStaticPage prevStaticPage:由系统传入静态分页地址,此地址为生成上下一页的标准
         */
        HttpServletRequest request = ( HttpServletRequest ) pageContext.getRequest();

        boolean pageMode = false;

        // 分页模式
        if( "true".equals( page ) )
        {
            // 注意:当不处于pageMode时,发布行为的任何参数都不需要传递
            pageMode = true;
        }
        

        if( pageInfo == null )
        {
            pageInfo = new Page( 1, 0, 0 );
        }

        // site
        if( site == null )
        {

            site = ( SiteGroupBean ) request.getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

            if( site == null )
            {
                site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
            }
        }
        
        if( site == null )
        {
            return;
        }

        // 和管理不同,url的组装需要走模板url规则
        // 移动
        String tpl = classBean.getClassTemplateUrl();

        if( StringUtil.isStringNull( tpl ) )
        {
            tpl = classBean.getMobClassTemplateUrl();
        }

        if( StringUtil.isStringNull( tpl ) )
        {
            tpl = classBean.getPadClassTemplateUrl();
        }

        String url = site.getSiteUrl()

            + StringUtil.replaceString( classBean.getClassTemplateUrl(), "{class-id}", Long
                .valueOf( targetClassId ).toString(), false, false );

        String prefixQuery = "?";
        if( StringUtil.isStringNotNull( classBean.getClassTemplateUrl() )
            && classBean.getClassTemplateUrl().indexOf( "?" ) != -1 )
        {
            prefixQuery = "&";
        }

        String nextQuery = null;
        String prevQuery = null;
        String publishNextQueryChain = null;

        String headQuery = null;
        String endQuery = null;
        String jumpQuery = null;

        /**
         * 所有的分页句只记录分页线索,不记录排序,过滤条件,这些条件由参数给出,若需要,由用户自行加入 201828:现URL参数带所有排序过滤条件
         */

        nextQuery = new StringBuffer( url ).append( prefixQuery )

        .append( queryCod ).append( "&pn=" + ( pageInfo.getCurrentPage() + 1 ) ).toString();

        prevQuery = new StringBuffer( url.toString() ).append( prefixQuery )

        .append( queryCod ).append( "&pn=" + ( pageInfo.getCurrentPage() - 1 ) ).toString();

        headQuery = new StringBuffer( url.toString() ).append( prefixQuery )

        .append( queryCod ).append( "&pn=1" ).toString();

        endQuery = new StringBuffer( url.toString() ).append( prefixQuery )

        .append( queryCod ).append( "&pn=" + pageInfo.getPageCount() ).toString();

        jumpQuery = new StringBuffer( url.toString() ).append( prefixQuery ).append( queryCod )
            .toString();

        // 201828:改动分页规则,增加分页参数入标签
        pageInfo.setQueryCod( queryCod );

        // 获取栏目发布规则
        PublishRuleBean ruleBean = retrieveSinglePublishRuleBean( classBean.getClassPublishRuleId() );

        pageInfo.setNextQuery( nextQuery );
        pageInfo.setPrevQuery( prevQuery );
        pageInfo.setHeadQuery( headQuery );
        pageInfo.setEndQuery( endQuery );
        pageInfo.setJumpQuery( jumpQuery );
        pageInfo.setEndPos( classBean.getListPageLimit() );

        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean.getClassProduceType() ) )
        {
            pageInfo.setJumpStatic( site.getSiteUrl()
                + ( ( ruleBean == null ) ? site.getSiteUrl() : ruleBean
                    .getFullContentClassPagePublishPath( site, classBean, null, null, Integer
                        .valueOf( -99999 ) )[1] ) );
        }

        log.info( "[Page] 分页对象为:" + pageInfo );

        if( pageMode )
        {
            // 页面使用分页对象
            pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );
        }

        boolean mob = request.getAttribute( "_pub_mob_" ) != null ? ( Boolean ) request
            .getAttribute( "_pub_mob_" ) : false;

        boolean pad = request.getAttribute( "_pub_pad_" ) != null ? ( Boolean ) request
            .getAttribute( "_pub_pad_" ) : false;

        boolean fromSystemAction = false;
        if( Boolean.TRUE.equals( request.getAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG ) ) )
        {
            fromSystemAction = true;

            if( pageMode )
            {
                request.setAttribute( "___system_dispose_page_object_for_pub___", pageInfo );
            }
        }

        if( fromSystemAction && request.getAttribute( "needPage" ) != null )
        {
            String siteTemplate = Constant.CONTENT.TEMPLATE_BASE + Constant.CONTENT.URL_SEP;

            // 设定上一页地址,上一页在分页流程中必定为静态地址

            pageInfo.setPrevQuery( site.getSiteUrl() + request.getAttribute( "prevStaticPage" ) );

            // 设定下一次访问标志,当达到分页标志位的时候,不再进行静态化操作
            int genPageSize = ( ( Integer ) request.getAttribute( "genPageSize" ) ).intValue();

            if( pageInfo.getCurrentPage() == pageInfo.getPageCount()
                || pageInfo.getPageCount() == 0 || pageInfo.getCurrentPage() == genPageSize )
            {
                // 静态化最后一页,nextQueryActionUrl将为空
                if( pageMode )
                {
                    request.setAttribute( "nextQueryActionUrl", null );
                }

                if( genPageSize >= pageInfo.getPageCount() )
                {
                    if( pageInfo.getPageCount() == 1 || pageInfo.getPageCount() == 0 )
                    {
                        // 只有一页
                        pageInfo.setNextQuery( site.getSiteUrl()
                            + request.getAttribute( "nextStaticPageOnlyOne" ) );
                    }

                    else
                    {
                        // 下一页不存在的情况,最后一页即为下一页
                        pageInfo.setNextQuery( site.getSiteUrl()
                            + request.getAttribute( "nextStaticPage" ) );
                    }
                }
                else
                {
                    // 下一页将为动态,注意不合法url
                    if( url.indexOf( ".jsp" ) != -1 || url.indexOf( ".thtml" ) != -1 )
                    {
                        nextQuery = new StringBuffer( url ).append( prefixQuery ).append( queryCod )
                            .append( "&pn=" + ( pageInfo.getCurrentPage() ) ).toString();
                    }

                    pageInfo.setNextQuery( nextQuery );

                }

            }
            else
            {
                // 下一页仍然是静态化系统动作

                // 必须变化为内部访问地址

                String pubListUrl = classBean.getClassTemplateUrl();

                if( mob )
                {
                    pubListUrl = classBean.getMobClassTemplateUrl();
                }
                else if( pad )
                {
                    pubListUrl = classBean.getPadClassTemplateUrl();
                }

                url = site.getSiteRoot()
                    + Constant.CONTENT.URL_SEP
                    + siteTemplate
                    + StringUtil.replaceString( pubListUrl, "{class-id}", Long.valueOf(
                        targetClassId ).toString(), false, false );

                // 获取系统内部访问URL,过滤参数全

                prefixQuery = "?";
                if( url.indexOf( ".jsp?" ) != -1 )
                {
                    prefixQuery = "&";
                }

                publishNextQueryChain = new StringBuffer( Constant.CONTENT.URL_SEP + url ).append(
                    prefixQuery ).append( queryCod ).append(
                    "&pn=" + ( pageInfo.getCurrentPage() + 1 ) ).toString();

                // 将nextQueryActionUrl传递给发布逻辑,继续处理分页逻辑
                if( pageMode )
                {
                    request.setAttribute( "nextQueryActionUrl", publishNextQueryChain );
                }

                pageInfo
                    .setNextQuery( site.getSiteUrl() + request.getAttribute( "nextStaticPage" ) );
            }

            // 因为存在静态分页,必定首页必定为静态第一页
            // 获取栏目发布规则

            if( pageMode )
            {
                String[] sps = ruleBean.getFullContentClassPagePublishPath( site, classBean, null,
                    null, Integer.valueOf( 1 ) );

                String sp = sps[1];

                if( mob )
                {
                    sp = sps[3];
                }

                if( pad )
                {
                    sp = sps[5];
                }

                pageInfo.setHeadQuery( site.getSiteUrl() + sp );
            }

            if( StringUtil.getIntValue( classBean.getListPageLimit(), 0 ) >= pageInfo
                .getPageCount() )
            {
                if( pageInfo.getPageCount() == 0 )
                {
                    if( pageMode )
                    {
                        String[] sps = ruleBean.getFullContentClassPagePublishPath( site,
                            classBean, null, null, Integer.valueOf( 1 ) );

                        String sp = sps[1];

                        if( mob )
                        {
                            sp = sps[3];
                        }

                        if( pad )
                        {
                            sp = sps[5];
                        }

                        pageInfo.setEndQuery( site.getSiteUrl() + sp );
                    }
                }
                else
                {
                    if( pageMode )
                    {
                        String[] sps = ruleBean.getFullContentClassPagePublishPath( site,
                            classBean, null, null, Integer.valueOf( pageInfo.getPageCount() ) );

                        String sp = sps[1];

                        if( mob )
                        {
                            sp = sps[3];
                        }

                        if( pad )
                        {
                            sp = sps[5];
                        }

                        pageInfo.setEndQuery( site.getSiteUrl() + sp );
                    }
                }
            }

        }
        else
        {
            // 正常访问,处理首页 尾页 以及 静态最后一页跳转url

            pageInfo.setNextQuery( nextQuery );

            Map infoMap = channelService.retrieveClassPublishPageAssistant();

            String target = targetClassId + "";

            if( mob )
            {
                target = "mob" + target;
            }
            else if( pad )
            {
                target = "pad" + target;
            }

            // 静态发布方式的上一页链接处理
            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean.getClassProduceType() ) )
            {
                PublishPageAssistantBean bean = ( PublishPageAssistantBean ) infoMap.get( target );

                if( bean != null )
                {
                    if( bean.getLastPn().intValue() + 1 == pageInfo.getCurrentPage() )
                    {
                        // 意味着第一个动态页,将使用静态的prevPage地址

                        prevQuery = site.getSiteUrl()
                            + ( ( PublishPageAssistantBean ) infoMap.get( target ) )
                                .getLastPageStaticUrl();
                    }

                    if( bean.getLastPn().intValue() == pageInfo.getPageCount() )
                    {
                        pageInfo.setEndQuery( site.getSiteUrl()
                            + ( ( PublishPageAssistantBean ) infoMap.get( target ) )
                                .getLastPageStaticUrl() );
                    }

                    // 因为存在静态分页,必定首页必定为静态第一页
                    if( pageMode )
                    {
                        String[] sps = ruleBean.getFullContentClassPagePublishPath( site,
                            classBean, null, null, Integer.valueOf( 1 ) );

                        String sp = sps[1];

                        if( mob )
                        {
                            sp = sps[3];
                        }

                        if( pad )
                        {
                            sp = sps[5];
                        }

                        pageInfo.setHeadQuery( site.getSiteUrl() + sp );
                    }

                }
            }

            pageInfo.setPrevQuery( prevQuery );
        }
    }

    public static String changeRuleParamToName( String rule )
    {
        rule = StringUtil.replaceString( rule, "{seq}", "{随机码}", false, false );
        rule = StringUtil.replaceString( rule, "{year}", "{年}", false, false );
        rule = StringUtil.replaceString( rule, "{month}", "{月}", false, false );
        rule = StringUtil.replaceString( rule, "{day}", "{日}", false, false );
        // rule = StringUtil.replaceString( rule, "{time}", "{时间}", false, false
        // );
        rule = StringUtil.replaceString( rule, "{class-id}", "{栏目ID}", false, false );
        rule = StringUtil.replaceString( rule, "{class-flag}", "{栏目代码}", false, false );
        rule = StringUtil.replaceString( rule, "{class-path}", "{栏目深度}", false, false );
        rule = StringUtil.replaceString( rule, "{type-id}", "{专题子分类ID}", false, false );
        rule = StringUtil.replaceString( rule, "{comm-flag}", "{专题子分类标识}", false, false );
        rule = StringUtil.replaceString( rule, "{content-id}", "{内容ID}", false, false );
        // rule = StringUtil.replaceString( rule, "{publish-year}", "{发布年}",
        // false, false );
        // rule = StringUtil.replaceString( rule, "{publish-day}", "{发布日}",
        // false,
        // false );

        return rule;
    }

    public static List checkRuleParamExist( String rule )
    {
        List ruleParam = new ArrayList();

        if( rule.indexOf( "{seq}" ) != -1 )
        {
            ruleParam.add( "{seq}" );
        }

        if( rule.indexOf( "{year}" ) != -1 )
        {
            ruleParam.add( "{year}" );
        }

        if( rule.indexOf( "{month}" ) != -1 )
        {
            ruleParam.add( "{month}" );
        }

        if( rule.indexOf( "{day}" ) != -1 )
        {
            ruleParam.add( "{day}" );
        }

        // if( rule.indexOf( "{time}" ) != -1 )
        // {
        // ruleParam.add( "{time}" );
        // }

        if( rule.indexOf( "{class-id}" ) != -1 )
        {
            ruleParam.add( "{class-id}" );
        }

        if( rule.indexOf( "{class-flag}" ) != -1 )
        {
            ruleParam.add( "{class-flag}" );
        }

        if( rule.indexOf( "{class-path}" ) != -1 )
        {
            ruleParam.add( "{class-path}" );
        }

        if( rule.indexOf( "{type-id}" ) != -1 )
        {
            ruleParam.add( "{type-id}" );
        }

        if( rule.indexOf( "{comm-flag}" ) != -1 )
        {
            ruleParam.add( "{comm-flag}" );
        }

        if( rule.indexOf( "{content-id}" ) != -1 )
        {
            ruleParam.add( "{content-id}" );
        }

        // if( rule.indexOf( "{publish-year}" ) != -1 )
        // {
        // ruleParam.add( "{publish-year}" );
        // }
        //
        // if( rule.indexOf( "{publish-day}" ) != -1 )
        // {
        // ruleParam.add( "{publish-day}" );
        // }

        return ruleParam;
    }

}

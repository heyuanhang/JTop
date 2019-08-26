package cn.com.mjsoft.cms.content.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class ClientContentMutiQueryTag extends AbstractIteratorTag
{
    private static Logger log = Logger.getLogger( ClientContentMutiQueryTag.class );

    private static final long serialVersionUID = 8989827541588231939L;

    private static MetaDataService mdService = MetaDataService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private String classId = "-1";// 分类ID,分类类型:ID 类型可以是content, gb, member, form

    private String modelId = "-1";// 表单模型ID

    private String siteId = "-1";// 指定站点ID

    private String query = "";// 查询条件,如:0=<a1(对应整数字段)<=9999,1971-1-1=<b1(对应日期字段)<=2018-18-18

    private String order = "";// 排序字段

    private String way = "";// 排序顺序

    private String page = "false";// 分页模式

    private String size = "";// 每页大小

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        /*
         * String queryCod = "classId=" + classId + "&formMode=" + formMode +
         * "&modelId=" + modelId + "&siteId=" + siteId + "&query=" + query +
         * "&order=" + order + "&way=" + way + "&page=" + page + "&size=" +
         * size;
         */

        query = SystemSafeCharUtil.decodeFromWeb( SystemSafeCharUtil.decodeFromWeb( query ) );

        log.info( "ClientContentMutiQueryTag: query = " + query );

        List sqlParamList = new ArrayList();

        String[] cIdType = null;

        if( classId.endsWith ( ":" ) )
        {
            cIdType = new String[] { classId, "-9999" };
        }
        else if( classId.indexOf( ":" ) != -1 )
        {
            cIdType = StringUtil.split( classId, ":" );
        }        
        else
        {
            cIdType = new String[] { classId, "-1" };
        }

        if( cIdType.length < 2 )
        {
            return sqlParamList;
        }

        String mode = cIdType[0];

        boolean defFormMode = false;

        if( "form".equals( mode ) )
        {
            defFormMode = true;
        }

        SiteGroupBean siteBean = getSiteObj( StringUtil.getLongValue( siteId, -1 ) );

        Map<String, Long> midMap = new HashMap<String, Long>( 2 );

        Page pageInfo = null;

        String targetQuerySql = mdService.retrieveModelMutiQuery( cIdType, siteBean, StringUtil
            .getLongValue( modelId, -1 ), query, order, way, sqlParamList, false, midMap, siteId );

        if( targetQuerySql == null )
        {

            pageInfo = new Page( 1, 0, 0 );

            sqlParamList.add( Long.valueOf( pageInfo.getFirstResult() ) );// 起始位置
            sqlParamList.add( Integer.valueOf( pageInfo.getPageSize() ) );// 起始位置

            this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

            return new ArrayList();
        }

        // 替换分页参数
        int nextPage = StringUtil.getIntValue( ( String ) this.pageContext.getRequest()
            .getParameter( "pn" ), 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        if( "true".equals( page ) )
        {
            List countSqlParamList = new ArrayList();

            Long count = mdService.retrieveSystemTableByQueryFlagAndPageInfoCount( mdService
                .retrieveModelMutiQuery( cIdType, siteBean, StringUtil.getLongValue( modelId, -1 ),
                    query, order, way, countSqlParamList, true, midMap, siteId ), countSqlParamList
                .toArray( new Object[] {} ), defFormMode );

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            sqlParamList.add( Long.valueOf( pageInfo.getFirstResult() ) );// 起始位置
            sqlParamList.add( Integer.valueOf( pageInfo.getPageSize() ) );// 起始位置

            this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );
        }
        else
        {

            // 非分页只查询部分数据
            sqlParamList.add( Long.valueOf( 0 ) );// 起始位置
            sqlParamList.add( Integer.valueOf( pageSize ) );// 起始位置
        }
        
        if( siteBean == null)
        {
            return null;
        }

        List resultMap = mdService.retrieveMutiQueryContentByQueryFlagAndPageInfo( targetQuerySql,
            sqlParamList.toArray( new Object[] {} ), defFormMode, siteBean.getSiteId(), midMap
                .get( "modelId" ) );

        ContentClassBean classBean = ( ContentClassBean ) this.pageContext.getAttribute( "Class" );

        if( classBean != null )
        {
            // 静态分页
            publishService.htmlTagPage( this.pageContext, null, classBean.getClassId(), classBean,
                classBean.getClassId(), pageInfo, page, "" );
        }

        return resultMap;
    }

    private SiteGroupBean getSiteObj( Long siteId )
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( siteId );

        if( site == null )
        {
            site = SiteGroupService
                .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                    .getRequest() );
        }

        return site;
    }

    protected String returnPutValueName()
    {
        return "MInfo";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setOrder( String order )
    {
        this.order = order;
    }

    public void setWay( String way )
    {
        this.way = way;
    }

    public void setQuery( String query )
    {
        this.query = query;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setPage( String page )
    {
        this.page = page;
    }

    public void setModelId( String modelId )
    {
        this.modelId = modelId;
    }

    public void setSiteId( String siteId )
    {
        this.siteId = siteId;
    }

}

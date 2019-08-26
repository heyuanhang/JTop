package cn.com.mjsoft.cms.comment.html;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.comment.service.CommentService;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientContentCommentTag extends AbstractIteratorTag
{
    private static CommentService commentService = CommentService.getInstance();

    private static final long serialVersionUID = -2423914983882813858L;

    private static PublishService publishService = PublishService.getInstance();

    private String contentId = "-1";

    private String commentId = "-1";

    private String typeId = "-1";

    private String userName;

    private String page = "false";

    private String size;

    protected void initTag()
    {

    }

    @SuppressWarnings( "unchecked" )
    protected List returnObjectList()
    {
        Long contentIdVar = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        Long commentIdVar = Long.valueOf( StringUtil.getLongValue( commentId, -1 ) );

        Integer typeIdVar = Integer.valueOf( StringUtil.getIntValue( typeId, -1 ) );

        int pageNum = StringUtil.getIntValue( ( String ) this.pageContext.getRequest()
            .getParameter( "pn" ), 1 );

        if( !"true".equals( page ) )
        {
            pageNum = 1;
        }

        int sizeVar = StringUtil.getIntValue( size, 15 );

        List result = null;

        Long count = null;

        Page pageInfo = null;

        if( typeIdVar.longValue() > 0 )
        {
            count = commentService.retrieveCommentCountTypeMode( null, typeIdVar, contentIdVar,
                Integer.valueOf( 1 ) );

            /**
             * 1.组装page对象
             */
            pageInfo = new Page( sizeVar, count.intValue(), pageNum );

            result = commentService.retrieveCommentBeanListByTypeId( contentIdVar, typeIdVar, Long
                .valueOf( pageInfo.getFirstResult() ), sizeVar, Integer.valueOf( 1 ) );
        }
        else if( contentIdVar.longValue() > 0 )
        {
            count = commentService.retrieveCommentCount( null, CommentService.CONTENT_MODE,
                contentIdVar, Integer.valueOf( 1 ) );

            /**
             * 1.组装page对象
             */
            pageInfo = new Page( sizeVar, count.intValue(), pageNum );

            result = commentService.retrieveCommentBeanListByContentId( contentIdVar, Long
                .valueOf( pageInfo.getFirstResult() ), sizeVar, Integer.valueOf( 1 ) );
        }
        else if( commentIdVar.longValue() > 0 )
        {
            result = new ArrayList( 2 );

            result.add( commentService.retrieveSingeleCommentBean( commentIdVar ) );

        }
        else if( !"".equals( userName ) )
        {
            SiteGroupBean site = ( SiteGroupBean ) this.pageContext.getRequest().getAttribute(
                "SiteObj" );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }

            count = commentService.retrieveCommentCountByUserName( site.getSiteId(), userName );

            /**
             * 1.组装page对象
             */
            pageInfo = new Page( sizeVar, count.intValue(), pageNum );

            result = commentService.retrieveAllCommentBeanListByUserName( site.getSiteId(),
                userName, Long.valueOf( pageInfo.getFirstResult() ), sizeVar );

        }

        this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

        //String queryCod = "contentId=" + contentId + "&commentId=" + commentId + "&typeId="
            //+ typeId + "&userName=" + userName + "&page=" + page + "&size=" + size;

        ContentClassBean classBean = ( ContentClassBean ) this.pageContext.getAttribute( "Class" );

        if( classBean != null )
        {
            // 静态分页
            publishService.htmlTagPage( this.pageContext, null, classBean.getClassId(), classBean,
                classBean.getClassId(), pageInfo, page, "" );
        }

        return result;
    }

    protected String returnPutValueName()
    {
        return "Comment";
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

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setCommentId( String commentId )
    {
        this.commentId = commentId;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public void setPage( String page )
    {
        this.page = page;
    }

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
    }

}

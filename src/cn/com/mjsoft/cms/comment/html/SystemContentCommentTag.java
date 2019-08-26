package cn.com.mjsoft.cms.comment.html;

import java.util.List;

import cn.com.mjsoft.cms.comment.service.CommentService;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemContentCommentTag extends AbstractIteratorTag
{
    private static CommentService commentService = CommentService.getInstance();

    private static final long serialVersionUID = -2423914983882813858L;

    private String contentId;

    private String classId;

    private String pn;

    private String size;

    private String userName = "";

    private String censor = "-1";

    private String textMode;

    private String commentId;

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        // TODO 系统标签必须是管理员

        Long contentIdVar = Long.valueOf( StringUtil.getLongValue( contentId,
            -1 ) );

        Integer censorVar = Integer.valueOf( StringUtil
            .getIntValue( censor, -1 ) );

        Long classIdVar = Long.valueOf( StringUtil.getLongValue( classId, -1 ) );

        userName = SystemSafeCharUtil.decodeFromWeb( userName );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int sizeVar = StringUtil.getIntValue( size, 15 );

        List result = null;

        Long count = null;

        Page pageInfo = null;

        if( contentIdVar.longValue() > 0 )
        {
            count = commentService.retrieveCommentCount( null,
                CommentService.CONTENT_MODE, contentIdVar, censorVar );

            /**
             * 1.组装page对象
             */
            pageInfo = new Page( sizeVar, count.intValue(), pageNum );

            result = commentService.retrieveCommentBeanListByContentId(
                contentIdVar, Long.valueOf( pageInfo.getFirstResult() ),
                sizeVar, censorVar );
        }
        else if( classIdVar.longValue() > 0 )
        {
            count = commentService.retrieveCommentCount( null,
                CommentService.CLASS_MODE, classIdVar, censorVar );

            /**
             * 1.组装page对象
             */
            pageInfo = new Page( sizeVar, count.intValue(), pageNum );

            result = commentService.retrieveCommentBeanListByClassId(
                classIdVar, Long.valueOf( pageInfo.getFirstResult() ), sizeVar,
                censorVar );
        }
        else
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
                .getSecuritySession().getCurrentLoginSiteInfo();

            if( !"".equals( userName ) )
            {
                count = commentService.retrieveCommentCountByUserName( site
                    .getSiteId(), userName );
            }
            else
            {
                count = commentService.retrieveCommentCount( site.getSiteId(),
                    CommentService.ALL_MODE, contentIdVar, censorVar );
            }

            /**
             * 1.组装page对象
             */
            pageInfo = new Page( sizeVar, count.intValue(), pageNum );

            this.pageContext.setAttribute( "___system_dispose_page_object___",
                pageInfo );

            if( !"".equals( userName ) )
            {
                result = commentService.retrieveAllCommentBeanListByUserName(
                    site.getSiteId(), userName, Long.valueOf( pageInfo
                        .getFirstResult() ), sizeVar );
            }
            else
            {
                result = commentService.retrieveAllCommentBeanList( site
                    .getSiteId(), Long.valueOf( pageInfo.getFirstResult() ),
                    sizeVar, censorVar );
            }
        }

        this.pageContext.setAttribute( "___system_dispose_page_object___",
            pageInfo );

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

    public void setTextMode( String textMode )
    {
        this.textMode = textMode;
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public void setPn( String pn )
    {
        this.pn = pn;
    }

    public void setCensor( String censor )
    {
        this.censor = censor;
    }

}

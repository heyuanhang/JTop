package cn.com.mjsoft.cms.comment.html;

import java.util.List;

import cn.com.mjsoft.cms.comment.service.CommentService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientCommentAllReplyTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -8858420856091935262L;

    private static CommentService commentService = CommentService.getInstance();

    private String ids;

    private String replyId;

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        Long replyIdVar = Long.valueOf( StringUtil.getLongValue( replyId, -1 ) );

        List result = null;
        if( replyIdVar.longValue() < 0 )
        {
            result = commentService.retrieveCommentBeanListByCommentIds( ids );
        }
        else
        {
            result = commentService
                .retrieveCommentBeanListByParentId( replyIdVar );
        }
        return result;
    }

    protected String returnPutValueName()
    {
        return "Reply";
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

    public void setIds( String ids )
    {
        this.ids = ids;
    }

    public void setReplyId( String replyId )
    {
        this.replyId = replyId;
    }
}

package cn.com.mjsoft.cms.comment.json;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.comment.service.CommentService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientContentCommentReplyJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientContentCommentJSonFlow.class );

    private static CommentService commentService = CommentService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String ids = StringUtil.notNull( ( String ) params.get( "ids" ) );

        String replyId = ( String ) params.get( "replyId" );

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

        if( result.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( result ) );

    }
}

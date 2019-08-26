package cn.com.mjsoft.cms.content.behavior;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.content.bean.ContentMainInfoBean;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.security.headstream.bean.ResourceState;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentAccAuthBehavior implements Behavior
{
    private static ContentService contentService = ContentService.getInstance();

    public Object operation( Object target, Object[] param )
    {
        ResourceState rs = ( ResourceState ) target;

        HttpServletRequest request = rs.getRequest();

        String targetKey = rs.getTargetKey();

        // 只有授权栏目下的内容才可以进行编辑，需要获取当前传入内容的所属栏目是否被授权
        if( "/core/content/EditUserDefineModelContent.jsp".equals( targetKey ) )
        {
            Long contentId = Long.valueOf( StringUtil.getLongValue( request
                .getParameter( "contentId" ), -1 ) );

            ContentMainInfoBean mainInfo = contentService
                .retrieveSingleContentMainInfoBean( contentId );

            if( mainInfo != null )
            {
                Long classId = mainInfo.getClassId();

                if( classId != null
                    && rs.getSymbolValue().equals( classId.toString() ) )
                {
                    return Boolean.TRUE;
                }
            }

            return Boolean.FALSE;
        }
        // else if( "/content/copyContent.cmd"
        // .equals( targetKey ) )
        // {
        // return Boolean.FALSE;
        // }

        return Boolean.TRUE;
    }
}

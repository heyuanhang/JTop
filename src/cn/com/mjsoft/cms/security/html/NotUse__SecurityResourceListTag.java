package cn.com.mjsoft.cms.security.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.security.service.SecurityService;

/**
 * 安全资源获取标签，不涉及权限控制逻辑，由系统使用
 * 
 * @author mjsoft
 * 
 */
public class NotUse__SecurityResourceListTag extends TagSupport
{

    private static final long serialVersionUID = 7369308444677755340L;

    private SecurityService resourceService = SecurityService.getInstance();

    public int doStartTag() throws JspException
    {
//        List resourceServicList = resourceService
//            .retrieveAllSecurityResourceBean();
//
//        pageContext.setAttribute( "securityResourceList", resourceServicList );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "securityResourceList" );
        return EVAL_PAGE;
    }

}

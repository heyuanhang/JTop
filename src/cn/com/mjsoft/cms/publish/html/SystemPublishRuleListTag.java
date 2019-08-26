package cn.com.mjsoft.cms.publish.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemPublishRuleListTag extends TagSupport
{
    private static final long serialVersionUID = 3490184199629942922L;

    private PublishService publishService = PublishService.getInstance();

    private String type;

    private String currSite = "false";

    public int doStartTag() throws JspException
    {
        Integer typeIntVar = Integer.valueOf( StringUtil.getIntValue( type,
            -9999 ) );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        List ruleList = null;

        if( "true".equals( currSite ) )
        {
            ruleList = publishService.retrievePublishRuleBeanByType(
                typeIntVar, site.getSiteId() );
        }
        else
        {
            ruleList = publishService
                .retrievePublishRuleBeanByType( typeIntVar );
        }

        pageContext.setAttribute( "RuleList", ruleList );

        return EVAL_PAGE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "RuleList" );
        return EVAL_PAGE;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public void setCurrSite( String currSite )
    {
        this.currSite = currSite;
    }
}

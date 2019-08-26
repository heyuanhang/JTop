package cn.com.mjsoft.cms.publish.html;

import java.util.List;

import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemPublishRuleTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -2699779009912590064L;

    private PublishService publishService = PublishService.getInstance();

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "Rule";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "RuleList";
    }

    protected Object returnSingleObject()
    {
        Long ruleIdVar = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

        return publishService.retrieveSinglePublishRuleBean( ruleIdVar );
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

}

package cn.com.mjsoft.cms.metadata.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "model_html_element", id = "htmlElementId", idMode = EntitySqlBridge.DB_IDENTITY )
public class ModelHtmlElement
{

    private Long htmlElementId = Long.valueOf( -1 );
    private String htmlElementName;
    private String htmlContent;

    public Long getHtmlElementId()
    {
        return this.htmlElementId;
    }

    public void setHtmlElementId( Long htmlElementId )
    {
        this.htmlElementId = htmlElementId;
    }

    public String getHtmlElementName()
    {
        return this.htmlElementName;
    }

    public void setHtmlElementName( String htmlElementName )
    {
        this.htmlElementName = htmlElementName;
    }

    public String getHtmlContent()
    {
        return this.htmlContent;
    }

    public void setHtmlContent( String htmlContent )
    {
        this.htmlContent = htmlContent;
    }

}

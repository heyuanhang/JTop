package cn.com.mjsoft.cms.metadata.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.framework.util.StringUtil;

/**
 * Html元素实际元素值
 * 
 * @author mjsoft
 * 
 */
public class SystemModelHtmlElementBean implements Serializable
{
    private static final long serialVersionUID = 5835942813417281986L;

    private Long htmlElementId = Long.valueOf( -1 );

    private String htmlElementName;

    private String htmlInputTemplet;

    private String htmlEditTemplet;
    /**
     * html参数对应配置中相同名称的用户自定义值
     */
    private List layoutParamList;

    /**
     * html参数对应的存储值
     */
    private List valueParamList;

    public String getHtmlInputTemplet()
    {
        return htmlInputTemplet;
    }

    public void setHtmlInputTemplet( String htmlInputTemplet )
    {
        this.htmlInputTemplet = htmlInputTemplet;
    }

    public String getHtmlEditTemplet()
    {
        return htmlEditTemplet;
    }

    public void setHtmlEditTemplet( String htmlEditTemplet )
    {
        this.htmlEditTemplet = htmlEditTemplet;
    }

    public Long getHtmlElementId()
    {
        return htmlElementId;
    }

    public void setHtmlElementId( Long htmlElementId )
    {
        this.htmlElementId = htmlElementId;
    }

    public String getHtmlElementName()
    {
        return htmlElementName;
    }

    public void setHtmlElementName( String htmlElementName )
    {
        this.htmlElementName = htmlElementName;
    }

    public List getLayoutParam()
    {
        return layoutParamList;
    }

    public void setLayoutParam( String layoutParam )
    {

        List paramList = new ArrayList();

        if( layoutParam != null )
        {
            String[] params = StringUtil.split(layoutParam, "," );

            for ( int i = 0; i < params.length; i++ )
            {
                if( StringUtil.isStringNotNull( params[i] ) )
                {
                    paramList.add( params[i] );
                }
            }
        }

        this.layoutParamList = paramList;
    }

    public List getValueParam()
    {
        return valueParamList;
    }

    public void setValueParam( String valueParam )
    {

        List paramList = new ArrayList();

        if( valueParam != null )
        {
            String[] params =StringUtil.split( valueParam, "," );

            for ( int i = 0; i < params.length; i++ )
            {
                if( StringUtil.isStringNotNull( params[i] ) )
                {
                    paramList.add( params[i] );
                }
            }
        }

        this.valueParamList = paramList;
    }

}

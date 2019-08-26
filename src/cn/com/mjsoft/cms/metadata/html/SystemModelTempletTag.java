package cn.com.mjsoft.cms.metadata.html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 获取自定义模型生成的 input edit 页面模版
 * 
 * @author
 * 
 */
public class SystemModelTempletTag extends TagSupport
{
    private static final long serialVersionUID = 4229825491448488464L;

    private static Logger log = Logger.getLogger( SystemModelTempletTag.class );

    private String modelSign = "";

    private String type = "";

    public int doStartTag() throws JspException
    {

        if( StringUtil.isStringNull( modelSign ) )
        {
            return EVAL_BODY_INCLUDE;
        }
        else
        {
            HttpServletRequest request = ( HttpServletRequest ) this.pageContext
                .getRequest();

            HttpServletResponse response = ( HttpServletResponse ) this.pageContext
                .getResponse();

      
            String modelTempletFile = "../../" + Constant.METADATA.MDEL_TEMPLET
                + Constant.CONTENT.URL_SEP + modelSign + ".jsp";

            try
            {
                request.getRequestDispatcher( modelTempletFile ).include(
                    request, response );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
                throw new JspException( e );
            }

        }

        // String siteBase = basePath + SITE_BASE;

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        // pageContext.removeAttribute( "dataModelList" );
        return EVAL_PAGE;
    }

    public void setModelSign( String modelSign )
    {
        this.modelSign = modelSign;
    }

    public void setType( String type )
    {
        this.type = type;
    }

}

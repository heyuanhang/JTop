package cn.com.mjsoft.cms.security.html;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.cms.security.service.SecurityService;

public class SystemDisplaySecurityResTag extends TagSupport
{

    private static final long serialVersionUID = 8126763307761167178L;

    private static String MID_LINE = "│";
    private static String HEAD_LINE = "├";
    private static String END_LINE = "└";

    private SecurityService securityResourceService = SecurityService
        .getInstance();

    public int doStartTag() throws JspException
    {

        long l1 = System.currentTimeMillis();

        StringBuilder builder = new StringBuilder();

        List allSecResList = null;

        allSecResList = securityResourceService
            .retrieveAllSecurityResourceBean();

        SecurityResourceBean securityResourceBean = null;

        Map layerLastChildFlag = new HashMap();
        for ( int i = 0; i < allSecResList.size(); i++ )
        {
            securityResourceBean = ( SecurityResourceBean ) allSecResList
                .get( i );
            builder.append( "<option value='"
                + securityResourceBean.getSecResId() + "'>" );
            int layer = securityResourceBean.getLayer().intValue();
            layerLastChildFlag.put( Integer.valueOf( layer ), Integer
                .valueOf( securityResourceBean.getIsLastChild().intValue() ) );
         
            for ( int j = 0; j < layer; j++ )
            {
                boolean isLastChild = ( ( Integer ) layerLastChildFlag
                    .get( new Integer( ( j + 1 ) ) ) ).intValue() == 1 ? true
                    : false;

                if( ( j + 1 ) != layer )
                {
                    // 非最后一个兄弟
                    if( isLastChild )
                    {
                        builder.append( "&nbsp&nbsp" );

                    }
                    else
                    {
                        builder.append( MID_LINE );
                    }

                }
                else
                {
                    // 非最后一个兄弟
                    if( isLastChild )
                    {
                        builder.append( END_LINE );

                    }
                    else
                    {
                        builder.append( HEAD_LINE );
                    }
                }

            }

            // if(contentClassVO.getParent().longValue() == -9999){
            // builder.append(
            // "<em><strong>"+contentClassVO.getClassName().trim()+"</strong></em>"
            // + "</option>\n" );
            //
            // }
            builder.append( securityResourceBean.getResourceName().trim()
                + "</option>\n" );

        }
        try
        {
            this.pageContext.getOut().write( builder.toString() );
        }
        catch ( IOException e )
        {

            throw new JspException( e );
        }
       
        return EVAL_PAGE;
    }
}

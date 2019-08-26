package cn.com.mjsoft.cms.channel.html;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemDisplayClassTag extends TagSupport
{
    private static final long serialVersionUID = 6096240296731473364L;

    private ChannelService channelService = ChannelService.getInstance();
    private static String MID_LINE = "│";
    private static String HEAD_LINE = "├";
    private static String END_LINE = "└";

    // 模型ID
    private String modelId;

    // 内容ID
    private String contentId;

    // 选中的classID
    private String initClassId;

    // 以空格填充栏目深度
    private String blankMode;

    public int doStartTag() throws JspException
    {
        List allConentClass = null;

        ContentClassBean contentClassBean = null;

        if( "true".equals( blankMode ) )
        {

        }
        else
        {

            String x = "<option value='1'>├首页</option>";

            StringBuffer builder = new StringBuffer();

            Integer contentIdvar = Integer.valueOf( StringUtil.getIntValue( contentId, -1 ) );

            if( contentIdvar.intValue() > 0 )
            {
                // 取对应内容的栏目名称
                // channelService.retrieveConetentClassBeanByCurrentClassID(
                // classId
                // )
                return EVAL_PAGE;
            }
            else
            {
                Long modelIdvar = Long.valueOf( StringUtil.getLongValue( modelId, -1 ) );

                allConentClass = channelService.fetchAllConetentClassBean( null );

                Map layerLastChildFlag = new HashMap();
                for ( int i = 0; i < allConentClass.size(); i++ )
                {
                    contentClassBean = ( ContentClassBean ) allConentClass.get( i );
                    /*
                     * 根据内容模型ID,类刷新select option
                     */
                    if( modelIdvar.intValue() < 0// 任意内容模型
                        || contentClassBean.getContentType().equals( modelIdvar )// 指定的某内容模型
                        || contentClassBean.getContentTypeName().equals( Long.valueOf( 0 ) ) )// 多模型
                    {
                        if( contentClassBean.getClassId().toString().equals( initClassId ) )
                        {
                            builder.append( "<option value='" + contentClassBean.getClassId()
                                + "' selected>" );
                        }
                        else
                        {
                            builder.append( "<option value='" + contentClassBean.getClassId()
                                + "'>" );
                        }
                    }

                    int layer = contentClassBean.getLayer().intValue();
                    layerLastChildFlag.put( Integer.valueOf( layer ), Integer
                        .valueOf( contentClassBean.getIsLastChild().intValue() ) );

                    for ( int j = 0; j < layer; j++ )
                    {
                        boolean isLastChild = ( ( Integer ) layerLastChildFlag.get( new Integer(
                            ( j + 1 ) ) ) ).intValue() == 1 ? true : false;

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
                    builder.append( contentClassBean.getClassName().trim() + "</option>\n" );

                }
                try
                {

                    this.pageContext.getOut().write( builder.toString() );
                }
                catch ( IOException e )
                {

                    throw new JspException( e );
                }

            }
        }

        return EVAL_BODY_INCLUDE;
    }

    public void setModelId( String modelId )
    {
        this.modelId = modelId;
    }

    public String getInitClassId()
    {
        return initClassId;
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }

    public void setInitClassId( String initClassId )
    {
        this.initClassId = initClassId;
    }

    public void setBlankMode( String blankMode )
    {
        this.blankMode = blankMode;
    }

}

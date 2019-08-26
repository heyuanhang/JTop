package cn.com.mjsoft.cms.metadata.html;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.bean.ContentCommendPushInfoBean;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemModelFiledListTag extends TagSupport
{
    private static final long serialVersionUID = -716232756145213384L;

    private static Logger log = Logger
        .getLogger( SystemModelFiledListTag.class );

    private static MetaDataService metaDataService = MetaDataService
        .getInstance();

    private String modelId = "";

    private String showMode = "false"; // 显示模式会按照定义的行和列来排列,并且特殊字段被特殊处理

    public int doStartTag() throws JspException
    {
        Long modelIdVar = Long.valueOf( StringUtil.getLongValue( modelId, -1 ) );

        DataModelBean model = metaDataService
            .retrieveSingleDataModelBeanById( modelIdVar );

        Integer modelType = ( model != null ) ? model.getModelType() : Integer
            .valueOf( 0 );

        /**
         * 根据内容类型从页面获取信息bean,如内容,栏目,留言,会员等,此信息使用在编辑模式下
         */
        Map currentInfo = null;
        if( Constant.METADATA.MODEL_TYPE_CONTENT.equals( modelType ) )
        {
            currentInfo = ( Map ) this.pageContext.getAttribute( "Info" );
        }
        else if( Constant.METADATA.MODEL_TYPE_CHANNEL.equals( modelType ) )
        {
            ContentClassBean classBean = ( ContentClassBean ) this.pageContext
                .getAttribute( "Class" );

            if( classBean != null )
            {
                classBean.getExt().put( "contentId", classBean.getClassId() );
                classBean.getExt().put( "classId", classBean.getClassId() );
                currentInfo = classBean.getExt();
            }
        }
        else if( Constant.METADATA.MODEL_TYPE_SITE.equals( modelType ) )
        {
            SiteGroupBean siteBean = ( SiteGroupBean ) this.pageContext
                .getAttribute( "Site" );

            if( siteBean != null )
            {
                siteBean.getExt().put( "contentId", siteBean.getSiteId() );
                currentInfo = siteBean.getExt();
            }
        }
        else if( Constant.METADATA.MODEL_TYPE_GBOOK.equals( modelType ) )
        {
            Map gbInfo = ( Map ) this.pageContext.getAttribute( "GbInfo" );

            if( gbInfo != null )
            {
                currentInfo = gbInfo;
            }
        }
        else if( Constant.METADATA.MODEL_TYPE_ADVERT_CONFIG.equals( modelType ) )
        {
            Map info = ( Map ) this.pageContext.getAttribute( "Pos" );

            if( info != null )
            {
                currentInfo = info;
            }
        }
        else if( Constant.METADATA.MODEL_TYPE_ADVERT_CONTENT.equals( modelType ) )
        {
            Map info = ( Map ) this.pageContext.getAttribute( "Advert" );

            if( info != null )
            {
                currentInfo = info;
            }
        }
        else if( Constant.METADATA.MODEL_TYPE_DEF_FORM.equals( modelType ) )
        {
            Map info = ( Map ) this.pageContext.getAttribute( "Info" );

            if( info != null )
            {
                currentInfo = info;
            }
        }
        else if( Constant.METADATA.MODEL_TYPE_MEMBER.equals( modelType ) )
        {
            Map info = ( Map ) this.pageContext.getAttribute( "Member" );

            if( info != null )
            {
                currentInfo = info;
            }
        }
        else if( Constant.METADATA.MODEL_TYPE_COMMEND.equals( modelType ) )
        {
            ContentCommendPushInfoBean info = ( ContentCommendPushInfoBean ) this.pageContext.getAttribute( "CommInfo" );

            if( info != null )
            {
                currentInfo = info.getExt();
            }
        }

        Object[] result = metaDataService
            .retrieveModelFiledAndValueInfoBeanList( currentInfo, modelIdVar );

        if( "true".equals( showMode ) )
        {

            pageContext.setAttribute( "ModelFiledInfoList", result[0] );
        }
        else
        {
            pageContext.setAttribute( "ModelFiledInfoList", result[1] );
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "ModelFiledInfoList" );
        return EVAL_PAGE;
    }

    public String getModelId()
    {
        return modelId;
    }

    public void setModelId( String modelId )
    {
        this.modelId = modelId;
    }

    public void setShowMode( String showMode )
    {
        this.showMode = showMode;
    }
}

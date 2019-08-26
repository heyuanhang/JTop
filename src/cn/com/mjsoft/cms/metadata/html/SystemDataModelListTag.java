package cn.com.mjsoft.cms.metadata.html;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemDataModelListTag extends TagSupport
{
    private static final long serialVersionUID = 2264381363433209027L;

    private static Logger log = Logger.getLogger( SystemDataModelListTag.class );

    private static MetaDataService metaDataService = MetaDataService
        .getInstance();

    String mode = "";

    String modelType = "";

    String showMode = "-1";
    
    String siteId = "-1";

    public int doStartTag() throws JspException
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        List result = null;
        // 需要通过强制分类 分开使用
        if( "manage".equals( mode ) )
        {
            result = metaDataService.retrieveAllDataModelBeanList(
                Constant.METADATA.MD_IS_ALL_STATE, Integer.valueOf( StringUtil
                    .getIntValue( modelType, 0 ) ), siteBean.getSiteId(),
                showMode );
        }
        else
        {
            result = metaDataService.retrieveAllDataModelBeanList(
                Constant.METADATA.MD_IS_USE_STATE, Integer.valueOf( StringUtil
                    .getIntValue( modelType, 0 ) ), siteBean.getSiteId(),
                showMode );
        }
        
        Long sid = Long.valueOf( StringUtil.getLongValue( siteId, -1 ) );
        
        if(sid.longValue() > 0)
        {
            DataModelBean model = null;
            
            List endRes = new ArrayList();
            
            for(int i = 0 ; i < result.size();i++)
            {
                model = ( DataModelBean ) result.get( i );
                
                if(model.getSiteId().equals( sid ))
                {
                    endRes.add( model );
                }
            }
            
            result = endRes;
        }
        
        

        pageContext.setAttribute( "dataModelList", result );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "dataModelList" );
        return EVAL_PAGE;
    }

    public void setMode( String mode )
    {
        this.mode = mode;
    }

    public void setModelType( String modelType )
    {
        this.modelType = modelType;
    }

    public void setShowMode( String showMode )
    {
        this.showMode = showMode;
    }

    public void setSiteId( String siteId )
    {
        this.siteId = siteId;
    }
    
    
}

package cn.com.mjsoft.cms.resources.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemSiteResourcesTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1543929672916789463L;

    private String classId = "";

    private String classChildMode = "false";

    private String resType = "";

    private String source = "";

    private String resId = "";

    // 大小默认12
    private String pageSize = "9";

    private static ChannelService channelService = ChannelService.getInstance();

    private static ResourcesService resService = ResourcesService.getInstance();

    @SuppressWarnings( "unchecked" )
    protected List returnObjectList()
    {
        List result = null;
        if( !"".equals( source ) )
        {
            result = new ArrayList( 1 );
            result.add( resService.retrieveSingleResourceBeanBySource( source ) );
        }
        else if( !"".equals( resId ) )
        {
            result = new ArrayList( 1 );
            result.add( resService.retrieveSingleResourceBeanByResId( Long.valueOf( StringUtil
                .getLongValue( resId, -1 ) ) ) );
        }
        else
        {
            /**
             * classId参数不为空的话,优先取
             */
            long targetClassId = -1;// 
            // 注意,标签中使用classId属性会比URL传递参数级别高
            if( StringUtil.isStringNotNull( classId ) )
            {
                targetClassId = StringUtil.getLongValue( classId, -1 );
            }
            else
            {
                targetClassId = StringUtil.getLongValue( pageContext.getRequest().getParameter(
                    "classId" ), -1 );
            }

            String cids = targetClassId + "";

            if( "true".equals( classChildMode ) )
            {
                if( "-9999".equals( targetClassId + "" ) )
                {
                    SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
                        .getSecuritySession().getCurrentLoginSiteInfo();

                    cids = channelService.retrieveContentClassIdByPreLinear( "000", site
                        .getSiteFlag() );
                }
                else
                {
                    ContentClassBean classBean = channelService
                        .retrieveSingleClassBeanInfoByClassId( targetClassId );

                    cids = channelService.retrieveContentClassIdByPreLinear( classBean
                        .getLinearOrderFlag(), classBean.getSiteFlag() );

                }

                if( StringUtil.isStringNull( cids ) )
                {
                    cids = "-1";
                }
            }

            Integer resTypeVal = Integer.valueOf( StringUtil.getIntValue( resType,
                Constant.RESOURCE.ANY_RES_TYPE.intValue() ) );

            Integer count = resService.retrieveResCountByClassIdAndResType( cids, resTypeVal );

            /**
             * 1.组装page对象
             */
            int pageVal = StringUtil.getIntValue(
                this.pageContext.getRequest().getParameter( "pn" ), 1 );

            int size = StringUtil.getIntValue( pageSize, 9 );
            Page pageInfo = new Page( ( size == 0 ) ? Page.DEFAULT_PAGE_SIZE : size, count
                .intValue(), pageVal );

            this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

            return resService.retrieveResourceBeanByClassIdAndResType( cids, resTypeVal, Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }

        return result;
    }

    protected String returnPutValueName()
    {
        return "Res";
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected void initTag()
    {

    }

    public void setResId( String resId )
    {
        this.resId = resId;
    }

    public void setSource( String source )
    {
        this.source = source;
    }

    public void setPageSize( String pageSize )
    {
        this.pageSize = pageSize;
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setResType( String resType )
    {
        this.resType = resType;
    }

    public void setClassChildMode( String classChildMode )
    {
        this.classChildMode = classChildMode;
    }

}

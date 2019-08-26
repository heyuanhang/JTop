package cn.com.mjsoft.cms.block.html;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.block.bean.BlockInfoBean;
import cn.com.mjsoft.cms.block.service.BlockService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.http.BufferedResponseWrapper;
import cn.com.mjsoft.framework.cache.http.BufferedResponseWrapper.WrappedOutputStream;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClientBlockTag extends TagSupport
{
    private static final long serialVersionUID = 4848790348666774767L;

    private static BlockService blockService = BlockService.getInstance();

    private String flag = "";

    public int doStartTag() throws JspException
    {

        HttpServletRequest request = ( HttpServletRequest ) this.pageContext.getRequest();

        HttpServletResponse response = ( HttpServletResponse ) this.pageContext.getResponse();

        BlockInfoBean blockBean = blockService.retrieveSingleBlockBean( flag );

        if( blockBean != null )
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( blockBean.getSiteId() );

            if( site == null )
            {
                throw new FrameworkException( "站点不存在或已被删除 siteId:" + blockBean.getSiteId() );
            }

            if( Boolean.TRUE.equals( request.getAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG ) ) )
            {
                // 静态发布页面流程中
                if( Constant.BLOCK.BLOCK_TYPE_AUTO.equals( blockBean.getType() ) )
                {
                    String serverContext = JtRuntime.cmsServer.getContext();

                    String target = null;

                    // 如果当前block没有被静态化
                    if( StringUtil.isStringNull( blockBean.getStaticUrl() ) )
                    {
                        // 此List由发布逻辑回调使用

                        ( ( List ) request
                            .getAttribute( Constant.CONTENT.HTML_PUB_ACTION_STATIC_BLOCKLIST_FLAG ) )
                            .add( blockBean );

                        // 生成静态地址,以项目部署地址为标准
                        target = "<!--#include virtual=\"" + Constant.CONTENT.URL_SEP
                            + serverContext + Constant.CONTENT.URL_SEP + site.getSiteRoot()
                            + Constant.CONTENT.URL_SEP + site.getPublishRoot()
                            + Constant.CONTENT.URL_SEP + Constant.CONTENT.BLOCK_BASE
                            + Constant.CONTENT.URL_SEP + blockBean.getFlag()
                            + Constant.CONTENT.HTML_PUB_HTML + "\"-->";
                    }
                    else
                    {
                        target = "<!--#include virtual=\"" + Constant.CONTENT.URL_SEP
                            + serverContext + Constant.CONTENT.URL_SEP + site.getSiteRoot()
                            + Constant.CONTENT.URL_SEP + site.getPublishRoot()
                            + Constant.CONTENT.URL_SEP + Constant.CONTENT.BLOCK_BASE
                            + Constant.CONTENT.URL_SEP + blockBean.getStaticUrl() + "\"-->";
                    }

                    try
                    {
                        this.pageContext.getOut().write( target );
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                    }

                }
                else if( Constant.BLOCK.BLOCK_TYPE_CUSTOM.equals( blockBean.getType() ) )
                {
                    // 自定义数据类型

                }

            }
            else
            {
                String lastUrl = null;

                String staticUrl = blockBean.getStaticUrl();

                if( StringUtil.isStringNotNull( staticUrl ) )
                {
                    lastUrl = Constant.CONTENT.URL_SEP + site.getSiteRoot()
                        + Constant.CONTENT.URL_SEP + site.getPublishRoot()
                        + Constant.CONTENT.URL_SEP + Constant.CONTENT.BLOCK_BASE
                        + Constant.CONTENT.URL_SEP + staticUrl;
                }
                else
                {
                    lastUrl = Constant.CONTENT.URL_SEP + site.getSiteRoot()
                        + Constant.CONTENT.URL_SEP + Constant.CONTENT.TEMPLATE_BASE
                        + Constant.CONTENT.URL_SEP + blockBean.getTemplateUrl();
                }

                // 动态访问中,直接使用模版
                HttpServletResponse responseWrapper = null;
                WrappedOutputStream contentOutputStream = null;
                try
                {
                    responseWrapper = new BufferedResponseWrapper( response );

                    request.getRequestDispatcher( lastUrl ).include( request, responseWrapper );
                    // 切记,一定要在获得output之前flushBuffer
                    responseWrapper.flushBuffer();
                    contentOutputStream = ( WrappedOutputStream ) responseWrapper.getOutputStream();

                    this.pageContext.getOut().write(
                        contentOutputStream.getBuffer().toString(
                            ( String ) request.getAttribute( "___jtopcms_tempplate_charset___" ) ) );

                }
                catch ( Exception e )
                {

                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        if( contentOutputStream != null )
                        {
                            contentOutputStream.close();
                        }
                    }
                    catch ( IOException e )
                    {

                        e.printStackTrace();
                    }
                }
            }
        }

        return SKIP_BODY;
    }

    public void setFlag( String flag )
    {
        this.flag = flag;
    }

}

package cn.com.mjsoft.cms.resources.html;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.StringUtil;

public class SiteFileResFullInfoTag extends TagSupport
{
    private static final long serialVersionUID = -1995482196495557818L;

    private static final String T_I = "t=i;";

    private static final String T_M = "t=m;";

    private static final String T_F = "t=f;";

    String res = "";

    String type = "";

    String key = "url";

    String objName = "Res";

    @SuppressWarnings( "unchecked" )
    public int doStartTag() throws JspException
    {
        Map result = new HashMap();

        if( !"".equals( res ) )
        {
            Long resId = StringUtil.getLongValue( res, -1 );

            if( resId.longValue() > 0 )
            {

                this.pageContext.setAttribute( objName, ResourcesService
                    .getInstance().retrieveSingleResourceBeanByResId( resId ) );

            }
            else
            {

                String siteId = null;
                try
                {
                    siteId = StringUtil.subString( res,
                        res.indexOf( "sid=" ) + 4, res.indexOf( ";", res
                            .indexOf( "sid=" ) + 4 ) );

                    SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                        .getEntry( Long.valueOf( StringUtil.getLongValue(
                            siteId, -1 ) ) );

                    if( site != null )
                    {

                        if( res.indexOf( T_I ) != -1 )
                        {
                            String reUrl = StringUtil.subString( res, res
                                .indexOf( "reUrl=" ) + 6, res.indexOf( ";", res
                                .indexOf( "reUrl=" ) + 6 ) );

                            result.put( "reUrl", reUrl );

                            result.put( "resize", site.getSiteImagePrefixUrl()
                                + StringUtil.replaceString( reUrl, "/",
                                    "/imgResize", false, false ) );

                            result.put( "url", site.getSiteImagePrefixUrl()
                                + reUrl );

                            result.put( "resId", StringUtil.subString( res, res
                                .indexOf( "id=" ) + 3, res.indexOf( ";", res
                                .indexOf( "id=" ) + 3 ) ) );

                            result.put( "imageW", StringUtil.subString( res,
                                res.indexOf( "iw=" ) + 3, res.indexOf( ";", res
                                    .indexOf( "iw=" ) + 3 ) ) );

                            result.put( "imageH", StringUtil.subString( res,
                                res.indexOf( "ih=" ) + 3, res.indexOf( ";", res
                                    .indexOf( "ih=" ) + 3 ) ) );
                        }
                        else if( res.indexOf( T_M ) != -1 )
                        {
                            String reUrl = StringUtil.subString( res, res
                                .indexOf( "reUrl=" ) + 6, res.indexOf( ";", res
                                .indexOf( "reUrl=" ) + 6 ) );

                            String coverReUrl = StringUtil.subString( res, res
                                .indexOf( "vc=" ) + 3, res.indexOf( ";", res
                                .indexOf( "vc=" ) + 3 ) );

                            result.put( "reUrl", reUrl );

                            result.put( "coverReUrl", coverReUrl );

                            result.put( "url", site.getSiteMediaPrefixUrl()
                                + reUrl );

                            result.put( "resId", StringUtil.subString( res, res
                                .indexOf( "id=" ) + 3, res.indexOf( ";", res
                                .indexOf( "id=" ) + 3 ) ) );

                            result.put( "mediaT", StringUtil.subString( res,
                                res.indexOf( "vt=" ) + 3, res.indexOf( ";", res
                                    .indexOf( "vt=" ) + 3 ) ) );

                            result.put( "mediaW", StringUtil.subString( res,
                                res.indexOf( "vw=" ) + 3, res.indexOf( ";", res
                                    .indexOf( "vw=" ) + 3 ) ) );

                            result.put( "mediaH", StringUtil.subString( res,
                                res.indexOf( "vh=" ) + 3, res.indexOf( ";", res
                                    .indexOf( "vh=" ) + 3 ) ) );

                            result.put( "mediaC", site.getSiteImagePrefixUrl()
                                + coverReUrl );
                        }
                        else if( res.indexOf( T_F ) != -1 )
                        {
                            String reUrl = StringUtil.subString( res, res
                                .indexOf( "reUrl=" ) + 6, res.indexOf( ";", res
                                .indexOf( "reUrl=" ) + 6 ) );

                            result.put( "reUrl", reUrl );

                            result.put( "id", StringUtil.subString( res, res
                                .indexOf( "id=" ) + 3, res.indexOf( ";", res
                                .indexOf( "id=" ) + 3 ) ) );

                            result.put( "fileN", StringUtil.subString( res, res
                                .indexOf( "fn=" ) + 3, res.indexOf( ";", res
                                .indexOf( "fn=" ) + 3 ) ) );

                            result.put( "fileUrl", site.getSiteFilePrefixUrl()
                                + reUrl );

                            result.put( "fileDown", site.getSiteUrl()
                                + "content/clientDf.do?id="
                                + StringUtil.subString( res, res
                                    .indexOf( "id=" ) + 3, res.indexOf( ";",
                                    res.indexOf( "id=" ) + 3 ) ) );
                        }

                    }
                }
                catch ( Exception e )
                {

                }

                this.pageContext.setAttribute( objName, result );
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        this.pageContext.removeAttribute( objName );

        return super.doEndTag();
    }

    public void setRes( String res )
    {
        this.res = res;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    public void setObjName( String objName )
    {
        this.objName = objName;
    }

}

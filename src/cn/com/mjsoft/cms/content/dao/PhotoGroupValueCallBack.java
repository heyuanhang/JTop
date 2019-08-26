package cn.com.mjsoft.cms.content.dao;

import java.util.Map;

import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.persistence.core.support.MapValueCallback;
import cn.com.mjsoft.framework.util.StringUtil;

public class PhotoGroupValueCallBack implements MapValueCallback
{
    // private boolean serverMode = false;

    private SiteGroupBean site = null;

    private Boolean apiMode = false;

    public PhotoGroupValueCallBack( SiteGroupBean site, boolean serverMode )
    {
        // this.serverMode = serverMode;
        this.site = site;
    }
    
    public PhotoGroupValueCallBack( SiteGroupBean site, boolean serverMode, Boolean apiMode )
    {
        // this.serverMode = serverMode;
        this.site = site;
        this.apiMode = apiMode;
    }

    @SuppressWarnings( "unchecked" )
    public void transformVlaue( Map result )
    {
        String fieldVal = ( String ) result.get( "url" );

        result.put( "cmsSysUrl", fieldVal );

        if( StringUtil.isStringNotNull( fieldVal ) )
        {
            String reUrl = StringUtil.subString( fieldVal, fieldVal.indexOf( "reUrl=" ) + 6,
                fieldVal.indexOf( ";", fieldVal.indexOf( "reUrl=" ) + 6 ) );

            result.put( "reUrl", reUrl );

            // if( serverMode )
            // {
            // String cmsDomain =
            // InitSiteGroupInfoBehavior.currentCmsServerInfoBean
            // .getDomainFullPath();
            //
            // result.put( "url", cmsDomain + "/" + site.getSiteRoot() + "/"
            // + site.getImageRoot() + "/" + reUrl );
            //
            // result.put( "resizeUrl", cmsDomain
            // + "/"
            // + site.getSiteRoot()
            // + "/"
            // + site.getImageRoot()
            // + "/"
            // + StringUtil.replaceString( reUrl, "/", "/imgResize",
            // false, false ) );
            // }
            // else

            if( site != null )
            {
                result.put( "url", site.getSiteImagePrefixUrlDMode() + reUrl );

                result.put( "resizeUrl", site.getSiteImagePrefixUrlDMode()
                    + StringUtil.replaceString( reUrl, "/", "/imgResize", false, false ) );
            }

            result.put( "resId", StringUtil.subString( fieldVal, fieldVal.indexOf( "id=" ) + 3,
                fieldVal.indexOf( ";", fieldVal.indexOf( "id=" ) + 3 ) ) );

            result.put( "width", StringUtil.subString( fieldVal, fieldVal.indexOf( "iw=" ) + 3,
                fieldVal.indexOf( ";", fieldVal.indexOf( "iw=" ) + 3 ) ) );

            result.put( "height", StringUtil.subString( fieldVal, fieldVal.indexOf( "ih=" ) + 3,
                fieldVal.indexOf( ";", fieldVal.indexOf( "ih=" ) + 3 ) ) );

        }
    }
}

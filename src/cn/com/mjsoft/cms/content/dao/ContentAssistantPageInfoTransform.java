package cn.com.mjsoft.cms.content.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.bean.ContentAssistantPageInfoBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentAssistantPageInfoTransform implements RowTransform
{
    private Map info = null;

    private ContentClassBean classBean;

    private SiteGroupBean site;

    public ContentAssistantPageInfoTransform( Map info,
        ContentClassBean classBean, SiteGroupBean site )
    {
        this.info = info;

        this.classBean = classBean;

        this.site = site;
    }

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentAssistantPageInfoBean bean = new ContentAssistantPageInfoBean();

        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setPageTitle( rs.getString( "pageTitle" ) );
        bean.setPos( Integer.valueOf( rs.getInt( "pos" ) ) );
        bean.setPageStaticUrl( rs.getString( "pageStaticUrl" ) );
        bean.setPageContent( rs.getString( "pageContent" ) );

        // url link
        // 每个内容必须对应有其所属的栏目
        if( classBean != null || classBean.getClassId().longValue() > 0 )
        {
            // HTML
            if( StringUtil.isStringNotNull( ( String ) info.get( "outLink" ) ) )
            {
                bean.setPageUrl( ( String ) info.get( "outLink" ) );
            }
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE
                .equals( classBean.getContentProduceType() ) )
            {
                bean.setPageUrl( site.getSiteUrl() + bean.getPageStaticUrl() );
            }//
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE
                .equals( classBean.getContentProduceType() ) )
            {
                String contentTemplateUrl = ( String ) info
                    .get( "especialTemplateUrl" );

                if( StringUtil.isStringNull( contentTemplateUrl ) )
                {
                    // 如果当前的单个内容没有特殊模斑则将取栏目共用模版
                    contentTemplateUrl = classBean.getContentTemplateUrl();
                }

                String endUrl = site.getSiteUrl()
                    + StringUtil.replaceString( contentTemplateUrl,
                        "{content-id}", ( ( Long ) info.get( "contentId" ) )
                            .toString(), false, false );

                String current = null;
                if( endUrl.indexOf( ".jsp?" ) != -1 )
                {
                    // 有参数
                    current = endUrl + "&pn=" + bean.getPos();

                }
                else
                {
                    current = endUrl + "?pn=" + bean.getPos();

                }

                bean.setPageUrl( current );
            }

        }

        return bean;
    }
}

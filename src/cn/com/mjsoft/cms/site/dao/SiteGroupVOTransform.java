package cn.com.mjsoft.cms.site.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.site.dao.vo.SiteGroup;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SiteGroupVOTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SiteGroup vo = new SiteGroup();

        vo.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        vo.setSiteFlag( rs.getString( "siteFlag" ) );
        vo.setSiteRoot( rs.getString( "siteRoot" ) );
        vo.setSiteName( rs.getString( "siteName" ) );
        vo.setSiteUrl( rs.getString( "siteUrl" ) );
        vo.setHome301Url( rs.getString( "home301Url" ) );
        
        vo.setOrderFlag( Integer.valueOf( rs.getInt( "orderFlag" ) ) );
        
        
        vo.setMobSiteUrl( rs.getString( "mobSiteUrl" ) );
        vo.setPadSiteUrl( rs.getString( "padSiteUrl" ) );
        vo.setPcVm( Integer.valueOf( rs.getInt( "pcVm" ) ) );
        vo.setMobVm( Integer.valueOf( rs.getInt( "mobVm" ) ) );
        vo.setPadVm( Integer.valueOf( rs.getInt( "padVm" ) ) );
        
        vo.setSiteDesc( rs.getString( "siteDesc" ) );
        vo.setHomePageTemplate( rs.getString( "homePageTemplate" ) );
        vo.setHomePageProduceType( Integer.valueOf( rs
            .getInt( "homePageProduceType" ) ) );
        vo.setFileRoot( rs.getString( "fileRoot" ) );
        vo.setImageRoot( rs.getString( "imageRoot" ) );
        vo.setMediaRoot( rs.getString( "mediaRoot" ) );
        vo.setPublishRoot( rs.getString( "publishRoot" ) );
        vo.setTemplateCharset( rs.getString( "templateCharset" ) );
        return vo;
    }

}

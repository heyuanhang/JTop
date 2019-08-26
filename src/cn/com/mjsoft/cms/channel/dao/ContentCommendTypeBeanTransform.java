package cn.com.mjsoft.cms.channel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentCommendTypeBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentCommendTypeBean bean = new ContentCommendTypeBean();

        bean.setCommendTypeId( Long.valueOf( rs.getLong( "commendTypeId" ) ) );
        bean.setCommendName( rs.getString( "commendName" ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setClassLinerFlag( rs.getString( "classLinerFlag" ) );
        bean
            .setChildClassMode( Integer.valueOf( rs.getInt( "childClassMode" ) ) );
        bean.setCommFlag( rs.getString( "commFlag" ) );
        bean.setTypeDesc( rs.getString( "typeDesc" ) );
        bean.setMustCensor( Integer.valueOf( rs.getInt( "mustCensor" ) ) );
        bean.setCreator( rs.getString( "creator" ) );
        bean.setSiteFlag( rs.getString( "siteFlag" ) );
        bean.setImageWidth( Integer.valueOf( rs.getInt( "imageWidth" ) ) );
        bean.setImageHeight( Integer.valueOf( rs.getInt( "imageHeight" ) ) );
        bean.setModelId( Long.valueOf( rs.getLong( "modelId" ) ) );
        
        String templateUrl = rs.getString( "listTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setListTemplateUrl( templateUrl );
        
        templateUrl = rs.getString( "mobListTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setMobListTemplateUrl( templateUrl );
        
        templateUrl = rs.getString( "padListTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setPadListTemplateUrl( templateUrl );

        bean.setListProduceType( Integer
            .valueOf( rs.getInt( "listProduceType" ) ) );
        bean.setListPublishRuleId( Long.valueOf( rs
            .getLong( "listPublishRuleId" ) ) );
        bean.setListStaticUrl( rs.getString( "listStaticUrl" ) );
        bean.setIsSpec( Integer.valueOf( rs.getInt( "isSpec" ) ) );

        return bean;
    }

}

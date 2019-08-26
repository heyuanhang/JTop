package cn.com.mjsoft.cms.content.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.content.bean.ContentMainInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ContentMainInfoBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentMainInfoBean bean = new ContentMainInfoBean();

        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setModelId( Long.valueOf( rs.getLong( "modelId" ) ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setRefCid( Long.valueOf( rs.getLong( "refCid" ) ) );
        bean.setLinkCid( Long.valueOf( rs.getLong( "linkCid" ) ) );
        bean.setTitle( rs.getString( "title" ) );
        bean.setSimpleTitle( rs.getString( "simpleTitle" ) );
        bean.setShortTitle( rs.getString( "shortTitle" ) );
        bean.setTitleStyle( rs.getString( "titleStyle" ) );
        bean.setSimpleTitleStyle( rs.getString( "simpleTitleStyle" ) );
        bean.setAuthor( rs.getString( "author" ) );
        bean.setCreator( rs.getString( "creator" ) );
        bean.setOrgCode( rs.getString( "orgCode" ) );
        bean.setBoost( rs.getFloat( "boost" ) );
        bean.setSummary( rs.getString( "summary" ) );
        bean.setAddTime( rs.getTimestamp( "addTime" ) );
        bean
            .setClickMonthCount( Long.valueOf( rs.getLong( "clickMonthCount" ) ) );
        bean.setClickWeekCount( Long.valueOf( rs.getLong( "clickWeekCount" ) ) );
        bean.setClickDayCount( Long.valueOf( rs.getLong( "clickDayCount" ) ) );
        bean.setClickCount( Long.valueOf( rs.getLong( "clickCount" ) ) );
        bean.setCommMonthCount( Long.valueOf( rs.getLong( "commMonthCount" ) ) );
        bean.setCommWeekCount( Long.valueOf( rs.getLong( "commWeekCount" ) ) );
        bean.setCommDayCount( Long.valueOf( rs.getLong( "commDayCount" ) ) );
        bean.setCommCount( Long.valueOf( rs.getLong( "commCount" ) ) );
        bean.setSupportCount( Long.valueOf( rs.getLong( "supportCount" ) ) );
        bean.setAgainstCount( Long.valueOf( rs.getLong( "againstCount" ) ) );
        // bean.setHomeImageSysReUrl( rs.getString( "homeImageSysReUrl" ) );
        // bean.setClassImageSysReUrl( rs.getString( "classImageSysReUrl" ) );
        // bean.setChannelImageSysReUrl( rs.getString( "channelImageSysReUrl" )
        // );
        // bean.setContentImageSysReUrl( rs.getString( "contentImageSysReUrl" )
        // );
        bean.setSystemHandleTime( rs.getString( "systemHandleTime" ) );
        bean.setEspecialTemplateUrl( rs.getString( "especialTemplateUrl" ) );
        bean.setStaticPageUrl( rs.getString( "staticPageUrl" ) );
        bean.setProduceType( Integer.valueOf( rs.getInt( "produceType" ) ) );
        bean.setCensorState( Integer.valueOf( rs.getInt( "censorState" ) ) );
        bean.setIsPageContent( Integer.valueOf( rs.getInt( "isPageContent" ) ) );
        bean.setIsSystemOrder( Integer.valueOf( rs.getInt( "isSystemOrder" ) ) );
        bean.setOrderIdFlag( Double.valueOf( rs.getDouble( "orderIdFlag" ) ) );
        bean.setKeywords( rs.getString( "keywords" ) );
        bean.setTagKey( rs.getString( "tagKey" ) );
        bean.setRelateIds( rs.getString( "relateIds" ) );
        bean.setRelateSurvey( rs.getString( "relateSurvey" ) );
        bean.setAppearStartDateTime( rs.getDate( "appearStartDateTime" ) );
        bean.setAppearEndDateTime( rs.getDate( "appearEndDateTime" ) );
        bean.setHomeImgFlag( Integer.valueOf( rs.getInt( "homeImgFlag" ) ) );
        bean.setClassImgFlag( Integer.valueOf( rs.getInt( "classImgFlag" ) ) );
        bean
            .setChannelImgFlag( Integer.valueOf( rs.getInt( "channelImgFlag" ) ) );
        bean
            .setContentImgFlag( Integer.valueOf( rs.getInt( "contentImgFlag" ) ) );
        bean.setCommendFlag( Integer.valueOf( rs.getInt( "commendFlag" ) ) );
        bean.setTypeFlag( rs.getString( "typeFlag" ) );
        bean.setTopFlag( Integer.valueOf( rs.getInt( "topFlag" ) ) );
        bean.setOtherFlag( Integer.valueOf( rs.getInt( "otherFlag" ) ) );
        bean.setAllowCommend( Integer.valueOf( rs.getInt( "allowCommend" ) ) );
        bean.setPubDateSysDT( Long.valueOf( rs.getLong( "pubDateSysDT" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }
}

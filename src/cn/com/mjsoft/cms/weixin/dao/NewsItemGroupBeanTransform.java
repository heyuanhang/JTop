package cn.com.mjsoft.cms.weixin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import cn.com.mjsoft.cms.weixin.bean.item.NewsItemGroupBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class NewsItemGroupBeanTransform implements RowTransform
{
    private Map prevBeanInfoMap = null;

    private boolean simpleMode = false;

    public NewsItemGroupBeanTransform()
    {

    }

    public NewsItemGroupBeanTransform( boolean simpleMode )
    {
        this.simpleMode = simpleMode;
    }

    public NewsItemGroupBeanTransform( Map prevBeanInfoMap )
    {
        this.prevBeanInfoMap = prevBeanInfoMap;
    }

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        NewsItemGroupBean bean = new NewsItemGroupBean();

        bean.setInfoId( Long.valueOf( rs.getLong( "infoId" ) ) );
        bean.setRowFlag( Long.valueOf( rs.getLong( "rowFlag" ) ) );
        bean.setRowOrder( Integer.valueOf( rs.getInt( "rowOrder" ) ) );
        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setTitle( rs.getString( "title" ) );
        bean.setImg( rs.getString( "img" ) );
        bean.setShowCover( Integer.valueOf( rs.getInt( "showCover" ) ) );

        if( !simpleMode )
        {
            bean.setUrl( rs.getString( "url" ) );

            bean.setSummary( rs.getString( "summary" ) );
        }

        bean.setAddTime( rs.getTimestamp( "addTime" ) );
        bean.setModelId( Long.valueOf( rs.getLong( "modelId" ) ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setCommendTypeId( Long.valueOf( rs.getLong( "commendTypeId" ) ) );
        bean.setOrderFlag( Integer.valueOf( rs.getInt( "orderFlag" ) ) );
        bean.setTypeFlag( rs.getString( "typeFlag" ) );
        bean.setCommendFlag( rs.getString( "commendFlag" ) );
        bean.setCommendMan( rs.getString( "commendMan" ) );
        bean.setCreateTime( rs.getTimestamp( "createTime" ) );
        bean.setWxId( Long.valueOf( rs.getLong( "wxId" ) ) );
        bean.setArticleText( rs.getString( "articleText" ) );
        bean.setIsTranSucc( Integer.valueOf( rs.getInt( "isTranSucc" ) ) );
        bean.setMediaId( rs.getString( "mediaId" ) );
        bean.setSiteFlag( rs.getString( "siteFlag" ) );
        bean.setWxCode( rs.getString( "wxCode" ) );

        if( prevBeanInfoMap != null )
        {

            bean.getRowInfoList().add( bean );

            Long rowFlag = Long.valueOf( rs.getLong( "rowFlag" ) );

            NewsItemGroupBean rowBean = ( NewsItemGroupBean ) prevBeanInfoMap
                .get( rowFlag );

            // 当没有rowbean存在,表示这行没有数据
            if( rowBean == null )
            {
                prevBeanInfoMap.put( rowFlag, bean );
            }
            else
            {
                // 当前行已经存在
                rowBean.getRowInfoList().add( bean );
            }
        }

        return bean;

    }
}

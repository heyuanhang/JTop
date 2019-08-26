package cn.com.mjsoft.cms.content.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import cn.com.mjsoft.cms.content.bean.ContentCommendPushInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ContentCommendPushInfoBeanTransform implements RowTransform
{
    private Map prevBeanInfoMap = null;

    private boolean simpleMode = false;

    public ContentCommendPushInfoBeanTransform()
    {

    }

    public ContentCommendPushInfoBeanTransform( boolean simpleMode )
    {
        this.simpleMode = simpleMode;
    }

    public ContentCommendPushInfoBeanTransform( Map prevBeanInfoMap )
    {
        this.prevBeanInfoMap = prevBeanInfoMap;
    }

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentCommendPushInfoBean bean = new ContentCommendPushInfoBean();

        bean.setInfoId( Long.valueOf( rs.getLong( "infoId" ) ) );
        bean.setRowFlag( Long.valueOf( rs.getLong( "rowFlag" ) ) );
        bean.setRowOrder( Integer.valueOf( rs.getInt( "rowOrder" ) ) );
        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setTitle( rs.getString( "title" ) );
        bean.setImg( rs.getString( "img" ) );

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
        bean.setSiteFlag( rs.getString( "siteFlag" ) );

        if( prevBeanInfoMap != null )
        {
          
            bean.getRowInfoList().add( bean );

            Long rowFlag = Long.valueOf( rs.getLong( "rowFlag" ) );

            ContentCommendPushInfoBean rowBean = ( ContentCommendPushInfoBean ) prevBeanInfoMap
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

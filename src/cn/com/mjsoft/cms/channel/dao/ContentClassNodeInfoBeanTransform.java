package cn.com.mjsoft.cms.channel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.channel.bean.ContentClassNodeInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ContentClassNodeInfoBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentClassNodeInfoBean bean = new ContentClassNodeInfoBean();
        bean.setLinearOrderFlag( rs.getString( "linearOrderFlag" ) );
        bean.setIsLeaf( Integer.valueOf( rs.getInt( "isLeaf" ) ) );
        bean.setLayer( Integer.valueOf( rs.getInt( "layer" ) ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setParent( Long.valueOf( rs.getLong( "parent" ) ) );
        bean.setIsLastChild( Integer.valueOf( rs.getInt( "isLastChild" ) ) );
        bean.setIsSpecial( Integer.valueOf( rs.getInt( "isSpecial" ) ) );
        
        return bean;
    }

}

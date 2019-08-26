package cn.com.mjsoft.cms.resources.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.resources.bean.ImgClassTreeItemBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ImgClassTreeItemBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ImgClassTreeItemBean bean = new ImgClassTreeItemBean();
        bean.setClassId( Long.valueOf( rs.getLong( "imgClassId" ) ) );
        bean.setName( rs.getString( "imgClassName" ) );
        bean.setParent( Long.valueOf( rs.getLong( "parent" ) ) );
        bean.setIsLeaf( Integer.valueOf( rs.getInt( "isLeaf" ) ) );

        return bean;
    }

}

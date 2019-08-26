package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SecurityResourceBeanWithParentInfoTransform implements
    RowTransform
{
    private SecurityResourceBeanTransform srbt = new SecurityResourceBeanTransform();

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SecurityResourceBean bean = ( SecurityResourceBean ) srbt.convertRow(
            rs, rowNum );

        bean.setParentResName( rs.getString( "parentName" ) );

        return bean;
    }

}

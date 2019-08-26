package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.security.bean.MemberSecurityResourceBean;

import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class MemberSecurityResourceBeanWithParentInfoTransform implements
    RowTransform
{
    private MemberSecurityResourceBeanTransform srbt = new MemberSecurityResourceBeanTransform();

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        MemberSecurityResourceBean bean = ( MemberSecurityResourceBean ) srbt
            .convertRow( rs, rowNum );

        bean.setParentResName( rs.getString( "parentName" ) );

        return bean;
    }

}

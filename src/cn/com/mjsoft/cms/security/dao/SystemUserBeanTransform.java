package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SystemUserBeanTransform implements RowTransform
{
    private Map infoMap;

    public SystemUserBeanTransform()
    {

    }

    public SystemUserBeanTransform( Map infoMap )
    {
        this.infoMap = infoMap;
    }

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SystemUserBean bean = new SystemUserBean();

        bean.setUserId( Long.valueOf( rs.getLong( "userId" ) ) );
        bean.setAddTime( rs.getTimestamp( "addTime" ) );
        bean.setPhone( rs.getString( "phone" ) );
        bean.setEmail( rs.getString( "email" ) );
        bean.setPassword( rs.getString( "password" ) );
        bean.setRemark( rs.getString( "remark" ) );
        bean.setUserName( rs.getString( "userName" ) );
        bean.setUserTrueName( rs.getString( "userTrueName" ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setManageOrgId( Long.valueOf( rs.getLong( "manageOrgId" ) ) );
        bean.setRelateOrgCode( rs.getString( "relateOrgCode" ) );

        bean.setWeixinName( rs.getString( "weixinName" ) );

        if( infoMap != null )
        {
            SystemUserBean existBean = ( SystemUserBean ) infoMap.get( bean
                .getUserId() );

            if( existBean != null )
            {
                // existBean.setSomeRoleName( existBean.getSomeRoleName() + ", "
                // + rs.getString( "roleName" ) );
            }
            else
            {
                // bean.setSomeRoleName( rs.getString( "roleName" ) );
                infoMap.put( bean.getUserId(), bean );
            }
        }

        return bean;
    }
}

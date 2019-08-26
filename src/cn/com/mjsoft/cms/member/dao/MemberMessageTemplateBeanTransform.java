package cn.com.mjsoft.cms.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.member.bean.MemberMessageTemplateBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class MemberMessageTemplateBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        MemberMessageTemplateBean bean = new MemberMessageTemplateBean();

        bean.setMtId( Long.valueOf( rs.getLong( "mtId" ) ) );
        bean.setTemplateName( rs.getString( "templateName" ) );
        bean.setTemplateTitle( rs.getString( "templateTitle" ) );
        bean.setTemplateContent( rs.getString( "templateContent" ) );
        bean.setCreator( rs.getString( "creator" ) );
        bean.setMtFlag( rs.getString( "mtFlag" ) );

        return bean;
    }
}

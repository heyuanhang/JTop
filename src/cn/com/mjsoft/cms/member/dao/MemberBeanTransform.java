package cn.com.mjsoft.cms.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class MemberBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        MemberBean bean = new MemberBean();

        bean.setMemberId( Long.valueOf( rs.getLong( "memberId" ) ) );
        bean.setMemberName( rs.getString( "memberName" ) );
        bean.setEmail( rs.getString( "email" ) );
        bean.setHeadPhoto( rs.getString( "headPhoto" ) );
        bean.setIsTrueEmail( Integer.valueOf( rs.getInt( "isTrueEmail" ) ) );
        bean.setPassword( rs.getString( "password" ) );
        bean.setIsTruePass( Integer.valueOf( rs.getInt( "isTruePass" ) ) );
        bean.setPhoneNumber( rs.getString( "phoneNumber" ) );
        bean.setIsTruePhone( Integer.valueOf( rs.getInt( "isTruePhone" ) ) );

        bean.setRegDt( rs.getTimestamp( "regDt" ).toString() );
        bean.setTrueName( rs.getString( "trueName" ) );
        bean.setCertType( Integer.valueOf( rs.getInt( "certType" ) ) );
        bean.setCertCode( rs.getString( "certCode" ) );
        bean.setCertPhotoP( rs.getString( "certPhotoP" ) );
        bean.setCertPhotoR( rs.getString( "certPhotoR" ) );
        bean.setIsTrueMan( Integer.valueOf( rs.getInt( "isTrueMan" ) ) );
        bean.setPrevLoginIp( rs.getString( "prevLoginIp" ) );
        bean.setPrevLoginArea( rs.getString( "prevLoginArea" ) );
        bean.setPrevLoginDt( rs.getTimestamp( "prevLoginDt" ) != null ? rs
            .getTimestamp( "prevLoginDt" ).toString() : "" );
        bean.setCurrLoginIp( rs.getString( "currLoginIp" ) );
        bean.setCurrLoginDt( rs.getTimestamp( "currLoginDt" ).toString() );
        bean.setMemLevel( Integer.valueOf( rs.getInt( "memLevel" ) ) );
        bean.setScore( Long.valueOf( rs.getLong( "score" ) ) );
        bean.setUseStatus( Integer.valueOf( rs.getInt( "useStatus" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setIsFirst( Integer.valueOf( rs.getInt( "isFirst" ) ) );
        bean.setLoginSuccessCount( Long.valueOf( rs
            .getLong( "loginSuccessCount" ) ) );

        return bean;

    }
}

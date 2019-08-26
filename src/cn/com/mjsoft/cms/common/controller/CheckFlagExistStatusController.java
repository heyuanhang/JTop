package cn.com.mjsoft.cms.common.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.dao.ValiDao;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.framework.persistence.core.JdbcInstrument;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/common" )
public class CheckFlagExistStatusController
{
    public static PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    public static ValiDao valiDao = new ValiDao( mysqlEngine );

    @ResponseBody
    @RequestMapping( value = "/sysFlagExist.do", method = { RequestMethod.POST } )
    public Object sysFlagExist( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String target = ( String ) params.get( "target" );

        String val = ( String ) params.get( "val" );
    

        val = val.trim();

        Integer count = 0;

        // 由于逻辑层简单,故在这里没有使用service
        if( "advertPos".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "advert_position", "posFlag", val );
        }
        else if( "advertContent".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "advert_content", "adFlag", val );
        }
        else if( "surveyGroup".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "survey_group", "flagName", val );
        }
        else if( "siteLinkType".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "friend_site_link_type", "typeFlag", val );
        }
        else if( "contentClass".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "contentclass", "classFlag", val );
        }
        else if( "mutiContentClass".equals( target ) )
        {
            String classNameInfo = ( String ) params.get( "val" );

            List classNameList = StringUtil.changeStringToList( classNameInfo, "," );

            String name = null;

            String flag = null;

            String[] temp = null;

            StringBuffer buf = new StringBuffer();

            for ( int i = 0; i < classNameList.size(); i++ )
            {
                temp = StringUtil.split( ( String ) classNameList.get( i ), "@" );

                if( temp.length < 2 )
                {
                    continue;
                }

                name = temp[0];

                flag = temp[1];

                if( StringUtil.isStringNull( name ) || StringUtil.isStringNull( flag ) )
                {
                    continue;
                }

                count = valiDao.querySystemTableFlagExist( "contentclass", "classFlag", flag );

                if( count.intValue() > 0 )
                {
                    buf.append( temp[1] + "<br/>" );
                }
            }

            return buf.toString();
        }
        else if( "contentType".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "content_type", "typeFlag", val );
        }
        else if( "commType".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "content_commend_type", "commFlag", val );
        }
        else if( "guestbookCfg".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "guestbook_config", "cfgFlag", val );
        }
        else if( "block".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "block_info", "flag", val );
        }
        else if( "tagType".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "tag_type", "flag", val );
        }
        else if( "sysUser".equals( target ) )
        {
            if( "admin".equalsIgnoreCase( val ) )
            {
                count = Integer.valueOf( 1 );
            }
            else
            {
                count = valiDao.querySystemTableFlagExist( "systemuser", "userName", val );
            }
        }
        else if( "sysOrg".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "system_organization", "orgFlag", val );
        }
        else if( "sysSite".equals( target ) )
        {
            if( "core".equalsIgnoreCase( val ) || "common".equalsIgnoreCase( val )
                || "sys_temp".equalsIgnoreCase( val ) || "WEB-INF".equalsIgnoreCase( val ) )
            {
                count = Integer.valueOf( 1 );
            }
            else
            {
                count = valiDao.querySystemTableFlagExist( "site_group", "siteFlag", val );
            }
        }
        else if( "sysSiteUrl".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "site_group", "siteUrl", val );
        }
        else if( "model".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "model", "modelSign", val );
        }
        else if( "modelField".equals( target ) )
        {
            Connection conn = mysqlEngine.openConnection();

            // 内容主表字段检查
            PreparedStatement ps = conn
                .prepareStatement( "select * from content_main_info where contentId=-1111" );

            ResultSet rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            for ( int i = 1; i <= columnCount; i++ )
            {
                String key = rsmd.getColumnLabel( i );

                if( key.equals( val ) )
                {
                    count = Integer.valueOf( 1 );
                    break;
                }
            }

            JdbcInstrument.closeResultSet( rs );
            JdbcInstrument.closeStatement( ps );
            if( count.intValue() == 0 )
            {
                // 广告配置
                ps = conn.prepareStatement( "select * from advert_config where configId=-1111" );

                rs = ps.executeQuery();

                rsmd = rs.getMetaData();
                columnCount = rsmd.getColumnCount();

                for ( int i = 1; i <= columnCount; i++ )
                {
                    String key = rsmd.getColumnLabel( i );

                    if( key.equals( val ) )
                    {
                        count = Integer.valueOf( 1 );
                        break;
                    }
                }

                JdbcInstrument.closeResultSet( rs );
                JdbcInstrument.closeStatement( ps );
            }

            if( count.intValue() == 0 )
            {
                // 广告内容
                ps = conn.prepareStatement( "select * from advert_content where advertId=-1111" );

                rs = ps.executeQuery();

                rsmd = rs.getMetaData();
                columnCount = rsmd.getColumnCount();

                for ( int i = 1; i <= columnCount; i++ )
                {
                    String key = rsmd.getColumnLabel( i );

                    if( key.equals( val ) )
                    {
                        count = Integer.valueOf( 1 );
                        break;
                    }
                }

                JdbcInstrument.closeResultSet( rs );
                JdbcInstrument.closeStatement( ps );
            }

            if( count.intValue() == 0 )
            {
                // 留言内容
                ps = conn.prepareStatement( "select * from guestbook_main_info where gbId=-1111" );

                rs = ps.executeQuery();

                rsmd = rs.getMetaData();
                columnCount = rsmd.getColumnCount();

                for ( int i = 1; i <= columnCount; i++ )
                {
                    String key = rsmd.getColumnLabel( i );

                    if( key.equals( val ) )
                    {
                        count = Integer.valueOf( 1 );
                        break;
                    }
                }

                JdbcInstrument.closeResultSet( rs );
                JdbcInstrument.closeStatement( ps );
            }

            JdbcInstrument.closeConnection( conn );

            // 自定义字段表
            if( count.intValue() == 0 )
            {
                count = valiDao
                    .querySystemTableFlagExist( "model_filed_metadata", "filedSign", val );
            }

        }

        else if( "templateStyle".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "template_style", "styleFlag", val );
        }

        else if( "apiCfg".equals( target ) )
        {
            count = valiDao.querySystemTableFlagExist( "system_api_config", "flowPath", val );
        }

        return count;

    }

}

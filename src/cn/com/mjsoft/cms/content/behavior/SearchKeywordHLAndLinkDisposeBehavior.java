package cn.com.mjsoft.cms.content.behavior;

import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.util.StringUtil;

@SuppressWarnings( "unchecked" )
public class SearchKeywordHLAndLinkDisposeBehavior implements Behavior
{

    public Object operation( Object p1, Object[] p2 )
    {
        Map<String, String> params = ( Map<String, String> ) p1;

        // 获取自定义模型数据
        List filedBeanList = ( List ) p2[1];

        ModelFiledInfoBean bean = null;

        String val = null;

        String keywords = params.get( "keywords" );

        List<String> ks = StringUtil.changeStringToList( keywords, " " );

        for ( int j = 0; j < filedBeanList.size(); j++ )
        {
            bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

            if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
            {
                val = params.get( bean.getFieldSign() );

                // clear
                String contentOld = val;

                int ss = contentOld.indexOf( "<a " );

                int se = contentOld.indexOf( "</a>", ss );

                String old = null;

                while ( ss > 0 && se > 0 )
                {
                    String as = contentOld.substring( ss, se + 4 );

                    old = as;

                    if( as.indexOf( "goSysLinkSearch" ) != -1 )
                    {
                        as = as.replaceAll( "</a>", "" );

                        String key = as.substring( as.indexOf( ">" ) + 1, as.length() );

                        val = StringUtil.replaceString( val, old, key );

                    }

                    ss = contentOld.indexOf( "<a ", se );

                    se = contentOld.indexOf( "</a>", ss );
                }

                for ( String key : ks )
                {
                    // String ekey = SystemSafeCharUtil.encode( key );

                    String sa = "<a name=\"sys_selink\" href=\"javascript:goSysLinkSearch('" + key
                        + "');\">" + key + "</a>";

                    val = ( ( String ) val ).replaceAll( key, sa );
                    // val = StringUtil.replaceString( ( String ) val, key, sa
                    // );
                }

                params.put( bean.getFieldSign(), ( String ) val );

                val = params.get( bean.getFieldSign() + "_jtop_sys_hidden_temp_html" );

            }
        }
        return null;
    }

    public static void main( String args[] )
    {}
}

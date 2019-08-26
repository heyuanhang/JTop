package cn.com.mjsoft.cms.member.behavior;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SecTokenModeCheckBehavior implements Behavior
{

    public Object operation( Object target, Object[] param )
    {
        HttpServletRequest request = ( HttpServletRequest ) target;

        String tet = request.getHeader( "sysSign" );

        if( tet == null )
        {
            tet = request.getParameter( "sysSign" );
        }

        String testETokenSign = tet;

        String suid = request.getHeader( "sysUid" );

        if( suid == null )
        {
            suid = request.getParameter( "sysUid" );
        }

        Long sysUserId = StringUtil.getLongValue( suid, 0 );

        String innerEToken = SecuritySessionKeeper
            .getETokenByUserId( sysUserId );

        //  加密算法作为接口, 过时时间可配置,增加cmd执行时间撮模式
        if( StringUtil.isStringNotNull( testETokenSign ) && testETokenSign.equals( innerEToken ) )
        {
            return sysUserId;
        }

        return null;
    }
}

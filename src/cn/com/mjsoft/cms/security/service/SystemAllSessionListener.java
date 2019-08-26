package cn.com.mjsoft.cms.security.service;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.filter.SecuritySessionDisposeFiletr;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.web.engine.InterceptFilter;

public class SystemAllSessionListener implements HttpSessionListener
{

    public void sessionCreated( HttpSessionEvent event )
    {

    }

    public void sessionDestroyed( HttpSessionEvent event )
    {
        HttpSession session = event.getSession();

        SecuritySession securitySession = ( SecuritySession ) session
            .getAttribute( SecuritySessionDisposeFiletr.SECURITY_SESSION_FLAG );

        System.err.println( "______SESSION_OPM________<<<<<<>>>>>>_:"
            + securitySession );

        if( securitySession != null )
        {
            Auth auth = securitySession.getAuth();

            if( securitySession.isManager() )
            {
                Map secMap = ( Map ) session.getServletContext().getAttribute(
                    SecuritySession.SEC_APP_FLAG );

                secMap.remove( securitySession.getUUID() );

                if( auth != null )
                {
                    InterceptFilter.removeSysMangerAct( auth.getIdentity() );
                }
            }
            else
            {
                if( auth != null )
                {
                    SecuritySessionKeeper.removeMemberAct( auth.getIdentity() );
                }
            }

        }
    }
}

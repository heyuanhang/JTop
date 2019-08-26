package cn.com.mjsoft.cms.common.spring.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class SessionForURLFilter extends OncePerRequestFilter
{

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain ) throws ServletException, IOException
    {
        if( request.isRequestedSessionIdFromURL() )
        {
            String jsessionId = request.getRequestedSessionId();
            if( ( jsessionId != null ) && ( !"".equals( jsessionId.trim() ) ) )
            {
                request.setAttribute( CookieHttpSessionAndUrlSessionIdStrategy.OLDEST_URL_SESSION_ID_ATTRIBUTE_NAME,
                    jsessionId );
            }
        }

        filterChain.doFilter( request, response );
    }

}

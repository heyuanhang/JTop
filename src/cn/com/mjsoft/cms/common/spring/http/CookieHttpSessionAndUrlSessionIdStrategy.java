package cn.com.mjsoft.cms.common.spring.http;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CookieHttpSessionAndUrlSessionIdStrategy extends CookieHttpSessionStrategyNotFinal
{

    public static final String OLDEST_URL_SESSION_ID_ATTRIBUTE_NAME = "__OLDEST_URL_SESSION_ID_ATTRIBUTE_NAME__";

    @Override
    public Map<String, String> getSessionIds( HttpServletRequest request )
    {
        Map<String, String> result = super.getSessionIds( request );
        if( result.isEmpty() )
        {
            String jsessionId = ( String ) request
                .getAttribute( OLDEST_URL_SESSION_ID_ATTRIBUTE_NAME );

            if( ( jsessionId != null ) && ( !"".equals( jsessionId.trim() ) ) )
            {
                result.put( DEFAULT_ALIAS, jsessionId );
            }
        }
        return result;
    }
}

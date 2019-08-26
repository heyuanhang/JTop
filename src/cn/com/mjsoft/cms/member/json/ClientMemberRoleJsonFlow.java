package cn.com.mjsoft.cms.member.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientMemberRoleJsonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientMemberRoleJsonFlow.class );

    private static MemberService memberService = MemberService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String memberId = StringUtil.notNull( ( String ) params
            .get( "memberId" ) );

        if( !"".equals( memberId ) )
        {
            Long memId = Long.valueOf( StringUtil.getLongValue( memberId, -1 ) );

            return ( JSON.toJSONString( memberService
                .retrieveMemeberRole( memId ) ) );
        }
        else
        {

            Long sysTokenUserId = ( Long ) SystemConfiguration.getInstance()
                .getSystemConfig().getTokenSecurityCheckBehavior().operation(
                    request, null );

            SecuritySession session = null;

            if( sysTokenUserId != null )
            {
                String innerEToken = SecuritySessionKeeper
                    .getETokenByUserId( sysTokenUserId );

                session = SecuritySessionKeeper
                    .getSecSessionByUserId( SecuritySessionKeeper
                        .getUserIdBySecEToken( innerEToken ) );
            }
            else
            {
                session = SecuritySessionKeeper.getSecuritySession();
            }

            if( session != null && session.getAuth() != null
                && !session.isManager() && session.getMember() != null )
            {
                List result = new ArrayList();

                Collections
                    .addAll( result, session.getAuth().getUserRoleCopy() );

                return ( JSON.toJSONString( result ) );

            }

            return ( JSON.toJSONString( "{empty:true}" ) );
        }

    }
}

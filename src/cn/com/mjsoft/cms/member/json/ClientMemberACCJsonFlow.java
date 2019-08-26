package cn.com.mjsoft.cms.member.json;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.member.bean.MemberAccRuleBean;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientMemberACCJsonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientMemberACCJsonFlow.class );

    private static SecurityService securityService = SecurityService
        .getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String classId = StringUtil
            .notNull( ( String ) params.get( "classId" ) );

        Long cid = Long.valueOf( StringUtil.getLongValue( classId, -1 ) );

        MemberAccRuleBean acBean = null;

        boolean acc = true;

        if( cid.longValue() > 0 )
        {
            acBean = securityService.retrieveSingleMemberAccRule( cid );
        }

        if( acBean != null && acBean.getAccRuleId().longValue() > 0 )
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

            if( session == null || session.getAuth() == null
                || session.isManager() || session.getMember() == null )
            {
                acc = false;
            }
            else
            {

                Set authorizationRoleIdSet = acBean.getRoleIdSet();

                Role[] rs = session.getAuth().getUserRole();

                boolean accRoleOk = false;

                for ( int i = 0; i < rs.length; i++ )
                {
                    if( authorizationRoleIdSet.contains( ( ( Role ) rs[i] )
                        .getRoleID() ) )
                    {
                        accRoleOk = true;
                        break;
                    }
                }

                MemberBean member = ( MemberBean ) session.getMember();

                int lever = member.getMemLevel().intValue();

                long score = member.getScore().longValue();

                // 确定是否可访问
                boolean accLevelOk = ( lever >= acBean.getMinLever().intValue() )
                    ? true : false;

                boolean accScoreOk = ( score >= acBean.getMinScore()
                    .longValue() ) ? true : false;

                if( acBean.getEft().intValue() == 1 )
                {
                    if( !( accRoleOk || accLevelOk || accScoreOk ) )
                    {
                        acc = false;
                    }
                }
                else
                {
                    if( !( accRoleOk && accLevelOk && accScoreOk ) )
                    {
                        acc = false;
                    }
                }
            }
        }

        return ( JSON.toJSONString( "{acc:" + acc + "}" ) );

    }
}

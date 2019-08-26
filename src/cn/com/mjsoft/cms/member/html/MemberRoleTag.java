package cn.com.mjsoft.cms.member.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class MemberRoleTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger( MemberRoleTag.class );

    private static MemberService memberService = MemberService.getInstance();

    private String userId = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {

        if( !"".equals( userId ) )
        {
            Long memId = Long.valueOf( StringUtil.getLongValue( userId, -1 ) );

            return memberService.retrieveMemeberRole( memId );
        }
        else
        {
            SecuritySession session = SecuritySessionKeeper
                .getSecuritySession();

            if( session != null && session.getAuth() != null
                && !session.isManager() && session.getMember() != null )
            {
                List result = new ArrayList();

                Collections
                    .addAll( result, session.getAuth().getUserRoleCopy() );

                return result;
            }

            return null;
        }
    }

    protected String returnPutValueName()
    {
        return "MemberRole";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {

        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.PAGE_RANFE;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

}

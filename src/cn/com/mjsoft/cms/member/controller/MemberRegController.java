package cn.com.mjsoft.cms.member.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.html.ParamUtilTag;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.dao.vo.Member;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.crypto.PasswordUtility;
import cn.com.mjsoft.framework.security.crypto.util.EncodeOne;
import cn.com.mjsoft.framework.security.headstream.IUser;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class MemberRegController
{
    private static MemberService memberService = MemberService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/regMember.do", method = { RequestMethod.POST } )
    public Object regMember( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Member member = ( Member ) ServletUtil.getValueObject( request, Member.class );

        // DCWY
        member.setPhoneNumber( member.getMemberName() );
        params.put( "phoneNumber", member.getMemberName() );

        Random r = new Random();

       // member.setMemberName( "mem" + r.nextInt( 100000000 ) );
       // params.put( "memberName", member.getMemberName() );

        // 验证码

        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            // return "-1";
        }
        else
        {
            ssn.removeAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
        }

        // site
        SiteGroupBean currentSiteBean = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

        // 是否关闭注册
        if( Constant.COMMON.OFF.equals( currentSiteBean.getAllowMemberReg() ) )
        {
            // 禁止注册
            return "0";
        }

        // 转码

        member.setMemberName( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getMemberName() ) ) );

        member.setTrueName( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getTrueName() ) ) );

        member.setPhoneNumber( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getPhoneNumber() ) ) );

        member.setEmail( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getEmail() ) ) );

        /**
         * 全部转码
         */
        ServiceUtil.decodeAndDisposeMapParam( params );

        params.put( "isTruePhone", Constant.COMMON.OFF );
        // 暂时注释 输入了手机号和验证码的才做验证
        // if(StringUtil.isStringNotNull(phone)&&StringUtil.isStringNotNull(checkPCode)){
        // int status = memberService.validateMemberMbNumber( phone,
        // checkPCode);
        //
        // if( status == 1 )
        // {
        // params.put( "isTruePhone", Constant.COMMON.ON );
        //
        // }else{
        // return this.responseAjaxTextMessage( "手机验证码无效!" );
        // }
        //                
        // }

        // return this.responseAjaxTextMessage( status + "" );

        // 用户名检查重复
        boolean nameExist = memberService
            .checkMemberUserName( ( String ) params.get( "memberName" ) );

        if( nameExist )
        {
            // 此会员名称已被注册!
            return "-2";
        }

        // 手机检查重复
        nameExist = memberService.checkMemberUserName( ( String ) params.get( "phoneNumber" ) );

        if( nameExist )
        {
            // 此会员名称已被注册!
            return "-2";
        }

        // email检查重复 如果未输入邮箱则不验证
        if( StringUtil.isStringNotNull( ( String ) params.get( "email" ) ) )
        {
            boolean mailExist = memberService
                .checkMemberUserMail( ( String ) params.get( "email" ) );

            if( mailExist )
            {// 此邮件地址已被注册!
                return "-3";
            }

        }

        // 密码检查
        String pw = ( String ) params.get( "password" );
        String checkPW = ( String ) params.get( "checkPassword" );

        if( StringUtil.isStringNull( pw ) || !pw.equals( checkPW ) )
        {
            // 密码不匹配
            return "-4";
        }

        member.setPassword( PasswordUtility.encrypt( pw ) );

        // 强制设定手机和mail不通过,强制身份为没有验证

        member.setIsTrueEmail( Constant.COMMON.OFF );
        member.setIsTrueMan( Constant.COMMON.ON );
        member.setIsTruePass( Constant.COMMON.ON );
        member.setIsTruePhone( Constant.COMMON.ON );

        // 注册时间
        member.setRegDt( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        // 上次登陆IP和时间初始化
        member.setCurrLoginIp( IPSeeker.getIp( request ) );

        member.setCurrLoginDt( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        member.setSiteId( currentSiteBean.getSiteId() );

        // 扩展模型信息
        List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( currentSiteBean
            .getExtMemberModelId() );

        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( currentSiteBean.getExtMemberModelId() );

        DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( currentSiteBean
            .getExtMemberModelId() );

        UpdateState us = memberService.addMemberBasicInfo( currentSiteBean, member, model,
            filedBeanList, sqlCodeBean, params );

        if( us.haveKey() )
        {

            // 记录第三方注册

            if( "true".equals( params.get( "thirdReg" ) ) )
            {

                String openId = ( String ) request.getSession().getAttribute( "qq_openid" );

                String weiboId = ( String ) request.getSession().getAttribute( "weibo_user_id" );

                if( StringUtil.isStringNotNull( openId ) )
                {

                    memberService.addThirdLoginInfo( Long.valueOf( us.getKey() ),
                        Constant.MEMBER.QQ_LOGIN, openId );

                }
                else if( StringUtil.isStringNotNull( weiboId ) )
                {

                    memberService.addThirdLoginInfo( Long.valueOf( us.getKey() ),
                        Constant.MEMBER.WEIBO_LOGIN, weiboId );

                }
            }
        }

        request.setAttribute( "fromReg", Boolean.TRUE );
        request.setAttribute( "memberName", params.get( "memberName" ) );
        request.setAttribute( "password", pw );

        // 设置flow执行成功标志,传递拦截器所需参数

        request.setAttribute( "successFlag", Boolean.TRUE );

        request.setAttribute( "memberId", us.getKey() );

        if( "true".equals( params.get( "firstLogin" ) ) )
        {
            return ServletUtil.forward( "/member/memberLogin.do" );
        }

        return "1";

    }

    @ResponseBody
    @RequestMapping( value = "/relateMember.do", method = { RequestMethod.POST } )
    public Object relateMember( HttpServletRequest request, HttpServletResponse response )
    {

        Map params = ServletUtil.getRequestInfo( request );

        Member member = ( Member ) ServletUtil.getValueObject( request, Member.class );

        // 验证码

        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            return "-1";
        }
        else
        {
            ssn.removeAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
        }

        member.setMemberName( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getMemberName() ) ) );

        member.setTrueName( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getTrueName() ) ) );

        member.setPhoneNumber( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getPhoneNumber() ) ) );

        member.setEmail( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getEmail() ) ) );

        params.put( "password", SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getPassword() ) ) );

        // 获取会员
        IUser memberUser = memberService.obtainUser( member.getMemberName() );

        if( memberUser == null )
        {

            memberUser = memberService.obtainUserByEmail( member.getEmail() );
        }

        if( memberUser == null )
        {
            // 不存在这样的会员，用户名称和邮件都无法匹配
            return "-2";
        }

        if( !PasswordUtility.match( member.getPassword(), memberUser.getPassword() ) )
        {
            // 密码不匹配
            return "-3";
        }

        // 关联
        String openId = ( String ) request.getSession().getAttribute( "qq_openid" );

        String weiboId = ( String ) request.getSession().getAttribute( "weibo_user_id" );

        if( StringUtil.isStringNotNull( openId ) )
        {

            memberService.addThirdLoginInfo( ( Long ) memberUser.getIdentity(),
                Constant.MEMBER.QQ_LOGIN, openId );

        }
        else if( StringUtil.isStringNotNull( weiboId ) )
        {

            memberService.addThirdLoginInfo( ( Long ) memberUser.getIdentity(),
                Constant.MEMBER.WEIBO_LOGIN, weiboId );

        }

        request.setAttribute( "fromThird", Boolean.TRUE );

        if( "true".equals( params.get( "firstLogin" ) ) )
        {
            return ServletUtil.forward( "/member/memberLogin.do" );
        }

        // 设置成功标志

        request.setAttribute( "successFlag", Boolean.TRUE );

        return "1";

    }

    @ResponseBody
    @RequestMapping( value = "/editMemberBasic.do", method = { RequestMethod.POST } )
    public Object editMemberBasic( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        Member member = ( Member ) ServletUtil.getValueObject( request, Member.class );

        String censorMode = ( String ) params.get( "censorMode" );

        if( "true".equals( censorMode ) )
        {
            Long memId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

            Integer isTrueMan = StringUtil.getIntValue( ( String ) params.get( "trueMan" ), 0 );

            memberService.censorMemberOK( memId, isTrueMan );

            return "success";
        }

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session == null || session.getAuth() == null || session.isManager()
            || session.getMember() == null )
        {
            // 非会员不可操作
            return "0";
        }

        // 验证码

        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            // DCWY
            // return "-1";
        }
        else
        {
            ssn.removeAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
        }

        member.setTrueName( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getTrueName() ) ) );

        member.setPhoneNumber( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getPhoneNumber() ) ) );

        member.setEmail( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
            .decodeFromWeb( member.getEmail() ) ) );

        /**
         * 全部转码
         */
        ServiceUtil.decodeAndDisposeMapParam( params );

        // 获取会员
        MemberBean memberUser = memberService.retrieveSingleMemberBean( ( Long ) session.getAuth()
            .getIdentity() );

        if( memberUser == null )
        {
            // 不存在这样的会员
            return "-2";
        }

        // email检查重复
        String newEMail = ( String ) params.get( "email" );

        if( StringUtil.isStringNotNull( newEMail ) && !newEMail.equals( memberUser.getEmail() ) )
        {
            MemberBean mem = memberService.retrieveSingleMemberBeanByEMail( newEMail );

            if( mem != null && !mem.getMemberId().equals( ( Long ) session.getAuth().getIdentity() ) )
            {// 此邮件地址已被注册!
                return "-3";
            }

            params.put( "sysMailChange", "true" );

        }

        // email检查重复
        String nmn = ( String ) params.get( "memberName" );

        if( StringUtil.isStringNotNull( nmn ) && !nmn.equals( memberUser.getMemberName() ) )
        {
            MemberBean mem = memberService.retrieveSingleMemberBean( nmn );

            if( mem != null && !mem.getMemberId().equals( ( Long ) session.getAuth().getIdentity() ) )
            {
                // 此名称已被注册!
                return "-4";
            }

            params.put( "sysMNChange", "true" );

        }

        SiteGroupBean currentSiteBean = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

        // 扩展模型信息
        List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( currentSiteBean
            .getExtMemberModelId() );

        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( currentSiteBean.getExtMemberModelId() );

        DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( currentSiteBean
            .getExtMemberModelId() );

        params.put( "memberId", memberUser.getMemberId() );

        memberService.editMemberBasicInfo( memberUser, model, filedBeanList, sqlCodeBean, params );

        // 设置成功标志

        request.setAttribute( "successFlag", Boolean.TRUE );

        return "1";

    }

    @ResponseBody
    @RequestMapping( value = "/changeHeadPhoto.do", method = { RequestMethod.POST } )
    public Object changeHeadPhoto( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session == null || session.getAuth() == null || session.isManager()
            || session.getMember() == null )
        {
            // 非会员不可操作
            return "0";
        }

        // 验证码

        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            //return "-1";
        }
        else
        {
            ssn.removeAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
        }

        int status = memberService.changeMemberHeadPhoto( params );

        if( status == 1 )
        {
            // 设置成功标志
            request.setAttribute( "successFlag", Boolean.TRUE );

        }

        return status + "";

    }

    @ResponseBody
    @RequestMapping( value = "/changePW.do", method = { RequestMethod.POST } )
    public Object changePW( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session == null || session.getAuth() == null || session.isManager()
            || session.getMember() == null )
        {
            // 非会员不可操作
            return "0";
        }

        // 验证码

        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            return "-1";
        }
        else
        {
            ssn.removeAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
        }

        String oldPw = ( String ) params.get( "oldPw" );
        String newPw = ( String ) params.get( "newPw" );
        String checkNewPw = ( String ) params.get( "checkNewPw" );

        int status = memberService.changeMemberLoginPassword( oldPw, newPw, checkNewPw );

        return status + "";

    }

    @ResponseBody
    @RequestMapping( value = "/memberSendVaEmail.do", method = { RequestMethod.POST } )
    public Object memberSendVaEmail( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session == null || session.getAuth() == null || session.isManager()
            || session.getMember() == null )
        {
            // 非会员不可操作
            return "0";
        }

        // 验证码

        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            return "-1";
        }
        else
        {
            ssn.removeAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String status = memberService.sendMemberValiEmail( site, params );

        return status;

    }

    @RequestMapping( value = "/memberEmailActive.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    public Object memberEmailActive( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Object[] res = memberService.validateMemberEmail( params );

        Integer status = ( ( Integer ) res[0] );

        Map memberInfo = ( Map ) res[1];

        String byt = "";
        if( memberInfo == null || memberInfo.isEmpty() )
        {
            status = Integer.valueOf( -2 );
        }
        else
        {
            // 加密
            byte[] encryptResult = EncodeOne.encryptAES( memberInfo.get( "memberId" ).toString(),
                ParamUtilTag.getSalt( "C" ) );

            byt = EncodeOne.encode16( encryptResult ).toLowerCase();
        }

        // if( status.intValue() == 1 )
        // {
        //
        // // 设置成功标志
        //
        // context.put( "successFlag", Boolean.TRUE );
        //
        // context.put( "memberId", memberInfo.get( "memberId" ) );
        //
        // paramMap.put( "id", byt );
        //
        // return "toCheckMailSuccess";
        //
        // }
        // else
        // {

        Map paramMap = new HashMap();

        // 加密
        byte[] encryptResult = EncodeOne
            .encryptAES( status.toString(), ParamUtilTag.getSalt( "F" ) );

        String bytStatus = EncodeOne.encode16( encryptResult ).toLowerCase();

        paramMap.put( "status", bytStatus );

        if( memberInfo != null )
        {
            Integer isTrueEmail = ( Integer ) memberInfo.get( "isTrueEmail" );

            if( Constant.COMMON.ON.equals( isTrueEmail ) )
            {

                paramMap.put( "id", byt );
            }
        }

        // 设置成功标志

        request.setAttribute( "successFlag", Boolean.TRUE );

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( res[2] );

        return ServletUtil.redirect( site.getSiteUrl() + site.getMailRegBackUri(), paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/sendResetPwMail.do", method = { RequestMethod.POST } )
    public Object sendResetPwMail( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String targetMail = ( String ) params.get( "email" );

        // 验证码

        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            return "-1";
        }
        else
        {
            ssn.removeAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
        }

        targetMail = SystemSafeCharUtil.decodeFromWeb( targetMail );

        Object[] status = memberService.sendResetMemberPasswordMail( targetMail );

        Map memInfo = ( Map ) status[1];

        ssn.setAttribute( "forgetPwMember", memInfo );

        return status[0].toString();

    }

    @RequestMapping( value = "/checkResetReq.do", method = { RequestMethod.POST } )
    public Object checkResetReq( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String key = ( String ) params.get( "key" );

        String[] keyValArray = memberService.decryptKey( key );

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( Long.valueOf( StringUtil.getLongValue( keyValArray[4], -1 ) ) );

        Map paramMap = new HashMap();

        String status = memberService.checkResetMemberKey( keyValArray, key );

        paramMap.put( "status", status );

        if( !"1".equals( status ) )
        {
            return ServletUtil.redirect( site.getSiteUrl() + site.getResetPwBackUri(), paramMap );
        }

        // 加密
        byte[] encryptResult = EncodeOne.encryptAES( keyValArray[0], ParamUtilTag.getSalt( "E" ) );

        String byt = EncodeOne.encode16( encryptResult ).toLowerCase();

        String idKey = byt;

        encryptResult = EncodeOne.encryptAES( keyValArray[1], ParamUtilTag.getSalt( "B" ) );

        byt = EncodeOne.encode16( encryptResult ).toLowerCase();

        String regKey = byt;

        encryptResult = EncodeOne.encryptAES( keyValArray[2], ParamUtilTag.getSalt( "C" ) );

        byt = EncodeOne.encode16( encryptResult ).toLowerCase();

        String mailKey = byt;

        paramMap.put( "idKey", idKey );
        paramMap.put( "regKey", regKey );
        paramMap.put( "mailKey", mailKey );
        paramMap.put( "key", key );

        return ServletUtil.redirect( site.getSiteUrl() + site.getResetPwBackUri(), paramMap );

    }

    @RequestMapping( value = "/resetMemberPw.do", method = { RequestMethod.POST } )
    public Object resetMemberPw( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        // 验证码
        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            return "-1";
        }
        else
        {
            ssn.removeAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
        }

        String status = memberService.resetMemberPassword( params );

        return status;

    }

}

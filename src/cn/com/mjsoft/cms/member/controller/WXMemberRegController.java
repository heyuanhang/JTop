package cn.com.mjsoft.cms.member.controller;

import java.sql.Timestamp;
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

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.member.dao.vo.Member;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.crypto.PasswordUtility;
import cn.com.mjsoft.framework.security.headstream.IUser;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class WXMemberRegController
{
    private static MemberService memberService = MemberService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/wxRegMember.do", method = { RequestMethod.POST } )
    public Object regMember( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Member member = ( Member ) ServletUtil.getValueObject( request, Member.class );

        // DCWY JC

        Random r = new Random();

        member.setMemberName( "mem" + r.nextInt( 100000000 ) );
        params.put( "memberName", member.getMemberName() );

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

        // 密码随机
        String ip = IPSeeker.getIp( request );

        String pw = ip + r.nextInt( 100000000 ) ;
        member.setPassword( PasswordUtility.encrypt( pw) );

        // 强制设定手机和mail不通过,强制身份为没有验证

        member.setIsTrueEmail( Constant.COMMON.OFF );
        member.setIsTrueMan( Constant.COMMON.ON );
        member.setIsTruePass( Constant.COMMON.ON );
        member.setIsTruePhone( Constant.COMMON.OFF );

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
    @RequestMapping( value = "/wxRelateMember.do", method = { RequestMethod.POST } )
    public Object relateMember( HttpServletRequest request, HttpServletResponse response )
    {

        Map params = ServletUtil.getRequestInfo( request );

        Member member = ( Member ) ServletUtil.getValueObject( request, Member.class );

        

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

   

   
    
 
 

}

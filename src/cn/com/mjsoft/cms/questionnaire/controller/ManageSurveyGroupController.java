package cn.com.mjsoft.cms.questionnaire.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.questionnaire.dao.vo.SurveyGroup;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/survey" )
public class ManageSurveyGroupController
{
    private static SurveyService surveyService = SurveyService.getInstance();

    @RequestMapping( value = "/createSurveyGroup.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "创建调查问卷项", token = true )
    public ModelAndView createSurveyGroup( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SurveyGroup quest = ( SurveyGroup ) ServletUtil.getValueObject( request, SurveyGroup.class );

        if( quest.getStartDate() == null )
        {
            quest.setStartDate( DateAndTimeUtil.getTodayTimestamp() );
        }

        if( quest.getEndDate() == null )
        {
            quest.setEndDate( Constant.CONTENT.MAX_DATE );
        }

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            quest.setHandlers( auth.getApellation().toString() );
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        quest.setSiteId( site.getSiteId() );

        surveyService.addNewSurveyGroup( quest );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "id", params.get( "groupId" ) );

        return ServletUtil.redirect( "/core/question/CreateSurveyGroup.jsp", paramMap );
    }

    @RequestMapping( value = "/editSurveyGroup.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑调查问卷项", token = true )
    public ModelAndView editSurveyGroup( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SurveyGroup quest = ( SurveyGroup ) ServletUtil.getValueObject( request, SurveyGroup.class );

        if( quest.getStartDate() == null )
        {
            quest.setStartDate( DateAndTimeUtil.getTodayTimestamp() );
        }

        if( quest.getEndDate() == null )
        {
            quest.setEndDate( Constant.CONTENT.MAX_DATE );
        }

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            quest.setHandlers( auth.getApellation().toString() );
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        quest.setSiteId( site.getSiteId() );

        surveyService.editSurveyGroup( quest );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "id", params.get( "groupId" ) );

        return ServletUtil.redirect( "/core/question/EditSurveyGroup.jsp", paramMap );
    }

    @RequestMapping( value = "/deleteSurveyGroup.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "删除问卷", token = true )
    public ModelAndView deleteSurveyGroup( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        surveyService.deleteSurveyGroupInfo( idList );

        Map paramMap = new HashMap();

        paramMap.put( "groupId", params.get( "groupId" ) );

        paramMap.put( "groupFlag", params.get( "groupFlag" ) );

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/question/ManageSurveyGroup.jsp", paramMap );
    }

}

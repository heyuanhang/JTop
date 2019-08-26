package cn.com.mjsoft.cms.questionnaire.controller;

import java.util.ArrayList;
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
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.questionnaire.dao.vo.SurveyBaseInfo;
import cn.com.mjsoft.cms.questionnaire.dao.vo.SurveyOptionInfo;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/survey" )
public class ManageSurveyAndOptionController
{
    private static SurveyService surveyService = SurveyService.getInstance();

    @RequestMapping( value = "/createSurvey.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加调查问卷项", token = true )
    public ModelAndView createSurvey( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SurveyBaseInfo survey = ( SurveyBaseInfo ) ServletUtil.getValueObject( request,
            SurveyBaseInfo.class );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        survey.setSiteFlag( siteBean.getSiteFlag() );

        if( params.get( "addInfo" ) == null )
        {
            survey.setHaveText( Constant.COMMON.OFF );

            survey.setAddiTitle( "" );
        }
        else
        {
            survey.setHaveText( Constant.COMMON.ON );
        }

        surveyService.addNewSurveyAndOption( survey, disposeSurveyOption( survey, params ) );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "groupId", params.get( "groupId" ) );

        paramMap.put( "groupFlag", params.get( "groupFlag" ) );

        paramMap.put( "sid", params.get( "sid" ) );

        return ServletUtil.redirect( "/core/question/CreateSurvey.jsp", paramMap );
    }

    @RequestMapping( value = "/editSurvey.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑调查问卷项", token = true )
    public ModelAndView editSurvey( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SurveyBaseInfo survey = ( SurveyBaseInfo ) ServletUtil.getValueObject( request,
            SurveyBaseInfo.class );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        survey.setSiteFlag( siteBean.getSiteFlag() );

        if( params.get( "addInfo" ) == null )
        {
            survey.setHaveText( Constant.COMMON.OFF );

            survey.setAddiTitle( "" );
        }
        else
        {
            survey.setHaveText( Constant.COMMON.ON );
        }

        surveyService.editSurveyAndOption( survey, disposeSurveyOption( survey, params ) );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "groupId", params.get( "groupId" ) );

        paramMap.put( "groupFlag", params.get( "groupFlag" ) );

        paramMap.put( "sid", params.get( "sid" ) );

        return ServletUtil.redirect( "/core/question/EditSurvey.jsp", paramMap );
    }

    @RequestMapping( value = "/swapSurveyOrder.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "调整问卷项顺序", token = true )
    public ModelAndView swapSurveyOrder( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SurveyBaseInfo survey = ( SurveyBaseInfo ) ServletUtil.getValueObject( request,
            SurveyBaseInfo.class );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        survey.setSiteFlag( siteBean.getSiteFlag() );

        if( params.get( "addInfo" ) == null )
        {
            survey.setHaveText( Constant.COMMON.OFF );

            survey.setAddiTitle( "" );
        }
        else
        {
            survey.setHaveText( Constant.COMMON.ON );
        }

        String groupFlag = ( String ) params.get( "groupFlag" );

        String act = ( String ) params.get( "action" );

        Long surveyId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "surveyId" ),
            -1 ) );

        surveyService.swapSurveyOrder( surveyId, act, groupFlag );

        Map paramMap = new HashMap();

        paramMap.put( "groupId", params.get( "groupId" ) );

        paramMap.put( "groupFlag", params.get( "groupFlag" ) );

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "sid", params.get( "sid" ) );

        return ServletUtil.redirect( "/core/question/ManageSurvey.jsp", paramMap );
    }

    @RequestMapping( value = "/deleteSurvey.do", method = { RequestMethod.POST, RequestMethod.GET} )
    @ActionInfo( traceName = "删除问卷项", token = true )
    public ModelAndView deleteSurvey( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        surveyService.deleteSurveyInfo( idList );

        Map paramMap = new HashMap();

        paramMap.put( "groupId", params.get( "groupId" ) );

        paramMap.put( "groupFlag", params.get( "groupFlag" ) );

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/question/ManageSurvey.jsp", paramMap );
    }

    private List disposeSurveyOption( SurveyBaseInfo survey, Map requestParams )
    {
        Integer optType = survey.getOptionType();

        List optBeanList = new ArrayList();

        int textCount = StringUtil.getIntValue( ( String ) requestParams.get( "textCount" ), 0 );
        int imageCount = StringUtil.getIntValue( ( String ) requestParams.get( "imageCount" ), 0 );

        SurveyOptionInfo option = null;
        String textOptVal = null;
        if( Constant.SURVEY.SINGLE_SELECT_TEXT.equals( optType )
            || Constant.SURVEY.MUTI_SELECT_TEXT.equals( optType ) )
        {
            for ( int i = 1; i <= textCount; i++ )
            {
                option = new SurveyOptionInfo();

                textOptVal = ( String ) requestParams.get( "text-opt-ele-input-" + i );

                if( StringUtil.isStringNull( textOptVal ) )
                {
                    continue;
                }

                option.setOptionText( textOptVal );
                option.setVote( Integer.valueOf( StringUtil.getIntValue( ( String ) requestParams
                    .get( "text-opt-ele-vote-" + i ), 0 ) ) );
                option.setSiteFlag( survey.getSiteFlag() );

                optBeanList.add( option );
            }
        }
        else if( Constant.SURVEY.SINGLE_SELECT_IMAGE.equals( optType )
            || Constant.SURVEY.MUTI_SELECT_IMAGE.equals( optType ) )
        {
            for ( int i = 1; i <= imageCount; i++ )
            {
                option = new SurveyOptionInfo();

                textOptVal = ( String ) requestParams.get( "image-opt-" + i + "-" );

                if( StringUtil.isStringNull( textOptVal ) )
                {
                    continue;
                }

                Long resId = Long.valueOf( StringUtil.getLongValue( textOptVal, -1 ) );

                ServiceUtil.disposeOldImageInfo( resId, "image-opt-" + i + "-", requestParams );

                option.setOptionImage( ServiceUtil.disposeSingleImageInfo( resId ) );

                option.setTarget( ( String ) requestParams.get( "image-opt-ele-target-" + i ) );

                option.setVote( Integer.valueOf( StringUtil.getIntValue( ( String ) requestParams
                    .get( "image-opt-ele-vote-" + i ), 0 ) ) );
                option.setSiteFlag( survey.getSiteFlag() );

                optBeanList.add( option );
            }
        }
        else if( Constant.SURVEY.INPUT_TEXT.equals( optType ) )
        {
            option = new SurveyOptionInfo();

            option.setInputTextCount( Integer.valueOf( StringUtil.getIntValue(
                ( String ) requestParams.get( "inputTextCount" ), 200 ) ) );

            option.setSiteFlag( survey.getSiteFlag() );

            optBeanList.add( option );
        }

        return optBeanList;

    }
}

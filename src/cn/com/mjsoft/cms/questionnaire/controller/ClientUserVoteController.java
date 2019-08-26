package cn.com.mjsoft.cms.questionnaire.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.questionnaire.bean.SurveyGroupBean;
import cn.com.mjsoft.cms.questionnaire.dao.vo.SurveyVoteInfo;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/survey" )
public class ClientUserVoteController
{
    private static SurveyService surveyService = SurveyService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/clientVote.do", method = { RequestMethod.POST } )
    public String clientVote( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        // 根据间隔时间以及访问者区分模式 限制投票
        // 需要增加新的表以记录用户投票IP方式限制，对于cookie方式，直接记录在cookie信息中
        Object[] voteInfoArray = disposeVoteInfo( params, request );

        if( voteInfoArray[1] == null )
        {
            // 元数据丢失
            return "-2";
        }

        // 验证码逻辑
        int status = surveyService.judgeUserCaptcha( ( String ) voteInfoArray[1], request, params );

        if( status < 0 )
        {
            // -1验证码错误
            return status + "";
        }

        // 投票核心限制规则

        status = coreVoteRuleCheck( ( String ) voteInfoArray[1] );

        if( status < 0 )
        {
            // -4 -5日期无效
            return status + "";
        }

        // 根据限制投票的方式以及用户信息判断是否允许投票
        boolean allowVote = surveyService.judgeUserVote( ( String ) voteInfoArray[1], request,
            response );

        if( !allowVote )
        {
            // 0达到投票规则限制条件
            return "0";
        }

        List voteInfoList = ( List ) voteInfoArray[0];

        if( voteInfoList.isEmpty() )
        {
            // 无投票信息,忽略
            return "-3";
        }

        surveyService.addNewClientUserVoteInfo( voteInfoList );

        // 设置成功标志
        request.setAttribute( "successFlag", Boolean.TRUE );

        return "1";
    }

    @SuppressWarnings( "unchecked" )
    private Object[] disposeVoteInfo( Map requestParam, HttpServletRequest request )
    {
        List voteIdInfoList = new ArrayList();

        String groupFlag = null;

        Iterator enteryIter = requestParam.entrySet().iterator();

        Entry entry = null;
        String key = null;
        Object val = null;

        SurveyVoteInfo voteInfo = null;

        long idVar;
        while ( enteryIter.hasNext() )
        {
            entry = ( Entry ) enteryIter.next();
            key = ( String ) entry.getKey();

            if( key.startsWith( Constant.SURVEY.HTML_ID_PERFIX ) )
            {
                val = entry.getValue();

                if( val instanceof String )
                {
                    idVar = StringUtil.getLongValue( ( String ) val, -1 );

                    if( idVar > 0 )
                    {
                        voteInfo = new SurveyVoteInfo();

                        voteInfo.setIp( IPSeeker.getIp( request ) );
                        voteInfo.setOptId( Long.valueOf( idVar ) );
                        voteInfo.setSurveyId( Long.valueOf( StringUtil.replaceString( key,
                            Constant.SURVEY.HTML_ID_PERFIX, "", false, false ) ) );
                        voteInfo.setVoteDate( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
                        voteInfo.setVoteMan( "匿名" );
                        voteInfo.setVoteText( null );

                        voteIdInfoList.add( voteInfo );
                    }
                }
                else if( val instanceof String[] )
                {
                    String[] temp = ( String[] ) val;

                    for ( int i = 0; i < temp.length; i++ )
                    {
                        idVar = StringUtil.getLongValue( ( String ) temp[i], -1 );

                        if( idVar > 0 )
                        {
                            voteInfo = new SurveyVoteInfo();

                            voteInfo.setIp( IPSeeker.getIp( request ) );
                            voteInfo.setOptId( Long.valueOf( idVar ) );
                            voteInfo.setSurveyId( Long.valueOf( StringUtil.replaceString( key,
                                Constant.SURVEY.HTML_ID_PERFIX, "", false, false ) ) );
                            voteInfo.setVoteDate( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
                            voteInfo.setVoteMan( "匿名" );
                            voteInfo.setVoteText( null );

                            voteIdInfoList.add( voteInfo );
                        }
                    }
                }
            }
            else if( key.startsWith( Constant.SURVEY.HTML_TEXT_ID_PERFIX ) )
            {
                voteInfo = new SurveyVoteInfo();

                voteInfo.setIp( IPSeeker.getIp( request ) );
                voteInfo.setOptId( Long.valueOf( -1 ) );
                voteInfo.setSurveyId( Long.valueOf( StringUtil.replaceString( key,
                    Constant.SURVEY.HTML_TEXT_ID_PERFIX, "", false, false ) ) );
                voteInfo.setVoteDate( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
                voteInfo.setVoteMan( "匿名" );
                voteInfo.setVoteText( SystemSafeCharUtil.filterHTMLNotApos( SystemSafeCharUtil
                    .decodeFromWeb( ( String ) entry.getValue() ) ) );

                voteIdInfoList.add( voteInfo );
            }
            else if( key.startsWith( Constant.SURVEY.HTML_GROUP_FLAG_PERFIX ) )
            {
                groupFlag = ( String ) entry.getValue();
            }
        }

        return new Object[] { voteIdInfoList, groupFlag };
    }

    private int coreVoteRuleCheck( String flag )
    {
        SurveyGroupBean groupBean = surveyService.retrieveSingleQuestBaseInfoBeanByQuestFlag( flag );

        if( groupBean.getGroupId().longValue() < 0 )
        {
            // 投票元信息丢失
            return -2;
        }

        if( Constant.COMMON.OFF.equals( groupBean.getUseState() ) )
        {
            return -6;// 关闭
        }

        String currentDate = DateAndTimeUtil
            .getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        String startDate = DateAndTimeUtil.formatTimestamp( new Timestamp( groupBean.getStartDate()
            .getTime() ), DateAndTimeUtil.DEAULT_FORMAT_YMD );

        String endDate = DateAndTimeUtil.formatTimestamp( new Timestamp( groupBean.getEndDate()
            .getTime() ), DateAndTimeUtil.DEAULT_FORMAT_YMD );

        int beforeDayCount = DateAndTimeUtil.getDayInterval( currentDate, startDate,
            DateAndTimeUtil.DEAULT_FORMAT_YMD );

        int afterDayCount = DateAndTimeUtil.getDayInterval( endDate, currentDate,
            DateAndTimeUtil.DEAULT_FORMAT_YMD );

        if( beforeDayCount < 0 )
        {
            return -4;// 没有到投票时间
        }

        if( afterDayCount < 0 )
        {
            return -5;// 投票时间已过
        }

        return 1;

    }
}

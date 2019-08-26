package cn.com.mjsoft.cms.questionnaire.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;

import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientSurveyJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientSurveyJSonFlow.class );

    private static SurveyService surveyService = SurveyService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String surveyId = ( String ) params.get( "surveyId" );

        if( StringUtil.getLongValue( surveyId, -1 ) < 0 )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        String groupId = ( String ) params.get( "groupId" );

        String groupFlag = StringUtil.notNull( ( String ) params
            .get( "groupFlag" ) );

        List result = Collections.EMPTY_LIST;

        Long groupIdVar = Long.valueOf( StringUtil.getLongValue( groupId, -1 ) );

        if( groupIdVar.longValue() > 0 )
        {
            result = surveyService
                .retrieveSurveyBaseInfoBeanListByGroupId( groupIdVar );
        }
        else if( groupFlag.length() > 0 )
        {
            result = surveyService
                .retrieveSurveyBaseInfoBeanListByGroupFlag( groupFlag );
        }
        else
        {
            Long surveyIdVar = Long.valueOf( StringUtil.getLongValue( surveyId,
                -1 ) );

            if( surveyIdVar.longValue() > 0 )
            {
                result = new ArrayList( 1 );
                result.add( surveyService
                    .retrieveSingleSurveyBaseInfoBeanBySurveyId( surveyIdVar ) );
            }
        }

        if( result.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( result ) );

    }
}

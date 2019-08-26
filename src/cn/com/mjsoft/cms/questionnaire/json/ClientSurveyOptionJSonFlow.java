package cn.com.mjsoft.cms.questionnaire.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.questionnaire.bean.SurveyOptionInfoBean;
import cn.com.mjsoft.cms.questionnaire.bean.SurveyOptionInfoJsonBean;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientSurveyOptionJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientSurveyOptionJSonFlow.class );

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

        List result = Collections.EMPTY_LIST;

        result = surveyService.retrieveSurveyOptionBeanListBySurveyId( Long
            .valueOf( StringUtil.getLongValue( surveyId, -1 ) ) );

        if( result.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( result ) );

    }

    public static List transformJSONData( List cbList )
    {
        if( cbList == null || cbList.isEmpty() )
        {
            return Collections.EMPTY_LIST;
        }

        SurveyOptionInfoBean soBean = null;

        List jbList = new ArrayList( cbList.size() );

        for ( int i = 0; i < cbList.size(); i++ )
        {
            soBean = ( SurveyOptionInfoBean ) cbList.get( i );

            jbList.add( transformJSONData( soBean ) );
        }

        return jbList;
    }

    public static SurveyOptionInfoJsonBean transformJSONData(
        SurveyOptionInfoBean soBean )
    {
        SurveyOptionInfoJsonBean jsonBean = new SurveyOptionInfoJsonBean();

        if( soBean == null )
        {
            return null;
        }

        jsonBean.setInputText( soBean.getInputText() );
        jsonBean.setInputTextCount( soBean.getInputTextCount() );
        jsonBean.setOptionId( soBean.getOptionId() );
        jsonBean.setOptionImage( soBean.getOptionImage() );
        jsonBean.setOptionImageResId( soBean.getOptionImageResId() );
        jsonBean.setOptionText( soBean.getOptionText() );
        jsonBean.setSiteFlag( soBean.getSiteFlag() );
        jsonBean.setSurveyId( soBean.getSurveyId() );
        jsonBean.setVote( soBean.getVote() );
        jsonBean.setVotePer( soBean.getVotePer() );
        jsonBean.setTarget( soBean.getTarget() );

        return jsonBean;

    }
}

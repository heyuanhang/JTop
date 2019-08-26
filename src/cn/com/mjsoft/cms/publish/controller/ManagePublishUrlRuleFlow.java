package cn.com.mjsoft.cms.publish.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.publish.dao.vo.PublishRule;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/publish" )
public class ManagePublishUrlRuleFlow
{
    private static PublishService publishService = PublishService.getInstance();

    @RequestMapping( value = "/createPubRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加发布URL规则", token = true )
    public ModelAndView createPubRule( HttpServletRequest request, HttpServletResponse response )
    {
        PublishRule rule = ( PublishRule ) ServletUtil.getValueObject( request, PublishRule.class );

        if( Constant.CONTENT.HTML_PUB.equals( rule.getSuffixRule() ) )
        {
            rule.setSuffixRule( Constant.CONTENT.HTML_PUB_HTML );
        }
        else if( Constant.CONTENT.HTML_PUB_S.equals( rule.getSuffixRule() ) )
        {
            rule.setSuffixRule( Constant.CONTENT.HTML_PUB_S_HTML );
        }

        // 转换rule name
        rule.setRuleName( changeRuleToRuleTitle( rule.getSavePathRule(), rule.getFileNameRule(),
            rule.getSuffixRule() ) );

        // 目前分页规则不再允许配置,自动加上_{pn}

        String saveRule = rule.getSavePathRule();

        if( saveRule.startsWith( "/" ) )
        {
            saveRule = StringUtil.subString( saveRule, 1, saveRule.length() );
        }

        if( saveRule.endsWith( "/" ) )
        {
            saveRule = StringUtil.subString( saveRule, 0, saveRule.length() - 1 );
        }

        rule.setSavePathRule( saveRule );
        
        rule.setPagePathRule( saveRule );

        rule.setPageFileNameRule( rule.getFileNameRule() + "_{pn}" );

        // 参数处理
        rule.setSavePathParam( StringUtil.join( ( String[] ) PublishService.checkRuleParamExist(
            rule.getSavePathRule() ).toArray( new String[] {} ), "," ) );

        rule.setFileNameParam( StringUtil.join( ( String[] ) PublishService.checkRuleParamExist(
            rule.getFileNameRule() ).toArray( new String[] {} ), "," ) );

        // 分页参数处理
        rule.setPagePathParam( StringUtil.join( ( String[] ) PublishService.checkRuleParamExist(
            rule.getSavePathRule() ).toArray( new String[] {} ), "," ) );

        List pageFileParamList = PublishService.checkRuleParamExist( rule.getFileNameRule() );

        pageFileParamList.add( "{pn}" );

        rule.setPageFileNameParam( StringUtil.join( ( String[] ) pageFileParamList
            .toArray( new String[] {} ), "," ) );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        rule.setSiteId( site.getSiteId() );

        publishService.addNewPublishRule( rule );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/CreatePublishRule.jsp", paramMap );
    }

    @RequestMapping( value = "/editPubRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑发布URL规则", token = true )
    public ModelAndView editPubRule( HttpServletRequest request, HttpServletResponse response )
    {
        PublishRule rule = ( PublishRule ) ServletUtil.getValueObject( request, PublishRule.class );

        if( Constant.CONTENT.HTML_PUB.equals( rule.getSuffixRule() ) )
        {
            rule.setSuffixRule( Constant.CONTENT.HTML_PUB_HTML );
        }
        else if( Constant.CONTENT.HTML_PUB_S.equals( rule.getSuffixRule() ) )
        {
            rule.setSuffixRule( Constant.CONTENT.HTML_PUB_S_HTML );
        }

        // 转换rule name
        rule.setRuleName( changeRuleToRuleTitle( rule.getSavePathRule(), rule.getFileNameRule(),
            rule.getSuffixRule() ) );

        // 目前分页规则不再允许配置,自动加上_{pn}

        String saveRule = rule.getSavePathRule();

        if( saveRule.startsWith( "/" ) )
        {
            saveRule = StringUtil.subString( saveRule, 1, saveRule.length() );
        }

        if( saveRule.endsWith( "/" ) )
        {
            saveRule = StringUtil.subString( saveRule, 0, saveRule.length() - 1 );
        }

        rule.setSavePathRule( saveRule );
        
        rule.setPagePathRule( saveRule );

        rule.setPageFileNameRule( rule.getFileNameRule() + "_{pn}" );

        // 参数处理
        rule.setSavePathParam( StringUtil.join( ( String[] ) PublishService.checkRuleParamExist(
            rule.getSavePathRule() ).toArray( new String[] {} ), "," ) );

        rule.setFileNameParam( StringUtil.join( ( String[] ) PublishService.checkRuleParamExist(
            rule.getFileNameRule() ).toArray( new String[] {} ), "," ) );

        // 分页参数处理
        rule.setPagePathParam( StringUtil.join( ( String[] ) PublishService.checkRuleParamExist(
            rule.getSavePathRule() ).toArray( new String[] {} ), "," ) );

        List pageFileParamList = PublishService.checkRuleParamExist( rule.getFileNameRule() );

        pageFileParamList.add( "{pn}" );

        rule.setPageFileNameParam( StringUtil.join( ( String[] ) pageFileParamList
            .toArray( new String[] {} ), "," ) );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        rule.setSiteId( site.getSiteId() );

        publishService.editNewPublishRule( rule );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/EditPublishRule.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deletePubRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除发布规则", token = true )
    public String deletePubRule( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        publishService.deletePublishRuleByIds( idList );

        return "success";

    }

    private String changeRuleToRuleTitle( String savePath, String saveFileName, String suffix )
    {
        String pathInfo = PublishService.changeRuleParamToName( savePath );
        String fileNameInfo = PublishService.changeRuleParamToName( saveFileName );

        if( pathInfo.endsWith( "/" ) )
        {
            if( pathInfo.startsWith( "/" ) )
            {
                return pathInfo + fileNameInfo + suffix;
            }

            return "/" + pathInfo + fileNameInfo + suffix;
        }
        else
        {
            if( pathInfo.startsWith( "/" ) )
            {
                return pathInfo + "/" + fileNameInfo + suffix;
            }

            return "/" + pathInfo + "/" + fileNameInfo + suffix;
        }

    }
}

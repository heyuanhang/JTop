package cn.com.mjsoft.cms.resources.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.resources.bean.TextBean;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/resources" )
public class ManageTemplateContentController
{

    private static ResourcesService resService = ResourcesService.getInstance();

    @RequestMapping( value = "/writeTextFile.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "编辑GBK模板内容" )
    public ModelAndView writeTextFile( HttpServletRequest request, HttpServletResponse response )
    {

        Set htmlParamSet = new HashSet();
        htmlParamSet.add( "content" );

        Map params = ServletUtil.getRequestInfo( request, htmlParamSet );

        String entry = ( String ) params.get( "entry" );

        entry = SystemSafeCharUtil.decodeFromWeb( entry );

        entry = StringUtil.replaceString( entry, "%20", " ", false, false );

        String fileEntry = ( String ) params.get( "fileEntry" );

        fileEntry = SystemSafeCharUtil.decodeFromWeb( fileEntry );

        fileEntry = StringUtil.replaceString( fileEntry, "%20", " ", false, false );

        // 被修改的或者版本库的content
        String content = ( String ) params.get( "content" );

        String type = ( String ) params.get( "type" );

        String editDesc = ( String ) params.get( "editDesc" );

        Long etId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "etId" ), -1 ) );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "entry", SystemSafeCharUtil.encode( entry ) );

        paramMap.put( "type", type );

        paramMap.put( "fileTile", SystemSafeCharUtil.encode( StringUtil.replaceString( StringUtil
            .replaceString( entry, "*", "/", false, false ), "/template", "", false, false ) ) );

        // 检查jsp内容
        if( ( entry.toLowerCase().endsWith( ".jsp" ) || entry.toLowerCase().endsWith( ".thtml" ) )
            && !ResourcesService.checkFile( content ) )
        {
            return ServletUtil.redirect( "/core/resources/EditCodeAndTextFile.jsp", paramMap );
        }

        if( etId.longValue() > 0 )
        {// 恢复版本内容
            resService.recoverSingleTemplateFileContent( fileEntry, etId, content, "GBK" );

            paramMap.put( "etId", etId );

            paramMap.put( "fileEntry", SystemSafeCharUtil.encode( fileEntry ) );

            return ServletUtil.redirect( "/core/templet/ViewTemplateEdition.jsp", paramMap );
        }
        else
        {
            resService.writeSingleTemplateFileContent( editDesc, entry, content, "GBK" );
        }

        return ServletUtil.redirect( "/core/resources/EditCodeAndTextFile.jsp", paramMap );

    }

    @RequestMapping( value = "/writeUTF8TextFile.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "编辑UTF-8模板内容" )
    public ModelAndView writeUTF8TextFile( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlParamSet = new HashSet();
        htmlParamSet.add( "content" );

        Map params = ServletUtil.getRequestInfo( request, htmlParamSet );

        String entry = ( String ) params.get( "entry" );

        entry = SystemSafeCharUtil.decodeFromWeb( entry );

        entry = StringUtil.replaceString( entry, "%20", " ", false, false );

        String fileEntry = ( String ) params.get( "fileEntry" );

        fileEntry = SystemSafeCharUtil.decodeFromWeb( fileEntry );

        fileEntry = StringUtil.replaceString( fileEntry, "%20", " ", false, false );

        String content = ( String ) params.get( "content" );

        String type = ( String ) params.get( "type" );

        String editDesc = ( String ) params.get( "editDesc" );

        Long etId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "etId" ), -1 ) );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "entry", SystemSafeCharUtil.encode( entry ) );

        paramMap.put( "type", type );

        paramMap.put( "fileTile", SystemSafeCharUtil.encode( entry ) );

        if( ( entry.toLowerCase().endsWith( ".jsp" ) || entry.toLowerCase().endsWith( ".thtml" ) )
            && !ResourcesService.checkFile( content ) )
        {

            return ServletUtil.redirect( "/core/resources/EditCodeAndTextFileUTF-8.jsp", paramMap );
        }

        if( etId.longValue() > 0 )
        {// 恢复版本内容
            resService.recoverSingleTemplateFileContent( fileEntry, etId, content, "UTF-8" );

            paramMap.put( "etId", etId );

            paramMap.put( "fileEntry", SystemSafeCharUtil.encode( fileEntry ) );

            return ServletUtil.redirect( "/core/templet/ViewTemplateEdition_UTF.jsp", paramMap );

        }
        else
        {
            resService.writeSingleTemplateFileContent( editDesc, entry, content, "UTF-8" );
        }

        return ServletUtil.redirect( "/core/resources/EditCodeAndTextFileUTF-8.jsp", paramMap );

    }

    @RequestMapping( value = "/directViewPage.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public ModelAndView directViewPage( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String entry = ( String ) params.get( "entry" );

        String type = ( String ) params.get( "type" );

        String mode = ( String ) params.get( "mode" );

        TextBean text = resService.retrieveSingleSiteTextFileContentInfo( entry, false );

        Map paramMap = new HashMap();

        paramMap.put( "entry", SystemSafeCharUtil.encode( ( String ) params.get( "entry" ) ) );

        paramMap.put( "type", type );

        paramMap.put( "fileTile", SystemSafeCharUtil.encode( StringUtil.replaceString( StringUtil
            .replaceString( ( String ) params.get( "entry" ), "*", "/", false, false ),
            "/template", "", false, false ) ) );

        if( "GBK".equals( text.getCharset() ) )
        {
            if( "edition".equals( mode ) )
            {
                paramMap.put( "etId", ( String ) params.get( "etId" ) );

                paramMap.put( "fileEntry", SystemSafeCharUtil.encode( ( String ) params
                    .get( "fileEntry" ) ) );

                return ServletUtil.redirect( "/core/templet/ViewTemplateEdition.jsp", paramMap );

            }

            return ServletUtil.redirect( "/core/resources/EditCodeAndTextFile.jsp", paramMap );
        }
        else if( "UTF-8".equals( text.getCharset() ) )
        {
            if( "edition".equals( mode ) )
            {
                paramMap.put( "etId", ( String ) params.get( "etId" ) );

                paramMap.put( "fileEntry", SystemSafeCharUtil.encode( ( String ) params
                    .get( "fileEntry" ) ) );

                return ServletUtil.redirect( "/core/templet/ViewTemplateEdition_UTF.jsp", paramMap );
            }

            return ServletUtil.redirect( "/core/resources/EditCodeAndTextFileUTF-8.jsp", paramMap );
        }

        return null;
    }

}

package cn.com.mjsoft.cms.stat.controller;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.stat.dao.vo.StatVisitInfo;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/stat" )
public class ColleactUserVisitInfoFlow
{

    private static final String COOKIE_NAME = "JTOPCMS-STAT-UVID";

    private static StatService statService = StatService.getInstance();

    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 365 * 50;

    @ResponseBody
    @RequestMapping( value = "/collStat.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public void collStat( HttpServletRequest request, HttpServletResponse response )
    {
        // Thread.sleep( 6000 );//测试代码,请勿删除
        /**
         * PV(访问量)：即Page View, 即页面浏览量或点击量，用户每次刷新即被计算一次。<br>
         * UV(独立访客)：即Unique Visitor,访问您网站的一台电脑客户端为一个访客。00:00-24:00内相同的客户端只被计算一次。
         * IP(独立IP)：即Internet Protocol,指独立IP数。00:00-24:00内相同IP地址之被计算一次。
         */

        /**
         * PV记录:任何客户端进入stat.js,某天内加1<br>
         * UV记录:任意客户端在某一天访问时,若没有Cookie内的UVID,生成客户端Cookie的UVID(一直存在),.若存在,则给当天的UV记录加1
         * IP记录:某IP当天访问的,按一次统计
         */

        /**
         * 每次的访问页等记录,单独记录
         */

        // BufferedReader input = this.getServletFlowContext().getRequest()
        // .getReader();
        // + new String( input.readLine().getBytes(), "UTF-8" ) );
        Map params = ServletUtil.getRequestInfo( request );

        Calendar currentTime = Calendar.getInstance();

        int year = currentTime.get( Calendar.YEAR );

        int month = currentTime.get( Calendar.MONTH ) + 1;

        int day = currentTime.get( Calendar.DAY_OF_MONTH );

        int hour = currentTime.get( Calendar.HOUR_OF_DAY );

        Cookie cookie = ServletUtil.getCookie( request, COOKIE_NAME );

        String uvid = null;
        if( cookie == null )
        {
            // 新访客
            uvid = StringUtil.getUUIDString();

            ServletUtil.addCookie( response, "JTOPCMS-STAT-UVID", uvid, COOKIE_MAX_AGE );
        }
        else
        {
            uvid = ( String ) cookie.getValue();
        }
     
        StatVisitInfo visitInfo = new StatVisitInfo();

        visitInfo.setUvId( uvid );
        
        visitInfo.setIp( IPSeeker.getIp( request ) );

        String browser = ( ( String ) params.get( "browser" ) );

        if( browser == null )
        {
            browser = "未知";
        }
        else if( browser.startsWith( "c1" ) )
        {
            browser = "Chrome";
        }
        else if( browser.indexOf( "firefox" ) != -1 )
        {
            browser = "FireFox";
        }
        else if( browser.indexOf( "opera" ) != -1 )
        {
            browser = "Opera";
        }
        else if( browser.indexOf( "safari" ) != -1 )
        {
            browser = "Safari";
        }

        visitInfo.setBrowser( browser );

        visitInfo.setSource( "" );

        String ref = SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "reffer" ) );

        visitInfo.setReffer( ref );

        if( StringUtil.isStringNotNull( ref ) && ref.length() > 1 )
        {
            String reffHost = StringUtil.subString( ref, ref.indexOf( "://" ) + 3, ref.indexOf(
                "/", ref.indexOf( "://" ) + 3 ) );

            visitInfo.setRefferHost( reffHost );
        }

        Long siteId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "siteId" ), -1 ) );

        visitInfo.setRefferKey( ( String ) params.get( "refferKey" ) );
        // visitInfo.setRefferType( Integer.valueOf( ( String ) params
        // .get( "refferType" ) ) );
        visitInfo.setScreen( ( String ) params.get( "screen" ) );
        visitInfo.setSystem( ( String ) params.get( "system" ) );
        visitInfo.setLang( changeLangCode( ( String ) params.get( "lang" ) ) );
        // visitInfo.setTitle( new String( s1.getBytes( "iso8859-1" ), "GBK" )
        // );

        visitInfo.setUrl( SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "url" ) ) );

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( siteId );
        visitInfo.setHost( site.getSiteUrl() );
        visitInfo.setVisitTimeIn( DateAndTimeUtil.getNotNullTimestamp( ( String ) params
            .get( "visitTimeIn" ), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );
        visitInfo.setVisitTimeOut( DateAndTimeUtil.getNotNullTimestamp( ( String ) params
            .get( "visitTimeOut" ), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );
        visitInfo.setVisitYear( Integer.valueOf( year ) );
        visitInfo.setVisitMonth( Integer.valueOf( month ) );
        visitInfo.setVisitDay( Integer.valueOf( day ) );
        visitInfo.setVisitHour( Integer.valueOf( hour ) );
        visitInfo.setContentId( Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "contentId" ), -1 ) ) );
        visitInfo.setClassId( Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "classId" ), -1 ) ) );
        visitInfo.setSiteId( siteId );

        statService.disposeReferType( visitInfo );

        statService.addNewVisitorStatInfo( visitInfo );

    }

    private String changeLangCode( String lan )
    {
        if( StringUtil.isStringNull( lan ) )
        {
            return "未知";
        }

        if( "zh-cn".equals( lan ) || "zh-sg".equals( lan ) )
        {
            return "中文";
        }
        else if( "zh-tw".equals( lan ) || "zh-hk".equals( lan ) )
        {
            return "繁体中文";
        }
        else if( lan.startsWith( "en-" ) )
        {
            return "英语";
        }
        else if( lan.startsWith( "fr-" ) )
        {
            return "法语";
        }
        else if( lan.startsWith( "de-" ) )
        {
            return "德语";
        }
        else if( lan.startsWith( "es-" ) )
        {
            return "西班牙语";
        }
        else if( "ja".equals( lan ) )
        {
            return "日本语";
        }
        else if( "ko".equals( lan ) )
        {
            return "朝鲜语";
        }
        else if( "ru".equals( lan ) )
        {
            return "俄语";
        }

        return "其他";

    }

}

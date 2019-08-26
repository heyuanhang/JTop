package cn.com.mjsoft.cms.stat.dao;

import java.util.Date;
import java.util.Map;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.persistence.core.support.MapValueCallback;
import cn.com.mjsoft.framework.util.StringUtil;

public class SiteTraceResultCallBack implements MapValueCallback
{
    private String flags;

    private Date weekEnd;

    private Date threeMonEnd;

    private Date halfYearEnd;

    private Date yearEnd;

    private StatDao statDao;

    public SiteTraceResultCallBack( String flags, Date weekEnd,
        Date threeMonEnd, Date halfYearEnd, Date yearEnd, StatDao statDao )
    {
        super();
        this.flags = flags;
        this.weekEnd = weekEnd;
        this.threeMonEnd = threeMonEnd;
        this.halfYearEnd = halfYearEnd;
        this.yearEnd = yearEnd;
        this.statDao = statDao;
    }

    @SuppressWarnings( "unchecked" )
    public void transformVlaue( Map result )
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( result.get( "siteId" ) );

        Integer weekCount = 0;

        Integer threeMonCount = 0;

        Integer halfYearCount = 0;

        Integer yearCount = 0;

        Integer zeroClass = 0;

        // 实时统计

        if( site != null )
        {
            weekCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId(
                    site.getSiteFlag(), weekEnd ).size();

            threeMonCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId(
                    site.getSiteFlag(), threeMonEnd ).size();

            halfYearCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId(
                    site.getSiteFlag(), halfYearEnd ).size();

            yearCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId(
                    site.getSiteFlag(), yearEnd ).size();

            zeroClass = statDao
                .queryStatClassContentAllZeroTraceBySiteAndClass(
                    site.getSiteFlag() ).size();
        }
        else if( StringUtil.isStringNotNull( flags ) )
        {
            weekCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteFlag(
                    flags, weekEnd ).size();

            threeMonCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteFlag(
                    flags, threeMonEnd ).size();

            halfYearCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteFlag(
                    flags, halfYearEnd ).size();

            yearCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteFlag(
                    flags, yearEnd ).size();

            zeroClass = statDao
                .queryStatClassContentAllZeroTraceBySiteAndClassByFlags( flags )
                .size();
        }
        else
        {
            weekCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( weekEnd )
                .size();

            threeMonCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId(
                    threeMonEnd ).size();

            halfYearCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId(
                    halfYearEnd ).size();

            yearCount = statDao
                .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( yearEnd )
                .size();

            zeroClass = statDao
                .queryStatClassContentAllZeroTraceBySiteAndClass().size();
        }

        result.put( "weekEndCount", weekCount );

        result.put( "halfYearEndCount", halfYearCount );

        result.put( "threeMonEndCount", threeMonCount );

        result.put( "yearEndCount", yearCount );

        result.put( "zeroCount", zeroClass );

    }

}

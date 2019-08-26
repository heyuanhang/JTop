package cn.com.mjsoft.cms.stat.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.stat.bean.StatClassContentTraceBean;
import cn.com.mjsoft.cms.stat.bean.StatContentVisitOrCommentDWMCount;
import cn.com.mjsoft.cms.stat.bean.StatManagerContentTraceBean;
import cn.com.mjsoft.cms.stat.bean.StatOrgContentTraceBean;
import cn.com.mjsoft.cms.stat.bean.StatVisitorDayAnalysisBean;
import cn.com.mjsoft.cms.stat.bean.StatVisitorHourAnalysisBean;
import cn.com.mjsoft.cms.stat.dao.vo.StatClassContentTrace;
import cn.com.mjsoft.cms.stat.dao.vo.StatManagerContentTrace;
import cn.com.mjsoft.cms.stat.dao.vo.StatOrgContentTrace;
import cn.com.mjsoft.cms.stat.dao.vo.StatVisitInfo;
import cn.com.mjsoft.cms.stat.dao.vo.StatVisitorDayAnalysis;
import cn.com.mjsoft.cms.stat.dao.vo.StatVisitorHourAnalysis;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.MathUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class StatDao
{

    public PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public StatDao()
    {

    }

    public StatDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState save( StatVisitInfo vo )
    {
        return pe.save( vo );
    }

    public List queryVisitInfoByHour( Long siteId, Integer year, Integer month,
        Integer day, Integer hour )
    {
        String sql = "select * from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and visitHour=?";

        return pe.query( sql, new Object[] { siteId, year, month, day, hour },
            new StatVisitInfoBeanTransform() );
    }

    public List queryVisitIpInfoByHour( Long siteId, Integer year,
        Integer month, Integer day, Integer hour )
    {
        String sql = "select siteId, count(distinct ip) as ipCount from stat_visit_info where visitYear=? and visitMonth=? and visitDay=? and visitHour=? group by siteId";

        return pe.queryResultMap( sql, new Object[] { year, month, day, hour } );
    }

    public List queryVisitInfoIPCountByHour( Integer year, Integer month,
        Integer day, Integer hour )
    {
        String sql = "select siteId, count(distinct ip) as ipCount from stat_visit_info where visitYear=? and visitMonth=? and visitDay=? and visitHour=? group by siteId";

        return pe.queryResultMap( sql, new Object[] { year, month, day, hour } );
    }

    public List queryVisitInfoUVCountByHour( Integer year, Integer month,
        Integer day, Integer hour )
    {
        String sql = "select siteId, count(distinct uvId) as uvCount from stat_visit_info where visitYear=? and visitMonth=? and visitDay=? and visitHour=? group by siteId";

        return pe.queryResultMap( sql, new Object[] { year, month, day, hour } );
    }

    public List queryVisitInfoPVCountByHour( Integer year, Integer month,
        Integer day, Integer hour )
    {
        String sql = "select siteId, count(*) as pvCount from stat_visit_info where visitYear=? and visitMonth=? and visitDay=? and visitHour=? group by siteId";

        return pe.queryResultMap( sql, new Object[] { year, month, day, hour } );
    }

    public List queryAllRefferHostByDay( Long siteId, Integer year,
        Integer month, Integer day )
    {
        String sql = "select distinct refferHost from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and refferType!=0";

        return pe.querySingleCloumn( sql, new Object[] { siteId, year, month,
            day }, String.class );
    }

    public Long queryRefferHostUvidCountByDay( Long siteId, Integer year,
        Integer month, Integer day, String refferHost )
    {
        String sql = "select count(distinct uvid) from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and refferHost=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, year,
            month, day, refferHost }, Long.class );
    }

    public void saveSiteUrlUvCount( Long siteId, Long uvCount, String siteUrl )
    {
        String sql = "insert into stat_visitor_src_s_analysis (siteId, uvCount, siteUrl) values (?,?,?)";

        pe.update( sql, new Object[] { siteId, uvCount, siteUrl } );
    }

    public void updateSiteUrlUvCount( Long siteId, Long addUvCount,
        String siteUrl )
    {
        String sql = "update stat_visitor_src_s_analysis set uvCount=uvCount+? where siteId=? and siteUrl=?";

        pe.update( sql, new Object[] { addUvCount, siteId, siteUrl } );
    }

    public List queryAllSearchHostByDay( Long siteId, Integer year,
        Integer month, Integer day )
    {
        String sql = "select distinct refferHost from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and refferType!=0 and refferType!=6";

        return pe.querySingleCloumn( sql, new Object[] { siteId, year, month,
            day }, String.class );
    }

    public void saveSearchUvCount( Long siteId, Long uvCount,
        String searchSiteUrl )
    {
        String sql = "insert into stat_visitor_search_analysis (siteId, uvCount, searchName) values (?,?,?)";

        pe.update( sql, new Object[] { siteId, uvCount, searchSiteUrl } );
    }

    public void updateSearchUvCount( Long siteId, Long addUvCount,
        String siteUrl )
    {
        String sql = "update stat_visitor_search_analysis set uvCount=uvCount+? where siteId=? and searchName=?";

        pe.update( sql, new Object[] { addUvCount, siteId, siteUrl } );
    }

    public List queryAllSearchKeyByDay( Long siteId, Integer year,
        Integer month, Integer day )
    {
        String sql = "select distinct refferKey from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and refferType!=0 and refferType!=6";

        return pe.querySingleCloumn( sql, new Object[] { siteId, year, month,
            day }, String.class );
    }

    public Long queryRefferKeyUvidCountByDay( Long siteId, Integer year,
        Integer month, Integer day, String key )
    {
        String sql = "select count(distinct uvid) from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and refferKey=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, year,
            month, day, key }, Long.class );
    }

    public void saveSearchKeyUvCount( Long siteId, Long uvCount, String key )
    {
        String sql = "insert into stat_visitor_seh_key_analysis (siteId, uvCount, keyVal) values (?,?,?)";

        pe.update( sql, new Object[] { siteId, uvCount, key } );
    }

    public void updateSearchKeyUvCount( Long siteId, Long addUvCount, String key )
    {
        String sql = "update stat_visitor_search_analysis set uvCount=uvCount+? where siteId=? and searchName=?";

        pe.update( sql, new Object[] { addUvCount, siteId, key } );
    }

    public StatVisitorHourAnalysisBean queryVisitorHourAnalysisBeanByDate(
        Long siteId, Integer year, Integer month, Integer day, Integer hour )
    {
        String sql = "select * from stat_visitor_hour_analysis where siteId=? and visitYear=? and visitMonth=? and visitDay=? and visitHour=?";

        return ( StatVisitorHourAnalysisBean ) pe.querySingleRow( sql,
            new Object[] { siteId, year, month, day, hour },
            new StatVisitorHourAnalysisBeanTransform() );
    }

    public void updateVisitorHourAnalysisBeanByDate(
        StatVisitorHourAnalysisBean bean )
    {

        String sql = "update stat_visitor_hour_analysis set uvCount=?, ipCount=?, pvCount=? where visitYear=? and visitMonth=? and visitDay=? and visitHour=?";

        pe.update( sql, new Object[] { bean.getUvCount(), bean.getIpCount(),
            bean.getPvCount(), bean.getVisitYear(), bean.getVisitMonth(),
            bean.getVisitDay(), bean.getVisitHour() } );
    }

    public UpdateState save( StatVisitorHourAnalysis vo )
    {
        return pe.save( vo );
    }

    public UpdateState save( StatVisitorDayAnalysis vo )
    {
        return pe.save( vo );
    }

    public void deleteVisitorInfoByBeforeDate( Date ld )
    {
        String sql = "delete from stat_visit_info where visitTimeIn<=?";
        pe.update( sql, new Object[] { ld } );
    }

    public void deleteVisitorInfoByDate( Integer year, Integer month,
        Integer day, Integer hour )
    {
        String sql = "delete from stat_visit_info where visitYear=? and visitMonth=? and visitDay=? and visitHour=?";
        pe.update( sql, new Object[] { year, month, day, hour } );
    }

    public void deleteVisitorInfoAnalysisBySiteId( Long siteId )
    {
        String sql = "delete from stat_visitor_hour_analysis where siteId=?";
        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteStatDateHelperAllDate()
    {
        String sql = "delete from stat_date_helper";
        pe.update( sql );
    }

    public void saveNewDateForStatDateHelper( Date prevDate, Date currentDate )
    {
        String sql = "insert into stat_date_helper (prevDay, currentDay) values (?, ?)";
        pe.update( sql, new Object[] { prevDate, currentDate } );
    }

    public Map queryStatDateHelper()
    {
        String sql = "select * from stat_date_helper";
        return pe.querySingleResultMap( sql );
    }

    public void updateAllContentDayClickCountToZero()
    {
        String sql = "update content_main_info set clickDayCount=0 where clickDayCount>0";
        pe.update( sql );
    }

    public void updateAllContentDayCommCountToZero()
    {
        String sql = "update content_main_info set commDayCount=0 where commDayCount>0";
        pe.update( sql );
    }

    public void updateAllContentWeekClickCountToZero()
    {
        String sql = "update content_main_info set clickWeekCount=0 where clickWeekCount>0";
        pe.update( sql );
    }

    public void updateAllContentWeekCommCountToZero()
    {
        String sql = "update content_main_info set commWeekCount=0 where commWeekCount>0";
        pe.update( sql );
    }

    public void updateAllContentMonthClickCountToZero()
    {
        String sql = "update content_main_info set clickMonthCount=0 where clickMonthCount>0";
        pe.update( sql );
    }

    public void updateAllContentMonthCommCountToZero()
    {
        String sql = "update content_main_info set commMonthCount=0 where commMonthCount>0";
        pe.update( sql );
    }

    public void updateContentAllClickCount(
        StatContentVisitOrCommentDWMCount clickDWMCount, Long contentId )
    {
        String sql = "update content_main_info set clickDayCount=clickDayCount+?, clickWeekCount=clickWeekCount+?, clickMonthCount=clickMonthCount+?, clickCount=clickCount+? where contentId=?";
        pe.update( sql, new Object[] { clickDWMCount.getDayCount(),
            clickDWMCount.getWeekCount(), clickDWMCount.getMonthCount(),
            clickDWMCount.getNoLimitCount(), contentId } );
    }

    public void updateContentAllCommentAddOneCount( Long contentId )
    {
        String sql = "update content_main_info set commDayCount=commDayCount+1, commWeekCount=commWeekCount+1, commMonthCount=commMonthCount+1, commCount=commCount+1 where contentId=?";
        pe.update( sql, new Object[] { contentId } );
    }

    public void updateContentAllCommentMinusOneCount( Long contentId )
    {
        String sql = "update content_main_info set commDayCount=commDayCount-1, commWeekCount=commWeekCount-1, commMonthCount=commMonthCount-1, commCount=commCount-1 where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public Map queryDayVisitCountInfo( Long siteId, Integer y, Integer m,
        Integer d )
    {
        String sql = "select count(distinct uvid) as uvc, count(visitorId) as pvc, count(distinct ip) as ivc from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, y, m, d } );
    }

    public StatVisitorDayAnalysisBean queryDayVisitBeanByDayAndSiteId(
        Long siteId, Integer y, Integer m, Integer d )
    {
        String sql = "select * from stat_visitor_day_analysis where siteId=? and visitYear=? and visitMonth=? and visitDay=?";

        return ( StatVisitorDayAnalysisBean ) pe.querySingleBean( sql,
            new Object[] { siteId, y, m, d }, StatVisitorDayAnalysisBean.class );
    }

    public List queryDayVisitBeanListByDayAndSiteId( Long siteId, Date sd,
        Date ed )
    {
        String sql = "select * from stat_visitor_day_analysis where siteId=? and vdt>=? and vdt<=? order by vdt asc";

        return pe.queryBeanList( sql, new Object[] { siteId, sd, ed },
            StatVisitorDayAnalysisBean.class );
    }

    public List queryDayVisitSumUvListByDayAndSiteId( Long siteId, Date sd,
        Date ed )
    {
        String sql = "select sum(newUv) as newUv, sum(oldUv) as oldUv from stat_visitor_day_analysis where siteId=? and vdt>=? and vdt<=? order by vdt asc";

        return pe.queryResultMap( sql, new Object[] { siteId, sd, ed } );
    }

    public Long queryUrlConutBySiteId( Long siteId, String url )
    {
        String sql = "select count(*) from stat_visitor_url_analysis where siteId=? and url=?";

        return ( Long ) pe.querySingleObject( sql,
            new Object[] { siteId, url }, Long.class );
    }

    public Long queryAllAUrlConutBySiteId( Long siteId )
    {
        String sql = "select sum(clickCount) from stat_visitor_url_analysis where siteId=?";

        Object obj = pe.querySingleObject( sql, new Object[] { siteId } );

        if( obj instanceof BigDecimal )
        {
            return Long.valueOf( ( ( BigDecimal ) obj ).longValue() );
        }
        else if( obj instanceof Double )
        {
            return Long.valueOf( ( ( Double ) obj ).longValue() );
        }

        return Long.parseLong( obj.toString() );

    }

    public void saveUrl( Long siteId, String url, Long count )
    {
        String sql = "insert into stat_visitor_url_analysis (siteId, url, clickCount) values (?,?,?)";

        pe.update( sql, new Object[] { siteId, url, count } );
    }

    public void updateUrlOnePv( Long siteId, String url )
    {
        String sql = "update stat_visitor_url_analysis set clickCount=clickCount+1 where siteId=? and url=?";

        pe.update( sql, new Object[] { siteId, url } );
    }

    public Long queryCommonConutBySiteId( Long siteId, String val,
        String table, String target )
    {
        String sql = "select count(*) from " + table + " where siteId=? and "
            + target + "=?";

        return ( Long ) pe.querySingleObject( sql,
            new Object[] { siteId, val }, Long.class );
    }

    public Long queryAllCommonConutBySiteId( Long siteId, String table )
    {
        String sql = "select sum(pvCount) from " + table + " where siteId=?";

        Object obj = pe.querySingleObject( sql, new Object[] { siteId } );

        if( obj instanceof BigDecimal )
        {
            return Long.valueOf( ( ( BigDecimal ) obj ).longValue() );
        }
        else if( obj instanceof Double )
        {
            return Long.valueOf( ( ( Double ) obj ).longValue() );
        }

        return Long.parseLong( obj.toString() );

    }

    public void saveCommonInfo( Long siteId, String val, Long count,
        String table, String target )
    {
        String sql = "insert into " + table + " (siteId, " + target
            + ", pvCount) values (?,?,?)";

        pe.update( sql, new Object[] { siteId, val, count } );
    }

    public void updateCommonOnePv( Long siteId, String targetVal, String table,
        String target )
    {
        String sql = "update " + table
            + " set pvCount=pvCount+1 where siteId=? and " + target + "=?";

        pe.update( sql, new Object[] { siteId, targetVal } );
    }

    public void updateCommonPv( Long siteId, String targetVal, Integer count,
        String table, String target )
    {
        String sql = "update " + table
            + " set pvCount=pvCount+？ where siteId=? and " + target + "=?";

        pe.update( sql, new Object[] { count, siteId, targetVal } );
    }

    public Long queryAreaConutBySiteId( Long siteId, String area )
    {
        String sql = "select count(*) from stat_visitor_area_analysis where siteId=? and area=?";

        return ( Long ) pe.querySingleObject( sql,
            new Object[] { siteId, area }, Long.class );
    }

    public Long queryAllAreaConutBySiteId( Long siteId )
    {
        String sql = "select sum(pvCount) from stat_visitor_area_analysis where siteId=?";

        Object obj = pe.querySingleObject( sql, new Object[] { siteId } );

        if( obj instanceof BigDecimal )
        {
            return Long.valueOf( ( ( BigDecimal ) obj ).longValue() );
        }
        else if( obj instanceof Double )
        {
            return Long.valueOf( ( ( Double ) obj ).longValue() );
        }

        return Long.parseLong( obj.toString() );

    }

    public void saveArea( Long siteId, String area, Long count )
    {
        String sql = "insert into stat_visitor_area_analysis (siteId, area, pvCount) values (?,?,?)";

        pe.update( sql, new Object[] { siteId, area, count } );
    }

    public void updateAreaOnePv( Long siteId, String area )
    {
        String sql = "update stat_visitor_area_analysis set pvCount=pvCount+1 where siteId=? and area=?";

        pe.update( sql, new Object[] { siteId, area } );
    }

    public void updateDayVisitBeanByDayAndSiteId( Integer uvc, Integer ivc,
        Integer pvc, Integer newUv, Integer oldUv, Long siteId, Integer y,
        Integer m, Integer d )
    {
        String sql = "update stat_visitor_day_analysis set uvCount=?, ipCount=?, pvCount=?, newUv=?, oldUv=? where siteId=? and visitYear=? and visitMonth=? and visitDay=?";

        pe.update( sql, new Object[] { uvc, ivc, pvc, newUv, oldUv, siteId, y,
            m, d } );
    }

    public List queryUvidInfoByDay( Long siteId, Integer y, Integer m, Integer d )
    {
        String sql = "select distinct(uvId) from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=?";

        return pe.querySingleCloumn( sql, new Object[] { siteId, y, m, d },
            String.class );
    }

    public List queryUvidInfoByHour( Long siteId, Integer y, Integer m,
        Integer d, Integer h )
    {
        String sql = "select distinct(uvId) from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and visitHour=?";

        return pe.querySingleCloumn( sql, new Object[] { siteId, y, m, d, h },
            String.class );
    }

    public Long queryNewUvidCountByHour( Long siteId, Integer y, Integer m,
        Integer d, Integer h )
    {
        String sql = "select count(distinct uvid) as newUv from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and visitHour=? and uvid not in (select uvid from stat_uvid_trace)";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, y, m,
            d, h }, Long.class );
    }

    public Long queryOldUvidCountByHour( Long siteId, Integer y, Integer m,
        Integer d, Integer h )
    {
        String sql = "select count(distinct uvid) as oldUv from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and visitHour=? and uvid in (select uvid from stat_uvid_trace)";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, y, m,
            d, h }, Long.class );
    }

    public Long queryNewUvidCountByDay( Long siteId, Integer y, Integer m,
        Integer d )
    {
        String sql = "select count(distinct uvid) as newUv from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and uvid not in (select uvid from stat_uvid_trace)";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, y, m,
            d }, Long.class );
    }

    public Long queryOldUvidCountByDay( Long siteId, Integer y, Integer m,
        Integer d )
    {
        String sql = "select count(distinct uvid) as oldUv from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and uvid in (select uvid from stat_uvid_trace)";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, y, m,
            d }, Long.class );
    }

    public void saveNewUvidByDay( Long siteId, Integer y, Integer m, Integer d )
    {
        String sql = "insert into stat_uvid_trace (uvid) select distinct uvid as newUv from stat_visit_info where siteId=? and visitYear=? and visitMonth=? and visitDay=? and uvid not in (select uvid from stat_uvid_trace)";

        pe.update( sql, new Object[] { siteId, y, m, d } );
    }

    public Long queryUvidTarceExist( String uvid )
    {
        String sql = "select count(uvid) from stat_uvid_trace where uvid=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { uvid },
            Long.class );
    }

    public void saveUvidTarce( String uvid )
    {
        String sql = "insert into stat_uvid_trace (uvid) values (?)";

        pe.update( sql, new Object[] { uvid } );
    }

    public Integer queryNewUvidDayCountNotUse( Long siteId, Integer y,
        Integer m, Integer d )
    {
        String sql = "select sum(newUv) from stat_visitor_hour_analysis where siteId=? and visitYear=? and visitMonth=? and visitDay=?";

        Object obj = pe.querySingleObject( sql,
            new Object[] { siteId, y, m, d } );

        if( obj instanceof BigDecimal )
        {
            return Integer.valueOf( ( ( BigDecimal ) obj ).intValue() );
        }
        else if( obj instanceof Double )
        {
            return Integer.valueOf( ( ( Double ) obj ).intValue() );
        }

        return Integer.parseInt( obj.toString() );
    }

    public Integer queryOldUvidDayCountNotUse( Long siteId, Integer y,
        Integer m, Integer d )
    {
        String sql = "select sum(oldUv) from stat_visitor_hour_analysis where siteId=? and visitYear=? and visitMonth=? and visitDay=?";

        Object obj = pe.querySingleObject( sql,
            new Object[] { siteId, y, m, d } );

        if( obj instanceof BigDecimal )
        {
            return Integer.valueOf( ( ( BigDecimal ) obj ).intValue() );
        }
        else if( obj instanceof Double )
        {
            return Integer.valueOf( ( ( Double ) obj ).intValue() );
        }

        return Integer.parseInt( obj.toString() );
    }

    public Map queryUvidInfoFirstVisitGateUrlByDay( Long siteId, String uvid,
        Integer y, Integer m, Integer d )
    {
        String sql = "select * from stat_visit_info where siteId=? and uvId=? and visitYear=? and visitMonth=? and visitDay=? order by visitTimeIn asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { siteId, uvid, y, m,
            d } );
    }

    public Map queryUvidInfoLastVisitExitUrlByDay( Long siteId, String uvid,
        Integer y, Integer m, Integer d )
    {
        String sql = "select * from stat_visit_info where siteId=? and uvId=? and visitYear=? and visitMonth=? and visitDay=? order by visitTimeIn desc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { siteId, uvid, y, m,
            d } );
    }

    public Long queryUvidInfoFirstVisitGateExist( Long siteId, Integer y,
        Integer m, Integer d, String url )
    {
        String sql = "select count(*) from stat_visitor_day_gate_analysis where siteId=? and visitYear=? and visitMonth=? and visitDay=? and url=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, y, m,
            d, url } );
    }

    public Long queryUvidInfoExitVisitGateExist( Long siteId, Integer y,
        Integer m, Integer d, String url )
    {

        String sql = "select count(*) from stat_visitor_day_exit_analysis where siteId=? and visitYear=? and visitMonth=? and visitDay=? and url=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, y, m,
            d, url } );
    }

    public void saveUvidVisitGate( Long siteId, Integer y, Integer m,
        Integer d, String url, Integer count )
    {
        String sql = "insert into stat_visitor_day_gate_analysis (siteId, visitYear, visitMonth, visitDay, url, count) values (?,?,?,?,?,?)";

        pe.update( sql, new Object[] { siteId, y, m, d, url, count } );
    }

    public void updateUvidVisitGate( Long siteId, Integer y, Integer m,
        Integer d, String url )
    {
        String sql = "update stat_visitor_day_gate_analysis set count=count+1 where siteId=? and visitYear=? and visitMonth=? and visitDay=? and url=?";

        pe.update( sql, new Object[] { siteId, y, m, d, url } );
    }

    public void updateUvidVisitExit( Long siteId, Integer y, Integer m,
        Integer d, String url )
    {
        String sql = "update stat_visitor_day_exit_analysis set count=count+1 where siteId=? and visitYear=? and visitMonth=? and visitDay=? and url=?";

        pe.update( sql, new Object[] { siteId, y, m, d, url } );
    }

    public void saveUvidVisitExit( Long siteId, Integer y, Integer m,
        Integer d, String url, Integer count )
    {
        String sql = "insert into stat_visitor_day_exit_analysis (siteId, visitYear, visitMonth, visitDay, url, count) values (?,?,?,?,?,?)";

        pe.update( sql, new Object[] { siteId, y, m, d, url, count } );
    }

    public Map querySiteAllPvUvIpCurrDayCount( Long siteId, Integer y,
        Integer m, Integer d )
    {
        String sql = "select sum(pvCount) as pv, sum(uvCount) as uv, sum(ipCount) as ip, sum(newUv) as newUv, sum(oldUv) as oldUv from stat_visitor_day_analysis where siteId=? and visitYear=? and visitMonth=? and visitDay=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, y, m, d } );
    }

    public Map querySiteAllPvUvIpWeek7DayCount( Long siteId, Date sd, Date ed )
    {
        String sql = "select sum(pvCount) as pv, sum(uvCount) as uv, sum(ipCount) as ip, sum(newUv) as newUv, sum(oldUv) as oldUv from stat_visitor_day_analysis where siteId=? and vdt>=? and vdt<=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, sd, ed } );
    }

    public Map querySiteAllPvUvIpMonthCount( Long siteId, Integer y, Integer m )
    {
        String sql = "select sum(pvCount) as pv, sum(uvCount) as uv, sum(ipCount) as ip, sum(newUv) as newUv, sum(oldUv) as oldUv from stat_visitor_day_analysis where siteId=? and visitYear=? and visitMonth=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, y, m } );
    }

    public Map querySiteAllPvUvIpCount( Long siteId )
    {
        String sql = "select sum(pvCount) as pv, sum(uvCount) as uv, sum(ipCount) as ip, sum(newUv) as newUv, sum(oldUv) as oldUv from stat_visitor_day_analysis where siteId=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId } );
    }

    public Long querySiteAllDayCount( Long siteId )
    {
        String sql = "select count(*) from stat_visitor_day_analysis where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId } );
    }

    public Map querySiteMaxPvCount( Long siteId )
    {
        String sql = "select pvCount, vdt from stat_visitor_day_analysis where siteId=? order by pvCount desc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { siteId } );
    }

    public Map querySiteMaxUvCount( Long siteId )
    {
        String sql = "select uvCount, vdt from stat_visitor_day_analysis where siteId=? order by uvCount desc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { siteId } );
    }

    public Map querySiteMaxIpCount( Long siteId )
    {
        String sql = "select ipCount, vdt from stat_visitor_day_analysis where siteId=? order by ipCount desc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { siteId } );
    }

    public Long queryAllSiteHostPvCount()
    {
        String sql = "select sum(pvCount) as pv from stat_visitor_host_analysis";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql ) );
    }

    public List querySiteAllHost()
    {
        String sql = "select host from stat_visitor_host_analysis";

        return pe.querySingleCloumn( sql );
    }

    public Long querySiteVisitorCount( Long siteId )
    {
        String sql = "select count(*) from stat_visit_info where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId } );
    }

    public Long querySiteVisitorContentCount( Long siteId )
    {
        String sql = "select count(*) from stat_visitor_content_analysis vca inner join content_main_info ci on vca.contentId=ci.contentId where vca.siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId } );
    }

    public List querySiteVisitor( Long siteId, Long start, Integer size )
    {
        String sql = "select * from stat_visit_info where siteId=? order by visitorId desc limit ?,?";

        return pe.query( sql, new Object[] { siteId, start, size },
            new StatVisitInfoBeanTransform() );
    }

    public List querySiteVisitorContentPvInfo( Long siteId, Long start,
        Integer size )
    {
        String sql = "select * from stat_visitor_content_analysis vca inner join content_main_info ci on vca.contentId=ci.contentId where vca.siteId=? order by pvCount desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, start, size } );
    }

    public Map querySiteVistorSumAllByHour( Long siteId, Integer hour )
    {
        String sql = "select sum(uvCount) as uvc, sum(pvCount) as pvc, sum(ipCount) as ivc from stat_visitor_hour_analysis where siteId=? and visitHour=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, hour } );
    }

    public Map querySiteVistorSumUvByHour( Long siteId, Integer hour )
    {
        String sql = "select sum(newUv) as nuv, sum(oldUv) as ouv  from stat_visitor_hour_analysis where siteId=? and visitHour=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, hour } );
    }

    public Map querySiteVistorHourSumUvBySite( Long siteId )
    {
        String sql = "select sum(newUv) as nuv, sum(oldUv) as ouv  from stat_visitor_hour_analysis where siteId=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId } );
    }

    public Map querySiteVistorDaySumUvBySite( Long siteId, Date sd, Date ed )
    {
        String sql = "select sum(newUv) as nuv, sum(oldUv) as ouv  from stat_visitor_day_analysis where siteId=? and vdt>=? and vdt<=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, sd, ed } );
    }

    public List querySiteVistorAeraAllPvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_area_analysis where siteId=? order by pvCount desc limit 100";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorGateAllPvBySite( Long siteId )
    {
        String sql = "select distinct ( url),sum(count) as count from stat_visitor_day_gate_analysis where siteId=? group by url order by count desc limit 100";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorUrlAllPvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_url_analysis where siteId=? order by clickCount desc limit 100";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorClassAllPvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_class_analysis where siteId=? order by pvCount desc";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorExitAllPvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_day_exit_analysis where siteId=? order by count desc limit 100";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public Long querySiteVistorAeraSumPvBySite( Long siteId )
    {
        String sql = "select sum(pvCount) as pvc from stat_visitor_area_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }
    
    public Long querySiteVistorAeraSumPvBySiteAndArea( Long siteId, String area )
    {
        String sql = "select sum(pvCount) from stat_visitor_area_analysis where siteId=? and area like '%"+area+"%'";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorGateSumPvBySite( Long siteId )
    {
        String sql = "select sum(count) as count from stat_visitor_day_gate_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorUrlPvSumPvBySite( Long siteId )
    {
        String sql = "select sum(clickCount) as count from stat_visitor_url_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorClassPvSumPvBySite( Long siteId )
    {
        String sql = "select sum(pvCount) as count from stat_visitor_class_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorExitSumPvBySite( Long siteId )
    {
        String sql = "select sum(count) as count from stat_visitor_day_exit_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorReffHostSumUvBySite( Long siteId )
    {
        String sql = "select sum(uvCount) as uvc from stat_visitor_src_s_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorSearchHostSumUvBySite( Long siteId )
    {
        String sql = "select sum(uvCount) as uvc from stat_visitor_search_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorSearchKeySumUvBySite( Long siteId )
    {
        String sql = "select sum(uvCount) as uvc from stat_visitor_seh_key_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorOsSumPvBySite( Long siteId )
    {
        String sql = "select sum(pvCount) as pvc from stat_visitor_os_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorBrSumPvBySite( Long siteId )
    {
        String sql = "select sum(pvCount) as pvc from stat_visitor_br_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorLaSumPvBySite( Long siteId )
    {
        String sql = "select sum(pvCount) as pvc from stat_visitor_la_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public Long querySiteVistorScSumPvBySite( Long siteId )
    {
        String sql = "select sum(pvCount) as pvc from stat_visitor_resol_analysis where siteId=?";

        return MathUtil.changeSUMResToLong( pe.querySingleObject( sql,
            new Object[] { siteId } ) );
    }

    public List querySiteVistorReffHostAllUvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_src_s_analysis where siteId=?";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorSearchHostAllUvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_search_analysis where siteId=?";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorSearchKeyAllUvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_seh_key_analysis where siteId=? order by uvCount desc limit 100";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorOsAllUvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_os_analysis where siteId=? order by pvCount desc";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorBrAllUvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_br_analysis where siteId=? order by pvCount desc";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorLaAllUvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_la_analysis where siteId=?";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySiteVistorScAllUvBySite( Long siteId )
    {
        String sql = "select * from stat_visitor_resol_analysis where siteId=? order by pvCount desc";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public Long querySiteHostPvCount( String host )
    {
        String sql = "select pvCount from stat_visitor_host_analysis where host=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { host } );
    }

    public List queryBlackClientIpInfo()
    {
        String sql = "select * from site_de_access";

        return pe.queryResultMap( sql );
    }

    public List queryBlackClientIpStr()
    {
        String sql = "select blackIp from site_de_access";

        return pe.querySingleCloumn( sql, String.class );
    }

    public Long queryBlackClientIpCount( String ip )
    {
        String sql = "select count(blackIp) from site_de_access where blackIp=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { ip },
            Long.class );
    }

    public Map querySingleBlackClientIpInfo( String ip )
    {
        String sql = "select * from site_de_access where blackIp=?";

        return pe.querySingleResultMap( sql, new Object[] { ip } );
    }

    public void deleteBlackClientIp( String ip )
    {
        String sql = "delete from site_de_access where blackIp=?";

        pe.update( sql, new Object[] { ip } );
    }

    public void saveBlackIp( String ip, Date startDT, Date endDT,
        Integer effectHour, Integer isDeForever )
    {
        String sql = "insert into site_de_access (blackIp, startDT, endDT, effectHour, isDeForever) values (?,?,?,?,?)";

        pe.update( sql, new Object[] { ip, startDT, endDT, effectHour,
            isDeForever } );
    }

    public void saveBlackIpAccess( String ip, String tragetUrl,
        String dangerStr, String queryStr, Date eventDT )
    {
        String sql = "insert into site_de_access_trace (ip, targetUrl, dangerStr, queryStr, eventDT, eventDay) values (?,?,?,?,?,?)";

        pe.update( sql, new Object[] { ip, tragetUrl, dangerStr, queryStr,
            eventDT, eventDT } );
    }

    public List queryBlackIpAccessInfoList( Long start, Integer size )
    {
        String sql = "select *, (select count(*) from site_de_access where blackIp=ip) as ipCount from site_de_access_trace order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { start, size } );
    }

    public List queryBlackIpInfoList( Long start, Integer size )
    {
        String sql = "select * from site_de_access order by infoId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { start, size } );
    }

    public List queryBlackIpAccessInfoList( Timestamp eventDt, Long start,
        Integer size )
    {
        String sql = "select *, (select count(*) from site_de_access where blackIp=ip) as ipCount from site_de_access_trace where eventDay=? order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { eventDt, start, size } );
    }

    public Map querySingleClientIpDangerAccessTrace( Long trId )
    {
        String sql = "select * from site_de_access_trace where trId=?";

        return pe.querySingleResultMap( sql, new Object[] { trId } );
    }

    public Long queryClientIpDangerAccessTraceCount()
    {
        String sql = "select count(*) from site_de_access_trace";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryClientIpDangerAccessTraceCount( Timestamp eventDt )
    {
        String sql = "select count(*) from site_de_access_trace where eventDay=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { eventDt },
            Long.class );
    }

    public Long queryClientIpInfoCount()
    {
        String sql = "select count(*) from site_de_access";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public List queryLiftBanBlackClientIpInfo( Timestamp currTime )
    {
        String sql = "select * from site_de_access where endDT<=? and isDeForever=0";

        return pe.queryResultMap( sql, new Object[] { currTime } );
    }

    public void saveManagerLoginTrace( String ip, String userName,
        Timestamp eventDT, Integer ls )
    {
        String sql = "insert into stat_manager_login_trace (ip, userName, eventDT, loginSuccess) values (?,?,?,?)";

        pe.update( sql, new Object[] { ip, userName, eventDT, ls } );
    }

    public Long queryManagerLoginTraceCount()
    {
        String sql = "select count(*) from stat_manager_login_trace";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryManagerLoginTraceCount( String userName )
    {
        String sql = "select count(*) from stat_manager_login_trace where userName=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userName },
            Long.class );
    }

    public List queryManagerLoginTrace( Long start, Integer size )
    {
        String sql = "select * from stat_manager_login_trace order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { start, size } );
    }

    public List queryManagerLoginTrace( String userName, Long start,
        Integer size )
    {
        String sql = "select * from stat_manager_login_trace where userName=? order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { userName, start, size } );
    }

    public void saveFlowExcuteTrace( String userName, String flowCommand,
        String flowName, Timestamp excuteDt, String ip, String params, Long siteId )
    {
        String sql = "insert into stat_flow_exe_trace (actUserName, flowCommand, flowName, executeDt, ip, params, siteId) values (?,?,?,?,?,?,?)";

        pe.update( sql, new Object[] { userName, flowCommand, flowName,
            excuteDt, ip, params,  siteId } );
    }

    public void deleteFlowExcuteTraceByDay( Timestamp day )
    {
        String sql = "delete from stat_flow_exe_trace where executeDt<?";

        pe.update( sql, new Object[] { day } );
    }

    public Long queryFlowExcuteTraceCount( String userName, Long siteId )
    {
        String sql = "select count(*) from stat_flow_exe_trace where actUserName=? and siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userName,
            siteId }, Long.class );
    }

    public List queryFlowExcuteTrace( String userName, Long siteId, Long start,
        Integer size )
    {
        String sql = "select * from stat_flow_exe_trace where actUserName=? and siteId=? order by executeDt desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { userName, siteId, start,
            size } );
    }

    public Long queryFlowExcuteTraceCount( Long siteId )
    {
        String sql = "select count(*) from stat_flow_exe_trace where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId },
            Long.class );
    }

    public List queryFlowExcuteTrace( Long siteId, Long start, Integer size )
    {
        String sql = "select * from stat_flow_exe_trace where siteId=? order by executeDt desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, start, size } );
    }

    /**
     * LZTE新加统计业务
     */

    public void save( StatClassContentTrace vo )
    {
        pe.save( vo );
    }

    public UpdateState save( StatManagerContentTrace vo )
    {
        return pe.save( vo );
    }

    public UpdateState save( StatOrgContentTrace vo )
    {
        return pe.save( vo );
    }

    public StatClassContentTraceBean querySingleStatClassContentTraceBean(
        Long classId, Integer y, Integer m, Integer d )
    {
        String sql = "select * from stat_class_content_trace where upYear=? and upMon=?  and upDay=? and classId=?";

        return ( StatClassContentTraceBean ) pe.querySingleBean( sql,
            new Object[] { y, m, d, classId }, StatClassContentTraceBean.class );
    }

    public void updateStatClassContentTraceBean( Long classId, Integer y,
        Integer m, Integer d, Integer addCount, Integer pubCount,
        Integer imageCount, Integer videoCount )
    {
        String sql = "update stat_class_content_trace set addCount=addCount+?, pubCount=pubCount+?, imgCount=imgCount+?, videoCount=videoCount+? where classId=? and upYear=? and upMon=? and upDay=?";

        pe.update( sql, new Object[] { addCount, pubCount, imageCount,
            videoCount, classId, y, m, d } );
    }

    public StatManagerContentTraceBean querySingleStatManagerContentTraceBean(
        Long userId, Integer y, Integer m, Integer d )
    {
        String sql = "select * from stat_manager_content_trace where upYear=? and upMon=? and upDay=? and userId=?";

        return ( StatManagerContentTraceBean ) pe
            .querySingleBean( sql, new Object[] { y, m, d, userId },
                StatManagerContentTraceBean.class );
    }

    public void updateStatManagerContentTraceBean( Long userId, Integer y,
        Integer m, Integer d, Integer addCount, Integer pubCount,
        Integer imageCount, Integer videoCount )
    {
        String sql = "update stat_manager_content_trace set addCount=addCount+?, pubCount=pubCount+?, imgCount=imgCount+?, videoCount=videoCount+? where userId=? and upYear=? and upMon=? and upDay=?";

        pe.update( sql, new Object[] { addCount, pubCount, imageCount,
            videoCount, userId, y, m, d } );
    }

    public StatOrgContentTraceBean querySingleStatOrgContentTraceBean(
        Long userId, Integer y, Integer m, Integer d )
    {
        String sql = "select * from stat_org_content_trace where upYear=? and upMon=? and upDay=? and orgId=?";

        return ( StatOrgContentTraceBean ) pe.querySingleBean( sql,
            new Object[] { y, m, d, userId }, StatOrgContentTraceBean.class );
    }

    public void updateStatOrgContentTraceBean( Long orgId, Integer y,
        Integer m, Integer d, Integer addCount, Integer pubCount,
        Integer imageCount, Integer videoCount )
    {
        String sql = "update stat_org_content_trace set addCount=addCount+?, pubCount=pubCount+?, imgCount=imgCount+?, videoCount=videoCount+? where orgId=? and upYear=? and upMon=? and upDay=?";

        pe.update( sql, new Object[] { addCount, pubCount, imageCount,
            videoCount, orgId, y, m, d } );
    }

    public void saveClassUpdateTrace( Map params )
    {
        String sql = "insert into stat_class_update_trace (classId, siteId, updateYear, updateMon, updateDay, updateDate) values (?,?,?,?,?,?)";

        pe.update( sql, params );
    }

    public Map querySingleClassUpdateTrace( Long classId )
    {
        String sql = "select * from stat_class_update_trace where classId=?";

        return pe.querySingleResultMap( sql, new Object[] { classId } );
    }

    public List queryAllClassUpdateTrace()
    {
        String sql = "select * from stat_class_update_trace";

        return pe.queryResultMap( sql );
    }

    public void updateClassUpdateTrace( Map params )
    {
        String sql = "update stat_class_update_trace set updateYear=?, updateMon=?, updateDay=?, updateDate=? where classId=?";

        pe.update( sql, params );
    }

    public void deleteClassUpdateTrace( Long classId )
    {
        String sql = "delete from stat_class_update_trace where classId=?";

        pe.update( sql, new Object[] { classId } );
    }

    public void deleteClassContentTrace( Long classId )
    {
        String sql = "delete from stat_class_content_trace where classId=?";

        pe.update( sql, new Object[] { classId } );
    }

    public void deleteOrgUpdateTrace( Long orgId )
    {
        String sql = "delete from stat_org_content_trace where orgId=?";

        pe.update( sql, new Object[] { orgId } );
    }

    public void deleteManagerUpdateTrace( Long userId )
    {
        String sql = "delete from stat_manager_content_trace where userId=?";

        pe.update( sql, new Object[] { userId } );
    }

    public List queryStatClassContentTraceCollBySiteId( String fs, String ids,
        String orderBy, String orderFlag, Date weekEnd, Date threeMonEnd,
        Date halfYearEnd, Date yearEnd )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, sg.siteId, sg.siteName from site_group sg left join stat_class_content_trace sc on sg.siteId=sc.siteId GROUP BY sg.siteId ORDER BY "
            + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {
            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, sg.siteId, sg.siteName from site_group sg left join stat_class_content_trace sc on sg.siteId=sc.siteId where sg.siteId in("
                + ids
                + ") GROUP BY sg.siteId ORDER BY "
                + orderBy
                + " "
                + orderFlag;
        }

        return pe.queryResultMap( sql, new SiteTraceResultCallBack( fs,
            weekEnd, threeMonEnd, halfYearEnd, yearEnd, this ) );

    }

    public List queryStatClassContentTraceCollByClassId( String siteFlag,
        String orderBy, String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, sg.classId, sg.className from contentclass sg left join stat_class_content_trace sc ON sg.classId = sc.classId where sg.siteFlag=? and classType<4  GROUP BY sg.classId ORDER BY sg.linearOrderFlag asc, "
            + orderBy + " " + orderFlag;

        return pe.queryResultMap( sql, new Object[] { siteFlag },
            new ClassOrgTreeResultCallBack() );

    }

    public List queryStatOrgContentTraceCollBySiteId( String ids,
        String orderBy, String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, so.orgId, so.orgName from system_organization so left join stat_org_content_trace soc ON so.orgId = soc.orgId GROUP BY so.orgId ORDER BY "
            + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {
            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, so.orgId, so.orgName from system_organization so left join stat_org_content_trace soc ON so.orgId = soc.orgId where so.orgId in("
                + ids
                + ") GROUP BY so.orgId ORDER BY so.linearOrderFlag asc, "
                + orderBy + " " + orderFlag;
        }

        return pe.queryResultMap( sql, new ClassOrgTreeResultCallBack() );

    }

    public List queryStatManagerContentTraceCollBySiteId( String ids,
        String orderBy, String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, su.userId, su.userName, su.userTrueName, (select  orgId from system_organization where linearOrderFlag=su.relateOrgCode) as orgId from systemuser su left join stat_manager_content_trace sm ON su.userId = sm.userId GROUP BY su.userId ORDER BY "
            + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {

            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, su.userId, su.userName, su.userTrueName,(select  orgId from system_organization where linearOrderFlag=su.relateOrgCode) as orgId  from systemuser su left join stat_manager_content_trace sm ON su.userId = sm.userId where su.relateOrgCode in("
                + ids
                + ") GROUP BY su.userId ORDER BY "
                + orderBy
                + " "
                + orderFlag;
        }

        return pe.queryResultMap( sql, new ClassOrgTreeResultCallBack() );

    }

    public List queryStatClassContentTraceCollBySiteId( String fs, String ids,
        Date st, Date et, String orderBy, String orderFlag, Date weekEnd,
        Date threeMonEnd, Date halfYearEnd, Date yearEnd )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, sg.siteId, sg.siteName from site_group sg left join stat_class_content_trace sc on sg.siteId=sc.siteId "
            + "and sc.upDT >=? and sc.upDT <? GROUP BY sg.siteId ORDER BY "
            + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {

            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, sg.siteId, sg.siteName from site_group sg left join stat_class_content_trace sc on sg.siteId=sc.siteId "
                + "and sc.upDT >=? and sc.upDT <? where sg.siteId in("
                + ids
                + ") GROUP BY sg.siteId ORDER BY " + orderBy + " " + orderFlag;
        }

        return pe.queryResultMap( sql, new Object[] { st, et },
            new SiteTraceResultCallBack( fs, weekEnd, threeMonEnd, halfYearEnd,
                yearEnd, this ) );

    }

    public List queryStatClassContentTraceCollByClassId( Date st, Date et,
        String siteFlag, String orderBy, String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, sg.classId, sg.className from contentclass sg left join stat_class_content_trace sc ON sg.classid = sc.classId and sc.upDT >=? and sc.upDT <? where sg.siteFlag=? and sg.classType<4 GROUP BY sg.classId ORDER BY sg.linearOrderFlag asc, "
            + orderBy + " " + orderFlag;

        return pe.queryResultMap( sql, new Object[] { st, et, siteFlag },
            new ClassOrgTreeResultCallBack() );

    }

    public List queryStatOrgContentTraceCollBySiteId( String ids, Date st,
        Date et, String orderBy, String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, so.orgId,so.orgName from system_organization so left join stat_org_content_trace soc ON so.orgId = soc.orgId "
            + "and soc.upDT >=? and soc.upDT <? GROUP BY so.orgId ORDER BY "
            + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {
            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, so.orgId, so.orgName from system_organization so left join stat_org_content_trace soc ON so.orgId = soc.orgId "
                + "and soc.upDT >=? and soc.upDT <? where so.orgId in("
                + ids
                + ") GROUP BY so.orgId ORDER BY so.linearOrderFlag asc, "
                + orderBy + " " + orderFlag;
        }

        return pe.queryResultMap( sql, new Object[] { st, et },
            new ClassOrgTreeResultCallBack() );

    }

    public List queryStatManagerContentTraceCollBySiteId( String ids, Date st,
        Date et, String orderBy, String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, su.userId, su.userName, su.userTrueName, (select orgId from system_organization where linearOrderFlag=su.relateOrgCode) as orgId from systemuser su left join stat_manager_content_trace sm ON su.userId = sm.userId "
            + "and sm.upDT >=? and sm.upDT <? GROUP BY su.userId ORDER BY "
            + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {
            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, su.userId, su.userName, su.userTrueName, (select orgId from system_organization where linearOrderFlag=su.relateOrgCode) as orgId from systemuser su left join stat_manager_content_trace sm ON su.userId = sm.userId "
                + "and sm.upDT >=? and sm.upDT <? where su.relateOrgCode in("
                + ids
                + ") GROUP BY su.userId ORDER BY "
                + orderBy
                + " "
                + orderFlag;
        }

        return pe.queryResultMap( sql, new Object[] { st, et },
            new ClassOrgTreeResultCallBack() );

    }

    public Map queryStatAllClassContentTraceColl( String fs, String ids,
        String orderBy, String orderFlag, Date weekEnd, Date threeMonEnd,
        Date halfYearEnd, Date yearEnd )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_class_content_trace sc "
            + "order by " + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {
            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_class_content_trace sc where sc.siteId in("
                + ids + ") " + "order by " + orderBy + " " + orderFlag;
        }

        return pe.querySingleResultMap( sql, null, new SiteTraceResultCallBack(
            fs, weekEnd, threeMonEnd, halfYearEnd, yearEnd, this ) );

    }

    public Map queryStatClassContentTraceBySE( Integer year )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, upYear  from stat_class_content_trace sc where sc.upYear=?";

        return pe.querySingleResultMap( sql, new Object[] { year } );
    }

    public Map queryStatClassContentTraceBySE( Integer year, Integer mon )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, upYear, upMon from stat_class_content_trace sc where sc.upYear=? and sc.upMon=?";

        return pe.querySingleResultMap( sql, new Object[] { year, mon } );
    }

    public Map queryStatClassContentTraceBySE( Integer year, Integer mon,
        Integer day )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, upYear, upMon, upDay from stat_class_content_trace sc where sc.upYear=? and sc.upMon=? and sc.upDay=?";

        return pe.querySingleResultMap( sql, new Object[] { year, mon, day } );
    }

    public Map queryStatClassContentTraceBySE( Integer year, Long siteId )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, upYear  from stat_class_content_trace sc where sc.siteId=? and sc.upYear=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, year } );
    }

    public Map queryStatClassContentTraceBySE( Integer year, Integer mon,
        Long siteId )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, upYear, upMon from stat_class_content_trace sc where sc.siteId=? and sc.upYear=? and sc.upMon=?";

        return pe
            .querySingleResultMap( sql, new Object[] { siteId, year, mon } );
    }

    public Map queryStatClassContentTraceBySE( Integer year, Integer mon,
        Integer day, Long siteId )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount, upYear, upMon, upDay from stat_class_content_trace sc where sc.siteId=? and sc.upYear=? and sc.upMon=? and sc.upDay=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, year, mon,
            day } );
    }

    public Map queryStatAllClassContentTraceColl( Long siteId )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_class_content_trace sc where sc.siteId=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId } );

    }

    public Map queryStatAllClassContentTraceColl( Date st, Date et, Long siteId )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_class_content_trace sc where sc.siteId=? and sc.upDT >=? and sc.upDT <?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, st, et } );

    }

    public Map queryStatAllOrgContentTraceColl( String ids, String orderBy,
        String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_org_content_trace sc "
            + "order by " + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {
            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_org_content_trace sc where sc.orgId in("
                + ids + ") " + "order by " + orderBy + " " + orderFlag;
        }

        return pe.querySingleResultMap( sql );

    }

    public Map queryStatAllManagerContentTraceColl( String ids, String orderBy,
        String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_manager_content_trace sc "
            + "order by " + orderBy + " " + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {
            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_manager_content_trace sc where sc.userId in (select userId from systemuser where relateOrgCode in ("
                + ids + ")) " + "order by " + orderBy + " " + orderFlag;
        }

        return pe.querySingleResultMap( sql );

    }

    public Map queryStatAllClassContentTraceColl( String fs, String ids,
        Date st, Date et, String orderBy, String orderFlag, Date weekEnd,
        Date threeMonEnd, Date halfYearEnd, Date yearEnd )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_class_content_trace sc "
            + "where sc.upDT >=? and sc.upDT <? ORDER BY "
            + orderBy
            + " "
            + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {
            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_class_content_trace sc "
                + "where sc.upDT >=? and sc.upDT <? and sc.siteId in("
                + ids
                + ") ORDER BY " + orderBy + " " + orderFlag;
        }

        return pe.querySingleResultMap( sql, new Object[] { st, et },
            new SiteTraceResultCallBack( fs, weekEnd, threeMonEnd, halfYearEnd,
                yearEnd, this ) );

    }

    public Map queryStatAllManagerContentTraceColl( String ids, Date st,
        Date et, String orderBy, String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_manager_content_trace sc "
            + "where sc.upDT >=? and sc.upDT <? ORDER BY "
            + orderBy
            + " "
            + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {

            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_manager_content_trace sc "
                + "where sc.upDT >=? and sc.upDT <? and sc.userId in (select userId from systemuser where relateOrgCode in ("
                + ids + ")) ORDER BY " + orderBy + " " + orderFlag;
        }

        return pe.querySingleResultMap( sql, new Object[] { st, et } );

    }

    public Map queryStatAllOrgContentTraceColl( String ids, Date st, Date et,
        String orderBy, String orderFlag )
    {
        String sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_org_content_trace sc "
            + "where sc.upDT >=? and sc.upDT <? ORDER BY "
            + orderBy
            + " "
            + orderFlag;

        if( StringUtil.isStringNotNull( ids ) )
        {

            sql = "select sum(addCount) as addAllCount, sum(pubCount) as pubAllCount,sum(imgCount) as imgAllCount,sum(videoCount) as videoAllCount from stat_org_content_trace sc "
                + "where sc.upDT >=? and sc.upDT <? and sc.orgId in("
                + ids
                + ") ORDER BY " + orderBy + " " + orderFlag;
        }

        return pe.querySingleResultMap( sql, new Object[] { st, et } );

    }

    public List queryStatClassContentAllZeroTraceBySiteAndClass( String siteFlag )
    {
        String sql = "select * from (select sum(pubCount) as allPubCount, cc.classId,cc.className from contentclass cc left join stat_class_content_trace  sc on sc.classId=cc.classId where cc.siteFlag=? and cc.classType<4 group by cc.classId) tmp where tmp.allPubCount<1 or tmp.allPubCount is null";

        return pe.queryResultMap( sql, new Object[] { siteFlag },
            new ClassOrgTreeResultCallBack() );

    }

    public List queryStatClassContentAllZeroTraceBySiteAndClassByFlags(
        String fs )
    {
        String sql = "select * from (select sum(pubCount) as allPubCount, cc.classId,cc.className from contentclass cc left join stat_class_content_trace  sc on sc.classId=cc.classId where cc.siteFlag in ("
            + fs
            + ") and cc.classType<4 group by cc.classId) tmp where tmp.allPubCount<1 or tmp.allPubCount is null";

        return pe.queryResultMap( sql );

    }

    public List queryStatClassContentAllZeroTraceBySiteAndClass()
    {
        String sql = "select * from (select sum(pubCount) as allPubCount, cc.classId, cc.className from contentclass cc left join stat_class_content_trace  sc on sc.classId=cc.classId where cc.classType<4 group by cc.classId) tmp where tmp.allPubCount<1 or tmp.allPubCount is null";

        return pe.queryResultMap( sql, new ClassOrgTreeResultCallBack() );
    }

    public List queryStatSiteContentWeekAndMonAndYearEndCountBySiteId(
        String siteFlag, Date end )
    {
        String sql = "select cc.classId, cc.className, (select updateDate from stat_class_update_trace where classId=cc.classId) as ud from contentclass cc left join stat_class_update_trace sc on cc.classId=sc.classId where cc.siteFlag=? and updateDate<?";

        return pe.queryResultMap( sql, new Object[] { siteFlag, end },
            new SiteEmptyClassInfoResultCallBack() );
    }

    public List queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( Date end )
    {
        String sql = "select cc.classId, (select updateDate from stat_class_update_trace where classId=cc.classId) as ud from contentclass cc left join stat_class_update_trace sc on cc.classId=sc.classId where updateDate<?";

        return pe.queryResultMap( sql, new Object[] { end },
            new SiteEmptyClassInfoResultCallBack() );
    }

    public List queryStatSiteContentWeekAndMonAndYearEndCountBySiteFlag(
        String fs, Date end )
    {
        String sql = "select cc.classId, (select updateDate from stat_class_update_trace where classId=cc.classId) as ud from contentclass cc left join stat_class_update_trace sc on cc.classId=sc.classId where updateDate<? and cc.siteFlag in ("
            + fs + ")";

        return pe.queryResultMap( sql, new Object[] { end },
            new SiteEmptyClassInfoResultCallBack() );
    }

    public Long querySiteCount()
    {
        String sql = "select count(*) from site_group";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryOrgCount()
    {
        String sql = "select count(*) from system_organization";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryManagerCount()
    {
        String sql = "select count(*) from systemuser";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryContentClassCount()
    {
        String sql = "select count(*) from contentclass where classType<4";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryAddContentCount()
    {
        String sql = "select sum(addCount) as allAddCount from stat_class_content_trace";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryPubContentCount()
    {
        String sql = "select sum(pubCount) as allPubCount from stat_class_content_trace";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryImageInfoCount()
    {
        String sql = "select sum(imgCount) as allImgCount from stat_class_content_trace";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Long queryVideoInfoCount()
    {
        String sql = "select sum(videoCount) as allVideoCount from stat_class_content_trace";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }
    
    public Long queryFilterInfoCount()
    {
        String sql = "select count(*) as filterCount from site_de_access_trace";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }
    
    public Long queryBlackInfoCount()
    {
        String sql = "select count(*) as blackCount from site_de_access";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public Map queryPubTrace( Long cId )
    {
        String sql = "select * from stat_content_pub_trace where contentId=?";

        return pe.querySingleResultMap( sql, new Object[] { cId } );
    }

    public void savePubTracePub( Long cId, Integer ic, Integer iv )
    {
        String sql = "insert into stat_content_pub_trace (contentId, isPub, imgCount, videoCount) values (?, ?, ?, ?)";

        pe.update( sql, new Object[] { cId, Integer.valueOf( 1 ), ic, iv } );
    }

    public void updatePubTrace( Long cId, Integer ic, Integer iv )
    {
        String sql = "update stat_content_pub_trace set imgCount=?, videoCount=? where contentId=?";

        pe.update( sql, new Object[] { ic, iv, cId } );
    }

    public void updatePubTracePub( Long cId, Integer st )
    {
        String sql = "update stat_content_pub_trace set isPub=? where contentId=?";

        pe.update( sql, new Object[] { st, cId } );
    }

    public void deletePubTracePub( Long cId )
    {
        String sql = "delete from stat_content_pub_trace where contentId=?";

        pe.update( sql, new Object[] { cId } );
    }

    public void deleteAllPubTracePub()
    {
        String sql = "delete from stat_content_pub_trace";

        pe.update( sql );
    }

    public void deleteAllStatClassContentTrace()
    {
        String sql = "delete from stat_class_content_trace";

        pe.update( sql );
    }

    public void deleteAllStatClassUpdateTrace()
    {
        String sql = "delete from stat_class_update_trace";

        pe.update( sql );
    }

    public void deleteAllStatManagerCoententTrace()
    {
        String sql = "delete from stat_manager_content_trace";

        pe.update( sql );
    }

    public void deleteAllStatOrgCoententTrace()
    {
        String sql = "delete from stat_org_content_trace";

        pe.update( sql );
    }

    public List queryTraceAllClass()
    {
        String sql = "select * from contentclass where classType<4";

        return pe.queryResultMap( sql );
    }

    

    public List queryTraceContentIdByClassIdAndModelId( Long classId,
        Long modelId, Long prevCId, Integer count )
    {
        String sql = "select contentId, siteId, classId, appearStartDateTime, addTime, creator, censorState, systemHandleTime from content_main_info where classId=? and modelId=? and creator!= 'admin' and classId>0 and refCid=-1 and linkCid=-1 and refCid<1 and orgCode!='' and contentId<? order by contentId desc limit ?";

        return pe.queryResultMap( sql, new Object[] { classId, modelId,
            prevCId, count } );
    }
    

}

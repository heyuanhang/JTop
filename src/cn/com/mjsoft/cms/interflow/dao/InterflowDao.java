package cn.com.mjsoft.cms.interflow.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.interflow.bean.FriendSiteLinkBean;
import cn.com.mjsoft.cms.interflow.bean.SiteAnnounceBean;
import cn.com.mjsoft.cms.interflow.dao.vo.FriendSiteLink;
import cn.com.mjsoft.cms.interflow.dao.vo.SiteAnnounce;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class InterflowDao
{
    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public InterflowDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public FriendSiteLink querySingleFriendSiteLinkBean( Long flId )
    {
        return ( FriendSiteLink ) pe.query( FriendSiteLink.class, flId );
    }

    public List queryFriendSiteLinkBeanList( Long siteId, Long typeId )
    {
        String sql = "select * from friend_site_link where siteId=? and typeId=? order by orderFlag asc";

        return pe.queryBeanList( sql, new Object[] { siteId, typeId },
            FriendSiteLinkBean.class );
    }

    public List queryFriendSiteLinkBeanList( Long siteId, String typeFlag )
    {
        String sql = "select * from friend_site_link where siteId=? and typeId=(select ltId from friend_site_link_type where typeFlag=?) order by orderFlag asc";

        return pe.queryBeanList( sql, new Object[] { siteId, typeFlag },
            FriendSiteLinkBean.class );
    }

    public void saveFriendSiteLinkBean( FriendSiteLink fl )
    {
        pe.save( fl );
    }

    public void updateFriendSiteLink( FriendSiteLink fb )
    {
        String sql = "update friend_site_link set siteName=?, siteLink=?, siteLogo=?, typeId=? where flId=?";

        pe.update( sql, fb );
    }

    public void updateFriendSiteLinkOrder( Integer order, Long flId )
    {
        String sql = "update friend_site_link set orderFlag=? where flId=?";

        pe.update( sql, new Object[] { order, flId } );
    }

    public void deleteFriendSiteLink( Long flId )
    {
        String sql = "delete from friend_site_link where flId=?";

        pe.update( sql, new Object[] { flId } );
    }

    public void deleteFriendSiteLinkByType( Long typeId )
    {
        String sql = "delete from friend_site_link where typeId=?";

        pe.update( sql, new Object[] { typeId } );
    }

    public Map querySingleFriendSiteLinkType( Long ltId )
    {
        String sql = "select * from friend_site_link_type where ltId=?";

        return pe.querySingleResultMap( sql, new Object[] { ltId } );
    }

    public List queryFriendSiteLinkTypeList( Long siteId )
    {
        String sql = "select * from friend_site_link_type where siteId=? order by ltId asc";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List queryFriendSiteLinkTypeIdList( Long siteId )
    {
        String sql = "select ltId from friend_site_link_type where siteId=?";

        return pe.querySingleCloumn( sql, new Object[] { siteId }, Long.class );
    }

    public void saveFriendSiteLinkType( Map valMap )
    {
        String sql = "insert into friend_site_link_type (typeName, typeFlag, siteId) values (?,?,?)";

        pe.update( sql, valMap );
    }

    public void updateFriendSiteLinkType( Map valMap )
    {
        String sql = "update friend_site_link_type set typeName=? where ltId=?";

        pe.update( sql, valMap );
    }

    public void deleteFriendSiteLinkType( Long ltId )
    {
        String sql = "delete from friend_site_link_type where ltId=?";

        pe.update( sql, new Object[] { ltId } );
    }

    public UpdateState saveSiteAnnounce( SiteAnnounce sa )
    {
        return pe.save( sa );
    }

    public void updateSiteAnnounce( SiteAnnounce sa )
    {
        String sql = "update site_announce set anTitle=?, content=?, showStartDate=?, showEndDate=? where anId=?";

        pe.update( sql, sa );
    }

    public void updateSiteAnnounceOrder( Long anId, Long order )
    {
        String sql = "update site_announce set anOrder=? where anId=?";

        pe.update( sql, new Object[] { order, anId } );
    }

    public void updateSiteAnnounceUseStatus( Long aId, Integer status )
    {
        String sql = "update site_announce set useStatus=? where anId=?";

        pe.update( sql, new Object[] { status, aId } );
    }

    public SiteAnnounce querySingleSiteAnnounceBean( Long aId )
    {
        return ( SiteAnnounce ) pe.query( SiteAnnounce.class, aId );
    }

    public Long querySiteAnnounceBeanCount( Long siteId )
    {
        String sql = "select count(*) from site_announce where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId },
            Long.class );
    }

    public Long querySiteAnnounceBeanCount( Long siteId, Date cd )
    {
        String sql = "select count(*) from site_announce where siteId=? and showStartDate<? and showEndDate>?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, cd,
            cd }, Long.class );
    }

    public List querySiteAnnounceBeanList( Long siteId, Long start, Integer size )
    {
        String sql = "select * from site_announce where siteId=? order by anOrder desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { siteId, start, size },
            SiteAnnounceBean.class );
    }
    
    

    public List querySiteAnnounceBeanList( Long siteId, Date cd, Long start,
        Integer size )
    {
        String sql = "select * from site_announce where siteId=? and showStartDate<=? and showEndDate>=? order by anOrder desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { siteId, cd, cd, start,
            size }, SiteAnnounceBean.class );
    }
    
    
    

    public void deleteSiteAnnounce( Long aId )
    {
        String sql = "delete from site_announce where anId=?";

        pe.update( sql, new Object[] { aId } );
    }

    public void deleteSiteAnnounceBySiteId( Long siteId )
    {
        String sql = "delete from site_announce where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }
}

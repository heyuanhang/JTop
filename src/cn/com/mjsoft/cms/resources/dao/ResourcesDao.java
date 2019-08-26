package cn.com.mjsoft.cms.resources.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResourceTrace;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class ResourcesDao
{
    private static Logger log = Logger.getLogger( ResourcesDao.class );

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public ResourcesDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public List queryImgClassInfoBeanByParentId( Long parent )
    {
        log.info( "[DAO] queryImgClassInfoBeanByParentId(): parentId=" + parent );
        String sql = "select * from imgclass where parent=? order by linearOrderFlag";

        return pe.query( sql, new Object[] { parent }, new ImgClassTreeItemBeanTransform() );
    }

    public UpdateState saveResourceInfo( SiteResource resInfo )
    {
        return pe.save( resInfo );
    }

    public List queryImageInfoBeanByClassId( Long imgClassId )
    {
        log.info( "[DAO] queryImageInfoBeanByClassId(): imgClassId=" + imgClassId );

        String sql = "select * from site_resource where imgClassId=? order by imgResId desc";

        return pe.query( sql, new Object[] { imgClassId }, new SiteResourceBeanTransform() );

    }

    public Integer queryImageAllCount( Long targetClassId )
    {
        log.info( "[DAO] queryImageAllCount(): imgClassId=" + targetClassId );
        String sql = "select count(*) from site_resource where imgClassId=?";

        Integer count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId },
            Integer.class );

        return count;

    }

    public List queryLimitImageByClassIDAndFlagImageId( String orderBy, Long imgClassId,
        Long flagImageId, int limitSize )
    {
        // TODO 暂时1W容量
        String sqlDesc = "select * from site_resource as ires inner join"
            + " (select imgResId as queryId from site_resource where imgResId<? and imgClassId=? order by imgResId desc limit ?) ids on ires.imgResId=ids.queryId order by ires.imgResId desc";

        String sqlAsc = "select * from site_resource as ires inner join"
            + " (select imgResId as queryId from site_resource where imgResId>? and imgClassId=? order by imgResId asc limit ?) ids on ires.imgResId=ids.queryId order by ires.imgResId desc";

        String sql = sqlDesc;

        if( "asc".equals( orderBy ) )
        {
            sql = sqlAsc;
        }

        List result = pe.query( sql, new Object[] { flagImageId, imgClassId,
            Integer.valueOf( limitSize ) }, new SiteResourceBeanTransform() );

        return result;
    }

    public Long queryImgClassIdForEndPageMode( Long targetClassId )
    {
        String sql = "select imgClassId from site_resource where imgClassId=? limit 1";

        Long result = ( Long ) pe.querySingleObject( sql, new Object[] { targetClassId },
            Long.class );

        if( result == null )
        {
            return Long.valueOf( -1 );
        }

        return result;
    }

    public void updateImageMarkStatus( Long resId, Integer status )
    {
        String sql = "update site_resource set haveMark=? where resId=?";

        pe.update( sql, new Object[] { status, resId } );
    }

    public void updateImageMarkStatus( String reUrl, Integer status )
    {
        String sql = "update site_resource set haveMark=? where resSource=?";

        pe.update( sql, new Object[] { status, reUrl } );
    }

    public Integer queryImageMarkStatus( Long resId )
    {
        String sql = "select haveMark from site_resource where resId=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { resId }, Integer.class );
    }

    public Integer queryImageMarkStatus( String reUrl )
    {
        String sql = "select haveMark from site_resource where resSource=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { reUrl }, Integer.class );
    }

    public void deleteImageInfo( Long imgResId )
    {
        String sql = "delete from site_resource where imgResId=?";

        pe.update( sql, new Object[] { imgResId } );

    }

    public SiteResourceBean querySingleImageInfoBeanByImgId( Long imgResId )
    {
        String sql = "select * from site_resource where imgResId=?";

        SiteResourceBean result = ( SiteResourceBean ) pe.querySingleRow( sql,
            new Object[] { imgResId }, new SiteResourceBeanTransform() );

        return result;

    }

    public Integer queryResCountByClassIdAndResType( String targetClassIds, Integer resTypeVal )
    {
        String sql = "select count(idInfo.resId) from (select resId from site_resource where classId in ("
            + targetClassIds + ") and resType=?) idInfo";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { resTypeVal }, Integer.class );
    }

    public List queryResourceBeanByClassIdAndResType( String targetClassIds, Integer resTypeVal,
        Long startPos, Integer limit )
    {
        String sql = "select sr.*  from site_resource sr inner join (select resId from site_resource where classId in ("
            + targetClassIds
            + ") and resType=? order by resId desc limit ?,? ) info on sr.resId=info.resId";
        return pe.query( sql, new Object[] { resTypeVal, startPos, limit },
            new SiteResourceBeanTransform() );
    }
    
    public Long queryResourceBeanCountBySiteId( Long siteId  )
    {
        String sql = "select count(resId) from site_resource where siteId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId}, Long.class);
    }
    
    public List queryResourceBeanBySiteId( Long siteId, Long startPos, Integer limit )
    {
        String sql = "select sr.*  from site_resource sr inner join (select resId from site_resource where siteId=? order by resId desc limit ?,? ) info on sr.resId=info.resId";
        return pe.query( sql, new Object[] { siteId, startPos, limit },
            new SiteResourceBeanTransform() );
    }

    public SiteResourceBean querySingleResourceBeanByResId( Long resId )
    {
        String sql = "select * from site_resource where resId=?";

        return ( SiteResourceBean ) pe.querySingleRow( sql, new Object[] { resId },
            new SiteResourceBeanTransform() );
    }

    public SiteResourceBean querySingleResourceBeanBySource( String source )
    {
        String sql = "select * from site_resource where resSource=?";

        return ( SiteResourceBean ) pe.querySingleRow( sql, new Object[] { source },
            new SiteResourceBeanTransform() );
    }

    public void updateSingleVideoResourceCover( String relateImageUrl, Long resId )
    {
        String sql = "update site_resource set cover=? where resId=?";
        pe.update( sql, new Object[] { relateImageUrl, resId } );
    }

    public void updateImageWHL( Long resId, Integer w, Integer h, Long size )
    {
        String sql = "update site_resource set width=?, height=?, resSize=? where resId=?";
        pe.update( sql, new Object[] { w, h, size, resId } );
    }

    public void deleteResInfoByRePath( String path )
    {
        String sql = "delete from site_resource where resSource=?";
        pe.update( sql, new Object[] { path } );
    }

    public boolean checkExistSiteResourceBySource( String source )
    {
        String sql = "select resId from site_resource where resSource=?";
        Long resId = ( Long ) pe.querySingleObject( sql, new Object[] { source }, Long.class );

        return ( resId == null ) ? false : true;
    }

    public void save( SiteResourceTrace srt )
    {
        pe.save( srt );
    }

    public Map queryResUseStatus( Long resId )
    {
        String sql = "select * from site_resource_upload_trace where resId=?";

        return pe.querySingleResultMap( sql, new Object[] { resId } );
    }

    public void updateResUseStatus( Long resId, Integer isUse )
    {
        String sql = "update site_resource_upload_trace set isUse=? where resId=?";

        if( resId.longValue() > 0 )
        {
            pe.update( sql, new Object[] { isUse, resId } );
        }
    }

    public void updateResName( Long resId, String name )
    {
        String sql = "update site_resource set resName=? where resId=?";

        pe.update( sql, new Object[] { name, resId } );

    }

    public void updateResClass( Long resId, Long classId )
    {
        String sql = "update site_resource set classId=? where resId=?";

        pe.update( sql, new Object[] { classId, resId } );

    }

    public List queryResUploadTraceInfoByEndUploadDateAndUseStatus( Date endDate, Integer isUse )
    {
        String sql = "select res.* from site_resource res inner join (select resId from site_resource_upload_trace where uploadDate<=? and isUse=?) ids on ids.resId=res.resId";
        return pe.query( sql, new Object[] { endDate, isUse }, new SiteResourceBeanTransform() );
    }

    public void deleteResInfoByEndUploadDateAndUseStatus( Date endDate, Integer isUse )
    {
        String sql = "delete from site_resource where resId in (select resId from site_resource_upload_trace where uploadDate<=? and isUse=?)";
        pe.update( sql, new Object[] { endDate, isUse } );
    }

    public void deleteResUploadTraceInfoByEndUploadDateAndUseStatus( Date endDate, Integer isUse )
    {
        String sql = "delete from site_resource_upload_trace where uploadDate<=? and isUse=?";
        pe.update( sql, new Object[] { endDate, isUse } );
    }

    public String queryIconResByName( String name )
    {
        String sql = "select * from icon_res_info where iconName=?";

        return ( String ) pe.querySingleObject( sql, new Object[] { name }, String.class );
    }

    public List queryIconRes( Long start, Integer count )
    {
        String sql = "select * from icon_res_info limit ?,?";

        return pe.queryResultMap( sql, new Object[] { start, count } );
    }

    public void saveMediaResCov( Long siteId, Long classId, Long resId )
    {
        String sql = "insert into site_res_media_cov (siteId, classId, resId) values (?,?,?)";

        pe.update( sql, new Object[] { siteId, classId, resId } );
    }

    public void deleteMediaResCovById( Long resId )
    {
        String sql = "delete from site_res_media_cov where resId=?";

        pe.update( sql, new Object[] { resId } );
    }

    public Map querySingleMediaCovById( Long covId )
    {
        String sql = "select * from site_resource where resId=(select resId from site_res_media_cov where mediaResId=?)";

        return pe.querySingleResultMap( sql, new Object[] { covId } );
    }

    public Long queryMediaCovCountBySiteId( Long siteId )
    {
        String sql = "select count(*) from site_res_media_cov where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId }, Long.class );
    }

    public List queryMediaCovBySiteId( Long siteId, Long start, Integer limit )
    {
        String sql = "select sr.* from site_resource sr, (select resId from site_res_media_cov where siteId=? order by mediaResId desc limit ?,?) tmp where tmp.resId=sr.resId order by sr.resId desc";

        return pe.queryResultMap( sql, new Object[] { siteId, start, limit } );
    }

    public Long queryMediaCovCount( Long classId )
    {
        String sql = "select count(*) from site_res_media_cov where classId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { classId }, Long.class );
    }

    public List queryMediaCov( Long classId, Long start, Integer limit )
    {
        String sql = "select sr.* from site_resource sr, (select resId from site_res_media_cov where classId=? order by mediaResId desc limit ?,?) tmp where tmp.resId=sr.resId order by sr.resId desc";

        return pe.queryResultMap( sql, new Object[] { classId, start, limit } );
    }

    public Long queryMediaCovCountBySiteAndClassId( Long siteId, Long classId )
    {
        String sql = "select count(*) from site_res_media_cov where siteId=? and (classId=-9999 or classId=?)";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, classId }, Long.class );
    }

    public List queryMediaCovBySiteAndClassId( Long siteId, Long classId, Long start, Integer limit )
    {
        String sql = "select sr.* from site_resource sr, (select resId from site_res_media_cov where siteId=? and (classId=-9999 or classId=?) order by classId desc, mediaResId desc limit ?,?) tmp where tmp.resId=sr.resId order by sr.resId desc";

        return pe.queryResultMap( sql, new Object[] { siteId, classId, start, limit } );
    }

    public void updateMediaCovClass( Long resId, Long classId )
    {
        String sql = "update site_res_media_cov set classId=? where resId=?";

        pe.update( sql, new Object[] { classId, resId } );

    }

}

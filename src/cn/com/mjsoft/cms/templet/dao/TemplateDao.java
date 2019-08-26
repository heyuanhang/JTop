package cn.com.mjsoft.cms.templet.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.templet.bean.TempletFileBean;
import cn.com.mjsoft.cms.templet.dao.vo.SiteTemplet;
import cn.com.mjsoft.framework.cache.Cache;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;

public class TemplateDao
{
    private static Logger log = Logger.getLogger( TemplateDao.class );

    public PersistenceEngine pe;

    private static Cache cache = new ClusterCacheAdapter( 1000, "templateDao.cache" );

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public TemplateDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public List queryTempletBeanByTypeAndSite( Integer type, String site )
    {
        String sql = "select * from sitetemplet where type=? and siteName=?";

        List beanList = null;
        beanList = this.pe.query( sql, new Object[] { type, site }, new TempletFileBeanTransform() );
        return beanList;
    }

    public TempletFileBean queryTempletBeanById( Long id )
    {
        String sql = "select * from sitetemplet where templetId=?";

        String key = "queryTempletBeanById" + id;

        TempletFileBean result = ( TempletFileBean ) cache.getEntry( key );
        if( result == null )
        {
            result = ( TempletFileBean ) this.pe.querySingleRow( sql, new Object[] { id },
                new TempletFileBeanTransform() );

            cache.putEntry( key, result );
        }
        return result;
    }

    public void saveSiteTemplet( SiteTemplet siteTemplet )
    {
        log.info( "saveSiteTemplet() siteTemplet:" + siteTemplet );
        this.pe.save( siteTemplet );
    }

    public void addTemplateEditionInfo( String tempalteName, String filePath, String editDesc,
        Long eritorId, Date editDT, Long siteId, String tpStyle )
    {
        String sql = "insert into template_edition (templateName,  filePath, editDT, editDesc, editor, siteId, tpStyle) values (?,?,?,?,?,?,?)";

        this.pe.update( sql, new Object[] { tempalteName, filePath, editDT, editDesc, eritorId,
            siteId, tpStyle } );
    }

    public Map querySingleTemplateEditionInfo( Long tempId )
    {
        String sql = "select * from template_edition where editionId=?";

        return this.pe.querySingleResultMap( sql, new Object[] { tempId } );
    }

    public Long querySingleTemplateEditionCountByTName( String tempalteName, Long siteId,
        String tpStyle )
    {
        String sql = "select count(*) from template_edition where templateName=? and siteId=? and tpStyle=?";

        return ( Long ) this.pe.querySingleObject( sql, new Object[] { tempalteName, siteId,
            tpStyle }, Long.class );
    }

    public List queryTemplateEditionInfoList( String templateName, Long siteId, String tpStyle )
    {
        String sql = "select * from template_edition where templateName=? and siteId=? and tpStyle=? order by editionId desc";

        return this.pe.queryResultMap( sql, new Object[] { templateName, siteId, tpStyle } );
    }

    public void deleteTemplateEditionInfoByTName( String templateName, Long siteId )
    {
        String sql = "delete from template_edition where templateName=? and siteId=?";

        this.pe.update( sql, new Object[] { templateName, siteId } );
    }

    public void deleteTemplateEditionInfoBySiteId( Long siteId )
    {
        String sql = "delete from template_edition where siteId=?";

        this.pe.update( sql, new Object[] { siteId } );
    }

    public void updateTemplateName( Long siteId, String oldName, String newName, String tpStyle )
    {
        String sql = "update template_edition set templateName=? where templateName=? and siteId=? and tpStyle=?";

        this.pe.update( sql, new Object[] { newName, oldName, siteId, tpStyle } );
    }

    public Map querySingleTemplateTipHelper( Long siteId, String fileName, String tpStyle )
    {
        String sql = "select * from template_helper where templetFileName=? and siteId=? and tpStyle=?";

        return this.pe.querySingleResultMap( sql, new Object[] { fileName, siteId, tpStyle } );
    }

    public void saveTemplateTipHelper( Long siteId, String fileName, String tip, String tpStyle )
    {
        String sql = "insert into template_helper (templetFileName, templetDisplayName, siteId, tpStyle) values (?,?,?,?)";

        this.pe.update( sql, new Object[] { fileName, tip, siteId, tpStyle } );
    }

    public void updateTemplateTipHelper( Long siteId, String fileName, String tip, String tpStyle )
    {
        String sql = "update template_helper set templetDisplayName=? where templetFileName=? and siteId=? and tpStyle=?";

        this.pe.update( sql, new Object[] { tip, fileName, siteId, tpStyle } );
    }

    public void updateTemplateNameForTip( Long siteId, String oldName, String newName,
        String tpStyle )
    {
        String sql = "update template_helper set templetFileName=? where templetFileName=? and siteId=? and tpStyle=?";

        this.pe.update( sql, new Object[] { newName, oldName, siteId, tpStyle } );
    }

    public void deleteTemplateHelperBySiteId( Long siteId )
    {
        String sql = "delete from template_helper where siteId=?";

        this.pe.update( sql, new Object[] { siteId } );
    }

    public Map querySingleTemplateStyle( Long tsId )
    {
        String sql = "select * from template_style where tsId=?";

        return this.pe.querySingleResultMap( sql, new Object[] { tsId } );
    }

    public Map querySingleTemplateStyleByUse( Long siteId, Integer flag )
    {
        String sql = "select * from template_style where siteId=? and isUse=?";

        return this.pe.querySingleResultMap( sql, new Object[] { siteId, flag } );
    }

    public List queryTemplateStyle( Long siteId )
    {
        String sql = "select * from template_style where siteId=? order by tsId desc";

        return this.pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public void saveTemplateStyle( Map params )
    {
        String sql = "insert into template_style (styleName, stylePic, styleFlag, siteId) values (?,?,?,?)";

        this.pe.update( sql, params );
    }

    public void updateTemplateStyle( Map params )
    {
        String sql = "update template_style set styleName=?, stylePic=? where tsId=?";

        this.pe.update( sql, params );
    }

    public void updateTemplateStyleAllUseFlag( Long siteId, Integer flag, Long exId )
    {
        String sql = "update template_style set isUse=? where siteId=? and tsId!=?";

        this.pe.update( sql, new Object[] { flag, siteId, exId } );
    }

    public void updateTemplateStyleUseFlag( Long siteId, Long tsId, Integer flag )
    {
        String sql = "update template_style set isUse=? where siteId=? and tsId=?";

        this.pe.update( sql, new Object[] { flag, siteId, tsId } );
    }

    public void deleteTemplateStyle( Long id )
    {
        String sql = "delete from template_style where tsId=?";

        this.pe.update( sql, new Object[] { id } );
    }

    public static void releaseAllCache()
    {
        cache.clearAllEntry();
    }

   
}

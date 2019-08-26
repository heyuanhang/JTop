package cn.com.mjsoft.cms.site.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.site.bean.SiteCloudCfgBean;
import cn.com.mjsoft.cms.site.bean.SiteDispenseServerBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.bean.SitePublishGatewayBean;
import cn.com.mjsoft.cms.site.dao.vo.SiteDispenseServer;
import cn.com.mjsoft.cms.site.dao.vo.SiteFileTransfeState;
import cn.com.mjsoft.cms.site.dao.vo.SiteGroup;
import cn.com.mjsoft.cms.site.dao.vo.SitePublishGateway;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.StringUtil;

public class SiteGroupDao
{
    private static Cache psCache = new ClusterCacheAdapter( 1000, "contentService.listContentCache" );

    public PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public SiteGroupDao()
    {

    }

    public SiteGroupDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveSiteGroup( SiteGroup siteGroup )
    {
        return pe.save( siteGroup );
    }

    public void updateSiteGroupBaseInfo( SiteGroup siteGroup )
    {
        String sql = "update site_group set siteName=?, siteUrl=?, mobSiteUrl=?, padSiteUrl=?, home301Url=?, templateCharset=?, siteDesc=? where siteId=?";

        pe.update( sql, siteGroup );
    }

    public List queryAllSiteBean()
    {

        String sql = "select * from site_group order by orderFlag asc";

        List resultList = pe.query( sql, new SiteGroupBeanTransform() );

        return resultList;

    }

    public void updateSiteNodeOrder( Long siteId, Long order )
    {
        String sql = "update site_group set orderFlag=? where siteId=?";

        pe.update( sql, new Object[] { order, siteId } );
    }

    public SiteGroupBean querySiteBeanById( Long siteId )
    {
        String sql = "select * from site_group where siteId=?";

        return ( SiteGroupBean ) pe.querySingleRow( sql, new Object[] { siteId },
            new SiteGroupBeanTransform() );
    }

    public void updateSiteStaticUrl( String staticUrl, Long siteId )
    {
        String sql = "update site_group set homePageStaticUrl=? where siteId=?";

        pe.update( sql, new Object[] { staticUrl, siteId } );

    }

    public void updateMobSiteStaticUrl( String staticUrl, Long siteId )
    {
        String sql = "update site_group set mobHomePageStaticUrl=? where siteId=?";

        pe.update( sql, new Object[] { staticUrl, siteId } );

    }

    public void updatePadSiteStaticUrl( String staticUrl, Long siteId )
    {
        String sql = "update site_group set padHomePageStaticUrl=? where siteId=?";

        pe.update( sql, new Object[] { staticUrl, siteId } );

    }

    public void updateSiteInfoBySiteId( SiteGroup siteGroup )
    {
        String sql = "update site_group set siteFlag=?, siteName=?, siteUrl=?, mobSiteUrl=?, padSiteUrl=?, mobVm=?, padVm=?, homePageTemplate=?, mobHomePageTemplate=?, padHomePageTemplate=?, siteDesc=?, homePageProduceType=?, logoImage=?, copyright=?, icp=?, staticFileType=?, seoTitle=?, seoKeyword=?, seoDesc=?, shareMode=?, downOutImage=?, deleteOutLink=?, summaryLength=?, genKw=?, sameTitle=?, defClickCount=?, managerIP=?, siteCollType=?, outSiteCollUrl=?, sendMailHost=?, mail=?, mailUserName=?, mailUserPW=?, mailSSL=?, smsApiUrl=?, smsAccount=?, smsPW=?, smsSendOnceSec=?, smsMaxCount=?, smsIpDayCount=?, managerLoginTime=?, searchFun=?, useFW=?, imageAllowType=?, mediaAllowType=?, fileAllowType=?, imageMaxC=?, mediaMaxC=?, fileMaxC=?, useImageMark=?, imageMarkType=?, imageMarkPos=?, imageMarkChar=?, imageMark=?, imageMarkDis=?, offSetX=?, offSetY=?, defEditorImageW=?, defEditorImageH=?, defEditorImageDM=?, defHomeImageW=?, defHomeImageH=?, defHomeImageDM=?, defClassImageW=?, defClassImageH=?, defClassImageDM=?, defListImageW=?, defListImageH=?, defListImageDM=?, defContentImageW=?, defContentImageH=?, defContentImageDM=?, extDataModelId=?, extMemberModelId=?, allowMemberReg=?, qqAppId=?, qqAppKey=?, qqBackUri=?, wbAppId=?, wbAppKey=?, wbBackUri=?, mailRegBackUri=?, resetPwBackUri=?, regMailText=?, resetPwText=?, memberDefRoleId=?, memberDefLv=?, memberDefSc=?, memberLoginOnce=?, relateMemberUri=?, thirdLoginErrorUri=?, thirdLoginSuccessUri=?, wxAppId=?, wxAppKey=?, wxPrevUid=?, memberExpire=?, mobJump=?, mobSiteId=?, memberLoginUri=?, useState=? where siteId=?";

        pe.update( sql, siteGroup );
    }

    public void deleteSiteGroupNode( Long siteId )
    {
        String sql = "delete from site_group where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public List queryAllDeleteSiteGroupTrace()
    {
        String sql = "select * from site_delete_trace";

        return pe.queryResultMap( sql );
    }

    public void saveDeleteSiteGroupTrace( Long siteId, String siteFlag )
    {
        String sql = "insert into site_delete_trace (siteId, siteFlag) values (?, ?)";

        pe.update( sql, new Object[] { siteId, siteFlag } );
    }

    public void deleteDeleteSiteGroupTrace( Long siteId )
    {
        String sql = "delete from site_delete_trace where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void saveSiteDispenseServer( SiteDispenseServer serverVO )
    {
        pe.save( serverVO );
    }

    public void updateSiteDispenseServer( SiteDispenseServer serverInfo )
    {
        String sql = "update site_dispense_server set protocol=?, serverName=?, serverIP=?, serverPort=?, serverUrl=?, fileRoot=?, status=?, loginName=?, loginPassword=?, filterFlag=? where serverId=?";

        pe.update( sql, serverInfo );
    }

    public void deleteSiteDispenseServer( Long sId )
    {
        String sql = "delete from site_dispense_server where serverId=?";

        pe.update( sql, new Object[] { sId } );
    }

    public void deleteSiteDispenseServerBySiteId( Long siteId )
    {
        String sql = "delete from site_dispense_server where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public UpdateState saveSitePublishGateway( SitePublishGateway gateway )
    {
        return pe.save( gateway );
    }

    public void editSitePublishGateway( SitePublishGateway gateway )
    {
        String sql = "update site_publish_gateway set name=?, transfeType=?, sourcePath=?, targetServerId=?, targetCloudId=?, targetServerRoot=?, useState=? where gatewayId=?";

        pe.update( sql, gateway );
    }

    public void deleteSitePublishGateway( Long id )
    {
        String sql = "delete from site_publish_gateway where gatewayId=?";

        pe.update( sql, new Object[] { id } );
    }

    public void deleteSitePublishGatewayBySiteId( Long siteId )
    {
        String sql = "delete from site_publish_gateway where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void updateSitePublishGatewayUseState( Long currentId, Long siteId, Integer transfeType,
        Integer state )
    {
        String sql = "update site_publish_gateway set useState=? where siteId=? and transfeType=? and gatewayId!=?";

        pe.update( sql, new Object[] { state, siteId, transfeType, currentId } );
    }

    public List queryAllDispenseServerBean()
    {
        String sql = "select * from site_dispense_server";

        List resultList = pe.query( sql, new SiteDispenseServerBeanTransform() );

        return resultList;
    }

    public void updateServerConnectStatus( Long serverId, Integer status )
    {
        String sql = "update site_dispense_server set connectStatus=? where serverId=?";
        pe.update( sql, new Object[] { status, serverId } );
    }

    public List queryDispenseServerBeanBySiteId( Long siteId )
    {
        String sql = "select * from site_dispense_server where siteId=?";

        List resultList = pe.query( sql, new Object[] { siteId },
            new SiteDispenseServerBeanTransform() );

        return resultList;
    }
    
    public List<SiteDispenseServer> queryDispenseServerBySiteId( Long siteId )
    {
        String sql = "select * from site_dispense_server where siteId=?";

        List<SiteDispenseServer> resultList = pe.queryBeanList ( sql, new Object[] { siteId },
            SiteDispenseServer.class );

        return resultList;
    }

    public SiteDispenseServerBean querySingleDispenseServerBeanById( Long id, Long siteId )
    {
        String sql = "select * from site_dispense_server where serverId=? and siteId=?";
        return ( SiteDispenseServerBean ) pe.querySingleRow( sql, new Object[] { id, siteId },
            new SiteDispenseServerBeanTransform() );
    }

    public List querysSitePublishGatewayBeanBySiteId( Long siteId )
    {
        String sql = "select * from site_publish_gateway where siteId=?";

        List resultList = pe.query( sql, new Object[] { siteId },
            new SitePublishGatewayBeanTransform() );

        return resultList;
    }
    
    public List<SitePublishGateway> querysSitePublishGatewayBySiteId( Long siteId )
    {
        String sql = "select * from site_publish_gateway where siteId=?";

        List resultList = pe.queryBeanList( sql, new Object[] { siteId },
            SitePublishGateway.class );

        return resultList;
    }

    public SitePublishGatewayBean querysSinglePublishGatewayBeanById( Long gwId, Long siteId )
    {
        String sql = "select * from site_publish_gateway where gatewayId=? and siteId=?";

        return ( SitePublishGatewayBean ) pe.querySingleRow( sql, new Object[] { gwId, siteId },
            new SitePublishGatewayBeanTransform() );
    }
    
    public void saveCloudCfgBean(SiteCloudCfgBean cc)
    {
        String sql = "insert into site_cloud_cfg (appId, accessKey, accessSecret, endPoint, accessUrl, bucketName, location, cloudType, siteId) values (?,?,?,?,?,?,?,?,?)";
        
        pe.update( sql, cc );
    }
    
    public void updateCloudCfgBean(SiteCloudCfgBean cc)
    {
        String sql = "update site_cloud_cfg set appId=?, accessKey=?, accessSecret=?, endPoint=?, accessUrl=?, bucketName=?, location=? where cloId=? and siteId=?";
       
        pe.update( sql, cc );
    }
    
    public void deleteCloudCfgBeanById(Long cloId, Long siteId)
    {
        String sql = "delete from site_cloud_cfg  where cloId=? and siteId=?";
       
        pe.update( sql, new Object[]{ cloId, siteId });
    }
    
    public void deleteCloudCfgBeanBySiteId( Long siteId)
    {
        String sql = "delete from site_cloud_cfg  where siteId=?";
       
        pe.update( sql, new Object[]{  siteId });
    }

    public SiteCloudCfgBean querysSingleCloudCfgBean( Long coId )
    {
        String key = "querysSingleCloudCfgBean:" + coId;

        SiteCloudCfgBean res = ( SiteCloudCfgBean ) psCache.getEntry( key );

        if( res == null )
        {

            String sql = "select * from site_cloud_cfg where cloId=? order by cloId desc";

            res = ( SiteCloudCfgBean ) pe.querySingleBean( sql, new Object[] { coId },
                SiteCloudCfgBean.class );

            psCache.putEntry( key, res );
        }

        return res;
    }
    
    public List<SiteCloudCfgBean> querysCloudCfgBeanBySiteId(Long siteId)
    {

        String sql = "select * from site_cloud_cfg where siteId=? order by cloId desc";

        return pe.queryBeanList( sql, new Object[]{siteId}, SiteCloudCfgBean.class );
    }

    public List querysAllCloudCfgBean()
    {

        String sql = "select * from site_cloud_cfg";

        return pe.queryBeanList( sql, SiteCloudCfgBean.class );
    }

    public SitePublishGatewayBean querysSingleEffiPublishGatewayBeanBySite( Long siteId,
        Integer tranType )
    {
        String key = "querysSingleEffiPublishGatewayBeanBySite:" + siteId + "|" + tranType;

        SitePublishGatewayBean res = ( SitePublishGatewayBean ) psCache.getEntry( key );

        if( res == null )
        {
            String sql = "select * from site_publish_gateway where siteId=? and transfeType=? and useState=1";

            res = ( SitePublishGatewayBean ) pe.querySingleRow( sql, new Object[] { siteId,
                tranType }, new SitePublishGatewayBeanTransform() );

            psCache.putEntry( key, res );
        }

        return res;
    }

    

    public void saveNewFileNotifyInfo( SiteFileTransfeState state )
    {
        pe.save( state );
    }

    // public Integer queryFileNotifyInfo( String fullPath, Date eventTime,
    // Integer fileEventFlag )
    // {
    // String sql = "select count(*) from site_file_transfe_state where
    // fullPath=? and eventTime=? and fileEventFlag=?";
    // return ( Integer ) pe.querySingleObject( sql, new Object[] { fullPath,
    // eventTime, fileEventFlag }, Integer.class );
    // }

    public void updateGatewayRelateCollectJob( Long jobId, Long gatewayId )
    {
        String sql = "update site_publish_gateway set collectJobId=? where gatewayId=?";

        pe.update( sql, new Object[] { jobId, gatewayId } );
    }

    public void updateGatewayRelateTransferJob( Long jobId, Long gatewayId )
    {
        String sql = "update site_publish_gateway set transferJobId=? where gatewayId=?";

        pe.update( sql, new Object[] { jobId, gatewayId } );
    }

    public List queryAllSitePublishGatewayBeanByState( Integer useState )
    {
        String sql = "select * from site_publish_gateway where useState=?";
        return pe.query( sql, new Object[] { useState }, new SitePublishGatewayBeanTransform() );
    }

    public SitePublishGatewayBean querySingleSitePublishGatewayBeanByGatewayId( Long gatewayId )
    {
        String key = "querySingleSitePublishGatewayBeanByGatewayId:" + gatewayId;

        SitePublishGatewayBean res = ( SitePublishGatewayBean ) psCache.getEntry( key );

        if( res == null )
        {
            String sql = "select * from site_publish_gateway where gatewayId=?";
            
            res = ( SitePublishGatewayBean ) pe.querySingleRow( sql, new Object[] { gatewayId },
                new SitePublishGatewayBeanTransform() );  
            
            psCache.putEntry( key, res );
        }
        
        return res;
    }

    public SiteDispenseServerBean querySingleSiteDispenseServerBeanByserverId( Long serverId )
    {
        String key = "querySingleSiteDispenseServerBeanByserverId:" + serverId;

        SiteDispenseServerBean res = ( SiteDispenseServerBean ) psCache.getEntry( key );

        if( res == null )
        {
            String sql = "select * from site_dispense_server where serverId=?";
            
            res = ( SiteDispenseServerBean ) pe.querySingleRow( sql, new Object[] { serverId },
                new SiteDispenseServerBeanTransform() );
            psCache.putEntry( key, res );
        }
        
        return res;
    }

    public List querySiteFileTransfeStateBeanByGatewayId( Long gatewayId )
    {
        String sql = "select * from site_file_transfe_state where gatewayId=? order by transferFileId asc";

        return pe.query( sql, new Object[] { gatewayId }, new SiteFileTransfeStateBeanTransform() );
    }

    public void deleteSiteFileTransfeStateBeanByTransferStatus( Integer status )
    {
        String sql = "delete from site_file_transfe_state where transferStatus=?";
        pe.update( sql, new Object[] { status } );
    }

    public void deleteSiteFileTransfeStateBeanByTransferId( Long transferFileId )
    {
        String sql = "delete from site_file_transfe_state where transferFileId=?";
        pe.update( sql, new Object[] { transferFileId } );
    }

    public void deleteSiteFileSuccessTransfeStateBeanByLastId( Long lastId, Long gatewayId )
    {
        String sql = "delete from site_file_transfe_state where transferFileId<=? and gatewayId=? and transferStatus!=-1";
        pe.update( sql, new Object[] { lastId, gatewayId } );
    }

    public void deleteSiteFileSuccessTransfeStateBeanByIdsFlag( String idsFlag )
    {
        String sql = "delete from site_file_transfe_state where transferFileId in (" + idsFlag
            + ")";
        pe.update( sql );
    }

    public void updateFileTransferStatus( Long transferFileId, Integer status )
    {
        String sql = "update site_file_transfe_state set transferStatus=? where transferFileId=?";
        pe.update( sql, new Object[] { status, transferFileId } );
    }

    public void updateContentClassSiteFlag( String newFlag, String oldFlag )
    {
        String sql = "update contentclass set siteFlag=? where siteFlag=?";
        pe.update( sql, new Object[] { newFlag, oldFlag } );
    }

    public void updateBlockTypeSiteFlag( String newFlag, String oldFlag )
    {
        String sql = "update block_type set siteFlag=? where siteFlag=?";
        pe.update( sql, new Object[] { newFlag, oldFlag } );
    }

    public void updateSurveyBaseInfoSiteFlag( String newFlag, String oldFlag )
    {
        String sql = "update survey_base_info set siteFlag=? where siteFlag=?";
        pe.update( sql, new Object[] { newFlag, oldFlag } );
    }

    public void updateSurveyOptionInfoSiteFlag( String newFlag, String oldFlag )
    {
        String sql = "update survey_option_info set siteFlag=? where siteFlag=?";
        pe.update( sql, new Object[] { newFlag, oldFlag } );
    }

    public void saveModelTemplate( Long siteId, Long dataModelId, String listTemplate,
        String contentTemplate )
    {
        String sql = "insert into site_def_template_config (siteId, modelId, listTemplateUrl, contentTemplateUrl) values (?,?,?,?)";
        pe.update( sql, new Object[] { siteId, dataModelId, listTemplate, contentTemplate } );
    }

    public Map querySingleModelTemplate( Long siteId, Long dataModelId )
    {
        String sql = "select * from site_def_template_config where siteId=? and modelId=?";
        return pe.querySingleResultMap( sql, new Object[] { siteId, dataModelId } );
    }

    public void deleteDefaultModelTemplateBySiteId( Long siteId )
    {
        String sql = "delete from site_def_template_config where siteId=?";
        pe.update( sql, new Object[] { siteId } );
    }

    public UpdateState saveSiteEmailSendInfo( Long siteId, String sendTo, String subject,
        String mailContent, Timestamp createDT )
    {
        String sql = "insert into site_mail_queue (mailId, siteId, createDT, sendTo, subject, mailContent) values (?,?,?,?,?,?)";
        return pe.insert( sql, new Object[] { StringUtil.getUUIDString(), siteId, createDT, sendTo,
            subject, mailContent } );
    }

    public Map querySingleSiteEmailSendInfo()
    {
        String sql = "select * from site_mail_queue order by createDT asc limit 1";
        return pe.querySingleResultMap( sql );
    }

    public void deleteSiteEmailSendInfo( String mailId )
    {
        String sql = "delete from site_mail_queue where mailId=?";

        pe.update( sql, new Object[] { mailId } );
    }

    public static void clearPSCache()
    {
        psCache.clearAllEntry();
    }
}

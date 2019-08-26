package cn.com.mjsoft.cms.common.dao;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import java.util.Map;

public class CommonSystemDao
{
    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public CommonSystemDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public Map queryCmsSystemRuntimeConfig()
    {
        String sql = "select * from system_cfg";

        return pe.querySingleResultMap( sql );
    }

    public Map queryCmsServerConfig()
    {
        String sql = "select * from site_cms_server";

        return pe.querySingleResultMap( sql );
    }

    public void updateCmsRuntimeCfg( Map valMap )
    {
        String sql = "update system_cfg set managerIp=?, loginTime=?, openOfficePath=?, rootOrgName=?, baiduMapDefCity=?, dangerAccessCount=?, otherVCUrl=?, backupDay=?, backupFtpId=?, backupHourExe=?, aldOpt=?, alhOpt=?, alStartHour=?, alEndHour=?";

        pe.update( sql, valMap );
    }

    public void updateCmsServerCfg( Map valMap )
    {
        String sql = "update site_cms_server set domain=?, context=?, port=? where serverId=?";

        pe.update( sql, valMap );
    }

    public String getMysqlRootBasePath()
    {
        String sql = "select @@basedir as basePath from dual";

        return ( String ) pe.querySingleObject( sql, String.class );
    }
}

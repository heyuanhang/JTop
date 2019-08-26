package cn.com.mjsoft.cms.stat.bean;

import java.sql.Timestamp;

import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class FlowTraceBean
{
    private String flowActUserName;
    private String flowCom;
    private String flowName;
    private String paramInfo;
    private Timestamp exeDt;
    private String ip;

    private Long siteId;

    public FlowTraceBean( String flowActUserName, String flowCom, String flowName,
        String paramInfo, String ip )
    {
        this.flowActUserName = flowActUserName;
        this.flowCom = flowCom;
        this.flowName = flowName;
        this.paramInfo = paramInfo;
        this.ip = ip;

        SecuritySession sec = SecuritySessionKeeper.getSecuritySession();

        Long siteId = Long.valueOf( -1 );

        if( sec != null )
        {
            siteId = ( ( SiteGroupBean ) sec.getCurrentLoginSiteInfo() ).getSiteId();
        }

        this.siteId = siteId;

        this.exeDt = new Timestamp( DateAndTimeUtil.clusterTimeMillis() );
    }

    public String getFlowActUserName()
    {
        return flowActUserName;
    }

    public void setFlowActUserName( String flowActUserName )
    {
        this.flowActUserName = flowActUserName;
    }

    public String getFlowCom()
    {
        return flowCom;
    }

    public void setFlowCom( String flowCom )
    {
        this.flowCom = flowCom;
    }

    public String getFlowName()
    {
        return flowName;
    }

    public void setFlowName( String flowName )
    {
        this.flowName = flowName;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp( String ip )
    {
        this.ip = ip;
    }

    public String getParamInfo()
    {
        return paramInfo;
    }

    public void setParamInfo( String paramInfo )
    {
        this.paramInfo = paramInfo;
    }

    public Timestamp getExeDt()
    {
        return exeDt;
    }

    public void setExeDt( Timestamp exeDt )
    {
        this.exeDt = exeDt;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

}

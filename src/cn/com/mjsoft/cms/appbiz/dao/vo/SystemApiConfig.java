package cn.com.mjsoft.cms.appbiz.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "system_api_config", id = "apiId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SystemApiConfig
{

    private Long apiId;
    private String apiName;
    private String flowPath;

    private String reqMethod = "get";
    private Integer mustTok = Integer.valueOf( 0 );
    private Integer mustEnc = Integer.valueOf( 0 );
    private Integer mustSecTok = Integer.valueOf( 0 );
    private String extBehaviorClass;
    private String apiParams;

    public Long getApiId()
    {
        return this.apiId;
    }

    public void setApiId( Long apiId )
    {
        this.apiId = apiId;
    }

    public String getApiName()
    {
        return this.apiName;
    }

    public void setApiName( String apiName )
    {
        this.apiName = apiName;
    }

    public String getFlowPath()
    {
        return this.flowPath;
    }

    public void setFlowPath( String flowPath )
    {
        this.flowPath = flowPath;
    }

    public Integer getMustTok()
    {
        return this.mustTok;
    }

    public void setMustTok( Integer mustTok )
    {
        this.mustTok = mustTok;
    }

    public Integer getMustEnc()
    {
        return this.mustEnc;
    }

    public void setMustEnc( Integer mustEnc )
    {
        this.mustEnc = mustEnc;
    }

    public Integer getMustSecTok()
    {
        return this.mustSecTok;
    }

    public void setMustSecTok( Integer mustSecTok )
    {
        this.mustSecTok = mustSecTok;
    }

    public String getExtBehaviorClass()
    {
        return this.extBehaviorClass;
    }

    public void setExtBehaviorClass( String extBehaviorClass )
    {
        this.extBehaviorClass = extBehaviorClass;
    }

    public String getApiParams()
    {
        return this.apiParams;
    }

    public void setApiParams( String apiParams )
    {
        this.apiParams = apiParams;
    }

    public String getReqMethod()
    {
        return this.reqMethod;
    }

    public void setReqMethod( String reqMethod )
    {
        this.reqMethod = reqMethod;
    }
}

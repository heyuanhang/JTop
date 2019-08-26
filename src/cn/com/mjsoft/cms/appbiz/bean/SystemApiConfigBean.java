package cn.com.mjsoft.cms.appbiz.bean;

public class SystemApiConfigBean
{
    private Long apiId = Long.valueOf( -1L );
    private String apiName;
    private String flowPath;
    private String reqMethod;
    private Integer mustTok;
    private Integer mustEnc;
    private Integer mustSecTok;
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

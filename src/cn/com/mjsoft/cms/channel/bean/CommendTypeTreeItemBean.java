package cn.com.mjsoft.cms.channel.bean;

public class CommendTypeTreeItemBean
{
    private Long commendTypeId;

    private String commendName;

    private Long classId;// 全站通用

    private Long firstClassId;// 全站第一个ID,默认

    private Integer childClassMode;

    private String commFlag;

    private Integer mustCensor;

    private String siteFlag;

    private String rootClassName = "";

    private Long rootClassId = Long.valueOf( -1 );

    public CommendTypeTreeItemBean( String rootClassName, Long rootClassId )
    {
        super();

        this.rootClassName = rootClassName;
        this.rootClassId = rootClassId;
    }

    public CommendTypeTreeItemBean( Long commendTypeId, String commendName,
        Long classId, Integer childClassMode, String commFlag,
        Integer mustCensor, String siteFlag, Long rootClassId )
    {
        super();
        this.commendTypeId = commendTypeId;
        this.commendName = commendName;
        this.classId = classId;
        this.childClassMode = childClassMode;
        this.commFlag = commFlag;
        this.mustCensor = mustCensor;
        this.siteFlag = siteFlag;
        this.rootClassId = rootClassId;
    }

    public Integer getChildClassMode()
    {
        return childClassMode;
    }

    public void setChildClassMode( Integer childClassMode )
    {
        this.childClassMode = childClassMode;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public String getCommendName()
    {
        return commendName;
    }

    public void setCommendName( String commendName )
    {
        this.commendName = commendName;
    }

    public Long getCommendTypeId()
    {
        return commendTypeId;
    }

    public void setCommendTypeId( Long commendTypeId )
    {
        this.commendTypeId = commendTypeId;
    }

    public String getCommFlag()
    {
        return commFlag;
    }

    public void setCommFlag( String commFlag )
    {
        this.commFlag = commFlag;
    }

    public Integer getMustCensor()
    {
        return mustCensor;
    }

    public void setMustCensor( Integer mustCensor )
    {
        this.mustCensor = mustCensor;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Long getFirstClassId()
    {
        return firstClassId;
    }

    public void setFirstClassId( Long firstClassId )
    {
        this.firstClassId = firstClassId;
    }

    public Long getRootClassId()
    {
        return rootClassId;
    }

    public void setRootClassId( Long rootClassId )
    {
        this.rootClassId = rootClassId;
    }

    public String getRootClassName()
    {
        return rootClassName;
    }

    public void setRootClassName( String rootClassName )
    {
        this.rootClassName = rootClassName;
    }

}

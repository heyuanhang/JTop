package cn.com.mjsoft.cms.block.bean;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class BlockTreeInfoBean
{
    private Boolean isType;

    private Long blockId = Long.valueOf( -1 );
    private Long parentId = Long.valueOf( -1 );
    private Long siteId = Long.valueOf( -1 );
    private String blockName;
    private String flag;
    private Integer type;
    private String templateUrl;
    private String blockDesc;
    private String staticUrl;
    private String creator;
    private Long jobId;
    private Integer periodType;
    private Integer period;
    private String lastPubTime;

    private Long blockTypeId;
    private String blockTypeName;
    private String mainTemplateUrl;

    private String siteFlag;

    public String getBlockDesc()
    {
        return blockDesc;
    }

    public void setBlockDesc( String blockDesc )
    {
        this.blockDesc = blockDesc;
    }

    public Long getBlockId()
    {
        return blockId;
    }

    public void setBlockId( Long blockId )
    {
        this.blockId = blockId;
    }

    public String getBlockName()
    {
        return blockName;
    }

    public void setBlockName( String blockName )
    {
        this.blockName = blockName;
    }

    public Long getBlockTypeId()
    {
        return blockTypeId;
    }

    public void setBlockTypeId( Long blockTypeId )
    {
        this.blockTypeId = blockTypeId;
    }

    public String getBlockTypeName()
    {
        return blockTypeName;
    }

    public void setBlockTypeName( String blockTypeName )
    {
        this.blockTypeName = blockTypeName;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag( String flag )
    {
        this.flag = flag;
    }

    public Boolean getIsType()
    {
        return isType;
    }

    public void setIsType( Boolean isType )
    {
        this.isType = isType;
    }

    public Long getJobId()
    {
        return jobId;
    }

    public void setJobId( Long jobId )
    {
        this.jobId = jobId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId( Long parentId )
    {
        this.parentId = parentId;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getStaticUrl()
    {
        return staticUrl;
    }

    public void setStaticUrl( String staticUrl )
    {
        this.staticUrl = staticUrl;
    }

    public String getTemplateUrl()
    {
        return templateUrl;
    }

    public void setTemplateUrl( String templateUrl )
    {
        this.templateUrl = templateUrl;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getLastPubTime()
    {
        return lastPubTime;
    }

    public void setLastPubTime( String lastPubTime )
    {
        this.lastPubTime = lastPubTime;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod( Integer period )
    {
        this.period = period;
    }

    public Integer getPeriodType()
    {
        return periodType;
    }

    public void setPeriodType( Integer periodType )
    {
        this.periodType = periodType;
    }

    // 业务方法

    public String getMainTemplateUrl()
    {
        return mainTemplateUrl;
    }

    public void setMainTemplateUrl( String mainTemplateUrl )
    {
        this.mainTemplateUrl = mainTemplateUrl;
    }

    public String getPubPeriod()
    {
        if( period.intValue() == 0 )
        {
            return "手动发布";
        }

        if( Constant.JOB.PERROID_SEC.equals( periodType ) )
        {
            return "每" + period + "秒";
        }
        else if( Constant.JOB.PERROID_MIN.equals( periodType ) )
        {
            return "每" + period + "分钟";
        }
        else if( Constant.JOB.PERROID_HOUR.equals( periodType ) )
        {
            return "每" + period + "小时";

        }
        else if( Constant.JOB.PERROID_DAY.equals( periodType ) )
        {
            return "每" + period + "天";
        }

        return null;
    }

    public void changeBlockToTreeItem( BlockInfoBean info )
    {
        this.isType = Boolean.FALSE;
        this.blockId = info.getBlockId();
        this.parentId = info.getParentId();
        this.siteId = info.getSiteId();
        this.blockName = info.getBlockName();
        this.flag = info.getFlag();
        this.type = info.getType();
        this.templateUrl = info.getTemplateUrl();
        this.blockDesc = info.getBlockDesc();
        this.staticUrl = info.getStaticUrl();
        this.creator = info.getCreator();
        this.jobId = info.getJobId();
        this.periodType = info.getPeriodType();
        this.period = info.getPeriod();
        if( info.getLastPubTime() != null )
        {
            this.lastPubTime = DateAndTimeUtil.getFormatDate( info
                .getLastPubTime().getTime(),
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
        }
    }

    public void changeTypeToTreeItem( BlockTypeBean type )
    {
        this.isType = Boolean.TRUE;
        this.blockTypeId = type.getBlockTypeId();
        this.blockTypeName = type.getBlockTypeName();
        this.siteFlag = type.getSiteFlag();
        this.creator = type.getCreator();
        this.mainTemplateUrl = type.getTemplateUrl();
    }
}

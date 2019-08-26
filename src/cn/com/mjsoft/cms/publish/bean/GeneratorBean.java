package cn.com.mjsoft.cms.publish.bean;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.util.StringUtil;

public class GeneratorBean
{
    private static ChannelService channelService = ChannelService.getInstance();

    private Long siteId = Long.valueOf( -1 );

    private String blockFlag;

    private String classIdInfo;

    private Boolean job = Boolean.FALSE;

    private int staticType = -1;

    private String startAddDate;

    private String endAddDate;

    public GeneratorBean()
    {

    }

    public GeneratorBean( Long siteId, int staticType )
    {
        this.siteId = siteId;
        this.staticType = staticType;
    }

    public GeneratorBean( Long siteId, String classIdInfo, int staticType )
    {
        this.siteId = siteId;
        this.classIdInfo = classIdInfo;
        this.staticType = staticType;
    }

    public String getClassIdInfo()
    {
        return classIdInfo;
    }

    public void setClassIdInfo( String classIdInfo )
    {
        this.classIdInfo = classIdInfo;
    }

    public int getStaticType()
    {
        return staticType;
    }

    public void setStaticType( int staticType )
    {
        this.staticType = staticType;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getBlockFlag()
    {
        return blockFlag;
    }

    public void setBlockFlag( String blockFlag )
    {
        this.blockFlag = blockFlag;
    }

    public Boolean getJob()
    {
        return job;
    }

    public void setJob( Boolean job )
    {
        this.job = job;
    }

    public String getEndAddDate()
    {
        return endAddDate;
    }

    public void setEndAddDate( String endAddDate )
    {
        this.endAddDate = endAddDate;
    }

    public String getStartAddDate()
    {
        return startAddDate;
    }

    public void setStartAddDate( String startAddDate )
    {
        this.startAddDate = startAddDate;
    }

    public List getClassBeanList()
    {
        if( StringUtil.isStringNull( classIdInfo ) )
        {
            return new ArrayList();
        }

        String[] tmpInfo = StringUtil.split( classIdInfo, "#" );

        List beanList = new ArrayList();

        ContentClassBean bean;
        long classId = -1;
        for ( int i = 0; i < tmpInfo.length; i++ )
        {
            classId = StringUtil.getLongValue( ( String ) tmpInfo[i], -1 );
            if( classId < 0 )
            {
                continue;
            }

            bean = channelService.retrieveSingleClassBeanInfoByClassId( Long
                .valueOf( classId ) );

            // 去掉已经不存在的栏目
            if( bean.getClassId().longValue() < 0 )
            {
                continue;
            }

            beanList.add( bean );
        }

        return beanList;
    }
}

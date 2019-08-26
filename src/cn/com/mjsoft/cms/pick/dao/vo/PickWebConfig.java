package cn.com.mjsoft.cms.pick.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "pick_web_config", id = "pickWebConfigId", idMode = EntitySqlBridge.DB_IDENTITY )
public class PickWebConfig
{

    private Long pickWebConfigId = Long.valueOf( -1 );

    private Long classId = Long.valueOf( -1 );

    private String pickWebName;

    private String startEntryUrl;

    private String contentEntryUrl;

    private String titleStart;

    private String titleEnd;

    private String contentStart;

    private String contentEnd;

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public String getContentEnd()
    {
        return contentEnd;
    }

    public void setContentEnd( String contentEnd )
    {
        this.contentEnd = contentEnd;
    }

    public String getContentEntryUrl()
    {
        return contentEntryUrl;
    }

    public void setContentEntryUrl( String contentEntryUrl )
    {
        this.contentEntryUrl = contentEntryUrl;
    }

    public String getContentStart()
    {
        return contentStart;
    }

    public void setContentStart( String contentStart )
    {
        this.contentStart = contentStart;
    }

    public Long getPickWebConfigId()
    {
        return pickWebConfigId;
    }

    public void setPickWebConfigId( Long pickWebConfigId )
    {
        this.pickWebConfigId = pickWebConfigId;
    }

    public String getPickWebName()
    {
        return pickWebName;
    }

    public void setPickWebName( String pickWebName )
    {
        this.pickWebName = pickWebName;
    }

    public String getStartEntryUrl()
    {
        return startEntryUrl;
    }

    public void setStartEntryUrl( String startEntryUrl )
    {
        this.startEntryUrl = startEntryUrl;
    }

    public String getTitleEnd()
    {
        return titleEnd;
    }

    public void setTitleEnd( String titleEnd )
    {
        this.titleEnd = titleEnd;
    }

    public String getTitleStart()
    {
        return titleStart;
    }

    public void setTitleStart( String titleStart )
    {
        this.titleStart = titleStart;
    }

}

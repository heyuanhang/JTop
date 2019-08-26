package cn.com.mjsoft.cms.weixin.bean.menu;

public class ButtonBean
{
    private String name;

    private String type;

    private String key;

    private String media_id;

    private String url;

    public ButtonBean( String name )
    {
        super();
        this.name = name;
    }

    public ButtonBean( String name, String type )
    {
        super();
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getMedia_id()
    {
        return media_id;
    }

    public void setMedia_id( String media_id )
    {
        this.media_id = media_id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

}

package cn.com.mjsoft.cms.weixin.bean.menu;

public class WxMenuBean
{
    private ButtonBean[] button;

    public WxMenuBean( ButtonBean[] button )
    {
        this.button = button;
    }

    public ButtonBean[] getButton()
    {
        return button;
    }

    public void setButton( ButtonBean[] button )
    {
        this.button = button;
    }

}

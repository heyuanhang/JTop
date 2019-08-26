package cn.com.mjsoft.cms.weixin.bean.menu;

public class HavaChildButtonBean extends ButtonBean
{
    public HavaChildButtonBean( String name )
    {
        super(name);
    }

    private ButtonBean[] sub_button;

    public ButtonBean[] getSub_button()
    {
        return sub_button;
    }

    public void setSub_button( ButtonBean[] sub_button )
    {
        this.sub_button = sub_button;
    }

}

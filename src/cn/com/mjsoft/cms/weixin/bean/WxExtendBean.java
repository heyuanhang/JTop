package cn.com.mjsoft.cms.weixin.bean;

import java.io.PrintWriter;
import java.util.Map;

import cn.com.mjsoft.cms.weixin.dao.vo.WxMenu;

public class WxExtendBean
{
    private Map requestMap;

    private PrintWriter out;

    private String event;

    private String msgType;

    private WxMenu menu;

    public WxExtendBean( Map requestMap, PrintWriter out, String event,
        String msgType )
    {
        super();
        this.requestMap = requestMap;
        this.out = out;
        this.event = event;
        this.msgType = msgType;
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent( String event )
    {
        this.event = event;
    }

    public PrintWriter getOut()
    {
        return out;
    }

    public void setOut( PrintWriter out )
    {
        this.out = out;
    }

    public Map getRequestMap()
    {
        return requestMap;
    }

    public void setRequestMap( Map requestMap )
    {
        this.requestMap = requestMap;
    }

    public WxMenu getMenu()
    {
        return menu;
    }

    public void setMenu( WxMenu menu )
    {
        this.menu = menu;
    }

    public String getMsgType()
    {
        return msgType;
    }

    public void setMsgType( String msgType )
    {
        this.msgType = msgType;
    }

}

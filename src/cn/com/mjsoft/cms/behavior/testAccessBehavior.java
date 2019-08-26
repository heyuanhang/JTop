package cn.com.mjsoft.cms.behavior;

import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.cms.weixin.bean.WxExtendBean;
import cn.com.mjsoft.framework.behavior.Behavior;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class testAccessBehavior implements Behavior
{
    private static StatService statService = StatService.getInstance();

    public Object operation( Object target, Object[] param )
    {
        HttpServletRequest req = ( HttpServletRequest ) target;

        WxExtendBean extendBean = ( WxExtendBean ) param[0];

        Map requestMap = extendBean.getRequestMap();

        String fromUserName = ( String ) requestMap.get( "FromUserName" );
        // 开发者微信号
        String toUserName = ( String ) requestMap.get( "ToUserName" );
        // 消息类型
        String msgType = ( String ) requestMap.get( "MsgType" );

        System.out.println( fromUserName + ":" + req.getSession().getId() );

        return new String[] { "true" };
    }
}

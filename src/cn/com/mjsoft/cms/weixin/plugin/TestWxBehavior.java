package cn.com.mjsoft.cms.weixin.plugin;

import java.io.PrintWriter;
import java.util.Date;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.weixin.bean.WxExtendBean;
import cn.com.mjsoft.cms.weixin.util.MessageUtil;
import cn.com.mjsoft.cms.weixin.util.message.resp.TextMessage;
import cn.com.mjsoft.framework.behavior.Behavior;

public class TestWxBehavior implements Behavior
{
    
    public Object operation( Object target, Object[] param )
    {
        WxExtendBean extend = ( WxExtendBean ) param[0];

        if( extend == null )
        {
            return null;
        }
        
        System.out.println( "++++++++++++++++++++++》"+extend.getEvent()+":"+extend.getMsgType());

        PrintWriter out = extend.getOut();

        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName( ( String ) extend.getRequestMap().get(
            "FromUserName" ) );
        textMessage.setFromUserName( ( String ) extend.getRequestMap().get(
            "ToUserName" ) );
        textMessage.setCreateTime( new Date().getTime() );
        textMessage.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_TEXT );

        textMessage.setContent( "huanyingnin!你好" );
        // 将文本消息对象转换成xml
        String respXml = MessageUtil.messageToXml( textMessage );

        out.print( respXml );

        return null;
    }

}

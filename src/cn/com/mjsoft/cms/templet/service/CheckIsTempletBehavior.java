package cn.com.mjsoft.cms.templet.service;

import cn.com.mjsoft.framework.behavior.Behavior;

public class CheckIsTempletBehavior implements Behavior
{

    private static final String TEMPLET_FLAG = "<cms:";

    private boolean needDocheck = true;

    public Object operation( Object target, Object[] param )
    {
        String targetLineStr = ( String ) target;

        if( needDocheck )
        {
            if( targetLineStr.toLowerCase().indexOf( TEMPLET_FLAG ) != -1 )
            {
                param[0] = Boolean.TRUE;
                // 已经证明这是一个模版文件,下次无需检查
                needDocheck = false;
            }
        }

        return Boolean.FALSE;
    }

}

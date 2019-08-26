package cn.com.mjsoft.cms.common.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class KeyAndValIterTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1L;

    private String choice = "";// 格式为a=1,b=2,c=3

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List res = new ArrayList();

        String[] strs = choice.split( "," );

        String[] kv = null;

        KV kvRes = null;

        for ( int i = 0; i < strs.length; i++ )
        {
            kv = strs[i].split( "=" );

            if( kv.length == 2 )
            {
                kvRes = new KV();

                kvRes.setKey( kv[0] );

                kvRes.setVal( kv[1] );

                res.add( kvRes );
            }
            else if( kv.length == 1 )
            {

                kvRes = new KV();

                kvRes.setKey( kv[0] );

                kvRes.setVal( kv[0] );

                res.add( kvRes );
            }
        }

        return res;
    }

    protected String returnPutValueName()
    {
        return "KV";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public class KV
    {
        private String key;

        private String val;

        public String getKey()
        {
            return key;
        }

        public void setKey( String key )
        {
            this.key = key;
        }

        public String getVal()
        {
            return val;
        }

        public void setVal( String val )
        {
            this.val = val;
        }

    }

    public void setChoice( String choice )
    {
        this.choice = choice;
    }

}

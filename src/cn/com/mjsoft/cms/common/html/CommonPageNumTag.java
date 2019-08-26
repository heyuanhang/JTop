package cn.com.mjsoft.cms.common.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class CommonPageNumTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -4548957371193535642L;

    private String max = "10";// 最多显示多少个页码

    private String movePos = "5";// 到达第几个位置时递进操作

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        Page pageInfo = ( Page ) pageContext.getAttribute( "Page" );

        if( pageInfo == null )
        {
            return null;
        }

        int maxVal = StringUtil.getIntValue( max, 10 );

        int movePosVal = StringUtil.getIntValue( movePos, 5 );

        int pageCount = pageInfo.getPageCount();

        int currentPage = pageInfo.getCurrentPage();

        int lp = maxVal - movePosVal + currentPage;

        boolean la = false;

        if( currentPage <= movePosVal )
        {
            la = true;
        }

        boolean ra = false;

        if( lp >= pageCount )
        {
            ra = true;
        }

        int startNum = 0;

        if( la )
        {
            startNum = currentPage - movePosVal;

            if( startNum < 1 )
            {
                startNum = 1;// 小于1则意味着还在movePos内,无需移位
            }

        }

        else if( ra )
        {
            startNum = pageCount - maxVal + 1;
        }

        else
        {
            startNum = currentPage - movePosVal + 1;
        }

        List numList = new ArrayList( maxVal + 1 );

        for ( int i = 0; i < maxVal; i++ )
        {
            if( startNum > pageCount )
            {
                break;
            }

            if(startNum > 0)
            {
                numList.add( Integer.valueOf( startNum ) );
            }
            
            startNum++;
        }

        return numList;
    }

    protected String returnPutValueName()
    {
        return "Num";
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

    public void setMax( String max )
    {
        this.max = max;
    }

    public void setMovePos( String movePos )
    {
        this.movePos = movePos;
    }
}

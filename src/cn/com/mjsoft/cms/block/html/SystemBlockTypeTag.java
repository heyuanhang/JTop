package cn.com.mjsoft.cms.block.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.block.service.BlockService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemBlockTypeTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 5866405455862055574L;

    private static BlockService blockService = BlockService.getInstance();

    private String typeId = "-1";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        if( !"-1".equals( typeId ) )
        {
            List res = new ArrayList( 2 );

            Long blockTypeId = Long.valueOf( StringUtil.getLongValue( typeId,
                -1 ) );

            res.add( blockService.retrieveSingleBlockTypeBean( blockTypeId ) );

            return res;
        }

        return blockService.retrieveAllBlockTypeBeanListByCurrSite();
    }

    protected String returnPutValueName()
    {
        return "BlockType";
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

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
    }

}

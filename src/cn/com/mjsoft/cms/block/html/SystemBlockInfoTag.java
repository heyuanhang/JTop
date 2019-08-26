package cn.com.mjsoft.cms.block.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.block.bean.BlockInfoBean;
import cn.com.mjsoft.cms.block.bean.BlockTreeInfoBean;
import cn.com.mjsoft.cms.block.bean.BlockTypeBean;
import cn.com.mjsoft.cms.block.service.BlockService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemBlockInfoTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -8729417486480586223L;

    private static BlockService blockService = BlockService.getInstance();

    private String blockId = "-1";

    private String typeId = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        Long blockIdVar = Long.valueOf( StringUtil.getLongValue( blockId, -1 ) );

        if( blockIdVar.longValue() > 0 )
        {
            List result = new ArrayList( 1 );

            BlockTreeInfoBean treeBean = new BlockTreeInfoBean();
            treeBean.changeBlockToTreeItem( ( blockService
                .retrieveSingleBlockBean( blockIdVar ) ) );
            result.add( treeBean );
            return result;
        }
        else
        {
            List treeItemBeanList = new ArrayList();

            List blockTypeList = null;

            List blockInfoList = null;

            if( !"".equals( typeId ) && !"-1".equals( typeId ) )
            {
                Long blockTypeId = Long.valueOf( StringUtil.getLongValue(
                    typeId, -1 ) );

                blockTypeList = new ArrayList( 2 );

                blockTypeList.add( blockService
                    .retrieveSingleBlockTypeBean( blockTypeId ) );

                blockInfoList = blockService
                    .retrieveBlockBeanListByTypeId( blockTypeId );
            }
            else
            {

                blockTypeList = blockService
                    .retrieveAllBlockTypeBeanListByCurrSite();

                blockInfoList = blockService
                    .retrieveAllBlockBeanListByCurrSite();

            }

            BlockTreeInfoBean treeBean = null;
            BlockInfoBean blockBean = null;
            BlockTypeBean typeBean = null;
            for ( int i = 0; i < blockTypeList.size(); i++ )
            {
                typeBean = ( BlockTypeBean ) blockTypeList.get( i );
                treeBean = new BlockTreeInfoBean();
                treeBean.changeTypeToTreeItem( typeBean );
                treeItemBeanList.add( treeBean );

                for ( int j = 0; j < blockInfoList.size(); j++ )
                {

                    blockBean = ( BlockInfoBean ) blockInfoList.get( j );

                    if( blockBean.getParentId().equals(
                        typeBean.getBlockTypeId() ) )
                    {
                        treeBean = new BlockTreeInfoBean();
                        treeBean
                            .changeBlockToTreeItem( ( BlockInfoBean ) blockInfoList
                                .get( j ) );
                        treeItemBeanList.add( treeBean );
                    }
                }
            }

            return treeItemBeanList;
        }
    }

    protected String returnPutValueName()
    {
        return "TreeItem";
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

    public void setBlockId( String blockId )
    {
        this.blockId = blockId;
    }

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
    }

}

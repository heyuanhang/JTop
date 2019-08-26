package cn.com.mjsoft.cms.resources.html;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemFileResourceTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -3919355284827042116L;

    private static Logger log = Logger.getLogger( SystemFileResourceTag.class );

    // 约定为aa*bb*cc,分隔符由服务器决定
    private String entry = "";

    // 分页标志
    private String page = "";

    // 大小默认20
    private String pageSize = "500";

    // 模板模式
    private String templateMode = "false";

    private static ResourcesService resService = ResourcesService.getInstance();

    protected List returnObjectList()
    {
        log.info( "[SystemFileResourceTag] {entryPath}:" + entry );

        List resList = Collections.EMPTY_LIST;

        if( StringUtil.isStringNull( entry ) )
        {
            entry = "";
        }

        
        log.info( "entry path:" + entry );

        resList = resService.retrieveSiteFolderAndFileTreeByParentFolder(
            entry, Constant.RESOURCE.LIST_FILE_MODE_ALL, StringUtil
                .getBooleanValue( templateMode, false ) );

        return resList;
    }

    protected String returnPutValueName()
    {
        return "File";
    }

    protected String returnValueRange()
    {
        return "selfRange";
    }

    // 不使用的方法

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnRequestAndPageListAttName()
    {

        return null;
    }

    protected void initTag()
    {

    }

    public String getEntry()
    {
        return entry;
    }

    public void setEntry( String entry )
    {
        this.entry = entry;
    }

    public String getPage()
    {
        return page;
    }

    public void setPage( String page )
    {
        this.page = page;
    }

    public String getPageSize()
    {
        return pageSize;
    }

    public void setPageSize( String pageSize )
    {
        this.pageSize = pageSize;
    }

    public void setTemplateMode( String templateMode )
    {
        this.templateMode = templateMode;
    }
}

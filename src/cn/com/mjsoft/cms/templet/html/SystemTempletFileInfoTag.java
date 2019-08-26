package cn.com.mjsoft.cms.templet.html;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.templet.service.TempletService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemTempletFileInfoTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = 4849183782207572002L;

    private static Logger log = Logger.getLogger( SystemTempletFileInfoTag.class );

    private TempletService templetService = TempletService.getInstance();

    // 约定为aa*bb*cc,分隔符由服务器决定
    private String entry;

    private String templetMode = "";

    private String folderMode = "false";

    protected List returnObjectList()
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( siteBean == null )
        {
            return new ArrayList();
            // TODO 跳转登陆
        }

        String baseRealPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        String rootPath = baseRealPath + siteBean.getSiteRoot() + File.separator
            + Constant.CONTENT.TEMPLATE_BASE;

        if( StringUtil.isStringNull( entry ) )
        {
            entry = "";
        }

        log.info( "entry path:" + entry );

        List fileList = templetService.retrieveAllTempletFileBySite( entry, rootPath, folderMode );

        log.info( "fileList:" + fileList );

        return fileList;
    }

    protected String returnPutValueName()
    {

        return "TempletFile";
    }

    protected String returnValueRange()
    {
        return "selfRange";
    }

    public int doEndTag() throws JspException
    {
        releaseAll();
        this.entry = null;
        return EVAL_PAGE;
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

    public String getEntry()
    {
        return entry;
    }

    public void setEntry( String entry )
    {
        this.entry = entry;
    }

    public String getTempletMode()
    {
        return templetMode;
    }

    public void setTempletMode( String templetMode )
    {
        this.templetMode = templetMode;
    }

    public void setFolderMode( String folderMode )
    {
        this.folderMode = folderMode;
    }

    protected void initTag()
    {

    }

}

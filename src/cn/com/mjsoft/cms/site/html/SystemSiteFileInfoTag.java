package cn.com.mjsoft.cms.site.html;

import java.io.FileFilter;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.templet.html.SystemTempletFileInfoTag;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

public class SystemSiteFileInfoTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 3140769449092705940L;

    private static final FileFilter FOLDER_TYPE = new SiteFolderTypeFilter();

    private static final FileFilter FILE_TYPE = new SiteFileTypeFilter();

    private static Logger log = Logger
        .getLogger( SystemTempletFileInfoTag.class );

    private String folderMode = "false";

    private String entryPath = "";

 

    private static SiteGroupService siteService = SiteGroupService
        .getInstance();

    // 约定为aa*bb*cc,分隔符由服务器决定
    private String entry;

    private String templetMode = "";

    protected List returnObjectList()
    {
        SecuritySession securitySession = SecuritySessionKeeper
            .getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession
            .getCurrentLoginSiteInfo();

        // 必须存在当前管理的站点信息
        if( securitySession == null || siteBean == null )
        {
            return Collections.EMPTY_LIST;
        }

        

        String rootPath = ServletUtil.getSiteFilePath( pageContext
            .getServletConfig() )
            + siteBean.getSiteRoot();

        if( StringUtil.isStringNull( entry ) )
        {
            entry = "";
        }

        log.info( "当前需要进入子目录:" + entry );

        List fileList = siteService.retrieveAllSiteFileInfoByPath( siteBean
            .getSiteName(), entry, rootPath, FOLDER_TYPE, FILE_TYPE );

        log.info( "目录输出结果:" + fileList );

        return fileList;
    }

    protected String returnPutValueName()
    {

        return "SiteFile";
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
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

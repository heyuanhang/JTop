package cn.com.mjsoft.cms.publish.bean;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class PublishRuleBean
{
    private static ChannelService channelService = ChannelService.getInstance();

    private Long ruleId = Long.valueOf( -1 );
    private Long siteId = Long.valueOf( -1 );
    private String ruleName;
    private Integer type;
    private String savePathRule;
    private String fileNameRule;

    private String savePathParam;
    private String fileNameParam;
    private String suffixRule;

    private String pagePathParam;
    private String pagePathRule;
    private String pageFileNameRule;
    private String pageFileNameParam;

    private String systemFormatPath;

    private String systemFormatPagePath;

    private Integer needPage;

    private Integer pageSize;

    private List pathParamList = new ArrayList();

    private List fileNameParamList = new ArrayList();

    private List pagePathParamList = new ArrayList();

    private List pageFileNameParamList = new ArrayList();

    public Long getRuleId()
    {
        return this.ruleId;
    }

    public void setRuleId( Long ruleId )
    {
        this.ruleId = ruleId;
    }

    public String getRuleName()
    {
        return this.ruleName;
    }

    public void setRuleName( String ruleName )
    {
        this.ruleName = ruleName;
    }

    public Integer getType()
    {
        return this.type;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public String getSavePathRule()
    {
        return this.savePathRule;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public void setSavePathRule( String savePathRule )
    {
        this.savePathRule = savePathRule;
        // systemFormatPath为系统相关文件分割符
        systemFormatPath =
        // File.separator +
        StringUtil.replaceString( this.savePathRule, Constant.CONTENT.URL_SEP,
            File.separator, false, false );

    }

    public String getFileNameRule()
    {
        return this.fileNameRule;
    }

    public void setFileNameRule( String fileNameRule )
    {
        this.fileNameRule = fileNameRule;

    }

    public String getSuffixRule()
    {
        return this.suffixRule;
    }

    public void setSuffixRule( String suffixRule )
    {
        this.suffixRule = suffixRule;
    }

    public String getFileNameParam()
    {
        return fileNameParam;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize( Integer pageSize )
    {
        this.pageSize = pageSize;
    }

    public void setFileNameParam( String fileNameParam )
    {
        this.fileNameParam = fileNameParam;

        if( !fileNameParamList.isEmpty() )
        {
            fileNameParamList = new ArrayList();
        }

        if( this.fileNameParam != null )
        {
            String[] pathParam = StringUtil.split( this.fileNameParam, "," );

            String param = null;

            for ( int i = 0; i < pathParam.length; i++ )
            {
                param = pathParam[i];
                if( StringUtil.isStringNotNull( param ) )
                {
                    fileNameParamList.add( param );
                }
            }
        }

    }

    public String getSavePathParam()
    {
        return savePathParam;
    }

    public void setSavePathParam( String savePathParam )
    {
        this.savePathParam = savePathParam;

        if( !pathParamList.isEmpty() )
        {
            pathParamList = new ArrayList();
        }

        if( this.savePathParam != null )
        {
            String[] pathParam = StringUtil.split( this.savePathParam, "," );

            String param = null;

            for ( int i = 0; i < pathParam.length; i++ )
            {
                param = pathParam[i];
                if( StringUtil.isStringNotNull( param ) )
                {
                    pathParamList.add( param );
                }
            }
        }
    }

    // 以下为业务方法

    public String getTypeStr()
    {
        if( this.type.intValue() == 1 )
        {
            return "栏目或专题首页";
        }

        if( this.type.intValue() == 2 )
        {
            return "栏目列表页";
        }

        if( this.type.intValue() == 3 )
        {
            return "专题列表页";
        }

        if( this.type.intValue() == 4 )
        {
            return "内容页";
        }

        return "";
    }

    public String getPageFileNameParam()
    {
        return pageFileNameParam;
    }

    public void setPageFileNameParam( String pageFileNameParam )
    {
        this.pageFileNameParam = pageFileNameParam;

        if( !pageFileNameParamList.isEmpty() )
        {
            pageFileNameParamList = new ArrayList();
        }

        if( this.pageFileNameParam != null )
        {
            String[] pathParam = StringUtil.split( this.pageFileNameParam, "," );

            String param = null;

            for ( int i = 0; i < pathParam.length; i++ )
            {
                param = pathParam[i];
                if( StringUtil.isStringNotNull( param ) )
                {
                    pageFileNameParamList.add( param );
                }
            }
        }
    }

    public String getPageFileNameRule()
    {
        return pageFileNameRule;
    }

    public void setPageFileNameRule( String pageFileNameRule )
    {
        this.pageFileNameRule = pageFileNameRule;
    }

    public String getPagePathParam()
    {
        return pagePathParam;
    }

    public Integer getNeedPage()
    {
        return needPage;
    }

    public void setNeedPage( Integer needPage )
    {
        this.needPage = needPage;
    }

    public void setPagePathParam( String pagePathParam )
    {
        this.pagePathParam = pagePathParam;

        if( !pagePathParamList.isEmpty() )
        {
            pagePathParamList = new ArrayList();
        }

        if( this.pagePathParam != null )
        {
            String[] pathParam = StringUtil.split( this.pagePathParam, "," );

            String param = null;

            for ( int i = 0; i < pathParam.length; i++ )
            {
                param = pathParam[i];
                if( StringUtil.isStringNotNull( param ) )
                {
                    pagePathParamList.add( param );
                }
            }
        }
    }

    public String getPagePathRule()
    {
        return pagePathRule;
    }

    public void setPagePathRule( String pagePathRule )
    {
        this.pagePathRule = pagePathRule;

        // systemFormatPath为系统相关文件分割符
        systemFormatPagePath = StringUtil.replaceString( this.pagePathRule,
            Constant.CONTENT.URL_SEP, File.separator, false, false );
    }

    /**
     * 返回完整的路径信息，包括服务器路径和网络路径
     * 
     * @param classBean
     * @param siteFilePath
     * @return String[] 0:为html
     */
    public String[] getFullContentClassPublishPath( SiteGroupBean site,
        ContentClassBean classBean, Map mainInfo, String siteFilePath )
    {
        String fullCatalogPath = systemFormatPath;
        String param = null;
        Calendar cal = Calendar.getInstance();

        Timestamp contentAddTime = null;

        if( mainInfo != null && mainInfo.get( "addTime" ) instanceof String )
        {
            contentAddTime = DateAndTimeUtil.getTimestamp( ( String ) mainInfo
                .get( "addTime" ), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
        }
        else
        {
            contentAddTime = ( mainInfo != null ) ? ( Timestamp ) mainInfo
                .get( "addTime" ) : null;
        }

        if( contentAddTime == null )
        {
            cal.setTime( classBean.getSystemHandleTime() );
        }
        else
        {
            cal.setTime( contentAddTime );
        }

        // 生成文件路径
        for ( int i = 0; i < pathParamList.size(); i++ )
        {
            param = ( String ) pathParamList.get( i );
            if( "{class-id}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{class-id}", classBean.getClassId().toString(), false,
                    false );
            }
            else if( "{class-flag}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{class-flag}", classBean.getClassFlag(), false, false );
            }
            else if( "{class-path}".equals( param ) )
            {
                List resultList = channelService
                    .retrieveContentClassBeanByCurrentPath( classBean
                        .getChannelPath() );

                String cPath = "";

                ContentClassBean bean = null;

                for ( int cp = 0; cp < resultList.size(); cp++ )
                {
                    bean = ( ContentClassBean ) resultList.get( cp );

                    if( cp + 1 != resultList.size() )
                    {
                        cPath = cPath + bean.getClassFlag() + File.separator;
                    }
                    else
                    {
                        cPath = cPath + bean.getClassFlag();
                    }
                }

                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{class-path}", cPath, false, false );
            }
            else if( "{content-id}".equals( param ) )
            {
                if( mainInfo != null
                    && mainInfo.get( Constant.METADATA.CONTENT_ID_NAME ) != null )
                {

                    fullCatalogPath = StringUtil.replaceString(
                        fullCatalogPath, "{content-id}", mainInfo.get(
                            Constant.METADATA.CONTENT_ID_NAME ).toString(),
                        false, false );
                }
            }
            else if( "{year}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{year}", Integer.toString( cal.get( Calendar.YEAR ) ),
                    false, false );
            }
            else if( "{month}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{month}",
                    Integer.toString( cal.get( Calendar.MONTH ) + 1 ), false,
                    false );
            }
            else if( "{day}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{day}", Integer
                        .toString( cal.get( Calendar.DAY_OF_MONTH ) ), false,
                    false );
            }

        }

        // 生成文件名称后缀
        String fullFileName = fileNameRule;
        for ( int i = 0; i < fileNameParamList.size(); i++ )
        {
            param = ( String ) fileNameParamList.get( i );
            if( "{class-id}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{class-id}", classBean.getClassId().toString(), false,
                    false );
            }
            else if( "{class-flag}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{class-flag}", classBean.getClassFlag(), false, false );
            }
            else if( "{content-id}".equals( param ) )
            {
                if( mainInfo != null
                    && mainInfo.get( Constant.METADATA.CONTENT_ID_NAME ) != null )
                {
                    fullFileName = StringUtil.replaceString( fullFileName,
                        "{content-id}", mainInfo.get(
                            Constant.METADATA.CONTENT_ID_NAME ).toString(),
                        false, false );
                }
            }
            else if( "{class-path}".equals( param ) )
            {
                List resultList = channelService
                    .retrieveContentClassBeanByCurrentPath( classBean
                        .getChannelPath() );

                String cPath = "";

                ContentClassBean bean = null;

                for ( int cp = 0; cp < resultList.size(); cp++ )
                {
                    bean = ( ContentClassBean ) resultList.get( cp );

                    if( cp + 1 != resultList.size() )
                    {
                        cPath = cPath + bean.getClassFlag() + File.separator;
                    }
                    else
                    {
                        cPath = cPath + bean.getClassFlag();
                    }
                }

                fullFileName = StringUtil.replaceString( fullFileName,
                    "{class-path}", cPath, false, false );
            }
            else if( "{year}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{year}", Integer.toString( cal.get( Calendar.YEAR ) ),
                    false, false );
            }
            else if( "{month}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{month}",
                    Integer.toString( cal.get( Calendar.MONTH ) + 1 ), false,
                    false );
            }
            else if( "{day}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName, "{day}",
                    Integer.toString( cal.get( Calendar.DAY_OF_MONTH ) ),
                    false, false );
            }
            else if( "{seq}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName, "{seq}",
                    StringUtil.getUUIDString(), false, false );
            }

        }

        String endPath = null;
        if( mainInfo == null )
        {
            // endPath = "channel" + fullCatalogPath;
            endPath = fullCatalogPath;
        }
        else
        {
            // endPath = "content" + fullCatalogPath;
            endPath = fullCatalogPath;
        }

        if( siteFilePath != null )
        {
            File file = new File( siteFilePath + site.getSiteRoot()
                + File.separator + site.getPublishRoot() + File.separator
                + endPath );
            if( !file.exists() )
            {
                file.mkdirs();
            }
        }
        
        String mobPath = "";
        
        String padPath = "";

        if( StringUtil.isStringNotNull( fullFileName ) )
        {
            if( fullFileName.startsWith( "/" ) )
            {
                mobPath = endPath + "mob_" + fullFileName + suffixRule;

                padPath = endPath + "pab_" + fullFileName + suffixRule;

                endPath = endPath + fullFileName + suffixRule;

            }
            else
            {
                mobPath = endPath + File.separator + "mob_" + fullFileName
                    + suffixRule;

                padPath = endPath + File.separator + "pad_" + fullFileName
                    + suffixRule;

                endPath = endPath + File.separator + fullFileName + suffixRule;
            }
        }
        
       

        if( endPath.startsWith( File.separator ) )
        {
            endPath = StringUtil.subString( endPath, 1, endPath.length() );
        }
        
        if( mobPath.startsWith( File.separator ) )
        {
            mobPath = StringUtil.subString( mobPath, 1, mobPath.length() );
        }
        
        if( padPath.startsWith( File.separator ) )
        {
            padPath = StringUtil.subString( padPath, 1, padPath.length() );
        }

        return new String[] {
            endPath,
            StringUtil.replaceString( endPath, File.separator,
                Constant.CONTENT.URL_SEP, false, false )
                , mobPath,
                StringUtil.replaceString( mobPath, File.separator,
                    Constant.CONTENT.URL_SEP, false, false )
                    ,padPath,
                    StringUtil.replaceString( padPath, File.separator,
                        Constant.CONTENT.URL_SEP, false, false )};
 
    }

    /**
     * 返回完整的分页路径信息，包括服务器路径和网络路径
     * 
     * @param classBean
     * @param siteFilePath
     * @return String[] 0:为html
     */
    public String[] getFullContentClassPagePublishPath( SiteGroupBean site,
        ContentClassBean classBean, Map mainInfo, String siteFilePath,
        Integer currentPageCount )
    {
        String fullCatalogPath = systemFormatPagePath;
        String param = null;

        Calendar cal = Calendar.getInstance();

//        Timestamp contentAddTime = ( mainInfo != null )
//            ? ( Timestamp ) mainInfo.get( "addTime" ) : null;
//
//        if( contentAddTime == null )
//        {
//            cal.setTime( classBean.getSystemHandleTime() );
//        }
//        else
//        {
//            cal.setTime( contentAddTime );
//        }
        Timestamp contentAddTime = null;

        if( mainInfo != null && mainInfo.get( "addTime" ) instanceof String )
        {
            contentAddTime = DateAndTimeUtil.getTimestamp( ( String ) mainInfo
                .get( "addTime" ), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
        }
        else
        {
            contentAddTime = ( mainInfo != null ) ? ( Timestamp ) mainInfo
                .get( "addTime" ) : null;
        }

        if( contentAddTime == null )
        {
            cal.setTime( classBean.getSystemHandleTime() );
        }
        else
        {
            cal.setTime( contentAddTime );
        }

        // 生成文件路径
        for ( int i = 0; i < pagePathParamList.size(); i++ )
        {
            param = ( String ) pagePathParamList.get( i );

            if( "{class-id}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{class-id}", classBean.getClassId().toString(), false,
                    false );
            }
            else if( "{class-flag}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{class-flag}", classBean.getClassFlag(), false, false );
            }
            else if( "{content-id}".equals( param ) )
            {
                if( mainInfo != null
                    && mainInfo.get( Constant.METADATA.CONTENT_ID_NAME ) != null )
                {
                    fullCatalogPath = StringUtil.replaceString(
                        fullCatalogPath, "{content-id}", mainInfo.get(
                            Constant.METADATA.CONTENT_ID_NAME ).toString(),
                        false, false );
                }
            }
            else if( "{class-path}".equals( param ) )
            {
                List resultList = channelService
                    .retrieveContentClassBeanByCurrentPath( classBean
                        .getChannelPath() );

                String cPath = "";

                ContentClassBean bean = null;

                for ( int cp = 0; cp < resultList.size(); cp++ )
                {
                    bean = ( ContentClassBean ) resultList.get( cp );

                    if( cp + 1 != resultList.size() )
                    {
                        cPath = cPath + bean.getClassFlag() + File.separator;
                    }
                    else
                    {
                        cPath = cPath + bean.getClassFlag();
                    }
                }

                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{class-path}", cPath, false, false );
            }
            else if( "{year}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{year}", Integer.toString( cal.get( Calendar.YEAR ) ),
                    false, false );
            }
            else if( "{month}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{month}",
                    Integer.toString( cal.get( Calendar.MONTH ) + 1 ), false,
                    false );
            }
            else if( "{day}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{day}", Integer
                        .toString( cal.get( Calendar.DAY_OF_MONTH ) ), false,
                    false );
            }
            else if( "{pn}".equals( param ) )
            {
                // 第一页不需要独立记录
                if( currentPageCount.intValue() > 1 )
                {
                    fullCatalogPath = StringUtil.replaceString(
                        fullCatalogPath, "{pn}", currentPageCount.toString(),
                        false, false );
                }
                else if( currentPageCount.intValue() < 0 )// 小于1,不替换pn,一般为跳转页使用
                {
                    // fullCatalogPath = StringUtil.replaceString(
                    // fullCatalogPath, "{pn}",
                    // "(1,"+classBean.getEndPagePos()+")", false, false );
                }
                else
                {
                    fullCatalogPath = StringUtil.replaceString(
                        fullCatalogPath, "_{pn}", "", false, false );
                }
            }
        }

        // 生成文件名称后缀
        String fullFileName = pageFileNameRule;
        for ( int i = 0; i < pageFileNameParamList.size(); i++ )
        {
            param = ( String ) pageFileNameParamList.get( i );
            if( "{class-id}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{class-id}", classBean.getClassId().toString(), false,
                    false );
            }
            else if( "{class-flag}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{class-flag}", classBean.getClassFlag(), false, false );
            }
            else if( "{content-id}".equals( param ) )
            {
                if( mainInfo != null
                    && mainInfo.get( Constant.METADATA.CONTENT_ID_NAME ) != null )
                {
                    fullFileName = StringUtil.replaceString( fullFileName,
                        "{content-id}", mainInfo.get(
                            Constant.METADATA.CONTENT_ID_NAME ).toString(),
                        false, false );
                }
            }
            else if( "{class-path}".equals( param ) )
            {
                List resultList = channelService
                    .retrieveContentClassBeanByCurrentPath( classBean
                        .getChannelPath() );

                String cPath = "";

                ContentClassBean bean = null;

                for ( int cp = 0; cp < resultList.size(); cp++ )
                {
                    bean = ( ContentClassBean ) resultList.get( cp );

                    if( cp + 1 != resultList.size() )
                    {
                        cPath = cPath + bean.getClassFlag() + File.separator;
                    }
                    else
                    {
                        cPath = cPath + bean.getClassFlag();
                    }
                }

                fullFileName = StringUtil.replaceString( fullFileName,
                    "{class-path}", cPath, false, false );
            }
            else if( "{year}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{year}", Integer.toString( cal.get( Calendar.YEAR ) ),
                    false, false );
            }
            else if( "{month}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{month}",
                    Integer.toString( cal.get( Calendar.MONTH ) + 1 ), false,
                    false );
            }
            else if( "{day}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName, "{day}",
                    Integer.toString( cal.get( Calendar.DAY_OF_MONTH ) ),
                    false, false );
            }
            else if( "{pn}".equals( param ) )
            {
                // 第一页不需要独立记录
                if( currentPageCount.intValue() > 1 )
                {
                    fullFileName = StringUtil.replaceString( fullFileName,
                        "{pn}", currentPageCount.toString(), false, false );
                }
                else if( currentPageCount.intValue() < 0 )// 小于1,不替换pn,一般为跳转页使用
                {
                    // fullFileName = StringUtil.replaceString( fullFileName,
                    // "{pn}", "(1,"+classBean.getEndPagePos()+")", false, false
                    // );
                }
                else
                {
                    fullFileName = StringUtil.replaceString( fullFileName,
                        "_{pn}", "", false, false );
                }
            }
        }

        String endPath = null;
        if( mainInfo == null )
        {
            // endPath = "channel" + fullCatalogPath;
            endPath = fullCatalogPath;
        }
        else
        {
            // endPath = "content" + fullCatalogPath;
            endPath = fullCatalogPath;

        }

        if( siteFilePath != null )
        {
            File file = new File( siteFilePath + site.getSiteRoot()
                + File.separator + site.getPublishRoot() + File.separator
                + endPath );
            if( !file.exists() )
            {
                file.mkdirs();
            }
        }
        
        String mobPath = "";
        
        String padPath = "";

        if( StringUtil.isStringNotNull( fullFileName ) )
        {
            if( fullFileName.startsWith( "/" ) )
            {
                mobPath = endPath + "mob_" + fullFileName + suffixRule;

                padPath = endPath + "pab_" + fullFileName + suffixRule;

                endPath = endPath + fullFileName + suffixRule;

            }
            else
            {
                mobPath = endPath + File.separator + "mob_" + fullFileName
                    + suffixRule;

                padPath = endPath + File.separator + "pad_" + fullFileName
                    + suffixRule;

                endPath = endPath + File.separator + fullFileName + suffixRule;
            }
        }

        if( endPath.startsWith( File.separator ) )
        {
            endPath = StringUtil.subString( endPath, 1, endPath.length() );
        }

        return new String[] {
            endPath,
            StringUtil.replaceString( endPath, File.separator,
                Constant.CONTENT.URL_SEP, false, false )
                , mobPath,
                StringUtil.replaceString( mobPath, File.separator,
                    Constant.CONTENT.URL_SEP, false, false )
                    ,padPath,
                    StringUtil.replaceString( padPath, File.separator,
                        Constant.CONTENT.URL_SEP, false, false )};
    }

    /**
     * 返回完整的路径信息，包括服务器路径和网络路径
     * 
     * @param classBean
     * @param siteFilePath
     * @return String[] 0:为html
     */
    public String[] getFullCommendInfoPublishPath( SiteGroupBean site,
        ContentCommendTypeBean comTypeBean, Map mainInfo, String siteFilePath,
        Integer currentPageCount )
    {
        String fullCatalogPath = systemFormatPagePath;
        String param = null;
        Calendar cal = Calendar.getInstance();

        // 生成文件路径
        for ( int i = 0; i < pagePathParamList.size(); i++ )
        {
            param = ( String ) pagePathParamList.get( i );
            if( "{class-id}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{class-id}", comTypeBean.getClassId().toString(), false,
                    false );
            }
            else if( "{type-id}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{type-id}", comTypeBean.getCommendTypeId().toString(),
                    false, false );
            }
            else if( "{comm-flag}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{comm-flag}", comTypeBean.getCommFlag(), false, false );
            }
            else if( "{year}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{year}", Integer.toString( cal.get( Calendar.YEAR ) ),
                    false, false );
            }
            else if( "{month}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{month}",
                    Integer.toString( cal.get( Calendar.MONTH ) + 1 ), false,
                    false );
            }
            else if( "{day}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{day}", Integer
                        .toString( cal.get( Calendar.DAY_OF_MONTH ) ), false,
                    false );
            }
            else if( "{pn}".equals( param ) )
            {
                // 第一页不需要独立记录
                if( currentPageCount.intValue() > 1 )
                {
                    fullCatalogPath = StringUtil.replaceString(
                        fullCatalogPath, "{pn}", currentPageCount.toString(),
                        false, false );
                }
                else if( currentPageCount.intValue() < 0 )// 小于1,不替换pn,一般为跳转页使用
                {
                    // fullCatalogPath = StringUtil.replaceString(
                    // fullCatalogPath, "{pn}",
                    // "(1,"+classBean.getEndPagePos()+")", false, false );
                }
                else
                {
                    fullCatalogPath = StringUtil.replaceString(
                        fullCatalogPath, "_{pn}", "", false, false );
                }
            }
            else if( "{seq}".equals( param ) )
            {
                fullCatalogPath = StringUtil.replaceString( fullCatalogPath,
                    "{seq}", StringUtil.getUUIDString(), false, false );
            }

        }

        // 生成文件名称后缀
        String fullFileName = pageFileNameRule;
        for ( int i = 0; i < pageFileNameParamList.size(); i++ )
        {
            param = ( String ) pageFileNameParamList.get( i );
            if( "{class-id}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{class-id}", comTypeBean.getClassId().toString(), false,
                    false );
            }

            else if( "{type-id}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{type-id}", comTypeBean.getCommendTypeId().toString(),
                    false, false );
            }
            else if( "{comm-flag}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{comm-flag}", comTypeBean.getCommFlag(), false, false );
            }

            else if( "{year}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{year}", Integer.toString( cal.get( Calendar.YEAR ) ),
                    false, false );
            }
            else if( "{month}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName,
                    "{month}",
                    Integer.toString( cal.get( Calendar.MONTH ) + 1 ), false,
                    false );
            }
            else if( "{day}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName, "{day}",
                    Integer.toString( cal.get( Calendar.DAY_OF_MONTH ) ),
                    false, false );
            }
            else if( "{pn}".equals( param ) )
            {
                // 第一页不需要独立记录
                if( currentPageCount.intValue() > 1 )
                {
                    fullFileName = StringUtil.replaceString( fullFileName,
                        "{pn}", currentPageCount.toString(), false, false );
                }
                else if( currentPageCount.intValue() < 0 )// 小于1,不替换pn,一般为跳转页使用
                {
                    // fullCatalogPath = StringUtil.replaceString(
                    // fullCatalogPath, "{pn}",
                    // "(1,"+classBean.getEndPagePos()+")", false, false );
                }
                else
                {
                    fullFileName = StringUtil.replaceString( fullFileName,
                        "_{pn}", "", false, false );
                }
            }
            else if( "{seq}".equals( param ) )
            {
                fullFileName = StringUtil.replaceString( fullFileName, "{seq}",
                    StringUtil.getUUIDString(), false, false );
            }
        }

        String endPath = null;
        if( mainInfo == null )
        {
            // endPath = "channel" + fullCatalogPath;
            endPath = fullCatalogPath;
        }
        else
        {
            // endPath = "content" + fullCatalogPath;
            endPath = fullCatalogPath;

        }

        if( siteFilePath != null )
        {
            File file = new File( siteFilePath + site.getSiteRoot()
                + File.separator + site.getPublishRoot() + File.separator
                + endPath );
            if( !file.exists() )
            {
                file.mkdirs();
            }
        }

        String mobPath = "";
        
        String padPath = "";

        if( StringUtil.isStringNotNull( fullFileName ) )
        {
            if( fullFileName.startsWith( "/" ) )
            {
                mobPath = endPath + "mob_" + fullFileName + suffixRule;

                padPath = endPath + "pab_" + fullFileName + suffixRule;

                endPath = endPath + fullFileName + suffixRule;

            }
            else
            {
                mobPath = endPath + File.separator + "mob_" + fullFileName
                    + suffixRule;

                padPath = endPath + File.separator + "pad_" + fullFileName
                    + suffixRule;

                endPath = endPath + File.separator + fullFileName + suffixRule;
            }
        }

        if( endPath.startsWith( File.separator ) )
        {
            endPath = StringUtil.subString( endPath, 1, endPath.length() );
        }

        return new String[] {
            endPath,
            StringUtil.replaceString( endPath, File.separator,
                Constant.CONTENT.URL_SEP, false, false ),mobPath,
                StringUtil.replaceString( mobPath, File.separator,
                    Constant.CONTENT.URL_SEP, false, false ),padPath,
                    StringUtil.replaceString( padPath, File.separator,
                        Constant.CONTENT.URL_SEP, false, false ) };
    }

}

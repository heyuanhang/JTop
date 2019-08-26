package cn.com.mjsoft.cms.common.page;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 分页助手
 * 
 * @author mj-soft
 * 
 */
public class Page
{
    private static Logger log = Logger.getLogger( Page.class );

    public static final int DEFAULT_PAGE_SIZE = 16;

    private int pageSize;// 页面数据大小

    private int totalCount;// 数据总数

    private int pageCount;// 总页数

    private int currentPage;// 当前页

    private String queryCod;// 当前分页的分页查询条件

    private String nextQueryCd = null;

    private String nextQuery = null;
    private String prevQuery = null;

    private String headQuery = null;
    private String endQuery = null;

    private String jumpQuery = null;

    private String jumpStatic = null;

    private String jumpQueryMob = null;

    private String endPos = null;

    private ContentClassBean classBean = null;// 若为内容分页，需要知道栏目信息，以处理多渠道模板

    // 发布使用

    private String nextQueryActionUrl = null;

    public Page( int pageSize, int totalCount )
    {
        log.info( "[Page] 创建一个新的Page对象  [pageSize]:" + pageSize + ", [totalCount]:" + totalCount );

        this.pageSize = pageSize;
        this.totalCount = totalCount;

        // 计算总页数(另:totalCount + pageSize -1 / pageSize)
        pageCount = this.totalCount / this.pageSize
            + ( this.totalCount % this.pageSize == 0 ? 0 : 1 );// 不足一页算一页
    }

    public Page( int pageSize, int totalCount, int page )
    {
        this( pageSize, totalCount );

        if( page < 1 )// 小于第一页为第一页
        {
            this.currentPage = 1;
        }
        else if( page > pageCount && pageCount > 0 )// 大于最后一页为最后一页
        {
            currentPage = pageCount;
        }
        else
        {
            this.currentPage = page;
        }

        if( pageSize < 1 )// 每页至少一条记录
            this.pageSize = 1;

        log.info( "[Page] 创建一个新的Page对象 [currentPage]:" + this.currentPage + ", [pageSize]:"
            + pageSize + ", [totalCount]:" + totalCount );
    }

    public Page( int pageSize, int totalCount, int page, ContentClassBean classBean )
    {
        this( pageSize, totalCount, page );

        this.classBean = classBean;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage( int page )
    {
        if( page < 1 )// 小于第一页为第一页
        {
            this.currentPage = 1;
        }
        else if( page > pageCount && pageCount > 0 )// 大于最后一页为最后一页
        {
            currentPage = pageCount;
        }
        else
        {
            this.currentPage = page;
        }
    }

    public int getPageSize()
    {
        if( getCurrentPage() == getPageCount() || getPageCount() == 0 )
        {
            return getLastPageSize();
        }

        return pageSize;
    }

    public int getLastPageSize()
    {
        int lastPageSize = totalCount - pageSize * ( pageCount - 1 );

        if( lastPageSize <= 0 )
        {
            lastPageSize = pageSize;
        }

        return lastPageSize;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    /**
     * 起始位置
     * 
     * @return
     */
    public long getFirstResult()
    {
        return Long.valueOf( ( currentPage - 1 ) * pageSize ).longValue();
    }

    /**
     * 结束位置
     * 
     * @return
     */
    public long getEndResult()
    {
        return Long.valueOf( getFirstResult() + pageSize ).longValue();
    }

    public boolean getHasPrevious()
    {
        return currentPage > 1;
    }

    public boolean getHasNext()
    {
        return currentPage < pageCount;
    }

    public boolean isEmpty()
    {
        return totalCount == 0;
    }

    public String getEndQuery()
    {
        return endQuery;
    }

    public void setEndQuery( String endQuery )
    {
        this.endQuery = endQuery;
    }

    public String getHeadQuery()
    {
        return headQuery;
    }

    public void setHeadQuery( String headQuery )
    {
        this.headQuery = headQuery;
    }

    public String getNextQuery()
    {
        return nextQuery;
    }

    public void setNextQuery( String nextQuery )
    {
        this.nextQuery = nextQuery;
    }

    public String getPrevQuery()
    {
        return prevQuery;
    }

    public void setPrevQuery( String prevQuery )
    {
        this.prevQuery = prevQuery;
    }

    public String getJumpQuery()
    {
        return jumpQuery;
    }

    public void setJumpQuery( String jumpQuery )
    {
        this.jumpQuery = jumpQuery;
    }

    public String getJumpStatic()
    {
        return jumpStatic;
    }

    public void setJumpStatic( String jumpStatic )
    {
        this.jumpStatic = jumpStatic;
    }

    public String getEndPos()
    {
        return endPos;
    }

    public void setEndPos( String endPos )
    {
        this.endPos = endPos;
    }

    public String getNextQueryActionUrl()
    {
        return nextQueryActionUrl;
    }

    public void setNextQueryActionUrl( String nextQueryActionUrl )
    {
        this.nextQueryActionUrl = nextQueryActionUrl;
    }

    public String getNextQueryCd()
    {
        return nextQueryCd;
    }

    public void setNextQueryCd( String nextQueryCd )
    {
        this.nextQueryCd = nextQueryCd;
    }

    public String getEndQueryMob()
    {
        return getMobAndPadPageUrl( this.classBean, this.endQuery, "list", "mob" );
    }

    public String getHeadQueryMob()
    {
        return getMobAndPadPageUrl( this.classBean, this.headQuery, "list", "mob" );
    }

    public String getNextQueryMob()
    {
        return getMobAndPadPageUrl( this.classBean, this.nextQuery, "list", "mob" );
    }

    public String getPrevQueryMob()
    {
        return getMobAndPadPageUrl( this.classBean, this.prevQuery, "list", "mob" );
    }

    public String getJumpQueryMob()
    {
        return jumpQueryMob;
    }

    public String getQueryCod()
    {
        return queryCod;
    }

    public void setQueryCod( String queryCod )
    {
        this.queryCod = queryCod;
    }

    public String getEndQueryPad()
    {
        return getMobAndPadPageUrl( this.classBean, this.endQuery, "list", "Pad" );
    }

    public String getHeadQueryPad()
    {
        return getMobAndPadPageUrl( this.classBean, this.headQuery, "list", "Pad" );
    }

    public String getNextQueryPad()
    {
        return getMobAndPadPageUrl( this.classBean, this.nextQuery, "list", "Pad" );
    }

    public String getPrevQueryPad()
    {
        return getMobAndPadPageUrl( this.classBean, this.prevQuery, "list", "Pad" );
    }

    public String getJumpQueryPad()
    {
        return jumpQueryMob;
    }

    public static String getMobAndPadPageUrl( ContentClassBean classBean, String url, String flag,
        String client )
    {
        if( url == null )
        {
            return null;
        }

        if( url.endsWith( ".html" ) || url.endsWith( ".shtml" ) )
        {
            return url;
        }

        if( classBean == null || classBean.getClassId() < 0 )
        {
            return "";
        }

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        if( site == null )
        {
            return "";
        }

        String cond = StringUtil.subString( url, url.indexOf( "?" ) + 1, url.length() );

        String target = "";

        String temp = "";

        String main = site.getMobSiteUrl();

        if( main.endsWith( "/mob/" ) || main.endsWith( "/pad/" ) )
        {
            main = StringUtil.subString( main, 0, main.length() - 4 );
        }

        if( "co".equals( flag ) )
        {
            if( "mob".equals( client ) )
            {
                target = classBean.getMobContentTemplateUrl();
            }
            else if( "pad".equals( client ) )
            {
                target = classBean.getPadContentTemplateUrl();
            }

        }
        else if( "list".equals( flag ) )
        {
            if( "mob".equals( client ) )
            {
                target = classBean.getMobClassTemplateUrl();
            }
            else if( "pad".equals( client ) )
            {
                target = classBean.getPadClassTemplateUrl();
            }

            target = StringUtil.replaceString( target, "{class-id}", classBean.getClassId()
                .toString(), false, true );

            if( StringUtil.isStringNull( classBean.getClassTemplateUrl() ) )
            {
                cond = "&" + cond;
            }
            else
            {
                temp = StringUtil.subString( classBean.getClassTemplateUrl(), classBean
                    .getClassTemplateUrl().indexOf( "?" ) + 1, classBean.getClassTemplateUrl()
                    .length() );

                temp = StringUtil.replaceString( temp, "{class-id}", classBean.getClassId()
                    .toString(), false, true );

                cond = StringUtil.replaceString( cond, temp, "", false, true );

                if( !cond.startsWith( "&" ) )
                {
                    cond = "&" + cond;
                }

            }
        }

        String mob = main + target + cond;

        return mob;
    }

}

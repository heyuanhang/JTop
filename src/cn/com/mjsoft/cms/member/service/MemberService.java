package cn.com.mjsoft.cms.member.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.member.bean.MemberAccRuleBean;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.bean.MemberLoginUser;
import cn.com.mjsoft.cms.member.bean.MemberMessageTemplateBean;
import cn.com.mjsoft.cms.member.bean.MemberRankBean;
import cn.com.mjsoft.cms.member.bean.MemberScoreActBean;
import cn.com.mjsoft.cms.member.dao.MemberDao;
import cn.com.mjsoft.cms.member.dao.vo.Member;
import cn.com.mjsoft.cms.member.dao.vo.MemberMessage;
import cn.com.mjsoft.cms.member.dao.vo.MemberMessageTemplate;
import cn.com.mjsoft.cms.member.dao.vo.MemberRank;
import cn.com.mjsoft.cms.member.dao.vo.MemberScoreAct;
import cn.com.mjsoft.cms.member.html.MemberUtilTag;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.security.bean.MemberRoleBean;
import cn.com.mjsoft.cms.security.dao.SecurityDao;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.crypto.PasswordUtility;
import cn.com.mjsoft.framework.security.crypto.util.EncodeOne;
import cn.com.mjsoft.framework.security.headstream.IUser;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

public class MemberService
{
    private static Logger log = Logger.getLogger( MemberService.class );

    private static final Cache mamberInfoCache = new ClusterCacheAdapter( 2000,
        "memberService.mamberInfoCache" );

    private static final Cache mamberActInfoCache = new ClusterCacheAdapter( 4000,
        "memberService.mamberActInfoCache" );

    private static MemberService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private ResourcesService resService = ResourcesService.getInstance();

    private MetaDataService metaDataService = MetaDataService.getInstance();

    private ContentService contentService = ContentService.getInstance();

    private SecurityService securityService = SecurityService.getInstance();

    private MemberDao memberDao;

    private SecurityDao securityDao;

    private ContentDao contentDao;

    private ChannelDao channelDao;

    private MemberService()
    {
        memberDao = new MemberDao( mysqlEngine );
        securityDao = new SecurityDao( mysqlEngine );
        contentDao = new ContentDao( mysqlEngine );
        channelDao = new ChannelDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new MemberService();
        }
    }

    public static MemberService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void addNewMemberMessageTemplate( MemberMessageTemplate mt )
    {
        memberDao.save( mt );
    }

    public void editMemberMessageTemplate( MemberMessageTemplate mt )
    {
        memberDao.updateMemberMessageTemplate( mt );
    }

    public void addMemberMessageTemplate( MemberMessageTemplate mt )
    {
        memberDao.updateMemberMessageTemplate( mt );
    }

    public Long retrieveMemeberCount( Long siteId )
    {

        Long count = memberDao.queryMemeberCountBySiteId( siteId );

        return count;

    }

    public List retrieveMemeberList( Long siteId, Long start, Integer size )
    {
        return memberDao.queryMemeberBySiteId( siteId, start, size );
    }

    public List retrieveMemeberByRoleId( Long roleId, Long start, Integer size )
    {
        return memberDao.queryMemeberByRoleId( roleId, start, size );

    }

    public Long retrieveMemeberCountByRoleId( Long roleId )
    {
        return memberDao.queryMemeberCountByRoleId( roleId );
    }

    public void clientAddNewMemberMessage( Long memberId, Map templateParams, String templateFlag )
    {
        if( templateParams == null )
        {
            return;
        }

        Long siteId = ( Long ) templateParams.get( "siteId" );

        // 处理模板参数

        MemberMessageTemplateBean template = memberDao.querySingleMessageTemplate( templateFlag,
            siteId );

        if( template == null )
        {
            return;
        }

        String templateContent = template.getTemplateContent();

        Iterator iter = templateParams.entrySet().iterator();

        Entry entry = null;

        String key = null;

        String value = null;

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();
            key = ( String ) entry.getKey();
            value = entry.getValue() != null ? entry.getValue().toString() : "";

            templateContent = StringUtil.replaceString( templateContent, "${" + key + "}", value,
                false, false );
        }

        MemberMessage msg = new MemberMessage();

        msg.setMsgTypeName( "系统日志" );
        msg.setMsgTitle( template.getTemplateTitle() );
        msg.setIsRead( Constant.COMMON.OFF );
        msg.setMemberId( memberId );
        msg.setMsgContent( templateContent );
        msg.setSendDt( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
        msg.setIsSys( Constant.COMMON.ON );

        memberDao.save( msg );

    }

    public void clientAddNewDefinedMemberMessage( Long memberId, Map templateParams, String title,
        String content )
    {
        if( templateParams == null )
        {
            return;
        }

        // 处理模板参数

        if( StringUtil.isStringNull( title ) || StringUtil.isStringNull( content ) )
        {
            return;
        }

        String templateContent = content;

        Iterator iter = templateParams.entrySet().iterator();

        Entry entry = null;

        String key = null;

        String value = null;

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();
            key = ( String ) entry.getKey();
            value = entry.getValue() != null ? entry.getValue().toString() : "";

            templateContent = StringUtil.replaceString( templateContent, "${" + key + "}", value,
                false, false );
        }

        MemberMessage msg = new MemberMessage();

        msg.setMsgTypeName( "系统日志" );
        msg.setMsgTitle( title );
        msg.setIsRead( Constant.COMMON.OFF );
        msg.setMemberId( memberId );
        msg.setMsgContent( templateContent );
        msg.setSendDt( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
        msg.setIsSys( Constant.COMMON.OFF );

        memberDao.save( msg );

    }

    public String disposeDefinedMemberEmailAndMessageContent( Map templateParams, String content )
    {
        if( templateParams == null )
        {
            return content;
        }

        // 处理模板参数

        if( StringUtil.isStringNull( content ) )
        {
            return content;
        }

        // 事件发生时间
        templateParams.put( "sysEventDT", DateAndTimeUtil.getFormatDate( DateAndTimeUtil
            .clusterTimeMillis(), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

        String templateContent = content;

        Iterator iter = templateParams.entrySet().iterator();

        Entry entry = null;

        String key = null;

        String value = null;

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();
            key = ( String ) entry.getKey();
            value = entry.getValue() != null ? entry.getValue().toString() : "";

            templateContent = StringUtil.replaceString( templateContent, "${" + key + "}", value,
                false, false );
        }

        return templateContent;

    }

    public void clientDisposeMemberScore( Long memberId, Map memberInfo, String commandFlag,
        Integer eventCount )
    {
        if( StringUtil.isStringNull( commandFlag ) )
        {
            return;
        }

        Long siteId = ( Long ) memberInfo.get( "siteId" );

        MemberScoreActBean saBean = memberDao.querySingleMemberScoreActInfoByCommand( commandFlag,
            siteId );

        if( saBean == null )
        {
            return;
        }

        // 积分扩展行为
        String saClassName = saBean.getActClass();

        if( StringUtil.isStringNotNull( saClassName ) )
        {
            Class classObj = ObjectUtility.getClassInstance( saClassName );

            if( classObj != null )
            {
                Object valiedateBehavior = null;

                try
                {
                    valiedateBehavior = classObj.newInstance();
                }
                catch ( InstantiationException e )
                {

                    e.printStackTrace();
                }
                catch ( IllegalAccessException e )
                {

                    e.printStackTrace();
                }

                if( valiedateBehavior instanceof Behavior )
                {
                    ( ( Behavior ) valiedateBehavior ).operation( saBean, new Object[] {
                        memberInfo, commandFlag } );
                }
            }
        }
        else
        {
            if( Constant.COMMON.ON.equals( saBean.getActFlag() ) )
            {
                // 增加积分
                memberDao.updateMemberScoreAddVal( memberId, Integer.valueOf( saBean.getActScore()
                    .intValue()
                    * eventCount.intValue() ) );

            }
            else if( Constant.COMMON.OFF.equals( saBean.getActFlag() ) )
            {
                // 减少积分
                memberDao.updateMemberScoreReduceVal( memberId, Integer.valueOf( saBean
                    .getActScore().intValue()
                    * eventCount.intValue() ) );
            }
        }

        // 等级变换

        disposeMemberRank( memberId );

        removeMemberInfoCache( memberId );

    }

    public void disposeMemberRank( Long memberId )
    {
        // 不可取cache
        MemberBean member = memberDao.querySingleMemberBeanById( memberId );

        if( member != null )
        {
            Map rank = null;

            long minScore = 0;

            long maxScore = 0;

            List levelInfo = memberDao.queryMemberRankInfo( member.getSiteId() );

            if( levelInfo.size() > 0 )
            {

                for ( int i = 0; i < levelInfo.size(); i++ )
                {
                    rank = ( Map ) levelInfo.get( i );

                    minScore = ( ( Long ) rank.get( "minScore" ) ).longValue();

                    maxScore = ( ( Long ) rank.get( "maxScore" ) ).longValue();

                    if( member.getScore().longValue() >= minScore
                        && member.getScore().longValue() <= maxScore )
                    {
                        memberDao.updateMemberLevel( member.getMemberId(), ( Integer ) rank
                            .get( "rankLevel" ) );

                        break;
                    }
                }

            }

        }
    }

    public void addNewMessageTemplateParam( Map param )
    {
        memberDao.saveMessageTemplateParam( param );
    }

    public void editMessageTemplateParam( Map params )
    {
        memberDao.updateMessageTemplateParam( params );
    }

    public void addMemberRank( MemberRank rank )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            rank.setSiteId( site.getSiteId() );

            memberDao.saveMemberRank( rank );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            memberDao.clearRankCache();
        }
    }

    public void editMemberRank( MemberRank rank )
    {
        memberDao.updateMemberRank( rank );

        memberDao.clearRankCache();
    }

    public MemberRankBean getMemberSingleRankForTag( String id )
    {

        Long rankId = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

        return memberDao.querySingleMemberRankInfo( rankId );
    }

    public Map getMemberSingleRankByLevelForTag( String level )
    {
        Integer levelId = Integer.valueOf( StringUtil.getIntValue( level, -1 ) );

        List rankList = memberDao.queryMemberRankInfoByLevel( levelId );

        if( rankList.size() > 0 )
        {
            return ( Map ) rankList.get( 0 );
        }

        return null;
    }

    public String getMemberRoleInfoForTag( String memId )
    {
        Long memberId = Long.valueOf( StringUtil.getLongValue( memId, -1 ) );

        List roleList = securityDao.queryMemberRoleBeanByUserId( memberId );

        StringBuffer buf = new StringBuffer();

        MemberRoleBean bean = null;

        for ( int i = 0; i < roleList.size(); i++ )
        {
            bean = ( MemberRoleBean ) roleList.get( i );

            if( i + 1 < roleList.size() )
            {
                buf.append( bean.getRoleName() + ",  " );
            }
            else
            {
                buf.append( bean.getRoleName() );
            }

        }

        return buf.toString();
    }

    public void deleteMemberRank( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long rankId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                rankId = Long.valueOf( ( idList.get( i ) ).toString() );

                if( rankId.longValue() < 1 )
                {
                    continue;
                }

                memberDao.deleteMemberRank( rankId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            memberDao.clearRankCache();
        }

    }

    public List getMemberRankInfoForTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return memberDao.queryMemberRankInfo( site.getSiteId() );
    }

    public void addMemberScoreAct( MemberScoreAct sa )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            sa.setSiteId( site.getSiteId() );

            memberDao.saveMemberScoreAct( sa );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }
    }

    public void editMemberScoreAct( MemberScoreAct sa )
    {
        memberDao.updateMemberScoreAct( sa );
    }

    public void deleteMemberScoreAct( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long saId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                saId = Long.valueOf( ( idList.get( i ) ).toString() );

                if( saId.longValue() < 1 )
                {
                    continue;
                }

                memberDao.deleteMemberScoreAct( saId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }

    }

    public List getMemberScoreActForTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return memberDao.queryMemberScoreActInfo( site.getSiteId() );
    }

    public MemberScoreActBean getMemberSingleScoreActForTag( String id )
    {
        Long saId = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

        return memberDao.querySingleMemberScoreActInfo( saId );
    }

    public Object[] retrieveMemberInfo( String sid, String status, String pageSize,
        String pageNumber, String searchAction, String searchKey )
    {
        List result = null;

        Long siteId = Long.valueOf( StringUtil.getLongValue( sid, -1 ) );

        if( siteId.longValue() < 0 )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        // 搜索会员优先执行

        if( StringUtil.isStringNotNull( searchAction ) )
        {
            result = new ArrayList();

            String flag = searchAction;
            String key = SystemSafeCharUtil.decodeFromWeb( searchKey );

            Map res = null;

            if( "name".equals( flag ) )
            {
                if( StringUtil.isStringNotNull( key ) )
                {
                    result.addAll( memberDao.queryMemeberLikeName( siteId, key ) );
                }
            }
            else if( "trueName".equals( flag ) )
            {
                if( StringUtil.isStringNotNull( key ) )
                {
                    result = memberDao.queryMemeberByTrueName( siteId, key );
                }
            }
            else if( "email".equals( flag ) )
            {
                if( StringUtil.isStringNotNull( key ) )
                {
                    result = memberDao.queryMemeberByEmail( siteId, key );

                }
            }
            else if( "phone".equals( flag ) )
            {
                if( StringUtil.isStringNotNull( key ) )
                {
                    res = memberDao.queryMemeberByPhone( siteId, key );

                    if( !res.isEmpty() )
                    {
                        result.add( res );
                    }
                }
            }
            else if( "applTrueName".equals( flag ) )
            {
                result = memberDao.queryMemeberByIsTrueName( siteId, "2" );
            }
            else if( "isTrueName".equals( flag ) )
            {
                result = memberDao.queryMemeberByIsTrueName( siteId, "1" );
            }

            pageInfo = new Page( size, result.size(), pn );

            return new Object[] { result, pageInfo };
        }

        if( !"".equals( status ) )
        {
            Long count = memberDao.queryMemeberCountBySiteId( siteId, Integer.valueOf( status ) );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberBySiteId( siteId, Integer.valueOf( status ), Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else
        {
            Long count = memberDao.queryMemeberCountBySiteId( siteId );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberBySiteId( siteId, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }

        return new Object[] { result, pageInfo };
    }

    public String sendMemberMessage( String title, String content, List idList )
    {
        List memberIdList = idList;

        // 每个会员发送消息
        Long memberId = null;
        for ( int i = 0; i < memberIdList.size(); i++ )
        {
            // 获取登陆ID
            memberId = Long.valueOf( ( memberIdList.get( i ) ).toString() );

            Map member = memberDao.querySingleMemberInfo( memberId );

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( member.get( "siteId" ) );

            member = retrieveSingleMemberAndExtInfo( memberId, site.getExtMemberModelId() );

            if( member.isEmpty() )
            {
                // 会员查询为空,说明非法会员
                continue;
            }

            // 事件发生时间
            member.put( "sysEventDT", DateAndTimeUtil.getFormatDate( DateAndTimeUtil
                .clusterTimeMillis(), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

            // 生成会员消息
            clientAddNewDefinedMemberMessage( memberId, member, title, content );
        }

        return "1";
    }

    public String sendMemberMail( String title, String content, List idList )
    {
        List memberIdList = idList;

        // 每个会员发送消息
        Long memberId = null;
        for ( int i = 0; i < memberIdList.size(); i++ )
        {
            // 获取登陆ID
            memberId = Long.valueOf( ( memberIdList.get( i ) ).toString() );

            // 加入邮件对列
            clientAddNewMemberEmail( memberId, title, content );
        }

        return "1";
    }

    public String clientAddNewMemberEmail( Long memberId, String title, String content )
    {

        Map memberInfo = memberDao.querySingleMemberInfo( memberId );

        if( memberInfo.isEmpty() )
        {
            // 用户不存在
            return "-1";
        }

        String targetMail = ( String ) memberInfo.get( "email" );

        if( StringUtil.isStringNull( targetMail ) )
        {
            // 邮件格式不正确
            return "-2";
        }

        String subject = title;

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( memberInfo.get( "siteId" ) );

        if( site != null && site.getExtMemberModelId().longValue() > 0 )
        {
            memberInfo = retrieveSingleMemberAndExtInfo( memberId, site.getExtMemberModelId() );
        }

        content = disposeDefinedMemberEmailAndMessageContent( memberInfo, content );

        int addMailInfoSuccess = ServiceUtil.addSiteEmailSendInfo( site.getSiteId(), targetMail,
            subject, content, new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        // boolean sendEmailSucc = MailAndSmsUtil.sendEmail( mailHost, null,
        // sslMode, Constant.SITE_CHANNEL.DEF_PAGE_CODE, sendTo, mail,
        // mailUserName, mail, pw, subject, msg );

        if( addMailInfoSuccess == 1 )
        {
            return "1";
        }

        return "0";
    }

    public void checkMemberCoreInfo( Long memberId, String mode, Boolean checkVal )
    {
        if( "cert".equals( mode ) )
        {
            if( checkVal.booleanValue() )
            {
                memberDao.updateMemberCertStatus( memberId, Constant.COMMON.ON );
            }
            else
            {
                memberDao.updateMemberCertStatus( memberId, Constant.COMMON.OFF );
            }
        }
        else if( "bank".equals( mode ) )
        {
            if( checkVal.booleanValue() )
            {
                memberDao.updateMemberBankStatus( memberId, Constant.COMMON.ON );
            }
            else
            {
                memberDao.updateMemberBankStatus( memberId, Constant.COMMON.OFF );
            }
        }
    }

    public String addMemberCollectContent( Long cId, Long memId )
    {
        if( memberDao.queryMemCollContent( cId, memId ).longValue() > 0 )
        {
            // 已经收藏
            return "-3";
        }

        Map info = ContentService.getInstance().retrieveSingleUserDefineContent( cId,
            Integer.valueOf( 1 ) );

        memberDao.saveMemCollContent( cId, ( Long ) info.get( "modelId" ), memId );

        return "1";
    }

    public Object getMemberCollContentCountForTag( String cId, String memId )
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        return memberDao.queryMemCollContent( StringUtil.getLongValue( cId, -1 ), member
            .getMemberId() );
    }

    public String deleteMemberCollectContent( Long cId, Long memId )
    {
        memberDao.deleteMemCollContent( cId, memId );

        return "1";
    }

    public Object[] getMemberCollContentForTag( String pageSize, String pageNumber )
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        Long count = memberDao.queryMemCollContentCount( member.getMemberId() );

        pageInfo = new Page( size, count.intValue(), pn );

        result = memberDao.queryMemCollContent( member.getMemberId(), Long.valueOf( pageInfo
            .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );

        return new Object[] { result, pageInfo };
    }

    public Object[] getMemberCollContentForTag( String modelId, String pageSize, String pageNumber )
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        Long count = memberDao.queryMemCollContentCount( member.getMemberId(), StringUtil
            .getLongValue( modelId, -1 ) );

        pageInfo = new Page( size, count.intValue(), pn );

        result = memberDao.queryMemCollContent( member.getMemberId(), StringUtil.getLongValue(
            modelId, -1 ), Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageInfo
            .getPageSize() ) );

        return new Object[] { result, pageInfo };
    }

    public Object[] getMemberHistoryContentForTag( String pageSize, String pageNumber )
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        Long count = memberDao.queryMemHisContentCount( member.getMemberId() );

        pageInfo = new Page( size, count.intValue(), pn );

        result = memberDao.queryMemHisContent( member.getMemberId(), Long.valueOf( pageInfo
            .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );

        return new Object[] { result, pageInfo };
    }

    public Object[] getMemberHistoryContentForTag( String modelId, String pageSize,
        String pageNumber )
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        Long count = memberDao.queryMemHisContentCount( member.getMemberId(), StringUtil
            .getLongValue( modelId, -1 ) );

        pageInfo = new Page( size, count.intValue(), pn );

        result = memberDao.queryMemHisContent( member.getMemberId(), StringUtil.getLongValue(
            modelId, -1 ), Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageInfo
            .getPageSize() ) );

        return new Object[] { result, pageInfo };
    }

    public void changeMemberStatus( List idList, String mode )
    {
        if( idList == null )
        {
            return;
        }

        Long memberId = null;

        for ( int i = 0; i < idList.size(); i++ )
        {
            if( idList.get( i ) instanceof Long )
            {
                memberId = ( Long ) idList.get( i );
            }
            else
            {
                memberId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );
            }

            if( memberId.longValue() < 0 )
            {
                continue;
            }

            if( "open".equals( mode ) )
            {
                memberDao.updateMemberUseStatus( memberId, Constant.COMMON.ON );
            }
            else if( "close".equals( mode ) )
            {
                memberDao.updateMemberUseStatus( memberId, Constant.COMMON.OFF );
            }

        }
    }

    public UpdateState addMemberBasicInfo( SiteGroupBean site, Member mem, DataModelBean model,
        List filedBeanList, ModelPersistenceMySqlCodeBean sqlCodeBean, Map requestParams )
    {
        UpdateState us = null;

        try
        {
            mysqlEngine.beginTransaction();

            // 默认等级
            mem.setMemLevel( site.getMemberDefLv() );

            // 默认积分

            mem.setScore( site.getMemberDefSc() );

            us = memberDao.save( mem );

            if( us.haveKey() && model != null && filedBeanList != null && sqlCodeBean != null )
            {
                ModelFiledInfoBean bean = null;

                List userDefineParamList = new ArrayList();

                String reUrl = null;

                Object val = null;

                for ( int j = 0; j < filedBeanList.size(); j++ )
                {
                    bean = ( ModelFiledInfoBean ) filedBeanList.get( j );
                    // 需要引入filed元数据来对不同类型字段进行对应处理

                    val = ServiceUtil
                        .disposeDataModelFiledFromWeb( bean, requestParams, null, true );

                    userDefineParamList.add( val );

                    // 单图水印处理
                    if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue()
                        && Constant.COMMON.ON.equals( bean.getNeedMark() ) )
                    {
                        // 水印处理
                        reUrl = ServiceUtil.getImageReUrl( ( String ) val );

                        // 已经加过水印的不需要再增加
                        if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                        {
                            if( ServiceUtil.disposeImageMark(
                                ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                                    .getCurrentLoginSiteInfo(), reUrl, Integer.valueOf( ServiceUtil
                                    .getImageW( ( String ) val ) ), Integer.valueOf( ServiceUtil
                                    .getImageH( ( String ) val ) ) ) )
                            {
                                // 成功加水印则更新
                                resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                            }
                        }
                    }

                }

                // 添加ID到最后位置
                userDefineParamList.add( Long.valueOf( us.getKey() ) );

                contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                    userDefineParamList.toArray() );

                // 默认会员组
                Long defRoleId = site.getMemberDefRoleId();

                if( defRoleId.longValue() > 0 )
                {
                    securityDao.saveMemberRelateRole( Long.valueOf( us.getKey() ), defRoleId );
                }
                else
                {
                    // 传入的会员组

                    long rid = StringUtil.getLongValue( ( String ) requestParams.get( "mRoleId" ),
                        -1 );

                    if( rid > 0 )
                    {
                        securityDao.saveMemberRelateRole( Long.valueOf( us.getKey() ), rid );
                    }
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }

        return us;
    }

    public void editMemberBasicInfo( MemberBean mem, DataModelBean model, List filedBeanList,
        ModelPersistenceMySqlCodeBean sqlCodeBean, Map requestParams )
    {

        try
        {
            mysqlEngine.beginTransaction();

            memberDao.updateMemberBasic( requestParams );

            changeMemberHeadPhoto( requestParams );

            changeCertPhoto( requestParams );

            if( "true".equals( requestParams.get( "sysMailChange" ) ) )
            {
                memberDao.updateMailCheckStatus( mem.getMemberId(), Constant.COMMON.OFF );
            }

            if( model != null && filedBeanList != null && sqlCodeBean != null )
            {

                Map currentObj = memberDao.queryMemberInfoById( mem.getMemberId(), model,
                    sqlCodeBean );

                Integer count = contentDao.queryUserDefinedContentExist( model, mem.getMemberId() );

                ModelFiledInfoBean bean = null;

                List userDefineParamList = new ArrayList();

                String reUrl = null;

                Object val = null;

                for ( int j = 0; j < filedBeanList.size(); j++ )
                {
                    bean = ( ModelFiledInfoBean ) filedBeanList.get( j );
                    // 需要引入filed元数据来对不同类型字段进行对应处理

                    val = ServiceUtil
                        .disposeDataModelFiledFromWeb( bean, requestParams, null, true );

                    if( !requestParams.containsKey( bean.getFieldSign() ) )
                    {
                        val = currentObj.get( bean.getFieldSign() );
                    }

                    userDefineParamList.add( val );

                    // 单图水印处理
                    if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue()
                        && Constant.COMMON.ON.equals( bean.getNeedMark() ) )
                    {
                        // 水印处理
                        reUrl = ServiceUtil.getImageReUrl( ( String ) val );

                        // 已经加过水印的不需要再增加
                        if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                        {
                            if( ServiceUtil.disposeImageMark(
                                ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                                    .getCurrentLoginSiteInfo(), reUrl, Integer.valueOf( ServiceUtil
                                    .getImageW( ( String ) val ) ), Integer.valueOf( ServiceUtil
                                    .getImageH( ( String ) val ) ) ) )
                            {
                                // 成功加水印则更新
                                resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                            }
                        }
                    }

                }

                // 添加ID到最后位置
                userDefineParamList.add( mem.getMemberId() );

                if( count.intValue() == 1 )
                {
                    // 已有数据,更新模式
                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getUpdateSql(),
                        userDefineParamList.toArray() );

                }
                else if( count.intValue() == 0 )
                {
                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }

        removeMemberInfoCache( mem.getMemberId() );
    }

    public boolean checkMemberUserName( String name )
    {
        Map memberInfo = memberDao.querySingleMemberInfoByName( name );

        if( memberInfo.isEmpty() )
        {
            memberInfo = memberDao.querySingleMemberInfoByPhone( name );

            if( memberInfo.isEmpty() )
            {
                return false;
            }
        }

        return true;
    }

    public boolean checkMemberUserMail( String mail )
    {
        Map memberInfo = memberDao.querySingleMemberInfoByMail( mail );

        if( memberInfo.isEmpty() )
        {
            return false;
        }

        return true;
    }

    public MemberBean retrieveSingleMemberBean( Long memId )
    {
        return memberDao.querySingleMemberBeanById( memId );
    }

    public MemberBean retrieveSingleMemberBean( String memName )
    {
        return memberDao.queryMemberBeanByName( memName );
    }

    public MemberBean retrieveSingleMemberBeanByEMail( String mail )
    {
        return memberDao.queryMemberBeanByMail( mail );
    }

    public Map retrieveSingleMemberAndExtInfo( Long memId, Long extModeId )
    {
        String key = "retrieveSingleMemberAndExtInfo:" + memId;

        Map result = ( Map ) mamberInfoCache.getEntry( key );

        if( result == null )
        {
            // 扩展模型信息
            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( extModeId );

            DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( extModeId );

            result = memberDao.queryMemberInfoById( memId, model, sqlCodeBean );

            result.put( "PREVLOGINDT", DateAndTimeUtil.formatTimestamp( ( Timestamp ) result
                .get( "PREVLOGINDT" ), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

            result.put( "REGDT", DateAndTimeUtil.formatTimestamp( ( Timestamp ) result
                .get( "REGDT" ), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

            result.put( "CURRLOGINDT", DateAndTimeUtil.formatTimestamp( ( Timestamp ) result
                .get( "CURRLOGINDT" ), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

            mamberInfoCache.putEntry( key, result );
        }

        return result;

    }

    public Long retrieveMemberActInfoCount( Map memberInfo, int actFlag )
    {
        if( memberInfo == null )
        {
            return Long.valueOf( 0 );
        }

        Long memId = ( Long ) memberInfo.get( "memberId" );

        Long siteId = ( Long ) memberInfo.get( "siteId" );

        String memberName = ( String ) memberInfo.get( "memberName" );

        if( actFlag == 1 )
        {
            // 评论总数
            return memberDao.queryActInfoCommentCount( memId, siteId );
        }

        if( actFlag == 2 )
        {
            // 消息总数
            return memberDao.queryActInfoMessageCount( memId );
        }

        if( actFlag == 3 )
        {
            // 留言总数
            return memberDao.queryActInfoGuestbookCount( memId, siteId );
        }

        if( actFlag == 4 )
        {
            // 投稿总数
            return memberDao.queryActInfoPushContentCount( memberName, siteId );
        }

        return Long.valueOf( 0 );
    }

    public MemberBean getSingleMemberBeanForTag( String memId )
    {
        return memberDao.querySingleMemberBeanById( Long.valueOf( StringUtil.getLongValue( memId,
            -1 ) ) );
    }

    public Map retrieveSingleMemberBeanByName( String name )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return memberDao.queryMemeberByName( site.getSiteId(), name );
    }

    public IUser obtainUser( String name )
    {
        Map params = memberDao.querySingleMemberInfoByName( name );

        if( params.isEmpty() )
        {
            return null;
        }

        List roleList = securityDao.queryMemberHaveSecurityRoleByUserId( ( Long ) params
            .get( "memberId" ) );

        MemberLoginUser user = new MemberLoginUser();

        user.setName( ( String ) params.get( "memberName" ) );
        user.setPassword( ( String ) params.get( "password" ) );
        user.setUserId( ( Long ) params.get( "memberId" ) );
        user.setRoleIdList( roleList );
        user.setStatus( ( Integer ) params.get( "useStatus" ) );

        return user;
    }

    public IUser obtainUserByPhone( String phone )
    {
        Map params = memberDao.querySingleMemberInfoByPhone( phone );

        if( params.isEmpty() )
        {
            return null;
        }

        List roleList = securityDao.queryMemberHaveSecurityRoleByUserId( ( Long ) params
            .get( "memberId" ) );

        MemberLoginUser user = new MemberLoginUser();

        user.setName( ( String ) params.get( "memberName" ) );
        user.setPassword( ( String ) params.get( "password" ) );
        user.setUserId( ( Long ) params.get( "memberId" ) );
        user.setRoleIdList( roleList );
        user.setStatus( ( Integer ) params.get( "useStatus" ) );

        return user;
    }

    public IUser obtainUserByEmail( String email )
    {
        Map params = memberDao.querySingleMemberInfoByMail( email );

        if( params.isEmpty() )
        {
            return null;
        }

        List roleList = securityDao.queryMemberHaveSecurityRoleByUserId( ( Long ) params
            .get( "memberId" ) );

        MemberLoginUser user = new MemberLoginUser();

        user.setName( ( String ) params.get( "memberName" ) );
        user.setPassword( ( String ) params.get( "password" ) );
        user.setUserId( ( Long ) params.get( "memberId" ) );
        user.setRoleIdList( roleList );
        user.setStatus( ( Integer ) params.get( "useStatus" ) );

        return user;
    }

    public List retrieveMemeberRole( Long memId )
    {
        List roleList = securityDao.queryMemberHaveSecurityRoleByUserId( memId );

        return roleList;
    }

    public void updateMemberLoginTrace( String ip, Timestamp loginDT, Long memberId )
    {
        Map memberInfo = memberDao.querySingleMemberInfo( memberId );

        String prevLoginIp = ( String ) memberInfo.get( "currLoginIp" );

        String area = StringUtil.isStringNotNull( prevLoginIp ) ? IPSeeker.getInstance()
            .getAddress( prevLoginIp ) : "";

        Timestamp prevLoginDT = ( Timestamp ) memberInfo.get( "currLoginDT" );

        memberDao.updateMemberLoginTrace( ip, loginDT, prevLoginIp, area, prevLoginDT, memberId );
    }

    public int changeMemberLoginPassword( String oldPw, String newPw, String checkNewPw )
    {
        if( StringUtil.isStringNull( oldPw ) || StringUtil.isStringNull( newPw )
            || StringUtil.isStringNull( checkNewPw ) )
        {
            // 信息缺少
            return -2;
        }

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        Long memberId = ( Long ) auth.getIdentity();

        try
        {
            mysqlEngine.beginTransaction();

            Map member = memberDao.querySingleMemberInfo( memberId );

            if( member.isEmpty() )
            {
                // 信息缺少
                return -2;
            }

            if( !PasswordUtility.match( oldPw, ( String ) member.get( "password" ) ) )
            {
                // 原密码不匹配
                return -3;
            }

            if( !newPw.equals( checkNewPw ) )
            {
                // 现密码重复不匹配
                return -4;
            }

            memberDao.updateMemberPW( memberId, PasswordUtility.encrypt( newPw ) );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        removeMemberInfoCache( memberId );

        return 1;
    }

    public Object[] validateMemberEmail( Map params )
    {
        String key = ( String ) params.get( "key" );

        // 解密
        byte[] decryptResult = EncodeOne.decryptAES( EncodeOne.parseHexStr2Byte( key ),
            Constant.MEMBER.SALT );

        String backKey = new String( decryptResult );

        if( StringUtil.isStringNull( backKey ) )
        {
            return new Object[] { Integer.valueOf( -1 ), null, null };
        }

        String[] keyArray = backKey.split( "\\|" );

        if( keyArray.length < 5 )
        {
            return new Object[] { Integer.valueOf( -1 ), null, null };
        }

        Long siteId = Long.valueOf( StringUtil.getLongValue( keyArray[4], -1 ) );

        String sendDay = keyArray[3];

        String currDay = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        int dayInterval = DateAndTimeUtil.getDayInterval( currDay, sendDay,
            DateAndTimeUtil.DEAULT_FORMAT_YMD );

        // 超过一天,验证失效
        if( dayInterval > 1 )
        {
            return new Object[] { Integer.valueOf( -2 ), null, siteId };
        }

        Long memberId = Long.valueOf( StringUtil.getLongValue( keyArray[0], -1 ) );

        Map memberInfo = memberDao.querySingleMemberInfo( memberId );

        if( memberInfo.isEmpty() )
        {
            return new Object[] { Integer.valueOf( -3 ), null, siteId };
        }

        // 2015 改动：需要演示自改动邮件，特屏蔽以下代码
        Integer isTrueMail = ( Integer ) memberInfo.get( "isTrueEmail" );

        if( Constant.COMMON.ON.equals( isTrueMail ) )
        {
            return new Object[] { Integer.valueOf( -4 ), memberInfo, siteId };
        }

        Long count = memberDao.queryMemberLostEmailTraceCount( key );

        if( count.longValue() > 0 )
        {
            // 已经失效
            return new Object[] { Integer.valueOf( -2 ), null, siteId };
        }
        else
        {
            memberDao.updateMailCheckStatus( memberId, Constant.COMMON.ON );

            memberDao
                .saveMemberLostEmailTrace( key, new Date( DateAndTimeUtil.clusterTimeMillis() ) );

            memberInfo.put( "isTrueEmail", Constant.COMMON.ON );

            removeMemberInfoCache( memberId );

            return new Object[] { Integer.valueOf( 1 ), memberInfo, siteId };
        }

    }

    public String sendMemberValiEmail( SiteGroupBean site, Map params )
    {
        if( site == null )
        {
            return "0";
        }

        MemberBean loginMember = ( MemberBean ) SecuritySessionKeeper.getSecuritySession()
            .getMember();

        if( loginMember == null )
        {
            return "0";
        }

        String targetMail = ( String ) params.get( "email" );

        targetMail = SystemSafeCharUtil.decodeFromWeb( targetMail );

        if( StringUtil.isStringNull( targetMail ) )
        {
            // 邮件格式不正确
            return "-2";
        }

        Long memberId = ( Long ) loginMember.getMemberId();

        Map memberInfo = memberDao.querySingleMemberInfo( memberId );

        String existMail = ( String ) memberInfo.get( "email" );

        Timestamp regDt = ( Timestamp ) memberInfo.get( "regDT" );

        if( StringUtil.isStringNotNull( existMail ) && !existMail.equals( targetMail ) )
        {
            existMail = targetMail;

            // 若填入了与注册时不一样的邮件地址,那说明出现了新的邮件地址,需要验证是否被注册
            // email检查重复
            boolean mailExist = checkMemberUserMail( existMail );

            if( mailExist )
            {
                // 此邮箱已经被注册
                return "-3";
            }

            // 立即使用的新的邮件地址
            memberDao.updateMemberEMail( memberId, existMail );

            memberDao.updateMailCheckStatus( memberId, Constant.COMMON.OFF );
        }
        else
        {

            if( Constant.COMMON.ON.equals( memberInfo.get( "isTrueEmail" ) )
                && existMail.equals( targetMail ) )
            {
                // 当前邮箱已经验证
                return "-4";
            }

            existMail = targetMail;

            // 若填入了与注册时不一样的邮件地址,那说明出现了新的邮件地址,需要验证是否被注册
            // email检查重复
            // boolean mailExist = checkMemberUserMail( existMail );
            //
            // if( mailExist )
            // {
            // // 此邮箱已经被注册
            // return "-3";
            // }

            // 立即使用的新的邮件地址
            memberDao.updateMemberEMail( memberId, existMail );

        }

        site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache.getEntry( site
            .getSiteId() );

        String subject = "注册邮件确认";

        String currDay = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        String content = memberId + "|" + regDt + "|" + existMail + "|" + currDay + "|"
            + site.getSiteId() + "|" + DateAndTimeUtil.clusterTimeMillis();

        // 加密
        byte[] encryptResult = EncodeOne.encryptAES( content, Constant.MEMBER.SALT );

        String byt = EncodeOne.encode16( encryptResult ).toLowerCase();

        String msg = disposeDefinedMemberEmailAndMessageContent( memberInfo, site.getRegMailText() );

        // callback
        String callbakUrl = site.getSiteUrl() + "member/memberEmailActive.do?key=" + byt;

        msg = StringUtil.replaceString( msg, "${sysRegMailcallback}", callbakUrl, false, false );

        // siteName
        msg = StringUtil.replaceString( msg, "${siteName}", site.getSiteName(), false, false );

        // siteUrl
        msg = StringUtil.replaceString( msg, "${siteUrl}", site.getSiteUrl(), false, false );

        int addMailInfoSuccess = ServiceUtil.addSiteEmailSendInfo( site.getSiteId(), existMail,
            subject, msg, new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        // boolean sendEmailSucc = MailAndSmsUtil.sendEmail( mailHost, null,
        // sslMode, Constant.SITE_CHANNEL.DEF_PAGE_CODE, sendTo, mail,
        // mailUserName, mail, pw, subject, msg );

        if( addMailInfoSuccess == 1 )
        {
            return "1";
        }

        return "0";
    }

    public Object[] sendResetMemberPasswordMail( String targetMail )
    {

        if( StringUtil.isStringNull( targetMail ) )
        {
            return new Object[] { "0", null };
        }

        // 2015 不再验证会员名，只发送邮件
        // Map memberInfo = memberDao.querySingleMemberInfoByName( memberName );
        //
        // if( memberInfo.isEmpty() )
        // {
        // memberInfo = memberDao.querySingleMemberInfoByPhone( memberName );
        // }
        //
        // if( memberInfo.isEmpty() )
        // {
        // return "-2";
        // }

        // String regMail = ( String ) memberInfo.get( "email" );
        //
        // if( !targetMail.equals( regMail ) )
        // {
        // // 邮箱非注册邮箱
        // return "-3";
        // }

        Map memberInfo = memberDao.querySingleMemberInfoByMail( targetMail );

        if( memberInfo.isEmpty() )
        {
            // 邮箱对应的会员不存在
            return new Object[] { "-2", null };
        }

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( memberInfo.get( "siteId" ) );

        String subject = "重置登录密码";

        String currDay = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        String content = memberInfo.get( "memberId" ) + "|" + memberInfo.get( "regDT" ) + "|"
            + targetMail + "|" + currDay + "|" + site.getSiteId() + "|"
            + DateAndTimeUtil.clusterTimeMillis();

        // 加密
        byte[] encryptResult = EncodeOne.encryptAES( content, Constant.MEMBER.SALT );

        String byt = EncodeOne.encode16( encryptResult ).toLowerCase();

        String msg = disposeDefinedMemberEmailAndMessageContent( memberInfo, site.getResetPwText() );

        // callback
        String callbakUrl = site.getSiteUrl() + "member/checkResetReq.do?key=" + byt;

        msg = StringUtil.replaceString( msg, "${sysResetPWcallback}", callbakUrl, false, false );

        // siteName
        msg = StringUtil.replaceString( msg, "${siteName}", site.getSiteName(), false, false );

        // siteUrl
        msg = StringUtil.replaceString( msg, "${siteUrl}", site.getSiteUrl(), false, false );

        int addMailInfoSuccess = ServiceUtil.addSiteEmailSendInfo( site.getSiteId(), targetMail,
            subject, msg, new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        // boolean sendEmailSucc = MailAndSmsUtil.sendEmail( mailHost, null,
        // sslMode, Constant.SITE_CHANNEL.DEF_PAGE_CODE, sendTo, mail,
        // mailUserName, mail, pw, subject, msg );

        if( addMailInfoSuccess == 1 )
        {
            return new Object[] { "1", memberInfo };
        }

        return new Object[] { "0", null };
    }

    public String checkResetMemberKey( String[] keyValArray, String key )
    {
        if( keyValArray == null || keyValArray.length < 5 )
        {
            return "0";
        }

        Long memberId = Long.valueOf( StringUtil.getLongValue( keyValArray[0], -1 ) );

        Map memberInfo = memberDao.querySingleMemberInfo( memberId );

        if( memberInfo.isEmpty() )
        {
            return "-2";
        }

        // 注册时间检查
        Timestamp keyRegDt = DateAndTimeUtil.getTimestamp( keyValArray[1],
            DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

        if( !keyRegDt.equals( memberInfo.get( "regDT" ) ) )
        {
            return "-3";
        }

        // 邮箱地址检查
        String keyEmail = keyValArray[2];

        String email = ( String ) memberInfo.get( "email" );

        if( !keyEmail.equals( email ) )
        {
            return "-4";
        }

        // 超过一天,验证失效
        String currDay = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        int dayInterval = DateAndTimeUtil.getDayInterval( currDay, keyValArray[3],
            DateAndTimeUtil.DEAULT_FORMAT_YMD );

        if( dayInterval > 1 )
        {
            return "-5";
        }

        Long count = memberDao.queryMemberLostEmailTraceCount( key );

        if( count.longValue() > 0 )
        {
            // 已经失效
            return "-5";
        }

        return "1";
    }

    public String resetMemberPassword( Map params )
    {
        String newPw = ( String ) params.get( "newPw" );
        String checkNewPw = ( String ) params.get( "checkNewPw" );

        String key = ( String ) params.get( "key" );

        String idKey = ( String ) params.get( "idKey" );
        String regKey = ( String ) params.get( "regKey" );
        String mailKey = ( String ) params.get( "mailKey" );

        if( StringUtil.isStringNull( newPw ) || StringUtil.isStringNull( checkNewPw )
            || StringUtil.isStringNull( key ) || StringUtil.isStringNull( idKey )
            || StringUtil.isStringNull( regKey ) || StringUtil.isStringNull( mailKey ) )
        {
            return "0";
        }

        byte[] decryptResult = EncodeOne.decryptAES( EncodeOne.parseHexStr2Byte( idKey ),
            MemberUtilTag.getSalt( "E" ) );

        String val = new String( decryptResult );

        String id = val;

        decryptResult = EncodeOne.decryptAES( EncodeOne.parseHexStr2Byte( regKey ), MemberUtilTag
            .getSalt( "B" ) );

        val = new String( decryptResult );

        String regDate = val;

        decryptResult = EncodeOne.decryptAES( EncodeOne.parseHexStr2Byte( mailKey ), MemberUtilTag
            .getSalt( "C" ) );

        val = new String( decryptResult );

        String keyMail = val;

        Long memberId = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

        Map member = memberDao.querySingleMemberInfo( memberId );

        if( member.isEmpty() )
        {
            return "-2";
        }

        Timestamp regDT = DateAndTimeUtil.getTimestamp( regDate,
            DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

        if( !regDT.equals( member.get( "regDT" ) ) )
        {
            return "-3";
        }

        if( !keyMail.equals( member.get( "email" ) ) )
        {
            return "-4";
        }

        if( !newPw.equals( checkNewPw ) )
        {
            return "-5";
        }

        Long count = memberDao.queryMemberLostEmailTraceCount( key );

        if( count.longValue() > 0 )
        {
            // 已经失效
            return "-6";
        }

        UpdateState us = memberDao.updateMemberPW( memberId, PasswordUtility.encrypt( newPw ) );

        if( us.getRow() > 0 )
        {
            memberDao
                .saveMemberLostEmailTrace( key, new Date( DateAndTimeUtil.clusterTimeMillis() ) );

            removeMemberInfoCache( memberId );

            return "1";
        }

        return "0";
    }

    public String[] decryptKey( String key )
    {
        // 解密
        byte[] decryptResult = EncodeOne.decryptAES( EncodeOne.parseHexStr2Byte( key ),
            Constant.MEMBER.SALT );

        String backKey = new String( decryptResult );

        if( StringUtil.isStringNull( backKey ) )
        {
            return null;
        }

        String[] keyArray = backKey.split( "\\|" );

        return keyArray;
    }

    public int changeMemberHeadPhoto( Map params )
    {
        Long headPhotoResIdVar = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "headPhoto" ), -1 ) );

        String headPhoto = ServiceUtil.disposeSingleImageInfo( headPhotoResIdVar );

        if( StringUtil.isStringNull( headPhoto ) )
        {
            return 0;
        }

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        Long memberId = ( Long ) auth.getIdentity();

        UpdateState us = memberDao.updateMemberHeadPhoto( headPhoto, memberId );

        if( us.getRow() > 0 )
        {
            removeMemberInfoCache( memberId );

            return 1;
        }

        return 0;
    }

    public int changeCertPhoto( Map params )
    {
        Long photoResIdVar = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "certPhotoP" ), -1 ) );

        String certPhotoP = ServiceUtil.disposeSingleImageInfo( photoResIdVar );

        photoResIdVar = Long.valueOf( StringUtil.getLongValue(
            ( String ) params.get( "certPhotoR" ), -1 ) );

        String certPhotoR = ServiceUtil.disposeSingleImageInfo( photoResIdVar );

        if( StringUtil.isStringNull( certPhotoP ) && StringUtil.isStringNull( certPhotoR ) )
        {
            return 0;
        }

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        Long memberId = ( Long ) auth.getIdentity();

        UpdateState us = memberDao.updateMemberCertPhoto( certPhotoP, certPhotoR, memberId );

        if( us.getRow() > 0 )
        {
            removeMemberInfoCache( memberId );

            return 1;
        }

        return 0;
    }

    public Map retrieveThirdLoginInfo( String loginType, String openID )
    {
        return memberDao.queryThirdLoginInfo( loginType, openID );
    }

    public void addThirdLoginInfo( Long memberId, String loginType, String openID )
    {
        memberDao.saveThirdLoginInfo( memberId, loginType, openID );
    }

    public void addOneCountForMemberLoginSuccess( Long memberId )
    {
        memberDao.updateMemberLoginSuccessCount( memberId );
    }

    public String getMemberRegTypeForTag( String memId )
    {
        Long memberId = Long.valueOf( StringUtil.getLongValue( memId, -1 ) );

        Map thirdRegInfo = memberDao.querySingleMemberThirdRegInfo( memberId );

        if( thirdRegInfo.isEmpty() )
        {
            return "普通注册帐号";
        }

        if( "qq".equals( thirdRegInfo.get( "loginType" ) ) )
        {
            return "关联QQ帐号";
        }

        if( "weibo".equals( thirdRegInfo.get( "loginType" ) ) )
        {
            return "关联微博帐号";
        }

        return "未知类型";

    }

    public void changeMemberScore( String mode, Integer score, List memIdList )
    {
        if( memIdList == null )
        {
            return;
        }

        Long memberId = null;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < memIdList.size(); i++ )
            {
                memberId = Long.valueOf( ( memIdList.get( i ) ).toString() );

                if( memberId.longValue() < 1 )
                {
                    continue;
                }

                if( "a".equals( mode ) )
                {
                    // 增加积分
                    memberDao.updateMemberScoreAddVal( memberId, score );

                }
                else if( "d".equals( mode ) )
                {
                    // 减少积分
                    memberDao.updateMemberScoreReduceVal( memberId, score );
                }

                // 等级变换
                disposeMemberRank( memberId );

                removeMemberInfoCache( memberId );
            }
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deleteMember( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long memberId = null;

            Map memberInfo = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                memberId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( memberId.longValue() < 0 )
                {
                    continue;
                }

                memberInfo = memberDao.querySingleMemberInfo( memberId );

                if( !memberInfo.isEmpty() )
                {

                    // 2014-12 : 不再使用删除限制，若业务需要可自己加上
                    // currLoginDT = ( Timestamp ) memberInfo.get( "currLoginDT"
                    // );
                    //
                    // String currDay = DateAndTimeUtil
                    // .getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD
                    // );
                    //
                    // int dayInterval = DateAndTimeUtil.getDayInterval(
                    // currDay,
                    // DateAndTimeUtil.formatTimestamp( currLoginDT,
                    // DateAndTimeUtil.DEAULT_FORMAT_YMD ),
                    // DateAndTimeUtil.DEAULT_FORMAT_YMD );
                    //
                    // // 90天内登陆过
                    // if( dayInterval < 90 )
                    // {
                    // continue;
                    // }

                    // 删除会员信息
                    memberDao.deleteMemberById( memberId );

                    // 删除会员组关联
                    securityDao.deleteMemberRoleRealte( memberId );

                    // 删除第三方登录信息
                    memberDao.deleteMemberThirdRegInfo( memberId );

                    // 删除站内信
                    memberDao.deleteMessage( memberId );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void addMemberLoginTrace( Long siteId, String userName, String ip, Integer ls )
    {
        memberDao.saveMemberLoginTrace( siteId, ip, userName, new Timestamp( DateAndTimeUtil
            .clusterTimeMillis() ), ls );
    }

    public Object getMemberLoginTraceTag( String userName, String pn, String size )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        List result = null;

        if( StringUtil.isStringNotNull( userName ) )
        {
            count = memberDao.queryMemberLoginTraceCount( userName.trim() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = memberDao.queryMemberLoginTrace( userName.trim(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else
        {
            count = memberDao.queryMemberLoginTraceCount( site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = memberDao.queryMemberLoginTrace( site.getSiteId(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );
        }

        return new Object[] { result, pageInfo };
    }

    // public List getClientCommandInfoForTag()
    // {
    // SystemRuntimeConfig runtime = SystemConfiguration.getInstance()
    // .getSystemConfig();
    //
    // Iterator iter = runtime.getModules().entrySet().iterator();
    //
    // Entry entry = null;
    //        
    // Map flowMap = null;
    //        
    // Iterator iterFlow = null;
    //
    // while ( iter.hasNext() )
    // {
    // entry = ( Entry ) iter.next();
    //            
    // flowMap = ( Map ) entry.getValue();
    //            
    //            
    // }
    // }

    /**
     * 以下为前台会员Tag方法
     */
    public Object[] getMemberContentForTag( String pageSize, String pageNumber, String state )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        if( "".equals( state ) )
        {
            Long count = memberDao.queryMemeberContentCountBySiteId( site.getSiteId(), member
                .getMemberName() );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberContentMainInfoBySiteId( site.getSiteId(), member
                .getMemberName(), Long.valueOf( pageInfo.getFirstResult() ), Integer
                .valueOf( pageInfo.getPageSize() ) );
        }
        else
        {
            Integer cs = Integer.valueOf( StringUtil.getIntValue( state, 1 ) );

            Long count = memberDao.queryMemeberContentCountBySiteIdAndState( site.getSiteId(),
                member.getMemberName(), cs );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberContentMainInfoBySiteIdAndState( site.getSiteId(),
                member.getMemberName(), cs, Long.valueOf( pageInfo.getFirstResult() ), Integer
                    .valueOf( pageInfo.getPageSize() ) );
        }

        return new Object[] { result, pageInfo };
    }

    public Map getMemberSingleContentForTag( String cid )
    {
        Long contentId = Long.valueOf( StringUtil.getLongValue( cid, -1 ) );

        Map info = memberDao.queryMemeberSIngleContentMainInfoById( contentId );

        Long classId = ( Long ) info.get( "classId" );

        ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

        if( classBean == null )
        {
            return null;
        }

        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( classBean
            .getContentType() );

        if( modelBean == null )
        {
            return null;
        }

        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( classBean.getContentType() );

        info = contentDao.querySingleUserDefineContent( sqlCodeBean,
            modelBean.getRelateTableName(), contentId );

        return info;

    }

    /**
     * 删除会员内容，只可删除草稿，发布后的内容由管理员维护，会员无权管理
     * 
     * @param modelId
     * @param idList
     */
    public void deleteContentForMember( List idList )
    {

        contentService.deleteSystemAndUserDefineContentForMember( idList );
    }

    public void deleteMessageForMember( List idList )
    {
        if( idList == null )
        {
            return;
        }

        MemberBean memberBean = ( MemberBean ) SecuritySessionKeeper.getSecuritySession()
            .getMember();

        if( memberBean == null || memberBean.getMemberId().longValue() < 0 )
        {
            return;
        }

        Long msgId = null;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < idList.size(); i++ )
            {
                msgId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( msgId.longValue() < 0 )
                {
                    continue;
                }

                memberDao.deleteMessageById( msgId, memberBean.getMemberId() );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deleteCommentForMember( List idList )
    {
        if( idList == null )
        {
            return;
        }

        MemberBean memberBean = ( MemberBean ) SecuritySessionKeeper.getSecuritySession()
            .getMember();

        if( memberBean == null || memberBean.getMemberId().longValue() < 0 )
        {
            return;
        }

        Long commId = null;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < idList.size(); i++ )
            {
                commId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( commId.longValue() < 0 )
                {
                    continue;
                }

                memberDao.deleteCommentById( commId, memberBean.getMemberId() );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deleteMessageTemplate( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long mtId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                mtId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( mtId.longValue() < 0 )
                {
                    continue;
                }

                memberDao.deleteMessageTemplate( mtId );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deleteMessageTemplateParam( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long mtpId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                mtpId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( mtpId.longValue() < 0 )
                {
                    continue;
                }

                memberDao.deleteMessageTemplateParam( mtpId );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public Object[] getMemberCommentForTag( String pageSize, String pageNumber, String state )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        if( "".equals( state ) )
        {
            Long count = memberDao.queryMemeberCommentCountBySiteId( site.getSiteId(), member
                .getMemberId() );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao
                .queryMemeberCommentBySiteId( site.getSiteId(), member.getMemberId(), Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else
        {
            Integer cs = Integer.valueOf( StringUtil.getIntValue( state, 1 ) );

            Long count = memberDao.queryMemeberCommentCountBySiteIdAndCensor( site.getSiteId(),
                member.getMemberId(), cs );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberCommentBySiteIdAndCensor( site.getSiteId(), member
                .getMemberId(), cs, Long.valueOf( pageInfo.getFirstResult() ), Integer
                .valueOf( pageInfo.getPageSize() ) );
        }

        return new Object[] { result, pageInfo };
    }

    public Object[] getMemberGbForTag( String pageSize, String pageNumber, String state )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        if( "".equals( state ) )
        {
            Long count = memberDao.queryMemeberGbCountBySiteId( site.getSiteId(), member
                .getMemberId() );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberGbBySiteId( site.getSiteId(), member.getMemberId(), Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else
        {
            Integer cs = Integer.valueOf( StringUtil.getIntValue( state, 1 ) );

            Long count = memberDao.queryMemeberGbCountBySiteIdAndCensor( site.getSiteId(), member
                .getMemberId(), cs );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberGbBySiteIdAndCensor( site.getSiteId(), member
                .getMemberId(), cs, Long.valueOf( pageInfo.getFirstResult() ), Integer
                .valueOf( pageInfo.getPageSize() ) );
        }

        return new Object[] { result, pageInfo };
    }

    public Object[] getMemberMsgForTag( String pageSize, String pageNumber, String state )
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        MemberBean member = ( MemberBean ) session.getMember();

        if( member == null )
        {
            return null;
        }

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        if( "".equals( state ) )
        {
            Long count = memberDao.queryMemeberMsgCount( member.getMemberId() );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberMsg( member.getMemberId(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else
        {
            Integer cs = Integer.valueOf( StringUtil.getIntValue( state, 1 ) );

            Long count = memberDao.queryMemeberMsgCount( member.getMemberId(), cs );

            pageInfo = new Page( size, count.intValue(), pn );

            result = memberDao.queryMemeberMsg( member.getMemberId(), cs, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }

        return new Object[] { result, pageInfo };
    }

    public Map getMemberSingleMsgForTag( String msgId )
    {
        Long messageId = Long.valueOf( StringUtil.getLongValue( msgId, -1 ) );

        Map msg = memberDao.querySingleMemeberMsg( messageId );

        String c = ( String ) msg.get( "msgContent" );

        c = SystemSafeCharUtil.removeHTMLTag( SystemSafeCharUtil.resumeHTML( c ) );

        msg.put( "msgContent", c );

        return msg;

    }

    public void updateMemberMsgReadFlagForTag( String msgId )
    {
        Long messageId = Long.valueOf( StringUtil.getLongValue( msgId, -1 ) );

        memberDao.updateMemeberMsgReadFlag( messageId );
    }

    public Object[] getMessageTemplateForTag( String pageNumber, String pageSize )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        List result = null;

        Long count = memberDao.queryMessageTemplateCountBySiteId( site.getSiteId() );

        pageInfo = new Page( size, count.intValue(), pn );

        result = memberDao.queryMessageTemplateBySiteId( site.getSiteId(), Long.valueOf( pageInfo
            .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );

        return new Object[] { result, pageInfo };
    }

    /*
     * 二次开发演示：检查会员是否可以看到全部内容。(by： 吴涛 2015-4-20)
     */
    public Object checkMemberAccInfoForTag( String classId )
    {
        // 获取登陆者权限Session，此方法在请求线程中任何代码有效
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        // 非登陆用户强制只能看到摘要
        if( session.getAuth() == null )
        {
            return false;
        }

        // 获取当前登录的会员信息,这里没有使用，作为演示
        MemberBean member = ( MemberBean ) session.getMember();

        // 传入的栏目ID
        Long cid = Long.valueOf( StringUtil.getLongValue( classId, -1 ) );

        // 获取当前栏目对应的浏览权限规则
        MemberAccRuleBean acBean = securityService.retrieveSingleMemberAccRule( cid );

        // 若当前栏目没有设置浏览权限规则，可直接访问
        if( acBean.getAccRuleId().longValue() < 0 )
        {
            return true;
        }

        // 当前规则的有效会员组ID，需要在规则维护里配置，比如配置只有高级会员才可访问
        Set authorizationRoleIdSet = acBean.getRoleIdSet();

        // 当前登录者的角色(会员组)
        Role[] rs = session.getAuth().getUserRole();

        boolean accRoleOk = false;
        // 若会员有浏览权限所需的会员组，则允许访问
        for ( int i = 0; i < rs.length; i++ )
        {
            if( authorizationRoleIdSet.contains( ( ( Role ) rs[i] ).getRoleID() ) )
            {
                accRoleOk = true;
                break;
            }
        }

        return accRoleOk;
    }

    // WY DC
    public String appThirdLoginAndReg( String type, String openId, String hpimg, boolean relate,
        String memberName, String password, String checkPassword, String ip,
        SiteGroupBean currentSiteBean, Map params )
    {
        if( StringUtil.isStringNull( openId ) )
        {
            return "no_open_id";
        }

        if( !"weixin".equals( type ) && "qq".equals( type ) && "weibo".equals( type ) )
        {
            return "type_error";
        }

        Map existThird = memberDao.queryThirdLoginInfo( type, openId );

        if( !existThird.isEmpty() )
        {
            // login

            return "openid_login";
        }
        else if( "true".equals( params.get( "loginMode" ) ) )
        {
            return "third_direct";
        }

        if( relate )
        {
            // relate reg
            // 获取会员
            IUser memberUser = obtainUser( memberName );

            if( memberUser == null )
            {

                memberUser = obtainUserByPhone( memberName );
            }

            if( memberUser == null )
            {
                // 不存在这样的会员，用户名称和邮件都无法匹配
                return "mem_not_exist";
            }

            if( !PasswordUtility.match( password, memberUser.getPassword() ) )
            {
                // 密码不匹配
                return "pw_error";
            }

            addThirdLoginInfo( ( Long ) memberUser.getIdentity(), type, openId );

            return "relate_success";
        }
        else
        {
            // creeate reg

            Member member = new Member();

            // DCWY
            Random r = new Random();

            member.setMemberName( "mem" + r.nextInt( 100000000 ) );
            params.put( "memberName", member.getMemberName() );

            // 用户名检查重复
            boolean nameExist = checkMemberUserName( member.getMemberName() );

            if( nameExist )
            {
                // 此会员名称已被注册!
                return "mn_exist";
            }

            // 密码检查
            String pw = password;

            if( "__sys_random__".equals( pw ) )
            {
                r = new Random();

                pw = ip + r.nextInt( 100000000 );
            }
            else
            {

                String checkPW = checkPassword;

                if( StringUtil.isStringNull( pw ) || !pw.equals( checkPW ) )
                {
                    // 密码不匹配
                    return "pw_error";
                }
            }

            member.setPassword( PasswordUtility.encrypt( pw ) );

            // 强制设定手机和mail不通过,强制身份为没有验证

            member.setIsTrueEmail( Constant.COMMON.OFF );
            member.setIsTrueMan( Constant.COMMON.ON );
            member.setIsTruePass( Constant.COMMON.ON );
            member.setIsTruePhone( Constant.COMMON.OFF );

            // 注册时间
            member.setRegDt( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

            // 上次登陆IP和时间初始化
            member.setCurrLoginIp( ip );

            member.setCurrLoginDt( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

            member.setSiteId( currentSiteBean.getSiteId() );

            // 扩展模型信息
            List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( currentSiteBean
                .getExtMemberModelId() );

            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( currentSiteBean.getExtMemberModelId() );

            DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( currentSiteBean
                .getExtMemberModelId() );

            UpdateState us = addMemberBasicInfo( currentSiteBean, member, model, filedBeanList,
                sqlCodeBean, params );

            if( us.haveKey() )
            {

                // 记录第三方注册

                addThirdLoginInfo( Long.valueOf( us.getKey() ), type, openId );

                return "relate_reg_success";
            }

        }

        // removeMemberInfoCache( memberId );
        return "error";

    }

    public void censorMemberOK( Long memberId, Integer isTrueMan )
    {
        memberDao.updateMemberCertStatus( memberId, isTrueMan );
        removeMemberInfoCache( memberId );

    }

    public static void clearMemberInfoCache()
    {
        mamberInfoCache.clearAllEntry();
    }

    public static void removeMemberInfoCache( Long memberId )
    {
        String key = "retrieveSingleMemberAndExtInfo:" + memberId;

        mamberInfoCache.removeEntry( key );
    }

    public static void clearMemberActInfoCache()
    {
        mamberActInfoCache.clearAllEntry();
    }

}

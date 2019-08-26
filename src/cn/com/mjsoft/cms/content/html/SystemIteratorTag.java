package cn.com.mjsoft.cms.content.html;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.QueryDataInfoBehavior;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateModelException;

public class SystemIteratorTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 8989827541588231939L;

    private static Logger log = Logger.getLogger( SystemIteratorTag.class );

    private MetaDataService mdService = MetaDataService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    // private static final Map SYS_TABLE_SIGN_MAP = new HashMap();
    //
    // private static final Map SYS_SQL_SIGN_MAPs = new HashMap();
    //
    // static
    // {
    // SYS_TABLE_SIGN_MAP.put( "M_F_M", "model_filed_metadata" );
    // // SYS_TABLE_SIGN_MAP.put( "C_I", "comment_info" );
    //
    // String sqlQuery = "select * from model_filed_metadata mfm,
    // model_html_config mhc where mfm.dataModelId=? and
    // mfm.metaDataId=mhc.metaDataId and mhc.htmlElementId in (4,5,6) limit
    // ?,?";
    // String sqlCount = "select count(*) from model_filed_metadata mfm,
    // model_html_config mhc where mfm.dataModelId=? and
    // mfm.metaDataId=mhc.metaDataId and mhc.htmlElementId in (4,5,6)";
    //
    // SYS_SQL_SIGN_MAP.put( "SELECT_META_INFO_QUERY", sqlQuery );
    // SYS_SQL_SIGN_MAP.put( "SELECT_META_INFO_COUNT", sqlCount );
    //
    // }

    // 以下为方法级别调用
    private String service = "";// Servcice类型 类全名称

    private String method = "";// 方法名

    private String reObj = "";// 从其他途径获取对象的名称

    private String list = "false";// 迭代其他list结果

    private String common = "false";// 本次查询结果将作为其他query的list值

    // 以下为sql级别调用
    private String querySign = "";// 查询sql语句

    private String countSign = "";// 数据总数sql语句,若无此参数,则为全取模式,否则为分页模式

    private String countVar = "";// 总数量参数,如1,2,3

    private String page = "false";

    private String size = "";// 每页大小

    // 公用参数
    private String var = "";// 参数,如1,2,3,若用于sql查询，limit特殊对待,以{start}{size}符号代替值出现,如1,2,3,{start},{size}

    private String objName = "";// 页面结果对象名称

    private String single = "false";// 若为single模式,即使查询无数据,标签中的页面也不会被跳过,表示当前为单一取值模式,而非循环模式

    protected void initTag()
    {

    }

    @SuppressWarnings( "unchecked" )
    protected List returnObjectList()
    {
        List result = null;

        Object returnObj = null;

        if( "true".equals( single ) )
        {
            this.setToListSingleMode();
        }

        boolean commMode = false;

        if( "true".equals( common ) )
        {
            commMode = true;
        }

        if( "true".equals( list ) )
        {
            Object objColl = null;

            returnObj = this.pageContext.getAttribute( reObj );

            if( returnObj == null || !( returnObj instanceof List ) )
            {
                // Bean或Map获取值
                int accPo = reObj.indexOf( "." );

                if( accPo > 0 )
                {
                    objColl = this.pageContext
                        .getAttribute( StringUtil.subString( reObj, 0, accPo ) );

                    String objName = StringUtil.subString( reObj, accPo + 1 );

                    if( StringUtil.isStringNotNull( objName ) )
                    {
                        if( objColl instanceof Map )
                        {
                            Map reMap = ( Map ) objColl;

                            returnObj = reMap.get( objName );
                        }
                        else
                        {
                            returnObj = ObjectUtility.getPrivateFieldValue( objName, objColl );
                        }
                    }
                }
            }

            // 返回的若不是List,那就是对象
            if( returnObj instanceof List )
            {
                return ( List ) returnObj;
            }
            else
            {
                result = new ArrayList( 1 );
                result.add( returnObj );
                return result;
            }
        }

        // service方法执行优先
        if( StringUtil.isStringNotNull( service ) )
        {
            // 2015：已废弃调用配置形式
            // Properties QUERY_SERVICE = QueryDataInfoBehavior
            // .getSystemQueryDataService();

            // 允许全名，必须为Service!

            // String className = ( String ) QUERY_SERVICE.get( service );
            //
            // if( service.indexOf( "." ) != -1 )
            // {
            // className = service;
            // }
            // else
            // {
            // className = ( String ) QUERY_SERVICE.get( service );
            // }

            String className = service;

            // String methodName = ( String ) QUERY_SERVICE.get( method );
            //
            // if( StringUtil.isStringNull( methodName ) )
            // {
            // methodName = method;
            // }

            String methodName = method;

            if( StringUtil.isStringNull( className ) || StringUtil.isStringNull( methodName ) )
            {
                if( commMode )
                {
                    this.pageContext.setAttribute( "jtopcms:sys:query:list:" + objName, result );
                }

                return result;
            }

            Class serviceClass = null;

            Method instanceMethod = null;

            Method targetMethod = null;

            Object serviceInstance = null;

            // 参数处理
            Class[] paramFlag = null;

            Object[] param = null;

            if( StringUtil.isStringNotNull( var ) )
            {
                int paramSize = StringUtil.getRepeatCharLength( var, "," ) + 1;

                List paramList = StringUtil.changeStringToList( var, "," );

                paramFlag = new Class[paramSize];
                param = new Object[paramSize];

                String val = null;
                for ( int i = 0; i < paramSize; i++ )
                {
                    paramFlag[i] = String.class;

                    if( i < paramList.size() )
                    {
                        val = ( String ) paramList.get( i );
                    }
                    else
                    {
                        val = "";
                    }

                    param[i] = val;

                }
            }

            try
            {
                serviceClass = ObjectUtility.getClassInstance( className );

                // 强制使用单根service类，不可执行其他类方法
                instanceMethod = serviceClass.getMethod( "getInstance", null );

                // 根据是否有参数获取目标方法
                if( paramFlag == null )
                {
                    targetMethod = serviceClass.getMethod( methodName, null );
                }
                else
                {
                    targetMethod = serviceClass.getMethod( methodName, paramFlag );
                }

                // 执行service getInstance()获取对象
                serviceInstance = instanceMethod.invoke( null, null );

                if( param == null )
                {
                    returnObj = targetMethod.invoke( serviceInstance, null );
                }
                else
                {
                    returnObj = targetMethod.invoke( serviceInstance, param );
                }

            }
            catch ( Exception e )
            {
                // 空参数一个参数,但值为空的情况
                if( param == null )
                {
                    try
                    {
                        targetMethod = serviceClass.getMethod( methodName,
                            new Class[] { String.class } );

                        // 执行service getInstance()获取对象
                        serviceInstance = instanceMethod.invoke( null, null );

                        returnObj = targetMethod.invoke( serviceInstance, new Object[] { "" } );
                    }
                    catch ( IllegalArgumentException e1 )
                    {

                        e1.printStackTrace();
                    }
                    catch ( IllegalAccessException e1 )
                    {

                        e1.printStackTrace();
                    }
                    catch ( InvocationTargetException e1 )
                    {

                        e1.printStackTrace();
                    }
                    catch ( SecurityException e2 )
                    {

                        e.printStackTrace();
                    }
                    catch ( NoSuchMethodException e3 )
                    {

                        e.printStackTrace();
                    }
                }
                else
                {
                    e.printStackTrace();
                }
            }

            // 返回的若不是List,那就是分页数据,或单一对象
            if( returnObj instanceof List )
            {
                if( commMode )
                {
                    this.pageContext.setAttribute( "jtopcms:sys:query:list:" + objName, returnObj );
                }

                return ( List ) returnObj;
            }
            else if( returnObj instanceof Object[] )// 分页
            {
                Object[] res = ( Object[] ) returnObj;

                if( res.length < 2 )
                {
                    if( commMode )
                    {
                        this.pageContext.setAttribute( "jtopcms:sys:query:list:" + objName, result );
                    }

                    return result;
                }

                result = ( List ) res[0];

                Page pageInfo = ( Page ) res[1];

                this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

                if( commMode )
                {
                    this.pageContext.setAttribute( "jtopcms:sys:query:list:" + objName, result );
                }

                //String queryCod = "var=" + var + "&page=" + page + "&size=" + size;

                ContentClassBean classBean = ( ContentClassBean ) this.pageContext
                    .getAttribute( "Class" );

                if( classBean != null )
                {
                    // 静态分页
                    publishService.htmlTagPage( this.pageContext, null, classBean.getClassId(),
                        classBean, classBean.getClassId(), pageInfo, page, "" );
                }

                return result;
            }
            else
            {
                result = new ArrayList( 1 );

                if( returnObj != null )
                {
                    result.add( returnObj );
                }

                if( commMode )
                {
                    this.pageContext.setAttribute( "jtopcms:sys:query:list:" + objName, result );
                }

                return result;
            }

        }

        /**
         * 获取来自page或request的值
         */
        // 若不是service方法获取数据,也不是查询sql获取,那么从request获取
        if( StringUtil.isStringNotNull( reObj ) )
        {
            Object obj = this.pageContext.getAttribute( "jtopcms:sys:query:list:" + reObj );

            if( obj == null )
            {
                obj = this.pageContext.getRequest().getAttribute( reObj );
            }

            if( obj instanceof List )
            {
                return ( List ) obj;
            }
            // freemarker
            else if( obj instanceof SimpleSequence )
            {
                try
                {
                    return ( ( SimpleSequence ) obj ).toList();
                }
                catch ( TemplateModelException e )
                {

                }
            }
            else
            {
                result = new ArrayList( 1 );
                result.add( obj );

                return result;
            }
        }

        /**
         * 以下为查询sql执行区
         */
        ClusterMapAdapter QUERY_SQL = QueryDataInfoBehavior.getSystemQueryDataSQl();

        // 严格保证sql来源,本系统不允许使用非PreparedStatement方式执行

        String targetQuerySql = ( String ) QUERY_SQL.get( querySign.trim().toLowerCase() );

        // SecuritySession securitySession = SecuritySessionKeeper
        // .getSecuritySession();

        // TODO 目前允许全部访问,因为 不是管理员,或无查询,禁止访问内部数据
        // if( !securitySession.isManager()
        if( StringUtil.isStringNull( targetQuerySql ) )
        {
            return new ArrayList( 1 );
        }

        int paramCount = StringUtil.getRepeatCharLength( targetQuerySql, "?" );

        // 替换分页参数

        int nextPage = StringUtil.getIntValue( this.pageContext.getRequest().getParameter( "pn" ),
            1 );

        if( !"true".equals( page ) )
        {
            nextPage = 1;
        }

        int pageSize = StringUtil.getIntValue( size, 15 );

        // 总数查询参数
        String[] countVarArray = null;
        Object[] varObj = new Object[paramCount];
        String[] typeInfo = null;

        if( StringUtil.isStringNotNull( countVar ) )
        {

            // 非法参数判断
            countVarArray = StringUtil.split( countVar, "," );

            for ( int i = 0; i < countVarArray.length; i++ )
            {
                if( SystemSafeCharUtil.hasSQLDChars( countVarArray[i] ) )
                {
                    throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
                }

                // typeInfo = countVarArray[i].split( ":" );

                // varObj[i] = StringUtil.changeStringToObject( typeInfo[1],
                // typeInfo[0] );
                varObj[i] = countVarArray[i];
            }
        }

        String targetCountSql = ( String ) QUERY_SQL.get( countSign );

        boolean isPage = StringUtil.isStringNotNull( targetCountSql ) ? true : false;
        Page pageInfo = null;

        if( isPage )
        {
            Long count = mdService.retrieveSystemTableByQueryFlagAndPageInfoCount( targetCountSql,
                varObj, false );

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

            // 替换实际分页参数
            // var = StringUtil.replaceString( var, "{start}", "I:"
            // + Integer.toString( pageInfo.getFirstResult() ), false, false );
            //
            // var = StringUtil.replaceString( var, "{size}", "I:"
            // + Integer.toString( pageSize ), false, false );
        }

        // 内容获取参数
        String[] varArray = null;

        typeInfo = null;

        varObj = new Object[paramCount];

        if( StringUtil.isStringNotNull( var ) )
        {
            // 非法参数判断
            varArray = StringUtil.split( var, "," );

            for ( int i = 0; i < varArray.length; i++ )
            {
                if( SystemSafeCharUtil.hasSQLDChars( varArray[i] ) )
                {
                    throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
                }

                // typeInfo = varArray[i].split( ":" );

                // varObj[i] = StringUtil.changeStringToObject( typeInfo[1],
                // typeInfo[0] );

                if( "{start}".equals( varArray[i] ) )
                {
                    if( pageInfo != null )
                    {
                        varObj[i] = Long.valueOf( pageInfo.getFirstResult() );
                    }
                    else
                    {
                        varObj[i] = null;
                    }
                }
                else if( "{size}".equals( varArray[i] ) )
                {
                    // if( pageInfo != null )
                    // {
                    // varObj[i] = Integer.valueOf( pageSize );
                    // }
                    // else
                    // {
                    // varObj[i] = null;
                    // }
                    varObj[i] = Integer.valueOf( pageSize );
                }
                else
                {
                    varObj[i] = varArray[i];
                }

            }
        }

        List resultMap = mdService.retrieveSystemTableByQueryFlagAndPageInfo( targetQuerySql,
            varObj );

        return resultMap;
    }

    protected String returnPutValueName()
    {
        if( StringUtil.isStringNotNull( objName ) )
        {
            return objName;
        }
        else
        {
            return "SysObj";
        }
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

    public void setCountSign( String countSign )
    {
        this.countSign = countSign;
    }

    public void setQuerySign( String querySign )
    {
        this.querySign = querySign;
    }

    public void setCountVar( String countVar )
    {
        this.countVar = countVar;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setVar( String var )
    {
        this.var = var;
    }

    public void setObjName( String objName )
    {
        this.objName = objName;
    }

    public void setService( String service )
    {
        this.service = service;
    }

    public void setMethod( String method )
    {
        this.method = method;
    }

    public void setSingle( String single )
    {
        this.single = single;
    }

    public void setList( String list )
    {
        this.list = list;
    }

    public void setReObj( String reObj )
    {
        this.reObj = reObj;
    }

    public void setCommon( String common )
    {
        this.common = common;
    }

}

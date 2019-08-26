package cn.com.mjsoft.cms.cluster.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/cluster" )
public class ManageClusterServerInfoController
{
    private static ClusterService clusterService = ClusterService.getInstance();

    @RequestMapping( value = "/addCluServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加集群服务器", token = true )
    public Object addCluServer( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String serverName = ( String ) params.get( "serverName" );

        String url = ( String ) params.get( "clusterUrl" );

        Integer port = StringUtil.getIntValue( ( String ) params.get( "port" ), 80 );

        Integer active = Integer.valueOf( -1 );

        clusterService.addClusterServer( serverName, url, port, active );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/AddClusterServer.jsp", paramMap );
    }

    @RequestMapping( value = "/editCluServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑集群服务器", token = true )
    public Object editCluServer( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long serverId = StringUtil.getLongValue( ( String ) params.get( "serverId" ), -1 );

        String serverName = ( String ) params.get( "serverName" );

        String url = ( String ) params.get( "clusterUrl" );

        Integer port = StringUtil.getIntValue( ( String ) params.get( "port" ), 80 );

        clusterService.editClusterServer( serverId, serverName, url, port );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/EditClusterServer.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteCluServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除集群服务器", token = true )
    public Object deleteCluServer( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long serverId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        clusterService.deleteClusterServer( serverId );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/checkCluServerStatus.do", method = { RequestMethod.POST } )
    public Object checkCluServerStatus( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        clusterService.checkClusterServerConnStatus();

        return "success";
    }

}

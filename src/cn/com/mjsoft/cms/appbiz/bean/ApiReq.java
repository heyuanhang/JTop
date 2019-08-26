package cn.com.mjsoft.cms.appbiz.bean;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.mjsoft.framework.web.wing.ServletUtil;

public class ApiReq
{
    private HttpServletRequest request;

    private HttpServletResponse response;

    private String code;

    private Map param;

    public ApiReq( HttpServletRequest request, HttpServletResponse response, String code, Map param )
    {
        this.request = request;
        this.response = response;
        this.code = code;
        this.param = param;
    }

    public String getCode()
    {
        return code;
    }

    public String repCode()
    {
        return ServletUtil.responseJSON( response, code );
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public Map getParam()
    {
        return param;
    }

    public void setParam( Map param )
    {
        this.param = param;
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }

    public void setRequest( HttpServletRequest request )
    {
        this.request = request;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public void setResponse( HttpServletResponse response )
    {
        this.response = response;
    }

}

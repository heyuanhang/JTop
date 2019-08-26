package cn.com.mjsoft.cms.cluster.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping( "/cluster" )
public class ClusterServerDateServlet extends HttpServlet
{

    private static final long serialVersionUID = -2619137176037097588L;

    private static Logger log = Logger.getLogger( ClusterServerDateServlet.class );

    /**
     * 为集群服务器提供统一的毫秒级时间
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping( value = "/getDate.do", method = { RequestMethod.GET } )
    public Long currentTimeMillis( HttpServletRequest request, HttpServletResponse response )
    {

        return System.currentTimeMillis();

    }

    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse res )
        throws ServletException, IOException
    {

    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse res )
        throws ServletException, IOException
    {

        res.setContentType( "application/json;charset=UTF-8" );
        PrintWriter out = null;
        try
        {
            out = res.getWriter();
            out.write( System.currentTimeMillis() + "" );

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if( out != null )
            {
                out.close();
            }
        }
    }

}

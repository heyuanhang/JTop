<%@ page contentType="text/html; charset=utf-8" session="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
   <head>
      <meta http-equiv="content-type" content="text/html; charset=utf-8" />
      <%
         response.setHeader("Pragma", "No-cache");
         response.setHeader("Cache-Control", "no-cache");
         response.setDateHeader("Expires", 0);
      %>
      <title></title>
      <link rel="stylesheet" type="text/css" href="../../style/default/console.css" />

   </head>
<script>

  
  


</script>
   <frameset rows="30,*" cols="*" framespacing="0" frameborder="0" scrolling="no" noresize="noresize">
     <frame src="top.jsp" frameborder="0" scrolling="no" />
     <frameset rows="*" cols="280,*" framespacing="0" frameborder="0" scrolling="no" noresize="noresize" name="nestCenterFrame" id="nestCenterFrame">
      <frame src="ListResClass.jsp" name="treeFrame" frameborder="0" scrolling="no"  id="treeFrame" />
      <frame src="ListFileRes.jsp?parentFolder=" name="nestContentFrame" frameborder="0" scrolling="yes"  id="nestContentFrame" />
   </frameset>
   
   
   </frameset>
 

   <noframes>
      <body>
      </body>
   </noframes>
</html>

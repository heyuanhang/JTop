<%@ page contentType="text/html; charset=utf-8" session="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
   <head>
      <meta http-equiv="content-type" content="text/html; charset=utf-8" />
      
      <title></title>
      <link rel="stylesheet" type="text/css" href="../style/default/console.css" />
      <link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
      <link href="../style/blue/images/left.css" rel="stylesheet" type="text/css" />

   </head>

   <frameset rows="30,*" cols="*" framespacing="0" frameborder="0" scrolling="no" noresize="noresize">
     <frame src="top.jsp" frameborder="0" scrolling="no" />
     <frameset rows="*" cols="255,*" framespacing="0" frameborder="0" scrolling="no" noresize="noresize" name="nestCenterFrame" id="nestCenterFrame">
      <frame src="ListClass.jsp" name="treeFrame" frameborder="0" scrolling="no"  id="treeFrame" />
      <frame src="EditSite.jsp?redirect=${param.redirect}&flag=${param.flag}&classId=${param.classId}" name="nestContentFrame" frameborder="0" scrolling="yes"  id="nestContentFrame" />
   </frameset>
   
   
   </frameset>
 

   <noframes>
      <body>
      
      </body>
   </noframes>
</html>

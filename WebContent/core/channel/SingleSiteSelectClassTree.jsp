<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>

		<link type="text/css" rel="stylesheet" href="../javascript/tree/xtree2b/css/xtree2.links.css">
		<script type="text/javascript" src="../common/js/jquery-1.6.2.min.js"></script>
		<link href="../style/blue/images/left.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript">var basepath="<cms:Domain/>";</script>
		<script type="text/javascript" src="../javascript/tree/xtree2b/js/radioTree.js"></script>
		
		<style type="text/css">

		   body
		   { 
		   		padding: 0px;
		   	 	margin-top: 6px;
		    	margin-left: 4px;
		   }


		/*去掉下划线*/
		a{text-decoration:none; font-family:″宋体″; font-size: 10pt; color:black;}

	    a:hover {text-decoration:underline; font-family:″宋体″; font-size: 10pt; }
	    
	   </style>
	</head>

	
	<body class="menutree">
		<script type="text/javascript">
		
		
		
 			var isSpec = 'true';
 			
 			 
		   
		    var siteName = <cms:Site siteId="${param.siteId}">'${Site.siteName}'</cms:Site>;
		    
		    var siteId = '${param.siteId}';
		    			 
	 
			
		 
			
		</script>
		
		<script type="text/javascript" src="javascript/radioTree.js"></script>
		<script type="text/javascript" src="javascript/initRadioTree.js"></script>
		
		<script type="text/javascript">
		
			selfClassId = '${param.classId}';
	
		    window.parent.selectedTargetClassId = radioValue;
		    
			targetLinear = '${param.linear}';
			
			mode = '${param.mode}';
		
			initRootTree('${param.mode}');
			
		</script>

	</body>


</html>
<script type="text/javascript">





</script>

<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>

		<link type="text/css" rel="stylesheet" href="../../javascript/tree/xtree2b/css/xtree2.links.css">
		<script type="text/javascript" src="../../common/js/jquery-1.6.2.min.js"></script>
		<link href="../../style/blue/images/left.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript">var basepath="<cms:Domain/>";</script>
		<script type="text/javascript" src="../../javascript/tree/xtree2b/js/checkboxTree.js"></script>
		


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
		   
		    var siteName = <cms:Site siteId="${param.siteId}">'${Site.siteName}'</cms:Site>;
		    
		    var siteId = '${param.siteId}';
		    			 
		</script>
		<script type="text/javascript" src="javascript/tree.js"></script>
		<script type="text/javascript" src="javascript/init.js"></script>
		
		<script type="text/javascript">
		    window.parent.checkedClassMap = checkboxMap;
		    
		    siteName = <cms:Site siteId="${param.siteId}">'${Site.siteName}'</cms:Site>;
		    
			targetModelId = '${param.modelId}';
		
			currentRefClassIdStr = '<cms:UrlParam target="${param.currentRefClassIdStr}" />';
			
			initRootTree();
		</script>

	</body>


</html>
<script type="text/javascript">



function manageBlockInfo()
{
	window.parent.parent.document.getElementById('main').src="<cms:Domain/>core/block/ManageBlockPushContent.jsp";
}




</script>


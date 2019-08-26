<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../style/blue/js/tab_change.js"></script>


		<script>  
		
		 
         if("true"==="${param.fromFlow}")
         {
           
      		var api = frameElement.api, W = api.opener; 
		 
            
             api.close();     
             W.window.location.reload();
         }
        
      </script>
	</head>
	<body>
		<form method="post" id="commendForm" name="commendForm">
			<table border="0" cellspacing="0" cellpadding="0" >
				<tr>
					<cms:SystemCommendType type="all">
						<td>
							<input type="checkbox" name="commendInfo" value="${CommendType.commendTypeId}*${CommendType.commFlag}" />
							${CommendType.commendName}&nbsp;&nbsp;&nbsp;
						</td>
					</cms:SystemCommendType>
				</tr>
			</table>


			<input type="hidden" id="contentId" name="contentId" value="${param.contentId}" />
			<input type="hidden" id="classId" name="classId" value="${param.classId}" />
			<input type="hidden" id="modelId" name="modelId" value="${param.modelId}" />

			</from>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="btnbg100">
					<td width="25%" class="input-title"></td>
					<td width="75%" class="td-input">
						<a href="javascript:submitCommend();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
						<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
					</td>
					<td valign="top">
						<br />
					</td>
				</tr>
			</table>

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
			</table>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  


function submitCommend()
{
    var api = frameElement.api, W = api.opener; 
    
    var from = document.getElementById("commendForm");
	from.action="../../content/commendContent.do";
    from.submit();

}

function close()
{
 	var api = frameElement.api, W = api.opener; 
     api.close();
}



  
</script>

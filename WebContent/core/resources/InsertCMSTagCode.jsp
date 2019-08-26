<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script>
	
      </script>
	</head>
	<body>
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
						<tr>
							<td width="17%" class="input-title">
								<strong>插入</strong>
							</td>
							<td class="td-input">
								<textarea id="editDescs" name="editDesc" class="form-textarea" style="width:320px; height:70px;"></textarea>
							</td>
						</tr>
						</table>

				     	<div style="height:15px;"></div>
				     	<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<td width="71%" class="input-title"></td>
								<td  class="td-input">
									<a href="javascript:gotoFormat();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</td>
							</tr>
						</table>
						</div>

		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

var api = frameElement.api, W = api.opener; 
 
function gotoFormat()
{  
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
   
    W.addCmsTagCode(document.getElementById('editDescs').value);
   
	 close();
}

function close()
{
 	var api = frameElement.api, W = api.opener; 
     api.close();
}



  
</script>

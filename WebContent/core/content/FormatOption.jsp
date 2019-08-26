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
	 //去掉点击链接 虚线边框
      </script>
	</head>
	<body>
		<table border="0" cellspacing="0" cellpadding="0" >
			<tr>
				<td>
					<input type="checkbox" id="formatOptBlank" name="formatOptBlank" value="text" checked />
					首行缩进&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					<input type="checkbox" id="formatOptImage" name="formatOptImage" value="image" checked />
					图片居中&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					行间距
					<input type="input" id="formatOptLine" name="formatOptLine" value="1.5" size="2" />
					&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
     	<div style="height:15px;"></div>
     	<div class="breadnavTab"  >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
			<tr class="btnbg100">
				<div style="float:right">
					<a href="javascript:gotoFormat();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
					<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
				</div>
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


function gotoFormat()
{
    var api = frameElement.api, W = api.opener; 
    
    var wd = W;
    
    if('true' == '${param.dialogMode}')
    {
    	wd = api.get('main_content');
    }
    
    
	if(true == document.getElementById('formatOptImage').checked)
	{
		wd.image= true;
	}
	
	if(true == document.getElementById('formatOptBlank').checked)
	{
		wd.blank= true;
	}
	
	wd.line = document.getElementById('formatOptLine').value;
   
    wd.okFlag = true;
    
    api.close();
}

function close()
{
 	var api = frameElement.api, W = api.opener; 
     api.close();
}



  
</script>

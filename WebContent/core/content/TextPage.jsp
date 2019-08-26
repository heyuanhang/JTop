<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />

		<script>
	 //去掉点击链接 虚线边框
      </script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
			<tr>
				<td width="17%" class="input-title">
					分页标题:
				</td>
				<td class="td-input">
					<input id="pageTitle" name="pageTitle" type="text" style="width:330px" class="form-input" />
					<span class="ps"></span>
				</td>
			</tr>
			<tr>
				<td  class="input-title">
					第几页:
				</td>
				<td class="td-input">
					<input id="pagePos" name="pagePos" type="text" style="width:70px" value="1" class="form-input" />
					<span class="red">*</span><span class="ps">必须按照正序连续序号排列分页页码</span>
				</td>
			</tr>
		</table>


		<div style="height:15px;"></div>
		<div class="breadnavTab"  >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
			<tr class="btnbg100">
				 <div style="float:right">
					<a href="javascript:gotoPage();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
					<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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
var api = frameElement.api, W = api.opener; 

var wd = W;
    
if('true' == '${param.dialogMode}')
{
   wd = api.get('main_content');
}

function gotoPage()
{
	wd.currentTitle = document.getElementById('pageTitle').value;
	
	wd.currentPage = document.getElementById('pagePos').value;
	
	wd.okFlag = true;
		 
    api.close();
}

function close()
{
     api.close();
}



  
</script>

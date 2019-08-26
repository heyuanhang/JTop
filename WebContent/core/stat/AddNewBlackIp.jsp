<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<title></title>
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
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
		<form id="biForm" name="biForm" method="post">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
			
			<tr>
				<td width="23%" class="input-title">
					<strong>目标IP地址</strong>
				</td>
				<td class="td-input">
					<input class="form-input" type="text" id="ips" name="ips"  style="width:268px" />
					<br/>
					<span class="ps">若需要屏蔽多个IP,请用,(逗号)在IP地址间隔离</span>
				</td>
			</tr>
			
			<tr>
				<td class="input-title">
					<strong>永远禁止</strong>
				</td>
				<td class="td-input">
					<input class="form-radio" type="radio" name="forever" value="0" onclick="javascript:change();" checked />
					否 &nbsp;&nbsp;&nbsp;
					<input class="form-radio" type="radio" name="forever" value="1" onclick="javascript:change();" />
					是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="input-title"><strong>屏蔽时间</strong></span>
					<input class="form-input" type="text" id="effectHour" name="effectHour" size="9" value="1" />
					小时
					
				</td>
			</tr>

			


		</table>
		<cms:Token mode="html"/>
		
		</form>

		<div style="height:15px;"></div>
		<div class="breadnavTab" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="btnbg100">
					<div style="float:right">
						<a href="javascript:submitBlackIp();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
						<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16" /><b>取消&nbsp;</b> </a>
					</div>
				</tr>
			</table>
		</div>

		</table>






	</body>
</html>

<script type="text/javascript">

function change()
{
	 
}


function close()
{
	var api = frameElement.api, W = api.opener; 	          
    api.close();      
}

function submitBlackIp()
{
	var biForm = document.getElementById('biForm');
	
    biForm.action="<cms:BasePath/>stat/addBlackIp.do";
    
    biForm.submit();

}
				
									
</script>


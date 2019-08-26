<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

		<script>
		 	
      </script>
	</head>
	<body>

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">安全与防护</a> &raquo;
						<a href="#">系统实时日志</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" >
									<div class="fl">
									<a href="javascript:resumeGetThread();" class="btnwithico" onclick=""> <img src="../../core/style/icons/alarm-clock--arrow.png" alt="" /><b>自动刷新&nbsp;</b> </a>
									<a href="javascript:stopGetThread();" class="btnwithico" onclick=""> <img src="../../core/style/icons/minus-white.png" alt="" /><b>停止刷新&nbsp;</b> </a>
									
										
									</div>
									
									<div class="fr">
									<a href="javascript:window.location.reload();" class="btnwithico" onclick=""> <img src="../../core/style/icons/arrow-circle-315.png" alt="" /><b>重置状态&nbsp;</b> </a>
									

									</div>
								</td>
							</tr>

							<tr>
								<td >
									
									<center>
										<textarea  wrap="off" readonly id="logText" class="form-textarea" style="width:97%"></textarea>
									
									<script>
										var innerH = document.body.scrollHeight-120;
										 
										$('#logText').attr('style','width:97%;height:'+innerH+'px');
									
									</script>
									
									</center>
									

								</td>
							</tr>


						</table>
						<div class="mainbody-right"></div>
					</td>
					
				</tr>

				
			</table>


			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

	
		

var prevPos = -1;

var timeId = -1;

timeId = setInterval("queryLogText()",2000);

function queryLogText()
{
	var url = "<cms:BasePath/>readSysLog?prevPos="+prevPos;
	
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(msg)
			            {     
			            	var jsonObj = eval("("+msg+")");
			            
			           		prevPos = jsonObj.pos;
			           		
			           		var text =  jsonObj.content;
			           	
   		
			           		
							$('#logText').val($("#logText").val()+text);
 		            	
			              
			            }
		});	                    
 		
  		var ltext = document.getElementById('logText');
			            	
		ltext.scrollTop=ltext.scrollHeight;

}

function resumeGetThread()
{
	if(timeId == -1)
	{
	 	timeId = setInterval("queryLogText()",2000);
	 	
	 	$.dialog.tips('重新启动刷新日志成功...',2); 
	}
	else
	{
		$.dialog.tips('当前已在自动刷新日志模式...',1); 
	}
}


function stopGetThread()
{
	 window.clearInterval(timeId);
	 timeId = -1;
	 $.dialog.tips('已经停止自动刷新日志...',2); 
}




</script>

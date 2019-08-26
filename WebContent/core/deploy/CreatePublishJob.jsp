<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script> 
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script>
			var api = frameElement.api, W = api.opener;
			
			 if("true"==="${param.fromFlow}")
	         {     	 	
	         	 W.$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '增加发布任务成功!', 
      				ok: function()
      				{
      					         		 
	       				W.window.location.reload();     
      				} 
                    
	  			});
	          
	              
	         }
        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/task.png" width="16" height="16" />任务信息
					</div>

					<form id="jobForm" name="jobForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="23%" class="input-title">
									<strong>发布目标</strong>
								</td>
								<td class="td-input">
									<input type="checkbox" name="homePubTarget" value="1" class="form-radio"   />
									站点首页&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="channelPubTarget" value="1" class="form-radio"   />
									栏目频道和列表&nbsp;&nbsp;
									<input type="checkbox" name="specPubTarget" value="1" class="form-radio"   />
									专题和列表
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>任务描叙</strong>
								</td>
								<td class="td-input">
									<textarea id="jobDesc" name="jobDesc" class="form-textarea" style="width:360px; height:50px;"></textarea>

								</td>
							</tr>

						

							<tr>
								<td class="input-title">
									<strong>执行计划</strong>
								</td>
								<td class="td-input">
									<input type="radio" name="triggerType" value="1" class="form-radio" onclick="javascript:disableRadio(this)" checked />
									时间段
									<input type="radio" name="triggerType" value="2" class="form-radio" onclick="javascript:disableRadio(this)" />
									每天计划
									<input type="radio" name="triggerType" value="5" class="form-radio" onclick="javascript:disableRadio(this)" />
									Cron规则
									<%--<input type="radio" name="triggerType" value="week" class="form-radio" onclick="javascript:disableRadio(this)" />
									周计划
									<input type="radio" name="triggerType" value="month" class="form-radio" onclick="javascript:disableRadio(this)" />
									月计划
									
								--%>
								<span class="ps">Cron语句为时间段描叙</span>
								</td>
							</tr>

							<tr id="trSeg">
								<td class="input-title">

								</td>
								<td class="td-input">
									每隔
									<input type="text" id="periodVar" name="periodVar" size=3 value="24" class="form-input" />
									<select id="periodSegment" name="periodSegment" class="form-select">
										<%--<option value="s">
											秒 &nbsp;&nbsp;&nbsp;
										</option>
										--%><option value="2" >
											分钟 &nbsp;&nbsp;&nbsp;
										</option>
										<option value="3" selected>
											小时&nbsp;&nbsp;&nbsp;
										</option>
										<option value="4">
											天&nbsp;&nbsp;&nbsp;
										</option>
									</select>
									执行一次
								</td>
							</tr>

							<tr id="trWeek" style="display:none">
								<td class="input-title">

								</td>
								<td class="td-input">
									<input type="checkbox" name="allWeek" />
									周一
									<input type="checkbox" name="allWeek" />
									周二
									<input type="checkbox" name="allWeek" />
									周三
									<input type="checkbox" name="allWeek" />
									周四
									<input type="checkbox" name="allWeek" />
									周五
									<input type="checkbox" name="allWeek" />
									周六
									<input type="checkbox" name="allWeek" />
									周日
									<br />
									<br />
									在
									<input id="weekTime" name="weekTime" size="12" maxlength="30" type="text" class="form-input-time" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'HH:mm:ss'});" />
									执行一次

								</td>
							</tr>

							<tr id="trMonth" style="display:none">
								<td class="input-title">

								</td>
								<td class="td-input">
									<input type="checkbox" name="allWeek" />
									一月
									<input type="checkbox" name="allWeek" />
									二月
									<input type="checkbox" name="allWeek" />
									三月
									<input type="checkbox" name="allWeek" />
									四月
									<input type="checkbox" name="allWeek" />
									五月
									<input type="checkbox" name="allWeek" />
									六月
									<br />
									<input type="checkbox" name="allWeek" />
									七月
									<input type="checkbox" name="allWeek" />
									八月
									<input type="checkbox" name="allWeek" />
									九月
									<input type="checkbox" name="allWeek" />
									十月
									<input type="checkbox" name="allWeek" />
									十一月
									<input type="checkbox" name="allWeek" />
									十二月

									<br />
									<br />
									在
									<select name="monthDay" id="monthDay" class="form-select">
										<option value="1">
											1 &nbsp;&nbsp;
										</option>
										<option value="2">
											2&nbsp;&nbsp;
										</option>
										<option value="3">
											3&nbsp;&nbsp;
										</option>
										<option value="4">
											4&nbsp;&nbsp;
										</option>
										<option value="5">
											5&nbsp;&nbsp;
										</option>
										<option value="6">
											6&nbsp;&nbsp;
										</option>
										<option value="7">
											7&nbsp;&nbsp;
										</option>
										<option value="8">
											8&nbsp;&nbsp;
										</option>
										<option value="9">
											9&nbsp;&nbsp;
										</option>
										<option value="10">
											10&nbsp;&nbsp;
										</option>
										<option value="11">
											11&nbsp;&nbsp;
										</option>
										<option value="12">
											12&nbsp;&nbsp;
										</option>
										<option value="13">
											13&nbsp;&nbsp;
										</option>
										<option value="14">
											14&nbsp;&nbsp;
										</option>
										<option value="15" selected>
											15&nbsp;&nbsp;
										</option>
										<option value="16">
											16&nbsp;&nbsp;
										</option>
										<option value="17">
											17&nbsp;&nbsp;
										</option>
										<option value="18">
											18&nbsp;&nbsp;
										</option>
										<option value="19">
											19&nbsp;&nbsp;
										</option>
										<option value="20">
											20&nbsp;&nbsp;
										</option>
										<option value="21">
											21&nbsp;&nbsp;
										</option>
										<option value="22">
											22&nbsp;&nbsp;
										</option>
										<option value="23">
											23&nbsp;&nbsp;
										</option>
										<option value="24">
											24&nbsp;&nbsp;
										</option>
										<option value="25">
											25&nbsp;&nbsp;
										</option>
										<option value="26">
											26&nbsp;&nbsp;
										</option>
										<option value="27">
											27&nbsp;&nbsp;
										</option>
										<option value="28">
											28&nbsp;&nbsp;
										</option>
										<option value="29">
											29&nbsp;&nbsp;
										</option>
										<option value="30">
											30&nbsp;&nbsp;
										</option>
										<option value="31">
											31&nbsp;&nbsp;
										</option>
									</select>
									号
									<input id="monthTime" name="monthTime" size="12" maxlength="30" type="text" class="form-input-time" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'dd HH:mm:ss'});" />
									执行一次

								</td>
							</tr>


							<tr id="trEvery" style="display:none">
								<td class="input-title">

								</td>
								<td class="td-input">
									每天在
									<input id="dayExeTime" name="dayExeTime" size="14" maxlength="30" type="text" class="form-input-time" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'HH:mm:ss'});" />
									执行一次
								</td>
							</tr>

							<tr id="trCron" style="display:none">
								<td class="input-title">

								</td>
								<td class="td-input">
									Cron规则
									<input type="text" id="cronExpression" id="cronExpression" size="50" value="" class="form-input" />
									<img class="from-img-icon" src="../style/blue/icon/help.png" />
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>执行周期</strong>
								</td>
								<td class="td-input">

									<input id="jobStartDate" name="jobStartDate" style="width:164px" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
									&nbsp;至 &nbsp;
									<input  id="jobEndDate" name="jobEndDate" style="width:164px" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" />

								</td>
							</tr>

							

						</table>
						<!-- hidden -->
						<cms:Token mode="html"/>
					
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitJobForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>
				</td>
			</tr>

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


function close()
{
	api.close();
}


function submitJobForm()
{
	disableAnchorElementByName("btnwithicosysflag",true);
	
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
		
	 encodeFormInput('jobForm', false);

	var jobForm = document.getElementById('jobForm');
	
	jobForm.action = '<cms:BasePath/>job/addPSJob.do';
	
	jobForm.submit();

}

function disableRadio(radio)
{
   if("2" == radio.value)
   {
   	  document.getElementById("trSeg").style.display = 'none';
	  document.getElementById("trEvery").style.display = '';
	  
	  document.getElementById("trWeek").style.display = 'none'
	  document.getElementById("trMonth").style.display = 'none'
   	 
   	  document.getElementById("trCron").style.display = 'none';
   
   }
   else if("1" == radio.value)
   {
   	  document.getElementById("trSeg").style.display = '';
	  document.getElementById("trEvery").style.display = 'none';
	  
	  document.getElementById("trWeek").style.display = 'none'
	  document.getElementById("trMonth").style.display = 'none'
   	 
   	  document.getElementById("trCron").style.display = 'none';
   }
   else if("3" == radio.value)
   {
   	  document.getElementById("trSeg").style.display = 'none';
	  document.getElementById("trEvery").style.display = 'none';
	  
	  document.getElementById("trWeek").style.display = ''
	  document.getElementById("trMonth").style.display = 'none'
   	 
   	  document.getElementById("trCron").style.display = 'none';
   }
   else if("4" == radio.value)
   {
   	  document.getElementById("trSeg").style.display = 'none';
	  document.getElementById("trEvery").style.display = 'none';
	  
	  document.getElementById("trWeek").style.display = 'none'
	  document.getElementById("trMonth").style.display = ''
   	 
   	  document.getElementById("trCron").style.display = 'none';
   }
   else if("5" == radio.value)
   {
	  document.getElementById("trSeg").style.display = 'none';
	  document.getElementById("trEvery").style.display = 'none';
	  
	  document.getElementById("trWeek").style.display = 'none'
	  document.getElementById("trMonth").style.display = 'none'
   	 
   	  document.getElementById("trCron").style.display = '';
   }

}

  
   
  
</script>

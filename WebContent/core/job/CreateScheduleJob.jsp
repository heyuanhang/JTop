<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script>
	
      </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />
						任务信息
					</div>

					<form id="createFlowForm" name="createFlowForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="19%" class="input-title">
									任务类别
								</td>
								<td width="87%" class="td-input">
									<select name="date" id="in4">
										<option value="m">
											所有任务 &nbsp;&nbsp;&nbsp;
										</option>
										<option value="h">
											静态化发步&nbsp;&nbsp;&nbsp;
										</option>
										<option value="d">
											网络采集&nbsp;&nbsp;&nbsp;
										</option>
										<option value="moth">
											数据库采集&nbsp;&nbsp;&nbsp;
										</option>
										<option value="moth">
											数据库备份&nbsp;&nbsp;&nbsp;
										</option>
									</select>
								</td>
							</tr>

							<tr>
								<td width="13%" class="input-title">
									任务名
								</td>
								<td width="87%" class="td-input">
									<input id="flowName" name="flowName" type="text" class="form-input" size="40" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									描叙
								</td>
								<td class="td-input">
									<textarea id="flowDesc" name="flowDesc" class="form-textarea" style="width:380px; height:40px;"></textarea>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									执行计划
								</td>
								<td class="td-input">
									<input type="radio" name="sc" value="seg" class="form-radio" onclick="javascript:disableRadio(this)" checked />
									时间段
									<input type="radio" name="sc" value="every" class="form-radio" onclick="javascript:disableRadio(this)" />
									每天计划
									<input type="radio" name="sc" value="week" class="form-radio" onclick="javascript:disableRadio(this)" />
									周计划
									<input type="radio" name="sc" value="month" class="form-radio" onclick="javascript:disableRadio(this)" />
									月计划
									<input type="radio" name="sc" value="cron" class="form-radio" onclick="javascript:disableRadio(this)" />
									高级Cron规则
								</td>
							</tr>

							<tr id="trSeg">
								<td class="input-title">

								</td>
								<td class="td-input">
									每隔
									<input type="text" id="in3" size=3 value="1" class="form-input" />
									<select name="date" id="in4" class="form-select">
										<option value="s">
											秒 &nbsp;&nbsp;&nbsp;
										</option>
										<option value="m" selected>
											分钟 &nbsp;&nbsp;&nbsp;
										</option>
										<option value="h">
											小时&nbsp;&nbsp;&nbsp;
										</option>
										<option value="d">
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
									<input id="dTime" name="dTime" size="12" maxlength="30" type="text" class="form-input-time" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'HH:mm:ss'});" />
									执行一次
								</td>
							</tr>

							<tr id="trCron" style="display:none">
								<td class="input-title">

								</td>
								<td class="td-input">
									Cron规则
									<input type="text" id="cronModeText" size="25" value="" class="form-input" />
									<img class="from-img-icon" src="../style/blue/icon/help.png" />
								</td>
							</tr>

							<tr>
								<td class="input-title">
									执行周期:
								</td>
								<td class="td-input">

									<input id="appearStartDateTime" name="appearStartDateTime" size="27" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
									&nbsp;至 &nbsp;
									<input d id="appearEndDateTime" name="appearEndDateTime" size="27" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'});" />

								</td>
							</tr>

							<tr>
								<td class="input-title">
									是否停用
								</td>
								<td class="td-input">
									<input type="radio" name="conjunctFlag" value="0" checked />
									否
									<input type="radio" name="conjunctFlag" value="1" />
									是
								</td>
							</tr>





						</table>
						<!-- hidden -->
						<input type="hidden" id="censorInfo" name="censorInfo" />

					</form>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr class="btnbg100">
							<td width="40%" class="input-title"></td>
							<td width="87%" class="td-input">
								<a href="javascript:createFlow();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
								<a href="javascript:;" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
							</td>
							<td valign="top">
								<br />
							</td>
						</tr>
					</table>


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

function disableRadio(radio)
{
   if("every" == radio.value)
   {
   	  document.getElementById("trSeg").style.display = 'none';
	  document.getElementById("trEvery").style.display = '';
	  
	  document.getElementById("trWeek").style.display = 'none'
	  document.getElementById("trMonth").style.display = 'none'
   	 
   	  document.getElementById("trCron").style.display = 'none';
   
   }
   else if("seg" == radio.value)
   {
   	  document.getElementById("trSeg").style.display = '';
	  document.getElementById("trEvery").style.display = 'none';
	  
	  document.getElementById("trWeek").style.display = 'none'
	  document.getElementById("trMonth").style.display = 'none'
   	 
   	  document.getElementById("trCron").style.display = 'none';
   }
   else if("week" == radio.value)
   {
   	  document.getElementById("trSeg").style.display = 'none';
	  document.getElementById("trEvery").style.display = 'none';
	  
	  document.getElementById("trWeek").style.display = ''
	  document.getElementById("trMonth").style.display = 'none'
   	 
   	  document.getElementById("trCron").style.display = 'none';
   }
   else if("month" == radio.value)
   {
   	  document.getElementById("trSeg").style.display = 'none';
	  document.getElementById("trEvery").style.display = 'none';
	  
	  document.getElementById("trWeek").style.display = 'none'
	  document.getElementById("trMonth").style.display = ''
   	 
   	  document.getElementById("trCron").style.display = 'none';
   }
   else if("cron" == radio.value)
   {
	  document.getElementById("trSeg").style.display = 'none';
	  document.getElementById("trEvery").style.display = 'none';
	  
	  document.getElementById("trWeek").style.display = 'none'
	  document.getElementById("trMonth").style.display = 'none'
   	 
   	  document.getElementById("trCron").style.display = '';
   }

}

  
   
  
</script>

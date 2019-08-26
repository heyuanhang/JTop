<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/uuid.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script language="JavaScript" src="../javascript/chart/fc/js/FusionCharts.js"></script>

		<script type="text/javascript" src="../javascript/progressbar/js/jquery.progressbar.js"></script>


		<script>
			//表格变色
			$(function()
			{ 
		   		$("#showlist tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
			});  
		
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
						<a href="#">数据分析</a> &raquo; <a href="#">点击数据排行</a> &raquo; <a href="#">链接点击排行</a>
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
							<td style="padding: 7px 10px;">
								<div class="fl">
									<span style="align:middle"> <a href="javascript:changeChart();" class="btnwithico"><img src="../style/icons/chart-up.png" width="16" height="16" /><b>重新统计&nbsp;</b> </a> &nbsp;(所有点击URL次数排名前100位统计) </span>

								</div>

								<div>


								</div>


							</td>
						</tr>





						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">



									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td width="40%">
												&nbsp;访问URL地址
											</td>


											<td width="9%">
												出现次数(PV)
											</td>





											<td width="13%">
												所占比例
											</td>


										</tr>

										<cms:QueryData objName="VisLog" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteVisitUrlPvInfoTag">


											<tr>
												<td>
													&nbsp; <a target="_blank" href="${VisLog.url}">${VisLog.url}</a>
												</td>
												<td>
													${VisLog.clickCount}
												</td>




												<td>
													<span class="progressBar" id="pb-${status.index}">%${VisLog.pvPer}%</span>
													<script type="text/javascript">
																		$("#pb-${status.index}").progressBar({ boxImage:'<cms:BasePath/>core/javascript/progressbar/images/progressbar.gif', barImage: '<cms:BasePath/>core/javascript/progressbar/images/progressbg_green.gif'} );
													</script>
												</td>


											</tr>
										</cms:QueryData>
										<cms:Empty flag="VisLog">
											<tr>
												<td class="tdbgyew" colspan="9">
													<center>
														当前没有数据!
													</center>
												</td>
											</tr>
										</cms:Empty>





									</table>
									<br />
								</div>
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





function changeChart()
{


	replaceUrlParam(window.location,'sd='+$('#startAddDateVal').val()+'&ed='+$('#endAddDateVal').val());
	
	

}



</script>

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
						<a href="#">数据分析</a> &raquo; <a href="#">访问源分析</a> &raquo; <a href="#">搜索词分析</a>
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
							
								<a href="javascript:changeChart();" class="btnwithico"><img src="../style/icons/chart-up.png" width="16" height="16" /><b>重新统计&nbsp;</b> </a>
									&nbsp;	<span style="align:middle">(搜索引擎的来源关键字百分比)		</span>
								

								</div>

								<div>
									
								
								</div>
							</td>
						</tr>

						<%--<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table class="listdate" width="100%" border="1" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td>
												搜索引擎来源关键字百分比
											</td>

										</tr>


										<tr>
											<td>

												<div id="chartdiv" align="center"></div>
												

												<script type="text/javascript">
												   var chart = new FusionCharts("../javascript/chart/fc/Charts/FCF_Pie2D.swf", "ChartId", "950", "220");
												   
												   chart.setTransparent(true);
												  
												   
												   chart.setDataXML('<graph baseFont="SunSim" baseFontSize="12" borderThickness="1" borderAlpha="40" showNames="1" decimalPrecision="0">'
												   
												   					  <cms:QueryData objName="Ht" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteVisitSearchKeyUvInfoTag" >
											
												   					  
																	  +'<set name="${Ht.keyVal}" value="${Ht.uvPer}" />'
																	  
																	  </cms:QueryData>
																	 
																	  +'</graph>"');
															   
												  chart.render("chartdiv");
												</script>

											</td>


											</td>
										</tr>

									</table>
								</div>

							</td>
						</tr>


						--%><tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="14%">
													&nbsp;来源搜索词汇
												</td>
												
												<td width="9%">
													UV(独立访问用户)
												</td>

												<td width="9%">
													占有百分比
												</td>

												

											</tr>
											  <cms:QueryData objName="Ht" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteVisitSearchKeyUvInfoTag" >
											
											
												
											</cms:QueryData>
											<cms:QueryData objName="VisLog"   reObj="Ht">
															
											<tr>
												<td>
													&nbsp;<cms:DecodeParam enc="utf-8" str="${VisLog.keyVal}"/>
												</td>
												<td>
													${VisLog.uvCount}
												</td>
												<td>
													<span class="progressBar" id="pb-${status.index}">%${VisLog.uvPer}%</span>
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
									<br/>
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

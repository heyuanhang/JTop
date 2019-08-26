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
		
		<script src="../javascript/chart/echarts/echarts.js"></script> 
   
   		<script src="../javascript/chart/echarts/theme/macarons.js"></script> 


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
						<a href="#">数据分析</a> &raquo; <a href="#">访问者数据</a> &raquo; <a href="#">小时数据分析</a>
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
									<a href="javascript:changeChart();" class="btnwithico"><img src="../style/icons/chart-up.png" width="16" height="16" /><b>重新统计&nbsp;</b> </a> (按照小时区间来统计访问数据)

								</div>

								<div>


								</div>

							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table class="listdate" width="100%" border="1" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td>
												小时访问量趋势
											</td>

										</tr>


										<tr>
											<td>

												 
											 <div id="main" style="width:100%;height:320px"></div>  
																							 
																							  <script type="text/javascript">  
													        // 路径配置  
													        require.config({  
													            paths: {  
													                echarts: '../javascript/chart/echarts'  
													            }  
													        });  
													          
													        // 使用  
													      require(  
													           [  
													               'echarts',  
													               'echarts/chart/bar',
													               'echarts/chart/line'
													               
													             
													           ],  
													            function (ec) {  
													                // 基于准备好的dom，初始化echarts图表  
													                var myChart = ec.init(document.getElementById('main'), theme);   
													                  
													                var option = {
																	    tooltip : {
																	        trigger: 'axis'
																	    },
																	    toolbox: {
																	        show : true,
																	        feature : {
																	            
																	            restore : {show: true},
																	            saveAsImage : {show: true}
																	        }
																	    },
																	    calculable : true,
																	    legend: {
																	        data:['PV访问量','UV访问量','IP访问量','回访率' ]
																	    },
																	    xAxis : [
																	        {
																	            type : 'category',
																	            data : [
																	            
																	            <cms:QueryData objName="Dv" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteVisitHourInfoTag" var="">
																					<cms:if test="${!status.last}">
																				 		'${status.index}点',
																				
																					 </cms:if>
																					 <cms:else>
																					 	'${status.index}点'
																					
																					 
																					 </cms:else>
																	            
																	            </cms:QueryData>
																	            ]
																	        }
																	    ],
																	   yAxis : [
																        {
																	            type : 'value',
																	            name : '访问数',
																	            axisLabel : {
																	                formatter: '{value} '
																	            }
																	        },
																	        {
																	            type : 'value',
																	            name : '回访百分比',
																	            axisLabel : {
																	                formatter: '{value} %'
																	            }
																	        }
																	    ],
																	    series : [
																	
																	        {
																	            name:'PV访问量',
																	            type:'bar',
																	            data:[
																	            
																	            <cms:QueryData objName="Dpv" reObj="Dv">
																
																					<cms:if test="${!status.last}">
																				 		${Dpv.pvc},
																				
																					 </cms:if>
																					 <cms:else>
																					 	${Dpv.pvc}
																					
																					 
																					 </cms:else>
																				</cms:QueryData>
																	            
																	            ]
																	        },
																	        {
																	            name:'UV访问量',
																	            type:'bar',
																	            data:[
																	            
																	            <cms:QueryData objName="Duv" reObj="Dv">
																
																					<cms:if test="${!status.last}">
																				 		${Duv.uvc},
																				
																					 </cms:if>
																					 <cms:else>
																					 	${Duv.uvc}
																					
																					 
																					 </cms:else>
																				</cms:QueryData>
																	            
																	            
																	            
																	            
																	            ]
																	        },
																	        {
																	            name:'IP访问量',
																	            type:'bar',
																	       
																	            data:[
																	            
																				<cms:QueryData objName="Duv" reObj="Dv">
																
																					<cms:if test="${!status.last}">
																				 		${Duv.uvc},
																				
																					 </cms:if>
																					 <cms:else>
																					 	${Duv.uvc}
																					
																					 
																					 </cms:else>
																				</cms:QueryData>

																				]
																	        }
																	        ,
																	        {
																	            name:'回访率',
																	            type:'line',
																	            yAxisIndex: 1,
																	            data:[
																				
																				<cms:QueryData objName="Dper" reObj="Dv">
																				<cms:if test="${!status.last}">
																				 		${Dper.uvPer},
																				
																					 </cms:if>
																					 <cms:else>
																					 	${Dper.uvPer}
																					
																					 
																					 </cms:else>
																				</cms:QueryData>

																				]
																	        }
																	    ]
																	};
																	                    
                    
													          
													                // 为echarts对象加载数据   
													                myChart.setOption(option);   
													            }  
													        );  
        
        
        

        
        
    									</script>  


											</td>
										</tr>

									</table>
								</div>

							</td>
						</tr>


						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">



											<td width="12%">
												&nbsp;&nbsp;时间点
											</td>

											<td width="9%">
												PV(点击量)
											</td>

											<td width="9%">
												UV(独立访客)
											</td>

											<td width="9%">
												IP(ip地址)
											</td>


											<td width="8%">
												回访率
											</td>


										</tr>

										<cms:QueryData objName="VisLog" reObj="Dv">
											<tr>
												<td>
													&nbsp;&nbsp;${status.index}:00 时
												</td>
												<td>
													${VisLog.pvc}
												</td>
												<td>
													${VisLog.uvc}
												</td>

												<td>
													${VisLog.ivc}
												</td>

												<td>
													${VisLog.uvPer}%
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

								</div>
								<div class="mainbody-right"></div>
								<br />
							</td>
						</tr>
					</table>


					<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">



<cms:if test="${param.pvc == 0}">

	document.getElementById('pv').checked = false;

</cms:if>


<cms:if test="${param.uvc == 0}">

	document.getElementById('uv').checked = false;

</cms:if>

<cms:if test="${param.ipc == 0}">

	document.getElementById('ip').checked = false;

</cms:if>

function changeChart()
{
	window.location.reload();

}



</script>

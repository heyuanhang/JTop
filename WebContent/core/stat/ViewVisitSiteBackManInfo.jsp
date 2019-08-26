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
						<a href="#">数据分析</a> &raquo; <a href="#">访问者数据</a> &raquo; <a href="#">回头客分析</a>
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
								<span style="align:middle">
									日期区间:&nbsp;
									<input id="startAddDateVal" class="form-input-top-date" size="18" maxlength="30" type="text" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" value="${param.sd}" />
									至
									<input id="endAddDateVal" class="form-input-top-date" size="18" maxlength="30" type="text" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" value="${param.ed}" />
								</span>
									&nbsp;&nbsp;&nbsp;

								</div>

								<div>
									<a href="javascript:changeChart();" class="btnwithico"><img src="../style/icons/chart-up.png" width="16" height="16" /><b>执行分析&nbsp;</b> </a>
									&nbsp;(按照日用户访问者来统计回访数据)
								
								</div>

								
							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table class="listdate" width="100%" border="1" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td>
												日访问量趋势
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
																	        data:['新访客','老用户', '回访率' ]
																	    },
																	    xAxis : [
																	        {
																	            type : 'category',
																	            data : [
																	            
																	            <cms:QueryData objName="Dv" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteDayBackManVisitInfoTag" var="${param.sd},${param.ed},,">
																					<cms:if test="${!status.last}">
																				 		'${Dv.visitYear}-${Dv.visitMonth}-${Dv.visitDay}',
																				
																					 </cms:if>
																					 <cms:else>
																					 	'${Dv.visitYear}-${Dv.visitMonth}-${Dv.visitDay}'
																					
																					 
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
																	            name:'新访客',
																	            type:'bar',
																	            data:[
																	            
																	            <cms:QueryData objName="Dpv" reObj="Dv">
															
																
																					<cms:if test="${!status.last}">
																				 		${Dpv.newUv},
																				
																					 </cms:if>
																					 <cms:else>
																					 	${Dpv.newUv}
																					
																					 
																					 </cms:else>
																				</cms:QueryData>
																	            
																	            ]
																	        },
																	        {
																	            name:'老用户',
																	            type:'bar',
																	            data:[
																	            
																	            <cms:QueryData objName="Duv" reObj="Dv">
																
																					<cms:if test="${!status.last}">
																				 		${Duv.oldUv},
																				
																					 </cms:if>
																					 <cms:else>
																					 	${Duv.oldUv}
																					
																					 
																					 </cms:else>
																				</cms:QueryData>
																	            
																	            
																	            
																	            
																	            ]
																	        },
																	        
																	        
																	        {
																	            name:'回访率',
																	            type:'line',
																	            yAxisIndex: 1,
																	            data:[
																				
																				<cms:QueryData objName="Div" reObj="Dv">
																				<cms:if test="${!status.last}">
																				 		${Div.oldUvPer},
																				
																					 </cms:if>
																					 <cms:else>
																					 	${Div.oldUvPer}
																					
																					 
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

												<td width="11%">
													&nbsp;日期
												</td>

												
												<td width="9%">
													新访问者
												</td>

												<td width="9%">
													回头客
												</td>

												


												<td width="8%">
													回访率
												</td>


											</tr>
											
											<cms:QueryData objName="VisLog"  service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteDayBackManVisitInfoTag" var="${param.sd},${param.ed},36,true">
															
										
											<tr>
												<td>
													&nbsp;<cms:FormatDate date="${VisLog.vdt}" format="yyyy-MM-dd"/>
												</td>
												<td>
													${VisLog.newUv}
												</td>
												<td>
													${VisLog.oldUv}
												</td>

												
												
												<td>
													${VisLog.oldUvPer}%
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

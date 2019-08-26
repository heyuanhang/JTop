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
						<a href="#">数据分析</a> &raquo; <a href="#">访问者客户端</a> &raquo; <a href="#">浏览器</a>
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
									&nbsp;	<span style="align:middle">(访问用户使用浏览器统计)		</span>
								

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
												浏览器百分比
											</td>

										</tr>


										<tr>
											<td>

												<div id="chartdiv" align="center"></div>
												

												 

											<div id="main" style="width:100%;height:350px"></div>  
																							 
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
													               
													               'echarts/chart/pie' 
													               
													             
													           ],  
													            function (ec) {  
													                // 基于准备好的dom，初始化echarts图表  
													                var myChart = ec.init(document.getElementById('main'), theme);   
													                  
													                var option = {
																    title : {
																        text: '浏览器百分比',
																        
																        x:'center'
																    },
																    tooltip : {
																        trigger: 'item',
																        formatter: "{a} <br/>{b} : {c} ({d}%)"
																    },
																    legend: {
																        orient : 'vertical',
																        x : 'left',
																        data:[
																        
																        <cms:QueryData objName="Ht" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteVisitBrPvInfoTag" >
											
																	        <cms:if test="${!status.last}">
																		 		'${Ht.brName}',
																		
																			 </cms:if>
																			 <cms:else>
																			 	'${Ht.brName}'
																			
																			 
																			 </cms:else>
																        
																         </cms:QueryData>
																        ]
																    },
																    toolbox: {
																        show : true,
																        feature : {
																           
																            restore : {show: true},
																            saveAsImage : {show: true}
																        }
																    },
																    calculable : true,
																    series : [
																        {
																            name:'访问百分比',
																            type:'pie',
																            radius : '65%',
																            center: ['50%', '60%'],
																            data:[
																            
																            
																            	<cms:QueryData objName="Hst" reObj="Ht">
															 	
																					<cms:if test="${!status.last}">
																				 		{value:${Hst.pvPer}, name:'${Hst.brName}'},
																				
																					 </cms:if>
																					 <cms:else>
																					 	{value:${Hst.pvPer}, name:'${Hst.brName}'}
																					
																					 
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

												<td width="14%">
													&nbsp;浏览器名称
												</td>
												
												<td width="9%">
													PV(点击量)
												</td>

												<td width="9%">
													占有百分比
												</td>

												

											</tr>
											
											<cms:QueryData objName="VisLog"   reObj="Ht">
															
											<tr>
												<td>
													&nbsp;${VisLog.brName}
												</td>
												<td>
													${VisLog.pvCount}
												</td>
												<td>
													${VisLog.pvPer}&nbsp;%
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

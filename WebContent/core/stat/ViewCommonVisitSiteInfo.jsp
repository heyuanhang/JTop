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
						<a href="#">数据分析</a> &raquo; <a href="#">综合报告</a> &raquo; <a href="#">总体访问情况</a>
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
									日期区间:&nbsp;
									<input id="startAddDateVal" class="form-input-top-date" size="18" maxlength="30" type="text" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" value="${param.sd}" />
									至
									<input id="endAddDateVal" class="form-input-top-date" size="18" maxlength="30" type="text" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" value="${param.ed}" />
									&nbsp;&nbsp;&nbsp;&nbsp; IP统计
									<%--<input type="checkbox" id="ip" name="ip" class="form-checkbox" value="1" checked />
									&nbsp;&nbsp; UV统计
									<input type="checkbox" id="uv" name="uv" class="form-checkbox" value="1" checked />
									&nbsp;&nbsp; PV统计
									<input type="checkbox" id="pv" name="pv" class="form-checkbox" value="1" checked />
									&nbsp;&nbsp;&nbsp;

								--%></div>

								<div>
									<a href="javascript:changeChart();" class="btnwithico"><img src="../style/icons/chart-up.png" width="16" height="16" /><b>执行分析&nbsp;</b> </a>

								
								</div>

								<div class="fr">
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
													    legend: {
													        data:['PV访问量','UV访问量','IP访问量' ]
													    },
													    toolbox: {
													        show : true,
													        feature : {
													          
													         
													             //magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
													             //magicType : {show: true, type: [ 'stack', 'tiled']},
													             restore : {show: true},
													             saveAsImage : {show: true}
													        }
													    },
													    calculable : true,
													    xAxis : [
													        {
													            type : 'category',
													            boundaryGap : false,
													            data : [
													            
													            
													            <cms:QueryData objName="Dv" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteDayVisitInfoTag" var="${param.sd},${param.ed},,">
																	 		
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
													            type : 'value'
													        }
													    ],
													    series : [
													        {
													            name:'PV访问量',
													            type:'line',
													            stack: '总量1',
													            data:[
													            
													            
													            <cms:QueryData objName="Dpv" reObj="Dv">
																																								
																	 <cms:if test="${!status.last}">
																	 	${Dpv.pvCount},
																	
																	 </cms:if>
																	 <cms:else>
																	 	${Dpv.pvCount}
																	 </cms:else>																								
																</cms:QueryData>	
													
													            
													            ]
													        },
													        {
													            name:'UV访问量',
													            type:'line',
													            stack: '总量2',
													            data:[
													            
													            <cms:QueryData objName="Duv" reObj="Dv">
																																								
																	 <cms:if test="${!status.last}">
																	 	${Duv.uvCount},
																	
																	 </cms:if>
																	 <cms:else>
																	 	${Duv.uvCount}
																	 </cms:else>																								
																</cms:QueryData>	
													            
													            
													            
													            
													            ]
													        },
													        {
													            name:'IP访问量',
													            type:'line',
													            stack: '总量3',
													            data:[
													            
													            
													            <cms:QueryData objName="Dip" reObj="Dv">
																																								
																	 <cms:if test="${!status.last}">
																	 	${Dip.ipCount},
																	
																	 </cms:if>
																	 <cms:else>
																	 	${Dip.ipCount}
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
									<cms:QueryData objName="VisLog" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteAllVisitInfoTag">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">



												<td width="9%">
													&nbsp;&nbsp;指标
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

											<tr>
												<td>
													&nbsp;&nbsp;总数据
												</td>
												<td>
													${VisLog.all.pv}
												</td>
												<td>
													${VisLog.all.uv}
												</td>

												<td>
													${VisLog.all.ip}
												</td>

												<td>
													${VisLog.all.ov}
												</td>

											</tr>

											<tr>
												<td>
													&nbsp;&nbsp;当前日
												</td>
												<td>
													${VisLog.day.pv}
												</td>
												<td>
													${VisLog.day.uv}
												</td>

												<td>
													${VisLog.day.ip}
												</td>

												<td>
													${VisLog.day.ov}
												</td>




											</tr>

											<tr>
												<td>
													&nbsp;&nbsp;本周
												</td>
												<td>
													${VisLog.week.pv}
												</td>
												<td>
													${VisLog.week.uv}
												</td>

												<td>
													${VisLog.week.ip}
												</td>

												<td>
													${VisLog.week.ov}
												</td>




											</tr>

											<tr>
												<td>
													&nbsp;&nbsp;本月
												</td>
												<td>
													${VisLog.month.pv}
												</td>
												<td>
													${VisLog.month.uv}
												</td>

												<td>
													${VisLog.month.ip}
												</td>

												<td>
													${VisLog.month.ov}
												</td>

											</tr>





											<tr>
												<td>
													&nbsp;&nbsp;每日平均
												</td>
												<td>
													${VisLog.per.pvp}
												</td>
												<td>
													${VisLog.per.uvp}
												</td>

												<td>
													${VisLog.per.ipp}
												</td>

												<td>
													0.0%
												</td>

											</tr>



											<tr>
												<td>
													&nbsp;&nbsp;最高值
												</td>
												<td>
													${VisLog.mpv.pvCount}&nbsp;&nbsp;(<cms:FormatDate date="${VisLog.mpv.vdt}" format="yyyy-MM-dd"/>)
												</td>
												<td>
													${VisLog.muv.uvCount}&nbsp;&nbsp;(<cms:FormatDate date="${VisLog.muv.vdt}" format="yyyy-MM-dd"/>)
												</td>

												<td>
													${VisLog.mip.ipCount}&nbsp;&nbsp;(<cms:FormatDate date="${VisLog.mip.vdt}" format="yyyy-MM-dd"/>)
												</td>

												<td>
													0.0%
												</td>

											</tr>




										</table>
									</cms:QueryData>
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
	var ip = $('#ip').attr('checked');
	
	var uv = $('#uv').attr('checked');
	
	var pv = $('#pv').attr('checked');

	var ipc = '0';
	
	if('checked' == ip)
	{
		ipc = '1';
	}
	
	var uvc = '0';
	
	if('checked' == uv)
	{
		uvc = '1';
	}
	
	var pvc = '0';
	
	if('checked' == pv)
	{
		pvc = '1';
	}

	replaceUrlParam(window.location,'ipc='+ipc+'&uvc='+uvc+'&pvc='+pvc+'&sd='+$('#startAddDateVal').val()+'&ed='+$('#endAddDateVal').val());
	
	

}



</script>

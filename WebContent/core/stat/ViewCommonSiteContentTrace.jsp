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
	<cms:LoginUser>
	<body>
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">数据分析</a> &raquo; <a href="#">工作量统计</a> &raquo; <a href="#">统计概览</a>
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
									站点:&nbsp;
									<select class="form-select" id="ids" name="ids" onchange="javascript:change();">
										<cms:if test="${Auth.orgCode == 001}">
										<option value="">
											------ 所有站点 ------
										</option>
										</cms:if>
										<cms:SystemOrgSite orgId="${Auth.orgIdentity}">
											<option value="${OrgSite.siteId}">
												${OrgSite.siteName}&nbsp;&nbsp;
											</option>
										</cms:SystemOrgSite>
									</select><%--
									&nbsp;
									日期:&nbsp;
									<input id="startAddDateVal" class="form-input-top-date" size="18" maxlength="30" type="text" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" value="${param.sd}" />
									至
									<input id="endAddDateVal" class="form-input-top-date" size="18" maxlength="30" type="text" onclick="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" value="${param.ed}" />
									 --%>
									&nbsp;&nbsp;&nbsp;&nbsp; 近30日
									<input type="radio"   name="collFlag" class="form-checkbox" value="day"  onclick="javascript:changeD();"/>
									&nbsp;&nbsp; 近12个月
									<input type="radio"   name="collFlag" class="form-checkbox" value="mon"  onclick="javascript:changeD();"/>
									&nbsp;&nbsp; 近10年 
									<input type="radio"   name="collFlag" class="form-checkbox" value="year"  onclick="javascript:changeD();"/>
									&nbsp;&nbsp;&nbsp;
								</div>

								<div>
									<a href="javascript:changeChart();" class="btnwithico"><img src="../style/icons/chart-up.png" width="16" height="16" /><b>重新统计&nbsp;</b> </a>
			
									<a href="javascript:initColl();" class="btnwithico"><img src="../style/icons/bin.png" width="16" height="16" /><b>初始化&nbsp;</b> </a>
									
								</div>

								<div class="fr">
									<script type="text/javascript">

									initSelect('ids','${param.ids}');
									
									initRadio('collFlag','${param.collFlag}');
									
									if('' == '${param.collFlag}')
									{
										initRadio('collFlag','day');
									}
									</script>
								</div>
							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table class="listdate" width="100%" border="1" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td>
												近期内容发布趋势
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
													        data:['录入数',  '发布数',  '图片数', '媒体数' ]
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
													            
													            
													            <cms:QueryData objName="Dv" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteContentMonTraceTag" var="${param.ids},${param.collFlag},${param.sd},${param.ed}">
																 		
																	 
																	 
																	 <cms:if test="${param.collFlag == 'year'}">								
																	 
																		<cms:if test="${!status.last}">
																	 	    '${Dv.upYear}年',
																	
																		 </cms:if>
																		 <cms:else>
																		 	'${Dv.upYear}年'
																		
																		 
																		 </cms:else>
																		
																		
																		
																		
																	 </cms:if>
																	<cms:elseif test="${param.collFlag == 'mon'}">								
																	 
																		<cms:if test="${!status.last}">
																	 	    '${Dv.upYear}-${Dv.upMon}',
																	
																		 </cms:if>
																		 <cms:else>
																		 	'${Dv.upYear}-${Dv.upMon}'
																		
																		 
																		 </cms:else>
																		
																		
																		
																		
																	</cms:elseif>
																	<cms:else>								
																		 
																		<cms:if test="${!status.last}">
																	 	    '${Dv.upMon}.${Dv.upDay}',
																	
																		 </cms:if>
																		 <cms:else>
																		 	'${Dv.upMon}.${Dv.upDay}'
																		
																		 
																		 </cms:else>
																		
																		
																		
																		
																		
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
													            name:'录入数',
													            type:'line',
													            stack: '总量1',
													            data:[
													            
													            
													            <cms:QueryData objName="Dpv" reObj="Dv">
																																								
																	 <cms:if test="${!status.last}">
																	 	${Dpv.addAllCount},
																	
																	 </cms:if>
																	 <cms:else>
																	 	${Dpv.addAllCount}
																	 </cms:else>																								
																</cms:QueryData>	
													
													            
													            ]
													        },
													        {
													            name:'发布数',
													            type:'line',
													            stack: '总量2',
													            data:[
													            
													            <cms:QueryData objName="Duv" reObj="Dv">
																																								
																	 <cms:if test="${!status.last}">
																	 	${Dpv.pubAllCount},
																	
																	 </cms:if>
																	 <cms:else>
																	 	${Dpv.pubAllCount}
																	 </cms:else>																								
																</cms:QueryData>	
													            
													            
													            
													            
													            ]
													        },
													        {
													            name:'图片数',
													            type:'line',
													            stack: '总量3',
													            data:[
													            
													            
													            <cms:QueryData objName="Dip" reObj="Dv">
																																								
																	 <cms:if test="${!status.last}">
																	 	${Dpv.imgAllCount},
																	
																	 </cms:if>
																	 <cms:else>
																	 	${Dpv.imgAllCount}
																	 </cms:else>																							
																</cms:QueryData>	
													            
													            
													            
													            
													            ]
													        },
													        {
													            name:'媒体数',
													            type:'line',
													            stack: '总量4',
													            data:[
													            
													            
													            <cms:QueryData objName="Dip" reObj="Dv">
																																								
																	 <cms:if test="${!status.last}">
																	 	${Dpv.videoAllCount},
																	
																	 </cms:if>
																	 <cms:else>
																	 	${Dpv.videoAllCount}
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
									<cms:QueryData objName="ST" service="cn.com.mjsoft.cms.stat.service.StatService" method="querySiteContentIntegratedTraceForTag" var="">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">
											
											<tr class="datahead" >



												<td colspan="5">
													&nbsp;&nbsp;统计概览
												</td>

												 


											</tr>

											<tr>
												<td  width="5%">
													&nbsp;&nbsp;开通站点数:
												</td>
												<td  width="10%">
													${ST.siteCount}
												</td>
												 
												
												<td  width="5%">
													栏目总数:
												</td>

												<td  width="10%">
													${ST.classCount}
												</td>

											 

											</tr>
											
											
											<tr>
												<td>
													&nbsp;&nbsp;管理员总数:
												</td>
												<td  width="10%">
													${ST.userCount}
												</td>
												 
												
												<td>
													机构（部门）总数:
												</td>

												<td   >
													${ST.orgCount}
												</td>

											 

											</tr>
											
											<tr>
												<td>
													&nbsp;&nbsp;累计录入数:
												</td>
												<td  width="10%">
													${ST.allAddCount}
												</td>
												 
												
												<td>
													累计发布数:
												</td>

												<td   >
													${ST.allPubCount}
												</td>

											 

											</tr>
											
											<tr>
												<td>
													&nbsp;&nbsp;累计图片信息:
												</td>
												<td  width="10%">
													${ST.allImgCount}
												</td>
												 
												
												<td>
													累计视频信息:
												</td>

												<td   >
													${ST.allVideoCount}
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



function changeChart()
{
	var collFlag = $("input[name='collFlag']:checked").val();
	  
                       var url = "<cms:BasePath/>stat/initCache.do"+"?<cms:Token mode='param'/>";
 		
				 		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {     
				            	var msg = eval("("+mg+")");
				            	
				               if('success' == msg)
				               {
				               		 
				               		replaceUrlParam(window.location,'collFlag='+collFlag+'&sd='+$('#startAddDateVal').val()+'&ed='+$('#endAddDateVal').val());
	
				               	 
				               } 	
				               else
				               {
				               	    $.dialog(
								    { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/fail.png', 
								    				
								                    content: "执行失败，无权限请联系管理员！",
						
								    				cancel: function()
								    				{
								    					window.location.reload();
								    				}
									});
				               }   
				              
				            }
				     	});	

	
	

}


function initColl()
{
	<cms:LoginUser>
	if('001' != '${Auth.orgCode}')
	{
		$.dialog({ 
	   					title :'提示',
	    				width: '240px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/fail.png', 
	    				
	                    content: '只有超级管理员允许初始化！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	</cms:LoginUser>
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '260px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认初始化工作量统计记录吗？<br/><br/>(注意：这项操作不可回滚!!!)',
                    
                    ok: function () 
                    { 
                   	 $.dialog.tips('正在初始化内容统计,可能需要较长时间,请耐心等待...',7600000000,'loading.gif');
	
                       var url = "<cms:BasePath/>stat/initSCT.do"+"?<cms:Token mode='param'/>";
 		
				 		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {     
				            	var msg = eval("("+mg+")");
				            	
				               if('success' == msg)
				               {
				               		 
				               		window.location.reload();
				               	 
				               } 	
				               else
				               {
				               	    $.dialog(
								    { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/fail.png', 
								    				
								                    content: "执行失败，无权限请联系管理员！",
						
								    				cancel: function()
								    				{
								    					window.location.reload();
								    				}
									});
				               }   
				              
				            }
				     	});	
       
    				}, 
    				cancel: true
    				
   	});

	
					

}

function change()
{
	replaceUrlParam(window.location,'ids='+$('#ids').val());
}

function changeD()
{
	var collFlag = $("input[name='collFlag']:checked").val();;
	
	replaceUrlParam(window.location,'collFlag='+collFlag);
}


</script>
</cms:LoginUser>

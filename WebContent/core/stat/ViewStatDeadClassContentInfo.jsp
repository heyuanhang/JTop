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

  
  		<cms:LoginUser>
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
						<a href="#">数据分析</a> &raquo; <a href="#">工作量统计</a> &raquo; <a href="#">栏目维护统计</a>
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
								所属:&nbsp;
									<select class="form-select" id="ids" name="ids" onchange="javascript:change();">
										 
										<cms:SystemOrgSite orgId="${Auth.orgIdentity}">
											<option value="${OrgSite.siteId}">
												${OrgSite.siteName}&nbsp;&nbsp;
											</option>
										</cms:SystemOrgSite>
									</select>
									&nbsp;
									状态:&nbsp;
									<select class="form-select" id="flag" name="flag" onchange="javascript:change();">
										 
										 	
										 
											<option value="week">
												一周无更新
											</option>
											
											<option value="threeMon">
												三个月无更新
											</option>
											
											<option value="halfYear">
												半年无更新
											</option>
											
											<option value="year">
												一年以上无更新
											</option>
											
											<option value="zero">
												无数据栏目
											</option>
										 
									</select>
									&nbsp;
									 &nbsp; 
								</div>

								<div>
									<a href="javascript:changeChart();" class="btnwithico"><img src="../style/icons/chart-up.png" width="16" height="16" /><b>重新统计&nbsp;</b> </a>
									
								 	<a href="<cms:BasePath/>stat/report.do?flag=empty&ids=${param.ids}&eFlag=${param.flag}" class="btnwithico"><img src="../style/icons/report-excel.png" width="16" height="16" /><b>导出报表&nbsp;</b> </a>
								   
								</div>

								<div >
							 		&nbsp; (若查询条件改变，请先执行重新统计才可导出最新报表)
								</div>
							</td>
						</tr>

						

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">


												<td width="3%">
													&nbsp;排名
												</td>
												
												<td width="3%">
													栏目ID
												</td>

												<td width="14%">
													栏目名称
												</td>

												<td width="6%">
													最后更新日期
												</td>
												
												<td width="6%">
													已停止更新天数
												</td>
												
												 

												 										

											</tr>
											<cms:QueryData objName="ST" service="cn.com.mjsoft.cms.stat.service.StatService" method="queryStatSiteContentWeekAndMonAndYearEndClassForTag" var="${param.ids},${param.flag}">
								
											<tr>
												<td>
												<cms:if test="${ST.className != '合计'}">
													&nbsp;${status.count}
												</cms:if>
													
												</td>
												<td>
													${ST.classId}
												</td>
												<td>
												
												    ${ST.classTree}
													 
												</td>
												
												<td>
													 
													<cms:if test="${empty ST.ud}">
													 无
													</cms:if>
													<cms:else>
													${ST.ud}
													</cms:else>	
													 
												</td>
												
												<td>
													<cms:if test="${empty ST.dayCount}">
													 无
													</cms:if>
													<cms:else>
													${ST.dayCount}天
													</cms:else>	
	
													 
												</td>

												 

											 
												
											 
											 

											</tr>

											 </cms:QueryData>
											
											<cms:Empty flag="ST">
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
							</td>
						</tr>
					</table>
					<br/>

					<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">



initSelect('ids','${param.ids}');

initSelect('flag','${param.flag}');

function changeChart()
{
	 
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
				               		 
				               		replaceUrlParam(window.location,'sd='+$('#startAddDateVal').val()+'&ed='+$('#endAddDateVal').val());
	
				               	 
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


function report()
{
					var url = "<cms:BasePath/>stat/report.do";
 		
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
						
								    				cancel: true
									});
				               }   
				              
				            }
				     	});	

}

function change()
{
	replaceUrlParam(window.location,'ids='+$('#ids').val()+'&flag='+$('#flag').val());
}

function viewMore(siteId,flag)
{
	var flagName = '';
	
	if(flag == 'zero')
	{
		flagName = '查看无数据栏目';
	}
	else if(flag == 'week')
	{
		flagName = '查看一周无更新';
	}
	else if(flag == 'threeMon')
	{
		flagName = '查看三个月无更新';
	}
	else if(flag == 'halfYear')
	{
		flagName = '查看半年无更新';
	}
	else if(flag == 'year')
	{
		flagName = '查看一年以上无更新';
	}


   $.dialog({ 
	    id : 'oesr',
    	title : flagName,
    	width: '500px', 
    	height: '550px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:BasePath/>core/stat/ViewMoreStat.jsp?siteId='+siteId+'&flag='+flag

	});
}

</script>
</cms:LoginUser>

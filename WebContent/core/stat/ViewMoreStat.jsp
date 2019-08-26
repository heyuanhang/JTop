<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>



		<script>
	 	var api = frameElement.api, W = api.opener; 


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

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->

					<div style="height:5px"></div>
					<form id="userRoleForm" name="userRoleForm" method="post">
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">


							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												 

												<td width="8%">
													<strong>所属站点</strong>
												</td>
												
												<td width="4%">
													<strong>栏目ID</strong>
												</td>

												 

												<td width="12%">
													<strong>栏目名称</strong>
												</td>

											</tr>

											<cms:QueryData objName="ST" service="cn.com.mjsoft.cms.stat.service.StatService" method="queryStatSiteContentWeekAndMonAndYearEndClassForTag" var="${param.siteId},${param.flag}">
								
												 <cms:Class id="${ST.classId}">
												 <cms:Site siteFlag="${Class.siteFlag}">
													<tr>
														 
														

														<td>
															&nbsp;${Site.siteName}
														</td>
														
														
														<td>
															&nbsp;${Class.classId}
														</td>

														 
														
														<td>
														
														 &nbsp;${ST.classTree}
															
														</td>
														


													</tr>
													</cms:Site>
													</cms:Class>
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
										<!-- hidden -->
									 
										
										</form>
										<div style="height:15px"></div>
					
										<div class="breadnavTab"  >
											<table width="100%" border="0" cellspacing="0" cellpadding="0" >
												<tr class="btnbg100">
													<td width="90%" class="input-title"></td>
													<td class="td-input">
														 	<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
													</td>
													 
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

var api = frameElement.api, W = api.opener;
  
function close()
{

	api.close();
	api.get('orud').window.location.reload();
}
   
 
  
</script>

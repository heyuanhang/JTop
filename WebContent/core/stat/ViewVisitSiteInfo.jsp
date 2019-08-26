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
						<a href="#">数据分析</a> &raquo; <a href="#">访问者数据</a> &raquo; <a href="#">近期记录</a>
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
							
								<a href="javascript:changeChart();" class="btnwithico"><img src="../style/icons/chart-up.png" width="16" height="16" /><b>刷新数据&nbsp;</b> </a>
									&nbsp;	<span style="align:middle">(当前站点的近几天访问者行为细节)		</span>							
								</div>

								<div>
									
								
								</div>
							</td>
						</tr>

						


						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div>
									<cms:QueryData objName="Vis" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteVisitInfoTag" var="${param.pn},20">
										<table width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="100%" height="60">
													&nbsp;来源IP:&nbsp;${Vis.ip}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													时间:&nbsp;<cms:FormatDate date="${Vis.visitTimeIn}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													
													浏览器:&nbsp;${Vis.browser}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													系统:&nbsp;${Vis.system}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													地区:&nbsp;<cms:IPArea ip="${Vis.ip}"/>
												
												</td>

											</tr>

											<tr>
												<td>
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															
															<td width="100%">
																<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
																	<tr>
																		<td width="5%" class="input-title">
																			&nbsp;访问URL:
																		</td>
																		<td width="52%" class="td-input">
																			<a href="${Vis.url}" target="_blank">${Vis.url}</a>
																		</td>
																		<td width="1%" class="input-title">
																			
																		</td>
																		<td width="6%" class="input-title">
																			来源主机:
																		</td>
																		<td width="18%" class="td-input">
																			<a href="javascript:;" >${Vis.host}</a>
																		</td>
																	</tr>
																	
																</table>

															</td>


														</tr>

													</table>
												</td>
											</tr>
										</table>
										<br />
									</cms:QueryData>
									<cms:Empty flag="Vis">

										<tr>
											<td>
												<center>
													<table class="listdate" width="98%" cellpadding="0" cellspacing="0">

														<tr>
															<td class="tdbgyew" style="height:40px;">


																当前没有访问数据!



															</td>
														</tr>

													</table>
												</center>
											</td>
										</tr>
									</cms:Empty>
									
									


									<cms:PageInfo>
										<tr id="pageBarTr">
											<td colspan="8" class="PageBar" align="left">
												<div class="fr">
													<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
													<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
													<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
													<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
													<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
												</div>
												<script>
																function query(flag)
																{
																	var cp = 0;
																	
																	if('p' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage-1}');
																	}
		
																	if('n' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage+1}');
																	}
		
																	if('h' == flag)
																	{
			                                                             cp = 1;
																	}
		
																	if('l' == flag)
																	{
			                                                             cp = parseInt('${Page.pageCount}');
																	}
		
																	if(cp < 1)
																	{
			                                                           cp=1;
																	}
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
												<div class="fl"></div>
												<br/><br/>
											</td>
										</tr>
									</cms:PageInfo>
								</div>
								<div class="mainbody-right"></div>
							</td>
						</tr>
					</table>

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

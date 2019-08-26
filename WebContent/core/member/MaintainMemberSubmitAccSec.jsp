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
						<a href="#">站点维护</a> &raquo;
						<a href="#">会员权限</a> &raquo;
						<a href="#">会员投稿维护</a>
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
									<a href="javascript:applyAcc();" class="btnwithico"><img src="../style/icons/folder-horizontal-open.png" width="16" height="16" /><b>应用当前规则&nbsp;</b> </a>

									<a href="javascript:clearAcc();" class="btnwithico"><img src="../style/icons/folder--minus.png" width="16" height="16" /><b>解除全站投稿权限&nbsp;</b> </a>
								</div>

								<div class="fr">
								</div>

							</td>
						</tr>

						<form method="post" id="accForm" name="accForm">
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">



											<td width="28%">
												<strong>栏目名称</strong>
											</td>
											<td width="21%">
												<strong>可选浏览规则</strong>
											</td>


											<td width="12%">

												<center>
													<strong>操作</strong>
												</center>

											</td>

										</tr>
										<cms:CurrentSite>
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
												<cms:SysClass>
													<tr>


														<td>
															${Class.layerUIClassName}
														</td>
														<td>
															<center>
																<select id="classAcc-${Class.classId}" name="classAcc-${Class.classId}" class="form-select">
																	<option value="-1">
																		--------------------------- 无投稿限制 ---------------------------
																	</option>
																	<cms:QueryData service="cn.com.mjsoft.cms.security.service.SecurityService" method="getMemberAccRuleListForTag" var="2" objName="MemAcc">

																		<option value="${MemAcc.accRuleId}">
																			${MemAcc.accName}
																		</option>
																	</cms:QueryData>

																</select>
															</center>
														</td>



														<td>
															<center>
																<a href="javascript:applyChildAcc('${Class.classId}');"><img src="../../core/style/icon/sort-quantity.png" width="16" height="16" />应用子栏目</a> &nbsp;

															</center>
														</td>
													</tr>
												</cms:SysClass>
											</cms:SystemClassList>
										</cms:CurrentSite>
									</table>
								</div>
								<div class="mainbody-right"></div>
							</td>
						</tr>

					</table>


				</td>
			</tr>
			</form>

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
<script type="text/javascript">

<cms:QueryData service="cn.com.mjsoft.cms.security.service.SecurityService" method="getMemberAccForTag" var="2" objName="Acc">
																	
initSelect('classAcc-${Acc.classId}','${Acc.accRuleId}');																
																	
</cms:QueryData>

function applyAcc()
{
	var url = "<cms:BasePath/>security/applyAcc.do?typeId=2"+"&<cms:Token mode='param'/>";
 			
 	 var postData = $("#accForm").serialize();
 					
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data: postData,
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               		 
			               		$.dialog({ 
									   					title :'提示',
									    				width: '160px', 
									    				height: '60px', 
									                    lock: true, 
									    				icon: '32X32/succ.png', 
									    				
									                    content: '设置投稿权限成功!', 
									      				ok: function()
									      				{
									      					         		 
										       				window.location.reload();     
									      				} 
									                    
								});
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

function applyChildAcc(cid)
{
	var url = "<cms:BasePath/>security/applyChildAcc.do?typeId=2&classId="+cid+"&<cms:Token mode='param'/>";
 			
 	  				
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data: '',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               		 
			               		$.dialog({ 
									   					title :'提示',
									    				width: '180px', 
									    				height: '60px', 
									                    lock: true, 
									    				icon: '32X32/succ.png', 
									    				
									                    content: '应用子栏目投稿权限成功!', 
									      				ok: function()
									      				{								      					         		 
										       				window.location.reload();     
									      				} 
									                    
								});
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

function clearAcc()
{

	var dialog = $.dialog({ 
   					title :'提示',
    				width: '220px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '将解除所有栏目会员投稿限制,您确定要执行吗?',
                    
                    ok: function () { 
       
   						var url = "<cms:BasePath/>security/clearAcc.do?typeId=2"+"&<cms:Token mode='param'/>";
 			
 	  				
						$.ajax({
								      		type: "POST",
								       		url: url,
								       		data: '',
								   
								       		success: function(mg)
								            {     
								            	var msg = eval("("+mg+")");
								            	
								               if('success' == msg)
								               {
								               		 
								               		$.dialog({ 
														   					title :'提示',
														    				width: '160px', 
														    				height: '60px', 
														                    lock: true, 
														    				icon: '32X32/succ.png', 
														    				
														                    content: '清除投稿权限成功!', 
														      				ok: function()
														      				{
														      					         		 
															       				window.location.reload();     
														      				} 
														                    
													});
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
    }, 
    cancel: true 
    });
	

}
</script>

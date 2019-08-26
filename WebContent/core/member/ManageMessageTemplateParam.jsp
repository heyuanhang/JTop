<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
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
						<a href="#">会员相关</a> &raquo;
						<a href="#">站内信模板参数<a href="#">
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="roleForm" name="roleForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openCreateMTPDialog();" class="btnwithico" onclick=""><img src="../style/icons/mail--pencil.png" width="16" height="16" /><b>添加参数&nbsp;</b> </a>

										<a href="javascript:deleteSelectTempParam();" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>

									</div>
									<div class="fr">

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="99.8%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>

												<td width="12%">
													<strong>参数名称</strong>
												</td>
												
													<td width="20%">
													<strong>替换标识</strong>
												</td>

												<td width="6%">
													<strong>创建者</strong>
												</td>

												<td width="7%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:CurrentSite>

												<cms:SystemList querySign="query_mg_template_param" objName="MGTP" var="${CurrSite.siteId}">
													<tr>
														<td>
															${MGTP.tpId}
														</td>
														<td>
															<input class="inputCheckbox"  name="checkedId" value="${MGTP.tpId}" type="checkbox" onclick="javascript:" />
														</td>

														<td>
															&nbsp;${MGTP.paramName}
														</td>
														
														<td>
															&nbsp;${MGTP.paramFlag}
														</td>
												
														<td>
															${MGTP.creator}
														</td>

														<td>
															<div>
																<center>
																	<span ><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditMTDialog('${MGTP.tpId}');">&nbsp;编辑</a>&nbsp;&nbsp;</span>
																</center>
															</div>
														</td>
													</tr>

												</cms:SystemList>
												<cms:Empty flag="MGTP">
													<tr>
														<td class="tdbgyew" colspan="9">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
												</cms:Empty>
											</cms:CurrentSite>


										</table>
									</div>
									<div class="mainbody-right"></div>
								</td>
							</tr>
							 

						</table>

						</form>

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
<script type="text/javascript">

function openCreateMTPDialog()
{
	$.dialog({ 
    	title :'新增站内信模板参数',
    	width: '450px', 
    	height: '180px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/CreateMessageTemplateParam.jsp'
	});
}

function openEditMTDialog(id)
{
	$.dialog({ 
    	title :'编辑站内信模板参数',
    	width: '450px', 
    	height: '180px',  
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:BasePath/>core/member/EditMessageTemplateParam.jsp?id='+id
	});
}



function deleteSelectTempParam()
{
	var cidCheck = document.getElementsByName('checkedId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	}
	
	if(ids == '')
	{
		$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '220px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/i.png', 
			        content: '请选择需要删除的模版参数!', 
			       	cancel: function()
			       	{
			       		
			       	}
		});
		return;
	}
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '220px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选模版参数吗？',
                    
                    ok: function () 
                    { 
                   
                    var url = "<cms:BasePath/>member/deleteMsgTempParam.do"+"?<cms:Token mode='param'/>";
    
   
				    var postData = "ids="+ids;
				 
				 	$.ajax({
				      	type: 'POST',
				       	url: url,
				       	data:postData,
				   
				       	success: function(mg)
				        {        
				        	var msg = eval("("+mg+")");
				        	
				            			   if('success' == msg)
							               {
							               	 
							               		
							               		$.dialog({ 
								   					title :'提示',
								    				width: '140px', 
								    				height: '60px', 
								                    lock: true, 
								    				icon: '32X32/succ.png', 
								    				
								                    content: '删除模版参数成功!',
								                    
								                    ok: function () 
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

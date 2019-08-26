<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/uuid.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
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
						<a href="#">内容组织</a> &raquo; 专题类型管理
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
									<a href="javascript:openAddSpecialClassDialog();" class="btnwithico"><img src="../style/icons/folder-open-table.png" width="16" height="16" /><b>添加专题分类&nbsp;</b> </a>
								</div>

								<div class="fr">

								</div>

							</td>
						</tr>

						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">
									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="4%">
												ID
											</td>

											<td width="27%">
												<strong>专题名称</strong>
											</td>

											<td width="12%">
												<strong>访问标识</strong>
											</td>



											<td width="10%">

												<center><strong>操作</strong></center>

											</td>

										</tr>
										<cms:CurrentSite>
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all" isSpec="true" classType="4">
												<cms:SysClass>
													<tr>
														<td>
															${Class.classId}
														</td>

														<td>
															&nbsp;${Class.layerUIClassName}
														</td>

														<td>
															${Class.classFlag}
														</td>

														<td>
															<div>
																<center>
																	<a href="javascript:openEditSpecialClassDialog('${Class.classId}');"><span ><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<a href="javascript:deleteSpecialClass('${Class.classId}')"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除 </a></span>
																</center>
															</div>
														</td>

													</tr>
												</cms:SysClass>
											</cms:SystemClassList>
											<cms:Empty flag="Class">
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


				</td>
			</tr>

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
		</table>

		<form method="post" id="generateForm" name="generateForm">
			<input type="hidden" id="siteId" name="siteId" value="${CurrSite.siteId}" />
			<input type="hidden" id="classIdInfo" name="classIdInfo" />
			<input type="hidden" id="staticType" name="staticType" />
			<input type="hidden" id="pubEventKey" name="pubEventKey" />
		</form>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">


function openAddSpecialClassDialog()
{
	$.dialog({ 
	    id : 'oascd',
    	title : '增加专题分类',
    	width: '560px', 
    	height: '250px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/channel/AddSpecialClass.jsp'
	});
}

function openEditSpecialClassDialog(classId)
{
	$.dialog({ 
	    id : 'oescd',
    	title : '编辑专题分类',
    	width: '560px', 
    	height: '250px',  
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
           
        content: 'url:<cms:Domain/>core/channel/EditSpecialClass.jsp?classId='+classId
	});
}

function deleteSpecialClass(classId)
{
	 $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选分类吗？将删除所有所属专题！',
                    
                    ok: function () { 
                    
 					var url = "../../channel/deleteSpecClass.do?classId="+classId+"&<cms:Token mode='param'/>";
 					
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
           		
			            
			               if('success' == msg)
			               {
			               				$.dialog(
									    { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/succ.png', 
									    				
									                    content: "删除专题分类成功！",
							
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

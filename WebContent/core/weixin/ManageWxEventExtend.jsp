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
							<a href="#">微信公众号</a> &raquo;
							<a href="#">事件扩展接口</a> 
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
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 11px;" class="">
									<div class="fl">
									类型:
									<select class="form-select" id="type" onchange="javascript:change(this.value)"  >
										<option value="">
											-- 所有扩展接口 --
										</option>
										<option value="menu">
												菜单事件
										</option>
										<option  value="msg">
												消息事件
										</option>
										<option  value="input">
												 用户输入
										</option>	
									</select>
									&nbsp;
								</div>
									
									<div>
										<a href="javascript:openCreateWxExtendDialog();" class="btnwithico" onclick=""><img src="../style/icons/zone--plus.png" width="16" height="16" /><b>添加事件接口&nbsp;</b> </a>
										<span>(响应微信事件的扩展接口，必须为实现Behavior接口的JAVA类)</span>
										</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">



												<td width="2%">
													<strong>ID</strong
												</td>
												<td width="8%">
													<strong>接口名称</strong>
												</td>

												<td width="8%">
													<strong>事件类型</strong>
												</td>
												
												<td width="16%">
													<strong>扩展权限业务类</strong>
												</td>

												<td width="3%">
													<strong>是否启用</strong>
												</td>
												 
												
												<td width="6%">
													<center><strong>操作</strong></center>
												</td>

												
											</tr>
											<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxExtendForTag" objName="WE" var=",,${param.type}">

												<tr>

													<td>
													${WE.ebId}
													</td>
													
													<td>														
															${WE.ebName}
													</td>
													
													<td>
															${WE.eventType}
													</td>		
																					
													<td>														
															${WE.beClass}											
													</td>
													
													<td>
															<cms:if test="${WE.useStatus==1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
													</td>
													
													<td>
															<div>
																<center>
																	<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditWxExtendDialog('${WE.ebId}');">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteWxExtend('${WE.ebId}');">删除</a> 
																	</span>
																</center>
															</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="WE">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
											</cms:Empty>

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
<cms:LoginUser>
<script>

initSelect('type', '${param.type}');

function openCreateWxExtendDialog()
{
	 
	$.dialog({ 
		id:'ocwed',
    	title :'新增扩展接口',
    	width: '700px', 
    	height: '300px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/CreateWxExtend.jsp'
	});
}

function openEditWxExtendDialog(id)
{
	 

	$.dialog({ 
		id:'oewed',
    	title :'编辑扩展接口',
    	width: '700px', 
    	height: '320px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/EditWxExtend.jsp?ebId='+id
	});
}

function deleteWxExtend(id, isSys)
{
	 
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除微信扩展接口吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>wx/deleteWxExtend.do?ebId="+id+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
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
				    				
				                    content: '删除微信扩展接口成功!',
				                    
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

function change(val)
{
	replaceUrlParam(window.location, 'type='+val);
}



</script>
</cms:LoginUser>

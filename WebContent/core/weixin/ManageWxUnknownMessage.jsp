<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
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
							<a href="#">默认信息回复管理</a> 
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
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										<a href="javascript:openCreateWxUnkMessageDialog();" class="btnwithico" onclick=""><img src="../style/icons/zone--plus.png" width="16" height="16" /><b>添加默认回复&nbsp;</b> </a>
											 (此功能在微信用户输入信息无法识别的情况下触发)
										<span></span>
							
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
												 
												 
 
												<td width="8%">
													<strong>输入信息类型</strong>
												</td>
											 
												
												<td width="16%">
													<strong>自动回复信息</strong>
												</td>

												 
												
												<td width="8%">
													<center><strong>操作</strong></center>
												</td>

												
											</tr>
											<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUnkMessageForTag" objName="WM" var="">
 
												<tr>

												 	 
													 
												 
													
													<td>
															&nbsp;${WM.msgType}
													</td>		
														
													 									
													<td>		
													
													
													<cms:if test="${WM.infoId != -1}">
															<cms:QueryData objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var=",,,,${WM.infoId}">																			
																								
															[图文${News.infoId}]  ${News.title}	
															</cms:QueryData>		
													
													</cms:if>

													<cms:elseif  test="${WM.resId != -1}">
													
														<cms:QueryData objName="WR" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxResForTag"  var="${WM.resId},,,,">
														 		
														 		[${WR.resTypeStr}${WR.wrId}]  ${WR.resTitle}
														 	
														 </cms:QueryData>
													
													</cms:elseif>
													<cms:elseif  test="${WM.isText == 1}">
													
													 	   
													
													</cms:elseif>
													<cms:else>
													 		
													</cms:else>
													
													<input type="hidden" id="mtId" name="infoId"  class="form-input" value="${WM.infoId}"></input>
													<input type="hidden" id="resId" name="resId"  class="form-input" value="${WM.resId}"></input>
												 
													 
												</td>
													
													
														
																				
													</td>
													
													 
													
													<td>
															<div>
																<center>
																	<span class="STYLE4">&nbsp;&nbsp;<a href="javascript:openEditWxUnkMessageDialog('${WM.msgType}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteWxUnkMsg('${WM.msgType}');">删除</a> 
																	</span>
																</center>
															</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="WM">
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

 

function search()
{
	var key = encodeURI(encodeURI(document.getElementById('searchKey').value));
	
	window.location='ManageWxMessage.jsp?key='+encodeData(key);
}

function openCreateWxUnkMessageDialog()
{
	 
	$.dialog({ 
		id:'ocwmd',
    	title :'新增默认信息回复',
    	width: '700px', 
    	height: '200px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/CreateWxUnkMessage.jsp'
	});
}

function openEditWxUnkMessageDialog(id)
{
	 

	$.dialog({ 
		id: 'oewmd',
    	title :'编辑默认信息回复',
    	width: '700px', 
    	height: '200px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/EditWxUnkMessage.jsp?msgType='+id
	});
}



function deleteWxUnkMsg(id)
{
	 
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除微信默认回复吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>wx/deleteWxUnkMsg.do?msgType="+id+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除微信默认回复成功!',
				                    
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
							
									    				cancel: function () 
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



</script>
</cms:LoginUser>

<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

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
						<a href="#">站点相关词</a> &raquo; 敏感词管理
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
										<a href="javascript:resumeEmInfo('video');" class="btnwithico" onclick=""><img src="../style/icons/tick.png" width="16" height="16" /><b>恢复视频组件&nbsp;</b> </a>
										<a href="javascript:resumeEmInfo('image');" class="btnwithico" onclick=""><img src="../style/icons/tick.png" width="16" height="16" /><b>恢复图片组件&nbsp;</b> </a>
										<a href="javascript:resumeEmInfo('file');" class="btnwithico" onclick=""><img src="../style/icons/tick.png" width="16" height="16" /><b>恢复附件组件&nbsp;</b> </a>

										<span style="align:middle">(若编辑器组件代码修改失败,或模板代码丢失,请恢复到默认数据)</span>
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
												<td width="2%">
													<strong>ID</strong>
												</td>
											
												<td width="10%">
													<strong>组件名称</strong>
												</td>
												
												<td width="20%">
													<strong>描叙</strong>
												</td>

												<td width="8%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:QueryData service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getEditorModuleTag" objName="Editor">

												<tr>
													<td>
														${Editor.emId}
													</td>
													
													<td>
														${Editor.emName}
													</td>
													
													<td>
														${Editor.emDesc}
													</td>
													

													<td>
														
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditEditorModuleCodeDialog('${Editor.emType}')">&nbsp;编辑组件代码</a>&nbsp;&nbsp;</span>
															</center>
														
													</td>
												</tr>

											</cms:QueryData>
											
											<cms:Empty flag="Editor">
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



function openEditEditorModuleCodeDialog(type)
{
	$.dialog({ 
	    id : 'oeemcd',
    	title : '编辑编辑器组件代码',
    	width: '780px', 
    	height: '570px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/tool/EditEditorModuleCode.jsp?type='+type
	});
}

function resumeEmInfo(type)
{
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认恢复到默认数据吗？',
                    
                    ok: function () { 
       
                     var url = "<cms:BasePath/>channel/reEditorCode.do?type="+type+"&<cms:Token mode='param'/>";
                    

 		
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
							    				width: '160px', 
							    				height: '60px', 
							                    lock: true,
							    				icon: '32X32/i.png', 
							    		
							                    content: '恢复编辑器组件信息成功!',
					
							    				ok:function()
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

<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

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

      var firstTypeId;
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
						<a href="#">站点资源</a> &raquo;
						<a href="#">模板风格管理</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="flForm" name="flForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
									 
									</div>
									<div>
										<a href="javascript:openAddTsDialog();" class="btnwithico" onclick=""><img src="../style/icons/projection-screen--plus.png" width="16" height="16" /><b>添加模板风格&nbsp;</b> </a>
										 <a href="javascript:changeStyle('1');" class="btnwithico" > <img src="../style/icons/light-bulb.png" alt="" /><b>启用&nbsp;</b> </a>
										<a href="javascript:changeStyle('0');" class="btnwithico" > <img src="../style/icons/light-bulb-off.png" alt="" /><b>停用&nbsp;</b> </a>
							 	 	
									</div>
									<div  >
									(当切换模板风格成功后，请重新发布静态页。若启用了资源发布点，需要重新同步)
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

												<td width="1%">
												</td>
												
												<td width="9%">
													<strong>效果图</strong>
												</td>
												<td width="12%">
													<strong>模板风格名</strong>
												</td>

												

												<td width="12%">
													<strong>风格标识</strong>
												</td>
												
												<td width="5%">
													<strong>是否使用</strong>
												</td>

												 

												<td width="8%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:QueryData objName="Ts" service="cn.com.mjsoft.cms.templet.service.TempletService" method="getTemplateStyleForTag" var="">
												<tr>
													<td>
														${Ts.tsId}
													</td>
													<td>
														<input class="inputRadio"  name="checkedId" value="${Ts.tsId}" type="radio" onclick="javascript:" />
													</td>
													
													<td align="center">
														<div style="height:6px;"></div>
														<cms:if test="${empty Ts.stylePic}">
															<a class="cmsSysShowSingleImage" href="../style/blue/images/no-image.png"><img id="contentImageShow" src="../style/blue/images/no-image.png" width="105" height="77" />
															</a>

														</cms:if>
														<cms:else>
															<cms:ResInfo res="${Ts.stylePic}">
																<a class="cmsSysShowSingleImage" href="${Res.url}"><img id="contentImageShow" src="${Res.resize}" width="105" height="77" />
																</a>
															</cms:ResInfo>
														</cms:else>



														<div style="height:6px;"></div>
													</td>
													<td>
														${Ts.styleName}
													</td>
													

													<td>
														${Ts.styleFlag}
													</td>
													
												 
													<td>
															<cms:if test="${Ts.isUse == 1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
													</td>

												 
													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditTsDialog('${Ts.tsId}');">&nbsp;编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteTs('${Ts.tsId}');">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>
											</cms:QueryData>

											<cms:Empty flag="Ts">
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
<script type="text/javascript">

initSelect('typeId','${param.typeId}');

function openAddTsDialog()
{
	$.dialog({ 
		id: 'oasld',
    	title :'新增模板风格',
    	width: '560px', 
    	height: '280px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/templet/CreateTemplateStyle.jsp?dialogApiId=oasld&typeId='+$('#typeId').val()
	});
}

function openEditTsDialog(id)
{
	$.dialog({ 
		id: 'oesld',
    	title :'编辑模板风格',
    	width: '560px', 
    	height: '280px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/templet/EditTemplateStyle.jsp?dialogApiId=oesld&tsId='+id
	});
}


function changeStyle(flag)
{
	var cidCheck = document.getElementsByName('checkedId');
 
	var id='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			id += cidCheck[i].value ;
		}
	}
	
	if('' == id)
	{
	   $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要切换的模板风格！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	 $.dialog.tips('正在切换模板风格，请耐心等待！',3600000000,'loading.gif');
	
	var url = "<cms:BasePath/>templet/changeTs.do?tsId="+id+"&flag="+flag+"&<cms:Token mode='param'/>";
 		 
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
				    				width: '260px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '切换模板成功！请前往&nbsp;[模板管理]&nbsp;查看',
				                    
				                    ok: function () { 
				                    	window.location.reload();
				                    }
				                })			               		
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


function deleteTs(ids)
{
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
                    
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选模板风格吗？',
                    
                    ok: function () 
                    { 
                    
                   		var tip = $.dialog.tips('正在删除模板风格！',3600000000,'loading.gif');
	
	
                    	var url = "<cms:BasePath/>templet/deleteTs.do?ids="+ids+"&<cms:Token mode='param'/>";
	
				
 		
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
									    				width: '160px', 
									    				height: '60px', 
									                    lock: true, 
									                   
									    				icon: '32X32/i.png', 
									    				
									                    content: '删除模板风格成功!',
									                    
									                    ok: function () 
									                    { 
									                    	window.location.reload();
									                    }
									                    
					    								
					                                   	});
								               		
								               	
								               } 	
								               else if('onuse' == msg)
								               {
								               		tip.close();
								               		
								               		$.dialog({ 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                   
									    				icon: '32X32/fail.png', 
									    				
									                    content: '无法删除正在使用的风格!',
									                    
									                    ok: function () 
									                    { 
									                    	window.location.reload();
									                    }
									                    
					    								
					                                   	});
								               }
								               else
								               {
								               		tip.close();
								               		
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

 

function change()
{
	replaceUrlParam(window.location,'typeId='+$('#typeId').val());
}

//图片查看效果
loadImageShow();
  

</script>

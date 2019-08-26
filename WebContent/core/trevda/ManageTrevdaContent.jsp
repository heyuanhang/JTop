<%@ page contentType="text/html; charset=utf-8" session="false"%>

<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<!--加载 js -->
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
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
						<a href="#">交互信息</a> &raquo;
						<a href="#">广告内容管理</a>
					</td>
					<td align="right"></td>
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
							<td style="padding: 6px 11px;" class="">
								<div class="fl">
									版位
									<select id="adPos" onchange="javascript:changeAdvertPos(this.value)" name="adPos" class="form-select">
										<option value="-1">
											所有版位 &nbsp;&nbsp;&nbsp;
										</option>
										<cms:SystemAdvertPos posId="all">
											<option value="${Pos.posId}">
												${Pos.posName}&nbsp;&nbsp;&nbsp;
											</option>
										</cms:SystemAdvertPos>
									</select>
									&nbsp;
								</div>
								<div>
									<a href="javascript:openCreateAdvertDialog();" class="btnwithico"> <img src="../style/icons/megaphone--plus.png" alt="" /><b>添加广告&nbsp;</b> </a>
									<a href="javascript:changeAdvertStatus('off');" class="btnwithico" > <img src="../style/icons/light-bulb-off.png" alt="" /><b>停用&nbsp;</b> </a>
									<a href="javascript:changeAdvertStatus('on');" class="btnwithico" > <img src="../style/icons/light-bulb.png" alt="" /><b>启用&nbsp;</b> </a>
									<a href="javascript:deleteAdvertContent();" class="btnwithico" > <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
								&nbsp;(请关闭浏览器广告屏蔽插件以管理广告)
								</div>
								<div class="fr">
								</div>

							</td>
						<tr>
							<td id="uid_td25" style="padding: 1px 9px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="3%" height="30">
												<strong>ID</strong>
											</td>
											<td width="2%" height="30">
												<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkedId',this);"/>
											</td>
											<td width="13%">
												<strong>广告名称</strong>
											</td>

											<td width="7%">
												<strong>引用名称</strong>
											</td>


											<td width="11%">
												<strong>所属版位</strong>
											</td>

											

											<td width="6%">
												<strong>投放日期</strong>
											</td>

											<td width="6%">
												<strong>下线日期</strong>
											</td>

											<td width="3%">
												<strong>状态</strong>
											</td>



											<td width="11%">
												<center><strong>维护</strong></center>
											</td>
										</tr>


										<cms:SystemAdvertContent posId="${param.posId}">
											<tr>
												<td>
													${Advert.advertId}
												</td>
												<td>
													<input class="form-checkbox" name="checkedId" value="${Advert.advertId}" type="checkbox"  />
												</td>

												<td>
													${Advert.adName}
												</td>

												<td>
													${Advert.adFlag}
												</td>
												<td>
													<cms:SystemAdvertPos posId="${Advert.posId}">														
													${Pos.posName}
													</cms:SystemAdvertPos>
												</td>

												

												<td>
													<cms:FormatDate date="${Advert.showStartDate}" format="yyyy-MM-dd" />
												</td>

												<td>
													<cms:FormatDate date="${Advert.showEndDate}" checkDate="9999" replace="永久有效" format="yyyy-MM-dd" />
												</td>

												<td>
													<cms:if test="${Advert.useState==1}">
														启用
													</cms:if>
													<cms:else>
														<font color="red">停用</font>
													</cms:else>
												</td>


												<td>
													<div>
														<center>
															<span ><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditAdvertDialog('${Advert.advertId}')">&nbsp;编辑</a>&nbsp; &nbsp; <img src="../style/icons/monitor-window.png" width="16" height="16" /><a href="javascript:perviewAdvert('${Advert.advertId}')">&nbsp;预览</a>&nbsp; &nbsp;</span>
														</center>
													</div>
												</td>

											</tr>
										</cms:SystemAdvertContent>
										
										<cms:Empty flag="Advert">
														<tr>
															<td class="tdbgyew" colspan="10">
																<center>
																	当前没有数据!
																</center>
															</td>
														</tr>
									   </cms:Empty>

										

									</table>
								<div class="mainbody-right"></div>
								</div>

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
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script>

initSelect('adPos','${param.posId}');

function openCreateAdvertDialog()
{
	$.dialog({ 
	    id : 'ocad',
    	title : '新建广告',
    	width: '730px', 
    	height: '470px', 
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/trevda/CreateTrevdaContent.jsp?dialogApiId=ocad&posId='+document.getElementById('adPos').value+"&uid="+Math.random()

	});
	

}

function openEditAdvertDialog(advertId)
{
	$.dialog({ 
	    id : 'oeacd',
    	title : '编辑广告',
    	width: '730px', 
    	height: '470px', 
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/trevda/EditTrevdaContent.jsp?dialogApiId=oeacd&advertId='+advertId+"&uid="+Math.random()

	});
}

function deleteAdvertConfig(configId)
{

	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除当前广告配置吗？',
                    
                    ok: function () { 
       
    window.location.href = '<cms:BasePath/>trevda/deleteTrevdaConfig.do?configId='+configId+"&<cms:Token mode='param'/>";
    }, 
    cancel: true 
    });
	
}

function perviewAdvert(advertId)
{
	window.open('component/SystemTrevdaPreview.jsp?advertId='+advertId, "_blank");
}

function changeAdvertPos(posId)
{
	if('-1' != posId)
	{
		window.location.href = 'ManageTrevdaContent.jsp?posId='+posId;	
	}
	else
	{
		 window.location.href = 'ManageTrevdaContent.jsp';	
	}
	
}

function changeAdvertStatus(flag)
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
	
	if('' == ids)
	{
	   $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要处理的广告！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	var url = "<cms:BasePath/>trevda/changeDaStatus.do?ids="+ids+"&flag="+flag+"&<cms:Token mode='param'/>";
 		
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
				    				icon: '32X32/succ.png', 
				    				
				                    content: '改变广告状态成功！',
				                    
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
						
								    				cancel: function () { 
								                    	window.location.reload();
								                    }
									});
			               }   
			              
			            }
			     	});	
	
}


function deleteAdvertContent()
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
	
	if('' == ids)
	{
	   $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要删除的广告！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选广告吗？',
                    
                    ok: function () { 
                    
                    var url = "<cms:BasePath/>trevda/deleteTrevda.do?ids="+ids+"&<cms:Token mode='param'/>";
 		
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
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除广告内容成功！',
				                    
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
       
        
    }, 
    cancel: true 
                    
	
	});
	
}


</script>


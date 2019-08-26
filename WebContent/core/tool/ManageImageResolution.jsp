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
						<a href="#">站点相关词</a> &raquo; 图片规格管理
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
										<a href="javascript:openCreateImageRatioDialog();" class="btnwithico" onclick=""><img src="../style/icons/image-crop.png" width="16" height="16" /><b>新增图片比例&nbsp;</b> </a>
										<a href="javascript:deleteRatio('');" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>									</div>
										<span style="align:middle">(以下图片规格配置将出现图片裁剪功能中，可裁剪出同比例的图片)</span>							
									
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
													<input class="inputCheckbox" onclick="javascript:selectAll('checkId',this);" type="checkbox" />
												</td>
										
												<td width="15%">
													<strong>图片比例名称</strong>
												</td>
												
												<td width="6%">
													<strong>宽度</strong>
												</td>
												
												<td width="8%">
													<strong>高度</strong>
												</td>

												<td width="8%">
													<strong>操作</strong>
												</td>
											</tr>

											<cms:QueryData service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getImageRadioTag" objName="Ra" var="all">

												<tr>
													<td>
														${Ra.irId}
													</td>
													<td>
														<input class="inputCheckbox"  name="checkId" value="${Ra.irId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														${Ra.ratioName}&nbsp;&nbsp;( ${Ra.ratioWidth} x ${Ra.ratioHeight} )
													</td>
													
													<td>
														${Ra.ratioWidth}
													</td>
													
													<td>
														${Ra.ratioHeight}
													</td>
													
													<td>
															<center>
																<span class="STYLE4"><a href="javascript:openEditImageRatioDialog('${Ra.irId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;</span>
																<span class="STYLE4"><a href="javascript:deleteRatio('${Ra.irId}')"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除</a></span>
															</center>
														
													</td>
												</tr>

											</cms:QueryData>
											
											<cms:Empty flag="Ra">
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

			<form id="deleteSystemForm" name="deleteSystemForm" method="post">

				<input type="hidden" id="modelId" name="modelId" value="-1" />

			</form>

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">



function openCreateImageRatioDialog()
{
	$.dialog({ 
	    id : 'ocird',
    	title : '创建图片规格',
    	width: '530px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/tool/CreateImageRatio.jsp'
	});
}


function openEditImageRatioDialog(irId)
{
	$.dialog({ 
	    id : 'oeird',
    	title : '编辑图片规格',
    	width: '530px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/tool/EditImageRatio.jsp?irId='+irId
	});
}



function deleteRatio(id)
{
	 var ids='';
	 
	 if('' == id)
	 {
	 	var cidCheck = document.getElementsByName('checkId');
	
		
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
	                    content: '没有选择任何图片规格！', 
	       				cancel: true 
		  });
		  
		  return;
		}
	 }
	 else
	 {
	 	ids = id;
	 }


	 $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选图片规格吗？！',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>channel/deleteIR.do?ids="+ids+"&<cms:Token mode='param'/>";
 					
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               	 
			               		window.location.reload();
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

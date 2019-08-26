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
						<a href="#">友情链接</a> &raquo;
						<a href="#">友链类别管理</a>
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
										<a href="javascript:openAddSiteLinkDialog();" class="btnwithico" onclick=""><img src="../style/icons/chain-unchain.png" width="16" height="16" /><b>添加友链类型&nbsp;</b> </a>
										<a href="javascript:deleteSiteLinks();" class="btnwithico" onclick=""><img src="../style/icons/document-attribute-x.png" width="16" height="16" /><b>删除&nbsp;</b> </a>
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

												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
												<td width="8%">
													<strong>类别名称</strong>
												</td>

												

												<td width="8%">
													<strong>访问标识</strong>
												</td>

												

												<td width="5%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:QueryData objName="Ft" service="cn.com.mjsoft.cms.interflow.service.InterflowService" method="getFriendSiteTypeTag" >
												<tr>
													<td>
														${Ft.ltId}
													</td>
													<td>
														<input class="inputCheckbox"  name="checkedId" value="${Ft.ltId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														${Ft.typeName}
													</td>
													
													<td>
														${Ft.typeFlag}
													</td>
													
													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditSiteLinkDialog('${Ft.ltId}');">&nbsp;编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteSiteLink('${Ft.ltId}');">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>
											</cms:QueryData>

											<cms:Empty flag="Ft">
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

function openAddSiteLinkDialog()
{
	$.dialog({ 
		id: 'oasld',
    	title :'新增友链类别',
    	width: '560px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/interflow/CreateSiteLinkType.jsp'
	});
}

function openEditSiteLinkDialog(id)
{
	$.dialog({ 
		id: 'oesld',
    	title :'编辑友链类别',
    	width: '560px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/interflow/EditSiteLinkType.jsp?ltId='+id
	});
}


function sortSiteLink()
{
	var url = "<cms:BasePath/>interflow/sortFSite.do"+"?<cms:Token mode='param'/>";
	
	var postData =$("#flForm").serialize();
                    
 		
 				
 		
	$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   
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
				    				
				                    content: '排序操作成功!',
				                    
				                    ok: function () 
				                    { 
				                    	window.location.reload();
				                    }
				                    
    								
                                   	});
			               		
			               	
			               } 	
			               else
			               {
			               	    $.dialog.tips('删除操作失败!...',1);
			               }   
			              
			            }
	});	

}


function deleteSiteLink(ids)
{
	$.dialog({ 
   					title :'提示',
    				width: '260px', 
    				height: '60px', 
                    lock: true, 
                    
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选友链类别吗？所属友链将同时被删除!',
                    
                    ok: function () 
                    { 
                    
                   
                    	var url = "<cms:BasePath/>interflow/deleteFSiteType.do?ids="+ids+"&<cms:Token mode='param'/>";
	
				
 		
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
									    				
									                    content: '删除操作成功!',
									                    
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

function deleteSiteLinks()
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
    				width: '180px', 
    				height: '60px', 
                    lock: true,
                    
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要删除的友链类别！', 
       cancel: true 
                    
	  });
	  return;
	}

	deleteSiteLink(ids);
	
	
	
	

}

//图片查看效果
loadImageShow();
  

</script>

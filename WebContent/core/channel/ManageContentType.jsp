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
						<a href="#">内容组织</a> &raquo; <a href="#">分类管理</a>
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
										<a href="javascript:openCreateCtDialog();" class="btnwithico" ><img src="../../core/style/icons/sticky-notes-pin.png" width="16" height="16" /><b>新增内容分类&nbsp;</b> </a>
										<a href="javascript:deleteType('');" class="btnwithico" ><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
												
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

												<td width="12%">
													<strong>内容分类名称</strong>
												</td>

												<td width="10%">
													<strong>访问标识</strong>
												</td>



												<td width="7%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:SystemContentTypeList>

												<tr>
													<td>
														${ContentType.typeId}
													</td>
													<td>
														<input class="form-checkbox" name="checkedId" value="${ContentType.typeId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														${ContentType.typeName}
													</td>

													<td>
														${ContentType.typeFlag}
													</td>


													<td>
														<center>
															<a href="javascript:openEditCtDialog('${ContentType.typeId}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;
															<a href="javascript:deleteType('${ContentType.typeId}')"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除</a>
														</center>

													</td>
												</tr>

											</cms:SystemContentTypeList>
											
											

											<cms:Empty flag="ContentType">
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



function openCreateCtDialog()
{
	$.dialog({ 
	    id : 'occtd',
    	title : '创建内容类别',
    	width: '580px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/channel/CreateContentType.jsp'
	});
}


function openEditCtDialog(id)
{
	$.dialog({ 
	    id : 'oectd',
    	title : '编辑内容类别',
    	width: '580px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:BasePath/>core/channel/EditContentType.jsp?id='+id
	});
}


function deleteType(id)
{
	 var ids='';
	 
	 if('' == id)
	 {
	 	var cidCheck = document.getElementsByName('checkedId');
	
		
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
	                    content: '没有选择任何类别！', 
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
    				
                    content: '您确认删除所选类别吗？',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>channel/deleteCt.do?ids="+ids+"&<cms:Token mode='param'/>";
 					
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
							                    content: '删除类别成功！', 
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

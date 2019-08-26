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
						<a href="#"></a> &raquo;
						<a href="#">区块类型管理</a>
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
							<td style="padding: 7px 10px;" class="">
								<div class="fl">
									<a href="javascript:openCreateBlockTypeDialog();" class="btnwithico"> <img src="../../core/style/icons/layers-stack-arrange-back.png" alt="" /><b>添加区块分类&nbsp;</b> </a>
									<a href="javascript:deleteBlockTypes();" class="btnwithico" onclick=""> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
								</div>
								<div class="fr">
								</div>

							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table id="showlist" align="center" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="2%" height="30">
												<strong>ID</strong>
											</td>
											<td width="2%" height="30">
												<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkedId',this);" />
											</td>
											<td width="14%">
												<strong>类别名称</strong>
											</td>

											<td width="17%">
												<strong>目标模板</strong>
											</td>

											<td width="10%">
												<center><strong>操作</strong></center>
											</td>
										</tr>


										<cms:SystemBlockType>
											<tr>
												<td>
													${BlockType.blockTypeId}
												</td>
												<td>
													<input type="checkbox" name="checkedId" value="${BlockType.blockTypeId}" />
												</td>

												<td>
													${BlockType.blockTypeName}
												</td>

												<td>
													${BlockType.templateUrl}
												</td>
												<td>
													<div>
														<center>
															<span><a href="javascript:openEditBlockTypeDialog('${BlockType.blockTypeId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp; &nbsp; <a href="javascript:deleteBlockType('${BlockType.blockTypeId}');"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除 </a>&nbsp;&nbsp;&nbsp;</span>
														</center>
													</div>
												</td>

											</tr>
										</cms:SystemBlockType>
										<cms:Empty flag="BlockType">
											<tr>
												<td class="tdbgyew" colspan="9">
													<center>
														当前没有数据!
													</center>
												</td>
											</tr>
										</cms:Empty>

										<tr>
											<td colspan="8" class="PageBar" align="left">
												<div class="fr">

												</div>
												<div class="fl"></div>
											</td>
										</tr>

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
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">

function openCreateBlockTypeDialog()
{
	$.dialog({ 
	    id : 'ocbtd',
    	title : '新建区块分类',
    	width: '580px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/block/CreateBlockType.jsp?uid='+Math.random()

	});
}

function openEditBlockTypeDialog(btId)
{
	$.dialog({ 
	    id : 'oebtd',
    	title : '编辑区块分类',
    	width: '580px', 
    	height: '190px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/block/EditBlockType.jsp?blockTypeId='+btId+'&uid='+Math.random()

	});
}

function deleteBlockTypes()
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
    				
                    content: '请选择需要删除的区块分类！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	deleteBlockType(ids);
}


function deleteBlockType(typeId)
{
	$.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选区块分类吗？',
                    
                    ok: function () 
                    { 
                    
                
                    var url = '<cms:BasePath/>block/deleteBlockType.do?ids='+typeId+"&<cms:Token mode='param'/>";
                    
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
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
				    				
				                    content: '删除所选区块分类成功！',
				                    
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


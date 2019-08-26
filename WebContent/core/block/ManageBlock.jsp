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
						<a href="#">区块管理</a>
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
									筛选:&nbsp;
									<select class="form-select" id="typeId" name="typeId" onchange="javascript:change();">
										<option value="-1">
											------ 所有区块 ------
										</option>
										<cms:SystemBlockType>
											<option value="${BlockType.blockTypeId}">
												${BlockType.blockTypeName}
											</option>
										</cms:SystemBlockType>
									</select>
									&nbsp;
								</div>
								<div>
									<a href="javascript:openCreateBlockDialog();" class="btnwithico"> <img src="../../core/style/icons/layers-group.png" alt="" /><b>添加区块&nbsp;</b> </a>
									<a href="javascript:deleteBlocks();" class="btnwithico" onclick=""> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>

								</div>
								<div class="fr">
								</div>


							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td width="2%" height="30">
												<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkedId',this);" />
											</td>
											<td width="14%">
												<strong>区块名称</strong>
											</td>


											<td width="8%">
												<strong>访问标志</strong>
											</td>


											<td width="6%">
												<strong>发布频率</strong>
											</td>

											<td width="12%">
												<center><strong>操作</strong></center>
											</td>
										</tr>

										<cms:SystemBlockInfo typeId="${param.typeId}">
											<tr>

												<td>
													<cms:if test="${TreeItem.isType == false}">
														<input type="checkbox" name="checkedId" value="${TreeItem.blockId}" />
													</cms:if>
													<cms:else>
														<input type="checkbox" name="checkedId" value="-9999" readonly />
													</cms:else>

												</td>

												<td>
													<cms:if test="${TreeItem.isType == true}">
														<img src="<cms:Domain/>core/style/default/images/t_small.png" />${TreeItem.blockTypeName}
													</cms:if>
													<cms:else>
														&nbsp;&nbsp;&nbsp;&nbsp;<img src="<cms:Domain/>core/style/default/images/control_small.png" />${TreeItem.blockName}
													</cms:else>
												</td>

												<td>
													<cms:if test="${TreeItem.isType == false}">${TreeItem.flag}</cms:if>
													<cms:else />
												</td>

												<td>
													<cms:if test="${TreeItem.isType == false}">
														&nbsp;&nbsp;${TreeItem.pubPeriod}
													</cms:if>

												</td>

												<td>
													<cms:if test="${TreeItem.isType == false}">
														<div>
															<center>
																<span><a href="javascript:openEditCodeAndTextDialog('${TreeItem.templateUrl}','')"><img src="../../core/style/icons/script-code.png" width="16" height="16" />模板</a>&nbsp; &nbsp; <a href="javascript:openManageTemplateEditionDialog('${TreeItem.templateUrl}');"><img src="../../core/style/icons/script-stamp.png" width="16" height="16" />版本</a>&nbsp; &nbsp; <a href="javascript:openEditBlockDialog('${TreeItem.blockId}','${TreeItem.blockName}')"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditBlockDialog('${TreeItem.blockId}','${TreeItem.blockName}')">&nbsp;编辑</a>&nbsp; &nbsp; <a href="javascript:deleteBlock('${TreeItem.blockId}')"><img src="../../core/style/default/images/del.gif" width="16" height="16" />删除 </a>&nbsp;&nbsp;&nbsp; 
																</span>
															</center>
														</div>
													</cms:if>
													<cms:else>
														<div>
															<center>
																<span><img src="../../core/style/icons/script-code.png" width="16" height="16" /><a href="javascript:openEditCodeAndTextDialog('${TreeItem.mainTemplateUrl}','')">父模板</a>&nbsp; &nbsp; <a href="javascript:openManageTemplateEditionDialog('${TreeItem.mainTemplateUrl}');"><img src="../../core/style/icons/script-stamp.png" width="16" height="16" />版本</a>&nbsp; &nbsp; 
															</center>
														</div>
													</cms:else>
												</td>

											</tr>

										</cms:SystemBlockInfo>
										<cms:Empty flag="TreeItem">
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

initSelect('typeId','${param.typeId}');

function openCreateBlockDialog()
{
	$.dialog({ 
	    id : 'ocbd',
    	title : '添加区块',
    	width: '650px', 
    	height: '430px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/block/CreateBlock.jsp?uid='+Math.random()

	});
}

function openEditBlockDialog(blockId, blockName)
{
	$.dialog({ 
	    id : 'oebd',
    	title : '编辑区块 - '+blockName,
    	width: '650px', 
    	height: '430px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/block/EditBlock.jsp?blockId='+blockId+'&uid='+Math.random()

	});
}


function deleteBlocks()
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
    				
                    content: '请选择需要删除的区块！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	deleteBlock(ids);
}


function deleteBlock(blockId)
{
	$.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选区块吗？',
                    
                    ok: function () 
                    { 
                    
                
                    var url = '<cms:BasePath/>block/deleteBlock.do?ids='+blockId+"&<cms:Token mode='param'/>";
                    
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   			dataType:'json',
			   			
			       		success: function(msg)
			            {     
			               if('success' == msg)
			               {
			               		$.dialog({ 
				   					title :'提示',
				    				width: '160px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除所选区块成功！',
				                    
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



function openEditCodeAndTextDialog(entry,type)
{
	if(entry == null || '' == entry)
	{
		$.dialog.tips('模板不存在...',1); 
		return;
	}

    entry = entry.replaceAll('/','*');
    
    entry = entry.replaceAll('\\\\','*');
    
    entry = '*template'+entry;
   
	if('css' == type)
	{
		type = 'text/css';
	}
	else if('js' == type)
	{
		type = 'text/javascript';
	}
	else
	{
		type = 'text/html';
	}
	
	var url = '<cms:Domain/>resources/directViewPage.do?entry='+encodeURIComponent(encodeURIComponent(entry))+'&type='+type;
	
	window.open(url,'','width=1400,height=780，top=20,left=40,toolbar=no,menubar=no,scrollbars=yes,titlebar=no, resizable=no,location=no, status=no');
	
}


function openManageTemplateEditionDialog(fileEntry)
{
	if(fileEntry == null || '' == fileEntry)
	{
		$.dialog.tips('模板不存在...',1); 
		return;
	}

	fileName = fileEntry.substring(fileEntry.lastIndexOf('/')+1,fileEntry.length);
	
	fileName = fileName.substring(fileName.lastIndexOf('\\')+1,fileName.length);
	
	fileEntry = fileEntry.replaceAll('/','*');
    
    fileEntry = fileEntry.replaceAll('\\\\','*');
    
    fileEntry = '*template'+fileEntry;
	
	if(fileEntry == null || '' == fileEntry)
	{
		$.dialog.tips('模板不存在...',1); 
		return;
	}
	

	$.dialog({ 
	    id : 'omted',
    	title : '查看历史版本 : '+fileName,
    	width: '920px', 
    	height: '500px',
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/templet/ManageTemplateEdition.jsp?tName='+fileName+'&fileEntry='+fileEntry
	});
}

function change()
{
	replaceUrlParam(window.location,'typeId='+$('#typeId').val());
}


</script>


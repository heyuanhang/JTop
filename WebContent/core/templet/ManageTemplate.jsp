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
			
			
			 
	         if("true"==="${param.deleteFlag}")
	         {   
	         	$.dialog.tips('删除文件成功...',1,'32X32/succ.png');  	 	
	         }
			
			
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
						<a href="#">模板管理</a>
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
							<td style="padding: 7px 10px;">
								<div class="fl">
									<a href="javascript:backToParentFolder('${param.parentFolder}');" class="btnwithico"> <img src="../style/blue/icon/folder_up.png" alt="" /><b>返回上层&nbsp;</b> </a>
							 		<a href="javascript:openCreateFileDialog('${param.parentFolder}');" class="btnwithico"> <img src="../../core/style/icons/script--plus.png" alt="" /><b>新建&nbsp;</b> </a>
									
									<a href="javascript:openSelectFolerDialog();" class="btnwithico"> <img src="../../core/style/icons/script--arrow.png" alt="" /><b>移动&nbsp;</b> </a>	
									<a href="javascript:openUploadDialog('${param.parentFolder}');" class="btnwithico"> <img src="../../core/style/icons/drive-upload.png" alt="" /><b>上传&nbsp;</b> </a>
									<a href="javascript:downloadRes();" class="btnwithico"> <img src="../../core/style/icons/drive-download.png" alt="" /><b>下载&nbsp;</b> </a>											
									<a href="javascript:deleteCheckedFile();" class="btnwithico" onclick=""> <img src="../../core/style/icons/script--minus.png" alt="" /><b>删除&nbsp;</b> </a>
									<a href="javascript:reload();" class="btnwithico" onclick=""> <img src="../../core/style/icons/arrow-circle.png" alt="" /><b>刷新&nbsp;</b> </a>
									(支持ZIP文件自解压和多文件下载)
								</div>
								<div class="fr">
								</div>
							</td>
						<tr>
						<tr>
							<td style="padding: 2px 6px;">
								路径:
									<input type="text" id="showPathTrace" style="width:96%" class="form-input" readonly />
									<div style="height:4px;"></div>
							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											
											<td width="1%" height="30">
												<input type="checkbox" id="fileAllCheckbox" name="fileAllCheckbox" value="all" onclick="javascript:checkAllFile()" />
											</td>
											<td width="14%">
												<strong>模板文件名</strong>
											</td>
											
											<td width="12%">
												<strong>模板标识说明</strong>
											</td>

											<td width="4%">
												<strong>大小</strong>
											</td>

											<td width="5%">
												<strong>修改时间</strong>
											</td>

											<td width="7%">
												<center><strong>操作</strong></center>
											</td>
										</tr>

										<cms:SystemFileResource entry="${param.parentFolder}"  >
											<tr>
												
												<td>
													<input type="checkbox" name="fileCheckbox" value="${File.fileName}" />
												</td>

												<td>
													<cms:if test="${File.isDir==true}">
														<a href="javascript:goToFolder('${param.parentFolder}*<cms:JsEncode str="${File.fileName}"/>');"> &nbsp;<img src="../style/blue/icon/${File.icon}" width="16" height="16" />&nbsp;${File.fileName} </a>
													</cms:if>
													<cms:elseif test="${File.type=='css' || File.type=='js' || File.type=='jsp' || File.type=='thtml' || File.type=='ftl' || File.type=='html' || File.type=='shtml' || File.type=='htm' || File.type=='txt'}">
														<a href="javascript:openEditCodeAndTextDialog('${param.parentFolder}*<cms:JsEncode str="${File.fileName}"/>','${File.type}')"> &nbsp;<img src="../style/blue/icon/${File.icon}" width="16" height="16" />&nbsp;${File.fileName} </a>
													</cms:elseif>
													<cms:elseif test="${File.type=='jpg' || File.type=='jpeg' || File.type=='png' || File.type=='gif' || File.type=='bmp' }">
														<a class="cmsSysShowSingleImage"  href="${File.fullPath}"> &nbsp;<img src="../style/blue/icon/${File.icon}" width="16" height="16" />&nbsp;${File.fileName} </a>
													</cms:elseif>
													<cms:else>
														&nbsp;&nbsp;<img src="../style/blue/icon/${File.icon}" width="16" height="16" />&nbsp;${File.fileName}
													</cms:else>
												</td>
												
												<td>
													<cms:if test="${File.isDir==false}">
														<cms:QueryData service="cn.com.mjsoft.cms.templet.service.TempletService" method="getTemplateHelperForTag" var="${File.fileName}" objName="Tip">
															
															 <input type="text" size="36" value="${Tip.templetDisplayName}" class="form-input" readonly/> 
															
														</cms:QueryData>
													</cms:if>
													<cms:else>
														  文件夹
													</cms:else>
												</td>

												<td>
													${File.size} KB
												</td>

												<td>
													${File.lastModifyTime}
												</td>

												<td>
													<span >
													<center>
													<cms:if test="${File.isDir != true}">
														
															<a href="javascript:openManageTemplateEditionDialog('<cms:JsEncode str="${File.fileName}"/>','${param.parentFolder}*<cms:JsEncode str="${File.fileName}"/>');"><img src="../../core/style/icons/script-stamp.png" width="16" height="16" />版本</a>&nbsp; &nbsp;
														
													</cms:if>
													<cms:if test="${File.isDir==true}">
														<a href="javascript:openRenameFileDialog('${param.parentFolder}*<cms:JsEncode str="${File.fileName}"/>','<cms:JsEncode str="${File.fileName}"/>','folder');"><img src="../../core/style/icons/script-attribute-r.png" alt="" />改名</a>
													
													</cms:if>
													<cms:else>
														<a href="javascript:openRenameFileDialog('${param.parentFolder}*<cms:JsEncode str="${File.fileName}"/>','<cms:JsEncode str="${File.fileName}"/>','file');"><img src="../../core/style/icons/script-attribute-r.png" alt="" />改名</a>												
													</cms:else>
														</center>
													</span>
												</td>


												<%--<td>
													<div>
														<center>
															<span ><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditAdvertDialog('${Advert.advertId}')">编辑</a>&nbsp; &nbsp; <img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:perviewAdvert('${Advert.advertId}')">预览</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif"
																	width="16" height="16" /><a href="javascript:deleteAdvertConfig('${Config.configId}');">删除 </a>&nbsp;&nbsp;&nbsp;</span>
														</center>
													</div>
												</td>

											--%>
											</tr>
										</cms:SystemFileResource>


										<tr>
											<td colspan="12" class="PageBar" align="left">
												<div class="fr">

												</div>
												<div class="fl"></div>
											</td>
										</tr>

									</table>

									<form id="deleteFileForm" name="deleteFileForm" method="post">
										<input type="hidden" id="fileInfo" name="fileInfo" />
										<input type="hidden" id="entry" name="entry" value="${param.parentFolder}" />
										<cms:Token mode="html"/>
									</form>
									
									<form id="downFileForm" name="downFileForm" method="post">
										<input type="hidden" id="downFileInfo" name="downFileInfo" />
										<cms:Token mode="html"/>
									</form>
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

var parFolder = '${param.parentFolder}';

if(parFolder == '')
{
	//parFolder = ' / template';
}

document.getElementById('showPathTrace').value=decodeURIComponent(parFolder.replaceAll('\\*',' / '));

if(document.getElementById('showPathTrace').value == '')
{
	document.getElementById('showPathTrace').value = ' / ';
}


//图片查看效果
loadImageShow();

function checkAllFile()
{
	var allFileCheckBox = document.getElementsByName("fileCheckbox");
	var actCheckBox = document.getElementById("fileAllCheckbox");
	
	for(var i = 0; i < allFileCheckBox.length; i++)
	{
		if(actCheckBox.checked)
		{
			allFileCheckBox[i].checked = true;
		}
		else
		{
			allFileCheckBox[i].checked = false;
		}	
	}
} 

function createFileOrFolder()
{
	//createSiteFile
}

function deleteCheckedFile()
{
	var checkedIdArray = new Array();
	var allFileCheckBox = document.getElementsByName('fileCheckbox');
    for(var i = 0; i < allFileCheckBox.length; i++)
	{
		if(allFileCheckBox[i].checked == true)
		{
			checkedIdArray.push(allFileCheckBox[i].value);
		}
	}
	
	if(encodeURIComponent(checkedIdArray.join('*')) == '')
	{
		$.dialog.tips('没有选择任何文件...',1); 
	
		return;
	}
	
	var dialog = $.dialog({ 
	                id:'dcf',
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png',   				
                    content: '您确认删除所选文件吗？',
                    
                    ok: function () { 
       				
       				document.getElementById('fileInfo').value = encodeData(encodeURIComponent(checkedIdArray.join('*')));
       				
       				var form = document.getElementById('deleteFileForm');
       				form.action = '<cms:BasePath/>resources/deleteSiteFile.do?templateMode=true';
       				form.submit();
       			

    }, 
    cancel: true 
    });
}



function backToParentFolder(parentFolder)
{
	var parentEntry = parentFolder.substring(0,parentFolder.lastIndexOf('*'));
	
	if('' == parentEntry)
	{
		parentEntry = '*template';
	}
	
	window.location.href = "ManageTemplate.jsp?parentFolder="+encodeData(encodeURIComponent(encodeURIComponent(parentEntry)));
}

function goToFolder(targetFolder)
{

	window.location.href = "ManageTemplate.jsp?parentFolder="+encodeData(encodeURIComponent(encodeURIComponent(targetFolder)));
}


function openEditCodeAndTextDialog(entry,type)
{
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
	
	var postData = encodeURI(encodeURIComponent(entry));

	postData = encodeData(postData);
	 
	var url = '<cms:Domain/>resources/directViewPage.do?entry='+postData+'&type='+type;
	
	window.open(url,'','width=1300,height=875，top=60,left=260,toolbar=no,menubar=no,scrollbars=yes,titlebar=no, resizable=no,location=no, status=no');
	
}

function openCreateFileDialog(entry)
{
	$.dialog({ 
		id:'oud',
    	title :'新建文件夹',
    	width: '520px', 
    	height: '120px', 
    	lock: true, 
        max: false,
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/CreateFileOrFolder.jsp?entry='+encodeData(encodeURIComponent(encodeURIComponent(entry)))
	});
}

function openRenameFileDialog(entry,name,type)
{
	var nameTemp = name.substring(0,name.lastIndexOf('.'));
	
	if(nameTemp == '')
	{
		nameTemp = name;
	}

	$.dialog({ 
		id:'oud',
    	title :'改名:文件或文件夹',
    	width: '580px', 
    	height: '180px', 
    	lock: true, 
        max: false,
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/templet/RenameTemplate.jsp?entry='+encodeData(encodeURIComponent(encodeURIComponent(entry)))+'&type='+type+'&fn='+encodeData(encodeURIComponent(encodeURIComponent(nameTemp)))
	});
}


function openUploadDialog(entry)
{
	$.dialog({ 
		id:'oud',
    	title :'上传站点文件',
    	width: '550px', 
    	height: '180px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/UploadSiteFile.jsp?entry='+encodeData(encodeURIComponent(encodeURIComponent(entry)))
	});
}

function openManageTemplateEditionDialog(fileName, fileEntry)
{ 
	var nameTemp = fileName.toLowerCase();
	
	if(nameTemp.lastIndexOf('.thtml')+6 != nameTemp.length && nameTemp.lastIndexOf('.jsp')+4 != nameTemp.length && nameTemp.lastIndexOf('.css')+4 != nameTemp.length && nameTemp.lastIndexOf('.js')+3 != nameTemp.length && nameTemp.lastIndexOf('.html')+5 != nameTemp.length)
	{
	 	$.dialog.tips('此文件类型不支持版本记录功能',2); 
	
		return;
	}

	$.dialog({ 
	    id : 'omted',
    	title : '查看历史版本 : '+fileName,
    	width: '1080px', 
    	height: '650px',
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/templet/ManageTemplateEdition.jsp?tName='+encodeData(encodeURIComponent(encodeURIComponent(fileName)))+'&fileEntry='+encodeData(encodeURIComponent(encodeURIComponent(fileEntry)))
	});
}


function openSelectFolerDialog()
{
	var checkedIdArray = new Array();
    
	var allFileCheckBox = document.getElementsByName('fileCheckbox');
	
    for(var i = 0; i < allFileCheckBox.length; i++)
	{
		if(allFileCheckBox[i].checked == true)
		{
			checkedIdArray.push(allFileCheckBox[i].value);
		}
	}
	
	if(encodeURIComponent(checkedIdArray.join('*')) == '')
	{
		$.dialog.tips('没有选择任何文件...',1); 
		
		
		return;
	}



	$.dialog({ 
		id:'ostd',
    	title :'选择目标文件夹',
    	width: '700px', 
    	height: '466px', 
    	lock: true, 
        max: false, 
        min: false, 
       
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/ShowFolder.jsp'
	});


}

function downloadRes()
{
	var checkedIdArray = new Array();
    
	var allFileCheckBox = document.getElementsByName('fileCheckbox');
	
    for(var i = 0; i < allFileCheckBox.length; i++)
	{
		if(allFileCheckBox[i].checked == true)
		{
			checkedIdArray.push(allFileCheckBox[i].value);
		}
	}
	
	if(encodeURIComponent(checkedIdArray.join('*')) == '')
	{
		$.dialog.tips('没有选择任何文件...',1); 
	
		return;
	}
	
	$.dialog.tips('正在打包文件...',2,'loading.gif'); 
	
	document.getElementById('downFileInfo').value = encodeData(encodeURIComponent(checkedIdArray.join('*')));
	
	var dfForm = document.getElementById('downFileForm');
	
	dfForm.action = '<cms:BasePath/>resources/downloadResFile.do?entry=${param.parentFolder}';
	
	dfForm.submit();
}

function reload()
{
	window.location.href = window.location.href;
}


</script>


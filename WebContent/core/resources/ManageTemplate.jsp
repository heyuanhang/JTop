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
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td style="padding: 2px 6px;">
								<div class="fl">
									<a href="javascript:backToParentFolder('${param.parentFolder}');" class="btnwithico"> <img src="../style/blue/icon/folder_up.png" alt="" /><b>返回上层&nbsp;</b> </a>
									<a href="javascript:openCreateFileDialog('${param.parentFolder}');" class="btnwithico" > <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>新建&nbsp;</b> </a>
									<a href="javascript:;" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>改名&nbsp;</b> </a>
									<a href="javascript:openUploadDialog('${param.parentFolder}');" class="btnwithico"> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>上传&nbsp;</b> </a>
									<a href="javascript:deleteCheckedFile();" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
									<a href="javascript:reload();" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>刷新&nbsp;</b> </a>

								</div>
								<div class="fr">
								</div>
							</td>
						<tr>
						<tr>
							<td style="padding: 2px 6px;">
								<div class="fl">
									路径:
									<input type="text" id="showPathTrace" size="150" class="form-input" readonly/>
									&nbsp;
								</div>


								<div class="fr">
								</div>




							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="2%" height="30">
												<strong>ID</strong>
											</td>
											<td width="2%" height="30">
												<input type="checkbox" id="fileAllCheckbox" name="fileAllCheckbox" value="all" onclick="javascript:checkAllFile()" />
											</td>
											<td width="30%">
												<strong>文件名称</strong>
											</td>

											<td width="6%">
												<strong>文件大小</strong>
											</td>


											<td width="6%">
												<strong>类型</strong>
											</td>

											<td width="9%">
												<strong>修改时间</strong>
											</td>



										</tr>


										<cms:SystemFileResource entry="${param.parentFolder}" >
											<tr >
												<td>
													${status.index+1}
												</td>
												<td>
													<input type="checkbox" name="fileCheckbox" value="${File.fileName}" />
												</td>

												<td>
													<cms:if test="${File.isDir==true}">
														<a href="javascript:javascript:goToFolder('${param.parentFolder}*${File.fileName}');"> &nbsp;<img src="../style/blue/icon/${File.icon}" width="16" height="16" />&nbsp;${File.fileName} </a>
													</cms:if>
													<cms:elseif test="${File.type=='css' || File.type=='js' || File.type=='jsp' || File.type=='html' || File.type=='shtml' || File.type=='htm' || File.type=='txt'}">
														<a href="javascript:openEditCodeAndTextDialog('${param.parentFolder}*${File.fileName}','${File.type}')"> &nbsp;<img src="../style/blue/icon/${File.icon}" width="16" height="16" />&nbsp;${File.fileName} </a>
													</cms:elseif>
													<cms:else>
														&nbsp;<img src="../style/blue/icon/${File.icon}" width="16" height="16" />&nbsp;${File.fileName}
													</cms:else>
												</td>

												<td>
													${File.size} KB
												</td>

												<td>
													${File.type}
												</td>

												<td>
													${File.lastModifyTime}
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

<script type="text/javascript">
document.getElementById('showPathTrace').value='${param.parentFolder}'.replaceAll('\\*',' / ');

if(document.getElementById('showPathTrace').value == '')
{
	document.getElementById('showPathTrace').value = ' / ';
}

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
	
	var dialog = $.dialog({ 
	                id:'dcf',
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png',   				
                    content: '您确认删除所选文件吗？',
                    
                    ok: function () { 
       				
       				document.getElementById('fileInfo').value = encodeURIComponent(checkedIdArray.join('*'));
       				
       				var form = document.getElementById('deleteFileForm');
       				form.action = '../../resources/deleteSiteFile.do';
       				form.submit();
       			
            	    window.parent.frames["treeFrame"].location.reload(); 
     
   // window.location.href = '../../resources/deleteSiteFile.do?fileInfo='++"&entry=${param.parentFolder}";
   // window.location.href =  window.location.href;
    }, 
    cancel: true 
    });
}






function backToParentFolder(parentFolder)
{
	var parentEntry = parentFolder.substring(0,parentFolder.lastIndexOf('*'));
	
	window.location.href = "ListFileRes.jsp?parentFolder="+encodeURIComponent(encodeURIComponent(parentEntry));
}

function goToFolder(targetFolder)
{

	window.location.href = "ManageTemplate.jsp?parentFolder="+encodeURIComponent(encodeURIComponent(targetFolder));
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
	
	
	
	$.dialog({ 
    	title :'编辑文本内容',
    	width: '1200px', 
    	height: '580px', 
    	lock: true, 
        max: false, 
       
        
        resize: false,
             
        content: 'url:<cms:Domain/>resources/directViewPage.do?entry='+encodeURIComponent(encodeURIComponent(entry))+'&type='+type
	});
}

function openCreateFileDialog(entry)
{

	$.dialog({ 
		id:'oud',
    	title :'新建文件或文件夹',
    	width: '350px', 
    	height: '100px', 
    	lock: true, 
        max: false, 
       
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/CreateFileOrFolder.jsp?entry='+encodeURIComponent(encodeURIComponent(entry))
	});
}


function openUploadDialog(entry)
{

	$.dialog({ 
		id:'oud',
    	title :'上传站点文件',
    	width: '450px', 
    	height: '100px', 
    	lock: true, 
        max: false, 
       
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/UploadSiteFile.jsp?entry='+encodeURIComponent(encodeURIComponent(entry))
	});
}

function reload()
{
	window.location.href = window.location.href;
}


</script>


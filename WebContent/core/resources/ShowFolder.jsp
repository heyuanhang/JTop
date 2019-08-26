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
		<script>
			var api = frameElement.api, W = api.opener; 
		          
			if("true"==="${param.fromFlow}")
         	{         
         		api.close();     
            	W.window.location.reload();
            	W.window.parent.frames["treeFrame"].location.reload();     			
             	
        	 }
			
			
		</script>	

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
									<a href="javascript:backEnterFolder('${param.entry}');" class="btnwithico"> <img src="../style/blue/icon/folder_up.png" alt="" /><b>返回上层&nbsp;</b> </a> &nbsp;&nbsp;&nbsp;路径:
									<input type="text" id="showPathTrace" size="99" class="form-input" readonly />
									&nbsp;
								</div>
								<div class="fr">
									<div class="fl">

									</div>

								</div>
							</td>
						<tr>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="2%">

											</td>

											<td width="30%">

												<strong>模板全名 
											</td>
											<td width="10%">
												<strong>文件大小 
											</td>




										</tr>


										<cms:TempletFile entry="${param.entry}" folderMode="true" >
											<cms:if test="${TempletFile.dir==true}">
												<tr>
													<td>
														<div align="center">

														</div>
													</td>
													<td ondblclick="javascript:enterFolder('${TempletFile.entry}');">
														<a href="javascript:enterFolder('${TempletFile.entry}');">&nbsp;&nbsp;<img src='../style/blue/icon/folder.gif' />${TempletFile.fileName}</a>
													</td>
													<td>

													</td>
												</tr>

											</cms:if>

										</cms:TempletFile>
										<cms:Empty flag="TempletFile">
											<tr>
												<td class="tdbgyew" colspan="9">
													<center>
														没有子文件夹!
													</center>
												</td>
											</tr>
										</cms:Empty>
									</table>
								</div>
							</td>
						</tr>
					</table>

					<div style="height:40px"></div>

					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:selectTempletFile();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>

							</tr>
						</table>
					</div>
				</td>
			</tr>
			
			<form id="moveFileForm" name="moveFileForm" method="post">
				<input type="hidden" id="fileInfo" name="fileInfo" />
				<input type="hidden" id="target" name="target"  />	
				<cms:Token mode="html"/>		
			</form>

		</table>

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">
var entryPath = '${param.entry}';

if(entryPath == '')
{
	entryPath = '/';
}

document.getElementById('showPathTrace').value=decodeURIComponent(entryPath.replaceAll('\\*',' / '));

var api = frameElement.api, W = api.opener; 

function close()
{
	api.close();
}

function enterFolder(entry)
{
	window.location.href = "ShowFolder.jsp?mode=${param.mode}&entry="+encodeURIComponent(encodeURIComponent(entry))+'&apiId=${param.apiId}&objId=${param.objId}';
}

function backEnterFolder(entry)
{
	var parentEntry = entry.substring(0,entry.lastIndexOf('*'));
	
	window.location.href = "ShowFolder.jsp?mode=${param.mode}&entry="+encodeURIComponent(encodeURIComponent(parentEntry))+'&apiId=${param.apiId}&objId=${param.objId}';
}

function selectTempletFile()
{
	if(entryPath == '/')
	{
		entryPath = '';
	}

   
    
    var checkedIdArray = new Array();
    
	var allFileCheckBox = W.document.getElementsByName('fileCheckbox');
	
    for(var i = 0; i < allFileCheckBox.length; i++)
	{
		if(allFileCheckBox[i].checked == true)
		{
			checkedIdArray.push(allFileCheckBox[i].value);
		}
	}
	
	
	document.getElementById('target').value = '*template'+entryPath;
	
	document.getElementById('fileInfo').value = encodeURIComponent(checkedIdArray.join('*'));
	
 
	if(entryPath != '' && document.getElementById('fileInfo').value.indexOf(entryPath) != -1)
	{
		W.$.dialog.tips('目标目录已被选为需移动目录,不可移动...',3); 
		return;
	}
	
	W.$.dialog.tips('正在移动文件...',400000,'loading.gif'); 
	     
    var form = document.getElementById('moveFileForm');
    
	form.action = '<cms:BasePath/>resources/moveFile.do?entry='+encodeURIComponent(W.document.getElementById('entry').value);
	//alert(W.document.getElementById('entry').value);
	form.submit();
   
    
}

function radioClick(divNode)
{
	
    var entry = divNode.id;
    
    var fs =  document.getElementsByName('selectFile');
    
    var check =false;
    
    for(var i=0; i<fs.length;i++)
    {
      if(fs[i].value==entry)
      {
         
        fs[i].click();
         break;
      }
    }

}








</script>


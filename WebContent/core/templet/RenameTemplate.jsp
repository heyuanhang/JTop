<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<title></title>
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script>
			
			if("true"==="${param.fromFlow}")
         	{        
      			var api = frameElement.api, W = api.opener; 
		          
             	api.close();     
            	W.window.location.reload();
            	W.window.parent.frames["treeFrame"].location.reload(); 
        	 }
        	 
        	 var ref_flag=/^[\w-_]{1,85}$/; 
          
	         $(function()
			 {
			 	validate('newName',0,ref_flag,'格式为字母,数字,上下划线');	
			    //validate('fileTip',0,ref_name,'格式为文字,数字,上下划线');
	 			
	 				
			 })
			
			
		</script>
	</head>
	<body>
	
	<form id="renameForm" name="renameForm" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
				

				 
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							
							<tr>
								<td width="23%" class="input-title">
									<strong>新名称</strong>
								</td>
								<td class="td-input">
									<input type="text" size="44" id="newName" name="newName" class="form-input" value='<cms:DecodeParam  str="${param.fn}"/>'></input>
									<br/><span class="ps">系统不允许修改文件类型,只可修改名称</span>
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>标识名称</strong>
								</td>
								<td class="td-input">
									<input type="text" size="44" id="fileTip" name="fileTip" class="form-input" value=""></input>
									 
								</td>
							</tr>
							
							
							

						</table>
						<!-- hidden -->
							<input type="hidden" id="entry" name="entry" value="${param.entry}" />
							
							<input type="hidden" id="oldName" name="oldName" value="${param.fn}" />
							
							<input type="hidden" id="type" name="type" value="${param.type}" />
							
							<cms:Token mode="html"/>		
					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a  href="javascript:renameFileOrFolder();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" id="submitTagClassFormImg"/><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>
				</td>
			</tr>
	
	
	
	</body>
</html>

<script type="text/javascript">

function renameFileOrFolder()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('newName',0,ref_flag,'格式为字母,数字,上下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    
    
    
    			
    if(hasError)
    {
    	 
	    
	    return;

	}
	
	var nfn = $('#newName').val();
	
	if(nfn.trim() == '')
	{
		$('#newName').val('${param.fn}');
	}

	encodeFormInput('renameForm', false);
	
	var form = document.getElementById('renameForm');
	form.action = '<cms:BasePath/>resources/renameFileOrFolder.do';
	form.submit();
}


function close()
{
	var api = frameElement.api, W = api.opener; 	          
    api.close();      
}
				
									
</script>


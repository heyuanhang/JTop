<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		 
		<!-- 配置文件 -->
		<script type="text/javascript" src="../javascript/ueditor/ueditor.config.js"></script>
		<!-- 编辑器源码文件 -->
		<script type="text/javascript" src="../javascript/ueditor/ueditor.all.gzjs"></script>
		
		<script type="text/javascript" charset="utf-8" src="../javascript/ueditor/lang/zh-cn/zh-cn.js"></script>
		
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>
		
		<cms:Set val="'fullscreen', 'source', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough','removeformat','forecolor', 'backcolor', 'formatmatch', 'autotypeset', 'link', 'unlink', 'anchor','fontfamily', 'fontsize','undo', 'redo'" id="UE_SMP" />
		
		<script>  
	 		var api = frameElement.api, W = api.opener; 
			 
	         if("true"==="${param.fromFlow}")
	         {     	 	
	            api.close(); 
	            
	       		W.window.location.reload();
	         }
        </script>
	</head>
	<body>
	<cms:CurrentSite>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/blue/icon/application-import.png" width="16" height="16" />
						消息模板
					</div>
					<cms:SystemList querySign="query_single_msg_template" objName="MGT" var="${param.id}">
						<form id="mgtForm" name="mgtForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="18%" class="input-title">
										<strong>名称</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:184px" id="templateName" name="templateName" class="form-input" value="${MGT.templateName}"></input>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<span class="input-title"><strong>标识</strong> </span>
										<input type="text" style="width:184px" id="mtFlag" name="mtFlag" class="form-input" value="${MGT.mtFlag}"></input>
									</td>
								</tr>
								<tr>
									<td class="input-title">
										<strong>标题</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:438px" id="templateTitle" name="templateTitle" class="form-input" value="${MGT.templateTitle}"></input>

									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>模板内容</strong>
									</td>
									<td class="td-input">
										<textarea id="templateContent" name="templateContent" style="height:280px;width:442px" >${MGT.templateContent}</textarea>
										<input type="hidden" id="templateContent_jtop_sys_hidden_temp_html" name="templateContent_jtop_sys_hidden_temp_html" >
									
										<script type="text/javascript">
						               var editor1_templateContent = UE.getEditor('templateContent',{zIndex : 99, autoFloatEnabled:false, allowDivTransToP: false, enableAutoSave:false, scaleEnabled:true, maximumWords:20000, toolbars: [[${UE_SMP}]]}); 
									</script>
									</td>
								</tr>
								<tr>
									<td class="input-title">
										<strong>内容变量</strong>
									</td>
									<td class="td-input">
										<select class="form-select" onchange="javascript:selectParamForUE(this,'templateContent')">
											<option value="">
												----- 备选参数 -----
											</option>

											<cms:SystemList querySign="query_mg_template_param" objName="MGTP" var="${CurrSite.siteId}">
												<option value="{${MGTP.paramFlag}}">
													${MGTP.paramName}
												</option>
											</cms:SystemList>
										</select>
									</td>
								</tr>



							</table>
							<!-- hidden -->
							<input type="hidden" id="mtId" name="mtId" value="${MGT.mtId}"></input>
							
							<cms:Token mode="html"/>


						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitMsgTemplate();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
									</div>
									 
								</tr>
							</table>
						</div>
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

function submitMsgTemplate()
{
	encodeFormInput('mgtForm', false);
	
	var form = document.getElementById("mgtForm");
	
	form.action = '<cms:BasePath/>member/editMT.do';
	
	form.submit();
}


function close()
{
	api.close();
}
  
</script>
</cms:SystemList>
</cms:CurrentSite>

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
	         	W.$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '160px', 
			    	height: '60px', 
			        lock: true, 
			    	icon: '32X32/succ.png', 
			    	parent: api,
			        content: '发送会员信息成功!', 
			       	ok: function()
			       	{
			       		W.window.location.reload();
			       	}
				 });
	         	
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
							发送会员消息
						</div>
						
						<cms:QueryData querySign="query_single_msg_template" objName="MTP" var="${param.mtId}" single="true">	
					
						<form id="mgtForm" name="mgtForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

								<tr>
									<td width="16%" class="input-title">
										<strong>标题</strong>
									</td>
									<td class="td-input">
										<input type="text" style="width:440px" id="templateTitle" name="templateTitle" class="form-input" value="${MTP.templateTitle}"></input>

									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>模板内容</strong>
									</td>
									<td class="td-input">
										<textarea id="templateContent" name="templateContent" style="height:300px;width:442px"  >${MTP.templateContent}</textarea>
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
										<select class="form-select" onchange="javascript:selectParamForFCK(this,'templateContent')" style="width:165px">
											<option value="">
												------- 备选参数 -------
											</option>

											<cms:SystemList querySign="query_mg_template_param" objName="MGTP" var="${CurrSite.siteId}">
												<option value="{${MGTP.paramFlag}}">
													${MGTP.paramName}
												</option>
											</cms:SystemList>

										</select>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<span class="input-title"><strong>可选模板</strong>
										<select id="tempId" class="form-select" onchange="javascript:selectTemplate(this);" style="width:165px">
											<option value="-1">
												------- 手动书写 -------
											</option>

											<cms:QueryData querySign="SELECT_MEMBER_MG_TEMPLATE" objName="Tmp" var="${CurrSite.siteId}">
												<option value="${Tmp.mtId}">
													${Tmp.templateName}
												</option>
											</cms:QueryData>

										</select>
										</span>
									</td>
								</tr>



							</table>
						
							
							</cms:QueryData>
							
							<!-- hidden -->
							<cms:Token mode="html"/>

						</form>
						<div style="height:15px"></div>
						<div class="breadnavTab">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a name="btnwithicosysflag" href="javascript:submitMsg();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>发送消息&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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
initSelect('tempId','${param.mtId}');

function submitMsg()
{
	var cidCheck = W.document.getElementsByName('checkedMemberId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	} 
	
	if(ids == '')
	{
		alert('请选择ID');
		return;
	}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	encodeFormInput('mgtForm', false);
	
	var form = document.getElementById("mgtForm");
	
	form.action = '<cms:BasePath/>member/sendMsg.do?ids='+ids;
	
	form.submit();

	
 	
}

function selectTemplate(obj)
{
	window.location.href='SystemManagerSendMessage.jsp?mtId='+obj.value;
}


function close()
{
	api.close();
}
  
</script>
</cms:CurrentSite>

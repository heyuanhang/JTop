<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script>  
		basePath = '<cms:BasePath/>';
		
		
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );    
       		W.window.location.reload();       
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('questName',0,ref_name,'格式为文字,数字,下划线');
 			validate('flagName',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
    
      	</script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					
					<div class="addtit">
							<img src="../style/icons/report.png" width="16" height="16" />配置
					</div>


					<form id="surveyGroupForm" name="surveyGroupForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
									<td width="24%" class="input-title">
										<strong>所属栏目: 
									</td>
									<td class="td-input">
										<select class="form-select" id="classId" name="classId" style="width:316px;">
											<option value="-1">
												---------------&nbsp;请选择调查所属栏目&nbsp;----------------
											</option>
											<option value="-9999">
												全站共用调查问卷
											</option>
											<cms:CurrentSite>
												<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
													<cms:SysClass>
														<option value="${Class.classId}">
															${Class.layerUIBlankClassName}
														</option>
													</cms:SysClass>
												</cms:SystemClassList>
											</cms:CurrentSite>
										</select>
									</td>
								</tr>
							<tr>
								<td class="input-title">
									<strong>调查名称
								</td>
								<td class="td-input">
									<input id="questName" name="questName" type="text" class="form-input" style="width:316px;" />
									 <span class="red">*</span><span class="ps"></span>
								</td>
							</tr>


							<tr>
								<td class="input-title">
									<strong>访问标识</strong>
								</td>
								<td class="td-input">
									<input id="flagName" name="flagName" type="text" class="form-input" style="width:316px;" />
									 <span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>描叙</strong>
								</td>
								<td class="td-input">
									<textarea id="questDesc" name="questDesc" class="form-textarea" style="width:316px; height:60px;"></textarea>
							</tr>

							<tr>
								<td class="input-title">
									<strong>投票时间</strong>
								</td>
								<td class="td-input">

									<input id="startDate" name="startDate" style="width:140px;" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" />
									&nbsp;&nbsp;至&nbsp;&nbsp;
									<input  id="endDate" name="endDate" style="width:140px;" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" />

								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>投票限制:</strong>
								</td>
								<td class="td-input">
									<input name="restriction" type="radio" value="1" checked />
									<span class="STYLE12">按访问用户唯一性限制</span>&nbsp;&nbsp;
									<input name="restriction" type="radio" value="2" />
									<span class="STYLE12">按照IP地址唯一性限制</span>&nbsp;
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>间隔时间:</strong>
								</td>
								<td class="td-input">
									<input id="restInterval" name="restInterval" type="text" class="form-input" style="width:100px;" value="24" />
									&nbsp;小时&nbsp;<span class="ps">投票间隔期限期间不可进行投票</span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>投票验证码:</strong>
								</td>
								<td class="td-input">
									<input name="needCaptcha" type="radio" value="1" checked />
									<span class="STYLE12">全部需要</span>
									<input name="needCaptcha" type="radio" value="2" />
									<span class="STYLE12">会员不需</span>
									<input name="needCaptcha" type="radio" value="0" />
									<span class="STYLE12">全部不需</span>
								
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>投票状态 </strong>
								</td>
								<td class="td-input">
									<input name="useState" type="radio" value="1" checked />
									<span class="STYLE12">启用</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input name="useState" type="radio" value="0" />
									<span class="STYLE12">停用</span>
								</td>
							</tr>

						</table>
						<!-- hidden -->
						<cms:Token mode="html"/>

					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:submitSurveyGroupForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>
							</tr>
						</table>
					</div>
				</td>
			</tr>


		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  
//showTips('modelName','不可为空');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
function submitSurveyGroupForm()
{
   if($('#classId').val() == '-1')
    {
    	W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择一个范围栏目!',

		    				cancel: true
		});
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('flagName',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('questName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	 
	    
	    return;
	}
	
	//后台验证
	
	
		var count = validateExistFlag('surveyGroup', $('#flagName').val());
		
		if(count > 0)
		{
		
			showTips('flagName', '系统已存在此值，不可重复录入');
			
			return;
		}
	
	disableAnchorElementByName("btnwithicosysflag",true);
		
	var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
	
	encodeFormInput('surveyGroupForm', false);
	
   var questInfoForm = document.getElementById('surveyGroupForm');
   questInfoForm.action="<cms:BasePath/>survey/createSurveyGroup.do";
    
   questInfoForm.submit();
}

   
function showSelectTempletDialog(mode)
{
	   var templetValue;	
	   var targetTemplet = window.showModalDialog("../channel/SelectChannelTemplet.jsp?random="+Math.random(),"","dialogWidth=950px;dialogHeight=500px;status=no");
	   
       if(targetTemplet=="")
       {
       		return;
       }
       
       templetValue = targetTemplet.replaceAll('\\*','/');
       
     
       
       document.getElementById("templateUrl").value=templetValue;
       
}
  
</script>

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
		
		<script>
		basePath='<cms:BasePath/>';
		 
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '150px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    		
		                    content: '新增系统模型成功!',

		    				ok:function()
		    				{ 
       							W.window.location.reload();  
		    				}
			});     
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('modelName',0,ref_name,'格式为文字,数字,下划线');
 			validate('modelSign',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
       
      	</script>
	</head>
	<body>

		<cms:SystemDataModel id="${param.modelId}">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/ruler--pencil.png" width="16" height="16" />基本信息
					</div>

					<form id="modelForm" name="modelForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="22%" class="input-title">
									<strong>模型名称: 
								</td>
								<td class="td-input">
									<input id="modelName" name="modelName" type="text" class="form-input" style="width:155px" value="${DataModel.modelName}"/>
									<span class="red">*</span><span class="ps"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span class="input-title"><strong>标识: </span>
									<input readonly id="modelSign" name="modelSign" type="text" class="form-input" style="width:155px" value="${DataModel.modelSign}"/>
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

						 

							<tr>
								<td class="input-title">
									<strong>描叙:</strong>
								</td>
								<td class="td-input">
									<textarea id="remark" name="remark" class="form-textarea" style="width:390px; height:70px;">${DataModel.remark}</textarea>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>验证接口: 
								</td>
								<td class="td-input">
									<input id="validateBehavior" name="validateBehavior" type="text" class="form-input" style="width:390px" value="${DataModel.validateBehavior}"/>
									<br/>
									<span class="ps">当增加或改动模型内容时,将执行配置类业务验证逻辑,并返回错误提示</span>
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>前处理接口: 
								</td>
								<td class="td-input">
									<input id="beforeBehavior" name="beforeBehavior" type="text" class="form-input" style="width:390px" value="${DataModel.beforeBehavior}" />
									<br/>
									<span class="ps">当用户提交增加或改动模型内容请求,系统将执行配置类中数据处理逻辑</span>
								</td>
							</tr>
							
						
							
							<tr>
								<td class="input-title">
									<strong>后处理接口: 
								</td>
								<td class="td-input">
									<input id="afterBehavior" name="afterBehavior" type="text" class="form-input" style="width:390px" value="${DataModel.afterBehavior}" />
									<br/>
									<span class="ps">当处理增加或改动模型内容请求成功,系统将执行配置类中数据处理逻辑</span>
								</td>
							</tr>
							

							<tr>
								<td class="input-title">
									<strong>私有模型: 
								</td>
								<td class="td-input">
									<input type="radio" name="privateMode" value="0" />
									否
									<input type="radio" name="privateMode" value="1" />
									是
									<span class="ps">若为私有模型,则只在当前站点有效,其他站点不可使用</span>
								</td>
							</tr>
							
							
							
							<tr>
								<td class="input-title">
									<strong>需验证码: </strong>
								</td>
								<td class="td-input">
									<input type="radio" name="mustToken" value="1"  />
									是
									<input type="radio" name="mustToken" value="0" />
									否
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span class="input-title"><strong>管理员可编辑:</strong> </span>
									<input type="radio" name="isManageEdit" value="1" />
									是
									<input type="radio" name="isManageEdit" value="0"  />
									否
									 
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>需要审核: </strong>
								</td>
								<td class="td-input">
									<input type="radio" name="mustCensor" value="1"  />
									是
									<input type="radio" name="mustCensor" value="0"/>
									否
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<span class="input-title"><strong>会员可编辑:</strong> </span>
									<input type="radio" name="isMemberEdit" value="1" />
									是
									<input type="radio" name="isMemberEdit" value="0"  />
									否
									 
								</td>
							</tr>
							
							<tr>
									<td class="input-title">
										<strong>引导图标</strong>
									</td>
									<td class="td-input">
									
									
										<input type="text" size="25" id="ico" name="ico" class="form-input" value="${DataModel.ico}" />
										
										<input onclick="javascript:openSelectIconDialog('oem');" value="选择" class="btn-1" type="button" />
										
										
										<img id="icoImg" style="vertical-align: middle;" src="<cms:Domain/>core/style/icons/${DataModel.ico}" height="16" width="16" />
										
										
									</td>

								</tr>


						</table>
						<!-- hidden -->
					    <input type="hidden" id="modelType" name="modelType" value="${param.modelType}" />

						<input type="hidden" id="dataModelId" name="dataModelId" value="${param.modelId}" />
							
						<cms:Token mode="html"/>

					</form>
						<div style="height:15px"></div>
					<div class="breadnavTab"  >
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr class="btnbg100">
							<div style="float:right">
								<a name="btnwithicosysflag" href="javascript:submitModelForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
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

initRadio('privateMode','${DataModel.privateMode}');
initRadio('isManageEdit','${DataModel.isManageEdit}');
initRadio('isMemberEdit','${DataModel.isMemberEdit}');
initRadio('mustCensor','${DataModel.mustCensor}');
initRadio('mustToken','${DataModel.mustToken}');

function submitModelForm()
{
  var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('modelSign',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('modelName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	encodeFormInput('modelForm', false);
	
   modelForm.action="<cms:BasePath/>metadata/editDataModel.do";
   modelForm.submit();
}

function openSelectIconDialog(target)
{
	W.$.dialog({ 
		    id : 'oscsd',
	    	title : '选取图标',
	    	width: '440px', 
	    	height: '580px', 
	    	parent:api,
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:<cms:BasePath/>core/resources/ShowIcon.jsp?uid='+Math.random()+'&target='+target
	
	});
}

function close()
{
	api.close();
}
   
  
</script>
</cms:SystemDataModel>

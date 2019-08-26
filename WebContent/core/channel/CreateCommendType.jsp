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
			    				width: '130px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    		
			                    content: '新建推荐位配置成功!',
	
			    				ok:function()
			    				{ 
	             					W.location.reload();
			    				}
			});           
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('commendName',0,ref_name,'格式为文字,数字,下划线');
 			validate('commFlag',0,ref_flag,'格式为字母,数字,下划线');	
 				
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/medal.png" width="16" height="16" />配置
					</div>

					<form id="typeForm" name="typeForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="27%" class="input-title">
									<strong>所属栏目: 
								</td>
								<td class="td-input">
									<select class="form-select" id="classId" name="classId" style="width:265px">
										<option value="-1">
											------------&nbsp;推荐位类型&nbsp;------------
										</option>
										<option value="-9999">
											全站共用推荐位
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
									<strong>扩展模型: 
								</td>
								<td class="td-input">
									<select id="modelId" name="modelId" class="form-select" style="width:265px">
															<option value="-1">
																-----------&nbsp;可选内容模型-----------&nbsp;
																</span>&nbsp;
															</option>

															<cms:SystemDataModelList modelType="10">
																<cms:SystemDataModel>
																	<option value="${DataModel.dataModelId}">
																		${DataModel.modelName}
																		</span>&nbsp;
																	</option>
																</cms:SystemDataModel>
															</cms:SystemDataModelList>
									</select>
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>推荐位名称: 
								</td>
								<td  class="td-input">
									<input id="commendName" name="commendName" type="text" class="form-input" style="width:262px" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>访问标识: 
								</td>
								<td class="td-input">
									<input id="commFlag" name="commFlag" type="text" class="form-input" style="width:262px" />
									<span class="red">*</span><span class="ps"></span>
								</td>
							</tr>




							<tr>
								<td class="input-title">
									<strong>描叙:</strong>
								</td>
								<td class="td-input">
									<textarea id="typeDesc" name="typeDesc" class="form-textarea" style="width:259px; height:50px;"></textarea>
							</tr>

							<tr>
								<td class="input-title">
									<strong>推荐范围: 
								</td>
								<td class="td-input">
									<input type="radio" name="childClassMode" value="1" checked />
									子栏目也可推荐
									<input type="radio" name="childClassMode" value="0" />
									只推荐所属栏目
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>编辑推荐: 
								</td>
								<td class="td-input">
									<input type="radio" name="mustCensor" value="0" checked />
									数据直接生效 &nbsp;&nbsp;
									<input type="radio" name="mustCensor" value="1" />
									需要经过审核
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>引导图规格: 
								</td>
								<td class="td-input">
									<input type="text" size="6" id="imageWidth" name="imageWidth" class="form-input" ></input>
									宽度 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="text" size="6" id="imageHeight" name="imageHeight" class="form-input" ></input>
									高度
								</td>
							</tr>
							
							


						</table>
						<!-- hidden -->
						<cms:Token mode="html"/>

					</form>
					<div style="height:10px"></div>
					<div class="breadnavTab" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						<tr class="btnbg100">
							<div style="float:right">
								<a name="btnwithicosysflag" href="javascript:submitTypeForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
								<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
							</div>
							 
						</tr>
					</table>
					</div>
				</td>
			</tr>


			</tr>
		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  
 
function close()
{
	api.close();
}

function submitTypeForm()
{
	if($('#classId').val() == '-1')
    {
    	showTips('classId', '请选择一个推荐归属栏目');
			
    	 
		
		return;
    }
	
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('commFlag',0,ref_flag,'格式为字母,数字,下划线');	
        
        if(currError)
        {
        	hasError = true;
        }
        
    currError = submitValidate('commendName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
        
        
    
    
    			
    if(hasError)
    {
    	 
	    
	    return;

	}
	
	//后台验证
	
	
		var count = validateExistFlag('commType', $('#commFlag').val());
		
		if(count > 0)
		{

			showTips('commFlag', '系统已存在此值，不可重复录入');
			
			return;
		}
		
   disableAnchorElementByName("btnwithicosysflag",true);
		
   var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
   
   encodeFormInput('typeForm', false);
	
   var typeForm = document.getElementById('typeForm');
   typeForm.action="<cms:BasePath/>channel/createCommendType.do";
   typeForm.submit();
}

   
  
</script>

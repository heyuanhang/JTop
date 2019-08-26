<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		
		<script type="text/javascript" src="../../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />



		<script>  
		 basePath = '<cms:BasePath/>';
		
		 var api = frameElement.api, W = api.opener; 
		 
		 var dialogApiId = '${param.dialogApiId}';
		
		 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '160px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '编辑专题内容成功!',
	
			    				ok: function()
			    				{
			    					W.window.location.reload();       
			    				}
			   });        
         }
         
         $(function()
		 {
		    validate('title',1,null,null);
 	
 			
 			//图片查看效果
 		  	loadImageShow();	
 				
		 })
    
      	</script>
	</head>
	<body>

		<cms:SystemCommendType typeId="${param.typeId}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../../style/icons/document-text-image.png" width="16" height="16" />内容信息
						</div>
						
						<cms:SysCommendContent infoId="${param.infoId}">
				
						<form id="commForm" name="commForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="22%" class="input-title">
										<strong>标题: 
									</td>
									<td class="td-input">
										<input id="title" name="title" type="text" value="${CommInfo.title}" maxLength="80" style="width: 375px;" class="form-input-title titlerule" />
										<input type="button" value="选取内容" onclick="javascript:openSelectSiteAndPushContentDialog();" class="btn-1" />
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>

								<tr>
									<td class="input-title">
										<strong>URL链接: 
									</td>
									<td class="td-input">
										<input id="url" name="url" type="text" class="form-input" maxLength="600" style="width: 375px;" value="${CommInfo.urlOrgVal}"/>

									</td>
								</tr>


								<tr>
									<td class="input-title">
										<strong>引导图: 
									</td>
									<td class="td-input">
										<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
															<tr>
																<td>
																	<a class="cmsSysShowSingleImage" id="imgCmsSysShowSingleImage" href="${CommInfo.img}"><img id="imgCmsSysImgShow" src="<cms:SystemResizeImgUrl reUrl="${CommInfo.imgReUrl}" />" width="90" height="67" /> </a>
																</td>
																<td>
																	<table border="0" cellpadding="0" cellspacing="0" height="65" class="form-table-big">
																		<tr>
																			<td>
																				&nbsp;
																				<input type="button" onclick="javascript:showModuleImageDialog('imgCmsSysImgShow','img','','','0','0',false)" value="上传" onclick="" class="btn-1" />
																				<input type="button" value="裁剪" onclick="javascript:disposeImage('img','','',false,'-1');" class="btn-1" />
																				<input type="button" value="删除" onclick="javascript:deleteImage('img');" class="btn-1" />
																			</td>
																		</tr>
																		<tr>
																			<td>
																				&nbsp;&nbsp;宽&nbsp;&nbsp;
																				<input id="imgCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" value="${CommInfo.imgImageW}" />
																				&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																				<input id="imgCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" value="${CommInfo.imgImageH}" />


																			</td>
																		</tr>
																	</table>
																	<input id="img" name="img" type="hidden" value="${CommInfo.imgResId}" />
																	<input id="img_sys_jtopcms_old" name="img_sys_jtopcms_old" type="hidden" value="${CommInfo.imgResId}"/>
																</td>
															</tr>
														</table>
									</td>
								</tr>


								<tr>
									<td class="input-title">
										<strong>简介:</strong>
									</td>
									<td class="td-input">
										<textarea id="summary" name="summary" class="form-textarea" style="width:380px; height:70px;">${CommInfo.summary}</textarea>
								</tr>
								</cms:SysCommendContent>
								<tr>
									<td class="input-title">
										<strong>行列位置: 
									</td>
									<td class="td-input">
										当前为
										<select id="infoColumn" class="form-select"  disabled>

											<cms:SysCommendContent flag="${CommendType.commFlag}" >
												<option value="${CommInfo.rowFlag}">
													第 ${status.index+1} 行
												</option>
											</cms:SysCommendContent>
											
										</select>
										&nbsp;&nbsp;
										<input type="checkbox" class="form-checkbox" id="inCol" name="inCol" value="true" onclick="javascript:enableRowSelect(this);" checked disabled/>
										之中 &nbsp;&nbsp;
										<select id="infoRow" name="infoRow" class="form-select" disabled>

											
										</select>
									</td>

								</tr>


							</table>
							<!-- hidden -->
							<cms:SysCommendContent infoId="${param.infoId}">
							<input type="hidden" id="infoId" name="infoId" value="${param.infoId}" />
							
							<input type="hidden" id="classId" name="classId" value="${param.classId}" />
							<input type="hidden" id="typeId" name="typeId" value="${param.typeId}" />
							<input type="hidden" id="contentId" name="contentId" value="${CommInfo.contentId}" />
							<input type="hidden" id="modelId" name="modelId" value="${CommInfo.modelId}" />

							<input type="hidden" id="commendTypeId" name="commendTypeId" value="${CommendType.commendTypeId}" />
							<input type="hidden" id="commendFlag" name="commendFlag" value="${CommendType.commFlag}" />
							
							<input type="hidden" id="flag" name="flag" value="" />
							
							<cms:Token mode="html"/>
						</form>
						<div style="height:16px"></div>
						<div class="breadnavTab" >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:sortRow('left','${CommInfo.infoId}');"  class="btnwithico"><img src="../../style/icons/arrow-180.png" width="16" height="16" /><b>左移&nbsp;</b> </a>
										<a href="javascript:sortRow('right','${CommInfo.infoId}');"  class="btnwithico" onclick=""><img src="../../style/icons/arrow.png" width="16" height="16"/><b>右移&nbsp;</b> </a>
							
										<a href="javascript:submitCommForm();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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

initSelect('infoColumn','${CommInfo.rowFlag}');

initRowOption('${CommInfo.rowFlag}','${CommInfo.rowOrder}');

 
function close()
{
	api.close();
	W.window.location.reload();
	
}


function openSelectSiteAndPushContentDialog()
{
	W.$.dialog({ 
	    id : 'aospcd',
    	title : '[ ${CommendType.commendName} ] - 选取站点内容',
    	width: '800px', 
    	height: '560px',
    	parent:api, 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/content/dialog/SelectSiteAndPushContent.jsp?&single=true&dialogId=oeccd&uid='+Math.random()+'&typeId=${param.typeId}'+'&classId=${param.classId}'
	});
}

function initRowOption(opt,order)
{	
     if('newCol' == opt)
     {
     	$("#infoRow").empty();
     	$("#infoRow").append('<option value="1">第 1 列 </option>');	  
     	return;
     }
     
	 var url = "<cms:BasePath/>content/getCommRowBeanJson.do?rowFlag="+opt+"&commFlag=${CommendType.commFlag}&random="+Math.random();
  	 $.getJSON
	 (
		url, 
  		{},
  		function(data)
  		{
      	  $("#infoRow").empty();
      	 
      	  var index = 0;
  		  $.each
  		  (
  				data,
  				function(i,item)
  				{  					
  					if(order == i)
  					{
  						$("#infoRow").append('<option value="'+i+'">第 '+i+' 列 </option>');	  
  					}
  					else
  					{
  						$("#infoRow").append('<option value="'+i+'">第 '+i+' 列 </option>');	  
  					}
  					 
  					index = i;   					      					
  				}	
  				     			
  			);
  			
  			order = parseInt(order);
			index = parseInt(index);
  			var num = (index+1) - order; 
  			
  			var rowOpt = document.getElementById('infoRow').options;
  			
  			for(var i = 0; i < rowOpt.length; i++)
  			{
  				if(rowOpt[i].value == num)
  				{
  					rowOpt[i].selected = true;
  				}
  			}
  		}
  	 );
}

function enableRowSelect(box)
{
	if(box.checked)
	{
		document.getElementById('infoRow').disabled  = false;
	}
	else
	{
		document.getElementById('infoRow').disabled  = true;
		var infoRowBoxOpts = document.getElementById('infoRow').options;
		infoRowBoxOpts[0].selected = true;
	}
}

function submitCommForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('title',1,null,null);
        
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
	
	encodeFormInput('commForm', false);
	
   var commForm = document.getElementById('commForm');
   commForm.action="<cms:BasePath/>content/editSingleSpecInfo.do?spec=true";
   commForm.submit();
}

function sortRow(flag,infoId)
{
	var url = "<cms:BasePath/>content/sortSpecInfo.do?typeId=${param.typeId}&type=row&infoId="+infoId+"&flag="+flag+"&<cms:Token mode='param'/>";
	 		
	 	$.ajax({
	      	type: "POST",
	       	url: url,
	       	data:'',
	   
	       	success: function(mg)
	        {        
	        	var msg = eval("("+mg+")");
           		
	        	if('success' == msg)
	        	{
	        		W.$.dialog.tips('移位成功...',1); 
	        		window.location.reload();
	        	}
	        	else
	        	{
	        			W.$.dialog({ 
		   					title :'提示',
		    				width: '200px', 
		    				height: '60px', 
		    				parent:api,
		                    lock: true, 
		    				icon: '32X32/fail.png', 
		    				
		                    content: "执行失败，无权限请联系管理员！", 
		       				cancel: true 
			  			});
	        	}
	        }
	     });	
}

</script>
</cms:SysCommendContent>
</cms:SystemCommendType>

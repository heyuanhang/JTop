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
		
		
		
		<script type="text/javascript" src="../javascript/format/editor_content_format.js"></script>
		<script type="text/javascript" src="../javascript/colorpicker/picker.js"></script>
		

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

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
			    				
			                    content: '添加图文素材成功!',
	
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
		 
		 var wxUE =  [[
            'fullscreen', '|', 'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
               'emotion', 'scrawl',       'insertframe',    'pagebreak', 'template', 'background', '|',
            'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
          'jtuploadimage',    'jtbmap',   '|', 'print', 'searchreplace' ,'preview' ,'source'
        ]];
    
      	</script>
	</head>
	<body>

		 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="addtit">
							<img src="../style/icons/document-text-image.png" width="16" height="16" />图文内容
						</div>
					

						<form id="newsForm" name="newsForm" method="post">
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
								<tr>
									<td width="20%" class="input-title listdate-show-data">
										<strong>标题: 
									</td>
									<td class="td-input listdate-show-data">
										<input id="title" name="title" type="text" maxLength="80" style="width: 586px;" class="form-input-title titlerule" />
										<input type="button" value="选取内容" onclick="javascript:openSelectSiteAndPushContentDialog();" class="btn-1" />
										<span class="red">*</span><span class="ps"></span>
									</td>
								</tr>

								<tr>
									<td class="input-title listdate-show-data">
										<strong>原文地址: 
									</td>
									<td class="td-input listdate-show-data">
										<input id="url" name="url" type="text" class="form-input"  style="width:640px" size="93" value="http://"/>
									</td>
								</tr>

								<tr>
									<td class="input-title listdate-show-data">
										<strong>封面图: 
									</td>
									<td class="td-input listdate-show-data">
										<table border="0" cellpadding="0" cellspacing="0" class="form-table-upload">
											<tr>
												<td>
													<a class="cmsSysShowSingleImage" id="imgCmsSysShowSingleImage" href="<cms:SystemResizeImgUrl reUrl="" />"><img id="imgCmsSysImgShow" src="<cms:SystemResizeImgUrl reUrl="" />" width="90" height="67" /> </a>
												</td>
												<td>
													<table border="0" cellpadding="0" cellspacing="0" height="65" class="form-table-big">
														<tr>
															<td>
																&nbsp;
																<cms:if test="${param.index==1 || empty param.index}">
																	<input type="button" onclick="javascript:showModuleImageDialog('imgCmsSysImgShow','img','360','200','1',false)" value="上传" onclick="" class="btn-1" />
																	<input type="button" value="裁剪" onclick="javascript:disposeImage('img','360','200',false,'-1');" class="btn-1" />													
																</cms:if>
																<cms:else>
																	<input type="button" onclick="javascript:showModuleImageDialog('imgCmsSysImgShow','img','200','200','1',false)" value="上传" onclick="" class="btn-1" />
																	<input type="button" value="裁剪" onclick="javascript:disposeImage('img','200','200',false,'-1');" class="btn-1" />													
																</cms:else>
																
																	<input type="button" value="删除" onclick="javascript:deleteImage('img');" class="btn-1" />
															</td>
														</tr>
														<tr>
															<td>
																&nbsp;&nbsp;宽&nbsp;&nbsp;
																<input id="imgCmsSysImgWidth" class="form-input" readonly type="text" style="width:44px" />
																&nbsp;&nbsp;&nbsp;&nbsp;高&nbsp;&nbsp;
																<input id="imgCmsSysImgHeight" class="form-input" readonly type="text" style="width:44px" />
																&nbsp;&nbsp;&nbsp;&nbsp;<span class="input-title">文章中显示封面</span><input type="checkbox" id="showCover" class="form-checkbox" name="showCover" value="1"/>
																
															</td>
														</tr>
													</table>
													<input id="img" name="img" type="hidden" />
												</td>
											</tr>
										</table>
									</td>
								</tr>
								
								<tr>
									<td class="input-title listdate-show-data">
										<strong>作者: 
									</td>
									<td class="td-input listdate-show-data">
										<input id="commendMan" name="commendMan" type="text" class="form-input" style="width:280px" size="33" />
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="input-title"><strong>分类:</strong></span>
										<select id="resTag" name="resTag" class="form-select" style="width:280px">
											<option value="">
											------------- 默认主分类标签 ------------
											</option>
											<cms:QueryData objName="Tag" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getResTagForTag" var="news">
											<option value="${Tag.resTagName}">
												${Tag.resTagName}
											</option>
											</cms:QueryData>
											
											
									</select>
									 
									</td>
								</tr>

								<tr>
									<td class="input-title listdate-show-data">
										<strong>简介:</strong>
									</td>
									<td class="td-input listdate-show-data">
										<textarea id="summary" name="summary" class="form-textarea" style="width:640px; height:80px;"></textarea>
								</tr>

								<tr>
									<td class="input-title listdate-show-data">
										<strong>内容:</strong>
									</td>
									<td class="td-input listdate-show-data">
										<div id="sys-obj-articleText"><textarea id="articleText" name="articleText"  style="width:648px; height:535px;"></textarea>
										<input type="hidden" id="articleText_jtop_sys_hidden_temp_html" name="articleText_jtop_sys_hidden_temp_html" ></input>
										 
										<script type="text/javascript">
							               var editor1_articleText = UE.getEditor('articleText',{zIndex : 99, autoFloatEnabled:false, allowDivTransToP: false, enableAutoSave:false, scaleEnabled:true, maximumWords:20000, toolbars : wxUE}); 
										</script>
																												
										</div>
									
									</td>
								</tr>


							</table>
							<!-- hidden -->

							<input type="hidden" id="classId" name="classId" value="-9999" />
							<input type="hidden" id="rootClassId" name="rootClassId" value="-9999" />
							
							<input type="hidden" id="classChildMode" name="classChildMode" value="true" />
							 
							<input type="hidden" id="contentId" name="contentId" value="-1" />
							<input type="hidden" id="modelId" name="modelId" value="-1" />

							 	
							<input type="hidden" id="rowFlag" name="rowFlag" value="${param.rowFlag}" />
							
							<input type="hidden" id="inCol" name="inCol" value="${param.inCol}" />
							
							<cms:Token mode="html"/>
						<div style="height:46px"></div>
						</form>
						<div style="height:16px"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellspacing="0" cellpadding="0" >
								<tr class="btnbg100">
									<div style="float:right">
										<a href="javascript:submitNewsForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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

 
function openSelectSiteAndPushContentDialog()
{
	W.$.dialog({ 
	    id : 'aospcd',
    	title : '选取站点内容',
    	width: '800px', 
    	height: '560px',
    	parent:api, 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/SelectSiteContent.jsp?&single=true&dialogId=${param.dialogApiId}&uid='+Math.random()+'&typeId=${param.typeId}'+'&classId=${param.classId}'
	});
}
 
function submitNewsForm()
{

	

	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('title',1,null,null);
        
        if(currError)
        {
        	hasError = true;
        }
        
       if($('#summary').val().length > 64)
     {
      
     	W.$.dialog({ 
				   					title :'提示',
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/i.png', 
				    				parent:api,
				                    content: '简介字数须在64字符内!',
				                    
				                    ok: function () 
				                    { 
				                    	 
				                    }
				                    
    								
       });
     	
     	return;
     
     }
     
       
     if('' == $('#img').val())
     {
      	
     	W.$.dialog({ 
				   					title :'提示',
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/i.png', 
				    				parent:api,
				                    content: '请上传封面图片!',
				                    
				                    ok: function () 
				                    { 
				                    	 
				                    }
				                    
    								
       });
     	
     	return;
     
     }
        
    
    
    			
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	encodeFormInput('newsForm', false);
	
   var newsForm = document.getElementById('newsForm');
   newsForm.action="../../wx/createNewsInfoItem.do";
   newsForm.submit();
}


//图象上传
function showImageDialog(editorId)
{	
	var obj = document.getElementById("classId");
	
	var classId = '';
	
	if(obj != null)
	{
		classId = obj.value;	
	}
	 
	W.$.dialog({ 
		id:'oud',
    	title :'图片',
    	width: '520px', 
    	height: '375px', 
    	lock: true, 
    	parent:api,
        max: false, 
        min: false, 
        resize: false,         
        content: 'url:'+basePath+'core/weixin/dialog/ImageUpload.jsp?classId='+classId+'&editorId='+editorId+'&apiId=ospcd'
	});
}


//图象处理
function showDisposeImageDialog(editorId)
{	 
	var e = FCKeditorAPI.GetInstance(editorId).Selection.GetSelectedElement();
	
	var outHtml = FCKeditorAPI.GetInstance(editorId).EditorDocument.body.outerHTML;

	if(e != null && e.tagName != null && 'IMG' == e.tagName )
	{

		if(e.id == null || e.id == '')
		{
			
			$.dialog.tips('只可编辑本地上传图片...',1);
			
			return;
		}
		
		var OldSrc = e.src;
		var bodyHtml = FCKeditorAPI.GetInstance(editorId).GetXHTML(true);
    	
    	var t = bodyHtml+'';
     
    	var fieldSign ='';
  
    	var mw = 100;
    	
    	var mh = 100;
    	
    	var resId = e.id.replace("jtopcms_content_image_","");
    	
    	var gm = 'false';
    	
    	var order = 1;
    	
    	W.$.dialog({ 
		    id : 'di',
	    	title : '裁剪图片',
	    	width: '1080px', 
	    	height: '625px', 
	        parent:api,
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/weixin/DisposeImage.jsp?editorId='+editorId+'&fieldSign='+fieldSign+'&mw='+mw+'&mh='+mh+'&fmw='+mw+'&fmh='+mh+'&orgResId='+resId+'&resId='+resId+'&ratio=false'+'&gm='+gm+'&order='+order+'&uid='+Math.random()+'&apiId=oeccd'
	      
	        
	     
		});
		
    	 
	}
	else
	{
		$.dialog.tips('请选择一张图片...',1);
	}

}

   
  
</script>
 

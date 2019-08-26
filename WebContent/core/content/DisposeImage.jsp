<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<cms:SystemSiteResource resId="${param.resId}">
<cms:CurrentSite>
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
	<!-- saved from url=(0032)http://www.cropzoom.com.ar/demo/ -->
	<HTML xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
		<head>
			
			<title>裁剪图片</title>
			<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
			<!--加载 js -->
			<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
			<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
			<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		
			
			<link rel="stylesheet" type="text/css" href="../javascript/cropzoom/jquery-ui-1.7.2.custom.css" />
			<link rel="stylesheet" type="text/css" href="../javascript/cropzoom/jquery.cropzoom.css" />
			<script type="text/javascript" src="../javascript/cropzoom/jquery.js"></script>
			<script type="text/javascript" src="../javascript/cropzoom/jquery-ui-1.8.14.custom.min.js"></script>
			<script type="text/javascript" src="../javascript/cropzoom/jquery.cropzoom.js"></script>

			<style type="text/css">
			
		</style>


		<SCRIPT type=text/javascript>
		var api = frameElement.api, W = api.opener; 
		
		var dialog = '${param.dialog}';
		
		var apiId = '${param.api}';
		
		
		var wd = W;
    
	    if('true' == '${param.dialog}')
	    {
	    	wd = api.get(apiId);
	    }
		
		var cropzoom = null;
		
		var inX =15;
		var inY = 15;
		
		var seX =15;
		var seY =15;
		
		var mw = ${param.mw};
		
		var mh = ${param.mh};
		
		var newCropWHs = checkSize(mw, mh, 40,40);
	
		var newCropWH = checkSize(mw, mh, 240,240);
		

		
		function init()
		{
					
			cropzoom = $('#cropzoom_container').cropzoom({
		          width: 1060,
		          height: 575,
		          bgColor: '#ccc',
		          enableRotation: false,
		          enableZoom: true,
		          zoomSteps:1,
		       
		          selector: {
					 
					    showPositionsOnDrag:false,
					    showDimetionsOnDrag:false,
		             	w:20,
		                h:20,
		                bgInfoLayer:'#fff',
		                borderColor: 'blue',
		                borderColorHover: 'yellow',
		                aspectRatio:${param.ratio},
		
						maxWidth:3000,
						maxHeight:3000,
		                centered:true,
		           
		                startWithOverlay: false,
		              hideOverlayOnDragAndResize: true ,
		              onSelectorDrag:function()
		              {
		              	
		              	 $('#sex').val(cropzoom.data('selector').x);
		         		 $('#sey').val(cropzoom.data('selector').y);
		         		 
		         		 var posX = cropzoom.data('image').posX; //图像宽
		          		 var posY = cropzoom.data('image').posY; //图像高
		          		 
		          		 var seW = cropzoom.data('selector').w; //图像宽
		          		 var seH = cropzoom.data('selector').h; //图像高
		         		 
		         		
		         		  seX = cropzoom.data('selector').x;
		         		  seY = cropzoom.data('selector').y ;
		         		 
		         		 inX = seX  -posX;
		         		 inY = seY -posY;
		         		 
		              },
		              
		              onSelectorResize:function()
		              {
		          		$('#sew').val(cropzoom.data('selector').w);
		         		$('#seh').val(cropzoom.data('selector').h);		         	   		  
		              }
		           },
		           image: {
		              source: '${Res.url}?uid='+Math.random(),
		              width: '${Res.width}',
		              height: '${Res.height}',
		              x:0,
		              y:0,
		              maxZoom:110,//注意:设定110才能最大化宽高到100%
		         	 
		              startZoom:100,
		               
		              snapToContainer:false,
		                
		                onZoom:function()
		                {
		                
		                var posX = cropzoom.data('image').posX;
		          			var posY = cropzoom.data('image').posY; 
		          			
		          			var seX = cropzoom.data('selector').x; //图像x坐标
				 			var seY = cropzoom.data('selector').y; //图像Y坐标
		          		    var seW = cropzoom.data('selector').w; //图像宽
		          			var seH = cropzoom.data('selector').h; //图像高
		          			
		          			cropzoom.setSelector( posX+inX ,posY+inY,seW,seH ,false); 
		          		
		                	$('#imageWidth').val(cropzoom.data('image').w);
		                	$('#imageHeight').val(cropzoom.data('image').h);
		                	
		                	
		                },
		                
		                onImageDrag:function()
		                {
		                	var posX = cropzoom.data('image').posX;
		          			var posY = cropzoom.data('image').posY; 
		          			
		          			var seX = cropzoom.data('selector').x; //图像x坐标
				 			var seY = cropzoom.data('selector').y; //图像Y坐标
		          		    var seW = cropzoom.data('selector').w; //图像宽
		          			var seH = cropzoom.data('selector').h; //图像高
		          				
					  		cropzoom.setSelector( posX+inX ,posY+inY,seW,seH ,false); 
		                }
		            }
		      });
		}
		
function crop()
{
 		var seX = cropzoom.data('selector').x; //图像x坐标
	 	var seY = cropzoom.data('selector').y; //图像Y坐标
	 	
		          var seW = cropzoom.data('selector').w; //图像宽
		          var seH = cropzoom.data('selector').h; //图像高
		          
		          var posX = cropzoom.data('image').posX; //图像宽
		          var posY = cropzoom.data('image').posY; //图像高
		          var W = cropzoom.data('image').w; //图像宽
		          var H = cropzoom.data('image').h;
		          
		          if(seX+seW < posX || posX+W < seX)
		          {
		            alert("裁剪框选择超出图片范围!");
		           	return  ;
		          }
		         
		          if(seY+seH < posY || posY+H < seY)
		          {
		            alert("裁剪框超出图片范围!");
		           	return  ;
		          }
		       
		          //此为AJAX提交
				  cropzoom.send('<cms:BasePath/>/content/disposeImage.do?fieldSign=${param.fieldSign}&resId=${Res.resId}&orgResId=${param.orgResId}&gm=${param.gm}&order=${param.order}&mw=${param.mw}&mh=${param.mh}&fmw=${param.fmw}&fmh=${param.fmh}', 'POST', {}, function(mg)
				  { 
				  		var msg = eval("("+mg+")");
		               replaceUrlParam(window.location,msg);
		          });			 

}

function resize()
{
	//此为AJAX提交
	cropzoom.send('<cms:BasePath/>content/disposeImage.do?fieldSign=${param.fieldSign}&resId=${Res.resId}&orgResId=${param.orgResId}&resize=true&ratio=${param.ratio}&gm=${param.gm}&order=${param.order}&mw=${param.mw}&mh=${param.mh}&fmw=${param.fmw}&fmh=${param.fmh}', 'POST', {}, function(mg) 
	{
		var msg = eval("("+mg+")");
		replaceUrlParam(window.location,msg);
	});
}
		
function effect()
{
	 W.$.dialog.tips('图片改动生效...',2);
	  
	 //编辑器模式
	 if('${param.editorId}' != '')
	 {
	 	   //var editorId = '${param.editorId}';
	 	   //var oEditor =  wd.FCKeditorAPI.GetInstance(editorId);
	 	    
	 	   //if(oEditor.EditMode == wd.FCK_EDITMODE_WYSIWYG)
		   {
		   		//var e =  wd.FCKeditorAPI.GetInstance(editorId).Selection.GetSelectedElement();
		   		var range = wd.ue.selection.getRange();
    			range.select();
       
    			var e = wd.ue.selection.getStart();
    
		   		 //var bodyHtml =  wd.FCKeditorAPI.GetInstance(editorId).GetXHTML(true);
		   		 
		   		 var bodyHtml = e.outerHTML;
    	 
    			 var idStr = 'id="'+e.id+'"';
    			 
    			 var nameStr = 'name="'+e.name+'"';
    			
    			 var start = bodyHtml.indexOf(idStr);
    			 
    			 var end = bodyHtml.indexOf(nameStr,start);
    			
    			// var whh = bodyHtml.substring(start+idStr.length, end);
    			 
    			// var oldWhh = bodyHtml.substring(start+idStr.length, end);
    			 
    			 //whh = whh.replace('width="'+e.width+'"', 'width="${Res.width}"');
    			 
    			// whh = whh.replace('height="'+e.height+'"', 'height="${Res.height}"');
    			 
    			
    			 bodyHtml= bodyHtml.replace('width="'+e.width+'"', 'width="${Res.width}"');
    			 
    			  bodyHtml= bodyHtml.replace('height="'+e.height+'"', 'height="${Res.height}"');
    			 
    			 var oe = e.src.replace('${CurrSite.hostMainUrl}', '/');
    			  
    			  
    			 if(e.src.startWith('http:') || e.src.startWith('https:'))
    			 {
    			 	bodyHtml= bodyHtml.replace(e.src, '${Res.url}');
    			 
    			 }
    			 else
    			 {
    			  	bodyHtml= bodyHtml.replace(oe, '${Res.url}');
    			 
    			 }
    			 
    			 
    			  bodyHtml= bodyHtml.replace(e.id, 'jtopcms_content_image_${Res.resId}');
    	
    	        // oEditor.SetHTML(bodyHtml) ;
    	       
    	        wd.ue.execCommand('insertHtml', bodyHtml)
		    }

	 
	 }
	 else if('true' == '${param.gm}')
	 {   
			 var fieldSign = '${param.fieldSign}';
			 var imageOrder = '${param.order}';
			 
			if('true' == dialog)
			{
				 var prevRePath = api.get(apiId).document.getElementById(fieldSign+'-relatePath-'+imageOrder).value;
	
				 api.get(apiId).document.getElementById(fieldSign+'-name-show-'+imageOrder).value = name;
			 
			     api.get(apiId).document.getElementById(fieldSign+'-cmsSysImageShowUrl-'+imageOrder).href = '${Res.url}';
				 api.get(apiId).document.getElementById(fieldSign+'-resizeUrl-'+imageOrder).src = '${Res.resizeImgUrl}';
				 api.get(apiId).document.getElementById(fieldSign+'-resizeUrl-'+imageOrder).title = '${Res.resName}';
				
				 api.get(apiId).document.getElementById(fieldSign+'-resId-'+imageOrder).value = '${Res.resId}';
				 api.get(apiId).document.getElementById(fieldSign+'-relatePath-'+imageOrder).value = '${Res.resSource}';
				 api.get(apiId).document.getElementById(fieldSign+'-photoName-'+imageOrder).value = '${Res.resName}';
				
				 api.get(apiId).document.getElementById(fieldSign+'-height-'+imageOrder).value = '${Res.height}';
				 api.get(apiId).document.getElementById(fieldSign+'-width-'+imageOrder).value = '${Res.width}';
				 
				 api.get(apiId).document.getElementById(fieldSign+'-height-show-'+imageOrder).value = '${Res.height}';
				 api.get(apiId).document.getElementById(fieldSign+'-width-show-'+imageOrder).value = '${Res.width}';
				 
				 api.get(apiId).document.getElementById(fieldSign+'-name-show-'+imageOrder).value = '${Res.resName}';
				 
				 //若为封面，则替换封面
				 var checkedFlag = api.get(apiId).document.getElementById(fieldSign+'-cover-'+imageOrder).checked;
		
				 if(checkedFlag)
				 {
					api.get(apiId).document.getElementById(fieldSign+'CmsSysImageCover').value = '${Res.resSource}';
				 }
			}
			else
			{
			 			 
				 var prevRePath = W.document.getElementById(fieldSign+'-relatePath-'+imageOrder).value;
	
				 W.document.getElementById(fieldSign+'-name-show-'+imageOrder).value = name;
			 
			     W.document.getElementById(fieldSign+'-cmsSysImageShowUrl-'+imageOrder).href = '${Res.url}';
				 W.document.getElementById(fieldSign+'-resizeUrl-'+imageOrder).src = '${Res.resizeImgUrl}';
				 W.document.getElementById(fieldSign+'-resizeUrl-'+imageOrder).title = '${Res.resName}';
				
				 W.document.getElementById(fieldSign+'-resId-'+imageOrder).value = '${Res.resId}';
				 W.document.getElementById(fieldSign+'-relatePath-'+imageOrder).value = '${Res.resSource}';
				 W.document.getElementById(fieldSign+'-photoName-'+imageOrder).value = '${Res.resName}';
				
				 W.document.getElementById(fieldSign+'-height-'+imageOrder).value = '${Res.height}';
				 W.document.getElementById(fieldSign+'-width-'+imageOrder).value = '${Res.width}';
				 
				 W.document.getElementById(fieldSign+'-height-show-'+imageOrder).value = '${Res.height}';
				 W.document.getElementById(fieldSign+'-width-show-'+imageOrder).value = '${Res.width}';
				 
				 W.document.getElementById(fieldSign+'-name-show-'+imageOrder).value = '${Res.resName}';
				 
				 //若为封面，则替换封面
				 var checkedFlag = W.document.getElementById(fieldSign+'-cover-'+imageOrder).checked;
		
				 if(checkedFlag)
				 {
					W.document.getElementById(fieldSign+'CmsSysImageCover').value = '${Res.resSource}';
				 }
			 }

		}
		else
		{
			if('true' == dialog)
			{
			 	
			 	//用于显示
			
			 	 api.get(apiId).document.getElementById('${param.fieldSign}CmsSysImgShow').src='${Res.resizeImgUrl}';
							 
				 api.get(apiId).document.getElementById('${param.fieldSign}CmsSysImgShow').width = 90;
				 api.get(apiId).document.getElementById('${param.fieldSign}CmsSysImgShow').height = 67;
			 	
			 	 if(api.get(apiId).document.getElementById('${param.fieldSign}CmsSysShowSingleImage') != null)
			 	 {
				 	api.get(apiId).document.getElementById('${param.fieldSign}CmsSysShowSingleImage').href = '${Res.url}';
				 }
				 
				
			
				 api.get(apiId).document.getElementById('${param.fieldSign}CmsSysImgWidth').value= '${Res.width}';
				 api.get(apiId).document.getElementById('${param.fieldSign}CmsSysImgHeight').value= '${Res.height}';
							 
				 //用于cms处理
				 api.get(apiId).document.getElementById('${param.fieldSign}').value= '${Res.resId}';
			
			}
			else
			{
				//用于显示
				 if(W.document.getElementById('${param.fieldSign}CmsSysShowSingleImage') != null)
				 {
				 	W.document.getElementById('${param.fieldSign}CmsSysShowSingleImage').href = '${Res.url}';
				 }
				 
				 W.document.getElementById('${param.fieldSign}CmsSysImgShow').src='${Res.resizeImgUrl}';
							 
				 W.document.getElementById('${param.fieldSign}CmsSysImgShow').width = 90;
				 W.document.getElementById('${param.fieldSign}CmsSysImgShow').height = 67;
			
				 W.document.getElementById('${param.fieldSign}CmsSysImgWidth').value= '${Res.width}';
				 W.document.getElementById('${param.fieldSign}CmsSysImgHeight').value= '${Res.height}';
							 
				 //用于cms处理
				 W.document.getElementById('${param.fieldSign}').value= '${Res.resId}';
			
			}
			
		      
	 }
}

function restore()
{
	cropzoom.restore();
				 //重新设定大小
    //重新设定大小
     if('true' == '${param.ratio}')
     {
     	cropzoom.setSelector(15,15,'${param.fmw}','${param.fmh}',true);
     	$('#sew').val(sew);
     	$('#seh').val(seh);
     }
     else
     {
     	cropzoom.setSelector(15,15,120,100,true);
     	$('#sew').val(120);
     	$('#seh').val(100);
     }
     
    			
	$('#imageWidth').val('${Res.width}');
	$('#imageHeight').val('${Res.height}');	
}
		
$(function()
{
     init();
     //重新设定大小
  
     if('true' == '${param.ratio}')
     {
     	cropzoom.setSelector(15,15,'${param.fmw}','${param.fmh}',true);
     	$('#sew').val('${param.fmw}');
     	$('#seh').val('${param.fmh}');
     }
     else
     {
     	cropzoom.setSelector(15,15,120,100,true);
     	$('#sew').val(120);
     	$('#seh').val(100);
     }
}
);




</SCRIPT>

		</head>
		<body>
			<div id="main" >
			<div style="height:2px"></div>
				<div class="crop">
					<div id="cropzoom_container"></div>
				</div>
			</div>

			<DIV>
				<DIV id=zoom></DIV>
				<div style="height:8px"></div>
				<table>
					<tr>
						<td>
							图宽:
							<input id="imageWidth" type="text" size=4 class="form-input" value="${Res.width}">
							px
							<font style="font-family: 宋体, simsun;">&nbsp;</font>图高:
							<input id="imageHeight" type="text" class="form-input" value="${Res.height}" size=4>
							px
							&nbsp;&nbsp;框宽:
							<input id="sew" type="text" size=4 class="form-input" value="">
							px
							<font style="font-family: 宋体, simsun;">&nbsp;</font>框高:
							<input id="seh" type="text" class="form-input" value="" size=4>
							px
							<font style="font-family: 宋体, simsun;">&nbsp;&nbsp;</font>可选比例:
							<select class="form-select" id="cwh" onchange="javascript:changeWH();">
								<option value="${param.fmw}*${param.fmh}">
									${param.fmw} x ${param.fmh}
								</option>
								<cms:QueryData service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getImageRadioTag" objName="Ra" var="all">
								<option value="${Ra.ratioWidth}*${Ra.ratioHeight}">
									${Ra.ratioWidth} x ${Ra.ratioHeight}
								</option>
								</cms:QueryData>							
							</select>
							<font style="font-family: 宋体, simsun;">&nbsp;&nbsp;</font>使用比例:
							<input id="ratio" name="ratio" type="checkbox" class="form-checkbox" value="1" onclick="javascript:setRatioAndInit()">
							&nbsp;&nbsp;
						</td>
						<td>
							<a href="javascript:crop();" class="btnwithico"> <img src="../../core/style/default/images/image-crop.png" alt="" /><b>剪切&nbsp;</b> </a>

							<a href="javascript:resize();" class="btnwithico"> <img src="../../core/style/default/images/image-resize.png" alt="" /><b>缩放&nbsp;</b> </a>

							<a href="javascript:restore();" class="btnwithico"> <img src="../../core/style/default/images/image-export.png" alt="" /><b>复位&nbsp;</b> </a>

							<a href="javascript:effect();" class="btnwithico"> <img src="../../core/style/default/images/image--pencil.png" alt="" /><b>应用&nbsp;</b> </a>

							<a href="javascript:close();" class="btnwithico"> <img src="../../core/style/icon/close.png" alt="" /><b>关闭&nbsp;</b> </a>
							
						</td>
					</tr>


				</table>


			</DIV>

		</body>
<script language="JavaScript" type="text/javascript">
if('true' == '${param.ratio}')
{	
	initRadio('ratio','1');
	initSelect('cwh',mw+'*'+mh);
}
else
{
	
	document.getElementById('cwh').disabled = true;
}
	
function close()
{
	api.close();
}

function setRatioAndInit()
{
	var ratio = document.getElementById("ratio").checked;
	if(ratio)
	{
		replaceUrlParam(window.location,'ratio=true');
	}
	else
	{
		replaceUrlParam(window.location,'ratio=false');
	}
}

function changeWH()
{	
	if('true' == '${param.ratio}')
	{
		var wh = document.getElementById('cwh').value;
		
	    var wha = wh.split('*');
	  
		replaceUrlParam(window.location,'fmw='+wha[0]+'&fmh='+wha[1]);
	}

}

function close()
{
	api.close();
}

</script>

	</html>
	</cms:CurrentSite>
</cms:SystemSiteResource>

<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/commonUtil.js"></script>
		<script type="text/javascript" src="../javascript/imagePreviewd.js"></script>


		<script>  
		 var api = frameElement.api, W = api.opener; 
		 W.dialogMode=false;
		 
         if("true"==="${param.fromFlow}")
         {
           
     
             W.document.getElementById("ids").value = "${param.newIds}";
             
             W.document.getElementById("newAddIds").value = "${param.newAddIds}";
             
     
             
             W.targetCoverPhoto = "${param.targetCover}";
             
             W.document.getElementById("showCover").src="<cms:Domain/>UPLOAD/imgResize${param.targetCover}";
             W.document.getElementById("cover").value="<cms:Domain/>UPLOAD/${param.targetCover}";
			 W.document.getElementById("resizeCover").value="<cms:Domain/>UPLOAD/imgResize${param.targetCover}";
			 
			
			 
     		 W.document.getElementById("groupFrame").src="PhotoGroupList.jsp?mode=edit&ids=${param.newIds}";
             //replaceUrlParam(W.document.getElementById("groupFrame").location,"targetCover=${param.targetCover}&ids=${param.newIds}&title=${param.title}");
      
            
             api.close();        
         }
         else
         {     
            var maxWidth = 240;
            var maxHeight = 210;
         
         }
      </script>

	</HEAD>
	<BODY>
		<form id="groupUploadForm" name="groupUploadForm" method="post" enctype="multipart/form-data">
			<table border=0 cellpadding=0 cellspacing=5 id=tabDialogSize>
				<tr>
					<td>
						<table border=0 cellpadding=0 cellspacing=5 align=center>
							<tr valign=top>
								<td width="60%">

									<table border=0 cellpadding=0 cellspacing=0 align=center width="100%">
										<tr>
											<td>
												<fieldset>
													<legend>
														<span class="STYLE1">图片源</span>
													</legend>
													<table border=0 cellpadding=5 cellspacing=0 width="100%">
														<tr>
															<td>

																<table border=0 cellpadding=0 cellspacing=2 width="100%">
																	<tr>
																		<td noWrap width="20%">
																			<input type=radio id="imageSourceTypeWeb" name="imageSourceType" value="s" onclick="javascript:changeSource('site');" checked>
																			<label id='labelSite'>
																				<span class="STYLE1">本地</span>
																			</label>
																			:
																		</td>
																		<td noWrap width="80%">
																			<input size='37' type="file" id='imgSite' name='imgSite' class="form-input-file" onchange="previeImg(this,'site');">
																		</td>
																	</tr>
																	<tr>
																		<td noWrap width="20%">
																			<input type=radio id="imageSourceTypeWeb" name="imageSourceType" onclick="javascript:changeSource('web');" value="w">
																			<label id='labelWeb' disabled='true'>
																				<span class="STYLE1">网络</span>
																			</label>
																			:
																		</td>
																		<td noWrap width="80%">
																			<input type=text class="form-input" id='imgWeb' name='imgWeb' size=50 value='' onpropertychange="previeImg(this,'web');" disabled='true'>
																		</td>
																	</tr>



																	<tr>
																		<td width="20%">
																			名称:
																		</td>
																		<td width="80%" colspan=4>
																			<input type=text id="photoName" name="photoName" size=50 value="" class="form-input" />
																		</td>
																	</tr>
																	<tr>
																		<td width="20%">
																			解说文字:
																		</td>
																		<td width="80%" colspan=4>
																			<input type=text id="photoName" name="photoName" size=50 value="" class="form-input" />
																		</td>
																	</tr>


																	<td noWrap width="20%" class="title_td">
																		简介:
																	</td>
																	<td noWrap width="80%" colspan=4>
																		<textarea id="photoDesc" name="photoDesc" style="width:265px;height:120px" class="form-textarea"></textarea>
																	</td>
																	</tr>
																</table>

															</td>
														</tr>
													</table>
												</fieldset>
											</td>
										</tr>
										<tr>
											<td height=5></td>
										</tr>

									</table>


								</td>
								<td width="40%" height="100%">

									<fieldset style="height:100%">
										<legend>
											<span class="STYLE1">预览</span>
										</legend>
										<table border=0 cellpadding=0 cellspacing=2 width="265" height="225" valign=top>
											<tr>
												<td colspan=2 bgcolor=#FFFFFF align=center valign=middle id=tdPreview height="100%">
													<div id='imgDiv'></div>
												</td>
											</tr>

										</table>
									</fieldset>

								</td>
							</tr>

						</table>


					</td>
				</tr>
			</table>


			<!-- hidden -->
			<input type="hidden" id="actionFlag" name="actionFlag" value="group" />
			<input type="hidden" id="parentTitle" name="parentTitle" value="" />
		</form>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
			<tr class="btnbg100">
				<td width="74%" class="input-title"></td>
				<td width="87%" class="td-input">
					<a onclick="javascript:submitImg();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
					<a href="javascript:;" class="btnwithico" onclick="javascript: frameElement.api.close();"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
				</td>
				<td valign="top">
					<br />
				</td>
			</tr>
		</table>
		<div id="divProcessing" style="width:200px;height:30px;position:absolute;left:220px;top:100px;display:none">
			<img src="../javascript/dialog/skins/icons/loading.gif" width=28 height=28 class="ui_icon_bg" />
			图片处理中......
		</div>

	</body>
</html>
<script language="javascript">
var api = frameElement.api, W = api.opener; 
//设置父页面值
document.getElementById("parentTitle").value=W.document.getElementById("title").value;

//alert("ee:"+document.getElementById("parentTitle").value);



//保持兼容
//onpropertychange(document.getElementById('imgWeb'),previeImg,'web',null);


 document.getElementById('labelSite').disabled=false;
 document.getElementById('imgSite').disabled=false; 
 document.getElementById('labelWeb').disabled=true;
 document.getElementById('imgWeb').disabled=true;
 
 

 
function changeSource(type)
{
    if('site' == type)
    {
     
      //alert('ok');
      document.getElementById('labelSite').disabled=false;
      document.getElementById('imgSite').disabled=false;
      
      document.getElementById('labelWeb').disabled=true;
      document.getElementById('imgWeb').disabled=true;
      
      previeImg(document.getElementById('imgSite'),'site');
      
      $('.imgP').remove();
    
      return false;
    }
    
    if('web' == type)
    {  
      document.getElementById('labelWeb').disabled=false;
      document.getElementById('imgWeb').disabled=false;
      
      document.getElementById('labelSite').disabled=true;
      document.getElementById('imgSite').disabled=true;
      
      //FF不需要此操作
      if (!document.getElementById('imgSite').files )
      {
     	 removeDIVimgFilter();
      }
      
      $('.imgP').remove();
     
      return false;
    }
}


function submitImg()
{
   if(document.getElementById("imgSite").value=='' && document.getElementById("imgWeb").value=='')
   {
      alert("当前没有选择任何图片源");
      return;
   }

   document.getElementById("divProcessing").style.display='';
  
  
   
   
   //清空file
  //if(document.getElementById("imageSourceType").value!=1)
   //{
   //   document.getElementById("imgSite").outerHTML = document.getElementById("imgSite").outerHTML;
  // }
     
   document.groupUploadForm.action='../../content/editorUploadImage.do?flowFlag=group&ids='+W.document.getElementById("ids").value+"&targetCover="+W.targetCoverPhoto+"&mode=edit&newAddIds="+W.document.getElementById("newAddIds").value;
   document.groupUploadForm.submit();
   
 
  
}




 </script>

<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />

		<script>
		//表格变色
			$(function()
			{ 
		   		$("#showlist tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
			});

      var firstTypeId;
      </script>
	</head>
	<body>
	<cms:CurrentSite>	
	 
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">站点维护</a> &raquo;
						<a href="#">资源管理</a> &raquo;
						<a href="#">视频媒体库</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="flForm" name="flForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										所属:&nbsp;
										<select class="form-select" id="classId" name="classId" onchange="javascript:changeClass(this.value)">
										<option value="" selected>
											------ 所有视频媒体 ------
										</option>
										<option value="-9999">
											全站共用视频
										</option>
										 
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
												<cms:SysClass>
													<option value="${Class.classId}">
														${Class.layerUIBlankClassName}
													</option>
												</cms:SysClass>
											</cms:SystemClassList>
										 
									</select>

									&nbsp;
									</div>
									<div>
										<a href="javascript:openUploadDialog()" class="btnwithico" onclick=""><img src="../style/icons/drive-upload.png" width="16" height="16" /><b>上传视频&nbsp;</b> </a>
										<a href="javascript:setClass();" class="btnwithico" onclick=""><img src="../style/default/images/sort-number.png" width="16" height="16" /><b>所属栏目&nbsp;</b> </a>
										
										<a href="javascript:deleteCovertVideo();" class="btnwithico" onclick=""><img src="../style/icons/drive--minus.png" width="16" height="16" /><b>删除&nbsp;</b> </a>
									</div>
									<div class="fr">

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkVideo',this);" type="checkbox" />
												</td>
												
												<td width="7%">
													<strong><center>封面图</center></strong>
												</td>
												
												<td width="14%">
													<strong>视频名称</strong>
												</td>
												
												<td width="4%">
													<strong>文件大小</strong>
												</td>

												

												<td width="4%">
													<strong>文件格式</strong>
												</td>

												 

												<td width="11%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:QueryData objName="Cov" service="cn.com.mjsoft.cms.resources.service.ResourcesService" method="getMediaResCovForTag" var=",${param.classId},${param.pn},5">
												<tr>
													<td>
														${Cov.resId}
													</td>
													<td>
														<input class="inputCheckbox"  name="checkVideo" value="${Cov.resId}" type="checkbox"  />
													</td>
													
													<td align="center">
														<div style="height:4px;"></div>
														<cms:if test="${empty Cov.cover}">
															<a class="cmsSysShowSingleImage" href="../style/blue/images/no-image.png"><img id="contentImageShow" src="../style/blue/images/no-image.png" width="100" height="67" />
															</a>

														</cms:if>
														<cms:else>
															 	<cms:SystemSiteResource source="${Cov.cover}">
																<a class="cmsSysShowSingleImage" href="${Res.url}">
																
																<img id="contentImageShow" src="${Res.resizeImgUrl}" width="100" height="67" />
																</cms:SystemSiteResource>
																</a>
															 
														</cms:else>



														<div style="height:5px;"></div>
													</td>
													<td>
													<cms:SystemSiteResource source="${Cov.resSource}">
													<cms:if test="${Cov.fileType == 'flv' || Cov.fileType == 'mp4'}">
													
														<a class="videoShow" href="<cms:BasePath/>core/extools/player/jwplayer/5.9/player.swf?file=${Res.url}&screencolor=black&autoStart=true" >&nbsp;${Cov.resName}</a>
											
													</cms:if>
													<cms:else>
														<a  target="_blank" href="${Res.url}" >&nbsp;${Cov.resName}</a>
													</cms:else>
													</cms:SystemSiteResource>
																</td>
													
													<td>
														<cms:SystemSiteResource resId="${Cov.resId}">
															${Res.sizeStr}
														</cms:SystemSiteResource>
													</td>

													<td>
														${Cov.fileType}
													</td>
												 
													<td>
														<div>
															<center>
																<a href="javascript:openCovertDialog('${Cov.resId}');"><img src="../../core/style/icons/arrow-circle-double.png" width="16" height="16" />&nbsp;转码</a>&nbsp;&nbsp;
																
																<a href="javascript:openDivideDialog('${Cov.resId}');"><img src="../../core/style/icons/scissors-blue.png" width="16" height="16" />&nbsp;裁剪</a>&nbsp;&nbsp;
																
																<a href="javascript:openChangeNameDialog('${Cov.resId}');"><img src="../../core/style/icons/property-blue.png" width="16" height="16" />&nbsp;改名</a>&nbsp;&nbsp;
															
																<a href="javascript:openSnapDialog('${Cov.resId}');"><img src="../../core/style/icons/image-select.png" width="16" height="16" />&nbsp;封面</a>&nbsp;&nbsp;
															</center>
														</div>
													</td>
												</tr>
											</cms:QueryData>

											<cms:Empty flag="Cov">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
											</cms:Empty>
											
											<cms:PageInfo>
												<tr id="pageBarTr">
													<td colspan="8" class="PageBar" align="left">
														<div class="fr">
															<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
															<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
															<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
															<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
															<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
														</div>
														<script>
																function query(flag)
																{
																	var cp = 0;
																	
																	if('p' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage-1}');
																	}
		
																	if('n' == flag)
																	{
			                                                             cp = parseInt('${Page.currentPage+1}');
																	}
		
																	if('h' == flag)
																	{
			                                                             cp = 1;
																	}
		
																	if('l' == flag)
																	{
			                                                             cp = parseInt('${Page.pageCount}');
																	}
		
																	if(cp < 1)
																	{
			                                                           cp=1;
																	}
																	
																	if(cp > parseInt('${Page.pageCount}'))
																	{
			                                                           cp=parseInt('${Page.pageCount}');
																	}
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																    var cp = parseInt(document.getElementById('pageJumpPos').value);
																    
																    if(cp > parseInt('${Page.pageCount}'))
																	{
			                                                           cp=parseInt('${Page.pageCount}');
																	}
																
																	replaceUrlParam(window.location,'pn='+cp);
																}
															</script>
														<div class="fl"></div>
													</td>
												</tr>
											</cms:PageInfo>

										</table>
									</div>
									<div class="mainbody-right"></div>
								</td>
							</tr>


						</table>

						</form>

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
<script type="text/javascript">

initSelect('classId','${param.classId}');


function openUploadDialog()
{
	$.dialog({ 
		id: 'oud',
    	title :'上传视频文件',
    	width: '550px', 
    	height: '180px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/UploadMediaFile.jsp?dialogApiId=oud&id='
	});
}

function openCovertDialog(id)
{
	$.dialog({ 
		id: 'ocd',
    	title :'转换视频文件',
    	width: '500px', 
    	height: '180px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/CovertVideo.jsp?dialogApiId=ocd&id='+id
	});
}

function openDivideDialog(id)
{
	$.dialog({ 
		id: 'odd',
    	title :'裁剪视频文件',
    	width: '500px', 
    	height: '180px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/DivideVideo.jsp?dialogApiId=ocd&id='+id
	});
}

function openChangeNameDialog(id)
{
	$.dialog({ 
		id: 'ocnd',
    	title :'视频文件改名',
    	width: '500px', 
    	height: '170px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/ChangeVideoName.jsp?dialogApiId=ocnd&id='+id
	});
}

function openSnapDialog(id)
{
	$.dialog({ 
		id: 'osd',
    	title :'截取视频封面',
    	width: '500px', 
    	height: '180px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/SnapVideoImage.jsp?dialogApiId=ocnd&id='+id
	});
}



function deleteCovertVideo()
{
	var cidCheck = document.getElementsByName('checkVideo');
	
	var ids='';
	
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	}
	
	if('' == ids)
	{
	   $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要删除的视频！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选视频吗？',
                    
                    ok: function () 
                    { 
                    
                    	var url = "<cms:BasePath/>resources/deleteCovrertVideo.do?ids="+ids+"&<cms:Token mode='param'/>";
                    

 		
						$.ajax({
								   type: "POST",
								   url: url,
								   data:'',
								   
								   success: function(mg)
								   {     
								   				var msg = eval("("+mg+")");
								   				
								               if('success' == msg)
								               {
					
								               		window.location.reload();
								               } 	
								               else
								               {
								               	    $.dialog(
												   { 
													   					title :'提示',
													    				width: '200px', 
													    				height: '60px', 
													                    lock: true, 
													                     
													    				icon: '32X32/fail.png', 
													    				
													                    content: "执行失败，无权限请联系管理员！",
											
													    				cancel: true
													});
								               }   
								              
								            }
								     	});	
                    }
                    
     });
	
	 

}

function setClass(id)
{
	var cidCheck = document.getElementsByName('checkVideo');
	
	var ids='';
	
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	}
	
	if('' == ids)
	{
	   $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需要设置的视频！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	$.dialog({ 
		id: 'occd',
    	title :'设置视频所属',
    	width: '445px', 
    	height: '110px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/resources/SetVideoClass.jsp?dialogApiId=occd&ids='+ids
	});

}


function changeClass()
{
	replaceUrlParam(window.location,'classId='+$('#classId').val());
}

//图片查看效果
loadImageShow();
  

</script>
 </cms:CurrentSite>

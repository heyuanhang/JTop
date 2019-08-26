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
		
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.mousewheel-3.0.4.pack.js"></script>
		<script type="text/javascript" src="../javascript/showImage/fb/jquery.fancybox-1.3.4.pack.js"></script>
		<link rel="stylesheet" type="text/css" href="../javascript/showImage/fb/jquery.fancybox-1.3.4.css" media="screen" />
		
		<script>
		 var selectedTargetClassId = '';
		 
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
        
            api.close(); 
          
       		W.window.location.reload();
         }
         
         
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

    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					 <div class="fl" style="padding: 2px 2px;">
						资源类型:&nbsp;
						<select class="form-select" id="resType" name="resType" onchange="javascript:change();">
							 
							<option value="news">
									图文&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</option>
							<option value="text">
									文本
							</option>
							<option value="image">
									图片
							</option>
							<option value="video">
									视频
							</option>
							<option value="voice">
									语音
							</option>
							<option value="music">
									音乐
							</option>
							
							 
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					
					 
						<div style="padding: 2px 2px;">
							分类:&nbsp;
							<select class="form-select" id="resTag" onchange="javascript:changeTag(this.value)"  >
										<option value="">
											----- 全部资源 -----
										</option>
										<cms:QueryData objName="Tag" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getResTagForTag" var="news">
											<option value="${Tag.resTagName}">
												${Tag.resTagName}
											</option>
										</cms:QueryData>
										
										 
							</select>
									 
							&nbsp;
						</div>
					
					
				 
					
					
					<div style="height:7px"></div>
					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">
							 
							</td>
							
							<td width="8%">
								<strong>封面</strong>
							</td>

							<td width="20%">
								<strong>标题</strong>
							</td>
							
							<td width="4%">
								<strong>已同步</strong>
							</td>
						</tr>
						
						<cms:CurrentSite>
						<cms:QueryData objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var="${param.resTag},${CurrSite.siteFlag},6,${param.pn},">

						 
							<tr>
								<td>
									<input type="radio" name="checkInfo" value="${News.infoId}" />
									<input type="hidden" id="ti-${News.infoId}" value="${News.title}" />
								</td>
								
								<td>
								<div style="height:1px;"></div>
															<cms:if test="${News.img == 'no_url'}">
																	<a class="cmsSysShowSingleImage" href="../style/blue/images/no-image.png"><img id="contentImageShow" src="../style/blue/images/no-image.png" width="100" height="67" />
																	</a>
		
																</cms:if>
																<cms:else>
																
																		<a class="cmsSysShowSingleImage" href="${News.img}"><img id="contentImageShow" src="${News.img}" width="100" height="67" />
																		</a>
																 
																</cms:else>
																<div style="height:1px;"></div>
								</td>

								<td>
									&nbsp;${News.title}
								</td>
								
								<td>
														<div align="left">
															<cms:if test="${News.isTranSucc ==1}">
																<img src="../style/icon/tick.png" />
															</cms:if>
															<cms:else>
																<img src="../style/icon/del.gif" />
															</cms:else>
														 
														</div>
								</td>
							</tr>
						</cms:QueryData>
						</cms:CurrentSite>
						
						<cms:Empty flag="News">
												<tr>
													<td class="tdbgyew" colspan="7">
														<center>
															当前没有数据!
														</center>
													</td>
												</tr>
						</cms:Empty>
						
						
						<cms:PageInfo>
												<tr id="pageBarTr">
													<td colspan="8" class="PageBar" align="left">
														<div class="fr">
															<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
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
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
														<div class="fl"></div>
													</td>
												</tr>
											</cms:PageInfo>
					</table>
		
					<div style="height:10px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								 
								<div style="float:right">
									<a href="javascript:checkInfo();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>
							</tr>
						</table>
					</div>

				</td>
			</tr>


		</table>
		<form method="post" id="commendForm" name="commendForm">
			<input type="hidden" id="contentId" name="contentId" value="${param.contentId}" />

		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

initSelect('resType','${param.resType}');

initSelect('resTag', '<cms:DecodeParam  codeMode='false' str='${param.resTag}'/>');
//图片查看效果
loadImageShow();
 
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function checkInfo()
{
 	
	 var checkInfoId = $('input:radio[name=checkInfo]:checked').val();
 
	  if('undefined' != typeof(checkInfoId))
	  {
		 if('${param.apiId}' == '')
		 {
		 	W.document.getElementById('resId').value = -1;
		 	W.document.getElementById('mtId').value = checkInfoId;
			W.document.getElementById('msgTitle').value = '[图文] '+$('#ti-'+checkInfoId).val();
		 }
		 else
		 {
		 	api.get('${param.apiId}').document.getElementById('resId').value = -1;
			api.get('${param.apiId}').document.getElementById('mtId').value = checkInfoId;
			api.get('${param.apiId}').document.getElementById('msgTitle').value = '[图文] '+$('#ti-'+checkInfoId).val();
		 }
	 
	  }
	  else
	  { 
	  
	  	if('${param.apiId}' == '')
		 {
		 	W.document.getElementById('mtId').value = -1;
		 	W.document.getElementById('resId').value = -1;
			W.document.getElementById('msgTitle').value = '';
		 }
		 else
		 {
			api.get('${param.apiId}').document.getElementById('mtId').value = -1;
			api.get('${param.apiId}').document.getElementById('resId').value = -1;
		    api.get('${param.apiId}').document.getElementById('msgTitle').value = '';
		 }
	  	
	  }
		
	   close();
}

function change()
{
	var resType = $('#resType').val();
	
	if('image' == resType)
	{
		window.location = 'SelectWxImageRes.jsp?resType=image&apiId=${param.apiId}';
	}
	else if('video' == resType)
	{
		window.location = 'SelectWxVideoRes.jsp?resType=video&apiId=${param.apiId}';
	}
	else if('voice' == resType)
	{
		window.location = 'SelectWxVoiceRes.jsp?resType=voice&apiId=${param.apiId}';
	}
	else if('music' == resType)
	{
		window.location = 'SelectWxMusicRes.jsp?resType=music&apiId=${param.apiId}';
	}
	else if('text' == resType)
	{
		window.location = 'SelectWxTextRes.jsp?resType=text&apiId=${param.apiId}';
	}

}

function changeTag(val)
{ 
	replaceUrlParam(window.location, 'resTag='+encodeURI(val));
}

  
</script>

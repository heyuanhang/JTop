<%@ page contentType="text/html; charset=utf-8" session="false"%>

<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<!--加载 js -->
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
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
							<a href="#">微信公众号</a> &raquo;
							<a href="#">图文素材管理</a> 
						 		</td>
						<td align="right"></td>
					</tr>
				</table>
			</div>
			<div style="height:25px;"></div>

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										类型:
									<select class="form-select" id="resTag" onchange="javascript:change(this.value)"  >
										<option value="">
											------ 所有图文类型 ------
										</option>
											<cms:QueryData objName="Tag"
												service="cn.com.mjsoft.cms.weixin.service.WeixinService"
												method="getResTagForTag" var="news">
												<option value="${Tag.resTagName}">
													${Tag.resTagName}
												</option>
											</cms:QueryData>


										</select>
									&nbsp;
										
									</div>
									<div>
									
										<a href="javascript:openAddCommendContentDialog('','','false');" class="btnwithico"> <img src="../../core/style/icons/document-text-image.png" alt="" /><b>添加图文&nbsp;</b> </a>
									 	
									 	<a href="javascript:openManageResTagDialog();" class="btnwithico"> <img src="../../core/style/icons/folder-tree.png" alt="" /><b>分类维护&nbsp;</b> </a>
									 	
									 	<a href="javascript:openAddToSendContentDialog();" class="btnwithico"> <img src="../../core/style/icons/megaphone.png" alt="" /><b>群发消息&nbsp;</b> </a>
									 	
									 	<a href="javascript:transferWxRes();" class="btnwithico"> <img src="../../core/style/icons/document-page-next.png" alt="" /><b>同步到微信&nbsp;</b> </a>
										
					
										<a href="javascript:deleteWxRes();" class="btnwithico"> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
										&nbsp;(注意：重新编辑资源后，需同步到微信服务器)
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
												 <td width="1%">
													<strong>ID</strong>
												</td>
												
												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
												 
												 
												<td width="6%">
													<strong>封面</strong>
												</td>
												
												
												<td width="32%">
													<strong>标题</strong>
												</td>
												
												<td width="3%">
													<strong>已同步</strong>
												</td>

												<td width="9%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:QueryData objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var="${param.resTag},${CurrSite.siteFlag},5,${param.pn},">


												<tr>
													 
													 <td>
														${News.infoId}
													</td>
													
													<td>
															<input class="inputCheckbox" name="checkedId"
																value="${News.infoId}" type="checkbox" onclick="javascript:" />
																
																<input id="rf-${News.infoId}"
																value="${News.rowFlag}" type="hidden" onclick="javascript:" />
																
																<input id="mid-${News.infoId}"
																value="${News.mediaId}" type="hidden" onclick="javascript:" />
													</td>
													 
													
													<td align="center">
														<div style="height:3px;"></div>
														
														<cms:QueryData list="true" reObj="News.rowInfoList" objName="Nr" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var="${param.resTag},${CurrSite.siteFlag},${param.pn},10,">
															
															
															<cms:if test="${status.index == 0}">
															
																<cms:if test="${Nr.img == 'no_url'}">
																	<a class="cmsSysShowSingleImage" href="../style/blue/images/no-image.png"><img id="contentImageShow" src="../style/blue/images/no-image.png" width="100" height="70" />
																	</a>
		
																</cms:if>
																<cms:else>
																
																		<a class="cmsSysShowSingleImage" href="${Nr.img}"><img id="contentImageShow" src="${Nr.img}" width="100" height="70" />
																		</a>
																 
																</cms:else>
															
															</cms:if>
															
																													
														</cms:QueryData>


														<div style="height:3px;"></div>
													</td>
													<td>
														<div align="left">
														
															<cms:QueryData list="true" reObj="News.rowInfoList" objName="Nr" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var="${param.resTag},${CurrSite.siteFlag},${param.pn},10,">
																
																<span class="STYLE1">&nbsp; <a href="javascript:openEditWxNewsInfoDialog('','${status.count}','${Nr.infoId}');"><font style="text-decoration:underline">${Nr.title}</font> </a> </span>
														
															
															</cms:QueryData>

														</div>
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

													
													<td>
														<div align="center">
															<span class="STYLE4"> <img src="../../core/style/icons/monitor-window.png" width="16" height="16" /> <a href="javascript:openPreviewDialog('${News.rowFlag}');">预览</a> </span>
												 	 
															
															<span class="STYLE4"> <img src="../../core/style/icons/document-text-image.png" width="16" height="16" /> <a href="javascript:openAddCommendContentDialog('${status.count+1}','${News.rowFlag}','true');">增加</a> </span>
															
															
															<span class="STYLE4"> <img src="../../core/style/default/images/doc_delete.png" width="16" height="16" /> <a href="javascript:openDeleteCommendRowDialog('${News.commendFlag}','${News.rowFlag}');">删除</a> </span>
														</div>
													</td>

												</tr>
												

											</cms:QueryData>

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

									</div>
								<div class="mainbody-right"></div>
								</td>
							</tr>

						</table>

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

initSelect('resTag', '<cms:DecodeParam  codeMode='false' str='${param.resTag}'/>');


//图片查看效果
loadImageShow();

var targetSortId = -1;

function regId(check)
{
   if(check.checked==true)
   {
      targetSortId=check.value;
   }
   else
   {
      targetSortId = -1;
   }
} 


 
 

function openAddCommendContentDialog(sindex, rowFlag,inCol)
{
	var cf = '添加图文素材';
	
	if('true' == inCol)
	{
		cf = '添加子图文素材';
	}
	
	$.dialog({ 
	    id : 'ospcd',
    	title : cf,
    	width: '1100px', 
    	height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/AddWxNewsInfo.jsp?index='+sindex+'&inCol='+inCol+'&rowFlag='+rowFlag+'&uid='+Math.random()+'&typeId=${param.typeId}&dialogApiId=ospcd'
	});
}

function openEditWxNewsInfoDialog(classId, sindex, infoId)
{
	$.dialog({ 
	    id : 'oeccd',
    	title : '编辑图文素材 - ID: '+infoId,
    	width: '1100px', 
    	height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/EditWxNewsInfo.jsp?index='+sindex+'&uid='+Math.random()+'&typeId=${param.typeId}&classId='+classId+'&infoId='+infoId+'&dialogApiId=oeccd'
	});
}

function openDeleteCommendRowDialog(commFlag, rowFlag)
{
	$.dialog({ 
	    id : 'odcrd',
    	title : '删除图文素材',
    	width: '520px', 
    	height: '365px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/DeleteNewsItemGroupInfo.jsp?typeId=${param.typeId}&uid='+Math.random()+'&commFlag='+commFlag+'&rowFlag='+rowFlag
	});
}

 
function transferWxRes()
{
	var cidCheck = document.getElementsByName('checkedId');
	
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
    				
                    content: '请选择需要同步的资源！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	
	 
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认同步所选资源吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>wx/transferNews.do?type=news&ids="+ids+"&<cms:Token mode='param'/>";
	
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {      
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               		 	
			               		$.dialog({ 
				   					title :'提示',
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '同步图文素材成功!',
				                    
				                    ok: function () 
				                    { 
				                    	window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               }
			               else if(mg.indexOf('资源ID:') != -1 )
			               {
			               	       $.dialog(
								   { 
									   					title :'提示',
									    				width: '220px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				
									                    content: msg,
							
									    				cancel: function () 
									                    { 
									                    	window.location.reload();
									                    }
									});
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
							
									    				cancel: function () 
									                    { 
									                    	window.location.reload();
									                    }
									});
			               }   
			              
			            }
			     	});	
       
       
    				}, 
    				cancel: true 
   	});


}

function openPreviewDialog(rowFlag)
{
	$.dialog({ 
	    id : 'odcrd',
    	title : '预览图文素材',
    	width: '400px', 
    	height: '565px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/PreviewWxNews.jsp?rowFlag='+rowFlag
	});


}

function openAddToSendContentDialog()
{
	var cidCheck = document.getElementsByName('checkedId');
	
	var ids='';
	var more = 0;
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value;
			 more++;
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
    				
                    content: '请选择需要群发的资源！', 
       cancel: true 
                    
	  });
	  return;
	}
	
	if(more > 1)
	{
		$.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true,
                    
    				icon: '32X32/i.png', 
    				
                    content: '群发内容只可选择一条！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	var mid = $('#mid-'+ids).val();
	 
	if(mid == null || '' == mid )
	{
		$.dialog({ 
   					title :'提示',
    				width: '240px', 
    				height: '60px', 
                    lock: true,
                    
    				icon: '32X32/i.png', 
    				
                    content: '没有上传微信服务器的资源不可群发！', 
	       cancel: true 
	                    
		  });
		  return;
	}
	
	ids = $('#rf-'+ids).val();
	 
	

	$.dialog({ 
	    id : 'odcrd',
    	title : '群发消息申请',
    	width: '440px', 
    	height: '195px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/SendAllWxInfo.jsp?msgId='+ids+'&msgType=news'
	});
}


function deleteWxRes()
{
	var cidCheck = document.getElementsByName('checkedId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += $('#rf-'+cidCheck[i].value).val()+',';
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
    				
                    content: '请选择需要删除的资源！', 
       cancel: true 
                    
	  });
	  return;
	}

	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除资源吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>wx/deleteAllNews.do?ids="+ids+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
			            	
			               if('success' == msg)
			               {
			               		 	
			               		$.dialog({ 
				   					title :'提示',
				    				width: '200px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				
				                    content: '删除图文素材成功!',
				                    
				                    ok: function () 
				                    { 
				                    	window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
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
							
									    				cancel: function () 
									                    { 
									                    	window.location.reload();
									                    }
									});
			               }   
			              
			            }
			     	});	
       
       
    				}, 
    				cancel: true 
   	});
	
	
	
	

}

function sendPreview(rowFlag)
{
	var tip = $.dialog.tips('正在执行...',3600000000,'loading.gif');
    
   
    var url = "<cms:BasePath/>wx/sendAll.do"+"?<cms:Token mode='param'/>";
 
 	var postData = 'preview=true&type=mpnews&previewId=o7j7ot4hqvkGO5yMwLKtr3ZPLvEY&newsId='+rowFlag;
  				
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,

       	success: function(mg)
        {     
  			var msg = eval("("+mg+")");
  			
           if(mg.indexOf('success') != -1)
           {
           		$.dialog({ 
	   					title :'提示',
	    				width: '170px', 
	    				height: '60px', 
	    				 
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '发送成功！', 
	                    ok: function(){ 
      						window.location.reload();
    					} 
		  		});
           		
           } 	
           
           if(mg.indexOf('errcode') != -1)
           {
           		$.dialog({ 
	   					title :'提示',
	    				width: '270px', 
	    				height: '60px', 
	    				 
	                    lock: true, 
	    				icon: '32X32/fail.png',
	                    content: msg, 
	                    ok: function(){ 
      						window.location.reload();
    					} 
		  		});
           		
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
						
								    				cancel: function(){ 
								      						window.location.reload();
								    				} 
								});
								
							 
		
	                            tip.close();
           }   
        }
 	});	


}

function openManageResTagDialog()
{
	$.dialog({ 
		id: 'omrtd',
    	title :'管理分类标签',
    	width: '380px', 
    	height: '500px',  
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/dialog/ManageResTag.jsp?dialogApiId=omrtd&type=news'
	});
}

function change(val)
{ 
	replaceUrlParam(window.location, 'resTag='+encodeURI(val));
}

</script>
 </cms:CurrentSite>

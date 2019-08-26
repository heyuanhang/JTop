<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/uuid.js"></script>
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
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

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
							<a href="#">微信公众号</a> &raquo;
							<a href="#">群发信息管理</a> 
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<form id="roleForm" name="roleForm" method="post">

			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
				<tr>
					<td class="mainbody" align="left" valign="top">
						<!--main start-->
						<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										审核状态:
									<select class="form-select" id="censor" name="censor" onchange="javascript:change(this.value)"  >
										<option value="">
											--- 所有群发 ---
										</option>
											<option value="-1">
													等待审核
											 </option>
											 <option value="0">
													未通过
											 </option>
											  <option value="1">
													已通过
											 </option>
											 
											  <option value="9999">
													已发送
											 </option>


										</select>
									&nbsp;
										
									</div>
								
									<div  >
										 
										 
										</div>
									
								 
									<div >
										 操作人筛选:&nbsp;
										<input id="searchKey" name="query" size="30" maxlength="60" class="form-input"   value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
										<input onclick="javascript:search();" value="查询" class="btn-1" type="button" style="vertical-align:top;" />

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												 
												
												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
 
												<td width="4%">
													<strong>消息类型</strong>
												</td>
												
												<td width="8%">
													<strong>操作人</strong>
												</td>
												
												<td width="4%">
													<strong>操作时间</strong>
												</td>

												<td width="8%">
													<strong>发送目标</strong>
												</td>
												
												<td width="4%">
													<strong>发送时间</strong>
												</td>
												
												<td width="3%">
													<strong>状态</strong>
												</td>

												 
												<td width="9%">
													<center><strong>操作</strong></center>
												</td>

												
											</tr>
											<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getSendAllInfoForTag" objName="SA" var=",${param.censor},${param.key},${param.pn},10">
 
												<tr>

												 	 
													<td>
														<input class="inputCheckbox"  name="checkedId" value="${SA.saId}" type="checkbox" onclick="javascript:" />
													</td>
													
													<td>														
															<cms:if test="${SA.msgType == '图文'}">
																<img src="../style/icons/document-text-image.png" />
															</cms:if>
															<cms:elseif test="${SA.msgType == '图片'}">
																<img src="../style/icons/image-vertical-sunset.png" />
															</cms:elseif>
															<cms:elseif test="${SA.msgType == '文本'}">
																<img src="../style/icons/script.png" />
															</cms:elseif>
															<cms:elseif test="${SA.msgType == '视频'}">
																<img src="../style/icons/film.png" />
															</cms:elseif>
															<cms:else>
																<img src="../style/icons/socket.png" />
															</cms:else>
															
															${SA.msgType}
													</td>
													
													<td>																												
															${SA.exeMan}
													</td>
													
													<td>			
															<cms:FormatDate date="${SA.exeTime}" format="yyyy-MM-dd HH:mm:ss"/>	
													</td>
													
													<td>
															<cms:if test="${SA.sendTarget != -9999}">
															 	<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxUserGroupForTag" objName="UG" var="${SA.sendTarget}">
			 														${UG.wuGroupName}
			 													</cms:QueryData>
															</cms:if>
															<cms:else>
																全部订阅用户
															</cms:else>		
														
															
													</td>	
													
													<td>
															<cms:FormatDate date="${SA.sendDT}" format="yyyy-MM-dd HH:mm:ss"/>	
													</td>		
														
																					
													<td>		

															<cms:if test="${SA.censor == 9999}">
															 <img src="../style/icons/balloon--arrow.png" title="已发送"/>
															</cms:if>
															<cms:elseif test="${SA.censor == 1}">
															 <img src="../style/icon/tick.png" title="审核通过" />
															</cms:elseif>
															<cms:elseif test="${SA.censor == -1}">
																<img src="../style/icons/clock.png" title="等待审核"/>
															</cms:elseif>	
															<cms:else>
																<img src="../style/icon/del.gif" title="未通过审核"/>
															</cms:else>							 
												   </td>
													
													
													
													<td>
															<div>
																<center>
																	 <a href="javascript:censorDialog('${SA.msgType}','${SA.msgId}','${SA.saId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;审核</a>&nbsp;&nbsp;&nbsp; <a href="javascript:openReturnMsgDialog('${SA.saId}');"><img src="../../core/style/icons/document-task.png" width="16" height="16" />&nbsp;结果</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/icons/xfn-friend.png" width="16" height="16" /><a href="javascript:sendPreview('${SA.msgId}','${SA.msgType}');">预览</a> 
																	</span>
																</center>
															</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="SA">
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
<cms:LoginUser>
<script>

initSelect('censor','${param.censor}');

function search()
{
	var key = encodeURI(encodeURI(document.getElementById('searchKey').value));
	
	
	replaceUrlParam(window.location, 'key='+encodeData(key));
 
}

function openCreateWxMessageDialog()
{
	 
	$.dialog({ 
		id:'ocwmd',
    	title :'新增自动回复',
    	width: '570px', 
    	height: '240px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/CreateWxMessage.jsp'
	});
}

function openEditWxMessageDialog(id)
{
	 

	$.dialog({ 
		id: 'oewmd',
    	title :'编辑自动回复',
    	width: '570px', 
    	height: '240px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/EditWxMessage.jsp?id='+id
	});
}

function deleteWxMsgs()
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
    				
                    content: '请选择需要删除的回复信息！', 
       cancel: true 
                    
	  });
	  return;
	}

	deleteWxMsg(ids);
	
	
	
	

}

function deleteWxMsg(id)
{
	 
	$.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除微信自动回复吗？',
                    
                    ok: function () 
                    { 
                    
                   
                    var url = "<cms:BasePath/>wx/deleteWxMsg.do?ids="+id+"&<cms:Token mode='param'/>";
                    
 		
 				
 		
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
				    				
				                    content: '删除微信自动回复成功!',
				                    
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

function change(val)
{
	replaceUrlParam(window.location, 'censor='+val);
}



function censorDialog(msgType, msgId, saId)
{
	var resType = msgType;
	
	var cenorPage = '';
	
	var w ='';
	
	var h = '';
	
	if('图文' == resType)
	{
		cenorPage = 'core/weixin/PreviewWxSendAllNews.jsp?rowFlag='+msgId+'&saId='+saId;
		
		w='400';
		h='565';
	}
	else if('图片' == resType)
	{
		resType = '图片';
		
		cenorPage = 'core/weixin/CensorWxImageRes.jsp?id='+msgId+'&saId='+saId;
		
		
		w='510';
		h='230';
	}
	else if('视频' == resType)
	{
		resType = '视频';
		
		cenorPage = 'core/weixin/CensorWxVideoRes.jsp?id='+msgId+'&saId='+saId;
		
		w='860';
		h='610';
	}
	else if('录音' == resType)
	{
		resType = '录音';
	
		cenorPage = 'core/weixin/CensorWxVoiceRes.jsp?id='+msgId+'&saId='+saId;
		
		
		w='620';
		h='400';
	}
	//else if('音乐' == resType)
	//{
		//resType = '音乐';
		
		//cenorPage = 'core/weixin/CensorWxMusicRes.jsp?id='+msgId+'&saId='+saId;
		
		
		//w='560';
		//h='510';
	//}
	else if('文本' == resType)
	{
		resType = '文本';
		
		cenorPage = 'core/weixin/CensorWxTextRes.jsp?id='+msgId+'&saId='+saId;
		
		w='860';
		h='610';
		
	}
 
	$.dialog({ 
	    id : 'oewtd',
    	title : '浏览并审核群发内容',
    	width: w+'px', 
    	height: h+'px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>'+cenorPage+"&uid="+Math.random()
	});
}

function sendPreview(resId, msgType)
{
	var tip = $.dialog.tips('正在执行...',3600000000,'loading.gif');
    
    var resType = '';
	 
	
	if('图文' == msgType)
	{
		resType = 'mpnews';
	}
	else if('图片' == msgType)
	{
		resType = 'image';
	}
	else if('视频' == msgType)
	{
		resType = 'mpvideo';
	}
	else if('录音' == msgType)
	{
		resType = 'voice';
	}
	else if('音乐' == msgType)
	{
		resType = 'music';
	}
	else if('文本' == msgType)
	{
		resType = 'text';
		
	}
   
    var url = "<cms:BasePath/>wx/sendAll.do"+"?<cms:Token mode='param'/>";
 
 	var postData = 'preview=true&type='+resType+'&resId='+resId;
  			 
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
	                    content: '发送预览成功！', 
	                    ok: function(){ 
      						window.location.reload();
    					} 
		  		});
           		
           } 	
           
           else if(mg.indexOf('errcode') != -1 || mg.indexOf('error') != -1    )
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

function openReturnMsgDialog(id)
{
	$.dialog({ 
		id: 'ormd',
    	title :'群发到微信响应',
    	width: '770px', 
    	height: '240px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/weixin/ViewSendAllReturnMsg.jsp?saId='+id
	});

}

</script>
</cms:LoginUser>

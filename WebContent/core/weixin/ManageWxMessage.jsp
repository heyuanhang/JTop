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
							<a href="#">回复信息管理</a> 
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
								<td style="padding: 6px 11px;" class="">
									<div class="fl">
										<a href="javascript:openCreateWxMessageDialog();" class="btnwithico" onclick=""><img src="../style/icons/zone--plus.png" width="16" height="16" /><b>添加自动回复&nbsp;</b> </a>
											<a href="javascript:deleteWxMsgs();" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
							
										<span></span>
							
										</div>
									
								 
									<div class="fr">
										 搜索关键字:&nbsp;
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
												<td width="2%">
													<strong>ID</strong>
												</td>
												
												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
 
												<td width="8%">
													<strong>关键字</strong>
												</td>
												
												<td width="4%">
													<strong>匹配模式</strong>
												</td>

												<td width="4%">
													<strong>回复类型</strong>
												</td>
												
												<td width="16%">
													<strong>信息摘要</strong>
												</td>

												 
												
												<td width="8%">
													<center><strong>操作</strong></center>
												</td>

												
											</tr>
											<cms:QueryData service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxMessageForTag" objName="WM" var=",${param.key},${param.pn},10">
 
												<tr>

												 	<td>
														${WM.msgId}
													</td>
													<td>
														<input class="inputCheckbox"  name="checkedId" value="${WM.msgId}" type="checkbox" onclick="javascript:" />
													</td>
													
													<td>														
															${WM.inputKey}
													</td>
													
													<td>														
															
															<cms:if test="${WM.isInclude == 0}">
																全字
															</cms:if>
															<cms:else>
																模糊
															</cms:else>
													</td>
													
													<td>
															${WM.msgType}
													</td>		
														
																					
													<td>		
													
													
													<cms:if test="${WM.infoId != -1}">
															<cms:QueryData objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemForTag"  var=",,,,${WM.infoId}">																			
																								
															${News.title}	
															</cms:QueryData>		
													
													</cms:if>

													<cms:elseif  test="${WM.resId != -1}">
													
														<cms:QueryData objName="WR" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getWxResForTag"  var="${WM.resId},,,,">
														 		
														 		${WR.resTitle}
														 	
														 </cms:QueryData>
													
													</cms:elseif>
													<cms:elseif  test="${WM.isText == 1}">
													
													 	   
													
													</cms:elseif>
													<cms:else>
													 		
													</cms:else>
													
													<input type="hidden" id="mtId" name="infoId"  class="form-input" value="${WM.infoId}"></input>
													<input type="hidden" id="resId" name="resId"  class="form-input" value="${WM.resId}"></input>
												 
													 
												</td>
													
													
														
																				
													</td>
													
													 
													
													<td>
															<div>
																<center>
																	<span class="STYLE4"><a href="javascript:openEditTextDialog('${WM.msgId}')"><img src="../../core/style/icons/script-attribute-t.png" width="16" height="16" />&nbsp;文本</a>&nbsp;&nbsp;&nbsp;<a href="javascript:openEditWxMessageDialog('${WM.msgId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteWxMsg('${WM.msgId}');">删除</a> 
																	</span>
																</center>
															</div>
													</td>
												</tr>

											</cms:QueryData>
											<cms:Empty flag="WM">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
											</cms:Empty>
											<cms:if test="${empty param.key}">
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
											</cms:if>
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

 

function search()
{
	var key = encodeURI(encodeURI(document.getElementById('searchKey').value));
	
	window.location='ManageWxMessage.jsp?key='+encodeData(key);
}

function openCreateWxMessageDialog()
{
	 
	$.dialog({ 
		id:'ocwmd',
    	title :'新增自动回复',
    	width: '700px', 
    	height: '310px', 
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
    	width: '700px', 
    	height: '310px',  
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
	replaceUrlParam(window.location, 'type='+val);
}



function openEditTextDialog(msgId)
{
	$.dialog({ 
	    id : 'oewtd',
    	title : '编辑回复文本',
    	width: '780px', 
    	height: '540px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/weixin/EditWxText.jsp?msgId='+msgId+"&uid="+Math.random()
	});
}



</script>
</cms:LoginUser>

<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>

		 

		<script>  
		
        	
      </script>
	</head>
	<body>
		<cms:LoginUser>
			 

			 
			<form id="advertForm" name="advertForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<center>
								<div class="auntion_tagRoom" style="margin-top:4px;width:99%">
									<ul>
										<li id="two1" onclick="javascript:setTab(1);" class="selectTag">
											<a href="javascript:;"><img src="../style/icons/mail-open-document-text.png" width="16" height="16" />我的收件箱&nbsp;</a>
										</li>
										<li id="two2" onclick="javascript:setTab(2);">
											<a href="javascript:;"><img src="../style/icons/mail-forward.png" width="16" height="16" />我的发件箱&nbsp;</a>
										</li>

										<%--<li id="two3" onclick="setTab2('two',3,3)">
											<a href="javascript:;"><img src="../style/icon/trash2.png" width="16" height="16" />已删除消息&nbsp;</a>
										</li>
									--%>
									</ul>
								</div>

								<div class="auntion_tagRoom_Content">
									<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
										<ul>
											<li>
												<table width="99.5%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																			&nbsp;状态
																			<select class="form-select" id="reStatus" onchange="javascript:changeRe(this);">
																				<option>
																					---- 全部接收消息 ----
																				</option>
																				<option value="1">
																					未读
																				</option>
																				<option value="2">
																					已读
																				</option>
																				<option value="3">
																					已回
																				</option>

																			</select>

																			&nbsp;
																		</div>
																		<div>
																			<a id="applyAudit" href="javascript:deleteMsg('re');" class="btnwithico"> <img id="applyAuditImg" src="../../core/style/icons/mail--minus.png" alt="" /><b id="applyAudit-b">删信&nbsp;</b> </a>
																		</div>
																		<div class="fr">
																			<%--
																			标题关键字:&nbsp;
																			<input class="form-input" id="query" name="query" size="30" />
																			<input onclick="" value="搜索" class="btn-1" type="button"  />
																		--%>
																		</div>

																	</td>
																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">

																			<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">
																					<td width="2%">
																						<strong>ID</strong>
																					</td>
																					<td width="2%">
																						<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkReId',this);"/>
																					</td>
																					<td width="17%">
																						<strong>信息标题</strong>
																					</td>

																					<td width="4%">
																						<strong>状态</strong>
																					</td>

																					<td width="8%">
																						<strong>发送者</strong>
																					</td>

																					<td width="7%">
																						<strong>收信时间</strong>
																					</td>


																				</tr>

																				<cms:QueryData service="cn.com.mjsoft.cms.message.service.MessageService" method="getManagerMessageTag" objName="Msg" var="${Auth.identity},re,${param.reStatus},${param.seStatus},,${param.pn},11">
																					<tr>
																						<td>
																							${Msg.msgId}
																						</td>
																						<td>
																							<input type="checkbox" name="checkReId" value="${Msg.msgId}" onclick="javascript:" />
																						</td>

																						<td>
																							<a href="javascript:openReadAndSendMessageDialog('${Msg.msgId}');"> <cms:if test="${Msg.isReply == 1}">
																									<img src="../style/icons/mail-receive.png" width="16" height="16" />
																								</cms:if> <cms:else>
																									<img src="../style/icons/mail.png" width="16" height="16" />
																								</cms:else> &nbsp;&nbsp;${Msg.msgTitle}</a>
																						</td>
																						<td>
																							<cms:if test="${Msg.isReply == 1}">
																								<font color="green">已回</font>
																							</cms:if>
																							<cms:elseif test="${Msg.isRead == 1}">
																								<font color="blue">已读</font>
																							</cms:elseif>
																							<cms:else>
																								<font color="red">未读</font>
																							</cms:else>

																						</td>
																						<td>
																							<cms:if test="${Msg.sender == -9999}">
																							
																								System
																							</cms:if>
																							<cms:else>
																								<cms:SystemUser id="${Msg.sender}">
																									${SysUser.userName}
																							</cms:SystemUser>

																							</cms:else>

																						</td>
																						<td>
																							<cms:FormatDate date="${Msg.sendDT}" />
																						</td>

																					</tr>
																				</cms:QueryData>

																				<cms:Empty flag="Msg">
																					<tr>
																						<td class="tdbgyew" colspan="9">
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
																								<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jumpRe()" /> </span>
																								<span class="page">[<a href="javascript:queryRe('h');">首页</a>]</span>
																								<span class="page">[<a href="javascript:queryRe('p');">上一页</a>]</span>
																								<span class="page">[<a href="javascript:queryRe('n');">下一页</a>]</span>
																								<span class="page">[<a href="javascript:queryRe('l');">末页</a>]</span>&nbsp;
																							</div>
																							<script>
																										function queryRe(flag)
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
																										
																											
																											replaceUrlParam(window.location,'tab=1&pn='+cp);		
																										}
																							
																							
																										function jumpRe()
																										{
																											replaceUrlParam(window.location,'tab=1&pn='+document.getElementById('pageJumpPos').value);
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
											</li>
										</ul>
									</div>

									<!-- 第二部分:内容审核 -->
									<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">

										<ul>
											<li>
												<table width="99.5%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">

																		<div class="fl">
																			&nbsp;状态
																			<select class="form-select" id="seStatus" onchange="javascript:changeSe(this);">
																				<option>
																					---- 全部发送消息 ----
																				</option>
																				<option value="1">
																					对方未读
																				</option>
																				<option value="2">
																					对方已读
																				</option>
																				<option value="3">
																					对方已回
																				</option>
																			</select>

																			&nbsp;
																		</div>

																		<div>
																			<a id="applyAudit" href="javascript:openSendMessageDialog();" class="btnwithico"> <img id="applyAuditImg" src="../../core/style/icons/mail--plus.png" alt="" /><b id="applyAudit-b">新短信&nbsp;</b> </a>
																			<a id="applyAudit" href="javascript:deleteMsg('se');" class="btnwithico"> <img id="applyAuditImg" src="../../core/style/icons/mail--minus.png" alt="" /><b id="applyAudit-b">删信&nbsp;</b> </a>
																		</div>
																		<div class="fr">
																			<%--
																			标题关键字:&nbsp;
																			<input class="form-input" id="query" name="query"  size="30" />
																			<input onclick="javascript:deleteMsg('se');" value="搜索" class="btn-1" type="button" />
																		--%>
																		</div>

																	</td>
																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">

																			<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">
																					<td width="2%">
																						<strong>ID</strong>
																					</td>
																					<td width="2%">
																						<input type="checkbox" name="checkbox" onclick="javascript:selectAll('checkSeId',this);" />
																					</td>
																					<td width="20%">
																						<strong>信息标题</strong>
																					</td>

																					<td width="4%">
																						<strong>状态</strong>
																					</td>

																					<td width="7%">
																						<strong>收信者</strong>
																					</td>

																					<td width="7%">
																						<strong>发送时间</strong>
																					</td>
																				</tr>

																				<cms:QueryData service="cn.com.mjsoft.cms.message.service.MessageService" method="getManagerMessageTag" objName="Msg" var="${Auth.identity},se,${param.reStatus},${param.seStatus},,${param.pn},11">
																					<tr>
																						<td>
																							${Msg.msgId}
																						</td>
																						<td>
																							<input type="checkbox" name="checkSeId" value="${Msg.msgId}" onclick="javascript:" />
																						</td>

																						<td>
																							<a href="javascript:openReadMessageDialog('${Msg.msgId}');"><img src="../style/icons/mail-send.png" width="16" height="16" />&nbsp;&nbsp;${Msg.msgTitle}</a>
																						</td>
																						<td>
																							<cms:if test="${Msg.isReply == 1}">
																								<font color="green">已回</font>
																							</cms:if>
																							<cms:elseif test="${Msg.isRead == 1}">
																								<font color="blue">已读</font>
																							</cms:elseif>
																							<cms:else>
																								<font color="red">已发</font>
																							</cms:else>

																						</td>
																						<td>
																							<cms:SystemUser id="${Msg.userId}">
																									${SysUser.userName}
																								</cms:SystemUser>
																						</td>
																						<td>
																							<cms:FormatDate date="${Msg.sendDT}" />
																						</td>

																					</tr>
																				</cms:QueryData>

																				<cms:Empty flag="Msg">
																					<tr>
																						<td class="tdbgyew" colspan="9">
																							<center>
																								当前没有数据!
																							</center>
																						</td>
																					</tr>
																				</cms:Empty>

																				</tr>

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
																										
																											
																											replaceUrlParam(window.location,'tab=2&pn='+cp);		
																										}
																							
																							
																										function jump()
																										{
																											replaceUrlParam(window.location,'tab=2&pn='+document.getElementById('pageJumpPos').value);
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
											</li>
										</ul>
									</div>

									<!-- 第三部分:留言回复 -->
									<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>

											</li>
										</ul>
									</div>


								</div>
							</center>
						</td>
					</tr>
				</table>

				<!-- hidden -->


			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

var api = frameElement.api, W = api.opener;

if('' != '${param.tab}')
{
	setTab('${param.tab}');
}

initSelect('reStatus','${param.reStatus}');
initSelect('seStatus','${param.seStatus}');

function openSendMessageDialog()
{
	W.$.dialog({ 
	    id : 'osmd',
    	title : '发送站内信',
    	width: '750px', 
    	height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
    	parent:api,
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/message/CreateAndSendMessageDialog.jsp?dialog=true'
	});
}

function openReadAndSendMessageDialog(id)
{
	W.$.dialog({ 
	    id : 'orasmd',
    	title : '阅读和回复站内信',
    	width: '750px', 
    	height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	parent:api,
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/message/ReadAndSendMessageDialog.jsp?dialog=true&msgId='+id
	});
}

function openReadMessageDialog(id)
{
	W.$.dialog({ 
	    id : 'orasmd',
    	title : '查看已发站内信',
    	width: '750px', 
    	height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
    	parent:api,
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/message/ReadMessage.jsp?dialog=true&msgId='+id
	});
}

function deleteMsg(flag)
{
		var cidCheck;
		
		if('se' == flag)
		{
			cidCheck = document.getElementsByName('checkSeId');
		}
		else if('re' == flag)
		{
			cidCheck = document.getElementsByName('checkReId');
		}
		
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
		   W.$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				parent:api,
	                    content: '请选择要删除的信件！', 
	       cancel: true 
	                    
		
		  });
		  return;
		}
	
		var url = "<cms:BasePath/>message/deleteMsg.do?ids="+ids+"&msgFlag="+flag+"&<cms:Token mode='param'/>";
         
		W.$.ajax({
					      		type: "POST",
					       		url: url,
					       		data:'',
					   
					       		success: function(mg)
					            {     
					            	var msg = eval("("+mg+")");
					            	
					               if('success' == msg)
					               {
					               		W.$.dialog(
									    { 
									   					title :'提示',
									    				width: '130px', 
									    				height: '60px', 
									                    lock: true, 
									                  
									    				icon: '32X32/i.png', 
									    				parent:api,
									                    content: '删除站内信成功!',
							
									    				ok:function()
									    				{ 
									    				  if('se' == flag)
									    				  {
									    				  	replaceUrlParam(window.location,'tab=2');
									    				  }
									    				  else
									    				  {
							             					replaceUrlParam(window.location,'tab=1');
							             				   }
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
									                    parent:api,
									    				icon: '32X32/fail.png', 
									    				
									                    content: "执行失败，无权限请联系管理员！",
							
									    				cancel: true
										});
					               
					               }
					              
					            }
		 });
	
	
}

function changeRe(obj)
{
	replaceUrlParam(window.location,'tab=1&seStatus=-1&reStatus='+obj.value);
}

function changeSe(obj)
{
	replaceUrlParam(window.location,'tab=2&reStatus=-1&seStatus='+obj.value);
}




function setTab(tab)
{
	setTab2('two',tab,2);
}


function re()
{
 window.location.reload();
}

</script>




</cms:LoginUser>

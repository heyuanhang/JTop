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
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>

		<script>
	     var api = frameElement.api, W = api.opener; 
	     
	     if("true"==="${param.fromFlow}")
         {  
         	    if("true"==="${param.censor}")
         		{
         			W.$.dialog.tips('改变评论状态成功',1,'32X32/succ.png'); 	
         		}
         		else
         		{
         			W.$.dialog.tips('删除评论成功',1,'32X32/succ.png'); 	
         		} 
	                    
         }

      </script>
	</head>
	<body>

		<!--main start-->
		<div class="auntion_tagRoom" style="margin-top:2px">
			<ul>
				<li id="two1" class="selectTag">
					<a><img src="../../core/style/icon/balloon-ellipsis.png" width="16" height="16" />内容评论&nbsp;</a>
				</li>
			</ul>
		</div>
		<div style="height:5px;"></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">


						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div>
									<cms:SystemComment contentId="${param.contentId}" size="18" pn="${param.pn}" censor="${param.status}">
										<table width="100%" cellpadding="0" cellspacing="0" border="0">

											<tr class="datahead">
												<td width="100%" height="60">
													<table cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td width="30%">
																<strong>评论人:</strong>&nbsp;${Comment.userName}
															</td>

															<td width="40%">
																<strong>评论时间:</strong>&nbsp;${Comment.commDT}
															</td>

															<td width="40%">
																<strong>IP地址:</strong>&nbsp;${Comment.ip}&nbsp;&nbsp;&nbsp;
																<cms:IPArea ip="${Comment.ip}" />
															</td>
														</tr>
													</table>
												</td>

											</tr>


											<tr>
												<td>
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="31%" height="60" valign="bottom">
																<textarea readonly style="width:520px;height:59px" class="form-textarea" readonly>${Comment.commentText}</textarea>
															</td>


															<td width="13%">
																<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

																	<tr>
																		<td width="28%" class="input-title">
																			当前状态 :
																		</td>
																		<td width="80%" class="td-input">
																			<cms:if test="${Comment.censorState==1}">
																				<font color="green">通过审核</font>
																			</cms:if>
																			<cms:elseif test="${Comment.censorState==0}">
																				<font color="orange">等待审核</font>
																			</cms:elseif>
																			<cms:else>
																				<font color="red">审核不通过</font>
																			</cms:else>

																		</td>
																	</tr>

																	<tr>
																		<td width="100%" colspan="2">
																			<a href="javascript:censorComment('1','${Comment.commentId}');" name="btnwithicosysflag" class="btnwithico"> <img src="../../core/style/icons/flag-blue.png" alt="" /><b>通过&nbsp;</b> </a>
																			<a href="javascript:censorComment('5','${Comment.commentId}');" name="btnwithicosysflag" class="btnwithico"> <img src="../../core/style/icons/flag-white.png" alt="" /><b>否决&nbsp;</b> </a>
																			<a href="javascript:deleteComment('${Comment.commentId}');" name="btnwithicosysflag" class="btnwithico"> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
																		</td>

																	</tr>
																</table>

															</td>

														</tr>

													</table>
												</td>
											</tr>
										</table>
										<br />
									</cms:SystemComment>

									<cms:Empty flag="Comment">

										<tr>
											<td>
												<center>
													<table class="listdate" width="98%" cellpadding="0" cellspacing="0">

														<tr>
															<td class="tdbgyew" style="height:40px;">


																当前没有评论数据!


															</td>
														</tr>

													</table>
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
								</div>
							</td>
						</tr>
					</table>
					<div style="height:60px;"></div>
					<div class="breadnavTab" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="btnbg100">
								 <div style="float:right">
									<a href="javascript:change('-1');"  class="btnwithico"><img src="../style/icon/balloon-ellipsis.png" width="16" height="16" /><b>所有评论&nbsp;</b> </a>
								
									<a href="javascript:change('0');"  class="btnwithico"><img src="../style/icons/flag-yellow.png" width="16" height="16" /><b>只显示待审&nbsp;</b> </a>
									
									<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16" /><b>关闭&nbsp;</b> </a>

								 </div>
							</tr>
						</table>
						 
					</div>
			<tr>

			</tr>
			</td>
			</tr>


		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">

function close()
{
	api.close();
	W.window.location.reload();
}


 
function deleteCheckedComment()
{
	var checkedIdArray = new Array();
	var allCommCheckBox = document.getElementsByName("checkCommentBox");
    for(var i = 0; i < allCommCheckBox.length; i++)
	{
		if(allCommCheckBox[i].checked == true)
		{
			checkedIdArray.push(allCommCheckBox[i].value);
		}
	}
	
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选评论吗？',
                    
                    ok: function () { 
                    
       disableAnchorElementByName("btnwithicosysflag",true);
       
    window.location.href = '<cms:BasePath/>comment/deleteComment.do?commentId='+checkedIdArray.join(',')+"&<cms:Token mode='param'/>"; 
    }, 
    cancel: true 
    });
}

function deleteComment(commId)
{
	var dialog = W.$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
                    parent:api,
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除当前评论吗？',
                    
                    ok: function () { 
       
    				window.location.href = '<cms:BasePath/>comment/deleteComment.do?contentId=${param.contentId}&commentId='+commId+'&single=true&pn=${param.pn}'+"&<cms:Token mode='param'/>";
    }, 
    cancel: true 
    });
	
}

function censorComment(flag,commId)
{
	disableAnchorElementByName("btnwithicosysflag",true);
	
	window.location.href = '<cms:BasePath/>comment/censorComment.do?contentId=${param.contentId}&commentId='+commId+'&censorState='+flag+"&nextPage=${page.currentPage}&single=true&pn=${param.pn}"+"&<cms:Token mode='param'/>";
}

function censorCheckedComment(flag)
{
	var checkedIdArray = new Array();
	var allCommCheckBox = document.getElementsByName("checkCommentBox");
    for(var i = 0; i < allCommCheckBox.length; i++)
	{
		if(allCommCheckBox[i].checked == true)
		{
			checkedIdArray.push(allCommCheckBox[i].value);
		}
	}
	
	window.location.href = '<cms:BasePath/>comment/censorComment.do?contentId=${param.contentId}&commentId='+checkedIdArray.join(',')+'&censorState='+flag+"&<cms:Token mode='param'/>";
}


function checkAllComment()
{
	var allCommCheckBox = document.getElementsByName("checkCommentBox");
	var actCheckBox = document.getElementById("checkAllComment");
	
	var id;
	for(var i = 0; i < allCommCheckBox.length; i++)
	{
		if(actCheckBox.checked)
		{
			allCommCheckBox[i].checked = true;
		}
		else
		{
			allCommCheckBox[i].checked = false;
		}	
	}
} 

function change(status)
{
	replaceUrlParam(window.location,'fromFlow=&status='+status);
}
 
</script>


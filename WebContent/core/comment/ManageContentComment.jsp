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
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>



		<script>
	   
         if("true"==="${param.fromFlow}")
         {  
         	    if("true"==="${param.censor}")
         		{
         			$.dialog.tips('改变评论状态成功',1,'32X32/succ.png'); 	
         		}
         		else
         		{
         			$.dialog.tips('删除评论成功',1,'32X32/succ.png'); 	
         		} 	                    
         }

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
						<a href="#"></a> &raquo;
						<a href="#">评论管理</a>
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
									栏目
									<select id="classId" name="classId" class="form-select" onchange="javascript:change();">
										<option value="-1">
											所有栏目
										</option>
										<cms:CurrentSite>
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
												<cms:SysClass>
													<option value="${Class.classId}">
														${Class.layerUIBlankClassName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													</option>
												</cms:SysClass>
											</cms:SystemClassList>
										</cms:CurrentSite>
									</select>
									<%--
									&nbsp; 类别
									<select id="typeBy" name="typeBy" onchange="javascript:;" class="form-select">
										<option value="" selected>
											所有分类
										</option>
										<cms:SystemContentTypeList>
											<option value="${ContentType.typeFlag}">
												${ContentType.typeName}
											</option>
										</cms:SystemContentTypeList>

									</select>
									--%>

									&nbsp; 状态
									<select id="status" name="status" onchange="javascript:change();" class="form-select">
										<option value="-1" selected>
											所有评论
										</option>
										<option value="1">
											通过
										</option>
										<option value="5">
											未通过&nbsp;&nbsp;&nbsp;&nbsp;
										</option>
										<option value="0">
											待审&nbsp;&nbsp;&nbsp;&nbsp;
										</option>
									</select>
									&nbsp;

								</div>
								<div>
									<a href="javascript:javascript:censorCheckedComment('1');" class="btnwithico"> <img src="../../core/style/icons/flag-blue.png" alt="" /><b>通过&nbsp;</b> </a>
									<a href="javascript:censorCheckedComment('5');" class="btnwithico"> <img src="../../core/style/icons/flag-white.png" alt="" /><b>否决&nbsp;</b> </a>
									<a href="javascript:deleteCheckedComment();" class="btnwithico"> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
								</div>
								<div class="fr">
									选中所有:
									<input type="checkbox" name="checkAllComment" id="checkAllComment" onclick="javascript:checkAllComment();" class="form-checkbox"></input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 评论人名称: &nbsp;
									<input type="text" class="form-input" id="searchKey" name="searchKey" size="25" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
									<input type="button" value="查询" onclick="javascript:search();" class="btn-1" />
								</div>
							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div>
									<cms:SystemComment classId="${param.classId}" size="20" userName="${param.key}" pn="${param.pn}" censor="${param.status}">
										<table width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="100%" height="60">
													<input type="checkbox" name="checkCommentBox" value="${Comment.commentId}" class="form-checkbox" />
													<strong>评论ID:</strong>${Comment.commentId}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<cms:Content id="${Comment.contentId}">
													[<a href="javascript:openManageCommentDialog('${Info.contentId}','<cms:JsEncode str='${Info.title}'/>');">&nbsp;管理内容评论</a>&nbsp;]&nbsp;
														<a target="_blank" href="${Info.contentUrl}">${Info.title}</a>
														
													 &nbsp;&nbsp; 
																	
													</cms:Content>
												</td>

											</tr>

											<tr>
												<td>
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td width="33%" height="90" valign="bottom">
																<textarea style="width:510px;height:90px" class="form-textarea" readonly>${Comment.commentText}</textarea>
															</td>

															<td width="24%">
																<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
																	<tr>
																		<td width="30%" class="input-title">
																			评论时间:
																		</td>
																		<td class="td-input">
																			<cms:FormatDate date="${Comment.commDT}" />
																		</td>
																	</tr>
																	<tr>
																		<td class="input-title">
																			IP地址:
																		</td>
																		<td class="td-input">
																			${Comment.ip}
																		</td>
																	</tr>

																	<tr>
																		<td class="input-title">
																			地区:
																		</td>
																		<td class="td-input">
																			<cms:IPArea ip="${Comment.ip}" />
																		</td>
																	</tr>
																</table>

															</td>


															<td width="17%">
																<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
																	<tr>
																		<td width="30%" class="input-title">
																			评论人:
																		</td>
																		<td class="td-input">
																			${Comment.userName}
																		</td>
																	</tr>
																	<tr>
																		<td class="input-title">
																			当前状态 :
																		</td>
																		<td class="td-input">
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
																			<a href="javascript:censorComment('1','${Comment.commentId}');" class="btnwithico"> <img src="../../core/style/icons/flag-blue.png" alt="" /><b>通过&nbsp;</b> </a>
																			<a href="javascript:censorComment('5','${Comment.commentId}');" class="btnwithico"> <img src="../../core/style/icons/flag-white.png" alt="" /><b>否决&nbsp;</b> </a>
																			<a href="javascript:deleteComment('${Comment.commentId}');" class="btnwithico"> <img src="../../core/style/default/images/del.gif" alt="" /><b>删除&nbsp;</b> </a>
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
								<div class="mainbody-right"></div>
							</td>
						</tr>
					</table>

				</td>
			</tr>

			<tr>
				<td height="10">
				<br/>
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

initSelect('status','${param.status}');
 



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
	
	if(checkedIdArray.length == 0)
	{
		 $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您没有选择任何评论!',
                    
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
    				
                    content: '您确认删除所选评论吗？',
                    
                    ok: function () { 
       
    window.location.href = '<cms:BasePath/>comment/deleteComment.do?commentId='+checkedIdArray.join(',')+"&<cms:Token mode='param'/>"; 
    }, 
    cancel: true 
    });
}

function deleteComment(commId)
{
	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选评论吗？',
                    
                    ok: function () { 
       
   	window.location.href = '<cms:BasePath/>comment/deleteComment.do?commentId='+commId+"&<cms:Token mode='param'/>"; 
    }, 
    cancel: true 
    });
	
}

function censorComment(flag,commId)
{
	window.location.href = '<cms:BasePath/>comment/censorComment.do?commentId='+commId+'&censorState='+flag+"&nextPage=${page.currentPage}"+"&<cms:Token mode='param'/>";
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
	
	if(checkedIdArray.length == 0)
	{
		 $.dialog({ 
   					title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您没有选择任何评论!',
                    
                    cancel: true
                   
	      });
	      
	      return;
	}
	
	window.location.href = '<cms:BasePath/>comment/censorComment.do?commentId='+checkedIdArray.join(',')+'&censorState='+flag+"&<cms:Token mode='param'/>";
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


function change()
{
	replaceUrlParam(window.location,'fromFlow=&key=&classId='+$('#classId').val()+'&status='+$('#status').val());
}

function openManageCommentDialog(contentId, title)
{
	var ti = '';
	
	ti = title.replaceAll('<','&lt;');
	ti = ti.replaceAll('>','&gt;');

	$.dialog({ 
		    id : 'omcd',
	    	title : '评论管理 - '+ti,
	    	width: '1180px', 
	    	height: '760px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:<cms:BasePath/>core/comment/ManageSingleContentComment.jsp?uid='+Math.random()+'&contentId='+contentId
	
	});

}

function search()
{
	var key = encodeURI(encodeURI(document.getElementById('searchKey').value));
	
	window.location='ManageContentComment.jsp?&key='+encodeData(key);
}
 
</script>


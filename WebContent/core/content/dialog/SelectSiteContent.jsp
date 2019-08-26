<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>

		<script>  
		
		var hasError = false;
		//验证
		
	
	     var api = frameElement.api, W = api.opener; 
		
		 function showErrorMsg(msg)
		 {
		
		    W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: msg,

		    				cancel: true
			});
			
		}
      
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	             api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             W.location.reload();
         	}
       		       
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

		<form id="advertPosForm" name="advertPosForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="" align="left" valign="top">
									<!--main start-->
									<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">

										<tr>
											<td style="padding: 10px 12px;" >
												<div class="fl">
													请输入关键词搜索: &nbsp;
													<input id="searchKey" name="searchKey" class="form-input" size="40" maxlength="60" value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
													<input onclick="javascript:search();" value="搜索" class="btn-1" type="button" />

												</div>
												<div>
													<%--<a href="javascript:gotoAddUserDefineContentPage(${param.modelId});" class="btnwithico"> <img src="../../core/style/default/images/doc_add.png" alt="" /><b>发布推荐位&nbsp;</b> </a>
																	<a href="javascript:;" class="btnwithico" onmousedown='javascript:sortContent();'> <img src="../../core/style/default/images/sort-number.png" alt="" /><b>排序&nbsp;</b> </a>
																	<a href="javascript:;" class="btnwithico" onclick=""> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>


																--%>
												</div>
												<div class="fr">
													
												</div>
											</td>
										</tr>

										<tr>
											<td id="uid_td25" style="padding: 2px 6px;">
												<div class="DataGrid">
													<cms:SystemManageContentList pageSize="13" key="${param.key}" relateSearch="true">
														<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

															<tr class="datahead">
																<td width="3%" height="30">
																	<strong>ID</strong>
																</td>
																<td width="2%" height="30">
																	
																		<input type="checkbox" name="checkbox" value="checkbox" onclick="javascript:selectAll('checkContent',this);"/>
																
																</td>
																<td width="28%">
																	<strong>标题</strong>
																</td>
																
																<td width="8%">
																	<strong>内容模型</strong>
																</td>

															</tr>

															<cms:SystemContent>

																<tr height="20">
																	<td>
																		${Info.contentId}
																	</td>
																	<td>
																		
																			<input type="checkbox" name="checkContent" value="${Info.contentId}" />
																
																	</td>
																	<td>
																		<div align="left">

																			<span class="STYLE1">&nbsp;${Info.title }</span></a>

																		</div>
																	</td>
																	
																	<td>
																		<cms:SystemDataModel id="${Info.modelId}">																					
																								${DataModel.modelName}	
																						</cms:SystemDataModel>
																	</td>
																</tr>
															</cms:SystemContent>

															<cms:Empty flag="Info">
																<tr>
																	<td class="tdbgyew" colspan="9">
																		<center>
																			当前搜索没有数据!
																		</center>
																	</td>
																</tr>
															</cms:Empty>

															<cms:if test="${empty param.key}">
																<tr id="pageBarTr">
																	<td colspan="8" class="PageBar" align="left">
																		<div class="fr">
																			<span class="text_m"> 共 ${page.totalCount} 条记录 第${page.currentPage}页 / ${page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos">
																				<input type="button" name="goto" value="GOTO" onclick="javascript:jump()">
																			</span>
																			<span class="page">[<a href="${page.headQuery}">首页</a>]</span>
																			<span class="page">[<a href="${page.prevQuery}">上一页</a>]</span>
																			<span class="page">[<a href="${page.nextQuery}">下一页</a>]</span>
																			<span class="page">[<a href="${page.endQuery}">末页</a>]</span>&nbsp;
																		</div>
																		<script>
																							function jump()
																							{
																								window.location="${page.jumpQuery}&currentPage="+document.getElementById('pageJumpPos').value;
																							}
																						</script>
																		<div class="fl"></div>
																	</td>
																</tr>
															</cms:if>
															<cms:else>
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
																								
																									
																									replaceUrlParam(window.location,'currentPage='+cp);		
																								}
																					
																					
																								function jump()
																								{
																								    var cp = parseInt(document.getElementById('pageJumpPos').value);
																								    
																								    if(cp > parseInt('${Page.pageCount}'))
																									{
											                                                           cp=parseInt('${Page.pageCount}');
																									}
																								
																									replaceUrlParam(window.location,'currentPage='+cp);
																								}
																							</script>
																			<div class="fl"></div>
																		</td>
																	</tr>
																</cms:PageInfo>
															</cms:else>

														</table>
													</cms:SystemManageContentList>
												</div>

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


						<div style="height:5px;"></div>
						<div class="breadnavTab"  >
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="btnbg100">
									<div style="float:right">
										<a id="buttonHref" href="javascript:submitSelectInfo();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
										<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
									</div>
								</tr>
							</table>
						</div>


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




function filterAction(classId,orderBy)
{
    var orderByVar = document.getElementById('orderBy').value+'-down';
    
	window.location.href = 'SelectSiteContent.jsp?classId='+classId+"&typeId=${param.typeId}"+'&commFlag=${param.commFlag}&orderBy='+orderByVar+'&single=${param.single}&dialogId=${param.dialogId}';
}

function submitSelectInfo()
{
	 
  	    var ids = '';
		var checks = document.getElementsByName('checkContent');
	
		for(var i = 0; i < checks.length; i++)
		{
			if(checks[i].checked == true)
			{
				ids += checks[i].value+'_';
			}
		}
		
		if(ids == '')
		{
			W.$.dialog({ 
			   	title :'提示',
			    width: '120px', 
			    height: '60px', 
			    parent: api,
			    lock: true, 
			    icon: '32X32/i.png', 
			    				
			    content: '没有选择内容！', 
			    cancel: true 
			});
			return;
		}
		
		
		
		//alert(api.get('osrcgd').window.location);
		//api.reload( api.get('osrcgd') );
       //api.get('osrcgd').window.location = 'ShowRelatedContent.jsp?rids='+ids+api.get('osrcgd').currentRids;
       
       
       var oldRids = api.get('osrcgd').currentRids;
       
       var rs = oldRids.split('-');
       
       for(var i=0; i<rs.length; i++)
       {
       		if(rs[i].startWith('${param.clId}'))
       		{
       		
       			var or = rs[i];
       		    // alert(oldRids+'     '+or+'   '+rs[i]+ids);
       		    oldRids = oldRids.replace(or, rs[i]+ids); 
       		    //alert(oldRids+'     '+or+'   '+rs[i]+ids);
       		     
       		}
       }
       
      
      
        replaceUrlParam(api.get('osrcgd').window.location, 'rids='+oldRids );   
  	 
	close();
	 
	
}

function search()
{
	var key = encodeData(encodeURI(encodeURI(document.getElementById('searchKey').value)));
	
	
	window.location='SelectSiteContent.jsp?closeCId=${param.closeCId}&single=false&key='+key;
	
	
	//replaceUrlParam(window.location, 'key='+key);
}  
  

function close()
{
	api.close();
}



 


</script>


<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script>
		 var selectedTargetClassId = '';
		 
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );        
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
				
				$("#showlist2 tr[id!='pageBarTr']").hover(function() 
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

					<div class="auntion_tagRoom" style="margin-top: 5px;">
						<ul>
							<li id="two1" onclick="setTab2('two',1,2)" class="selectTag">
								<a href="javascript:;"><img src="../../style/icons/folder-open-document-text.png" width="16" height="16" />站点推荐位&nbsp;</a>
							</li>
							<li id="two2" onclick="setTab2('two',2,2)">
								<a href="javascript:;"><img src="../../style/icons/script-text.png" width="16" height="16" />专题分类&nbsp;</a>
							</li>

						</ul>
					</div>
					<div class="auntion_tagRoom_Content">
						<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
							<ul>
								<li>

									<iframe src="CommendTypeInnerTable.jsp?classId=${param.classId}&contentId=${param.contentId}&modelId=${param.modelId}" height="445" width="100%" id="commendTypeFrame" scrolling="auto" frameborder="0"></iframe>

									<div class="breadnavTab"  >
										<table width="100%" border="0" cellspacing="0" cellpadding="0" >
											<tr class="btnbg100">
												<div style="float:right">
													<a name="btnwithicosysflag" href="javascript:submitCommendContent();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
													<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
												</div>

											</tr>
										</table>
									</div>

								</li>
							</ul>
						</div>

						<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
							<ul>
								<li>
									<div style="margin-top: 10px;" class="fl">
										专题分类:
										<select id="specId" name="specId" onchange="javascript:changeFilter();">
											<option value="">
												-------- 全部专题 --------
											</option>
											<cms:SystemClassList type="all" isSpec="true" classType="4">
												<cms:SysClass>
													<option value="${Class.classId}">
														${Class.layerUIBlankClassName}
													</option>
												</cms:SysClass>
											</cms:SystemClassList>
										</select>
										&nbsp;(注意：只有专题拥有子信息分类才可成功推荐)
									</div>
									<%--<div class="fr" style="margin-top: 10px;">
										<input id="searchKey" name="searchKey" size="30" maxlength="60" class="form-input" style="vertical-align:top;" />
										<input onclick="javascript:search();" value="查询" class="btn-1" type="button" style="vertical-align:top;" />
									</div>

									--%><table id="showlist2" style="margin-top: 10px;" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">

											<td width="1%" height="30">
												<input type="checkbox" class="classCheck" id="selectAll" name="selectAll" onclick="javascript:selectAll('checkSpec',this);" />
											</td>

											<td width="35%">
												<strong>专题名称</strong>
											</td>
											
												<td width="15%">
												<strong>信息子分类</strong>
											</td>

										</tr>
										<cms:CurrentSite>
											<cms:SystemClassList parentId="${param.specId}" type="all" isSpec="true" classType="5" size="8">

												<cms:SysClass>
													<tr id="${Class.linearOrderFlag}" >
														<td>
															<input type="checkbox"  name="checkSpec" value="${Class.classId}" />
														</td>

														<td>					
															&nbsp;${Class.className}
														</td>
														
														<td>					
															<select name="specCommTypeId">
																<option value="-1">
																----- 请选择信息分类 -----																
																</option>
																<cms:SystemCommendType classId="${Class.classId}" isSpec="true">
																<option value="${CommendType.commendTypeId}">
																${CommendType.commendName}															
																</option>
																</cms:SystemCommendType>	
																														
															</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</td>
														
														
													</tr>
												</cms:SysClass>

												<cms:Empty flag="Class">
													<tr>
														<td class="tdbgyew" colspan="6">
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
																
																	
																	replaceUrlParam(window.location,'pn='+cp+'&tab=2');		
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

											</cms:SystemClassList>
										</cms:CurrentSite>
									</table>


									<div class="breadnavTab" >
										<table width="100%" border="0" cellspacing="0" cellpadding="0" >
											<tr class="btnbg100">
												<div style="float:right">
													<a name="btnwithicosysflag" href="javascript:submitCommendContent('spec');"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
													<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
												</div>

											</tr>
										</table>
									</div>

								</li>
							</ul>
						</div>
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

if('${param.tab}' != '')
{
	setTab2('two','${param.tab}',2);
}

initSelect('specId','${param.specId}');
 
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}

function submitCommendContent(flag)
{
	var isSpec = false;
	
	if('spec' == flag)
	{
		//专题推荐
		var cidCheck = document.getElementsByName('specCommTypeId');
		
		var ids='';
		for(var i=0; i<cidCheck.length;i++)
		{
			if(cidCheck[i].value > 0)
			{
				ids += cidCheck[i].value+',';
			}
		}
	
		if('' == ids)
		{
		   $.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '请选择对应专题！', 
	       cancel: true 
	                    
		
		  });
		  return;
		}
		
		isSpec = true;		
	}
	else
	{
    	var cidCheck = document.getElementById('commendTypeFrame').contentWindow.document.getElementsByName('commendCheck');
	
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
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/i.png',    				
	                    content: '没有选择任何推荐位！', 
	       				cancel: true 
		  });
		  window.location.reload();
		  return;
		}

	}
	
		disableAnchorElementByName("btnwithicosysflag",true);
		
		var tip = W.$.dialog.tips('正在执行...',3600000000,'loading.gif');
		
		var url = "<cms:BasePath/>content/commendContent.do?ids="+ids+"&contentIds=${param.contentId}&isSpec="+isSpec+"&<cms:Token mode='param'/>";
	 		
	 	$.ajax({
	      	type: "POST",
	       	url: url,
	       	data:'',
	   
	       	success: function(mg)
	        {        
	        
	        	var msg = eval("("+mg+")");
           		
	        	if('success' == msg)
	        	{
	        		W.$.dialog({ 
	   					title :'提示',
	    				width: '150px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '推荐内容成功！', 
	                    ok: function()
	                    { 
	                    	
      						W.window.location.reload();
    					} 
		  			});
	        	}
	        	else
	        	{
	        		W.$.dialog({ 
		   					title :'提示',
		    				width: '200px', 
		    				height: '60px', 
		    				parent:api,
		                    lock: true, 
		    				icon: '32X32/fail.png', 
		    				
		                    content: "执行失败，无权限请联系管理员！", 
		       				cancel: true 
			  			});
	        	}
	        	
	        	tip.close();
	        }
	     });	
	     
	     return;    	
    
	
    api.close();
    
    
}
   
function changeFilter()
{ 
	replaceUrlParam(window.location,'specId='+$('#specId').val()+'&tab=2');
	
}
   

  
</script>

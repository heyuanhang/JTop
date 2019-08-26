<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
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
						<a href="#">站点相关词</a> &raquo; 敏感词管理
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
										<a href="javascript:openAddSensitiveWordDialog();" class="btnwithico" onclick=""><img src="../style/icons/traffic-cone--plus.png" width="16" height="16" /><b>添加敏感词&nbsp;</b> </a>
										<a href="javascript:openAddManySWDialog();" class="btnwithico" onclick=""><img src="../style/icons/quill--arrow.png" width="16" height="16" /><b>批量导入敏感词&nbsp;</b> </a>
								
										<a href="javascript:changeStatus('off', '');" class="btnwithico" onclick=""><img src="../style/icons/light-bulb-off.png" width="16" height="16" /><b>停用&nbsp;</b> </a>

										<a href="javascript:changeStatus('on', '');" class="btnwithico" onclick=""><img src="../style/icons/light-bulb.png" width="16" height="16" /><b>启用&nbsp;</b> </a>

										<a href="javascript:deleteSw('');" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>

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
													<input class="form-checkbox" onclick="javascript:selectAll('checkedId',this);" type="checkbox" />
												</td>
												<td width="14%">
													<strong>敏感词</strong>
												</td>

											 
											 	<td width="14%">
													<strong>替换词</strong>
												</td>

												<td width="6%">
													<strong>可用状态</strong>
												</td>

												<td width="6%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											<cms:QueryData objName="Sw" service="cn.com.mjsoft.cms.content.service.ContentService" method="getSensitiveWordTag" var=",${param.pn},10"> 
											
												<tr>
													<td>
														${Sw.swId}
													</td>
													<td>
														<input class="form-checkbox" name="checkedId" value="${Sw.swId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														${Sw.sensitiveStr}
													</td>
													
													<td>
														<cms:if test="${empty Sw.replaceStr}"><<i>空格</i>></cms:if>
														<cms:else>${Sw.replaceStr}</cms:else>
														
													</td>
													 

													<td>
														<cms:if test="${Sw.useStatus == 1}">
															<img src="../style/icon/tick.png" />
														</cms:if>
													
														<cms:else>
															<img src="../style/icon/del.gif" />
														</cms:else>
														
													</td>

													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditSensitiveWordDialog('${Sw.swId}');">&nbsp;编辑</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteSw('${Sw.swId}');">删除</a></span>
															</center>
														</div>
													</td>
												</tr>

											</cms:QueryData>
											
											<cms:Empty flag="Sw">
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

			<form id="deleteSystemForm" name="deleteSystemForm" method="post">

				<input type="hidden" id="modelId" name="modelId" value="-1" />

			</form>

			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

function openAddSensitiveWordDialog()
{
	$.dialog({ 
    	title :'新增敏感词',
    	width: '645px', 
    	height: '220px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/words/CreateSensitiveWord.jsp'
	});
}

function openEditSensitiveWordDialog(id)
{
	$.dialog({ 
    	title :'编辑敏感词',
    	width: '645px', 
    	height: '220px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/words/EditSensitiveWord.jsp?id='+id
	});
}

function deleteSw(id)
{
	 var ids='';
	 
	 if('' == id)
	 {
	 	var cidCheck = document.getElementsByName('checkedId');
	
		
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
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png',    				
	                    content: '没有选择任何敏感词！', 
	       				cancel: true 
		  });
		  
		  return;
		}
	 }
	 else
	 {
	 	ids = id;
	 }


	 $.dialog({ 
   					title :'提示',
    				width: '200px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除敏感词吗？',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>content/deleteSw.do?ids="+ids+"&<cms:Token mode='param'/>";
 					
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
    }, 
    cancel: true 
                    
    });


}

function changeStatus(status, id)
{
	var ids='';
	 
	 if('' == id)
	 {
	 	var cidCheck = document.getElementsByName('checkedId');
	
		
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
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png',    				
	                    content: '没有选择任何敏感词！', 
	       				cancel: true 
		  });
		  
		  return;
		}
	 }
	 else
	 {
	 	ids = id;
	 }


	 var url = "<cms:BasePath/>content/changeUs.do?ids="+ids+"&status="+status+"&<cms:Token mode='param'/>";
 					
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

function openAddManySWDialog()
{
	$.dialog({ 
	    id : 'oeaccd',
    	title : '',
    	width: '780px', 
    	height: '840px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/words/AddManySensitiveWord.jsp'
	});
}

</script>

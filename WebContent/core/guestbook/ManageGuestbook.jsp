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
						<a href="#">交互信息</a> &raquo;
						<a href="#">留言回复</a>
					</td>
					<td align="right"></td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
		<cms:LoginUser>
		<cms:CurrentSite>
		
		
		
		
		
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
			<tr>
				<td class="mainbody" align="left" valign="top">
					<!--main start-->
					<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

						<tr>
							<td style="padding: 7px 10px;" class="">
								<div class="fl">
									留言板 
									<select id="configFlag" onchange="javascript:changeFilter(this.value)" class="form-select">
										 
										<cms:AccInfoList siteId="-99999" roleId="-99999" secType="5" classMode="guestbook">
										<cms:AccInfo>
										<cms:SystemGbConfig configId="${Acc.accId}">
										${Acc.accId}
										<cms:if test="${!empty GbCfg.cfgFlag}">
											<option value="${GbCfg.cfgFlag}">
												${GbCfg.cfgName}&nbsp;&nbsp;&nbsp;
											</option>
										</cms:if>
										</cms:SystemGbConfig>
										</cms:AccInfo>
										</cms:AccInfoList>
										
										<cms:Empty flag="Acc">
											<option value="-1">
												无留言板&nbsp;&nbsp;&nbsp;
											</option>
										</cms:Empty>
									</select>
									&nbsp;
									<%--<select id="isReply" onchange="javascript:changeFilter(this.value)" class="form-select">
										<option value="">
											回复状态 &nbsp;&nbsp;&nbsp;
										</option>
										<option value="1">
											已经回复
										</option>
										<option value="0">
											未回复
										</option>

									</select>
									--%><select id="isCensor" onchange="javascript:changeFilter(this.value)" class="form-select">
										<option value="">
											审核状态 &nbsp;&nbsp;&nbsp;
										</option>

										<option value="1">
											通过审核
										</option>
										<option value="0">
											未通过
										</option>
									</select>


									 <select id="isOpen" onchange="javascript:changeFilter(this.value)" class="form-select">
										<option value="">
											公开状态 &nbsp;&nbsp;&nbsp;
										</option>

										<option value="1">
											公开的
										</option>
										<option value="0">
											不公开
										</option>
									</select>
								 </div>

								<div>
								</div>
								<div class="fr">
									<%--<a href="javascript:changeGbInfoStatus('censor','1');" class="btnwithico" > <img src="../style/icons/flag-blue.png" alt="" /><b>通过&nbsp;</b> </a>
									<a href="javascript:changeGbInfoStatus('censor','0');" class="btnwithico"> <img src="../style/icons/flag-white.png" alt="" /><b>不通过&nbsp;</b> </a>

									<a href="javascript:changeGbInfoStatus('open','1');" class="btnwithico" > <img src="../../core/style/icons/light-bulb.png" alt="" /><b>公开&nbsp;</b> </a>
									<a href="javascript:changeGbInfoStatus('open','0');" class="btnwithico" > <img src="../../core/style/icons/light-bulb-off.png" alt="" /><b>不公开&nbsp;</b> </a>
									--%><a href="javascript:deleteGuestbook(null);" class="btnwithico" > <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
								</div>
							</td>
						<tr>
							<td id="uid_td25" style="padding: 2px 6px;">
								<div class="DataGrid">

									<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

										<tr class="datahead">
											<td width="3%" height="30">
												<strong>ID</strong>
											</td>
											<td width="2%" height="30">
												<input type="checkbox"  onclick="javascript:selectAll('checkIds',this);"/>
											</td>
											<td width="19%">
												<strong>留言标题</strong>
											</td>

											<td width="10%">
												<strong>留言人</strong>
											</td>

											<%--<td width="3%">
												<strong>回复</strong>
											</td>
											
											--%><td width="3%">
												<strong>通过</strong>
											</td>
											
											<td width="3%">
												<strong>公开</strong>
											</td>

											 <td width="7%">
												<center><strong>维护</strong></center>
											</td>
										</tr>

			
										<cms:SystemGbInfo configFlag="${param.configFlag}" isOpen="${param.isOpen}" isReply="${param.isReply}" isCensor="${param.isCensor}" pageSize="10">
										<cms:if test="${status.first}">
										<script>
										 
										var cfgId = '${GbInfo.configId}';
										</script>
										
										
										</cms:if>
											
											<tr>
												<td>
													${GbInfo.gbId}
													<cms:if test="${status.first}">
													<cms:SystemGbConfig configId="${GbInfo.configId}">
														
														<script>
														
															var wkId = ${GbCfg.workflowId};
														</script>
													</cms:SystemGbConfig>
													</cms:if>
												</td>
												<td>
													<input type="checkbox" name="checkIds" value="${GbInfo.gbId}"  />
												</td>

												<td>
													<cms:if test="${GbInfo.gbTitle!=null && GbInfo.gbTitle!=''}">
														<a href="javascript:openEditGuestbookDialog('${GbInfo.configFlag}','${GbInfo.configId}','${GbInfo.gbId}')"> <font style="color:#454545">${GbInfo.gbTitle}</font> </a>
													</cms:if>
													<cms:else>
														<a href="javascript:openEditGuestbookDialog('${GbInfo.configFlag}','${GbInfo.configId}','${GbInfo.gbId}')"> <font style="color:#454545">${GbInfo.gbText}
														</font>
														</a>
													</cms:else>
												</td>

												<td>
													${GbInfo.gbMan}
												</td><%--

												<td>
													
													<cms:if test="${GbInfo.isReply==1}">
																<img src="../style/icon/tick.png" />
													</cms:if>
													<cms:else>
																<img src="../style/icon/del.gif" />
													</cms:else>
												</td>
												
												--%><td>
													
													<cms:if test="${GbInfo.isCensor==1}">
																<img src="../style/icon/tick.png" />
													</cms:if>
													<cms:else>
																<img src="../style/icon/del.gif" />
													</cms:else>
												</td>
												
												 <td>
													
													<cms:if test="${GbInfo.isOpen==1}">
																<img src="../style/icon/tick.png" />
													</cms:if>
													<cms:else>
																<img src="../style/icon/del.gif" />
													</cms:else>
												</td>

												<td>
													
														<center>
															<span ><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditGuestbookDialog('${GbInfo.configFlag}','${GbInfo.configId}','${GbInfo.gbId}')">&nbsp;回复</a>&nbsp;&nbsp;&nbsp;<img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteGuestbook('${GbInfo.gbId}');">删除 </a>&nbsp;</span>
														
														<a href="javascript:openContentOperLogDialog('${GbInfo.gbId}','<cms:JsEncode str='${GbInfo.gbTitle}'/>');"><img src="../../core/style/icons/document-task.png" width="16" height="16" />&nbsp;日志</a>&nbsp;
																		
														</center>
													
												</td>

											</tr>
										</cms:SystemGbInfo>

											<cms:Empty flag="GbInfo">
												<tr>
													<td class="tdbgyew" colspan="9">
														<center>
															当前没有数据!
														</center>
													</td>
											</cms:Empty>
										<tr id="pageBarTr">
										<cms:PageInfo>
											<td colspan="8" class="PageBar" align="left">
												<div class="fr">
													<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPos" name="pageJumpPos">
														<input type="button" name="goto" value="GOTO" onclick="javascript:jump()"/>
													</span>
													<span class="page">[<a href="javascript:page('f')">首页</a>]</span>
													<span class="page">[<a href="javascript:page('p')">上一页</a>]</span>
													<span class="page">[<a href="javascript:page('n')">下一页</a>]</span>
													<span class="page">[<a href="javascript:page('l')">末页</a>]</span> &nbsp;
												</div>

												<script>
														
														function page(flag)
														{
															if('n'==flag)
															{
																window.location='ManageGuestbook.jsp?configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&pn=${Page.currentPage+1}';
															}
															else if('p'==flag)
															{
																window.location='ManageGuestbook.jsp?configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&pn=${Page.currentPage-1}';
															}
															else if('f'==flag)
															{
																window.location='ManageGuestbook.jsp?configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&pn=1';
															}
															else if('l'==flag)
															{
																window.location='ManageGuestbook.jsp?configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&pn=${Page.pageCount}';
															}
														}
													</script>
												<div class="fl"></div>
												</cms:PageInfo>
											</td>
										</tr>

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

initSelect('configFlag','${param.configFlag}');
initSelect('isOpen','${param.isOpen}');
initSelect('isReply','${param.isReply}');
initSelect('isCensor','${param.isCensor}');

function openEditGuestbookDialog(configFlag,cfgId,gbId)
{
	$.dialog({ 
	    id : 'oegbd',
    	title : '留言回复',
    	width: '1000px', 
    	height: '700px',
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/guestbook/ReplyAndEditGuestbook.jsp?configFlag='+document.getElementById('configFlag').value+"&gbId="+gbId+"&configId="+cfgId
	});
}


function changeGbInfoStatus(action,flag)
{
	var ids = document.getElementsByName('checkIds');
		
	var idArray = new Array();
	for(var i = 0; i < ids.length; i++)
	{
		if(ids[i].checked == true)
		{
			idArray.push(ids[i].value);
		}
	}
	
	var id = idArray.join(',');

	if(id == null || id == '')
	{
	
		$.dialog
		(
	  		{ 
				title :'提示',
			    width: '130px', 
				height: '60px', 
				lock: true, 
				icon: '32X32/i.png', 
									    				
				content: "没有选择留言",
							
				cancel: true
			}
		);	
		return;							  
	}
	
	
	$.dialog({ 
   					title :'提示',
    				width: '165px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认改变留言状态吗？',
                    
                    ok: function () {
           				var url = "../../guestbook/changeStatus.do?action="+action+"&flag="+flag+'&id='+id+"&configId="+cfgId+"&<cms:Token mode='param'/>";
 		
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


function deleteGuestbook(id)
{
	if(wkId > 0)
 	{
 		var dialog = $.dialog({ 
   					title :'提示',
    				width: '380px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/fail.png', 
    				
                    content: '启用工作流的留言版删除留言请在 [回复] 中使用删除工作流！',
                                      
       cancel: true 
                    
	   });
       return;
 	
 	}

	if(id == null)
	{
		var ids = document.getElementsByName('checkIds');
		
		var idArray = new Array();
		for(var i = 0; i < ids.length; i++)
		{
			if(ids[i].checked == true)
			{
				idArray.push(ids[i].value);
			}
		}
		id = idArray.join(',');
	}
	
	
	if(id == null || id == '')
	{
	
		$.dialog
		(
	  		{ 
				title :'提示',
			    width: '130px', 
				height: '60px', 
				lock: true, 
				icon: '32X32/i.png', 
									    				
				content: "没有选择留言",
							
				cancel: true
			}
		);	
		return;							  
	}
	
	
	$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选留言吗？',
                    
                    ok: function () {
           				var url = "<cms:BasePath/>guestbook/deleteGb.do?id="+id+"&cfgFlag="+$('#configFlag').val()+"&configId="+cfgId+"&<cms:Token mode='param'/>";
 		
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



function changeFilter()
{
	var isOpen=''; var isReply='';
	var configFlag = document.getElementById('configFlag').value;
	var isOpen = document.getElementById('isOpen').value;
	//var isReply = document.getElementById('isReply').value;
	var isCensor= document.getElementById('isCensor').value;
	
	window.location.href = 'ManageGuestbook.jsp?configFlag='+configFlag+'&isOpen='+isOpen+'&isReply='+isReply+'&isCensor='+isCensor;	
}

function openContentOperLogDialog(contentId, title)
{
	var ti = '';
	
	ti = title.replaceAll('<','&lt;');
	ti = ti.replaceAll('>','&gt;');

	$.dialog({ 
		    id : 'ocold',
	    	title : '操作记录 - '+ti,
	    	width: '1400px', 
	    	height: '740px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:<cms:BasePath/>core/guestbook/ViewContentOperInfo.jsp?uid='+Math.random()+'&contentId='+contentId
	
	});

}


</script>

</cms:CurrentSite>
</cms:LoginUser>

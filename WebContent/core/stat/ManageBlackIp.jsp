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

		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

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
						<a href="#">安全与防护</a> &raquo;
						<a href="#">被限制黑名单</a>
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
											<a href="javascript:addToBlackIP();" class="btnwithico" onclick=""><img src="../style/icons/xfn-colleague.png" width="16" height="16" /><b>新加IP黑名单&nbsp;</b> </a>
										
									
										<a href="javascript:deleteBlackIp('');" class="btnwithico" onclick=""><img src="../style/icons/xfn-friend-met.png" width="16" height="16" /><b>解除屏蔽&nbsp;</b> </a>


									
										&nbsp;
									</div>
									<div>
										
									</div>
									<div class="fr">
										
										搜索IP:
										<input class="form-input" type="text" size="30" id="targetIp" name="targetIp" value="${param.targetIp}"/>
										<input type="button" value="查询" onclick="javascript:search();" class="btn-1" />
								
									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">


												<td width="1%">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedIp',this);" type="checkbox" />
												</td>
												<td width="8%">
													<strong>被屏蔽IP</strong>
												</td>

												<td width="10%">
													<strong>地域信息</strong>
												</td>
												
												<td width="6%">
													<strong>限制时间</strong>
												</td>

												<td width="9%">
													<strong>屏蔽生效时间</strong>
												</td>

												<td width="8%">
													<center>
														<strong>操作</strong>
													</center>
												</td>
											</tr>

											<cms:QueryData objName="IPI" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteClientBlackIpInfoTag" var="${param.targetIp},${param.pn},12">
												<tr>
													<td>
														<input class="form-checkbox" name="checkedIp" value="${IPI.blackIp}" type="checkbox" onclick="javascript:" />
													</td>

													<td>
														&nbsp;${IPI.blackIp}
													</td>
													<td>
														<cms:IPArea ip="${IPI.blackIp}" />
													</td>
													
													<td>
														<cms:if test="${IPI.isDeForever == 1}">
															 永久屏蔽
														</cms:if>
														<cms:else>
															 屏蔽 ${IPI.effectHour} 小时
														</cms:else>
													</td>
													
													<td>
														<cms:FormatDate date="${IPI.startDT}" />
													</td>

													<td>
														<center>
															<a href="javascript:deleteBlackIp('${IPI.blackIp}');"><img src="../../core/style/icons/xfn-friend-met.png" width="16" height="16" />&nbsp;解除屏蔽</a>&nbsp;&nbsp;
														</center>
													</td>
												</tr>
											</cms:QueryData>
											
											<cms:Empty flag="IPI">
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
<script type="text/javascript">

//initSelect('tagTypeId','${param.tagTypeId}');

function openAddTagDialog()
{
	$.dialog({ 
    	title :'新增Tag词',
    	width: '400px', 
    	height: '220px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/words/CreateSiteTag.jsp'
	});
}


function openAddBlackIpDialog()
{
	
}


function deleteBlackIp(id)
{
	 var ids='';
	 
	 if('' == id)
	 {
	 	var cidCheck = document.getElementsByName('checkedIp');
	
		
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
	                    content: '没有选择任何IP记录！', 
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
    				
                    content: '您确认解除屏蔽所选IP吗？',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>stat/clearBlackIp.do?ips="+ids+"&<cms:Token mode='param'/>";
 					
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


function search()
{
	replaceUrlParam(window.location,'targetIp='+$('#targetIp').val());
}

function addToBlackIP()
{
	
	
	$.dialog({ 
    	title :'加入屏蔽IP',
    	width: '450px', 
    	height: '160px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/stat/AddNewBlackIp.jsp'
	});

}


</script>

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
						<a href="#">站点相关词</a> &raquo; <a href="#">Tag词管理</a>
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
										<select id="tagTypeId" name="tagTypeId" onchange="javascript:change();">
											<option>
											----- 所有类型Tag -----
											</option>							
											<cms:QueryData objName="TagType" service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getTagTypeListQueryTag" >
											<option value="${TagType.tagTypeId}">
											${TagType.tagTypeName}
											</option>										
											</cms:QueryData>				
										</select>
										&nbsp;
									</div>
									<div>
										<a href="javascript:openAddTagDialog();" class="btnwithico" onclick=""><img src="../style/icons/price-tag--plus.png" width="16" height="16" /><b>添加Tag&nbsp;</b> </a>
										<a href="javascript:deleteTag('');" class="btnwithico" onclick=""><img src="../../core/style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>
									</div>
									<div class="fr"><%--
										搜索Tag:
										<input class="form-input" type="text" size="30"/>
										<input type="button" value="搜索..." onclick=";" class="btn-1" />
									--%></div>
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
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedTag',this);" type="checkbox" />
												</td>
												<td width="8%">
													<strong>Tag词</strong>
												</td>

												<td width="5%">
													<strong>点击数</strong>
												</td>

												<td width="5%">
													<strong>关联内容数</strong>
												</td>

												<td width="5%">
													<center><strong>操作</strong></center>
												</td>
											</tr>

											
											
											<cms:QueryData objName="Tag" service="cn.com.mjsoft.cms.channel.service.ChannelService" method="getTagWordListQueryTag" var="${param.tagTypeId},,${param.pn},10"> 
												<tr>
													<td>
														${Tag.tagId}
													</td>
													<td>
														<input class="inputCheckbox"  name="checkedTag" value="${Tag.tagId}" type="checkbox" onclick="javascript:" />
													</td>
													<td>
														${Tag.tagName}
													</td>
													<td>
														${Tag.clickCount}
													</td>
													<td>
														${Tag.contentCount}
													</td>
													<td>
														<div>
															<center>
																<span class="STYLE4"><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditTagDialog('${Tag.tagId}');">&nbsp;编辑</a>&nbsp; &nbsp; <img src="../../core/style/default/images/del.gif" width="16" height="16" /><a href="javascript:deleteTag('${Tag.tagId}')">删除 </a>&nbsp;&nbsp;&nbsp; </span>
															</center>
														</div>
													</td>
												</tr>
												

											</cms:QueryData>
											<cms:Empty flag="Tag">
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

initSelect('tagTypeId','${param.tagTypeId}');

function openAddTagDialog()
{
	$.dialog({ 
    	title :'新增Tag词',
    	width: '640px', 
    	height: '270px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/words/CreateSiteTag.jsp'
	});
}


function openEditTagDialog(tagId)
{
	$.dialog({ 
    	title :'编辑Tag词',
    	width: '640px', 
    	height: '250px',
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/words/EditSiteTag.jsp?tagId='+tagId
	});
}


function deleteTag(id)
{
	 var ids='';
	 
	 if('' == id)
	 {
	 	var cidCheck = document.getElementsByName('checkedTag');
	
		
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
	                    content: '没有选择任何Tag！', 
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
    				
                    content: '您确认删除所选Tag吗？！',
                    
                    ok: function () { 
                    
 					var url = "<cms:BasePath/>channel/deleteTag.do?ids="+ids+"&<cms:Token mode='param'/>";
 					
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


function change()
{
	replaceUrlParam(window.location,'tagTypeId='+$('#tagTypeId').val());
}


</script>

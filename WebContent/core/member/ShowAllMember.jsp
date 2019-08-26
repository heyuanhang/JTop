<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>



		<script>
	 	var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );
         	api.get('orud').window.location.reload();         
       		//W.window.location.reload();       
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

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->

					<div style="height:5px"></div>

					<div class="fl">



						&nbsp;
						<select id="searchAction" class="form-select">
							
							<option value="name" selected>
								会员名
							</option>
							<option value="trueName">
								真实名
							</option>
							<option value="email">
								邮件
							</option>
							 

						</select>
						<input id="searchKey" name="query" size="25" maxlength="60" class="form-input" style="vertical-align:top;" value="<cms:DecodeParam codeMode="false" str="${param.key}"/>"/>
						<input onclick="javascript:search();" value="查询" class="btn-1" type="button" style="vertical-align:top;" />
						<div style="height:5px"></div>
					</div>

					<form id="userRoleForm" name="userRoleForm" method="post">
						<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">


							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

											<tr class="datahead">
												<td width="1%" height="30">
													<input class="inputCheckbox" onclick="javascript:selectAll('checkedUserId',this);" type="checkbox" />
												</td>


												<td width="10%">
													<strong>会员名</strong>
												</td>

												<td width="10%">
													<strong>邮箱地址</strong>
												</td>


											</tr>



											<cms:QueryData service="cn.com.mjsoft.cms.security.service.SecurityService" method="getAllSystemMemberBeanForTag" var="${param.roleId},${param.pn},11,${param.sa},${param.key}" objName="Member">
												<tr>
													<td>
														<input class="inputCheckbox" name="checkedUserId" value="${Member.memberId}" type="checkbox" onclick="javascript:" />
													</td>


													<td>
														&nbsp;${Member.memberName}
													</td>

													<td>
														${Member.email}
													</td>

												</tr>
											</cms:QueryData>
											<cms:Empty flag="Member">
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
										<!-- hidden -->
										<input type="hidden" id="roleId" name="roleId" value="${param.roleId}" />
										
										<cms:Token mode="html"/>


										</form>
										<div style="height:15px"></div>

										<div class="breadnavTab">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr class="btnbg100">
													<div style="float:right">
														<a href="javascript:addNewMemberForRole();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
														<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
													</div>

												</tr>
											</table>
										</div>
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
<script>  


 

initSelect('searchAction','${param.sa}');


 

var api = frameElement.api, W = api.opener;
  
function close()
{

	api.close();
	api.get('orud').window.location.reload();
}
   
function addNewMemberForRole()
{
	var cidCheck = document.getElementsByName('checkedUserId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	} 
	
	if(ids == '')
	{
		W.$.dialog
			    ({ 
			      
			  		title : '提示',
			    	width: '180px', 
			    	height: '60px', 
			        lock: true, 
			        parent: api,
			    	icon: '32X32/i.png', 
			        content: '请选择需要加入会员组的会员!', 
			       	cancel: true
		});
		return;
	}

	W.$.dialog.tips('会员组成员增加成功...',1,'loading.gif'); 

	var userForm = document.getElementById('userRoleForm');
	userForm.action = '<cms:BasePath/>security/addMemberFromRole.do';
	userForm.submit();

}
  
function search()
{
	var sa = document.getElementById('searchAction').value;
	
	var key = encodeData(document.getElementById('searchKey').value);
	
	replaceUrlParam(window.location, 'sa='+sa+'&key='+key);
	 
}
</script>

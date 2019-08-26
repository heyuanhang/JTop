<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

		<script>  
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
               var tip =  W.$.dialog
           		({ 
			   					title :'提示',
			    				width: '170px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/succ.png', 
			    				
			                    content: '添加工作流步骤成功',
	
			    				ok: function()			    				
			    				{
			    					tip.close();
			    					
			    					api.get('mcfs').close();
			    					
						            api.get('cwf').window.location.reload();	
						            
						            					            
			    				}
				});
				
				
         }
         
         //验证
		 var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('stepNodeName',0,ref_name,'格式为文字,数字,下划线');
 		 		
		 })
         
            //表格变色
			$(function()
			{ 
		   		$("#showlist1 tr[id!='pageBarTr']").hover(function() 
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
				
				
				$("#showlist3 tr[id!='pageBarTr']").hover(function() 
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

		<cms:LoginUser>
			
			<form id="flowStepForm" name="flowStepForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<div class="auntion_tagRoom" style="margin-top:3px">
								<ul>
									<li id="two1" onclick="setTab('two',1,4)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/node-insert-next.png" width="16" height="16" />步骤基本信息&nbsp;</a>
									</li>

									<li id="two2" onclick="setTab('two',2,4)">
										<a href="javascript:;"><img src="../style/icons/balance.png" width="16" height="16" />参与审核机构&nbsp;</a>
									</li>
									<li id="two3" onclick="setTab('two',3,4)">
										<a href="javascript:;"><img src="../style/icons/users.png" width="16" height="16" />参与审核角色&nbsp;</a>
									</li>
									<li id="two4" onclick="setTab('two',4,4)">
										<a href="javascript:;"><img src="../style/icons/user-green.png" width="16" height="16" />参与审核用户&nbsp;</a>
									</li>
								</ul>
							</div>

							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="21%" class="input-title">
														<strong>名称:</strong>
													</td>
													<td width="80%" class="td-input">
														<input type="text" style="width:369px" id="stepNodeName" name="stepNodeName" class="form-input"></input>
													</td>
												</tr>

												<tr>
													<td width="20%" class="input-title">
														<strong>步骤描叙:</strong>
													</td>
													<td class="td-input">
														<textarea id="stepDesc" name="stepDesc" style="height:65px;width:370px" class="form-textarea"></textarea>
													</td>
												</tr>


												<%--<tr>
														<td width="20%" class="input-title">
															<strong>机构会签模式:</strong>
														</td>
														<td class="td-input">
															<input name="conjunctOrgFlag" type="radio" value="0" checked/>
															否&nbsp;
															<input name="conjunctOrgFlag" type="radio" value="1" />
															是&nbsp;
															<span class="ps">是否需要指定参与机构必须全部通过此步骤才审核生效</span>
														</td>
														
													</tr>

													<tr>
														<td width="20%" class="input-title">
															<strong>角色会签模式:</strong>
														</td>
														<td class="td-input">
															<input name="conjunctRoleFlag" type="radio" value="0" checked/>
															否&nbsp;
															<input name="conjunctRoleFlag" type="radio" value="1" />
															是&nbsp;
															<span class="ps">是否需要指定参与角色必须全部通过此步骤才审核生效</span>
														</td>
														
													</tr>

													<tr>
														<td width="20%" class="input-title">
															<strong>管理员会签模式:</strong>
														</td>
														<td class="td-input">
															<input name="conjunctManFlag" type="radio" value="0" checked/>
															否&nbsp;
															<input name="conjunctManFlag" type="radio" value="1" />
															是&nbsp;
															<span class="ps">是否需要指定参与人员必须全部通过此步骤才审核生效</span>
														</td>
														
													</tr>


													--%>
												<tr>
													<td width="20%" class="input-title">
														<strong>回避模式:</strong>
													</td>
													<td class="td-input">
														<input name="avoidFlag" type="radio" value="0" checked />
														否&nbsp;
														<input name="avoidFlag" type="radio" value="1" />
														是&nbsp;
														<span class="ps">是否系统用户需要回避,不可参与审核自己增加的内容</span>
													</td>

												</tr>
												
												<tr>
													<td width="20%" class="input-title">
														<strong>部门审核:</strong>
													</td>
													<td class="td-input">
														<input name="orgMode" type="radio" value="0" checked />
														否&nbsp;
														<input name="orgMode" type="radio" value="1" />
														是&nbsp;
														<span class="ps">开启部门审核，内容只有所属部门的管理员可审核</span>
													</td>

												</tr>
												
												


											</table>


											<div style="height:20px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a name="btnwithicosysflag" href="javascript:submitFlowStepForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
														</div>
													</tr>
												</table>
											</div>
										<li>
											<ul>
								</div>

								<!-- 第三部分：审核机构 -->
								<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
									<div style="height:10px;"></div>
									<ul>
										<li>
											<div class="DataGrid">
												<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td id="uid_td25" style="padding: 2px 6px;">
															<div class="DataGrid">
																<table id="showlist1"  class="listdate" width="100%" cellpadding="0" cellspacing="0">

																	<tr class="datahead">
																		<td width="1%">
																			<input class="inputCheckbox" value="*" type="checkbox" onclick="javascript:selectAll('checkedOrgId',this);"/>
																		</td>

																		<td width="2%">
																			<strong>机构ID</strong>
																		</td>


																		<td width="18%">
																			<strong>机构名称</strong>
																		</td>



																	</tr>

																	<cms:SystemOrg orgId="${Auth.orgIdentity}" childMode="true">
																		<tr>
																			<td>
																				<input class="inputCheckbox" id="checkedOrgId-${Org.orgId}" name="checkedOrgId" value="${Org.orgId}" type="checkbox"  />
																			</td>
																			<td>
																				${Org.orgId}
																			</td>
																			<td>
																				<span title="">${Org.uiName}</span>
																			</td>

																		</tr>
																	</cms:SystemOrg>
																</table>
														</td>
													</tr>
												</table>
											</div>
											<div style="height:20px"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a name="btnwithicosysflag" href="javascript:submitFlowStepForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
															<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
														</div>
													</tr>
												</table>
											</div>


										</li>
									</ul>

								</div>


								<!-- 第四部分： 审核角色-->
								<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">
									<div style="height:10px;"></div>
									<ul>
										<li>
											<div class="DataGrid">
												<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td id="uid_td25" style="padding: 2px 6px;">
															<div class="DataGrid">
																<table id="showlist2" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																	<tr class="datahead">
																		<td width="1%" height="30">
																			<input class="inputCheckbox" value="*" type="checkbox" onclick="javascript:selectAll('checkedRoleId',this);"/>
																		</td>

																		<td width="2%" height="30">
																			<strong>角色ID</strong>
																		</td>


																		<td width="8%">
																			<strong>角色名称</strong>
																		</td>


																		<td width=7%">
																			<strong>所属机构</strong>
																		</td>

																	</tr>

																	<cms:SystemRoleList>
																		<cms:SystemRole>
																			<tr>
																				<td>
																					<input class="inputCheckbox" id="checkedRoleId-${Role.roleId}" name="checkedRoleId" value="${Role.roleId}" type="checkbox" />
																				</td>
																				<td>
																					${Role.roleId}
																				</td>
																				<td>
																					<span title="">${Role.roleName}</span>
																				</td>


																				<td>
																					${Role.orgName}
																				</td>


																			</tr>
																		</cms:SystemRole>
																	</cms:SystemRoleList>
																</table>
														</td>
													</tr>
												</table>
												<div style="height:20px"></div>
												<div class="breadnavTab"  >
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<div style="float:right">
																<a name="btnwithicosysflag" href="javascript:submitFlowStepForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
																<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
															</div>
														</tr>
													</table>
												</div>
											</div>
										</li>
									</ul>

								</div>






								<!-- 第四部分：审核用户 -->
								<div id="g3_two_4" class="auntion_Room_C_imglist" style="display:none;">
									<div style="height:10px;"></div>
									<ul>
										<li>
											<div class="DataGrid">
												<table  class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td id="uid_td25" style="padding: 2px 6px;">
															<div class="DataGrid">
																<table id="showlist3" class="listdate" width="100%" cellpadding="0" cellspacing="0">
																	<tr class="datahead">
																		<td width="1%" height="30">
																			<input class="inputCheckbox" value="*" type="checkbox" onclick="javascript:selectAll('checkedUserId',this);"/>
																		</td>
																		<td width="2%" height="30">
																			<strong>Id</strong>
																		</td>
																		<td width="8%">
																			<strong>用户名称</strong>
																		</td>
																		<td width="8%">
																			<strong>真实名称</strong>
																		</td>
																	</tr>
																	<cms:SystemUserList allMode="true">
																		<cms:SystemUser>
																			<tr>
																				<td>
																					<input class="inputCheckbox" id="checkedUserId-${SysUser.userId}" name="checkedUserId" value="${SysUser.userId}" type="checkbox" />
																				</td>
																				<td>
																					${SysUser.userId}
																				</td>
																				<td>
																					${SysUser.userName}
																				</td>
																				<td>
																					${SysUser.userTrueName}
																				</td>
																			</tr>

																		</cms:SystemUser>
																	</cms:SystemUserList>
																</table>
															</div>
															</li>
															</ul>
															</div>
															<div style="height:20px"></div>
															<div class="breadnavTab" >
																<table width="100%" border="0" cellpadding="0" cellspacing="0">
																	<tr class="btnbg100">
																		<div style="float:right">
																			<a name="btnwithicosysflag" href="javascript:submitFlowStepForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
																			<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
																		</div>
																	</tr>
																</table>
															</div>

														</td>
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
				<!-- hidden -->
				<input type="hidden" id="checkOrgIds" name="checkOrgIds" />
				<input type="hidden" id="checkRoleIds" name="checkRoleIds" />
				<input type="hidden" id="checkUserIds" name="checkUserIds" />
				<input type="hidden" id="actions" name="actions" />

				<input type="hidden" id="flowId" name="flowId" value="${param.flowId}" />
				<input type="hidden" id="stepId" name="stepId" value="${param.stepId}" />
				
				<cms:Token mode="html"/>
			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">


function setTab(flag,pos,size)
{
	
		//hideTips('stepNodeName');
		setTab2(flag,pos,size);

}

var auditOrgArray = new Array();
var auditRoleArray = new Array();
var auditUserArray = new Array();

var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
	api.reload( api.get('cwf') );   
}


function submitFlowStepForm()
{
	var hasError = false;
	//系统信息字段验证
     
    currError = submitValidate('stepNodeName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }
    
    
    			
    
    
    if(hasError)
    {
    	setTab('two',1,4);
     	
	}
	else
	{
		disableAnchorElementByName("btnwithicosysflag",true);

		var from = document.getElementById('flowStepForm');
	    
	    from.action="<cms:BasePath/>workflow/createWorkflowStep.do";
	    
	    checkAuditOrg()
	    
	    checkAuditRole();
	    
	    checkAuditUser();
	    
	    
	    document.getElementById('checkOrgIds').value = auditOrgArray.join('*');
	    
	    document.getElementById('checkRoleIds').value = auditRoleArray.join('*');
	    
	    document.getElementById('checkUserIds').value = auditUserArray.join('*');
	    
	    encodeFormInput('flowStepForm', false);
	    
	    from.submit();
    }
}

function checkAuditUser(check)
{
	var checkedUserIds = document.getElementsByName('checkedUserId');
    
    var box;
    for(var i = 0; i < checkedUserIds.length; i++)
    {
    	box = checkedUserIds[i];
    	if(box.checked == true)
    	{
    		auditUserArray.push(box.value);
    	}	
    
    }
}

function checkAuditRole()
{
    var checkedRoleIds = document.getElementsByName('checkedRoleId');
    
    for(var i = 0; i < checkedRoleIds.length; i++)
    {
    	box = checkedRoleIds[i];
    	if(box.checked == true)
    	{
    		auditRoleArray.push(box.value);
    	}
    }
}

function checkAuditOrg()
{
    var checkedOrgIds = document.getElementsByName('checkedOrgId');
    
    for(var i = 0; i < checkedOrgIds.length; i++)
    {
    	box = checkedOrgIds[i];
    	if(box.checked == true)
    	{
    		auditOrgArray.push(box.value);
    	}
    }
}


</script>

</cms:LoginUser>

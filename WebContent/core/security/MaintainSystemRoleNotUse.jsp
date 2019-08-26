<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>


		<script>
	 

　

      </script>
	</head>
	<body>
		<cms:SystemRole id="${param.roleId}">
			<div class="breadnav">
				<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left">
							&nbsp;
							<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
							当前位置：
							<a href="#">系统管理</a> &raquo; 角色管理 &raquo; 编辑角色 &raquo; ${Role.roleName}
						</td>
						<td align="right">

						</td>
					</tr>
				</table>
			</div>
			<div style="height:25px;"></div>

			<form id="resourceForm" name="resourceForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="mainbody" align="left" valign="top">

							<!--main start-->
							<div class="auntion_tagRoom">
								<ul>
									<li id="two1" onclick="setTab2('two',1,5)" class="selectTag">
										<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />基本信息&nbsp;</a>
									</li>
									<li id="two2" onclick="setTab2('two',2,5)">
										<a href="javascript:;"><img src="../style/blue/icon/application-search-result.png" width="16" height="16" />系统菜单权限&nbsp;</a>
									</li>
									<li id="two3" onclick="setTab2('two',3,5)">
										<a href="javascript:;"><img src="../style/blue/icon/application-dock-tab.png" width="16" height="16" />栏目管理权限&nbsp;</a>
									</li>
									<li id="two4" onclick="setTab2('two',4,5)">
										<a href="javascript:;"><img src="../style/blue/icon/application--exclamation.png" width="16" height="16" />内容管理权限&nbsp;</a>
									</li>
									<li id="two5" onclick="setTab2('two',5,5)">
										<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />推荐位&nbsp;</a>
									</li>

								</ul>
							</div>

							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
										<tr>
											<td width="20%" class="input-title">
												<strong>角色名称:</strong>
											</td>
											<td width="80%" class="td-input">
												<input type="text" size="39" id="roleName" name="roleName" class="form-input" value="${Role.roleName}"></input>
											</td>
										</tr>
										<tr>
											<td width="20%" class="input-title">
												<strong>所属机构:</strong>
											</td>
											<td class="td-input">
												<select id="orgId" name="orgId" form="form-select">
													<option value="-1">
														------------ 请选择所属部门 ------------
													</option>

													<cms:SystemOrg orgId="all">
														<option value="${Org.orgId}">
															${Org.uiLayerName}
														</option>
													</cms:SystemOrg>

												</select>
												<span class="red">*</span><span class="ps"></span>
											</td>
										</tr>
										<tr>
											<td width="20%" class="input-title">
												<strong>角色描叙:</strong>
											</td>
											<td class="td-input">
												<textarea id="roleDesc" name="roleDesc" cols="93" rows="4" class="form-textarea">${Role.roleDesc}</textarea>
											</td>
										</tr>


										<tr>
											<td width="20%" class="input-title">
												<strong>状态:</strong>
											</td>
											<td class="td-input">
												<input id="userState" name="useState" type="radio" value="1" />
												<span class="STYLE12">启用</span> &nbsp;
												<input id="userState" name="useState" type="radio" value="0" />
												<span class="STYLE12">停用</span>
											</td>
									<script type="text/javascript">
                                      initRadio('useState','${Role.useState}');
                                       initSelect('orgId','${Role.orgId}');
                                    </script>
										</tr>

									</table>

									<div style="height:6px;"></div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="btnbg100">
											<td class="input-title" width="40%"></td>
											<td class="td-input">
												<a href="javascript:submitResourceForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
												<a href="javascript:window.location='ManagerRole.jsp';" class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
											</td>
										</tr>
									</table>
								</div>
								<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
									<cms:ResourceList orgId="${Role.orgId}">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
											<tr>
												<td class="input-title">

												</td>
												<td width="100%" class="td-input">
													<a href="javascript:regRes('checkAll');" class="btnwithico"> <img src="../style/icons/tick.png" width="16" height="16" /> <b>全选&nbsp;</b> </a>
												</td>
											</tr>



											<tr>
												<td class="input-title">

												</td>
												<td class="td-input">
													<cms:Resource>
														<cms:if test="${Res.resourceType == 1}">
															<div class="addtit">
																<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
																<img src="<cms:Domain/>icons/${Res.icon}" width="16" height="16" />
																<strong>${Res.resourceName}</strong>
															</div>
															<br />
														</cms:if>
														<cms:elseif test="${Res.resourceType == 3}">
												
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
															<img src="<cms:Domain/>icons/${Res.icon}" width="16" height="16" />
															<strong>${Res.resourceName}</strong>

															<br />
															<cms:if test="${Res.isLeaf == 0}">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															</cms:if>
															<cms:else/>
														</cms:elseif>
														<cms:elseif test="${Res.resourceType == 4}">
															<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
															<strong>${Res.resourceName}</strong>
															<cms:if test="${Res.isLastChild == 1}">
																<br />
																<br />
															</cms:if>
															<cms:else/>
														</cms:elseif>
													</cms:Resource>
												</td>
											</tr>

										</table>
									</cms:ResourceList>

									<div style="height:6px;"></div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr class="btnbg100">
											<td class="input-title" width="40%"></td>
											<td class="td-input">
												<a href="javascript:submitResourceForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
												<a href="javascript:window.location='ManagerRole.jsp';" class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
											</td>
										</tr>
									</table>
								</div>

								<!-- 第三部分： -->
								<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">
									<ul>
										<li>

											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td class="input-title">

													</td>
													<td class="td-input">
														<div style="height:4px;"></div>
														<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">


															<tr>
																<td id="uid_td25" style="padding: 2px 6px;">
																	<div class="DataGrid">
																		<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

																			<tr class="datahead">
																				<td width="2%">
																					<strong>ID</strong>
																				</td>


																				<td width="35%">
																					<strong>栏目名称</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="add-accredit-checkAll" onclick="javascript:('add',this);">
																					<strong>添加</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="edit-accredit-checkAll" onclick="javascript:accreditAll('edit',this);">
																					<strong>修改</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="maintain-accredit-checkAll" onclick="javascript:accreditAll('maintain',this);">
																					<strong>维护</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="delete-accredit-checkAll" onclick="javascript:accreditAll('delete',this);">
																					<strong>删除</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="commend-accredit-checkAll" onclick="javascript:accreditAll('commend',this);">
																					<strong>推荐</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="publish-accredit-checkAll" onclick="javascript:accreditAll('publish',this);">
																					<strong>发布</strong>
																				</td>

																			</tr>

																			<cms:ClassList site="总站" type="all">
																				<cms:Class>
																					<tr>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-checkClassAll" name="checkClassAll" onclick="javascript:accreditClassAll('${Class.linearOrderFlag}',this);">
																						</td>

																						<td>
																							${Class.layerUIClassName}
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-add" name="add-class-accredit-check" value="${Class.classId}" onclick="javascript:accreditSingle('add','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-edit" name="edit-class-accredit-check" value="${Class.classId}" onclick="javascript:accreditSingle('edit','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-maintain" name="maintain-class-accredit-check" value="${Class.classId}" onclick="javascript:accreditSingle('maintain','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-delete" name="delete-class-accredit-check" value="${Class.classId}" onclick="javascript:accreditSingle('delete','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-commend" name="commend-class-accredit-check" value="${Class.classId}" onclick="javascript:accreditSingle('commend','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-publish" name="publish-class-accredit-check" value="${Class.classId}" onclick="javascript:accreditSingle('publish','${Class.linearOrderFlag}',this);">
																						</td>

																					</tr>
																				</cms:Class>
																			</cms:ClassList>
																		</table>
																	</div>

																</td>
															</tr>

														</table>
													</td>
												</tr>

											</table>
										</li>
									</ul>
								</div>

								<!-- 第四部分：内容管理权限 -->
								<div id="g3_two_4" class="auntion_Room_C_imglist" style="display:none;">
									<ul>
										<li>

											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td class="input-title">

													</td>
													<td class="td-input">
														<div style="height:4px;"></div>
														<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">


															<tr>
																<td id="uid_td25" style="padding: 2px 6px;">
																	<div class="DataGrid">
																		<table class="listdate" width="100%" cellpadding="0" cellspacing="0">

																			<tr class="datahead">
																				<td width="2%">
																					<strong>ID</strong>
																				</td>


																				<td width="35%">
																					<strong>栏目名称</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="add-accredit-checkAll" onclick="javascript:accreditAll('add',this);">
																					<strong>添加</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="edit-accredit-checkAll" onclick="javascript:accreditAll('edit',this);">
																					<strong>修改</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="maintain-accredit-checkAll" onclick="javascript:accreditAll('maintain',this);">
																					<strong>维护</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="delete-accredit-checkAll" onclick="javascript:accreditAll('delete',this);">
																					<strong>删除</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="commend-accredit-checkAll" onclick="javascript:accreditAll('commend',this);">
																					<strong>推荐</strong>
																				</td>
																				<td width="6%">
																					<input type="checkbox" id="publish-accredit-checkAll" onclick="javascript:accreditAll('publish',this);">
																					<strong>发布</strong>
																				</td>

																			</tr>

																			<cms:SystemOrgClass siteId="1" orgId="${Role.orgId}" secType="1">
																			
																					<tr>
																						<td>
																							<input type="checkbox" id="${Class.linearOrderFlag}-checkClassAll" name="checkClassAll" onclick="javascript:accreditClassAll('${Class.linearOrderFlag}',this);">
																						</td>

																						<td>
																							${Class.layerUIClassName}
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.classId}-add-content-accredit" name="add-content-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:accreditSingle('add','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.classId}-edit-content-accredit" name="edit-content-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:accreditSingle('edit','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.classId}-maintain-content-accredit" name="maintain-content-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:accreditSingle('maintain','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.classId}-delete-content-accredit" name="delete-content-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:accreditSingle('delete','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.classId}-commend-content-accredit" name="commend-content-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:accreditSingle('commend','${Class.linearOrderFlag}',this);">
																						</td>
																						<td>
																							<input type="checkbox" id="${Class.classId}-publish-content-accredit" name="publish-content-accredit" value="${Class.linearOrderFlag}-${Class.classId}" onclick="javascript:accreditSingle('publish','${Class.linearOrderFlag}',this);">
																						</td>

																					</tr>
																				</cms:SystemOrgClass>
																			
																		</table>
																	</div>

																</td>
															</tr>

														</table>
													</td>
												</tr>

											</table>

											<div style="height:6px;"></div>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr class="btnbg100">
													<td class="input-title" width="40%"></td>
													<td class="td-input">
														<a href="javascript:submitResourceForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
														<a href="javascript:window.location='ManagerRole.jsp';" class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>返回&nbsp;</b> </a>
													</td>
												</tr>
											</table>
										</li>
									</ul>
								</div>
								<div id="g3_two_5" class="auntion_Room_C_imglist" style="display:none;">
									<ul>
										<li>
											5
										</li>
									</ul>
								</div>

								

							</div>






						</td>
					</tr>

					<tr>
						<td height="10">
							&nbsp;
						</td>
					</tr>
				</table>

				<!-- hidden -->
				<input type="hidden" id="roleId" name="roleId" value="${param.roleId}">
			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

//系统粗粒度资源初试化  --start--

var addCheckBox = document.getElementsByName('add-content-accredit');
var editCheckBox = document.getElementsByName('edit-content-accredit');
var maintainCheckBox = document.getElementsByName('maintain-content-accredit');
var deleteCheckBox = document.getElementsByName('delete-content-accredit');
var commendCheckBox = document.getElementsByName('commend-content-accredit');
var publishCheckBox = document.getElementsByName('publish-content-accredit');


//初始化系统菜单功能 开始
var checkedIdMap = new HashMapJs();
var haveAllResIdArray = new Array();
var allResIdArray = new Array();

//当前角色所拥有的资源
<cms:ResourceList roleId="${param.roleId}">
   <cms:Resource>
   	haveAllResIdArray.push("${Res.linearOrderFlag}-checkRes-${Res.secResId}");
   </cms:Resource>
</cms:ResourceList>

//系统所有资源
<cms:ResourceList roleId="all">
   <cms:Resource>
   	allResIdArray.push("${Res.linearOrderFlag}-checkRes-${Res.secResId}");
   </cms:Resource>
</cms:ResourceList>

var targetRes;


//在所有已经被选择的资源里寻找
for(var i =0; i < haveAllResIdArray.length; i++)
{
   targetRes = document.getElementById(haveAllResIdArray[i]);
   //若id存在,将此ID注册在页面
   if(targetRes != null)
   {
   	targetRes.checked=true;
   	//regRes(targetRes);
   }
}

var selectAllFlag = false;

//注册系统资源checkBox方法
function regRes(resBox)
{

  if(resBox==="checkAll" && selectAllFlag==false)
  {
      selectAll('checkResource');
      selectAllFlag = true;
  }
  
  else if(resBox==="checkAll" &&  selectAllFlag==true)
  {
      unSelectAll('checkResource');
      selectAllFlag = false;
  }
  
  else
  {

     //单一资源注册
     //alert(resBox.checked);
     

      var currentIdFlag = resBox.id.split('-')[0];
      var testResId = "";
  	  for(var i =0; i < allResIdArray.length; i++)
		{
		   targetRes = document.getElementById(allResIdArray[i]);
		   //若id存在,将此ID注册在页面
		   if(targetRes != null)
		   {
		      testResId = allResIdArray[i]+"";
		     
		      //alert(testResId + " : " + currentIdFlag);
		   	  if(testResId.startWith(currentIdFlag))
		   	  {
		   	  	 if(resBox.checked == true)
		   	  	 {
		   	  		targetRes.checked=true;
		   	  	 }
		   	     else
		   	     {
		   	        targetRes.checked=false;
		   	     }
		   	  }

		    }
    
		}
	
		if(resBox.checked == true)
		{
			//所有父节点check
			var linearCount = currentIdFlag.length / 3;
			var linear;
			var pos=0;
		    for(var j=0; j<linearCount;j++)
			{
			    linear = currentIdFlag.substring(pos,pos + ((j+1) * 3));
			    
			    for(var k =0; k < allResIdArray.length; k++)
				{
				    testResId = allResIdArray[k]+"";
				    targetRes = document.getElementById(allResIdArray[k]);
				    
				    if(targetRes != null)
			        {
					    if(testResId.startWith(linear+"-"))
						{
						   	if(resBox.checked == true)
						   	{
						   	    targetRes.checked=true;
						   	}
						   	else
						   	{
						   	    targetRes.checked=false;
						   	}
						 }
					}
			    }
			}
	   } 
   }
  
  
}

//系统粗粒度资源初试化  --end--


//内容管理细粒度权限初试化  --start--
var parentOrgRoleContentAccClassInfoMap = new HashMapJs();
<cms:AccInfoList orgId="${Role.orgId}" siteId="1" secType="1">
<cms:AccInfo>
parentOrgRoleContentAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

var allRoleContentAccClassInfoMap = new HashMapJs();
<cms:AccInfoList roleId="${param.roleId}" accType="group">
<cms:AccInfo>
allRoleContentAccClassInfoMap.put('${Acc.accId}-${Acc.sysFlag}','true');
</cms:AccInfo>
</cms:AccInfoList>

//alert( allRoleContentAccClassInfoMap.allValueToString('*'));


	var box;
	for(var i=0; i<addCheckBox.length;i++)
	{
		box = addCheckBox[i];
		changeCheckBoxStateForAcc(box);
	}
	
	for(i=0; i<editCheckBox.length;i++)
	{
		box = editCheckBox[i];
		changeCheckBoxStateForAcc(box);
	}
	
	for(i=0; i<maintainCheckBox.length;i++)
	{
		box = maintainCheckBox[i];
		changeCheckBoxStateForAcc(box);
	}
	
	for(i=0; i<deleteCheckBox.length;i++)
	{
		box = deleteCheckBox[i];
		changeCheckBoxStateForAcc(box);
	}
	
	for(i=0; i<commendCheckBox.length;i++)
	{
		box = commendCheckBox[i];
		changeCheckBoxStateForAcc(box);
	}
	
	for(i=0; i<publishCheckBox.length;i++)
	{
		box = publishCheckBox[i];
		changeCheckBoxStateForAcc(box);
	}
	
function changeCheckBoxStateForAcc(target)
{
	var flag = allRoleContentAccClassInfoMap.get(target.id+'');
	var parFlag = parentOrgRoleContentAccClassInfoMap.get(target.id+'');
	var id = target.id+'';
	if(target != null)
	{
	//alert(id.indexOf(flag+''));
	//alert(flag);
		if('true' == flag)
		{			
			target.checked = true;	
		}	
		
		if('true' != parFlag)
		{
		 target.style.display='none';
		}
		else{
		
		}
	}
}
	

var checkAddClassIds = document.getElementsByName('checkAddClass');
var length = checkAddClassIds.length;
for(var i=0; i<length; i++ )
{
	if('true' == addClassIdTempMap.get(checkAddClassIds[i].value))
	{
		checkAddClassIds[i].checked=true;
	}
}

//内容管理细粒度权限初试化  --end--




//内容审核细粒度权限初试化  --start--
var checkedAuditClassIdMap = new HashMapJs();
<cms:AccInfoList roleId="${param.roleId}" accType="audit">
<cms:AccInfo>
checkedAuditClassIdMap.put('ccuc${Acc.accId}','true');
</cms:AccInfo>
</cms:AccInfoList>

//alert( checkedAuditClassIdMap.allValueToString('*'));

var checkAuditClassIds = document.getElementsByName('checkAuditClass');
var length = checkAuditClassIds.length;
for(var i=0; i<length; i++ )
{
	if('true' == checkedAuditClassIdMap.get('ccuc'+checkAuditClassIds[i].value))
	{
		checkAuditClassIds[i].checked=true;
	}
}

//内容审核细粒度权限初试化  --end--



var checkedIdMap = new HashMapJs();
var checkedManagerClassIdMap = new HashMapJs();
var checkedAddClassIdMap = new HashMapJs();
//fun:注册细粒度内容录入资源
function regAddContentClass(classBox)
{

  if(classBox.id==='checkAllAddClass')
  {
      if(classBox.checked==true) 
      {  
         selectAll('checkAddClass');
      }
      else if(classBox.checked==false)
      {
         unSelectAll('checkAddClass');
      }
         
      return false;
  }
 
  if(classBox.checked)
  {
      //checkedAddClassIdMap.put(classBox.value,classBox.value);
  }
  else
  {
     // checkedAddClassIdMap.remove(classBox.value,classBox.value);
      document.getElementById('checkAllAddClass').checked=false;
  }
  
  
  //alert(checkedAddClassIdMap.allValueToString('*'));
   return false;  
}

//fun:注册细粒度内容审核资源
function regAuditContentClass(classBox)
{
  if(classBox.id==='checkAllAuditClass')
  {
      if(classBox.checked==true) 
      {  
         selectAll('checkAuditClass',checkedAuditClassIdMap,'checkAllAuditClass');
      }
      else if(classBox.checked==false)
      {
         unSelectAll('checkAuditClass',checkedAuditClassIdMap);
      }
      
       return false;
  }
  
   	  if(classBox.checked)
	  {
	      checkedAuditClassIdMap.put(classBox.value,classBox.value);
	  }
	  else
	  {
	      checkedAuditClassIdMap.remove(classBox.value,classBox.value);
	      document.getElementById('checkAllAuditClass').checked=false;
	  }
  
   return false;
}


function accreditAll(flag,allAction)
{
	var targetCheckBox = document.getElementsByName(flag+'-content-accredit');
	
	var box;
	for(var i=0; i<targetCheckBox.length;i++)
	{
		box = targetCheckBox[i];
		if(box != null)
		{
		    if(allAction.checked)
		    {
		    	box.checked = true;	
		    }
		    else
		    {
		    	box.checked = false;	
		    }
			
		}
	
	}

}

function accreditClassAll(linearFalg,classCheckEvent)
{
	 
	
	
	var classCheckAllBox = document.getElementsByName('checkClassAll');
	
	
	
	var box;
	for(var i=0; i<addCheckBox.length;i++)
	{
		box = addCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<editCheckBox.length;i++)
	{
		box = editCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<maintainCheckBox.length;i++)
	{
		box = maintainCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<deleteCheckBox.length;i++)
	{
		box = deleteCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<commendCheckBox.length;i++)
	{
		box = commendCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<publishCheckBox.length;i++)
	{
		box = publishCheckBox[i];
		changeCheckBoxState(box,linearFalg,classCheckEvent);
	}
	
	for(i=0; i<classCheckAllBox.length;i++)
	{
		box = classCheckAllBox[i];
		if(box.id.startWith(linearFalg+''))
		if(classCheckEvent.checked)
		{
		    box.checked = true;	
		}
		else
		{
		    box.checked = false;	
		}		
	}

}

function changeCheckBoxState(target,flag,event)
{
	if(target != null)
	{
		if(target.value.startWith(flag+''))
		if(event.checked)
		{
		    target.checked = true;	
		}
		else
		{
		    target.checked = false;	
		}			
	}


}

function accreditSingle(flag,linearFlag,eventBox)
{
		//var allBox = document.getElementsByName(flag+'-accredit-check');
		//var allClassBox = document.getElementsByName('checkClassAll');
		
		//var box;
		//for(var i=0; i<allBox.length;i++)
		//{
		//	box = allBox[i];
		//	changeCheckBoxState(box,linearFlag,eventBox);
		//}
		
		//for(i=0; i<allClassBox.length;i++)
		//{
		//	box = allClassBox[i];
			//changeCheckBoxState(box,linearFlag,eventBox);
		//}
		
}




function submitResourceForm()
{
   resourceForm.action="../../security/editSystemRole.do";
   resourceForm.submit();
}









</script>

</cms:SystemRole>

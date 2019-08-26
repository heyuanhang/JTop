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
		<script type="text/javascript" src="../../javascript/layer/layer.js"></script>
	
		

		<script>  
		  var api = frameElement.api, W = api.opener; 
         
         
        	
      </script>
	</head>
	<body>


		 
		<form id="flowStepForm" name="flowStepForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div style="padding: 9px 0px;">
						所属机构:&nbsp;
						<select class="form-select" id="orgCode" name="orgCode" onchange="javascript:change();">
							<option value="">
								---- 所有管理员 ---- 
							</option>
							
							<cms:SystemOrg orgId="all">
							<option value="${Org.linearOrderFlag}">
								${Org.orgName}
							</option>
							
							</cms:SystemOrg>
							 

						</select>
						&nbsp; 
						<div class="fr">
										<span>真实姓名(模糊查询):</span>
										<input id="searchKey"size="20" maxlength="60" class="form-input"  value="<cms:DecodeParam  codeMode='false' str='${param.key}'/>"/>
										<input onclick="javascript:search();" value="查询" class="btn-1" class="form-input" type="button" style="vertical-align:top;" />

									</div>
					</div>

						<div class="auntion_tagRoom_Content">


							 <!-- 第四部分：审核用户 -->
						<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
							<div style="height:10px;"></div>
							<ul>
								<li>
									<div class="DataGrid">
										<table class="listtable" width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td id="uid_td25" style="padding: 0px 0px;">
													<div class="DataGrid">
														<table class="listdate" width="100%" cellpadding="0" cellspacing="0">
															<tr class="datahead">
																<td width="1%" height="30">
																	 
																</td>
																 
																<td width="9%">
																	<strong>真实姓名</strong>
																</td>
																
																<td width="9%">
																	<strong>用户名</strong>
																</td>
															</tr>
															<cms:QueryData objName="SysUser" service="cn.com.mjsoft.cms.content.service.ContentService" method="getStepActUserForTag" var="${param.contentId},${param.toStep},${param.infoType},${param.orgCode},${param.key}">
															
																	<tr onclick="javascript:doCheck('checkedUserId-${SysUser.userId}');">
																		<td >
																			<input class="inputCheckbox" id="checkedUserId-${SysUser.userId}" name="checkedUserId" value="${SysUser.userId}-${SysUser.userName}" type="radio" />
																		</td>
																		 
																		<td>
																			${SysUser.userTrueName}
																		</td>
																		 
																		<td>
																			${SysUser.userName}
																		</td>
																		 
																	</tr>

															</cms:QueryData>
																<cms:Empty flag="SysUser">
																	<tr>
																		<td class="tdbgyew" colspan="9">
																			<center>
																				当前没有数据!
																			</center>
																		</td>
																	</tr>
																</cms:Empty>
														</table>
													</div>
													</li>
													</ul>
													</div>
													<div style="height:50px;"></div>
													<div class="breadnavTab"  >
														<table width="100%" border="0" cellpadding="0" cellspacing="0">
															<tr class="btnbg100">
																<div style="float:right">
																	<a href="javascript:selectReMan();"  class="btnwithico"><img src="../../style/icons/tick.png" width="16" height="16"><b>确认审核人&nbsp;</b> </a>

																	<a href="javascript:close();"  class="btnwithico"><img src="../../style/icon/close.png" width="16" height="16"><b>关闭&nbsp;</b> </a>



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
		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
</html>
<script type="text/javascript">


function doCheck(idv)
{
	$('#'+idv).attr('checked','true');
}
  
  //init
  	var cps;
  	
	if( api.get('main_content') != null )
	{
		cps = api.get('main_content').window.document.getElementById('jtopcms_sys_flow_next_step').value;	
	}
	else
	{
		cps = api.get('oegbd').window.document.getElementById('jtopcms_sys_flow_next_step').value;
	}
 

    var cpsa = cps.split(',');
	for(var i=0; i<cpsa.length; i++)
	{
	   if(cpsa[i] != '')
	   {
	   	 $('#checkedUserId-'+cpsa[i]).attr('checked','true');
	   
	   }
	}
	
	initSelect('orgCode','${param.orgCode}');

function close()
{	  
	if( api.get('main_content') != null )
	{
		api.get('main_content').$('#jtopcms_sys_flow_suguest').val(api.get('gscp').$('#flowSug').val())
		api.get('main_content').$('#jtopcms_sys_flow_edit_suguest').val(api.get('gscp').$('#flowEditSug').val())
		  
		replaceUrlParam(api.get('gscp').window.location,'reload=true');
	}
	else
	{
		api.get('oegbd').$('#jtopcms_sys_flow_suguest').val(api.get('gscp').$('#flowSug').val())
		api.get('oegbd').$('#jtopcms_sys_flow_edit_suguest').val(api.get('gscp').$('#flowEditSug').val())
		  
		replaceUrlParam(api.get('gscp').window.location,'reload=true');
	}
	
	
	      
 
	 api.close();
	 
}
 
	
function selectReMan()
{
	var ids='';
	
	var temp;
	
	var show = '';

	 
	
	//user
	ids='';
	
	cidCheck = document.getElementsByName('checkedUserId');
	
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			temp = cidCheck[i].value.split('-');
			ids += temp[0]+',';
			show += temp[1]+', ';
		}
	}
	
	if( api.get('main_content') != null )
	{
		api.get('main_content').window.document.getElementById('jtopcms_sys_flow_next_step').value =ids; 
	}
	else
	{
		api.get('oegbd').window.document.getElementById('jtopcms_sys_flow_next_step').value =ids;
	}
	
 	  
	//显示名称
	//api.get('osmd').document.getElementById('showSelectMan').value = show;

	close();
}

function search()
{
		
	var key = document.getElementById('searchKey').value;
	 
	key=encodeURIComponent(encodeData(key));
	 
	replaceUrlParam(window.location, 'key='+key);
 
}


function change()
{

	replaceUrlParam(window.location, 'orgCode='+$('#orgCode').val());
}


</script>


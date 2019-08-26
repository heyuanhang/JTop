<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>
<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css"
			rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript"
			src="../style/blue/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript"
			src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script>
	

      </script>
	</head>
	<body>

		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16"
							class="home" />
						当前位置：
						<a href="#">站点维护</a> &raquo; FTP文件传输状态
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
						<table class="listtable" width="99.8%" border="0" cellpadding="0"
							cellspacing="0">

							<tr>
								<td style="padding: 7px 10px;" class="">
									<div class="fl">
										发布点:&nbsp;<select class="form-select">
											<option>
												-------- 所有发布点 --------
											</option>
										</select>
										&nbsp;

									</div>
									<div>
										<a href="javascript:deleteCheckedRole();" class="btnwithico"
											onclick=""><img
												src="../style/blue/icon/application--minus.png" width="16"
												height="16" /><b>删除传输记录&nbsp;</b> </a>

									</div>
								</td>
							</tr>

							<tr>
								<td id="uid_td25" style="padding: 2px 6px;">
									<div class="DataGrid">
										<table class="listdate" width="100%" cellpadding="0"
											cellspacing="0">

											<tr class="datahead">

												<td width="2%">
													<strong>ID</strong>
												</td>

												<td width="1%">
													<input class="inputCheckbox" value="*" onclick=""
														type="checkbox" />
												</td>
												<td width="20%">
													<strong>文件名称</strong>
												</td>

												<td width="7%">
													<strong>发布点</strong>
												</td>

												<td width="3%">
													<strong>状态</strong>
												</td>

												<td width="7%">
													<strong>传输时间</strong>
												</td>

												<td width="6%">
													<strong>操作</strong>
												</td>
											</tr>

											<cms:CurrentSite>
											
													<tr>
														<td>
															12
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\index.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															<font color="red">失败</font>
														</td>

														<td>
															2012-5-12 12:20:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															13
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\content\10345.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															<font color="red">失败</font>
														</td>

														<td>
															2012-5-12 12:22:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															14
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\content\10346.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															<font color="red">失败</font>
														</td>

														<td>
															2012-5-12 1:14:09
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															15
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\content\10642.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															<font color="green">等待</font>
														</td>

														<td>
															2013-11-06 12:20:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															16
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\channel\27.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															<font color="green">等待</font>
														</td>

														<td>
															2013-11-06 12:20:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															17
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\channel\28.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															
															<font color="green">等待</font>
														</td>

														<td>
															2013-11-06 12:20:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													
													<tr>
														<td>
															18
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\channel\21.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															<font color="green">等待</font>
														</td>

														<td>
															2013-11-06 12:20:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															19
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\channel\28.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															
															<font color="green">等待</font>
														</td>

														<td>
															2013-11-06 12:20:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													
													<tr>
														<td>
															20
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\channel\22.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															<font color="green">等待</font>
														</td>

														<td>
															2013-11-06 12:20:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															21
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\channel\29.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															
															<font color="green">等待</font>
														</td>

														<td>
															2012-11-06 12:21:23
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													
													<tr>
														<td>
															22
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\channel\31.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															<font color="green">等待</font>
														</td>

														<td>
															2012-11-06 12:21:45
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															23
														</td>
														<td>
															<input class="inputCheckbox" id="checkedRoleId"
																name="checkedRoleId" value="${Gateway.gatewayId}"
																type="checkbox" onclick="javascript:" />
														</td>
														<td>
															&nbsp;\demo\html\channel\32.html
														</td>
														<td>
															静态HTML发布
														</td>
														<td>
															
															<font color="green">等待</font>
														</td>

														<td>
															2012-11-06 12:22:45
														</td>


														<td>
															<div>
																<center>
																	<span class="STYLE4"><a
																		href="EditSystemRole.jsp?roleId=${Role.roleId}">立即发送</a>&nbsp;
																		|&nbsp; <a
																		href="../../security/deleteSystemRole.do?roleId=${Role.roleId}">删除
																	</a> </span>
																</center>
															</div>
														</td>
													</tr>
												
											</cms:CurrentSite>
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

function  openAddPublishGateway()
{
	$.dialog({ 
    	title :'新增内容分发点',
    	width: '550px', 
    	height: '340px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/deploy/AddPublishGateway.jsp'
	});
}


</script>

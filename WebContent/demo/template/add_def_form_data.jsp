<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		 
			<title>JTopCMS - 演示站点</title>
			<!--[if IE 7]>
			<link rel="stylesheet" href="css/font-awesome-ie7.css">
			<![endif]-->
			<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
			<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
			<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>
	</head>


	<body>
		<!--头部开始-->
		<cms:Include page="include/head.jsp?currClassId=${Info.classId}" />
		<!--头部结束-->
		
		<cms:Member loginMode="true">
		<!--主体开始-->
		<div class="main_box">
			<!--左侧-->
			<div>

				<div >
					<!--新闻详情页开始-->
					<div  >
						<h1 class="ep-h1 bigtitle">
							 
						</h1>

						<!--留言-->
						<div class="bs-example">
							<div class="tie-titlebar">
								<span style="left:120px; top:30px; font-size:18px; font-weight:bold; padding:10px 15px; color:#06c">投递简历</span>
								<ins>
								
								</ins>
							</div>
							<form id="gbForm" name="gbForm" method="post">
								<table width="100%" border="0" cellpadding="0" cellspacing="12">
									<tr>
										<td align="right" width="15%">
											<label for="exampleInputEmail1" class="control-label">
												招聘岗位:
											</label>


										</td>

										<td  style="vertical-align:top">

											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														<input name="jt_mh_jl_gw" type="radio" value="1" />项目经理
													</td>

													<td>
														<input name="jt_mh_jl_gw" type="radio" value="2" />设计总监
													</td>

													<td>
														<input name="jt_mh_jl_gw" type="radio" value="3" />工程师
													</td>

													<td>
														<input name="jt_mh_jl_gw" type="radio" value="4" />前端美工
													</td>

													<td>
														<input name="jt_mh_jl_gw" type="radio" value="5" />软件测试
													</td>
												</tr>

											</table>




										</td>


									</tr>

									<tr>
										<td align="right" width="15%">
											<label for="exampleInputEmail1" class="control-label">
												人员姓名:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														<input name="jt_jl_xm" id="jt_jl_xm" type="text" style="width:245px" class="form-control" value="${Member.memberName}"/>
													</td>
													<td width="1%">

													</td>
													<td>
														<label for="exampleInputEmail1" class="control-label">
															最高学历:
														</label>

													</td>
													<td>
													<select id="jt_jl_xl" name="jt_jl_xl" class="form-select"  ><option value="-1" >----- 请选择您的学历 -----</option><option value="1" >博士</option><option value="2" >研究生</option><option value="3" >本科</option><option value="4" >大专</option><option value="5" >高中</option></select>
														 
													</td>

												</tr>

											</table>


										</td>


									</tr>


								 

									 

									<tr>
										<td align="right" width="15%">
											个人介绍:

										</td>
										<td>

											<textarea style="width:670px;height:180px" id="jt_jl_jieshao" name="jt_jl_jieshao" id="gbText" class="form-control"></textarea>

										</td>


									</tr>

									<tr>
										<td align="right" width="15%">
											验证码:


										</td>

										<td>
											<table width="30%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>

														<input name="sysCheckCode" id="sysCheckCode" type="text" class="form-control" style="width:80px" maxlength="4" />

													</td>
													<td>
														<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=2&point=160&width=90&height=24&jump=4" />

													</td>
													<td>

														<a style="cursor: pointer;" onclick="javascript:changeCode();">重刷</a>
													</td>

												</tr>
											</table>


										</td>


									</tr>


								</table>





								<input type="hidden" id="modelId" name="modelId" value="101" />

							</form>

							<div class="highlight">

								<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:submitResumeInfo();">
										提交
									</button> </span>
							</div>
						</div>





					</div>
					<!--新闻详情页结束-->

				</div>


			</div>




		</div>

		<!--主体结束-->
		<div class="main_br"></div>
		<!--主体结束-->

		<cms:Include page="include/foot.jsp" />

		<script type="text/javascript">
		
	
		
		

</script>
	</body>
</html>
</cms:Member>
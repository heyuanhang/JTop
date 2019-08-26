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
			
			
			
			
				<script type="text/javascript">
				
				
				
		 
				
         									function upload() {
         								 
         											        $('#gbForm').ajaxSubmit({
														            type: 'post', // 提交方式 get/post
														            url: '${SiteBase}content/multiUpload.do?type=1', // 需要提交的 url
														            data: '',
														           //dataType: 'json',
														            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
														                // 此处可对 data 作相关处理
														                alert(data);
														               $('#mh_ly_zltp').val(data.obj_0.resId);
														               alert('上传成功');
														                 
														              
														            }
														            //$('#gbForm').resetForm(); // 提交后重置表单
														        });
														        return false; // 阻止表单自动提交事件
														  
    
    
													 
											}
													 
										 		</script>
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
								<span style="left:120px; top:30px; font-size:18px; font-weight:bold; padding:10px 15px; color:#06c">系统留言</span>
								<ins>
								
								</ins>
							</div>
							   
						   
							<form id="gbForm" name="gbForm" method="post">
							
							
								<table width="100%" border="0" cellpadding="0" cellspacing="12">
									<tr>
										<td align="right" width="15%">
											<label for="exampleInputEmail1" class="control-label">
												留言类型:
											</label>


										</td>

										<td  style="vertical-align:top">

											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														<input name="mh_lylx" type="radio" value="1" />业务咨询
													</td>

													<td>
														<input name="mh_lylx" type="radio" value="2" />改进建议
													</td>

													<td>
														<input name="mh_lylx" type="radio" value="3" />服务投诉
													</td>

													<td>
														<input name="mh_lylx" type="radio" value="4" />需求确认
													</td>

													<td>
														<input name="mh_lylx" type="radio" value="5" />人员查询
													</td>
												</tr>

											</table>


										</td>


									</tr>

									<tr>
										<td align="right" width="15%">
											<label for="exampleInputEmail1" class="control-label">
												姓名信息:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														<input name="gbMan" id="gbMan" type="text" style="width:245px" class="form-control" value="${Member.memberName}"/>
													</td>
													<td width="1%">

													</td>
													<td>
														<label for="exampleInputEmail1" class="control-label">
															联系电话:
														</label>

													</td>
													<td>
														<input name="mh_ly_lxdh" id="mh_ly_lxdh" type="text" style="width:245px" class="form-control" />
													</td>

												</tr>

											</table>


										</td>


									</tr>


									<tr>
										<td align="right" width="15%">
											<label for="exampleInputEmail1" class="control-label">
												QQ号码:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>

														<input name="mh_ly_qqhm" id="mh_ly_qqhm" type="text" style="width:245px" class="form-control" />
													</td>
													<td width="1%">

													</td>
													<td>
														<label for="exampleInputEmail1" class="control-label">
															电子邮箱:
														</label>

													</td>
													<td>

														<input name="mh_ly_mail" id="mh_ly_mail" type="text" style="width:245px" class="form-control" />
													</td>

												</tr>

											</table>



										</td>


									</tr>

									<tr>
										<td align="right" width="15%">

											<label for="exampleInputEmail1" class="control-label">
												单位地址:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>

														<input name="mh_ly_dwdz" id="mh_ly_dwdz" type="text" style="width:245px" class="form-control" />

													</td>
													<td width="1%">

													</td>
													<td>
														<label for="exampleInputEmail1" class="control-label">
															受理人员:
														</label>

													</td>
													<td>

														<input name="mh_ly_slr" id="mh_ly_slr" type="text" style="width:245px" class="form-control" />

													</td>

												</tr>

											</table>


										</td>


									</tr>


									<tr>
										<td align="right" width="15%">
											<label for="exampleInputEmail1" class="control-label">
												留言标题:
											</label>


										</td>

										<td>


											<input name="gbTitle" id="gbTitle" type="text" style="width:670px" class="form-control" />

										</td>


									</tr>

									<tr>
										<td align="right" width="15%">
											留言内容:

										</td>
										<td>

											<textarea style="width:670px;height:180px" name="gbText" id="gbText" class="form-control"></textarea>

										</td>


									</tr>
									
									
									
									<tr>
										<td align="right" width="15%">
											上传文件:

										</td>
										<td>
										 
		  								<input id="File1" name="fileupload"   type="file" value="" />
		  								
		  								<input type="hidden" id="mh_ly_zltp" name="mh_ly_zltp"   />
		  									
						      			 <input id="btnImportOK" type="button" value="上传" onclick="javascript:upload();"/>
						   
										
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





								<input type="hidden" id="sysConfigFlag" name="sysConfigFlag" value="cs" />

							</form>

							<div class="highlight">

								<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:submitGBInfo();">
										提交留言
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
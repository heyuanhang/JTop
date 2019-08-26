<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>门户演示站 - 本站基于JTopcms构建</title>
		<!--[if IE 7]>
			<link rel="stylesheet" href="css/font-awesome-ie7.css">
			<![endif]-->
		<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
		<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
		<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>
		
	
	</head>


	<body>
		<!--头部开始-->
		<cms:Include page="../include/head.jsp" />
		<!--头部结束-->

		<cms:Member loginMode="true">
		<!--主体开始-->
		<div class="main_box">
			<!--左侧-->
			<div>

				<div>
					<!--详情页开始-->
					<div>
						<h1 class="ep-h1 bigtitle">
							 
						</h1>

						<!--登录-->
						<div class="bs-example">
							<div class="tie-titlebar">
								<span style="left:120px; top:30px; font-size:18px; font-weight:bold; padding:10px 15px; color:#06c">会员主页</span>
								<ins>

								</ins>
							</div>
							<form id="memForm" name="memForm" method="post">
							
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
								<tr>
									<td>
									 <cms:Include page="../include/member_left.jsp"></cms:Include>
									</td>
									
									<td>
									
									
									<!-- 右边开始 -->
									
									<table width="100%" border="0" cellpadding="0" cellspacing="12">


									<tr>
										<td align="right" width="15%">
											<label for="exampleInputEmail1" class="control-label">
												会员名:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input readonly name="memberName" id="memberName" value="${Member.memberName}" type="text" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														
													</td>

												</tr>

											</table>


										</td>


									</tr>

									

									<tr>
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												电子邮件:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="email" id="email" value="${Member.email}" type="text" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														可用的电子邮件，可用于找回密码等功能
													</td>

												</tr>

											</table>


										</td>


									</tr>


									<tr>
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												手机:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="phoneNumber" id="phoneNumber" value="${Member.phoneNumber}" type="text" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														您的手机号码，可用于接受系统短信
													</td>

												</tr>

											</table>


										</td>


									</tr>


									<tr>
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												真实姓名:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="trueName" id="trueName"  value="${Member.trueName}" type="text" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														真实姓名为中文和字母
													</td>

												</tr>

											</table>


										</td>


									</tr>
									
									
									<tr>
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												工作单位:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="mh_mem_gzdw" id="mh_mem_gzdw" value="${Member.mh_mem_gzdw}" type="text" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														扩展会员模型字段应用 - 工作单位
													</td>

												</tr>

											</table>


										</td>


									</tr>
									
									
									
									<tr>
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												所在部门:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="mh_mem_bm" id="mh_mem_bm" value="${Member.mh_mem_bm}"type="text" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														扩展会员模型字段应用 - 所在部门
													</td>

												</tr>

											</table>


										</td>


									</tr>
									
									<tr>
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												学历:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<select id="mh_mem_xl" name="mh_mem_xl">
															<option value="-1">
																----------------- 请选择您的学历 ------------------
															
															</option>
															
															<option value="1">
															
																博士
															</option>
															
															<option value="2">
																硕士
															
															</option>
															
															<option value="3">
															    本科
															
															</option>
															
															<option value="4">
																大专
															
															</option>
															
															<option value="5">
																高中
															
															</option>
														
														
														</select>
													</td>

													<td width="7%">


													</td>
													<td>
														扩展会员模型字段应用 - 学历
													</td>

												</tr>

											</table>


										</td>


									</tr>
									

									<tr>
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												自我简介:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<textarea style="width:265px;height:100px" id="mh_mem_zwjs" name="mh_mem_zwjs" class="form-control">${Member.mh_mem_zwjs}</textarea>
													</td>

													<td width="7%">


													</td>
													<td>
														扩展会员模型字段应用 - 自我简介
													</td>

												</tr>

											</table>


										</td>


									</tr>
									


								

									<tr>
										<td align="right" width="15%">
											验证码:


										</td>

										<td>
											<table width="35%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>

														<input name="sysCheckCode" id="sysCheckCode" type="text" class="form-control" style="width:80px" maxlength="4" />

													</td>
													<td>
														<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4" />

													</td>
													<td>

														<a style="cursor: pointer;" onclick="javascript:changeCode();">重刷</a>
													</td>

												</tr>
											</table>


										</td>


									</tr>


									</table>
									
									
									<!-- 右边结束 -->
									
									</td>
								
								</tr>
							
							
							</table>
							
							
								





								<input type="hidden" id="sysConfigFlag" name="sysConfigFlag" value="mh_ly" />

							</form>

							<div class="highlight">

								<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:editMember();">
										确认
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

		<cms:Include page="../include/foot.jsp" />

		<script>
		
		initSelect('mh_mem_xl','${Member.mh_mem_xl}');
		
function editMember()
{ 
     var a =$('#memberName').val(); 
  
  if(a.length<3||a.lengt>15){
	    alert('用户名长度在6-15个字符之间');
        return false;
	
	}
	
  if (/[\u4E00-\u9FA5]/i.test(a)) {
    
    
     alert('用户名不能为中文');
    return flase;
}
  
   
  
       

	var url = "${SiteBase}member/editMemberBasic.do";
    var postData = encodeURI($("#memForm").serialize());
    
    $.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
   		dataType:'json',
       	success: function(msg)
        {        
             
            if('1' == msg)
			{
					 alert('编辑会员资料成功!');
					
					 window.location.reload();
					//window.location.href = '${SiteBase}member/reg_phone.jsp';

			}
			else if('-1' == msg)
			{		 
					 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4&rand='+Math.random());
		     
					 alert('请输入正确的验证码');
		     		 
					 
			}
			else if('-2' == msg)
			{		 
					 
		     		 alert('会员已不存在!');
					 
			}
			 
			else  
			{		 
					
					 alert('编辑会员资料出现异常！');
			}
        }
     });	
    
    
    
    
	// var x = document.getElementById('memberRegForm');
	// x.action= url;
	// x.submit();
	
}

 function handler()
 {
      window.location.href = '${SiteBase}member/reg_phone.jsp';
 }
 
function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4?rand='+Math.random());

}
</script>




	</body>
</html>
</cms:Member>

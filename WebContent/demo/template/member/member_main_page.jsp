<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<title>XP门户演示站 - 本站基于JTopcms构建</title>
		
		<link href="${ResBase}member/css/style.css" rel="stylesheet" type="text/css">
		
	</head>
	
	<cms:Member loginMode="true" jumpPage="member/member_login.jsp"/>

	<body>
		<div class="header">
			<!--头部开始-->
			<cms:Include page="common/member_head.jsp" />
			<!--头部结束-->

			<cms:Member loginMode="true">
		</div>

		<div class="container">

			<!--左边开始-->
			<cms:Include page="common/member_left.jsp" />
			<!--左边开始-->


			<div class="my_myresume_r">


				<div class="switchBox" id="switchBox">
					<h1 class="new_section">
						个人资料
					</h1>
					<div class="eidt-password">
						<form id="memForm" name="memForm" method="post">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="27%" align="right">
										会员名
									</td>
									<td>
										${Member.memberName}
										<input name="memberName" id="memberName" value="${Member.memberName}" type="hidden" class="from-input" />
									</td>
								</tr>
								
								<tr>
									<td align="right">
										所属会员组
									</td>
									<td>
										<input readonly value="<cms:MemberRole userId="${Member.memberId}">${Role.roleName},  </cms:MemberRole>" type="text" class="from-input" />
									</td>
								</tr>
								
								  
     
								<tr>
									<td align="right">
										电子邮件
									</td>
									<td>
										<input name="email" id="email" value="${Member.email}" type="text" class="from-input" />
									</td>
								</tr>
								<tr>
									<td align="right">
										手机号码
									</td>
									<td>
										<input name="phoneNumber" id="phoneNumber" value="${Member.phoneNumber}" type="text" class="from-input" />
									</td>
								</tr>
								<tr>
									<td align="right">
										真实姓名
									</td>
									<td>
										<input name="trueName" id="trueName" value="${Member.trueName}" type="text" class="from-input" />
									</td>
								</tr>
								<tr>
									<td align="right">
										工作单位(扩展)
									</td>
									<td>
										<input name="mh_mem_gzdw" id="mh_mem_gzdw" value="${Member.mh_mem_gzdw}" type="text" class="from-input" />
									</td>
								</tr>
								<tr>
									<td align="right">
										所在部门(扩展)
									</td>
									<td>
										<input name="mh_mem_bm" id="mh_mem_bm" value="${Member.mh_mem_bm}" type="text" class="from-input" />
									</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										学历(扩展)
									</td>
									<td>
										<select id="mh_mem_xl" name="mh_mem_xl">
											<option value="-1">
												----------------------- 请选择您的学历 ------------------------

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
								</tr>
								<tr>
									<td align="right" valign="top">
										自我介绍(扩展)
									</td>
									<td>
										<textarea style="width:300px;height:100px" id="mh_mem_zwjs" name="mh_mem_zwjs" class="from-textarea">${Member.mh_mem_zwjs}</textarea>

									</td>
								</tr>
								
								<tr>
									<td align="right">
										验证码
									</td>
									<td>
									<table width="73%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="22">

														<input name="sysCheckCode" id="sysCheckCode" type="text" class="from-input" style="width:80px" maxlength="4" />
									

													</td>
													<td width="15%">
														<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8" />

													</td>
													<td width="15%">

														<a style="cursor: pointer;" onclick="javascript:changeCode();">重刷</a>
													</td>

												</tr>
											</table>
										 
									</td>
								</tr>
								
								<tr>
									<td align="right">
										&nbsp;
									</td>
									<td>
										<input type="button" name="button" id="button" value="保存修改" class="btn btn2 fl btn-primary" onclick="javascript:editMember();"/>
									</td>
								</tr>
							</table>
						</form>
					</div>


				</div>
			</div>

		</div>
		<div class="footer">
			© 2013 jtopcms.com All Rights Reserved
		</div>
		 
		<script type="text/javascript">
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
					 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8&rand='+Math.random());
		     
					 alert('请输入正确的验证码');
		     		 
					 
			}
			else if('-2' == msg)
			{		 
					 
		     		 alert('会员已不存在!');
					 
			}
			else if('-3' == msg)
			{		 
					 
		     		 alert('邮箱地址已被注册!');
					 
			}
			
			 
			
			sysMNChange
			 
			else  
			{		 
					
					 alert('编辑会员资料出现异常！');
			}
        }
     });	
    
    
    
 
	
}

 function handler()
 {
      window.location.href = '${SiteBase}member/reg_phone.jsp';
 }
 
function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=60&width=90&height=32&jump=8&rand='+Math.random());

}
</script>
	</body>
</html>

</cms:Member>

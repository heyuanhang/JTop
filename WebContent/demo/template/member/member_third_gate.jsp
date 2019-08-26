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

		<!--主体开始-->
		<div class="main_box">
			<!--左侧-->
			<div>

				<div>
					<!--新闻详情页开始-->
					<div>
						<h1 class="ep-h1 bigtitle">
							 
						</h1>

						<!--注册-->
						<div class="bs-example">
							<div class="tie-titlebar">
								<span style="left:120px; top:30px; font-size:18px; font-weight:bold; padding:10px 15px; color:#06c">快捷注册</span>
								<ins>

								</ins>
							</div>
							<form id="memForm" name="memForm" method="post">
								<table width="100%" border="0" cellpadding="0" cellspacing="12">
									
									
									<tr>
										<td align="right" width="18%">
											<label for="exampleInputEmail1" class="control-label">
												 <img id="thirdHeadImg" />
												 
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr style="vertical-align:middle">
													<td width="19%" >
														   <b><span id="showName">sdsd</span></b>
														   <script type="text/javascript">
														  
														   
 
											 					 //会员状态
											  					var url = '${SiteBase}member/memberStatus.do?thirdUserInfo=true';
											 		
														 		$.ajax({
														      		type: "POST",
														       		url: url,
														       		data:'',
														   			dataType:'json',
														       		success: function(msg)
														            {      
														            	 
														            	if(msg != '')
														            	{
														            		 
														            	 	if(mem.ret == '0')
														            	 	{
														            		 
														            		 
														            			$('#thirdHeadImg').attr('src',mem.figureurl_1);
														            			$('#showName').html(mem.nickname+"  (QQ用户名称)");
														            		
														            		}
														            		else if(mem.id != null && mem.id != '')
														            		{
														            		
														            			$('#thirdHeadImg').attr('src',mem.profile_image_url);
														            			$('#showName').html(mem.name+"  (微博用户名称)");
														            		}
														            	 
														            		
														            		
														            	}
														            	 
														               
														               
														                
														            }
														     	});	
														     	
												
											  
											  
											  
											  				</script>
													</td>
													
													<td width="19%" >
														  
													</td>

													<td width="7%">


													</td>
													<td >
													 
													</td>

												</tr>

											</table>


										</td>


									</tr>
									
									
									
									<tr>
										<td align="right" width="18%">
											<label for="exampleInputEmail1" class="control-label">
												注册方式:
											</label>
											 

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr style="vertical-align:middle">
													<td width="19%" >
														  <input type="radio" name="regAct" value="1" onclick="javascript:change();" checked/>创建新的帐号  
													
													</td>
													
													<td width="19%" >
														 <input type="radio" name="regAct" value="2" onclick="javascript:change();"/>已拥有本站帐号
											
													</td>

													<td width="7%">


													</td>
													<td >
														如果您已注册过，可以选择关联已存在的帐号
													</td>

												</tr>

											</table>


										</td>


									</tr>
									

									<tr>
										<td align="right" width="23%">
											<label for="exampleInputEmail1" class="control-label">
												会员名称:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="memberName" id="memberName" type="text" style="width:265px" class="form-control" value=""/>
													</td>

													<td width="7%">

 
													</td>
													
													
													<td>
														会员名长度在6-15个字符之间,不可输入中文
													</td>

												</tr>

											</table>


										</td>


									</tr>

									

									<tr id="mailTr">
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												电子邮件:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="email" id="email" type="text" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														输入可用的电子邮件，可用于找回密码等功能
													</td>

												</tr>

											</table>


										</td>


									</tr>


									<tr id="pwTr">
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												密码:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="password" id="password" type="password" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														密码长度由6-15个字母和数字以及下划线组成
													</td>

												</tr>

											</table>


										</td>


									</tr>
									
									<tr id="cpwTr">
										<td align="right" width="22%">
											<label for="exampleInputEmail1" class="control-label">
												重复密码:
											</label>

										</td>

										<td>
											<table width="100%" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="30%">
														<input name="checkPassword" id="checkPassword" type="password" style="width:265px" class="form-control" />
													</td>

													<td width="7%">


													</td>
													<td>
														请重复输入密码，以保证您的密码设置正确
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
											<table width="30%" border="0" cellpadding="0" cellspacing="0">
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





								<input type="hidden" id="sysConfigFlag" name="sysConfigFlag" value="mh_ly" />

							</form>

							<div class="highlight">

								<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:regMember();">
										确认注册
									</button> </span>
							</div>
						</div>





					</div>
					<!--详情页结束-->

				</div>


			</div>




		</div>

		<!--主体结束-->
		<div class="main_br"></div>
		<!--主体结束-->

		<cms:Include page="../include/foot.jsp" />

		<script>
function regMember()
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
  
   var pwd =  $('#password').val(); 
     if(pwd.length<6||pwd.lengt>16){
          alert('密码长度在6-15个字符之间');
        return false;
  }
    if(!(/^[a-zA-Z0-9]+$/.test(pwd) && /(^(.*\d+.*[a-zA-Z]+.*)$)|(^(.*[a-zA-Z]+.*\d+.*)$)/.test(pwd))){
         alert('密码必须是字母和数字的组合');
        return false;
     }
  
    var mode = $('[name=regAct]:checked').val();
	
	if(mode == 1)
	{
		var url = "${SiteBase}member/regMember.do?thirdReg=true&firstLogin=true";
	    var postData = encodeURI($("#memberName,#password, #checkPassword, #email,#sysCheckCode").serialize());
	    
	    $.ajax({
	      	type: "POST",
	       	url: url,
	       	data:postData,
	   		dataType:'json',
	       	success: function(msg)
	        {        
	             
	            if('1' == msg)
				{
						 alert('注册成功!');
						
						 window.location.href='${SiteBase}';
						//window.location.href = '${SiteBase}member/reg_phone.jsp';	
				}
				else if('-1' == msg)
				{		 
						 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4&rand='+Math.random());
			     
						 alert('请输入正确的验证码');
				}
				else if('-2' == msg)
				{		 
						 
			     		 alert('此会员名称已被注册!');					 
				}
				else if('-3' == msg)
				{		 						 
			     		 alert('此邮件地址已被注册!');						 
				}
				else if('-4' == msg)
				{		 	     	 
						 alert('两次输入密码不一样！');
				}
	        }
	     });	
    
		 
	}
	else if(mode == 2)
	{
		var url = "${SiteBase}member/relateMember.do?firstLogin=true";
	    var postData = encodeURI($("#memberName,#password,  #email,#sysCheckCode").serialize());
	    
	    $.ajax({
	      	type: "POST",
	       	url: url,
	       	data:postData,
	   		dataType:'json',
	       	success: function(msg)
	        {        
	             
	            if('1' == msg)
				{
						 alert('关联会员 '+$('#memberName').val()+' 成功!');
						
						 window.location.href='${SiteBase}';
						//window.location.href = '${SiteBase}member/reg_phone.jsp';
	
				}
				else if('-1' == msg)
				{		 
						 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=24&jump=4&rand='+Math.random());
			     
						 alert('请输入正确的验证码');
			     		 
						 
				}
				else if('-2' == msg)
				{		 
						 
			     		 alert('用户名或密码错误!');
						 
				}
				else if('-3' == msg)
				{		 
						 
			     		 alert('用户名或密码错误!');
						 
				}
				 
	        }
	     });	
    
		 
	}
    
    

	
    
    
	 
	
}

 function handler()
 {
      window.location.href = '${SiteBase}member/reg_phone.jsp';
 }
 
function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}zcm/authImg.do?id='+Math.random());

}

function change()
{
	var mode = $('[name=regAct]:checked').val();
	
	if(mode == 1)
	{
		$('#cpwTr').show();
		 
	}
	else if(mode == 2)
	{
		$('#cpwTr').hide();
		 
	}


}



</script>




	</body>
</html>


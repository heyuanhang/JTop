<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>门户演示站 - 本站基于JTopcms构建</title>


		<link href="${ResBase}member/css/style.css" rel="stylesheet" type="text/css"></link>
		<link href="${ResBase}member/css/login.css" rel="stylesheet" type="text/css"></link>

		<script type="text/javascript" src="${ResBase}js/jquery-1.7.min.js"></script>
		<script type="text/javascript" src="${ResBase}js/common.js"></script>



	</head>


	<body style="background:#fafafc;">
		<div>
			<div class="blogo">
				<div class="user-box">
					<div class="register-logo">
						<img src="${ResBase}member/images/register-logo.png" />
					</div>
				</div>
			</div>
			<div class="user-box" id="setp_quicklogin">
				<div class="register-form">
					<div class="title-item">
						<ins>
							已有帐号，现在
							<a href="${SiteBase}member/member_login.jsp" id="title-item-a">登录</a>
						</ins>
						<h4 class="title-biggest">
							第三方帐号关联
						</h4>
					</div>
					<div class="regbox step1">
						<form id="memForm" name="memForm" method="post">
							<div class="yskj_bd fl-register">
								<ul class="ul">
									<li>
										<label>
											<img id="thirdHeadImg" />
										</label>
										&nbsp;<b><span id="showName"></span> </b>
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
									</li>
									<li>
										<label>
											注册方式：
										</label>
										  <input type="radio" name="regAct" value="1" onclick="javascript:change();" checked/>创建新的帐号  &nbsp;&nbsp;
										   <input type="radio" name="regAct" value="2" onclick="javascript:change();"/>已拥有本站帐号
									</li>
									<li>
										<label>
											<span id="memTitle">会员名</span>：
										</label>
										<input name="memberName" id="memberName"  type="text" class="txt1 txt" ></input>
									</li>
									<%--<li id="mailTr">
										<label>
											电子邮箱：
										</label>
										<input name="email" id="email" type="text" class="txt1 txt"  ></input>
									</li>
									--%><li>
										<label>
											登录密码：
										</label>
										<input   name="password" id="password" type="password" class="txt1 txt" placeholder="创建密码"></input>
									</li>
									<li id="cpwTr">
										<label>
											确认密码：
										</label>
										<input name="checkPassword" id="checkPassword" type="password" class="txt1 txt" placeholder="确认密码"></input>
									</li>

									<li id="captchas1" class="positon-r">
										<label>
											验证码：
										</label>
										<input name="sysCheckCode" id="sysCheckCode" class="txt1 txt" placeholder="验证码" style=" width:130px;" />
										<a href="javascript:changeCode();" class="yzm-img"><img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=1&point=100&width=90&height=30&jump=4" /> </a>
									</li>
									<li class="reg">
										<div style="width:140px; height:40px;" class="fl"></div>
										<input type="button" name="dosubmit" value="注册" class="btn btn4 fl btn-primary" onclick="javascript:regMember();"></input>
									</li>
								</ul>

							</div>

							<div class="thirdlogin-fr">
								<div class="m-box-register">
									<h4>
										第三方帐号接入说明?
									</h4>
									<ul>
										<li>
											<h4>
												感谢您注册本站! 若您已在本站注册过，可选择关联已有帐号， 也可以选择在本站注册一个全新的帐户。
											</h4>
										</li>

									</ul>
									<br />
									<br />
									<br />
									<ul>
										<li id="sinal">
											<a href="${SiteBase}member/thirdLoginReq.do?flag=weibo">微博账号登录</a>
										</li>
										<li id="qql">
											<a href="${SiteBase}member/thirdLoginReq.do?flag=qq">QQ账号登录</a>
										</li>
									</ul>
								</div>
							</div>
						</form>
					</div>

				</div>
			</div>
			<!--reg_setp end-->
	</body>
</html>

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
						 $('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=100&height=24&jump=4&rand='+Math.random());
			     
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
						
						 
						window.location.href = '${SiteBase}member/member_main_page.jsp';
	
				}
				else if('-1' == msg)
				{		 
						  
						 alert('请输入正确的验证码');
						 
						 changeCode();
			     		 
						 
				}
				else if('-2' == msg)
				{		 
						 
			     		 alert('用户名或密码错误!');
			     		 
			     		 changeCode();
						 
				}
				else if('-3' == msg)
				{		 
						 
			     		 alert('用户名或密码错误!');
			     		 
			     		 changeCode();
						 
				}
				 
	        }
	     });	
    
		 
	}
    
    

	
    
    
	 
	
}

 
 

function change()
{
	var mode = $('[name=regAct]:checked').val();
	
	if(mode == 1)
	{
		$('#cpwTr').show();
		$('#mailTr').show();
		
		$('#memTitle').html('会员名');
	}
	else if(mode == 2)
	{
		$('#cpwTr').hide();
		
		$('#mailTr').hide();
		 
		$('#memTitle').html('会员名/邮箱');
		 
	}


}
 
function changeCode()
{
	$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=1&point=100&width=100&height=30&jump=4?rand='+Math.random());

}
</script>


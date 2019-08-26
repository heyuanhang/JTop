<%@ page contentType="text/html; charset=gbk" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<div class="Black10"></div>



</div>
<div class="siteBar">
<div class="clearfix wrap">
<article>
<h3><a href="${SiteBase}aboutus.jsp#jump07">联系我们</a></h3>
<ul class="cantactUs">
<li class="icon_tel"><!--客服电话<br />--><b>400-646-0900</b> 8:30—21:00</li>
<!--<li class="icon_mail">客服邮箱<br />info@zcmnet.com</li>
<li class="icon_address">公司地址<br />北京市丰台区马家堡东路远洋自然<br />新天地602</li>-->
</ul>

<!--<h3>关注我们</h3>-->
<ul class="attentionUs clearfix">
<li>
<span class="weixin">微信<br />公众号:昂道</span>
<img src="images2014/qrcode_weixin.gif" width="80" height="80" alt="招财猫微信" />
</li>
<li>
<a href="http://weibo.com/zcmnet/" class="weibo">微博<br /><span class="btn_guanzhu"><b>+</b> 关注</span></a>
<a href="http://weibo.com/zcmnet/"><img src="images2014/qrcode_weibo.gif" width="80" height="80" alt="招财猫新浪微博" /></a>
</li>
</ul>
</article>

<article>
<h3>　网站导航</h3>
<ul class="navList clearfix">
<li>
<a href="${SiteBase}help.jsp" class="bt" target="_self">新手帮助</a>
<a href="${SiteBase}help.jsp#jump02" class="bt" target="_self">我们的优势</a>
<a href="${SiteBase}security.jsp" class="bt" target="_self">安全保障</a>
<a href="${SiteBase}security.jsp#jump05" target="_self">资金安全</a>
<a href="${SiteBase}security.jsp#jump01" target="_self">担保机构</a>
<a href="${SiteBase}security.jsp#jump02" target="_self">风险审查</a>
<a href="${SiteBase}security.jsp#jump03" target="_self">全球征信</a>
<a href="${SiteBase}security.jsp#jump07" target="_self">债权回购</a>
<!--<a href="${SiteBase}security.jsp#jump06" target="_self">银行委贷</a>
<a href="${SiteBase}security.jsp#jump09" target="_self">客户监事会</a>
<a href="${SiteBase}aboutus.jsp" class="bt" target="_self">关于我们</a>
<a href="${SiteBase}aboutus.jsp#jump01" target="_self">平台简介</a>
<a href="${SiteBase}aboutus.jsp#jump06" target="_self">加入我们</a>
<a href="${SiteBase}financing.jsp#rzsq" class="bt" target="_self">融资申请</a>-->
</li>
<li>
<a href="${SiteBase}invest_zr.jsp" class="bt" target="_self">我要投资</a>
<a href="${SiteBase}invest_zr.jsp#zqzrtop" target="_self">投资列表</a>
<a href="${SiteBase}help.jsp#jump06" target="_self">投资流程</a>
<a href="${SiteBase}aboutus.jsp" class="bt" target="_self">关于我们</a>
<!--<a href="#">投资计算器</a>
  <cms:Class flagMode="true" id="tzgbsh">
<a href="${Class.classUrl}">投资分享</a>
	</cms:Class>
<a href="${SiteBase}member/memberLogin.jsp" class="bt" target="_self">账户管理</a>
<a href="${SiteBase}member/security_settings.jsp" target="_self">实名认证</a>
<a href="${SiteBase}member/show_cat.jsp" target="_self">猫币管理</a>
<a href="${SiteBase}member/message.jsp" target="_self">消息管理</a>-->
  <cms:Class flagMode="true" id="wzgg">
<a href="${Class.classUrl}" class="bt" target="_self">网站公告</a>
	</cms:Class>
  <cms:Class flagMode="true" id="hydt">
<a href="${Class.classUrl}" target="_self">行业动态</a>
	</cms:Class>
  <cms:Class flagMode="true" id="mtbd">
<a href="${Class.classUrl}" target="_self">媒体报道</a>
    </cms:Class>
  <!--<cms:Class flagMode="true" id="xmgg">
<a href="${Class.classUrl}" target="_self">项目公告</a>
     </cms:Class>-->
    <cms:Class flagMode="true" id="wdzq">
<a href="${Class.classUrl}" target="_self">问答专区</a>
       </cms:Class>
</li>
</ul>
</article>

<article class="two">
<div class="clearfix">
<div class="fl boxReg">
<span class="fr mt20 asty"><input class="checkbox" type="checkbox" checked /> 已同意招财猫<a href="${SiteBase}/member/syxy.jsp">协议</a>及<a href="${SiteBase}/member/ystk.jsp">条款</a></span>
<h3>快速注册</h3>
<ul class="formStyAll formSty02">
<li>
<label for="memberName2">用户名/手机号/邮箱</label>
<input id="memberName2" name="memberName2" type="text" class="text name jq_form_text" value="" />
<div class="tips_form"><i class="arrow"></i>3-15位的英文、数字组合及"_"<br />建议不要使用真实姓名作为用户名！</div>
<i class="icon_right" title="正确">&nbsp;</i>
<div class="tips_form02"><i class="arrow"></i>亲，用户名不能空着哦</div>
</li>
<li>
<label for="password2">密码</label>
<input id="password2"  type="password" class="text password jq_form_text" value="" />
<div class="tips_form"><i class="arrow"></i>请输入6-16个字母数字的组合</div>
<i class="icon_right" title="正确">&nbsp;</i>
<div class="tips_form02"><i class="arrow"></i>亲，密码不符合要求哦</div>
</li>
<li>
<label for="checkPassword2">确认密码</label>
<input id="checkPassword2" name="checkPassword2"  type="password" class="text password jq_form_text" value="" />
<i class="icon_right" title="正确">&nbsp;</i>
<div class="tips_form02"><i class="arrow"></i>两次输入的密码不一样哦</div>
</li>
<li>
<label for="checkCode2">验证码</label>

<input id="checkCode2" type="text" class="text code jq_form_text" value="" /><img class="codeImg"  id="checkCodeImg2"  src="${SiteBase}common/authImg.do?count=4&line=0&point=10&width=90&height=30&jump=4&rand="+Math.random()  onclick="changeCode('2');" title="看不清楚，换一张" />


<i class="icon_right" title="正确">&nbsp;</i>
<div class="tips_form02"><i class="arrow"></i>验证码输入不正确哦</div>
</li>
<li><a  href="javascript:void(0);"      onclick="javascript:regMember('2');"   target="_self"  class="btn_reg02">快速注册<span>送50元</span></a></li>
</ul>
</div>
<form id="memberLoginForm" name="memberLoginForm" method="post">
<div class="fr boxLogin">
<h3>快捷登录</h3>
<ul class="formStyAll formSty02">
<%--<li><a href="#" class="btn_login_weibo">微博登录</a>
　　<a href="#" class="btn_login_qq">QQ登录</a></li>
--%>
<li>
<label class="jq_label">用户名/手机号/邮箱</label>
<input id="memberName" name="memberName"  type="text" class="text name jq_form_text" value="" />
</li>
<li>
<label class="jq_label">密码</label>
<input id="password" name="password" type="password" class="text password jq_form_text" value="" />
</li>
<li>
<label class="jq_label">验证码</label>
<input id="checkCode" name="checkCode" type="text" class="text code jq_form_text" value="" /><img class="codeImg"  id="checkCodeImg5"  src="${SiteBase}common/authImg.do?count=4&line=0&point=10&width=90&height=30&jump=4&rand="+Math.random()  onclick="changeCode('5');" title="看不清楚，换一张" />
</li>

<li class="lab"><a href="javascript:gotoResetPage();" class="fr">忘记密码</a><label><input type="checkbox" class="checkbox" /> 自动登录</label></li>
<li><input type="button" onclick="javascript:login('memberLoginForm');" value="登录" class="btn_login02" /></li>
</ul>
</div>
</form>
</div>
<!--
<div class="formStyAll boxComment formSty02">
<form id="lyForm" name="lyForm" method="post">
<input type="button"  onclick="javascript:doLy();" value="提交" class="btnSty02 fr" />


<textarea class="textArea" id="gbText" name="gbText" onFocus="if(this.value=='留言/意见及建议'){this.value='';this.style.color='#fff'}" onBlur="if(this.value==''){this.value='留言/意见及建议';this.style.color='#686868'}">留言/意见及建议</textarea>

<input type="text" class="text tel" value="手机号/邮箱" id="mobile_emai" name="mobile_emai" onFocus="if(this.value=='手机号/邮箱'){this.value='';this.style.color='#fff'}" onBlur="if(this.value==''){this.value='手机号/邮箱';this.style.color='#686868'}" />

<input type="text" class="text name" id="gbMan" name="gbMan" value="称呼/昵称" onFocus="if(this.value=='称呼/昵称'){this.value='';this.style.color='#fff'}" onBlur="if(this.value==''){this.value='称呼/昵称';this.style.color='#686868'}" />

<input type="hidden" id='sysConfigFlag' name='sysConfigFlag' value='zcmlyjy'/>

<input id="sysCheckCode" name="sysCheckCode" type="text" class="text code jq_form_text" value="" /><img class="codeImg"  id="checkCodeImg9"  src="${SiteBase}common/authImg.do?count=4&line=0&point=10&width=90&height=30&jump=4&rand="+Math.random()  onclick="changeCode('9');" title="看不清楚，换一张" />
</form>

</div>-->
</article>
</div>
</div>


<div class="fixbar">
<a href="http://qiao.baidu.com/v3/?module=default&controller=im&action=index&ucid=7568492&type=n&siteid=5230446" class="nav zixun"><em>在线<br />咨询</em></a>
<a href="http://wpa.qq.com/msgrd?v=3&uin=3012075246&site=qq&menu=yes" class="nav kefu"><em>在线<br />客服</em></a>
<a href="${SiteBase}/help.jsp" class="nav help"><em>新手<br />帮助</em></a>
<dl id="jq_calculator" class="calculatorBox">
<dt class="nav calculator"><em>投资<br />计算器</em></dt>
<dd>
<h6 class="btSty01"><span>投资计算器</span></h6>
<ul class="formStyAll formSty03">
<li>
<label for="jine">投资金额</label>
<input type="text" id="jine" value="" class="text tbje jq_form_text" id="jine"> 元
</li>
<li>
<label for="syl">固定年化收益率（%）</label>
<input type="text" id="syl" value="" class="text syl jq_form_text" id="syl"> %
</li>
<li>
<label for="qixian">投资天数（天）</label>
<input type="text"  id = "qixian" value="" class="text qixian jq_form_text" id="qixian"> 天
</li>
<li>
<input type="button" value="预计收益" class="btnSty07" onclick="getCounter()" /> = <input type="text"  id="sy" value="" class="text yjsy" id=""> 元
</li>
</ul>
</dd>
</dl>

<!--<a href="mailto:info@zcmnet.com" class="nav feedback"><em>邮件<br />留言</em></a>-->

<form id="voteForm" name="voteForm" method="post">




<dl id="jq_feedback" class="feedbackBox">
<dt class="nav feedback"><em>意见<br />反馈</em></dt>
<dd>
<h6 class="btSty01"><span>意见反馈</span></h6>
<ul>

<cms:Survey groupFlag="zcm_hx_dc">


	<cms:if test="${Survey.optionType==5}">
	
		<li>您对我们的建议：
		<br /><textarea id="jtopcms-text-survey-${Survey.surveyId}" name="jtopcms-text-survey-${Survey.surveyId}" rows="3" cols="20"></textarea>
		</li>
	
	</cms:if>
	
	<cms:else>
	
		<li>${Survey.surveyTitle}
		<br /><cms:SurveyOption><label><input name="jtopcms-survey-${Survey.surveyId}" value="${Option.optionId}" type="checkbox"> ${Option.optionText}</label></cms:SurveyOption>
		</li>
	
	</cms:else>
	
	
	

</cms:Survey>


<input type="hidden" id="jtopcms-group-survey-zcm_hx_dc" name="jtopcms-group-survey-zcm_hx_dc" value="zcm_hx_dc" />







<li>
<input name="jtopcms-vote-captcha-zcm_hx_dc" id="jtopcms-vote-captcha-zcm_hx_dc" type="text"    style="height:20px;width:100px;"/><img id="checkCodeImg61"  src="${SiteBase}common/authImg.do?count=4&line=0&point=10&width=90&height=30&jump=4&rand="  onclick="changeCode('61');" title="看不清楚，换一张" />
<input type="button" value="提交" class="btnStyQa" onclick="javascript:voteIndex2();"/>

</li>
</form>
<script>

 function voteIndex2()
{
	
 


 	var url = '${SiteBase}survey/clientVote.do';
 		
 		$.ajax
 		({
			type:'POST',
			url:url,
			data:encodeURI($("#voteForm").serialize()),
			success:
			function(data, textStatus)
			{
				//alert(data);
				if(data === '0')
				{
					alert('不可重复投票，感谢您的参与!');
				}
				else if(data === '1')
				{
					alert('已成功投票，谢谢您的参与!');
					window.location.href=window.location.href;
					return;
				}
				else if(data === '-1')
				{
					alert('请正确输入验证码!');
				}
				else if(data === '-2')
				{
					alert('投票已经失效!');
				}
				else if(data === '-3')
				{
					alert('投票无效!');
				}
				
				
  			}
  		});
}

</script>

<!--<li>
<input type="submit" value="提交" class="btnSty06" />
</li>-->
</ul>
</dd>
</dl>

<!--
  <a href="javascript:void(0)";  onclick ="gototop()" class="nav gototop" target="_self"><em>返回<br />顶部</em></a>
  <script>
  
 
}
  
    function gototop(){
      
      var url = window.location.href;
      
      if(url.indexOf("#zcmtop") > 0 )
{
    window.location.href =window.location.href;
}else{
      
      window.location.href =window.location.href+'#zcmtop';
      
}
    }
    
  </script>-->
<a href="javascript:scroll(0,0)" class="nav gototop" target="_self"><em>返回<br />顶部</em></a>
</div>

<footer class="footer">
<div class="wrap">
<div class="fr links"><a href="http://webscan.360.cn/index/checkwebsite/url/www.zcmnet.com" class="nav01">360网站安全检测</a><a href="http://www.zcmnet.com/2014122/wdzq/296410.html" class="nav02">昂道招财猫资质认证</a><!--<a href="#" class="nav03">360网站安全检测</a><a href="#" class="nav04">360网站安全检测</a><a href="#" class="nav05">360网站安全检测</a>--></div>
北京昂道网络科技有限公司(zcmnet.com) <a href="http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action">京ICP备14013783号</a>

<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fe27107090909b40153b75b0f7b133208' type='text/javascript'%3E%3C/script%3E"));
</script>

<script  type="text/javascript">
  var _sogou_sa_q = _sogou_sa_q || [];
  _sogou_sa_q.push(['_sid', '236559-243359']);
 (function() {
    var _sogou_sa_protocol = (("https:" == document.location.protocol) ? "https://" : "http://");
    var _sogou_sa_src=_sogou_sa_protocol+"hermes.sogou.com/sa.js%3Fsid%3D236559-243359";
    document.write(unescape("%3Cscript src='" + _sogou_sa_src + "' type='text/javascript'%3E%3C/script%3E"));
    })();
</script>

<script type="text/javascript" src="//stat.tf.360.cn/search/c.js?u=1350301699" charset="utf-8"></script>
<script type="text/javascript">
    (function() {
        var obj = document.createElement('script');
        obj.src = '//s.va.cn/va.js?id=17455';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(obj, s);
    })();
</script>


<script type="text/javascript" src="http://stat.e.tf.360.cn/search/c.js?u=1350301699"></script>
<script type="text/javascript">
var _e360_uid=1350301699;
(function () {
try {
var d = document;
var at = d.createElement('script'); at.type = 'text/javascript'; at.async = true;
at.src = ('https:' == document.location.protocol ? 'https:' : 'http:') + '//t3.adsage.com/trc/atac/conv_q.js?id=' + _e360_uid;
var s = d.getElementsByTagName('script')[0]; s.parentNode.insertBefore(at, s);
} catch (e) { } 
})();
</script>


<!--<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_5924368'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s19.cnzz.com/stat.php%3Fid%3D5924368' type='text/javascript'%3E%3C/script%3E"));</script>-->
</div>
</footer>


<script>
function getCounter(){

var reg = /^[1-9]*[1-9][0-9]*$/;
var reg2= /^([\d]+|([\d]+[.]?|[\d]+[.]?[\d]+))$/;

 var a =$('#jine').val();
 var b =$('#syl').val();
 var c = $('#qixian').val();

 if(!reg.test(a)){
			
			 alert("金额请输入数字");
			return false;
} else if(!reg2.test(b)){
       alert("收益率请输入数字");
			return false;

	}else if(!reg.test(c)){
       alert("投资天数请输入整数");
			return false;

	}
 else{
  //var d = (a*b*c)/(365*100);
   
   var d =((a*b)/(365*100)).toFixed(2);
   var e = (d*c).toFixed(2);
  
  $('#sy').val(e);
}

}

function regMember(state)
{ 
   var memberName =$('#memberName'+state).val(); 
  
  if(memberName.length<3||memberName.lengt>25){
	   layer.alert('用户名长度在6-25个字符之间',8,'提示');
        return false;
	
	}
	
  if (/[\u4E00-\u9FA5]/i.test(memberName)) {
    
    
    layer.alert('用户名不能为中文',8,'提示');
    return flase;
}

						
  
   var password =  $('#password'+state).val(); 
     if(password.length<6||password.lengt>16){
         layer.alert('密码长度在6-15个字符之间',8,'提示');
        return false;
  }
    if(!(/^[a-zA-Z0-9]+$/.test(password) && /(^(.*\d+.*[a-zA-Z]+.*)$)|(^(.*[a-zA-Z]+.*\d+.*)$)/.test(password))){
     
         layer.alert('密码必须是数字和英文组合',8,'提示'); 
      return false;
     }
  
    var checkPassword = $('#checkPassword'+state).val();
     if(password!=checkPassword){
        layer.alert('两次密码输入不一致',8,'提示');
        return false;
     }
  var checkCode =  $('#checkCode'+state).val(); 

	var url = "${SiteBase}zcm/memberInfoReg.do";
    //var postData = $("#memberName"+state, "#password"+state, "#checkPassword"+state,"#checkCode"+state).serialize();
    
     var postData ="memberName="+memberName+"&password="+password+"&checkPassword="+checkPassword+"&checkCode="+checkCode+"&emailVal= "+"&phone= "+"&checkPCode=";

   
    
    $.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
   		dataType:'json',
       	success: function(msg)
        {        
           
            if('successRegLogin' == msg)
			{
					 layer.alert('注册成功!',9,'提示',handler);
					
					//window.location.href = '${SiteBase}member/reg_phone.jsp';

			}
			else 
			{		 
					 
		     		$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=0&point=10&width=90&height=28&jump=4&rand='+Math.random());
		     
					 layer.alert(msg,8,'提示');
			}
        }
     });	
    
    
    
	// var x = document.getElementById('memberRegForm');
	// x.action= url;
	// x.submit();
	
}

function doLy()
			{
				var phone = /^0?(13[0-9]|15[012356789]|18[024536789]|14[57])[0-9]{8}$/;
				if($("#gbText").val().trim()=="")
				{
					alert('请输入留言内容。');
					return ;
				}

			
			
				var url = '${SiteBase}guestbook/clientAddGb.do';
			    var postData = encodeURI($("#lyForm").serialize());
			  //  alert(postData);
			    $.ajax({
			      	type: "POST",
			       	url: url,
			       	data:postData,
			   		dataType:'json',
			       	success: function(msg)
			        {        
			          //  alert(msg);
			            if('1' == msg)
			            {
							alert('尊敬的朋友，您的建议已成功提交，感谢您对招财猫团队的信任!');
							window.location.reload();
			            }
			            else if('-3' == msg)
			            {
							alert('验证码无效!');			            	
			            }
						else 
			            {
							alert('您的建议提交失败!');			            	
			            }
						
			        }
			     });	
			
			
			
}

 function handler()
 {
      window.location.href = '${SiteBase}member/reg_phone.jsp';
 }
 
 
function changeCode(type)
{
	$('#checkCodeImg'+type).attr('src','${SiteBase}common/authImg.do?count=4&line=0&point=10&width=90&height=28&jump=4&rand='+Math.random());

}


function login(formName)
{
	

	var url = "${SiteBase}zcm/memberLogin.do";
    
    var postData = $("#"+formName).serialize();
    
    $.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
   		dataType:'json',
       	success: function(msg)
        {        
            //alert(msg);
            if('successLogin' == msg)
            {
            	//alert('登陆成功!');
				window.location.href = '${SiteBase}member/main.jsp';
            }
            else if('errorCheckCode' == msg)
			{				
				alert('错误的验证码!');
				$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=0&point=10&width=90&height=28&jump=4&rand='+Math.random());
			}
			else if('failLogin' == msg)
			{				
				alert('用户名或密码错误!');
				$('#checkCodeImg').attr('src','${SiteBase}common/authImg.do?count=4&line=0&point=10&width=90&height=28&jump=4&rand='+Math.random());
			}
			
			
        }
     });	

    
    
	// var x = document.getElementById('memberLoginForm');
	// x.action= url;
	// x.submit();
	
}


function gotoResetPage()
{

	window.location.href = '${SiteBase}member/send_reset_pw_mail.jsp';
}



function gotoRegPage()
{

	window.location.href = '${SiteBase}member/reg.jsp';
}

</script>

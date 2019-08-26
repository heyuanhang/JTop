basePath = '';

//扩展String的一些方法

String.prototype.trim=function()
{
	return this.replace(/(^\s*)|(\s*$)/g,'');
}

String.prototype.replaceAll=function(s1,s2)
{    
	return this.replace(new RegExp(s1,"gm"),s2);    
} 

function replaceAll(targetStr, oldStr, newStr)
{
	var endStr = '';
	var index = targetStr.indexOf( oldStr );
	
	while( index != -1 )
	{
     	endStr = targetStr.replace(oldStr, newStr); 
     	index = targetStr.indexOf(index, oldStr );
	}	

	return (endStr == '')?targetStr:endStr;
}

String.prototype.startWith=function(str){     
  var reg=new RegExp("^"+str);     
  //alert(reg.test(this));
  return reg.test(this);        
}  
 
String.prototype.endWith=function(str){     
  var reg=new RegExp(str+"$");     
  return reg.test(this);        
}


//StringBuffer
var StringBuffer = function(){ 
	this._strings_ = new Array(); 
} 

StringBuffer.prototype.append = function(str){ 
	this._strings_ .push(str); 
} 

StringBuffer.prototype.toString = function(sc){ 
	return this._strings_.join(sc);
} 

//获取select已经选中的文本html
function getSelectedText(name)
{
	var obj=document.getElementById(name);
	for(i=0;i<obj.length;i++)
	{
   		if(obj[i].selected == true)
   		{
    		return obj[i].innerHTML;
   		}
	}
}

//初始化radio,针对指定的radio名字，将等于给定value值的选项选中
function initRadio(targetRadioName,initValue)
{
	var radioArray = document.getElementsByName(targetRadioName);  

    for (var i=0; i<radioArray.length; i++)
    {  
        if (radioArray[i].value==initValue) 
        {  
            radioArray[i].checked= true;  
            break;  
        }  
    }  
}


//获取radio选取的值
function getRadioCheckedValue(targetRadioName)
{
	var radioArray = document.getElementsByName(targetRadioName);
	
    for (var i=0; i<radioArray.length; i++)
    {  
        if (radioArray[i].checked == true) 
        {  
           return radioArray[i].value;        
        }  
    }  
    
    return null;
}

//初始化select,针对指定的select ID，将等于给定value值的选项选中
function initSelect(targetSelectId,initValue)
{
	var select = document.getElementById(targetSelectId);
	
    for (var i=0; i<select.length; i++)
    {  
      if (select.options[i].value==initValue) 
      {  
           select.options[i].selected=true;  
           break;  
      }  
    }  
}

//初始化select,针对指定的多个select ID，将等于给定value值的选项选中
function initMupSelect(targetSelectId,initValueArray)
{
	var select = document.getElementById(targetSelectId);
	
    for (var i=0; i<select.length; i++)
    {  
      for(var j=0;j<initValueArray.length;j++ )
      { 	
      	if (select.options[i].value==initValueArray[j]) 
      	{  
           	select.options[i].selected=true;  
      	}  
      }
    }  
}



//勾选所有checkbox
function selectAll(targetCheckName, allObj)
{
   var allCheck = allObj.checked;
   
   var checks = document.getElementsByName(targetCheckName);

   for(var i = 0; i < checks.length; i++)
   {
	 	if('none' == checks[i].style.display)
	 	{
	 		continue;
	 	}
	 	
	   	if(allCheck)
	   	{
	      checks[i].checked=true;
	   	}
	   	else
	   	{
	   	  checks[i].checked=false;
	   	}
	 	
      //targetMap.put(checks[i].value,checks[i].value);
   }
  // targetMap.remove(checkAllIdName);
   
}


//勾选所有checkbox
function selectAllBox(targetCheckName)
{
   
   var checks = document.getElementsByName(targetCheckName);

   for(var i = 0; i < checks.length; i++)
   {
	   	
	      checks[i].checked=true;
	   	
      //targetMap.put(checks[i].value,checks[i].value);
   }
  // targetMap.remove(checkAllIdName);
   
}


/**
 * 替换当前URL中的参数，若当前URL无此参数，则加上新的参数:targetNameValueStr (a=1&b=2)
 */
function replaceUrlParam(loc,targetNameValueStr)
{
	var targetName;
	var value;
	
  var splitCharPos;
	
	var tmpKey;
	var tmpVal;
	
	var params = loc.search;
	
	targetNameValueStr = encodeURI(targetNameValueStr);

	if(targetNameValueStr == null || targetNameValueStr == '')
	{
		loc.href=href;
	}

	if(params.indexOf("?")!=-1)//已有参数
	{
		var href = loc.href.substring(0,loc.href.indexOf("?"));
		
		
		var nvArray = targetNameValueStr.split('&');
		for(var j=0; j<nvArray.length; j++)
		{
			splitCharPos = nvArray[j].indexOf('=');
				
		    tmpKey = nvArray[j].substring(0, splitCharPos);
		    tmpVal = nvArray[j].substring((splitCharPos+1), nvArray[j].length );
		 
			targetName = tmpKey;
			value = tmpVal;
			
			if(targetName != 'undefined')
			{
				
				 
			if(params.indexOf('&'+targetName+'=')!=-1 || params.indexOf('?'+targetName+'=')!=-1)//已存在当前参数
			{
				 //alert(params+'     &'+targetName+'=');
				var searchStrArray = params.substring(1,params.length).split('&');
		 		var targetP;
		 		var targetV;
		 		for(var i=0;i<searchStrArray.length;i++)
		 		{
		 			splitCharPos = searchStrArray[i].indexOf('=');
				
		            tmpKey = searchStrArray[i].substring(0, splitCharPos);
		            tmpVal = searchStrArray[i].substring((splitCharPos+1), searchStrArray[i].length );
			 		
			 		if(tmpKey==targetName)
			 		{	 			
				 		targetP = tmpKey;
				 		targetV = tmpVal;
				 		break;
			 		}	 	
		 		}
				//alert( "     -------     "+targetP+'='+targetV+'  ----    '+targetName+'='+value);
				if(params.indexOf('?'+targetName+'=')!=-1)
				{
					params =replaceAll(params,'?'+targetP+'='+targetV,'?'+targetName+'='+value);//替换
				}
				else
				{
					params =replaceAll(params,'&'+targetP+'='+targetV,'&'+targetName+'='+value);//替换
				}
				
			//alert(params);
				//loc.href=href+params;
			}
			else//不存在当前参数
			{
				params+="&"+targetName+"="+value;
			}
			}
		}
		//alert(params);
		loc.href=href+params;
		 
	}
	else//没有参数
	{ //alert("?"+targetNameValueStr);
		loc.href+="?"+targetNameValueStr;
	   ;
	}
	
}

//获取radio选取的值
function getRadioCheckedValue(targetRadioName)
{
	var radioArray = document.getElementsByName(targetRadioName);
	
    for (var i=0; i<radioArray.length; i++)
    {  
        if (radioArray[i].checked == true) 
        {  
           return radioArray[i].value;        
        }  
    }  
    
    return null;
}


//获取select已经选中的文本html
function getSelectedText(name)
{
	var obj=document.getElementById(name);
	for(i=0;i<obj.length;i++)
	{
   		if(obj[i].selected == true)
   		{
    		return obj[i].innerHTML;
   		}
	}
}

function replaceAll(targetStr, oldStr, newStr)
{
	var endStr = '';
	var index = targetStr.indexOf( oldStr );
	
	while( index != -1 )
	{
     	endStr = targetStr.replace(oldStr, newStr); 
     	index = targetStr.indexOf(index, oldStr );
	}	

	return (endStr == '')?targetStr:endStr;
}

function changeCode()
{
	$('#checkCodeImg').attr('src',basePath+'common/authImg.do?count=4&line=2&point=160&width=90&height=24&jump=4&ra='+Math.random());

}


function submitGBInfo()
{
	var text =  '';
	
	text =  getRadioCheckedValue('mh_lylx');

    if(text == null)
	{
		alert("请选择一个留言类型！");
		return;
	}
	
	
	text =  document.getElementById('gbMan');
	
    if(text.value.length < 3)
	{
		alert("请输入正确姓名信息！");
		return;
	}
	
	text =  document.getElementById('gbTitle');
	
    if(text.value.length < 10)
	{
		alert("请输入不少10字的留言标题！");
		return;
	}
	
	text =  document.getElementById('gbText');
	
    if(text.value.length < 20)
	{
		alert("您输入的留言文本字数过少！");
		return;
	}
	else if(text.value.length > 2500)
	{
		alert("您输入的留言文本字数超过2500字！");
		return;
	}
	
	text =  document.getElementById('sysCheckCode');
	
    if(text.value.length < 4)
	{
		alert("请输入正确验证码！");
		return;
	}
	
	var url = basePath+'guestbook/clientAddGb.do';
	//必须用encodeURI，因已经编码了=
	var postData =  encodeURI($("#gbForm").serialize()) ;
	 
	 postData = postData.replace(/\+/g, " ");
	 postData = encodeData(postData);
	    
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		async : false,
			       		data:postData,
			   			dataType:'json',
			       		success: function(msg)
			            {     
			            	 	
			            	if('1' == msg)
			            	{		            		
			            		alert('您的留言成功,请等待站点管理员处理!');
			            		
			            		window.location.reload();
			            	}
			            	else if('-1' == msg)
			            	{
			            		alert('系统异常,留言失败!');
			            	}
			            	else if('-2' == msg)
			            	{
			            		alert('留言人姓名不可为空!');
			            	}
			            	else if('-3' == msg)
			            	{
			            		alert('验证码错误!');
			            	}
			            	else if('-4' == msg)
			            	{
			            		alert('留言标题不允许为空!');
			            	}
			            	else if('-5' == msg)
			            	{
			            		alert('只有会员才可留言!');
			            	}
			            	
			            	
			            }
	 });
	 
	 


}

function submitResumeInfo()
{
	var text =  '';
	
	text =  getRadioCheckedValue('jt_mh_jl_gw');

    if(text == null)
	{
		alert("请选择一个投递岗位！");
		return;
	}
	
	
	text =  document.getElementById('jt_jl_xm');
	
    if(text.value.length < 3)
	{
		alert("请输入正确姓名信息！");
		return;
	}
	
	text =  document.getElementById('jt_jl_xl');
	
    if(text.value == -1)
	{
		alert("请选择您的学历！");
		return;
	}
	
	 
	
	text =  document.getElementById('jt_jl_jieshao');
	
    if(text.value.length < 20)
	{
		alert("您输入的个人介绍字数过少！");
		return;
	}
	else if(text.value.length > 2500)
	{
		alert("您输入的个人介绍字数超过2500字！");
		return;
	}
	
	text =  document.getElementById('sysCheckCode');
	
    if(text.value.length < 4)
	{
		alert("请输入正确验证码！");
		return;
	}
	
	var url = basePath+'content/clientAddFormData.do';
	//必须用encodeURI，因已经编码了=
	var postData =  encodeURI($("#gbForm").serialize()) ;

	 postData = postData.replace(/\+/g, " ");
	 postData = encodeData(postData);
	    
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		async : false,
			       		data:postData,
			   			dataType:'json',
			       		success: function(msg)
			            {     
			            	  
			            	if('1' == msg)
			            	{		            		
			            		alert('您的简历投递成功,请等待站点管理员处理!');
			            		
			            		window.location.reload();
			            	}
			            	 else if('-1' == msg)
			            	{
			            		alert('表单元数据不全!');
			            	}
			            	else if('-2' == msg)
			            	{
			            		alert('姓名不可为空!');
			            	}
			            	else if('-3' == msg)
			            	{
			            		alert('验证码错误!');
			            	}
			            	 
			            	else if('-5' == msg)
			            	{
			            		alert('只有会员才可留言!');
			            	}
			            	
			            	
			            }
	 });
	 
	 


}

function digg(cid, val)
{
	 var url = basePath+'comment/digg.do?contentId='+cid+'&digg='+val;
 	
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   			dataType:'json',
			       		success: function(msg)
			            {     
			            	if('1' == msg)
			            	{
			            		  alert('表达意见成功');
			            		  window.location.reload();
			            	}
			            	
			            	else if('-1' == msg)
			            	{
			            		  alert("您已经顶踩过这篇内容");
			            	}
			            
			            }
	 });
	 
	 

}

function mood(cid,flag)
{
	var url = basePath+'comment/mood.do?contentId='+cid+'&mood='+flag;
 	
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   			dataType:'json',
			       		success: function(msg)
			            {     
			            	if('1' == msg)
			            	{
			            		  alert('表达心情成功');
			            		  window.location.reload();
			            	}
			            	
			            	else if('-1' == msg)
			            	{
			            		  alert("您已经表达过心情");
			            	}
			            
			            }
	 });
}


function commentContent()
{
	var text =  document.getElementById('commentText');
    if(text.value.length < 10 || '文明上网,请您发言…' == text.value)
	{
		alert("您输入的字数过少！");
		return;
	}
	
	text =  document.getElementById('sysCheckCode');
	
    if(text.value.length < 4)
	{
		alert("请输入验证码！");
		return;
	}
	
	var url = basePath+'comment/clientAddComment.do';
	
	var postData = encodeURI($("#commentForm").serialize());
	
 	 postData = postData.replace(/\+/g, " ");
	 postData = encodeData(postData);
		
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		data:postData,
			   			dataType:'json',
			       		success: function(msg)
			            {     
			            	
			            	
			            	if('1' == msg)
			            	{		            		
			            		alert('您的评论成功,可进入评论页查看!');
			            		
			            		window.location.reload();
			            	}
			            	else if('-1' == msg)
			            	{
			            		alert('验证码错误!');
			            	}
			            	else if('-2' == msg)
			            	{
			            		alert('系统异常,评论失败!');
			            	}
			            	else if('-3' == msg)
			            	{
			            		alert('评论文本少于10字!');
			            	}
			            	else if('-4' == msg)
			            	{
			            		alert('当前内容不允许评论!');
			            	}
			            	else if('-5' == msg)
			            	{
			            		alert('只有会员才可评论!');
			            	}
			            	else if('-6' == msg)
			            	{
			            		alert('留言版已被关闭!');
			            	}
			            	
			            }
	 });
	 
	 


}


function diggComment(cmid, val)
{
	 var url = basePath+'comment/clientAddComment.do?commentId='+cmid+'&digg='+val;
 	
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   			dataType:'json',
			       		success: function(msg)
			            {     
			            	if('1' == msg)
			            	{
			            		  alert('对评论表达意见成功');
			            		  window.location.reload();
			            	}
			            	
			            	else if('-6' == msg)
			            	{
			            		  alert("您已经顶踩过这篇评论");
			            	}
			            
			            }
	 });
	 
	 

}


function voteIndex2()
{
	
  	var postData = encodeURI($("#voteForm").serialize());

	 postData = postData.replace(/\+/g, " ");
	 postData = encodeData(postData);
	 
 	var url = basePath+'survey/clientVote.do';
 		
 		$.ajax
 		({
			type:'POST',
			url:url,
			data:postData ,
			success:
			function(da, textStatus)
			{
				 var data = eval("("+da+")");
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




/////////////会员/////////////////

function loginOut(token)
{ 
	var url = basePath+'member/memberLogin.do?action=LoginOut&'+token ;
 	 
	$.ajax({
  		type: "POST",
   		url: url,
   		data:'',
   			dataType:'json',
       		success: function(msg)
            {      
            
            	if(msg == '1')
	        	{
	        		alert("退出登录！");
	        		
	        		window.location.href = basePath;

	        	}
	        	else
	        	{
	        		alert("退出失败！");
	        	}
        	 
           
           
            
        }
 	});	

}




////////////////////////////////记住用户///////////////////////////////

 
     
    function setLastLoginSuccessUser(usr) 
    {
        var id = "jtopcms_member_login_name";
        var expdate = new Date();
        //当前时间加上一个月的时间
        expdate.setTime(expdate.getTime() + 30 * (24 * 60 * 60 * 1000));
        setCookie(id, usr, expdate);
    }
   
    //取Cookie的值
     
    function getCookie(name) 
    {
        var arg = name + "=";
        var alen = arg.length;
        var clen = document.cookie.length;
        var i = 0;
        while (i < clen) 
        {
            var j = i + alen;
  
            if (document.cookie.substring(i, j) == arg) return getCookieVal(j);
            i = document.cookie.indexOf(" ", i) + 1;
            if (i == 0) break;
        }
        return null;
    }
 
     
    function getCookieVal(offset) 
    {
        var endstr = document.cookie.indexOf(";", offset);
        if (endstr == -1) endstr = document.cookie.length;
        return unescape(document.cookie.substring(offset, endstr));
    }
    //写入到Cookie
     
    function setCookie(name, value, expires) 
    {
        var argv = setCookie.arguments;
 
        var argc = setCookie.arguments.length;
        var expires = (argc > 2) ? argv[2] : null;
        var path = (argc > 3) ? argv[3] : null;
        var domain = (argc > 4) ? argv[4] : null;
        var secure = (argc > 5) ? argv[5] : false;
        document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
    }
     
    function resetCookie() 
    {
       
        var expdate = new Date();
        setCookie('jtopcms_member_login_name', null, expdate);
    }



/**
 * 通用的自定义模型字段验证规则，分别绑定 focus propertychange blur 三事件
 */
 

function validate(filedSign,notNull,reg,msg)
{				
		$("#"+filedSign).bind('focus', function() 
		{
		   // $( 'div.'+filedSign+'_jtop_ui_tips_class' ).remove();
			
			var target = $("#"+filedSign).val();
			
			if(target != '')
			{			
				if(reg != null && reg !='' )
				{
					if(!eval('regexTest("'+$("#"+filedSign).val()+'",'+reg+')'))
	        		{
	             		showTips(filedSign,msg);
	        		}      
				}           						
			}
			else if(1 == notNull)
			{
			    showTips(filedSign,'不可为空');
			}
		});
		
		
		
		$("#"+filedSign).bind('keyup', function() 
		{
		  
		   $( 'div.'+filedSign+'_jtop_ui_tips_class' ).remove();
			
			var target = $("#"+filedSign).val();
			if(target != '')
			{
				if(reg != null && reg !='' )
				{
					if(!eval('regexTest("'+$("#"+filedSign).val()+'",'+reg+')'))
	        		{
	             		showTips(filedSign,msg);
	        		} 
				}                						
			}
			else if(1 == notNull)
			{
			    showTips(filedSign,'不可为空');
			}
		   
		});

		$("#"+filedSign).bind('blur', function() 
		{
			$( 'div.'+filedSign+'_jtop_ui_tips_class' ).remove();
		});
}


/**
 * 通用的自定义模型字段验证规则,提交验证规则,若不通过,将所有错误信息提示
 */
function submitValidate(filedSign,notNull,reg,msg)
{				
	    var hasError = false;
		var target = $("#"+filedSign).val();

		if(target == '' && 1 == notNull)
		{
				showTips(filedSign,'不可为空');
				hasError = true;
	    }
	    else
	    {
	    	if(reg != null && reg !='' )
	    	{
		    	if(!eval('regexTest("'+$("#"+filedSign).val()+'",'+reg+')'))
		    	{
		    		showTips(filedSign,msg);
		    		hasError = true;
		    	}
	    	}
	    }
	    
	    return hasError;
}

function regexTest(target,patrn)
{
	if (!patrn.exec(target)) 
	{
		return false;
	}
	return true;
}


function showTips( id,tips )
{
	var offset = $('#'+id).offset();
	if(offset == null)
	{
		return;
	}
	
	var y = offset.top-9;
				 
	var x = $('#'+id).width() +18+ offset.left;
    var tipsDiv = "<div class='"+id+"_jtop_ui_tips_class'><div class='onError'><div class='onError-bottom'><div class='onError-right'><div class='onError-left'><p class='onError-top-right'>"+tips+"</p></div></div></div></div></div>";

    $( 'body' ).append( tipsDiv ); 
	$( '.'+id+"_jtop_ui_tips_class" ).css(
	{ 
		'top' : y + 'px', 
		'left' : x + 'px', 
		'position' : 'absolute'

	}).show(); 

//setTimeout( function(){$( 'div._jtop_ui_tips_class' ).fadeOut();}, ( time * 1000 ) ); 
} 

function hideTips(id)
{
	$( '.'+id+"_jtop_ui_tips_class" ).hide(); 
	
}


/////////////////////////////////////////////正则表达式////////////////////////////////////////////////
//校验是否全由数字组成

function isDigit(s)
{
var patrn=/^[0-9]{1,20}$/;
if (!patrn.exec(s)) return false
return true
}

//校验登录名：只能输入5-20个以字母开头、可带数字、“_”、“.”的字串

function isRegisterUserName(s)  
{  
var patrn=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/;  
if (!patrn.exec(s)) return false
return true
}


//function isSearch(s)  
//{  
//var patrn=/^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]{0,19}$/;  
//if (!patrn.exec(s)) return false
//return true
//}  


//校验用户姓名：只能输入1-30个以字母开头的字串

function isTrueName(s)  
{  
var patrn=/^[a-zA-Z]{1,30}$/;  
if (!patrn.exec(s)) return false
return true
}  


//校验密码：只能输入6-20个字母、数字、下划线  
function isPasswd(s)  
{  
var patrn=/^(\w){6,20}$/;  
if (!patrn.exec(s)) return false
return true
}  


 
//校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-”  
function isTel(s)  
{  
//var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?(\d){1,12})+$/;  
var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;  
if (!patrn.exec(s)) return false
return true
}  

//校验手机号码：必须以数字开头，除数字外，可含有“-”  
function isMobile(s)  
{  
var patrn=/^1[3|4|5|8][0-9]\d{4,8}$/; 
if (!patrn.exec(s)) return false
return true
}  
 
//校验邮政编码  
function isPostalCode(s)  
{  
var patrn=/^\d{6}$/;  
if (!patrn.exec(s)) return false
return true
}  
  
 

function isIP(s) 
{  
var patrn=/^[0-9.]{1,20}$/;  
if (!patrn.exec(s)) return false
return true
}  


 function idCard(v)
 {	
 		var r15=/[1-6]\d{5}\d{2}(?:0\d|1[12])(?:0\d|[12]\d|3[01])\d{3}/;	
  		var r18=/[1-6]\d{5}(?:19|20)\d{2}(?:0\d|1[12])(?:0\d|[12]\d|3[01])\d{3}[\dXx]/;	
  		if(v.length==15)
  		{			
  			return r15.test(v);	
  		}	
  	    else if(v.length==18)
  		{			
			var coefficient = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7,9, 10, 5, 8, 4, 2);		
			var count = 0;		
			for (var i=0;i<v.length&&i<coefficient.length;i++) 
			{			
				var t = parseInt(v.charAt(i));			
				count += t * coefficient[i];		
			}		
			count = count % 11;		
			var c = null;		
			switch (count) 
			{		case 0:			c = "1";			break;		case 1:			c = "0";			break;		case 2:			c = "x";			break;		case 3:			c = "9";			break;		case 4:			c = "8";			break;		case 5:			c = "7";			break;		case 6:			c = "6";			break;		case 7:			c = "5";			break;		case 8:			c = "4";			break;		case 9:			c = "3";			break;		default:			c = "2";			break;		
			
			}
  			
  			return r18.test(v)&&(v.charAt(17)==c);	
  		}
  		return false;
}
 



 

/** 
* 浮动DIV定时显示提示信息,如操作成功, 失败等 
* @param string tips (提示的内容) 
* @param int height 显示的信息距离浏览器顶部的高度 
* @param int time 显示的时间(按秒算), time > 0 
* @sample <a href="javascript:void(0);" onclick="showTips( '操作成功', 100, 3 );">点击</a> 
* @sample 上面代码表示点击后显示操作成功3秒钟, 距离顶部100px 
* @copyright ZhouHr 2010-08-27 
*/ 
function showSuccessTips( tips, height, time ){ 
var windowWidth = document.documentElement.clientWidth; 
var tipsDiv = '<div class="tipsClass">' + tips + '</div>'; 

$( 'body' ).append( tipsDiv ); 
$( 'div.tipsClass' ).css({ 
'top' : height + 'px', 
'left' : ( windowWidth / 2 ) - ( tips.length * 13 / 2 ) + 'px', 
'position' : 'absolute', 
'padding' : '3px 5px', 
'background': '#8FBC8F', 
'font-size' : 12 + 'px', 
'margin' : '0 auto', 
'text-align': 'center', 
'width' : 'auto', 
'color' : '#fff', 
'opacity' : '0.8' 
}).show(); 
setTimeout( function(){$( 'div.tipsClass' ).fadeOut();}, ( time * 1000 ) ); 
} 




var reg_d_ymd = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
var reg_d_ymd_hms = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})(\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
var reg_d_hms = /^((20|21|22|23|[0-1]\d)\:[0-5][0-9])(\:[0-5][0-9])?$/;

var reg_cc = /^[\u0391-\uFFE5]+$/;
var reg_ec = /^[a-zA-Z]+$/;
var reg_dit = /^[-+]?\d*$/;
var reg_double = /^[-\+]?\d+(\.\d+)?$/;
var reg_email  =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var reg_url = /^(https|http|ftp|rtsp|mms):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;











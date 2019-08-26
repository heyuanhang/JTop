
var scripts = document.getElementsByTagName('script');
var currentScriptSrc ;

var cidTemp;

var contentId = -1;
var classId = -1;
var siteId = -1;
var siteUrl = '';
var notHost = 'false';

for(var i = scripts.length-1; i >= 0; i-- )
{
	currentScriptSrc = scripts[i].src;

	if(currentScriptSrc != '' && currentScriptSrc.indexOf('core/javascript/stat.js') != -1)
	{
		var temp = currentScriptSrc.split('?');
		var param = temp[1];
		var paramA;
		if(typeof(temp[1]) !== 'undefined')
		{
			paramA = param.split('&');
			
			for(var i=0; i<paramA.length; i++)
			{
				cidTemp = paramA[i].split('=');
					
				if('siteId' == cidTemp[0])
				{
					siteId = cidTemp[1];
				}
				else if('siteUrl' == cidTemp[0])
				{
					siteUrl = cidTemp[1];
				}

				else if('notHost' == cidTemp[0])
				{
					notHost = cidTemp[1];
				}

				
				else if('classId' == cidTemp[0])
				{
					classId = cidTemp[1];
				}
				
				else if('contentId' == cidTemp[0])
				{
					contentId = cidTemp[1];
				}

			}

		}
		
		break;
	}
	
}

 

var hostUrl = siteUrl;

 

function AJAX(url ,method ,data) 
{ 
 if (window.XMLHttpRequest)
  {    
  	req = new XMLHttpRequest();   
  	req.onreadystatechange = processAjaxResponse;    
  	req.open(method, url, true);  
  	
  	req.setRequestHeader('Content-Type','whatever');
 	req.setRequestHeader('Content-Length',data.length);
 	req.setRequestHeader('Connection','close');
 	 	  
  	req.send(data);  
  } 
  else if (window.ActiveXObject) 
  {    
 	req = new ActiveXObject("Microsoft.XMLHTTP");    
 	if (req) 
 	{      
 		req.onreadystatechange = processAjaxResponse;     
 	 	req.open(method, url, true); 
 	 	    
 	 	req.setRequestHeader('Content-Type','whatever');
 	 	req.setRequestHeader('Content-Length',data.length);
 	 	req.setRequestHeader('Connection','close');
 	 	
 	 	req.send(data);     
 	}  
  }
}

function processAjaxResponse() 
{  
 if (req.readyState == 4) 
 {
   if (req.status == 200) 
   {     
   	 //alert(); 
   }
   else 
   {      
   	 // alert("There was a problem retrieving the XML data:/n" +      req.statusText);   
   }
 }  

} 


var second=0;  
var minute=0;  
var hour=0;  
idt=window.setTimeout("intervalJTopVisTime();",1000);  
function intervalJTopVisTime()
{  
        second++;  
        if(second==60) 
        {
        	second=0;
        	minute+=1;  
        }
        
        if(minute==60) 
        {
        	minute=0;
        	hour+=1;  
        }
        
        //idt=window.setTimeout("interval();",1000);  
}  



var req;
var visitID;
collectUserVisitInfoAndSendToServer();

function collectUserVisitInfoAndSendToServer() 
{
		var statData;
       
        var link = window.location.href;
        
        var host = window.location.host;
      
        var prevLink = document.referrer;

  
       // var titleName = document.title;
   
        var screen = window.screen.width + "*" + window.screen.height;    
 
        statData =hostUrl+'stat/collStat.do?'+"host="+encode(host)+"&url=" +encode(link) + "&reffer=" + encode(prevLink) +  "&system=" + getSystemInfo() + "&screen=" + screen + "&browser=" + getBr() + "&refferKey=" + getKeyword(prevLink)+"&lang="+getLan()+"&contentId="+contentId+"&classId="+classId+"&siteId="+siteId;
 
		//只加载一次jquery资源
		if (typeof jQuery == 'undefined') 
		{ 
		  		var fileObj=null;
			 	fileObj=document.createElement('script');
		        fileObj.src = hostUrl+"/core/javascript/jquery-1.7.min.js";
		       
		        fileObj.onload = fileObj.onreadystatechange = function() 
		        {
		        	//只有js加载完成后,才可以执行
			    	if (!this.readyState || 'loaded' === this.readyState || 'complete' === this.readyState) 
		            {
		            	//跨域
			      
				       $.getScript
				       (
					        statData,
					       	function()
					       	{
					  
					
					    	}
				       );
		            }
		         }
		         document.getElementsByTagName('head')[0].appendChild(fileObj);
		} 
		else 
		{ 
			    //跨域
			     
		       $.getScript
		       (
			        statData,
			       	function()
			       	{
			       
			
			    	}
		       );
		}

       
      
}

function encode(target)
{
	return encodeURIComponent(encodeURIComponent(target));
}


// 获取来自搜索引擎的关键词
function getKeyword(url) {
    if (url.toString().indexOf(".baidu.com") > 0) 
    {
        return request(url, "wd");
    }
    else if (url.toString().indexOf(".so.com") > 0) 
    {
        return request(url, "q");
    }
    else if (url.toString().indexOf(".haosou.com") > 0) 
    {
        return request(url, "q");
    }
    //else if (url.toString().indexOf("google") > 0) 
   // {
       // return request(url, "q");
    //}
    else if (url.toString().indexOf(".sogou.com") > 0) 
    {
        return request(url, "query");
    }
    else if (url.toString().indexOf(".soso.com") > 0) 
    {
        return request(url, "query");
    }
    else {
        return "";
    }
}

// 获取链接地址中某个参数的值
function request(url, paras) 
{
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {}
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}

// 回调函数，可以获取添加后的访问ID，以便其他操作。
function callback() 
{
    if (req.readyState == 4) 
    {
        if (req.status == 200) 
        {
            visitID = req.responseText.toString();
        }
        else 
        {
            
        }
    }
    else 
    {
        
    }
}

// 获取系统信息
function getSystemInfo() 
{
    var ua = navigator.userAgent.toLowerCase();
    isWin8 = ua.indexOf("nt 6.2") > -1
    isWin7 = ua.indexOf("nt 6.1") > -1
    isVista = ua.indexOf("nt 6.0") > -1
    isWin2003 = ua.indexOf("nt 5.2") > -1
    isWinXp = ua.indexOf("nt 5.1") > -1
    isWin2000 = ua.indexOf("nt 5.0") > -1
    isWindows = (ua.indexOf("windows") != -1 || ua.indexOf("win32") != -1)
    isMac = (ua.indexOf("macintosh") != -1 || ua.indexOf("mac os x") != -1)
    isAir = (ua.indexOf("adobeair") != -1)
    isLinux = (ua.indexOf("linux") != -1)
    isAndroid = ua.match(/android/i) === "android"
    isIpad = ua.match(/ipad/i) === "ipad"
    isIphoneOs = ua.match(/iphone os/i) === "iphone os"
    
    var broser = "";
    if (isWin7) {
        sys = "Windows 7";
    } else if (isWin8) {
        sys = "Windows 8";
    } else if (isVista) {
        sys = "Vista";
    } else if (isWinXp) {
        sys = "Windows XP";
    } else if (isWin2003) {
        sys = "Windows 2003";
    } else if (isWin2000) {
        sys = "Windows 2000";
    } else if (isWindows) {
        sys = "Windows";
    } else if (isMac) {
        sys = "Macintosh";
    } else if (isAir) {
        sys = "Adobeair";
    } else if (isLinux) {
        sys = "Linux";
    } else if (isAndroid) {
        sys = "Android";
    } else if (isIpad) {
        sys = "IPad";
    } else if (isIphoneOs) {
        sys = "Iphone";
    } 
    
    else {
        sys = "其他";
    }
    return sys;
}

// 获取浏览器类型
function getBrowserType() 
{

    var ua = navigator.userAgent.toLowerCase();

    if (ua == null) return "ie";

    else if (ua.indexOf('chrome') != -1) return "c1";//返回chrome有bug,只能返回代号

    else if (ua.indexOf('opera') != -1) return "opera";

    else if (ua.indexOf('msie') != -1) return "ie";

    else if (ua.indexOf('safari') != -1) return "safari";

    else if (ua.indexOf('firefox') != -1) return "firefox";

    else if (ua.indexOf('gecko') != -1) return "gecko";

    else return "ie";

}

// 获取浏览器版本
function getBrowserVersion() 
{

    var ua = navigator.userAgent.toLowerCase();

    if (ua == null) return "null";

    else if (ua.indexOf('chrome') != -1) return ua.substring(ua.indexOf('chrome') + 7, ua.length).split(' ')[0];

    else if (ua.indexOf('opera') != -1) return ua.substring(ua.indexOf('version') + 8, ua.length);

    else if (ua.indexOf('msie') != -1) return ua.substring(ua.indexOf('msie') + 5, ua.length - 1).split(';')[0];

    else if (ua.indexOf('safari') != -1) return ua.substring(ua.indexOf('safari') + 7, ua.length);

    else if (ua.indexOf('gecko') != -1) return ua.substring(ua.indexOf('firefox') + 8, ua.length);

    else return "null";
    
    
    
}

function getBr()
{
 
	var userAgent = navigator.userAgent,   
	rMsie = /(msie\s|trident.*rv:)([\w.]+)/,   
	rFirefox = /(firefox)\/([\w.]+)/,   
	rOpera = /(opera).+version\/([\w.]+)/,   
	rChrome = /(chrome)\/([\w.]+)/,   
	rSafari = /version\/([\w.]+).*(safari)/;  
	var browser;  
	var version;  
	var ua = userAgent.toLowerCase();  
	function uaMatch(ua){  
	  var match = rMsie.exec(ua);  
	  if(match != null){  
	    return { browser : "IE", version : match[2] || "0" };  
	  }  
	  var match = rFirefox.exec(ua);  
	  if (match != null) {  
	    return { browser : match[1] || "", version : match[2] || "0" };  
	  }  
	  var match = rOpera.exec(ua);  
	  if (match != null) {  
	    return { browser : match[1] || "", version : match[2] || "0" };  
	  }  
	  var match = rChrome.exec(ua);  
	  if (match != null) {  
	  	//chrome返回代号
	    return { browser : 'c1' || "", version : match[2] || "0" };  
	  }  
	  var match = rSafari.exec(ua);  
	  if (match != null) {  
	    return { browser : match[2] || "", version : match[1] || "0" };  
	  }  
	  if (match != null) {  
	    return { browser : "", version : "0" };  
	  }  
	}  
	var browserMatch = uaMatch(userAgent.toLowerCase());  
	if ( typeof(browserMatch) != 'undefined' && browserMatch.browser){  
	  browser = browserMatch.browser;  
	  version = browserMatch.version;  
	}  
	return (browser+version);          
 
	
}




 

 

function getLan()
{
	var lan = '';
	//ie 
	if (navigator.browserLanguage != "undefined" && navigator.browserLanguage != null) 
	{ 
		lan = navigator.systemLanguage;
	} 
	//firefox、chrome,360 
	else
	{ 
		lan = navigator.language; 
	} 
	
	 return lan.toLowerCase();
	 
	 
	 
	 
}


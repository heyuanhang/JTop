/**
 *  da投放代码
 */


var scripts = document.getElementsByTagName('script');
var currentScriptSrc ;

var cidTemp;

var domain =  '';
var posFlag = -1;
var siteUrl = '';
var notHost = 'false';

for(var i = scripts.length-1; i >= 0; i-- )
{
	currentScriptSrc = scripts[i].src;

	if(currentScriptSrc != '' && currentScriptSrc.indexOf('core/javascript/loadTrevda.js') != -1)
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
					
				if('domain' == cidTemp[0])
				{
					domain = cidTemp[1];
				}
				
				else if('posFlag' == cidTemp[0])
				{
					posFlag = cidTemp[1];
				}
				
				else if('siteUrl' == cidTemp[0])
				{
					siteUrl = cidTemp[1];
				}

				else if('notHost' == cidTemp[0])
				{
					notHost = cidTemp[1];
				}

			}

		}
		
		break;
	}
	
}


//只加载一次jquery资源
if (typeof jQuery == 'undefined') 
{ 
  		var fileObj=null;
	 	fileObj=document.createElement('script');
        fileObj.src = domain+"/core/javascript/jquery-1.7.min.js";
       
        fileObj.onload = fileObj.onreadystatechange = function() 
        {
        	//只有js加载完成后,才可以执行da代码
	    	if (!this.readyState || 'loaded' === this.readyState || 'complete' === this.readyState) 
            {
            	ajaxTrevda();
            }
         }
         document.getElementsByTagName('head')[0].appendChild(fileObj);
} 
else 
{ 
	    ajaxTrevda();
}

function ajaxTrevda()
{
		domain = siteUrl;
 
		var postData = '?posFlag='+posFlag+"&random="+Math.random();
 		var url = domain+"trevda/showTrevda.do"+postData;
 		 
 		var currPosFlag = posFlag;
 		
 		$.ajax
 		({
			type:'POST',
			url:url,
			dataType:'json',
			
			success:			
			function(data, textStatus)
			{ 
				$("#_____jtop__cms__trevda_pos_"+currPosFlag+"_____").append(data);	
  			}
  		});
 	 
}

 




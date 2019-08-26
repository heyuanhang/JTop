var myObject = {
  a: {
    one: 1, 
    two: 2, 
    three: 3
  }, 
  b: [1,2,3]
};
 var params = { a:1680,b:1050 };
 var actionFlag = { add:1,replace:0,remove:-1 };
 var pageActionFlag  = { next:1,prev:-1 };
 


//var recursiveEncoded = param(params);
//var recursiveDecoded = decodeURIComponent(param(myObject));

//alert(recursiveEncoded);
//alert(recursiveDecoded);
//a%5Bone%5D=1&a%5Btwo%5D=2&a%5Bthree%5D=3&b%5B%5D=1&b%5B%5D=2&b%5B%5D=3
//a[one]=1&a[two]=2&a[three]=3&b[]=1&b[]=2&b[]=3

//var shallowEncoded = param(myObject, true);
//var shallowDecoded = decodeURIComponent(shallowEncoded);

//alert(shallowEncoded);
//alert(shallowDecoded);
//a=%5Bobject+Object%5D&b=1&b=2&b=3
//a=[object+Object]&b=1&b=2&b=3







function pagination(headPosQueryCondition,lastPosQueryCondition,currentPage,pageCount,pageSize,pageAction,link)
{
	alert("OKOK");
	//alert(headPosQueryCondition);
	//alert(lastPosQueryCondition);
	
//	var idParamKey;
//	if(pageAction == pageActionFlag.next)
//	{
//		idParamKey='lastId';
//	}
//	else if(pageAction == pageActionFlag.prev)
//	{
//		idParamKey='firstId';
//	}
	
	if(pageAction==1)
	{
		if(currentPage==pageCount)
		{
			//已经是最后一页
			return;
		}
	}
	
	if(pageAction==-1)
	{
		if(currentPage==1)
		{
			//已经是第一页
			return;
		}
	}
	
	var searchStrArray = window.location.search.split('&');
	var endSearchStr='';
	var paramStr;
	
	var classId='';
	var modelId='';
	
	//记录原始的参数
	for(var i=0;i<searchStrArray.length;i++)
 	{
 		paramStr=searchStrArray[i].split('=');
 		var key=paramStr[0];
 		var value=paramStr[1];
 		//alert('-->'+paramStr[0]+paramStr[1]);
 			
 		if(key.indexOf('classId')!=-1)
 		{
 			classId=encodeURIComponent(value);
 		}
 		else if(key.indexOf('modelId')!=-1)
 		{
 			modelId=encodeURIComponent(value);
 		}
 		
 		
 		
 		
 		
 	}
 	endSearchStr='?modelId='+modelId+'&classId='+classId+'&currentPage='+encodeURIComponent(currentPage)+'&pageAction='+encodeURIComponent(pageAction)+'&pageCount='+pageCount+headPosQueryCondition+lastPosQueryCondition;
 	
	//alert(endSearchStr);
	
	//alert(window.location.protocol);
	//alert();
	
	//window.location.href='http://www.sohu.com';
	//window.location.search=endSearchStr;
	
	window.location=window.location.protocol+"//"+window.location.host+window.location.pathname+endSearchStr;
	//alert('s:'+link.href);
	//link.click();
	//param(idParam,actionFlag.add);
	//alert("end:"+window.location.search);
	
//alert(this.location.search );
//	if(action === nextAction)
//	{
//		if((currentPage+1) > count)
//		{
//			alert('222222');
//			var params = { lastId:0,currentPage:count, pageCount:count };
//		}
//		else
//		{
//			//alert(pageFlag);
//			var params = { lastId:pageFlag,currentPage:currentPage+1, pageCount:count };
//		}
//		
//	 	
//	 	var recursiveEncoded = param(params);
//		
//		var location = window.location;
//		alert(recursiveEncoded);
//		$url.attr('search',recursiveEncoded+'&classId=-9999');
//		//alert($url.attr('search'));
//	}
//	else if(action === prevAction)
//	{
//		if((currentPage-1) < 1)
//		{
//			
//			var params = { lastId:250000,currentPage:1, pageCount:count };
//		}
//		else
//		{
//			var params = { firstId:pageFlag, currentPage:currentPage-1, pageCount:count };
//		}
//		
//	 	var recursiveEncoded = $.param(params);
//		
//		var $url = $(window.location);
//		alert($url.attr('search'));
//		$url.attr('search',recursiveEncoded);
//		alert($url.attr('search'));
//		
//		
//	}
}

function firstPage()
{
	var searchStrArray = window.location.search.split('&');
	var endSearchStr='';
	var paramStr;
	
	var classId='';
	var modelId='';
	
	for(var i=0;i<searchStrArray.length;i++)
 	{
 		paramStr=searchStrArray[i].split('=');
 		var key=paramStr[0];
 		var value=paramStr[1];
 		//alert('-->'+paramStr[0]+paramStr[1]);
 			
 		if(key.indexOf('classId')!=-1)
 		{
 			classId=encodeURIComponent(value);
 		}
 		else if(key.indexOf('modelId')!=-1)
 		{
 			modelId=encodeURIComponent(value);
 		}
 	}
 	endSearchStr='?modelId='+modelId+'&classId='+classId+'&pageAction='+2;
 	
	alert(endSearchStr);
	window.location.search=endSearchStr;
}

function lastPage(pageCount,totalCount,size)
{
	//alert(pageCount+" "+totalCount);
	
	var endContentSize=0;
	
	if(pageCount == 1)
	{
		endContentSize=size;
	}
	else
	{
		endContentSize=totalCount - size * (pageCount-1);
	}
	
	//alert(endContentSize);
	var searchStrArray = window.location.search.split('&');
	var endSearchStr='';
	var paramStr;
	
	var classId='';
	var modelId='';
	
	for(var i=0;i<searchStrArray.length;i++)
 	{
 		paramStr=searchStrArray[i].split('=');
 		var key=paramStr[0];
 		var value=paramStr[1];
 		//alert('-->'+paramStr[0]+paramStr[1]);
 			
 		if(key.indexOf('classId')!=-1)
 		{
 			classId=encodeURIComponent(value);
 		}
 		else if(key.indexOf('modelId')!=-1)
 		{
 			modelId=encodeURIComponent(value);
 		}
 	}
 	endSearchStr='?modelId='+modelId+'&classId='+classId+'&pageAction='+(-2)+'&endContentSize='+endContentSize+'&pageAllCount='+pageCount;
 	
	alert(endSearchStr);
	window.location.search=endSearchStr;
	
}


function param(paramsObj,flag,keyArray)
{
	
	
	var searchStr = window.location.search;
	alert('first'+searchStr);
	var paramTmpStr;
	//alert(this.location.search );
	//传入的必须是js对象
	if(actionFlag.add == flag)
	{
			//paramTmpStr += param
			
			managerSearchURL(paramsObj,false);	
		//alert(this.location.search )
			//alert(param);
	}
	else if(actionFlag.replace == flag)
	{
		
	}
	
	
	
}

function managerSearchURL(paramsObj,remove)
{
	var tempParamStr='';
	for ( var param in paramsObj) 
	{	if(tempParamStr != '')
		{
			tempParamStr +='&';
		}
		tempParamStr += encodeURIComponent(param) + '=' + encodeURIComponent(paramsObj[param]);
	}
	alert('tempParamStr:'+tempParamStr);
	
	if(remove == true)
	{
	  window.location.search='?'+tempParamStr;
	}
	else
	{
	
	 if(window.location.search == '')
	 {	
	 	
	 	window.location.search='?'+tempParamStr;
	  	
	 }
	 else
	 {
	 	var searchStrArray = window.location.search.split('&');
	 	var endSearchStr;
	 	var paramStr;
	 	for(var i=0;i<searchStrArray.length;i++)
	 	{
	 		//alert('-->'+searchStrArray[i]);
	 		paramStr=searchStrArray[i].split('=');
	 		
	 		var key=paramStr[0];
	 		var value=paramStr[0];
	 		//alert('-->'+paramStr[0]+paramStr[1]);
	 	//	if()
	 		//endSearchStr+=+searchStrArray[i];
	 	}
	 	
	 	window.location.search+='&'+tempParamStr;
	 }
	}
	//alert('----------->'+window.location.search);
}

function replaceSearchURL(paramsObj)
{
	var tempParamStr='';
	for ( var param in paramsObj) 
	{	
		if(tempParamStr != '')
		{
			tempParamStr +='&';
		}
		tempParamStr += encodeURIComponent(param) + '=' + encodeURIComponent(paramsObj[param]);
	}
	
	return tempParamStr;
}


/**
 * 替换当前URL中的参数，若当前URL无此参数，则加上新的参数
 */
function replaceUrlParam(targetName,value)
{
	var params = window.location.search;
	
	if(params.indexOf("?")!=-1)//已有参数
	{
		var href = window.location.href.substring(0,window.location.href.indexOf("?"));
		if(params.indexOf(targetName)!=-1)//已存在当前参数
		{
			
			var searchStrArray = params.substring(1,params.length).split('&');
	 		var targetP;
	 		var targetV;
	 		for(var i=0;i<searchStrArray.length;i++)
	 		{
		 		paramStr=searchStrArray[i].split('=');
		 
		 		if(paramStr[0]==targetName)
		 		{	 			
			 		targetP = paramStr[0];
			 		targetV = paramStr[1];
			 		break;
		 		}	 	
	 		}
		
			params = params.replaceAll(targetP+'='+targetV,targetName+'='+value);//替换
		
			window.location.href=href+params;
		}
		else//不存在当前参数
		{
			window.location.href+="&"+targetName+"="+value;
		}
		
	}
	else//没有参数
	{
		window.location.href+="?"+targetName+"="+value;
	}
	
	
	
} 


/////////////////////////////////////////////////////////////////////////////////////////////////////////

function pageKad(nextPage)
{
	replaceUrlParam("currentPage",nextPage);
	
}

function pageJump(nextPage)
{
	////alert(nextPage.value);
	replaceUrlParam("currentPage",nextPage.value);
}





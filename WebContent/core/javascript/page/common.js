
var page_num_param_name = "pageNum";

//加载函数
function addLoadEvent(func) {
  var oldonload = window.onload;
  if (typeof window.onload != 'function') {
    window.onload = func;
  } else {
    window.onload = function() {
      oldonload();
      func();
    }
  }
}



//控制元素的显示
function displayOrHide(displayElement, hideElement) {
	if (displayElement != "") {
		var displayObj = document.getElementById(displayElement);
		displayObj.style.display = "";//显示
	}
	if (hideElement != "") {
		var hideObj = document.getElementById(hideElement);
		hideObj.style.display = "none";//不显示
	}
}



//对searchStrParam进行解析，返回指定参数为name的value
function getSearchValue(searchStrParam, name) {
	if (searchStrParam == "URL") {
		searchStr = this.location.search.substr(1);//如果传入的完整的URL,则获取URL中&后面的参数对  
	} else {//否则传入的就是参数对
		searchStr = searchStrParam;
	}
	var searchArray = new Array();//用来装参数对字符串
	var returnStr = "";
	if (searchStr != "") {//不空
		searchArray = searchStr.split("&");//分割
		for (var i = 0; i < searchArray.length; i++) {//迭代搜索指定name的value
			if (searchArray[i] != "") {
				var paramArray = new Array();
				paramArray = searchArray[i].split("=");//一次迭代的参数对
				if (paramArray[0] == name) {//如参数名为指定name，返回value
					returnStr = paramArray[1];
					return returnStr;
				}
			}
		}
	}
	return "";
}



//对于searchStr,会根据operType指定的操作类型，对name=value进行相关操作
function searchManage(searchStr, operType, name, value) {
	var searchArray = new Array();
	var returnStr = "";
	if (searchStr != "") {
		searchArray = searchStr.split("&");//分割各个参数对
		for (var i = 0; i < searchArray.length; i++) {//迭代当前参数对，去掉当前存在的name=value对
			if (searchArray[i] != "") {
				var paramArray = new Array();
				paramArray = searchArray[i].split("=");
				if (paramArray[0] == name) {//如果找到指定参数，从参数数组中移去这个数据
					searchArray.splice(i, 1);
					i--;//继续考察当前位置元素
				}
			}
		}
	}
	if (operType == "add") {//是add就加上新的参数对
		returnStr = name + "=" + value;
	}
	if (operType == "remove") {//remove就不加，指定参数对已删除了
	}
	for (var j = 0; j < searchArray.length; j++) {//加上原先所有的参数对
		if (searchArray[j] != "") {
			returnStr += "&" + searchArray[j];
		}
	}
	
	return returnStr;
}



//在指定searchStr上添加一个参数对
function addSearch(searchStr, name, value) {
	if (searchStr == "URL") {
		return searchManage(this.location.search.substr(1), "add", name, value);
	}
	return searchManage(searchStr, "add", name, value);
}

function addLast(searchStr, name, value) {
	if (searchStr == "URL") {
		return searchManage(this.location.search.substr(1), "add", name, value);
	}
	return searchManage(searchStr, "lastId", name, value);
}

//替换当前URL的指定参数对
function addURLParam(paramName, paramValue) {
	var tmp = removeSearch("URL", paramName);//先删除原先的值
	this.location.search = addSearch(tmp, paramName, paramValue);//加上新的值
}



//在指定searchStr上删除一个参数对
function removeSearch(searchStr, name) {
	if (searchStr == "URL") {
		return searchManage(this.location.search.substr(1), "remove", name, "");
	}
	return searchManage(searchStr, "remove", name, "");
}



//跳到某一页
function pageJump() {
	
	var selectV = document.getElementById("pageTagSelect");
	//var e = event.srcElement;//设置或获取触发事件的对象。

	this.location.search = addSearch("URL", page_num_param_name, selectV.value);//设置href中?后的页数数据
}



//第一页
function firstPage() {
	this.location.search = addSearch("URL", page_num_param_name, 1);
}


//最后一页
function lastPage(pageCount) {
	this.location.search = addSearch("URL", page_num_param_name, pageCount);
}

//下一页
//下一页
function nextPage(lastId,pageCount) {
	
	var pn = getSearchValue("URL", page_num_param_name);//得到当前页
	if (pn == "" || pn == null) {//第一页无pn
		var nextPage = 2;
	} else {
		var nextPage = parseInt(pn) + 1;
	}
	if (nextPage > pageCount) {//最大为pageCount
		nextPage = pageCount;
	}
	
	 addSearch("URL", page_num_param_name, nextPage);
	addSearch("URL", 'firstId', '-99999999');
	this.location.search =addSearch("URL", 'lastId', lastId);
	//alert(this.location);
}

//上一页
function prevPage(firstId) {
	var pn = getSearchValue("URL", page_num_param_name);//得到当前页
	if (pn == "" || pn == null) {//是第一页仍然回第一页
		first_page();
		return;
	}
	var nextPage = parseInt(pn) - 1;//上一页
	if (nextPage < 1) {//最小为第一页
		nextPage = 1;
	}
	addSearch("URL", page_num_param_name, nextPage);
	addSearch("URL", 'lastId', '-99999999');
	this.location.search =  addSearch("URL", 'firstId', firstId);
}


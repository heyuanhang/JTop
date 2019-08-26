var basePath="";


 
function linkBlur()
{
   for(var i=0; i<document.links.length; i++)
   {
     document.links[i].onclick=function(){this.blur()}
   }
}


var splitChar='*';
  //  ua = navigator.userAgent.toLowerCase(),
  
 //   check = function(r){
  //      return r.test(ua);
//    },
//    isStrict = document.compatMode == "CSS1Compat",//    isOpera = check(/opera/),
//    isChrome = check(/chrome/),
//    isWebKit = check(/webkit/),
//    isSafari = !isChrome && check(/safari/),
//    isSafari3 = isSafari && check(/version\/3/),
//    isSafari4 = isSafari && check(/version\/4/),
//    isOpera = check(/opera/),
//    isIE = !isOpera && check(/msie/),
//    isIE7 = isIE && check(/msie 7/),
//    isIE8 = isIE && check(/msie 8/),
//    isFF = check(/firefox/),
//    isGecko = !isWebKit && check(/gecko/),
//    isGecko3 = isGecko && check(/rv:1\.9/),
//    isBorderBox = isIE && !isStrict,
//    isWindows = check(/windows|win32/),
//    isMac = check(/macintosh|mac os x/),
//    isAir = check(/adobeair/),
//    isLinux = check(/linux/),
//    isSecure = /^https/i.test(window.location.protocol);



//�Զ���ģ��
/**
 * ȡָ��frameName�� ָ��targetId��Ԫ��ֵ
 */
function getResultFromFrame(frameName,targetId)
{
    return document.getElementById(frameName).contentWindow.document.getElementById(targetId).value;
}
/********************************************************** Util **********************************************************/
//���ݵ���� onpropertychange �¼�
function onpropertychange(element, fun, capture)
{/* shawl.qiu code, return default, func:none */
	
 if(document.addEventListener) return element.addEventListener("input", func, capture);
 if(document.attachEvent) return element.attachEvent("onpropertychange",func);
 alert("�������֧�� onpropertychange ����!");
 return;
}




//��չString��һЩ����

String.prototype.trim=function()
{
	return this.replace(/(^\s*)|(\s*$)/g,'');
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}  

function replaceAll(targetStr, oldStr, newStr)
{
	var endStr = targetStr;
	var index = targetStr.indexOf( oldStr );
		
	var i = 0;
	
	while( index != -1 )
	{
     	endStr = endStr.replace(oldStr, newStr); 
     	 
     	index = endStr.indexOf(oldStr,  index+1);
	}	

	return endStr;
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

//��ȡselect�Ѿ�ѡ�е��ı�html
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

//��ʼ��radio,���ָ����radio���֣������ڸ�valueֵ��ѡ��ѡ��
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


//��ȡradioѡȡ��ֵ
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

//��ʼ��select,���ָ����select ID�������ڸ�valueֵ��ѡ��ѡ��
function initSelect(targetSelectId,initValue)
{
	var select = document.getElementById(targetSelectId);
	
	if(select == null)
	{
		return;
	}
	
    for (var i=0; i<select.length; i++)
    {  
      if (select.options[i].value==initValue) 
      {  
           select.options[i].selected=true;  
           break;  
      }  
    }  
}

//��ʼ��select,���ָ���Ķ��select ID�������ڸ�valueֵ��ѡ��ѡ��
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



//��ѡ����checkbox
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


//��ѡ����checkbox
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


function checkSelectAll(flag,allFlag)
{
	var boxs = document.getElementsByName(flag);
	
	var box;
	var checkStateIn = false;
	var notCheckStateIn = false;
	for(var i = 0; i < boxs.length; i++)
	{
		box = boxs[i];
		
		if(box.checked)
		{
			checkStateIn = true;
		}
		else
		{
			notCheckStateIn = true;
		}
	}
	
	if(checkStateIn && notCheckStateIn)
	{
		document.getElementById(allFlag).checked = false;
	}
	else if(checkStateIn)
	{
		document.getElementById(allFlag).checked = true;
	}
	else if(notCheckStateIn)
	{
		document.getElementById(allFlag).checked = false;
	}
}




//ȡ��ѡ����checkbox
function unSelectAllBox(targetCheckName)
{
   var checks = document.getElementsByName(targetCheckName);

   for(var i = 0; i < checks.length; i++)
   {
      checks[i].checked=false;
   }
   //targetMap.removeAll();
}

//����ѡȡ�̶�ɫ
function changeTRBG(obj, index)
{
	//����ɫ

	if(obj.checked == true)
	{
		$('#tr-'+index).addClass("tdbgyewck"); 
	}
	else
	{
		$('#tr-'+index).removeClass("tdbgyewck"); 
	}

}
//���÷��� ����


//��ǰҳ����
function back()
{
	window.history.back();
}

//
function backAndReload()
{
	window.history.back();
	window.location.reload();
}

// ���á����ó����� ie ffͨ��
// @targetId : ������ID
// @disable: true,���ã� false������
function disableAnchorElement(targetId, disableFlag) 
{
    var obj = document.getElementById(targetId);
    
    if(obj == null)
    {
    	return;
    }

    if (disableFlag) 
    { // disable
        var href = obj.getAttribute("href");
        if (href && href != "" && href != null) 
        {
            obj.setAttribute('href_bak', href);
        }
        
        //��ȥhref
        obj.removeAttribute('href');
        // ����onclick�¼�,��Ч��
        var onclick = obj.getAttribute("onclick"); 
        if(onclick != null)
        { 
            obj.setAttribute('onclick_bak', onclick); 
            obj.setAttribute('onclick', "void(0);"); 
        } 
        
        obj.onclick = null;
        obj.style.color = "gray";
        obj.style.cursor="not-allowed";
        obj.setAttribute('disabled', 'disabled');
    }
    else 
    { // enable,��ԭֵ
        if (obj.attributes['href_bak'])
        {
            obj.setAttribute('href', obj.attributes['href_bak'].nodeValue);
        }
        
        if(obj.attributes['onclick_bak']!=null) 
        {
            obj.setAttribute('onclick', obj.attributes['onclick_bak'].nodeValue); 
        }
        
        obj.removeAttribute('style');
        obj.removeAttribute('disabled');
    }
}

function disableAnchorElementByName(targetName, disableFlag) 
{
	var objs = document.getElementsByName(targetName);
	
	var obj;
	for(var i = 0; i < objs.length; i++)
	{
		obj = objs[i];
		
		if(obj == null)
	    {
	    	return;
	    }
	
	    if (disableFlag) 
	    { // disable
	        var href = obj.getAttribute("href");
	        if (href && href != "" && href != null) 
	        {
	            obj.setAttribute('href_bak', href);
	        }
	        
	        //��ȥhref
	        obj.removeAttribute('href');
	        // ����onclick�¼�,��Ч��
	        var onclick = obj.getAttribute("onclick"); 
	        if(onclick != null)
	        { 
	            obj.setAttribute('onclick_bak', onclick); 
	            obj.setAttribute('onclick', "void(0);"); 
	        } 
	        
	        obj.onclick = null;
	        obj.style.color = "gray";
	        obj.style.cursor="not-allowed";
	        obj.setAttribute('disabled', 'disabled');
	    }
	    else 
	    { // enable,��ԭֵ
	        if (obj.attributes['href_bak'])
	        {
	            obj.setAttribute('href', obj.attributes['href_bak'].nodeValue);
	        }
	        
	        if(obj.attributes['onclick_bak']!=null) 
	        {
	            obj.setAttribute('onclick', obj.attributes['onclick_bak'].nodeValue); 
	        }
	        
	        obj.removeAttribute('style');
	        obj.removeAttribute('disabled');
	    }
		
	}
	
	
}


//���������õ���ȷ�ĳ���

function accDiv(arg1,arg2)
{
	if(arg2 ==0)
	{
		return 0;
	}									
	
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
}
//function initChaeckBox(targetBoxName,initValueArray)
//{
//	
//	
//	
//	var radioArray = document.getElementsByName(targetRadioName);  
//    for (var i=0; i<radioArray.length; i++)
//    {  
//        
//    }  
//}

/**
 * �͵�ǰ���ڶԱ�,��ȡ������������
 */
function getOtherDate(n)
{
			var nn = new Date();
			nn.setDate(nn.getDate()+n);
			year1 = nn.getYear();
			mon1 = nn.getMonth() + 1;
			date1 = nn.getDate();
			var monstr1;
			var datestr1
			if (mon1 < 10)
				monstr1 = "0" + mon1;
			else
				monstr1 = "" + mon1;

			if (date1 < 10)
				datestr1 = "0" + date1;
			else
				datestr1 = "" + date1;
			return year1 + "-" + monstr1 + "-" + datestr1;
}


/********************************************************** HashTable **********************************************************/
function Hashtable() 
{
    this.container = new Object();
    
    /**//** put element */
    this.put = function (key, value) 
    {
        if (typeof (key) == "undefined")
        {
            return false;
        } 
        if (this.contains(key))
        {
            return false;
        } 
        this.container[key] = typeof (value) == "undefined" ? null : value;
        return true;
    };

    /**//** remove element */
    this.remove = function (key) 
    {
        delete this.container[key];
    };
    
    /**//** get size */
    this.size = function () 
    {
        var size = 0;
        for (var attr in this.container) 
        {
            size++;
        }
        return size;
    };
    
    /**//** get value by key */
    this.get = function (key) 
    {
        return this.container[key];
    };

    /**//** containts a key */
    this.contains = function (key) 
    {
        return typeof (this.container[key]) != "undefined";
    };

    /**//** clear all entrys */
    this.clear = function () 
    {
        for (var attr in this.container)
        {
            delete this.container[attr];
        }
    };
    
    /**//** hashTable 2 string */
    this.toString = function()
    {
        var str = "";
        for (var attr in this.container)
        {
            str += "," + attr + "=" + this.container[attr];
        }
        if(str.length>0)
        {
            str = str.substr(1, str.length);
        }
        return "{" + str + "}";
    };
}
        
        var hashtable = new Hashtable();
        hashtable.put('1','huyvanpull');
        hashtable.put('2','ensoodge');
        hashtable.put('3','huyfan');
        
        hashtable.remove('2');
        //alert(hashtable.toString());
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
 
 /********************************************************** HashMap **********************************************************/
  var HashMapJs = function() {

  this.initialize();

}

 

HashMapJs.hashmap_instance_id = 0;

 

HashMapJs.prototype = {

  hashkey_prefix: "<#HashMapHashkeyPerfix>",

  hashcode_field: "<#HashMapHashcodeField>",

 

  initialize: function() {

    this.backing_hash = {};

    this.code = 0;
    
    this.mapSize=0;

    HashMapJs.hashmap_instance_id += 1;

    this.instance_id = HashMapJs.hashmap_instance_id;

  },

 

  hashcodeField: function() {

    return this.hashcode_field + this.instance_id;

  },

  /*

   maps value to key returning previous assocciation

   */

  put: function(key, value) {

    var prev;

 

    if (key && value) {

      var hashCode;

      if (typeof(key) === "number" || typeof(key) === "string") {

        hashCode = key;

      } else {

        hashCode = key[this.hashcodeField()];

      }

      if (hashCode) {

        prev = this.backing_hash[hashCode];

      } else {

        this.code += 1;

        hashCode = this.hashkey_prefix + this.code;

        key[this.hashcodeField()] = hashCode;

      }

      this.backing_hash[hashCode] = [key, value];

    }
	
	this.mapSize++;

    return prev === undefined ? undefined : prev[1];

  },

  /*

   returns value associated with given key

   */

  get: function(key) {

    var value;

    if (key) {

      var hashCode;

      if (typeof(key) === "number" || typeof(key) === "string") {

        hashCode = key;

      } else {

        hashCode = key[this.hashcodeField()];

      }

      if (hashCode) {

        value = this.backing_hash[hashCode];

      }

    }

    return value === undefined ? undefined : value[1];

  },

  /*

   deletes association by given key.

   Returns true if the assocciation existed, false otherwise

   */

  remove: function(key) {

    var success = false;

    if (key) 
    {

      var hashCode;

      if (typeof(key) === "number" || typeof(key) === "string") {

        hashCode = key;

      } else {

        hashCode = key[this.hashcodeField()];
        

      }

      if (hashCode) {

        var prev = this.backing_hash[hashCode];

        this.backing_hash[hashCode] = undefined;

        if (prev !== undefined){

          key[this.hashcodeField()] = undefined; //let's clean the key object

          success = true;

        }

      }

    }
    
    if(success)
    {
		this.mapSize--;
    }
	
    return success;

  },

  /*

   iterate over key-value pairs passing them to provided callback

   the iteration process is interrupted when the callback returns false.

   the execution context of the callback is the value of the key-value pair

   @ returns the HashMap (so we can chain)                                                                  (

   */

  each: function(callback, args) {

    var key;

    for (key in this.backing_hash){

      if (callback.call(this.backing_hash[key][1], this.backing_hash[key][0], this.backing_hash[key][1]) === false)

        break;

    }

    return this;

  },
  
  /**����ǰmap�е�����ֵת��Ϊһ��String���ָ����ɲ�����  **/
  allValueToString: function(sep)
  {
    var key;
	var endValueStr='';
	var index = 0;
    for (key in this.backing_hash)
    {
      if( this.backing_hash[key] !== undefined)
      {
      	index ++;
        if(index == this.mapSize)
      	{
      		endValueStr += this.backing_hash[key][1];
      	}
      	else
      	{
      		endValueStr += this.backing_hash[key][1]+sep;
      	}
      }
    }   
	return endValueStr;
  },
  
   /**����ǰmap�е�����keyת��Ϊһ��String���ָ����ɲ�����  **/
  allKeyToString: function(sep)
  {
    var key;
	var endKeyStr='';
	var index = 0;
    for (key in this.backing_hash)
    {
      index ++;
      if( this.backing_hash[key] !== undefined)
      {
      	if(index == this.mapSize)
      	{
      		endKeyStr += key;
      	}
      	else
      	{
      		endKeyStr += key+sep;
      	}
      }
    }   
	return endKeyStr;
  },
  
  /**��ǰmap�Ĵ�С  **/
  size: function()
  {
	return this.mapSize;
  },
  
  /**ɾ��map������Ԫ��  **/
  removeAll: function()
  {
	this.backing_hash = {};
	this.mapSize=0;
  },
  
  toString: function() {
    return "HashMapJS"

  }
}
        
        
/********************************************************** ��ʱ�ĵ������� **********************************************************/
  
function openWindow(targetPageUrl,pageId,height,width,topX,leftX,testMode)
{
	if(!testMode)
	{
		window.open(targetPageUrl,pageId,'height='+height+',width='+width+',top='+topX+',left='+leftX+',toolbar=no,meaubar=no,scrollbars=no,resizable=no,titlebar=no,location=no,status=no');
	}
	else
	{
		
	}
}

function gotoCurrentPage(url)
{
    var link=document.createElement("a");
    link.href=url;
    document.body.appendChild(link);
    
    
   //TODO FF��֧��
    link.click();	
	
}
  
 
/********************************************************** File function **********************************************************/

function entryAction(targetURL,entry,param)
{
	var prevEntry='';
	
	var es = entry.split(splitChar);
	
	for(var i=0; i<es.length; i++)
	{
		if(i+1 != es.length)
		{
			if(i+2==es.length)
			{
				prevEntry+=es[i];
			}else
			{
				prevEntry+=es[i]+splitChar;
			}
		}
	}
	//alert(targetURL+"?entry="+entry+"&prevEntry="+prevEntry+param);
	
	if(typeof(param) != "undefined")
	{
		//document.location.href=targetURL+"?entry="+entry+"&prevEntry="+prevEntry+param;
		gotoCurrentPage(targetURL+"?entry="+entry+"&prevEntry="+prevEntry+param);
	}
	else
	{
	    //document.location.href=targetURL+"?entry="+entry+"&prevEntry="+prevEntry;
	    gotoCurrentPage(targetURL+"?entry="+entry+"&prevEntry="+prevEntry);
	}
}





/*****************************���ģ��*********************************/

function getResultFromFrame(frameName,targetName)
{
    return document.getElementById(frameName).contentWindow.document.getElementById(targetName).value;
}

  
  
/*****************************URL����*********************************/
 
/**
 * �滻��ǰURL�еĲ�������ǰURL�޴˲���������µĲ���:targetNameValueStr (a=1&b=2)
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

	if(params.indexOf("?")!=-1)//���в���
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
				
				 
			if(params.indexOf('&'+targetName+'=')!=-1 || params.indexOf('?'+targetName+'=')!=-1)//�Ѵ��ڵ�ǰ����
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
					params =replaceAll(params,'?'+targetP+'='+targetV,'?'+targetName+'='+value);//�滻
				}
				else
				{
					params =replaceAll(params,'&'+targetP+'='+targetV,'&'+targetName+'='+value);//�滻
				}
				
			//alert(params);
				//loc.href=href+params;
			}
			else//�����ڵ�ǰ����
			{
				params+="&"+targetName+"="+value;
			}
			}
		}
		//alert(params);
		loc.href=href+params;
		 
	}
	else//û�в���
	{ //alert("?"+targetNameValueStr);
		loc.href+="?"+targetNameValueStr;
	   ;
	}
	
} 

function getFormJson(frm) {    var o = {};    var a = $(frm).serializeArray();    $.each(a, function () {        if (o[this.name] !== undefined) {            if (!o[this.name].push) {                o[this.name] = [o[this.name]];            }            o[this.name].push(this.value || '');        } else {            o[this.name] = this.value || '';        }    });    return o;}


/////////////////////////////////////////////UI///////////////////////////////////////////////////

//tab�л�
function setTab2(name,cursel,n){
 for(i=1;i<=n;i++)
 {
  var menus=document.getElementById(name+i);
  var cons=document.getElementById("g3_"+name+"_"+i);
  menus.className=i==cursel?"selectTag":"";
  cons.style.display=i==cursel?"block":"none";
 }
}


//ͼ���ϴ�
function showModuleImageDialog(srcId,valId,tw,th,dm,needMark)
{
	if(typeof(dialogApiId) == 'undefined' || dialogApiId == '')
	{
		var targetClass = $('#classId').val();
		
		var classId = -9999;
		if(targetClass != 'undefined' && targetClass != null)
		{
			classId = targetClass;
		}
	
		$.dialog({ 
			id:'oud',
	    	title :'ͼƬ',
	    	width: '520px', 
	    	height: '375px', 
	    	lock: true, 
	        max: false, 
	        min: false, 
	        resize: false,         
	        content: 'url:'+basePath+'core/content/dialog/ImageUploadModule.jsp?classId='+classId+'&srcId='+srcId+'&valId='+valId+'&tw='+tw+'&th='+th+'&dm='+dm+'&mark='+needMark
		});
	}
	else
	{
		var targetClass = W.document.getElementById("classId");
	
		var classId = -9999;
		
		if(targetClass != 'undefined' && targetClass != null)
		{
			classId = W.document.getElementById("classId").value;
		}
		else
		{
			//ȡ��ǰҳ
			
			targetClass =  document.getElementById("classId");
			
			if(targetClass != 'undefined' && targetClass != null)
			{
				classId = document.getElementById("classId").value;
			}
		}
		
		
		targetClass = W.document.getElementById("classChildMode");
	
		var classChildMode = "false";
		
		if(targetClass != 'undefined' && targetClass != null)
		{
			classChildMode = W.document.getElementById("classChildMode").value;
		}
		else
		{
			//ȡ��ǰҳ
			
			targetClass =  document.getElementById("classChildMode");
			
			if(targetClass != 'undefined' && targetClass != null)
			{
				classChildMode = document.getElementById("classChildMode").value;
			}
		}
		
		
		targetClass = W.document.getElementById("rootClassId");
	
		var rootClassId = -9999;
		
		if(targetClass != 'undefined' && targetClass != null)
		{
			rootClassId = W.document.getElementById("rootClassId").value;
		}
		else
		{
			//ȡ��ǰҳ
			
			targetClass =  document.getElementById("rootClassId");
			
			if(targetClass != 'undefined' && targetClass != null)
			{
				rootClassId = document.getElementById("rootClassId").value;
			}
		}
		
		if('true' == classChildMode)
		{
			classId = rootClassId;
		}
		
	
	
		W.$.dialog({ 
			id:'oud',
	    	title :'ͼƬ',
	    	width: '520px', 
	    	height: '375px',
	    	parent:api, 
	    	lock: true, 
	        max: false, 
	        min: false, 
	        resize: false,         
	        content: 'url:'+basePath+'core/content/dialog/ImageUploadModule.jsp?classId='+classId+'&classChildMode='+classChildMode+'&srcId='+srcId+'&valId='+valId+'&tw='+tw+'&th='+th+'&dm='+dm+'&mark='+needMark+'&apiId='+dialogApiId+'&isDialogMode=true'
		});
	}
}


//ͼ���ϴ�,�ര��ģʽ
function showModuleImageDialogForDialog(srcId,valId,tw,th,dm,needMark,apiId)
{
	var targetClass = W.document.getElementById("classId");
	
	var classId = -9999;
	
	if(targetClass != 'undefined' && targetClass != null)
	{
		classId = W.document.getElementById("classId").value;
	}

	W.$.dialog({ 
		id:'oud',
    	title :'ͼƬ',
    	width: '520px', 
    	height: '375px',
    	parent:api, 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,         
        content: 'url:'+basePath+'core/content/dialog/ImageUploadModule.jsp?classId='+classId+'&srcId='+srcId+'&valId='+valId+'&tw='+tw+'&th='+th+'&dm='+dm+'&mark='+needMark+'&apiId='+apiId+'&isDialogMode=true'
	});
	
}

//��ͼƬģʽ,ͼ��ģʽ
function showEditSingleImageDialog(fieldSign, imageOrder)
{
	var tw = $('#'+fieldSign+'CmsSysMaxWidth').val();
	var th = $('#'+fieldSign+'CmsSysMaxHeight').val();
	var dm = $('#'+fieldSign+'CmsSysDisposeMode').val();

	var mark = $('#'+fieldSign+'CmsSysNeedMark').attr('checked');
	
	var targetClass = $('#classId').val();
	
	var classId = -9999;
	if(targetClass != 'undefined' && targetClass != null)
	{
		classId = targetClass;
	}
		
	if('checked' == mark)
	{
		mark = 1;
	}
	else
	{
		mark = 0;
	}
	
	
	$.dialog({ 
		id:'osid',
    	title :'���滻��ǰͼƬ - '+$('#'+fieldSign+'-photoName-'+imageOrder).val(),
    	width: '520px', 
    	height: '375px', 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,         
        content: 'url:'+basePath+'core/content/dialog/ImageUploadModule.jsp?resId='+document.getElementById(fieldSign+'-resId-'+imageOrder).value+'&photoName='+document.getElementById(fieldSign+'-photoName-'+imageOrder).value+'&imageOrder='+imageOrder+'&tw='+tw+'&th='+th+'&dm='+dm+'&mark='+mark+'&classId='+classId+'&fieldSign='+fieldSign+'&groupEditMode=true'
	});
}


//ͼ���ϴ�,ͼ��ģʽ
function showModuleImageGroupDialog(fieldSign)
{
	var targetClass = document.getElementById("classId");
	
	var classId = -9999;
	if(targetClass != 'undefined' && targetClass != null)
	{
		classId = document.getElementById("classId").value;
	}
	
	var mw = document.getElementById(fieldSign+"CmsSysMaxWidth").value;
	
	var mh = document.getElementById(fieldSign+"CmsSysMaxHeight").value;
	
	var dw = document.getElementById(fieldSign+"CmsSysDisposeMode").value;
	
	$.dialog({ 
		id:'oigud',
    	title :'�����ϴ�ͼƬ',
    	width: '570px', 
    	height: '240px', 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,         
        content: 'url:'+basePath+'core/content/dialog/ImageGroupUploadModule.jsp?classId='+classId+'&fieldSign='+fieldSign+'&mw='+mw+'&mh='+mh+'&dw='+dw
	});
}





//ý���ϴ�
function showModuleMediaDialog(srcId,valId)
{
	$.dialog({ 
		id:'smmd',
    	title :'��ý��',
    	width: '520px', 
    	height: '375px', 
    	lock: true, 
        max: false, 
        min: false, 
        resize: false,         
        content: 'url:'+basePath+'core/content/dialog/MediaUploadModule.jsp?classId='+document.getElementById("classId").value+'&srcId='+srcId+'&valId='+valId
	});
}

//�ļ��ϴ�
function showModuleFileDialog(srcId,valId)
{
	if(typeof(dialogApiId) == 'undefined' || dialogApiId == '')
	{
		var targetClass = $('#classId').val();
		
		var classId = -9999;
		if(targetClass != 'undefined' && targetClass != null)
		{
			classId = targetClass;
		}
	
		$.dialog({ 
			id:'smfd',
	    	title :'�ļ�',
	    	width: '520px', 
	    	height: '375px', 
	    	lock: true, 
	        max: false, 
	        min: false, 
	        resize: false,         
	        content: 'url:'+basePath+'core/content/dialog/FileUploadModule.jsp?classId='+classId+'&srcId='+srcId+'&valId='+valId
		});
	}
	else
	{
		var targetClass = W.document.getElementById("classId");
	
		var classId = -9999;
		
		if(targetClass != 'undefined' && targetClass != null)
		{
			classId = W.document.getElementById("classId").value;
		}
	
		W.$.dialog({ 
			id:'smfd',
	    	title :'�ļ�',
	    	width: '520px', 
	    	height: '375px',
	    	parent:api,
	    	lock: true, 
	        max: false, 
	        min: false, 
	        resize: false,         
	        content: 'url:'+basePath+'core/content/dialog/FileUploadModule.jsp?classId='+classId+'&srcId='+srcId+'&valId='+valId+'&apiId='+dialogApiId+'&isDialogMode=true'
		});
	}
}



function resizeImage(img)
{
	var w = img.width;
	var h = img.height;
	
	var newWH = checkSize(w,h,120,160);
	
	img.width = newWH[0];
	img.height = newWH[1];
}

function checkSize(width, height, maxWidth, maxHeight) 
{
    if (width && height)
    {
       if ((width / height) > (maxWidth / maxHeight)) 
       {
          if (width > maxWidth) 
          {
          	height = height * maxWidth / width;
            width = maxWidth;
          }
       }
	   else 
	   {
           if (height > maxHeight) 
           {
             width = width * maxHeight / height;
             height = maxHeight;
           }
       }
 
       return [ Math.round(width), Math.round(height) ];
      }
}


function insertVideoPlayerForIE(fileUrl,autoStart,cover) 
{
	var str = '';
	
	if(fileUrl.lastIndexOf('.rm') != -1)
	{
		str +='<object id="rep1" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width=648 height=275>';
			str +='<param name="_ExtentX" value="11298">';
			str +='<param name="_ExtentY" value="7938">';
			str +='<param name="AUTOSTART" value="0">';
			str +='<param name="SHUFFLE" value="0">';
			str +='<param name="PREFETCH" value="0">';
			str +='<param name="NOLABELS" value="-1">';
			str +='<param name="SRC" value="'+fileUrl+'";>';
			str +='<param name="CONTROLS" value="Imagewindow">';
			str +='<param name="CONSOLE" value="clip1">';
			str +='<param name="LOOP" value="1">';
			str +='<param name="NUMLOOP" value="0">';
			str +='<param name="CENTER" value="0">';
			str +='<param name="MAINTAINASPECT" value="1">';
			str +='<param name="BACKGROUNDCOLOR" value="#000000">';
			str +='<param name="wmode" value="transparent">';
		str +='</object>';
		str +='<br />';
		str +='<object id="rep2" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width=648 height=30>';
			str +='<param name="_ExtentX" value="11298">';
			str +='<param name="_ExtentY" value="794">';
			str +='<param name="AUTOSTART" value="0">';
			str +='<param name="SHUFFLE" value="0">';
			str +='<param name="PREFETCH" value="0">';
			str +='<param name="NOLABELS" value="-1">';
			str +='<param name="SRC" value="'+fileUrl+'";>';
			str +='<param name="CONTROLS" value="ControlPanel">';
			str +='<param name="CONSOLE" value="clip1">';
			str +='<param name="LOOP" value="1">';
			str +='<param name="NUMLOOP" value="0">';
			str +='<param name="CENTER" value="0">';
			str +='<param name="MAINTAINASPECT" value="1">';
			str +='<param name="BACKGROUNDCOLOR" value="#000000">';
			str +='<param name="wmode" value="transparent">';
		str +='</object>';
	}
	else if(fileUrl.lastIndexOf('.wmv') != -1 || fileUrl.lastIndexOf('.wma') != -1)
	{
		str +=	'<object id="player" height="305" width="648" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">';
			str +=	'<param NAME="AutoStart" VALUE="-1">';
			str +=	'<param NAME="Balance" VALUE="0">';
			str +=	'<param name="enabled" value="-1">';
			str +=	'<param NAME="EnableContextMenu" VALUE="-1">';
			str +=	'<param NAME="url" value="'+fileUrl+'">';
			str +=	'<param NAME="PlayCount" VALUE="1">';
			str +=	'<param name="rate" value="1">';
			str +=	'<param name="currentPosition" value="0">';
			str +=	'<param name="currentMarker" value="0">';
			str +=	'<param name="defaultFrame" value="">';
			str +=	'<param name="invokeURLs" value="0">';
			str +=	'<param name="baseURL" value="">';
			str +=	'<param name="stretchToFit" value="0">';
			str +=	'<param name="volume" value="50">';
			str +=	'<param name="mute" value="0">';
			str +=	'<param name="uiMode" value="full">';
			//���²���ǳ���Ҫ������ڵ�DIV����
			str +=	'<param name="windowlessVideo" value="true">';
			str +=	'<param name="fullScreen" value="0">';
			str +=	'<param name="enableErrorDialogs" value="-1">';
			str +=	'<param name="SAMIStyle" value>';
			str +=	'<param name="SAMILang" value>';
			str +=	'<param name="SAMIFilename" value>';
			str +='<param name="wmode" value="Opaque">';
		str +=	'</object>';
	}
	else
	{
		str +=	'<object id="testCmsSysMediaImgShow" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" color="black" allowfullscreen="true" quality="high" type="application/x-shockwave-flash" width="648" height="305" wmode="transparent" data="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'">';
		str +=		'<param id="testCmsSysMediaImgShowParam" name="movie" value="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'" />';
		str +=		'<param name="wmode" value="transparent" />';
		str +=		'<param name="quality" value="high" />';
		str +=		'<param name="allowfullscreen" value="true" />';
		str +=		'<param name="displayheight" value="0" />';
		str +=      '<param name="wmode" value="Opaque">';
		str +=		'<embed id="embedPlayer" name="embedPlayer" wmode="transparent" width="648" height="305" allowfullscreen="true" quality="high" type="application/x-shockwave-flash" src="'+basePath+'core/extools/player/jwplayer/5.9/player.swf?file='+fileUrl+'&screencolor=black&autoStart='+autoStart+'&image='+cover+'" />';
		str +=	'</object>';
			
		
	}

	document.write(str);
}

/**
 * ɾ����Ƶ�ļ���Ϣ,�ȴ�ɾ��
 */
function deleteMedia(fieldSign)
{
	$.dialog({ 
   					title :'��ʾ',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '��ȷ��ɾ��ǰ��Ƶ��',
                    
                    ok: function () { 
                    
                    $("#"+fieldSign +"_delete_flag").val('true');
                    
                    $("#"+fieldSign).val('-1');
                    
					$("#"+fieldSign +"_sys_jtopcms_media_showtime").val('');
	
					$("#"+fieldSign +"_sys_jtopcms_media_width").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_height").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_name").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_type").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_cover_src").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_cover_w").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_cover_h").val('');
					
					$("#"+fieldSign +"_sys_jtopcms_media_cover_n").val('');
					
					document.getElementById(fieldSign+'_sys_jtopcms_iframe').src = basePath+'core/content/UploadVideoModule.jsp?fileUrl=&autoStart=false&cover=';
			}, 
    		cancel: true 
	});
	
}


function openCopyContentToSiteClassDialog(flag, modelId, refClassIdStr, siteId)
{
	$.dialog({ 
	    id : 'occtcd',
    	title : '������������Ŀ',
    	width: '400px', 
    	height: '500px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:'+basePath+'core/content/dialog/ShowCopyContentClass.jsp?siteId='+siteId+'&uid='+Math.random()+'&modelId='+modelId+'&refClassIdStr='+refClassIdStr+'&flag='+flag

	});
}




function openMoveContentToSiteClassDialog(modelId, classId)
{
	$.dialog({ 
	    id : 'occtcd',
    	title : '�ƶ���������Ŀ',
    	width: '400px', 
    	height: '500px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:'+basePath+'core/content/dialog/ShowMoveContentClass.jsp?uid='+Math.random()+'&modelId='+modelId+'&classId='+classId

	});
}
/**
 * iframe��̬����Ӧ��С
 */
function iframeResize(id) 
{ 
	var targetFrame = null; 
	if (document.getElementById)
	{ 
		targetFrame = document.getElementById(id); 
	} 
	else
	{ 
		eval('targetFrame = ' + id + ';'); 
	} 

	if (targetFrame && !window.opera)
	{ 

		targetFrame.style.display="block"
		if(targetFrame.contentDocument && targetFrame.contentDocument.body.offsetHeight)
		{ 
			//ns6 syntax 
			targetFrame.height = targetFrame.contentDocument.body.offsetHeight ; 
			targetFrame.width = targetFrame.contentDocument.body.scrollWidth; 
		} 
		else if (targetFrame.Document && targetFrame.Document.body.scrollHeight)
		{ 
			//ie5+ syntax 
			alert(targetFrame.Document.body.scrollHeight+' '+targetFrame.Document.body.scrollWidth);
			targetFrame.height = targetFrame.Document.body.scrollHeight; 
			targetFrame.width = targetFrame.Document.body.scrollWidth; 
		} 
	} 
} 

/////////////////////////////////////////////�ֶ��������///////////////////////////////////////////////////

//ҳ��ȫ��ͼ������
var allImageGroupSortInfo = new Array();


function initUploadImage(sid, type, size)
{

try{
swfu = new SWFUpload({
upload_url: basePath+"content/multiUpload.do;jsessionid="+sid,

// File Upload Settings
file_size_limit : size+" MB", // 1000MB
file_types : type,//���ÿ��ϴ�������
file_types_description : "ͼƬ�ļ�",
file_upload_limit : "50",

file_queue_error_handler : fileQueueError,//ѡ���ļ������
file_dialog_complete_handler : fileDialogComplete,//ѡ����ļ����ύ
file_queued_handler : fileQueued,
upload_progress_handler : uploadProgress,
upload_error_handler : uploadError,
upload_success_handler : uploadSuccess,
upload_complete_handler : uploadComplete,

// Button Settings
button_image_url :  basePath+"core/javascript/upload/SWFUpload/upload.png",
button_placeholder_id : "spanButtonPlaceholder",
button_width: 62,
button_height: 22,
button_text : '',
button_text_style : '',
button_text_top_padding: 10,
button_text_left_padding: 18,
button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
button_cursor: SWFUpload.CURSOR.HAND,


// Flash Settings
flash_url : basePath+"core/javascript/upload/SWFUpload/swfupload.swf",

use_query_string : true,
custom_settings : {
upload_target : "divFileProgressContainer"
},

// Debug Settings
debug: false //�Ƿ���ʾ���Դ���


});
}catch(ex)
{
	alert(ex);
}
}

function startUploadFile()
{
	swfu.startUpload();
}

/**
 * ͼ����ͼƬ����
 */
function changePhotoPos(fieldSign, currentPos, action)
{		
	var sortArray = allImageGroupSortInfo[fieldSign];
	
	var targetPos = null;
	if('up' === action)
	{	
		if(currentPos == 0)
		{
			return;
		}
		
		for(var i = (currentPos-1); i >= 0; i--)
		{
			targetPos = sortArray[i];
			
			if(targetPos != null)
			{
				break;
			}
		}
	}
	else if('down' === action)
	{
		if(currentPos == (sortArray.length-1))
		{
			return;
		}
		
		for(var i = (currentPos+1); i < sortArray.length; i++)
		{
			targetPos = sortArray[i];
			
			if(targetPos != null)
			{
				break;
			}
		}
	}
	
	if(targetPos == null)
	{
		return;
	}
	
	var currentCover = $("#"+fieldSign +"-cover-"+currentPos).is(':checked');
	var targetCover = $("#"+fieldSign +"-cover-"+targetPos).is(':checked');
	
	if(currentCover)
	{
		$("#"+fieldSign +"-cover-"+targetPos).attr('checked',true);
		$("#"+fieldSign +"-cover-"+currentPos).attr('checked',false);
	}
	else if(targetCover)
	{
		$("#"+fieldSign +"-cover-"+targetPos).attr('checked',false);
		$("#"+fieldSign +"-cover-"+currentPos).attr('checked',true);
	}
	
	
	
	
	var tempShowImageHref = $("#"+fieldSign +"-cmsSysImageShowUrl-"+targetPos).attr('href');
	var tempResizeUrl = $("#"+fieldSign +"-resizeUrl-"+targetPos).attr('src');
	var tempAlt = $("#"+fieldSign +"-resizeUrl-"+targetPos).attr('title');
	var tempShowName = $("#"+fieldSign +"-name-show-"+targetPos).val();
	
	var tempResId = $("#"+fieldSign +"-resId-"+targetPos).val();
	var tempRelatePath = $("#"+fieldSign +"-relatePath-"+targetPos).val();
	var tempPhotoName = $("#"+fieldSign +"-photoName-"+targetPos).val();
	var tempPhotoOutLinkUrl = $("#"+fieldSign +"-photoOutLinkUrl-"+targetPos).val();
	var tempPhotoDesc = $("#"+fieldSign +"-photoDesc-"+targetPos).val();
	var tempHeight = $("#"+fieldSign +"-height-"+targetPos).val();
	var tempWidth = $("#"+fieldSign +"-width-"+targetPos).val();
	
	var tempHeightShow = $("#"+fieldSign +"-height-show-"+targetPos).val();
	var tempWidthShow  = $("#"+fieldSign +"-width-show-"+targetPos).val();
	
	$("#"+fieldSign +"-cmsSysImageShowUrl-"+targetPos).attr('href',$("#"+fieldSign +"-cmsSysImageShowUrl-"+currentPos).attr('href'));
	$("#"+fieldSign +"-cmsSysImageShowUrl-"+currentPos).attr('href',tempShowImageHref);
	
	$("#"+fieldSign +"-resizeUrl-"+targetPos).attr('src',$("#"+fieldSign +"-resizeUrl-"+currentPos).attr('src'));
	$("#"+fieldSign +"-resizeUrl-"+currentPos).attr('src',tempResizeUrl);
	
	$("#"+fieldSign +"-resizeUrl-"+targetPos).attr('title',$("#"+fieldSign +"-resizeUrl-"+currentPos).attr('title'));
	$("#"+fieldSign +"-resizeUrl-"+currentPos).attr('title',tempAlt);
	
	$("#"+fieldSign +"-photoDesc-"+targetPos).val($("#"+fieldSign +"-photoDesc-"+currentPos).val());
	$("#"+fieldSign +"-photoDesc-"+currentPos).val(tempPhotoDesc);
	
	$("#"+fieldSign +"-name-show-"+targetPos).val($("#"+fieldSign +"-name-show-"+currentPos).val());
	$("#"+fieldSign +"-name-show-"+currentPos).val(tempShowName);
	
	$("#"+fieldSign +"-photoOutLinkUrl-"+targetPos).val($("#"+fieldSign +"-photoOutLinkUrl-"+currentPos).val());
	$("#"+fieldSign +"-photoOutLinkUrl-"+currentPos).val(tempPhotoOutLinkUrl);
	
	$("#"+fieldSign +"-photoName-"+targetPos).val($("#"+fieldSign +"-photoName-"+currentPos).val());
	$("#"+fieldSign +"-photoName-"+currentPos).val(tempPhotoName);
	
	$("#"+fieldSign +"-resId-"+targetPos).val($("#"+fieldSign +"-resId-"+currentPos).val());
	$("#"+fieldSign +"-resId-"+currentPos).val(tempResId);
		
	$("#"+fieldSign +"-relatePath-"+targetPos).val($("#"+fieldSign +"-relatePath-"+currentPos).val());
	$("#"+fieldSign +"-relatePath-"+currentPos).val(tempRelatePath);
	
	$("#"+fieldSign +"-height-"+targetPos).val($("#"+fieldSign +"-height-"+currentPos).val());
	$("#"+fieldSign +"-height-"+currentPos).val(tempHeight);
	
	$("#"+fieldSign +"-width-"+targetPos).val($("#"+fieldSign +"-width-"+currentPos).val());
	$("#"+fieldSign +"-width-"+currentPos).val(tempWidth);
	
	$("#"+fieldSign +"-height-show-"+targetPos).val($("#"+fieldSign +"-height-show-"+currentPos).val());
	$("#"+fieldSign +"-height-show-"+currentPos).val(tempHeightShow);
	
	$("#"+fieldSign +"-width-show-"+targetPos).val($("#"+fieldSign +"-width-show-"+currentPos).val());
	$("#"+fieldSign +"-width-show-"+currentPos).val(tempWidthShow);
}

/**
 * �л�ͼ������
 */
function setImageGroupCover(fieldSign, pos, filePath)
{
	var imageGroup = document.getElementsByName(fieldSign+'-cover');
	
	var imageCoverCheck;
	for(var i=0; i<imageGroup.length;i++)
	{
		imageCoverCheck = imageGroup[i];
		
		if(imageCoverCheck.id != fieldSign+'-cover-'+pos)
		{
			imageCoverCheck.checked = false;		
		}
	}
	
	var checkedFlag = document.getElementById(fieldSign+'-cover-'+pos).checked;
	
	if(checkedFlag)
	{
		$("#"+fieldSign +"CmsSysImageCover").val(filePath);
	}
	else
	{
		$("#"+fieldSign +"CmsSysImageCover").val('');
	}
}

/**
 * ����ͼƬչʾЧ��
 */
function loadImageShow()
{
	$(".cmsSysShowSingleImage").fancybox
	({
		'overlayShow'	: false,
		'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic'
	});		
	
	$(".videoShow").fancybox
	({
		'overlayShow'	: false,
		'type' : 'swf',  
		'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic'
	});			
}

/**
 * ��ʼ��ͼƬ
 */
function addGroupPhotoToPage(fieldSign, mw, mh, imageCount, url, resizeUrl, resId, reUrl, photoName, height, width, photoDesc)
{
	imageCount = imageCount-1;//��0��ʼ����,���Լ�1

	var uploadImageTr =	 '<div id="'+fieldSign +'-cmsSysDiv-'+imageCount+'" style="height:10px"></div>'
									+'<table id="'+fieldSign +'-uploadPhotoTable-'+imageCount+'" border="0" cellpadding="0" cellspacing="0" class="form-table-upload">'
											+'<tr>'
												+'<td>'													 
													 +'<a id="'+fieldSign +'-cmsSysImageShowUrl-'+imageCount+'" href="'+url+'" class="cmsSysShowSingleImage"><img id="'+fieldSign +'-resizeUrl-'+imageCount+'" src="'+resizeUrl+'" width="131" height="102" title="'+photoName+'"/></a>'
										 			 +'<input type="hidden" id="'+fieldSign +'-resId-'+imageCount+'" name="'+fieldSign +'-resId-'+imageCount+'" value="'+resId+'" />'
										 			 +'<input type="hidden" id="'+fieldSign +'-relatePath-'+imageCount+'" name="'+fieldSign +'-relatePath-'+imageCount+'" value="'+reUrl+'" />'
										 			 +'<input type="hidden" id="'+fieldSign +'-photoName-'+imageCount+'" name="'+fieldSign +'-photoName-'+imageCount+'" value="'+photoName+'" />'	
										 			 +'<input type="hidden" id="'+fieldSign +'-photoOutLinkUrl-'+imageCount+'" name="'+fieldSign +'-photoOutLinkUrl-'+imageCount+'" value="" />'
										 		     +'<input type="hidden" id="'+fieldSign +'-orderFlag-'+imageCount+'" name="'+fieldSign +'-orderFlag-'+imageCount+'" value="'+imageCount+'" />'
										 			 +'<input type="hidden" id="'+fieldSign +'-height-'+imageCount+'" name="'+fieldSign +'-height-'+imageCount+'" value="'+height+'" />'
										 			 +'<input type="hidden" id="'+fieldSign +'-width-'+imageCount+'" name="'+fieldSign +'-width-'+imageCount+'" value="'+width+'" />'
												+'</td>'
												+'<td>'
													+'<table border="0" cellpadding="0" cellspacing="0" height="95px" class="form-table-big">'
														+'<tr>'
															+'<td>'
																+'&nbsp;&nbsp;'
																+'<textarea id="'+fieldSign +'-photoDesc-'+imageCount+'" name="'+fieldSign +'-photoDesc-'+imageCount+'" style="height:65px;width:480px" class="form-textarea">'+photoDesc+'</textarea>'															
																+'&nbsp;&nbsp;&nbsp;<img class="a_pointer" src="../../core/style/icons/arrow-skip-090.png" onclick=\'javascript:changePhotoPos("'+fieldSign+'", ' +imageCount+',"up")\' />'
															+'</td>'
														+'</tr>'
														+'<tr>'
															+'<td>'
															    +'&nbsp;&nbsp;����&nbsp;<input class="form-checkbox" id="'+fieldSign +'-cover-'+imageCount+'" name="'+fieldSign +'-cover" onclick="javascript:setImageGroupCover(\''+fieldSign +'\',\''+imageCount+'\',\''+reUrl+'\');" type="checkbox" value="1"/>&nbsp;'
												    			+'&nbsp;&nbsp;���&nbsp;<input style="width:90px;" class="form-input" id="'+fieldSign +'-name-show-'+imageCount+'" name="'+fieldSign +'-name-show-'+imageCount+'" type="text" value="'+photoName+'"/>&nbsp;'
																+'&nbsp;&nbsp;��&nbsp;'
																+'<input id="'+fieldSign +'-width-show-'+imageCount+'" class="form-input" readonly type="text" style="width:30px;" value="'+width+'"/>'
																+'&nbsp;&nbsp;��&nbsp;'
																+'<input id="'+fieldSign +'-height-show-'+imageCount+'" class="form-input" readonly type="text" style="width:30px;" value="'+height+'"/>'

																//+'&nbsp;<input type="checkbox" class="form-checkbox" />ˮӡ' 
																//+'&nbsp;<input type="checkbox" class="form-checkbox" />����' 
																+'&nbsp;&nbsp;'
																+'&nbsp;&nbsp;&nbsp;<input type="button" value="�滻" onclick="javascript:showEditSingleImageDialog(\''+fieldSign+'\', \''+imageCount+'\')" class="btn-1" />&nbsp;'
															
																+'<input type="button" value="�ü�" onclick="javascript:disposeImage(\''+fieldSign +'\',\''+mw+'\',\''+mh+'\','+true+',\''+imageCount+'\');" class="btn-1" />&nbsp;'
																
																+'<input type="button" value="ɾ��" onclick="javascript:deleteGroupPhotoSingleInfo(\''+fieldSign+'\', \''+imageCount+'\', \''+reUrl+'\');" width="16" height="16" alt="ɾ��ͼƬ" class="btn-1" />'
												
																+'&nbsp;&nbsp;&nbsp;<img class="a_pointer" src="../../core/style/icons/arrow-skip-270.png" onclick=\'javascript:changePhotoPos("'+fieldSign+'", '+imageCount+', "down")\' />'
															+'</td>'
														+'</tr>'
													+'</table>'
												+'</td>'
											+'</tr>'
										+'</table>';
										

                //alert(uploadImageTr);
				//allExistImageInfoArray.push(reUrl);
				
				//alert(allImageGroupSortInfo[fieldSign]);
				
				var sortArray = allImageGroupSortInfo[fieldSign];
				sortArray[imageCount] = imageCount;
				
				$('#'+fieldSign+'CmsSysImageUploadTab').append(uploadImageTr);
				
				//��ǰ��������ID
				$('#'+fieldSign+'CmsSysImageCurrentCount').val(imageCount+1);
}

function deleteGroupPhotoSingleInfo(fieldSign, pos, relatePath)
{
	
	var dialog = $.dialog({ 
   					title :'��ʾ',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '��ȷ��ɾ���ͼƬ��',
                    
                    ok: function () { 
    //��Ϊ���棬��ȥ������
    var checkedFlag = document.getElementById(fieldSign+'-cover-'+pos).checked;

    if(checkedFlag)
 	{
	    document.getElementById(fieldSign+'CmsSysImageCover').value = '';
    }
   
  	$('#'+fieldSign+'-uploadPhotoTable-'+pos).remove();
  	$('#'+fieldSign+'-cmsSysDiv-'+pos).remove();

	var sortArray = allImageGroupSortInfo[fieldSign];
	sortArray[pos] = null;	
	var currentCount = parseInt(document.getElementById(fieldSign+'CmsSysImageCurrentCount').value);
	
	document.getElementById(fieldSign+'CmsSysImageCurrentCount').value = currentCount-1;
	
    document.getElementById(fieldSign+'CmsSysImageGroupCount').innerHTML = document.getElementById(fieldSign+'CmsSysImageCurrentCount').value;
   
    }, 
    cancel: true 
                    
	});
}

function deleteAllGroupPhoto(fieldSign)
{
	var maxPos = document.getElementById(fieldSign+'CmsSysImageArrayLength').value;
	
	var dialog = $.dialog({ 
   					title :'��ʾ',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '��ȷ��ɾ��ͼ��ȫ��ͼƬ��',
                    
                    ok: function () { 
     //ȥ����               	
     document.getElementById(fieldSign+'CmsSysImageCover').value = '';   
                 	
     var pos;       
     
     var sortArray = allImageGroupSortInfo[fieldSign];        	
  
     for(var i = 0; i < maxPos; i++)
     {
     	pos = i;
     
	    
	   
	  	$('#'+fieldSign+'-uploadPhotoTable-'+pos).remove();
	  	$('#'+fieldSign+'-cmsSysDiv-'+pos).remove();
	  	
	  	
	    sortArray[pos] = null;
	  	
     }
     
     document.getElementById(fieldSign+'CmsSysImageCurrentCount').value = 0;
                    	
     document.getElementById(fieldSign+'CmsSysImageGroupCount').innerHTML = document.getElementById(fieldSign+'CmsSysImageCurrentCount').value;
   
    }, 
    cancel: true 
                    
	});
	
}



/**
 * ��ȡָ������ֶ�ָ�����
 */
function getValueFormFieldValInfo(fieldVal, valFlag)
{
	return fieldVal.substring(fieldVal.indexOf(valFlag+'=') + (valFlag.length+1), fieldVal.indexOf( ';' ,fieldVal.indexOf(valFlag+'=') + (valFlag.length+1)));
}


/////////////////////////////////////////////���������������///////////////////////////////////////////////////

function disposeImage(fieldSign, mw, mh, gm, order)
{
	if(typeof(dialogApiId) == 'undefined' || dialogApiId == '')
	{
		var resId = $('#'+fieldSign).val();
	
		if(true == gm)
		{
			var resId = $('#'+fieldSign+'-resId-'+order).val();
		}
	
	    if(resId == '' || resId == '-1' || resId == null)
	    {
	    	$.dialog({ 
	    	title : '��ʾ',
	    	width: '110px', 
	    	height: '60px', 
	    	icon: '32X32/i.png', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: '��ǰû��ͼƬ!'
	     
			});
	    	return;
	    }
	    
	     if(mw == '' || mw == null)
	     {
	     mw = $('#'+fieldSign+'CmsSysImgWidth').val();
	     }
	    
	     if(mh == '' ||  mh == null )
	     {
	     mh = $('#'+fieldSign+'CmsSysImgHeight').val();
	     }
	     
	     
	     if(typeof(mw) == 'undefined' || mw == '')
	     {
	     	mw = 100;
	     }
	     
	     if(typeof(mh) == 'undefined' || mh == '')
	     {
	     	mh = 100;
	     }
		
		$.dialog({ 
		    id : 'di',
	    	title : '�ü�ͼƬ',
	    	width: '1005px', 
	    	height: '565px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/DisposeImage.jsp?fieldSign='+fieldSign+'&mw='+mw+'&mh='+mh+'&fmw='+mw+'&fmh='+mh+'&orgResId='+resId+'&resId='+resId+'&ratio=false'+'&gm='+gm+'&order='+order+'&uid='+Math.random()
	      
	        
	        //content: 'url:'+basePath+'/core/content/index2.jsp?resId='+resId+'&fieldSign='+fieldSign+'&sw='+newCropWH[0]+'&sh='+newCropWH[1]+'&mw='+newCropWH2[0]+'&mh='+newCropWH2[1]
	
		});
		
	}
	else
	{
		var resId = $('#'+fieldSign).val();
	
		if(true == gm)
		{
			var resId = $('#'+fieldSign+'-resId-'+order).val();
		}
		
	    if(resId == '' || resId == '-1' || resId == null)
	    {
	    	W.$.dialog({ 
	    	title : '��ʾ',
	    	width: '110px', 
	    	height: '60px', 
	    	icon: '32X32/i.png', 
	    	parent:api, 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	        cancel:true,
	       
	        content: '��ǰû��ͼƬ!'
	     
			});
	    	return;
	    }
	    
	    
	     if(mw == '' || mw == null)
	     {
	     mw = $('#'+fieldSign+'CmsSysImgWidth').val();
	     }
	    
	     if(mh == '' ||  mh == null )
	     {
	     mh = $('#'+fieldSign+'CmsSysImgHeight').val();
	     }
	     
	     if(typeof(mw) == 'undefined' || mw == '')
	     {
	     	mw = 100;
	     }
	     
	     if(typeof(mh) == 'undefined' || mh == '')
	     {
	     	mh = 100;
	     }
	    
	     
		W.$.dialog({ 
		    id : 'di',
	    	title : '�ü�ͼƬ',
	    	width: '1005px', 
	    	height: '565px',
	    	parent:api, 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/DisposeImage.jsp?fieldSign='+fieldSign+'&mw='+mw+'&mh='+mh+'&fmw='+mw+'&fmh='+mh+'&orgResId='+resId+'&resId='+resId+'&ratio=false'+'&gm='+gm+'&order='+order+'&dialog=true&api='+dialogApiId+'&uid='+Math.random()
	      
	        
	     
		});
		
		
	}
}

function deleteImage(flag)
{
		var old = document.getElementById(flag+'_sys_jtopcms_old');
		
		if(typeof(old) != 'undefined' && old != null)
		{
			document.getElementById(flag+'_sys_jtopcms_old').value = document.getElementById(flag).value;			
		}
		
		
		document.getElementById(flag).value= '-1';
		
		document.getElementById(flag+'CmsSysShowSingleImage').href = basePath+'core/style/blue/images/no-image.gif';
		
		document.getElementById(flag+'CmsSysImgShow').src = basePath+'core/style/blue/images/no-image.gif';
		
		document.getElementById(flag+'CmsSysImgWidth').value = '';
		
		document.getElementById(flag+'CmsSysImgHeight').value = '';
}


function deleteFile(flag)
{
		var old = document.getElementById(flag+'_sys_jtopcms_old');
		
		if(typeof(old) != 'undefined' && old != null)
		{
			document.getElementById(flag+'_sys_jtopcms_old').value = document.getElementById(flag).value;			
		}
				
		document.getElementById(flag).value= '-1';
		
		document.getElementById(flag+'_sys_jtopcms_file_info').value = '';
}

function systemDownloadFile(flag)
{
	var obj = null;
	
	var dialog = false;
	
	obj = document.getElementById(flag);
	
	if(typeof(dialogApiId) == 'undefined' || dialogApiId == '')
	{
		//���ڲ���Ҫ����dialog�����
	}
	else
	{
		dialog = true;
	}
	
	
	if(typeof(obj) != 'undefined' && obj != null)
	{
		var resId = obj.value;
	    
	    if(resId == null || '' == resId ||  resId < 0)
	    {	    	
	    	if(dialog)
	    	{
	    		W.$.dialog.tips('�ļ������ڻ�ʧЧ',1,'32X32/i.png'); 
	    	}
	    	else
	    	{ 
	    		$.dialog.tips('�ļ������ڻ�ʧЧ',1,'32X32/i.png'); 
	    	}
	    	
	    	
	    	return;	    	
	    }
	   
	    window.location = basePath+'resources/clientDf.do?id='+resId;
		
	}
	
}



/**
 * ����UploadVideoModule�Ľ�ͼ����
 */
function cutCover(frmaeId, id)
{
	document.getElementById(frmaeId).contentWindow.snapshotImage(id); 
}

/**
 * չʾ��Ƶ��ͼ
 */
function showCover(fieldSign)
{
	var src = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_src').value;
	
	var type = document.getElementById(fieldSign+'_sys_jtopcms_media_type').value;
    
    if('rm' == type)
    {
    	alert('��ǰ��Ƶ���Ͳ�֧�ֽ�ȡͼƬ!');
    	return;
    }
	
	if('' == src || null == src)
	{	
		$.dialog({ 
   					title :'��ʾ',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '��ǰ��Ƶ�޽�ȡͼƬ!',
    				cancel: true 
		 });
		 return;
	 }
	 
	 var w = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_w').value;
	
	 var h = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_h').value;
	
	 var n = document.getElementById(fieldSign+'_sys_jtopcms_media_cover_n').value;
	 
	 var newWH = checkSize(w, h, 800, 800);
	
	 $.dialog
	 ({ 
    	title: n+' ('+w+' x '+h+')', 
    	lock: true,
    	max: false, 
        min: false,
    	content: '<img src="'+src+'" width="'+newWH[0]+'" height="'+newWH[1]+'" />', 
    	padding: 0 
	 });
}

function showOutLink(obj)
{
	if(obj.checked)
	{
		$('#outlinkTr').show();
	}
	else
	{
		$('#outlinkTr').hide();
	}
}

function getKeywordFromContent()
{
	var text = '';
	
	var content = '';
	try
	{
		//Ŀǰֻ���Ǳ����ժҪ��ȡ�ؼ���
		//content = getEditorTextContents('content');
	}
	catch(ex)
	{
		
	}

	//����content
	if('' != content)
	{
		text = content;
	}
	
	//if('' == text)
	//{
		//text = document.getElementById('summary').value;
	//}
	
	if('' == text)
	{
		text = document.getElementById('title').value;
	}
	
	if('' == text)
	{
		$.dialog({ 
   					title :'��ʾ',
    				width: '190px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '���ⲻ����,�޷���ȡ�ؼ���!',
    cancel: true 
                    
	
	//../../../content/deleteUserDefineContent.do?contentId=${Info.contentId}&classId=${Info.classId}&&modelId=${Info.modelId}��
	});
	
	document.getElementById('keywords').value = '';
	}
	
	//alert(text);
	if('' != text)
	{	
 		var url = basePath+"search/distillKeyword.do";
 		
 		$("#jtopcms_sys_keyword_content").val(text);
		//var postData = encodeURI($("#content,#title").serialize());
		var postData = encodeURI($("#jtopcms_sys_keyword_content").serialize());
		
		postData = encodeData(postData);
 		
 		$.ajax({
      		type: "POST",
       		url: url,
       		data:postData,
   
       		success: function(msg)
            {        
               document.getElementById('keywords').value = msg;
               
               $.dialog.tips('��ȡ�ؼ��ֳɹ�',1,'32X32/succ.png'); 
            }
     	});	
	}
	else
	{
		
	}
}

function textCounter(obj, showid) 
{
    var len = obj.value.length;

	$('#'+showid).html(len);
}

var titleEm = false;
var titleStrong = false;
var titleUnderline = false;

var simpleTitleEm = false;
var simpleTitleStrong = false;
var simpleTitleUnderline = false;


function setTitleChar(obj)
{
	var titleText = document.getElementById('title');
	
	//titleText.style.color = '';
		
	if(obj.id == 'strongTitle')
	{
		if(titleStrong)
		{
			titleStrong = false;
			titleText.style.fontWeight = "";
		}
		else
		{
			titleStrong = true;
			titleText.style.fontWeight = "bold";
		}
	}
	
	if(obj.id == 'emTitle')
	{
		if(titleEm)
		{
			titleEm = false;
			titleText.style.fontStyle = "";
		}
		else
		{
			titleEm = true;
			titleText.style.fontStyle = "italic";
		}
	}
	
	if(obj.id == 'underTitle')
	{
		if(titleUnderline)
		{
			titleUnderline = false;
			titleText.style.textDecoration = "none";
		}
		else
		{
			titleUnderline = true;
			titleText.style.textDecoration = "underline";
		}
	}
	
	//titleText.style.color = document.getElementById('titleColor').value;
}

function setSimpleTitleChar(obj)
{
	var simpleTitleText = document.getElementById('simpleTitle');
	
	//titleText.style.color = '';
		
	if(obj.id == 'strongSimpleTitle')
	{
		if(simpleTitleStrong)
		{
			simpleTitleStrong = false;
			simpleTitleText.style.fontWeight = "";
		}
		else
		{
			simpleTitleStrong = true;
			simpleTitleText.style.fontWeight = "bold";
		}
	}
	
	if(obj.id == 'emSimpleTitle')
	{
		if(simpleTitleEm)
		{
			simpleTitleEm = false;
			simpleTitleText.style.fontStyle = "";
		}
		else
		{
			simpleTitleEm = true;
			simpleTitleText.style.fontStyle = "italic";
		}
	}
	
	if(obj.id == 'underSimpleTitle')
	{
		if(simpleTitleUnderline)
		{
			simpleTitleUnderline = false;
			simpleTitleText.style.textDecoration = "none";
		}
		else
		{
			simpleTitleUnderline = true;
			simpleTitleText.style.textDecoration = "underline";
		}
	}
	
	//simpleTitleText.style.color = document.getElementById('simpleTitleColor').value;
}

function changeTitleColor(titleId,val)
{
	var titleText = document.getElementById(titleId);
	titleText.style.color = val;
}

function showTitleInput(flag1,flag2,obj)
{
	if(obj.checked == true)
	{
		$('#'+flag1+'Tr').show();
		$('#'+flag2+'Tr').show();
	}
	else
	{
		$('#'+flag1+'Tr').hide();
		$('#'+flag2+'Tr').hide();
	}
}

function disposeTitleStyle()
{
	//��ȡtitle��ʽ
	var titleBgColor = document.getElementById('titleBgVal').value;
	
	var titleFontStyle = document.getElementById('title').style.fontStyle;
	
	var titleFontWeight = document.getElementById('title').style.fontWeight;
	
	var titleTextDec = document.getElementById('title').style.textDecoration;
	
	document.getElementById('titleStyle').value = 'color:'+titleBgColor+';font-weight:'+titleFontWeight+';font-style:'+titleFontStyle+';text-decoration:'+titleTextDec;
	
	//��ȡsimpleTitle��ʽ
	titleBgColor = document.getElementById('simpleTitleBgVal').value;
	
	titleFontStyle = document.getElementById('simpleTitle').style.fontStyle;
	
	titleFontWeight = document.getElementById('simpleTitle').style.fontWeight;
	
	titleTextDec = document.getElementById('simpleTitle').style.textDecoration;
	
	document.getElementById('simpleTitleStyle').value = 'color:'+titleBgColor+';font-weight:'+titleFontWeight+';font-style:'+titleFontStyle+';text-decoration:'+titleTextDec;
}





/**
 * ������ѡ�������
 */
function publishContent(type)
{
	if(type != '3')
	{
		$.dialog({ 
   					title :'��ʾ',
    				width: '230px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '��ǰ��Ŀ����Ϊ��̬����,���農̬����', 
        cancel: true 
        });
		
		return;
	}
	
	var cidCheck = document.getElementsByName('checkContent');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	}
	
	if('' == ids)
	{
	   $.dialog({ 
   					title :'��ʾ',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '��ѡ����Ҫ���������ݣ�', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	var url = basePath+"/publish/generateContent.do";
 		
 	$("#someContentId").val(ids);
 	
	var postData = $("#someContentId,#staticType").serialize();
 		
 	$.ajax({
      	type: "POST",
       	url: url,
       	data:postData,
   
       	success: function(msg)
        {        
        	if('success' == msg)
        	{
        		$.dialog({ 
   					title :'��ʾ',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/succ.png', 
    				
                    content: '�������ݳɹ���', 
       				ok: true 
	  			});
	  			
	  			for(var i=0; i<cidCheck.length;i++)
				{
					cidCheck[i].checked = false;
				}
        	}
        	else
        	{
        		$.dialog({ 
   					title :'��ʾ',
    				width: '320px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/succ.png', 
    				
                    content: msg, 
       				cancel: true 
	  			});
        		
        	}
        }
     });	
	
	//alert();
}


/**
 * ѡ�����
 */
function selectRule(rule,target)
{
   var fileRuleName = document.getElementById(target);
   
   var selection = document.selection;
                                
   fileRuleName.focus();
                                
   if (typeof fileRuleName.selectionStart != "undefined")
   {
    	var s = fileRuleName.selectionStart;
         fileRuleName.value = fileRuleName.value.substr(0, fileRuleName.selectionStart) + rule.value + fileRuleName.value.substr(fileRuleName.selectionEnd);
         fileRuleName.selectionEnd = s + rule.value.length;
    } 
    else if (selection && selection.createRange) 
    {
         var sel = selection.createRange();
         sel.text = rule.value;
    } else 
    {
         fileRuleName.value += rule.value;
    }
   
   
   var opts = rule.options;
   
   for(var i=0; i<opts.length; i++)
   {
   		if(opts[i].value == '-1')
   		{
   			opts[i].selected=true;
   			break;
   		}	
   }
}


function selectParamForFCK(obj,id)
{
     var oEditor = FCKeditorAPI.GetInstance(id);
     
     if(obj.value == '' || obj.value == null)
     {
     	return;	
     }
     
     
     if(oEditor.EditMode == FCK_EDITMODE_WYSIWYG)
     {    
      	oEditor.InsertHtml('$'+obj.value);
     } 
}


/**
 * ѡ��ģ��
 */
function openSelectTempletDialog(mode, objId)
{
      var targetName = '';
      
      if('channel' == mode)
      {
      	targetName = '��Ŀ��ҳ';
      }
      else if('class' == mode)
      {
      	targetName = '�б�ҳ';
      }
      else if('content' == mode)
      {
      	targetName = '����ҳ';
      }
       
	  $.dialog({ 
		id:'ostd',
    	title :'ѡ��'+targetName+'ģ��',
    	width: '700px', 
    	height: '466px', 
    	lock: true, 
        max: false, 
        min: false, 
       
        
        resize: false,
             
        content: 'url:'+basePath+'/core/channel/SelectChannelTemplet.jsp?mode='+mode+'&objId='+objId
	});
}



//ѡȡ�������
function openSelectContentSourceDialog()
{
	$.dialog({ 
		    id : 'oscsd',
	    	title : 'ѡȡ��Դ',
	    	width: '380px', 
	    	height: '540px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowContentSource.jsp?uid='+Math.random()
	
	});
}

//ѡȡTag
function openSelectTagDialog()
{
	$.dialog({ 
		    id : 'ostd',
	    	title : 'ѡȡTag��ǩ',
	    	width: '510px', 
	        height: '555px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowSiteTag.jsp?uid='+Math.random()
	
	});
}

//ѡȡվȺ�ڵ�
function openSelectSiteGroupDialog(ids,flag)
{
	$.dialog({ 
		    id : 'ossgd',
	    	title : '���?վȺ',
	    	width: '300px', 
	    	height: '280px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowSiteGroup.jsp?uid='+Math.random()+'&ids='+ids+'&flag='+flag
	
	});
}

//ѡȡ�������
function openSelectRelatedContentDialog(contentId,classId)
{
	$.dialog({ 
		    id : 'osrcgd',
	    	title : 'ѡȡ�������',
	    	width: '740px', 
	    	height: '460px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowRelatedContent.jsp?uid='+Math.random()+'&classId='+classId+'&contentId='+contentId+'&rids='+$('#relateIds').val()
	
	});
}

//ѡȡվ�����
function openSelectSurveyDialog(contentId,classId)
{
	$.dialog({ 
		    id : 'ossd',
	    	title : 'ѡȡվ�����',
	    	width: '600px', 
	    	height: '440px', 
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowRelateSurvey.jsp?uid='+Math.random()+'&classId='+classId+'&contentId='+contentId+'&rsids='+$('#relateSurvey').val()
	});
}


//////////////////////////////////////////////////////��֤ͨ��///////////////////////////////////////////////
/**
 * ͨ�õ��Զ���ģ���ֶ���֤���򣬷ֱ�� focus propertychange blur ���¼�
 */
function validate(filedSign,notNull,reg,msg)
{			
		var obj = $("#"+filedSign);

		if($("#"+filedSign).length < 1)
		{
			obj = $("input[name='"+filedSign+"']");		
		}
		
		if(obj.attr('type') == 'radio' || obj.attr('type') == 'checkbox' )
		{
			filedSign = 'sys-obj-'+filedSign;
		}
	
		obj.bind('focus', function() 
		{	
		
		   // $( 'div.'+filedSign+'_jtop_ui_tips_class' ).remove();
		   

			var target = obj.val();
			
			if(obj.attr('type') == 'radio' || obj.attr('type') == 'checkbox' )
			{
				target = getRadioCheckedValue(filedSign);
			}
			
			if(target != null && target != undefined  )
			{
				target =target.replace(/\\/g,'\\\\');
			}
			
			if(target != null && target != undefined  )
			{
				target =target.replace(/\"/g,'\\"');
			}
			
			if(target != null && target != undefined && target.trim() != '' )
			{			
				if(reg != null && reg !='' )
				{
					if(!eval('regexTest("'+target+'",'+reg+')'))
	        		{
	             		showTips(filedSign,msg);
	        		}      
				}           						
			}
			else if(1 == notNull)
			{
			    showTips(filedSign,'����Ϊ��');
			}
		});
		
		
		obj.bind('keyup', function() 
		{
		
		   $( 'div.'+filedSign+'_jtop_ui_tips_class' ).remove();
			
			var target = obj.val();
		
			if(obj.attr('type') == 'radio' || obj.attr('type') == 'checkbox' )
			{
				target = getRadioCheckedValue(filedSign);
			}
			
			if(target != null && target != undefined  )
			{
				target =target.replace(/\\/g,'\\\\');
			}
			
			if(target != null && target != undefined  )
			{
				target =target.replace(/\"/g,'\\"');
			}
			
			if(target != null && target != undefined && target.trim() != '' )
			{
				if(reg != null && reg !='' )
				{
					if(!eval('regexTest("'+target+'",'+reg+')'))
	        		{
	             		showTips(filedSign,msg);
	        		} 
				}                						
			}
			else if(1 == notNull)
			{
			    showTips(filedSign,'����Ϊ��');
			}
		   
		});

		obj.bind('blur', function() 
		{
			$( 'div.'+filedSign+'_jtop_ui_tips_class' ).remove();
		});
}

function validateExistFlag(target, val)
{
		var url = basePath+"common/sysFlagExist.do?target="+target+'&val='+val;
	  
	    var count = 0;
	  
		$.ajax({
		      	type: "POST",
		       	url: url,
		       	async:false,
		       	data:'',
		   
		       	success: function(msg)
		        {       
		        	count =  msg;
		        }
		 });	
			
		return count;
}


/**
 * ͨ�õ��Զ���ģ���ֶ���֤����,�ύ��֤����,����ͨ��,�����д�����Ϣ��ʾ
 */
function submitValidate(filedSign,notNull,reg,msg)
{				
		var obj = $("#"+filedSign);

		if($("#"+filedSign).length < 1)
		{
			obj = $("input[name='"+filedSign+"']");
			
		}
		
		var target = obj.val();
		
		var editorMode = false;
		
		//fck�༭����Ҫ����ȡֵ
		if ( typeof(FCKeditorAPI) != "undefined" )
		{
			var oEditorx = FCKeditorAPI.GetInstance(filedSign); 
    		 
	    	if(oEditorx != undefined)
	    	{
	    		target =oEditorx.GetXHTML(true);
	    		editorMode = true;
	    	}
		}
     
		
	
	    var hasError = false;
		 
		

 

		if(obj.attr('type') == 'radio' || obj.attr('type') == 'checkbox' )
		{
			target = getRadioCheckedValue(filedSign);
			
			filedSign = 'sys-obj-'+filedSign;
		}
		
		if(editorMode)
		{
			filedSign = 'sys-obj-'+filedSign;
		}
		
		
		if(target != null && target != undefined  )
		{
			target =target.replace(/\\/g,'\\\\');
		}
		
		if(target != null && target != undefined  )
		{
			target =target.replace(/\"/g,'\\"');
		}
		 
	
	    if((target == null || target == undefined || target.trim() == '' ) && 1 == notNull)
		{
				showTips(filedSign,'����Ϊ��');
				hasError = true;
	    }
	    else
	    {
	    	if(reg != null && reg !='' )
	    	{ 
		    	if(!eval('regexTest("'+target+'",'+reg+')'))
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

	var test = !patrn.exec(target);
	
	if (test) 
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
		 offset = 	$("input[name='"+id+"']").offset();

		 if(offset == null)
		 {
			return;
		 }
	}
	
	var y = offset.top-9;
	
	var width = $('#'+id).width();
	
	if(width == null)
	{
		 width = $("input[name='"+id+"']") .width();
		
		 if(width == null)
		 {
			return;
		 }
	}
		
	var x = 0;		
	
	
	if(id.startWith('sys-obj'))
	{
		x = width +5+ offset.left;
	}				 
	else
	{
		x = width +18+ offset.left;
	}
	
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

function hideTips(filedSign)
{
	//$( '.'+id+"_jtop_ui_tips_class" ).hide(); 
	$( 'div.'+filedSign+'_jtop_ui_tips_class' ).remove();
}


function encodeData(data)
{
	temp = data;
	
	temp = temp.replace(/\'/g, "**!1**"); 
	temp = temp.replace(/\(/g, "**!2**"); 
	temp = temp.replace(/\)/g, "**!3**");
		
	temp = replaceAll(temp, '..', "**!4**"  );
	//temp = replaceAll(temp, "\'", "**!5**"  );
	temp = replaceAll(temp, '"', "**!6**"  );
	//temp = replaceAll(temp, '\"', "**!7**"  );
	temp = replaceAll(temp, '<', "**!8**"  );
	temp = replaceAll(temp, '>', "**!9**"  );
	temp = replaceAll(temp, '|', "**!10**"  );
	temp = replaceAll(temp, '\\', "**!11**"  );
	//temp = replaceAll(temp, '+', "**!12**"  );
	temp = temp.replace(/\+/g, "**!12**");	
	//temp = replaceAll(temp, ';', "**!13**"); 
	//temp = temp.replace(/\;/g, "**!13**");
	//temp = replaceAll(temp, '@', "**!14**"); 
	temp = temp.replace(/\@/g, "**!14**");
	temp = replaceAll(temp, '$', "**!15**"); 
	temp = replaceAll(temp, ':', "**!16**"); 
	temp = replaceAll(temp, '/', "**!17**"); 
	temp = replaceAll(temp, ' a', "**!18**"); 
 	temp = replaceAll(temp, ' A', "**!19**"); 

	return temp
}

function encodeFormInput(formName, encodeParam)
{	  
		var uform = document.getElementById(formName);
	
		for(var i = 0; i < uform.length; i++)
		{
		
			if(uform.elements[i].type == 'text' || uform.elements[i].type == 'textarea' || uform.elements[i].type == 'hidden' )
			{
				
				if(uform.elements[i].id == null || uform.elements[i].id =="")
				{
					continue;
				}
	
				encodeForInput(uform.elements[i].id, encodeParam);
				
			}
		}
}


function encodeFormEditor(formName, encodeParam)
{
		var uform = document.getElementById(formName);
		 
		for(var i = 0; i < uform.length; i++)
		{
		
			if(uform.elements[i].type == 'text' || uform.elements[i].type == 'textarea' || uform.elements[i].type == 'hidden' )
			{
				
				if(uform.elements[i].id == null || uform.elements[i].id =="")
				{
					continue;
				}

				if (typeof (document.getElementById(uform.elements[i].id)) == "undefined")
			    {
			  	   return;
			    }
			    
			
			    if(uform.elements[i].id.endWith('_jtop_sys_hidden_temp_html'))
			    {
			    	var editorId = replaceAll(uform.elements[i].id, '_jtop_sys_hidden_temp_html', ''  );
			    	   	 
			    	var temp =  getEditorHTMLContents(editorId);
			    	 
			    	if(temp != null)
			    	{
			    		if(encodeParam)
						{
							temp =  encodeURIComponent(temp);
						}
				  
						clearEditor(editorId);                    
						 
						temp = encodeData(temp);
					 
						document.getElementById(editorId+'_jtop_sys_hidden_temp_html').value = temp;
			    	}
			    }
				
			}
		}
}

function encodeForInput(fieldSign, jsEncode)
{  
	    if (typeof (document.getElementById(fieldSign)) == "undefined")
	    {
	  	   return;
	    }
	    
	
	    if(fieldSign.endWith('_jtop_sys_hidden_temp_html'))
	    {
	    	var editorId = replaceAll(fieldSign, '_jtop_sys_hidden_temp_html', ''  );
	    	
	         	   // setFCKeditorReadOnly(editorId);
	    	var temp =  getEditorHTMLContents(editorId);
	    	
	    	if(temp != null)
	    	{
				if(jsEncode)
				{
					temp =  encodeURIComponent(temp);
				}
				 
				clearEditor(editorId);                    
		
				temp = encodeData(temp);
				
				document.getElementById(editorId+'_jtop_sys_hidden_temp_html').value = temp;

	    	}
	    }
	    else
	    {

			//var temp =  document.getElementById(fieldSign).value;
			var temp =  $('#'+fieldSign).val();
			
			//if('textarea' == $('#'+fieldSign).attr("type"))
			//{
				
				//temp = $('#'+fieldSign).html(); 
				
				//temp = replaceAll(temp, '&lt;', "<"); 
				
				//temp = replaceAll(temp, '&gt;', ">"); 
				
				//temp = replaceAll(temp, '&amp;', "&"); 
				
 			//}
			
			if(temp != null)
			{
				if(jsEncode)
				{
					temp =  encodeURIComponent(temp);
				}
			
			
			    temp = encodeData(temp);
				
				 
				//document.getElementById(fieldSign).value = temp;
				$('#'+fieldSign).val(temp);
			}
	    }

			

}



/////////////////////////////////////////////FCK�༭��API////////////////////////////////////////////////

//���ñ༭��������
function setContents(codeStr){
   var oEditor = FCKeditorAPI.GetInstance(content) ;
   oEditor.SetHTML(codeStr) ;
}
 
// ��ȡ�༭����HTML����
function getEditorHTMLContents(EditorName) { 
    var oEditor = FCKeditorAPI.GetInstance(EditorName); 
    return(oEditor.GetXHTML(true)); 
}
// ��ȡ�༭������������
function getEditorTextContents(EditorName) { 
    var oEditor = FCKeditorAPI.GetInstance(EditorName); 
     
    if (document.all) 
    {  
        return(oEditor.EditorDocument.body.innerText); 
    }  
    else 
    {  
        var r = oEditor.EditorDocument.createRange();  
        r.selectNodeContents(oEditor.EditorDocument.body);  
        return r; 
     }  
    
}
// ���ñ༭��������
function SetEditorContents(EditorName, ContentStr) { 
	try
	{
    var oEditor = FCKeditorAPI.GetInstance(EditorName) ; 
    oEditor.SetHTML(ContentStr) ; 
	}
	catch(e)
	{
	}
}


function clearEditor(EditorName)
{
	if(FCKeditorAPI.GetInstance(EditorName).EditorDocument != null)
	{
		FCKeditorAPI.GetInstance(EditorName).EditorDocument.body.innerHTML='';
	}
	else
	{
		SetEditorContents(EditorName,'');
	}
}

//��༭������ָ������
function insertHTMLToEditor(content,codeStr){
   var oEditor = FCKeditorAPI.GetInstance(content);
   if (oEditor.EditMode==FCK_EDITMODE_WYSIWYG){
     oEditor.InsertHtml(codeStr);
   }else{
     return false;
   }
}


/*����FCKEDITORΪֻ�� */ 
function setFCKeditorReadOnly( editorId ){ 
	 var editor = FCKeditorAPI.GetInstance(editorId);
try{ 
editor.EditorDocument.body.contentEditable = false; 
editor.EditMode=FCK_EDITMODE_SOURCE; 
editor.ToolbarSet.RefreshModeState(); 
editor.EditMode=FCK_EDITMODE_WYSIWYG; 
editor.ToolbarSet.RefreshModeState(); 
editor.EditorWindow.parent.document.getElementById('xExpanded').style.display = 'none'; 
editor.EditorWindow.parent.document.getElementById('xCollapsed').style.display = 'none'; 
editor.EditorWindow.blur(); 
} 
catch(e){ 
} 
}


/////////////////////////////////////////////������ʽ////////////////////////////////////////////////
//У���Ƿ�ȫ���������

//У��Flag��ֻ������1-25����ĸ�����֡��»���  
function isFlag(s)  
{  
var patrn=/^(\w){1,25}$/;  
if (!patrn.exec(s)) return false
return true
} 

function isDigit(s)
{
var patrn=/^[0-9]{1,20}$/;
if (!patrn.exec(s)) return false
return true
}

//У���¼��ֻ������5-20������ĸ��ͷ���ɴ����֡���_������.�����ִ�

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


//У���û�����ֻ������1-30������ĸ��ͷ���ִ�

function isTrueName(s)  
{  
var patrn=/^[a-zA-Z]{1,30}$/;  
if (!patrn.exec(s)) return false
return true
}  


//У�����룺ֻ������6-20����ĸ�����֡��»���  
function isPasswd(s)  
{  
var patrn=/^(\w){6,20}$/;  
if (!patrn.exec(s)) return false
return true
}  


 
//У����ͨ�绰��������룺���ԡ�+����ͷ���������⣬�ɺ��С�-��  
function isTel(s)  
{  
//var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?(\d){1,12})+$/;  
var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;  
if (!patrn.exec(s)) return false
return true
}  

//У���ֻ���룺���������ֿ�ͷ���������⣬�ɺ��С�-��  
function isMobile(s)  
{  
var patrn=/^1[3|4|5|8][0-9]\d{4,8}$/; 
if (!patrn.exec(s)) return false
return true
}  
 
//У����������  
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
* ��ʾ��ʾ��Ϣ
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
var reg_d_ymd_hms = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})\s*(\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
var reg_d_hms = /^((20|21|22|23|[0-1]\d)\:[0-5][0-9])(\:[0-5][0-9])?$/;

var reg_cc = /^[\u0391-\uFFE5]+$/;
var reg_ec = /^[a-zA-Z]+$/;
var reg_dit = /^[-+]?\d*$/;
var reg_double = /^[-\+]?\d+(\.\d+)?$/;
var reg_email  =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var reg_url = /^(https|http|ftp|rtsp|mms):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;










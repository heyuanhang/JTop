basePath = '';

//��չString��һЩ����

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
	$('#checkCodeImg').attr('src',basePath+'common/authImg.do?count=4&line=1&point=60&width=90&height=24&jump=2&ra='+Math.random());

}


function submitGBInfo()
{
	var text =  '';
	
	text =  getRadioCheckedValue('mh_lylx');

    if(text == null)
	{
		alert("��ѡ��һ���������ͣ�");
		return;
	}
	
	
	text =  document.getElementById('gbMan');
	
    if(text.value.length < 3)
	{
		alert("��������ȷ������Ϣ��");
		return;
	}
	
	text =  document.getElementById('gbTitle');
	
    if(text.value.length < 10)
	{
		alert("�����벻��10�ֵ����Ա��⣡");
		return;
	}
	
	text =  document.getElementById('gbText');
	
    if(text.value.length < 20)
	{
		alert("������������ı�������٣�");
		return;
	}
	else if(text.value.length > 2500)
	{
		alert("������������ı������2500�֣�");
		return;
	}
	
	text =  document.getElementById('sysCheckCode');
	
    if(text.value.length < 4)
	{
		alert("��������ȷ��֤�룡");
		return;
	}
	
	var url = basePath+'guestbook/clientAddGb.do';
	//������encodeURI�����Ѿ�������=
	var postData =  encodeURI($("#gbForm").serialize()) ;
	
	 postData = postData.replace(/\+/g, " ");
	 postData = encodeData(postData);
	    
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		async : false,
			       		data:postData,
			   
			       		success: function(msg)
			            {     
			            	
			            	
			            	if('1' == msg)
			            	{		            		
			            		alert('������Գɹ�,��ȴ�վ�����Ա����!');
			            		
			            		window.location.reload();
			            	}
			            	else if('-1' == msg)
			            	{
			            		alert('ϵͳ�쳣,����ʧ��!');
			            	}
			            	else if('-2' == msg)
			            	{
			            		alert('�����������Ϊ��!');
			            	}
			            	else if('-3' == msg)
			            	{
			            		alert('��֤�����!');
			            	}
			            	else if('-4' == msg)
			            	{
			            		alert('���Ա��ⲻ����Ϊ��!');
			            	}
			            	else if('-5' == msg)
			            	{
			            		alert('ֻ�л�Ա�ſ�����!');
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
		alert("��ѡ��һ��Ͷ�ݸ�λ��");
		return;
	}
	
	
	text =  document.getElementById('jt_jl_xm');
	
    if(text.value.length < 3)
	{
		alert("��������ȷ������Ϣ��");
		return;
	}
	
	text =  document.getElementById('jt_jl_xl');
	
    if(text.value == -1)
	{
		alert("��ѡ�����ѧ��");
		return;
	}
	
	 
	
	text =  document.getElementById('jt_jl_jieshao');
	
    if(text.value.length < 20)
	{
		alert("������ĸ��˽���������٣�");
		return;
	}
	else if(text.value.length > 2500)
	{
		alert("������ĸ��˽��������2500�֣�");
		return;
	}
	
	text =  document.getElementById('sysCheckCode');
	
    if(text.value.length < 4)
	{
		alert("��������ȷ��֤�룡");
		return;
	}
	
	var url = basePath+'content/clientAddFormData.do';
	//������encodeURI�����Ѿ�������=
	var postData =  encodeURI($("#gbForm").serialize()) ;

	 postData = postData.replace(/\+/g, " ");
	 postData = encodeData(postData);
	    
	 $.ajax({
			      		type: "POST",
			       		url: url,
			       		async : false,
			       		data:postData,
			   
			       		success: function(msg)
			            {     
			            	 	 
			            	if('1' == msg)
			            	{		            		
			            		alert('��ļ���Ͷ�ݳɹ�,��ȴ�վ�����Ա����!');
			            		
			            		window.location.reload();
			            	}
			            	 
			            	else if('-2' == msg)
			            	{
			            		alert('�����Ϊ��!');
			            	}
			            	else if('-1' == msg)
			            	{
			            		alert('��֤�����!');
			            	}
			            	 
			            	else if('-5' == msg)
			            	{
			            		alert('ֻ�л�Ա�ſ�����!');
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
			   
			       		success: function(msg)
			            {     
			            	if('1' == msg)
			            	{
			            		  alert('������ɹ�');
			            		  window.location.reload();
			            	}
			            	
			            	else if('-1' == msg)
			            	{
			            		  alert("���Ѿ����ȹ���ƪ����");
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
			   
			       		success: function(msg)
			            {     
			            	if('1' == msg)
			            	{
			            		  alert('�������ɹ�');
			            		  window.location.reload();
			            	}
			            	
			            	else if('-1' == msg)
			            	{
			            		  alert("���Ѿ���������");
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
			   
			       		success: function(msg)
			            {     
			            	if('1' == msg)
			            	{
			            		  alert('�����۱�����ɹ�');
			            		  window.location.reload();
			            	}
			            	
			            	else if('-6' == msg)
			            	{
			            		  alert("���Ѿ����ȹ���ƪ����");
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
			function(data, textStatus)
			{
				//alert(data);
				if(data === '0')
				{
					alert('�����ظ�ͶƱ����л��Ĳ���!');
				}
				else if(data === '1')
				{
					alert('�ѳɹ�ͶƱ��лл��Ĳ���!');
					window.location.href=window.location.href;
					return;
				}
				else if(data === '-1')
				{
					alert('����ȷ������֤��!');
				}
				else if(data === '-2')
				{
					alert('ͶƱ�Ѿ�ʧЧ!');
				}
				else if(data === '-3')
				{
					alert('ͶƱ��Ч!');
				}
				
				
  			}
  		});
}




 

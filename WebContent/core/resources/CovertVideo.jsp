<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />

		<title></title>
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script language="javascript" type="text/javascript" src="../javascript/commonUtil_src.js"></script>
	 
		<script>
			basePath = '<cms:BasePath/>';
			
		 	var api = frameElement.api, W = api.opener; 
		 	
			if("true"==="${param.fromFlow}")
         	{         
      			var api = frameElement.api, W = api.opener; 
		          
             	api.close();     
            	 W.window.location.reload();
        	 }
        	 
			
			
		</script>
	</head>
	<body>
		<cms:SystemSiteResource resId="${param.id}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
			<tr>
				<td width="21%" class="input-title">
					<strong>当前视频</strong>
				</td>
				<td class="td-input">
					<input readonly type="text"  style="width:279px" class="form-input" value="${Res.resName}.${Res.fileType}" />
					<span class="ps"></span>
				</td>
			</tr>
			<tr>
				<td  class="input-title">
					<strong>转换格式</strong>
				</td>
				<td class="td-input">
					 <select id="ft" name="ft" class="form-select">
					 	<option value="mp4">
					 		mp4
					 	</option>
					 	<option value="flv">
					 		flv&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	</option>
					  
					 </select>
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <span class="td-input"><strong>转换质量</strong></span>
					 <select id="qual" name="qual" class="form-select">
					 	<option value="3">
					 		原画
					 	</option>
					 	<option value="4">
					 		超高
					 	</option>
					 	<option value="6">
					 		极高
					 	</option>
					 	<option value="8">
					 		很高
					 	</option>
					 	<option value="10" >
					 		高级
					 	</option>
					 	<option value="12" selected>
					 		中级
					 	</option>
					 	<option value="13">
					 		低级 
					 	</option>
					 	<option value="14">
					 		很低 
					 	</option>
					 	<option value="16">
					 		极低
					 	</option>
					 	<option value="20">
					 		超低&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	</option>
					 </select>
				</td>
			</tr>
		</table>

		<div style="height:15px;"></div>
		<div class="breadnavTab"  >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="btnbg100">
					<div style="float:right">
						<a href="javascript:covert();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
						<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16" /><b>关闭&nbsp;</b> </a>
					</div>
				</tr>
			</table>
		</div>

		</table>





		<div id="divProcessing" style="width:200px;height:30px;position:absolute;left:150px;top:110px;display:none">
			<img style="vertical-align: middle;" src="../javascript/dialog/skins/icons/loading.gif" width=28 height=28 class="ui_icon_bg" />
			
			<span id="uploadPercent">正在转换视频...... 0%</span>
		</div>
	</body>
</html>

<script type="text/javascript">

var timeId = -1;

function covert()
{
	if('${Res.fileType}' == $('#ft').val())
	{
		 W.$.dialog.tips('目标格式不可和当前视频相同...', 2);
		 
		 return;
	}
	
	timeId = setInterval("showRTStatus()",150);
	
	var url = "<cms:BasePath/>resources/covrertVideo.do"+"?<cms:Token mode='param'/>";
	
 	var postData = 'resId=${param.id}&ft='+$('#ft').val()+'&qual='+$('#qual').val();
 	
  					
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,
   
       	success: function(mg)
        {    
        	var msg = eval("("+mg+")");
        	
           if('success' == msg)
           {         
           	   
           	           		
           } 	
           else
           {
           	   W.$.dialog(
												   { 
													   					title :'提示',
													    				width: '200px', 
													    				height: '60px', 
													                    lock: true, 
													                    parent:api,
													                     
													    				icon: '32X32/fail.png', 
													    				
													                    content: "执行失败，无权限请联系管理员！",
											
													    				cancel: true
													});
           }   
        }
 	});	


} 
function showRTStatus()
{
   $('#divProcessing').show();
   
   var url = '<cms:BasePath/>resources/checkCovrertVideo.do?flag=covert_video';

   $.ajax
   (
	   {type:'POST',async:false,url:url,success:
		   function(da, textStatus)
		   {      
		   		var data = eval("("+da+")");
		   		
		   	  if(data <= 100 && data != -1)
		   	  {
		   	  	$('#uploadPercent').html( '正在转换视频...... '+data+'%');
		   	  }
		   	  else if(data > 100 || data == -1)
		   	  {	
		   	  	 W.window.location.reload();
		   	  }
		   	  
		        
		     
		    }
		}
	);
	

}



function close()
{
	var api = frameElement.api, W = api.opener; 	          
    api.close();   
    W.window.location.reload();   
}
				
									
</script>
</cms:SystemSiteResource>

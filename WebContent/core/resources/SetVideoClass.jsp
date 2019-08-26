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

			
		</script>
	</head>
	<body>
		 
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
			 
			<tr>
				<td width="22%" class="input-title">
					<strong>所属栏目</strong>
				</td>
				<td class="td-input">
					<select class="form-select" id="classId" name="classId" style="width:240px" >
										 
										<option value="-9999">
											全站共用视频&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</option>
										 
											<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
												<cms:SysClass>
													<option value="${Class.classId}">
														${Class.layerUIBlankClassName}
													</option>
												</cms:SysClass>
											</cms:SystemClassList>
										 
									</select>
					<span class="ps"></span>
				</td>
			</tr>
		</table>

		<div style="height:15px;"></div>
		<div class="breadnavTab"  >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" >
				<tr class="btnbg100">
					<div style="float:right">
						<a href="javascript:setClass();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
						<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16" /><b>关闭&nbsp;</b> </a>
					</div>
				</tr>
			</table>
		</div>

		</table>





		 
	</body>
</html>

<script type="text/javascript">

 

function setClass()
{
	 
	var url = "<cms:BasePath/>resources/setVideoClass.do?<cms:Token mode='param'/>";
	
 	var postData = 'ids=${param.ids}&classId='+$('#classId').val();
 			
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,
   
       	success: function(mg)
        {    
        	var msg = eval("("+mg+")");
        	
           if('success' == msg)
           {         
           	   
           	      W.window.location.reload();     		
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
 


function close()
{
	var api = frameElement.api, W = api.opener; 	          
    api.close();   
    W.window.location.reload();   
}
				
									
</script>
 

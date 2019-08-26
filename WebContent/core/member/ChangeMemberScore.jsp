<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script>
		basePath='<cms:BasePath/>';
		
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
          
            api.close(); 
 
         }
         
         var ref_flag=/^(\w){1,25}$/; 
         
         var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         $(function()
		 {
		    validate('actName',0,ref_name,'格式为文字,数字,下划线');
 		 		
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/cake.png" width="16" height="16" />
						积分变动
					</div>

					<form id="saForm" name="saForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


							<tr>
								<td width="21%" class="input-title">
									<strong>积分行为</strong>
								</td>
								<td class="td-input">
									<select id="flag" name="flag">
										<option value="a">
											--- 增加积分 ---
										</option>
										<option value="d">
											--- 减少积分 ---
										</option>
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="input-title"><strong>积分变化值</strong></span>&nbsp;<input type="text" size="10" id="score" name="score" class="form-input" value="1"/>&nbsp;分

								</td>
							</tr>
							
						


				 





						</table>
						<!-- hidden -->


					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="btnbg100">
								<div style="float:right">
									<a id="submitScoreFormBut" href="javascript:submitSaForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" id="submitTagClassFormImg" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
								</div>

							</tr>
						</table>

					</div>


				</td>
			</tr>


		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  
   //showTips('modelName','不可为空');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   


function submitSaForm()
{
	
	
	
	$("#submitTagClassFormBut").attr("disabled","disabled"); 
	$("#submitTagClassFormImg").attr("disabled","disabled"); 
	
	var url = "<cms:BasePath/>member/changeMemberScore.do?ids=${param.ids}"+"&<cms:Token mode='param'/>";
 	var postData = encodeURI($("#saForm").serialize());
 					
	$.ajax({
  		type: "POST",
   		 url: url,
   		data: postData,

       	success: function(mg)
        {     
       		var msg = eval("("+mg+")");
       		
           if('success' == msg)
           {
           		W.$.dialog({ 
	   					title :'提示',
	    				width: '150px', 
	    				height: '60px', 
	    				parent:api,
	                    lock: true, 
	    				icon: '32X32/succ.png',
	                    content: '改动会员积分成功！', 
	                    ok: function(){ 
      						W.window.location.reload();
    					} 
		  		});
           		
           } 	
           else
           {
           						$("#submitTagClassFormBut").removeAttr("disabled"); 
								$("#submitTagClassFormImg").removeAttr("disabled"); 
           
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
								
								$("#submitTagClassFormBut").removeAttr("disabled"); 
								$("#submitTagClassFormImg").removeAttr("disabled"); 
           }   
        }
 	});	


}
   

  
</script>

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
						<img src="../style/icons/address-book-open.png" width="16" height="16" />
						规则信息
					</div>
					
					<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberSingleScoreActForTag" objName="Sa" var="${param.id}">

					<form id="saForm" name="saForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

							 
							<tr>
								<td width="25%" class="input-title">
									<strong>规则名称</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:293px" id="actName" name="actName" class="form-input" value="${Sa.actName}"></input>

								</td>
							</tr>
							<tr>
								<td width="23%" class="input-title">
									<strong>目标动作</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:293px" id="targetCmd" name="targetCmd" class="form-input" value="${Sa.targetCmd}"></input>

								</td>
							</tr>
							
							
							<tr>
								<td class="input-title">
									<strong>积分行为</strong>
								</td>
								<td class="td-input">
									<select id="actFlag" name="actFlag">
										<option value="1">
											--- 增加积分 ---
										</option>
										<option value="0">
											--- 减少积分 ---
										</option>
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="input-title"><strong>积分变化值</strong></span>&nbsp;<input type="text" size="10" id="actScore" name="actScore" class="form-input" value="${Sa.actScore}"/>&nbsp;分

								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>扩展行为类</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:293px" id="actClass" name="actClass" class="form-input" value="${Sa.actClass}"></input>
									
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>描叙</strong>
								</td>
								<td class="td-input">
									<textarea id="actDesc" name="actDesc" style="height:60px;width:295px" class="form-textarea">${Sa.actDesc}</textarea>
								</td>
							</tr>


				 





						</table>
						<!-- hidden -->
						<input type="hidden" id="saId" name="saId" value="${Sa.saId}"/>
						
						<cms:Token mode="html"/>

					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="btnbg100">
								<div style="float:right">
									<a id="submitTagClassFormBut" href="javascript:submitSaForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" id="submitTagClassFormImg" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();" class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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

initSelect('actFlag','${Sa.actFlag}');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   


function submitSaForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('actName',0,ref_name,'格式为文字,数字,下划线');

   		if(currError)
        {
        	hasError = true;
        }

    			
    if(hasError)
    {
    	$("#submitFormBut").removeAttr("disabled"); 
	    $("#submitFormImg").removeAttr("disabled"); 
	    
	    return;

	}
	
	
	
	
	$("#submitTagClassFormBut").attr("disabled","disabled"); 
	$("#submitTagClassFormImg").attr("disabled","disabled"); 
	
	var url = "<cms:BasePath/>member/editScoreAct.do";
 	var postData = encodeURI($("#saForm").serialize());
 	
 	postData = encodeData(postData);
 					
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
	                    content: '编辑积分规则成功！', 
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
</cms:QueryData>

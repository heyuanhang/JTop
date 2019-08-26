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
		    validate('accName',0,ref_name,'格式为文字,数字,下划线');
 		 		
		 })
    
      	</script>
	</head>
	<body>

		<cms:QueryData service="cn.com.mjsoft.cms.security.service.SecurityService" method="getSingleMemberAccRuleForTag" objName="MemAcc" var="${param.accId}">
										
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/pencil-ruler.png" width="16" height="16" />规则细节
					</div>
					
					<form id="ruleForm" name="ruleForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td class="input-title">
									<strong>规则类型</strong>
								</td>
								<td class="td-input">
									<input class="form-radio" type="radio" name="typeId" checked value="1" />
									栏目浏览规则&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input class="form-radio" type="radio" name="typeId" value="2" />
									会员投稿规则

								</td>
							</tr>
							<tr>
								<td width="23%" class="input-title">
									<strong>规则名称</strong>
								<br /></td>
								<td class="td-input">
									<input type="text" style="width:295px" id="accName" name="accName" class="form-input" value="${MemAcc.accName}"></input>
									 
								<br /></td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>描叙</strong>
								</td>
								<td class="td-input">
									<textarea id="ruleDesc" name="ruleDesc" style="height:50px;width:295px" class="form-textarea">${MemAcc.ruleDesc}</textarea>
								</td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>操作会员组</strong>
								<br /></td>
								<td class="td-input">
									<select id="roleIds" name="roleIds" class="form-select" multiple  style="width:300px;height:150px;">
										<cms:MemberRole >
										<option value="${Role.roleId}">
										  ${Role.roleName}
										</option>
										</cms:MemberRole>
										
									
									</select>
									<span class="ps">(按住Ctrl多选)</span>
								<br /></td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>最小等级</strong>
								<br /></td>
								<td class="td-input">
									<select id="minLever" name="minLever" class="form-select" style="width:120px;">
										<option value="-1">
										   ------ 无等级 ------
										
										</option>
										
										<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberRankInfoForTag" objName="Rank"  >
											<option value="${Rank.rankLevel}">
											   ${Rank.rankName}										
											</option>
											
									    </cms:QueryData>
										 
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp;<span class="input-title"><strong>最小积分</strong></span>&nbsp;<input type="text" style="width:100px;" id="minScore" name="minScore" class="form-input" value="${MemAcc.minScore}"></input>
									
								<br /></td>
							</tr>
							
							<tr>
								<td class="input-title">
									<strong>条件关系</strong>
								<br /></td>
								<td class="td-input">
									<input class="form-radio" type="radio" name="eft" checked value="1"/>任一条件满足即可&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="form-radio" type="radio" name="eft" value="2"/>必须全部达到条件

									
								<br /></td>
							</tr>
							
							 



						</table>
						<!-- hidden -->
					    <input type="hidden" id="accRuleId" name="accRuleId" value="${MemAcc.accRuleId}"/>
					    
					    <cms:Token mode="html"/>
						 
					</form>
					
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a id="submitTagClassFormBut" href="javascript:submitTagClassForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" id="submitTagClassFormImg"/><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
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

var rIds = '${MemAcc.roleIds}';

var ridArray = rIds.split(',');

for(var i = 0; i < ridArray.length; i++)
{
	initSelect('roleIds',ridArray[i]);
}

initRadio('typeId','${MemAcc.typeId}');

initSelect('minLever','${MemAcc.minLever}');

initRadio('eft','${MemAcc.eft}');
   
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   


function submitTagClassForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('accName',0,ref_name,'格式为文字,数字,下划线');

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
	
	var url = "<cms:BasePath/>security/editAccRule.do"+"?<cms:Token mode='param'/>";
 	var postData = encodeURI($("#ruleForm").serialize());
 					
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
	                    content: '编辑浏览规则成功！', 
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

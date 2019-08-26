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
		    validate('rankName',0,ref_name,'格式为文字,数字,下划线');
 		 		
		 })
    
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/crown.png" width="16" height="16" />
						等级信息
					</div>
					<cms:QueryData service="cn.com.mjsoft.cms.member.service.MemberService" method="getMemberSingleRankForTag" objName="Rank" var="${param.id}">
					<form id="rankForm" name="rankForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">

							 
							<tr>
								<td width="25%" class="input-title">
									<strong>等级名称</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:293px" id="rankName" name="rankName" class="form-input" value="${Rank.rankName}"></input>

								</td>
							</tr>
							<tr>
								<td width="23%" class="input-title">
									<strong>等级值</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:293px" id="rankLevel" name="rankLevel" class="form-input" value="${Rank.rankLevel}"></input>

								</td>
							</tr>
							
							
							<tr>
								<td class="input-title">
									<strong>起始积分</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:95px" id="minScore" name="minScore" class="form-input" value="${Rank.minScore}"></input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="input-title"><strong>结束积分</strong></span>&nbsp;<input type="text" style="width:95px" id="maxScore" name="maxScore" class="form-input" value="${Rank.maxScore}"></input>

								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>描叙</strong>
								</td>
								<td class="td-input">
									<textarea id="rankDesc" name="rankDesc" style="height:60px;width:295px" class="form-textarea">${Rank.rankDesc}</textarea>
								</td>
							</tr>


				 





						</table>
						<!-- hidden -->
						<input type="hidden" id="rankId" name="rankId" value="${Rank.rankId}"/>
						
						<cms:Token mode="html"/>

					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="btnbg100">
								<div style="float:right">
									<a id="submitTagClassFormBut" href="javascript:submitRankForm();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" id="submitTagClassFormImg" /><b>确认&nbsp;</b> </a>
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
   //showTips('modelName','不可为空');
   
var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
}
   
   


function submitRankForm()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('rankName',0,ref_name,'格式为文字,数字,下划线');

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
	
	var url = "<cms:BasePath/>member/editRank.do";
 	var postData = encodeURI($("#rankForm").serialize());
 	
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
	                    content: '编辑会员等级成功！', 
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

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
		 var selectedTargetClassId = '';
		 
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            //W.$.dialog.tips('添加模型步骤成功...',1); 
            api.close(); 
         	//api.reload( api.get('cwa') );        
<%--       		W.window.location.reload();--%>
         }
         
         //表格变色
			$(function()
			{ 
		   		$("#showlist tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
			});  
         
      	</script>
	</head>
	<body>


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/user-business-gray-boss.png" width="16" height="16" />人员信息
					</div>

					<div style="height:4px"></div>

					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">

						<tr class="datahead">
							<td width="2%">
							</td>

							<td width="40%">

								<strong> 名称 </strong>

							</td>

							<td width="50%">

								<strong> 真实姓名</strong>

							</td>


						</tr>
						<cms:SystemUserList orgCode="${param.orgCode}">
							<cms:SystemUser>
								<tr>
									<td>
										<input type="radio" name="checkUser" value="${SysUser.userId}" />
									</td>

									<td>

										&nbsp;${SysUser.userName}

									</td>

									<td>

										${SysUser.userTrueName}

									</td>
								</tr>
							</cms:SystemUser>
						</cms:SystemUserList>
						<cms:Empty flag="SysUser">
							<tr>
								<td class="tdbgyew" colspan="7">
									<center>
										当前没有数据!
									</center>
								</td>
							</tr>
						</cms:Empty>

					</table>
					<div style="height:2px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitOrgBoss();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确定&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16" /><b>取消&nbsp;</b> </a>
								</div>

							</tr>

						</table>
					</div>

				</td>
			</tr>


		</table>


		<input type="hidden" id="cids" name="cids" value="${param.ids}" />

		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

//init
initRadio('checkUser','${param.currBossId}');


var api = frameElement.api, W = api.opener;
  
function close()
{
	api.close();
	W.window.location.reload(); 
}

function submitOrgBoss()
{
    	    var uid = getRadioCheckedValue('checkUser');
    	    
    	    if(uid == null)
    	    {
    	    	close();
    	    }
    
			var url = "<cms:BasePath/>organization/setOrgBoss.do?uid="+uid+"&orgId=${param.orgId}"+"&<cms:Token mode='param'/>";
		 		
		 	$.ajax({
		      	type: "POST",
		       	url: url,
		       	data:'',
		   
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
		                    content: '设定主管成功！', 
		                    ok: function(){ 
	      						W.window.location.reload();
	    					} 
			  			});
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
	     
	     	return;  
	    
    	api.close();
      
}
   

   

  
</script>

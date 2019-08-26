<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>

		<script>  
		var hasError = false;
		//验证
		$(window).load(function()
		{
			$("#configName").bind('focus', function() 
			{
				var target = $("#posName").val();
				//alert(target);
			    if(target == '')
				{
					hasError = true;
  					showTips('posName','不可为空');
  				}
  				else
  				{
  					hasError = false;
  				}
			});	
			
			$("#posName").bind('propertychange', function() 
						{
						   $( 'div.posName_jtop_ui_tips_class' ).remove();
  							
							var target = $("#posName").val();

    						if(target == '')
    						{
    							hasError = true;
    							showTips('posName','不可为空');               					
  							}
  							else
  							{
  								hasError = false;
  							}
  							
  							
						});
						
						
			
		
		})
	
	     var api = frameElement.api, W = api.opener; 
		
		 function showErrorMsg(msg)
		 {
		
		    W.$.dialog(
		    { 
		   					title :'提示',
		    				width: '190px', 
		    				height: '60px', 
		                    lock: true, 
		                    parent:api,
		    				icon: '32X32/i.png', 
		    				
		                    content: msg,

		    				cancel: true
			});
			
		}
      
	
		 
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	             api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             W.location.reload();
         	}
       		       
         }
         
        	
      </script>
	</head>
	<body>

		

		

		<form id="advertPosForm" name="advertPosForm" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">

						<!--main start-->
						<div class="auntion_tagRoom" style="margin-top: 5px;">
							<ul>
								<li id="two1" onclick="setTab2('two',1,2)" class="selectTag">
									<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />站点内容&nbsp;</a>
								</li>
								<li id="two2" onclick="setTab2('two',2,2)">
									<a href="javascript:;"><img src="../style/blue/icon/application-search-result.png" width="16" height="16" />编辑推送&nbsp;</a>
								</li>




							</ul>
						</div>

						<div class="auntion_tagRoom_Content">
							<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
								<ul>
									<li>
										 
										 
										 
										<iframe width="100%" height="510" id="treeFrame" src="DisplaySiteContent.jsp" scrolling=no></iframe>
 

  
										<div style="height:5px;"></div>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr class="btnbg100">
												<td class="input-title" width="35%"></td>
												<td class="td-input">
													<a id="buttonHref" href="javascript:submitSelectInfo();" class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>
													<a href="javascript:close();" class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
												</td>
											</tr>
										</table>
									</li>
								</ul>
							</div>

							<!-- 第二部分:步骤动作 -->
							<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
								<div style="height:10px;"></div>
								<ul>
									<li>

									</li>
								</ul>
							</div>








						</div>

					</td>
				</tr>
			</table>

			<!-- hidden -->
			<input type="hidden" name="posConfigCount" id="posConfigCount" value="4" />

			<input type="hidden" name="conConfigCount" id="conConfigCount" value="4" />

		</form>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">


function submitSelectInfo()
{
	var classTreeWindow = document.getElementById('treeFrame').contentWindow.document.getElementById('treeFrame').contentWindow.document.getElementById('classTree').contentWindow;
	alert(classTreeWindow.radioValue);


}

  
function close()
{
	api.close();
}



 


</script>


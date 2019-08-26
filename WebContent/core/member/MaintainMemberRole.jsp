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
		
		//去掉点击链接 虚线边框
      
		
	
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
	             W.$.dialog.tips('改动会员角色权限成功',1.5,'32X32/succ.png'); 
         	}
       		       
         }
         
         //表格变色
			$(function()
			{ 
		   		$("#showlistclass tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
				
				$("#showlistspec tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
				
				$("#showlistcommend tr[id!='pageBarTr']").hover(function() 
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
		<cms:CurrentSite>
			<cms:MemberRole id="${param.roleId}">
				

				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<div class="auntion_tagRoom" style="margin-top:2px">
								<ul>
									<li id="two1" onclick="setTab('two',1,1)" class="selectTag">
										<a href="javascript:;"><img src="../style/icons/gear.png" width="16" height="16" />菜单权限&nbsp;</a>
									</li>
									 


								</ul>
							</div>

							<form id="roleAuthForm" name="roleAuthForm" method="post">
								<div class="auntion_tagRoom_Content">
									<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
										<ul>
											<li>
												<cms:MemberResourceList roleId="all">
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
														<tr>
															<td class="input-title">

															</td>
															<td width="100%" class="td-input">
																<a href="javascript:regRes('checkAll');" class="btnwithico"> <img src="../style/icons/tick.png" width="16" height="16" /> <b>全选&nbsp;</b> </a>
															</td>
														</tr>

														<tr>
															<td class="input-title">

															</td>
															<td class="td-input">
																<cms:MemberResource>
																	
																	<cms:if test="${Res.resourceType == 1}">
																	<!-- 系统入口 -->
																		<div class="addtit">
																			<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
																			 
																			<strong>${Res.resourceName}</strong>
																		</div>
																		<br />
																	</cms:if>
																	<cms:elseif test="${Res.resourceType == 3}">
																	   	<!-- 菜单 -->										
																		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																		<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
																		 
																		${Res.resourceName}
																		<br />
																		<cms:if test="${Res.isLeaf == 0}">
																		</cms:if>
																	</cms:elseif>
																	<cms:elseif test="${Res.resourceType == 4}">
																		<!-- 组合 -->
																		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;										
																		<input type="checkbox" name="checkResource" id="${Res.linearOrderFlag}-checkRes-${Res.secResId}" value="${Res.secResId}-${Res.resourceType}" onclick="javascript:regRes(this);" />
																		${Res.resourceName}
																		<br/>
																		<cms:if test="${Res.isLastChild == 1}">
																			<br />															 
																		</cms:if>
																	</cms:elseif>
																</cms:MemberResource>
																
															</td>
														</tr>
													</table>
												</cms:MemberResourceList>
												<cms:Empty flag="Res">
												
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
														
														<td class="tdbgyew">
															<center>
																当前没有数据!
															</center>
														</td>
													</tr>
			
													</tr>
														
														</table>
													
													
			
												</cms:Empty>

												<div style="height:40px;"></div>
												<div class="breadnavTab"  >
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
														<tr class="btnbg100">
															<div style="float:right">
																<a href="javascript:submitRoleAuthForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"/><b>确认&nbsp;</b> </a>
																<a href="javascript:close();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"/><b>返回&nbsp;</b> </a>
															</div>
														</tr>
													</table>
												</div>
											</li>
										</ul>
									</div>

									

								<!-- hidden -->
								<input type="hidden" id="roleId" name="roleId" value="${Role.roleId}"/>
								<input type="hidden" id="targetSiteId" name="targetSiteId" value="${param.siteId}"/>
								<input type="hidden" id="tab" name="tab" value="${param.tab}"/>
								
								<cms:Token mode="html"/>
							</form>
						</td>
					</tr>
				</table>

				<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

//初始化系统菜单功能 开始
var checkedIdMap = new HashMapJs();
var haveAllResIdArray = new Array();
var allResIdArray = new Array();




  
//当前角色所拥有的资源
<cms:MemberResourceList roleId="${Role.roleId}">
   <cms:MemberResource>
   	haveAllResIdArray.push("${Res.linearOrderFlag}-checkRes-${Res.secResId}");
   </cms:MemberResource>
</cms:MemberResourceList>


//当前角色所有可以选择的资源
<cms:MemberResourceList roleId="all">
   <cms:MemberResource>
   	allResIdArray.push("${Res.linearOrderFlag}-checkRes-${Res.secResId}");
   </cms:MemberResource>
</cms:MemberResourceList>
 

var targetRes;

//加载已拥有粗粒度资源:在所有已经被选择的资源里寻找
for(var i =0; i < haveAllResIdArray.length; i++)
{

   targetRes = document.getElementById(haveAllResIdArray[i]);
   //若id存在
   if(targetRes != null)
   {
   	targetRes.checked=true;
   	//regRes(targetRes);
   }
}

var selectAllFlag = false;

//注册系统资源checkBox方法,结合树型结构
function regRes(resBox)
{

  if(resBox==="checkAll" && selectAllFlag==false)
  {
      selectAllBox('checkResource');
      selectAllFlag = true;
  }
  
  else if(resBox==="checkAll" &&  selectAllFlag==true)
  {
      unSelectAllBox('checkResource');
      selectAllFlag = false;
  }
  
  else
  {

     //单一资源注册
     //alert(resBox.checked);
     

      var currentIdFlag = resBox.id.split('-')[0];
      var testResId = "";
  	  for(var i =0; i < allResIdArray.length; i++)
		{
		   targetRes = document.getElementById(allResIdArray[i]);
		   //若id存在,将此ID注册在页面
		   if(targetRes != null)
		   {
		      testResId = allResIdArray[i]+"";
		     
		     // alert(testResId + " : " + currentIdFlag);
		   	  if(testResId.startWith(currentIdFlag))
		   	  {
		   	  	 if(resBox.checked == true)
		   	  	 {
		   	  		targetRes.checked=true;
		   	  	 }
		   	     else
		   	     {
		   	        targetRes.checked=false;
		   	     }
		   	  }

		    }
    
		}
	
		
		if(resBox.checked == true)
		{
			//所有父节点check
			var linearCount = currentIdFlag.length / 3;
			var linear;
			var pos=0;
		    for(var j=0; j<linearCount;j++)
			{
			    linear = currentIdFlag.substring(pos,pos + ((j+1) * 3));

			    for(var k =0; k < allResIdArray.length; k++)
				{
				    testResId = allResIdArray[k]+"";
				    targetRes = document.getElementById(allResIdArray[k]);
				    
				    if(targetRes != null)
			        {
					    if(testResId.startWith(linear+"-"))
						{
						   	if(resBox.checked == true)
						   	{
						   	    targetRes.checked=true;
						   	}
						   	else
						   	{
						   	    targetRes.checked=false;
						   	}
						 }
					}
			    }
			}
	   } 
   }
  
  
}
//系统粗粒度资源初试化  --end--






//此处初始化tab
var tab = '${param.tab}';

if('' != tab)
{
	setTab2('two',tab,4);
}

function setTab(mod,pos,count)
{
	document.getElementById('tab').value = pos;
	setTab2('two',pos,count);
}



function submitRoleAuthForm()
{
	var roleAuthForm = document.getElementById('roleAuthForm');
	roleAuthForm.action = '../../security/matainMemberRoleAuth.do';
	roleAuthForm.submit();
}



function close()
{
	api.close(); 
	W.window.location.reload();

}

</script>
</cms:MemberRole>
</cms:CurrentSite>

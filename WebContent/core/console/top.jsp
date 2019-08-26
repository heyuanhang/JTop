<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title></title>
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<link href="../style/blue/css/default.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>

		<script type="text/javascript">
		var currentTabId = '';		
		
		var defaultTarget="/core/console/menu.jsp";
		
		
		function initFirstTargetResource(target,resId)
		{   
	
		  target=defaultTarget;	
		  
		  var targetFrame = window.parent.document.getElementById("leftMenu");
		 
		  targetFrame.src="<cms:Domain/>"+target+"?parResId="+resId+"&random="+Math.random();   
		}
		
		</script>

	</head>

	<cms:LoginUser>
		<body>
			<cms:CurrentSite>
				<table width="100%" class="top">
					<tr>
						<td id="toppage" align="right" valign="top"  >
						<script>
						$('#toppage').attr('style','width:'+(window.screen.width * 0.155-9)+'px')
						</script>
						
							<div class="logo">
								<span class="height53">Version 3.0 &nbsp;版&nbsp;(MySQL)</span>
							</div>
						</td>
						<td align="left">
							<div class="topinfor">
								<div class="Black10"></div>
								当前管理员:
								<span> ${Auth.apellation}</span>， 您当前登录在

								<select id="currentSite" onchange="javascript:changeCurrentSite();" class="form-select">
									<cms:SystemOrgSite orgId="${Auth.orgIdentity}">
										<option value="${OrgSite.siteId}">
											${OrgSite.siteName}&nbsp;&nbsp;
										</option>
									</cms:SystemOrgSite>
									<cms:Empty flag="OrgSite">
										<option value="-9999">
											--- 无站点信息 ---&nbsp;&nbsp;
										</option>
									</cms:Empty>
								</select>

								<script>
							
							 initSelect('currentSite','${CurrSite.siteId}');
								
						</script>

							</div>
							<div class="nav">
								<ul>
									<cms:SecurityResourceList parentId="-1" type="1">
										<cms:SecurityResource >

											<cms:if test="${status.index == 0}">

												<li>
													<a class="curr" id="mainTab${Resource.secResId}" href="javascript:void(0)" onmousedown="javascript:dierctTargetResource('${Resource.target}','${Resource.secResId}',this);">${Resource.resourceName} </a>
												</li>
												<script>
										currentTabId = "mainTab${Resource.secResId}";
										
										if('/core/console/ListClassFramePage.jsp' != '${Resource.target}')
										{
											 
											initFirstTargetResource('${Resource.target}','${Resource.secResId}');
										}
										else
										{
										 	 if('true' != '${param.notReload}')
										 	 {
											  
											  var targetFrame = window.parent.document.getElementById("leftMenu");
											 
											  targetFrame.src="<cms:BasePath/>core/console/ListClassFramePage.jsp?parResId=${Resource.secResId}&random="+Math.random(); 
											  
											  } 
										}
										
									</script>
											</cms:if>
											<cms:else>

												<li>
													<a id="mainTab${Resource.secResId}" href="javascript:void(0)" onmousedown="javascript:dierctTargetResource('${Resource.target}','${Resource.secResId}',this);">${Resource.resourceName} </a>
												</li>
											</cms:else>

										</cms:SecurityResource>
									</cms:SecurityResourceList>
								</ul>
							</div>
							<div class="header-ico">
								<ul>
									<li>
										<a class="ico-out" href="javascript:loginout();">注销</a>
									</li>
									<li>
										<a class="ico-key-solid" href="javascript:changePassword('${Auth.identity}','${Auth.apellation}');">修改密码</a>
									</li>
									<li>
										<a class="ico-user" href="javascript:editUser('${Auth.identity}','${Auth.apellation}');">个人信息</a>
									</li>
 
									
									<li>
										<a class="ico-wb" href="javascript:gotoWorkbench();">我的工作台</a>
									</li>
									
									<li>
										
										<a href="javascript:openMsgDialog();" style="position:relative;" class="ico-mail">站内信件</a>
										<span id="msgShow" style="font-size:11px" class="mailnum">9+</span>
									</li>
								</ul>
							</div>
						</td>
					 
					</tr>
				</table>


				<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
		</body>
</html>
<script type="text/javascript">

function loginout()
{
		 

		 
 	    
	     
		var url = "<cms:BasePath/>login/postLogin.do?action=LoginOut&<cms:Token mode='param'/>";
        
         
     
           
		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data: '',
				   
				       		success: function(mg)
				            {     
				            	var msg = eval("("+mg+")");
				            	
				               if('success' == msg)
				               {
				               		 window.location.href = "<cms:BasePath/>core/SystemManager/login/page.jsp";
				               		
				               }
				                
				              
				            }
		});	

      
     
}

function dierctTargetResource(target,resId,selectTab)
{   

  if(null == target || '' == target.trim())
  {
      target=defaultTarget;
  }
  
  var curTab = document.getElementById(currentTabId);
  if(curTab != null)
  {
  	curTab.className  = '';
  }
  selectTab.className  = 'curr';
  currentTabId= selectTab.id;

  //var targetFrame = window.parent.document.all[id='leftFrame'];
  var targetFrame = window.parent.document.getElementById("leftMenu");
 
  targetFrame.src="<cms:Domain/>"+target+"?parResId="+resId+"&random="+Math.random();   
}



function changeCurrentSite()
{
   var siteId = document.getElementById('currentSite').value;
	
   var url = "<cms:BasePath/>security/changeLoginUserSite.do?siteId="+siteId+"&random="+Math.random();
 
   $.ajax({
   type:'POST',
   async:false,
   url:url,
   success:
  	function(data, textStatus)
  	{
  		 var msg = eval("("+data+")");
  	 	 if('success' == msg)
  	 	 {
  	 	 	window.parent.window.location.reload();
  	 	 }
  	}
  });
  

}

function mainPage()
{
	window.parent.location='<cms:BasePath/>core/console/main_blue.jsp'
}

function goMessagePage()
{
	  var targetFrame = window.parent.document.getElementById("main");

      targetFrame.src="<cms:Domain/>core/message/ManageSiteMessage.jsp";  

}

checkMotReadMessageCount();
setInterval("checkMotReadMessageCount()",20*1000);

function checkMotReadMessageCount()
{
	var url = "<cms:BasePath/>message/getNotReadMsgCount.do";
 					
	$.ajax({
  		type: "POST",
   		url: url,
   		data: '',
		dataType: 'json',
       	success: function(msg)
        {      
           var count = parseInt(msg);
      
           if(count >9)
           {
           		$('#msgShow').html('9+');
           }
           else if(count > 0)
           {  
           		$('#msgShow').show();
           	    $('#msgShow').html(count);
           }
           else
           {
           		$('#msgShow').hide();
           }
           
        }
 	});	

}




function editUser(userId, userName)
{
   $.dialog({ 
	    id : 'oesr',
    	title : '当前登录管理员 - '+userName,
    	width: '620px',  
    	height: '560px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/security/EditSystemUser.jsp?userId='+userId

	});
}

function changePassword(userId, userName)
{
	$.dialog({ 
	    id : 'ocpd',
    	title : '修改密码 - '+userName,
    	width: '530px', 
    	height: '185px',  
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/security/ChangePassword.jsp?userId='+userId
	});

}


function openMsgDialog()
{
	$.dialog({ 
	    id : 'omsgd',
    	title : '站内消息',
    	width: '1200px', 
    	height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/message/ManageSiteMessageDialog.jsp?dialog=true'
	});

}

function gotoWorkbench()
{
			window.parent.parent.document.getElementById('main').src="<cms:Domain/>core/console/Workbench.jsp?isReply=0";
			
		 
}

   
</script>
</cms:CurrentSite>
</cms:LoginUser>

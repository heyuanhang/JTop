<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />	
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../style/layui/css/layui.css"  media="all">
			
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>

		<script language="JavaScript" src="../javascript/chart/fc/js/FusionCharts.js"></script>
		
		<script src="../javascript/chart/echarts/echarts.js"></script> 
   
   		<script src="../javascript/chart/echarts/theme/macarons.js"></script> 
		

		<script>  
		basePath = '<cms:BasePath/>';
        //表格变色
			$(function()
			{ 
		   		$("#showlist1 tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
				
				$("#showlist2 tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
				
				$("#showlist3 tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
				
				$("#showlist4 tr[id!='pageBarTr']").hover(function() 
		   		{ 
					$(this).addClass("tdbgyew"); 
				}, 
				function() 
				{ 
					$(this).removeClass("tdbgyew"); 
				}); 
			}); 
			
			function setTab(tab)
			{
				setTab2('two',tab,5)
			} 	
			
			
			
function applyAudit()
{
	if(-1 == currentSelectId)
	{
		$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需申请审核内容!',
                    
                   
       			    cancel: true 
		});

		return;
	}
	
	var url = "<cms:BasePath/>workflow/applyAudit.do?contentId="+currentSelectId+"&<cms:Token mode='param'/>";
 		
	$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {     
				             	var msg = eval("("+mg+")");
				             	
				               if('success' == msg)
				               {
				               		 $.dialog({ 
						   					title :'提示',
						    				width: '200px', 
						    				height: '60px', 
						                    lock: true, 
						    				icon: '32X32/succ.png', 
						    				
						                    content: '申请审核权成功!',
						                    
						                   
						       				ok: function()
						       				{
						       					window.location = 'Workbench.jsp?tab=2';
						       				}
						
									});
				               		
				               		 
				               } 	
				               else if('fail' == msg)
				               {
				               		$.dialog({ 
						   					title :'提示',
						    				width: '190px', 
						    				height: '60px', 
						                    lock: true, 
						    				icon: '32X32/fail.png', 
						    				
						                    content: '当前内容已经拥有审核人!',
						                    
						                   
						       				cancel: true 
						
									});
				               	   
				               }   
				              
				            }
	});	
}

function applyAuditGb()
{
	if(-1 == currentSelectGbId)
	{
		$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择需申请审核留言!',
                    
                   
       			    cancel: true 
		});

		return;
	}
	
	var url = "<cms:BasePath/>workflow/applyAudit.do?infoType=2&contentId="+currentSelectGbId+"&<cms:Token mode='param'/>";
 		
	$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {     
				             	var msg = eval("("+mg+")");
				             	
				               if('success' == msg)
				               {
				               		 $.dialog({ 
						   					title :'提示',
						    				width: '200px', 
						    				height: '60px', 
						                    lock: true, 
						    				icon: '32X32/succ.png', 
						    				
						                    content: '申请审核权成功!',
						                    
						                   
						       				ok: function()
						       				{
						       					window.location = 'Workbench.jsp?tab=3';
						       				}
						
									});
				               		
				               		 
				               } 	
				               else if('fail' == msg)
				               {
				               		$.dialog({ 
						   					title :'提示',
						    				width: '190px', 
						    				height: '60px', 
						                    lock: true, 
						    				icon: '32X32/fail.png', 
						    				
						                    content: '当前内容已经拥有审核人!',
						                    
						                   
						       				cancel: true 
						
									});
				               	   
				               }   
				              
				            }
	});	
}

function openViewWFSteoInfoDialog()
{
	if(currentSelectId == -1)
	{
		$.dialog({ 
   					title :'提示',
    				width: '190px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要查阅流程日志的内容!',
                    
                   
       					cancel: true 

		});
		return;
	}

	$.dialog
	({
   					title :'查看审核流程   <标题> '+document.getElementById('SPTitle-'+currentSelectId).value,
    				width: '1080px', 
    				height: '550px', 

    				lock:true,
        			max: false, 
        			min: false,
        			resize: false,
    				
                    content: 'url:<cms:BasePath/>core/workflow/ViewWorkflowOperInfo.jsp?cid='+currentSelectId
	});

}



function readContent(mr, ps, pm, cid,ccid,mid,operStatus,man,tn)
{
	

	var tid;
	
	var tcid;
	
	var tmid;
	
	if('' == cid)
	{
		if(currentSelectId == -1)
		{
			$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/i.png', 
	    				
	                    content: '请选择要审核的内容!',
	                    
	                   
	       					cancel: true 
	
			});
			return;
		}
		
		tid = currentSelectId;
		
		tcid = currentClassId;
		
		tmid = currentModelId;
	}
	else
	{
		tid = cid;
		
		tcid = ccid;
		
		tmid  = mid;
	}
	
	
	
	
	
	
	<cms:LoginUser>
		var currentManager = '${Auth.apellation}';
	</cms:LoginUser>
	
	 
	
	if('1' == mr)
	{
		if('0' == ps)
		{
			$.dialog({ 
	   				title :'提示',
    				width: '180px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '当前步骤需要 [申请审核权] 才可进行审核操作！',
	                    
	                   
	       			cancel: true 
	
			});
			return;
		}
		 
	}
	
	 currentProcessManager = man;
	
   if(currentProcessManager != '' && currentManager != currentProcessManager)
   {
	     $.dialog({ 
   					title :'提示',
    				width: '300px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '内容审核权已由 <font color="red"> '+ tn+' </font> 获得!',
                    
                   
       				cancel: true 

		});
		return;
   }
	
	//window.location = "<cms:BasePath/>core/content/CheckContentAndCensor.jsp?contentId="+tid+"&classId="+tcid+"&modelId="+tmid;
	
	$.dialog({ 
		id : 'main_content',
    	title :'审核内容',
    	width:   '1200px', 
    	 height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: "url:<cms:BasePath/>core/content/CheckContentAndCensor.jsp?contentId="+tid+"&classId="+tcid+"&modelId="+tmid+'&innerWidth=1200'
             
  	});
}



function filterAction(value)
{
	if(value==4)
	{
		window.location='AuditContentList.jsp?personalReject=true';
	}
}

function regId(check,modelId,classId,possessStatus, currentMan)
{
   if(check.checked==true)
   {
       
      var pervCheck = document.getElementById("check"+currentSelectId);
     
      if(pervCheck != null)
      {
      	pervCheck.checked=false;
      }
      currentSelectId=check.value;
      currentProcessManager = currentMan;
      currentModelId = modelId;
      currentClassId = classId;
      
      if(possessStatus == 1)
	  {
	  	disableAnchorElement('applyAudit',true);
	  	document.getElementById('applyAudit').style.cursor="not-allowed";
	  	document.getElementById('applyAudit-b').style.cursor="not-allowed";
	  	
	  }
   }
   else
   {
      currentSelectId = -1;
      currentProcessManager = '';
      currentModelId = -1;
      currentClassId = -1;
      
      disableAnchorElement('applyAudit',false);
      document.getElementById('applyAudit').style.cursor="default";
	  document.getElementById('applyAudit-b').style.cursor="default";
   }
   
   
    
} 


 



function regGbId(check,modelId,classId,possessStatus, currentMan)
{
   if(check.checked==true)
   {
      
      var pervCheck = document.getElementById("checkGb"+currentSelectGbId);
    
      if(pervCheck != null)
      {
      	pervCheck.checked=false;
      }
      currentSelectGbId=check.value;
      currentProcessManagerGb = currentMan;
      currentModelIdGb = modelId;
      currentClassIdGb = classId;
      
      if(possessStatus == 1)
	  {
	  	disableAnchorElement('applyAuditGb',true);
	  	document.getElementById('applyAuditGb').style.cursor="not-allowed";
	  	document.getElementById('applyAuditGb-b').style.cursor="not-allowed";
	  	
	  }
   }
   else
   {
      currentSelectGbId = -1;
      currentProcessManagerGb = '';
      currentModelIdGb = -1;
      currentClassIdGb = -1;
      
      disableAnchorElement('applyAuditGb',false);
      document.getElementById('applyAuditGb').style.cursor="default";
	  document.getElementById('applyAuditGb-b').style.cursor="default";
   }
   
   
  
}

/****************  以下留言区域   ***************/


function openEditGuestbookDialog(configFlag,cfgId,gbId)
{
	$.dialog({ 
	    id : 'oegbd',
    	title : '留言回复',
    	width: '1000px', 
    	height: '700px',
    	lock: true, 
    	max: false,
        min: false,
        resize: false,
        
        content: 'url:<cms:Domain/>core/guestbook/ReplyAndEditGuestbook.jsp?configFlag='+configFlag+"&gbId="+gbId+"&configId="+cfgId
	});
}


function changeGbInfoStatus(action,flag)
{
	var ids = document.getElementsByName('checkIds');
		
	var idArray = new Array();
	for(var i = 0; i < ids.length; i++)
	{
		if(ids[i].checked == true)
		{
			idArray.push(ids[i].value);
		}
	}
	
	var id = idArray.join(',');

	if(id == null || id == '')
	{
	
		$.dialog
		(
	  		{ 
				title :'提示',
			    width: '130px', 
				height: '60px', 
				lock: true, 
				icon: '32X32/i.png', 
									    				
				content: "没有选择留言",
							
				cancel: true
			}
		);	
		return;							  
	}
	
	
	$.dialog({ 
   					title :'提示',
    				width: '165px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认改变留言状态吗？',
                    
                    ok: function () {
           				var url = "<cms:BasePath/>guestbook/changeStatus.do?action="+action+"&flag="+flag+'&id='+id+"&configId="+cfgId+"&<cms:Token mode='param'/>";
 		
				 		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {     
				            
				            	var msg = eval("("+mg+")");
           		
				               if('success' == msg)
				               {
				               		//showMsg('留言状态改动成功!');
				               		window.location.reload();
				               		//W.$.dialog.tips('留言状态改动成功...',1); 
				               		
				               		 
				               } 	
				               else
				               {
				               	    showMsg('改动失败!');
				               }   
				              
				            }
				     	});	
       
    
    				}, 
    				cancel: true 
    });
	
	
}


function deleteGuestbook(id)
{
	if(id == null)
	{
		var ids = document.getElementsByName('checkIds');
		
		var idArray = new Array();
		for(var i = 0; i < ids.length; i++)
		{
			if(ids[i].checked == true)
			{
				idArray.push(ids[i].value);
			}
		}
		id = idArray.join(',');
	}
	
	
	if(id == null || id == '')
	{
	
		$.dialog
		(
	  		{ 
				title :'提示',
			    width: '130px', 
				height: '60px', 
				lock: true, 
				icon: '32X32/i.png', 
									    				
				content: "没有选择留言",
							
				cancel: true
			}
		);	
		return;							  
	}
	
	
	$.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选留言吗？',
                    
                    ok: function () {
           				var url = "../../guestbook/deleteGb.do?id="+id+"&cfgFlag="+$('#configFlag').val()+"&configId="+cfgId+"&<cms:Token mode='param'/>";
 		
				 		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {        
				            
				            	var msg = eval("("+mg+")");
           		
				            	if('success' == msg)
				            	{ 
									replaceUrlParam(window.location, 'isReply=0&tab=3');
				            	}
				            	else
				            	{
				            		$.dialog(
								    { 
								   					title :'提示',
								    				width: '165px', 
								    				height: '60px', 
								                    lock: true, 
								    				icon: '32X32/fail.png', 
								    				
								                    content: "删除失败，请联系管理员！",
						
								    				cancel: true
									});
				            	}
				            	
				             
				            }
				     	});	 
       
    
    				}, 
    				cancel: true 
    });
}



//共享内容


function deleteShareContent()
{
	var ids= '';
	
	var cidCheck = document.getElementsByName('checkShareId');
			 
	for(var i=0; i<cidCheck.length;i++)
	{
				if(cidCheck[i].checked)
				{
					ids += cidCheck[i].value+',';
				}
	}
			 
	if('' == ids)
	{
			   $.dialog({ 
		   					title :'提示',
		    				width: '180px', 
		    				height: '60px', 
		                    lock: true, 
		    				icon: '32X32/i.png', 
		    				
		                    content: '请选择需要删除的共享内容！', 
		       				cancel: true 
			  });
			  return;
	}			
		
		var url = "<cms:BasePath/>content/deleteShareContent.do?ids="+ids+"&<cms:Token mode='param'/>";
	 			
	 	$.ajax({
	      	type: "POST",
	       	url: url,
	       	data:'',
	       	
	       	success: function(mg)
	        {
	        	var msg = eval("("+mg+")");
           		
	        	if(mg.indexOf('您没有操作权限!') != -1)
	        	{
	        					$.dialog(
							   { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/fail.png', 
								    				
								                    content: "执行失败，无权限请联系管理员！",
						
								    				cancel: true
								});
								
								return;
	        	
	        	
	        	} 
	        
	               
	        	if('' != msg)
	        	{
	        		$.dialog({ 
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/succ.png', 
	    				
	                    content: '删除成功！', 
	       				ok: function(){ 
      						replaceUrlParam(window.location, 'isReply=0&tab=3');
    					} 
		  			});
		  			
	        	}
	        	else
	        	{
	        					$.dialog(
							   { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/fail.png', 
								    				
								                    content: "删除操作失败！",
						
								    				cancel: true
								});
	        	
	        	
	        	}
	        }
	     });	
    

}



function changeFilter()
{
	var configFlag = document.getElementById('configFlag').value;
	var isOpen = document.getElementById('isOpen').value;
	var isReply = document.getElementById('isReply').value;
	var isCensor= document.getElementById('isCensor').value;
	
	window.location.href = 'Workbench.jsp?configFlag='+configFlag+'&isOpen='+isOpen+'&isReply='+isReply+'&isCensor='+isCensor+'&tab=3';	
}

function openShareContentToSiteClassDialog(flag, modelId, refClassIdStr, cid)
{
	if(cid == '' && ('-1' == modelId || '' == modelId))
	{		
		 $.dialog({ 
		   					title :'提示',
		    				width: '180px', 
		    				height: '60px', 
		                    lock: true, 
		    				icon: '32X32/i.png', 
		    				
		                    content: '请指定一个内容模型！', 
		       				cancel: true 
		});
	    return;
	}
	
	var ids= '';
	
	if(cid != '')
	{
		ids = cid;
	}
	else
	{
		var cidCheck = document.getElementsByName('checkShareId');
				
		for(var i=0; i<cidCheck.length;i++)
		{
					if(cidCheck[i].checked)
					{
						ids += cidCheck[i].value+',';
					}
		}
				
		if('' == ids)
		{
				   $.dialog({ 
			   					title :'提示',
			    				width: '180px', 
			    				height: '60px', 
			                    lock: true, 
			    				icon: '32X32/i.png', 
			    				
			                    content: '请选择需要投送的共享内容！', 
			       				cancel: true 
				  });
				  return;
		}
	}
	
	$.dialog({ 
	    id : 'osctcd',
    	title : '投送内容至栏目',
    	width: '400px', 
    	height: '500px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:'+basePath+'core/content/dialog/ShowShareContentClass.jsp?uid='+Math.random()+'&cid='+ids+'&modelId='+modelId+'&refClassIdStr='+refClassIdStr+'&flag='+flag
	});
}



function changeFilterShare()
{
	var shareModelId = document.getElementById('shareModelId').value;
	
	
	replaceUrlParam(window.location,'modelId='+shareModelId+'&tab=4');
}

//稿件区域

function changeFilterDraft()
{
	var dClassId = document.getElementById('dClassId').value;
	
	var dCensor = document.getElementById('dCensor').value;
	
	var author = document.getElementById('author').value;
	
	
	
	replaceUrlParam(window.location,'dCensor='+dCensor+'&dClassId='+dClassId+'&author='+encodeURIComponent(author)+'&tab=5');
}

function gotoEditUserDefineContentPage(linkId, contentId, classId, modelId, creator, censor)
{
	<cms:LoginUser>
		var currentManager = '${Auth.apellation}';
	</cms:LoginUser>
	
	if(censor == '-1' && creator != currentManager)
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '只能处理自己的稿件！',
                                      
       cancel: true 
                    
	   });
       return;
	}
	
	if(linkId != '' && linkId != '-1')
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '170px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '引用来的内容无法编辑！',
                                      
       cancel: true 
                    
	   });
       return;
	}
	
	
	$.dialog({ 
		id : 'main_content',
    	title :'审核内容',
    	width:   '1200px', 
    	 height: (window.parent.document.body.scrollHeight-80 )+'px', 
    	lock: true, 
        max: false, 
        min: false,
        
        resize: false,
             
        content: "url:<cms:BasePath/>core/content/EditUserDefineModelContent.jsp?fromDraft=true&pn=${param.pn}&dClassId="+classId+"&contentId="+contentId+"&classId="+classId+"&modelId="+modelId+"&uid="+Math.random()+'&innerWidth=1200'
             
     	});


 }


function deleteSelectContent()
{
	var cidCheck = document.getElementsByName('checkedDId');
	
	var ids='';
	for(var i=0; i<cidCheck.length;i++)
	{
		if(cidCheck[i].checked)
		{
			ids += cidCheck[i].value+',';
		}
	}
	
	if('' == ids)
	{
	   $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '请选择要删除的内容！', 
       cancel: true 
                    
	
	  });
	  return;
	}
	
	deleteContent('',ids);

}


//选取相关内容
function openSelectCSDialog()
{
	$.dialog({ 
		    id : 'oscsd',
	    	title : '选取来源',
	    	width: '430px', 
	    	height: '550px', 
	     
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ShowContentSource.jsp?mode=wb&uid='+Math.random()
	
	});
}

function openContentOperLogDialog(contentId, title)
{
	var ti = '';
	
	ti = title.replaceAll('<','&lt;');
	ti = ti.replaceAll('>','&gt;');

	$.dialog({ 
		    id : 'ocold',
	    	title : '操作记录 - '+ti,
	    	width: '1100px', 
	    	height: '780px',  
	    	lock: true, 
	        max: false, 
	        min: false,
	        resize: false,
	       
	        content: 'url:'+basePath+'/core/content/dialog/ViewContentOperInfo.jsp?uid='+Math.random()+'&contentId='+contentId
	
	});

}



function deleteContent(modelId,ids, creator, censor)
{
	<cms:LoginUser>
		var currentManager = '${Auth.apellation}';
	</cms:LoginUser>
	
	if(censor == '-1' && creator != currentManager)
	{
		var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '只能处理自己的稿件！',
                                      
       cancel: true 
                    
	   });
       return;
	}


	var dialog = $.dialog({ 
   					title :'提示',
    				width: '160px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/i.png', 
    				
                    content: '您确认删除所选内容吗？',
                    
                    ok: function () 
                    { 
                    
                    //var url = "<cms:BasePath/>content/deleteContent.do?ids="+ids+"&modelId="+modelId;
                    var url = "<cms:BasePath/>content/deleteMySelfSiteContentToTrash.do?ids="+ids+"&modelId="+modelId+"&<cms:Token mode='param'/>";;
                    
 		
 					//$("#content").val(text);
					//var postData = encodeURI($("#replyText,#configFlag,#gbId").serialize());
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {     
			            	var msg = eval("("+mg+")");
           		
			               if('success' == msg)
			               {
			               		//showMsg('回复留言成功!');
			               		//W.$.dialog.tips('删除内容成功...',1); 
			               		
			               		replaceUrlParam(window.location, 'isReply=0&tab=4');
			               } 	
			               else
			               {
			               		$.dialog(
							   { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/fail.png', 
								    				
								                    content: "执行失败，无权限请联系管理员！",
						
								    				cancel: true
								});
			               	     
			               }   
			              
			            }
			     	});	
       
       
    				}, 
    				cancel: true 
   	});
}

//统计

function changeTJ()
{
	replaceUrlParam(window.location,'tab=5&ids='+$('#ids').val());
}

function changeChart()
{
	var collFlag = 'mon';
	  
                       var url = "<cms:BasePath/>stat/initCache.do"+"?<cms:Token mode='param'/>";
 		
				 		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(mg)
				            {     
				            	var msg = eval("("+mg+")");
           		
				               if('success' == msg)
				               {
				               		 
				               		replaceUrlParam(window.location,'tab=5&collFlag='+collFlag+'&sd='+$('#startAddDateVal').val()+'&ed='+$('#endAddDateVal').val());
	
				               	 
				               } 	
				               else
				               {
				               	    $.dialog(
								    { 
								   					title :'提示',
								    				width: '200px', 
								    				height: '60px', 
								                    lock: true, 
								                     
								    				icon: '32X32/fail.png', 
								    				
								                    content: "执行失败，无权限请联系管理员！",
						
								    				cancel: function()
								    				{
								    					window.location.reload();
								    				}
									});
				               }   
				              
				            }
				     	});	

	
	

}
      	</script>
	</head>
	<body>
		<cms:CurrentSite>
			<div class="breadnav">
				<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left">
							&nbsp;
							<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
							当前位置：
							<a href="#">文档维护</a> &raquo;
							<a href="#">我的工作台</a>
						</td>
						<td align="right">

						</td>
					</tr>
				</table>
			</div>

			<div style="height:15px;"></div>

			<form id="advertForm" name="advertForm" method="post">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="left" valign="top">

							<!--main start-->
							<center>
								<div class="auntion_tagRoom" style="margin-top:14px;width:99%">
									<ul>
									
										<li id="two1" onclick="setTab(1)" class="selectTag">
											<a href="javascript:;"><img src="../style/icons/application-home.png" width="16" height="16" />综合统计&nbsp;</a>
										</li>
																			
										<li id="two2" onclick="setTab(2)" >
											<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />待我审核<font color="red"><span id="censor-size">(0)</span></font>&nbsp;</a>
										</li>
										<li id="two3" onclick="setTab(3)">
											<a href="javascript:;"><img src="../style/icons/socket--pencil.png" width="16" height="16" />待我回复留言<font color="red"><span id="gb-size">(0)</span></font>&nbsp;</a>
										</li>
										<li id="two4" onclick="setTab(4)">
											<a href="javascript:;"><img src="../style/blue/icon/sitemap-application.png" width="16" height="16" />站群共享<font color="red"><span id="share-size">(0)</span></font>&nbsp;</a>
										</li>
										<li id="two5" onclick="setTab(5)">
											<a href="javascript:;"><img src="../style/blue/icon/channel_document.png" width="16" height="16" />个人稿件<font color="red"><span id="da-size">(0)</span></font>&nbsp;</a>
										</li>
										
									
									</ul>
								</div>

								<div class="auntion_tagRoom_Content">
								
								
								
									<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
										<ul>
											<li>
													
														<cms:LoginUser>
															 <table width="99.8%" border="0" cellspacing="0" cellpadding="0"  >
			
														<tr>
															<td    valign="top">
																<!--main start-->
																<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">
																	<tr>
																		<td  >
																			 <div style="height:6px"></div>
																		</td>
																	</tr>
											
																	<tr>
																		<td id="uid_td25" style="padding: 2px 6px;">
																			<div class="DataGrid">
																				<table class="listdate" width="100%" border="1" cellpadding="0" cellspacing="0">
											
																					<tr class="datahead">
											
																						<td colspan="2">
																							近期日访问数据
																						</td>
																						
																						 
											
																					</tr>
											
											
																					<tr>
																						<td >
																							<table  width="100%" border="0" cellpadding="0" cellspacing="0">
																								<tr >
											
																									<td width="50%">
																									<div id="main" style="width:100%;height:320px"></div>  
																							 
																							  <script type="text/javascript">  
																				        // 路径配置  
																				        require.config({  
																				            paths: {  
																				                echarts: '../javascript/chart/echarts'  
																				            }  
																				        });  
																				          
																				        // 使用  
																				      require(  
																				           [  
																				               'echarts',  
																				               
																				               'echarts/chart/bar',
																				               'echarts/chart/line'
																				             
																				           ],  
																				            function (ec) {  
																				                // 基于准备好的dom，初始化echarts图表  
																				                var myChart = ec.init(document.getElementById('main'), theme);   
																				                  
																				                var option = {
																				    tooltip : {
																				        trigger: 'axis'
																				    },
																				    legend: {
																				        data:['PV访问量','UV访问量','IP访问量' ]
																				    },
																				    toolbox: {
																				        show : true,
																				        feature : {
																				          
																				         
																				             //magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
																				             //magicType : {show: true, type: [ 'stack', 'tiled']},
																				             //restore : {show: true},
																				             //saveAsImage : {show: true}
																				        }
																				    },
																				    calculable : true,
																				    xAxis : [
																				        {
																				            type : 'category',
																				            boundaryGap : false,
																				            data : [
																				            
																				            
																				            <cms:QueryData objName="Dv" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteDayVisitInfoTag" var="${param.sd},${param.ed},,">
																								 		
																								 <cms:if test="${!status.last}">
																								 	'${Dv.visitYear}-${Dv.visitMonth}-${Dv.visitDay}',
																								
																								 </cms:if>
																								 <cms:else>
																								 	'${Dv.visitYear}-${Dv.visitMonth}-${Dv.visitDay}'
																								
																								 
																								 </cms:else>
																								 																															
																								 																						
																							</cms:QueryData>
																				            
																				           
																				            
																				            
																				            ]
																				        }
																				    ],
																				    yAxis : [
																				        {
																				            type : 'value'
																				        }
																				    ],
																				    series : [
																				        {
																				            name:'PV访问量',
																				            type:'line',
																				            stack: '总量1',
																				            data:[
																				            
																				            
																				            <cms:QueryData objName="Dpv" reObj="Dv">
																																															
																								 <cms:if test="${!status.last}">
																								 	${Dpv.pvCount},
																								
																								 </cms:if>
																								 <cms:else>
																								 	${Dpv.pvCount}
																								 </cms:else>																								
																							</cms:QueryData>	
																				
																				            
																				            ]
																				        },
																				        {
																				            name:'UV访问量',
																				            type:'line',
																				            stack: '总量2',
																				            data:[
																				            
																				            <cms:QueryData objName="Duv" reObj="Dv">
																																															
																								 <cms:if test="${!status.last}">
																								 	${Duv.uvCount},
																								
																								 </cms:if>
																								 <cms:else>
																								 	${Duv.uvCount}
																								 </cms:else>																								
																							</cms:QueryData>	
																				            
																				            
																				            
																				            
																				            ]
																				        },
																				        {
																				            name:'IP访问量',
																				            type:'line',
																				            stack: '总量3',
																				            data:[
																				            
																				            
																				            <cms:QueryData objName="Dip" reObj="Dv">
																																															
																								 <cms:if test="${!status.last}">
																								 	${Dip.ipCount},
																								
																								 </cms:if>
																								 <cms:else>
																								 	${Dip.ipCount}
																								 </cms:else>																							
																							</cms:QueryData>	
																				            
																				            
																				            
																				            
																				            ]
																				        } 
																				    ]
																				};
																				          
																				                // 为echarts对象加载数据   
																				                myChart.setOption(option);   
																				            }  
																				        );  
		
							    														</script>  
																									
																									 
																									</td>
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									<td width="50%">
																									
																									<div id="main2" style="width:100%;height:320px"></div>  
																							 
																							  <script type="text/javascript">  
																		        // 路径配置  
																		        require.config({  
																		            paths: {  
																		                echarts: '../javascript/chart/echarts'  
																		            }  
																		        });  
																		          
																		        // 使用  
																		      require(  
																		           [  
																		               'echarts',  
																		               
																		               'echarts/chart/pie' 
																		               
																		             
																		           ],  
																		            function (ec) {  
																		                // 基于准备好的dom，初始化echarts图表  
																		                var myChart = ec.init(document.getElementById('main2'), theme);   
																		                  
																		                var option = {
																					   
																					    tooltip : {
																					        trigger: 'item',
																					        formatter: "{a} <br/>{b} : {c} ({d}%)"
																					    },
																					     
																					    toolbox: {
																					        show : true,
																					        feature : {
																					           
																					            //restore : {show: true},
																					            //saveAsImage : {show: true}
																					        }
																					    },
																					    calculable : true,
																					    series : [
																					        {
																					            name:'来源分析',
																					            type:'pie',
																					            radius : '65%',
																					            center: ['50%', '50%'],
																					            data:[
																					            
																					            
																					            	 <cms:QueryData objName="Ht" common="true" service="cn.com.mjsoft.cms.stat.service.StatService" method="getSiteVisitReffHostUvInfoTag" >
											
																					
																										<cms:if test="${!status.last}">
																									 		{value:${Ht.uvPer}, name:'${Ht.siteUrl}'},
																									
																										 </cms:if>
																										 <cms:else>
																										 	{value:${Ht.uvPer}, name:'${Ht.siteUrl}'}
																										
																										 
																										 </cms:else>
																									</cms:QueryData>
																					               
																					            ]
																					        }
																					    ]
																					};
					                    
																		          
																		                // 为echarts对象加载数据   
																		                myChart.setOption(option);   
																		            }  
																		        );  
					        
					        
					        
					
					        
					        
					    									</script>  
											 
																									 
																									</td>
																									
																									 
														
																								</tr>
											
											
											
																							</table>
																							
																							
																							 
    
    
											
											
																						</td>
																						
																						
																						
																						
																						
																						
																						
																						
																						
																						
																						
																						
																						 
																					</tr>
											
																				</table>
																			</div>
											
																		</td>
																	</tr>
											
											
																	<tr>
																		<td id="uid_td25" style="padding: 2px 6px;">
																			<div class="DataGrid">
																				<cms:QueryData objName="ST" service="cn.com.mjsoft.cms.stat.service.StatService" method="querySiteContentIntegratedTraceForTag" var="">
																					<table id="showlist" class="listdate" width="100%" cellpadding="0" cellspacing="0">
																						
																						<tr class="datahead" >
											
											
											
																							<td colspan="5">
																								&nbsp;&nbsp;统计概览
																							</td>
											
																							 
											
											
																						</tr>
																						
																						<tr>
																							<td  width="5%">
																								&nbsp;&nbsp;系统版本:
																							</td>
																							<td  width="10%">
																								<cms:QueryData service="cn.com.mjsoft.cms.common.service.CommonSystemService" method="getCMSCoreVer" objName="Ver">
																									${Ver}
																								</cms:QueryData>&nbsp;
																								<a href="http://www.jtopcms.com" target="_blank"><font color="green">查看最新版</font></a>
																							</td>
																							 
																							
																							<td  width="5%">
																								服务器架构及系统:
																							</td>
											
																							<td  width="10%">
																								<%=System.getProperty("os.arch")%>
																								-
																								<%=System.getProperty("os.name")%>
																								(
																								<%=System.getProperty("os.version")%>
																								)
																							</td>
											
																						 
											
																						</tr>
																						
																						<tr>
																							<td  width="5%">
																								&nbsp;&nbsp;Java运行版本:
																							</td>
																							<td  width="10%">
																								<%=System.getProperty("java.runtime.name")%>
																								-
																								<%=System.getProperty("java.version")%>
																							</td>
																							 
																							
																							<td  width="5%">
																								JavaEE容器信息:
																							</td>
											
																							<td  width="10%">
																								<%= application.getServerInfo() %>
																							</td>
											
																						 
											
																						</tr>
											
																						<tr>
																							<td  width="5%">
																								&nbsp;&nbsp;开通站点数:
																							</td>
																							<td  width="10%">
																								${ST.siteCount}
																							</td>
																							 
																							
																							<td  width="5%">
																								栏目总数:
																							</td>
											
																							<td  width="10%">
																								${ST.classCount}
																							</td>
											
																						 
											
																						</tr>
																						
																						
																						<tr>
																							<td>
																								&nbsp;&nbsp;管理员总数:
																							</td>
																							<td  width="10%">
																								${ST.userCount}
																							</td>
																							 
																							
																							<td>
																								机构（部门）总数:
																							</td>
											
																							<td   >
																								${ST.orgCount}
																							</td>
											
																						 
											
																						</tr>
																						
																						<tr>
																							<td>
																								&nbsp;&nbsp;累计录入数:
																							</td>
																							<td  width="10%">
																								${ST.allAddCount}
																							</td>
																							 
																							
																							<td>
																								累计发布数:
																							</td>
											
																							<td   >
																								${ST.allPubCount}
																							</td>
											
																						 
											
																						</tr>
																						
																						<tr>
																							<td>
																								&nbsp;&nbsp;累计图片信息:
																							</td>
																							<td  width="10%">
																								${ST.allImgCount}
																							</td>
																							 
																							
																							<td>
																								累计视频信息:
																							</td>
											
																							<td   >
																								${ST.allVideoCount}
																							</td>
											
																						 
											
																						</tr>
											
																						 <tr>
																							<td>
																								&nbsp;&nbsp;拦截攻击次数:
																							</td>
																							<td  width="10%">
																								${ST.allFilterCount}
																							</td>
																							 
																							
																							<td>
																								当前IP黑名单数:
																							</td>
											
																							<td   >
																								${ST.allBlackCount}
																							</td>
											
																						 
											
																						</tr>
											
											 
											
											
											
																					</table>
																				</cms:QueryData>
																			</div>
																			 
																		</td>
																	</tr>
																</table>
																
																</td>
																</tr>
																</table>
																</cms:LoginUser>

											</li>
										</ul>
									</div>
									
									
	
								
								
								
									<div id="g3_two_2" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="99.5%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																			<a id="applyAudit" href="javascript:applyAudit();" class="btnwithico"> <img id="applyAuditImg" src="../../core/style/icons/document-node.png" alt="" /><b id="applyAudit-b">申请审核权&nbsp;</b> </a>
																			<%--<a href="javascript:readContent('','','');" class="btnwithico"> <img src="../../core/style/icons/document-task.png" alt="" /><b>审阅稿件&nbsp;</b> </a>
																			
																			
																			--%><a href="javascript:openViewWFSteoInfoDialog();" class="btnwithico"> <img src="../../core/style/icons/document-php.png" alt="" /><b>流程日志&nbsp;</b> </a>
																		</div>
																		<div class="fr">
																			<%--
																			标题:&nbsp;
																			<input class="form-input" id="query" name="query" size="20" maxlength="60" />
																			<input onclick="" value="搜索" class="btn-1" type="button" />
																		--%>
																		</div>

																	</td>
																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">
																			<cms:SPContentList personalReject="${param.personalReject}" pn="${param.pn}" size="10">
																				<table id="showlist1" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																					<tr class="datahead">
																						<td width="4%" height="30">
																							<strong>ID</strong>
																						</td>
																						<td width="3%" height="30">
																						</td>
																						<td width="30%">
																							<strong>标题</strong>
																						</td>

																						<td width="6%">
																							<strong>状态</strong>
																						</td>
																						<td width="10%">
																							<strong>当前步骤</strong>
																						</td>
																						
																						<td width="9%">
																							<strong>当前审核者</strong>
																						</td>
																						
																						<td width="9%">
																							<strong>投稿人</strong>
																						</td>

																						
																						<%--<td width="8%">
																							<strong>操作</strong>
																						</td>
																					--%>
																					</tr>

																					<cms:SPContent>  
																					<cms:SystemContent id="${SPInfo.contentId}" modelId="${SPInfo.modelId}">
																					
																					<cms:SystemWorkflowStep flowId="${SPInfo.flowId}" step="${SPInfo.currentStep}">
																					
																					
																						<tr>
																							<td>
																								${SPInfo.contentId}
																							</td>
																							<td>
																								<input type="checkbox" name="checkContent" value="${SPInfo.contentId}" id="check${SPInfo.contentId}" onclick="javascript:regId(this,'${SPInfo.modelId}','${SPInfo.classId}','${SPInfo.possessStatus}','${SPInfo.currentAuditUser}');" />
																							</td>
																							<td>
																								<div align="left">
																									&nbsp;
																									 <cms:SystemUser name="${SPInfo.currentAuditUser}">
																									 
																									<a class="title-a" href='javascript:readContent("${Step.mustReq}", "${SPInfo.possessStatus}", "${SPInfo.currentAuditUser}", "${SPInfo.contentId}", "${SPInfo.classId}","${SPInfo.modelId}","${SPInfo.operStatus}","${SPInfo.currentAuditUser}", "${SysUser.userTrueName}");'>
																									
																									${Info.title}</a>
																									
																									</cms:SystemUser> 
																									<cms:Empty flag="SysUser">
																									
																									<a class="title-a" href='javascript:readContent("${Step.mustReq}", "${SPInfo.possessStatus}", "${SPInfo.currentAuditUser}", "${SPInfo.contentId}", "${SPInfo.classId}","${SPInfo.modelId}","${SPInfo.operStatus}","${SPInfo.currentAuditUser}", "");'>
																									
																									${Info.title}</a>
																									
																									</cms:Empty>
																									<input type="hidden" id="SPTitle-${SPInfo.contentId}" value="${SPInfo.title}" />
																								</div>
																							</td>
																							<td>

																								${SPInfo.operStatusStr}

																							</td>
																							<td>
																								${SPInfo.stepNodeName}
																								
																								 
																								
																								
																								<cms:if test="${Step.mustReq == 1}">
																								
																								<font color="red">(须申请)</font>
																								</cms:if>
																							</td>
																							

																							<td>
																								<cms:if test="${empty SPInfo.currentAuditUser}">无</cms:if>
																								<cms:else>
																								
																								 <cms:SystemUser name="${SPInfo.currentAuditUser}">
																								${SysUser.userTrueName}	
																								</cms:SystemUser> 
																								
																								
																								</cms:else>
																							</td>
																							
																							<td>
																								 <cms:SystemUser name="${SPInfo.creator}">
																								${SysUser.userTrueName}	
																								</cms:SystemUser> 
																							</td>
																							<%--<td>																							
																								<center>
																									<a href="javascript:readContent('${SPInfo.contentId}','${SPInfo.classId}','${SPInfo.modelId}');"><img src="../../core/style/icons/document-task.png" width="16" height="16" />&nbsp;审稿</a>
																								</center>											
																							</td>


																						--%>
																						</tr>
																						
																						</cms:SystemWorkflowStep>
																						</cms:SystemContent>
																					</cms:SPContent>
																					<cms:Empty flag="SPInfo">
																						<tr>
																							<td class="tdbgyew" colspan="8">
																								<center>
																									当前没有数据!
																								</center>
																							</td>
																						</tr>
																					</cms:Empty>



																					<cms:PageInfo>
																						<tr id="pageBarTr">
																							<td colspan="8" class="PageBar" align="left">
																								<div class="fr">
																									<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jumpW()" /> </span>
																									<span class="page">[<a href="javascript:queryW('h');">首页</a>]</span>
																									<span class="page">[<a href="javascript:queryW('p');">上一页</a>]</span>
																									<span class="page">[<a href="javascript:queryW('n');">下一页</a>]</span>
																									<span class="page">[<a href="javascript:queryW('l');">末页</a>]</span>&nbsp;
																								</div>
																								<script>
																										//数量
																										$('#censor-size').html('(${Page.totalCount})');
																										
																										
																										function queryW(flag)
																										{
																											var cp = 0;
																											
																											if('p' == flag)
																											{
													                                                             cp = parseInt('${Page.currentPage-1}');
																											}
												
																											if('n' == flag)
																											{
													                                                             cp = parseInt('${Page.currentPage+1}');
																											}
												
																											if('h' == flag)
																											{
													                                                             cp = 1;
																											}
												
																											if('l' == flag)
																											{
													                                                             cp = parseInt('${Page.pageCount}');
																											}
												
																											if(cp < 1)
																											{
													                                                           cp=1;
																											}
																										
																											
																											replaceUrlParam(window.location,'isReply=0&tab=2&pn='+cp);		
																										}
																							
																							
																										function jumpW()
																										{
																									 
																											replaceUrlParam(window.location,'isReply=0&tab=2&pn='+document.getElementById('pageJumpPos').value);
																										}
																								</script>
																								<div class="fl"></div>
																							</td>
																						</tr>
																					</cms:PageInfo>
																				</table>
																			</cms:SPContentList>
																		</div>
																		<div class="mainbody-right"></div>
																	</td>
																</tr>

															</table>

														</td>
													</tr>

													<tr>
														<td height="10">
															&nbsp;
														</td>
													</tr>
												</table>
											</li>
										</ul>
									</div>

									<!-- 第二部分:留言 -->
									<div id="g3_two_3" class="auntion_Room_C_imglist" style="display:none;">

										<ul>
											<li>
												<table width="99.5%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																				<a id="applyAuditGb" href="javascript:applyAuditGb();" class="btnwithico"> <img id="applyAuditImg" src="../../core/style/icons/document-node.png" alt="" /><b id="applyAuditGb-b">申请审核权&nbsp;</b> </a>
																				<%--
																			 <a href="javascript:openViewWFSteoInfoDialog();" class="btnwithico"> <img src="../../core/style/icons/document-php.png" alt="" /><b>流程日志&nbsp;</b> </a>
																	
																		--%></div>

																		<div>
																		</div>
																		<div class="fr">
																			<%--<a href="javascript:changeGbInfoStatus('censor','1');" class="btnwithico"> <img src="../style/icons/flag-blue.png" alt="" /><b>通过&nbsp;</b> </a>
																			<a href="javascript:changeGbInfoStatus('censor','0');" class="btnwithico"> <img src="../style/icons/flag-white.png" alt="" /><b>不通过&nbsp;</b> </a>

																			<a href="javascript:changeGbInfoStatus('open','1');" class="btnwithico"> <img src="../../core/style/icons/light-bulb.png" alt="" /><b>公开&nbsp;</b> </a>
																			<a href="javascript:changeGbInfoStatus('open','0');" class="btnwithico"> <img src="../../core/style/icons/light-bulb-off.png" alt="" /><b>不公开&nbsp;</b> </a>
																			<a href="javascript:deleteGuestbook(null);" class="btnwithico"> <img src="../../core/style/default/images/doc_delete.png" alt="" /><b>删除&nbsp;</b> </a>
																		--%></div>
																	</td>
																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">

																			<table id="showlist2" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">
																					<td width="3%" height="30">
																						<strong>ID</strong>
																					</td>
																					<td width="2%" height="30">
																						<input type="checkbox" onclick="javascript:selectAll('checkIds',this);" />
																					</td>
																					<td width="23%">
																						<strong>留言标题</strong>
																					</td>

																					<td width="10%">
																						<strong>留言人</strong>
																					</td>
																					
																					<td width="6%">
																							<strong>状态</strong>
																					</td>
																					<td width="10%">
																							<strong>当前步骤</strong>
																					</td>
																						
																					<td width="9%">
																							<strong>当前审核者</strong>
																					</td>
										
																					 
																					
																					<td width="3%">
																						<strong>通过</strong>
																					</td>
																					
																					<%--<td width="3%">
																						<strong>公开</strong>
																					</td>

																					--%><td width="7%">
																						<center>
																							<strong>维护</strong>
																						</center>
																					</td>
																				</tr>
																				<cms:SPContentList mode="gb" personalReject="${param.personalReject}" pn="${param.pn}" size="10">
																			
																			   <cms:SPContent>  
																			   
																			   <cms:SystemGbInfo configFlag="" gbId="${SPInfo.contentId}">
																			   
																			   <cms:SystemGbConfig configId="${GbInfo.configId}">
																			   
																			   <cms:SystemWorkflowStep flowId="${SPInfo.flowId}" step="${SPInfo.currentStep}">
																																		 
																				
																			 		<tr>
																						<td>
																							${GbInfo.gbId}
																						</td>
																						<td>
																							<input type="checkbox" name="checkContent" value="${SPInfo.contentId}" id="checkGb${SPInfo.contentId}" onclick="javascript:regGbId(this,'${SPInfo.modelId}','${SPInfo.classId}','${SPInfo.possessStatus}','${SPInfo.currentAuditUser}');" />
																						</td>

																						<td>
																							<cms:if test="${GbInfo.gbTitle!=null && GbInfo.gbTitle!=''}">
																								<a href="javascript:openEditGuestbookDialog('${GbCfg.cfgFlag}','${GbInfo.configId}','${GbInfo.gbId}')"> <font style="color:#454545"><cms:SubString len="123" tail=" ......" str="${GbInfo.gbTitle}" /> </font> </a>
																							</cms:if>
																							<cms:else>
																								<a href="javascript:openEditGuestbookDialog('${GbCfg.cfgFlag}','${GbInfo.configId}','${GbInfo.gbId}')"> <font style="color:#454545"><cms:SubString len="123" tail=" ......" str="${GbInfo.gbText}" /> </font> </a>
																							</cms:else>
																						</td>

																						<td>
																							${GbInfo.gbMan}
																						</td>
																						
																						

																						<td>

																								${SPInfo.operStatusStr}

																							</td>
																							<td>
																								${SPInfo.stepNodeName}
																								
																								 
																								
																								
																								<cms:if test="${Step.mustReq == 1}">
																								
																								<font color="red">(须申请)</font>
																								</cms:if>
																							</td>
																							

																							<td>
																								<cms:if test="${empty SPInfo.currentAuditUser}">无</cms:if>
																								<cms:else>
																								
																								 <cms:SystemUser name="${SPInfo.currentAuditUser}">
																								${SysUser.userTrueName}	
																								</cms:SystemUser> 
																								
																								
																								</cms:else>
																							</td>
																						
																						 
																						
																						<td>
																							
																							<cms:if test="${GbInfo.isCensor==1}">
																										<img src="../style/icon/tick.png" />
																							</cms:if>
																							<cms:else>
																										<img src="../style/icon/del.gif" />
																							</cms:else>
																						</td>
																						
																						<%--<td>
																							
																							<cms:if test="${GbInfo.isOpen==1}">
																										<img src="../style/icon/tick.png" />
																							</cms:if>
																							<cms:else>
																										<img src="../style/icon/del.gif" />
																							</cms:else>
																						</td>

																						--%><td>

																							<center>
																								<span><img src="../../core/style/icons/card-address.png" width="16" height="16" /><a href="javascript:openEditGuestbookDialog('${GbCfg.cfgFlag}','${GbInfo.configId}','${GbInfo.gbId}')">&nbsp;处理</a>&nbsp;&nbsp;&nbsp; </span>
																							</center>

																						</td>

																					</tr>
																					
																				</cms:SystemWorkflowStep>
																				
																				</cms:SystemGbConfig>
																					
																				</cms:SystemGbInfo>
																				 </cms:SPContent>
																				</cms:SPContentList>
																				<cms:Empty flag="SPInfo">
																					<tr>
																						<td class="tdbgyew" colspan="15">
																							<center>
																								当前没有数据!
																							</center>
																						</td>
																					</tr>
																				</cms:Empty>

																				<tr id="pageBarTr">
																					<cms:PageInfo>
																						<td colspan="15" class="PageBar" align="left">
																							<div class="fr">
																								<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPosGb" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jumpGb()" /> </span>
																								<span class="page">[<a href="javascript:pageGb('f')">首页</a>]</span>
																								<span class="page">[<a href="javascript:pageGb('p')">上一页</a>]</span>
																								<span class="page">[<a href="javascript:pageGb('n')">下一页</a>]</span>
																								<span class="page">[<a href="javascript:pageGb('l')">末页</a>]</span> &nbsp;
																							</div>

																							<script>
																								//数量
																								$('#gb-size').html('(${Page.totalCount})');
																										
																								function pageGb(flag)
																								{
																									if('n'==flag)
																									{
																										window.location='Workbench.jsp?configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&pn=${Page.currentPage+1}&tab=3';
																									}
																									else if('p'==flag)
																									{
																										window.location='Workbench.jsp?configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&pn=${Page.currentPage-1}&tab=3';
																									}
																									else if('f'==flag)
																									{
																										window.location='Workbench.jsp?configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&pn=1&tab=3';
																									}
																									else if('l'==flag)
																									{
																										window.location='Workbench.jsp?configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&pn=${Page.currentPage-1}&tab=3';
																									}
																								}
																								
																								function jumpGb()
																								{
																									replaceUrlParam(window.location,'configFlag=${param.configFlag}&isOpen=${param.isOpen}&isReply=${param.isReply}&isCensor=${param.isCensor}&tab=3&pn='+document.getElementById('pageJumpPosGb').value);
																								}
																							</script>
																							<div class="fl"></div>
																					</cms:PageInfo>
																					</td>
																				</tr>

																			</table>
																			<div class="mainbody-right"></div>
																		</div>

																	</td>
																</tr>

															</table>

														</td>
													</tr>

													<tr>
														<td height="10">
															&nbsp;
														</td>
													</tr>
												</table>
											</li>
										</ul>
									</div>

									<!-- 第三部分:站群共享 -->
									<div id="g3_two_4" class="auntion_Room_C_imglist" style="display:none;">

										<ul>
											<li>
												<table width="99.5%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																			内容模型:&nbsp;
																			<select id="shareModelId" class="class-form" onchange="javascript:changeFilterShare();">
																				<option>
																					------ 所有模型 ------
																				</option>
																				<cms:SystemDataModelList modelType="2">
																					<cms:SystemDataModel>
																						<option value="${DataModel.dataModelId}">
																							${DataModel.modelName}&nbsp;
																						</option>
																					</cms:SystemDataModel>
																				</cms:SystemDataModelList>

																			</select>
																			&nbsp;

																		</div>
																		<div>
																			<a href="javascript:openShareContentToSiteClassDialog('','${param.modelId}','','');" " class="btnwithico" onclick=""><img src="../style/icons/document-export.png" width="16" height="16" /><b>投送到本站栏目&nbsp;</b> </a>
																			<a href="javascript:deleteShareContent();" class="btnwithico" onclick=""><img src="../style/default/images/del.gif" width="16" height="16" /><b>删除&nbsp;</b> </a>

																		</div>
																		<div class="fr">

																		</div>
																	</td>
																</tr>

																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">
																			<table id="showlist3" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">

																					<td width="3%">
																						<strong>ID</strong>
																					</td>

																					<td width="2%">
																						<input class="inputCheckbox" value="*" type="checkbox" onclick="javascript:selectAll('checkShareId',this);" />
																					</td>
																					<td width="24%">
																						<strong>标题</strong>
																					</td>


																					<td width="9%">
																						<strong>来源站点</strong>
																					</td>
																					<td width="9%">
																						<strong>内容模型</strong>
																					</td>


																					<td width="5%">
																						<center>
																							<strong>操作</strong>
																						</center>
																					</td>
																				</tr>

																				<cms:CurrentSite>
																					<cms:QueryData service="cn.com.mjsoft.cms.content.service.ContentService" method="getShareContentForSiteQueryTag" objName="ShareInfo" var="${param.modelId},${param.pn},12">

																						<tr>
																							<td>
																								${ShareInfo.contentId}
																							</td>
																							<td>
																								<input class="inputCheckbox" name="checkShareId" value="${ShareInfo.contentId}" type="checkbox" onclick="javascript:" />
																							</td>
																							<td>
																								&nbsp;${ShareInfo.title}
																							</td>
																							<td>
																							
																								<cms:Site siteId="${ShareInfo.fromSiteId}">
																								${Site.siteName}
																							</cms:Site>
																							</td>
																							<td>
																							
																							
																								<cms:Model id="${ShareInfo.modelId}">
																								   ${Model.modelName}
																								</cms:Model>
																							 
																							</td>


																							<td>
																								<center>
																									<a href="javascript:openShareContentToSiteClassDialog('','${ShareInfo.modelId}','','${ShareInfo.contentId}');"><img src="../../core/style/icons/card-address.png" width="16" height="16" />&nbsp;投送</a>
																								</center>
																							</td>
																						</tr>

																					</cms:QueryData>
																					<cms:Empty flag="ShareInfo">
																						<tr>
																							<td class="tdbgyew" colspan="7">
																								<center>
																									当前没有数据!
																								</center>
																							</td>
																						</tr>
																					</cms:Empty>
																				</cms:CurrentSite>

																				<tr id="pageBarTr">
																					<cms:PageInfo>
																						<td colspan="8" class="PageBar" align="left">
																							<div class="fr">
																								<span class="text_m"> 共 ${Page.totalCount} 条记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="5" id="pageJumpPosSc" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jumpSc()" /> </span>
																								<span class="page">[<a href="javascript:pageSc('f')">首页</a>]</span>
																								<span class="page">[<a href="javascript:pageSc('p')">上一页</a>]</span>
																								<span class="page">[<a href="javascript:pageSc('n')">下一页</a>]</span>
																								<span class="page">[<a href="javascript:pageSc('l')">末页</a>]</span> &nbsp;
																							</div>

																							<script>
																							initSelect('shareModelId','${param.modelId}');
																							
																							//数量
																							$('#share-size').html('(${Page.totalCount})');
																							
																							function pageSc(flag)
																							{
																								if('n'==flag)
																								{
																									window.location='Workbench.jsp?isReply=0&modelId=${param.modelId}&pn=${Page.currentPage+1}&tab=4';
																								}
																								else if('p'==flag)
																								{
																									window.location='Workbench.jsp?isReply=0&modelId=${param.modelId}&pn=${Page.currentPage-1}&tab=4';
																								}
																								else if('f'==flag)
																								{
																									window.location='Workbench.jsp?isReply=0&modelId=${param.modelId}&n=1&tab=4';
																								}
																								else if('l'==flag)
																								{
																									window.location='Workbench.jsp?isReply=0&modelId=${param.modelId}&pn=${Page.currentPage-1}&tab=4';
																								}
																							}
																							
																							function jumpSc()
																							{
																								replaceUrlParam(window.location,'isReply=0&modelId=${param.modelId}&tab=4&pn='+document.getElementById('pageJumpPosSc').value);
																							}
																						</script>
																							<div class="fl"></div>
																					</cms:PageInfo>
																					</td>
																				</tr>
																			</table>
																		</div>
																		<div class="mainbody-right"></div>
																	</td>
																</tr>


															</table>

															</form>

														</td>
													</tr>
												</table>
											</li>
										</ul>
									</div>

									<!-- 第四部分:个人稿件 -->
									<div id="g3_two_5" class="auntion_Room_C_imglist" style="display:none;">
										<ul>
											<li>
												<table width="99.5%" border="0" cellspacing="0" cellpadding="0" class="mainbody-x">
													<tr>
														<td class="mainbody" align="left" valign="top">
															<!--main start-->
															<table class="listtable" width="99.8%" border="0" cellpadding="0" cellspacing="0">

																<tr>
																	<td style="padding: 7px 10px;" class="">
																		<div class="fl">
																			筛选属性:&nbsp;
																			<select id="dClassId" name="dClassId" class="form-select" onchange="javascript:changeFilterDraft();">
																				<option>
																					---------- 所有栏目稿件 ----------
																				</option>
																				<cms:SystemClassList site="${CurrSite.siteFlag}" type="all">
																					<cms:SysClass>
																						<option value="${Class.classId}">
																							${Class.layerUIBlankClassName}
																						</option>
																					</cms:SysClass>
																				</cms:SystemClassList>
																			</select>
																			&nbsp;
																			<select id="dCensor" name="dCensor" class="form-select" onchange="javascript:changeFilterDraft();">
																				<option value="-1">
																					 ---- 新稿件 ---- 
																				</option>
																				 
																						 
																						 
																						 <option value="1">
																							已发布
																						</option>
																						<option value="0">
																							审核中
																						</option>
																						<option value="2">
																							待发布
																						</option>
																						
																						<option value="3">
																							已下线
																						</option>
																					 
																			</select>
																			
																		 
																		 											  
																			
																			<button type="button" style="padding:0 10px" class="layui-btn layui-btn-primary layui-btn-xs"   onclick="javascript:deleteSelectContent();"  >删除稿件</button>
									
																		</div>
																		<div  class="fl"> 
																		 
																		</div>
																		 
																	</td>
																</tr>

																<tr>
																	<td id="uid_td25" style="padding: 2px 6px;">
																		<div class="DataGrid">
																			<table id="showlist4" class="listdate" width="100%" cellpadding="0" cellspacing="0">

																				<tr class="datahead">

																					<td width="4%">
																						<strong>ID</strong>
																					</td>

																					<td width="2%">
																						<input class="inputCheckbox"  type="checkbox" onclick="javascript:selectAll('checkedDId',this);" />
																					</td>
																					<td width="25%">
																						<strong>标题</strong>
																					</td>

																					<td width="9%">
																						<strong>所属栏目</strong>
																					</td>
																					
																					 

																					<td width="9%">
																						<strong>数据模型</strong>
																					</td>


																					<td width="9%">
																						<center>
																							<strong>操作</strong>
																						</center>
																					</td>
																				</tr>

																				<cms:QueryData service="cn.com.mjsoft.cms.content.service.ContentService" method="getDraftContentForSiteQueryTag" objName="DInfo" var="${param.dCensor},${param.dClassId},${param.pn},12">

																					<tr>
																						<td>
																							${DInfo.contentId}
																						</td>
																						<td>
																							<input class="inputCheckbox" id="checkedDId" name="checkedDId" value="${DInfo.contentId}" type="checkbox" onclick="javascript:" />
																						</td>
																						<td>
																							${DInfo.title}
																						</td>
																						<td>
																							<cms:SysClass id="${DInfo.classId}">
																								${Class.className}				
																							</cms:SysClass>
																						</td>
																						 
																						<td>
																							<cms:SystemDataModel id="${DInfo.modelId}">
																									${DataModel.modelName}					
																							</cms:SystemDataModel>
																						</td>

																						<td>
																							<div>
																								<center>
																									<img src="../../core/style/icons/card-address.png" width="16" height="16" />
																									<a href="javascript:gotoEditUserDefineContentPage('${DInfo.linkCid}','${DInfo.contentId}','${DInfo.classId}','${DInfo.modelId}','${DInfo.creator}','${DInfo.censorState}');">&nbsp;处理稿件</a>&nbsp; &nbsp;
																									<a href="javascript:openContentOperLogDialog('${DInfo.contentId}','<cms:JsEncode str='${DInfo.title}'/>');"><img src="../../core/style/icons/document-task.png" width="16" height="16" />&nbsp;日志</a>&nbsp;
																		
																									
																								 
																								</center>
																							</div>
																						</td>
																					</tr>


																				</cms:QueryData>

																				<cms:Empty flag="DInfo">
																					<tr>
																						<td class="tdbgyew" colspan="8">
																							<center>
																								当前没有数据!
																							</center>
																						</td>
																					</tr>
																				</cms:Empty>



																				<cms:PageInfo>
																					<tr id="pageBarTr">
																						<td colspan="8" class="PageBar" align="left">
																							<div class="fr">
																								<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
																								<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
																								<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
																								<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
																								<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
																							</div>
																							<script>
																										//数量
																										$('#da-size').html('(${Page.totalCount})');
																										
																										function query(flag)
																										{
																											var cp = 0;
																											
																											if('p' == flag)
																											{
													                                                             cp = parseInt('${Page.currentPage-1}');
																											}
												
																											if('n' == flag)
																											{
													                                                             cp = parseInt('${Page.currentPage+1}');
																											}
												
																											if('h' == flag)
																											{
													                                                             cp = 1;
																											}
												
																											if('l' == flag)
																											{
													                                                             cp = parseInt('${Page.pageCount}');
																											}
												
																											if(cp < 1)
																											{
													                                                           cp=1;
																											}
																										
																											
																											replaceUrlParam(window.location,'isReply=0&tab=5&pn='+cp);		
																										}
																							
																							
																										function jump()
																										{
																											replaceUrlParam(window.location,'isReply=0&tab=5&pn='+document.getElementById('pageJumpPos').value);
																										}
																								</script>
																							<div class="fl"></div>
																						</td>
																					</tr>
																				</cms:PageInfo>
																			</table>
																		</div>
																		<div class="mainbody-right"></div>
																	</td>
																</tr>


															</table>

															</form>

														</td>
													</tr>
												</table>
											</li>
										</ul>
									</div>
									
									
									
									
								<!--结尾 -->
								</div>
							</center>
						</td>
					</tr>
				</table>

				<!-- hidden -->


			</form>
			<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">

if('${param.tab}' != '' && '${param.tab}' != null)
{ 
	setTab('${param.tab}');
}


initSelect('configFlag','${param.configFlag}');
initSelect('isOpen','${param.isOpen}');
initSelect('isReply','${param.isReply}');
initSelect('isCensor','${param.isCensor}');
initSelect('dClassId','${param.dClassId}');

initSelect('dCensor','${param.dCensor}');



//initSelect('auditFilter','${param.filterBy}');

//提示

<cms:SystemWorkflowAction actId="${param.actId}">
		<cms:SystemContent id="${param.censorCId}" modelId="${param.modelId}">
		
		
		if('1' == '${Action.conjunctManFlag}')
		{
			//会签模式
			var info;
			
			if('1' == '${param.actSuccess}')
			{
				info = '${Info.title}<br/><br/> 提交<font color="red"> [${Action.passActionName}]</font> 成功!';		
			}
			else
			{
				info = '${Info.title}<br/><br/> 提交<font color="red"> [${Action.passActionName}]</font> 成功! <br/><br/>当前动作为会签,需全体通过生效。';	
			}
			
			
			$.dialog({ 
   					title :'提示',
    				width: '280px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/succ.png', 
    				
                    content: info,
   
                    ok: true 
			});

			
		}
		else
		{
			$.dialog({ 
   					title :'提示',
    				width: '280px', 
    				height: '60px', 
                    lock: true, 
    				icon: '32X32/succ.png', 
    				
                    content: '${Info.title}<br/><br/> 提交<font color="red"> [${Action.passActionName}]</font> 成功!',
   
                    ok: true 
			});

			
		}
		</cms:SystemContent>
</cms:SystemWorkflowAction>





var currentSelectId = -1;
var currentProcessManager = ''

var currentModelId = -1;
var currentClassId = -1;

var currentSelectGbId = -1;
var currentProcessManagerGb = ''

var currentModelIdGb = -1;
var currentClassIdGb = -1;


/*
*以下为工作流审核操作
*/


var censorTip;



</script>

</cms:CurrentSite>

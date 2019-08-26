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
		<script type="text/javascript" src="../javascript/dialog/lhgdialog.min.js?skin=iblue"></script>


		<script>  
		basePath='<cms:Domain/>';
		
         if("true"==="${param.fromFlow}")
         {  

         	if("${param.error}" === "true")	
         	{
         	     showErrorMsg("<cms:UrlParam target='${param.errorMsg}' />");
         	}
         	else
         	{
	             //api.close(); 
	             //W.$.dialog.tips('添加成功...',2); 
	             //W.location.reload();
         	}
       		       
         }
         else if("true"==="${param.redirect}")
         {
         	
			$.dialog
			({ 
						id:'tip',
	   					title :'提示',
	    				width: '160px', 
	    				height: '60px', 
	                    lock: true, 
	    				icon: '32X32/succ.png', 
	                    content: '添加栏目成功',
	                    
	   				 	cancel: true 
	    	});	
         
         }
         
        	
      </script>
	</head>
	<body>
		<cms:SysClass id="${param.classId}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
						<!--main start-->
						<div class="auntion_tagRoom" style="margin-top: 0px;">
							<ul>
								<li id="two1" onclick="setTab2('two',1,1)" class="selectTag">
									<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />专题分类配置&nbsp;</a>
								</li>
								<%--<li id="two2" onclick="setTab2('two',2,3)">
									<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />相关参数设置&nbsp;</a>
								</li>
								<li id="two3" onclick="setTab2('two',3,3)">
									<a href="javascript:;"><img src="../style/blue/icon/application-dock-tab.png" width="16" height="16" />扩展模型信息&nbsp;</a>
								</li>
								--%>
								<%--<li id="two4" onclick="setTab('two',4,5)">
										<a href="javascript:;"><img src="../style/blue/icon/application-dock-tab.png" width="16" height="16" />nbsp;</a>
									</li>
									<li id="two5" onclick="setTab('two',5,5)">
										<a href="javascript:;"><img src="../style/blue/icon/application-dock-tab.png" width="16" height="16" />推荐位权限&nbsp;</a>
									</li>
								--%>
							</ul>
						</div>

						<form id="classForm" name="classForm" method="post">
							<div class="auntion_tagRoom_Content">
								<div id="g3_two_1" class="auntion_Room_C_imglist" style="display:block;">
									<ul>
										<li>
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
												<tr>
													<td width="18%" class="input-title">
														分类ID:
													</td>
													<td class="td-input">
														<input type="text" size="50" class="form-input" value="${param.classId}" disabled></input>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														专题分类名称:
													</td>
													<td class="td-input">
														<input type="text" size="50" id="className" name="className" class="form-input" value="${Class.className}"></input>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														引用标识:
													</td>
													<td class="td-input">
														<input type="text" size="50" id="classFlag" name="classFlag" class="form-input" value="${Class.classFlag}"></input>
													</td>
												</tr>




												<tr>
													<td width="13%" class="input-title">
														开启内容评论:
													</td>
													<td width="87%" class="td-input">
														<input name="openComment" type="radio" value="1" />
														是
														<input name="openComment" type="radio" value="0" />
														否 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<span style="color: #666;"> 评论是否审核: <input name="mustCommentCensor" type="radio" value="1" /> 是</span>
														<input name="mustCommentCensor" type="radio" value="0" />
														否
														</span>
													</td>
												</tr>

												<tr>
													<td width="13%" class="input-title">
														允许非会员评论:
													</td>
													<td width="87%" class="td-input">
														<input name="notMemberComment" type="radio" value="1" />
														是
														<input name="notMemberComment" type="radio" value="0" />
														否 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<span style="color: #666;"> 允许评论输入HTML: <input name="commentHtml" type="radio" value="1" /> 是</span>
														<input name="commentHtml" type="radio" value="0" />
														否
														</span>
													</td>
												</tr>

												<tr>
													<td width="13%" class="input-title">
														过滤评论敏感词:
													</td>
													<td width="87%" class="td-input">
														<input name="filterCommentSensitive" type="radio" value="1" />
														是
														<input name="filterCommentSensitive" type="radio" value="0" />
														否 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<span style="color: #666;"> 强制输入验证码: <input name="commentCaptcha" type="radio" value="1" /> 是</span>
														<input name="commentCaptcha" type="radio" value="0" />
														否
														</span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														专题浏览权限:
													</td>
													<td class="td-input">
														<select id="xx" name="xx" class="form-select">
															<option value="-1">
																----- 请选浏览权限配置 -----
																</span>&nbsp;
															</option>

															<cms:SystemDataModelList modelType="3">
																<cms:SystemDataModel>
																	<option value="${DataModel.dataModelId}">
																		${DataModel.modelName}
																		</span>&nbsp;
																	</option>
																</cms:SystemDataModel>
															</cms:SystemDataModelList>
														</select>


													</td>
												</tr>


												<tr>
													<td class="input-title">
														列表页发布分页数:
													</td>
													<td class="td-input">
														<input type="text" size="6" id="listPageLimit" name="listPageLimit" class="form-input" value="${Class.listPageLimit}"></input>
														页
														<span class="ps">若空值，则发布全部页数</span>
													</td>
												</tr>
												<tr>
													<td class="input-title">
														同步发布专题:
													</td>
													<td class="td-input">
														<input name="syncPubClass" type="radio" value="1" />
														是
														</span> &nbsp;&nbsp;&nbsp;&nbsp;
														<input name="syncPubClass" type="radio" value="0" />
														否
														</span>
														<span class="ps">专题内容增删改,排序操作将同步发布栏目</span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														专题横幅图规格:
													</td>
													<td class="td-input">
														<input type="text" size="6" id="homeImageW" name="homeImageW" class="form-input" value="${Class.homeImageW}"></input>
														宽度 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="homeImageH" name="homeImageH" class="form-input" value="${Class.homeImageH}"></input>
														高度
													</td>
												</tr>
												<tr>
													<td class="input-title">
														首页引导图规格:
													</td>
													<td class="td-input">
														<input type="text" size="6" id="homeImageW" name="homeImageW" class="form-input" value="${Class.homeImageW}"></input>
														宽度 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="homeImageH" name="homeImageH" class="form-input" value="${Class.homeImageH}"></input>
														高度
													</td>
												</tr>

												<tr>
													<td class="input-title">
														栏目页引导图规格:
													</td>
													<td class="td-input">
														<input type="text" size="6" id="classImageW" name="classImageW" class="form-input" value="${Class.classImageW}"></input>
														宽度 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="classImageH" name="classImageH" class="form-input" value="${Class.classImageH}"></input>
														高度
													</td>
												</tr>


												<tr>
													<td class="input-title">
														内容引导图规格:
													</td>
													<td class="td-input">
														<input type="text" size="6" id="contentImageW" name="contentImageW" class="form-input" value="${Class.contentImageW}"></input>
														宽度 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<input type="text" size="6" id="contentImageH" name="contentImageH" class="form-input" value="${Class.contentImageH}"></input>
														高度
													</td>
												</tr>
												<tr>
													<td class="input-title">
														是否显示:
													</td>
													<td class="td-input">
														<input name="showStatus" type="radio" value="1" />
														是
														</span> &nbsp;&nbsp;&nbsp;&nbsp;
														<input name="showStatus" type="radio" value="0" />
														否
														</span>
													</td>
												</tr>

												<tr>
													<td class="input-title">
														状态:
													</td>
													<td class="td-input">
														<input id="userStatus" name="useStatus" type="radio" value="1" />
														启用
														</span> &nbsp;
														<input id="userStatus" name="useStatus" type="radio" value="2" />
														停用
														</span>
													</td>
												</tr>
												<script type="text/javascript">
                                     
                                    			</script>
											</table>

											<div style="height:50px;"></div>
											<div class="breadnavTab"  >
												<table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr class="btnbg100">
														<div style="float:right">
															<a href="javascript:submitEditForm()"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>确认&nbsp;</b> </a>

															<a href="javascript:"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>排序&nbsp;</b> </a>
															<a href="javascript:"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>移动&nbsp;</b> </a>

															<a href="javascript:openAddContentClassDialog();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16"><b>添加专题分类&nbsp;</b> </a>
															<a href="javascript:submitDeleteAction();"  class="btnwithico"><img src="../style/icon/close.png" width="16" height="16"><b>删除&nbsp;</b> </a>
														</div>
													</tr>
												</table>
												
											</div>
										</li>
									</ul>
								</div>





							</div>

							<!-- hidden -->
							<input type="hidden" id='classId' name='classId' value='${Class.classId}'>
							<input type="hidden" id='parent' name='parent' value='${Class.parent}'>
							<input type="hidden" id='isLastChild' name='isLastChild' value='${Class.isLastChild}'>
							<input type="hidden" id='classType' name='classType' value='${Class.classType}'>

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
	
//主配置	
//initRadio('classType','${Class.classType}');
//disposeClassTypeChange();

//('contentType','${Class.contentType}');

//initSelect('workflowId','${Class.workflowId}');

//initRadio('classHomeProduceType','${Class.classHomeProduceType}');

//initRadio('classProduceType','${Class.classProduceType}');

//initRadio('contentProduceType','${Class.contentProduceType}');

//initRadio('needCensor','${Class.needCensor}');

//initRadio('syncPubClass','${Class.syncPubClass}');

initRadio('useStatus','${Class.useStatus}');

initRadio('showStatus','${Class.showStatus}');

//其他配置

//initRadio('searchStatus','${Class.searchStatus}');

//initRadio('relateRangeType','${Class.relateRangeType}');

//('memberAddContent','${Class.memberAddContent}');

//initSelect('extDataModelId','${Class.extDataModelId}');

initRadio('openComment','${Class.openComment}');

initRadio('mustCommentCensor','${Class.mustCommentCensor}');

initRadio('notMemberComment','${Class.notMemberComment}');

initRadio('commentHtml','${Class.commentHtml}');

initRadio('filterCommentSensitive','${Class.filterCommentSensitive}');

initRadio('commentCaptcha','${Class.commentCaptcha}');




 
function openAddContentClassDialog()
{
    var classId = '${Class.classId}';
    var className = '${Class.className}';
	$.dialog({ 
	    id : 'oacd',
    	title : '增加栏目/专题',
    	width: '560px', 
    	height: '340px', 
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
        
        
        content: 'url:<cms:Domain/>core/channel/AddContentClass.jsp?classId='+classId+'&className='+className
	
	  
	});
}




function showFormatSelect(id,eventValue)
{
   var target = document.getElementById(id);
   
   if(target.style.display=='none' && eventValue=='3')
   {
      target.style.display='';
   }
   else if(target.style.display=='' && eventValue=='1')
   {
      target.style.display='none';
   }
}


function submitEditForm()
{
 
   
   document.all.classForm.action='../../channel/editContentClass.do';
   document.all.classForm.submit(); 

}

function submitDeleteAction()
{
    if(confirm('当前栏目以及子栏目的相关信息将被物理删除,您确定要删除吗?'))
    {
    	document.all.classForm.action='../../channel/deleteContentClass.do';
    	document.all.classForm.submit();
    }
    else
    {
      return;
    }
    
    return;
  

}

function goSortNodePage()
{
	window.parent.location.href="SortContentClass.jsp?classId=${Class.classId}";
}

function addClass()
{
	window.location.href="AddContentClassForParent.jsp?classId=${Class.classId}&className=${Class.className}";
}

function showMoveDialog()
{
    
      
      var parent = window.showModalDialog("SelectParentNode.jsp?classId=${Class.classId}&className=${Class.className}&parent=${Class.parent}&random="+Math.random(),"","dialogWidth=150px;dialogHeight=430px;status=no");
      
          
        window.parent.document.all[id='treeFrame'].src='ListClass.jsp'
      
    
     
   
}

function openSelectPublishRuleDialog(type,idName)
{
	$.dialog({ 
		id:'osprd',
    	title :'选择发布规则',
    	width: '500px', 
    	height: '300px', 
    	lock: true, 
        max: false, 
        min: false, 
       
        resize: false,
             
        content: 'url:<cms:Domain/>core/channel/SelectPublishRule.jsp?type='+type+'&idName='+idName
	});



}



function openSelectTempletDialog(mode)
{
      var targetName = '';
      
      if('channel' == mode)
      {
      	targetName = '栏目首页';
      }
      else if('class' == mode)
      {
      	targetName = '列表页';
      }
      else if('content' == mode)
      {
      	targetName = '内容页';
      }
       
	  $.dialog({ 
		id:'ostd',
    	title :'选择'+targetName+'模版',
    	width: '700px', 
    	height: '466px', 
    	lock: true, 
        max: false, 
         min: false, 
       
        
        resize: false,
             
        content: 'url:<cms:Domain/>core/channel/SelectChannelTemplet.jsp?mode='+mode
	});
}


function editCurrentTemplet(mode)
{      
	   var currrentTemplet="";
	   if(mode=='class')
       {
       		currrentTemplet=document.getElementById("classTemplateUrl").value;
       	
       }   
       else if(mode=='content')
       {
       		currrentTemplet=document.getElementById("contentTemplateUrl").value;
       }
      
       if("" == currrentTemplet)
       {
       		return;
       }
       //alert(currrentTemplet);

       
       window.showModalDialog("EditChannelTemplet.jsp?entry="+currrentTemplet.replaceAll("/","*"),"","dialogWidth=1250px;dialogHeight=600px;status=no");
	 
}

function disposeClassTypeChange()
{
	var classTypeArray = document.getElementsByName('classType');

	for(var i = 0; i < classTypeArray.length; i++)
	{
		if(classTypeArray[i].checked == true)
		{
			if(classTypeArray[i].value==1)
			{
				document.getElementById('outLinkTr').style.display="none";
			}
			else if(classTypeArray[i].value==2)
			{
				document.getElementById('outLinkTr').style.display="";
			}
			else if(classTypeArray[i].value==3)
			{
				document.getElementById('outLinkTr').style.display="none";
			}
			else if(classTypeArray[i].value==4)
			{
				document.getElementById('outLinkTr').style.display="none";
			}
		}
	}
}

function selectRule(rule,target)
{
   var fileRuleName = document.getElementById(target);
   
   var selection = document.selection;
                                
   fileRuleName.focus();
                                
   if (typeof fileRuleName.selectionStart != "undefined")
   {
    	var s = fileRuleName.selectionStart;
         fileRuleName.value = fileRuleName.value.substr(0, fileRuleName.selectionStart) + rule.value + fileRuleName.value.substr(fileRuleName.selectionEnd);
         fileRuleName.selectionEnd = s + rule.value.length;
    } 
    else if (selection && selection.createRange) 
    {
         var sel = selection.createRange();
         sel.text = rule.value;
    } else 
    {
         fileRuleName.value += rule.value;
    }
   
   
   var opts = rule.options;
   
   for(var i=0; i<opts.length; i++)
   {
   		if(opts[i].value == '-1')
   		{
   			opts[i].selected=true;
   			break;
   		}	
   }
}

</script>
</cms:SysClass>

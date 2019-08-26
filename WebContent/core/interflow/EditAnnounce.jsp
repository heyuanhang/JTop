<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />	
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		 
		<!-- 配置文件 -->
		<script type="text/javascript" src="../javascript/ueditor/ueditor.config.js"></script>
		<!-- 编辑器源码文件 -->
		<script type="text/javascript" src="../javascript/ueditor/ueditor.all.gzjs"></script>
		<script type="text/javascript" charset="utf-8" src="../javascript/ueditor/lang/zh-cn/zh-cn.js"></script>
		
		<script language="javascript" type="text/javascript" src="../javascript/My97DatePicker/WdatePicker.js"></script>
		
		<cms:Set val="'fullscreen', 'source', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough','removeformat','forecolor', 'backcolor', 'formatmatch', 'autotypeset', 'link', 'unlink', 'anchor','fontfamily', 'fontsize','undo', 'redo'" id="UE_SMP" />
		
		<script>  
		
			var api = frameElement.api, W = api.opener;
		
			if("true"==="${param.fromFlow}")
	         {     	 	
	            W.$.dialog(
			    { 
			   					title :'提示',
			    				width: '150px', 
			    				height: '60px', 
			                    lock: true, 
			                    parent:api,
			    				icon: '32X32/i.png', 
			    				
			                    content: '编辑站点公告成功!',
	
			    				ok:function()
			    				{ 
	       							W.window.location.reload();  
			    				}
				});
	                 
	         }
	         
	         var ref_flag=/^(\w){1,25}$/; 
         
             var ref_name = /^[\u0391-\uFFE5\w]{1,50}$/;

         	$(function()
		 	{
		    	validate('anTitle',1,null,null);
		    
		    	
 			
 				
		 	})

        </script>
	</head>
	<body>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/clipboard-task.png" width="16" height="16" />公告
					</div>

					<form id="saForm" name="saForm" method="post">
					
					<cms:QueryData objName="Sa" service="cn.com.mjsoft.cms.interflow.service.InterflowService" method="getSiteAnnounceTag" var="${param.anId},,">
					
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">


							<tr>
								<td width="18%" class="input-title">
									<strong>标题</strong>
								</td>
								<td class="td-input">
									<input type="text" style="width:420px" id="anTitle" name="anTitle" class="form-input" value="${Sa.anTitle}"></input>

								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>公告内容</strong>
								</td>
								<td class="td-input">
									<textarea id="content" name="content" style="height:240px;width:423px" >${Sa.content}</textarea>
									<input type="hidden" id="content_jtop_sys_hidden_temp_html" name="content_jtop_sys_hidden_temp_html" >									
									<script type="text/javascript">
						               var editor1_content = UE.getEditor('content',{zIndex : 99, autoFloatEnabled:false, allowDivTransToP: false, enableAutoSave:false, scaleEnabled:true, maximumWords:20000, toolbars: [[${UE_SMP}]]}); 
									</script>
								</td>
							</tr>
							<tr>
								<td class="input-title">
									<strong>有效时间</strong>
								</td>
								<td class="td-input">

									<input id="showStartDate" name="showStartDate" style="width:194px" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" value="<cms:FormatDate date="${Sa.showStartDate}" format="yyyy-MM-dd"/>"/>
									&nbsp;至 &nbsp;
									<input id="showEndDate" name="showEndDate" style="width:194px" maxlength="30" type="text" class="form-input-date" onmousedown="javascript:WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'});" value="<cms:FormatDate date="${Sa.showEndDate}" format="yyyy-MM-dd"/>"/>

								</td>
							</tr>

						</table>
						<!-- hidden -->
						<input type="hidden" id="anId" name="anId" value="${Sa.anId}"/>
						
						<cms:Token mode="html"/>

					</form>
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitSa();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"><b>取消&nbsp;</b> </a>
								</div>
								 
							</tr>
						</table>
					</div>


				</td>
			</tr>

			<tr>
				<td height="10">
					&nbsp;
				</td>
			</tr>
		</table>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script>  

var api = frameElement.api, W = api.opener;

function submitSa()
{
	var hasError = false;
	//系统信息字段验证
    var currError = submitValidate('anTitle',1,null,null);
        
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
	
	encodeFormInput('saForm', false);
	
	var form = document.getElementById("saForm");
	
	form.action = '<cms:BasePath/>interflow/editAn.do';
	
	form.submit();
}


function close()
{
	api.close();
}
  
</script>
</cms:QueryData>

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
		 var api = frameElement.api, W = api.opener; 
		 
         if("true"==="${param.fromFlow}")
         {     	 	
            W.$.dialog.tips('同步栏目配置成功...',2); 
           // api.close(); 
         	//api.reload( api.get('cwa') );    
       		//W.window.location.reload();       
       	
         }
      
      	</script>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" valign="top">
					<!--main start-->
					<div class="addtit">
						<img src="../style/icons/document-convert.png" width="16" height="16" />配置选项
					</div>

					<form id="infoForm" name="infoForm" method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" class="form-table">
							<tr>
								<td width="20%" class="input-title">
									<strong>栏目模板</strong>
								</td>
								<td class="td-input">
									<input type="checkbox" name="template" class="form-checkbox" value="1"/>
									栏目首页模板
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="template" class="form-checkbox" value="2"/>
									列表页模板
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="template" class="form-checkbox" value="3"/>
									内容页模板
									</input>
									&nbsp;&nbsp;&nbsp;

								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>静态发布支持</strong>
								</td>
								<td class="td-input">
									<input type="checkbox" name="publish" class="form-checkbox" value="1"/>
									栏目首页设置
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="publish" class="form-checkbox" value="2"/>
									列表页设置
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="publish" class="form-checkbox" value="3"/>
									内容页设置
									</input>
									&nbsp;&nbsp;&nbsp;

								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>静态URL规则</strong>
								</td>
								<td class="td-input">
									<input type="checkbox" name="staticRule" class="form-checkbox" value="1"/>
									栏目首页规则
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="staticRule" class="form-checkbox" value="2"/>
									列表页规则
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="staticRule" class="form-checkbox" value="3"/>
									内容页规则
									</input>
									&nbsp;&nbsp;&nbsp;
								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>栏目图片规格</strong>
								</td>
								<td class="td-input">
									<input type="checkbox" name="imageRule" class="form-checkbox" value="1"/>
									编辑器图片规格
									</input>
									&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="imageRule" class="form-checkbox" value="2"/>
									首页引导图规格
									</input>
									&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="imageRule" class="form-checkbox" value="3"/>
									栏目引导图规格
									</input>
									&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="input-title">

								</td>
								<td class="td-input">
									<input type="checkbox" name="imageRule" class="form-checkbox" value="4"/>
									列表引导图规格
									</input>
									&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="imageRule" class="form-checkbox" value="5"/>
									内容引导图规格
									</input>
									&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="imageRule" class="form-checkbox" value="6"/>
									编辑器水印支持
									</input>
									&nbsp;&nbsp;&nbsp;

								</td>
							</tr>

							<tr>
								<td class="input-title">
									<strong>其他配置</strong>
								</td>
								<td class="td-input">
									<input type="checkbox" name="other" class="form-checkbox" value="1"/>
									栏目是否显示
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="other" class="form-checkbox" value="2"/>
									列表页分页数
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="other" class="form-checkbox" value="3"/>
									同步发布设置
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="input-title">

								</td>
								<td class="td-input">
									<input type="checkbox" name="other" class="form-checkbox" value="4"/>
									搜索支持设置
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="other" class="form-checkbox" value="5"/>
									内容筛选设置
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="other" class="form-checkbox" value="6"/>
									会员投稿设置
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							
							<tr>
								<td class="input-title">

								</td>
								<td class="td-input">
									<input type="checkbox" name="other" class="form-checkbox" value="7"/>
									内容信息模型
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="other" class="form-checkbox" value="9"/>
									栏目扩展模型
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="checkbox" name="other" class="form-checkbox" value="8"/>
									审核工作流
									</input>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							
							
							

							<tr>
								<td class="input-title">
									<strong>栏目同步范围</strong>
								</td>
								<td class="td-input">
									<input name="range" type="radio" checked value="1" />
									所有子栏目&nbsp;
									<input name="range" type="radio" value="2"/>
									相同数据模型&nbsp;
									<input name="range" type="radio" value="3"/>
									单页类型栏目
								</td>
							</tr>

						</table>
						<!-- hidden -->
						<input type="hidden" id="classId" name="classId" value="${param.classId}" />
						
						<cms:Token mode="html"/>
					</form>
					
					<div style="height:15px"></div>
					<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a href="javascript:submitCopyConfigForm();"  class="btnwithico"><img src="../style/icons/tick.png" width="16" height="16" /><b>确认&nbsp;</b> </a>
									<a href="javascript:close();" class="btnwithico" ><img src="../style/icon/close.png" width="16" height="16"/><b>取消&nbsp;</b> </a>
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
   
   
function submitCopyConfigForm()
{
   var classForm = document.getElementById('infoForm');
   
   classForm.action="<cms:BasePath/>channel/copyChannelConfig.do";
   
   classForm.submit();
}

</script>

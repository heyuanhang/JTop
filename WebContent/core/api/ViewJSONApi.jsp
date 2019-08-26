<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
	 		
	  <link rel="stylesheet" href="../javascript/json/view/jquery.jsonview.css" />
  <script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
  <script type="text/javascript" src="../javascript/json/view/jquery.jsonview.js"></script>
  <cms:CurrentSite>
		 <script type="text/javascript">
      
      
      $(function() {
     	//$("#json-collapsed").JSONView(json, { collapsed: true, nl2br: true, recursive_collapser: true });

      $('#collapse-btn').on('click', function() {
        $('#json').JSONView('collapse');
      });

      $('#expand-btn').on('click', function() {
        $('#json').JSONView('expand');
      });

      $('#toggle-btn').on('click', function() {
        $('#json').JSONView('toggle');
      });

      $('#toggle-level1-btn').on('click', function() {
        $('#json').JSONView('toggle', 1);
      });

      $('#toggle-level2-btn').on('click', function() {
        $('#json').JSONView('toggle', 2);
      });
      
       $('#toggle-level3-btn').on('click', function() {
        $('#json').JSONView('toggle', 3);
      });
    });
  </script>
	</head>
	<body>
		
		<div class="breadnav">
			<table width="99.9%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						&nbsp;
						<img src="../style/blue/images/home.gif" width="16" height="16" class="home" />
						当前位置：
						<a href="#">接口管理</a> &raquo;
						<a href="#">JSON接口</a> &raquo;
						<a href="#">查看示例</a>
					</td>
					<td align="right">

					</td>
				</tr>
			</table>
		</div>
		<div style="height:25px;"></div>
			<br/>
			<br/>
			接口: <select id="api" name=api" class="form-select">
											<option value="">
												--------------------------------------- 请选择JSON接口 --------------------------------------
											</option>
											
											
												<option value="<cms:BasePath/>japi/getResInfo.do">
													通用业务：解析文件资源
												</option>
												
												<option value="<cms:BasePath/>japi/getModel.do">
													通用业务：获取自定义模型
												</option>
												
												<option disabled>
															=====================================
												</option>
														
												<option value="<cms:BasePath/>japi/getSite.do">
													站点栏目：获取站点
												</option>
												
												<option value="<cms:BasePath/>japi/getClass.do">
													站点栏目：获取栏目
												</option>
												
												<option value="<cms:BasePath/>japi/getChannelPath.do">
													站点栏目：获取栏目路径
												</option>
												
												<option value="<cms:BasePath/>japi/checkWhiteIP.do">
													站点栏目：检查栏目访问者白名单
												</option>
												
			
												<option value="<cms:BasePath/>japi/getCommendType.do">
													站点栏目：获取推荐位类型
												</option>
												
												<option value="<cms:BasePath/>japi/getTagWord.do">
													站点栏目：获取Tag词
												</option>
												
												<option disabled>
															=====================================
												</option>
											 
												<option value="<cms:BasePath/>japi/getContentList.do">
													内容相关：获取内容列表
												</option>
																								
												<option value="<cms:BasePath/>japi/getContent.do">
													内容相关：获取单一内容
												</option>
												
												<option value="<cms:BasePath/>japi/getPageArticle.do">
													内容相关：获取文章内容分页
												</option>
												
												<option value="<cms:BasePath/>japi/getContentStatus.do">
													内容相关：获取内容访问状态
												</option>
												
												<option value="<cms:BasePath/>japi/getNPContent.do">
													内容相关：获取上一下一条内容
												</option>
												
												<option value="<cms:BasePath/>japi/getCommend.do">
													内容相关：获取推荐位行内容
												</option>
												
												<option value="<cms:BasePath/>japi/getMutiContentList.do">
													内容相关：获取自定义查询内容
												</option>
												
												<option value="<cms:BasePath/>japi/getTagContent.do">
													内容相关：获取Tag词内容
												</option>
												
												<option value="<cms:BasePath/>japi/getRelateContent.do">
													内容相关：获取关联内容
												</option>
												
												<option value="<cms:BasePath/>japi/getPhotoGroup.do">
													内容相关：获取图片集内容
												</option>
												
												<option value="<cms:BasePath/>japi/search.do">
													内容相关：搜索内容
												</option>
												
												<option value="<cms:BasePath/>japi/searchKey.do">
													内容相关：获取用户搜索词汇
												</option>
												
												<option disabled>
															=====================================
												</option>
												
												<option value="<cms:BasePath/>japi/getComment.do">
													交互模块：获取评论信息
												</option>
												
												<option value="<cms:BasePath/>japi/getReply.do">
													交互模块：获取回复评论
												</option>
												
												<option value="<cms:BasePath/>japi/getGbConfig.do">
													交互模块：获取留言板配置
												</option>
												
												<option value="<cms:BasePath/>japi/getGbInfo.do">
													交互模块：获取留言信息
												</option>
												
												<option value="<cms:BasePath/>japi/getSurveyGroup.do">
													交互模块：获取调查问卷组
												</option>
												
												<option value="<cms:BasePath/>japi/getSurvey.do">
													交互模块：获取调查信息
												</option>
												
												<option value="<cms:BasePath/>japi/getSurveyOpt.do">
													交互模块：获取调查单项信息
												</option>
												
												<option value="<cms:BasePath/>japi/getSurveyText.do">
													交互模块：获取调查文本单项信息
												</option>
												
												<option value="<cms:BasePath/>japi/getSiteAnn.do">
													交互模块：获取站点公告
												</option>
												
												<option value="<cms:BasePath/>japi/getFLink.do">
													交互模块：获取友情链接
												</option>
												<option disabled>
															=====================================
												</option>
												<option value="<cms:BasePath/>member/memberLogin.do">
													会员模块：会员登录 
												</option>
												
												<option value="<cms:BasePath/>member/memberLoginOut.do">
													会员模块：会员注销
												</option>
												<option value="<cms:BasePath/>japi/getMember.do">
													会员模块：获取登录会员信息
												</option>
												
												<option value="<cms:BasePath/>japi/getMemberRole.do">
													会员模块：获取会员角色信息
												</option>
												
												<option value="<cms:BasePath/>japi/getMemberAcc.do">
													会员模块：栏目当前会员白名单
												</option>
																																		 
										</select>
											 
											  <button onclick="javascript:executeAjaxApi();">执行API</button>
			<br/>
			<br/>
			参数:<input  id="queryParam" name="queryParam" type="text" class="form-input" size="100" value="" />
			<br/>						
			<br/>
		
			 <br/>						
			<br/>
			  <h2>API执行结果:</h2>
			  <br/>
  <button id="collapse-btn">全折叠</button>
  <button id="expand-btn">全展开</button>
    <button id="toggle-level1-btn">折叠一层</button>
  <button id="toggle-level2-btn">折叠二层</button>
  <button id="toggle-level3-btn">折叠三层</button>
  <button id="toggle-btn">切换展示状态</button>
<br/><br/>
  <div id="json"></div><%--
  <h2>API数据</h2>
  <div id="json-collapsed"></div>

			--%><!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>
<script type="text/javascript">



function executeAjaxApi()
{
	var japi = $('#api').val();
	
	var jp = $('#queryParam').val();
	
	jp = encodeData(jp); 
		 
	jp = encodeURI(encodeURI(jp));
	
	var url = japi+"?"+jp;
 
	if(japi == '')
	{
		alert('请选择一个接口');
		return;
	}
	$.ajax({
			      		type: "GET",
			      		async:false,
			       		url: url,
			       		data:'',
			   
			       		success: function(msg)
			            {     
			                    
			                 jsonView(msg);
			            }
			             	 
			     	});	
	
	 
	//alert(jsons);
	// $("#json").JSONView(jsons);
	//alert(japi+"?"+jp);
	//$("#json").JSONView(jsons);
	//window.location.href = '<cms:BasePath/>core/json/ViewJSONApi.jsp?apiUrl='+japi;
	
}

function jsonView(json)
{
	 $("#json").JSONView(json);

      

}
 

</script>
</cms:CurrentSite>

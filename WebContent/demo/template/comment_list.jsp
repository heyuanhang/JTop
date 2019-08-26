<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<cms:Content id="${param.id}">
			<title>评论:${Info.title}  </title>
			<!--[if IE 7]>
			<link rel="stylesheet" href="css/font-awesome-ie7.css">
			<![endif]-->
			<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
			<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
			<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>
			
			
	</head>


	<body>
		<!--头部开始-->
		<cms:Include page="include/head.jsp?currClassId=${Info.classId}" />
		<!--头部结束-->
		<div class="main_br"></div>
		<!--主体开始-->
		<div class="main_box">
			<!--左侧-->
			<div class="layoutcon news-br mt15">

				<div class="layoutLeft pr15"  >
					<!--新闻详情页开始-->
					<div class="mainBody">
						<h1 class="ep-h1 bigtitle">
							${Info.title}
						</h1>

						<!--评论-->
						<cms:Include page="include/comment.jsp?targetId=${Info.contentId}&classId=${Info.classId}" />

 

						<div class="bs-Comments">
							<ul>

								<cms:Comment page="true" size="8"  contentId="${Info.contentId}">

									<li class="media">
										<a class="pull-left" href="javascript:"> 
										<cms:Member id="${Comment.memberId}">
									    <cms:ResInfo res="${Member.headPhoto}">
											<cms:if test="${empty Res.url}">
												<img src="${ResBase}images/def_avatar.png" width="96" height="96" />
												
												   
											</cms:if>
											<cms:else>
												
												 
										
												<img src="${Res.url}" width="96" height="96" />
												
												
												
											</cms:else>
									
										</cms:ResInfo>
										
										</cms:Member>
										 </a>
										<div class="media-body">
											<h4 class="media-heading">
												<a href="javascript:">${Comment.userName}</a><span>${Comment.commDT}</span>
											</h4>
											<span class="txt"><pre>${Comment.commentText}</pre></span>
											<ins class="np-post">
												<span><a href="javascript:diggComment('${Comment.commentId}','s');"><i >顶</i>[ ${Comment.supportCount} ]</a> <a href="javascript:diggComment('${Comment.commentId}','a');"><i></i>踩[ ${Comment.againstCount} ]</a> </span>
											</ins>
										</div>
									</li>

								</cms:Comment>


							</ul>
							<cms:PageInfo>
								<tr id="pageBarTr">
									<td colspan="8" class="PageBar" align="left">
										<div class="fr">
											<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
											<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
											<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
											<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
											<span class="page">[<a href="javascript:query('l');">末页</a>]</span> 
										</div>
										<script>
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
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
										<div class="fl"></div>
									</td>
								</tr>
							</cms:PageInfo>
						</div>
						<%--
                  <div class="loading"><a href="#"><i class="fa fa-spinner"></i>加载更多</a></div> 	      
       	  --%>


					</div>
					<!--新闻详情页结束-->

				</div>


			</div>

			<!--左侧结束-->
			<div class="area-sub fr mt15">

				<cms:Include page="block/hot_comment.jsp" />
				<!--排行结束-->
			 
				<div class="main_br"></div>
				 <!--专题-->
		       <cms:Include page="block/commend_spec.jsp"/>
		        <!--专题-->
			</div>


		</div>

		<!--主体结束-->
		<div class="main_br"></div>
		<!--主体结束-->

		<cms:Include page="include/foot.jsp" />

		<script type="text/javascript">
		
	
		
		

</script>
	</body>
</html>
</cms:Content>
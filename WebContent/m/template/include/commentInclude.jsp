
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<div class="next-article-box mui-bg-white mt15">
				<div class="news-list-title">
					<span>评论</span>
					<span class="mui-pull-right mui-h5">参与者<span id="commCount">0</span>人</span>
					<script>
                  
                   
			        var url = '${SiteBase}content/status.do?id=${param.id}';
  
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(msg)
			            {     
			               var status = eval("("+msg+")");
			              
			               $('#commCount').text(status.commCount);
			               $('#commCount2').text(status.commCount);
			             
			            }
			     	});	
                  
                  
                  </script>
				</div>
				<ul class="mui-table-view">
					<cms:Comment size="6"  contentId="${param.id}">

					<li class="mui-table-view-cell mui-media">
						<a href="javascript:void();">
							<img class="mui-media-object mui-pull-left img-circle" src="${ResBase}img/def_avatar.png">
							<div class="mui-media-body">
								<span class="mui-h5 text-color-default">${Comment.userName}</span>
								<p class="mui-ellipsis-20">${Comment.commentText}</p>
								<h6 class="timeinfo text-color-bbb">
									<span class="mui-pull-left mui-h6">${Comment.commDT}</span>
									<%--<span class="mui-pull-right mui-h6 text-color-bbb"><i class="iconfont">&#xe603;</i> 233</span>
								--%></h6>
							</div>
						</a>
					</li>
					
					</cms:Comment>
					 
					<div class="comment-more mui-text-center">
						<a href="${SiteBase}comment.jsp?id=${param.id}">
							发贴、查看更多评论
						</a>
					</div>

				</ul>
 
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<div class="hotlist">
					<div class="hd">
						热门评论排行榜
					</div>
					<div class="bd">
						<ul>
							 <cms:Content pageSize="12" classId="allChild:root" order="comm-down">
							
								<li class="f-three">
								<em>${status.count}</em><span class="r_txt"><P>
										<a href="${Info.contentUrl}">${Info.title}</a>
									</P>
									<p>
										(共${Info.commCount}条评论)
									</p> </span>
								</li>
							
							
							</cms:Content>						
							 
							
						</ul>
					</div>
				</div>
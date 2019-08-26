<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<div class="news-title">

		<cms:if test="${empty param.channelId}">
		        <!-- 二级栏目 -->
				<cms:Class objName="CurrClass" id="${param.cid}">
					<cms:if test="${CurrClass.layer==3}">
						 <cms:Class objName="ParentClass" id="${CurrClass.parent}">
						 	<a href="${ParentClass.classUrl}">${ParentClass.className}</a> >>
						 </cms:Class>
					</cms:if>
				</cms:Class>
				
				<cms:Class idList="bro:${param.cid}">		
				<cms:if test="${status.last}">
				
					<cms:if test="${Class.classId == param.cid}">
						<a href="${Class.classUrl}"><b>${Class.className}</b></a>
					</cms:if>
					<cms:else>
						<a href="${Class.classUrl}">${Class.className}</a>
					</cms:else>
				</cms:if>
				<cms:else>
					<cms:if test="${Class.classId == param.cid}">
						<a href="${Class.classUrl}"><b>${Class.className}</b></a>|  
					</cms:if>
					<cms:else>
						<a href="${Class.classUrl}">${Class.className}</a>|  
					</cms:else>
					
				</cms:else>
				</cms:Class>
		</cms:if>
		<cms:else>
				<cms:Class idList="child:${param.channelId}">
		
				<cms:if test="${status.last}">
					<a href="${Class.classUrl}">${Class.className}</a>
				</cms:if>
				<cms:else>
					<a href="${Class.classUrl}">${Class.className}</a>|  
				</cms:else>
					
			   </cms:Class>
		</cms:else>
		
		
		
</div>
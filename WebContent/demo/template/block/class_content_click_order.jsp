<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

          <div class="news-pai">
          <div class="p-title">栏目排行榜</div>
          <div class="content-list">
          		<ul>
          			<cms:if test="${empty param.cid}">
          					
          					 
		              		<cms:Content pageSize="10" classId="${param.channelId}" order="click-down" >
		              		
		              		
		              		<cms:if test="${status.index <3}">
		              		
		              		<li><span class="num blue-c">${status.index+1}</span><a href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a></li>
		              		
		              		</cms:if>
		              		<cms:else>
		              		
		              		<li><span class="num grey-c">${status.index+1}</span><a href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a></li>
		              		
		              		
		              		</cms:else>
		              		
		              		</cms:Content>
		              
		              	 
          			
          			</cms:if>
          			<cms:else>
          			
          					 
		              		<cms:Content  pageSize="10" classId="${param.cid}" order="click-down">
		              		
		              		
		              		<cms:if test="${status.index <3}">
		              		
		              		<li><span class="num blue-c">${status.index+1}</span><a href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a></li>
		              		
		              		</cms:if>
		              		<cms:else>
		              		
		              		<li><span class="num grey-c">${status.index+1}</span><a href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a></li>
		              		
		              		
		              		</cms:else>
		              		
		              		</cms:Content>
		                
          			
          			
          			
          			</cms:else>
          		
          		   
              	</ul>
              	 
          </div>
          </div>
         
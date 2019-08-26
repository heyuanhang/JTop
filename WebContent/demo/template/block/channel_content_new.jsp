<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

          <div class="news-pai" style="width:auto;">
        <div class="p-title">频道最新内容</div>
        <div class="layout-news list-main video-li">
            
              
              
          		    
              		<cms:Content pageSize="12" classId="allChild:${param.channelId}">
              		
              		
              		 
              		
              		 <li><a href="${Info.contentUrl}"><cms:SubString str="${Info.title}" len="18" tail=""/></a>  </li>
              		
              		 
              		
              		</cms:Content>
               
              
              
            </ul>
          </div>
    </div>
            
              
           
              
              
              
               
             
              
              
              

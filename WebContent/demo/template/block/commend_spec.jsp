<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

        
    <div class="news-pai" style="width:auto;">
    <div class="p-title">推荐专题</div>
    <div class="layout-news list-main video-li">
    
    <cms:Class  specMode="true" specComm="true"  idList="child:10686">
       		
	       		<cms:if test="${status.first}">
	        	<div class="list-sub-figure130">
		          		<div class="list-figure">
		          		  <div class="m-img">
		          		  
		          		  
		                  <span class="imgs"><img src="${Class.logoImage}" width="130" height="89"  alt="${Class.className}"/></span>
		                  <span class="name"><a href="${Class.channelUrl}">${Class.className}</a></span>
	            
		                   
		                </div>
		              </div>
		              <div class="news-li-2 fr">
		              		<ul>
		              		<cms:CommendType classId="${Class.classId}" isSpec="true">
		              		
		              			<cms:if test="${status.last}">
			              			<cms:CommendContent typeId="${CommendType.commendTypeId}" size="5">
										<li><a href="${CommInfo.url}">${CommInfo.title}</a></li>			
									</cms:CommendContent>
								</cms:if>
		              		              		
		              		</cms:CommendType>
		              		
		                  	 
		                  </ul>
		              </div>
		              
		             
		              
		        </div>
	       		<ul class="mb15">
		       		 
		       		<cms:CommendType classId="${Class.classId}" isSpec="true">
	              		
	              			<cms:if test="${status.first}">
		              			<cms:CommendContent typeId="${CommendType.commendTypeId}" size="8">
									<li><a href="${CommInfo.url}">${CommInfo.title}</a></li>			
								</cms:CommendContent>
							</cms:if>
	              		              		
	              </cms:CommendType>
		            
		        </ul>
		          
		          
		         </cms:if>
		         </cms:Class>
            
              
           </div>
    </div>
              
              
              
              
              
             
              
              
              

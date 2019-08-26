<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<cms:MemberAcc classId="${param.channelId}" jump="member/member_login.jsp"/> 
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JTopCMS - 演示站点</title>
<!--[if IE 7]>

<![endif]-->
<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>


</head>


<body>
<!--头部开始-->
 <cms:Class test="true" id="${param.channelId}"/> 
 
 <cms:Include page="include/head.jsp"/>
<!--头部结束-->
<div class="main_br"></div>
<!--主体开始-->
<div class="main_box">
	<cms:Include page="include/child.jsp"/>
	
   	<!--左侧-->
   	<div class="layoutcon news-br mt15">
    	 
        <div class="layoutLeft pr15">
        	<!--头条开始-->
        	<div class="news-hot">
          		<div class="hotimg fl">
              		<div class="bigimg mb15">
              		<cms:CommendContent flag="mh_gn_let" size="1">
                  	<span class="top-img"><a target="_blank" href="${CommInfo.url}"><img src="${CommInfo.img}" width="275" height="200"  alt="${CommInfo.title}"/></a></span>
                    	<span class="bigtxt"><a href="${CommInfo.url}">${CommInfo.title}</a></span>
                    </cms:CommendContent>
                  </div>
                  <div class="hotimg-li">
                  	<ul>
                  			<cms:CommendContent flag="gn_left_small" size="4">
                  			
                  				<li>
                         		<span class="imgs"><a href="${CommInfo.url}" target="_blank"><img src="${CommInfo.img}" width="130" height="85"  alt="${CommInfo.title}"/></a></span>
                            	<span class="imgtxt"><a href="${CommInfo.url}" target="_blank">${CommInfo.title}</a></span>
                         		</li>
                  			
                  			</cms:CommendContent>

                    	</ul>
                  </div>
           	  </div>
              	  <!--右侧列表-->
                <div class="hotnews fr">
                	<ul>
                	<cms:CommendContent flag="gn_mid_list" size="7">
                		<cms:if test="${status.last}">
                				<li style="border-bottom:none;"><h3 class="th3"><a target="_blank" href="${CommInfo.url}">${CommInfo.title}</a></h3><h6>${CommInfo.summary}</h6></li>
         
                		</cms:if>
                		<cms:else>
                		
                				<li><h3 class="th3"><a target="_blank" href="${CommInfo.url}">${CommInfo.title}</a></h3><h6>${CommInfo.summary}</h6></li>
                    
                		</cms:else>
                	
                	</cms:CommendContent>
                  	                  </ul>
                </div>
          	</div>
          	<!--头条结束-->
           <!--ad-->
          	<div class="news-ad-86"><a href="#"><img src="${ResBase}img/051714.jpg" alt=""/></a></div>
           <!--ad结束-->
          
          	<!--新闻页图片列表开始-->
           <div class="news-list-pic">
           	<ul>
	  		  	
	  		  		   
						
							 
							  <cms:Content pageSize="5" classId="allChild:${param.channelId}" page="true" pageClassId="${param.channelId}" >
			
			                  <li>
			                  	<cms:if test="${Info.classImgFlag == 1}">
			                  	<div class="leftimg"><a target="_blank" href="${Info.contentUrl}"><img src="${Info.classImageCmsSysResize}"  alt=""/></a></div>
			                  	</cms:if>
			                  	<cms:if test="${Info.classImgFlag == 1}">
			                   	  	<div class="conright">
			                   	</cms:if>
			                   	<cms:else>
			                   		<div class="conright conright-100">
			                   	</cms:else>
			                   		<h3><a target="_blank" href="${Info.contentUrl}">${Info.title}</a></h3>
			                        	<span class="s-text">${Info.summary}…[<a target="_blank" href="${Info.contentUrl}">全文</a>]</span>			                   	
			                   	</div>
			                  </li>
			                  </cms:Content>	
			                
						 
					 
	  		  	
            </ul>
            <br/>
            <cms:PageInfo>
																				 		<div class="kkpager">  
																							<div class="fr">
																								<span > 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" />  <span ><a href="javascript:jump();">跳转</a></span> </span>
																								<span ><a href="${Page.headQuery}">首页</a></span>
																								<span ><a href="${Page.prevQuery}">上一页</a></span>
																								<span ><a href="${Page.nextQuery}">下一页</a></span>
																								<span><a href="${Page.endQuery}">末页</a></span> 
																							</div>
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
																										
																											
																											replaceUrlParam(window.location,'cp='+cp);		
																										}
																										
																										
																							
																										function jump()
																										{
																											window.location="${Page.jumpQuery}&cp="+document.getElementById('pageJumpPos').value;
																										}
																									</script>
																							<div class="fl"></div>
																						 
																				</cms:PageInfo>
              	<!--加载开始--><%-- 
              	<div class="loading"><a href="#"><i class="fa fa-spinner"></i>加载10条</a></div>
           --%></div>
         
           <!--新闻页图片列表开始-->
        </div>
        
    
   	</div>
    
   	<!--左侧结束-->
  <div class="area-sub fr mt15">
      <cms:Include page="block/channel_content_click_order.jsp"/>
      <!--排行结束-->
      <div class="main_br"></div>
      <!--最新内容-->
       <cms:Include page="block/channel_content_new.jsp"/>
        <!--最新内容-->
        <div class="main_br"></div>
        <cms:Include page="block/channel_content_comment_order.jsp"/>
  </div>
    

</div>

<!--主体结束-->
<div class="main_br"></div>
<cms:Include page="include/foot.jsp"/>



</body>
</html>
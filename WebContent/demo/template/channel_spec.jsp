<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>门户演示站 - 本站基于JTopcms构建</title>
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
	<div class="news-title">

	<cms:Class flagMode="true" id="zgjj">
			<a href="${Class.channelUrl}"><b>${Class.className}</b></a> >>
	

	<cms:CommendType classId="${Class.classId}" isSpec="true">
		 
		<cms:if test="${status.last}">
					<a href="${CommendType.url}">${CommendType.commendName}</a>
		</cms:if>
		<cms:else>
					<a href="${CommendType.url}">${CommendType.commendName}</a>|  
		</cms:else>
		
		
	</cms:CommendType>
	
	</cms:Class>
	</div>
   	<!--左侧-->
   	<div class="layoutcon news-br mt15">
    	 
        <div class="layoutLeft pr15">
        		<!--头条开始-->
        	<div class="news-hot">
           	<div class="news2-big">
           		<cms:Class flagMode="true" id="zgjj">
           			
            		<div class="imgs fl"><img src="${Class.banner}" width="400" height="278"  alt="${Class.className}"/></div>
            		
                 	<div class="txtbox fr">
                  	<h3>${Class.className}</h3>
                    	<span class="date"></span>
                     	<p class="contxt">${Class.seoDesc}</p>
            		</div>
            	</cms:Class>
           	</div>
          	</div>
          	<!--头条结束-->	
     
          
          	<!--新闻页图片列表开始-->
           <div class="news-list-pic">
           	<ul>
          
           	 	<cms:CommendContent typeId="71"  page="true" size="9">
	  		  	 <cms:Content id="${CommInfo.contentId}">

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
                 </cms:CommendContent>
                 
            </ul>
              	<!--加载开始--> 
              	<%--<div class="loading"><a href="#"><i class="fa fa-spinner"></i>加载10条</a></div>
           --%>
           <br/>
           
             <div id="kkpager" class="kkpager"></div>
             <script type="text/javascript" src="${ResBase}js/kkpager.min.js"></script>
          
           
           
         
           
           
           
           </div>
           <!--新闻页图片列表开始-->
        </div>
        
    
   	</div>
    
   	<!--左侧结束-->
  <div class="area-sub fr mt15">
  
      <cms:Include page="block/content_click_order.jsp"/>
       <%--<cms:Block flag="mh_sy_hd"></cms:Block>--%>
       
      <!--排行结束-->
      <div class="main_br"></div>
      <!--专题-->
      <cms:Include page="block/commend_spec.jsp"/>
        <!--时政聚焦结束-->
        <div class="main_br"></div>
        <!--月点击-->
       <cms:Include page="block/content_click_month_order.jsp"/>
        <div class="main_br"></div>
      
       
  </div>
    

</div>

<!--主体结束-->
<div class="main_br"></div>
<cms:Include page="include/foot.jsp"/>


<cms:VisStat classId="${param.channelId}" />

</body>
</html>

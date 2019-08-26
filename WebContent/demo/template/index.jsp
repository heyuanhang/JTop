<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<html>
<head>
<meta property="qc:admins" content="11501542047624703536375" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<cms:MobiSite/>

<title>JTopCMS - 演示站点</title>
<!--[if IE 7]>

<![endif]-->

<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/index.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript">
/*
* 智能机浏览器版本信息:
*
*/
  
</script>

</head>

<body>
<!--头部开始-->
<cms:Include page="include/head.jsp"/>
<!--头部结束-->
 
<!--body-->

<cms:Content pageSize="1" classId="10658"  >
	  		  	 	
	  		  	 	<cms:Set val="${Info.contentId}" id="ImgInfoId" /> 
	  		  	 	</cms:Content>
 
 

<div class="wrapper">

<div class="main_box"> 
<div class="layoutcon">
	<div class="layoutLeft">
    	<!--要闻开始-->
       	<div class="leftNews mr15 fl">
       		<!--幻灯开始-->
       		<cms:Include page="block/index_left_top.jsp"/>
    <%--  <cms:Block flag="mh_sy_hd"></cms:Block> --%>
              <!--幻灯结束-->
           </div>  
         
           <!--视频新闻开始-->
           <div class="layout-video">
           	<div class="tab-bd-con video-li">
            		<ul class="list-main mb15">
                  			
                  			<cms:CommendContent flag="mh_left_sp" size="10">
                  			
                  				 
                  					<li>
                  					
	                  					<cms:CommendRow>
	                  					
	                  						<cms:if test="${status.first}">
	                  								<a href="${RowInfo.url}" target="_blank"><i class="fa fa-youtube-play"></i>${RowInfo.title}</a>
	                  						</cms:if>
	                  						<cms:else>
	                  								<a href="${RowInfo.url}">${RowInfo.title}</a>
	                  						
	                  						</cms:else>
	                  					
	                  					</cms:CommendRow>                					
                  					
                  					</li>
                         		 
                  			
                  			</cms:CommendContent>
                  </ul>
            	</div>
            	
           </div>
       </div>
       <!--右侧要闻结束-->
       <!--右侧要闻开始-->
       <div class="leftNews fr">
       		<div class="layout-news tab-main">
           	<div class="tab-hd"><div class="title-con"><b>要闻</b></div></div>
            	<div class="tab-bd-con mt15 tab-bd-photo news-li">
              		<ul class="list-main mb15">
              		<cms:CommendContent flag="mh_syyw" size="3">
              			 
              			 	<li>
              			 	
              			 	<cms:if test="${status.first}">
              			 	
              			 		<cms:CommendRow>
              			 			<cms:Content id="${RowInfo.contentId}">
              			 		
              			 			<a href="${RowInfo.url}" target="_blank"><b>${RowInfo.title}</b></a>
              			 			
              			 			</cms:Content>
              			 		</cms:CommendRow> 
              			 	
              			 	</cms:if>
	                  		<cms:else>
	                  			
	                  			<cms:CommendRow>
	                  			<cms:Content id="${RowInfo.contentId}">
	                  			
              			 			<a href="${RowInfo.url}" target="_blank"> ${RowInfo.title} </a>
              			 			
              			 		</cms:Content>
              			 		</cms:CommendRow> 	
	                  		
	                  		
	                  		</cms:else>
	                  				
	                 		</li> 				 
              			
                  			
                  	
                    </cms:CommendContent>
                    
                  </ul>
                  <ul class="list-main mb15">
                  	
                  		<cms:CommendContent flag="mh_syyw2" size="6">
              			 
              			 	<li>
              			 	
              			 	 
	                  		
	                  			<cms:CommendRow>
              			 			<a href="${RowInfo.url}" target="_blank"> ${RowInfo.title} </a>
              			 		</cms:CommendRow> 	
	                  		
	                  		
	                  		 
	                  				
	                 		</li> 				 
              			
                  			
                  	
                    	</cms:CommendContent>
							
                  </ul>
                  <ul class="list-main mb15">
                  	 
 						<cms:CommendContent flag="mh_syyw3" size="8">
              			 
              			 	<li>
              			 	
              			 	 
	                  		
	                  			<cms:CommendRow>
              			 			<a href="${RowInfo.url}" target="_blank"> ${RowInfo.title} </a>
              			 		</cms:CommendRow> 	
	                  		
	                  		
	                  		 
	                  				
	                 		</li> 				 
              			
                  			
                  	
                    	</cms:CommendContent>
 						
                  </ul>
              	</div>
           </div>
       </div>
   	</div>
    <div class="main_br"></div><!--间距15px-->
   	<!--要闻结束-->
   	
   	<div class="layoutLeft">
    	<!--财经 / 股票 / 理财开始-->
       <div class="tab-hd">
         <div class="title-con"><b>财经 / 理财 / 科技 <span class="more"><a href="#">..</a></span></b></div></div>
       	<div class="leftNews fl">
           <div class="layout-news tab-main">
           	<div class="tab-bd-con mt15 tab-bd-photo video-li">
              		<div class="index-img ml10">
              		
              		 
	  		  	 	<cms:Content pageSize="1" classId="10652" filter="homeImg">
	  		  	 	
                  	<span class="imgbox"><a href="${Info.contentUrl}" target="_blank"><img src="${Info.homeImage}" alt=""/></a></span>
                    	<h3><a href="${Info.contentUrl}" target="_blank">${Info.simpleTitle}</a></h3>
                     	<span class="text">${Info.summary}…</span>
                     	
                    </cms:Content>
                    
              </div>
                  
              <ul class="list-main mb15">
              
              		 
	  		  	 	<cms:Content  pageSize="5" classId="10652">
	  		  	 	<cms:if test="${status.first}"> 
	  		  	 	
	  		  	 	<li><a href="${Info.contentUrl}" target="_blank"><b>${Info.title}</b></a></li>
	  		  	 	
	  		  	 	</cms:if>
	  		  	 	<cms:else>
	  		  	 	
	  		  	 	 <li><a href="${Info.contentUrl}" target="_blank">${Info.title}</a></li> 
	  		  	 	
	  		  	 	</cms:else>
	  		  	 	
                  	  
                     	
                    </cms:Content>
                    
                   
              </ul>
              	</div>
           	
          	</div>
       	</div>
       	<!--左侧结束-->
       	<!--右侧开始-->
       	<div class="leftNews fr">
           <div class="layout-news tab-main">
           	<div class="tab-bd-con mt15 tab-bd-photo video-li">
              		<div class="index-img ml10">
                  
	  		  	 	<cms:Content  pageSize="1" classId="10651" filter="homeImg">
	  		  	 	
                  	<span class="imgbox"><a href="${Info.contentUrl}" target="_blank"><img src="${Info.homeImage}" alt=""/></a></span>
                    	<h3><a href="${Info.contentUrl}" target="_blank">${Info.simpleTitle}</a></h3>
                     	<span class="text">${Info.summary}…</span>
                     	
                    </cms:Content>
                    
              </div>
                  
              <ul class="list-main mb15">
                   
	  		  	 	<cms:Content  pageSize="5" classId="10651" >
	  		  	 	<cms:if test="${status.first}"> 
	  		  	 	
	  		  	 	<li><a href="${Info.contentUrl}" target="_blank"><b>${Info.title}</b></a></li>
	  		  	 	
	  		  	 	</cms:if>
	  		  	 	<cms:else>
	  		  	 	
	  		  	 	 <li><a href="${Info.contentUrl}" target="_blank">${Info.title}</a></li> 
	  		  	 	
	  		  	 	</cms:else>
	  		  	 	
                  	  
                     	
                    </cms:Content>
                    
              </ul>
              	</div>
           	
          	</div>
       	</div>
       	<!--右侧结束-->
   	</div>
   	<!--财经 / 股票 / 理财结束-->
   	<div class="main_br"></div><!--间距15px-->
   	<div class="layoutLeft">
    	<!--财经/科技/房产开始-->
       <div class="tab-hd">
         <div class="title-con"><b>体育 / 娱乐 / 时尚<span class="more"><a href="#">..</i></a></span></b></div></div>
       	<div class="leftNews fl">
           <div class="layout-news tab-main">
           	<div class="tab-bd-con mt15 tab-bd-photo video-li">
              		<div class="index-img ml10">
                  	 
	  		  	 	<cms:Content pageSize="1" classId="10650" filter="homeImg">
	  		  	 	
                  	<span class="imgbox"><a href="${Info.contentUrl}" target="_blank"><img src="${Info.homeImage}" alt=""/></a></span>
                    	<h3><a href="${Info.contentUrl}" target="_blank">${Info.simpleTitle}</a></h3>
                     	<span class="text">${Info.summary}…</span>
                     	
                    </cms:Content>
                     
              </div>
                  
              <ul class="list-main mb15">
                  	 
	  		  	 	<cms:Content  pageSize="5" classId="10650">
	  		  	 	<cms:if test="${status.first}"> 
	  		  	 	
	  		  	 	<li><a href="${Info.contentUrl}" target="_blank"><b>${Info.title}</b></a></li>
	  		  	 	
	  		  	 	</cms:if>
	  		  	 	<cms:else>
	  		  	 	
	  		  	 	 <li><a href="${Info.contentUrl}" target="_blank">${Info.title}</a></li> 
	  		  	 	
	  		  	 	</cms:else>
	  		  	 	
                  	  
                     	
                    </cms:Content>
                     
              </ul>
           	 </div>
           	
          	</div>
       	</div>
       	<!--左侧结束-->
       	<!--右侧开始-->
       	<div class="leftNews fr">
           <div class="layout-news tab-main">
           	<div class="tab-bd-con mt15 tab-bd-photo video-li">
              		<div class="index-img ml10">
                  	 
	  		  	 	<cms:Content pageSize="1" classId="10653" filter="homeImg">
	  		  	 	
                  	<span class="imgbox"><a href="${Info.contentUrl}" target="_blank"><img src="${Info.homeImage}" alt=""/></a></span>
                    	<h3><a href="${Info.contentUrl}" target="_blank">${Info.simpleTitle}</a></h3>
                     	<span class="text">${Info.summary}…</span>
                     	
                    </cms:Content>
                   
              </div>
                  
              <ul class="list-main mb15">
                   
	  		  	 	<cms:Content  pageSize="5" classId="10653">
	  		  	 	<cms:if test="${status.first}"> 
	  		  	 	
	  		  	 	<li><a href="${Info.contentUrl}" target="_blank"><b>${Info.title}</b></a></li>
	  		  	 	
	  		  	 	</cms:if>
	  		  	 	<cms:else>
	  		  	 	
	  		  	 	 <li><a href="${Info.contentUrl}" target="_blank">${Info.title}</a></li> 
	  		  	 	
	  		  	 	</cms:else>
	  		  	 	
                  	  
                     	
                    </cms:Content>
                     
              </ul>
              	</div>
           	
          	</div>
       	</div>
       	<!--右侧结束-->
   	</div>
   	<!--财经/科技/房产结束-->
   	<div class="main_br"></div>
   	<div class="layoutLeft">
    	<!--财经/科技/房产开始-->
       <div class="tab-hd">
         <div class="title-con"><b>读书 / 养生<span class="more"><a href="#">..</a></span></b></div></div>
       	<div class="leftNews fl">
           <div class="layout-news tab-main">
           	<div class="tab-bd-con mt15 tab-bd-photo video-li">
              		<div class="index-img ml10">
                  	 
	  		  	 	<cms:Content  pageSize="1" classId="10720" filter="homeImg">
	  		  	 	
                  	<span class="imgbox"><a href="${Info.contentUrl}" target="_blank"><img src="${Info.homeImage}" alt=""/></a></span>
                    	<h3><a href="${Info.contentUrl}" target="_blank">${Info.simpleTitle}</a></h3>
                     	<span class="text">${Info.summary}…</span>
                     	
                    </cms:Content>
                   
              </div>
                  
              <ul class="list-main mb15">
                  	 
	  		  	 	<cms:Content  pageSize="5" classId="10720">
	  		  	 	<cms:if test="${status.first}"> 
	  		  	 	
	  		  	 	<li><a href="${Info.contentUrl}" target="_blank"><b>${Info.title}</b></a></li>
	  		  	 	
	  		  	 	</cms:if>
	  		  	 	<cms:else>
	  		  	 	
	  		  	 	 <li><a href="${Info.contentUrl}" target="_blank">${Info.title}</a></li> 
	  		  	 	
	  		  	 	</cms:else>
	  		  	 	
                  	  
                     	
                    </cms:Content>
                    
              </ul>
           	 </div>
           	
          	</div>
       	</div>
       	<!--左侧结束-->
       	<!--右侧开始-->
       	<div class="leftNews fr">
           <div class="layout-news tab-main">
           	<div class="tab-bd-con mt15 tab-bd-photo video-li">
              		<div class="index-img ml10">
                   
	  		  	 	<cms:Content  pageSize="1" classId="10721" filter="homeImg">
	  		  	 	
                  	<span class="imgbox"><a href="${Info.contentUrl}" target="_blank"><img src="${Info.homeImage}" alt=""/></a></span>
                    	<h3><a href="${Info.contentUrl}" target="_blank">${Info.simpleTitle}</a></h3>
                     	<span class="text">${Info.summary}…</span>
                     	
                    </cms:Content>
                   
              </div>
                  
              <ul class="list-main mb15">
                  	 
	  		  	 	<cms:Content  pageSize="5" classId="10721">
	  		  	 	<cms:if test="${status.first}"> 
	  		  	 	
	  		  	 	<li><a href="${Info.contentUrl}" target="_blank"><b>${Info.title}</b></a></li>
	  		  	 	
	  		  	 	</cms:if>
	  		  	 	<cms:else>
	  		  	 	
	  		  	 	 <li><a href="${Info.contentUrl}" target="_blank">${Info.title}</a></li> 
	  		  	 	
	  		  	 	</cms:else>
	  		  	 	
                  	  
                     	
                    </cms:Content>
                    
              </ul>
              	</div>
           	
          	</div>
       	</div>
       	<!--右侧结束-->
   	</div>
   	<!--财经/科技/房产结束-->
   	<div class="main_br"></div>
</div>
<!--左侧结束-->

<div class="area-sub fr">
	<!--视频-->	
   	<div class="box-sub">
   		<div class="box-hd">精选图集</div>
       	<div class="box-bd-padding">
            <ul>
            
            	<cms:CommendContent flag="mh_sy_right_tp" size="6">
              			<cms:Content id="${CommInfo.contentId}">
              			 	<li>
              			 	
              			 	 
	                  			<div class="video-box">
				                  	<span class="sapn-img"><a href="${CommInfo.url}" target="_blank"><img width="400" height="90" src="${CommInfo.img}" alt="${CommInfo.title}"/></a></span>
				                    	<span class="sapn-name"><a href="${CommInfo.url}" target="_blank">${CommInfo.title}</a></span>	
				                    	<span class="sapn-ico"></span>			                     	 
				                </div>
	                  			 
	                  				
	                 		</li>			 
              			</cms:Content>
                  	
                </cms:CommendContent>
            	
              	
            </ul>
       	</div>
   	</div>
   	<!--视频结束-->
   	<div class="main_br"></div><!--间距15px-->
    
   	<!--广告-->
   	 <div class="ad-106">
     	<cms:Advert posFlag="mh_sy_tp"/>
  	</div>
    <!--广告结束-->
  
  
  
  <!-- flash start -->
  <%--   
  
  
   	<div class="main_br"></div><!--间距15px-->
   	<div class="box-sub">
   		<div class="box-hd">推荐专题</div>
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
		              			<cms:CommendContent typeId="${CommendType.commendTypeId}" size="9">
									<li><a href="${CommInfo.url}">${CommInfo.title}</a></li>			
								</cms:CommendContent>
							</cms:if>
	              		              		
	              </cms:CommendType>
		            
		        </ul>
		          
		          
		         </cms:if>
		         </cms:Class>
           
           
            
       		</div>
   	</div>
   	<!--科技 / 数码-->
   	<div class="main_br"></div><!--间距15px-->
   	<div class="ad-250">
   	
   	  <object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="300" height="250">
   	    <param name="movie" value="http://img1.126.net/channel4/016673/nissan300250_0516.swf">
   	    <param name="quality" value="high">
   	    <param name="wmode" value="opaque">
   	    <param name="swfversion" value="6.0.65.0">
   	    <!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
   	    <param name="expressinstall" value="Scripts/expressInstall.swf">
   	    <!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
   	    <!--[if !IE]>-->
   	    <object type="application/x-shockwave-flash" data="http://img1.126.net/channel4/016673/nissan300250_0516.swf" width="300" height="250">
   	      <!--<![endif]-->
   	      <param name="quality" value="high">
   	      <param name="wmode" value="opaque">
   	      <param name="swfversion" value="6.0.65.0">
   	      <param name="expressinstall" value="Scripts/expressInstall.swf">
   	      <!-- 浏览器将以下替代内容显示给使用 Flash Player 6.0 和更低版本的用户。 -->
   	      <div>
   	        <h4>此页面上的内容需要较新版本的 Adobe Flash Player。</h4>
   	        <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="获取 Adobe Flash Player" width="112" height="33" /></a></p>
 	        </div>
   	      <!--[if !IE]>-->
 	      </object>
   	    <!--<![endif]-->
 	    </object>
   	</div>
                
      --%>
    <!-- flash end -->             
                
                
   	
   	<div class="main_br"></div><!--间距15px-->
   	<!--最新专题-->
   	<div class="box-sub">
   		<div class="box-hd">最新专题</div>
       	<div class="layout-news list-main video-li">
       	
       		<cms:Class  specMode="true" order="down" idList="child:10686">
       		
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
		              			<cms:CommendContent typeId="${CommendType.commendTypeId}" size="9">
									<li><a href="${CommInfo.url}">${CommInfo.title}</a></li>			
								</cms:CommendContent>
							</cms:if>
	              		              		
	              </cms:CommendType>
	       		 
	            
	        </ul>
	          
	          
	         </cms:if>
	         </cms:Class>
	         
       	</div>
   	</div>
   	<div class="main_br"></div><!--间距15px-->
   	<div class="ad-126"><img src="${ResBase}img/ad124.jpg" width="300" height="124"  alt=""/></div>
   	<!--社会结束-->
   	<div class="main_br"></div><!--间距15px-->
   	<div class="box-sub">
   		<div class="box-hd">社会</div>
       	<div class="layout-news list-main video-li">
       	  <ul class="mb15">
           	<li><a href="#"><b>地下炒金敛财黑幕:赔钱交易算数</b></a> <a href="#"><b>赚钱违规</b></a></li>
            	<li><a href="#">网购珠宝风险高退货难：要价40万</a> <a href="#">估值5万</a></li>
              	<li><a href="#">股市流行舌尖体:被套股民自嘲“关灯吃面”</a></li>
              	<li><a href="#">7购房人欲通过中介靠关系买房</a> <a href="#">被骗百余万</a></li>
              	<li><a href="#">李大霄:对创业板是利空</a> <a href="#">张海东：将延续调整</a></li>
          </ul>
       	</div>
   	</div>
</div>


</div>
<!--左右模块结束-->

<!--图片新闻开始-->
<div class="main_box">
    <div class="layoutLeft tab-main">
    <cms:Class id="10648">
    <div class="tab-hd"><div class="title-con"><b>图片新闻<span class="more"><a  target="_blank" href="${Class.channelUrl}"><i class="fa  fa-ellipsis-h fr"></i></a></span></b></div></div>
    </cms:Class>
    <div class="index-picture">
    	<ul>
    	
    	 
	    <cms:Content classId="child:10648" showAll="true" pageSize="5" filter="channelImg">
	       				
	       				<li><div class="pic-box"><span class="imgs"><a target="_blank" href="${Info.contentUrl}"><img src="${Info.channelImage}"  alt="${Info.title}"/></a></span><span class="i-text"><a target="_blank" href="${Info.contentUrl}">${Info.title}</a></span></div></li>
          
	       					 
	            
	    </cms:Content>
	 
    	
       		        </ul>
    </div>
    </div>
</div>
</div>
<!--图片新闻结束-->

<cms:Include page="include/foot.jsp"/>


 <cms:VisStat/>

</body>
</html>
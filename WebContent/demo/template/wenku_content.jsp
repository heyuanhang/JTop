<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<cms:Content use404="true" id="${param.id}">
<title>${Info.title} </title>
<!--[if IE 7]>

<![endif]-->
<link href="${ResBase}css/font-awesome.min.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/base.css" rel="stylesheet" type="text/css"></link>
<link href="${ResBase}css/content.css" rel="stylesheet" type="text/css"></link>

 <script type="text/javascript" src="${ResBase}tool/FlexPaper/js/flexpaper_flash.js"></script>
 <script type="text/javascript" src="${ResBase}tool/FlexPaper/js/swfobject.js"></script> 
</head>

<%--<cms:MemberAcc classId="${Info.classId}" jump="member/member_login.jsp"/>

--%><body>
<!--头部开始-->
 <cms:Include page="include/head.jsp?currClassId=${Info.classId}"/>
<!--头部结束-->
<div class="main_br"></div>
<!--主体开始-->
<script type="text/javascript">  
             var swfVersionStr = "10.0.0";  
             var xiSwfUrlStr = "${ResBase}tool/FlexPaper/playerProductInstall.swf";  
             var flashvars = {  
               SwfFile : escape("${Info.jt_wk_docFileWkSwf}"),  // Swf 后台加上去的 文库的原理就是将你上传的word文件转换成swf 和pdf格式
       Scale : 0.6,  
       ZoomTransition : "easeOut",  
       ZoomTime : 0.5,  
         ZoomInterval : 0.1,  
         FitPageOnLoad : false,  
         FitWidthOnLoad : true,  
         PrintEnabled : true,  
         FullScreenAsMaxWindow : false,  
         ProgressiveLoading : true,  
         PrintToolsVisible : true,  
         ViewModeToolsVisible : true,  
         ZoomToolsVisible : true,  
         FullScreenVisible : true,  
         NavToolsVisible : true,  
         CursorToolsVisible : true,  
       SearchToolsVisible : true,  
         localeChain: "zh_CN"  
       };  
      
     var params = {  
      
        }  
             params.quality = "high";  
             params.bgcolor = "#ffffff";  
             params.allowscriptaccess = "sameDomain";  
             params.allowfullscreen = "true";  
             var attributes = {};  
             attributes.id = "FlexPaperViewer";  
             attributes.name = "FlexPaperViewer";  
             swfobject.embedSWF(  
                 "${ResBase}tool/FlexPaper/FlexPaperViewer.swf", "flashContent",  
                 "810", "700",  
                 swfVersionStr, xiSwfUrlStr,  
                 flashvars, params, attributes);  
    swfobject.createCSS("#flashContent", "display:block;text-align:left;");  
         </script> 
<div class="main_box">
	<div class="news-title">
   		
   	   <cms:ChannelPath classId="${Info.classId}">
   	   	<cms:if test="${PathClass.layer == 1}">
   	   		<a href="${PathClass.channelUrl}">${PathClass.className}</a> > 
   	   	</cms:if>
   	   	<cms:else>
   	   		<a href="${PathClass.classUrl}">${PathClass.className}</a> > 
   	   	</cms:else> 	   
   	   </cms:ChannelPath>正文
   	</div>

   	<!--左侧-->
   	<div class="">
    	 
        <div class="layoutLeft pr15">
        	<!--新闻详情页开始-->
          	<div class="ep-content-main">
          		<h1 class="ep-h1">${Info.title}</h1>
              	<div class="clearfix">
              		<div class="ep-info">
                 		<div class="fl"><cms:FormatDate date="${Info.pubDate}" />　来源: ${Info.author}</div>
                     	<div class="fr">参与者<i ></i><span id="contentClickCount">0</span>人</div>
                     	<script>
                  
                   
				        var url = '${SiteBase}content/status.do?id=${Info.contentId}';
	 		
				 		$.ajax({
				      		type: "POST",
				       		url: url,
				       		data:'',
				   
				       		success: function(msg)
				            {     
				               var status = eval("("+msg+")");
				               
				               $('#contentClickCount').text(status.clickCount);
				          
				            }
				     	});	
	                  
	                  
	                  </script>
                  </div>
                  <div id="endText">
                  <p>
                  
                   	<center>
                   		<div id="flashContent">  
			          	</div>  
			         </center>
                  		
                  </p>
                     	<div class="ep-source"><span class="fl"><i class="fa fa-columns"></i>本文来源：${Info.author}</span></div>
                     	
                  </div>
                  
                  <div class="share">
                    <div class="bdsharebuttonbox fr">
                      <span class="share-title">分享到：</span>
                      <a href="#" class="bds_more" data-cmd="more"></a>
                      <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                      <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                      <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
                      <a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
                      <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                    </div>

                  </div>
                  
                  
                   <!--阅读心情-->
                  <center>
                  <div class="apps_svy_mood">
                    <h2>读完这篇文章后，您心情如何？</h2>
                    <div class="apps_svy_mood-box">
                        <ul class="clearfix">
                            <li>
                                <div class="moodd mood_result" id="apps_svy_mood_res_0" style="display:none">
                                    <div class="ft" id="apps_svy_opt_count_0">0</div>
                                    <div class="bar">
                                        <div class="bg" id="apps_svy_opt_per_0" style="height:0px;"></div>
                                    </div>
                                </div>
                                <div class="moodd mood" id="apps_svy_opt_title_0">
                                    <a id="apps_svy_face_0" class="moodd mood1" title="给力" href="javascript:mood('${Info.contentId}',1)"></a>
                                </div>
                            </li>
                            <li>
                                <div class="moodd mood_result" id="apps_svy_mood_res_1" style="display:none">
                                    <div class="ft" id="apps_svy_opt_count_1">0</div>
                                    <div class="bar">
                                        <div class="bg" id="apps_svy_opt_per_1" style="height:0px;"></div>
                                    </div>
                                </div>
                                <div class="moodd mood" id="apps_svy_opt_title_1">
                                    <a id="apps_svy_face_1" class="moodd mood2" title="淡定" href="javascript:mood('${Info.contentId}',2)"></a>
                                </div>
                            </li>
                            <li>
                                <div class="moodd mood_result" id="apps_svy_mood_res_2" style="display:none">
                                    <div class="ft" id="apps_svy_opt_count_2">0</div>
                                    <div class="bar">
                                        <div class="bg" id="apps_svy_opt_per_2" style="height:0px;"></div>
                                    </div>
                                </div>
                                <div class="moodd mood" id="apps_svy_opt_title_2">
                                    <a id="apps_svy_face_2" class="moodd mood3" title="震惊" href="javascript:mood('${Info.contentId}',3)"></a>
                                </div>
                            </li>
                            <li>
                                <div class="moodd mood_result" id="apps_svy_mood_res_3" style="display:none">
                                    <div class="ft" id="apps_svy_opt_count_3">0</div>
                                    <div class="bar">
                                        <div class="bg" id="apps_svy_opt_per_3" style="height:0px;"></div>
                                    </div>
                                </div>
                                <div class="moodd mood" id="apps_svy_opt_title_3">
                                    <a id="apps_svy_face_3" class="moodd mood4" title="坑爹" href="javascript:mood('${Info.contentId}',4)"></a>
                                </div>
                            </li>
                            <li>
                                <div class="moodd mood_result" id="apps_svy_mood_res_4" style="display:none">
                                    <div class="ft" id="apps_svy_opt_count_4">0</div>
                                    <div class="bar">
                                        <div class="bg" id="apps_svy_opt_per_4" style="height:0px;"></div>
                                    </div>
                                </div>
                                <div class="moodd mood" id="apps_svy_opt_title_4">
                                    <a id="apps_svy_face_4" class="moodd mood5" title="打酱油" href="javascript:mood('${Info.contentId}',5)"></a>
                                </div>
                            </li>
                        </ul>
                        <ul class="clearfix">
                            
                        </ul>
                        </center>
                        <script>
                  
                   
					        var url = '${SiteBase}content/status.do?id=${Info.contentId}';
		 		
					 		$.ajax({
					      		type: "POST",
					       		url: url,
					       		data:'',
					   
					       		success: function(msg)
					            {     
					                var status = eval("("+msg+")");
					               
					                var allCount = status.moodT1Count+status.moodT2Count+status.moodT3Count+status.moodT4Count+status.moodT5Count;
					             
					              
					               
					                document.getElementById('apps_svy_opt_count_0').innerHTML=status.moodT1Count;
									document.getElementById('apps_svy_opt_per_0').style.height=accDiv(status.moodT1Count,allCount)*100*0.52+"px";
									document.getElementById('apps_svy_mood_res_0').style.display='block';
									
									document.getElementById('apps_svy_opt_count_1').innerHTML=status.moodT2Count;
									document.getElementById('apps_svy_opt_per_1').style.height=accDiv(status.moodT2Count,allCount)*100*0.52+"px";
									document.getElementById('apps_svy_mood_res_1').style.display='block';
									
									
									document.getElementById('apps_svy_opt_count_2').innerHTML=status.moodT3Count;
									document.getElementById('apps_svy_opt_per_2').style.height=accDiv(status.moodT3Count,allCount)*100*0.52+"px";
									document.getElementById('apps_svy_mood_res_2').style.display='block';
									
									
									document.getElementById('apps_svy_opt_count_3').innerHTML=status.moodT4Count;
									document.getElementById('apps_svy_opt_per_3').style.height=accDiv(status.moodT4Count,allCount)*100*0.52+"px";
									document.getElementById('apps_svy_mood_res_3').style.display='block';
									
									document.getElementById('apps_svy_opt_count_4').innerHTML=status.moodT5Count;
									document.getElementById('apps_svy_opt_per_4').style.height=accDiv(status.moodT5Count,allCount)*100*0.52+"px";
									document.getElementById('apps_svy_mood_res_4').style.display='block';
					            
					            }
					     	});	
					     	
					     	
					     	function accDiv(arg1,arg2)
							{
								if(arg2 ==0)
								{
									return 0;
								}
								
							    var t1=0,t2=0,r1,r2;
							    try{t1=arg1.toString().split(".")[1].length}catch(e){}
							    try{t2=arg2.toString().split(".")[1].length}catch(e){}
							    with(Math){
							        r1=Number(arg1.toString().replace(".",""));
							        r2=Number(arg2.toString().replace(".",""));
							        return (r1/r2)*pow(10,t2-t1);
							    }
							}
		                  
		                  
		                  </script>
                    </div>
                </div><!--apps_svy_mood end-->
                  
                  
                  <!--阅读心情end-->
                  
                  
                  <div class="commend-wrap">
                 		<div class="title">相关阅读</div>
                     	<ul class="news-li">
                     	<cms:Relate type="c" size="5">
                     		<cms:Content id="${ReId}" objName="RInfo">
                     			<li><ins><cms:FormatDate date="${RInfo.pubDate}" format="yyyy-MM-dd"/></ins><a target="_blank" href="${RInfo.contentUrl}">${RInfo.title}</a></li>
                     		</cms:Content>
                     	</cms:Relate>
                     	
                     	</ul>
                 	</div>
                  <!--评论-->
                  <cms:Include page="include/comment.jsp?targetId=${Info.contentId}&classId=${Info.classId}"/>
                  
              	</div>
          	</div>
          	<!--新闻详情页结束-->
          
        </div>
        
    
   	</div><%--
    
   	<!--左侧结束-->
  <div class="area-sub fr mt15">
  
      <cms:Include page="block/content_click_order.jsp"/>
       <cms:Block flag="mh_sy_hd"></cms:Block>
       
      <!--排行结束-->
      <div class="main_br"></div>
      <!--专题-->
      <cms:Include page="block/commend_spec.jsp"/>
        <!--时政聚焦结束-->
        <div class="main_br"></div>
        <!--月点击-->
       <cms:Include page="block/content_click_month_order.jsp"/>
        <div class="main_br"></div>
       <!--周点击-->
       <cms:Include page="block/content_click_week_order.jsp"/>
       
  </div>
    

--%></div>

<!--主体结束-->

<!--foot束-->
<cms:Include page="include/foot.jsp"/>




<!--share-->
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"1","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"24"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>


</body>
</html>


<cms:VisStat contentId="${Info.contentId}" classId="${Info.classId}"/>

</cms:Content>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<cms:MemberAcc classId="${param.cid}" jump="member/member_login.jsp"/> 
<html>
<head>

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
 <cms:Class test="true" id="${param.cid}">  
 <cms:Include page="include/head.jsp"/>
  <%--
 
  <%@include file="include/head.jsp" %>
--%><!--头部结束-->
<div class="main_br"></div>
<!--主体开始-->
<div class="main_box">
	<cms:Include page="include/child.jsp?channelId=${param.cid}"/>
 
   	<!--左侧-->
   	<div class="layoutcon news-br mt15">
    	 
        <div class="layoutLeft pr15"> 
        	
     <cms:MutiContent page="true" classId="content:${param.cid}" query="{jt_wwj_lb}=${param.lb},{jt_wwj_pc}=第二批,{jt_wwj_dy}=" order="jt_wwj_dj" size="2">
           	 	${MInfo.jt_wwj_dy} ss ${MInfo.contentId}<br/>
           	 	</cms:MutiContent>
          
           <!--新闻页图片列表开始-->
           <div class="news-list-pic">
           	<ul><%--
           	  	 <cms:Content page="true" pageSize="10" classId="${param.cid}">

                  <li>
                  	<cms:if test="${Info.classImgFlag == 1}">
                  	<div class="leftimg"><a  href="${Info.contentUrl}"><img src="${Info.classImageCmsSysResize}"  alt=""/></a></div>
                  	</cms:if>
                  	<cms:if test="${Info.classImgFlag == 1}">
                   	  	<div class="conright">
                   	</cms:if>
                   	<cms:else>
                   		<div class="conright conright-100">
                   	</cms:else>
                   		<h3><a  href="${Info.contentUrl}">${Info.title}</a></h3>
                        	<span class="s-text">${Info.summary}…[<a target="_blank" href="${Info.contentUrl}">全文</a>]</span>
                   	
                   	</div>
                  </li>
                  </cms:Content>	
                   
                 
            --%></ul>
              	 
           <br/>
           
             <div id="kkpager" class="kkpager"></div>
             <script type="text/javascript" src="${ResBase}js/kkpager.min.js"></script>
          
           
           
         
           	<cms:PageInfo><%--
																					 
																							 <div style="padding-bottom:2px;float:right;">
																								<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="跳转" onclick="javascript:jump()" /> </span>
																								<span class="page">[<a href="${Page.headQuery}">首页</a>]</span>
																								<span class="page">[<a href="${Page.prevQuery}">上一页</a>]</span>
																								<span class="page">[<a href="${Page.nextQuery}">下一页</a>]</span>
																								<span class="page">[<a href="${Page.endQuery}">末页</a>]</span> 
																							</div>
																							 --%><script>
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
													                                                           pn=1;
																											}
																										
																											
																											replaceUrlParam(window.location,'tab=2&pn='+cp);		
																										}
																							
																										function jump()
																										{
																											jumpPos(document.getElementById('pageJumpPos').value);
																										}
																										
																										function jumpPos(pos)
																										{
																										
																											var endPos = parseInt('${Page.endPos}');
																											
																											var jumpQuery = '${Page.jumpQuery}';
																											
																											var jumpStatic = '${Page.jumpStatic}';
																											
																											
																											if('' != jumpStatic && pos <=endPos)
																											{
																													if(pos==1)
																													{
																														window.location=jumpStatic.replace('_{pn}','');
																													}
																													else
																													{
																														window.location=jumpStatic.replace('{pn}',pos);
																													}																								
																											}
																											else
																											{
																												window.location="${Page.jumpQuery}&pn="+pos;
																											}
																										
																										
																										}
																										
																										
																									 
																								           //init
																								
																									var totalPage = '${Page.pageCount}';
																									var totalRecords = '${Page.totalCount}';
																									var pageNo = '${Page.currentPage}';
																									 
																									//生成分页
																									//有些参数是可选的，比如lang，若不传有默认值
																									kkpager.generPageHtml({
																									 
																										pno : pageNo,
																										//总页码
																										total : totalPage,
																										//总数据条数
																										totalRecords : totalRecords,
																										//链接前部
																										hrefFormer : '${Page.headQuery}',
																										//链接尾部
																										hrefLatter : '${Page.endQuery}',
																										getLink : function(n){
																										
																										var linkx = '';
																										
																											var endPos = parseInt('${Page.endPos}');
																											
																											var jumpQuery = '${Page.jumpQuery}';
																											
																											var jumpStatic = '${Page.jumpStatic}';
																											
																											
																											if('' != jumpStatic && n <=endPos)
																											{
																													if(n==1)
																													{
																														linkx =jumpStatic.replace('_{pn}','');
																													}
																													else
																													{
																														linkx =jumpStatic.replace('{pn}',n);
																													}																								
																											}
																											else
																											{
																												linkx ="${Page.jumpQuery}&pn="+n;
																											}
																										 
																											return linkx;


																										},
																										lang : {
																											firstPageText : '|<',
																											lastPageText : '>|',
																											prePageText : '<',
																											nextPageText : '>',
																											totalPageBeforeText : '',
																											totalPageAfterText : '页',
																											totalRecordsAfterText : '条',
																											gopageBeforeText : '至',
																											gopageButtonOkText : '确定',
																											gopageAfterText : '页',
																											buttonTipBeforeText : '第',
																											buttonTipAfterText : '页'
																										}
																										//,
																										//mode : 'click',//默认值是link，可选link或者click
																										//click : function(n){
																										//	this.selectPage(n);
																										//  return false;
																										//}
																									});
	
																									</script>
																							<div class="fl"></div>
																						<br/><%--
																				
																<cms:if test="${Page.currentPage - 6 > 0}">
																
																..
																
															   </cms:if>
															   				
																<cms:PageNum max="10"  movePos="6">
																	<cms:if test="${Page.currentPage == Num}">
																	<strong> <font color="red"><a href="javascript:jumpPos('${Num}');">${Num}</a> </font>	</strong>
				
																   </cms:if>
																   <cms:else>
																   <a href="javascript:jumpPos('${Num}');">${Num}</a> 
																   </cms:else>
																	
				
																</cms:PageNum>
															
																<cms:if test="${(Page.currentPage + 4) < Page.pageCount}">
				
																		..
																</cms:if>
																						--%> 
																				</cms:PageInfo>
           
           
           </div>
           <!--新闻页图片列表开始-->
        </div>
        
    
   	</div>
    
   	<!--左侧结束-->
  <div class="area-sub fr mt15">
  
       <cms:Include page="block/class_content_click_order.jsp"/>
       <%--<cms:Block flag="mh_sy_hd"></cms:Block>--%>
       
      <!--排行结束-->
      <div class="main_br"></div>
      <!--专题-->
       <cms:Include page="block/commend_spec.jsp"/>
        <!--专题-->
        <div class="main_br"></div>
         <cms:Include page="block/class_content_comment_order.jsp"/>
  </div>
    

</div>

<!--主体结束-->
<div class="main_br"></div>
<cms:Include page="include/foot.jsp"/>


<cms:VisStat classId="${param.cid}" />

</body>
</html>
</cms:Class>
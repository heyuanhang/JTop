<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

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
 <cms:Include page="include/head.jsp"/>
<!--头部结束-->
<div class="main_br"></div>
<!--主体开始-->

<cms:SearchEntry  key="${param.keyword}" enc="utf-8"    page="${param.page}" pageSize="7" contentLength="90" light="true" showField="mh_wz" modelId="${param.searchModelId}" lsTag="[span class='c03']" leTag="[/span]" >
<div class="main_box">
	<div class="news-title search_title">
  	<span>您现在位置：<strong class="c03">
  	
  	<cms:if test="${empty param.searchModelId}">
  		全站搜索
  	</cms:if>
  	<cms:else>
  		<cms:Model id="${param.searchModelId}">
                  		
          ${Model.modelName}
                  
        </cms:Model>
  	</cms:else>
  	
  
  	
  	
  	</strong></span>
	<ins><span>搜索词：<strong class="c03"><EM> <cms:DecodeParam enc="utf-8" str="${param.keyword}" /> </EM></strong></span></ins>
   	<ins>找到相关新闻<strong class="c03"> ${SearchInfo.resultCount} </strong>篇</ins>
   	<ins><span>搜索时间：<strong class="c03">${SearchInfo.time} 秒</strong></span></ins>
    </div>
	
   	 <!--左侧-->
    <div class="layoutcon news-br mt15">
    <div class="layoutLeft pr15">
      <!--头条开始--><!--头条结束-->
      <!--ad-->
      <!--ad结束-->
      <!--新闻页图片列表开始-->
      <div class="news2-list search-list ">
        <ul>
          <cms:SearchResult>
          <li>
            <h3><a target="_blank" href="${Hit.url}">${Hit.title}</a></h3>
            <span class="s-text">${Hit.content}</span> <span class="time">${Hit.addDate}</span>
          </li>
          </cms:SearchResult>
         
        </ul>
        <!--加载开始-->
        <cms:PageInfo>
	
																						 <div class="kkpager"> 
																						 <div class="fr">
																								<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <span ><a href="javascript:jump();">跳转</a></span> </span>
																								<span class="page"><a href="javascript:searchPage('h');">首页</a></span>
																								<span class="page"><a href="javascript:searchPage('p');">上一页</a></span>
																								<span class="page"><a href="javascript:searchPage('n');">下一页</a></span>
																								<span class="page"><a href="javascript:searchPage('e');">末页</a></span>
																							</div></div></div>
																							<script>
																										var page = '${Page.currentPage}';
																										
																										
																										
																										var searchKey = '<cms:DecodeParam enc="utf-8" str="${param.keyword}" />';
	
																										var pc= parseInt('${Page.pageCount}');
																										
																										function searchPage(flag)
																										{
																											var nextPage = null;
																											
																											if('n' == flag)
																											{
																												nextPage = parseInt(page) + 1;
																												
																												if(nextPage > pc)
																												{
																													nextPage = pc;
																												}
																											}
																											
																											if('p' == flag)
																											{
																												nextPage = parseInt(page) - 1;
																											}
																											
																											if('h' == flag)
																											{
																												nextPage = 1;
																											}
																											
																											if('e' == flag)
																											{
																												nextPage = pc;
																											}
																											
																										
																											
																											
																											window.location = "${SiteBase}search.jsp?page="+nextPage+"&keyword="+encodeURIComponent(encodeURIComponent(searchKey))+"&searchModelId=${param.searchModelId}";
																										}
																							
																							
																										
																									</script>
																							<div class="fl"></div>
																						 
				</cms:PageInfo>
		
		
      </div>
      <!--新闻页图片列表开始-->
    </div>


<div class="area-sub fr mt15">
    <div class="news-pai">
      <div class="p-title">大家都在搜</div>
      <div class="content-list">
        <ul>
	        <cms:SearchKey size="12">
	        <cms:if test="${status.index<3}">
	        	 <li><span class="num blue-c">${status.index+1}</span><a href="javascript:gotoSearch('<cms:JsEncode str="${Key.queryKey}"/>');">${Key.queryKey}</a></li>	     
	        </cms:if>
	        <cms:else>
	        	 <li><span class="num grey-c">${status.index+1}</span><a href="javascript:gotoSearch('<cms:JsEncode str="${Key.queryKey}"/>');">${Key.queryKey}</a></li>
	        </cms:else>
	            </cms:SearchKey>  
        </ul>
      </div>
    </div>


  </div>
  <!--左侧结束-->
  
  <!--排行结束--><!--时政聚焦--><!--时政聚焦结束--></div>
  <!--主体结束-->
<div class="main_br"></div>
<cms:Include page="include/foot.jsp"/>

</div>

<script type="text/javascript">


function gotoSearch(searchKey)
{
		searchKey = encodeData(searchKey); 
		 
		 
	    searchKey= 	encodeURI(encodeURIComponent(searchKey));

 
	window.location = "${SiteBase}search.jsp?keyword="+searchKey+"&searchModelId=${param.searchModelId}";
}
</script>


</body>
</html>


</cms:SearchEntry>
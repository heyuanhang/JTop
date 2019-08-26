<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<div class="header">
  <div class="top_nav">
    <div class="top_nav_inner">
      <div class="fl nav-collect"> <a href="#">设为首页</a> <a href="#">收藏本站</a> </div>
      <div class="siderNav fr">
        <ul class="topmenu" id="jq_topmenu">
          <!--nav-map-->
          <li class="webnav down_con"> <a href="#"><b class="icon_arr"><i class="fa fa-bars"></i>网站导航</b></a>
            <div class="jq_hidebox"> <span class="home-line"><a href="#"><i class="fa fa-home"></i>网站首页</a></span>
              <dl class="asty01">
                <dt><a href="http://www.17sucai.com/"><i class="fa fa-list-alt"></i>新闻</a></dt>
                <dd><a href="http://www.17sucai.com/"> 报考指导 </a> | <a href="http://www.17sucai.com/"> 招考信息 </a> | <a href="http://www.17sucai.com/"> 考试快讯 </a> <br />
                  <a href="http://www.17sucai.com/"> 职位查询 </a> | <a href="http://www.17sucai.com/"> 考试时间 </a> | <a href="http://www.17sucai.com/"> 时政热点 </a> </dd>
                <dt><a href="http://www.17sucai.com/"><i class="fa  fa-beer"></i>娱乐</a></dt>
                <dd><a href="http://www.17sucai.com/"> 行测辅导 </a> | <a href="http://www.17sucai.com/"> 申论辅导 </a> | <a href="http://www.17sucai.com/"> 面试辅导 </a> <br />
                  <a href="http://www.17sucai.com/"> 备考技巧 </a> | <a href="http://www.17sucai.com/"> 名师指导 </a> | <a href="http://www.17sucai.com/"> 历年真题 </a> </dd>
                <dt><a href="http://www.17sucai.com/"><i class="fa  fa-video-camera"></i>视频</a></dt>
                <dd><a href="http://www.17sucai.com/"> 公考论坛 </a> | <a href="http://www.17sucai.com/"> 在线模考 </a> | <a href="http://www.17sucai.com/"> 申论批改 </a> <br />
                  <a href="http://www.17sucai.com/"> 在线答疑 </a> | <a href="http://www.17sucai.com/"> 照片调整 </a> | <a href="http://www.17sucai.com/"> 信息预约 </a> </dd>
                <dt><a href="http://www.17sucai.com/"><i class="fa  fa-picture-o"></i>图片</a></dt>
                <dd><a href="http://www.17sucai.com/"> 面授课程 </a> | <a href="http://www.17sucai.com/"> 网校课程 </a> | <a href="http://www.17sucai.com/"> 图书教材 </a> <br />
                  <a href="http://www.17sucai.com/"> 直播课堂 </a> | <a href="http://www.17sucai.com/"> 公考讲堂 </a> | <a href="http://www.17sucai.com/"> 一对一 </a> <br>
                </dd>
              </dl>
            </div>
          </li>
          <!--help-->
          <li><a href="#"><b>帮助中心</b></a></li>
          <!--down-->
          <li><a href="#"><b>下载软件</b></a></li>
          <!--register-->
          <li><a href="#"><b>注册</b></a></li>
          <!--login-->
          <li class="down_con"> <a href="#"><b class="icon_arr">登录</b></a>
            <div class="jq_hidebox login_box">
              <div></div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <!--logo-->
  <div class="top-search-logo">
    <div class="top-search-logo-box">
      <div class="head-wrapper"> <a href="#" class="head-logo fl"><img src="images/logo.png"></a>
        <!--<div class="head-engine fl">
                	<div class="engine-wrapper">
                	  	<form id="form1" name="form1" method="post">
                      	<div class="engine-options">
                        		<span class="baidu">百度<i class="fa fa-caret-down"></i></span>
                       	  </div>
                       	<div class="engine-inputs">
                            <input type="text" name="textfield" id="textfield" class="engine-key">
                            <input type="button" name="button" id="button" value="" class="engine-submit submit-bit">
                         </div>
              	   		</form>
                	</div>
              	</div>-->
        <div class="search_box fl"> <span class="left l_bg"></span> <span class="right r_bg"></span>
          <div class="search">
            <form name=search_form onSubmit="return bottomForm(this);" target="_blank" method="post">
              <div id="pt1" class="select"> <a id="s0">全站搜索</a>
                <div style="display:none;" id="pt2" class="part">
                  <p> <a id="s1">新闻</a> <a id="s2">图片</a> <a id="s3">视频</a> </p>
                </div>
              </div>
              <input id="catid" name="catid" type="hidden" value="7" />
              <input id="q" class="enter" name="infos" onFocus="if(this.value=='请输入关键词…'){this.value='';}else{this.select();}this.style.color='black';"  value="请输入关键词…" />
              <input class="sb" name="input" type="submit" value="" />
            </form>
          </div>
        </div>
        <!--weather-->
        <div class="head-weather fr"> <span>星期三</span> <span>24～10℃</span> <span>小雨</span> <span>合肥</span> </div>
      </div>
    </div>
  </div>
  <!--nav-->
  <div class="nav">
  <div class="main_box">
        	<ul>
        		
        	
            	<li class="on"><a href="${SiteBase}">首页</a></li>
            	
            	
				<cms:Class idList="child:root">
				<li><a href="${Class.classUrl}">${Class.className}</a></li>
				</cms:Class>
			
            	
            	
        	
        	
              	
              	
           </ul>
	</div>
  
   
  </div>
</div>
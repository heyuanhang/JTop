<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<script type="text/javascript" src="${ResBase}js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${ResBase}js/jquery.form.js"></script>
<script type="text/javascript" src="${ResBase}js/jquery.slides.min.js"></script>
<script type="text/javascript" src="${ResBase}js/swfobject_modified.js"></script>
<script type="text/javascript" src="${ResBase}js/common.js"></script>
<script type="text/javascript" src="${ResBase}js/commonUtil_src.js"></script>

<script src="${ResBase}js/jquery.slides.js" language="javascript" type="text/javascript"></script>

<div class="header">  
  <div class="top_nav">
    <div class="top_nav_inner">
      <div class="fl nav-collect"> <div id="regDiv" ><a href="${SiteBase}member/member_login.jsp">会员登录</a> <a href="${SiteBase}member/member_reg.jsp">注册新会员</a>   </div>  <div id="memberDiv" style="display:none">欢迎您！ <a href="${SiteBase}member/member_main_page.jsp"> <span id="memName"></span> </a>您当前登录在 <span id="memSiteName"></span>  <a href="javascript:loginOut('<cms:Token />');">注销</a></div></div>
      <div class="siderNav fr">
        <ul class="topmenu" id="jq_topmenu">
          <!--nav-map-->
          <li class="webnav down_con"> <a href="#"><b class="icon_arr"> 网站导航</b></a>
            <div class="jq_hidebox"> <span class="home-line"><a href="${SiteBase}">网站首页</a></span>
              <dl class="asty01">
                
                <cms:Class idList="child:root">
                	<dt><a href="${Class.channelUrl}">${Class.className}</a></dt>
                	
                	<dd>
                	<cms:Class idList="child:${Class.classId}" objName="ChildClass">
                	
                	
                	<cms:if test="${status.count % 3 == 0}">
                		<a href="${ChildClass.classUrl}"> ${ChildClass.className} </a><br/>
                	
                	</cms:if>
                	<cms:else>
                		<a href="${ChildClass.classUrl}"> ${ChildClass.className} </a> | 
                	
                	</cms:else>
                
                	</cms:Class>
                	</dd>
                </cms:Class>	
                
                
              </dl>
            </div>
          </li>
          <!--help-->
       <li><a href="javascript:void(0)"><b>帮助中心</b></a></li>
          <!--down-->
           
          <!--register
          <li><a href="#"><b>JTopCMS群号 : 34833246</b></a></li>
          -->
          
          <%--
          <!--login-->
          <li class="down_con">
           <a href="#"><b class="icon_arr">登录</b></a>
            <div class="jq_hidebox login_box">
              <div></div>
            </div>
          </li>
        --%></ul>
      </div>
    </div>
  </div>
  <script type="text/javascript">
 
 					 //会员状态
  					var url = '${SiteBase}member/memberStatus.do';
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			    		dataType:"json",
			       		success: function(mem)
			            {   
			           
			            	if(mem != '')
			            	{
			            		
			            		
			            		$('#regDiv').hide();
			            		$('#memberDiv').show();
			            		 
			            		$('#memName').html(mem.memberName);
			            		$('#prevDt').html(mem.prevLoginDt);
			            		$('#memSiteName').html(mem.loginSiteName);
			            		
			            		
			            	}
			            	 
			               
			               
			                
			            }
			     	});	
			     	

  
  </script>
  <!--logo-->
  <div class="top-search-logo">
    <div class="top-search-logo-box">
      <div class="head-wrapper"> <a href="http://tc.xyw.gz.cn" class="head-logo fl">
	   <img src="${ResBase}images/logo.png"> 
	 
	  </a>
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
            <form name=search_form  target="_blank" method="post">
              <div id="pt1" class="select"> <a id="s0">全站搜索</a>
                <div style="display:none;" id="pt2" class="part" >
                  <p> <cms:Model siteMode="false">
                  		
                  		<a href="javascript:changeMod('${Model.dataModelId}','${Model.modelName}');" id="s1">${Model.modelName}</a>
                  
                  	  </cms:Model> </p>
                </div>
              </div>
              <input id="catid" name="catid" type="hidden" value="7" />
             
             <cms:if test="${empty param.keyword}">
             	<input id="key" class="enter" name="infos" onFocus="if(this.value=='请输入关键词…'){this.value='';}else{this.select();}this.style.color='black';"  value="请输入关键词…" />           
             </cms:if>
             <cms:else>
             		 <input id="key" class="enter" name="infos" onFocus="if(this.value=='请输入关键词…'){this.value='';}else{this.select();}this.style.color='black';"   value='<cms:DecodeParam enc="utf-8" str="${param.keyword}" />' /> 
             </cms:else>

              <input class="sb" name="input" type="button" onclick="javascript:search();"  />
            </form>
          </div>
        </div>
        <!--weather-->
        <div class="head-weather fr" id="siteVisStat">本站访问总量: </iframe>
 
 		<script type="text/javascript">
 
 					 
			     	
			     	
			     	 //站点访问状态
  					var url = '${SiteBase}stat/visInfo.do';
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {      

							var msg = eval("("+mg+")");		
						            
			            	if(msg != '')
			            	{
			            		var Vis = eval("("+msg+")");
			            		
			            		 $('#siteVisStat').html('本站访问总量:'+Vis.all.pv+ '    本日访问量:'+Vis.day.pv);
			            		
			            		
			            	}
			            	 
			               
			               
			                
			            }
			     	});	
			     	
	
  
  
  
  </script>
 
 
 </div>
      </div>
    </div>
  </div>
  <!--nav-->
  <div class="nav">
  <div class="main_box">
        	<ul>
        		
        	   <%--  <cms:if test="${empty param.channelId && empty param.cid && empty param.currClassId}">
						<li class="on"><a href="${SiteBase}">首页</a></li>
				</cms:if>
				<cms:else>
						<li ><a href="${SiteBase}">首页${param.currId}</a></li>
				</cms:else> --%>
            	
            	
            		
            
					<cms:Class idList="child:root">
						<cms:if test="${Class.classId == param.channelId}">
					 
							<cms:if test="${Class.haveChannel}">
							
								<li class="on"><a href="${Class.channelUrl}">${Class.className}</a></li>
							
							</cms:if>
							<cms:else>
							
								<li class="on"><a href="${Class.classUrl}">${Class.className}</a></li>
														
							</cms:else>
							
						</cms:if>
						<cms:elseif test="${param.cid > 0}">
						
							 <cms:Class idList="parent:${param.cid}" objName="PClass">
							 
							 	<cms:if test="${PClass.layer ==2}">
							 		<!-- cid为三级栏目，需要取父亲的父亲 -->
							 		
							 		<cms:Class idList="parent:${PClass.classId}" objName="RClass">
							 		
							 			<cms:if test="${Class.classId == RClass.classId}">
											<li class="on"><a  href="${Class.channelUrl}">${Class.className}</a></li>
										</cms:if>
										<cms:else>
											<li><a href="${Class.channelUrl}">${Class.className}</a></li>
										</cms:else>	
							 		
							 		</cms:Class>
							 								 		
							 	</cms:if>
							 	<cms:else>
							 	    <!-- cid为二级栏目 -->
							 		<cms:if test="${Class.classId == PClass.classId}">
										
										<cms:if test="${Class.haveChannel}">
							
											<li class="on"><a href="${Class.channelUrl}">${Class.className}</a></li>
										
										</cms:if>
										<cms:else>
										
											<li class="on"><a href="${Class.classUrl}">${Class.className}</a></li>
																	
										</cms:else>
										
										
									</cms:if>
									<cms:else>
										<li><a href="${Class.channelUrl}">${Class.className}</a></li>
									</cms:else>							 	
							 	</cms:else>
							 	
							 
							 </cms:Class>
							 
						</cms:elseif>						
						<cms:elseif test="${param.currClassId > 0}">
						
							 <cms:Class idList="parent:${param.currClassId}" objName="PClass">
							 
							 	<cms:if test="${Class.classId == PClass.classId}">
									<li class="on"><a  href="${Class.channelUrl}">${Class.className}</a></li>
								</cms:if>
								<!-- 列表型频道内容页栏目 -->
								<cms:elseif test="${PClass.classId == -1}">
								
									<cms:if test="${Class.classId == param.currClassId}">
										<li class="on"><a  href="${Class.channelUrl}">${Class.className}</a></li>
									</cms:if>
									<cms:else>
										<li><a href="${Class.channelUrl}">${Class.className} </a></li>
									</cms:else>
									
								</cms:elseif>
								<cms:else>
									<li><a href="${Class.channelUrl}">${Class.className} </a></li>
								</cms:else>
							 
							 </cms:Class>
							 
						</cms:elseif>						
						<cms:else>
							
							
							<cms:if test="${Class.haveChannel}">
							
								<li><a href="${Class.channelUrl}">${Class.className}</a></li>
							
							</cms:if>
							<cms:else>
							
								<li><a href="${Class.classUrl}">${Class.className}</a></li>
														
							</cms:else>
							
							
						</cms:else>
					
					
					</cms:Class>
			
            	
				
			

           </ul>
	</div>
  
   
  </div>
</div>




<script type="text/javascript">
basePath = '${SiteBase}';

var searchModeId = '${param.searchModelId}';


<cms:if test="${empty param.searchModelId}">
  		$('#s0').html('全站搜索');
</cms:if>
<cms:else>
$('#s0').html('全站搜索s');
  		<cms:Model id="${param.searchModelId}">
                  		
         $('#s0').html('${Model.modelName}'); 
                  
        </cms:Model>
</cms:else>

//顶部导航下拉菜单
$(document).ready(function(){
	$("#jq_topmenu li").hover(function(){
		$(this).addClass("hover").find("div.jq_hidebox").show();
	},function(){
		$(this).removeClass("hover").find("div.jq_hidebox").hide();
	});
	
	
	$("#pt1").hover(function(){
		$("#pt2").show();
	},function(){
		$("#pt2").hide();
	});
	
	
});





function changeMod(id, modelName)
{
	$('#s0').html(modelName);
	
	searchModeId = id;
	
 
}


function search(page)
{
	var searchKey = '';
	
	if('请输入关键词…' != document.getElementById("key").value)
	{
		searchKey = document.getElementById("key").value;
	}
	
	if(searchKey == '')
	{
		window.location = "${SiteBase}search.jsp";
	}
	else
	{
		if(page!=null)
		{
			window.location = "${SiteBase}search.jsp?page="+page+"&searchModelId="+searchModeId+"&keyword="+encodeURI(encodeURIComponent(searchKey));
		}
		else
		{	 
		searchKey = encodeData(searchKey); 
		 
	    searchKey= 	encodeURI(encodeURIComponent(searchKey));
		

	 
			window.location = "${SiteBase}search.jsp?keyword="+searchKey+"&searchModelId="+searchModeId;
		}
	}
	//replaceUrlParam(window.location,encodeURI("key="+document.getElementById("key").value));
}


</script>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="GBK">
	<title>图文查看</title>
	<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		
			<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>

		
		 
	 <style type="text/css">
		* {
		    -webkit-box-sizing: border-box;
		    box-sizing: border-box;
		    -webkit-user-select: none;
		    outline: 0;
		    -webkit-tap-highlight-color: transparent;
		    -webkit-tap-highlight-color: transparent;
		}
		h1,h2,h3,h4,h5,h6,p{
			margin: 0;
			padding: 0;
		}
		img{
			border: 0;
		}
		 
		.phone {
		    margin: 0 auto;
		    position: relative;
		   
		    border-radius: 55px;
		   
		    width: 320px;
		   
		    padding: 10px 25px 10px 25px;
		    -webkit-box-sizing: content-box;
		    box-sizing: content-box;
		}
		.phone:before {
		    content: '';
		    width: 60px;
		    height: 10px;
		    border-radius: 10px;
		    position: absolute;
		    left: 50%;
		    margin-left: -30px;
		    background: #333;
		    top: 50px;
		}
		 
		.phone .container {
		    width: 320px;
		    height: 85%;
		    display: block;
		    width: 100%;
		    margin-top: 2px;
		}

		.mui-content {
		    background-color: #efeff4;
		    height: 100%;
		    -webkit-overflow-scrolling: touch;
		    padding: 15px;
		    box-sizing: border-box;
		    overflow: auto;
		}
		.mui-bar-nav~.mui-content {
		    padding-top: 64px;
		}
		.mui-bar {
		    position: absolute;
		    z-index: 10;
		    right: 0;
		    left: 0;
		    height: 44px;
		    line-height: 44px;
		    padding-right: 10px;
		    padding-left: 10px;
		    border-bottom: 0;
		    background-color: #111;
		    color: #fff;
		    font-size: 1.4em;
		    text-align: center;
		    -webkit-backface-visibility: hidden;
		    backface-visibility: hidden;
		}
		.mui-slider {
		    position: relative;
		    z-index: 1;
		    overflow: hidden;
		    width: 100%;
		    background: #fff;
		    border: 1px solid #ccc;
		    border-radius: 4px;
		    box-sizing: border-box;
		}
		.mui-slider .mui-slider-group {
		    font-size: 0;
		    position: relative;
		    -webkit-transition: all 0s linear;
		    transition: all 0s linear;
		    white-space: nowrap;
		}
		.mui-slider .mui-slider-group .mui-slider-item {
		    font-size: 14px;
		    position: relative;
		    display: inline-block;
		    width: 100%;
		    height: 100%;
		    padding: 10px;
		    vertical-align: top;
		    white-space: normal;
		    box-sizing: border-box;
		}
		.mui-slider-item a{
			color: #fff;
		}
		.mui-slider-title {
		    line-height: 20px;
		    position: absolute;
		    bottom: 10px;
		    left: 10px;
		    right: 10px;
		    /*width: 100%;*/
		    margin: 0;
		    padding: 5px;
		    box-sizing: border-box;
		    text-align: left;
		    opacity: .8;
		    font-size: 16px;
		    background-color: #000;
		}
		.mui-slider .mui-slider-group .mui-slider-item img {
		    width: 100%;
		}

		.mui-table-view {
		    position: relative;
		    margin-top: 10px;
		    margin-bottom: 0;
		    padding-left: 0;
		    list-style: none;
		    background-color: #fff;
		}
		.mui-table-view:before {
		    position: absolute;
		    right: 0;
		    left: 0;
		    height: 1px;
		    content: '';
		    -webkit-transform: scaleY(.5);
		    transform: scaleY(.5);
		    background-color: #c8c7cc;
		    top: -1px;
		}
		/*.mui-table-view:after {
		    position: absolute;
		    right: 0;
		    bottom: 0;
		    left: 0;
		    height: 1px;
		    content: '';
		    -webkit-transform: scaleY(.5);
		    transform: scaleY(.5);
		    background-color: #c8c7cc;
		}*/
		.mui-table-view-cell {
		    position: relative;
		    overflow: hidden;
		    padding: 11px 15px;
		    -webkit-touch-callout: none;
		}
		.mui-table-view-cell a{
			text-decoration: none;
		}
		.mui-table-view .mui-media, .mui-table-view .mui-media-body {
		    overflow: hidden;
		}
		.mui-table-view-cell>a:not(.mui-btn) {
		    position: relative;
		    display: block;
		    overflow: hidden;
		    margin: -11px -15px;
		    padding: inherit;
		    text-overflow: ellipsis;
		    color: inherit;
		}
		.mui-pull-right {
		    float: right;
		}
		.mui-table-view .mui-media-object {
		    line-height: 42px;
		    max-width: 42px;
		    height: 42px;
		}
		.mui-table-view .mui-media-object.mui-pull-right {
		    margin-left: 10px;
		}
		.mui-table-view .mui-media, .mui-table-view .mui-media-body {
		    overflow: hidden;
		}
		.mui-table-view-cell:after {
		    position: absolute;
		    right: 0;
		    bottom: 0;
		    left: 0;
		    height: 1px;
		    content: '';
		    -webkit-transform: scaleY(.5);
		    transform: scaleY(.5);
		    background-color: #c8c7cc;
		}
		.phone .statusbar {
		  
		}
	</style>
</head>
<body>
<div class="phone">
	<div class="container">
		 
		<div class="mui-content">
			
			<div class="mui-slider">
			
				<cms:CurrentSite>
						<cms:QueryData objName="News" service="cn.com.mjsoft.cms.weixin.service.WeixinService" method="getNewsItemGroupForTag"  var="${CurrSite.siteFlag},${param.rowFlag}">
						<cms:if test="${status.first}">
							<!-- big img -->
							<div class="mui-slider-group mui-slider-loop" >
								<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
								<div class="mui-slider-item mui-slider-item-duplicate">
									<a href="javascript:openEditWxNewsInfoDialog('${News.rowFlag}','${News.infoId}');">
										<img src="${News.img}">
										<p class="mui-slider-title" style="font-size:14px"><font color="white">${News.title}</font></p>
									</a>
								</div>
								
							</div>
							<!-- end -->
							
							<!-- list img -->
							<ul class="mui-table-view">
						
						
						</cms:if>
						<cms:else>
						
							<li class="mui-table-view-cell mui-media">
								<a  href="javascript:openEditWxNewsInfoDialog('${News.rowFlag}','${News.infoId}');">
									<img class="mui-media-object mui-pull-right" src="${News.img}">
									<div class="mui-media-body">
										<p class="mui-ellipsis">${News.title}</p>
									</div>
								</a>
							</li>
						
						</cms:else>
				
				
						</cms:QueryData>
				</cms:CurrentSite>
			
				
				 
					
				</ul>
				<!-- end -->
				
			</div>
			<div class="breadnavTab"  >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
							<tr class="btnbg100">
								<div style="float:right">
									<a name="btnwithicosysflag" href="javascript:censor('1');"  class="btnwithico"><img src="../style/icons/light-bulb.png" width="16" height="16" /><b>通过&nbsp;</b> </a>
									<a name="btnwithicosysflag" href="javascript:censor('0');"  class="btnwithico"><img src="../style/icons/light-bulb-off.png" width="16" height="16" /><b>无效&nbsp;</b> </a>
									
									
									<a href="javascript:close();"  class="btnwithico" onclick=""><img src="../style/icon/close.png" width="16" height="16"/><b>关闭&nbsp;</b> </a>
								&nbsp;
								</div>
								<td></td>
								 
							</tr>
						</table>
		   </div>

			
		</div>
	</div>
	<!-- statusbar -->
	<div class="statusbar"></div>
</div>	
</body>
</html>
<script>

var api = frameElement.api, W = api.opener;


  
function close()
{
	api.close();
}
	
function censor(flag)
{
	
	var url = "<cms:BasePath/>wx/censor.do?saId=${param.saId}&censor="+flag+"&<cms:Token mode='param'/>";
	
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(mg)
			            {      
			            	var msg = eval("("+mg+")");
			           
			             if(msg ==  'success')
			               {
			               		 	
			               		W.$.dialog({ 
				   					title :'提示',
				    				width: '140px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				api:true,
				                    content: '审核操作成功!',
				                    
				                    ok: function () 
				                    { 
				                    	W.window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               }
			               else if(mg.indexOf('send job submission success') != -1)
			               {
			               		 	
			               		W.$.dialog({ 
				   					title :'提示',
				    				width: '140px', 
				    				height: '60px', 
				                    lock: true, 
				    				icon: '32X32/succ.png', 
				    				api:true,
				                    content: '审核操作成功!',
				                    
				                    ok: function () 
				                    { 
				                    	W.window.location.reload();
				                    }
				                    
    								
                                 });
			               		
			               	
			               }
			               else if('sendover' == msg)
			               {
			               	       W.$.dialog(
								   { 
									   					title :'提示',
									    				width: '260px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				api:true,
									                    content: "已经推送到微信的群发不可再审核！",
							
									    				cancel: function () 
									                    { 
									                    	W.window.location.reload();
									                    }
									});
			               }   
			               
			               else if(mg.indexOf('errcode') != -1)
				           {
				           		W.$.dialog({ 
					   					title :'提示',
					    				width: '270px', 
					    				height: '60px', 
					    				 api:true,
					                    lock: true, 
					    				icon: '32X32/fail.png',
					                    content: msg, 
					                    ok: function(){ 
				      						W.window.location.reload();
				    					} 
						  		});
				           		
				           } 
			               
			               else
			               {
			               	       W.$.dialog(
								   { 
									   					title :'提示',
									    				width: '200px', 
									    				height: '60px', 
									                    lock: true, 
									                     
									    				icon: '32X32/fail.png', 
									    				api:true,
									                    content: "执行失败，无权限请联系管理员！",
							
									    				cancel: function () 
									                    { 
									                    	W.window.location.reload();
									                    }
									});
			               }   
			              
			            }
			     	});	
	  
}

function openEditWxNewsInfoDialog(sindex, infoId)
{
	W.$.dialog({ 
	    id : 'oeccd',
    	title : '查看图文素材 - ID: '+infoId,
    	width: '1100px', 
    	height: '690px', 
    	parent:api,
    	lock: true, 
        max: false, 
        min: false,
        resize: false,
       
        content: 'url:<cms:Domain/>core/weixin/CensorWxNewsInfo.jsp?index='+sindex+'&uid='+Math.random()+'&typeId=${param.typeId}&infoId='+infoId+'&dialogApiId=oeccd'
	});
}
</script>

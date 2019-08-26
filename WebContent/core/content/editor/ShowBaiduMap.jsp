<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../../common/js/jquery-1.7.gzjs"></script>
		<title>百度地图</title>


		<script type="text/javascript">
		
		var iscreatr=false;
		
		function initialize() {
		//---------------------------------------------基础示例---------------------------------------------
		var map = new BMap.Map("allmap",{minZoom:8,maxZoom:25});            // 创建Map实例
		
		
	  	//var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
		//map.addControl(ctrl_nav);
	
		
		//map.centerAndZoom(new BMap.Point(116.4035,39.915),15);  //初始化时，即可设置中心点和地图缩放级别。
		
		var currentArea = '<cms:DecodeParam  enc="utf-8" str="${param.area}" />';
		 
		if('${param.px}' == '' || '${param.py}' == '')
		{
			map.centerAndZoom("北京", 13);
		}
		else
		{
			map.centerAndZoom(new BMap.Point(${param.px},${param.py}),${param.zoom});
			
		}                    // 初始化地图,设置中心点坐标和地图级别。

		map.enableScrollWheelZoom(false);//鼠标滑动轮子可以滚动
		
		//标注
		var mt = '<cms:DecodeParam  enc="utf-8" str="${param.mt}" />';
		
		var mc = '<cms:DecodeParam  enc="utf-8" str="${param.mc}" />';
		
		var tPx = '${param.tPx}';
		
		var tPy = '${param.tPy}';
		
		if(tPx != '' && tPy != '')
		{
			var point = new BMap.Point(tPx,tPy);
		
			var marker = new BMap.Marker(point);
				
			
			map.addOverlay(marker);
					
			
		
			
			if('' != mt || '' != mc)
			{
				map.centerAndZoom(point, ${param.zoom});
				
				var opts = {
				  width : 200,     // 信息窗口宽度
				  height: 60,     // 信息窗口高度
				  title : mt , // 信息窗口标题
				  enableMessage:false
				}
				var infoWindow = new BMap.InfoWindow(mc, opts);  // 创建信息窗口对象
				marker.openInfoWindow(infoWindow,point); //开启信息窗口
				
				marker.addEventListener("click", function(e){ 
					this.openInfoWindow(infoWindow);
				});
			
			}
		}
		

		

		//---------------------------------------------改变鼠标样式---------------------------------------------
		//需要自己制作。cur格式的静态光标
		//map.setDefaultCursor("url('01.cur')");        //设置地图默认的鼠标指针样式 
		//map.setDraggingCursor("url('03.cur')");         //设置地图拖拽时的鼠标指针样式
		
	}
 
	function loadScript() {
	   var script = document.createElement("script");
	   script.src = "http://api.map.baidu.com/api?v=1.4&callback=initialize";
	   document.body.appendChild(script);

	  
	}
	



</script>


	</head>
	<body onload="loadScript();">
		
		<div id="allmap" style="width: ${param.w}px; height: ${param.h}px"></div>
					
	</body>
</html>

<script type="text/javascript">
function showMap()
{
	
}
</script>

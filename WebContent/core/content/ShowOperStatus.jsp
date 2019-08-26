<%@ page contentType="text/html; charset=utf-8" session="false"%>

<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet"/>
<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
<!--加载 js -->
<script type="text/javascript" src="../style/blue/js/tab_change.js"></script>
<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>
<script type="text/javascript" src="../javascript/commonUtil.js"></script>
<script type="text/javascript" src="../javascript/progressBar/progress.js"></script>


<script>
	 //去掉点击链接 虚线边框
      window.onload=function()
      {
     for(var i=0; i<document.links.length; i++)
     document.links[i].onclick=function(){this.blur()}
     }

	// var d = $.dialog({content: 'url:http://www.sohu.com'});
    
    //使用此组件之前，必须先调用组件初始化函数，否则组件会运行的不正常
    JProgressBar.init();
    var bar;
　

      </script>
</head>
<body>


<div style="height:25px;"></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr>
    <td  align="left" valign="top"><!--main start-->
       <script type="text/javascript">
             //创建一个宽300，高20,默认进度值为500，最大进度值为500的水平方向的进度条
             bar = new JProgressBar({
                 width:400,
                 height:20,
                 maxValue:500,
                 value:'0%'
             });
             //由于没有指定renderTo属性，所以默认的采用了document.write来输出到当前调用所在元素中
         </script>
    
    </td>
  </tr>

  <tr><td height="10">&nbsp;</td></tr>
</table>
<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
</body>
</html>

<script type="text/javascript">
var timeId = -1;

var uuid= "${param.eventKey}";

timeId = setInterval("showConvertRTStatus('"+uuid+"')",200);






function showConvertRTStatus(id)
{
    
   var url = '../../common/queryOperationRTStatus.do?eventKey=${param.eventKey}';
   
   $.ajax({type:'POST',async:false,url:url,success:
   function(da, textStatus)
   {
   		var data = eval("("+da+")");
           		
      if(data > 0)
   	  {
      	bar.setValue(data+'%',true);
      }
   
      
      if(data == '100')
      {
        	 
      	window.clearInterval(timeId);
      	 
      }
      return;
   }
   }
);

}







</script>


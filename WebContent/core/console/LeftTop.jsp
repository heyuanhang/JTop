<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

		<title>menu</title>
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />
		<link href="../style/blue/images/left.css" rel="stylesheet" type="text/css" />
		<link type="text/css" rel="stylesheet" href="../javascript/tree/xtree2b/css/xtree2.links.css"/>
		<script type="text/javascript" src="../common/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="../javascript/tree/xtree2b/js/xtree2.js"></script>

		<script>
	 	//去掉点击链接 虚线边框
        window.onload=function()
        {
     		for(var i=0; i<document.links.length; i++)
     		document.links[i].onclick=function(){this.blur()}
        }
	  
		
      </script>


		<style type="text/css">
	  	body{ padding:0px;}
	  </style>

	</head>

	<body class="menutree">

		<table width="100%" height="100%" align="left" border='0' cellspacing='0' cellpadding='0'>
			<tr>
				<td>
					<div>
						<table width="100%" border="0" cellspacing="1" cellpadding="1" class="left_tab">
							<tr>
								<td valign="middle">
									<a id="c1" class="a1" onmouseover="javascript:outCheck(1);" onmouseout="javascript:outCheck(1);" onclick="javascript:gotoContentInfo()"><img src="<cms:Domain/>core/style/icon/report.png" width="16" height="16" title="栏目内容管理" /> </a>
								</td>
								<td>
									<a id="c2" class="a1" onmouseover="javascript:outCheck(2);" onmouseout="javascript:outCheck(2);" onclick="javascript:gotoSpecialInfo()"><img src="<cms:Domain/>core/style/icon/folder_table.png" width="16" height="16" title="专题管理" /> </a>
								</td>
								<td>
									<a id="c3" class="a1" onmouseover="javascript:outCheck(3);" onmouseout="javascript:outCheck(3);" href="javascript:gotoCommendType();"><img src="<cms:Domain/>core/style/icon/database_table.png" width="16" height="16" title="推荐位管理" /> </a>
								</td>
								
								<td>
									<a id="c5" class="a1" onmouseover="javascript:outCheck(5);"  onmouseout="javascript:outCheck(5);"  href="javascript:gotoMessage();"><img src="<cms:Domain/>core/style/icons/document-search-result.png" width="16" height="16" title="全站内容快速管理 " /> </a>
								</td>
								
								<td>
									<a id="c4" class="a1" onmouseover="javascript:outCheck(4);" onmouseout="javascript:outCheck(4);" href="javascript:gotoManageTrashContent();"><img src="<cms:Domain/>core/style/icon/trash2.png" width="16" height="16" title="回收站" /> </a>
								</td>
	
								
								<%--<td>
									<a id="c6" class="a1" onmouseover="javascript:outCheck(6);" onmouseout="javascript:outCheck(6);" href="javascript:gotoWorkbench();"><img src="<cms:Domain/>core/style/icon/application-task.png" width="16" height="16" title="我的工作台" /> </a>
								</td>
							--%></tr>
						</table>
						<div style="height:2px;"></div>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>

<script>
	 	
	 	
	 	
	  	var c1In = false;
	  	var c2In = false;
	  	var c3In = false;
	  	var c4In = false;
	  	var c5In = false;
	  	var c6In = false;
	  	
	  	//init
	  	$('#c1').attr('class','a3');
			
		$('#c2').attr('class','a1');
		$('#c3').attr('class','a1');
		$('#c4').attr('class','a1');
		$('#c5').attr('class','a1');
		$('#c6').attr('class','a1');
			
		c1In = true;		
			
			
	  	
		function gotoContentInfo()
		{
			window.parent.document.getElementById('lcFrame').src="<cms:Domain/>core/console/ListClass.jsp";
			
			$('#c1').attr('class','a3');
			
			$('#c2').attr('class','a1');
			$('#c3').attr('class','a1');
			$('#c4').attr('class','a1');
			$('#c5').attr('class','a1');
			$('#c6').attr('class','a1');
			
			c1In = true;
			
			c2In = false;
			c3In = false;
			c4In = false;
			c5In = false;
			c6In = false;
			
		}
		
		function gotoSpecialInfo()
		{
			window.parent.document.getElementById('lcFrame').src="<cms:Domain/>core/console/ListSpecialClass.jsp";
			
			$('#c2').attr('class','a3');
			
			$('#c1').attr('class','a1');
			$('#c3').attr('class','a1');
			$('#c4').attr('class','a1');
			$('#c5').attr('class','a1');
			$('#c6').attr('class','a1');
			
			c2In = true;
			
			c1In = false;
			c3In = false;
			c4In = false;
			c5In = false;
			c6In = false;
		}
		
		function gotoCommendType()
		{
			window.parent.document.getElementById('lcFrame').src="<cms:Domain/>core/console/ListCommentType.jsp";
			
			$('#c3').attr('class','a3');
			
			$('#c1').attr('class','a1');
			$('#c2').attr('class','a1');
			$('#c4').attr('class','a1');
			$('#c5').attr('class','a1');
			$('#c6').attr('class','a1');
			
			c3In = true;
			
			c2In = false;
			c1In = false;
			c4In = false;
			c5In = false;
			c6In = false;
		}
		
		function gotoManageTrashContent()
		{
			window.parent.document.getElementById('lcFrame').src="<cms:Domain/>core/console/ListTrashClass.jsp";
			
			$('#c4').attr('class','a3');
			
			$('#c1').attr('class','a1');
			$('#c2').attr('class','a1');
			$('#c3').attr('class','a1');
			$('#c5').attr('class','a1');
			$('#c6').attr('class','a1');
			
			c4In = true;
			
			c2In = false;
			c3In = false;
			c1In = false;
			c5In = false;
			c6In = false;
		}
		
		
		function gotoMessage()
		{
			window.parent.parent.document.getElementById('main').src="<cms:Domain/>core/content/QuickManageContent.jsp";
			
			$('#c5').attr('class','a3');
			
			$('#c1').attr('class','a1');
			$('#c2').attr('class','a1');
			$('#c4').attr('class','a1');
			$('#c3').attr('class','a1');
			$('#c6').attr('class','a1');
			
			c5In = true;
			
			c2In = false;
			c3In = false;
			c4In = false;
			c1In = false;
			c6In = false;
		}
		
		function gotoWorkbench()
		{
			window.parent.parent.document.getElementById('main').src="<cms:Domain/>core/console/Workbench.jsp?isReply=0";
			
			$('#c6').attr('class','a3');
			
			$('#c1').attr('class','a1');
			$('#c2').attr('class','a1');
			$('#c4').attr('class','a1');
			$('#c5').attr('class','a1');
			$('#c3').attr('class','a1');
			
			c6In = true;
			
			c2In = false;
			c3In = false;
			c4In = false;
			c5In = false;
			c1In = false;
		}
		
		function outCheck(flag)
		{		
			var currentClass = $('#c'+flag+'').attr('class');
			
			var testFlag = 0;
			
			if(c1In)
			{
				testFlag =1;
			}
			if(c2In)
			{
				testFlag =2;
			}
			if(c3In)
			{
				testFlag =3;
			}
			if(c4In)
			{
				testFlag =4;
			}
			if(c5In)
			{
				testFlag =5;
			}
			if(c6In)
			{
				testFlag =6;
			}
			
			
			
			
			//没选中
			if(testFlag !=flag )
			{
				if(currentClass == 'a1')
				{
					$('#c'+flag+'').attr('class','a2');
				}
				else
				{
					$('#c'+flag+'').attr('class','a1');
				}
			}

			
		}

		
		
		
		
      </script>

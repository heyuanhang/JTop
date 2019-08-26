<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link href="../style/blue/css/main.css" type="text/css" rel="stylesheet" />
		<link href="../style/blue/css/reset-min.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="../javascript/commonUtil_src.js"></script>
		<script type="text/javascript" src="../common/js/jquery-1.7.gzjs"></script>

		<script type="text/javascript" src="../javascript/progressbar/js/jquery.progressbar.js"></script>

		<script>
	    	
    
        </script>
	</head>
	<body>

		<!--main start-->
		<div class="auntion_tagRoom" style="margin-top:2px">
			<ul>
				<li id="two1" class="selectTag">
					<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />问卷文本&nbsp;</a>
				</li>

			</ul>
		</div>

		<div style="height:10px;"></div>
		<center>
			<cms:Survey surveyId="${param.sid}">
				<table class="listdate-high" width="99%" border="0" cellpadding="0" cellspacing="0">

					<tr class="datahead">

						<td width="50%" align="left" colspan="3">
							<strong>&nbsp;${status.count}.${Survey.surveyTitle}</strong>
						</td>

					</tr>
					<cms:SurveyOptText page="true" size="6">

						<tr>
							<td width="14%">
								${OptText.voteMan}
							</td>
							<td align="left" width="37%">
								<div style="height:5px"></div>
								&nbsp;
								<textarea style="width:446px; height:56px;" readonly class="form-textarea">${OptText.voteText}</textarea>
								<div style="height:5px"></div>

							</td>
							<td width="15%">
								<center>
									${OptText.ip}
									<br />
									<cms:IPArea ip="${OptText.ip}" />
								</center>
							</td>
						</tr>





					</cms:SurveyOptText>
					<cms:Empty flag="OptText">
						<tr>
							<td class="tdbgyew" colspan="3">
								<center>
									当前没有数据!
								</center>
							</td>
						</tr>
					</cms:Empty>


					<cms:PageInfo>
						<tr id="pageBarTr">
							<td colspan="3" class="PageBar" align="left">
								<div class="fr">
									<span class="text_m"> 共 ${Page.totalCount} 行记录 第${Page.currentPage}页 / ${Page.pageCount}页 <input type="text" size="4" id="pageJumpPos" name="pageJumpPos" /> <input type="button" name="goto" value="GOTO" onclick="javascript:jump()" /> </span>
									<span class="page">[<a href="javascript:query('h');">首页</a>]</span>
									<span class="page">[<a href="javascript:query('p');">上一页</a>]</span>
									<span class="page">[<a href="javascript:query('n');">下一页</a>]</span>
									<span class="page">[<a href="javascript:query('l');">末页</a>]</span>&nbsp;
								</div>
								<script>
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
			                                                           cp=1;
																	}
																
																	
																	replaceUrlParam(window.location,'pn='+cp);		
																}
													
													
																function jump()
																{
																	replaceUrlParam(window.location,'pn='+document.getElementById('pageJumpPos').value);
																}
															</script>
								<div class="fl"></div>
							</td>
						</tr>
					</cms:PageInfo>
				</table>

				<!--问卷文本-->


			</cms:Survey>
		</center>
		
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


</script>


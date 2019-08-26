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
					<a href="javascript:;"><img src="../style/blue/icon/application-share.png" width="16" height="16" />投票比例&nbsp;</a>
				</li>





			</ul>
		</div>

		<div style="height:10px;"></div>
		<center>
			<cms:Survey surveyId="${param.sid}">
				<table class="listdate-high" id="" width="99%" cellpadding="0" cellspacing="0">

					<tr class="datahead">

						<td width="50%" align="left" colspan="3">
							<strong>&nbsp;${status.count}.${Survey.surveyTitle}</strong>
						</td>

					</tr>
					<cms:SurveyOption>
						<cms:if test="${Survey.optionType==1}">

							<tr>
								<td width="1%">
									&nbsp;
								</td>
								<td align="left" width="55%">
									<input name="jtopcms-survey-${Survey.surveyId}" type="radio" value="${Option.optionId}" />
									${Option.optionText}

								</td>
								<td>
									<span class="progressBar" id="pb-${Option.optionId}">%${Option.votePer}%</span>
									<script type="text/javascript">
																	$("#pb-${Option.optionId}").progressBar({ boxImage:'<cms:BasePath/>core/javascript/progressbar/images/progressbar.gif', barImage: '<cms:BasePath/>core/javascript/progressbar/images/progressbg_green.gif'} );
															</script>
								</td>
							</tr>
						</cms:if>

						<cms:elseif test="${Survey.optionType==2}">

							<tr>
								<td>
									&nbsp;
								</td>
								<td align="left">
									<input name="jtopcms-survey-${Survey.surveyId}" type="checkbox" value="${Option.optionId}" />
									${Option.optionText}

								</td>
								<td>
									<span class="progressBar" id="pb-${Option.optionId}">%${Option.votePer}%</span>
									<script type="text/javascript">
																		$("#pb-${Option.optionId}").progressBar({  boxImage:'<cms:BasePath/>core/javascript/progressbar/images/progressbar.gif', barImage: '<cms:BasePath/>core/javascript/progressbar/images/progressbg_green.gif'} );
																</script>
								</td>
							</tr>

						</cms:elseif>


						<cms:elseif test="${Survey.optionType==3}">
							<tr>
								<td>
									&nbsp;
								</td>
								<td align="left">
									<div style="height:5px"></div>
									<input name="jtopcms-survey-${Survey.surveyId}" type="radio" value="${Option.optionId}" />
									<img width="90" height="67" src="${Option.optionImage}" style="vertical-align:middle;" />
									<div style="height:5px"></div>
								</td>
								<td>
									<span class="progressBar" id="pb-${Option.optionId}">%${Option.votePer}%</span>
									<script type="text/javascript">
																		$("#pb-${Option.optionId}").progressBar({ boxImage:'<cms:BasePath/>core/javascript/progressbar/images/progressbar.gif', barImage: '<cms:BasePath/>core/javascript/progressbar/images/progressbg_green.gif'} );
																</script>
								</td>
						</cms:elseif>
						
						<cms:elseif test="${Survey.optionType==4}">
							<tr>
								<td>
									&nbsp;
								</td>
								<td align="left">
									<div style="height:5px"></div>
									<input name="jtopcms-survey-${Survey.surveyId}" type="checkbox" value="${Option.optionId}" />
									<img width="90" height="67" src="${Option.optionImage}" style="vertical-align:middle;" />
									<div style="height:5px"></div>
								</td>
								<td>
									<span class="progressBar" id="pb-${Option.optionId}">%${Option.votePer}%</span>
									<script type="text/javascript">
																		$("#pb-${Option.optionId}").progressBar({ boxImage:'<cms:BasePath/>core/javascript/progressbar/images/progressbar.gif', barImage: '<cms:BasePath/>core/javascript/progressbar/images/progressbg_green.gif'} );
																</script>
								</td>
						</cms:elseif>

						<cms:elseif test="${Survey.optionType==5}">
							<tr>
								<td>
									&nbsp;
								</td>
								<td align="left">

									<textarea style="width:446px; height:55px;" id="jtopcms-text-survey-${Survey.surveyId}" name="jtopcms-text-survey-${Survey.surveyId}"></textarea>

								</td>
								<td>

								</td>
						</cms:elseif>


					</cms:SurveyOption>
				</table>

				<!--问卷文本-->


			</cms:Survey>
		</center>
		<div style="height:5px;"></div>
		<!--[if lt IE 7]>
        <script type="text/javascript" src="js/unitpngfix.js"></script>
<![endif]-->
	</body>
</html>

<script type="text/javascript">


</script>


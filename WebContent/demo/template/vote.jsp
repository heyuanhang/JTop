<%@ page contentType="text/html; charset=utf-8"%>
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
		<cms:Include page="include/head.jsp" />
		<!--头部结束-->

		<script type="text/javascript" src="${ResBase}tool/progressbar/js/jquery.progressbar.js"></script>


		<div class="main_br"></div>
		<!--主体开始-->
		<div class="main_box">


			<!--左侧-->
			<div >

				<div  >
					<!--新闻详情页开始-->
					<div class="ep-content-main">

						<div class="clearfix">

							<div id="endText">


								<div class="bs-example">
									<div class="tie-titlebar">
										<span style="left:120px; top:30px; font-size:18px; font-weight:bold; padding:10px 15px; color:#06c">调查问卷</span>
										<ins>

										</ins>
									</div>


									<form id="voteForm" name="voteForm" method="post">


										<cms:Survey groupFlag="mh_wj1">
										<center>
											<table border="0" cellpadding="0" cellspacing="0" width="90%">

												<tr>
													<td width="1%">
														 
													</td>
													<td width="30%" align="left">
														${status.count}.${Survey.surveyTitle}
													</td>
													<td>
														 
													</td>
												</tr>
												<cms:SurveyOption>
													<cms:if test="${Survey.optionType==1}">

														<tr>
															<td>
																 
															</td>
															<td align="left">
																<input name="jtopcms-survey-${Survey.surveyId}" type="radio" value="${Option.optionId}" />
																${Option.optionText}

															</td>
															<td>
																<span class="progressBar" id="pb-${Option.optionId}">%${Option.votePer}%</span>
																<script type="text/javascript">
																	$("#pb-${Option.optionId}").progressBar({ boxImage:'${ResBase}tool/progressbar/images/progressbar.gif', barImage: '${ResBase}tool/progressbar/images/progressbg_green.gif'} );
															
															
															</script>
															</td>
															<td align="right">
																${Option.vote}票
															</td>
														</tr>
													</cms:if>

													<cms:elseif test="${Survey.optionType==2}">

														<tr>
															<td>
																 
															</td>
															<td align="left">
																<input name="jtopcms-survey-${Survey.surveyId}" type="checkbox" value="${Option.optionId}" />
																${Option.optionText}

															</td>
															<td>
																<span class="progressBar" id="pb-${Option.optionId}">%${Option.votePer}%</span>
																<script type="text/javascript">
																		$("#pb-${Option.optionId}").progressBar({ boxImage:'${ResBase}tool/progressbar/images/progressbar.gif', barImage: '${ResBase}tool/progressbar/images/progressbg_green.gif'}  );
																</script>
															</td>
															<td align="right">
																${Option.vote}票
															</td>
														</tr>

													</cms:elseif>


													<cms:elseif test="${Survey.optionType==3}">
														<tr>
															<td>
																 
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
																		$("#pb-${Option.optionId}").progressBar({ barImage: '{ boxImage:'${ResBase}tool/progressbar/images/progressbar.gif', barImage: '${ResBase}tool/progressbar/images/progressbg_green.gif'}  );
																</script>
															</td>
															<td align="right">
																${Option.vote}票
															</td>
													</cms:elseif>

													<cms:elseif test="${Survey.optionType==4}">
														<tr>
															<td>
																 
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
																		$("#pb-${Option.optionId}").progressBar({ boxImage:'${ResBase}tool/progressbar/images/progressbar.gif', barImage: '${ResBase}tool/progressbar/images/progressbg_green.gif'}  );
																</script>
															</td>
															<td align="right">
																${Option.vote}票
															</td>
													</cms:elseif>

													<cms:elseif test="${Survey.optionType==5}">
														<tr>
															<td>
																 
															</td>
															<td align="left">

																<textarea style="width:840px; height:55px;" id="jtopcms-text-survey-${Survey.surveyId}" name="jtopcms-text-survey-${Survey.surveyId}"></textarea>

															</td>
															<td>

															</td>
															<td>

															</td>
													</cms:elseif>


												</cms:SurveyOption>
											</table>
											<br />
											<!--问卷文本-->


										</cms:Survey>
										
</center>
										<input type="hidden" id="jtopcms-group-survey-mh_wj1" name="jtopcms-group-survey-mh_wj1" value="mh_wj1" />
									

									<br/>
									<div class="highlight">
										<table border="0" cellpadding="0" cellspacing="0" width="100%">

											<tr>
												 
												<td   width="100%">
													<table width="98%" border="0" cellpadding="0" cellspacing="0">
														<tr>

															<td  width="10%">
																验证码:
															</td>
															<td width="12%">

																<input name="jtopcms-vote-captcha-mh_wj1" id="jtopcms-vote-captcha-mh_wj1" type="text" class="form-control" style="width:80px" maxlength="4" />

															</td>
															<td width="7%">
																<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=2&point=160&width=90&height=24&jump=4" />

															</td>
															<td width="7%">
																 <a style="cursor: pointer;" onclick="javascript:changeCode();">刷新</a>
																 
															</td>
															<td>
																 <span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:voteIndex2();">
																	提交问卷
																</button> 
																</span>
																 
															</td>

														</tr>
													</table>
												</td>
												</td>
											</tr>


										</table>
										
									</div>
								</div>

							</div>
							</form>
							<!--评论-->


						</div>
					</div>
					<!--新闻详情页结束-->

				</div>


			</div>

			<!--左侧结束-->
			 
		</div>

		<!--主体结束-->

		<cms:Include page="include/foot.jsp" />


	</body>
</html>

<cms:VisStat  />
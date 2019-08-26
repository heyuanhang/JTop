<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<title>调查问卷演示</title>
		<!--[if IE 7]>
<link rel="stylesheet" href="css/font-awesome-ie7.css">
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
			<div class="layoutcon news-br mt15">

				<div class="layoutLeft pr15">
					<!--新闻详情页开始-->
					<div class="ep-content-main">

						<div class="clearfix">

							<div id="endText">




								<div class="bs-example">
									<div class="tie-titlebar">
										<strong>调查问卷</strong>
										<ins>

										</ins>
									</div>







									<form id="voteForm" name="voteForm" method="post">


										<cms:Survey groupFlag="mh_wj1">
											<table border="0" cellpadding="0" cellspacing="0">

												<tr>
													<td width="1%">
														&nbsp;
													</td>
													<td width="50%" align="left">
														${status.count}.${Survey.surveyTitle}
													</td>
													<td>
														&nbsp;
													</td>
												</tr>
												<cms:SurveyOption>
													<cms:if test="${Survey.optionType==1}">

														<tr>
															<td>
																&nbsp;
															</td>
															<td align="left">
																<input name="jtopcms-survey-${Survey.surveyId}" type="radio" value="${Option.optionId}" />
																${Option.optionText}

															</td>
															<td>
															<span class="progressBar" id="pb-${Option.optionId}">%${Option.votePer}%</span>
															<script type="text/javascript">
																	$("#pb-${Option.optionId}").progressBar({ barImage: '${ResBase}tool/progressbar/images/progressbg_green.gif'} );
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
																		$("#pb-${Option.optionId}").progressBar({ barImage: '${ResBase}tool/progressbar/images/progressbg_green.gif'} );
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

																<input name="jtopcms-survey-${Survey.surveyId}" type="radio" value="${Option.optionId}" />
																<img width="90" height="67" src="${Option.optionImage}" />
															</td>
															<td>
																<span class="progressBar" id="pb-${Option.optionId}">%${Option.votePer}%</span>
																<script type="text/javascript">
																		$("#pb-${Option.optionId}").progressBar({ barImage: '${ResBase}tool/progressbar/images/progressbg_green.gif'} );
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
											<br />
											<!--问卷文本-->


										</cms:Survey>
										<table border="0" cellpadding="0" cellspacing="0">

											<tr>
												<td>
													&nbsp;
												</td>
												<td align="left">
													验证码:
												</td>
												<td>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td>

												</td>
												<td align="left">
													<table border="0" cellpadding="0" cellspacing="0">
														<input name="jtopcms-vote-captcha-mh_wj1" id="jtopcms-vote-captcha-mh_wj1" type="text" size="11" maxLength="4" />


														<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=2&point=160&width=90&height=24&jump=6" />
														&nbsp;&nbsp;
														<button onclick="javascript:changeCode();">
															重刷验证码
														</button>
													</table>
												</td>
												<td>
													&nbsp;
												</td>
											</tr>

										</table>

										<input type="hidden" id="jtopcms-group-survey-mh_wj1" name="jtopcms-group-survey-mh_wj1" value="mh_wj1" />
									</form>





















									<div class="highlight">

										<span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:javascript:voteIndex2();">
												提交问卷
											</button> </span>
									</div>
								</div>


















							</div>

							<!--评论-->


						</div>
					</div>
					<!--新闻详情页结束-->

				</div>


			</div>

			<!--左侧结束-->
			<div class="area-sub fr mt15">

				<cms:Include page="block/content_click_order.jsp" />
				<%--<cms:Block flag="mh_sy_hd"></cms:Block>--%>

				<!--排行结束-->
				<div class="main_br"></div>
				<!--时政聚焦-->
				<div class="news-pai" style="width:auto;">
					<div class="p-title">
						时政聚焦
					</div>
					<div class="layout-news list-main video-li">
						<div class="list-sub-figure130">
							<div class="list-figure">
								<div class="m-img">
									<span class="imgs"><img src="img/051708.jpg" width="130" height="89" alt="" /> </span>
									<span class="name"><a href="#">葛兰素史克行贿</a> </span>
								</div>
							</div>
							<div class="news-li-2 fr">
								<ul>
									<li>
										<a href="#">沪指底部波动中现抬高迹象</a>
									</li>
									<li>
										<a href="#">投资高手智慧：无招胜有招</a>
									</li>
									<li>
										<a href="#">沪港通开启 AH股如何投资</a>
									</li>
									<li>
										<a href="#">留学季可关注美元理财</a>
									</li>
									<li>
										<a href="#">奢侈品寄卖瞄上普通白领</a>
									</li>
								</ul>
							</div>
						</div>
						<ul class="mb15">
							<li>
								<a href="#"><b>地下炒金敛财黑幕:赔钱交易算数</b> </a>
								<a href="#"><b>赚钱违规</b> </a>
							</li>
							<li>
								<a href="#">网购珠宝风险高退货难：要价40万</a>
								<a href="#">估值5万</a>
							</li>
							<li>
								<a href="#">股市流行舌尖体:被套股民自嘲“关灯吃面”</a>
							</li>
							<li>
								<a href="#">购房人欲通过中介靠关系买房</a>
								<a href="#">被骗百余万</a>
							</li>
							<li>
								<a href="#">李大霄:对创业板是利空</a>
								<a href="#">张海东：将延续调</a>
							</li>
							<li>
								<a href="#">网购珠宝风险高退货难：要价40万</a>
								<a href="#">估值5万</a>
							</li>
							<li>
								<a href="#">股市流行舌尖体:被套股民自嘲“关灯吃面”</a>
							</li>
							<li>
								<a href="#">购房人欲通过中介靠关系买房</a>
								<a href="#">被骗百余万</a>
							</li>
						</ul>
					</div>
				</div>
				<!--时政聚焦结束-->
				<div class="main_br"></div>
				<div class="news-ad-2">
					<a href="#"><img src="img/051722.jpg" width="300" height="400" alt="" /> </a>
				</div>
			</div>


		</div>

		<!--主体结束-->

		<cms:Include page="include/foot.jsp" />


	</body>
</html>



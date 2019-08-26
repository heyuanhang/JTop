<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>


<cms:Member loginMode="true">

	<center>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<cms:ResInfo res="${Member.headPhoto}">
						<cms:if test="${empty Res.url}">
							<img width="120" height="120" src="${ResBase}images/def_avatar.png" />
						</cms:if>

						<cms:else>
							<img width="120" height="120" src="${Res.resize}" />
						</cms:else>

					</cms:ResInfo>
				</td>

			</tr>
			<tr>
				<td>
					欢迎您！
					<strong>${Member.memberName}</strong>
				</td>

			</tr>
			<tr>
				<td>
					<a href="${SiteBase}member/member_main.jsp">个人资料</a>
				</td>

			</tr>
			<tr>
				<td>
					<a href="${SiteBase}member/member_change_hp.jsp">修改头像</a>
				</td>

			</tr>
			<tr>
				<td>
					<a href="${SiteBase}member/member_content.jsp">我的投稿</a>
				</td>

			</tr>
			<tr>
				<td>
					<a href="${SiteBase}member/member_guestbook.jsp">我的留言</a>
				</td>

			</tr>
			<tr>
				<td>
					<a href="${SiteBase}member/member_comment.jsp">我的评论</a>
				</td>

			</tr>
			<tr>
				<td>
					<a href="${SiteBase}member/member_message.jsp">我的短信</a>
				</td>

			</tr>
			<tr>
				<td>
					<a href="${SiteBase}member/member_reg_mail.jsp">验证邮箱</a>
				</td>

			</tr>
			<tr>
				<td>
					<a href="${SiteBase}member/member_change_pw.jsp">修改密码</a>
				</td>

			</tr>


		</table>
	</center>
</cms:Member>

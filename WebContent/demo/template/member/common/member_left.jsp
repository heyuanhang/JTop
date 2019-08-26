<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<cms:Member loginMode="true" actInfo="cmt:msg:gb">
	<div class="my_myresume_l">
		<div class="my_user">
			<div class="avatar">
				<cms:ResInfo res="${Member.headPhoto}">
						<cms:if test="${empty Res.url}">
							<img width="64" height="64" src="${ResBase}images/def_avatar.png" />
						</cms:if>

						<cms:else>
							<img width="64" height="64" src="${Res.resize}" />
						</cms:else>

					</cms:ResInfo>
			</div>
			<div class="name">
				${Member.memberName}
			</div>
			<div class="u-tips">
				<ul>
					<li>
						<a href="${SiteBase}member/member_msg.jsp" class="li-line"><b>${Member.msgCount}</b>消息</a>
					</li>
					<li>
						<a href="${SiteBase}member/member_comm.jsp" class="li-line"><b>${Member.commCount}</b>评论</a>
					</li>
					<li>
						<a href="${SiteBase}member/member_gb.jsp"><b>${Member.gbCount}</b>留言</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="my_module">
			<ul>
				<li id="main_page">
					<a  href="${SiteBase}member/member_main_page.jsp">个人资料</a>
				</li>
				<li id="avatar_page">
					<a href="${SiteBase}member/member_change_avatar.jsp">更换头像</a>
				</li>
				<li d="con_page">
					<a href="${SiteBase}member/member_all_content.jsp">我的投稿</a>
				</li>
				<li id="gb_page">
					<a href="${SiteBase}member/member_gb.jsp">我的留言</a>
				</li>
				<li id="comm_page">
					<a href="${SiteBase}member/member_comm.jsp">我的评论</a>
				</li>
				<li id="msg_page">
					<a href="${SiteBase}member/member_msg.jsp">我的消息</a>
				</li>
				<li id="reg_mail_page">
					<a href="${SiteBase}member/member_reg_email.jsp">验证邮箱</a>
				</li>
				<li id="pw_page">
					<a href="${SiteBase}member/member_change_password.jsp">修改密码</a>
				</li>
				
				
			</ul>
			<script>
				if('${SiteBase}member/member_main_page.jsp' == window.location.href)
				{
					$('#main_page').addClass("active");
				}
				
				else if('${SiteBase}member/member_change_avatar.jsp' == window.location.href)
				{
					$('#avatar_page').addClass("active");
				}
				
				else if('${SiteBase}member/member_all_content.jsp' == window.location.href)
				{
					$('#con_page').addClass("active");
				}
				
				else if('${SiteBase}member/member_reg_email.jsp' == window.location.href)
				{
					$('#reg_mail_page').addClass("active");
				}
				
				else if('${SiteBase}member/member_change_password.jsp' == window.location.href)
				{
					$('#pw_page').addClass("active");
				}
				
				else if('${SiteBase}member/member_msg.jsp' == window.location.href)
				{
					$('#msg_page').addClass("active");
				}
				
				else if('${SiteBase}member/member_comm.jsp' == window.location.href)
				{
					$('#comm_page').addClass("active");
				}
				
				else if('${SiteBase}member/member_gb.jsp' == window.location.href)
				{
					$('#gb_page').addClass("active");
				}
				
				
			</script>
		</div>
	</div>
</cms:Member>

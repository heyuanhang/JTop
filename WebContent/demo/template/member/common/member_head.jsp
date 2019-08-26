<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<script type="text/javascript" src="${ResBase}js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="${ResBase}js/common.js"></script>

<script>

basePath = '${SiteBase}';

</script>

<cms:Member loginMode="true" jumpPage="member/member_login.jsp">
  <div class="user-box">
    <div class="logo"><img src="${ResBase}member/images/logo.png" alt=""/></div>
    <div class="nav">
      <ul>
        <li><a href="${SiteBase}">首页</a></li>
        <li><a href="${SiteBase}guestbook.jsp">我要留言</a></li>
       
      </ul>
    </div>
    <div class="user">
      <div class="user-avatar">
        <span class="avatar">
			<cms:ResInfo res="${Member.headPhoto}">
						<cms:if test="${empty Res.url}">
							<img width="25" height="25" src="${ResBase}images/def_avatar.png" />
						</cms:if>

						<cms:else>
							<img width="25" height="25" src="${Res.resize}" />
						</cms:else>

			</cms:ResInfo>
		</span>
        <span class="name"><a href="#">${Member.memberName}</a></span>
        <span class="name"><a href="javascript:">等级: ${Member.memLevel} 级 </a></span>
        <span class="name"><a href="javascript:">积分: ${Member.score} 分 </a></span>
        <span class="name"><a href="javascript:loginOut('${SiteBase}');">退出</a></span>
      </div>
    </div>
  </div>
</cms:Member>
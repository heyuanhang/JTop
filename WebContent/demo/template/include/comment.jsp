<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/cmsTag" prefix="cms"%>

<div class="bs-example">
	<div class="tie-titlebar">
		<strong>网友评论</strong>
		<ins>
			<a href="${SiteBase}comment_list.jsp?id=${param.targetId}" target="_blank"><span id="commCount">0</span>条评论</a>
			
			
				<script>
                  
                   
			        var url = '${SiteBase}content/status.do?id=${param.targetId}';
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(msg)
			            {     
			               var status = eval("("+msg+")");
			              
			               $('#commCount').text(status.commCount);
			             
			            }
			     	});	
                  
                  
                  </script>
			
		
		</ins>
	</div>
  <form id="commentForm" name="commentForm" method="post" style="padding:15px;">		


          <textarea name="commentText" id="commentText" style="height:90px" onFocus="if(this.value=='文明上网,请您发言…'){this.value='';}else{this.select();}this.style.color='black';"  class="form-control">文明上网,请您发言…</textarea>
          <input type="hidden" id="contentId" name="contentId" value="${param.targetId}" />
          <input type="hidden" id="classId" name="classId" value="${param.classId}" />
	
		<div class="highlight">
		 
	  <table width="460" border="0" cellpadding="0" cellspacing="0" class="fl">

			<tr>
				<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="120"  >
						呢称:
                                                  <input name="userName" id="userName" value="${Member.memberName}" type="text" maxLength="15" style="width:65px;"/>
						
						</td>
						
						<td width="120" >
							验证码:
						<input name="sysCheckCode" id="sysCheckCode" type="text" style="width:65px; maxLength="4"/>
						
						</td>
						
						<td width="100" >
						<img id="checkCodeImg" src="${SiteBase}common/authImg.do?count=4&line=2&point=160&width=90&height=24&jump=4" />
			
						
						</td>
						
						<td>
					          <a style="cursor: pointer;" onclick="javascript:changeCode();">刷新</a>
						
						</td>
					
					</tr>
				
				</table>
					 <script type="text/javascript">
 
 					 //会员状态
  					var url = '${SiteBase}member/memberStatus.do';
 		
			 		$.ajax({
			      		type: "POST",
			       		url: url,
			       		data:'',
			   
			       		success: function(msg)
			            {      
			            	var mem = eval("("+msg+")");
			            		
			            	if(msg != '')
			            	{
			            		
			            		 
			            		 
			            		$('#userName').val(mem.memberName);
			            		 
			            		
			            		
			            	}
			            	 
			               
			               
			                
			            }
			     	});	
			     	
	
  
  
  
  				</script>
				 
			</tr>

		</table>	
		</form>
          <span class="fr"><button type="button" class="btn btn-primary" onclick="javascript:commentContent();">
				发表评论
			</button>
		</span>
	</div>
</div>
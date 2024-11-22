<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% Map<String, String> profile = (Map<String, String>)request.getAttribute("profile"); %>

<main>
	<div class="container">
		<div class="profile">
			<div class="display-title">
				<h2>Profile</h2>
			</div>
			<div class="display">
				<div class="display-tbody">
					<div class="display-td">Name</div>
					<div class="display-td"><%= profile.get("name") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Email</div>
					<div class="display-td"><%= profile.get("email") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Password</div>
					<%
						String password = profile.get("password");
						int count = password.length();
						String secretPassword = "";
						for (int i = 0; i < count; i++) {
							secretPassword += "*";
						}
					%>
					<div class="display-td">
						<span id="password_span" class="secret"><%= secretPassword %></span>
						<input type="button" id="switch_password" class="button" value="see password">
						<input type=hidden id="open_password" value="<%= password %>">
						<input type=hidden id="secret_password" value="<%= secretPassword %>">
					</div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Phone Number</div>
					<div class="display-td"><%= profile.get("countryCode") %> (<%= profile.get("phoneNumber").substring(0, 3) %>) <%= profile.get("phoneNumber").substring(3) %></div>
				</div>
			</div>
			<div class="links">
				<div class="link">
					<a href="<%= request.getContextPath() %>/top">Back to Top</a>
				</div>
			</div>
		</div>
	</div>
</main>
	
<jsp:include page="../common/footer.jsp" flush="true"/>

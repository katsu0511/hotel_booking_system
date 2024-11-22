<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<h2>Login</h2>
	
		<form class="login-form" action="<%= request.getContextPath() %>/login" method="POST">
			<div class="login-items">
				<div class="login-item">
					<label for="email" class="login-label">Email :</label>
					<div class="login-input">
						<input type="text" id="email" class="login-info" name="email" autocomplete="off">
					</div>
				</div>
				
				<div class="login-item">
					<label for="password" class="login-label">Password :</label>
					<div class="login-input">
						<input type="password" id="password" class="login-info" name="password" autocomplete="off">
					</div>
				</div>
				
				<% if (request.getAttribute("errorMessage") != null && request.getAttribute("errorMessage") != "") { %>
					<p class="error_message"><%= request.getAttribute("errorMessage") %></p>
				<% } %>
				
				<div class="login-item">
					<div class="login-submit">
						<input type="submit" class="button" name="loginBtn" value="Login">
					</div>
				</div>
			</div>
		</form>
		
		<div class="demo">
			<p>Demo Account :</p>
			<input type="button" id="demo_button" class="button" value="Demo">
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% ArrayList<String> countries = (ArrayList<String>)request.getAttribute("countries"); %>

<main>
	<div class="container">
		<h2>Login for hotel operator</h2>
	
		<form class="login-form" action="<%= request.getContextPath() %>/hotel_login" method="POST">
			<div class="login-items">
				<div class="login-item">
					<label for="country" class="login-label">Country :</label>
					<div class="login-input">
						<select id="country" name="country">
							<option value="">-</option>
							<% for (String country : countries) { %>
								<option value="<%= country %>"><%= country %></option>
							<% } %>
						</select>
					</div>
				</div>
				
				<div class="login-item">
					<label for="number" class="login-label">Phone Number :</label>
					<div class="login-input">
						<input type=text id="number" class="login-info" name="number">
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
			<input type="button" id="hotel_demo_button" class="button" value="Demo">
		</div>
		
		<div class="links">
			<div class="link">
				<a href="<%= request.getContextPath() %>/login">For guests</a>
			</div>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>

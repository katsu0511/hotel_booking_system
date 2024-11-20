<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
	<title>Hotel Booking System</title>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
	<script src="<%= request.getContextPath() %>/js/script.js" defer></script>
</head>
<body>

	<header>
		<div class="container">
			<h1><a href="<%= request.getContextPath() %>/top">Hotel Booking System</a></h1>
			<% if (session.getAttribute("email") != null && session.getAttribute("password") != null) { %>
				<form class="header-btn" action="<%= request.getContextPath() %>/logout" method="POST">
					<button type="submit" class="button">Logout</button>
				</form>
			<% } %>
		</div>
	</header>
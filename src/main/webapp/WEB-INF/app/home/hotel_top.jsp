<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% String hotel = (String)request.getAttribute("hotel"); %>

<main>
	<div class="container">
		<div class="hotel_title">
			<h2><%= hotel %></h2>
		</div>
		<div class="hotel_top">
			<div class="hotel_menus">
				<div class="menu">
					<a href="<%= request.getContextPath() %>/booking/status">Booking Status</a>
				</div>
				<div class="menu">
					<a href="<%= request.getContextPath() %>/service/status">Service Status</a>
				</div>
				<div class="menu">
					<a href="<%= request.getContextPath() %>/index/staff">Staff index</a>
				</div>
			</div>
		</div>
	</div>
</main>
	
<jsp:include page="../common/footer.jsp" flush="true"/>

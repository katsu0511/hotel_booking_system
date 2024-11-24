<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% ArrayList<Map<String, String>> bookings = (ArrayList<Map<String, String>>)request.getAttribute("bookings"); %>

<main>
	<div class="container">
		<div class="profile">
			<div class="display-title">
				<h2>My Booking</h2>
			</div>
			<div class="booking_list">
			<% if (bookings.isEmpty()) { %>
				<p>You don't have any bookings yet.</p>
			<% } else { %>
				<% for (Map<String, String> booking : bookings) { %>
					<div class="booking_div">
						<a href="<%= request.getContextPath() %>/show/booking?hotelId=<%= booking.get("hotelId") %>&roomNumber=<%= booking.get("roomNumber") %>&checkInDate=<%= booking.get("checkInDate") %>&checkOutDate=<%= booking.get("checkOutDate") %>">
							<div class="booking_display">
								<div class="booking"><%= booking.get("hotel") %></div>
								<div class="booking"><%= booking.get("roomNumber") %></div>
								<div class="booking"><%= booking.get("checkInDate") %> ~ <%= booking.get("checkOutDate") %></div>
							</div>
						</a>
					</div>
				<% } %>
			<% } %>
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

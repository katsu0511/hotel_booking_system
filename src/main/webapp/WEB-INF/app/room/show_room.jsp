<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% Map<String, String> hotel = (Map<String, String>)request.getAttribute("hotel"); %>
<% Map<String, String> room = (Map<String, String>)request.getAttribute("room"); %>
<% String guestId = (String)request.getAttribute("guestId"); %>

<main>
	<div class="container">
		<div class="profile">
			<div class="display-title">
				<h2><%= hotel.get("name") %></h2>
			</div>
			<div class="display">
				<div class="display-tbody">
					<div class="display-td">Address</div>
					<div class="display-td"><%= hotel.get("street") %>, <%= hotel.get("city") %>, <%= hotel.get("province") %>, <%= hotel.get("country") %><br><%= hotel.get("postalCode") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Phone Number</div>
					<div class="display-td"><%= hotel.get("countryCode") %> (<%= hotel.get("phoneNumber").substring(0, 3) %>) <%= hotel.get("phoneNumber").substring(3) %></div>
				</div>
			</div>
			<div class="room">
				<div class="display-tbody">
					<div class="display-td">Room Number</div>
					<div class="display-td"><%= room.get("number") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Type</div>
					<div class="display-td"><%= room.get("type") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Cost</div>
					<% double totalAmount = Double.parseDouble(room.get("cost")) + Double.parseDouble(room.get("tax")); %>
					<div class="display-td">$<%= totalAmount %></div>
				</div>
			</div>
			<div class="book">
			<form id="book" action="<%= request.getContextPath() %>/book" method="POST">
				<input type="hidden" id="hotel_id" name="hotel_id" value="<%= hotel.get("id") %>">
				<input type="hidden" id="guest_id" name="guest_id" value="<%= guestId %>">
				<input type="hidden" id="room_number" name="room_number" value="<%= room.get("number") %>">
				<input type="date" id="check_in" name="check_in" required>
				<input type="date" id="check_out" name="check_out" required>
				<p id="check_in_error" class="error">Please enter your check-in date.</p>
				<p id="check_out_error" class="error">Please enter your check-out date.</p>
				<input type="submit" id="book_btn" name="submit" value="book" form="book">
			</form>
			<a href="<%= request.getContextPath() %>/index/room?id=<%= hotel.get("id") %>">Back to Room Index</a>
			<a href="<%= request.getContextPath() %>/top">Back to Top</a>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>

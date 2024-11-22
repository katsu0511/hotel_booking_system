<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% Map<String, String> booking = (Map<String, String>)request.getAttribute("booking"); %>
<% String originalCheckIn = (String)request.getAttribute("originalCheckIn"); %>
<% String originalCheckOut = (String)request.getAttribute("originalCheckOut"); %>

<main>
	<div class="container">
		<div class="room_detail">
			<div class="display-title">
				<h2><%= booking.get("hotel") %></h2>
			</div>
			<div class="display">
				<h3>Room</h3>
				<div class="display-tbody">
					<div class="display-td">Room Number</div>
					<div class="display-td"><%= booking.get("roomNumber") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Type</div>
					<div class="display-td"><%= booking.get("roomType") %></div>
				</div>
			</div>
			<div class="book">
				<h3>Change booking date</h3>
				<form id="book" action="<%= request.getContextPath() %>/update/booking" method="POST">
					<input type="hidden" id="guest_id" name="guest_id" value="<%= booking.get("guestId") %>">
					<input type="hidden" id="hotel_id" name="hotel_id" value="<%= booking.get("hotelId") %>">
					<input type="hidden" id="room_number" name="room_number" value="<%= booking.get("roomNumber") %>">
					<input type="hidden" id="original_check_in" class="date" name="original_check_in" value="<%= booking.get("checkInDate") %>">
					<input type="hidden" id="original_check_out" class="date" name="original_check_out" value="<%= booking.get("checkOutDate") %>">
					<div class="date_inputs">
						<div class="date_input">
							<label for="check_in" class="date_label">Check in :</label>
							<input type="date" id="check_in" class="date" name="check_in" value="<%= booking.get("checkInDate") %>" required>
						</div>
						<div class="date_input">
							<label for="check_out" class="date_label">Check out :</label>
							<input type="date" id="check_out" class="date" name="check_out" value="<%= booking.get("checkOutDate") %>" required>
						</div>
					</div>
					<p id="check_in_error" class="error">Please enter your check-in date.</p>
					<p id="check_out_error" class="error">Please enter your check-out date.</p>
					<p id="dates_error" class="error">Dates are invalid.</p>
					<p id="date_error">
					<% if (originalCheckIn != null && originalCheckOut != null) { %>
						<p id="date_error">This room is already reserved on these dates.</p>
					<% } %>
					<input type="submit" id="book_btn" class="button" name="submit" value="change" form="book">
				</form>
				<div class="links">
					<div class="link">
						<a href="<%= request.getContextPath() %>/show/booking?hotelId=<%= booking.get("hotelId") %>&roomNumber=<%= booking.get("roomNumber") %>&checkInDate=<%= booking.get("checkInDate") %>&checkOutDate=<%= booking.get("checkOutDate") %>">Back to Booking Detail</a>
					</div>
					<div class="link">
						<a href="<%= request.getContextPath() %>/top">Back to Top</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>

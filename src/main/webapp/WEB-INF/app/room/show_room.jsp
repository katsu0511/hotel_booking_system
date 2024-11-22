<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% Map<String, String> hotel = (Map<String, String>)request.getAttribute("hotel"); %>
<% Map<String, String> room = (Map<String, String>)request.getAttribute("room"); %>
<% String guestId = (String)request.getAttribute("guestId"); %>
<% String checkIn = (String)request.getAttribute("checkIn"); %>
<% String checkOut = (String)request.getAttribute("checkOut"); %>

<main>
	<div class="container">
		<div class="room_detail">
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
			<div class="display">
				<h3>Room</h3>
				<div class="display-tbody">
					<div class="display-td">Room Number</div>
					<div class="display-td"><%= room.get("number") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Type</div>
					<div class="display-td"><%= room.get("type") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Cost / night</div>
					<%
						double cost = Double.parseDouble(room.get("cost"));
						double tax = Double.parseDouble(room.get("tax"));
						double totalCost = cost + tax;
					%>
					<div class="display-td">$<%= totalCost %> ( $<%= cost %> + tax $<%= tax %> )</div>
				</div>
			</div>
			<div class="book">
				<h3>Booking</h3>
				<form id="book" action="<%= request.getContextPath() %>/book" method="POST">
					<input type="hidden" id="hotel_id" name="hotel_id" value="<%= hotel.get("id") %>">
					<input type="hidden" id="guest_id" name="guest_id" value="<%= guestId %>">
					<input type="hidden" id="room_number" name="room_number" value="<%= room.get("number") %>">
					<div class="date_inputs">
						<div class="date_input">
							<label for="check_in" class="date_label">Check in :</label>
							<input type="date" id="check_in" class="date" name="check_in" value="<%= checkIn %>" required>
						</div>
						<div class="date_input">
							<label for="check_out" class="date_label">Check out :</label>
							<input type="date" id="check_out" class="date" name="check_out" value="<%= checkOut %>" required>
						</div>
					</div>
					<p id="check_in_error" class="error">Please enter your check-in date.</p>
					<p id="check_out_error" class="error">Please enter your check-out date.</p>
					<p id="dates_error" class="error">Dates are invalid.</p>
					<p id="date_error">
					<% if (checkIn != null && checkOut != null) { %>
						<p id="date_error">You can't book this room on these dates.</p>
					<% } %>
					<input type="submit" id="book_btn" class="button" name="submit" value="book" form="book">
				</form>
				<div class="links">
					<div class="link">
						<a href="<%= request.getContextPath() %>/index/room?id=<%= hotel.get("id") %>">Back to Room Index</a>
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

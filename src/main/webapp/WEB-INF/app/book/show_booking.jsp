<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% Map<String, String> booking = (Map<String, String>)request.getAttribute("booking"); %>
<% int numberOfNights = Integer.valueOf(String.valueOf(request.getAttribute("numberOfNights"))); %>

<main>
	<div class="container">
		<div class="profile">
			<div class="display-title">
				<h2>
					<a href="<%= request.getContextPath() %>/index/room?id=<%= booking.get("hotelId") %>">
						<%= booking.get("hotel") %>
					</a>
				</h2>
			</div>
			<div class="display">
				<div class="display-tbody">
					<div class="display-td">Address</div>
					<div class="display-td"><%= booking.get("street") %>, <%= booking.get("city") %>, <%= booking.get("province") %>, <%= booking.get("country") %><br><%= booking.get("postalCode") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Phone Number</div>
					<div class="display-td"><%= booking.get("countryCode") %> (<%= booking.get("phoneNumber").substring(0, 3) %>) <%= booking.get("phoneNumber").substring(3) %></div>
				</div>
			</div>
			<div class="room_display">
				<div class="room_display_tbody">
					<a href="<%= request.getContextPath() %>/show/room?id=<%= booking.get("hotelId") %>&number=<%= booking.get("roomNumber") %>">
						<div class="room_display_td">Room Number</div>
						<div class="room_display_td"><%= booking.get("roomNumber") %></div>
					</a>
				</div>
				<div class="display-tbody">
					<div class="display-td">Room type</div>
					<div class="display-td"><%= booking.get("roomType") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Check-in Date</div>
					<div class="display-td"><%= booking.get("checkInDate") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Check-out Date</div>
					<div class="display-td"><%= booking.get("checkOutDate") %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Number of Nights</div>
					<div class="display-td"><%= numberOfNights %> night<% if (numberOfNights > 1) { %>s<% } %></div>
				</div>
				<div class="display-tbody">
					<div class="display-td">Total Cost</div>
					<div class="display-td">
						<%
							double cost = Double.parseDouble(booking.get("cost"));
							double tax = Double.parseDouble(booking.get("tax"));
							double totalCost = (cost + tax) * numberOfNights;
						%>
						$<%= totalCost %> ($<%= cost * numberOfNights %> + tax $<%= tax * numberOfNights %>)
					</div>
				</div>
			</div>
			<div class="forms">
				<div class="cancel_booking">
				<form id="cancel_booking" action="<%= request.getContextPath() %>/cancel/booking" method="POST">
					<input type="hidden" id="guest_id" name="guest_id" value="<%= booking.get("guestId") %>">
					<input type="hidden" id="hotel_id" name="hotel_id" value="<%= booking.get("hotelId") %>">
					<input type="hidden" id="room_number" name="room_number" value="<%= booking.get("roomNumber") %>">
					<input type="hidden" id="check_in" name="check_in" value="<%= booking.get("checkInDate") %>">
					<input type="hidden" id="check_out" name="check_out" value="<%= booking.get("checkOutDate") %>">
					<input type="submit" id="cancel_btn" class="button" name="cancel_btn" value="Cancel Booking" form="cancel_booking">
				</form>
			</div>
			<div class="update_booking">
				<form id="update_booking" action="<%= request.getContextPath() %>/update/booking" method="GET">
					<input type="hidden" id="guest_id" name="guest_id" value="<%= booking.get("guestId") %>">
					<input type="hidden" id="hotel_id" name="hotel_id" value="<%= booking.get("hotelId") %>">
					<input type="hidden" id="room_number" name="room_number" value="<%= booking.get("roomNumber") %>">
					<input type="hidden" id="check_in" name="check_in" value="<%= booking.get("checkInDate") %>">
					<input type="hidden" id="check_out" name="check_out" value="<%= booking.get("checkOutDate") %>">
					<input type="submit" id="update_btn" class="button" name="update_btn" value="Update Booking" form="update_booking">
				</form>
			</div>
			</div>
			<div class="links">
				<div class="link">
					<a href="<%= request.getContextPath() %>/index/booking">Back to booking index</a>
				</div>
				<div class="link">
					<a href="<%= request.getContextPath() %>/top">Back to Top</a>
				</div>
			</div>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>

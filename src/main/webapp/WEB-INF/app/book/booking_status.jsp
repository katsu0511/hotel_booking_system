<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% ArrayList<Map<String, String>> bookings = (ArrayList<Map<String, String>>)request.getAttribute("bookings"); %>
<% ArrayList<Map<String, String>> staffs = (ArrayList<Map<String, String>>)request.getAttribute("staffs"); %>

<main>
	<div class="container">
		<div class="bookings">
			<div class="display-title">
				<h2>Booking Status</h2>
			</div>
			<div class="booking">
				<div class="booking_table">
					<div class="booking_thead">
						<div class="booking_row">
							<div class="booking_th">Guest Name</div>
							<div class="booking_th">Room Number</div>
							<div class="booking_th">Check-in Date</div>
							<div class="booking_th">Check-out Date</div>
							<div class="booking_th">Handles</div>
							<div class="booking_th">Payment Type</div>
							<div class="booking_th">Paid</div>
							<div class="booking_th"></div>
						</div>
					</div>
					<% for (Map<String, String> booking : bookings) { %>
						<div class="display_tbody">
							<div class="booking_row">
								<div class="booking_td"><%= booking.get("guestName") %></div>
								<div class="booking_td"><%= booking.get("roomNumber") %></div>
								<div class="booking_td"><%= booking.get("checkInDate") %></div>
								<div class="booking_td"><%= booking.get("checkOutDate") %></div>
								<div class="booking_td">
									<% if (booking.get("staffName") == null) { %>
										<select class="book_info" name="staffId">
											<option value="">-</option>
											<% for (Map<String, String> staff : staffs) { %>
												<option value="<%= staff.get("staffId") %>"><%= staff.get("staffName") %></option>
											<% } %>
										</select>
									<% } else { %>
										<%= booking.get("staffName") %>
									<% } %>
								</div>
								<div class="booking_td">
									<% if (booking.get("paymentType") == null) { %>
										<select class="book_info" name="paymentType">
											<option value="">-</option>
											<option value="Debit Card">Debit Card</option>
											<option value="Credit Card">Credit Card</option>
											<option value="Cash">Cash</option>
										</select>
									<% } else { %>
										<%= booking.get("paymentType") %>
									<% } %>
								</div>
								<div class="booking_td">
									<% if (booking.get("paid").equals("1")) { %>
										complete
									<% } else { %>
										incomplete
									<% } %>
								</div>
								<div class="booking_td">
									<% if (booking.get("paid").equals("0")) { %>
										<form class="paid" action="<%= request.getContextPath() %>/paid" method="POST">
											<input type="hidden" name="guest_id" value="<%= booking.get("guestId") %>">
											<input type="hidden" name="hotel_id" value="<%= booking.get("hotelId") %>">
											<input type="hidden" name="room_number" value="<%= booking.get("roomNumber") %>">
											<input type="hidden" name="check_in_date" value="<%= booking.get("checkInDate") %>">
											<input type="hidden" name="check_out_date" value="<%= booking.get("checkOutDate") %>">
											<input type="hidden" class="staff_id" name="staff_id" value="">
											<input type="hidden" class="payment_type" name="payment_type" value="">
											<button type="submit" class="button paid_btn">Paid</button>
										</form>
									<% } %>
								</div>
							</div>
						</div>
					<% } %>
				</div>
				<p id="staff_id_error" class="error">Please select a staff who handles the payment.</p>
				<p id="payment_type_error" class="error">Please select payment type.</p>
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

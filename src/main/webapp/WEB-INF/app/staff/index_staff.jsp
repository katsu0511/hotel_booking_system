<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% String hotelName = (String)request.getAttribute("hotelName"); %>
<% ArrayList<Map<String, String>> staffs = (ArrayList<Map<String, String>>)request.getAttribute("staffs"); %>
<% ArrayList<Map<String, String>> trainings = (ArrayList<Map<String, String>>)request.getAttribute("trainings"); %>

<main>
	<div class="container">
		<div class="bookings">
			<div class="display-title">
				<h2>Staff</h2>
			</div>
			<div class="booking">
				<div class="booking_table">
					<div class="booking_thead">
						<div class="booking_row">
							<div class="service_th">Hotel</div>
							<div class="service_th">Staff Name</div>
							<div class="service_th">Birth Date</div>
							<div class="service_th">Role</div>
						</div>
					</div>
					<% for (Map<String, String> staff : staffs) { %>
						<div class="display_tbody">
							<div class="booking_row">
								<div class="service_td"><%= hotelName %></div>
								<div class="service_td"><%= staff.get("name") %></div>
								<div class="service_td"><%= staff.get("birthDate") %></div>
								<div class="service_td"><%= staff.get("role") %></div>
							</div>
						</div>
					<% } %>
				</div>
			</div>
			<div class="display-title">
				<h2>Training</h2>
			</div>
			<div class="training">
				<div class="training_table">
					<div class="training_thead">
						<div class="training_row">
							<div class="training_th">Trainer</div>
							<div class="training_th">Trainee</div>
						</div>
					</div>
					<% for (Map<String, String> training : trainings) { %>
						<div class="training_tbody">
							<div class="training_row">
								<div class="training_td"><%= training.get("trainer") %></div>
								<div class="training_td"><%= training.get("trainee") %></div>
							</div>
						</div>
					<% } %>
				</div>
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

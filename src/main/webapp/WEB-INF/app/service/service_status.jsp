<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% ArrayList<Map<String, String>> uses = (ArrayList<Map<String, String>>)request.getAttribute("uses"); %>

<main>
	<div class="container">
		<div class="bookings">
			<div class="display-title">
				<h2>Service Status</h2>
			</div>
			<div class="booking">
				<div class="booking_table">
					<div class="booking_thead">
						<div class="booking_row">
							<div class="service_th">Guest Name</div>
							<div class="service_th">Service Type</div>
							<div class="service_th">Date</div>
							<div class="service_th">Time</div>
						</div>
					</div>
					<% for (Map<String, String> use : uses) { %>
						<div class="display_tbody">
							<div class="booking_row">
								<div class="service_td"><%= use.get("guestName") %></div>
								<div class="service_td"><%= use.get("serviceType") %></div>
								<div class="service_td"><%= use.get("useDate") %></div>
								<div class="service_td"><%= use.get("useTime") %></div>
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

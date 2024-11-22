<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% Map<String, String> hotel = (Map<String, String>)request.getAttribute("hotel"); %>
<% ArrayList<Map<String, String>> rooms = (ArrayList<Map<String, String>>)request.getAttribute("rooms"); %>

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
			<div class="room_display">
				<h3>Room List</h3>
				<% for (Map<String, String> room : rooms) { %>
					<div class="room_div">
						<a href="<%= request.getContextPath() %>/show/room?id=<%= hotel.get("id") %>&number=<%= room.get("number") %>">
							<div class="room_type">Room type: <%= room.get("type") %></div>
							<div class="room_cost">$<%= room.get("cost") %> <span>+ tax $<%= room.get("tax") %></span></div>
						</a>
					</div>
				<% } %>
			</div>
			<div class="links">
				<div class="link">
					<a href="<%= request.getContextPath() %>/index/hotel">Back to Hotel Index</a>
				</div>
				<div class="link">
					<a href="<%= request.getContextPath() %>/top">Back to Top</a>
				</div>
			</div>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>

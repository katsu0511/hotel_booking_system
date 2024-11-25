<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<jsp:include page="../common/header.jsp" flush="true"/>
<% ArrayList<Map<String, String>> hotels = (ArrayList<Map<String, String>>)request.getAttribute("hotels"); %>
<% ArrayList<Map<String, String>> costs = (ArrayList<Map<String, String>>)request.getAttribute("costs"); %>

<main>
	<div class="container">
		<div class="profile">
			<div class="display-title">
				<h2>Hotel</h2>
			</div>
			<div class="list">
				<div class="menus">
					<% for (Map<String, String> hotel : hotels) { %>
						<div class="menu">
							<a href="<%= request.getContextPath() %>/index/room?id=<%= hotel.get("id") %>">
								<span class="hotel_name"><%= hotel.get("name") %></span>
								<span class="hotel_costs">
								(
								<% for (Map<String, String> cost : costs) { %>
									<% if (hotel.get("id").equals(cost.get("id"))) { %>
										min: $<%= cost.get("min") %>, max: $<%= cost.get("max") %>
									<% } %>
								<% } %>
								)
								</span>
							</a>
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

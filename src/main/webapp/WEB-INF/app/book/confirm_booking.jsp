<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<div class="confirm_booking">
			<p>Your Booking has been completed!</p>
			<a href="<%= request.getContextPath() %>/top">Top</a>
			<a href="<%= request.getContextPath() %>/check/booking">Check my booking</a>
		</div>
	</div>
</main>
	
<jsp:include page="../common/footer.jsp" flush="true"/>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<div class="confirm_booking">
			<p>Your booking date has been changed!</p>
			<div class="links">
				<div class="link">
					<a href="<%= request.getContextPath() %>/top">Top</a>
				</div>
				<div class="link">
					<a href="<%= request.getContextPath() %>/index/booking">Check my booking</a>
				</div>
			</div>
		</div>
	</div>
</main>
	
<jsp:include page="../common/footer.jsp" flush="true"/>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<div class="top">
			<a href="<%= request.getContextPath() %>/profile">Profile</a>
			<a href="<%= request.getContextPath() %>/index/hotel">Search Hotel</a>
		</div>
	</div>
</main>
	
<jsp:include page="../common/footer.jsp" flush="true"/>

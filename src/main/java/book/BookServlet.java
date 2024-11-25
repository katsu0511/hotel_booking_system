package book;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import db.HotelDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/book")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		final String EMAIL = (String) session.getAttribute("email");
		final String PASSWORD = (String) session.getAttribute("password");
		
		if (EMAIL == null || PASSWORD == null) {
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			HotelDAO db = new HotelDAO();
			Connection conn = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			ResultSet rset1 = null;
			ResultSet rset2 = null;
			
			String hotelId = request.getParameter("hotel_id");
			String guestId = request.getParameter("guest_id");
			String roomNumber = request.getParameter("room_number");
			LocalDate checkIn = LocalDate.parse(request.getParameter("check_in"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalDate checkOut = LocalDate.parse(request.getParameter("check_out"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			boolean guestAuthentication = false;
			boolean datesAuthentication = true;
			
			try {
				conn = db.getConnection();
				String sql1 = "SELECT GuestID "
							+ "FROM Guest "
							+ "WHERE Email = ? AND Password = ?";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, EMAIL);
				pstmt1.setString(2, PASSWORD);
				rset1 = pstmt1.executeQuery();
				String guestIdInDatabase = "";
				if (rset1.next()) {
					guestIdInDatabase = rset1.getString(1);
				}
				
				if (guestId.equals(guestIdInDatabase)) {
					guestAuthentication = true;
				}
				
				
				String sql2 = "SELECT CheckInDate, CheckOutDate "
							+ "FROM Book "
							+ "WHERE HotelID = ? AND RoomNumber = ? OR GuestID = ? "
							+ "ORDER BY CheckInDate";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, hotelId);
				pstmt2.setString(2, roomNumber);
				pstmt2.setString(3, guestId);
				rset2 = pstmt2.executeQuery();
				ArrayList<Map<String, LocalDate>> checkDates = new ArrayList<Map<String, LocalDate>>();
				while (rset2.next()) {
					Map<String, LocalDate> checkDate = new HashMap<>();
					checkDate.put("checkInDate", rset2.getDate(1).toLocalDate());
					checkDate.put("checkOutDate", rset2.getDate(2).toLocalDate());
					checkDates.add(checkDate);
				}
				
				for (Map<String, LocalDate> checkDate : checkDates) {
					if (
						(checkIn).isEqual(checkDate.get("checkInDate")) ||
						(checkIn).isAfter(checkDate.get("checkInDate")) && (checkIn).isBefore(checkDate.get("checkOutDate")) ||
						(checkOut).isEqual(checkDate.get("checkOutDate")) ||
						(checkOut).isAfter(checkDate.get("checkInDate")) && (checkOut).isBefore(checkDate.get("checkOutDate")) ||
						(checkIn).isBefore(checkDate.get("checkInDate")) && (checkOut).isAfter(checkDate.get("checkOutDate"))
					) {
						datesAuthentication = false;
					}
				}
				
				
				if (guestAuthentication && datesAuthentication) {
					String sql3 = "INSERT INTO Book (GuestID, HotelID, RoomNumber, CheckInDate, CheckOutDate, PaymentType, Paid) "
								+ "VALUES (?, ?, ?, ?, ?, null, 0)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, guestId);
					pstmt3.setString(2, hotelId);
					pstmt3.setString(3, roomNumber);
					pstmt3.setString(4, checkIn.toString());
					pstmt3.setString(5, checkOut.toString());
					pstmt3.executeUpdate();
				}
				
				
				hotelId = URLEncoder.encode(hotelId, "UTF-8");
				guestId = URLEncoder.encode(guestId, "UTF-8");
				roomNumber = URLEncoder.encode(roomNumber, "UTF-8");
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt1.close();
				} catch (SQLException e) { }
				
				try {
					pstmt2.close();
				} catch (SQLException e) { }
				
				try {
					if (guestAuthentication && datesAuthentication) {
						pstmt3.close();
					}
				} catch (SQLException e) { }
				
				try {
					rset1.close();
				} catch (SQLException e) { }
				
				try {
					rset2.close();
				} catch (SQLException e) { }
				
				try {
					conn.close();
				} catch (SQLException e) {  }
			}
			
			if (guestAuthentication && datesAuthentication) {
				response.sendRedirect(request.getContextPath() + "/confirm/booking");
			} else if (!guestAuthentication) {
				response.sendRedirect(request.getContextPath() + "/top");
			} else {
				response.sendRedirect(request.getContextPath() + "/show/room?id=" + hotelId + "&number=" + roomNumber + "&checkIn=" + checkIn + "&checkOut=" + checkOut);
			}
		}
	}
}

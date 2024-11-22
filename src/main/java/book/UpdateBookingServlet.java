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
 * Servlet implementation class UpdateBookingServlet
 */
@WebServlet("/update/booking")
public class UpdateBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			
			final String GUEST_ID = request.getParameter("guest_id");
			final String HOTEL_ID = request.getParameter("hotel_id");
			final String ROOM_NUMBER = request.getParameter("room_number");
			final String CHECK_IN = request.getParameter("check_in");
			final String CHECK_OUT = request.getParameter("check_out");
			final String ORIGINAL_CHECK_IN = request.getParameter("original_check_in");
			final String ORIGINAL_CHECK_OUT = request.getParameter("original_check_out");

			try {
				conn = db.getConnection();
				String sql = "SELECT b.GuestID, h.HotelID, h.Name, r.RoomNumber, r.RoomType, b.CheckInDate, b.CheckOutDate "
						   + "FROM Hotel h, Room r, Book b "
						   + "WHERE h.HotelID = r.HotelID "
						   + "AND h.HotelID = b.HotelID "
						   + "AND r.RoomNumber = b.RoomNumber "
						   + "AND b.GuestID = ? "
						   + "AND h.HotelID = ? "
						   + "AND r.RoomNumber = ? "
						   + "AND b.CheckInDate = ? "
						   + "AND b.CheckOutDate = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, GUEST_ID);
				pstmt.setString(2, HOTEL_ID);
				pstmt.setString(3, ROOM_NUMBER);
				if (ORIGINAL_CHECK_IN == null) {
					pstmt.setString(4, CHECK_IN);
				} else {
					pstmt.setString(4, ORIGINAL_CHECK_IN);
				}
				if (ORIGINAL_CHECK_OUT == null) {
					pstmt.setString(5, CHECK_OUT);
				} else {
					pstmt.setString(5, ORIGINAL_CHECK_OUT);
				}
				rset = pstmt.executeQuery();
				Map<String, String> booking = new HashMap<>();
				if (rset.next()) {
					booking.put("guestId", rset.getString(1));
					booking.put("hotelId", rset.getString(2));
					booking.put("hotel", rset.getString(3));
					booking.put("roomNumber", rset.getString(4));
					booking.put("roomType", rset.getString(5));
					if (ORIGINAL_CHECK_IN == null) {
						booking.put("checkInDate", rset.getString(6));
					} else {
						booking.put("checkInDate", CHECK_IN);
					}
					if (ORIGINAL_CHECK_OUT == null) {
						booking.put("checkOutDate", rset.getString(7));
					} else {
						booking.put("checkOutDate", CHECK_OUT);
					}
				}
				
				
				request.setAttribute("booking", booking);
				request.setAttribute("originalCheckIn", ORIGINAL_CHECK_IN);
				request.setAttribute("originalCheckOut", ORIGINAL_CHECK_OUT);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) { }
				
				try {
					rset.close();
				} catch (SQLException e) { }
				
				try {
					conn.close();
				} catch (SQLException e) {  }
			}
			
			request.getRequestDispatcher("/WEB-INF/app/book/update_booking.jsp").forward(request, response);
		}
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
			
			String guestId = request.getParameter("guest_id");
			String hotelId = request.getParameter("hotel_id");
			String roomNumber = request.getParameter("room_number");
			String originalCheckIn = request.getParameter("original_check_in");
			String originalCheckOut = request.getParameter("original_check_out");
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
							+ "WHERE NOT (CheckInDate = ? OR CheckOutDate = ?) "
							+ "AND ("
							+ "    (HotelID = ? AND RoomNumber = ?) OR "
							+ "    GuestID = ? "
							+ ")"
							+ "ORDER BY CheckInDate";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, originalCheckIn);
				pstmt2.setString(2, originalCheckOut);
				pstmt2.setString(3, hotelId);
				pstmt2.setString(4, roomNumber);
				pstmt2.setString(5, guestId);
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
					String sql3 = "UPDATE Book "
								+ "SET CheckInDate = ?, CheckOutDate = ? "
								+ "WHERE GuestID = ? "
								+ "AND HotelID = ? "
								+ "AND RoomNumber = ? "
								+ "AND CheckInDate = ? "
								+ "AND CheckOutDate = ?";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, checkIn.toString());
					pstmt3.setString(2, checkOut.toString());
					pstmt3.setString(3, guestId);
					pstmt3.setString(4, hotelId);
					pstmt3.setString(5, roomNumber);
					pstmt3.setString(6, originalCheckIn);
					pstmt3.setString(7, originalCheckOut);
					pstmt3.executeUpdate();
				}
				
				
				guestId = URLEncoder.encode(guestId, "UTF-8");
				hotelId = URLEncoder.encode(hotelId, "UTF-8");
				roomNumber = URLEncoder.encode(roomNumber, "UTF-8");
				originalCheckIn = URLEncoder.encode(originalCheckIn, "UTF-8");
				originalCheckOut = URLEncoder.encode(originalCheckOut, "UTF-8");

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
				response.sendRedirect(request.getContextPath() + "/confirm/update");
			} else if (!guestAuthentication) {
				response.sendRedirect(request.getContextPath() + "/top");
			} else {
				response.sendRedirect(request.getContextPath() + "/update/booking?guest_id=" + guestId + "&hotel_id=" + hotelId + "&room_number=" + roomNumber + "&check_in=" + checkIn + "&check_out=" + checkOut + "&original_check_in=" + originalCheckIn + "&original_check_out=" + originalCheckOut);
			}
		}
	}
}

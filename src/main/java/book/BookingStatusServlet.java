package book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Servlet implementation class BookingStatusServlet
 */
@WebServlet("/booking/status")
public class BookingStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingStatusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		final String COUNTRY = (String) session.getAttribute("country");
		final String NUMBER = (String) session.getAttribute("number");
		
		if (COUNTRY == null || NUMBER == null) {
			response.sendRedirect(request.getContextPath() + "/hotel_login");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			HotelDAO db = new HotelDAO();
			Connection conn = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			ResultSet rset1 = null;
			ResultSet rset2 = null;

			try {
				conn = db.getConnection();
				String sql1 = "SELECT HotelID "
							+ "FROM HotelPhoneNumber "
							+ "WHERE PhoneNumber = ? "
							+ "AND CountryCode = ("
							+ "  SELECT CountryCode"
							+ "  FROM HotelCountry"
							+ "  WHERE Country = ?"
							+ ")";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, NUMBER);
				pstmt1.setString(2, COUNTRY);
				rset1 = pstmt1.executeQuery();
				String hotelId = "";
				if (rset1.next()) {
					hotelId = rset1.getString(1);
				}
				
				
				String sql2 = "SELECT g.GuestID, g.Name, h.HotelID, r.RoomNumber, r.RoomType, b.CheckInDate, b.CheckOutDate, b.PaymentType, b.Paid "
							+ "FROM Book b, Guest g, Hotel h, Room r "
							+ "WHERE b.GuestID = g.GuestID "
							+ "AND b.HotelID = h.HotelID "
							+ "AND h.HotelID = r.HotelID "
							+ "AND b.RoomNumber = r.RoomNumber "
							+ "AND b.HotelID = ?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, hotelId);
				rset2 = pstmt2.executeQuery();
				ArrayList<Map<String, String>> bookings = new ArrayList<Map<String, String>>();
				
				while (rset2.next()) {
					Map<String, String> booking = new HashMap<>();
					booking.put("guestId", rset2.getString(1));
					booking.put("guestName", rset2.getString(2));
					booking.put("hotelId", rset2.getString(3));
					booking.put("roomNumber", rset2.getString(4));
					booking.put("roomType", rset2.getString(5));
					booking.put("checkInDate", rset2.getString(6));
					booking.put("checkOutDate", rset2.getString(7));
					booking.put("paymentType", rset2.getString(8));
					booking.put("paid", rset2.getString(9));
					bookings.add(booking);
				}
				
				request.setAttribute("bookings", bookings);

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
					rset1.close();
				} catch (SQLException e) { }
				
				try {
					rset2.close();
				} catch (SQLException e) { }
				
				try {
					conn.close();
				} catch (SQLException e) {  }
			}
			
			request.getRequestDispatcher("/WEB-INF/app/book/booking_status.jsp").forward(request, response);
		}
	}
}
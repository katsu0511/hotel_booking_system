package book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
 * Servlet implementation class ShowBookingServlet
 */
@WebServlet("/show/booking")
public class ShowBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowBookingServlet() {
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
			final String HOTEL_ID = request.getParameter("hotelId");
			final String ROOM_NUMBER = request.getParameter("roomNumber");
			final String CHECK_IN_DATE = request.getParameter("checkInDate");
			final String CHECK_OUT_DATE = request.getParameter("checkOutDate");
			HotelDAO db = new HotelDAO();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;

			try {
				conn = db.getConnection();
				String sql = "SELECT g.GuestID, h.HotelId, h.Name, h.Street, hpc.City, hpc.Province, hc.Country, hpc.PostalCode, hpn.CountryCode, hpn.PhoneNumber, b.RoomNumber, r.RoomType, rc.Cost, cat.Tax, b.CheckInDate, b.CheckOutDate "
						   + "FROM Guest g, Hotel h, HotelCountry hc, HotelPostalCode hpc, HotelPhoneNumber hpn, Room r, RoomCost rc, CostAndTax cat, Book b "
						   + "WHERE g.GuestID = b.GuestID "
						   + "AND b.HotelID = h.HotelID "
						   + "AND b.RoomNumber = r.RoomNumber "
						   + "AND h.Country = hc.Country "
						   + "AND h.PostalCode = hpc.PostalCode "
						   + "AND h.HotelID = hpn.HotelID "
						   + "AND (r.HotelID = rc.HotelID AND r.RoomType = rc.RoomType) "
						   + "AND rc.Cost = cat.Cost "
						   + "AND g.Email = ? "
						   + "AND g.Password = ? "
						   + "AND b.HotelID = ? "
						   + "AND b.RoomNumber = ? "
						   + "AND b.CheckInDate = ? "
						   + "AND b.CheckOutDate = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, EMAIL);
				pstmt.setString(2, PASSWORD);
				pstmt.setString(3, HOTEL_ID);
				pstmt.setString(4, ROOM_NUMBER);
				pstmt.setString(5, CHECK_IN_DATE);
				pstmt.setString(6, CHECK_OUT_DATE);
				rset = pstmt.executeQuery();
				Map<String, String> booking = new HashMap<>();
				if (rset.next()) {
					booking.put("guestId", rset.getString(1));
					booking.put("hotelId", rset.getString(2));
					booking.put("hotel", rset.getString(3));
					booking.put("street", rset.getString(4));
					booking.put("city", rset.getString(5));
					booking.put("province", rset.getString(6));
					booking.put("country", rset.getString(7));
					booking.put("postalCode", rset.getString(8));
					booking.put("countryCode", rset.getString(9));
					booking.put("phoneNumber", rset.getString(10));
					booking.put("roomNumber", rset.getString(11));
					booking.put("roomType", rset.getString(12));
					booking.put("cost", rset.getString(13));
					booking.put("tax", rset.getString(14));
					booking.put("checkInDate", rset.getString(15));
					booking.put("checkOutDate", rset.getString(16));
				}
				
				LocalDate checkIn = LocalDate.parse(CHECK_IN_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				LocalDate checkOut = LocalDate.parse(CHECK_OUT_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				long numberOfNights = ChronoUnit.DAYS.between(checkIn, checkOut);
				
				
				request.setAttribute("booking", booking);
				request.setAttribute("numberOfNights", numberOfNights);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					rset.close();
					conn.close();
				} catch (SQLException e) { }
			}
			
			request.getRequestDispatcher("/WEB-INF/app/book/show_booking.jsp").forward(request, response);
		}
	}
}

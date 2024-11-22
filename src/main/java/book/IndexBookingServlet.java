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
 * Servlet implementation class IndexBookingServlet
 */
@WebServlet("/index/booking")
public class IndexBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexBookingServlet() {
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

			try {
				conn = db.getConnection();
				String sql = "SELECT h.HotelId, h.Name as Hotel, b.RoomNumber, b.CheckInDate, b.CheckOutDate "
						   + "FROM Guest g, Book b, Hotel h "
						   + "Where g.GuestID = b.GuestID AND b.HotelID = h.HotelID AND g.Email = ? AND g.Password = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, EMAIL);
				pstmt.setString(2, PASSWORD);
				rset = pstmt.executeQuery();
				ArrayList<Map<String, String>> bookings = new ArrayList<Map<String, String>>();
				
				while (rset.next()) {
					Map<String, String> booking = new HashMap<>();
					booking.put("hotelId", rset.getString(1));
					booking.put("hotel", rset.getString(2));
					booking.put("roomNumber", rset.getString(3));
					booking.put("checkInDate", rset.getString(4));
					booking.put("checkOutDate", rset.getString(5));
					bookings.add(booking);
				}
				
				request.setAttribute("bookings", bookings);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) { }
				
				try {
					if (rset != null) {
						rset.close();
					}
				} catch (SQLException e) { }
				
				try {
					conn.close();
				} catch (SQLException e) {  }
			}
			
			request.getRequestDispatcher("/WEB-INF/app/book/index_booking.jsp").forward(request, response);
		}
	}
}

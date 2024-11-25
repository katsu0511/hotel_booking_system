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
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			ResultSet rset1 = null;
			ResultSet rset2 = null;

			try {
				conn = db.getConnection();
				String sql1 = "SELECT b.HotelId, b.RoomNumber, b.CheckInDate, b.CheckOutDate "
							+ "FROM Guest g "
							+ "INNER JOIN Book b "
							+ "ON g.GuestID = b.GuestID "
							+ "AND g.Email = ? "
							+ "AND g.Password = ?";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, EMAIL);
				pstmt1.setString(2, PASSWORD);
				rset1 = pstmt1.executeQuery();
				ArrayList<Map<String, String>> bookings = new ArrayList<Map<String, String>>();
				
				String sql2 = "SELECT Name "
							+ "FROM Hotel "
							+ "WHERE HotelId = ?";
				pstmt2 = conn.prepareStatement(sql2);
				
				while (rset1.next()) {
					Map<String, String> booking = new HashMap<>();
					booking.put("hotelId", rset1.getString(1));
					pstmt2.setString(1, booking.get("hotelId"));
					rset2 = pstmt2.executeQuery();
					if (rset2.next()) {
						booking.put("hotel", rset2.getString(1));
					}
					booking.put("roomNumber", rset1.getString(2));
					booking.put("checkInDate", rset1.getString(3));
					booking.put("checkOutDate", rset1.getString(4));
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
			
			request.getRequestDispatcher("/WEB-INF/app/book/index_booking.jsp").forward(request, response);
		}
	}
}

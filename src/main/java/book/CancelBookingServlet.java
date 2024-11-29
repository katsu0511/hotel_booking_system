package book;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.HotelDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CancelBookingServlet
 */
@WebServlet("/cancel/booking")
public class CancelBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelBookingServlet() {
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
			PreparedStatement pstmt = null;
			
			String guestId = request.getParameter("guest_id");
			String hotelId = request.getParameter("hotel_id");
			String roomNumber = request.getParameter("room_number");
			String checkIn = request.getParameter("check_in");
			String checkOut = request.getParameter("check_out");
			
			try {
				conn = db.getConnection();
				String sql = "DELETE FROM Book "
						   + "WHERE GuestID = ? "
						   + "AND HotelID = ? "
						   + "AND RoomNumber = ? "
						   + "AND CheckInDate = ? "
						   + "AND CheckOutDate = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, guestId);
				pstmt.setString(2, hotelId);
				pstmt.setString(3, roomNumber);
				pstmt.setString(4, checkIn);
				pstmt.setString(5, checkOut);
				pstmt.executeUpdate();
				
				
				guestId = URLEncoder.encode(guestId, "UTF-8");
				hotelId = URLEncoder.encode(hotelId, "UTF-8");
				roomNumber = URLEncoder.encode(roomNumber, "UTF-8");
				checkIn = URLEncoder.encode(checkIn, "UTF-8");
				checkOut = URLEncoder.encode(checkOut, "UTF-8");
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) { }
				
				try {
					conn.close();
				} catch (SQLException e) {  }
			}
			
			response.sendRedirect(request.getContextPath() + "/index/booking");
		}
	}
}

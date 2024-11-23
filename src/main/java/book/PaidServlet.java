package book;

import java.io.IOException;
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
 * Servlet implementation class PaidServlet
 */
@WebServlet("/paid")
public class PaidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			PreparedStatement pstmt = null;
			
			String guestId = request.getParameter("guest_id");
			String hotelId = request.getParameter("hotel_id");
			String roomNumber = request.getParameter("room_number");
			String checkInDate = request.getParameter("check_in_date");
			String checkOutDate = request.getParameter("check_out_date");
			
			try {
				conn = db.getConnection();
				String sql = "UPDATE Book "
						   + "SET Paid = 1 "
						   + "WHERE GuestID = ? "
						   + "AND HotelID = ? "
						   + "AND RoomNumber = ? "
						   + "AND CheckInDate = ? "
						   + "AND CheckOutDate = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, guestId);
				pstmt.setString(2, hotelId);
				pstmt.setString(3, roomNumber);
				pstmt.setString(4, checkInDate);
				pstmt.setString(5, checkOutDate);
				pstmt.executeUpdate();

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
			
			response.sendRedirect(request.getContextPath() + "/booking/status");
		}
	}
}

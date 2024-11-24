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
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			
			String guestId = request.getParameter("guest_id");
			String hotelId = request.getParameter("hotel_id");
			String roomNumber = request.getParameter("room_number");
			String checkInDate = request.getParameter("check_in_date");
			String checkOutDate = request.getParameter("check_out_date");
			String staffId = request.getParameter("staff_id");
			String paymentType = request.getParameter("payment_type");
			
			try {
				conn = db.getConnection();
				String sql1 = "UPDATE Book "
							+ "SET Paid = 1, PaymentType = ? "
							+ "WHERE GuestID = ? "
							+ "AND HotelID = ? "
							+ "AND RoomNumber = ? "
							+ "AND CheckInDate = ? "
							+ "AND CheckOutDate = ?";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, paymentType);
				pstmt1.setString(2, guestId);
				pstmt1.setString(3, hotelId);
				pstmt1.setString(4, roomNumber);
				pstmt1.setString(5, checkInDate);
				pstmt1.setString(6, checkOutDate);
				pstmt1.executeUpdate();
				
				
				String sql2 = "INSERT INTO Handles (StaffID, GuestID, HotelID, RoomNumber, CheckInDate, CheckOutDate) "
							+ "VALUES (?, ?, ?, ?, ?, ?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, staffId);
				pstmt2.setString(2, guestId);
				pstmt2.setString(3, hotelId);
				pstmt2.setString(4, roomNumber);
				pstmt2.setString(5, checkInDate);
				pstmt2.setString(6, checkOutDate);
				pstmt2.executeUpdate();

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
					conn.close();
				} catch (SQLException e) {  }
			}
			
			response.sendRedirect(request.getContextPath() + "/booking/status");
		}
	}
}

package hotel;

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
 * Servlet implementation class IndexHotelServlet
 */
@WebServlet("/index/hotel")
public class IndexHotelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexHotelServlet() {
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
				String sql1 = "SELECT HotelID, Name "
							+ "FROM Hotel";
				pstmt1 = conn.prepareStatement(sql1);
				rset1 = pstmt1.executeQuery();
				ArrayList<Map<String, String>> hotels = new ArrayList<Map<String, String>>();
				
				while (rset1.next()) {
					Map<String, String> hotel = new HashMap<>();
					hotel.put("id", rset1.getString(1));
					hotel.put("name", rset1.getString(2));
					hotels.add(hotel);
				}
				
				
				String sql2 = "SELECT r.HotelID, MIN(ct.Cost + ct.Tax), MAX(ct.Cost + ct.Tax) "
							+ "FROM Room r, RoomCost c, CostAndTax ct "
							+ "WHERE r.HotelID = c.HotelID "
							+ "AND r.RoomType = c.RoomType "
							+ "AND c.Cost = ct.Cost "
							+ "GROUP BY r.HotelID "
							+ "ORDER BY r.HotelID";
				pstmt2 = conn.prepareStatement(sql2);
				rset2 = pstmt2.executeQuery();
				ArrayList<Map<String, String>> costs = new ArrayList<Map<String, String>>();
				
				while (rset2.next()) {
					Map<String, String> cost = new HashMap<>();
					cost.put("id", rset2.getString(1));
					cost.put("min", rset2.getString(2));
					cost.put("max", rset2.getString(3));
					costs.add(cost);
				}
				
				
				request.setAttribute("hotels", hotels);
				request.setAttribute("costs", costs);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt1.close();
					pstmt2.close();
				} catch (SQLException e) { }
				
				try {
					rset1.close();
					rset2.close();
				} catch (SQLException e) { }
				
				try {
					conn.close();
				} catch (SQLException e) {  }
			}
			
			request.getRequestDispatcher("/WEB-INF/app/hotel/index_hotel.jsp").forward(request, response);
		}
	}
}

package room;

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
 * Servlet implementation class IndexRoomServlet
 */
@WebServlet("/index/room")
public class IndexRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexRoomServlet() {
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
			final String HOTEL_ID = request.getParameter("id");
			HotelDAO db = new HotelDAO();
			Connection conn = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			ResultSet rset1 = null;
			ResultSet rset2 = null;

			try {
				conn = db.getConnection();
				String sql1 = "SELECT h.HotelID, h.Name, c.CountryCode, n.PhoneNumber, h.Street, p.City, p.Province, c.Country, p.PostalCode "
							+ "FROM Hotel h, HotelCountry c, HotelPostalCode p, HotelPhoneNumber n "
							+ "WHERE h.PhoneNumber = n.PhoneNumber AND h.PostalCode = p.PostalCode AND n.CountryCode = c.CountryCode "
							+ "AND h.HotelID = ?";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, HOTEL_ID);
				rset1 = pstmt1.executeQuery();
				Map<String, String> hotel = new HashMap<>();
				if (rset1.next()) {
					hotel.put("id", rset1.getString(1));
					hotel.put("name", rset1.getString(2));
					hotel.put("countryCode", rset1.getString(3));
					hotel.put("phoneNumber", rset1.getString(4));
					hotel.put("street", rset1.getString(5));
					hotel.put("city", rset1.getString(6));
					hotel.put("province", rset1.getString(7));
					hotel.put("country", rset1.getString(8));
					hotel.put("postalCode", rset1.getString(9));
				}
				
				
				String sql2 = "SELECT r.RoomNumber, r.RoomType, c.Cost, t.Tax "
							+ "FROM Hotel h, Room r, RoomCost c, CostAndTax t "
							+ "WHERE h.HotelID = r.HotelID AND (h.HotelID = c.HotelID AND r.RoomType = c.RoomType) AND c.Cost = t.Cost AND h.HotelID = ? "
							+ "ORDER BY RoomNumber";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, HOTEL_ID);
				rset2 = pstmt2.executeQuery();
				ArrayList<Map<String, String>> rooms = new ArrayList<Map<String, String>>();
				while (rset2.next()) {
					Map<String, String> room = new HashMap<>();
					room.put("number", rset2.getString(1));
					room.put("type", rset2.getString(2));
					room.put("cost", rset2.getString(3));
					room.put("tax", rset2.getString(4));
					rooms.add(room);
				}
				
				
				request.setAttribute("hotel", hotel);
				request.setAttribute("rooms", rooms);

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
			
			request.getRequestDispatcher("/WEB-INF/app/room/index_room.jsp").forward(request, response);
		}
	}
}

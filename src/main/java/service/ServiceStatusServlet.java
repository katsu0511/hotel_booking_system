package service;

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
 * Servlet implementation class ServiceStatusServlet
 */
@WebServlet("/service/status")
public class ServiceStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceStatusServlet() {
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
			PreparedStatement pstmt3 = null;
			ResultSet rset1 = null;
			ResultSet rset2 = null;
			ResultSet rset3 = null;

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
				
				
				String sql2 = "SELECT g.Name, s.Type, u.Date, u.Time "
							+ "FROM Hotel h, Offer o, Service s, Uses u, Guest g "
							+ "WHERE h.HotelID = o.HotelID "
							+ "AND o.ServiceID = s.ServiceID "
							+ "AND u.HotelID = h.HotelID "
							+ "AND u.ServiceID = s.ServiceID "
							+ "AND u.GuestID = g.GuestID "
							+ "AND h.HotelID = ?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, hotelId);
				rset2 = pstmt2.executeQuery();
				ArrayList<Map<String, String>> uses = new ArrayList<Map<String, String>>();
				
				while (rset2.next()) {
					Map<String, String> use = new HashMap<>();
					use.put("guestName", rset2.getString(1));
					use.put("serviceType", rset2.getString(2));
					use.put("useDate", rset2.getString(3));
					use.put("useTime", rset2.getString(4));
					uses.add(use);
				}
				
				
				String sql3 = "SELECT g.Name "
							+ "FROM Guest g "
							+ "JOIN Uses u "
							+ "ON g.GuestID = u.GuestID "
							+ "AND u.HotelID = ? "
							+ "GROUP BY g.GuestID, g.Name "
							+ "HAVING COUNT(DISTINCT u.ServiceID) = ("
							+ "    SELECT COUNT(*)"
							+ "    FROM Offer"
							+ "    WHERE HotelID = ?"
							+ ")";
				pstmt3 = conn.prepareStatement(sql3);
				pstmt3.setString(1, hotelId);
				pstmt3.setString(2, hotelId);
				rset3 = pstmt3.executeQuery();
				ArrayList<String> vips = new ArrayList<String>();
				
				while (rset3.next()) {
					vips.add(rset3.getString(1));
				}
				
				
				request.setAttribute("uses", uses);
				request.setAttribute("vips", vips);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt1.close();
					pstmt2.close();
					pstmt3.close();
				} catch (SQLException e) { }
				
				try {
					rset1.close();
					rset2.close();
					rset3.close();
				} catch (SQLException e) { }
				
				try {
					conn.close();
				} catch (SQLException e) {  }
			}
			
			request.getRequestDispatcher("/WEB-INF/app/service/service_status.jsp").forward(request, response);
		}
	}
}

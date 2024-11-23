package home;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.HotelDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class HotelTopServlet
 */
@WebServlet("/hotel_top")
public class HotelTopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelTopServlet() {
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
		final String EMAIL = (String) session.getAttribute("email");
		final String PASSWORD = (String) session.getAttribute("password");
		
		if (COUNTRY != null && NUMBER != null) {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			HotelDAO db = new HotelDAO();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;

			try {
				conn = db.getConnection();
				String sql = "SELECT Name "
						   + "FROM Hotel "
						   + "WHERE HotelID = ("
						   + "  SELECT HotelID "
						   + "  FROM HotelPhoneNumber "
						   + "  WHERE PhoneNumber = ? "
						   + "  AND CountryCode = ("
						   + "    SELECT CountryCode "
						   + "    FROM HotelCountry "
						   + "    WHERE Country = ?"
						   + "  )"
						   + ")";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, NUMBER);
				pstmt.setString(2, COUNTRY);
				rset = pstmt.executeQuery();
				String hotel = "";
				if (rset.next()) {
					hotel = rset.getString(1);
				}
				
				request.setAttribute("hotel", hotel);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
				} catch (SQLException e) { }
				
				try {
					rset.close();
				} catch (SQLException e) { }
				
				try {
					conn.close();
				} catch (SQLException e) {  }
			}
			
			request.getRequestDispatcher("/WEB-INF/app/home/hotel_top.jsp").forward(request, response);

		} else if (EMAIL != null && PASSWORD != null) {
			response.sendRedirect(request.getContextPath() + "/top");
		} else {
			response.sendRedirect(request.getContextPath() + "/hotel_login");
		}
	}
}

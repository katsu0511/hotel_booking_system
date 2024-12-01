package home;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBManager;
import db.HotelDAO;
import db.HotelDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class HotelLoginServlet
 */
@WebServlet("/hotel_login")
public class HotelLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelLoginServlet() {
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
			response.sendRedirect(request.getContextPath() + "/hotel_top");
		} else if (EMAIL != null && PASSWORD != null) {
			response.sendRedirect(request.getContextPath() + "/top");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			HotelDAO db = new HotelDAO();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;

			try {
				conn = db.getConnection();
				String sql = "SELECT Country "
						   + "FROM HotelCountry";
				pstmt = conn.prepareStatement(sql);
				rset = pstmt.executeQuery();
				ArrayList<String> countries = new ArrayList<String>();
				
				while (rset.next()) {
					countries.add(rset.getString(1));
				}
				
				request.setAttribute("countries", countries);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					rset.close();
					conn.close();
				} catch (SQLException e) { }
			}
			
			request.getRequestDispatcher("/WEB-INF/app/home/hotel_login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String country = request.getParameter("country");
		String number = request.getParameter("number");
		String errorMessage = null;
		DBManager dbm = new DBManager();
		HotelDTO hotel = dbm.getLoginHotel(country, number);
		
		if (country.equals("") || number.equals("")) {
			errorMessage = "Input country and phone number";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/WEB-INF/app/home/hotel_login.jsp").forward(request, response);
		} else if (hotel != null) {
			HttpSession session = request.getSession();
			session.setAttribute("country", country);
			session.setAttribute("number", number);
			country = URLEncoder.encode(country, "UTF-8");
			number = URLEncoder.encode(number, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/hotel_top");
		} else {
			errorMessage = "Country or phone number is wrong";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/WEB-INF/app/home/hotel_login.jsp").forward(request, response);
		}
	}
}

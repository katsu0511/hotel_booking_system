package staff;

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
 * Servlet implementation class IndexStaffServlet
 */
@WebServlet("/index/staff")
public class IndexStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexStaffServlet() {
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
			PreparedStatement pstmt4 = null;
			ResultSet rset1 = null;
			ResultSet rset2 = null;
			ResultSet rset3 = null;
			ResultSet rset4 = null;

			try {
				conn = db.getConnection();
				String sql1 = "SELECT HotelID, Name "
							+ "FROM Hotel "
							+ "WHERE HotelID = ("
							+ "  SELECT HotelID "
							+ "  FROM HotelPhoneNumber "
							+ "  WHERE PhoneNumber = ? "
							+ "  AND CountryCode = ("
							+ "    SELECT CountryCode"
							+ "    FROM HotelCountry"
							+ "    WHERE Country = ?"
							+ "  )"
							+ ")";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1, NUMBER);
				pstmt1.setString(2, COUNTRY);
				rset1 = pstmt1.executeQuery();
				String hotelId = "";
				String hotelName = "";
				if (rset1.next()) {
					hotelId = rset1.getString(1);
					hotelName = rset1.getString(2);
				}
			
			
				String sql2 = "SELECT Name, BirthDate, Role "
							+ "FROM Staff "
							+ "WHERE HotelID = ?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, hotelId);
				rset2 = pstmt2.executeQuery();
				ArrayList<Map<String, String>> staffs = new ArrayList<Map<String, String>>();
				
				while (rset2.next()) {
					Map<String, String> staff = new HashMap<>();
					staff.put("name", rset2.getString(1));
					staff.put("birthDate", rset2.getString(2));
					staff.put("role", rset2.getString(3));
					staffs.add(staff);
				}
				
				
				String sql3 = "SELECT t.Trainer_StaffID, t.Trainee_StaffID "
							+ "FROM Training t "
							+ "WHERE t.Trainer_HotelID = ? "
							+ "AND t.Trainee_HotelID = ?";
				pstmt3 = conn.prepareStatement(sql3);
				pstmt3.setString(1, hotelId);
				pstmt3.setString(2, hotelId);
				rset3 = pstmt3.executeQuery();
				ArrayList<Map<String, String>> trainer_ids = new ArrayList<Map<String, String>>();
				
				while (rset3.next()) {
					Map<String, String> trainer_id = new HashMap<>();
					trainer_id.put("trainer", rset3.getString(1));
					trainer_id.put("trainee", rset3.getString(2));
					trainer_ids.add(trainer_id);
				}
				
				
				String sql4 = "SELECT Name "
							+ "FROM Staff "
							+ "WHERE StaffID = ? "
							+ "AND HotelID = ?";
				pstmt4 = conn.prepareStatement(sql4);
				ArrayList<Map<String, String>> trainings = new ArrayList<Map<String, String>>();
				for (Map<String, String> trainer_id : trainer_ids) {
					Map<String, String> training = new HashMap<>();
					pstmt4.setString(1, trainer_id.get("trainer"));
					pstmt4.setString(2, hotelId);
					rset4 = pstmt4.executeQuery();
					if (rset4.next()) {
						training.put("trainer", rset4.getString(1));
					}
					pstmt4.setString(1, trainer_id.get("trainee"));
					pstmt4.setString(2, hotelId);
					rset4 = pstmt4.executeQuery();
					if (rset4.next()) {
						training.put("trainee", rset4.getString(1));
					}
					trainings.add(training);
				}
				
				
				request.setAttribute("hotelName", hotelName);
				request.setAttribute("staffs", staffs);
				request.setAttribute("trainings", trainings);

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
			
			request.getRequestDispatcher("/WEB-INF/app/staff/index_staff.jsp").forward(request, response);
		}
	}
}

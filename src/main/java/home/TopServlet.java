package home;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class TopServlet
 */
@WebServlet("/top")
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopServlet() {
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
		final String COUNTRY = (String) session.getAttribute("country");
		final String NUMBER = (String) session.getAttribute("number");
		
		if (EMAIL != null && PASSWORD != null) {
			request.getRequestDispatcher("/WEB-INF/app/home/top.jsp").forward(request, response);
		} else if (COUNTRY != null && NUMBER != null) {
			response.sendRedirect(request.getContextPath() + "/hotel_top");
		} else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}
}

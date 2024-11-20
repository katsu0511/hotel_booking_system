package home;

import java.io.IOException;
import java.net.URLEncoder;

import db.DBManager;
import db.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		if (session.getAttribute("email") == null || session.getAttribute("password") == null) {
			request.getRequestDispatcher("/WEB-INF/app/home/login.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/top");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String errorMessage = null;
		DBManager dbm = new DBManager();
		UserDTO user = dbm.getLoginUser(email, password);
		
		if (email.equals("") || password.equals("")) {
			errorMessage = "Input your e-mail and password";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/WEB-INF/app/home/login.jsp").forward(request, response);
		} else if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			session.setAttribute("password", password);
			email = URLEncoder.encode(email, "UTF-8");
			password = URLEncoder.encode(password, "UTF-8");
			response.sendRedirect(request.getContextPath() + "/top");
		} else {
			errorMessage = "Email or password is wrong";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/WEB-INF/app/home/login.jsp").forward(request, response);
		}
	}
}

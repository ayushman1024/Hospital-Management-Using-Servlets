

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		PrintWriter out = response.getWriter();
		String email =  request.getParameter("email");
		String pwd =  request.getParameter("pwd");
		Connection c = GetConnection.getConnection();	
		String checkUserSql = "select * from assistant where email = ?";

		PreparedStatement ps;
		try {
			ps = c.prepareStatement(checkUserSql);
			ps.setString(1,email);
			ps.addBatch();
			ResultSet rs = ps.executeQuery();
			if(rs.next() && rs.getString("password").equals(pwd)){
				HttpSession session = request.getSession();
				session.setAttribute("name", rs.getString("name"));
				session.setAttribute("aid", rs.getInt("aid"));
				response.sendRedirect("welcome.html");
			}
			else{
				  
				response.setContentType("text/html");  
				out.println("<br><br><br><h1 align=center><font color=\"red\">TRY AGAIN<br>REDIRECTING YOU TO LOGING PAGE</font></h1><script type=\"text/javascript\">");  
				out.println("redirectURL = \"login.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
				out.println("</script>");
			}
			ps.clearBatch();
		} catch (SQLException e) {
			response.setContentType("text/html");  
			out.println("<br><br><br><h1 align=center><font color=\"red\">TRY AGAIN<br>REDIRECTING YOU TO LOGING PAGE</font></h1><script type=\"text/javascript\">");  
			out.println("redirectURL = \"login.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
			out.println("</script>");
			e.printStackTrace();
		}
	}

}

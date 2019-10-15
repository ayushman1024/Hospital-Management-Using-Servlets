

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateAssistant
 */
@WebServlet("/CreateAssistant")
public class CreateAssistant extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAssistant() {
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
		
		String name = request.getParameter("name");
		String email =  request.getParameter("email");
		String phone = request.getParameter("phone");
		String pwd =  request.getParameter("pwd");
		String joindate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		PrintWriter out = response.getWriter();
		Connection c;
		try {
		c = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");	
		String sql = "insert into assistant(name,email,phone,joindate,password) values(?,?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1,name);
		ps.setString(2,email);
		ps.setString(3,phone);
		ps.setString(4,joindate);
		ps.setString(5,pwd);
		ps.addBatch();

		// Executing SQL
		int successCount = 0;
		successCount += ps.executeBatch()[0];
		ps.clearBatch();
		
		if(successCount == 1) {
			response.sendRedirect("login.html");
		}
		else {
			response.setContentType("text/html");  
			out.println("<br><br><br><h1 align=center><font color=\"red\">TRY AGAIN<br>REDIRECTING BACK REGISTERATION PAGE</font></h1><script type=\"text/javascript\">");  
			out.println("redirectURL = \"newAssistant.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
			out.println("</script>");
		}
		} catch (SQLException e) { 
			e.printStackTrace();
			response.setContentType("text/html");  
			out.println("<br><br><br><h1 align=center><font color=\"red\">TRY AGAIN<br>REDIRECTING BACK REGISTERATION PAGE</font></h1><script type=\"text/javascript\">");  
			out.println("redirectURL = \"newAssistant.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
			out.println("</script>");
		}
	}

}

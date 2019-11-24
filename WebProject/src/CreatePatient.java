import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreatePatient
 */
@WebServlet("/CreatePatient")
public class CreatePatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePatient() {
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
		Connection c = GetConnection.getConnection();
		int successCount = 0;
		String  name = request.getParameter("name");
		String  email = request.getParameter("email");
		String  phone = request.getParameter("phone");
		String  age = request.getParameter("age");
		String  gender = request.getParameter("gender");
		String  blood = request.getParameter("blood");
		String visited = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String  symptom = request.getParameter("symptom");
		String  disease = request.getParameter("disease");
		String doctor = request.getParameter("doctor");
		try {
			String sql = "insert into patient(name,email,phone,age,gender,blood,visited,symptom,disease,doctor) values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, phone);
			ps.setInt(4, Integer.valueOf(age));
			ps.setString(5, gender);
			ps.setString(6, blood);
			ps.setString(7, visited);
			ps.setString(8,symptom);
			ps.setString(9, disease);
			ps.setString(10, doctor);
			ps.addBatch();
			ps.executeBatch();
			ps.clearBatch();
			}
			catch (SQLException e) { 
				e.printStackTrace();
			}
		try {
				String sql1 = "select patients from doctor where did = "+doctor;
				Statement s = c.createStatement();
				ResultSet r = s.executeQuery(sql1);
				r.next();
				String patients = r.getString("patients");
				
				PreparedStatement s2 = c.prepareStatement("select pid from patient where email = ?");
				s2.setString(1, email);
				ResultSet r2 = s2.executeQuery();
				r2.next();
				String pid = r2.getString("pid");
				String newPat = new StringBuilder().append(patients).append(",").append(pid).toString();
				
				
				PreparedStatement s3 = c.prepareStatement("update doctor set patients = ? where did = ?");
				s3.setString(1, newPat);
				s3.setInt(2, Integer.valueOf(doctor));
				s3.executeUpdate();
				response.setContentType("text/html");  
				out.println("<br><br><br><h1 align=center><font color=\"green\">SUCCESSFUL<br></font></h1>");
			} catch (SQLException e) { 
				e.printStackTrace();
				response.setContentType("text/html");  
				out.println("<br><br><br><h1 align=center><font color=\"red\">TRY AGAIN<br></font></h1><script type=\"text/javascript\">");  
				out.println("redirectURL = \"welcome.html\";setTimeout(\"location.href = redirectURL;\",\"5000\");");  
				out.println("</script>");
		}
	}
		
}


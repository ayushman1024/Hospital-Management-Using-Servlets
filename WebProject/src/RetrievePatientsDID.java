

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RetrievePatientsDID
 */
@WebServlet("/RetrievePatientsDID")
public class RetrievePatientsDID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrievePatientsDID() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		Connection c = GetConnection.getConnection();
		String did = request.getParameter("did");
		System.out.println(did);
		String sql = "select patients,name from doctor where did = "+did;
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery(sql);
			r.next();
			String[] pList = r.getString("patients").split(",");
			String name = r.getString("name");
			ResultSet rrr = s.executeQuery("select * from patient");
			ResultSetMetaData rms = rrr.getMetaData();
			response.setContentType("text/html");  
			out.println(" <style> table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;} td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;} </style>");
			out.println("<h2>List of all the Patients working under: "+name+"</h2>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>"+rms.getColumnName(1)+"</th>");
			out.println("<th>"+rms.getColumnName(2)+"</th>");
			out.println("<th>"+rms.getColumnName(3)+"</th>");
			out.println("<th>"+rms.getColumnName(4)+"</th>");
			out.println("<th>"+rms.getColumnName(5)+"</th>");
			out.println("<th>"+rms.getColumnName(6)+"</th>");
			out.println("<th>"+rms.getColumnName(7)+"</th>");
			out.println("<th>"+rms.getColumnName(8)+"</th>");
			out.println("<th>"+rms.getColumnName(9)+"</th>");
			out.println("<th>"+rms.getColumnName(10)+"</th>");
			out.println("<th>"+rms.getColumnName(11)+"</th>");
			out.println("</tr>");
			for(String p: pList) {
				if(Integer.valueOf(p) >= 0){
					Statement ss = c.createStatement();
					ResultSet rr = ss.executeQuery("select * from patient where pid = "+p);
					rr.next();
					out.println("<tr>");
					out.println("<td>"+rr.getString(1)+"</td>");out.println("<td>"+rr.getString(2)+"</td>");out.println("<td>"+rr.getString(3)+"</td>");out.println("<td>"+rr.getString(4)+"</td>");
					out.println("<td>"+rr.getString(5)+"</td>");out.println("<td>"+rr.getString(6)+"</td>");out.println("<td>"+rr.getString(7)+"</td>");out.println("<td>"+rr.getString(8)+"</td>");
					out.println("<td>"+rr.getString(9)+"</td>");out.println("<td>"+rr.getString(10)+"</td>");out.println("<td>"+rr.getString(11)+"</td>");
					out.println("</tr>");
				}
			}
			out.println("</table>");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}

}

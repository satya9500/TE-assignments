

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		String roll_no, name, branch, year, contact, email, password;
		
		roll_no = request.getParameter("roll_no");
		name = request.getParameter("name");
		branch = request.getParameter("branch");
		year = request.getParameter("year");
		contact = request.getParameter("contact");
		email = request.getParameter("email");
		password = request.getParameter("password");
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/register?autoReconnect=true&useSSL=false", "root", "Mysql@24");
			
			Statement stm = con.createStatement();
			
			String query = "insert into student values(" + roll_no + ", '" + name + "', '" + branch + "', '" + year + "', " + contact + ", '" + email + "', " + password + ");";
			
			int i = stm.executeUpdate(query);
			
			/*PreparedStatement ps = con.prepareStatement("insert into student values (?,?,?,?,?,?,?)");
			ps.setString(1,roll_no);
			ps.setString(2, name);
			ps.setString(3, branch);
			ps.setString(4, year);
			ps.setString(5, contact);
			ps.setString(6, email);
			ps.setString(7, password);
			
			int i = ps.executeUpdate();*/
			
			if(i > 0) {
				out.println("Student is registered successfully");
				out.print("<a href='login.jsp'>Click here for login</a>");
			}
			else {
				out.println("Error!");
				System.out.println("Error inserting");
			}
			
		}catch(Exception ee){
			ee.printStackTrace();
			System.out.println("Error connecting");
		}
	}

}

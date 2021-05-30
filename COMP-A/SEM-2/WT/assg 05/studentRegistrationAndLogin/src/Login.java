

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import java.sql.*;  
/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
		
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		String user, password;
		
		user = request.getParameter("user");
		password = request.getParameter("password");
		
		boolean flag = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/register?autoReconnect=true&useSSL=false", "root", "Mysql@24");
			
			Statement stm = con.createStatement();
			
			String query = "select * from student where email = '"+ user + "' and password = " + password + ";";
			
			System.out.println(query);
			
			ResultSet rs = stm.executeQuery(query);
			
			flag = rs.next();
			
			if(flag == true) {
				HttpSession session = request.getSession();
				
				session.setAttribute("user", user);
				
				RequestDispatcher rds = request.getRequestDispatcher("home.jsp");
				
				rds.forward(request, response);
			}
			else {
				out.print("Username or Password is wrong!");
				RequestDispatcher rds = request.getRequestDispatcher("login.jsp");
				rds.include(request, response);
			}
			
		}catch(Exception ee){
			ee.printStackTrace();
		}
	}

}

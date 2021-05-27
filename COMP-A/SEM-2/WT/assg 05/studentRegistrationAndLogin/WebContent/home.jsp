<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*" %>
<% Class.forName("com.mysql.jdbc.Driver"); %>   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>

<style type="text/css">
	button{
		margin-top: 30px;
	}
</style>

</head>
<body>
<%
if((session.getAttribute("user")==null)){
	response.sendRedirect("login.jsp");
}
String user = request.getParameter("user");
String password = request.getParameter("password");
%>
<h1>Welcome!!! <%= user %></h1>
<hr>
<%
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/register?autoReconnect=true&useSSL=false","root","Mysql@24");
PreparedStatement ps = con.prepareStatement("select * from student where email=? and password=?");
ps.setString(1,user);
ps.setString(2,password);
ResultSet rs = ps.executeQuery();
%>

<table border="1">
<tr>
<th>Roll_No</th>
<th>Name</th>
<th>Branch</th>
<th>Year</th>
<th>Contact</th>
<th>Email</th>
<th>Password</th>
</tr>
<% while(rs.next()){ %>
<tr>
<td><%= rs.getString(1) %></td>
<td><%= rs.getString(2) %></td>
<td><%= rs.getString(3) %></td>
<td><%= rs.getString(4) %></td>
<td><%= rs.getString(5) %></td>
<td><%= rs.getString(6) %></td>
<td><%= rs.getString(7) %></td>

</tr>
<% }%>
</table>

<button onclick="logoutFromPage()">Logout</button>

<script>
	function logoutFromPage(){
		window.location.replace("/studentRegistrationAndLogin");
	}
</script>

</body>
</html>
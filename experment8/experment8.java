package experment8;
import java.sql.*;
import java.io.*;
public class ConsoleJdbc
{
 public static void main(String arg[])
 {
 try
 {
 Class.forName("com.mysql.jdbc.Driver");
 Connection con;
 String url="jdbc:mysql://localhost:3306/onlinestore";
 con=DriverManager.getConnection(url,”root","");
 Statement st=con.createStatement();
 ResultSet rs=st.executeQuery("Select * from user");
 System.out.println("Name\tPassword\tE-Mail\tPhoneNo");
 while(rs.next())
 {
 System.out.print(rs.getString(1)+ "\t"+ rs.getString(2) +"\t");
 System.out.println(rs.getString(3) +"\t"+ rs.getString(4));
 }
 } 
Advanced Java Lab Manual
Shri Vishnu Engineering College for Women (Autonomous), IT Department 15
 catch(SQLException e)
 {
 System.out.println("Error is:"+e);
 }
 catch(Exception e)
 {
 System.out.println("Error is:"+e);
 }
 }
}
Servlet with JDBC
index.html
<html>
<head>
<TITLE>USER REGISTRATION PAGE</TITLE>
</head>
<body>
<center><font size=8> Registration Form </font></center>
<form action="./registration" method="post">
<table width=100% border=0>
<tr>
 <th>Name:</th>
 <td>
 <input type="text" name="nametxt">
 </td>
</tr>
<tr>
 <th>Password:</th>
 <td>
 <input type="password" name="passtxt">
Advanced Java Lab Manual
Shri Vishnu Engineering College for Women (Autonomous), IT Department 16
 </td>
</tr>
<tr>
 <th>Email id:</th>
 <td>
 <input type="text" name="emailtxt">
 </td>
</tr>
<tr>
 <th>Phone number:</th>
 <td>
 <input type="text" name="phtxt" maxlength=10>
 </td>
</tr>
<tr>
 <th>
 <input type="submit" value="Submit">
 </th>
 <td>
 <input type="reset" value="Reset">
 </td>
</tr>
</table>
</form>
</body>
</html>
RegistrationServlet.java
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*; 
Advanced Java Lab Manual
Shri Vishnu Engineering College for Women (Autonomous), IT Department 17
public class RegistrationServlet extends HttpServlet
{
public Connection con;
public void init(ServletConfig sc) throws ServletException
 {
 try {
 Class.forName("com.mysql.jdbc.Driver");
String url="jdbc:mysql://localhost:3306/onlinestore";
con=DriverManager.getConnection(url,”root","");
 }
 catch (Exception e)
 {
 System.out.println("Error in connection"+e.getMessage());
 }
 }
public void doPost(HttpServletRequest req, HttpServletResponse res)
 throws ServletException, IOException
 {
 PrintWriter pw = res.getWriter();

 Statement st;
 Statement s1;
 try {
 String name = req.getParameter("nametxt");
 String pass = req.getParameter("passtxt");
 String email = req.getParameter("emailtxt");
 String phno = req.getParameter("phtxt");
 st = con.createStatement();
 String qry="insert into user values(?,?,?,?)";
 PreparedStatement insertQuery=con.prepareStatement(qry); 
 insertQuery.setString(1,name);
 insertQuery.setString(2,pass);
 insertQuery.setString(3,email);
 insertQuery.setString(4,phno);
 int num = insertQuery.executeUpdate();
 pw.println("<html>");
 pw.println("<body>" + "<h1>" + num + " row is updated</h1>");
 s1 = con.createStatement();
 ResultSet rs = s1.executeQuery("select * from user");
 pw.println("<table width=0>");
 pw.println("<tr><th>Register number </th>”);
 pw.println(“<th>password</th> <th>email-id</th>“);
 pw.println(“<th>ph_no</th></tr>");
 while (rs.next())
 {
 pw.println("<tr><td>" + rs.getString(1) + "</td><td>");
 pw.println(rs.getString(2) + "</td><td>");
 pw.println(rs.getString(3)+ "</td><td>");
 pw.println(rs.getString(4) + "</td></tr>");
 }
 pw.println("</table></body></html>");
 pw.close();
 }
 catch (SQLException e)
 {
 pw.println(e); 
}
 catch (Exception e)
 {
 pw.println(e);
 }
 }
}
Web.xml
<web-app>
 <servlet>
 <servlet-name>rg</servlet-name>
 <servlet-class>RegistrationServlet</servlet-class>
 </servlet>
 <servlet-mapping>
 <servlet-name>rg</servlet-name>
 <url-pattern>/registration</url-pattern>
 </servlet-mapping>

</web-app>
JSP with JDBC
Registration.jsp
<html>
<body>
<%@ page import="java.sql.*" %>
<%!
 Connection con;
 Statement st;
 ResultSet rs;
 int no; 
String q,name1,pwd1,email1,phoneno;

%>
<%
 Class.forName("com.mysql.jdbc.Driver");
 String url="jdbc:mysql://localhost:3306/onlinestore";
 con=DriverManager.getConnection(url,”root","");
 st=con.createStatement();

 name1=request.getParameter("nametxt");
 pwd1=request.getParameter("passtxt");
 email1=request.getParameter("emailtxt");
 phoneno=request.getParameter("phtxt");
 q= "insert into user values ('" + name1 + "','" + pwd1 + "','" + email1;
 q=q+"','" +phoneno+"')";
 no=st.executeUpdate(q);
if(no>0)
{
%>

 <h3><%=no%> row is inserted</h3>
<% } %>
<h3>Total Users </h3>
 <table width=0>
 <tr>
<th>Register number </th>
 <th>password</th>
 <th>email-id</th> 
 <th>ph_no</th>
 </tr>
 <%
 st=con.createStatement();
 rs=st.executeQuery("select * from user");
 while(rs.next())
 {
 %>
<tr>
 <td><%=rs.getString(1)%>
 <td><%=rs.getString(2)%>
 <td><%=rs.getString(3)%>
 <td><%=rs.getString(4)%>
 </tr>
<%
}
%>
</table>
</body>
</html>

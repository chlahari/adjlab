package experment7;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
public class LoginClass extends HttpServlet
{
 Enumeration en;
 ServletConfig sc;
public void init(ServletConfig sc) throws ServletException
 {
 this.sc = sc;
 en = sc.getInitParameterNames();
 }
public void doGet(HttpServletRequest req, HttpServletResponse res)
 throws ServletException, IOException
 {
 Cookie c[] = new Cookie[4];
 int i = 0;
 while (en.hasMoreElements())
 {
 String name = (String) en.nextElement();
 String value = sc.getInitParameter(name);
 c[i] = new Cookie(name, value);
 res.addCookie(c[i]);
 i++;
 }
 PrintWriter pw = res.getWriter();
 pw.println("<head>");
 pw.println("<head><title>Welcome to loginpage");
 pw.println(“</title></head>”);
 pw.println("<body>" + "<form action=./MyLogin method=post>");
pw.println("<br><br><br><center>”);
pw.println(“<font size=4>Login:</font>”);
pw.println(“<input type=text name=t1><br><br>");
pw.println("<font size=4>Password:</font>”);
pw.println(“<input type=password name=t2><br>");
pw.println("<input type=submit value=Submit>”);
pw.println(“<input type=reset value=Reset></center>");
 pw.println("</form></body></html>");
 pw.close();
 }
public void doPost(HttpServletRequest req, HttpServletResponse res)
 throws ServletException, IOException
 {
 PrintWriter pw = res.getWriter(); 
Advanced Java Lab Manual
Shri Vishnu Engineering College for Women (Autonomous), IT Department 13
 String name = req.getParameter("t1");
 String pass = req.getParameter("t2");
 Cookie c[] = req.getCookies();
 int flag = 0;
 for (Cookie x : c)
 {
 String cname = (String) x.getName();
 String cpass = (String) x.getValue();
 if (name == cname && pass == cpass)
 {
 pw.println("<h1>Welcome " + name + "</h1>");
 flag = 1;
 break;
 }
 }
 if (flag == 0)
 {
 pw.println("<h1> You are not authenticated user</h1>");
 }
 pw.close();
 }
}

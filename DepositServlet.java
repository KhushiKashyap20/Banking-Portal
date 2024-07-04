

	import java.io.IOException;
	import java.io.PrintWriter;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;


	@WebServlet("/DepositServlet")
	public class DepositServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

	    
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			int num = Integer.parseInt(request.getParameter("num"));
			int balance = Integer.parseInt(request.getParameter("balance"));
			PrintWriter out =response.getWriter();
			response.setContentType("text/html");
			
			String driver = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			try {
				Class.forName(driver);
				Connection con = DriverManager.getConnection(url,"khushi","khushi");
				
				String query = "update account set balance = balance + ? where num= ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1,balance);
				ps.setInt(2,num);
				int count = ps.executeUpdate();	
				if (count>0)
				{
					out.println("<h2>Amount deposited<h2>");
				RequestDispatcher rd= request.getRequestDispatcher("Success.html");
					rd.forward(request, response);
					
				}
				
				else
					out.println("<h2>Not deposited<h2>");
			}
			catch(Exception e){
				out.println("<h2>Exception :" +e.getMessage()+"</h2>");
			}
		}

	}
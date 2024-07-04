

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WithdrawServlet")


public class WithdrawServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		int amt = Integer.parseInt(request.getParameter("balance"));
		PrintWriter out =response.getWriter();
		response.setContentType("text/html");
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url,"khushi","khushi");
			
			String query = "Select balance from account where num =?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, num);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				
				int balance = rs.getInt(1);
				if(amt<= balance) {
					String query1 = "update account set balance = balance -? where num = ?";
					PreparedStatement ps1 = con.prepareStatement(query1);
					ps1.setInt(1, amt);
					ps1.setInt(2, num);
					ps1.executeUpdate();
					out.println("<h2>Amount Withdrawn successfully<h2>");
					RequestDispatcher rd= request.getRequestDispatcher("Success.html");
					rd.forward(request, response);
					
				}
				else {
					out.println("<h2>Low Balance<h2>");
				}
			}
			else {
				out.println("<h2>Invalid Account<h2>");
			}
		}
		catch(Exception e){
			out.println("<h2>Exception :" +e.getMessage()+"</h2>");
		
	}

}
}
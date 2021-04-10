package com.coupon.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coupon.logic.Customer;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in login 4");

		// get site data from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");
		
		// set site mode to register
		site_data.set_mode(SiteMode.login);
		
		// Receive form's data
		String user_name = (String)request.getParameter("user_name");
		String user_password = (String)request.getParameter("user_password");
				
		try
		{
			// Create the customer and save to DB
			Customer customer = new Customer().login(user_name, user_password);
			
			site_data.set_customer(customer);
			site_data.set_mode(SiteMode.home);
		}
		catch (CouponSiteMsg msg)
		{
			site_data.set_msg(msg);
		}
		
		// back to site
		session.setAttribute("site_data", site_data);
		String nextJSP = "/index.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}

}

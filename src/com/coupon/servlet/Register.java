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
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in register 4");

		// get site data from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");

		// set site mode to register
		site_data.set_mode(SiteMode.register);
		
		// Receive form's data
		String full_name = (String)request.getParameter("full_name");
		String user_name = (String)request.getParameter("user_name");
		String user_password = (String)request.getParameter("user_password");
		String user_type = (String)request.getParameter("user_type");
		
		System.out.println(full_name + " " + user_name + " " + user_password + " " + user_type);
		
		try
		{
			// Create the customer and save to DB
			Customer customer = new Customer().register(user_name, full_name, user_password, user_type);
			
			// When new customer is registered, otherwise register by admin
			if (site_data.get_customer() == null)
			{
				site_data.set_customer(customer);	
				site_data.set_mode(SiteMode.home);
			}
			else
			{
				site_data.set_customer_list(new Customer().get_all_customers());
				site_data.set_mode(SiteMode.customers_list);
			}
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

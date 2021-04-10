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
import com.coupon.msg.UnauthorizedAccessError;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class CustomersList
 */
@WebServlet("/customers_list")
public class CustomersList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomersList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in customers_list 4");

		// get site data from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");
		
		try
		{
			site_data.set_mode(SiteMode.customers_list);
			
			site_data.set_customer_list(new Customer().get_all_customers());
		}
		catch (NullPointerException npe)
		{
			site_data = new SiteData();
			site_data.set_msg(new UnauthorizedAccessError());
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

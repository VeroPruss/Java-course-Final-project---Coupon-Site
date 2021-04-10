package com.coupon.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coupon.logic.Coupon;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.UnauthorizedAccessError;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class ManageCoupons
 */
@WebServlet("/manage_coupons")
public class ManageCoupons extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageCoupons() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in manage coupons 4");

		// get site data from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");
		
		try
		{	
			site_data.set_mode(SiteMode.manage_coupons);
			
			site_data.set_coupon_list(new Coupon().get_all_coupons());
			
			// Creates the first row which enables addition of new coupon
			site_data.get_coupon_list().put(null, null);
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

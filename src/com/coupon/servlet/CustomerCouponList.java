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
import com.coupon.logic.CustomerCoupon;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.DeleteSucess;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class CustomerCouponList
 */
@WebServlet("/customer_coupon_list")
public class CustomerCouponList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerCouponList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in customer-coupno-list 4");

		// get site data from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");
		
		site_data.set_mode(SiteMode.customer_coupon_list);
		
		
		try
		{
			site_data.set_customer_coupon_list(new CustomerCoupon().coupons_for_customer(site_data.get_customer().get_user_name()));
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

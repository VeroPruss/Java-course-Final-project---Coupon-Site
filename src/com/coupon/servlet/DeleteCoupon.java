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
import com.coupon.msg.DeleteSucess;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class DeleteCoupon
 */
@WebServlet("/delete_coupon")
public class DeleteCoupon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCoupon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in delete-coupon 4");

		// Get site data objects from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");
		
		// Set site mode to manage coupons
		site_data.set_mode(SiteMode.manage_coupons);
		
		// Receive form's data
		String coupon_id = (String)request.getParameter("coupon_id");
		
		System.out.println(coupon_id);
		
		try
		{
			new Coupon().delete_coupon(coupon_id);
			site_data.get_coupon_list().remove(coupon_id);
			site_data.set_msg(new DeleteSucess());
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

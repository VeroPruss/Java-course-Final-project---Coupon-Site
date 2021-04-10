package com.coupon.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coupon.logic.Coupon;
import com.coupon.logic.CustomerCoupon;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.ExpDateError;
import com.coupon.msg.NumericDataError;
import com.coupon.msg.PurchaseSuccess;
import com.coupon.msg.UpdateSuccess;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class PurchaseCoupon
 */
@WebServlet("/purchase_coupon")
public class PurchaseCoupon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseCoupon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		System.out.println("in purchase-coupon 4");

		// get site data from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");
		
		site_data.set_mode(SiteMode.coupons_list);
		
		try
		{
			// Receive form's data
			// Receive string type
			String coupon_id = (String)request.getParameter("coupon_id");
			
			// Receive numeric type
			int amount = Integer.parseInt((String)request.getParameter("amount"));
			
			System.out.println(coupon_id + " " + amount);
			
			new CustomerCoupon().purchase_coupon(site_data.get_customer().get_user_name(), 
					coupon_id, amount);
			
			site_data.set_mode(SiteMode.customer_coupon_list);
			site_data.set_customer_coupon_list(new CustomerCoupon().coupons_for_customer(
					site_data.get_customer().get_user_name()));
			site_data.set_msg(new PurchaseSuccess());	
		}
		// Handles numeric parsing exception
		catch (NumberFormatException nfe)
		{
			site_data.set_msg(new NumericDataError());
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

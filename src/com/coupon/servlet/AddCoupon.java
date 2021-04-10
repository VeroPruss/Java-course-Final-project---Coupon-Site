package com.coupon.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coupon.logic.Coupon;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.ExpDateError;
import com.coupon.msg.InsertSuccess;
import com.coupon.msg.NumericDataError;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class AddCoupon
 */
@WebServlet("/add_coupon")
public class AddCoupon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCoupon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in add coupon 4");

		// Get site data object from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");

		// Set site mode to manager coupons
		site_data.set_mode(SiteMode.manage_coupons);
		
		try
		{
			// Receive form's data
			// Receive string types
			String coupon_id = (String)request.getParameter("coupon_id");
			String type = (String)request.getParameter("type");
			String title = (String)request.getParameter("title");
			String desc = (String)request.getParameter("desc");
			String image_url = (String)request.getParameter("image_url");
			
			// Convert expiration date to sql type
			DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date util_exp_date = date_format.parse((String)request.getParameter("exp_date"));;
			java.sql.Date exp_date = new java.sql.Date(util_exp_date.getTime());
			
			// Receive numeric types
			float price = Float.parseFloat((String)request.getParameter("price"));
			int total_amount = Integer.parseInt((String)request.getParameter("total_amount"));
			int available_amount = Integer.parseInt((String)request.getParameter("available_amount"));
			
			System.out.println(coupon_id + " " + exp_date + " " + price + " " + total_amount
			+ " " + available_amount + " " + type + " " + title + " " + desc + " " + image_url);
			
			// Create the coupon and save to DB
			Coupon coupon = new Coupon().add_coupon(coupon_id, exp_date, price, total_amount, 
													available_amount, type, title, desc, image_url);
			
			// Add coupon to coupon list
			site_data.get_coupon_list().put(coupon.get_coupon_id(), coupon);
			
			// Set success message
			site_data.set_msg(new InsertSuccess());
		}
		// Handles date parsing exception
		catch (ParseException pe) 
		{
			pe.printStackTrace();
			site_data.set_msg(new ExpDateError());
		}
		// Handles numeric parsing exception
		catch (NumberFormatException nfe)
		{
			site_data.set_msg(new NumericDataError());
		}
		// Handles other messages
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

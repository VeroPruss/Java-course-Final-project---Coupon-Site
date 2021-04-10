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
import com.coupon.logic.Customer;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.ExpDateError;
import com.coupon.msg.NumericDataError;
import com.coupon.msg.UpdateSuccess;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class UpdateCoupon
 */
@WebServlet("/update_coupon")
public class UpdateCoupon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCoupon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		System.out.println("in update-coupon 4");

		// get site data from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");
		
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
			
			System.out.println("here1");
			
			// Convert expiration date to sql type
			DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date util_exp_date = date_format.parse((String)request.getParameter("exp_date"));;
			java.sql.Date exp_date = new java.sql.Date(util_exp_date.getTime());
			
			System.out.println("here2");
			
			// Receive numeric types
			float price = Float.parseFloat((String)request.getParameter("price"));
			int total_amount = Integer.parseInt((String)request.getParameter("total_amount"));
			int available_amount = Integer.parseInt((String)request.getParameter("available_amount"));
			
			System.out.println(coupon_id + " " + exp_date + " " + price + " " + total_amount
			+ " " + available_amount + " " + type + " " + title + " " + desc + " " + image_url);
			
			Coupon coupon = new Coupon().update_coupon(coupon_id, exp_date, price, total_amount, 
													   available_amount, type, title, desc, image_url);
						
			site_data.get_coupon_list().replace(coupon_id, coupon);
			site_data.set_msg(new UpdateSuccess());	
		}
		// Handles date parsing exception
		catch (ParseException pe) 
		{
			site_data.set_msg(new ExpDateError());
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

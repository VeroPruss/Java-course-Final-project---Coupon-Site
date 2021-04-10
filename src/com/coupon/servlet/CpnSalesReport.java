package com.coupon.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coupon.msg.CouponSiteMsg;
import com.coupon.reports.CouponSalesReport;
import com.coupon.reports.ExpiredCouponsReport;
import com.coupon.site.SiteData;
import com.coupon.site.SiteMode;

/**
 * Servlet implementation class CpnSalesReport
 */
@WebServlet("/coupon_sales_report")
public class CpnSalesReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CpnSalesReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("in coupons sales report");

		// get site data from session
		HttpSession session = request.getSession();
		SiteData site_data = (SiteData)session.getAttribute("site_data");
		
		site_data.set_mode(SiteMode.coupon_sales_report);
		
		try
		{
			CouponSalesReport report = new CouponSalesReport();
			site_data.set_report_headers(report.get_report_headers());
			site_data.set_report_data(report.create_report());
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

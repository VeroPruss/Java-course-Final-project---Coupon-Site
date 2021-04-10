<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<% SiteData site_data = (SiteData)session.getAttribute("site_data");

   if (site_data == null)
   {
	   site_data = new SiteData();
	   site_data.set_mode(SiteMode.home);
	   session.setAttribute("site_data", site_data);
   } %>
   
	<%@include file="coupon_header.jsp" %>
	
	<%@include file="nav_bar.jsp" %>
	
	<%@include file="error.jsp"%>
	
	<%@include file="home_page.jsp" %>
	
	<%@include file="register_login.jsp" %>
	
	<%@include file="coupon_list.jsp" %>
	
	<%@include file="customer_coupon_list.jsp" %>
	
	<%@include file="customer_list.jsp" %>
	
	<%@include file="manage_coupons.jsp" %>
	
	<%@include file="exp_coupons_report.jsp" %>

	<%@include file="coupon_sales_report.jsp" %>
	
	<%@include file="footer.jsp" %>
</body>
</html>
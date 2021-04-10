<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@page import="com.coupon.msg.CouponSiteMsg"%>
<%@page import="com.coupon.msg.ErrorSeverity"%>

<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<% SiteData error_data = (SiteData)session.getAttribute("site_data");
   if (error_data != null)
   {
 		CouponSiteMsg msg = error_data.get_msg();
	    if (msg != null)
        { %> 
	   <div
	   		<% switch (msg.get_severity())
			{
				case Info: %> class="alert alert-info"
					<% break; 
				case Warning: %> class="alert alert-warning"
					<% break;
				case Success: %> class="alert alert-success"
					<% break;
				default: %>  class="alert alert-danger"
					<% break;
			} %>
	   >
	   		<strong> <%= msg.get_error_msg() %> </strong>
	   </div>
	   
	   <% error_data.set_msg(null);
	   	session.setAttribute("site_data", error_data);
 	  } 
  }%>
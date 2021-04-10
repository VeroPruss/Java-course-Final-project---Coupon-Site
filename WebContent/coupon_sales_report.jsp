<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@page import="com.coupon.logic.Coupon"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<% SiteData cpn_sales_rep = (SiteData)session.getAttribute("site_data");

        if (cpn_sales_rep.get_mode() == SiteMode.coupon_sales_report && 
        	cpn_sales_rep.get_customer().isAdmin() &&
        	cpn_sales_rep.get_report_data() != null)
        	{ %>
	<div id="coupon_sales" class="table table-responsive">
		<table class="table table-hover">
			<tr>
				<% for (String header : cpn_sales_rep.get_report_headers()) 
					{ %>	
					<th><%=header%></th>		
				<% } %>
			</tr>	
			
			<% for (Coupon tmp : cpn_sales_rep.get_report_data()) 
			   { %>
				<tr>
					<td><%=tmp.get_coupon_id() %></td>
					<td><%=tmp.get_total_amount() %></td>
				</tr>	
			<% } %>
		</table>
	</div>
	<% } %>

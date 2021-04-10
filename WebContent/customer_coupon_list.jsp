<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@page import="com.coupon.logic.CustomerCoupon"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<% SiteData list_data = (SiteData)session.getAttribute("site_data");

    if (list_data.get_mode() == SiteMode.customer_coupon_list && 
    	list_data.get_customer_coupon_list() != null)
    	{
    %>
	<h1> Coupon list for <%= list_data.get_customer().get_full_name() %></h1>
	<div id="mycouponlist" class="table table-responsive">
	
		<table class="table table-hover">
			<tr>
				<% for (String header : list_data.get_customer_coupon_list_headers()) 
					{ %>	
					<th><%=header%></th>		
				<% } %>
			</tr>	
			
			<% for (CustomerCoupon tmp : list_data.get_customer_coupon_list()) 
			   { %>
				<tr>
					<td><%=tmp.get_coupon().get_coupon_id() %></td>
					<td><%=tmp.get_coupon().get_exp_date() %></td>
					<td><%=tmp.get_coupon().get_price() %></td>
					<td><%=tmp.get_amount() %></td>
					<td><%=tmp.get_coupon().get_type() %></td>
					<td><%=tmp.get_coupon().get_title() %></td>
					<td><%=tmp.get_coupon().get_coupon_desc() %></td>
					<td>
						<img src="<%=tmp.get_coupon().get_img_url()%>">
					</td>
				</tr>	
			<% } %>
		</table>
	</div>
	<% } %>
	

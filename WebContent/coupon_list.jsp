<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@page import="com.coupon.logic.Coupon"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<%
    	SiteData coupon_list_data = (SiteData)session.getAttribute("site_data");

        if (coupon_list_data.get_mode() == SiteMode.coupons_list && 
        	coupon_list_data.get_coupon_list() != null)
        	{
    %>
	<div id="mycouponlist" class="table table-responsive vertical-align">
		<table class="table table-hover">
			<tr>
				<% for (String header : coupon_list_data.get_coupon_list_headers()) 
					{ %>	
					<th><%=header%></th>		
				<% } %>
				<% if (coupon_list_data.get_customer() != null) { %>
					<th>amount</th>
				<% } %>
			</tr>	
			
			<% for (Coupon tmp : coupon_list_data.get_coupon_list().values()) 
			   { %>
				<tr>
					<td class="align-middle"><%=tmp.get_coupon_id() %></td>
					<td><%=tmp.get_exp_date() %></td>
					<td><%=tmp.get_price() %></td>
					<td><%=tmp.get_total_amount() %></td>
					<td><%=tmp.get_available_amount() %></td>
					<td><%=tmp.get_type() %></td>
					<td><%=tmp.get_title() %></td>
					<td><%=tmp.get_coupon_desc() %></td>
					<td>
						<img src="<%=tmp.get_img_url()%>">
					</td>
					<% if (coupon_list_data.get_customer() != null) { %>
						<td>
							<form class="form-horizontal" method="post" action="purchase_coupon">
								<input type="hidden" name="coupon_id" value="<%=tmp.get_coupon_id()%>">
								<input type="number" name="amount" size="3" min="1" max="<%=tmp.get_available_amount()%>" value="1">
								<button type="submit" class="btn btn-default">Purchase</button>
							</form>
						</td>
					<% } %>
				</tr>	
			<% } %>
		</table>
	</div>
	<% } %>

<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@page import="com.coupon.logic.Coupon"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<% SiteData exp_rep = (SiteData)session.getAttribute("site_data");

        if (exp_rep.get_mode() == SiteMode.exp_coupon_report && 
        	exp_rep.get_customer().isAdmin() &&
        	exp_rep.get_report_data() != null)
        	{ %>
	<div id="exp_coupons" class="table table-responsive">
		<table class="table table-hover">
			<tr>
				<% for (String header : exp_rep.get_report_headers()) 
					{ %>	
					<th><%=header%></th>		
				<% } %>
			</tr>	
			
			<% for (Coupon tmp : exp_rep.get_report_data()) 
			   { %>
				<tr>
					<td><%=tmp.get_coupon_id() %></td>
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
				</tr>	
			<% } %>
		</table>
	</div>
	<% } %>

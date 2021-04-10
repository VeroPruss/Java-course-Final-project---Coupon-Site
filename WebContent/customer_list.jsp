<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@page import="com.coupon.logic.Customer"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<% SiteData customer_list_data = ((SiteData)session.getAttribute("site_data")==null) ?
								new SiteData() : (SiteData)session.getAttribute("site_data");
								
   boolean customer_list_admin = (customer_list_data.get_customer() == null) ?
			false : customer_list_data.get_customer().isAdmin();

        if (customer_list_data.get_mode() == SiteMode.customers_list &&
        	customer_list_admin &&
        	customer_list_data.get_customer_list() != null)
        	{
    %>
	
	<div id="mycouponlist" class="table table-responsive">
		<p><h1> List of registered customers:</h1></p>
		<table class="table table-hover">
			<tr>
				<% for (String header : customer_list_data.get_customer_list_headers()) 
					{ %>	
					<th><%=header%></th>		
				<% } %>
			</tr>	
			
			<% for (Customer tmp : customer_list_data.get_customer_list().values()) 
			   { 
				if (!tmp.get_user_name().equals(customer_list_data.get_customer().get_user_name())) 
				{ %>
				<tr>
				<form class="form-horizontal" method="post" action="update_customer">
					<input type="hidden" name="user_name" value="<%=tmp.get_user_name() %>">
					<td>
						<label><%=tmp.get_user_name() %></label>
					</td>
					<td>
						<input type="text" class="form-control" maxlength="35" name="full_name" value="<%=tmp.get_full_name() %>">
					</td>
					<td>
						<input type="password" class="form-control" name="user_password" maxlength="20" value="<%=tmp.get_password() %>">
					</td>
					<td>
						<input type="radio" name="user_type" value="customer" <% if (!tmp.isAdmin()) { %> checked <% } %> >
				        <label class="control-label" for="type">Customer</label>
				        <input type="radio" name="user_type" value="admin" <% if (tmp.isAdmin()) { %> checked <% } %> >
				        <label class="control-label" for="type">Administrator</label>
					</td>
					<td>
						<button type="submit" class="btn btn-default">Update</button>
					</td>
				</form>
					<td>
						<form class="form-horizontal" method="post" action="delete_customer">
							<input type="hidden" name="user_name" value="<%=tmp.get_user_name() %>">
							<button type="submit" class="btn btn-default">Delete</button>
						</form>
					</td>
				</tr>	
			<% } 
			}%>
		</table>
	</div>
	<% } %>
	

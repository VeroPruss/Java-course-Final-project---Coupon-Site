<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@page import="com.coupon.logic.Coupon"%>
<%@page import="com.coupon.msg.UnauthorizedAccessError"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<% SiteData manage_coupons = ((SiteData)session.getAttribute("site_data")==null) ? 
								new SiteData() : (SiteData)session.getAttribute("site_data");
								
	boolean manage_coupons_admin = (manage_coupons.get_customer() == null) ?
								false : manage_coupons.get_customer().isAdmin();

	 if (manage_coupons.get_mode() == SiteMode.manage_coupons &&
		 manage_coupons_admin) { %>
		<div id="managecoupons" class="table table-responsive text-centered">
		<table class="table table-hover">
			<tr>
				<% for (String header : manage_coupons.get_coupon_list_headers()) 
					{ %>	
					<th><%=header%></th>		
				<% } %>
			</tr>	
			<tr>
				<% for (Coupon tmp : manage_coupons.get_coupon_list().values()) 
			   	   { %>
				<form id="coupon_form" class="form-horizontal" method="post" 
					  action="<% if (tmp!=null) {%>update_coupon<% } else { %>add_coupon<%}%>">
					<td>
						<% if (tmp!=null) { %>
							<%=tmp.get_coupon_id() %>
							<input type="hidden" name="coupon_id" value="<%=tmp.get_coupon_id()%>">
						<% } else { %>
							<input type="text" class="form-control" maxlength="10" name="coupon_id">
						<% } %>
					</td>
					<td>
						<input type="date" class="form-control" name="exp_date"
						<% if (tmp!=null) {%> value="<%=tmp.get_exp_date()%>"<%}%>>
					</td>
					<td>
						<input type="number" class="form-control" name="price" step="0.01"
						<% if (tmp!=null) {%> value="<%=tmp.get_price()%>"<%}%>>
					</td>
					<td>
						<input type="number" class="form-control" name="total_amount"
						<% if (tmp!=null) {%> value="<%=tmp.get_total_amount()%>"<%}%>>
					</td>
					<td>
						<input type="number" class="form-control" name="available_amount"
						<% if (tmp!=null) {%> value="<%=tmp.get_available_amount()%>"<%}%>>
				
					</td>
					<td>
						<input type="text" class="form-control" name="type"
						<% if (tmp!=null) {%> value="<%=tmp.get_type()%>"<%}%>>
					</td>
					<td>
						<input type="text" class="form-control" name="title"	
						<% if (tmp!=null) {%> value="<%=tmp.get_title()%>"<%}%>>
					</td>
					<td>
						<textarea rows="5" cols="10" name="desc" form="coupon_form" dir="ltr" draggable="false" wrap="hard" style="resize: none;"><% if (tmp!=null) {%><%=tmp.get_coupon_desc().trim()%><%}%></textarea>
					</td>
					<td>
						<input type="url" name="image_url"
						<% if (tmp!=null) {%> value="<%=tmp.get_img_url()%>"<%}%>>
					</td>
					<td>
						<button type="submit" class="btn btn-default">
							<% if (tmp!=null) {%>Update<% } else { %> Add <% } %>
						</button>
					</td>
					<% if (tmp==null) {%>
						<td> 
							<button type="reset" class="btn btn-default">Clear</button>
						</td>
					<% } %>
					</form>
					<% if (tmp!=null) {%>
						<td>
							<form class="form-horizontal" method="post" action="delete_coupon">
								<input type="hidden" name="coupon_id" value="<%=tmp.get_coupon_id() %>">
								<button type="submit" class="btn btn-default">Delete</button>
							</form>
						</td>
					<% } %>
			</tr>
			<% } %>
		</table>
	</div>
<% } %>

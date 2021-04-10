<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
  
<% SiteData reg_data = (SiteData)session.getAttribute("site_data"); %>
    
<% if (reg_data.get_mode() == SiteMode.register || reg_data.get_mode() == SiteMode.login) { %>
	<div id="register" class="container">
	  <form class="form-horizontal" method="post" 
	  <% if (reg_data.get_mode() == SiteMode.register) { %>
	  	action="register"
	  <% } else { %>
	    action="login"
	  <% } %>
	  >
	  <% if (reg_data.get_mode() == SiteMode.register) { %>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="fullname">Full name:</label>
	      <div class="col-sm-10">
	        <input type="text" name="full_name" class="form-control" placeholder="your name" maxlength="35"
	        <% if ((String)request.getParameter("full_name") != null) { %>
	        	value="<%=(String)request.getParameter("full_name") %>" 
	        <% } %>>
	      </div> 
	    </div>
	    <% } %> 
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="username">User name:</label>
	      <div class="col-sm-10">
	        <input type="text" name="user_name" class="form-control" placeholder="user name" maxlength="20"
	        <% if ((String)request.getParameter("user_name") != null) { %>
	        	value="<%=(String)request.getParameter("user_name") %>" 
	        <% } %>>
	      </div>
	    </div>
	    <div class="form-group">
	      <label class="control-label col-sm-2" for="pwd">Password:</label>
	      <div class="col-sm-10">          
	        <input type="password" class="form-control" placeholder="password" name="user_password" maxlength="20">
	      </div>
	    </div>
	    <%
	    	if (reg_data.get_customer() != null && reg_data.get_customer().isAdmin()) {
	    %>
		    <div class="form-group">
		      <label class="control-label col-sm-2" for="type">User Type:</label>
		      <div class="col-sm-10">          
		        <input type="radio" name="user_type" value="customer" checked>
		        <label class="control-label" for="type">Customer</label>
		        <input type="radio" name="user_type" value="admin">
		        <label class="control-label" for="type">Administrator</label>
		      </div>
		    </div>
	    <% } %>
	    <div class="form-group">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-default">Submit</button>
	      </div>
	    </div>
	  </form>
	</div>
<% } %>
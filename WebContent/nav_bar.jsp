<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<nav class="navbar">

<% SiteData nav_data = (SiteData)session.getAttribute("site_data"); %>	

<div class="container-fluid coupon-nav">
    <div class="navbar-header"> 
    </div>
    <ul class="nav navbar-nav">
      <li <% if (nav_data.get_mode() == SiteMode.home) { %>class="active"<%} %>>
      	<a href="home">Home</a>
      </li>
      <li <%if (nav_data.get_mode() == SiteMode.coupons_list) {%>class="active"<%} %>>
	    <a href="coupons_list">Coupon List</a>
      </li>
      <%
      	if (nav_data.get_customer() != null) {
      %>
      	<li <%if (nav_data.get_mode() == SiteMode.customer_coupon_list) {%>class="active"<%}%>>
      		<a href="customer_coupon_list">My Coupons</a>
      	</li>
      	<%
      		if (nav_data.get_customer().isAdmin()) {
      	%>
	      	<li <%if (nav_data.get_mode() == SiteMode.manage_coupons) {%>class="active"<%}%>>
	      		<a href="manage_coupons">Manage Coupons</a>
	      	</li>
		    <li class="dropdown">
		    	<a class="dropdown-toggle" data-toggle="dropdown" href="#">Customers
		        <span class="caret"></span></a>
		        <ul class="dropdown-menu">
		          <li><a href="register">Register</a></li>
		          <li><a href="customers_list">Manage</a></li>
		        </ul>
		    </li>
      		<li class="dropdown">
		    	<a class="dropdown-toggle" data-toggle="dropdown" href="#">Reports
		        <span class="caret"></span></a>
		        <ul class="dropdown-menu">
		          <li><a href="exp_coupons_report">Expired Coupons</a></li>
		          <li><a href="coupon_sales_report">Coupon Sales</a></li>
		        </ul>
		    </li>
      	<%
      		}
      	%>
      <%
      	}
      %>
    </ul>
    <%
    	if (nav_data.get_customer() != null) {
    %>
        <ul class="nav navbar-nav navbar-right">
      	<li>
      		<a><span
      			<%if (nav_data.get_customer().isAdmin()) {%>
      				class="glyphicon glyphicon-star"
      			<%} else {%>
      				class="glyphicon glyphicon-check"
      			<%}%>
      		></span> Hello <%=nav_data.get_customer().get_full_name()%></a>
      	</li>
      	<li>
      		<a href="logout"><span class="glyphicon glyphicon-log-out"></span>  Logout</a>
      	</li>
    </ul>
    <% } else { %>
    <ul class="nav navbar-nav navbar-right">
      	<li <% if (nav_data.get_mode() == SiteMode.register) { %>class="active"<%} %>>
      		<a href="register"><span class="glyphicon glyphicon-user"></span>   Register</a>
      	</li>
      	<li <% if (nav_data.get_mode() == SiteMode.login) { %>class="active"<%} %>>
      		<a href="login"><span class="glyphicon glyphicon-log-in"></span>  Login</a>
      	</li>
    </ul>
    <% } %>
  </div>
</nav>
  



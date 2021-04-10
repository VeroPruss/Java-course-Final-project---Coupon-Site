<%@page import="com.coupon.site.SiteData"%>
<%@page import="com.coupon.site.SiteMode"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
    
<% SiteData home_data = (SiteData)session.getAttribute("site_data"); %>	

<% if (home_data == null || home_data.get_mode() == SiteMode.home) 
   { %>
	<div id="home" class="container" align="center">
		<h1> Welcome to Lifestyle&Co! We have the most fantastic coupons! </h1>
	</div>
<% } %>

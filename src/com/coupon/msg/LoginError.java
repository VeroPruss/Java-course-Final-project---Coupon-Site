package com.coupon.msg;

public class LoginError extends CouponSiteMsg 
{
	public LoginError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Login failed; user name or password incorrect!");
	}
}

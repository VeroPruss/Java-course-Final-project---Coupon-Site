package com.coupon.msg;

public class UnauthorizedAccessError extends CouponSiteMsg 
{
	public UnauthorizedAccessError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Unauthorized access to site. Please return to main page and login/register properly.");
	}
}

package com.coupon.msg;

public class EmptyFullNameError extends CouponSiteMsg  
{
	public EmptyFullNameError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Please enter user name");
	}
}

package com.coupon.msg;

public class EmptyUserNameError extends CouponSiteMsg 
{
	public EmptyUserNameError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Please enter user name");
	}
}

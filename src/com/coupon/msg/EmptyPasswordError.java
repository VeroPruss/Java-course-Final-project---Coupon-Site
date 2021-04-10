package com.coupon.msg;

public class EmptyPasswordError extends CouponSiteMsg  
{
	public EmptyPasswordError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Please enter password");
	}
}

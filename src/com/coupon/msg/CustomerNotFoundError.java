package com.coupon.msg;

public class CustomerNotFoundError extends CouponSiteMsg 
{
	public CustomerNotFoundError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("No customer was found with these credentials");
	}
}

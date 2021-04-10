package com.coupon.msg;

public class NoCustomersFoundError extends CouponSiteMsg 
{
	public NoCustomersFoundError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("No customers.");
	}
}

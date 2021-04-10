package com.coupon.msg;

public class NoCustomerCouponFoundError extends CouponSiteMsg 
{
	public NoCustomerCouponFoundError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("No combination of customer and coupon was found.");
	}
}

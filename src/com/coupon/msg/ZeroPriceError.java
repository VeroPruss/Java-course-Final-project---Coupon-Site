package com.coupon.msg;

public class ZeroPriceError extends CouponSiteMsg  
{
	public ZeroPriceError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Coupon price cannot be zero.");
	}
}

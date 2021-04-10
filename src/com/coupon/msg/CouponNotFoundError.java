package com.coupon.msg;

public class CouponNotFoundError extends CouponSiteMsg 
{
	public CouponNotFoundError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Coupon was not found.");
	}
}

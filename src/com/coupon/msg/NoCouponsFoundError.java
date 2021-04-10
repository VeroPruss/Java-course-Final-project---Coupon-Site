package com.coupon.msg;

public class NoCouponsFoundError extends CouponSiteMsg  
{
	public NoCouponsFoundError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("No coupons.");
	}
}

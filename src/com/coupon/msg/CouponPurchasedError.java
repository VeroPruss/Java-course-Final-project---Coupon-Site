package com.coupon.msg;

public class CouponPurchasedError extends CouponSiteMsg  
{
	public CouponPurchasedError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Cannot delete coupon, was already purchased by customers.");
	}
}

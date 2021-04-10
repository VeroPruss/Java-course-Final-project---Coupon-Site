package com.coupon.msg;

public class PurchaseSuccess extends CouponSiteMsg  
{
	public PurchaseSuccess()
	{
		this.set_severity(ErrorSeverity.Success);
		this.set_error_msg("Coupon purchased successfully.");
	}
}

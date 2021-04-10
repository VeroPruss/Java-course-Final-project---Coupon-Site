package com.coupon.msg;

public class DuplicateCustomerCouponError extends CouponSiteMsg 
{
	public DuplicateCustomerCouponError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Cannot purchase the same coupon twice.");
	}
}

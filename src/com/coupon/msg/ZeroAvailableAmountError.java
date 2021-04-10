package com.coupon.msg;

public class ZeroAvailableAmountError extends CouponSiteMsg  
{
	public ZeroAvailableAmountError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Available amount of the coupon cannot be zero.");
	}
}

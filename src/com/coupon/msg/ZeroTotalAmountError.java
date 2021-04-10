package com.coupon.msg;

public class ZeroTotalAmountError extends CouponSiteMsg  
{
	public ZeroTotalAmountError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Total amount of the coupon cannot be zero.");
	}
}

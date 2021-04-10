package com.coupon.msg;

public class EmptyCouponIdError extends CouponSiteMsg  
{
	public EmptyCouponIdError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Please enter an unique coupon id");
	}
}

package com.coupon.msg;

public class EmptyCouponTypeError extends CouponSiteMsg
{
	public EmptyCouponTypeError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Please enter coupon type.");
	}
}

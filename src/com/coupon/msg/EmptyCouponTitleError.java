package com.coupon.msg;

public class EmptyCouponTitleError extends CouponSiteMsg
{
	public EmptyCouponTitleError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Please enter coupon title.");
	}
}

package com.coupon.msg;

public class DuplicateCouponError extends CouponSiteMsg
{
	public DuplicateCouponError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("A coupon with the same id exists, please choose another id.");
	}
}

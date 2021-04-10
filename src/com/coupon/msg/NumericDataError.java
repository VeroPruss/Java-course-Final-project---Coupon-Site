package com.coupon.msg;

public class NumericDataError extends CouponSiteMsg 
{
	public NumericDataError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Coupon price or the amounts are missing. Please fill them correctly.");
	}
}

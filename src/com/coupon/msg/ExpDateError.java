package com.coupon.msg;

public class ExpDateError extends CouponSiteMsg  
{
	public ExpDateError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Expiration date is incorrect.");
	}
}

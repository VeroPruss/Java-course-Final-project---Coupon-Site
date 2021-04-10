package com.coupon.msg;

public class ExpirationDateError extends CouponSiteMsg  
{
	public ExpirationDateError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Expiration date should be at least two weeks ahead of current date.");
	}
}

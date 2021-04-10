package com.coupon.msg;

public class ExpirationDateDecreaseError extends CouponSiteMsg 
{
	public ExpirationDateDecreaseError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Expiration date cannot be decreased.");
	}
}

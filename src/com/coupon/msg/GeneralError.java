package com.coupon.msg;

public class GeneralError extends CouponSiteMsg  
{
	public GeneralError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("An unexpected error has occured, please try again later.");
	}
}

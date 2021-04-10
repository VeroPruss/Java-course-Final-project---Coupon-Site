package com.coupon.msg;

public class DuplicateUserNameError extends CouponSiteMsg 
{
	public DuplicateUserNameError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("User name already exists, please choose another.");
	}
}

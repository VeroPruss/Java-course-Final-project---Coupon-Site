package com.coupon.msg;

public class UpdateSuccess extends CouponSiteMsg 
{
	public UpdateSuccess()
	{
		this.set_severity(ErrorSeverity.Success);
		this.set_error_msg("Updated successfully");
	}
}

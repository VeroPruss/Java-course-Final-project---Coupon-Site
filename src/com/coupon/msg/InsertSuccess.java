package com.coupon.msg;

public class InsertSuccess extends CouponSiteMsg  
{
	public InsertSuccess()
	{
		this.set_severity(ErrorSeverity.Success);
		this.set_error_msg("Successfully saved.");
	}
}

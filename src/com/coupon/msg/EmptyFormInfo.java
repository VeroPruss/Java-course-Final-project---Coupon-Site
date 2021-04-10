package com.coupon.msg;

public class EmptyFormInfo extends CouponSiteMsg 
{
	public EmptyFormInfo()
	{
		this.set_severity(ErrorSeverity.Info);
		this.set_error_msg("Please fill in the required data.");
	}
}

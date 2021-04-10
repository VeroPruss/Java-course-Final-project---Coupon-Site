package com.coupon.msg;

public class LoginRequiredInfo extends CouponSiteMsg 
{
	public LoginRequiredInfo()
	{
		this.set_severity(ErrorSeverity.Info);
		this.set_error_msg("Please login or register first to purchase coupons.");
	}
}

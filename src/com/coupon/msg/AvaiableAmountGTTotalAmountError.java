package com.coupon.msg;

public class AvaiableAmountGTTotalAmountError extends CouponSiteMsg   
{
	public AvaiableAmountGTTotalAmountError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Available amount cannot be greater than total amount.");
	}
}

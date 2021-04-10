package com.coupon.msg;

public class AmountDiscrepancyError extends CouponSiteMsg 
{
	public AmountDiscrepancyError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Change in available amount is inconsistant with total amount.");
	}
}

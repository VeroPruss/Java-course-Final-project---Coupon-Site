package com.coupon.msg;

public class PurchaseAmountError extends CouponSiteMsg 
{
	public PurchaseAmountError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("Purchase amount greater than available amount.");
	}
}

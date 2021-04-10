package com.coupon.msg;

public class DeleteSucess extends CouponSiteMsg 
{
	public DeleteSucess()
	{
		this.set_severity(ErrorSeverity.Success);
		this.set_error_msg("Deleted successfully");
	}
}

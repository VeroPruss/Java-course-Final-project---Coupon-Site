package com.coupon.msg;

import com.coupon.msg.CouponSiteMsg;

public class SqlError extends CouponSiteMsg 
{
	public SqlError()
	{
		this.set_severity(ErrorSeverity.Error);
		this.set_error_msg("An unexpected error has occured, please try again later.");
	}
}

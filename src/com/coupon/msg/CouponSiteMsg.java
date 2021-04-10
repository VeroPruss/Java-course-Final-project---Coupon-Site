package com.coupon.msg;

public class CouponSiteMsg extends Exception
{
	private ErrorSeverity _severity;
	private String _error_msg;
	
	public CouponSiteMsg() 
	{
		super();
	}

	public ErrorSeverity get_severity() 
	{
		return _severity;
	}

	public void set_severity(ErrorSeverity _severity) 
	{
		this._severity = _severity;
	}

	public String get_error_msg() 
	{
		return _error_msg;
	}

	public void set_error_msg(String _error_msg) 
	{
		this._error_msg = _error_msg;
	}
}

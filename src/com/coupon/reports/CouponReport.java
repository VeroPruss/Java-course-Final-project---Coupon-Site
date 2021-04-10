package com.coupon.reports;

import java.util.ArrayList;

import com.coupon.logic.Coupon;
import com.coupon.msg.CouponSiteMsg;

public interface CouponReport 
{
	String[] get_report_headers();
	ArrayList<Coupon> create_report() throws CouponSiteMsg;
}

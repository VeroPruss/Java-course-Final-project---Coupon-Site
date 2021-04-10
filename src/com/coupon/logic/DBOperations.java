package com.coupon.logic;

import com.coupon.msg.CouponSiteMsg;

public interface DBOperations 
{
	void select_by_id(String id) throws CouponSiteMsg;
	void save_to_DB() throws CouponSiteMsg;
	void update_in_DB() throws CouponSiteMsg;
	void delete_from_DB() throws CouponSiteMsg;
}

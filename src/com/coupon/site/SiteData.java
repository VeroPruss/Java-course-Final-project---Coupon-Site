package com.coupon.site;

import java.util.ArrayList;
import java.util.HashMap;

import com.coupon.logic.Coupon;
import com.coupon.logic.Customer;
import com.coupon.logic.CustomerCoupon;
import com.coupon.msg.CouponSiteMsg;

public class SiteData 
{
	private SiteMode _mode;
	private CouponSiteMsg _msg;
	private Customer _customer;
	private Coupon _coupon;
	private HashMap<String, Customer> _customer_list;
	private HashMap<String, Coupon> _coupon_list;
	private ArrayList<CustomerCoupon> _customer_coupon_list;
	private String[] _customer_list_headers = {"user name", "full name", "password", "isAdmin"};
	private String[] _coupon_list_headers = {"coupon id", "exp_date", "price", "total amount", "available amount",
            "type", "title", "description", "image"};
	private String[] _customer_coupon_list_headers = {"coupon id", "exp_date", "price", "amount", 
            "type", "title", "description", "image"};
	private String[] _report_headers;
	private ArrayList<Coupon> _report_data;

	public SiteData()
	{
		
	}
	
	public void reset()
	{
		this._mode = SiteMode.home;
		this._msg = null;
		this._customer = null;
		this._coupon = null;
		this._customer_list = null;
		this._coupon_list = null;
		this._customer_coupon_list = null;
	}

	public SiteMode get_mode() 
	{
		return _mode;
	}

	public void set_mode(SiteMode _mode) 
	{
		this._mode = _mode;
	}

	public CouponSiteMsg get_msg() 
	{
		return _msg;
	}

	public void set_msg(CouponSiteMsg _msg) 
	{
		this._msg = _msg;
	}

	public Customer get_customer() 
	{
		return _customer;
	}

	public void set_customer(Customer _customer) 
	{
		this._customer = _customer;
	}
	
	public Coupon get_coupon() 
	{
		return _coupon;
	}

	public void set_coupon(Coupon _coupon) 
	{
		this._coupon = _coupon;
	}

	public HashMap<String, Customer> get_customer_list() 
	{
		return _customer_list;
	}

	public void set_customer_list(HashMap<String, Customer> _customer_list) 
	{
		this._customer_list = _customer_list;
	}

	public HashMap<String, Coupon> get_coupon_list() 
	{
		return _coupon_list;
	}

	public void set_coupon_list(HashMap<String, Coupon> _coupon_list) 
	{
		this._coupon_list = _coupon_list;
	}

	public ArrayList<CustomerCoupon> get_customer_coupon_list() 
	{
		return _customer_coupon_list;
	}

	public void set_customer_coupon_list(ArrayList<CustomerCoupon> _customer_coupon_list) 
	{
		this._customer_coupon_list = _customer_coupon_list;
	}

	public String[] get_customer_list_headers() 
	{
		return _customer_list_headers;
	}

	public String[] get_coupon_list_headers() 
	{
		return _coupon_list_headers;
	}

	public String[] get_customer_coupon_list_headers() 
	{
		return _customer_coupon_list_headers;
	}

	public String[] get_report_headers() 
	{
		return _report_headers;
	}

	public void set_report_headers(String[] _report_headers) 
	{
		this._report_headers = _report_headers;
	}

	public ArrayList<Coupon> get_report_data() 
	{
		return _report_data;
	}

	public void set_report_data(ArrayList<Coupon> _report_data) 
	{
		this._report_data = _report_data;
	}
	
	
}

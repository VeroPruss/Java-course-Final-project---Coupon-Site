package com.coupon.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.DuplicateCustomerCouponError;
import com.coupon.msg.DuplicateUserNameError;
import com.coupon.msg.GeneralError;
import com.coupon.msg.NoCouponsFoundError;
import com.coupon.msg.NoCustomerCouponFoundError;
import com.coupon.msg.SqlError;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class CustomerCoupon implements DBOperations
{
	private Customer _customer;
	private Coupon _coupon;
	private int _amount;
	
	public CustomerCoupon()
	{
		
	}

	public CustomerCoupon(Customer _customer, Coupon _coupon, int _amount) 
	{
		super();
		this._customer = _customer;
		this._coupon = _coupon;
		this._amount = _amount;
	}
	
	public CustomerCoupon(String user_name, String coupon_id) throws CouponSiteMsg
	{
		try
		{
			this._customer = new Customer(user_name);
			this._coupon = new Coupon(coupon_id);
			select_by_id(user_name, coupon_id);
		}
		catch (CouponSiteMsg err)
		{
			throw err;
		}
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

	public int get_amount() 
	{
		return _amount;
	}

	public void set_amount(int _amount) 
	{
		this._amount = _amount;
	}
	
	@Override
	public void save_to_DB() throws CouponSiteMsg 
	{
		String query = String.format("INSERT INTO coupon_site_2.customer_coupon VALUES ('%s', '%s', '%d')",
				this._customer.get_user_name(), this._coupon.get_coupon_id(), this._amount);
				
		System.out.println(query);

		try
		{
			SQLHandler.execute(query);
		}
		catch (MySQLIntegrityConstraintViolationException e)
		{
			throw new DuplicateCustomerCouponError();
		}
		catch (SQLException err)
		{
			throw new SqlError();
		}
		catch (Exception e)
		{
			throw new GeneralError();
		}
	}

	@Override
	public void update_in_DB() throws CouponSiteMsg 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete_from_DB() throws CouponSiteMsg
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void select_by_id(String id) throws CouponSiteMsg 
	{
		// no implementation, two ids required. See next method
	}
	
	public void select_by_id(String user_name, String coupon_id) throws CouponSiteMsg
	{
		String query = String.format("SELECT amount FROM coupon_site_2.customer_coupon "
				+ "WHERE user_name = '%s' AND coupon_id ='%s'", user_name, coupon_id);
		System.out.println(query);
	
		try
		{
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			if (res.next())
			{
				this._amount = res.getInt(1);
			}
			else
			{
				throw new NoCustomerCouponFoundError();
			}
		}
		catch (SQLException e)
		{
			throw new SqlError();
		}
		catch (Exception e)
		{
			throw new GeneralError();
		}
		finally 
		{
			try 
			{
				SQLHandler.closeConnetion();
			} 
			catch (SQLException e) 
			{
				throw new SqlError();
			}
		}	
	}
	
	public ArrayList<CustomerCoupon> coupons_for_customer(String user_name) throws CouponSiteMsg
	{
		ArrayList<CustomerCoupon> coupons_for_customer = new ArrayList<CustomerCoupon>();
		
		String query = String.format("SELECT T2.coupon_id "
				+ "FROM coupon_site_2.customer_coupon T1 JOIN coupon_site_2.coupons T2 "
				+ "ON T1.coupon_id = T2.coupon_id WHERE T1.user_name = '%s' ORDER BY coupon_id", user_name);
		System.out.println(query);
		
		try
		{	
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			
			// coupons found for user - save them to list
			if (res.next())
			{
				do
				{
					coupons_for_customer.add(new CustomerCoupon(user_name, res.getString(1)));
				}
				while (res.next());
			}
			// no coupons found for user
			else
			{
				throw new NoCouponsFoundError();
			}
		}
		catch (CouponSiteMsg msg)
		{
			throw msg;
		}
		catch (SQLException e)
		{
			throw new SqlError();
		}
		catch (Exception e)
		{
			throw new GeneralError();
		}
		finally
		{
			try 
			{
				SQLHandler.closeConnetion();
			} 
			catch (SQLException e) 
			{
				throw new SqlError();
			}
		}
		
		return coupons_for_customer;
	}
	
	public boolean is_coupon_purchased(String coupon_id) throws CouponSiteMsg
	{
		boolean result = false;
		
		String query = String.format("SELECT T2.coupon_id "
				+ "FROM coupon_site_2.customer_coupon T1 JOIN coupon_site_2.coupons T2 "
				+ "ON T1.coupon_id = T2.coupon_id WHERE T2.coupon_id = '%s'", coupon_id);
		System.out.println(query);
		
		try
		{	
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			
			// found a user who purchased the coupon
			if (res.next())
			{
				result = true;
			}
		}
		catch (SQLException e)
		{
			throw new SqlError();
		}
		catch (Exception e)
		{
			throw new GeneralError();
		}
		finally
		{
			try 
			{
				SQLHandler.closeConnetion();
			} 
			catch (SQLException e) 
			{
				throw new SqlError();
			}
		}
		
		return result;
	}
	
	public CustomerCoupon purchase_coupon(String user_name, String coupon_id, int amount) throws CouponSiteMsg
	{
		CustomerCoupon cust_coup = null;
		
		try
		{
			System.out.println("here1");
			Customer customer = new Customer(user_name);
			System.out.println("here2");
			Coupon coupon = new Coupon(coupon_id);
			coupon.check_purchase(amount);
			cust_coup = new CustomerCoupon(customer, coupon, amount);
			cust_coup.save_to_DB();
			coupon.purchase(amount);
			
		}
		catch (CouponSiteMsg msg)
		{
			throw msg;
		}
		
		return cust_coup;
	}
}

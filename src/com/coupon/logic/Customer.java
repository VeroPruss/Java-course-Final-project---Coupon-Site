package com.coupon.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.CustomerNotFoundError;
import com.coupon.msg.DuplicateUserNameError;
import com.coupon.msg.EmptyFormInfo;
import com.coupon.msg.EmptyFullNameError;
import com.coupon.msg.EmptyPasswordError;
import com.coupon.msg.EmptyUserNameError;
import com.coupon.msg.GeneralError;
import com.coupon.msg.LoginError;
import com.coupon.msg.NoCustomersFoundError;
import com.coupon.msg.SqlError;
import com.coupon.logic.SQLHandler;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Customer implements DBOperations
{
	private String _user_name;
	private String _full_name;
	private String _password;
	private boolean _isAdmin;
	
	public Customer() 
	{
		
	}
	
	public Customer(String _user_name, String _full_name, String _password, boolean _isAdmin) 
	{
		super();
		this._user_name = _user_name;
		this._full_name = _full_name;
		this._password = _password;
		this._isAdmin = _isAdmin;
	}
	
	public Customer(String id) throws CouponSiteMsg
	{
		select_by_id(id);
	}

	public String get_user_name() 
	{
		return _user_name;
	}

	public void set_user_name(String _user_name) 
	{
		this._user_name = _user_name;
	}

	public String get_full_name() 
	{
		return _full_name;
	}

	public void set_full_name(String _full_name) 
	{
		this._full_name = _full_name;
	}

	public String get_password() 
	{
		return _password;
	}

	public void set_password(String _password) 
	{
		this._password = _password;
	}

	public boolean isAdmin() 
	{
		return _isAdmin;
	}

	public void set_Admin(boolean _isAdmin) 
	{
		this._isAdmin = _isAdmin;
	}

	@Override
	public String toString() 
	{
		return "Customer [_user_name=" + _user_name + ", _full_name=" + _full_name + ", _password=" + _password
				+ ", _isAdmin=" + _isAdmin + "]";
	}
	
	@Override
	public void save_to_DB() throws CouponSiteMsg 
	{
		String query = String.format("INSERT INTO coupon_site_2.customers VALUES ('%s','%s','%s',%b)", 
				this._user_name, this._full_name, this._password, this._isAdmin);
		System.out.println(query);

		try
		{
			SQLHandler.execute(query);
		}
		catch (MySQLIntegrityConstraintViolationException e)
		{
			throw new DuplicateUserNameError();
		}
		catch (SQLException se) 
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
		String query = String.format("UPDATE coupon_site_2.customers "
				+ "SET full_name= '%s', password = '%s', isAdmin = %b "
				+ "WHERE user_name = '%s'", this._full_name, this._password, this._isAdmin, this._user_name);
		System.out.println(query);

		try
		{
			SQLHandler.execute(query);
		}
		catch (SQLException e)
		{
			throw new SqlError();
		}
		catch (Exception e)
		{
			throw new GeneralError();
		}
	}

	@Override
	public void delete_from_DB() throws CouponSiteMsg 
	{
		String query = String.format("DELETE FROM coupon_site_2.customers WHERE user_name = '%s'", 
				this._user_name);
		System.out.println(query);

		try
		{
			SQLHandler.execute(query);
		}
		catch (SQLException e)
		{
			throw new SqlError();
		}
		catch (Exception e)
		{
			throw new GeneralError();
		}
	}

	@Override
	public void select_by_id(String id) throws CouponSiteMsg 
	{
		String query = String.format("SELECT full_name, password, isAdmin "
				+ "FROM coupon_site_2.customers WHERE user_name ='%s'", id);
		System.out.println(query);
		
		try
		{
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			if (res.next())
			{
				this._user_name = id;
				this._full_name = res.getString(1);
				this._password = res.getString(2);
				this._isAdmin = res.getBoolean(3);
			}
			else
			{
				throw new CustomerNotFoundError();
			}
		}
		catch (SQLException err)
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
	
	public boolean determine_user_type(String user_type)
	{
		if (user_type!= null && user_type.equals("admin"))
		{
			return true;
		}
		
		return false;
	}
	
	public Customer register(String user_name, String full_name, String password, String user_type) throws CouponSiteMsg
	{
		Customer customer = null;
		
		try
		{
			validate_register_data(user_name, full_name, password);
			customer = new Customer(user_name, full_name, password, determine_user_type(user_type));
			customer.save_to_DB();
		}
		catch (CouponSiteMsg msg)
		{
			throw msg;
		}
		
		return customer;
	}
	
	// Check data for login
	private void validate_register_data(String user_name, String full_name, String password) throws CouponSiteMsg
	{	
		if (user_name == null && full_name == null && password == null)
		{
			throw new EmptyFormInfo();
		}
		
		if (user_name.isEmpty())
		{
			throw new EmptyUserNameError();
		}
		
		if (full_name.isEmpty())
		{
			throw new EmptyFullNameError();
		}
		
		if (password.isEmpty())
		{
			throw new EmptyPasswordError();
		}
	}
	
	public Customer login(String user_name, String password) throws CouponSiteMsg
	{
		Customer customer = null;

		try
		{	
			// check data for login
			validate_login_data(user_name, password);
			
			try
			{
				// prepare login statement with user_name and password
				String query = String.format("SELECT full_name, isAdmin FROM coupon_site_2.customers "
						+ "WHERE user_name = '%s' AND password = '%s'", user_name, password);
				
				System.out.println(query);
			
				SQLHandler.connect2DB();
				ResultSet res = SQLHandler.run_query(query);
				if (res.next())
				{
					customer = new Customer(user_name, res.getString(1), password, res.getBoolean(2));
				}
				else
				{
					throw new LoginError();
				}
			}
			catch (CouponSiteMsg msg)
			{
				throw msg;
			}
			catch (SQLException se) 
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
				catch (SQLException se) 
				{
					System.out.println("here4");
					throw new SqlError();
				}
			}
		}
		catch (CouponSiteMsg msg)
		{
			throw msg;
		}

		return customer;
	}
	
	// Check data for login
	private void validate_login_data(String user_name, String password) throws CouponSiteMsg
	{	
		if (user_name == null && password == null)
		{
			throw new EmptyFormInfo();
		}
		
		if (user_name.isEmpty())
		{
			throw new EmptyUserNameError();
		}
		
		if (password.isEmpty())
		{
			throw new EmptyPasswordError();
		}
	}
	
	public Customer update_customer(String user_name, String full_name, String password, String user_type) throws CouponSiteMsg
	{
		Customer customer = null;
		
		try
		{
			validate_register_data(user_name, full_name, password);
			customer = new Customer(user_name);
			customer.set_full_name(full_name);
			customer.set_password(password);
			customer.set_Admin(determine_user_type(user_type));
			customer.update_in_DB();
		}
		catch (CouponSiteMsg msg)
		{
			throw msg;
		}
		
		return customer;
	}
	
	public HashMap<String, Customer> get_all_customers() throws CouponSiteMsg
	{
		HashMap<String, Customer> customers = new HashMap<String, Customer>();
		
		String query = "SELECT user_name, full_name, password, isAdmin "
				+ "FROM coupon_site_2.customers ORDER BY user_name";
		System.out.println(query);
		
		try
		{	
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			
			// customers found
			if (res.next())
			{
				do
				{
					customers.put(res.getString(1), 
							new Customer(res.getString(1), res.getString(2), res.getString(3), res.getBoolean(4)));
				}
				while (res.next());
			}
			// no customers
			else
			{
				throw new NoCustomersFoundError();
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
		
		return customers;
	}
}

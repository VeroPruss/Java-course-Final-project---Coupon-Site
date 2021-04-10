package com.coupon.reports;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coupon.logic.Coupon;
import com.coupon.logic.SQLHandler;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.GeneralError;
import com.coupon.msg.NoCouponsFoundError;
import com.coupon.msg.SqlError;

public class CouponSalesReport implements CouponReport 
{
	private String[] _report_headers = {"coupon id", "amount of customers"};
	
	public CouponSalesReport() 
	{
		super();
	}

	@Override
	public String[] get_report_headers() 
	{
		return this._report_headers;
	}


	@Override
	public ArrayList<Coupon> create_report() throws CouponSiteMsg
	{
		java.sql.Date now = new Date(System.currentTimeMillis());
		
		ArrayList<Coupon> coupon_sales = new ArrayList<Coupon>();
		
		String query = "SELECT coupon_id, count(*) AS cnt "
				+ "FROM coupon_site_2.customer_coupon "
				+ "GROUP BY coupon_id "
				+ "ORDER BY cnt DESC, coupon_id ASC";
		
		try
		{	
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			
			// coupons found
			if (res.next())
			{
				do
				{
					coupon_sales.add( 
							new Coupon(res.getString(1), now, 0, res.getInt(2), 0, 
									"", "", "", ""));
				}
				while (res.next());
			}
			// no coupons
			else
			{
				throw new NoCouponsFoundError();
			}
		}
		catch (CouponSiteMsg err)
		{
			throw err;
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
			catch (SQLException e) 
			{
				throw new SqlError();
			}
		}
		
		return coupon_sales;
	}
}

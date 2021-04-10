package com.coupon.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coupon.logic.Coupon;
import com.coupon.logic.SQLHandler;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.GeneralError;
import com.coupon.msg.NoCouponsFoundError;
import com.coupon.msg.SqlError;

public class ExpiredCouponsReport implements CouponReport 
{
	private String[] _report_headers = {"coupon id", "exp_date", "price", "total amount", "available amount",
            "type", "title", "description", "image"};
	
	public ExpiredCouponsReport() 
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
		ArrayList<Coupon> expired_coupons = new ArrayList<Coupon>();
		
		String query = "SELECT coupon_id, exp_date, price, total_amount, available_amount, type, title, "
				+ "coupon_desc, image_url FROM coupon_site_2.coupons "
				+ "WHERE available_amount > 0 AND exp_date < CURRENT_DATE()"
				+ "ORDER BY exp_date DESC";
		
		try
		{	
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			
			// coupons found
			if (res.next())
			{
				do
				{
					expired_coupons.add( 
							new Coupon(res.getString(1), res.getDate(2), res.getFloat(3), res.getInt(4), res.getInt(5), 
									res.getString(6), res.getString(7), res.getString(8), res.getString(9)));
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
		
		return expired_coupons;
	}
}

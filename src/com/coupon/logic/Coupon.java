package com.coupon.logic;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.coupon.msg.AmountDiscrepancyError;
import com.coupon.msg.AvaiableAmountGTTotalAmountError;
import com.coupon.msg.CouponNotFoundError;
import com.coupon.msg.CouponPurchasedError;
import com.coupon.msg.CouponSiteMsg;
import com.coupon.msg.DuplicateCouponError;
import com.coupon.msg.EmptyCouponIdError;
import com.coupon.msg.EmptyCouponTitleError;
import com.coupon.msg.EmptyCouponTypeError;
import com.coupon.msg.EmptyFormInfo;
import com.coupon.msg.ExpirationDateDecreaseError;
import com.coupon.msg.ExpirationDateError;
import com.coupon.msg.GeneralError;
import com.coupon.msg.NoCouponsFoundError;
import com.coupon.msg.PurchaseAmountError;
import com.coupon.msg.SqlError;
import com.coupon.msg.ZeroAvailableAmountError;
import com.coupon.msg.ZeroPriceError;
import com.coupon.msg.ZeroTotalAmountError;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Coupon implements DBOperations
{
	private final String DEFAULT_IMG_URL = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOAAAADgCAMAAAAt85rTAAAAY1BMVEX////e3t57e3uvr69+fn54eHisrKzQ0NC8vLy5ubnAwMDOzs62traxsbF3d3fS0tLFxcXk5OSLi4vw8PD09PTZ2dns7OyFhYXj4+P5+fmenp5xcXGKioqXl5eRkZGnp6dra2sH7R5VAAAOpUlEQVR4nO2da3uqvBKGISYooBwDAUR8//+v3DM5ETzVrl0t9srzYS2rYHMzk5kkE2gQeHl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl9gngL4v1vN+Nl4k0Oapow7Nr6L3K2uSsALf+YObv8UtstgIYt57z/A6R9eAU4c6I5wW8/2qKqC96FlJgf3T/rR3zWlui1CNrVH0fZyuY/qxSVh2X9Ob2z/Q6flLwiad7+dsuflAXMc7TO85wN/+2mP6fS8LU9523X5MoNLekC2f0h/wzAPlQ8qWlvz+u6DUMA1aT31HxGL+T5rfb2gNm2ZQecd0HDDwE0zQ9vfSrNCaRhnl8Blu9u6r+Jm/Z2j46C7omgYNAZ8EOiaP0dg6DjNp8GWHzT40oDWL+0XT+m8rsGCYtl1F27GtXeYvukQXp9Qvoheb7PC1BaFM8apJYngD4kDfZgPNXeJ08o9fHFzbSyPvHim+3tzAkfE0S1HqZBR4054UOCaGva+2yWMIDpZ3RBCPrf9DgTY9KXNuvnZNv7ZBDt0w8DNO3Nn/S42p7w2nb9lHrjoU9kib4FK5ffjbq/LBNjkq+7IAwJwrnPPnHC76rvW2ximWi1i8+uj+d5kSQAmJsT3tXQf1Hf1zBXDzExhKa9Tlarm+0lIQ8BL8FcuX7AnuNKBAoBG9PeOYiWaZJcjDPLLfJJwFQfX7y52U+Kt4YOhF5pDFIYQN5IFndc0yo8OKhdNSB32Axgb9qrxyV957Ao1dYpExidcf15sv01jjvq2yUdqMaRqG6vyhL11sLoxF83ReK+Z4PS6rJEe4kXdjKrqebupEu26Qyzw27Yw+c75y0nKK0uS9wABILOtL3FIowDk+x2IS8X7yS78GZQWomuAdEG4U4JDNKn5rX5vzCvtGIIu7k+4FMAGwNT25cPBGfksXpZrA6Q34ox0iCyvXWQ3uVyAVMNuL7ZYF/eAOSGCuJjYRzxNly8i8FqvNAfPzv5eKOufJRjloilsL1J/JVgANfKo4B2dVniGhCDaLvTgJAAdl8CNkHfmBNWWHi57IQlAJa66ZgGv+Tbc5gx6dfrS4Ogi04oJ0t7lDQI3z9WvEfAVP+0viAaXPkoBtFOtxcM0n4BCIgQYyzg+mIMRJQbWcK0F34IYc6wewS4wyCqX69yxekiUdS4ION6HNb+yq7J0wJC5Q1AsFqd6NfrXHFa+igwcdPe1NnW06s5Y7NNdvH+cDhYQMgSoTHxs4WM94ovsgSmQWOoXYG7l5aRsVc1622hOA8I1egT4mfX+d+rRaKQWUJa6HDQdophSlukTbcsOsA8OZR+iwM1dfBht8YsESx9FAEbTbcQku6KIm9wO9ocLDnHPquPWeuKkxtH0QbbazyHE+ZL6LhdO3fPZOWA/AKw2DwgNJxxAtZsmg6XtWP97gpXnKTcRIEdLXnINguvQ7xL0sJ49CrTIMrphNil9puvTWghUeaHdWaJwO2EmCVa1erv67DSIOomCgyi3eF5A2orao9d41Bbau6ECJj/owE3h9/muC/ro+hk6eYffXT/2xj3ZRMFBtEi+h5XtFEnRLvfxrivvnMAk8i22SBETzBH0WqzRDAnCgwTRYRCSKP51UOtNksEs4/i8Kvdn1HPQTnarDZLBLgFXaVBNb7kOB1K9ocnLae13iwR2ETRzdMEmOB2cj4U75/k3K9xQcaqnoPMQj2uV3RhmgDmY789r3LFyUp3QryX7Jan4ebzuu2K3T6Kzre76HnNQdRJFADZdWV576a5vi7Bb5NYGdRywv/nNQdRLGWHF5KYyHkLFMJQvk2LxHAi6AqX7V3Vl4AWswXM2/aEQBTCpBc76DlaX+VsqetC4YKzfZADcKGt69acJFDXhUIHr6zX3vwndMdHoSP+AThUf8ty7efdmntf13Qfenv1PS06YfmXTKfFHdP9dlteoxLT3p/qdRfq6zvjFi8vLy8vLy8vLy8vLy8vLy8vLy8vL6+3q0hTe+dUnrqPwSmbNM2v67M9vK23FdSLRxy2+FXz+fIhj5fPa+w7fE5e3uiN69x5zOOrKvn/Zdkx1q+n7Gi3jzebSZCMTsPlJpD6lGWDPiYDVeaD+AhfNe+vbwh8OCz21/TFMAmWZURM6leGIjNir9qZf6SUCn31TiQzOLEgjGWMEUKH5Qn1QIgBhHMpMUYbCaXM7n3t9wQ/de0SnighBL4S/jmrdyqKb8l3X7UzHwGJ+nUAyDRglFEybtJ0A61mp4WbOoC5BNS7QRWtBeQnQuGCOc3OKYEvPW0Oh/NJpAaQnPStNK+6CfZIBSWi0YDagumRskHeb1QO0KqD25McwITQaiQn9UMMjucAdoRMCTMfIgxegIO+iUnf9wSA7NX3VBzpdKZkkEYygPUErdPOVU6ECvfqOoAxIdFAKtlofmKn2H4C8BmJuowy0wnRolcw4UheD0imEIwkwQxgQp224A9uAHAAd9C8DaPy0EaQKM/IZA7D7+oFtZ16y6CDXtYYATB7vQVpvWeU8sAC9ufZgGDC0RhYyQE8A2ABPRjbDd+RA6BxyfpISQ0H2DdOhFRXG2Pf46JZy8FIUWCDTAuNOc9I4Fuj0zQHcADAUJCpk+dQns4WhO4HV6WAK6d8tAWvj65yalcRtuM16mXVfwQM4ozS1loQr6tzq0NEFtH+AhCzQ44hBN5MGal0n4NrBdG1FFQ5cNCM8g0QL6XkcWEFvjJIRS8FLOEXnS1gIyhzbnU4EJrdBOzBalv0030PMQU6U5pRDdgKQht1rEpBqTDO2FQTqCoUoM2DU/AiSUAI+JgqbgPu7wHWJyJyoAJn7OHNPtgSY8EU8hu69Y6RqVVvGMA8YxDUMhm3wvFdgNBWGFwMCrCcKNvMR0AmrG72QQXYZ9iLGY5NIJKOKqNEEF3wybcRDJPk05zwoikXbU7DSejADC7KXr0fWAHiFRbhXgFikJkHkRzCw8l9ipoFhMABgMFEMoifeCYAqpSJyZPAoDOzozd4Z47F4Nsz4DuiqP6d+0jnQQgrwu5DztFhnQQ2A0IMrEIZMA8bJloHEM7BoXZ2BELlq+gH1IwX6l8ADMIjHbWLBgX0jI1m6jGIuvf5O4ATw3RZQnKopHmsi0LXq0K867yODNeOUDvM+RXAIGLUDjzgcmd69JIwncmvASH0IyBH48jJAHpmoQDM+D2kZgADcZXp7Mot4PieRC9ftDjd0YAdtOa4Kdu2gxxBqsVcdAaEUImAPQxipK/KbI/ttWM/+FBAD5ZYDRJWRdfWbTM5FtyrvNi96v4fC4hzAzt0zAU0WpxO8B8bl7cFzIBbofoXNF0FEAMIVreD7DNgqQuUjvCdGZVB1EZRiG2jlDi/aCxzpEcNiMHTjo3DU6Zmpmy4WEtYWFCG13ZiJDHfAC96nIyYw3PIInrFoDxAWJXfyfTSQYheoxPh8CJAJuyUvBCCzlkpPJyoGJKrpZJ6oFR1MJhnyGzSJ8O5VICUwgihZoLO90RSISwtLw6DEMPZPMOqm4QRfZUFvby8vLy8vLw+Vp3zGPrO/SsSLbyvJ6jyT2LMAyn8+wOhfpFcPU+Mp4u/X8PDXP4Bh3wupIXwze4Z9k90vOTPppwzxkzNKyVzJS0oWEb0s7FFxjI2z3m7Sh8Wjm65zDSfsiyblzgaGG0SLJhVg5mU7I/sP3eC2RBog5T9jT8nOQk0q8/cWTeBibxeD4MZO5aN5nVLmNaq9aOuotdVvZhhkcUWM7DmpKYLRK+Q4hHHBaBzyI8DbgXWh4T2DGeJFxeJ9MoazO4m4aw7OYDkClBXzezbDU792rbZC2pqOFeAgpKzfCDpC56zChP2zWCvbc6oqYXk80rMiYhYrZ8pPQRswfAFIydDAICZ9M2U0EzNoW4AZq+61V6ubB6YWUDpmamF4jKEUN5ajmQKYZJre+dDwISRCHyamWhhAQPr5rcAX/X0ykaQihfU9Da5tidf8MouGxWUDf3BWSl9CDipqpldSnIB9WryOwF3jAxy3UBP5NNMNyc0L4L+zMB4WH42geMRID9iFSealywsYE6IjsRvdFFcnyzkmq8uTdbGjw5MmxJxoPtxYdcRHwJi96tlsVP7BALmfV83ky3J3QBkRV23L6ihlfDba6yuGB/F0ueIL4TNC2C7iqPzkqH/GnAgLJbJx2yaAEAyRNEgCI00860oqmpopzj4WUFEEIGMmET7H/goJop29tADk2tozkrgA8BWFQv5cqcJkbtRRvNYmRuAVO0uyS52rPzfwnoE/j/vb+ngaibS0yaFUwMNZl+e2cDxANBUzeBy6NViBJSLZhC+hvYOIKFmZe1n+ThkhQj/aFcFbqj3Vp11JdT0yhCOiXGUKOyA5wHgBveOwMERtZVd6AXoHeGBmg037wsyWEHHVVgcXB31GCLB/qg9DYXRBkeJeEzWfwGIK9tyWZfYnDCnCcisKhu8L01A1mPZ8XjMoOOx2Pw2Iprc1LyCoHKP2X4BiL0pk0fbqtkMWGd6FPE2QI6Fv5qDWmjYqN/EzLEjTMfQFrseHsJDYUY59wGh640dHtzvweDNErA/aid/G2Axj/rBEU0chZwxzCNPs4NG9U4VOBaAbvHLrZoJwmSFcQaMzUAXAd12vKwP4i4d7YgwyibaFmpPndkNMs3pLyG63O5Ol8hBPy8Iv6gDdzZVs0lXzVSi57zEoqqoDal59EevAFnifM1PCSICM2Vc3Kajc1AvyDz7K50BTAPZW5fbqQWkpnaCn4CHHs2AFXyChQqQnM7nAfoyUddHzhhNxUVV37CGpr/mBx8ogcNLU0viETP7IwIYexLTJ3B3iVNYVxYHC2bGRY1kn530MEheDhgqIHRDZGCFOXs2aT+MMzO/JbJy19gfSRb9IGAixpOdX6ZjVWnaRlSV0G9HYjzbie5urCakLYdRSLOWJzhLaYQBA4e581w1G6sRQ0oYnadqrIbNvNySCHNWJWfa4VTNX+MfCeLl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eX1w/ofLWA8kmdzDb0AAAAASUVORK5CYII=";
	
	private String _coupon_id;
	private Date _exp_date;
	private float _price;
	private int _total_amount;
	private int _available_amount;
	private String _type;
	private String _title;
	private String _coupon_desc;
	private String _img_url;
	
	public Coupon()
	{
		
	}
	
	public Coupon(String _coupon_id, Date _exp_date, float _price, int _total_amount, int _available_amount,
			String _type, String _title, String _coupon_desc, String _img_url) 
	{
		super();
		this._coupon_id = _coupon_id;
		this._exp_date = _exp_date;
		this._price = _price;
		this._total_amount = _total_amount;
		this._available_amount = _available_amount;
		this._type = _type;
		this._title = _title;
		this._coupon_desc = (_coupon_desc == null) ? " " : _coupon_desc;
		this._img_url = (_img_url.isEmpty()) ? DEFAULT_IMG_URL : _img_url;
	}
	
	public Coupon(String coupon_id) throws CouponSiteMsg
	{
		select_by_id(coupon_id);
	}

	public String get_coupon_id() 
	{
		return _coupon_id;
	}

	public void set_coupon_id(String _coupon_id) 
	{
		this._coupon_id = _coupon_id;
	}

	public Date get_exp_date() {
		return _exp_date;
	}

	public void set_exp_date(Date _exp_date) 
	{
		this._exp_date = _exp_date;
	}

	public float get_price() 
	{
		return _price;
	}

	public void set_price(float _price) 
	{
		this._price = _price;
	}

	public int get_total_amount() 
	{
		return _total_amount;
	}

	public void set_total_amount(int _total_amount) 
	{
		this._total_amount = _total_amount;
	}

	public int get_available_amount() 
	{
		return _available_amount;
	}

	public void set_available_amount(int _available_amount) 
	{
		this._available_amount = _available_amount;
	}

	public String get_type() 
	{
		return _type;
	}

	public void set_type(String _type) 
	{
		this._type = _type;
	}

	public String get_title() 
	{
		return _title;
	}

	public void set_title(String _title) 
	{
		this._title = _title;
	}

	public String get_coupon_desc() 
	{
		return _coupon_desc;
	}

	public void set_coupon_desc(String _coupon_desc) 
	{
		this._coupon_desc = _coupon_desc;
	}

	public String get_img_url() 
	{
		return _img_url;
	}

	public void set_img_url(String _img_url) 
	{
		this._img_url = _img_url;
	}

	@Override
	public String toString() 
	{
		return "Coupon [_coupon_id=" + _coupon_id + ", _exp_date=" + _exp_date + ", _price=" + _price
				+ ", _total_amount=" + _total_amount + ", _available_amount=" + _available_amount + ", _type=" + _type
				+ ", _title=" + _title + ", _coupon_desc=" + _coupon_desc + ", _img_url=" + _img_url + "]";
	}
	
	@Override
	public void save_to_DB() throws CouponSiteMsg 
	{
		String query = String.format("INSERT INTO coupon_site_2.coupons "
				+ "VALUES ('%s', '%s', '%f', '%d', '%d', '%s', '%s', '%s', '%s')",
				this._coupon_id, this._exp_date, this._price, this._total_amount, this._available_amount,
				this._type, this._title, this._coupon_desc, this._img_url);
				
		System.out.println(query);
		
		try
		{
			SQLHandler.execute(query);
		}
		catch (MySQLIntegrityConstraintViolationException e)
		{
			throw new DuplicateCouponError();
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
		String query = String.format("UPDATE coupon_site_2.coupons "
				+ "SET exp_date = '%s', price = '%f', total_amount = '%d', "
				+ "available_amount = '%d', type = '%s', title = '%s', coupon_desc = '%s', "
				+ "image_url = '%s' "
				+ "WHERE coupon_id = '%s'", this._exp_date, this._price, this._total_amount,
				                            this._available_amount, this._type, this._title,
				                            this._coupon_desc, this._img_url, this._coupon_id);
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
		// check that no customer has already bought this coupon
		
		String query = String.format("DELETE FROM coupon_site_2.coupons WHERE coupon_id = '%s'", 
				this._coupon_id);
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
		String query = String.format("SELECT exp_date, price, total_amount, available_amount, title, "
				+ "coupon_desc, type, image_url FROM coupon_site_2.coupons WHERE coupon_id ='%s'", id);
		System.out.println(query);
		
		try
		{
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			if (res.next())
			{
				this._coupon_id = id;
				this._exp_date = res.getDate(1);
				this._price = res.getFloat(2);
				this._total_amount = res.getInt(3);
				this._available_amount = res.getInt(4);
				this._title = res.getString(5);
				this._coupon_desc = (res.getString(6) == null) ? " " : res.getString(6);
				this._type = res.getString(7);
				this._img_url = (res.getString(8).isEmpty()) ? DEFAULT_IMG_URL : res.getString(8);
			}
			else
			{
				throw new CouponNotFoundError();
			}
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
	}
	
	// Creates new coupon
	public Coupon add_coupon(String coupon_id, Date exp_date, float price, int total_amount,
							 int available_amount, String type, String title, String desc, 
							 String image_url) throws CouponSiteMsg
	{
		Coupon coupon = null;
		
		try
		{
			validate_coupon_data(coupon_id, exp_date, price, total_amount, available_amount, type, title, desc, image_url);
			coupon = new Coupon(coupon_id, exp_date, price, total_amount, available_amount, type, title, desc, image_url);
			coupon.save_to_DB();
		}
		catch (CouponSiteMsg msg)
		{
			throw msg;
		}
		
		return coupon;
	}
	
	// Validates data entered for coupon
	private void validate_coupon_data(String coupon_id, Date exp_date, float price, int total_amount,
			 int available_amount, String type, String title, String desc, 
			 String image_url) throws CouponSiteMsg
	{
		if (coupon_id == null && exp_date == null && price == 0 && total_amount == 0 && available_amount == 0 &&
			type == null && title == null && desc == null && image_url == null)
		{
			throw new EmptyFormInfo();
		}
		
		if (coupon_id.isEmpty())
		{
			throw new EmptyCouponIdError();
		}
		
		java.sql.Date two_weeks_forward = new Date(System.currentTimeMillis() + 14*24*60*60*1000);
		
		if (exp_date.before(two_weeks_forward))
		{
			throw new ExpirationDateError();
		}
		
		if (price == 0)
		{
			throw new ZeroPriceError();
		}
		
		if (total_amount == 0)
		{
			throw new ZeroTotalAmountError();
		}
		
		if (available_amount == 0)
		{
			throw new ZeroAvailableAmountError();
		}
		
		if (available_amount > total_amount)
		{
			throw new AvaiableAmountGTTotalAmountError();
		}
		
		if (type.isEmpty())
		{
			throw new EmptyCouponTypeError();
		}
		
		if (title.isEmpty())
		{
			throw new EmptyCouponTitleError();
		}
	}
	
	// Updates coupon
	public Coupon update_coupon(String coupon_id, Date exp_date, float price, int total_amount,
			 int available_amount, String type, String title, String desc, 
			 String image_url) throws CouponSiteMsg
	{
		Coupon new_coupon = null;
		
		try
		{
			// Get old coupon data
			Coupon old_coupon = new Coupon(coupon_id);
			
			// Create new coupon using the data received
			validate_coupon_data(coupon_id, exp_date, price, total_amount, available_amount, type, title, desc, image_url);
			new_coupon = new Coupon(coupon_id, exp_date, price, total_amount, available_amount, type, title, desc, image_url);
			
			// Validate updated data
			validate_update_coupon(old_coupon, new_coupon);
			
			// Update new data
			new_coupon.update_in_DB();
		}
		catch (CouponSiteMsg msg)
		{
			throw msg;
		}
		
		return new_coupon;
	}
	
	// Validates update of coupon
	private void validate_update_coupon(Coupon old_coupon, Coupon new_coupon) throws CouponSiteMsg
	{
		// New expiration date cannot be decreased
		if (new_coupon.get_exp_date().before(old_coupon.get_exp_date()))
		{
			throw new ExpirationDateDecreaseError();
		}
		
		// Discrepancy in amounts
		if (new_coupon.get_available_amount() > old_coupon.get_available_amount())
		{
			int available_diff = new_coupon.get_available_amount() - old_coupon.get_available_amount();
			int total_diff = new_coupon.get_total_amount() - old_coupon.get_total_amount();
			if (total_diff != available_diff)
			{
				throw new AmountDiscrepancyError();
			}
		}
	}
	
	// Deletes coupon 
	public void delete_coupon(String coupon_id) throws CouponSiteMsg
	{
		try
		{
			Coupon coupon = new Coupon(coupon_id);
			validate_delete_coupon(coupon_id);
			coupon.delete_from_DB();
		}
		catch (CouponSiteMsg msg)
		{
			throw msg;
		}
	}
	
	// Checks if the coupon might be deleted
	private void validate_delete_coupon(String coupon_id) throws CouponSiteMsg
	{
		if (new CustomerCoupon().is_coupon_purchased(coupon_id))
		{
			throw new CouponPurchasedError();
		}
	}
	
	// Returns all coupons suitable for purchase
	public HashMap<String, Coupon> get_all_coupons() throws CouponSiteMsg
	{
		HashMap<String, Coupon> coupons = new HashMap<String, Coupon>();
		
		String query = "SELECT coupon_id, exp_date, price, total_amount, available_amount, type, title, "
				+ "coupon_desc, image_url FROM coupon_site_2.coupons "
				+ "WHERE available_amount > 0 AND exp_date >= CURRENT_DATE()"
				+ "ORDER BY coupon_id ASC";
		
		try
		{	
			SQLHandler.connect2DB();
			ResultSet res = SQLHandler.run_query(query);
			
			// coupons found
			if (res.next())
			{
				do
				{
					coupons.put(res.getString(1), 
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
		
		return coupons;
	}
	
	// Check if available amount is sufficient for purchase
	public void check_purchase(int amount) throws CouponSiteMsg
	{
		if (this._available_amount < amount)
		{
			throw new PurchaseAmountError();
		}
	}
	
	// Update available amount of coupon
	public void purchase(int amount) throws CouponSiteMsg
	{
		try
		{
			this._available_amount-=amount;
			this.update_in_DB();
		}
		catch (CouponSiteMsg msg) 
		{
			throw msg;
		}
	}
}

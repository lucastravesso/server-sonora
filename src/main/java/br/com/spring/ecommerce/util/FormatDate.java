package br.com.spring.ecommerce.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {

	static public final String convertDateToString(Date date) {
		
		SimpleDateFormat sdt = new SimpleDateFormat();
		sdt.applyPattern("yyyy-MM-dd");
		return sdt.format(date);
	}
	
	static public final Date convertStringToDate(String string) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(string);
		return date;
	}
}

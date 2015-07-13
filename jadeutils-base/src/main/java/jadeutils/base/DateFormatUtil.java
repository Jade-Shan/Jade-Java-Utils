package jadeutils.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

	private ThreadLocal<DateFormat> dateformat;

	DateFormatUtil(final String format) {
		this.dateformat = new ThreadLocal<DateFormat>() {
			@Override
			protected DateFormat initialValue() {
				return new SimpleDateFormat(format);
			}
		};
	}

	public String format(String format, Date date) {
		return this.dateformat.get().format(date);
	}

	public Date convert(String format, String date) throws ParseException {
		return this.dateformat.get().parse(date);
	}

}

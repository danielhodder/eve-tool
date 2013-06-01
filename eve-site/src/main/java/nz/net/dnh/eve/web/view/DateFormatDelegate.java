package nz.net.dnh.eve.web.view;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatDelegate {
	private SimpleDateFormat dateFormat;

	public DateFormatDelegate(SimpleDateFormat simpleDateFormat) {
		this.dateFormat = simpleDateFormat;
		
	}

	public String format(Date date) {
		if (date == null)
			return null;
		else
			return dateFormat.format(date);
	}
}
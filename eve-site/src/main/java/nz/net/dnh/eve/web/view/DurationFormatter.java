package nz.net.dnh.eve.web.view;

import java.util.concurrent.TimeUnit;

public class DurationFormatter {
	public long hoursToDays(long hours) {
		return TimeUnit.HOURS.toDays(hours);
	}
}

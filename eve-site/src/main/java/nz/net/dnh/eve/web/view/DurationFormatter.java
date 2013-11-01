package nz.net.dnh.eve.web.view;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component("durationFormatter")
public class DurationFormatter {
	public long hoursToDays(long hours) {
		return TimeUnit.HOURS.toDays(hours);
	}
}

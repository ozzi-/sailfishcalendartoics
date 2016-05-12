package jollatoics;

import java.text.SimpleDateFormat;
import java.util.Date; 

public class CalendarEntry {

	private String name;
	private Date dateStart;
	private String timeZone;

	public CalendarEntry(String name, int dateStart, String timeZone) {
		this.name = name;
		this.dateStart = new Date((long) dateStart * 1000);
		this.timeZone =timeZone;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getDF(String dF){
		return Integer.valueOf(new SimpleDateFormat(dF).format(this.getDateStart()));
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(int dateStart) {
		this.dateStart = new Date((long) dateStart * 1000);
	}

	@Override
	public String toString() {
		return name + "@" +new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(dateStart);
	}
}

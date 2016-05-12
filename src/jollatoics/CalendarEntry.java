package jollatoics;

import java.text.SimpleDateFormat;
import java.util.Date; 

public class CalendarEntry {

	private String name;
	private Date dateStart;

	public CalendarEntry(String name, int dateStart) {
		this.name = name;
		this.dateStart = new Date((long) dateStart * 1000);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

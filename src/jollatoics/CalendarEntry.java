package jollatoics;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarEntry {

	private String name;
	private Date dateStart;
	private String timeZone;
	private String category;
	private Date dateEndDue;
	private String location;
	private String description;

	public CalendarEntry(String name, int dateStart, int dateEndDue, String timeZone, String category, String location,
			String description) {
		this.name = name;
		this.dateStart = new Date((long) dateStart * 1000);
		this.dateEndDue = new Date((long) dateEndDue * 1000);
		this.timeZone = timeZone;
		this.category = category;
		this.location = location;
		this.description = description;
	}

	
	@Override
	public String toString() {
		return name + "@" + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(dateStart);
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

	public boolean isBirthday() {
		return this.getCategory().equals("BIRTHDAY");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDF(String dF, boolean end) {
		Date date = end ? this.getDateEndDue() : this.getDateStart();
		return Integer.valueOf(new SimpleDateFormat(dF).format(date));
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(int dateStart) {
		this.dateStart = new Date((long) dateStart * 1000);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDateEndDue() {
		return dateEndDue;
	}

	public void setDateEndDue(Date dateEndDue) {
		this.dateEndDue = dateEndDue;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

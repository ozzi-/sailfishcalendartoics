package jollatoics;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;

public class CalendarEntry {

	private String name;
	private Date dateStart;
	private TimeZone timeZone;
	private String category;
	private Date dateEndDue;
	private String location;
	private String description;
	private Date dateStartLocal;
	private Date dateEndDueLocal;

	private static TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();

	public CalendarEntry(String name, int dateStartLocal, int dateEndDueLocal, int dateStart, int dateEndDue,
			String timeZone, String category, String location, String description) {
		this.name = name;
		this.dateStartLocal = new Date((long) dateStartLocal * 1000);
		this.dateEndDueLocal = new Date((long) dateEndDueLocal * 1000);
		this.dateStart = new Date((long) dateStart * 1000);
		this.dateEndDue = new Date((long) dateEndDue * 1000);
		this.timeZone = registry.getTimeZone(timeZone);
		this.category = category;
		this.location = location;
		this.description = description;
	}

	@Override
	public String toString() {
		return name + "@" + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(dateStart);
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = registry.getTimeZone(timeZone);
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

	public int getDF4J(String dF, boolean end) {
		int offset = dF.equals("MM") ? -1 : 0; // java starts counting with 0
												// for months
		return getDF(dF, end) + offset;
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

	public Date getDateStartLocal() {
		return dateStartLocal;
	}

	public void setDateStartLocal(Date dateStartLocal) {
		this.dateStartLocal = dateStartLocal;
	}

	public Date getDateEndDueLocal() {
		return dateEndDueLocal;
	}

	public void setDateEndDueLocal(Date dateEndDueLocal) {
		this.dateEndDueLocal = dateEndDueLocal;
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (otherObj instanceof CalendarEntry) {
			CalendarEntry other = (CalendarEntry) otherObj;
			return(
					this.getName().equals(other.getName()) &&
					this.getDescription().equals(other.getDescription()) &&
					this.getDateStart().equals(other.getDateStart()) &&
					this.getDateEndDue().equals(other.getDateEndDue())
			);
		}
		return false;	
	}
	@Override
	public int hashCode() {
        int hash = 1;
        hash = hash * 17 + (this.getName()+this.getDescription()).hashCode();
        hash = hash * 31 + this.getDateStart().hashCode();
        hash = hash * 13 + this.getDateEndDue().hashCode();
        return hash;	
	}
}

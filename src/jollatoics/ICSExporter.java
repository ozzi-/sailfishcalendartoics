package jollatoics;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.SocketException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class ICSExporter {
	
	private TimeZone timezone;
	private Calendar calendar;
	private UidGenerator uidGenerator;
	private String icsLocation;
	
	public ICSExporter(String icsLocationp) {
		icsLocation=icsLocationp;
		calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//https://github.com/ozzi-/sailfishcalendartoics//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);

		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		timezone = registry.getTimeZone("Australia/Melbourne"); // TODO timezone!?
		try {
			uidGenerator = new UidGenerator("1");
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void addEvent(String eventName){
		java.util.Calendar cal = java.util.Calendar.getInstance(timezone);
		cal.set(java.util.Calendar.YEAR, 2005);
		cal.set(java.util.Calendar.MONTH, java.util.Calendar.NOVEMBER);
		cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
		cal.set(java.util.Calendar.HOUR_OF_DAY, 15);
		cal.clear(java.util.Calendar.MINUTE);
		cal.clear(java.util.Calendar.SECOND);
		DateTime dt = new DateTime(cal.getTime());
		dt.setTimeZone(timezone);
		VEvent event = new VEvent(dt, eventName);
		event.getProperties().add(uidGenerator.generateUid());
		calendar.getComponents().add(event);
	}
	
	public void export(){
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(icsLocation);
		} catch (FileNotFoundException e1) {
			System.out.println(icsLocation+" invalid export location");
			e1.printStackTrace();
			System.exit(1);
		}
		CalendarOutputter outputter = new CalendarOutputter();
		try {
			outputter.output(calendar, fout);
		} catch (Exception e) {
			System.out.println("Could not save ics!");
			e.printStackTrace();
			System.exit(1);
		}

	}
	


	

	
	
}

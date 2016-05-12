package jollatoics;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.text.ParseException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class ICSExporter {
	
	private Calendar calendar;
	private UidGenerator uidGenerator;
	private String icsLocation;
	private TimeZoneRegistry registry;
	
	public ICSExporter(String icsLocationp) {
		icsLocation=icsLocationp;
		calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//https://github.com/ozzi-/sailfishcalendartoics//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);

		registry = TimeZoneRegistryFactory.getInstance().createRegistry();

		try {
			uidGenerator = new UidGenerator("1");
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void addEvent(CalendarEntry calendarEntry){
		java.util.Calendar cal;
		TimeZone timeZone = null;
		if(!calendarEntry.getTimeZone().equals("")){
			timeZone = registry.getTimeZone(calendarEntry.getTimeZone());
			cal = timeZone==null?java.util.Calendar.getInstance():java.util.Calendar.getInstance(timeZone);			
		}else{
			cal = java.util.Calendar.getInstance();		
		}
		
		cal.set(java.util.Calendar.YEAR, calendarEntry.getDF("yyyy"));
		cal.set(java.util.Calendar.MONTH, calendarEntry.getDF("MM"));
		cal.set(java.util.Calendar.DAY_OF_MONTH, calendarEntry.getDF("dd"));
		cal.set(java.util.Calendar.HOUR_OF_DAY, calendarEntry.getDF("hh"));
		cal.set(java.util.Calendar.MINUTE, calendarEntry.getDF("mm"));
		cal.set(java.util.Calendar.SECOND, calendarEntry.getDF("ss"));

		DateTime dt = new DateTime(cal.getTime());
		if(!calendarEntry.getTimeZone().equals("")){
			dt.setTimeZone(timeZone);
		}
		String additionalInfo= calendarEntry.getCategory().equals("")?"":"("+calendarEntry.getCategory()+")";
		VEvent event = new VEvent(dt, calendarEntry.getName()+additionalInfo);
		event.getProperties().add(uidGenerator.generateUid());
		if(calendarEntry.getCategory().equals("BIRTHDAY")){
			String rrule = "FREQ=YEARLY;";
			try {
				event.getProperties().add(new RRule(rrule));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
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

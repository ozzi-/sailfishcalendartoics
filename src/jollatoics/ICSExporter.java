package jollatoics;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.text.ParseException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class ICSExporter {
	
	private Calendar calendar;
	private UidGenerator uidGenerator;
	private String icsLocation;

	public ICSExporter(String icsLocationp) {
		icsLocation=icsLocationp;
		calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//https://github.com/ozzi-/sailfishcalendartoics//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		

		try {
			uidGenerator = new UidGenerator("1");
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void addEvent(CalendarEntry calendarEntry){
		java.util.Calendar calStart;
		java.util.Calendar calEnd;
		TimeZone timeZone = calendarEntry.getTimeZone();
		
		calStart	= timeZone==null?java.util.Calendar.getInstance():java.util.Calendar.getInstance(timeZone);		
		calEnd		= timeZone==null?java.util.Calendar.getInstance():java.util.Calendar.getInstance(timeZone);			
		
		calStart.set(java.util.Calendar.YEAR, calendarEntry.getDF4J("yyyy",false));
		calStart.set(java.util.Calendar.MONTH, calendarEntry.getDF4J("MM",false)); 
		calStart.set(java.util.Calendar.DAY_OF_MONTH, calendarEntry.getDF4J("dd",false));
		calStart.set(java.util.Calendar.HOUR_OF_DAY, calendarEntry.getDF4J("HH",false));
		calStart.set(java.util.Calendar.MINUTE, calendarEntry.getDF4J("mm",false));
		calStart.set(java.util.Calendar.SECOND, calendarEntry.getDF4J("ss",false));
		
		calEnd.set(java.util.Calendar.YEAR, calendarEntry.getDF4J("yyyy",true));
		calEnd.set(java.util.Calendar.MONTH, calendarEntry.getDF4J("MM",true)); 
		calEnd.set(java.util.Calendar.DAY_OF_MONTH, calendarEntry.getDF4J("dd",true));
		calEnd.set(java.util.Calendar.HOUR_OF_DAY, calendarEntry.getDF4J("HH",true));
		calEnd.set(java.util.Calendar.MINUTE, calendarEntry.getDF4J("mm",true));
		calEnd.set(java.util.Calendar.SECOND, calendarEntry.getDF4J("ss",true));
		
		DateTime dtStart = new DateTime(calStart.getTime());
		DateTime dtEnd = new DateTime(calEnd.getTime());
	
		if(timeZone!=null){
			dtStart.setTimeZone(timeZone);
			dtEnd.setTimeZone(timeZone);
		}
		
		VEvent event;
		if(calendarEntry.isBirthday()){
			event = new VEvent(dtStart, calendarEntry.getName()+"("+calendarEntry.getCategory()+")");
			String rrule = "FREQ=YEARLY;";
			try {
				event.getProperties().add(new RRule(rrule));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			event = new VEvent(dtStart, dtEnd, calendarEntry.getName());			
		}

		event.getProperties().add(uidGenerator.generateUid());
		if(!calendarEntry.getLocation().equals("")){
			event.getProperties().add(new Location(calendarEntry.getLocation()));			
		}
		if(!calendarEntry.getDescription().equals("")){
			event.getProperties().add(new Description(calendarEntry.getDescription()));			
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

package jollatoics;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.fortuna.ical4j.data.ParserException;

public class jollatoics {

	public static void main(String[] args) throws SQLException, IOException, ParserException {
		String dbLocation;
		if (args.length==0) {
			System.out.println("Specify db path as argument");
			System.exit(1);
		}
		dbLocation = args[0];
		String icsLocation="export.ics";
		if (args.length>1) {
			icsLocation = args[1] ;
		}

		File f = new File(dbLocation);
		if (!f.exists() || f.isDirectory()) {
			System.out.println(dbLocation + " does not seem to exist or is a directory!");
			System.exit(1);
		}

		Connection db = SQLiteJDBC.open(dbLocation);
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<CalendarEntry> calendarList = new ArrayList<CalendarEntry>();

		try {
			stmt = db.createStatement();
			rs = stmt.executeQuery("SELECT * FROM components;");
			while (rs.next()) {
				calendarList.add(
						new CalendarEntry(
								rs.getString("Summary"), 
								rs.getInt("DateStart"),
								rs.getString("StartTimeZone")
							)
					);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		ICSExporter icsExporter = new ICSExporter(icsLocation);
		
		for (CalendarEntry calendarEntry : calendarList) {
			icsExporter.addEvent(calendarEntry);
			System.out.println(calendarEntry);
		}
		icsExporter.export();
	}
}

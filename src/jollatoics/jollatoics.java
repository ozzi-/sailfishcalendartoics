package jollatoics;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		Statement stmtTE = null;
		ResultSet rsTE = null;
		int currentEvent=0;
		ICSExporter icsExporter = new ICSExporter(icsLocation);
		
		try {
			stmtTE = db.createStatement();
			rsTE = stmtTE.executeQuery("SELECT COUNT(*) FROM components;");
			int totalEvents=(rsTE.getInt("COUNT(*)"));
			System.out.println("Got "+totalEvents+"x events");
			stmt = db.createStatement();
			rs = stmt.executeQuery("SELECT * FROM components;");
			
			while (rs.next()) {
				CalendarEntry cE= new CalendarEntry(
						rs.getString("Summary"),
						// TODO do we need the local or non local column? ics4j should calculate it ,,
						rs.getInt("DateStart"), 
						rs.getInt("DateEndDue"),
						rs.getString("StartTimeZone"),
						rs.getString("Category"),
						rs.getString("Location"),
						rs.getString("Description")
					);
				Progress.updateProgress((float)currentEvent++/totalEvents);
				icsExporter.addEvent(cE);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLiteJDBC.safeClose(rsTE);
			SQLiteJDBC.safeClose(stmtTE);
			SQLiteJDBC.safeClose(rs);
			SQLiteJDBC.safeClose(stmt);
		}

		icsExporter.export();
	}
}

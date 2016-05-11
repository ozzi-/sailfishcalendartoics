package jollatoics;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class jollatoics {

	public static void main(String[] args) throws SQLException {
		String dbLocation=args[0];
		if (dbLocation == null) {
			System.out.println("Specify db path as argument");
			System.exit(1);
		}
		File f = new File(dbLocation);
		if (!f.exists() || f.isDirectory()) {
			System.out.println(dbLocation+" does not seem to exist or is a directory!");
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
				calendarList.add(new CalendarEntry(rs.getString("Summary"), rs.getInt("DateStart")));				
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
		
		for (CalendarEntry calendarEntry : calendarList) {
			System.out.println(calendarEntry);
		}
	}
}

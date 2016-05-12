package jollatoics;

import java.sql.*;

public class SQLiteJDBC {
	public static Connection open(String dbName) {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:"+dbName);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}
	
	public static void safeClose(Object obj){
		try {
			if(obj != null){
				if(obj instanceof ResultSet){
					ResultSet resultSetObj = (ResultSet)obj;
					resultSetObj.close();
				}
				if(obj instanceof Statement){
					Statement statemenetObj = (Statement)obj;
					statemenetObj.close();
				}
			}
		} catch (SQLException e) {
			// who you gonna call? 
		}
	}
}
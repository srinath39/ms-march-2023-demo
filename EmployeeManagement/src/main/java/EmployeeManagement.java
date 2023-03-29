import java.sql.*;

public class EmployeeManagement {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String dbName = "mydb";
		String db_username = "root";
		String db_password = "1234";
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/" + dbName, db_username, db_password);
		} catch (Exception e) {
			System.out.println("Unable to connect");
		}
		EmployeeManagementSystem ems = new EmployeeManagementSystem(con);
		ems.login();
	}
}

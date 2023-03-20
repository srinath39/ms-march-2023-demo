import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

public class EmployeeManagement {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		// creates connection with sql
		String dbName = "mydb";
		String db_username = "root";
		String db_password = "1234";
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3307/" + dbName, db_username, db_password);
		EmployeeManagementSystem ems = new EmployeeManagementSystem(con);

		ems.loginFrame = new JFrame();

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(200, 150, 220, 50);
		usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		ems.loginFrame.add(usernameLabel);

		JTextField usernameField = new JTextField();
		usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		usernameField.setBounds(450, 150, 420, 50);
		ems.loginFrame.add(usernameField);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		passwordLabel.setBounds(200, 250, 220, 50);
		ems.loginFrame.add(passwordLabel);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		passwordField.setBounds(450, 250, 420, 50);
		ems.loginFrame.add(passwordField);

		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		submitButton.setBounds(450, 400, 250, 50);
		ems.loginFrame.add(submitButton);

		submitButton.addActionListener(actionEvent -> {
			String username = usernameField.getText();
			String password = String.valueOf(passwordField.getPassword());

			try {
				boolean auth = ems.login(username, password);
				if (auth) {
					ems.loginFrame.dispose();
					ems.mainMenu(usernameField, passwordField);
				}

			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		});

		ems.loginFrame.setSize(1100, 750);
		ems.loginFrame.setLayout(null);
		ems.loginFrame.setVisible(true);
		ems.loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}

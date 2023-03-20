import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmployeeManagementSystem {
	public Connection con;

	public JFrame loginFrame;
	public JFrame menuFrame;

	public EmployeeManagementSystem(Connection conn) {
		super();
		this.con = conn;
	}

	public boolean login(String username, String password) throws SQLException {
		Statement statement = this.con.createStatement();

		String q = String.format("select password from admin where username = '%s';", username);
		ResultSet resultSet = statement.executeQuery(q);

		if (resultSet.next()) {

			if (resultSet.getString(1).equals(password)) {
				return true;
			} else {
				JOptionPane.showMessageDialog(loginFrame, "The password you entered is invalid.", "Invalid password",
						1);
				return false;
			}
		} else {

			JOptionPane.showMessageDialog(loginFrame, "The username you entered does not exist.", "Invalid username",
					1);
			return false;
		}
	}

	public void mainMenu(JTextField usernameField, JTextField passwordField) {
		menuFrame = new JFrame("Employee Management System");

		JLabel welcomeLabel = new JLabel("Welcome to Employee Management System");
		welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
		welcomeLabel.setBounds(250, 50, 800, 50);
		menuFrame.add(welcomeLabel);

		JButton viewEmpButton = new JButton("View all employees");
		viewEmpButton.setBounds(400, 200, 300, 40);
		viewEmpButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		viewEmpButton.setFocusPainted(false);
		viewEmpButton.addActionListener(actionEvent -> {
			try {
				viewEmployee();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		});
		menuFrame.add(viewEmpButton);

		JButton addEmpButton = new JButton("Add an employee");
		addEmpButton.setBounds(400, 270, 300, 40);
		addEmpButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addEmpButton.setFocusPainted(false);
		addEmpButton.addActionListener(actionEvent -> {
			try {
				addEmployee();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		});
		menuFrame.add(addEmpButton);

		JButton editEmpButton = new JButton("Edit an employee");
		editEmpButton.setBounds(400, 340, 300, 40);
		editEmpButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		editEmpButton.setFocusPainted(false);
		editEmpButton.addActionListener(actionEvent -> {
			try {
				editEmployee();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		});
		menuFrame.add(editEmpButton);

		JButton deleteEmpButton = new JButton("Delete an employee");
		deleteEmpButton.setBounds(400, 410, 300, 40);
		deleteEmpButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		deleteEmpButton.setFocusPainted(false);
		deleteEmpButton.addActionListener(deleteEvent -> {
			try {
				deleteEmployee();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		menuFrame.add(deleteEmpButton);

		JButton exitButton = new JButton("Log Out");
		exitButton.setBounds(400, 480, 300, 40);
		exitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		exitButton.setFocusPainted(false);

		exitButton.addActionListener(actionEvent -> {
			loginFrame.setVisible(true);
			usernameField.setText("");
			passwordField.setText("");
			menuFrame.dispose();
		});
		menuFrame.add(exitButton);

		menuFrame.setSize(1100, 750);
		menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		menuFrame.setLayout(null);// using no layout managers
		menuFrame.setVisible(true);// making the frame visible

	}

	public void viewEmployee() throws SQLException {
		menuFrame.setVisible(false);

		JFrame frame = new JFrame("Employee Records");
		JPanel panel = new JPanel();

		Statement statement = this.con.createStatement();
		String q = "select * from employee";
		ResultSet resultSet = statement.executeQuery(q);

		while (resultSet.next()) {
			JPanel employeeCard = new JPanel();

			JLabel idLabel = new JLabel("ID");
			idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel idVal = new JLabel(String.valueOf(resultSet.getInt(1)));
			idVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel nameLabel = new JLabel("Name");
			nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel nameVal = new JLabel(resultSet.getString(2));
			nameVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel genderLabel = new JLabel("Gender");
			genderLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel genderVal = new JLabel(resultSet.getString(3));
			genderVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel phoneLabel = new JLabel("Phone Number");
			phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel phoneVal = new JLabel(resultSet.getString(4));
			phoneVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel emailLabel = new JLabel("Email");
			emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel emailVal = new JLabel(resultSet.getString(5));
			emailVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel designationLabel = new JLabel("Designation");
			designationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel designationVal = new JLabel(resultSet.getString(6));
			designationVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel salaryLabel = new JLabel("Salary");
			salaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel salaryVal = new JLabel(String.valueOf(resultSet.getDouble(7)));
			salaryVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			employeeCard.add(idLabel);
			employeeCard.add(idVal);
			employeeCard.add(nameLabel);
			employeeCard.add(nameVal);
			employeeCard.add(genderLabel);
			employeeCard.add(genderVal);
			employeeCard.add(phoneLabel);
			employeeCard.add(phoneVal);
			employeeCard.add(emailLabel);
			employeeCard.add(emailVal);
			employeeCard.add(designationLabel);
			employeeCard.add(designationVal);
			employeeCard.add(salaryLabel);
			employeeCard.add(salaryVal);

			employeeCard.setSize(1000, 400);
			employeeCard.setBackground(new Color(166, 209, 230));
			employeeCard.setBorder(new EmptyBorder(20, 50, 20, 50));
			GridLayout cardLayout = new GridLayout(0, 2);
			cardLayout.setHgap(5);
			cardLayout.setVgap(10);
			employeeCard.setLayout(cardLayout);
			panel.add(employeeCard);
		}

		JPanel buttonPanel = new JPanel();
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		backButton.setBounds(450, 30, 200, 50);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			frame.dispose();
			menuFrame.setVisible(true);
		});
		buttonPanel.add(backButton);
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(new Color(254, 251, 246));
		panel.add(buttonPanel);

		// panel.setPreferredSize(new Dimension(500,500));
		GridLayout layout = new GridLayout(0, 1);
		layout.setVgap(30);
		panel.setLayout(layout);
		panel.setBackground(new Color(254, 251, 246));
		panel.setBorder(new EmptyBorder(50, 0, 50, 0));

		JScrollPane scrollBar = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frame.add(scrollBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1100, 750));
		frame.pack();
		frame.setVisible(true);

	}

	public void addEmployee() throws SQLException {

		menuFrame.setVisible(false);

		JFrame frame = new JFrame("Employee Records");
		JPanel panel = new JPanel();

		JLabel idLabel = new JLabel("Enter ID");
		idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField idVal = new JTextField("");
		idVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		idVal.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // if it's not a number, ignore the event
				}
			}
		});

		JLabel nameLabel = new JLabel("Enter Name");
		nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField nameVal = new JTextField("");
		nameVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel genderLabel = new JLabel("Enter gender");
		genderLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JComboBox<String> genderVal = new JComboBox<>(new String[] { "Male", "Female" });
		genderVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel phoneLabel = new JLabel("Enter Phone Number");
		phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField phoneVal = new JTextField("");
		phoneVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel emailLabel = new JLabel("Enter Email Address");
		emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField emailVal = new JTextField("");
		emailVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel designationLabel = new JLabel("Enter Designation");
		designationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField designationVal = new JTextField("");
		designationVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel salaryLabel = new JLabel("Enter Salary ($)");
		salaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField salaryVal = new JTextField("");
		salaryVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		salaryVal.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // if it's not a number, ignore the event
				}
			}
		});

		panel.add(idLabel);
		panel.add(idVal);
		panel.add(nameLabel);
		panel.add(nameVal);
		panel.add(genderLabel);
		panel.add(genderVal);
		panel.add(phoneLabel);
		panel.add(phoneVal);
		panel.add(emailLabel);
		panel.add(emailVal);
		panel.add(designationLabel);
		panel.add(designationVal);
		panel.add(salaryLabel);
		panel.add(salaryVal);

		GridLayout cardLayout = new GridLayout(0, 2);
		cardLayout.setHgap(60);
		cardLayout.setVgap(40);
		panel.setLayout(cardLayout);

		panel.setSize(1000, 400);
		panel.setBackground(new Color(166, 209, 230));
		panel.setBorder(new EmptyBorder(20, 50, 20, 50));

		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		backButton.setBounds(450, 30, 200, 50);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			frame.dispose();
			menuFrame.setVisible(true);
		});
		panel.add(backButton);

		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		submitButton.setBounds(450, 30, 200, 50);
		submitButton.setFocusPainted(false);

		submitButton.addActionListener(actionListener -> {
			Statement statement = null;
			try {
				statement = this.con.createStatement();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

			int id = Integer.parseInt(idVal.getText());
			String name = nameVal.getText();
			String gender = (String) genderVal.getSelectedItem();
			String phoneNum = phoneVal.getText();
			String email = emailVal.getText();
			String designation = designationVal.getText();
			double salary = Double.parseDouble(salaryVal.getText());

			String q = String.format("insert into employee values (%d, '%s', '%s', '%s', '%s', '%s', %f);", id, name,
					gender,
					phoneNum, email, designation, salary);
			try {
				statement.executeUpdate(q);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

			menuFrame.setVisible(true);
			frame.dispose();

		});
		panel.add(submitButton);

		panel.setBackground(new Color(254, 251, 246));
		panel.setBorder(new EmptyBorder(50, 50, 50, 50));

		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1100, 750));
		frame.pack();
		frame.setVisible(true);

	}

	public void editEmployee() throws SQLException {
		menuFrame.setVisible(false);

		JFrame frame = new JFrame("Edit Employee");

		JLabel label = new JLabel("Enter employee id");
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label.setBounds(250, 200, 200, 50);
		frame.add(label);

		JTextField idVal = new JTextField();
		idVal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		idVal.setBounds(500, 200, 200, 50);
		frame.add(idVal);
		idVal.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // if it's not a number, ignore the event
				}
			}
		});

		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		backButton.setBounds(275, 400, 150, 40);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			menuFrame.setVisible(true);
			frame.dispose();

		});
		frame.add(backButton);

		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		submitButton.setBounds(525, 400, 150, 40);
		submitButton.setFocusPainted(false);

		submitButton.addActionListener(actionEvent -> {
			int id = Integer.parseInt(idVal.getText());
			String q = String.format("select * from employee where id = %d;", id);
			Statement statement = null;
			try {
				statement = this.con.createStatement();
				ResultSet resultSet = statement.executeQuery(q);
				if (resultSet.next()) {
					System.out.println(resultSet.getString(2));
					// return true;
					editEmployeeHelper(id, frame);

				} else {
					JOptionPane.showMessageDialog(frame, "The ID you entered is invalid.", "Invalid ID", 1);
				}
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

		});

		frame.add(submitButton);

		frame.setLayout(null);
		frame.setSize(new Dimension(1100, 750));
		frame.setVisible(true);
	}

	public void editEmployeeHelper(int id, JFrame parentFrame) throws SQLException {
		parentFrame.dispose();
		JFrame frame = new JFrame("Edit Employee");
		JPanel panel = new JPanel();

		Statement statement = null;
		try {
			statement = this.con.createStatement();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		String q = String.format("select * from employee where id = %d;", id);
		ResultSet resultSet = statement.executeQuery(q);
		resultSet.next();
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField nameVal = new JTextField(resultSet.getString(2));
		nameVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel genderLabel = new JLabel("Gender");
		genderLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		String gender = resultSet.getString(3);
		JComboBox<String> genderVal = new JComboBox<>(new String[] { "Male", "Female" });
		genderVal.setSelectedIndex(gender.equals("Male") ? 0 : 1);
		genderVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel phoneLabel = new JLabel("Phone Number");
		phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField phoneVal = new JTextField(resultSet.getString(4));
		phoneVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField emailVal = new JTextField(resultSet.getString(5));
		emailVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel designationLabel = new JLabel("Designation");
		designationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField designationVal = new JTextField(resultSet.getString(6));
		designationVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JLabel salaryLabel = new JLabel("Salary");
		salaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JTextField salaryVal = new JTextField(String.valueOf(resultSet.getDouble(7)));
		salaryVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		salaryVal.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // if it's not a number, ignore the event
				}
			}
		});

		panel.add(nameLabel);
		panel.add(nameVal);
		panel.add(genderLabel);
		panel.add(genderVal);
		panel.add(phoneLabel);
		panel.add(phoneVal);
		panel.add(emailLabel);
		panel.add(emailVal);
		panel.add(designationLabel);
		panel.add(designationVal);
		panel.add(salaryLabel);
		panel.add(salaryVal);

		GridLayout cardLayout = new GridLayout(0, 2);
		cardLayout.setHgap(60);
		cardLayout.setVgap(40);
		panel.setLayout(cardLayout);

		panel.setSize(1000, 400);
		panel.setBackground(new Color(166, 209, 230));
		panel.setBorder(new EmptyBorder(20, 50, 20, 50));

		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		backButton.setBounds(450, 30, 200, 50);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			frame.dispose();
			menuFrame.setVisible(true);
		});
		panel.add(backButton);

		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		submitButton.setBounds(450, 30, 200, 50);
		submitButton.setFocusPainted(false);

		submitButton.addActionListener(actionListener -> {
			Statement statemnt = null;
			try {
				statemnt = this.con.createStatement();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

			String name = nameVal.getText();
			String genderValSelectedItem = (String) genderVal.getSelectedItem();
			String phoneNum = phoneVal.getText();
			String email = emailVal.getText();
			String designation = designationVal.getText();
			double salary = Double.parseDouble(salaryVal.getText());

			String query = String.format(
					"update employee set name = '%s', gender = '%s', phoneNum = '%s', email = '%s', " +
							"designation = '%s', salary = %s where id = %d;",
					name, genderValSelectedItem,
					phoneNum, email, designation, salary, id);
			try {
				statemnt.executeUpdate(query);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

			menuFrame.setVisible(true);
			frame.dispose();

		});
		panel.add(submitButton);

		panel.setBackground(new Color(254, 251, 246));
		panel.setBorder(new EmptyBorder(50, 50, 50, 50));

		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1100, 750));
		frame.pack();
		frame.setVisible(true);

	}

	public void deleteEmployee() throws SQLException {
		menuFrame.setVisible(false);

		JFrame frame = new JFrame("Delete Employee");

		JLabel label = new JLabel("Enter employee id");
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label.setBounds(250, 200, 200, 50);
		frame.add(label);

		JTextField idVal = new JTextField();
		idVal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		idVal.setBounds(500, 200, 200, 50);
		frame.add(idVal);
		idVal.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // if it's not a number, ignore the event
				}
			}
		});

		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		backButton.setBounds(275, 400, 150, 40);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			menuFrame.setVisible(true);
			frame.dispose();

		});
		frame.add(backButton);

		JButton submitButton = new JButton("Delete");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		submitButton.setBounds(525, 400, 150, 40);
		submitButton.setFocusPainted(false);

		submitButton.addActionListener(actionEvent -> {
			int id = Integer.parseInt(idVal.getText());
			String q = String.format("select * from employee where id = %d;", id);
			Statement statement = null;
			try {
				statement = this.con.createStatement();
				ResultSet resultSet = statement.executeQuery(q);
				if (resultSet.next()) {
					System.out.println(resultSet.getString(2));

					String deleteQuery = String.format("delete from employee where id = %d;", id);
					statement.executeUpdate(deleteQuery);
					menuFrame.setVisible(true);
					frame.dispose();

				} else {
					JOptionPane.showMessageDialog(frame, "The ID you entered is invalid.", "Invalid ID", 1);

				}
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

		});

		frame.add(submitButton);

		frame.setLayout(null);
		frame.setSize(new Dimension(1100, 750));
		frame.setVisible(true);
	}

}

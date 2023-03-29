import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class EmployeeManagementSystem {
	public Connection con;
	public Boolean dd = false;
	public JFrame loginFrame;
	public JFrame menuFrame;

	public EmployeeManagementSystem(Connection conn) {
		super();
		this.con = conn;
	}

	public void login() {
		loginFrame = new JFrame("Employee Management System");
		JLabel usernameLabel = new JLabel("Username : ");
		usernameLabel.setBounds(400, 150, 220, 50);
		usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		loginFrame.add(usernameLabel);

		JTextField usernameField = new JTextField();
		usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		usernameField.setBounds(650, 150, 420, 50);
		loginFrame.add(usernameField);

		JLabel passwordLabel = new JLabel("Password : ");
		passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		passwordLabel.setBounds(400, 250, 220, 50);
		loginFrame.add(passwordLabel);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		passwordField.setBounds(650, 250, 420, 50);
		loginFrame.add(passwordField);

		JLabel cp = new JLabel(
				"<html><a style = \"font-family:georgia,garamond,serif;font-size:12px;font-style:Arial;\"><h3>Forgot Password ?<h3></a></html>");

		cp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changePassword();
			}
		});
		cp.setBounds(650, 300, 200, 50);
		loginFrame.add(cp);
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		submitButton.setBounds(650, 400, 250, 50);
		submitButton.setBackground(Color.lightGray);
		loginFrame.add(submitButton);

		submitButton.addActionListener(actionEvent -> {
			String username = usernameField.getText();
			String password = String.valueOf(passwordField.getPassword());
			if (username.equals("") && password.equals(""))
				JOptionPane.showMessageDialog(loginFrame, "Please Enter Username and Password", "Empty", 1);
			else if (username.equals("") && !password.equals(""))
				JOptionPane.showMessageDialog(loginFrame, "Please Enter Username", "Empty", 1);
			else if (!username.equals("") && password.equals(""))
				JOptionPane.showMessageDialog(loginFrame, "Please Enter Password", "Empty", 1);
			else {
				try {
					boolean auth = loginCheck(username, password);
					if (auth) {
						loginFrame.dispose();
						mainMenu(usernameField, passwordField);
					}

				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
			}
		});
		submitButton.setFocusPainted(false);
		submitButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		loginFrame.setLayout(null);
		loginFrame.setVisible(true);
		loginFrame.getContentPane().setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));
		loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public boolean loginCheck(String username, String password) throws SQLException {
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

	public void changePassword() {
		loginFrame.setVisible(false);

		JFrame frame = new JFrame("change password");

		JLabel ulabel = new JLabel("Enter username");
		ulabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ulabel.setBounds(500, 150, 200, 50);
		frame.add(ulabel);

		JTextField user = new JTextField();
		user.setFont(new Font("Times New Roman", Font.BOLD, 20));
		user.setBounds(750, 150, 200, 50);
		frame.add(user);

		JLabel label = new JLabel("Enter New Password");
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label.setBounds(500, 250, 200, 50);
		frame.add(label);

		JTextField idVal = new JTextField();
		idVal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		idVal.setBounds(750, 250, 200, 50);
		frame.add(idVal);

		JLabel clabel = new JLabel("Confirm password");
		clabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		clabel.setBounds(500, 350, 200, 50);
		frame.add(clabel);

		JPasswordField pVal = new JPasswordField();
		pVal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		pVal.setBounds(750, 350, 200, 50);
		frame.add(pVal);

		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		backButton.setBounds(500, 450, 150, 40);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			loginFrame.setVisible(true);
			frame.dispose();
		});
		frame.add(backButton);

		backButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		JButton submitButton = new JButton("Change");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		submitButton.setBounds(750, 450, 150, 40);
		submitButton.setFocusPainted(false);

		submitButton.addActionListener(actionEvent -> {
			String np = idVal.getText();
			String conp = new String(pVal.getPassword());
			if (np.equals(conp)) {
				try {

					Statement statement = con.createStatement();
					String sql = String.format("update admin set password='%s' where username='%s' ;", np,
							user.getText());
					System.out.println(sql);

					statement.executeUpdate(sql);
					frame.dispose();
					loginFrame.setVisible(true);
					JOptionPane.showMessageDialog(loginFrame, "password change succesfully", "Done", 1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(frame, "The New password and confirm password does'nt match", "MisMatch",
						1);
			}
		});
		submitButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		frame.add(submitButton);
		frame.getContentPane().setBackground(Color.white);
		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));
	}

	public void mainMenu(JTextField usernameField, JTextField passwordField) {
		menuFrame = new JFrame("Employee Management System");
		JLabel welcomeLabel = new JLabel("Welcome to Employee Management System !");
		welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
		welcomeLabel.setBounds(100, 30, 800, 50);
		menuFrame.add(welcomeLabel);

		JButton viewEmpButton = new JButton("View all employees");
		viewEmpButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		viewEmpButton.setFocusPainted(false);
		viewEmpButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
		viewEmpButton.addActionListener(actionEvent -> {
			try {
				viewEmployee();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		});
		menuFrame.add(viewEmpButton);

		JButton addEmpButton = new JButton("Add an employee");
		addEmpButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		addEmpButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
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
		editEmpButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		editEmpButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
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
		deleteEmpButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		deleteEmpButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
		deleteEmpButton.setFocusPainted(false);
		deleteEmpButton.addActionListener(deleteEvent -> {
			try {
				deleteEmployee();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		menuFrame.add(deleteEmpButton);

		viewEmpButton.setBounds(100, 150, 250, 100);
		addEmpButton.setBounds(380, 150, 250, 100);
		editEmpButton.setBounds(100, 300, 250, 100);
		deleteEmpButton.setBounds(380, 300, 250, 100);

		JMenuBar menubar = new JMenuBar();
		menubar.add(Box.createHorizontalStrut(50));
		JMenu set = new JMenu("Settings");
		set.setMnemonic(KeyEvent.VK_S);
		set.setToolTipText("settings");
		set.setFont(new Font("Times New Roman", Font.BOLD, 20));

		JMenu mode = new JMenu("Mode");
		mode.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JRadioButtonMenuItem def = new JRadioButtonMenuItem("Default", !dd);
		def.setEnabled(true);
		def.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		def.addActionListener(e -> {
			menubar.setBackground(Color.decode("#778da9"));
			menuFrame.getContentPane().setBackground(Color.decode("#bde0fe"));
			viewEmpButton.setBackground(Color.decode("#778da9"));
			addEmpButton.setBackground(Color.decode("#778da9"));
			editEmpButton.setBackground(Color.decode("#778da9"));
			deleteEmpButton.setBackground(Color.decode("#778da9"));
			dd = false;
		});

		JRadioButtonMenuItem dark = new JRadioButtonMenuItem("Dark", dd);
		dark.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		dark.addActionListener(e -> {
			menubar.setBackground(Color.decode("#476D7C"));
			menuFrame.getContentPane().setBackground(Color.decode("#77ABB7"));
			viewEmpButton.setBackground(Color.decode("#476D7C"));
			addEmpButton.setBackground(Color.decode("#476D7C"));
			editEmpButton.setBackground(Color.decode("#476D7C"));
			deleteEmpButton.setBackground(Color.decode("#476D7C"));
			dd = true;
		});

		ButtonGroup btn = new ButtonGroup();
		btn.add(def);
		btn.add(dark);

		mode.add(def);
		mode.add(dark);

		JMenu layout = new JMenu("Layout");
		layout.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JRadioButtonMenuItem grid = new JRadioButtonMenuItem("Grid", true);
		def.setEnabled(true);
		grid.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		grid.addActionListener(e -> {
			viewEmpButton.setBounds(100, 150, 250, 100);
			addEmpButton.setBounds(380, 150, 250, 100);
			editEmpButton.setBounds(100, 300, 250, 100);
			deleteEmpButton.setBounds(380, 300, 250, 100);
		});

		JRadioButtonMenuItem box = new JRadioButtonMenuItem("Horizontal");
		box.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		box.addActionListener(e -> {
			viewEmpButton.setBounds(100, 150, 250, 100);
			addEmpButton.setBounds(370, 150, 250, 100);
			editEmpButton.setBounds(640, 150, 250, 100);
			deleteEmpButton.setBounds(910, 150, 250, 100);
		});

		JRadioButtonMenuItem vbox = new JRadioButtonMenuItem("Vertical");
		vbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		vbox.addActionListener(e -> {
			viewEmpButton.setBounds(200, 150, 250, 100);
			addEmpButton.setBounds(200, 270, 250, 100);
			editEmpButton.setBounds(200, 390, 250, 100);
			deleteEmpButton.setBounds(200, 510, 250, 100);
		});

		ButtonGroup lbtn = new ButtonGroup();
		lbtn.add(grid);
		lbtn.add(box);
		lbtn.add(vbox);

		layout.add(grid);
		layout.add(box);
		layout.add(vbox);

		set.add(mode);
		set.add(layout);

		menuFrame.setJMenuBar(menubar);
		menubar.add(Box.createRigidArea(new Dimension(30, 50)));
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		help.setToolTipText("help");
		help.setFont(new Font("Times New Roman", Font.BOLD, 20));

		JMenu exit = new JMenu("Exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setToolTipText("exit");
		exit.setFont(new Font("Times New Roman", Font.BOLD, 20));

		JMenuItem out = new JMenuItem("LogOut");
		out.setMnemonic(KeyEvent.VK_E);
		out.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		out.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
				usernameField.setText("");
				passwordField.setText("");
				menuFrame.dispose();
			}
		});
		exit.add(out);

		menubar.add(set);
		menubar.add(Box.createHorizontalStrut(1200));
		menubar.add(help);
		menubar.add(Box.createHorizontalStrut(30));
		menubar.add(exit);
		menubar.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		menuFrame.setLayout(null);
		menuFrame.setVisible(true);
		menuFrame.getContentPane().setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));
	}

	public void viewEmployee() throws SQLException {
		menuFrame.setVisible(false);
		JFrame frame = new JFrame("Employee Records");
		JPanel panel = new JPanel();

		Statement statement = this.con.createStatement();
		String q = "select * from employee";
		ResultSet resultSet = statement.executeQuery(q);

		while (resultSet.next()) {
			JPanel outer = new JPanel();
			JPanel employeeCard = new JPanel();

			JLabel userId = new JLabel(String.valueOf(resultSet.getInt(1)));

			JLabel idLabel = new JLabel("ID");
			idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel idVal = new JLabel(String.valueOf(":   " + resultSet.getInt(1)));
			idVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel nameLabel = new JLabel("Name");
			nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel nameVal = new JLabel(":   " + resultSet.getString(2));
			nameVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel genderLabel = new JLabel("Gender");
			genderLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel genderVal = new JLabel(":   " + resultSet.getString(3));
			genderVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel phoneLabel = new JLabel("Phone Number");
			phoneLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel phoneVal = new JLabel(":   " + resultSet.getString(4));
			phoneVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel emailLabel = new JLabel("Email");
			emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel emailVal = new JLabel(":   " + resultSet.getString(5));
			emailVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel designationLabel = new JLabel("Designation");
			designationLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel designationVal = new JLabel(":   " + resultSet.getString(6));
			designationVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel salaryLabel = new JLabel("Salary");
			salaryLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

			JLabel salaryVal = new JLabel(String.valueOf(":   " + resultSet.getDouble(7)));
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
			employeeCard.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
			employeeCard.setBorder(new EmptyBorder(20, 50, 20, 50));

			GridLayout cardLayout = new GridLayout(0, 2);
			cardLayout.setHgap(5);
			cardLayout.setVgap(10);
			employeeCard.setLayout(cardLayout);

			JButton avail = new JButton("Availability");
			avail.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			avail.setBounds(450, 30, 200, 50);
			avail.setFocusPainted(false);
			avail.addActionListener(event -> {
				try {
					availabilityInfo(Integer.parseInt(userId.getText()), frame);
				} catch (SQLException e) {
					System.out.println("error");
				}
			});
			avail.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

			outer.add(employeeCard);
			outer.add(avail);
			outer.setLayout(new FlowLayout());
			outer.setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));

			panel.add(outer);
		}

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		backButton.setBounds(600, 30, 200, 50);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			frame.dispose();
			menuFrame.setVisible(true);
		});
		buttonPanel.add(backButton);
		buttonPanel.setLayout(null);
		panel.add(buttonPanel);
		backButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		GridLayout layout = new GridLayout(0, 1);
		layout.setVgap(30);
		panel.setLayout(layout);

		panel.setBorder(new EmptyBorder(50, 0, 0, 0));
		panel.setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));

		JScrollPane scrollBar = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	private void availabilityInfo(int userId, JFrame parent) throws SQLException {
		Statement statement = con.createStatement();
		String sql = String.format("select * from available where userId=%d and available='%s';", userId, "yes");
		ResultSet res = statement.executeQuery(sql);
		String[] str = null;
		if (res.next()) {
			str = (res.getInt(2) + " " + res.getInt(3) + " " + res.getInt(4) + " " + res.getInt(5) + " " + res.getInt(6)
					+ " " + res.getString(7)).split(" ");
		}

		String sql2 = String.format("select * from available where userId=%d and available='%s';", userId, "no");
		ResultSet res2 = statement.executeQuery(sql2);
		String[] str2 = null;
		if (res2.next()) {
			str2 = (res2.getInt(2) + " " + res2.getInt(3) + " " + res2.getInt(4) + " " + res2.getInt(5) + " "
					+ res2.getInt(6) + " " + res2.getString(7)).split(" ");
		}

		JTable table = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("monday");
		dtm.addColumn("tuesday");
		dtm.addColumn("wednesday");
		dtm.addColumn("friday");
		dtm.addColumn("saturday");
		dtm.addColumn("availability");
		dtm.addRow(str);
		dtm.addRow(str2);
		table.setModel(dtm);
		table.setRowHeight(20);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		JDialog dialog = new JDialog(parent, "table", true);
		JScrollPane scrollPane = new JScrollPane(table);
		dialog.getContentPane().add(scrollPane);
		dialog.setSize(600, 200);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}

	public void addEmployee() throws SQLException {
		menuFrame.setVisible(false);
		JFrame frame = new JFrame("Employee Records");
		JPanel panel = new JPanel();

		JLabel idLabel = new JLabel("Enter ID");
		idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		SpinnerModel sm = new SpinnerNumberModel(101, 101, 199, 1);
		JSpinner idVal = new JSpinner(sm);

		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) idVal.getEditor();
		JTextField textField = editor.getTextField();
		textField.setHorizontalAlignment(JTextField.LEFT);

		idVal.setFont(new Font("Times New Roman", Font.PLAIN, 20));

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

		ArrayList<Component> list = new ArrayList<Component>();
		list.add(textField);
		list.add(nameVal);
		list.add(phoneVal);
		list.add(emailVal);
		list.add(designationVal);
		list.add(salaryVal);

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
		backButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		submitButton.setBounds(450, 30, 200, 50);
		submitButton.setFocusPainted(false);
		submitButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
		submitButton.addActionListener(actionListener -> {
			Statement statement = null;
			try {
				statement = this.con.createStatement();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

			int id = (int) idVal.getValue();
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

		JButton btn = new JButton("upload");
		btn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		panel.add(btn);

		btn.addActionListener(e -> {
			JFileChooser open = new JFileChooser();
			int option = open.showOpenDialog(frame);
			int i = 0;
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					Scanner sc = new Scanner(new FileReader(open.getSelectedFile().getPath()));
					while (sc.hasNext()) {
						((JTextComponent) list.get(i)).setText(sc.nextLine());
						i++;
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btn.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
		panel.setBackground(new Color(254, 251, 246));
		panel.setBorder(new EmptyBorder(50, 50, 50, 50));

		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		panel.setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));
	}

	public void editEmployee() throws SQLException {
		menuFrame.setVisible(false);
		JFrame frame = new JFrame("Edit Employee");
		JLabel label = new JLabel("Enter employee id : ");
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label.setBounds(500, 250, 200, 50);
		frame.add(label);

		JTextField idVal = new JTextField();
		idVal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		idVal.setBounds(750, 250, 200, 50);
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
		backButton.setBounds(500, 350, 200, 50);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			menuFrame.setVisible(true);
			frame.dispose();

		});
		backButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
		frame.add(backButton);

		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		submitButton.setBounds(750, 350, 200, 50);
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
		submitButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		frame.add(submitButton);

		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));
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
		backButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
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
		submitButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		panel.setBackground(new Color(254, 251, 246));
		panel.setBorder(new EmptyBorder(50, 50, 50, 50));

		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		panel.setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));

	}

	public void deleteEmployee() throws SQLException {
		menuFrame.setVisible(false);
		JFrame frame = new JFrame("Delete Employee");
		JLabel label = new JLabel("Enter employee id : ");
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label.setBounds(500, 250, 200, 50);
		frame.add(label);

		JTextField idVal = new JTextField();
		idVal.setFont(new Font("Times New Roman", Font.BOLD, 20));
		idVal.setBounds(750, 250, 200, 50);
		frame.add(idVal);
		idVal.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});

		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		backButton.setBounds(500, 350, 200, 50);
		backButton.setFocusPainted(false);

		backButton.addActionListener(actionListener -> {
			menuFrame.setVisible(true);
			frame.dispose();

		});
		frame.add(backButton);
		backButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));
		JButton submitButton = new JButton("Delete");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		submitButton.setBounds(750, 350, 200, 50);
		submitButton.setFocusPainted(false);

		submitButton.addActionListener(actionEvent -> {
			int id = Integer.parseInt(idVal.getText());
			String q = String.format("select * from employee where id = %d;", id);
			Statement statement = null;
			try {
				statement = this.con.createStatement();
				ResultSet resultSet = statement.executeQuery(q);
				if (resultSet.next()) {
					int a = JOptionPane.showConfirmDialog(frame, "Are you sure?");
					if (a == JOptionPane.YES_OPTION) {
						String deleteQuery = String.format("delete from employee where id = %d;", id);
						statement.executeUpdate(deleteQuery);
					} else {
						return;
					}
					menuFrame.setVisible(true);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "The ID you entered is invalid.", "Invalid ID", 1);
				}
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}

		});
		submitButton.setBackground(Color.decode(dd ? "#476D7C" : "#778da9"));

		frame.add(submitButton);

		frame.setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.decode(dd ? "#77ABB7" : "#bde0fe"));
	}

}
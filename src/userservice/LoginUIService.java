package userservice;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import dbconnection.DatabaseUtil;
import homepage.HomePage;
import question.AdminUI;
import quizquestionsresults.TopicSelectionUi;

public class LoginUIService implements ActionListener {
	private User user;
	private JFrame frame;
	private JLabel nameLabel = new JLabel("User Name");
	private JTextField nameTextField = new JTextField();
	private JButton backButton = new JButton("Back");
	private JLabel passwordLabel = new JLabel("Password");
	private JPasswordField passwordField = new JPasswordField();
	
	
	JButton loginButton = new JButton("Login");

	public LoginUIService() {
		createWindow();
		setLocationAndSize();
		addComponentsToFrame();
		addActionEvent();

	}

	private void createWindow() {
		frame = new JFrame();
		frame.setTitle("Login Form");
		frame.setSize(500, 300);
		frame.setBounds(400, 200, 500, 300);
		frame.getContentPane().setBackground(Color.white);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

	}

	private void setLocationAndSize() {
		nameLabel.setBounds(80, 50, 100, 30);
		nameLabel.setFont(new Font("Cambria", Font.BOLD, 18));

		nameTextField.setBounds(200, 50, 200, 30);

		passwordLabel.setBounds(80, 100, 100, 30);
		passwordLabel.setFont(new Font("Cambria", Font.BOLD, 18));

		passwordField.setBounds(200, 100, 200, 30);
		backButton.setBounds(300, 200, 100, 30);
		loginButton.setBounds(150, 200, 100, 30);
		loginButton.setFont(new Font("Cambria", Font.BOLD, 15));
	}

	private void addComponentsToFrame() {
		frame.add(nameLabel);
		frame.add(nameTextField);
		frame.add(passwordLabel);
		frame.add(passwordField);
		frame.add(loginButton);
		frame.add(backButton);
	}

	private void addActionEvent() {
		loginButton.addActionListener(this);
		backButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			loginUser();
		}

		if (e.getSource() == backButton) {
			new HomePage();
			frame.setVisible(false);
		}
	}

	private void loginUser() {
		 String username = nameTextField.getText();
		 String password = new String(passwordField.getPassword());
		 int tempid = 0;

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Username or Password cannot be empty!");
			return;
		}

		try (Connection conn = DatabaseUtil.getConnection()) {
			System.out.println("Database connected successfully");
			String query = "select password , user_id from user where username = ?";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, username);
				ResultSet rs = stmt.executeQuery();

				if (rs.next()) {
					String storedHash = rs.getString("password");
					String enteredHash = hashPassword(password);
					tempid=rs.getInt("user_id");
					System.out.println("userid "+tempid  + "logged in");

					if (storedHash.equals(enteredHash)) {
						if ("admin".equals(username) && storedHash.equals(enteredHash)) {
							JOptionPane.showMessageDialog(frame, "Logged in successfully!" + "\n Hello " + username);
							new AdminUI().setVisible(true);
							frame.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(frame, "Logged in successfully! \n Hello " + username);
							new TopicSelectionUi().setVisible(true);
							frame.setVisible(false);
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Invalid username or password!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "User does not exist!");
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
		}
	}

	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error hashing password", e);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LoginUIService login = new LoginUIService();
			login.frame.setVisible(true);
		});

	}

}

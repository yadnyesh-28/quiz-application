package userservice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import homepage.HomePage;

public class RegisterUI implements ActionListener {
	private JFrame frame;
	private JLabel title = new JLabel("Registration Form");
	private JLabel userNameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	private JTextField nameTextField = new JTextField();
	private JPasswordField passwdTextField = new JPasswordField();
	private JButton backButton = new JButton("Back");
	private JButton submitButton = new JButton("Submit");
	private JButton resetButton = new JButton("Reset");
	private UserRegisterService userService;

	public RegisterUI() {
		userService = new UserRegisterService();
		createWindow();
		setLocationAndSize();
		addComponentsToFrame();
		actionEvent();
	}

	public void createWindow() {
		frame = new JFrame();
		frame.setTitle("User Registration");
		frame.setBounds(400, 200, 500, 300);
		frame.getContentPane().setBackground(Color.white);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	public void setLocationAndSize() {
		title.setBounds(150, 10, 200, 30);
		title.setFont(new Font("Cambria", Font.BOLD, 20));
		title.setForeground(Color.BLACK);

		userNameLabel.setBounds(80, 50, 100, 30);
		userNameLabel.setForeground(Color.BLACK);
		userNameLabel.setFont(new Font("Cambria", Font.BOLD, 18));


		nameTextField.setBounds(200, 50, 200, 30);

		passwordLabel.setBounds(80, 100, 100, 30);
		passwordLabel.setForeground(Color.BLACK);
		passwordLabel.setFont(new Font("Cambria", Font.BOLD, 18));

		passwdTextField.setBounds(200, 100, 200, 30);
		backButton.setBounds(60, 200, 100, 30);
		submitButton.setBounds(170, 200, 100, 30);
		resetButton.setBounds(280, 200, 100, 30);
	}

	public void addComponentsToFrame() {
		frame.add(title);
		frame.add(userNameLabel);
		frame.add(nameTextField);
		frame.add(passwordLabel);
		frame.add(passwdTextField);
		frame.add(submitButton);
		frame.add(resetButton);
		frame.add(backButton);
		frame.setVisible(true);
	}

	public void actionEvent() {
		submitButton.addActionListener(this);
		resetButton.addActionListener(this);
		backButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			String username = nameTextField.getText();
			String password = new String(passwdTextField.getPassword());

			if (username.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Username is missing");
				return;
			}

			if (password.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Password is missing");
				return;
			}

			try {
				boolean success = userService.registerUser(username, password);
				if (success) {
					JOptionPane.showMessageDialog(frame, "User registered successfully");
					new HomePage();
					frame.setVisible(false); // Optionally close the frame
					resetFields();
				} else {
					JOptionPane.showMessageDialog(frame, "User already exists");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		if (e.getSource() == backButton) {
			new homepage.HomePage();
			frame.setVisible(false);
		}

		if (e.getSource() == resetButton) {
			nameTextField.setText("");
			passwdTextField.setText("");
		}
	}

	// Reset form fields when user registered
	private void resetFields() {
		nameTextField.setText("");
		passwdTextField.setText("");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(RegisterUI::new);
	}

}

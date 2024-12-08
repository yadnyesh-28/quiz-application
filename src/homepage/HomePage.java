package homepage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import userservice.LoginUIService;
import userservice.RegisterUI;

public class HomePage implements ActionListener {
	static String Username;
	private JFrame frame;
	private JLabel welcome = new JLabel("Welcome to Quiz Application");
	private JMenuBar menuBar = new JMenuBar();

	private JMenu menu1 = new JMenu("File");
	private JMenu menu3 = new JMenu("Login");
	JMenu menu4 = new JMenu("Feedback");

	private JMenuItem exitItem2 = new JMenuItem("Exit"); // File tab

	private JMenuItem NewUserItem3 = new JMenuItem("New User Registartions"); // Login tab
	private JMenuItem LogInItem4 = new JMenuItem("Login"); // LogIn tab
	private JMenuItem FeedbackItem7 = new JMenuItem("Give Feedback"); // feedback tab
	private ImageIcon QuizIcon;
	private JLabel image = new JLabel();

	public HomePage() {
		// TODO Auto-generated constructor
		CreateWindow();
		addComponentToFrame();
		actionEvent();
		setLocationSize();

	}

	private void CreateWindow() {
		frame = new JFrame();
		frame.setTitle("Quiz Application");
		//frame.setSize(1000,600);
		frame.setBounds(200, 50, 1000, 600); // bottom ,top padding, width, height of window
		frame.getContentPane().setBackground(Color.white);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	private void setLocationSize() {

		menuBar.setBounds(2, 2, 1280, 30);
		menu1.setBounds(2, 2, 100, 30);
		menu1.setFont(new Font("Cambria", Font.BOLD, 18));

		exitItem2.setBounds(2, 2, 100, 40);
		exitItem2.setFont(new Font("Cambria", Font.CENTER_BASELINE, 15));

		NewUserItem3.setBounds(2, 2, 100, 40);
		NewUserItem3.setFont(new Font("Cambria", Font.CENTER_BASELINE, 15));

		LogInItem4.setBounds(2, 2, 100, 40);
		LogInItem4.setFont(new Font("Cambria", Font.CENTER_BASELINE, 15));

		menu3.setBounds(30, 2, 100, 30);
		menu3.setFont(new Font("Cambria", Font.BOLD, 18));

		menu4.setBounds(30, 2, 100, 30);
		menu4.setFont(new Font("Cambria", Font.BOLD, 18));

		menu1.add(exitItem2); // logout=file

		menu3.add(NewUserItem3); // menu3-login tab -->new user
		menu3.add(LogInItem4); // menu3-login login

		menu4.add(FeedbackItem7); // feedback tab --> feedback

		menuBar.add(menu1);
		menuBar.add(menu3);
		menuBar.add(menu4);

		welcome.setBounds(300, 80, 800, 50);
		welcome.setBackground(Color.white);

		welcome.setFont(new Font("Cambria", Font.BOLD, 25));

		QuizIcon = new ImageIcon("E:\\CDAC\\AdvJavaProjects\\QuizApp\\1745773486.jpg");
		image.setBounds(0, 0, 1000, 600);
		image.setIcon(QuizIcon);

	}

	void actionEvent() {
		exitItem2.addActionListener(this);
		NewUserItem3.addActionListener(this);
		LogInItem4.addActionListener(this);
		FeedbackItem7.addActionListener(this);
	}

	private void addComponentToFrame() {
		frame.add(menuBar);
		frame.add(welcome);
		frame.add(image);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new HomePage();
			
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == NewUserItem3) {
			// registration Form
			new RegisterUI();
			frame.setVisible(false);
		}

		if (e.getSource() == exitItem2) {
			frame.setVisible(false);
			frame.dispose();
		}

		if (e.getSource() == LogInItem4) {
			// Login l1 = new Login(); //loginpage
			LoginUIService.main(null);
			frame.setVisible(false);

		}

	}

}

package quizquestionsresults;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ResultUI extends JFrame {
	private JPanel contentPane;
    private JButton againButton;
    private JButton exitButton;
    private ResultService service= new ResultService();
   private int userid;

	public ResultUI(int totalQuestionss,int score)throws SQLException {
        setTitle("Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setBounds(400, 200, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Get user details and quiz results
        String username = "user";
        int correctAnswers = score;
        int totalQuestions = totalQuestionss; 
        int wrongAnswers = totalQuestions - correctAnswers;

        // Display user information and results
        JLabel usernameLabel = new JLabel("<html>Hey, " + username +"   !</html>");
        usernameLabel.setBounds(190, 40, 300, 100);
        contentPane.add(usernameLabel);
        
        JLabel footerLabel = new JLabel("Thank you");
        footerLabel.setBounds(260, 40, 300, 100);
        contentPane.add(footerLabel);

        JLabel resultLabel = new JLabel("<html>Your Final Score: " + correctAnswers +
                "<br><br>Total Right Answers: " + correctAnswers +
                "<br><br>Total Wrong Answers: " + wrongAnswers + "</html>");
        resultLabel.setBounds(190, 100, 300, 100);
        contentPane.add(resultLabel);

        // Save result to the database
        try {
        	service.saveResultToDatabase(userid, username, correctAnswers, wrongAnswers, correctAnswers, totalQuestions);
        	
        }catch(SQLException e) {
        	e.printStackTrace();
        }

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setBounds(280, 250, 89, 23);
        contentPane.add(exitButton);
        exitButton.addActionListener(e -> {
        	JOptionPane.showMessageDialog(this, "Logout Successfully" );
        	System.exit(0);
        	});
        	

        // Play Again button
        againButton = new JButton("Play Again");
        againButton.setBounds(185, 250, 89, 23);
        contentPane.add(againButton);
        
        againButton.addActionListener(e -> {
        	try {
				new TopicSelectionUi().setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            dispose();
        });

        
    }

	
     	
   
     
    
   
}
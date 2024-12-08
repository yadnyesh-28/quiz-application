package quizquestionsresults;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dbconnection.DatabaseUtil;
import homepage.HomePage;
import question.QuestionService;

public class TopicSelectionUi extends JFrame {
	private JButton logoutButton;
	private QuestionService queService= new QuestionService();
	 public TopicSelectionUi() throws SQLException {
	        setTitle("Select Topic");
	        setSize(500, 300);
	        setBounds(400, 200, 500, 300);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setResizable(false);

	        JPanel panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	        panel.setBounds(400, 200, 500, 300);

	        JLabel label = new JLabel("Select a Topic for a quiz:");
	        label.setBounds(100, 100, 100, 30);
	        
	        logoutButton = new JButton("Log out");
	        logoutButton.setBounds(300, 200, 100, 30);
	        
	        panel.add(label);
	        add(logoutButton);

	        try(Connection con= DatabaseUtil.getConnection()) {
	            List<String> topics =queService.getTopics();
	            for (String topic : topics) {
	                JButton topicButton = new JButton(topic);
	                topicButton.setBounds(100, 50, 100, 30);
	                topicButton.addActionListener((ActionEvent e) -> {
	                    int topicId = Integer.parseInt(topic.split(" - ")[0]);
	                    try {
							new QuizUi(topicId).setVisible(true);
							
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
	                    dispose();
	                });
	                panel.add(topicButton);
	            }
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(this, "Error loading topics: " + e.getMessage());
	        }

	        add(panel);
	        setLocationRelativeTo(null);
	        
	        logoutButton.addActionListener(e->   {
	        	
	        	JOptionPane.showMessageDialog(this, "Logout Successfully" );
	        	new HomePage();
	        });
	    }
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		new TopicSelectionUi().setVisible(true);
	}

}

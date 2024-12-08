package quizquestionsresults;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import question.Question;
import question.QuestionService;

public class QuizUi extends JFrame {

	private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionsGroup;
    private  JButton nextButton;
    private  JButton submitButton;
    
    private JLabel timerLabel;
    private QuestionService queService=new QuestionService();
    

    public QuizUi(int topicId) throws SQLException {
    	
        setTitle("Quiz");
        setSize(600, 400);
        setBounds(400, 200, 600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        
        

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 500, 50);
        add(questionLabel);
        

        optionsGroup = new ButtonGroup();
        option1 = createOption(50, 120);
        option2 = createOption(50, 160);
        option3 = createOption(50, 200);
        option4 = createOption(50, 240);

         nextButton = new JButton("Next");
        nextButton.setBounds(400, 300, 100, 30);
        
        submitButton = new JButton("Submit");
        submitButton.setBounds(400, 300, 100, 30);
        
        JButton BackButton = new JButton("Back");
        BackButton.setBounds(400, 50, 100, 30); //side padding , top padding , width, height
        
        nextButton.addActionListener(e -> {
			try {
				onNextClicked(e);
				submitButton.setEnabled(false);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        add(nextButton);
        
        BackButton.addActionListener(e -> {
			try {
				onBackClicked(e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        add(BackButton);
        
        
        submitButton.addActionListener(e -> submitAnswer());
        add(submitButton);

        timerLabel = new JLabel("01:00");
        timerLabel.setBounds(500, 10, 100, 30);
        add(timerLabel);
        loadQuestions(topicId);
        startTimer();
        
    }

    private void loadQuestions(int topicId) throws SQLException {
 
        try {
            questions = queService.getQuestionsByTopic(topicId);
            if(questions.isEmpty()) {
            	JOptionPane.showMessageDialog(this, "Quiz is empty");
            	
            	}
            else
            displayQuestion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage());
        }
    }

    private void displayQuestion() throws SQLException {
    	
        if (currentQuestionIndex >= questions.size()) {
            showResults();
            return;
        }
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getQuestion());
        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        option4.setText(question.getOption4());
        optionsGroup.clearSelection();
	
    }

    private JRadioButton createOption(int x, int y) {
        JRadioButton option = new JRadioButton();
        option.setBounds(x, y, 500, 30);
        optionsGroup.add(option);
        add(option);
        return option;
    }

    private void onNextClicked(ActionEvent e) throws SQLException {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            int selectedOption = getSelectedOption();
            if(selectedOption ==-1) {
            	JOptionPane.showMessageDialog(this, "Please select an option.");
            	return;
            	
            }
            int correctOption =question.getCorrectOption();
             if (selectedOption == question.getCorrectOption()) {
            	 JOptionPane.showMessageDialog(this, "Correct");
                score++;
            } else
            	JOptionPane.showMessageDialog(this,"Incorrect! Correct answer: Option " + correctOption);
            currentQuestionIndex++;
            displayQuestion();
        }
    }
    
    private void onBackClicked(ActionEvent e) throws SQLException {
        if (currentQuestionIndex < questions.size()) {
            currentQuestionIndex--;
            displayQuestion();
        }
    }
    
    private void submitAnswer() {
    	if(currentQuestionIndex ==questions.size()) {
    		setEnabled(true);
    		nextButton.setEnabled(true);
    		
    	}
    	
    	 return;
    }

    private int getSelectedOption() {
        if (option1.isSelected()) return 1;
        if (option2.isSelected()) return 2;
        if (option3.isSelected()) return 3;
        if (option4.isSelected()) return 4;
        
       
        return -1;
    }

    private void startTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            int time = 60;
            @Override
            public void actionPerformed(ActionEvent e) {
                time--;
                timerLabel.setText(formatTime(time));
                if (time <= 0) {
                    ((Timer) e.getSource()).stop();
                    try {
						showResults();
						//new class AnswerUI
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });
        timer.start();
    }

    private void showResults() throws SQLException {
    	
        JOptionPane.showMessageDialog(this, "Quiz Over! Thank you.");
        new ResultUI(questions.size(), score).setVisible(true);
        System.out.println("Questions size: "+questions.size());
       
        dispose();
    }

    private String formatTime(int time) {
        return String.format("%02d:%02d", time / 60, time % 60);
    }
}

package question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbconnection.DatabaseUtil;

public class QuestionService   {
	private Connection conn;
	
	public QuestionService() throws SQLException {
		conn=DatabaseUtil.getConnection();
		System.out.println("question & topic Database connected");
	}
	
	public void addTopic(String topicName) throws SQLException {
        String query = "insert into topic (topic_name) values (?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, topicName);
            statement.executeUpdate();
            System.out.println("Topic added successfully.");
        }
    }
	
	public void addQuestion(int topicId, String question, String option1, String option2, String option3, String option4, int correctOption) throws SQLException {
        String query = "insert into questions (topic_id, question, option1, option2, option3, option4, correct_option) values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, topicId);
            statement.setString(2, question);
            statement.setString(3, option1);
            statement.setString(4, option2);
            statement.setString(5, option3);
            statement.setString(6, option4);
            statement.setInt(7, correctOption);
            statement.executeUpdate();
            System.out.println("Question added successfully.");
        }
    }
	
	public void updateQuestion(int queId, String question) throws SQLException {
		String query = "update questions set question= ? where id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, question);
        stmt.setInt(2, queId);
        stmt.executeUpdate();
    }
	
	public ResultSet getQuestionssByTopic(int topicId) throws SQLException {
        String query = "select * from questions where topic_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, topicId);
        return stmt.executeQuery();
    }


    public ResultSet getTopic() throws SQLException {
        String query = "select * from topic";
        return conn.prepareStatement(query).executeQuery();
    }
    
    public ResultSet getQuestions() throws SQLException {
        String query = "select * from questions";
        return conn.prepareStatement(query).executeQuery();
    }
    
    public List<String> getTopics() throws SQLException {
        String query = "select * from topic";
        List<String> topics = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                topics.add(resultSet.getInt("topic_id") + " - " + resultSet.getString("topic_name"));
            }
        }
        return topics;
    }

    public List<Question> getQuestionsByTopic(int topicId) throws SQLException {
        String query = "select * from questions where topic_id = ?";
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, topicId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(new Question(
                        resultSet.getInt("id"),
                        resultSet.getString("question"),
                        resultSet.getString("option1"),
                        resultSet.getString("option2"),
                        resultSet.getString("option3"),
                        resultSet.getString("option4"),
                        resultSet.getInt("correct_option")
                ));
            }
        }
        return questions;
    }
    
    
    public void updateTopic(int topicId, String newName) throws SQLException {
        String query = "update topic set topic_name = ? where topic_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newName);
        stmt.setInt(2, topicId);
        stmt.executeUpdate();
    }
    

    public void deleteTopic(int topicId) throws SQLException {
        String query = "delete from topic where topic_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, topicId);
        stmt.executeUpdate();
    }
    
    public void deleteQuestion(int questionId) throws SQLException {
        String query = "delete from questions where id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, questionId);
        stmt.executeUpdate();
    }
    

	
	
 	

}

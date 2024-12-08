package quizquestionsresults;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbconnection.DatabaseUtil;

public class ResultService {
	
	private Connection conn;
	
	public ResultService() throws SQLException {
		// TODO Auto-generated constructor stub
		conn=DatabaseUtil.getConnection();
		System.out.println("database connected");
	}

	
	public void saveResultToDatabase(int userId, String username, int correct, int wrong, int score, int total)throws SQLException {   
            String sql = "insert into result (userId, username, correct, wrong, score, total) values (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, username);
                pstmt.setInt(3, correct);
                pstmt.setInt(4, wrong);
                pstmt.setInt(5, score);
                pstmt.setInt(6, total);
                pstmt.executeUpdate();
            }
        }
	
	
	
}

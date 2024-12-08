package userservice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbconnection.DatabaseUtil;

public class UserRegisterService {

	    public boolean isUserExists(String username) throws SQLException {
	        String query = "select * from user where username = ?";
	        try (Connection conn = DatabaseUtil.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.setString(1, username);
	            ResultSet rs = ps.executeQuery();
	            return rs.next();
	        }
	    }
	    
	    

	    public boolean registerUser(String username, String password) throws SQLException {
	        if (isUserExists(username)) {
	            return false;
	        }
	        // Hash the password
            String hashedPassword = hashPassword(password);

	        String insertQuery = "insert into user (username, password) values (?, ?)";
	        try (Connection conn = DatabaseUtil.getConnection();
	             PreparedStatement ps = conn.prepareStatement(insertQuery)) {
	            ps.setString(1, username);
	            ps.setString(2, hashedPassword); // Ideally, hash the password here
	            ps.executeUpdate();
	            return true;
	        }
	    }
	    
	    
	 // Password hashing using SHA-256
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
	}

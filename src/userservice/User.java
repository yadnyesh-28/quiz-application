package userservice;

public class User {
	protected int userId=0;
    protected String username="";
    
    public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int userId, String username) {
		super();
		this.userId = userId;
		this.username = username;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
}

package api.models;

public class Post {
	private int id;
	private String thread;
	private String conent;
	private String user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getConent() {
		return conent;
	}
	public void setConent(String conent) {
		this.conent = conent;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
}


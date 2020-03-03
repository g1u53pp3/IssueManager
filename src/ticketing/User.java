package ticketing;

import java.util.Set;

import ticketing.IssueManager.UserClass;

public class User {
	String username;
	Set<UserClass> uClass;
	int numtick;
	public int getNumtick() {
		return numtick;
	}
	public void setNumtick() {
		this.numtick ++;
	}
	public User(String username, Set<UserClass> uClass) {
		this.username = username;
		this.uClass = uClass;
	}
	public String getUsername() {
		return username;
	}
	public Set<UserClass> getuClass() {
		return uClass;
	}
	
	

}

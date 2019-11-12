package api.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;;


public class User {
	
	private String id;
	private String password;
	private String icon;
	private String tag;
	
	public String getId() {
		return id;
	}
	public void setId(String username) {
		this.id = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}

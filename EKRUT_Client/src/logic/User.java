package logic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements EkrutIF, Serializable {
	
	protected String username;
	protected String id;
	protected String first_name;
	protected String last_name;
	protected Role role;
	protected String email;
	protected String phone_number;
	protected int is_logged_in;
	
	public User(String username, String id, String first_name, String last_name, Role role, String email,
			String phone_number, int is_logged_in) {
		this.username = username;
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.role = role;
		this.email = email;
		this.phone_number = phone_number;
		this.is_logged_in = is_logged_in;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}

	public int getIsLoggedIn() {
		return is_logged_in;
	}

	public void setIsLoggedIn(int is_logged_in) {
		this.is_logged_in = is_logged_in;
	}

	@Override
	public void insertData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		
	}
}
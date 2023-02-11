package com.javaex.vo;

public class UserVo {

	private int userNumber;
	private String name;
	private String email;
	private String password;
	private String gender;

	public UserVo() {
	}

	public UserVo(int userNumber) {
		this(userNumber, "none", "none", "none", "none");
	}
	public UserVo(String email, String password) {
		this(0, "none", email, password, "none");
	}

	public UserVo(String name, String password, String gender) {
		this(0, name, "none", password,gender);
	}

	public UserVo(String name, String email, String password, String gender) {
		this(0, name, email, password, gender);
	}


	public UserVo(int userNumber, String name, String email, String password, String gender) {
		this.userNumber = userNumber;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
	}




	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "UserVo [no=" + userNumber + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + "]";
	}

}

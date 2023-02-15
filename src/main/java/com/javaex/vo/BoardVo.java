package com.javaex.vo;

import javax.servlet.http.Part;

public class BoardVo {

	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	private String userName;
	private FileVo file;

	public BoardVo() {
	}

	public BoardVo(int no) {
		this(no, "none", "none", 0,"none", 0,"none");
	}
	
	public BoardVo(int no, String title, String content) {
		this(no, title, content, 0, "none", 0, "none");
	}

	public BoardVo(String title, String content, int userNo, FileVo file) {
		this(0, title, content, 0, "none", userNo, "none", file);
	}

	public BoardVo(int no, String title, int hit, String regDate, int userNo, String userName) {
		this(no, title, "none", hit, regDate, userNo, userName, null);

	}

	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String userName) {
		this(no, title, hit, regDate, userNo, userName);
		this.content = content;
	}

	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String userName, FileVo file) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
		this.file = file;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUserNo() {
		return userNo;
	}

	public FileVo getFile() {
		return file;
	}

	@Override
	public String toString() {
		return "BoardVo{" +
				"no=" + no +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", hit=" + hit +
				", regDate='" + regDate + '\'' +
				", userNo=" + userNo +
				", userName='" + userName + '\'' +
				", file=" + file +
				'}';
	}
}

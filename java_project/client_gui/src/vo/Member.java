package vo;

import java.io.Serializable;

public class Member implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4894236748562299117L;
	// 가입일 필요?
	// 탈퇴 회원 테이블 필요?
	// 비밀번호 암호화
	private int no;
	private String id;
	private String pw;
	private String name;
	private int age;
	private String addr;
	private boolean isOwner;

	// 기본 생성자
	public Member() {

	}

	// id 중복확인용 생성자
	public Member(String id) {
		this.id = id;
	}

	// 회원 인증용 생성자
	public Member(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	public Member(String id, String pw, String name, int age, String addr, boolean isOwner) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.age = age;
		this.addr = addr;
		this.isOwner = isOwner;
	}

	// Getter & Setter
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	// toString
	@Override
	public String toString() {
		return "MemberVO [no=" + no + ", id=" + id + ", pw=" + pw + ", name=" + name + ", age=" + age + ", addr=" + addr
				+ ", isOwner=" + isOwner + "]";
	}

}

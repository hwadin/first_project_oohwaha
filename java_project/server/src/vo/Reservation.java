package vo;

import java.sql.Timestamp;

public class Reservation {
	private int no;
	private int cafe;
	private int member;
	private Timestamp time;
	private boolean isDutch;
	private boolean isApply;
	
	// 기본 생성자
	public Reservation() {}

	// 필드를 다 전달받는 생성자
	public Reservation(int no, int cafe, int member, Timestamp time, boolean isDutch, boolean isApply) {
		super();
		this.no = no;
		this.cafe = cafe;
		this.member = member;
		this.time = time;
		this.isDutch = isDutch;
		this.isApply = isApply;
	}
	// Getter , Setter
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getCafe() {
		return cafe;
	}

	public void setCafe(int cafe) {
		this.cafe = cafe;
	}

	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public boolean isDutch() {
		return isDutch;
	}

	public void setDutch(boolean isDutch) {
		this.isDutch = isDutch;
	}

	public boolean isApply() {
		return isApply;
	}

	public void setApply(boolean isApply) {
		this.isApply = isApply;
	}
	
	
	
}

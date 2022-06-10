package vo;

import java.io.Serializable;

public class Schedule implements Serializable {

	private static final long serialVersionUID = 4894236748562299117L;

	// 스케쥴 번호
	private int no;
	// 회원 번호
	private int member;
	// 시작시간
	private String start_time;
	// 끝시간
	private String end_time;

	public Schedule() {
	}

	// 스케쥴 확인용 생성자
	public Schedule(int no, int member_no) {
		this.no = no;
		this.member = member_no;
	}

	public Schedule(int no, int member, String start_time, String end_time) {
		this.no = no;
		this.member = member;
		this.start_time = start_time;
		this.end_time = end_time;
	}

	// Getter & Setter

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getMember() {
		return member;
	}

	public void setMember(int member_no) {
		this.member = member_no;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	@Override
	public String toString() {
		return "Schedule [no=" + no + ", member_no=" + member + ", start_time=" + start_time + ", end_time=" + end_time
				+ "]";
	}

}

package vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Schedule implements Serializable {

	private static final long serialVersionUID = 342539071942934970L;

	// 스케쥴 번호
	private int no;
	// 회원 번호
	private int member;
	// 시작시간
	private Timestamp start_time;
	// 끝시간
	private Timestamp end_time;

	private String title;

	private String detail;

	public Schedule() {
	}

	// 스케쥴 확인용 생성자
	public Schedule(int no, int member_no) {
		this.no = no;
		this.member = member_no;
	}

	public Schedule(int no, int member, Timestamp start_time, Timestamp end_time) {
		this.no = no;
		this.member = member;
		this.start_time = start_time;
		this.end_time = end_time;
	}

	public Schedule(int member, Timestamp start_time, Timestamp end_time, String title, String detail) {
		this.member = member;
		this.start_time = start_time;
		this.end_time = end_time;
		this.title = title;
		this.detail = detail;
	}

	public Schedule(int no, int member, Timestamp start_time, Timestamp end_time, String title, String detail) {
		this.no = no;
		this.member = member;
		this.start_time = start_time;
		this.end_time = end_time;
		this.title = title;
		this.detail = detail;
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

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Schedule [no=" + no + ", member=" + member + ", start_time=" + start_time + ", end_time=" + end_time
				+ ", title=" + title + ", detail=" + detail + "]";
	}

}

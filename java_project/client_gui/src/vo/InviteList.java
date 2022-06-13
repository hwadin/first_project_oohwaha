package vo;

import java.io.Serializable;

public class InviteList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6693261992837143277L;

	private int no;
	private int code;
	private int member;
	private int participant;
	private boolean is_invited;

	// participant 정보
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InviteList(int no, int code, int member, int participant, boolean is_invited, String id, String name) {
		this.no = no;
		this.code = code;
		this.member = member;
		this.participant = participant;
		this.is_invited = is_invited;
		this.id = id;
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public int getParticipant() {
		return participant;
	}

	public void setParticipant(int participant) {
		this.participant = participant;
	}

	public boolean getIs_invited() {
		return is_invited;
	}

	public void setIs_invited(boolean is_invited) {
		this.is_invited = is_invited;
	}

	public InviteList(int no, int code, int member, int participant, boolean is_invited) {
		this.no = no;
		this.code = code;
		this.member = member;
		this.participant = participant;
		this.is_invited = is_invited;
	}

	public InviteList(int code, int member, int participant) {
		this.code = code;
		this.member = member;
		this.participant = participant;
	}

}

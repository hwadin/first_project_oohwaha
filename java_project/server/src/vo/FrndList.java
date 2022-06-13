package vo;

import java.io.Serializable;

public class FrndList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1454704919732494487L;

	private int no;
	private int member;
	private int friend;
	private boolean isInvited;

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

	public FrndList(int no, int member, int friend, boolean isInvited, String id, String name) {
		this.no = no;
		this.member = member;
		this.friend = friend;
		this.isInvited = isInvited;
		this.id = id;
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getMember() {
		return member;
	}

	public void setMember(int member) {
		this.member = member;
	}

	public int getFriend() {
		return friend;
	}

	public void setFriend(int friend) {
		this.friend = friend;
	}

	public boolean isInvited() {
		return isInvited;
	}

	public void setInvited(boolean isInvited) {
		this.isInvited = isInvited;
	}

	public FrndList(int no, int member, int friend, boolean isInvited) {
		this.no = no;
		this.member = member;
		this.friend = friend;
		this.isInvited = isInvited;
	}

	public FrndList(int member, int friend) {
		this.member = member;
		this.friend = friend;
	}

	public FrndList(int member, int friend, boolean isInvited) {
		this.member = member;
		this.friend = friend;
		this.isInvited = isInvited;
	}

}

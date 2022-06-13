package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import util.DBHelper;
import vo.FrndList;
import vo.InviteList;
import vo.Member;

public class MemberDAO implements IMemberDAO {

	// DB 연결 객체
	Connection conn;
	// Prepare 쿼리 객체
	PreparedStatement pstmt;
	// DB 결과 객체
	ResultSet rs;

	@Override
	public int save(Member member) {
		int result = 0;
		conn = DBHelper.getConnection();
		String query = "insert into member(id, pw, name, age, addr, is_owner) values(?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPw());
			pstmt.setString(3, member.getName());
			pstmt.setInt(4, member.getAge());
			pstmt.setString(5, member.getAddr());
			pstmt.setBoolean(6, member.isOwner());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	// 중복 확인, 사람 찾기 등 ID로만 검색하는 경우
	@Override
	public Member findById(String id) {
		System.out.println("MemberDAO.findById");
		Member member = null;
		conn = DBHelper.getConnection();
		String query = "select no, id from member where id=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new Member(rs.getInt(1), rs.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt);
		}
		System.out.println(member);
		return member;
	}

	@Override
	public Member findByIdAndPw(String id, String pw) {
		System.out.println("MemberDAO.findByIdAndPw");
		Member member = null;
		conn = DBHelper.getConnection();
		String query = "select * from member where id=? and pw=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getBoolean(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt);
		}

		return member;
	}

	@Override
	public Member update(Member member) {
		int result = 0;
		conn = DBHelper.getConnection();
		String query = "update member set pw=?, age=?, addr=? where id=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getPw());
			pstmt.setInt(2, member.getAge());
			pstmt.setString(3, member.getAddr());
			pstmt.setString(4, member.getId());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}
		if (result == 1) {
			return member;
		}
		return null;
	}

	@Override
	public int delete(Member member) {
		int result = 0;
		conn = DBHelper.getConnection();
		String query = "delete from member where id=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getId());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	@Override
	public ArrayList<Member> frdList(Member member) {
		ArrayList<Member> frdList = new ArrayList<>();

		String sql = "SELECT   mem.* FROM frndlist frd, member mem where frd.friend = mem.no and member=? AND frd.is_invited = true\r\n"
				+ "union \r\n"
				+ "select  mem.* from frndlist frd, member mem where frd.member=mem.no and friend=? and frd.is_invited=true";

		conn = DBHelper.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member.getNo());
			pstmt.setInt(2, member.getNo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(5), rs.getString(6));
				frdList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return frdList;
	}

	@Override
	public ArrayList<Member> findId(Member member) {
		ArrayList<Member> findId = new ArrayList<>();
		String sql = "SELECT no,id FROM member WHERE id = ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getInt(1), rs.getString(2));
				findId.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return findId;
	}

	@Override
	public ArrayList<Member> mbList(Member member) {
		ArrayList<Member> mbList = new ArrayList<>();
		String sql = "SELECT * FROM member WHERE id = ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				mbList.add(member);
			}
		} catch (SQLException e) {
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return mbList;
	}

	public Collection<? extends Object> getFrndAlert(Member member) {
		ArrayList<FrndList> frdList = new ArrayList<>();
		String sql = "SELECT frndlist.*, member.id, member.name from frndlist, member where frndlist.member = member.no and friend = ? and is_invited = false";
		conn = DBHelper.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member.getNo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				FrndList frndList = new FrndList(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4),
						rs.getString(5), rs.getString(6));
				frdList.add(frndList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return frdList;
	}

	@Override
	public Collection<? extends Object> getInviteAlert(Member member) {
		ArrayList<InviteList> inviteList = new ArrayList<>();
		String sql = "SELECT invitelist.*, member.id, member.name from invitelist, member where invitelist.member= member.no and participant = ? and is_invited = false";
		conn = DBHelper.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member.getNo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				InviteList invList = new InviteList(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
						rs.getBoolean(5), rs.getString(6), rs.getString(7));
				inviteList.add(invList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return inviteList;
	}

	public int frdAdd(int no, int no2) {
		int result = 0;
		conn = DBHelper.getConnection();

		String sql = "select * from frndlist where (member=? and friend=?) or (friend=? and member=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setInt(2, no2);
			pstmt.setInt(3, no);
			pstmt.setInt(4, no2);

			rs = pstmt.executeQuery();
			if (!rs.next()) {
				sql = "INSERT INTO frndlist(member,friend) VALUES(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, no);
				pstmt.setInt(2, no2);
				result = pstmt.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}

		return result;

	}

	@Override
	public int frdAccept(FrndList frndList) {
		int result = 0;
		conn = DBHelper.getConnection();
		String query = "update frndlist set is_invited=true where no=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, frndList.getNo());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}
		System.out.println("frdAccept: " + result);
		return result;
	}

	@Override
	public int frdReject(FrndList frndList) {
		int result = 0;
		conn = DBHelper.getConnection();
		String query = "delete from frndlist where no=? and is_invited = false";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, frndList.getNo());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}

		return result;
	}

	@Override
	public int frdDelete(int no, int no2) {
		int result = 0;
		conn = DBHelper.getConnection();

		String sql = "DELETE FROM frndlist where (member=? and friend=?) or (friend=? and member=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setInt(2, no2);
			pstmt.setInt(3, no);
			pstmt.setInt(4, no2);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}

		return result;

	}

}

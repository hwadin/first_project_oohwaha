package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.DBHelper;
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
		String sql = "SELECT  frd.friend, mem.id, mem.name FROM frndlist frd, member mem where frd.friend = mem.no and member=?;";
		conn = DBHelper.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member.getNo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3));
				frdList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return frdList;
	}

}

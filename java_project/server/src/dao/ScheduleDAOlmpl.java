package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.DBHelper;
import vo.Schedule;

public class ScheduleDAOlmpl implements IScheduleDAO {

	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	CallableStatement cstmt;
	ResultSet rs;

	// Schedule 정보를 넘겨받아서 글쓰기 진행
	@Override
	public int save(Schedule schedule) {
		int result = 0;
		conn = DBHelper.getConnection();
		try {
			String sql = "INSERT INTO member_schedule(member_no,start_time,end_time) VALUES(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, schedule.getMember());
			pstmt.setString(2, schedule.getStart_time());
			pstmt.setString(3, schedule.getEnd_time());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	// 아이디로 스케줄 검색
	@Override
	public ArrayList<Schedule> find(int member_no) {
		ArrayList<Schedule> list = new ArrayList<>();
		Schedule schedule = null;
		String sql = "SELECT * FROM member_schedule WHERE member_no= " + member_no;
		conn = DBHelper.getConnection();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				schedule = new Schedule(rs.getInt("no"), rs.getInt("member"), rs.getString("start_time"),
						rs.getString("end_time"));
				list.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt);
		}
		return list;
	}

	@Override
	public int update(Schedule schedule) {
		int result = 0;

		String sql = "UPDATE member_schedule SET start_time = ?, emd_time =? WHERE no = ?";

		conn = DBHelper.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, schedule.getStart_time());
			pstmt.setString(2, schedule.getEnd_time());
			pstmt.setInt(3, schedule.getNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	@Override
	public int delete(Schedule schedule) {
		int result = 0;
		conn = DBHelper.getConnection();
		String sql = "DELETE FROM member_schedule WHERE no = " + schedule;
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(stmt);
		}
		return result;
	}

}

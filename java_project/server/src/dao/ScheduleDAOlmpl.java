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
			String sql = "INSERT INTO member_schedule(member,start_time,end_time,title,detail) VALUES(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, schedule.getMember());
			pstmt.setTimestamp(2, schedule.getStart_time());
			pstmt.setTimestamp(3, schedule.getEnd_time());
			pstmt.setString(4, schedule.getTitle());
			pstmt.setString(5, schedule.getDetail());
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
		String sql = "SELECT * FROM member_schedule WHERE member= " + member_no;
		conn = DBHelper.getConnection();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				schedule = new Schedule(rs.getInt("no"), rs.getInt("member"), rs.getTimestamp("start_time"),
						rs.getTimestamp("end_time"), rs.getString("title"), rs.getString("detail"));
				list.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt);
		}
		return list;
	}

	public ArrayList<Schedule> findWeek(int member_no) {
		ArrayList<Schedule> list = new ArrayList<>();
		Schedule schedule = null;
		String sql = "select * from member_schedule "
				+ "where ((start_time between date_add(curdate(), interval -dayofweek(curdate())+1 day) and date_add(curdate(), interval 7-dayofweek(curdate()) day)) "
				+ " or (end_time between date_add(curdate(), interval -dayofweek(curdate())+1 day) and date_add(curdate(), interval 7-dayofweek(curdate()) day))) "
				+ "and member = " + member_no;
		conn = DBHelper.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				schedule = new Schedule(rs.getInt("no"), rs.getInt("member"), rs.getTimestamp("start_time"),
						rs.getTimestamp("end_time"), rs.getString("title"), rs.getString("detail"));
				list.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt);
		}
		return list;
	}

	public Schedule findByNo(int no) {
		Schedule schedule = null;
		String sql = "SELECT * FROM member_schedule WHERE no= " + no;
		conn = DBHelper.getConnection();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				schedule = new Schedule(rs.getInt("no"), rs.getInt("member"), rs.getTimestamp("start_time"),
						rs.getTimestamp("end_time"), rs.getString("title"), rs.getString("detail"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, stmt);
		}
		return schedule;
	}

	@Override
	public int update(Schedule schedule) {
		int result = 0;

		String sql = "UPDATE member_schedule SET start_time = ?, end_time =?, title=?, detail=? WHERE no = ?";

		conn = DBHelper.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, schedule.getStart_time());
			pstmt.setTimestamp(2, schedule.getEnd_time());
			pstmt.setString(3, schedule.getTitle());
			pstmt.setString(4, schedule.getDetail());
			pstmt.setInt(5, schedule.getNo());
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
		String sql = "DELETE FROM member_schedule WHERE no = " + schedule.getNo();
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

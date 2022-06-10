package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.DBHelper;
import vo.Reservation;

public class ReservationDAOImpl implements IReservationDAO {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	@Override
	public int save(Reservation reservation) {
		int result = 0;
		conn = DBHelper.getConnection();
		String sql = "INSERT INTO reservation(cafe,member,time,is_dutch,is_apply) VALUES(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reservation.getCafe());
			pstmt.setInt(2, reservation.getMember());
			pstmt.setTimestamp(3, reservation.getTime());
			pstmt.setBoolean(4, reservation.isDutch());
			pstmt.setBoolean(5, reservation.isApply());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	@Override
	public ArrayList<Reservation> select(int member) {
		ArrayList<Reservation> reservationList = new ArrayList<>();
		conn = DBHelper.getConnection();
		String sql = "SELECT * FROM reservation WHERE no = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Reservation reservation = new Reservation(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getTimestamp(4),
						rs.getBoolean(5), rs.getBoolean(6));
				reservationList.add(reservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return reservationList;
	}

	@Override
	public int update(Reservation reservation) {
		int result = 0;
		conn = DBHelper.getConnection();
		String sql = "UPDATE reservation SET cafe=? member=? time=? WHERE no= ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reservation.getCafe());
			pstmt.setInt(2, reservation.getMember());
			pstmt.setTimestamp(3, reservation.getTime());
			pstmt.setInt(4, reservation.getNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}

		try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int delete(Reservation reservation) {
		int result = 0;
		conn = DBHelper.getConnection();
		String sql = "DELETE FROM reservation WHERE no = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reservation.getNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

}

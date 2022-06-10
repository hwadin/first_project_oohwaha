package dao;

import java.util.ArrayList;

import vo.Reservation;

// DBHelper 사용해서 DB 접근해서 CRUD(Create, Read, Update, Delete)기능
public interface IReservationDAO {

	// 예약
	int save(Reservation reservation);

	// 예약정보 검색
	ArrayList<Reservation> select(int no);

	// 예약 수정
	int update(Reservation reservation);

	// 예약 삭제
	int delete(Reservation reservation);

}

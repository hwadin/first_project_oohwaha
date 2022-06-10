package service;

import java.util.ArrayList;

import vo.Reservation;

public interface IReservationService {

	// 예약 등록
	int insert(Reservation reservation);

	// 예약 수정
	int update(Reservation reservation);

	// 예약 확인
	ArrayList<Reservation> select(int memberNo);

	// 예약 삭제
	int delete(Reservation reservation);
}

package service;

import java.util.ArrayList;

import dao.IReservationDAO;
import dao.ReservationDAOImpl;
import vo.Reservation;

public class ReservationService implements IReservationService {

	IReservationDAO dao;

	public ReservationService() {
		dao = new ReservationDAOImpl();
	}

	@Override
	public int insert(Reservation reservation) {
		int num = dao.save(reservation);
		return num;
	}

	@Override
	public int update(Reservation reservation) {
		int num = dao.update(reservation);
		return num;
	}

	@Override
	public ArrayList<Reservation> select(int memberNo) {
		ArrayList<Reservation> list = dao.select(memberNo);
		return list;
	}

	@Override
	public int delete(Reservation reservation) {
		int num = dao.delete(reservation);
		return num;
	}

}

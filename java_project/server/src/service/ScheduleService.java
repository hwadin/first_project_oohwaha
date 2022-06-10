package service;


import java.util.ArrayList;

import dao.IScheduleDAO;
import dao.ScheduleDAOlmpl;
import vo.Schedule;

public class ScheduleService implements IScheduleService{
	
	IScheduleDAO dao;

	public ScheduleService() {
		dao = new ScheduleDAOlmpl();
	}

	@Override
	public int save(Schedule schedule) {
		int  s = dao.save(schedule);
		return s;
	}


	@Override
	public ArrayList<Schedule> find(int member_no) {
		ArrayList<Schedule> array = dao.find(member_no);
		return array;
	}

	@Override
	public int update(Schedule schedule) {
		int u = dao.update(schedule);
		return u;
	}

	@Override
	public int delete(Schedule schedule) {
		int d = dao.delete(schedule);
		return d;
	}

}

package service;

import java.util.ArrayList;

import vo.Schedule;

public interface IScheduleService {

	int save(Schedule schedule);
	
	ArrayList<Schedule> find(int member_no);
	
	int update(Schedule schedule);
	
	int delete(Schedule schedule);
	
}

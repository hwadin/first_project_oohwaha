package dao;

import java.util.ArrayList;

import vo.Schedule;

public interface IScheduleDAO {

	
	// 스케줄 쓰기
	int save(Schedule schedule);
	
	// 스케줄 목록 검색
	ArrayList<Schedule> find(int member_no);
	
	// 회원정보 수정
	int update(Schedule schedule);
	
	// 회원 탈퇴
	int delete(Schedule schedule);


}

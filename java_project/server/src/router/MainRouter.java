package router;

import java.util.ArrayList;

import network_dto.NetworkData;
import service.IMemberService;
import service.IReservationService;
import service.MemberService;
import service.ReservationService;
import service.ScheduleService;
import vo.FrndList;
import vo.Member;
import vo.Reservation;
import vo.Schedule;

public class MainRouter {
	static IMemberService memberService = new MemberService();
	static IReservationService reservationService = new ReservationService();
	static ScheduleService scheduleService = new ScheduleService();

	public static NetworkData<?> route(NetworkData<?> data) {
		NetworkData<?> returnData = null;
		System.out.println("router 진입");
		String actionClass = data.getAction().split("/")[0];
		System.out.println("actionClass : " + actionClass);
		switch (actionClass) {
		case "member":
			returnData = memberRoute(data);
			break;
		case "schedule": // 화진
			returnData = scheduleRoute(data);
			break;
		case "reservation": // 옥형
			returnData = reservationRoute(data);
			break;
		case "cafe": // cafe/general/ cafe/menu cafe/schedule
			String cafeClass = data.getAction().split("/")[1];
			switch (cafeClass) {
			case "general":
				cafeGeneralRoute(data);
				break;
			case "menu":
				cafeMenuRoute(data);
				break;
			case "schedule":
				cafeScheduleRoute(data);
				break;
			}
			break;
		}
		return returnData;
	}

	private static void cafeScheduleRoute(NetworkData<?> data) {

	}

	private static void cafeMenuRoute(NetworkData<?> data) {

	}

	private static void cafeGeneralRoute(NetworkData<?> data) {

	}

	@SuppressWarnings("unchecked")
	private static NetworkData<?> memberRoute(NetworkData<?> data) {
		NetworkData<?> returnData = null;
		Member resultMember = null;
		Integer resultInt = null;
		System.out.println();
		String action = data.getAction().split("/")[1];
		System.out.println("memberRouter 진입 || action : " + action);
		Member member = null;
		ArrayList<Member> frdAddList = null;
		FrndList frndList = null;
		ArrayList<Object> alertList = null;
		if (data.getV() instanceof Member) {
			member = (Member) data.getV();
		} else if (data.getV() instanceof ArrayList<?>) {
			frdAddList = (ArrayList<Member>) data.getV();
		} else if (data.getV() instanceof FrndList) {
			frndList = (FrndList) data.getV();
		}
		switch (action) {
		case "login":
			resultMember = memberService.login(member);
			returnData = new NetworkData<Member>("member/login", resultMember);
			break;
		case "find": // 아이디로 회원이 존재하는지 검색
			resultMember = memberService.find(member);
			returnData = new NetworkData<Member>("member/find", resultMember);
			break;
		case "join":
			resultInt = memberService.join(member);
			returnData = new NetworkData<Integer>("member/join", resultInt);
			break;
		case "update":
			resultMember = memberService.update(member);
			returnData = new NetworkData<Member>("member/update", resultMember);
			break;
		case "delete":
			resultInt = memberService.delete(member);
			returnData = new NetworkData<Integer>("member/delete", resultInt);
			break;
		case "frdList":
			ArrayList<Member> list = memberService.frdList(member);
			returnData = new NetworkData<ArrayList<Member>>("member/frdList", list);
			break;

		case "findId":
			ArrayList<Member> list2 = memberService.findId(member);
			returnData = new NetworkData<ArrayList<Member>>("member/findId", list2);
			break;

		case "alert":
			alertList = memberService.getAlert(member);
			returnData = new NetworkData<ArrayList<Object>>("member/alert", alertList);
			break;
		case "frdAdd":
			resultInt = memberService.frdAdd(frdAddList);
			returnData = new NetworkData<Integer>("member/frdAdd", Integer.valueOf(resultInt));
			break;

		case "frdDelete":
			resultInt = memberService.frdDelete(frdAddList);
			returnData = new NetworkData<Integer>("member/frdDelete", Integer.valueOf(resultInt));
			break;

		case "frdAccept":
			resultInt = memberService.frdAccept(frndList);
			Member m = new Member(frndList.getFriend());
			alertList = memberService.getAlert(m);
			if (resultInt == 1) {
				System.out.println("친구 수락 후 알림 전송");
				returnData = new NetworkData<ArrayList<Object>>("member/alert", alertList);
			}
			break;
		case "frdReject":
			resultInt = memberService.frdReject(frndList);
			Member m1 = new Member(frndList.getFriend());
			alertList = memberService.getAlert(m1);
			if (resultInt == 1) {
				returnData = new NetworkData<ArrayList<Object>>("member/alert", alertList);
			}
			break;
		}
		return returnData;
	}

	private static NetworkData<?> reservationRoute(NetworkData<?> data) {
		NetworkData<?> returnData = null;
		Integer resultInt = null;
		Member member = null;
		System.out.println();
		System.out.println("reservationRouter 진입");
		String action = data.getAction().split("/")[1];
		System.out.println("action : " + action);
		Reservation reservation = (Reservation) data.getV();
		if (data.getV() instanceof Reservation) {
			reservation = (Reservation) data.getV();
		} else if (data.getV() instanceof Member) {
			member = (Member) data.getV();
		}
		switch (action) {
		case "insert":
			resultInt = reservationService.insert(reservation);
			returnData = new NetworkData<Integer>("reservation/insert", resultInt);
			break;
		case "select":
			ArrayList<Reservation> list = reservationService.select(member.getNo());
			returnData = new NetworkData<ArrayList<Reservation>>("reservation/select", list);
			break;
		case "update":
			resultInt = reservationService.insert(reservation);
			returnData = new NetworkData<Integer>("reservation/update", resultInt);
			break;
		case "delete":
			resultInt = reservationService.insert(reservation);
			returnData = new NetworkData<Integer>("reservation/delete", resultInt);
			break;
		}
		return returnData;
	}

	private static NetworkData<?> scheduleRoute(NetworkData<?> data) {
		NetworkData<?> returnData = null;
		Integer resultInt = null;
		Schedule schedule = null;
		Member member = null;
		ArrayList<Schedule> s = null;
		String action = data.getAction().split("/")[1];
		System.out.println("scheduleRouter 진입 || action : " + action);
		if (data.getV() instanceof Schedule) {
			schedule = (Schedule) data.getV();
		} else if (data.getV() instanceof Member) {
			member = (Member) data.getV();
		}

		switch (action) {
		case "save": // 스케줄 등록
			resultInt = scheduleService.save(schedule);
			returnData = new NetworkData<Integer>("schedule/save", resultInt);
			break;
		case "find": // 스케줄 검색
			s = scheduleService.find(member.getNo());
			returnData = new NetworkData<ArrayList<Schedule>>("schedule/find", s);
			break;
		case "findByNo": // 스케줄 번호로 검색
			schedule = scheduleService.findByNo(schedule.getNo());
			returnData = new NetworkData<Schedule>("schedule/findByNo", schedule);
			break;
		case "findWeek":
			s = scheduleService.findWeek(member.getNo());
			returnData = new NetworkData<ArrayList<Schedule>>("schedule/findWeek", s);
			break;
		case "update": // 스케줄 수정
			resultInt = scheduleService.update(schedule);
			returnData = new NetworkData<Integer>("schedule/update", resultInt);
			break;
		case "delete": // 스케줄 삭제
			resultInt = scheduleService.delete(schedule);
			returnData = new NetworkData<Integer>("schedule/delete", resultInt);
			break;

		}

		return returnData;
	}

}

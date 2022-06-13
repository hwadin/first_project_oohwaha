package router;

import java.io.IOException;
import java.util.ArrayList;

import application.Connector;
import application.Main;
import application.SceneLoader;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import network_dto.NetworkData;
import service.MemberService;
import service.ScheduleService;
import vo.Member;
import vo.Schedule;

public class MainRouter {
	NetworkData<?> data;

	public static Stage stage;

	public static MemberService memberService = new MemberService();
	public static ScheduleService scheduleService = new ScheduleService();

	public NetworkData<?> route(NetworkData<?> data) {
		String action = data.getAction();
		Object value = data.getV();

		if (Main.loginMember == null) {
			Member m = null;
			if (value instanceof Member) {
				m = (Member) value;
			}
			switch (action) {
			case "member/login":
				login(m);
				break;
			case "member/join":
				join(m);
				break;
			case "member/find":

				find(m);
				break;
			}
		} else {
			String actionClass = action.split("/")[0];
			switch (actionClass) {
			case "member":
				memberRoute(data);
				break;
			case "schedule":
				scheduleRoute(data);
				break;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private void scheduleRoute(NetworkData<?> data) {
		String action = data.getAction().split("/")[1];
		int result = 0;
		BorderPane borderPane;
		AnchorPane monthCal;
		switch (action) {
		case "find":
			ArrayList<Schedule> scheList = (ArrayList<Schedule>) data.getV();
			scheduleService.getAllSchedule(scheList);
			borderPane = (BorderPane) ScheduleService.border;
			Platform.runLater(() -> {
				stage.setScene(borderPane.getScene());
			});
			break;
		case "save":
			result = (Integer) data.getV();
			if (result == 1) {
				Connector.send(new NetworkData<Member>("schedule/find", Main.loginMember));
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("일정 등록 실패");
				alert.setHeaderText("일정 등록에 실패했습니다.");
				alert.show();
			}
			break;
		case "findWeek":
			ArrayList<Schedule> scheWeekList = (ArrayList<Schedule>) data.getV();
			scheduleService.getWeekSchedule(scheWeekList);
			borderPane = (BorderPane) ScheduleService.border;
			Platform.runLater(() -> {
				stage.setScene(borderPane.getScene());
			});
			break;

		case "findByNo":
			Schedule schedule = (Schedule) data.getV();
			scheduleService.getDetailSchedule(schedule);
			break;
		case "update":
			result = (Integer) data.getV();
			if (result == 1) {
//				Connector.send(new NetworkData<Member>("schedule/find", Main.loginMember));
				monthCal = (AnchorPane) Main.sceneLoader.load(SceneLoader.M_SCHEDULE_PATH);
				borderPane = (BorderPane) ScheduleService.border;
				ScheduleService.setCalendar(monthCal);
				Platform.runLater(() -> {
					borderPane.setCenter(monthCal);
				});
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("일정 등록 실패");
				alert.setHeaderText("일정 등록에 실패했습니다.");
				alert.show();
			}
			break;
		case "delete":
			result = (Integer) data.getV();
			if (result == 1) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("일정 삭제");
					alert.setHeaderText("일정 삭제 완료.");
					alert.show();
					alert.setOnCloseRequest(ev -> {
						AnchorPane exmonthCal = (AnchorPane) Main.sceneLoader.load(SceneLoader.M_SCHEDULE_PATH);
						BorderPane exborderPane = (BorderPane) ScheduleService.border;
						ScheduleService.setCalendar(exmonthCal);
						exborderPane.setCenter(exmonthCal);
					});
				});

			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("일정 삭제 실패");
				alert.setHeaderText("일정 등록에 실패했습니다.");
				alert.show();
			}
			break;
		}

	}

	private void memberRoute(NetworkData<?> data) {
		String action = data.getAction().split("/")[1];
		switch (action) {
		case "frdList":
			ArrayList<Member> frdList = (ArrayList<Member>) data.getV();
			memberService.frdList(frdList);
			break;

		case "update":
			if (data.getV() != null) {
				Member member = (Member) data.getV();
				memberService.update(member);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("회원 정보 수정 실패");
				alert.setHeaderText("수정에 실패했습니다.");
				alert.show();
			}
			break;
		case "delete":

			memberService.delete(data);

		case "findId":
			ArrayList<Member> findID = (ArrayList<Member>) data.getV();
			memberService.findId(findID);
			break;

		case "frdAdd":
			int result = (Integer) data.getV();
			Platform.runLater(() -> {
				if (result == 1) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("친구 요청");
					alert.setHeaderText("친구 요청 성공!");
					alert.setContentText("친구 요청이 완료되었습니다.");
					alert.show();
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("친구 요청");
					alert.setHeaderText("친구 요청 실패!");
					alert.setContentText("친구 요청이 실패하였습니다.");
					alert.show();
				}
			});
		}
	}

	private void login(Member member) {
		if (member != null) {
			Main.loginMember = member;
			try {
				BorderPane userMain = FXMLLoader.load(getClass().getResource("../view/UserMain.fxml"));
				Platform.runLater(() -> {
					stage.setScene(new Scene(userMain));
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("로그인 실패");
				alert.setHeaderText("계정 정보를 확인하세요.");
				alert.show();
			});
		}
	}

	private void join(Member m) {
		if (m == null) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("성공적으로 회원가입되었습니다.");
				alert.show();
			});
		} else {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("이미 존재하는 회원입니다.");
				alert.show();
			});
		}
	}

	private void find(Member member) {
		if (member == null) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("사용가능한 아이디입니다.");
				alert.show();
			});

		} else {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("중복된 아이디입니다.");
				alert.show();
			});

		}

	}
}

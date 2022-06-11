package router;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

	static MemberService memberService = new MemberService();
	static ScheduleService scheduleService = new ScheduleService();

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
				break;
			}
		}
		String actionClass = action.split("/")[0];
		switch (actionClass) {
		case "member":
			memberRoute(data);
			break;
		case "schedule":
			scheduleRoute(data);
			break;
		}
		return null;
	}

	private void scheduleRoute(NetworkData<?> data) {
		String action = data.getAction().split("/")[1];
		switch (action) {
		case "find":
			ArrayList<Schedule> scheList = (ArrayList<Schedule>) data.getV();
			scheduleService.getAllSchedule(scheList);
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
		}
	}

	private void login(Member member) {
		Main.loginMember = member;
		try {
			BorderPane userMain = FXMLLoader.load(getClass().getResource("../view/UserMain.fxml"));
			Platform.runLater(() -> {
				stage.setScene(new Scene(userMain));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import controller.MonthScheduleController;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import vo.Schedule;

public class ScheduleService {
	public static Parent border;
	public static Parent calendar;

	public static void setTarget(Parent t) {
		border = t;
	}

	public static void setCalendar(Parent t) {
		calendar = t;
	}

	public static ArrayList<VBox> boxList;

	public static void setBoxList(ArrayList<VBox> boxList) {
		ScheduleService.boxList = boxList;
	}

	public void getAllSchedule(ArrayList<Schedule> data) {
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		BorderPane borderPane = (BorderPane) border;
		System.out.println(data);
		MonthScheduleController.setMemberSchedule(data);
		for (Schedule s : data) {
			Timestamp startDate = s.getStart_time();
			Timestamp endDate = s.getEnd_time();
			String title = s.getTitle();
			String detail = s.getDetail();
			for (VBox b : boxList) {

				if (((Label) b.getChildren().get(0)).getText().equals(sdf.format(startDate))) {
					Label lbl = new Label();
					lbl.setText(title);
					Platform.runLater(() -> {
						b.getChildren().add(lbl);
					});
				}
			}
		}
		AnchorPane monthCal = (AnchorPane) calendar;
		borderPane.setCenter(monthCal);
	}
}

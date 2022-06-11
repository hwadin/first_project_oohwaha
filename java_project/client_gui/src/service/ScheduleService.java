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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyM-d");
		BorderPane borderPane = (BorderPane) border;

//		String year = ((Label) calendar.lookup("#year")).getText();
		String month = ((Label) calendar.lookup("#year")).getText().split(" ")[0]
				+ ((Label) calendar.lookup("#month")).getText();
		System.out.println(month);

		MonthScheduleController.setMemberSchedule(data);
		for (Schedule s : data) {
			Timestamp startDate = s.getStart_time();
			Timestamp endDate = s.getEnd_time();
			String title = s.getTitle();
			String detail = s.getDetail();

			if (sdf.format(startDate).startsWith(month)) {
				if (sdf.format(endDate).startsWith(month)) {
					for (VBox b : boxList) {
						String day = ((Label) b.getChildren().get(0)).getText();
						System.out.println(day);
						System.out.println(sdf.format(startDate).split("-")[1]);
						System.out.println(sdf.format(endDate).split("-")[1]);
						if (day.equals("")
								&& Integer.parseInt(day) >= Integer.parseInt(sdf.format(startDate).split("-")[1])
								|| Integer.parseInt(day) <= Integer.parseInt(sdf.format(endDate).split("-")[1])) {
							Label lbl = new Label();
							lbl.setText(title);
							Platform.runLater(() -> {
								b.getChildren().add(lbl);
							});
						}
					}
				} else {
					for (VBox b : boxList) {
						String day = ((Label) b.getChildren().get(0)).getText();
						if (Integer.parseInt(day) >= Integer.parseInt(sdf.format(startDate).split("-")[1])) {
							Label lbl = new Label();
							lbl.setText(title);
							Platform.runLater(() -> {
								b.getChildren().add(lbl);
							});
						}
					}
				}
			}
		}
		AnchorPane monthCal = (AnchorPane) calendar;
		borderPane.setCenter(monthCal);
	}
}

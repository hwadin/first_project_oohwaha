package service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import application.Main;
import application.SceneLoader;
import controller.MonthScheduleController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
//		BorderPane borderPane = (BorderPane) border;
		String month = ((Label) calendar.lookup("#year")).getText().split(" ")[0] + "-"
				+ ((Label) calendar.lookup("#month")).getText();

		for (VBox b : boxList) {
			ObservableList<Node> bList = b.getChildren();
			if (bList.size() > 1) {
				Label tmp = (Label) bList.get(0);
				Platform.runLater(() -> {
					bList.clear();
					bList.add(tmp);
				});
			}
		}

		MonthScheduleController.setMemberSchedule(data);
		for (Schedule s : data) {
			String startDate = sdf.format(s.getStart_time());
			Timestamp endDate = s.getEnd_time();
			String title = s.getTitle();
			String detail = s.getDetail();

			for (VBox b : boxList) {

				String day = ((Label) b.getChildren().get(0)).getText();
				try {
					if (!day.equals("")) {
						Date vDate = sdf.parse(month + "-" + day);
						if ((vDate.after(sdf.parse(startDate)) || vDate.equals(sdf.parse(startDate)))
								&& vDate.before(endDate)) {
							Label lbl = new Label();

							lbl.setText(s.getTitle());

							Platform.runLater(() -> {
								b.getChildren().add(lbl);
							});

						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		Platform.runLater(() -> {
			for (VBox b : boxList) {
				ObservableList<Node> bList = b.getChildren();
				if (bList.size() > 3) {
					Label tmp1 = (Label) bList.get(0);
					Label tmp2 = (Label) bList.get(1);
					bList.clear();
					bList.add(tmp1);
					bList.add(tmp2);
					bList.add(new Label("..."));
				}
			}
		});

	}

	public void getDetailSchedule(Schedule schedule) {
		BorderPane borderPane = (BorderPane) border;
		AnchorPane updateSchedule = (AnchorPane) Main.sceneLoader.load(SceneLoader.UPDATE_SCHE_PATH);
		// 업데이트 스케쥴 창에 데이터 채워넣기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Label scheNo = (Label) updateSchedule.lookup("#scheNo");
		TextField start_time = (TextField) updateSchedule.lookup("#start_time");
		TextField end_time = (TextField) updateSchedule.lookup("#end_time");
		TextField title = (TextField) updateSchedule.lookup("#title");
		TextField detail = (TextField) updateSchedule.lookup("#detail");

		scheNo.setText(Integer.toString(schedule.getNo()));
		start_time.setText(sdf.format(schedule.getStart_time()));
		end_time.setText(sdf.format(schedule.getEnd_time()));
		title.setText(schedule.getTitle());
		detail.setText(schedule.getDetail());

		Platform.runLater(() -> {
			borderPane.setCenter(updateSchedule);
		});
	}

	public void getWeekSchedule(ArrayList<Schedule> data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Schedule s : data) {
			String startDate = sdf.format(s.getStart_time());
			Timestamp endDate = s.getEnd_time();
			String title = s.getTitle();
			String detail = s.getDetail();

			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK) + 1);
			for (VBox b : boxList) {

				try {
					Date vDate = cal.getTime();
					if ((vDate.after(sdf.parse(startDate)) || vDate.equals(sdf.parse(startDate)))
							&& vDate.before(endDate)) {
						Label lbl = new Label();

						lbl.setText(s.getTitle());

						Platform.runLater(() -> {
							b.getChildren().add(lbl);
						});

					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				cal.add(Calendar.DATE, 1);
			}
		}
//		Platform.runLater(() -> {
//			for (VBox b : boxList) {
//				ObservableList<Node> bList = b.getChildren();
//				if (bList.size() > 3) {
//					Label tmp1 = (Label) bList.get(0);
//					Label tmp2 = (Label) bList.get(1);
//					bList.clear();
//					bList.add(tmp1);
//					bList.add(tmp2);
//					bList.add(new Label("..."));
//				}
//			}
//		});
	}

}

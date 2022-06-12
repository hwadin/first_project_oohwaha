package controller;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import network_dto.NetworkData;
import service.ScheduleService;
import vo.Schedule;

public class UpdateScheduleController implements Initializable {

	@FXML
	private TextField start_time, end_time, title, detail;

	@FXML
	private Button btnAccept, btnCancel, btnDelete;

	@FXML
	private ComboBox<String> newStartTime, newEndTime;

	@FXML
	private DatePicker newStartDate, newEndDate;

	@FXML
	private Label scheNo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		newStartTime.getSelectionModel().selectFirst();
		newEndTime.getSelectionModel().selectFirst();

		newStartDate.setOnAction(ev2 -> {
			restrictDatePicker(newEndDate, newStartDate.getValue(), newStartDate.getValue().plusYears(2));
		});

		btnAccept.setOnAction(ev -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				int no = Integer.parseInt(scheNo.getText());
				String title = this.title.getText();
				String detail = this.detail.getText();
				Timestamp startDate = new Timestamp(sdf.parse(start_time.getText()).getTime());
				Timestamp endDate = new Timestamp(sdf.parse(end_time.getText()).getTime());
				if (newStartDate.getValue() != null && newEndDate.getValue() != null) {
					startDate = new Timestamp(
							sdf.parse(newStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " "
									+ (String) newStartTime.getValue()).getTime());
					endDate = new Timestamp(
							sdf.parse(newEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " "
									+ (String) newEndTime.getValue()).getTime());
				}

				Connector.send(new NetworkData<Schedule>("schedule/update",
						new Schedule(no, Main.loginMember.getNo(), startDate, endDate, title, detail)));

			} catch (Exception e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("일정 수정 실패");
				alert.setHeaderText("값을 확인하세요.");
				alert.show();
			}
		});

		btnCancel.setOnAction(ev -> {
			AnchorPane monthCal = (AnchorPane) ScheduleService.calendar;// (AnchorPane)
			// Main.sceneLoader.load(SceneLoader.M_SCHEDULE_PATH);
			BorderPane borderPane = (BorderPane) ScheduleService.border;
			borderPane.setCenter(monthCal);
		});

		btnDelete.setOnAction(ev -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("삭제 확인");
			alert.setHeaderText("정말로 일정을 삭제하시겠습니까?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				Connector.send(new NetworkData<Schedule>("schedule/delete",
						new Schedule(Integer.parseInt(scheNo.getText()), Main.loginMember.getNo())));
			} else if (result.get() == ButtonType.CANCEL) {
				alert.close();
			}
		});
	}

	public void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isBefore(minDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						} else if (item.isAfter(maxDate)) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						}
					}
				};
			}
		};
		datePicker.setDayCellFactory(dayCellFactory);
	}

}

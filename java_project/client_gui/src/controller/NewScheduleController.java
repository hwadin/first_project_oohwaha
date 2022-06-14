package controller;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Popup;
import javafx.util.Callback;
import network_dto.NetworkData;
import vo.Schedule;

public class NewScheduleController implements Initializable {

	@FXML
	private TextField txtTitle, txtPlace;

	@FXML
	private ComboBox<String> startTime, endTime;

	@FXML
	private DatePicker endDate;

	@FXML
	private Label today;

	@FXML
	private Button btnAccept, btnCancel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		startTime.getSelectionModel().selectFirst();
		endTime.getSelectionModel().selectFirst();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		btnAccept.setOnAction(ev -> {
			try {
				String title = txtTitle.getText();
				String detail = txtPlace.getText();
				Timestamp start_date = new Timestamp(
						sdf.parse(today.getText() + " " + (String) startTime.getValue()).getTime());
				Timestamp end_date = new Timestamp(
						sdf.parse(endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " "
								+ (String) endTime.getValue()).getTime());
				Connector.send(new NetworkData<Schedule>("schedule/save",
						new Schedule(Main.loginMember.getNo(), start_date, end_date, title, detail)));
				Popup pop = (Popup) btnAccept.getScene().getWindow();
				pop.hide();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("일정 등록 실패");
				alert.setHeaderText("값을 확인하세요.");
				alert.show();
			}
		});

		endDate.setOnMouseEntered(ev2 -> {
			restrictDatePicker(endDate, LocalDate.parse(today.getText()),
					LocalDate.of(Integer.parseInt(today.getText().split("-")[0]) + 1, Month.DECEMBER, 31));
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

package controller;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.Connector;
import application.Main;
import application.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import network_dto.NetworkData;
import service.ScheduleService;
import vo.Member;
import vo.Schedule;

public class MonthScheduleController implements Initializable {

	@FXML
	private Button btnNext, btnPrev;

	@FXML
	private GridPane grid;

	@FXML
	private VBox box01, box02, box03, box04, box05, box06, box07, box08, box09, box10, box11, box12, box13, box14,
			box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25, box26, box27, box28, box29,
			box30, box31, box32, box33, box34, box35, box36, box37, box38, box39, box40, box41, box42;

	@FXML
	private Label lbl01, lbl02, lbl03, lbl04, lbl05, lbl06, lbl07, lbl08, lbl09, lbl10, lbl11, lbl12, lbl13, lbl14,
			lbl15, lbl16, lbl17, lbl18, lbl19, lbl20, lbl21, lbl22, lbl23, lbl24, lbl25, lbl26, lbl27, lbl28, lbl29,
			lbl30, lbl31, lbl32, lbl33, lbl34, lbl35, lbl36, lbl37, lbl38, lbl39, lbl40, lbl41, lbl42, lblYear,
			lblMonth;

	Calendar cal;
	ArrayList<VBox> boxList;

	int year;
	int month;

	public static ArrayList<Schedule> memberSchedule;

	public static void setMemberSchedule(ArrayList<Schedule> memberSchedule) {
		MonthScheduleController.memberSchedule = memberSchedule;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boxList = new ArrayList<>(Arrays.asList(box01, box02, box03, box04, box05, box06, box07, box08, box09, box10,
				box11, box12, box13, box14, box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25,
				box26, box27, box28, box29, box30, box31, box32, box33, box34, box35, box36, box37, box38, box39, box40,
				box41, box42));

		cal = Calendar.getInstance();
		Date currentDate = new Date();
		cal.setTime(currentDate);

		drawCal();

		btnNext.setOnAction(ev -> {
			cal.add(Calendar.MONTH, 1);
			drawCal();
		});
		btnPrev.setOnAction(ev -> {
			cal.add(Calendar.MONTH, -1);
			drawCal();
		});

		for (VBox v : boxList) {
			v.setOnMouseClicked(ev -> {
				initColorCal();
				v.setStyle("-fx-background-color:rgba(255,129,129,0.5);");
				Popup pop = new Popup();

				AnchorPane monthDetail = (AnchorPane) Main.sceneLoader.load(SceneLoader.M_SCHE_DETAIL_PATH);

				TableView<Schedule> table = (TableView) monthDetail.getChildren().get(1);

				TableColumn<Schedule, ?> tColumnNo = table.getColumns().get(0);
				tColumnNo.setCellValueFactory(new PropertyValueFactory<>("no"));

				TableColumn<Schedule, ?> tColumnStart = table.getColumns().get(1);
				tColumnStart.setCellValueFactory(new PropertyValueFactory<>("start_time"));

				TableColumn<Schedule, ?> tColumnTitle = table.getColumns().get(2);
				tColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

				ObservableList<Schedule> tableList = FXCollections.observableArrayList();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (Schedule s : memberSchedule) {
					String startDate = sdf.format(s.getStart_time());
					Timestamp endDate = s.getEnd_time();
					String title = s.getTitle();
					String detail = s.getDetail();
					String day = ((Label) v.getChildren().get(0)).getText();
					Date vDate;
					try {
						vDate = sdf.parse(year + "-" + (month + 1) + "-" + day);
						if ((vDate.after(sdf.parse(startDate)) || vDate.equals(sdf.parse(startDate)))
								&& vDate.before(endDate)) {
							tableList.add(s);
						}
						Timestamp thatDay = new Timestamp(vDate.getTime());
						Label today = (Label) monthDetail.lookup("#today");
						today.setText(sdf.format(vDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}

				}
				table.setItems(tableList);

				pop.getContent().add(monthDetail);
				pop.setAutoHide(true);
				pop.setX(ev.getScreenX());
				pop.setY(ev.getScreenY() - 150);
				pop.show(MainController.stage);
			});
		}

	}

	void initColorCal() {
		for (VBox v : boxList) {
			v.setStyle("-fx-background-color:rgba(0,0,0,0);");
		}
	}

	void drawCal() {
		ArrayList<Label> lblList = new ArrayList<>(Arrays.asList(lbl01, lbl02, lbl03, lbl04, lbl05, lbl06, lbl07, lbl08,
				lbl09, lbl10, lbl11, lbl12, lbl13, lbl14, lbl15, lbl16, lbl17, lbl18, lbl19, lbl20, lbl21, lbl22, lbl23,
				lbl24, lbl25, lbl26, lbl27, lbl28, lbl29, lbl30, lbl31, lbl32, lbl33, lbl34, lbl35, lbl36, lbl37, lbl38,
				lbl39, lbl40, lbl41, lbl42));

		for (Label l : lblList) {
			l.setText("");
		}
		for (VBox v : boxList) {
			v.setDisable(false);
			ObservableList<Node> vList = v.getChildren();
			if (vList.size() > 1) {
				Label tmp = (Label) vList.get(0);
				vList.clear();
				vList.add(tmp);
			}
			v.setMaxHeight(52);
		}

		year = cal.get(Calendar.YEAR); // 올해
		month = cal.get(Calendar.MONTH); // 이번 달(0~11)
		int dayMonth = cal.get(Calendar.DAY_OF_MONTH); // 이번 달의 오늘

		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH); // 이번 달의 첫째 날
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 이번 달의 마지막 날
		int weeks = cal.get(Calendar.WEEK_OF_MONTH); // 오늘이 현재 월의 몇째 주
		int days = cal.get(Calendar.DAY_OF_WEEK); // 오늘이 현재 주의 몇째 일(1이 일요일, 7이 토요일)

		lblYear.setText(Integer.toString(year) + " 년");
		lblMonth.setText(Integer.toString(month + 1));

		for (int i = firstDay; i <= lastDay; i++) {
			lblList.get(7 * (weeks - 1) + days - dayMonth + i - 1).setText(Integer.toString(i));
		}

		for (int i = 0; i < 42; i++) {
			if ((i < 7 * (weeks - 1) + days - dayMonth + firstDay - 1)
					|| (i > 7 * (weeks - 1) + days - dayMonth + lastDay - 1)) {
				boxList.get(i).setDisable(true);
			}
		}

		// DB에서 스케쥴 읽어오게 요청 후 받아온 데이터에 기반하여 스케쥴 항목 추가

		ScheduleService.setBoxList(boxList);

		Connector.send(new NetworkData<Member>("schedule/find", Main.loginMember));
	};

}

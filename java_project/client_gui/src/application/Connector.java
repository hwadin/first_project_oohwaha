package application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import network_dto.NetworkData;
import router.MainRouter;
import vo.Member;

public class Connector {
	public static final String IP = "127.0.0.1";
	public static final int PORT = 5001;

	static Socket server;

	static MainRouter mainRouter;

	// receive 제어용 플래그
	public static boolean isRun = true;

	public static boolean isConnected = false;

	private static ObjectOutputStream req;
	private static ObjectInputStream res;

	public boolean connect() {
		try {
			mainRouter = new MainRouter();
			server = new Socket(IP, PORT);
			System.out.println("서버 연결 성공");
			isConnected = true;
			receive();
			return true;
		} catch (IOException e) {
			System.out.println("서버 연결 오류");
			isConnected = false;
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	void receive() {
		Thread t = new Thread(() -> {
			while (isRun) {
				try {
					res = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
					NetworkData<Member> data = (NetworkData<Member>) res.readObject();
					System.out.println("receive() : " + data);
					mainRouter.route(data);
				} catch (IOException e) {
					System.out.println("서버 연결 오류");
					isRun = false;
					try {
						server.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	public static void send(NetworkData<?> data) {
		try {
			req = new ObjectOutputStream(new BufferedOutputStream(server.getOutputStream()));
			req.writeObject(data);
			req.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

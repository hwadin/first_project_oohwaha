
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import network_dto.NetworkData;
import router.MainRouter;
import vo.Member;

public class ServerTask implements Runnable {

	Socket client;
	Member loginMember;

	private ObjectOutputStream res;
	private ObjectInputStream req;

	// 소켓 생성시 만들어진 client를 주입 후 스레드풀에 던져줌
	public ServerTask(Socket client) {
		this.client = client;
		Server.serverPool.submit(ServerTask.this);
	}

	// 스레드풀에 들어갔을 때 실행될 run() 메소드
	@Override
	public void run() {
		while (true) {
			try {
				// 데이터 수신
				req = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
				NetworkData<?> data = (NetworkData<?>) req.readObject();
				System.out.println("request : " + data);

				// 수신한 데이터를 action에 따른 처리를 위해 MainRouter로 전달
				if (data.getAction().equals("member/logout")) {
					Server.onlineMembers.remove(loginMember);
					System.out.println("현재 로그인한 인원 : " + Server.onlineMembers.size());
					loginMember = null;
					continue;
				}
				Member frd = null;
				if (data.getAction().equals("member/frdAdd")) {
					ArrayList<Member> frdAddList = (ArrayList<Member>) data.getV();
					frd = frdAddList.get(1);
				}

				NetworkData<?> returnData = MainRouter.route(data);
				if (returnData.getAction().equals("member/login") && returnData.getV() != null) {
					Member m = (Member) returnData.getV();
					loginMember = m;
					Server.onlineMembers.put(new Member(m.getNo(), m.getId()), this.client.getOutputStream());
					System.out.println("현재 로그인한 인원 : " + Server.onlineMembers.size());
				}
				if (returnData.getAction().equals("alert")) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (returnData.getAction().equals("member/frdAdd") && ((Integer) returnData.getV()) == 1) {
					NetworkData<?> frdData = MainRouter.route(new NetworkData<Member>("member/find", frd));
					frd = (Member) frdData.getV();
					if (Server.onlineMembers.containsKey(frd)) {
						OutputStream os = Server.onlineMembers.get(frd);
						ObjectOutputStream frdOs = new ObjectOutputStream(new BufferedOutputStream(os));
						System.out.println("contains :" + Server.onlineMembers.containsKey(frd));
						System.out.println("frd :" + frd);
						frdData = MainRouter.route(new NetworkData<Member>("member/alert", frd));
						System.out.println("frdData : " + frdData);
						System.out.println("os : " + os);
						frdOs.writeObject(frdData);
						frdOs.flush();
					}
				}
				send(returnData);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("클라이언트 통신 오류");
				client = null;
				Server.onlineMembers.remove(loginMember);
				loginMember = null;
				System.out.println("현재 로그인한 인원 : " + Server.onlineMembers.size());
				break;
			}
		}
	}

	public void send(NetworkData<?> data) {
		try {
			res = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
			System.out.println("response : " + data);
			res.writeObject(data);
			res.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

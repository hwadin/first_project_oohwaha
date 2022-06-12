
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vo.Member;

public class Server {
	private static String ip;
	private static int port;

	ServerSocket serverSocket;

	static ExecutorService serverPool;
	static Hashtable<Member, OutputStream> onlineMembers;

	public static String getIp() {
		return ip;
	}

	public static int getPort() {
		return port;
	}

	public Server() {
		try {
			ip = "127.0.0.1";
			port = 5001;

			serverPool = Executors.newFixedThreadPool(20);
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(ip, port));

			onlineMembers = new Hashtable<>();

		} catch (IOException e1) {
			System.out.println("IO Exception" + e1.getMessage());
		}

		System.out.println("서버 열림 : " + ip + " : " + port);

		while (true) {
			try {
				Socket client = serverSocket.accept();

				String clientIP = client.getInetAddress().getHostAddress();
				System.out.println("클라이언트 접속 : " + client.getRemoteSocketAddress());
				new ServerTask(client);

			} catch (IOException e) {
				e.printStackTrace();
				serverPool.shutdownNow();
				if (serverSocket != null && !serverSocket.isClosed()) {
					try {
						serverSocket.close();
					} catch (IOException e1) {
					}
				}
				break;
			}
		}

	}
}

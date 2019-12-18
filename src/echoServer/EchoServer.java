package echoServer;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {

	static ArrayList<Writer> list = new ArrayList<Writer>();
	
	public static void main(String[] args) {		
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket();
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			System.out.println(hostAddress);
			serverSocket.bind( new InetSocketAddress ( "127.0.0.1", 7003));
			//요청 대기
			while(true) {
				System.out.println("연결대기!");
				Socket socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + "에서 연결되었습니다.");
				new ChatServerTread(socket, list).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}

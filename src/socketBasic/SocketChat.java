package socketBasic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketChat {
	public static void main(String[] args) {
		//소켓 생성
		ServerSocket serverSocket = null;
		StringBuffer sBuffer = null;
		try {
			//소켓 생성
			serverSocket = new ServerSocket();
			//소켓 호스트의 포트와 연결
			serverSocket.bind(new InetSocketAddress("localhost", 7001));
			System.out.println("서버 연결 대기 중");
			//클라이언트로부터 소켓 연결 대기
			Socket socket = serverSocket.accept();			
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			System.out.println("서버 연결 됨 - " + inetSocketAddress.getHostName() + ", " + inetSocketAddress.getPort());			
			//stream 객체 얻기
			//input 객체
			InputStream is = socket.getInputStream();
			//output 객체
			OutputStream os = socket.getOutputStream();
			//welcome인사!
			String data = "Hello! MY SERVER!";
			os.write(data.getBytes("UTF-8"));
			os.flush();
			//데이터 받기
			while(true) {
				byte[] buffer = new byte[128];
				int readByteCount = is.read(buffer);
				if(readByteCount < 0) {
					System.out.println("서버로부터 연결 끊김");
					is.close();
					socket.close();
					break;
				}
				//socket client에서 종료하는 방법?
				/*
				 * if(socket.getKeepAlive() == true) { System.out.println("종료!"); break; }
				 */
				String cData = new String(buffer, 0, readByteCount, "UTF-8");
				os.write(cData.getBytes("UTF-8"));
				System.out.println(cData);
			}
			os.close();					
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null && serverSocket.isClosed() == false ) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		}
	}
}

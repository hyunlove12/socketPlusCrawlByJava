package echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChatServerTread extends Thread {
	
	private String nickName;
	private Socket socket;
	private ArrayList<Writer> list = new ArrayList<Writer>();
	
	public ChatServerTread(Socket socket, ArrayList<Writer> list) {
		this.socket = socket;
		this.list = list;		
	}	
	
	@Override
	public void run() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));			
			while(true) {
				String request = bufferedReader.readLine();
				System.out.println(request);
				if(request == null) {
					System.out.println("연결 끊김");
					break;
				}
				//닉넴임 입력
				String[] tokens = request.split(":");
				if("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter);
				} else if("message".equals(tokens[0])) {
					
				} else if("quit".equals(tokens[0])) {
					
				} else {
					System.out.println("알 수 없는 요청");
				}
				//printWriter.write(request);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void doJoin(String nickName, Writer writer) {
		this.nickName = nickName;		
		String joinName = nickName + "님이 참여하였습니다.";
		addWriter(writer);
		broadcast(joinName);	
	}
	
	private void addWriter(Writer writer) {
		synchronized (EchoServer.list) {
			list.add(writer);
		}
	}
	
	private void broadcast(String data) { 
		synchronized (EchoServer.list) {
			for(Writer writer : EchoServer.list) {
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println( data );
				printWriter.flush();
			}
		}
		
	}
	
}

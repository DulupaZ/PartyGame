package com.dulupa.scum;
import java.io.*;
import java.net.*;
import com.dulupa.scum.struct.*;
import com.dulupa.scum.util.Debug;

public class GameServer {
	public final static int CLIENT_SERVICE_PORT=7777;
	public final static int DISPLAY_SERVICE_PORT=8888;
	
	ServerSocket clientServSocket;
	Socket clientComuSocket;
	BufferedReader reader;
	
	ServerSocket dispServSocket;
	Socket dispComuSocket;
	BufferedWriter writer;
	
	Player player;
	
	public static void main(String[] args) {
		
		
		GameServer server=new GameServer();
		server.start();
		
		
	}
	
	Vector2 playerPosition;
	
	public void start() {
		/*這樣會有玩家連線後就通知顯示器，但顯示器可能還沒連線的問題，先放著之後改*/
		//接受玩家連線
		new Thread(clientConnectHandler()).start();
		//接受顯示器連線
		new Thread(displayConnectHandler()).start();

	}
	// 等待client連線，建構通訊socket和串劉
	private Runnable clientConnectHandler() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					clientServSocket=new ServerSocket(CLIENT_SERVICE_PORT);
					Debug.log("Server start listen Port:"+CLIENT_SERVICE_PORT);
					//阻斷到該客戶連線，創造讀取客戶的串流
					clientComuSocket= clientServSocket.accept();
					reader=new BufferedReader(new InputStreamReader(clientComuSocket.getInputStream()));
					Debug.log("Client has connected!");
					player=new Player();
					//開啟另一個任務，用讀取斷流，監聽玩家是否送出操作，並通知disp
					new Thread(clientInputHandler()).start();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		};
		
	}
	// 等待DISP連線，建構通訊socket和串劉
	private Runnable displayConnectHandler() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					//傾聽DISP的連線
					dispServSocket=new ServerSocket(DISPLAY_SERVICE_PORT);
					//等到display連線，創造通訊socket和串流
					dispComuSocket=dispServSocket.accept();
					writer=new BufferedWriter(new OutputStreamWriter(dispComuSocket.getOutputStream()));
					Debug.log("disp has connected!");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
	}
	//	監聽玩家操作，並把玩家的最新位置通知display
	private Runnable clientInputHandler() {
		return new Runnable() {
				@Override
				public void run() {
					//檢查和client通訊的Socket是否還連線中
					Debug.log("isClosed:"+clientComuSocket.isClosed());
					Debug.log("isConnected:"+clientComuSocket.isConnected());
					//當收到玩家input，在通知display
					while(true) {		
						try {	
							if(reader.ready()) {

								String input=reader.readLine();
								Debug.log(input);
								//解讀出玩家控制指令
								PlayerInputParcel playerInput=PlayerInputParcel.parse(input);
								//更新玩家位置
								player.moveBaseOn(playerInput);
								Debug.log(player.getPosition());
								
								//通知顯示器(先不通知display，因為可能還沒連上server)
								//sendToDisplay(player.getPosition());
								
							}
							else {
								Debug.log("No player input");
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		};
	}
	
	//把玩家位置轉乘字串，再傳送到disp
	private void sendToDisplay(Vector2 playerPosition) {
		try {
			writer.write(playerPosition.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}

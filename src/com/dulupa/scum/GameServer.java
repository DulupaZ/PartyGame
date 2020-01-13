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
		/*�o�˷|�����a�s�u��N�q����ܾ��A����ܾ��i���٨S�s�u�����D�A����ۤ����*/
		//�������a�s�u
		new Thread(clientConnectHandler()).start();
		//������ܾ��s�u
		new Thread(displayConnectHandler()).start();

	}
	// ����client�s�u�A�غc�q�Tsocket�M��B
	private Runnable clientConnectHandler() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					clientServSocket=new ServerSocket(CLIENT_SERVICE_PORT);
					Debug.log("Server start listen Port:"+CLIENT_SERVICE_PORT);
					//���_��ӫȤ�s�u�A�гyŪ���Ȥ᪺��y
					clientComuSocket= clientServSocket.accept();
					reader=new BufferedReader(new InputStreamReader(clientComuSocket.getInputStream()));
					Debug.log("Client has connected!");
					player=new Player();
					//�}�ҥt�@�ӥ��ȡA��Ū���_�y�A��ť���a�O�_�e�X�ާ@�A�óq��disp
					new Thread(clientInputHandler()).start();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		};
		
	}
	// ����DISP�s�u�A�غc�q�Tsocket�M��B
	private Runnable displayConnectHandler() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					//��ťDISP���s�u
					dispServSocket=new ServerSocket(DISPLAY_SERVICE_PORT);
					//����display�s�u�A�гy�q�Tsocket�M��y
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
	//	��ť���a�ާ@�A�ç⪱�a���̷s��m�q��display
	private Runnable clientInputHandler() {
		return new Runnable() {
				@Override
				public void run() {
					//�ˬd�Mclient�q�T��Socket�O�_�ٳs�u��
					Debug.log("isClosed:"+clientComuSocket.isClosed());
					Debug.log("isConnected:"+clientComuSocket.isConnected());
					//���쪱�ainput�A�b�q��display
					while(true) {		
						try {	
							if(reader.ready()) {

								String input=reader.readLine();
								Debug.log(input);
								//��Ū�X���a������O
								PlayerInputParcel playerInput=PlayerInputParcel.parse(input);
								//��s���a��m
								player.moveBaseOn(playerInput);
								Debug.log(player.getPosition());
								
								//�q����ܾ�(�����q��display�A�]���i���٨S�s�Wserver)
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
	
	//�⪱�a��m�୼�r��A�A�ǰe��disp
	private void sendToDisplay(Vector2 playerPosition) {
		try {
			writer.write(playerPosition.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}

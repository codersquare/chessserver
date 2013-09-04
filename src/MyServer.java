import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class MyServer {
	private ArrayList<String> userlists;
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;
	private Handler handler = new Handler();
	private boolean duplicate = false;


	public MyServer(){
		System.out.println("Inside startserver ");

	}
	public void startServer(){
		userlists = new ArrayList<String>();
		try {
			serverSocket = new ServerSocket(8888);
			System.out.println("Listening :8888");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			try {
				socket = serverSocket.accept();
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				String ip = socket.getInetAddress().toString();
				// System.out.println(userlists.size());
				if (!checkDuplicate(ip.substring(1))){				
					userlists.add(ip.substring(1));
				}
	
				// System.out.println("first userlist" + userlists);	
				String receive=dataInputStream.readUTF();
				// System.out.println(receive);
				if (receive.length() >4)startSend(ip.substring(1), receive);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
	
				
				if( dataInputStream!= null){
					try {

						dataInputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if( dataOutputStream!= null){
					try {
						dataOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}			
				if( socket!= null){
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}


			}
		}		
	}

	public void startSend(String incoming_ip, String msg){
		Socket sendsocket = null;
		// System.out.println(userlists);
		for (int x = 0; x < userlists.size(); x++) {
			String toip = userlists.get(x);
			// System.out.println("toip" + toip);
			// System.out.println("incoming_ip" + incoming_ip);
			if (!toip.equals(incoming_ip)){
				// System.out.println("true");
				DataOutputStream dataOutputStream = null;
				DataInputStream dataInputStream = null;
				try {               
					sendsocket = new Socket(toip, 8889);
					dataOutputStream = new DataOutputStream(sendsocket.getOutputStream()); 
					dataOutputStream.writeUTF(msg); 	
				}
				catch (UnknownHostException e) {
					e.printStackTrace();
				} 
				catch (IOException e) {
			// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					if (sendsocket != null){
						try {
							sendsocket.close();
						} 
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public boolean checkDuplicate (String string){
		if (userlists.size() >0){				
			for (int x = 0; x < userlists.size(); x++) {
			String nowip = userlists.get(x);
			if (string.equals(nowip))
			duplicate = true;
		}

		}
		else{
			duplicate = false;
		}
		return duplicate;
	}


	public static void main(String[] args){
		MyServer ms = new MyServer();
		ms.startServer();
	}

}

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;


public class MyServer {
	private Hashtable<String, Socket> userlists;
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;
	private Handler handler = new Handler();
	private boolean game1 = false;

	public MyServer(){
		System.out.println("Inside startserver ");

	}
	public void startServer(){
		try {
			userlists = new Hashtable<String, Socket>();

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
				userlists.put(socket.getInetAddress().toString(),socket);				
				String receive=dataInputStream.readUTF();
				System.out.println(receive);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				if( socket!= null){
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if( dataInputStream!= null){
					try {
						startSend();
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
			}
		}		
	}

	public void startSend(){
		Socket sendsocket = null;
		DataOutputStream dataOutputStream = null;
		DataInputStream dataInputStream = null;
		try {               
		sendsocket = new Socket("18.111.75.234", 8700);
		dataOutputStream = new DataOutputStream(sendsocket.getOutputStream()); 
		dataOutputStream.writeUTF("fen"); 
		}
		
		catch (UnknownHostException e) {
		// TODO Auto-generated catch block
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




	public static void main(String[] args){
		MyServer ms = new MyServer();
		ms.startServer();
	}

}

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Server extends JFrame {

	// GUI components
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_2;
	public static Messenger messenger = new Messenger();
	public static AccountAuthenticator authenticator =  new AccountAuthenticator();
	// Input/Output components
	//public static PrintWriter out = null;
	//public static BufferedReader in = null;
	//public static Vector<PrintWriter> connectionsOut = new Vector<PrintWriter>();
	//public static Vector<BufferedReader> connectionsIn = new Vector<BufferedReader>();
	public static OutputStream clientSocketOut;
	public static InputStream clientSocketIn;
	public static ObjectOutputStream clientSocketObjOut;
	public static ObjectInputStream clientSocketObjIn;
	public static Vector<ObjectOutputStream> ObjOutVector = new Vector<ObjectOutputStream>();
	public static Vector<ObjectInputStream> ObjInVector = new Vector<ObjectInputStream>();
	//public static Vector<ObjectOutputStream> AccOutVector = new Vector<ObjectOutputStream>();
	//public static Vector<ObjectInputStream> AccInVector = new Vector<ObjectInputStream>();
	
	public static ServerSocket s = null;
	
	public static void main(String[] args) {
		
		// Launch and initialize the application
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			// Create a server socket
			s = new ServerSocket(1254);
			
			while(true){
				// Listen for a connection request
				Socket s1 = s.accept();
				
				/*
				authHandler accounts = new authHandler(s1);
				accounts.start();
				accounts.join();
				*/
				
				dataHandler data = new dataHandler(s1);
				data.start();
				
				
				/*
				accountHandler accounts = new accountHandler(s1);
				accounts.start();
				accounts.join();
				
				chatHandler chat = new chatHandler(s1);
				chat.start();
				*/
				
			
				// Create data input and output streams
				//in = new BufferedReader(new InputStreamReader(s1.getInputStream()));
				//out = new PrintWriter(s1.getOutputStream(), true);
				
				// Link input/output to messenger
				//messenger.startConnection(out, in);
				
			}
		}
		catch(IOException ioex){
			ioex.printStackTrace();
		}
	}
	
	// handles data input and output streams for each connected client
	private static class dataHandler extends Thread{
		
		private Socket clientSocket;
		
		public dataHandler(Socket clientSocket){
			this.clientSocket = clientSocket;
		}
		
		public void run(){
			try{
				
				clientSocketOut = clientSocket.getOutputStream();
				clientSocketObjOut = new ObjectOutputStream(clientSocketOut);
				
				clientSocketIn = clientSocket.getInputStream();
				clientSocketObjIn = new ObjectInputStream(clientSocketIn);
				
				ObjOutVector.add(clientSocketObjOut);
				ObjInVector.add(clientSocketObjIn);
				
				AuthenticationThread authThread = new AuthenticationThread(ObjOutVector, ObjInVector);
				authThread.start();
				authThread.join();
				
				MessengerThread messengerThread = new MessengerThread(ObjOutVector, ObjInVector);
				messengerThread.start();
				
				
			}
			catch(IOException ioex){
				ioex.printStackTrace();
			}
			catch(InterruptedException iex){
				iex.printStackTrace();
			}
		}
	}
	
	private static class AuthenticationThread extends Thread{
		
		public Vector<ObjectOutputStream> ObjOutVector;
		public Vector<ObjectInputStream> ObjInVector;
		
		public AuthenticationThread(Vector<ObjectOutputStream> ObjOutVector, Vector<ObjectInputStream> ObjInVector){
			this.ObjOutVector = ObjOutVector;
			this.ObjInVector = ObjInVector;
		}
		
		public void run(){
			authenticator.startAuthenticator(ObjOutVector, ObjInVector);
		}
	}
	
	private static class MessengerThread extends Thread{
		
		public Vector<ObjectOutputStream> ObjOutVector;
		public Vector<ObjectInputStream> ObjInVector;
		
		public MessengerThread(Vector<ObjectOutputStream> ObjOutVector, Vector<ObjectInputStream> ObjInVector){
			this.ObjOutVector = ObjOutVector;
			this.ObjInVector = ObjInVector;
		}
		
		public void run(){
			messenger.startServerConnection(ObjOutVector, ObjInVector);	
		}
	}
	
	/*
	private static class authHandler extends Thread{
		
		private Socket clientSocket;
		
		public authHandler(Socket clientSocket){
			this.clientSocket = clientSocket;
		}
		
		public void run(){
			try{	
				clientSocketOut = clientSocket.getOutputStream();
				clientSocketObjOut = new ObjectOutputStream(clientSocketOut);
				
				clientSocketIn = clientSocket.getInputStream();
				clientSocketObjIn = new ObjectInputStream(clientSocketIn);
				
				AccOutVector.add(clientSocketObjOut);
				AccInVector.add(clientSocketObjIn);
				
				authenticator.startAuthenticator(AccOutVector, AccInVector);
			}
			catch(IOException ioex){
				ioex.printStackTrace();
			}
		}
	}
	
	*/
	
	
	// Creates the frame
	public Server() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{627, 292, 0};
		gbl_panel.rowHeights = new int[]{288, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel.add(panel_2, gbc_panel_2);
		
		messenger = new Messenger();
		GridBagConstraints gbc_messenger = new GridBagConstraints();
		gbc_messenger.fill = GridBagConstraints.BOTH;
		gbc_messenger.gridx = 1;
		gbc_messenger.gridy = 0;
		panel.add(messenger, gbc_messenger);
		messenger.setLayout(new GridLayout(1, 1, 0, 0));
		
	}

}

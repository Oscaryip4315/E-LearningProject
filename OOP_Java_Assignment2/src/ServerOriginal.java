import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ServerOriginal extends JFrame {

	// GUI components
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_2;
	public static MessengerOriginal messenger;

	// Input/Output component
	//public static PrintWriter out = null;
	//public static BufferedReader in = null;
	public static Vector<PrintWriter> connectionsOut = new Vector<PrintWriter>();
	public static Vector<BufferedReader> connectionsIn = new Vector<BufferedReader>();
	public static Map<Socket,AccountOriginal> clientAcc = new HashMap<Socket,AccountOriginal>();
	
	public static ServerSocket s = null;
	
	public static void main(String[] args) {
		
		// Launch and initialize the application
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerOriginal frame = new ServerOriginal();
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
				chatHandler chat = new chatHandler(s1);
				chat.start();
				
				messenger.startServerConnection(connectionsOut, connectionsIn);
				// Create data input and output streams
				//in = new BufferedReader(new InputStreamReader(s1.getInputStream()));
				//out = new PrintWriter(s1.getOutputStream(), true);
				
				// Link input/output to messenger
				//messenger.startConnection(out, in);
				
			}
		}
		catch(IOException iex){
			iex.printStackTrace();
		}
		
	}

	// handles the input and output streams for each connected client
	private static class chatHandler extends Thread{
		
		private Socket clientSocket;
		
		public chatHandler(Socket clientSocket){
			this.clientSocket = clientSocket;
		}
		
		public void run(){
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				
	            
				connectionsOut.add(out);
				connectionsIn.add(in);
				
				
				//messenger.startConnection(out, in);
			}
			catch(IOException iex){
				iex.printStackTrace();
			}
		}
	}
	// Creates the frame
	public ServerOriginal() {
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
		
		messenger = new MessengerOriginal();
		GridBagConstraints gbc_messenger = new GridBagConstraints();
		gbc_messenger.fill = GridBagConstraints.BOTH;
		gbc_messenger.gridx = 1;
		gbc_messenger.gridy = 0;
		panel.add(messenger, gbc_messenger);
		messenger.setLayout(new GridLayout(1, 1, 0, 0));
		
	}

}

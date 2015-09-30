import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Client extends JFrame {

	// GUI components
	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_2;
	
	public static Client frame = null;
	public static Messenger messenger = new Messenger();
	public static SignInFrame signIn = null;
	
	// Input/Output components
	//public static PrintWriter out = null;
	//public static BufferedReader in = null;
	public static ObjectOutputStream clientSocketObjOut;
	public static ObjectInputStream clientSocketObjIn;
	
	public static void main(String[] args) {
		
		// Launch and initialize the application
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Client();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			System.out.println("Client started");
			Thread.sleep(1000); // Sleeps for 1 second, so that invokeLater can run before subsequent statements
			
			// Connect to Server
			Socket s1 = new Socket("localhost", 1254);
			System.out.println("Client connected to localhost");
						
			
			// Create data input and output streams
			
			
			OutputStream clientSocketOut = s1.getOutputStream();
			clientSocketObjOut = new ObjectOutputStream(clientSocketOut);
			
			InputStream clientSocketIn = s1.getInputStream();
			clientSocketObjIn = new ObjectInputStream(clientSocketIn);
			
			// Sign in frame
			signIn = new SignInFrame(clientSocketObjOut, clientSocketObjIn);
			signIn.setVisible(true);
			signIn.addWindowListener(new WindowListener(){
				public void windowOpened(WindowEvent e) {
					System.out.println("SignInFrame Opened");
					frame.setVisible(false);
					}
					public void windowClosing(WindowEvent e) {
					System.out.println("SignInFrame closing");
					}
					public void windowClosed(WindowEvent e) {
					System.out.println("SignInFrame closed");
					frame.setVisible(true);
					// Link input/output to messenger
					messenger.startConnection(clientSocketObjOut, clientSocketObjIn, s1.getInetAddress());
					}
					public void windowIconified(WindowEvent e) {
					System.out.println("SignInFrame iconified");
					}
					public void windowDeiconified(WindowEvent e) {
					System.out.println("SignInFrame deiconified");
					}
					public void windowActivated(WindowEvent e) {
					System.out.println("SignInFrame activated");
					}
					public void windowDeactivated(WindowEvent e) {
					System.out.println("SignInFrame deactivated");
					}
			});
			
		}
		catch(IOException iex){
			iex.printStackTrace();
		}
		catch(InterruptedException intex){
			intex.printStackTrace();
		}
		
	}
	
	
	// Creates the frame
	public Client() {
		setTitle("Client");
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

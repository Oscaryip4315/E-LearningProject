import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Note: Add this jPanel to a jFrame for it to work
public class MessengerOriginal extends JPanel implements ActionListener{
	
	// GUI components
	private JTextField textField; // Input text field
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea textArea; // Chat Box area
	private StringBuffer msgToAppend = new StringBuffer(""); 
	private StringBuffer msgToSend = new StringBuffer("");
	private JLabel lblMessaging;
	
	
	// Creates the panel
	public MessengerOriginal() {
	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{360, 0};
		gridBagLayout.rowHeights = new int[]{280, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{363, 0};
		gbl_panel.rowHeights = new int[]{0, 244, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblMessaging = new JLabel("Messaging");
		GridBagConstraints gbc_lblMessaging = new GridBagConstraints();
		gbc_lblMessaging.insets = new Insets(0, 0, 5, 0);
		gbc_lblMessaging.gridx = 0;
		gbc_lblMessaging.gridy = 0;
		panel.add(lblMessaging, gbc_lblMessaging);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel.add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField(20);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 2;
		panel.add(textField, gbc_textField);
		textField.addActionListener(this);
		textField.setColumns(10);
	}
	
	// Action for textField
	public void actionPerformed(ActionEvent e){
		String text = textField.getText();
		if(!text.equals("")){
			
			appendChatBox("VVV [You] VVV");
			textField.selectAll();
			
			sendMessage(text);
		}
	}
	
	// Append message to chat box
	public void appendChatBox(String s){
		synchronized(msgToAppend){ // synchronized to maintain thread-safety
			msgToAppend.append(s);
			textArea.append(msgToAppend.toString() + "\n");
			msgToAppend.setLength(0);
		}
	}
	
	// Append message to msgToSend buffer
	public void sendMessage(String s){
		synchronized(msgToSend){ // synchronized to maintain thread-safety
			msgToSend.append(s + "\n");
		}
	}

	
	// Enable sending and receiving once connection has been established for clients
	public void startConnection(PrintWriter out, BufferedReader in, InetAddress clientInetAddress){
		MessengerHandler chat = new MessengerHandler(out, in, clientInetAddress);
		chat.start();
	}
	
	// Allows server to communicate messages across all clients
	public void startServerConnection(Vector<PrintWriter> connectionsOut, Vector<BufferedReader> connectionsIn){
		MessengerServerHandler chatServer = new MessengerServerHandler(connectionsOut, connectionsIn);
		chatServer.start();
	}
	
	
	// Handles chat for multiple clients
	public class MessengerHandler extends Thread{
		
		public PrintWriter out = null;
		public BufferedReader in = null;
		public InetAddress clientInetAddress = null;
		
		public MessengerHandler(PrintWriter out, BufferedReader in, InetAddress clientInetAddress){
			this.out = out;
			this.in = in;
			this.clientInetAddress = clientInetAddress;
		}
		
		public void run(){
	
			while(true){
				try{
					// Send message
					if(msgToSend.length() != 0){
						out.print(msgToSend);
						out.flush();
						msgToSend.setLength(0);
					}
					
					// Receive message
					if(in.ready()){
						String text = in.readLine();
						if((text != null) && (text.length() != 0) ){
							appendChatBox(clientInetAddress.toString() + " says: " + text + "\n");
						}
					}
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			
		}
	}

	
	// Relays all messages received to all clients connected to the server
	public class MessengerServerHandler extends Thread{
		
		public Vector<PrintWriter> connectionsOut = null;
		public Vector<BufferedReader> connectionsIn = null;
		
		public MessengerServerHandler(Vector<PrintWriter> connectionsOut, Vector<BufferedReader> connectionsIn){
			this.connectionsOut = connectionsOut;
			this.connectionsIn = connectionsIn;
		}
		
		public void run(){
	
			while(true){
				try{
					
					// For every connection input to the server
					synchronized(connectionsIn){
						
						for(BufferedReader in : connectionsIn){
							
							if(in.ready()){
								String text = in.readLine();
								appendChatBox("Received: " + text + "\n"); // for debugging
								
								synchronized(connectionsOut){
									// For every connection output to the server
									for(PrintWriter out : connectionsOut){
										if((text != null) && (text.length() != 0)){
											//appendChatBox("Received: " + text + "\n"); // for debugging
											out.println(text);
										}
									}
								}
								
							}
						}
					}
					
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			
		}
	}
}

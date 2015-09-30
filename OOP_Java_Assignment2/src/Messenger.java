import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//Note: this jPanel is a component of Server.java and Client.java
public class Messenger extends JPanel implements ActionListener{
	
	// Variable declarations
	private JTextField textField; // Input text field
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea textArea; // Chat Box area
	private StringBuffer msgToAppend = new StringBuffer(""); 
	private StringBuffer msgToSend = new StringBuffer("");
	private JLabel lblMessaging;
	
	
	// Draws the GUI
	public Messenger() {
	
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

	
	// Links I/O streams and inetAddress of connected client to the Messenger
	public void startConnection(ObjectOutputStream out, ObjectInputStream in, InetAddress clientInetAddress){
		MessengerHandler chat = new MessengerHandler(out, in, clientInetAddress);
		chat.start();
	}
	
	
	// Links I/O streams of all connected clients to the Messenger Server
	public void startServerConnection(Vector<ObjectOutputStream> connectionsOut, Vector<ObjectInputStream> connectionsIn){
		MessengerServerHandler chatServer = new MessengerServerHandler(connectionsOut, connectionsIn);
		chatServer.start();
	}
	
	
	// Client-side Messenger handler
	public class MessengerHandler extends Thread{
		
		public ObjectOutputStream out = null;
		public ObjectInputStream in = null;
		public InetAddress clientInetAddress = null;
		
		public MessengerHandler(ObjectOutputStream out, ObjectInputStream in, InetAddress clientInetAddress){
			this.out = out;
			this.in = in;
			this.clientInetAddress = clientInetAddress;
		}
		
		public void run(){
			
			// Read messages from server
			ObjectReader readerClient = new ObjectReader(in, clientInetAddress);
			readerClient.start();
			
			// Constantly checks msgToSend for messages and sends them
			while(true){
				try{
					// Send message
					if(msgToSend.length() != 0){
						
						out.writeObject(new DataObject(DataObject.STRING, msgToSend.toString()));
						out.flush();
						msgToSend.setLength(0);
					}
					
				}
				catch(IOException ioe){
					ioe.printStackTrace();
				}
			}
			
		}
	}

	
	// Relays all messages received to all clients connected to the server
	public class MessengerServerHandler extends Thread{
		
		public Vector<ObjectOutputStream> connectionsOut = null;
		public Vector<ObjectInputStream> connectionsIn = null;
		
		public MessengerServerHandler(Vector<ObjectOutputStream> connectionsOut, Vector<ObjectInputStream> connectionsIn){
			this.connectionsOut = connectionsOut;
			this.connectionsIn = connectionsIn;
		}
		
		public void run(){
			
			textField.setEnabled(false);
			
			synchronized(connectionsIn){
				
				for(ObjectInputStream in : connectionsIn){
					ObjectReaderServer objectReaderServer = new ObjectReaderServer(in, connectionsOut);
					objectReaderServer.start();
				}
			}
			
		}
	}
	
	// Client-side Object reader - Reads objects from server
	public class ObjectReader extends Thread{

		public ObjectInputStream in = null;
		public InetAddress clientInetAddress;
	
		public ObjectReader(ObjectInputStream in, InetAddress clientInetAddress){
			this.in = in;
			this.clientInetAddress = clientInetAddress;
		}
		
		public void run(){
			while(true){
				if(in != null){
					try{
						DataObject data = (DataObject)in.readObject();
						if(data.getType() == DataObject.STRING){
							if((data.getString() != null) && (data.getString().length() != 0) ){
								appendChatBox(clientInetAddress.toString() + " says: " + data.getString() + "\n");
							}
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	// Server-side Object reader - Reads objects from individual clients
	public class ObjectReaderServer extends Thread{
		public ObjectInputStream in;
		public Vector<ObjectOutputStream> connectionsOut = null;
		
		public ObjectReaderServer(ObjectInputStream in, Vector<ObjectOutputStream> connectionsOut){
			this.in = in;
			this.connectionsOut = connectionsOut;
		}
		
		public void run(){
			while(true){
				try{
					// Read object from Client
					DataObject data = (DataObject)in.readObject();
					if(data.getType() == 0){
						if((data.getString() != null) && (data.getString().length() != 0) ){
							appendChatBox("Received: " + data.getString() + "\n");
							
							// Relays all messages received to all clients connected to the server
							synchronized(connectionsOut){
								// For every connection output to the server
								for(ObjectOutputStream out : connectionsOut){
										out.writeObject(data);
								}
							}
						
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}

	

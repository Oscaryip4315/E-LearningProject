import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JOptionPane;


public class AccountAuthenticator {

	ObjectOutputStream ObjOut = null;
	PrintWriter printWriter = null;
	private File accDataFile = new File("AccountData.txt");
	
	public AccountAuthenticator(){
	}
	
	//Start Authenticator for client
	public void startAuthenticator(Vector<ObjectOutputStream> ObjOutVector, Vector<ObjectInputStream> ObjInVector){
		AuthenticationHandler authHandler = new AuthenticationHandler(ObjOutVector, ObjInVector);
		authHandler.start();
	}
	
	public class AuthenticationHandler extends Thread{
		
		public Vector<ObjectOutputStream> ObjOutVector = null;
		public Vector<ObjectInputStream> ObjInVector = null;
		public Object account = null;
		
		public AuthenticationHandler(Vector<ObjectOutputStream> ObjOutVector, Vector<ObjectInputStream> ObjInVector){
			this.ObjOutVector = ObjOutVector;
			this.ObjInVector = ObjInVector;
		}
		
		public void run(){
			try{
				if(accDataFile.exists()){
					Map<String,Account> accRecordPair = readFromFile();
					
					synchronized(ObjInVector){
						int index = 0;
						for(ObjectInputStream ObjIn : ObjInVector){
								ObjectReaderServer objectReader = new ObjectReaderServer(ObjIn, index, ObjOutVector, accRecordPair);
								objectReader.start();
								objectReader.join();
								index++;
								
						}
					}
				}
				else{
					accDataFile.createNewFile();
					
					printWriter = new PrintWriter(new BufferedWriter(new FileWriter(accDataFile,true)));
					printWriter.println("admin,password");
				}
				
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	}
	
	// Server-side Object reader - Reads objects from individual clients
	public class ObjectReaderServer extends Thread{
		public ObjectInputStream in;
		public ObjectOutputStream out;
		public int index;
		public Vector<ObjectOutputStream> connectionsOut;
		public Map<String,Account> accRecordPair;
		
		public ObjectReaderServer(ObjectInputStream in, int index, Vector<ObjectOutputStream> connectionsOut, Map<String,Account> accRecordPair){
			this.in = in;
			this.index = index;
			this.connectionsOut = connectionsOut;
			this.accRecordPair = accRecordPair;
		}
		
		public void run(){
			while(true){
				try{
					// Read object from Client
					DataObject data = (DataObject)in.readObject();
					if(data.getType() == DataObject.ACCOUNT){
						
						out = connectionsOut.get(index);
						if(accRecordPair.containsValue(data)){	
							out.writeObject(new DataObject(DataObject.SIGNIN, true));
						}
						else
							out.writeObject(new DataObject(DataObject.SIGNIN, false));
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	//=========
	
	public synchronized Map<String,Account> readFromFile(){
		Scanner scanner = null;
		Map <String,Account> accRecordPair = null;
		
		try{
			scanner = new Scanner(accDataFile);
			accRecordPair = new HashMap<String,Account>();
		
			while(scanner.hasNextLine()){
				String accEntry = scanner.nextLine();
				String [] data = accEntry.split(",");
				accRecordPair.put(data[0],new Account(data[0],data[1]));
			}

		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Error: " + ex,"Error",JOptionPane.ERROR_MESSAGE);
		}finally{
			scanner.close();
		}
		
		return accRecordPair;
	}
}

import java.io.*;
import java.util.*;

import javax.swing.*;

public class AccountOriginal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static File accDataFile = new File("AccountData.txt");
	private String profileName;
	private String userName;
	private String userPass;
	private String accType;
	
	/*
	//Launch the application(Test Driver)
	public static void main(String [] args){
		new Account("Koh Keng Chye","lastkoh","1234","student").writeToFile();
		Map<String,Account> accData = Account.readFromFile();
		for(String key:accData.keySet()){
			System.out.println(accData.get(key).toString());
		}
		System.out.println("Process Completed!");
	}
	*/
	
	public AccountOriginal(){
		profileName = null;
		userName = null;
		userPass = null;
		accType = null;
	}
	
	public AccountOriginal(String profileName,String userName,String userPass,String accType){
		this.profileName = profileName;
		this.userName = userName;
		this.userPass = userPass;
		this.accType = accType;
	}
	
	//Write the instance of an account to a file
	public synchronized boolean writeToFile(){
		PrintWriter printWriter = null;
		
		try{
			if(!accDataFile.exists())
				accDataFile.createNewFile();
			
			if(isAccExist())
				throw new Exception("Sorry, account already exists!");
			
			printWriter = new PrintWriter(new BufferedWriter(new FileWriter(accDataFile,true)));
			printWriter.println(profileName + "," + userName + "," + userPass +","+ accType);
		
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Error: " + ex,"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}finally{
			printWriter.close();
		}
		
		return true;
	}
	
	//Read from a file and return a map that with userName as the key and Account as the value
	public synchronized static Map<String,AccountOriginal> readFromFile(){
		Scanner scanner = null;
		Map <String,AccountOriginal> accRecordPair = null;
		
		try{
			if(!accDataFile.exists())
				throw new Exception(accDataFile.getName() + "not exists");
			
			scanner = new Scanner(accDataFile);
			accRecordPair = new HashMap<String,AccountOriginal>();
			while(scanner.hasNextLine()){
				String accEntry = scanner.nextLine();
				String [] data = accEntry.split(",");
				accRecordPair.put(data[1],new AccountOriginal(data[0],data[1],data[2],data[3]));
			}

		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Error: " + ex,"Error",JOptionPane.ERROR_MESSAGE);
		}finally{
			scanner.close();
		}
		
		return accRecordPair;
	}
	
	//Check is an account instance with the userName and userPass exists (Account validation)
	public synchronized boolean isAccExist(){
		try{
			Map <String,AccountOriginal> accRecordPair = AccountOriginal.readFromFile();
			
			if(accRecordPair != null){
				for(String key:accRecordPair.keySet()){
					if(key.equals(userName) && (accRecordPair.get(key).getUserPass()).equals(userPass))
						return true;
				}
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"Error: " + ex,"Error",JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [profileName=" + profileName + ", userName=" + userName
				+ ", userPass=" + userPass + ", accType=" + accType + "]";
	}
	
	
}

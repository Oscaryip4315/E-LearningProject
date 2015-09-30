import java.io.Serializable;

public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private String profileName;
	private String userName;
	private String userPass;
	//private String accType;
	
	public Account(){
		userName = null;
		userPass = null;
	}
	
	public Account(String userName,String userPass){
		//this.profileName = profileName;
		this.userName = userName;
		this.userPass = userPass;
		//this.accType = accType;
	}
	

	/*
	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	*/
	
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
	
	/*
	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	*/
}

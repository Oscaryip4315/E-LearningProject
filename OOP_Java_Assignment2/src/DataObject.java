import java.io.Serializable;


public class DataObject implements Serializable {

	static final int STRING = 0, ACCOUNT = 1, SIGNIN = 2;
	private static final long serialVersionUID = 2L;
	int type;
	String string;
	Account account;
	boolean signIn;
	
	public DataObject(int type, String string){
		this.type = type;
		this.string = string;
	}
	
	public DataObject(int type, Account account){
		this.type = type;
		this.account = account;
	}
	
	public DataObject(int type, boolean signIn){
		this.type = type;
		this.signIn = signIn;
	}

	public boolean isSignIn() {
		return signIn;
	}

	public void setSignIn(boolean signIn) {
		this.signIn = signIn;
	}

	public int getType() {
		return type;
	}

	public String getString() {
		return string;
	}

	public Account getAccount() {
		return account;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setString(String string) {
		this.string = string;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}

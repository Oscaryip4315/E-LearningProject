import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;


public class SignInFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;

	private String username;
	private char[] password;
	
	private static ObjectOutputStream clientSocketObjOut = null;
	private static ObjectInputStream clientSocketObjIn = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInFrame frame = new SignInFrame(clientSocketObjOut, clientSocketObjIn);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignInFrame(ObjectOutputStream clientSocketObjOut, ObjectInputStream clientSocketObjIn) {
		
		this.clientSocketObjOut = clientSocketObjOut;
		this.clientSocketObjIn = clientSocketObjIn;
		
		// GUI
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Welcome");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 294, 184);
		setLocationRelativeTo(null);  
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{97, 254, 87, 0};
		gbl_contentPane.rowHeights = new int[]{32, 35, 74, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewJgoodiesTitle_1 = DefaultComponentFactory.getInstance().createTitle("Blended Learning");
		lblNewJgoodiesTitle_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewJgoodiesTitle_1 = new GridBagConstraints();
		gbc_lblNewJgoodiesTitle_1.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewJgoodiesTitle_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewJgoodiesTitle_1.gridx = 1;
		gbc_lblNewJgoodiesTitle_1.gridy = 0;
		contentPane.add(lblNewJgoodiesTitle_1, gbc_lblNewJgoodiesTitle_1);
		
		JLabel lblManagementSystem = DefaultComponentFactory.getInstance().createTitle("Management System");
		lblManagementSystem.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblManagementSystem = new GridBagConstraints();
		gbc_lblManagementSystem.anchor = GridBagConstraints.NORTH;
		gbc_lblManagementSystem.insets = new Insets(0, 0, 5, 5);
		gbc_lblManagementSystem.gridx = 1;
		gbc_lblManagementSystem.gridy = 1;
		contentPane.add(lblManagementSystem, gbc_lblManagementSystem);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{76, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 0;
		panel.add(lblUsername, gbc_lblUsername);
		
		usernameField = new JTextField();
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameField.gridx = 1;
		gbc_usernameField.gridy = 0;
		panel.add(usernameField, gbc_usernameField);
		usernameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 1;
		panel.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		panel.add(passwordField, gbc_passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(usernameField.getText() == null || passwordField.getPassword()== null)
					JOptionPane.showMessageDialog(new JFrame(),"One of the fields are not filled in.", "Error",JOptionPane.ERROR_MESSAGE);
				else{
					username = usernameField.getText();
					password = passwordField.getPassword();
					
					if(authenticate(username, password) == true){
						JOptionPane.showMessageDialog(new JFrame(),"Authentication is successful.", "Login Success",JOptionPane.INFORMATION_MESSAGE);
						dispose();	
					}else
						JOptionPane.showMessageDialog(new JFrame(),"Username and/or password is wrong.","Error", JOptionPane.ERROR_MESSAGE);
				}

				
					
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.anchor = GridBagConstraints.WEST;
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 2;
		panel.add(btnLogin, gbc_btnLogin);
	}
	
	public boolean authenticate(String username, char[] password){
		
		Account account = new Account();
		account.setUserName(username);
		account.setUserPass(password.toString());
		
		DataObject accountData = new DataObject(DataObject.ACCOUNT, account);
		
		try{
			clientSocketObjOut.writeObject(accountData);
			
			while(true){
				DataObject result = (DataObject)clientSocketObjIn.readObject();
				if(result.getType() == 2){
					return (result.isSignIn()); 
				}
			}
		}
		catch(IOException iex){
			iex.printStackTrace();
			return false;
		}
		catch(ClassNotFoundException cnfex){
			cnfex.printStackTrace();
			return false;
		}	
		
	}

}

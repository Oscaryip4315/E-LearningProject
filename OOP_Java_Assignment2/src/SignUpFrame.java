import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Window.Type;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.JRadioButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SignUpFrame extends JFrame {

	private JPanel contentPane;
	private JTextField userName;
	private JLabel lblConfirmPassword;
	private JButton signUpButton;
	private JPasswordField passField;
	private JPasswordField confirmPassField;
	private JLabel lblDisplayName;
	private JTextField nameField;
	private JRadioButton lecturerRadioButton;
	private JRadioButton studentRadioButton;
	private JLabel lblAcountType;
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterScreen frame = new RegisterScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
	/**
	 * Create the frame.
	 */
	public SignUpFrame() {
		setAlwaysOnTop(true);
		setTitle("Account Sign-Up");
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 333, 262);
		setLocationRelativeTo(null);  
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblDisplayName = DefaultComponentFactory.getInstance().createLabel("Display Name:");
		GridBagConstraints gbc_lblDisplayName = new GridBagConstraints();
		gbc_lblDisplayName.anchor = GridBagConstraints.WEST;
		gbc_lblDisplayName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDisplayName.gridx = 1;
		gbc_lblDisplayName.gridy = 1;
		contentPane.add(lblDisplayName, gbc_lblDisplayName);
		
		nameField = new JTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.insets = new Insets(0, 0, 5, 5);
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.gridx = 3;
		gbc_nameField.gridy = 1;
		contentPane.add(nameField, gbc_nameField);
		nameField.setColumns(10);
		
		lblAcountType = new JLabel("Account Type (*):");
		GridBagConstraints gbc_lblAcountType = new GridBagConstraints();
		gbc_lblAcountType.anchor = GridBagConstraints.WEST;
		gbc_lblAcountType.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcountType.gridx = 1;
		gbc_lblAcountType.gridy = 2;
		contentPane.add(lblAcountType, gbc_lblAcountType);
		
		lecturerRadioButton = new JRadioButton("Lecturer");
		lecturerRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				studentRadioButton.setSelected(false);
			}
		});
		GridBagConstraints gbc_lecturerRadioButton = new GridBagConstraints();
		gbc_lecturerRadioButton.anchor = GridBagConstraints.WEST;
		gbc_lecturerRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_lecturerRadioButton.gridx = 3;
		gbc_lecturerRadioButton.gridy = 2;
		contentPane.add(lecturerRadioButton, gbc_lecturerRadioButton);
		
		studentRadioButton = new JRadioButton("Student");
		studentRadioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lecturerRadioButton.setSelected(false);
			}
		});
		GridBagConstraints gbc_studentRadioButton = new GridBagConstraints();
		gbc_studentRadioButton.anchor = GridBagConstraints.WEST;
		gbc_studentRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_studentRadioButton.gridx = 3;
		gbc_studentRadioButton.gridy = 3;
		contentPane.add(studentRadioButton, gbc_studentRadioButton);
		
		JLabel lblUsername = new JLabel("Username (*):");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 4;
		contentPane.add(lblUsername, gbc_lblUsername);
		
		userName = new JTextField();
		GridBagConstraints gbc_userName = new GridBagConstraints();
		gbc_userName.insets = new Insets(0, 0, 5, 5);
		gbc_userName.fill = GridBagConstraints.HORIZONTAL;
		gbc_userName.gridx = 3;
		gbc_userName.gridy = 4;
		contentPane.add(userName, gbc_userName);
		userName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password (*):");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 5;
		contentPane.add(lblPassword, gbc_lblPassword);
		
		passField = new JPasswordField();
		GridBagConstraints gbc_passField = new GridBagConstraints();
		gbc_passField.insets = new Insets(0, 0, 5, 5);
		gbc_passField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passField.gridx = 3;
		gbc_passField.gridy = 5;
		contentPane.add(passField, gbc_passField);
		
		lblConfirmPassword = new JLabel("Confirm Password (*):");
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.WEST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 1;
		gbc_lblConfirmPassword.gridy = 6;
		contentPane.add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		confirmPassField = new JPasswordField();
		GridBagConstraints gbc_confirmPassField = new GridBagConstraints();
		gbc_confirmPassField.insets = new Insets(0, 0, 5, 5);
		gbc_confirmPassField.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmPassField.gridx = 3;
		gbc_confirmPassField.gridy = 6;
		contentPane.add(confirmPassField, gbc_confirmPassField);
		
		signUpButton = new JButton("Sign-up");
		signUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Boolean isFieldComplete = true;
				if(!studentRadioButton.isSelected() && !lecturerRadioButton.isSelected())
					isFieldComplete = false;
				if(userName.getText() == null && confirmUserName.getText() == null)
					isFieldComplete = false;
				if(passField.getPassword() == null && confirmPassField.getPassword() == null)
					isFieldComplete = false;
				
				if(!isFieldComplete){
					JOptionPane.showMessageDialog(new JFrame(),"Please fill in all the fields!", "Error",JOptionPane.WARNING_MESSAGE);
					return;	
				}
				
				AccountOriginal tempAccount = new AccountOriginal();
				
				if(studentRadioButton.isSelected()){
					tempAccount = new AccountOriginal(nameField.getText(),userName.getText(),new String(passField.getPassword()),"student");
				}else{
					tempAccount = new AccountOriginal(nameField.getText(),userName.getText(),new String(passField.getPassword()),"lecturer");
				}
			}
		});
		GridBagConstraints gbc_signUpButton = new GridBagConstraints();
		gbc_signUpButton.anchor = GridBagConstraints.WEST;
		gbc_signUpButton.insets = new Insets(0, 0, 0, 5);
		gbc_signUpButton.gridx = 3;
		gbc_signUpButton.gridy = 7;
		contentPane.add(signUpButton, gbc_signUpButton);
	}

}

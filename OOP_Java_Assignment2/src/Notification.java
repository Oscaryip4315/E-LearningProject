import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;


public class Notification {
	public static void main(String[] args){
			String message = "You got a new notification message. Isn't it awesome to have such a notification message.";
			String header = "Appointment notification";
			JFrame frame = new JFrame();
			frame.setSize(300,125);
			frame.setUndecorated(true);
			frame.setLayout(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = 1.0f;
			constraints.weighty = 1.0f;
			constraints.insets = new Insets(5, 5, 5, 5);
			constraints.fill = GridBagConstraints.BOTH;
			Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
			Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());// height of the task bar
			frame.setLocation(scrSize.width - frame.getWidth(), scrSize.height - toolHeight.bottom - frame.getHeight());
			JLabel headingLabel = new JLabel(header);
			headingLabel .setIcon(new ImageIcon(Notification.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif"))); // --- use image icon you want to be as heading image.
			headingLabel.setOpaque(false);
			frame.add(headingLabel, constraints);
			constraints.gridx++;
			constraints.weightx = 0f;
			constraints.weighty = 0f;
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.NORTH;
			JButton closeButton = new JButton("X");
			closeButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					frame.dispose();
				}
			});
			closeButton.setMargin(new Insets(1, 4, 1, 4));
			closeButton.setFocusable(false);
			frame.add(closeButton, constraints);
			constraints.gridx = 0;
			constraints.gridy++;
			constraints.weightx = 1.0f;
			constraints.weighty = 1.0f;
			constraints.insets = new Insets(5, 5, 5, 5);
			constraints.fill = GridBagConstraints.BOTH;
			JLabel messageLabel = new JLabel("<HtMl>"+message);
			frame.add(messageLabel, constraints);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			new Thread(){
			      @Override
			      public void run() {
			           try {
			                  Thread.sleep(3000); // time after which pop up will be disappeared.
			                  frame.dispose();
			           } catch (InterruptedException e) {
			                  e.printStackTrace();
			           }
			      };
			}.start();
	}
	
}

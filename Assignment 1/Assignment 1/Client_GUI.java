/**
 * @author 100583384
 * 
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.net.*;
import java.awt.Color;

public class Client_GUI {
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_GUI window = new Client_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client_GUI() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("Trivia Game - Savan Patel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		final Socket client = null;
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			//when Start button is pressed the connection to server gets establsihed
			public void actionPerformed(ActionEvent e) {
				
				//launch the client connection and navigate to the next screen
				try {
					Client.startConnection(client);
					Second_Screen second = new Second_Screen();
					frame.setVisible(false);
					second.setVisible(true);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnStart.setBounds(173, 80, 85, 21);
		frame.getContentPane().add(btnStart);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.BLACK);
		btnExit.setBackground(Color.RED);
		btnExit.addActionListener(new ActionListener() {
			//when exit button is pressed application and connection to server is closed
			public void actionPerformed(ActionEvent e) {
				try {
					Client.closeConnection();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnExit.setBounds(173, 155, 85, 21);
		frame.getContentPane().add(btnExit);
	}
}

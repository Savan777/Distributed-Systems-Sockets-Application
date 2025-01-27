/**
 * @author 100583384
 * 
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Second_Screen extends JFrame {

	private JPanel contentPane;
	private JTextField txtAnswer;
	private JButton btnSubmit;
	private JTextArea txtrQuestion;
	private JLabel lblAnswer;
	static String question;
	private int counter = 1;
	private JButton btnDownloadQuestions;
	private JButton btnHint;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Second_Screen frame = new Second_Screen();
					frame.setVisible(true);
					frame.setTitle("Trivia Game - Savan Patel");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public Second_Screen() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 426, 0};
		gbl_contentPane.rowHeights = new int[]{22, 0, 0, 0, 212, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		final int numOfQues = Client.getNumOfQuestions();
		
		//get the first question
		question = Client.getQuestion();
		
		//question text area properties
		txtrQuestion = new JTextArea();
		txtrQuestion.setFont(new Font("Monospaced", Font.PLAIN, 17));
		txtrQuestion.setLineWrap(true);
		txtrQuestion.setEditable(false);
		txtrQuestion.setText("Question:");
		GridBagConstraints gbc_txtrQuestion = new GridBagConstraints();
		gbc_txtrQuestion.gridwidth = 2;
		gbc_txtrQuestion.fill = GridBagConstraints.BOTH;
		gbc_txtrQuestion.insets = new Insets(5, 5, 5, 0);
		gbc_txtrQuestion.gridx = 1;
		gbc_txtrQuestion.gridy = 4;
		contentPane.add(txtrQuestion, gbc_txtrQuestion);
		txtrQuestion.setText("Question: "+question);
		
		//answer label properties
		lblAnswer = new JLabel("Answer:");
		GridBagConstraints gbc_lblAnswer = new GridBagConstraints();
		gbc_lblAnswer.gridwidth = 2;
		gbc_lblAnswer.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_lblAnswer.gridx = 1;
		gbc_lblAnswer.gridy = 6;
		contentPane.add(lblAnswer, gbc_lblAnswer);
		
		//answer text field properties
		txtAnswer = new JTextField();
		GridBagConstraints gbc_txtAnswer = new GridBagConstraints();
		gbc_txtAnswer.gridwidth = 2;
		gbc_txtAnswer.insets = new Insets(0, 0, 5, 0);
		gbc_txtAnswer.fill = GridBagConstraints.BOTH;
		gbc_txtAnswer.gridx = 1;
		gbc_txtAnswer.gridy = 7;
		contentPane.add(txtAnswer, gbc_txtAnswer);
		txtAnswer.setColumns(10);
		
		//initializing the submit button
		btnSubmit = new JButton("Submit");
		//when the submit button is pressed get the user answer and check with server
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//will check if the answer is empty or not and if so then give error message, else send to server for checking
					if (txtAnswer.getText().isEmpty()) {
						JOptionPane.showMessageDialog(txtrQuestion,"Please enter an answer.","Warning",JOptionPane.WARNING_MESSAGE);
					}
					else {
						//only request question from server if server has more.
						if (counter <= numOfQues) {
							//get answer from user
							String answer = txtAnswer.getText();
							String result = Client.checkAnswer(answer); //send to server for checking
							String playerScore = Client.getScore();
							JOptionPane.showMessageDialog(txtrQuestion,"Your answer is "+result+". Your Score is "+playerScore +"/"+numOfQues+" The next question will be displayed.","RESULT",JOptionPane.PLAIN_MESSAGE);
							//only update question if more questions are left
							if (counter < numOfQues) {
								updateQuestion();
							}
						}
						counter++;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnHint = new JButton("Hint");
		btnHint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getHint();
					JOptionPane.showMessageDialog(txtrQuestion,"A Hint.html file has been created, take a look on the Desktop for it.","Hint",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnHint = new GridBagConstraints();
		gbc_btnHint.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHint.insets = new Insets(0, 0, 5, 5);
		gbc_btnHint.gridx = 1;
		gbc_btnHint.gridy = 8;
		contentPane.add(btnHint, gbc_btnHint);
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 0);
		gbc_btnSubmit.gridx = 2;
		gbc_btnSubmit.gridy = 8;
		contentPane.add(btnSubmit, gbc_btnSubmit);
		
		btnDownloadQuestions = new JButton("Download Questions");
		btnDownloadQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				try {
					Client.getFile();
				} catch (IOException e1) {
					
					e1.printStackTrace();
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnDownloadQuestions = new GridBagConstraints();
		gbc_btnDownloadQuestions.anchor = GridBagConstraints.EAST;
		gbc_btnDownloadQuestions.insets = new Insets(0, 0, 5, 0);
		gbc_btnDownloadQuestions.gridx = 2;
		gbc_btnDownloadQuestions.gridy = 9;
		contentPane.add(btnDownloadQuestions, gbc_btnDownloadQuestions);
	}
	
	//gets next question from server
	public void updateQuestion() throws IOException {
		question = Client.getQuestion();
		//display the question
		txtrQuestion.setText("Question: "+question);
		txtAnswer.setText("");
	}
	
	public void getHint() throws IOException {
		final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
		String searchURL = GOOGLE_SEARCH_URL + "?q="+question.toString()+"&num="+2;
		Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
		
		Path outputPath = Paths.get("C:\\Users\\100583384\\Desktop\\Hint.html");
		Files.write(outputPath, doc.html().getBytes());
		//System.out.println(doc.html());
		
		Elements results = doc.select("h3.r > a");

		for (Element result : results) {
			String linkHref = result.attr("href");
			String linkText = result.text();
			System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
		}
		
	}
	
}

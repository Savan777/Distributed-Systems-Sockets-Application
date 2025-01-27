/**
 * @author 100583384
 * 
 */

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//this class will be used to communicate to the server, send and recieve requests.
//the client_gui will be leveraging the functions in this class for communications.
public class Client {
	static Socket client;
	static BufferedReader in;
	static PrintWriter out;
	static int Request_Code1 = 1; //this code will be used to tell the server that a question has been requested
	static int Request_Code2 = 2; //this code will tell the server that the answer needs to be checked
	static int questionID;
	
	//this method will be called to from the Client GUI to connect with Server
	public static void startConnection(Socket client) throws Exception {
		try {
			//connecting to server(local host) on port 4000
			client = new Socket("localhost", 4000);
			out = new PrintWriter(client.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}
	
	//gets the next question from the server
	public static String getQuestion() throws IOException {
		out.println(Request_Code1);
		String question = in.readLine();
		questionID = Integer.parseInt(in.readLine());
		System.out.println("The server sent: "+question + " ID: " + questionID);
		return question;
	}
	
	//sends the answer to server for checking and returns if the answer is correct or not
	public static String checkAnswer(String Answer) throws IOException {
		out.println(Request_Code2);
		out.println(questionID);
		out.println(Answer);
		String A = in.readLine();
		return A;
	}
	
	//gets the updated score from the server
	public static String getScore() throws IOException {
		out.println(3);//sending request to server to get the score of the client
		String score = in.readLine();
		return score;
	}
	
	//gets the number of questions from the server based on request code
	public static int getNumOfQuestions() throws NumberFormatException, IOException {
		out.println(4);//sending request to server to get how many questions there are
		int numOfQues = Integer.parseInt(in.readLine());
		return numOfQues;
	}
	
	//gets the file from server
	public static void getFile() throws IOException, ClassNotFoundException {
		out.println(5);//send request to server
		//set download path
		Path outputPath = Paths.get("C:\\Users\\100583384\\Desktop\\outputQuestions.txt");
		ArrayList<String> list = new ArrayList<String>();
		//read the file from server
		for (int i = 0; i < 5; i++) {
			list.add(in.readLine());
		}
		//write file to download path
		Files.write(outputPath,list,Charset.defaultCharset());
	}
	
	//this will close the connection established
	public static void closeConnection() throws Exception {
			
			//client.close;
			System.exit(0);
	}
}

/**
 * 
 */

/**
 * @author 100583384
 *
 */

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

	private ServerSocket server;
	
	public static void main(String argv[]) throws Exception {
		new Server(); //calling constructor
	}
	
	//constructor gets called when new object is created
	public Server() throws Exception {
		//create socket on port
		server = new ServerSocket(4000);
		System.out.println("Server listening on port 4000.");
		this.start();
	}
	
	//run method that starts a new thread of connection
	public void run() {
		while (true) {
			try {
				Socket client = server.accept(); //establishes the connection with client
				System.out.println("Accepted a connection from: " + client.getRemoteSocketAddress());
				Connection cc = new Connection(client);
			}
			catch (Exception e) {
				System.out.println("Exception: "+e);
		    }
		}
	}
}

class Connection extends Thread {
	private Socket client;
	PrintWriter out;
	BufferedReader in;
	private int Request_Code;
	int questionID = 0;
	String answer = "";

	
	//constructor
	public Connection(Socket s) {
		client = s; 
		
		//will try to initialize the input and output streams
		try {
			//Initializing output stream to send output
			out = new PrintWriter(client.getOutputStream(),true);
			//Initializing input stream to read input 
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			
		}catch (IOException e) { //if failure then catch and handle it
			try {
				//close the client
	             client.close();
	           } catch (IOException ex) {
	             System.out.println("Error while getting socket streams.."+ex);
	           }
	           return;
		}
		//calls the run() method to start the thread
		this.start();
	}
	
	//this run method starts a new thread within the previous thread so that multiple clients can join
	public void run() {
		try {
			//read the file to get the questions
			Questions[] question; //create an array of type Questions 
			question = readQuestions(); //store questions into variable
			int score = 0;
			while(true) {
			//read the request from client
				Request_Code = Integer.parseInt(in.readLine());
				switch (Request_Code) {
					case 1: 
						//if the request is 1 then send next question to client
						if (questionID <= question.length) {
							out.println(question[questionID].getQuestion());
							out.println(question[questionID].getQuestionNumber());
							break;
						}
				
					case 2:
						//if request is 2 then check answer given by client with stored answer
						questionID = Integer.parseInt(in.readLine());//read the question ID sent by client
						//make sure question is not out of bound
						if (questionID <= question.length) {
							answer = in.readLine().toLowerCase();//read the client answer
							if (answer.equals(question[questionID - 1].getAnswer())) {
								out.println("Correct");
								score = score+1;
								break;
							}
							else {
								out.println("Incorrect");
								break;
							}
						}
						
						//this request from client means to send the updated score
					case 3:
						out.println(score);
						break;
						//this request from client will send the amount of questions
					case 4:
						out.println(question.length);
						break;
						
					case 5:
						//send file to client
						for (int i = 0; i < question.length; i++) {
							out.println(question[i].getQuestionNumber()+") "+question[i].getQuestion()+" "+question[i].getAnswer());
						}
				}
			}
			
		}catch (IOException e) {
			System.out.println(e.getMessage() + " by Client " + client.getRemoteSocketAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/*
	 * Reads the questions from the questions file and stores it in an array
	 */
	public Questions[] readQuestions() throws IOException, ParseException{
		int numOfLines = countLines("questions.txt");
		Questions[] question = new Questions[numOfLines]; //Initialize the array
		File file = new File("questions.txt"); //create a file path
		List<String> list = new ArrayList<String>(); //temp store all the contents of the file
		int counter = 0; //this counter will be used to initialize the questions array one by one
		boolean skipFirstLine = true; //need to skip the first line read because it is a header
		//if the file exists, read it
		if(file.exists()){
	        try { 
	            list = Files.readAllLines(file.toPath(),Charset.defaultCharset());
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	      if(list.isEmpty()) //if the contents of the list is empty print message
	    	  System.out.println("questions file is empty.");
	    }
		//loop through the lines and initialize the questions
	    for(String line : list){
	        String [] res = line.split(","); //splits the lines by , so that the questions and answers can be separated
	        if (skipFirstLine) {
	        	skipFirstLine = false;
	        	continue;
	        }
	        question[counter] = new Questions(Integer.parseInt(res[0]),res[1],res[2]);
        	counter++;
	    }
	    return question; //returns the question array that has the questions loaded
	}
	
	/*
	 * this method will count the amount of lines in the questions.txt file 
	 * so that it can be used to create the dynamic array size which will store the questions
	 */
	public static int countLines(String filename) throws IOException {
	    InputStream inStream = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];

	        int readChars = inStream.read(c);
	        if (readChars == -1) {
	            // exit if there is nothing to read
	            return 0;
	        }

	        // make it easy for the optimizer to tune this loop
	        int count = 0;
	        while (readChars == 1024) {
	            for (int i=0; i<1024;) {
	                if (c[i++] == '\n') {
	                    ++count;
	                }
	            }
	            readChars = inStream.read(c);
	        }

	        // count remaining characters
	        while (readChars != -1) {
	            for (int i=0; i<readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	            readChars = inStream.read(c);
	        }
	        //return the amount of lines in file
	        return count == 0 ? 1 : count;
	    } finally {
	        inStream.close();
	    }
	}
}

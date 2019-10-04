/**
 * @author 100583384
 * 
 */

// this class will be used to create and object of type questions
// when the server reads the questions file, it will store those values into this object.
public class Questions {
	
	private int questionNumber;
	private String question, answer;
	
	//constructor
	public Questions(int number, String question, String answer) {
		questionNumber = number;
		this.question = question;
		this.answer = answer;
	}
	
	//getters
	public int getQuestionNumber() {
		return questionNumber;
	}
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}

}

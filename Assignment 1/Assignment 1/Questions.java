/**
 * @author 100583384
 * 
 */

public class Questions {
	
	private int questionNumber;
	private String question, answer;
	
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
package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class MCQ_Question extends Questions {
	
	
	@ManyToOne
	@JsonIgnore
	MCQ_Game MCQ_TheGame;
	
	
	private String Choices[];
	public MCQ_Question(){
		super();
	}
	public MCQ_Question( String Question ,String answer , String [] Choices){
		this();
		this.setAnswer(answer);
		this.setQuestion(Question);
		this.Choices = Choices;
	}
	@JsonIgnore
	public MCQ_Game getMCQ_TheGame() {
		return MCQ_TheGame;
	}
	public void setMCQ_TheGame(MCQ_Game mCQ_TheGame) {
		MCQ_TheGame = mCQ_TheGame;
	}
	public MCQ_Question( String Question,String answer , String [] Choices,Game game){
		this();
		this.setAnswer(answer);
		this.setQuestion(Question);
		this.Choices = Choices;
	}
	@JsonIgnore
	public String [] getChoices() {
		return Choices;
	}
	public void setChoices(String [] choices) {
		Choices = choices;
	}

}

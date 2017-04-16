package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
public class TF_Question extends Questions{
	@JsonIgnore
	@ManyToOne
	private TF_Game TF_TheGame;
	
	@JsonIgnore
	public TF_Game getTF_TheGame() {
		return TF_TheGame;
	}
	public void setTF_TheGame(TF_Game tF_TheGame) {
		TF_TheGame = tF_TheGame;
	}
	public TF_Question() {
		super();
	}
	public TF_Question(String question,String answer) {
		this();
		this.setQuestion(question);
		this.setAnswer(answer);
	}
	public TF_Question(String question,String answer,TF_Game game) {
		this();
		this.setQuestion(question);
		this.setAnswer(answer);
		TF_TheGame = game;
	}
}

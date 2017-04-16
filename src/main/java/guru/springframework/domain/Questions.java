package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public abstract class Questions {
	@NotNull
	@NotEmpty
	private String Question;
	
	@NotNull
	@NotEmpty
	private String Answer;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public final Long id ; 
	
	
	public Questions(){
		id = null;
		Question = Answer = null;
	}
	public Long getId() {
		return id;
	}
	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
	
	

}

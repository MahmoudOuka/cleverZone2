package guru.springframework.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;



@Entity
public class MCQ_Game extends  Game{
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="MCQ_TheGame",targetEntity = MCQ_Question.class)
	
	private List<MCQ_Question> Questions;
	

	public MCQ_Game(){
		super();
		Questions = new ArrayList<>();
	}
	public MCQ_Game(String name , String desc , String imageSrc){
		this();
		this.setName(name);
		this.setdescption(desc);
		this.setImageSrc(imageSrc);
	}
	
	
	public List<MCQ_Question> getQuestions() {
		return Questions;
	}
	public void addQuestion(MCQ_Question question) {
		question.setMCQ_TheGame(this);
		Questions.add(question);
	}

	
}

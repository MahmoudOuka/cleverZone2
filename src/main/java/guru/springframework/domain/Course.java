package guru.springframework.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final Long id;
	@NotNull
	@NotEmpty
	String name;
	@NotEmpty
	@NotNull
	String descption;
	@NotEmpty
	@NotNull
	String imageSrc;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="course",targetEntity = AbstractContent.class)
	private List<AbstractContent> Contents;
	@ManyToOne
	User Teacher;
	
	
	public Course() {
		id = null;
		Contents = new ArrayList<>();
	}
	
	public Course(String name , String desc , String imageSrc){
		this();
		this.descption = desc;
		this.name = name;
		this.imageSrc = imageSrc;
	}
	@JsonIgnore
	public User getTeacher() {
		return Teacher;
	}

	public void setTeacher(User teacher) {
		Teacher = teacher;
	}

	public List<AbstractContent> getContents() {
		return Contents;
	}


	public void addContents(AbstractContent content) {
		content.setCourse(this);
		Contents.add(content);
	}


	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescption() {
		return descption;
	}

	public void setDescption(String descption) {
		this.descption = descption;
	}

	public String getImageSrc() {
		return imageSrc;
	}
	public Long getId() {
		return this.id;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	
}

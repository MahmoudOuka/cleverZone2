package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractContent {
	 @Id 
	 @GeneratedValue(strategy = GenerationType.TABLE)
	 private Long id;
	 
	 @JsonIgnore
	 @ManyToOne
	 private Course course ;
	 
	 public AbstractContent() {
		id = null;
	}
	public Long getId() {
		return id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
}

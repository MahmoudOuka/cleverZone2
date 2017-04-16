package guru.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.domain.Course;
import guru.springframework.domain.MCQ_Question;
@Repository
public interface MCQQuestionRepository extends JpaRepository<MCQ_Question, Long> {
	MCQ_Question findByQuestion(String Question);
}

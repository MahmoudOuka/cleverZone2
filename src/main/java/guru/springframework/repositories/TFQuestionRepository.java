package guru.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.domain.MCQ_Question;
import guru.springframework.domain.TF_Question;
@Repository
public interface TFQuestionRepository extends JpaRepository<TF_Question, Long> {
	TF_Question findByQuestion(String Question);
}

package guru.springframework.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.domain.MCQ_Game;
import guru.springframework.domain.TF_Game;

@Repository
public interface MCQGameRepository extends JpaRepository<MCQ_Game, Long> {
	MCQ_Game findByName(String name);
}

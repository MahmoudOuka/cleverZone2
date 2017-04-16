package guru.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.domain.Course;



@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	
	Course findByname(String name);

}

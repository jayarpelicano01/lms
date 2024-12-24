package proj.ws101.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proj.ws101.lms.entity.Course;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}


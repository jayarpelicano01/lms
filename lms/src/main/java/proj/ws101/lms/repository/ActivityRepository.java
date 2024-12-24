package proj.ws101.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proj.ws101.lms.entity.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByActivityType(String activityType);

    // Custom query to get activities by course ID and activity type 'Assignment'
    @Query("SELECT a FROM Activity a WHERE a.course.id = :courseId AND a.activityType = 'Assignment'")
    List<Activity> findAssignmentsByCourseId(Long courseId);

    // Optional: Custom method to get all activities of type 'Assignment' (without filtering by course)
    @Query("SELECT a FROM Activity a WHERE a.activityType = 'Assignment'")
    List<Activity> findAllAssignments();

    // Custom query to get activities by course ID and activity type 'Assignment'
    @Query("SELECT a FROM Activity a WHERE a.course.id = :courseId AND a.activityType = 'Quiz'")
    List<Activity> findQuizzesByCourseId(Long courseId);

    // Optional: Custom method to get all activities of type 'Assignment' (without filtering by course)
    @Query("SELECT a FROM Activity a WHERE a.activityType = 'Quiz'")
    List<Activity> findAllQuizzes();
}


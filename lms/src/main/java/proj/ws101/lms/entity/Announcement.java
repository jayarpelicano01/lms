package proj.ws101.lms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;

    private String what;

    @Column(name = "`where`")
    private String where;

    @Column(name = "`when`")
    private String when;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    @JsonBackReference("course-announcements")
    private Course course;

}

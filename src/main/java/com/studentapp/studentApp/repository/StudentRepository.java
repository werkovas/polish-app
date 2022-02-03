package com.studentapp.studentApp.repository;

import com.studentapp.studentApp.IQuery.IRankUserQuery;
import com.studentapp.studentApp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByUsername(String username);


    @Query(value = "select V.id as id, V.username as username, V.points as points " +
            "from ( " +
            "         select s.id, " +
            "                s.username, " +
            "                s.points, " +
            "                row_number() over (partition by s.id order by s2.test_time desc) as rank " +
            "         from student s " +
            "                  join score s2 on s.id = s2.id_student " +
            "     ) as V " +
            "where V.rank = 1 " +
            "order by V.points desc " +
            "limit 10 ", nativeQuery = true)
    List<IRankUserQuery> getTopUser();

}

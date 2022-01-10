package practice.task.rba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.task.rba.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}

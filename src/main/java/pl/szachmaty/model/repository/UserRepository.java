package pl.szachmaty.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szachmaty.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
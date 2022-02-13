package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.entity.House;
import com.hendisantika.postgres.entity.Position;
import com.hendisantika.postgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPosition(Position position);

    User findByEmailAndPassword(String email, String password);
}
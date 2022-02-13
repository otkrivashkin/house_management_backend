package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.entity.House;
import com.hendisantika.postgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByAgent(User user);

    List<House> findByClient(User user);

    List<House> findByIdIn(List<Long> houseIds);
}
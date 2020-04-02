package com.zspt_sf.demo.repository;

import com.zspt_sf.demo.entity.Sign;
import com.zspt_sf.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignRepository extends JpaRepository<Sign,Integer> {
    List<Sign> findByUser(User user);
}

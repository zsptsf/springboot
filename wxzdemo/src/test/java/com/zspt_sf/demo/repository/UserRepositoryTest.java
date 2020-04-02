package com.zspt_sf.demo.repository;

import com.zspt_sf.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAll(){
        System.out.println(userRepository.findAll());
    };

    @Test
    void findByStu_number() {
        User user = userRepository.findByNumber("12610517101");
        System.out.println(user);
    }

    @Test
    void findByNameLike(){
        System.out.println(userRepository.findByNameLike("%"+"晓"+"%"));
    };


}
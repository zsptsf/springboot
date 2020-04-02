package com.zspt_sf.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClbumControllerRepositoryTest {

    @Autowired
    private ClbumRepository clbumRepository;

    @Test
    void findAll(){
        System.out.println(clbumRepository.findAll());
    };
}
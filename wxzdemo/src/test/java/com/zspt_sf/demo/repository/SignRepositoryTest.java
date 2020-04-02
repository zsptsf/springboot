package com.zspt_sf.demo.repository;

import com.zspt_sf.demo.entity.Sign;
import com.zspt_sf.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SignRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SignRepository signRepository;

    @Test
    void findAll(){
        System.out.println(signRepository.findAll());
    };

    @Test
    void findByUser() {
        User user = userRepository.findByNumber("12610517101");
        System.out.println(signRepository.findByUser(user));
    }

    @Test
    void addsave() {
        Sign sign = new Sign();
        User user = userRepository.findByNumber("12610517101");
        sign.setUser(user);
        Date date = new Date();
        sign.setSigntime(date);
        sign.setRemark("测试");
        Sign sign1 = signRepository.save(sign);
        System.out.println(sign1);
    }
}
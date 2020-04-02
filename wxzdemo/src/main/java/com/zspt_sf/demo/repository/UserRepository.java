package com.zspt_sf.demo.repository;

import com.zspt_sf.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
//JpaRepository<User, Integer> 泛型参数分别是：要查询的实体（Entity），实体的主键类型
//JpaSpecificationExecutor<User> 泛型参数：要查的实体
public interface UserRepository extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {
    User findByNumber(String number);
    List<User> findByNameLike(String s);
}

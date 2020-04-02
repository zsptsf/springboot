package com.zspt_sf.demo.repository;

import com.zspt_sf.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String name);
    List<Role> findByNameLike(String s);
}

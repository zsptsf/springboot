package com.zspt_sf.demo.repository;

import com.zspt_sf.demo.entity.Clbum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClbumRepository extends JpaRepository<Clbum,Integer> {
    List<Clbum> findByNameLike(String s);
}

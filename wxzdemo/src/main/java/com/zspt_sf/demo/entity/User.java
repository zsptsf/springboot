package com.zspt_sf.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class User implements Serializable {//一个对象序列化的接口，一个类只有实现了Serializable接口，它的对象才能被序列化。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "clbum_id")
    private Clbum clbum;
    private String number;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    private String avatar;
}

package com.zspt_sf.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Sign implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @JSONField(format = "yyyy.MM.dd HH:mm:ss")
    @Column(name = "sign_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date signtime;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String remark; // 内容
}

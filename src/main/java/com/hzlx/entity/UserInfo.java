package com.hzlx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private Integer id;
    private String userName;
    private String password;
    private String nickName;
    private String tel;
    private String address;
    private Date creationTime;
    private Integer status;
}

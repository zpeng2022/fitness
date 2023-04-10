package com.yiie.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class H5Background implements Serializable {
    private String id;
    private String background;
    private Date createtime;
    private Date updatetime;
}

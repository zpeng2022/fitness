package com.yiie.vo.data;

//import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigInteger;

@ApiModel("各项运动人数占比数值")
@Data
//@TableName("sportrate")
public class SportRank {
    private BigInteger id;
    private String name;
    private int value;

    public SportRank(String name, int value) {
        this.name = name;
        this.value = value;
    }
}

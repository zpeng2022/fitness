package com.yiie.vo.data;

//import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("各项运动人数占比数值")
@Data
//@TableName("sportrate")
public class SportAndValue {
    private String sport;
    private Integer value;

    public SportAndValue() {
    }

    public SportAndValue(String sport, int value) {
        this.sport = sport;
        this.value = value;
    }
}

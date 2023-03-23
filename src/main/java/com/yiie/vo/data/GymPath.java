package com.yiie.vo.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GymPath {
    @ApiModelProperty(value = "gymId")
    private String gymId;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "路径")
    private String path;

    public GymPath() {
    }

    public GymPath(String gymId, Integer id, String path) {
        this.gymId = gymId;
        this.id = id;
        this.path = path;
    }
}

package com.yj.swagger3.demo.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author DUCHONG
 * @since 2020-10-24 16:32
 **/
@Data
@ApiModel(value = "用户信息接口入参")
public class UserVO {

    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "地址")
    private String address;
}

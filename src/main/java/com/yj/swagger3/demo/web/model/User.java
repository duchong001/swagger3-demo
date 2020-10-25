package com.yj.swagger3.demo.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户
 *
 * @author DUCHONG
 * @since 2020-10-24 16:19
 **/
@Data
@Accessors(chain = true)
@ApiModel("用户接口返回队形")
public class User implements Serializable {

    private static final long serialVersionUID = -6184820859988580981L;

    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "地址")
    private String address;
}

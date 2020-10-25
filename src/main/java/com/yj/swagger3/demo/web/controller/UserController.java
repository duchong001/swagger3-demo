package com.yj.swagger3.demo.web.controller;

import com.yj.swagger3.demo.web.model.User;
import com.yj.swagger3.demo.web.model.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 用户控制器
 * @author DUCHONG
 * @since 2020-10-24 16:18
 **/
@Slf4j
@Api(tags = "用户控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    static List<User> list= new ArrayList<>();
    static Long maxLimit=20L ;
    static {

        for (long i = 0; i < maxLimit; i++) {
            User u=new User()
                    .setId(i)
                    .setUserName(UUID.randomUUID().toString())
                    .setAddress("address"+i)
                    .setPhone("1001011-"+i);
            list.add(u);

        }
    }

    @ApiOperation(value = "获取用户列表")
    @GetMapping("/getUserList")
    @ApiResponse(code=200,message = "",response=List.class)
    public List<User> getUserList(){

        return list;
    }

    @ApiOperation(value = "获取用户信息接口-单个入参")
    @GetMapping("/getUser")
    @ApiResponse(code=200,message = "",response=User.class)
    public User getUser(@ApiParam(name = "userId",value = "用户id") Long userId, @ApiParam(name = "address",value = "用户id")String address){

       return  list.stream().filter(u->u.getId().equals(userId) && u.getAddress().equals(address))
                     .filter(Objects::nonNull)
                     .findFirst()
                     .orElse(new User())
                ;
    }

    @ApiOperation(value = "获取用户信息接口2-实体对象入参")
    @GetMapping("/getUser2")
    @ApiResponse(code=200,message = "",response=User.class)
    public User getUser2(UserVO userVO){

        return  list.stream().filter(u->u.getId().equals(userVO.getId())
                                    && u.getAddress().equals(userVO.getAddress()))
                .filter(Objects::isNull)
                .findFirst()
                .orElse(new User())
                ;
    }

}

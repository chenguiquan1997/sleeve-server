package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.core.UnifyResponse;
import com.quan.windsleeve.core.annotation.ScopeLevel;
import com.quan.windsleeve.dto.MinUserDTO;
import com.quan.windsleeve.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/1 8:27
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private IUserService userService;
    /**
     * @Description: 注册新用户
     * @param minUserDTO
     * @return io.github.talelin.latticy.vo.CreatedVO
     * @Author: Guiquan Chen
     * @Date: 2021/5/31
     */
    @PostMapping("/save")
    @ScopeLevel
    public void save(@RequestBody @Validated MinUserDTO minUserDTO) {
        userService.save(minUserDTO);
        UnifyResponse.success(00000);
    }

    /**
     * @Description: 根据 昵称 查询用户信息
     * @param nickName
     * @return java.lang.Boolean
     * @Author: Guiquan Chen
     * @Date: 2021/6/1
     */
    @GetMapping("/searchUser")
    public Boolean searchUser(@RequestParam("nickName") @NotEmpty String nickName) {
        return userService.findByNickname(nickName);
    }
}

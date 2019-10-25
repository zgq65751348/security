package org.peak.myth.security.controller;

import org.peak.myth.security.entity.User;
import org.peak.myth.security.response.ResponseBody;
import org.peak.myth.security.response.ResponseUtil;
import org.peak.myth.security.service.UserService;
import org.peak.myth.security.utils.RequestVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *
 * </p>
 * windows 7  旗舰版
 *
 * @author 墨茗琦妙
 * @since 2019/10/23 0023 下午 5:32
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping(value = "/register")
    public ResponseBody userRegister(@Valid @RequestBody User user , BindingResult result){
        RequestVerifyUtil.valid(result);
        User newUser = userService.register(user);
        if(null != newUser ){
            return ResponseUtil.returnSuccess(newUser);
        }
        return ResponseUtil.returnFail();
    }

    @PostMapping(value="/login")
    public ResponseBody userLogin(@Valid @RequestBody User user , BindingResult result){
        RequestVerifyUtil.valid(result);
        return  userService.login(user);
    }


}

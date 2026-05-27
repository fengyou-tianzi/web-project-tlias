package com.gzlg.controller;

import com.gzlg.pojo.Emp;
import com.gzlg.pojo.LoginInfo;
import com.gzlg.pojo.Result;
import com.gzlg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        log.info("员工登录, 用户名: {}", emp.getUsername());
        LoginInfo loginInfo = empService.login(emp);
        return Result.success(loginInfo);
    }
}

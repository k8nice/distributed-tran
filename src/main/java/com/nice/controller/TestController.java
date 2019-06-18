package com.nice.controller;

import com.nice.domain.SysUser;
import com.nice.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ningh
 */
@Controller
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/set")
    public String test(){
        sysUserService.addSysUser(new SysUser(1L,"nice"));
        return "";
    }

}

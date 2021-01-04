package com.github.maojx0630.rbac.sys.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/user")
public class SysUserController {

    @GetMapping("list")
    public String getList(){
        return "ListUtil.empty()";
    }
}

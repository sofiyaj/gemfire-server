package com.jc.controller;

import com.jc.service.AppService;

import org.gj.demo.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 19:07 2018/1/8
 */
@RestController
@RequestMapping("/geode")
@CrossOrigin
public class AppController {
    @Autowired
    AppService appService;

    @RequestMapping("/add")
    @ResponseBody
    public Customer addCustomer(long id, String firstName, String lastName){
        return  appService.addCustomer(id, "wang", "jie");
    }


}

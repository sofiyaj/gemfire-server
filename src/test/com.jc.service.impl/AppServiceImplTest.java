package com.jc.service.impl;

import com.jc.service.AppService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 13:58 2018/1/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppServiceImplTest {
    @Autowired
    AppService appService;
    @Test
    public void addCustomer(){
        appService.addCustomer(89,"wang","jie",25);
    }


}

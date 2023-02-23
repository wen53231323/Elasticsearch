package com.wen.elasticsearch.controller;


import com.wen.elasticsearch.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Hotel)表控制层
 *
 * @author makejava
 * @since 2023-02-23 18:04:06
 */
@RestController
@RequestMapping("hotel")
public class HotelController {
    /**
     * 服务对象
     */
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public void test() {
        
    }

}


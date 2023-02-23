package com.wen.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wen.elasticsearch.entity.Hotel;
import com.wen.elasticsearch.mapper.HotelMapper;
import com.wen.elasticsearch.service.HotelService;
import org.springframework.stereotype.Service;

/**
 * (Hotel)表服务实现类
 *
 * @author makejava
 * @since 2023-02-23 18:04:08
 */
@Service("hotelService")
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements HotelService {

}


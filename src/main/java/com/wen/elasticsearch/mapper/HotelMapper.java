package com.wen.elasticsearch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.elasticsearch.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Hotel)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-23 18:04:07
 */
@Mapper
@Repository
public interface HotelMapper extends BaseMapper<Hotel> {

}


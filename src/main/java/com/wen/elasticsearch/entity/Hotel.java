package com.wen.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

// jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// Lombok提供的注解，代表get、set、toString、equals、hashCode等操作
@Data
// Lombok提供的注解，代表对应字段的 setter 方法调用后，会返回当前对象，代替返回的void
@Accessors(chain=true)
// Lombok提供的注解，代表无参构造
@NoArgsConstructor
// Lombok提供的注解，代表全参构造
@AllArgsConstructor
public class Hotel implements Serializable { // 实现Serializable接口，对象能被序列化
    // 反序列化的过程需要使用serialVersionUID
    private static final long serialVersionUID = -53816139340284927L;

    // 酒店id
    private Long id;

    // 酒店名称
    private String name;

    // 酒店地址
    private String address;

    // 酒店价格
    private Integer price;

    // 酒店评分
    private Integer score;

    // 酒店品牌
    private String brand;

    // 所在城市
    private String city;

    // 酒店星级，1星到5星，1钻到5钻
    private String starName;

    // 商圈
    private String business;

    // 纬度
    private String latitude;

    // 经度
    private String longitude;

    // 酒店图片
    private String pic;
}


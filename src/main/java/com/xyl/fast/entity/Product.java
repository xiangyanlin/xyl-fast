package com.xyl.fast.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xiangyanlin
 * @date 2021/11/11
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**商品唯一标识*/
    private Long id;
    /**商品名称*/
    private String title;
    /**分类名称*/
    private String category;
    /**商品价格*/
    private Double price;
    /**图片地址*/
    private String images;
}

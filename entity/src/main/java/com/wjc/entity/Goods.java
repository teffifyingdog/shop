package com.wjc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Goods extends BaseEntity  {
    private String title;
    private String description;
    private BigDecimal price;
    private Integer gnum;
    private Integer type;
    @TableField(exist = false)
    private String cover;
    @TableField(exist = false)
    private List<String> imgs;
    @TableField(exist = false)
    private List<GoodsImage> goodsImages;
    @TableField(exist = false)
    private Seckill seckill;

}
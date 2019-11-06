package com.wjc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "shopcar")
public class ShopCar extends BaseEntity {

    private Integer uid;
    private Integer gid;
    private Integer gnum;
    private BigDecimal price;

    @TableField(exist = false)
    private Goods goods;

}

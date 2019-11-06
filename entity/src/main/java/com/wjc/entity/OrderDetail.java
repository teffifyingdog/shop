package com.wjc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "order_detail")
public class OrderDetail extends BaseEntity{
    private String title;
    private String oid;
    private Integer gid;
    private BigDecimal price;
    private Integer gnum;
    private BigDecimal total_price;

    @TableField(exist = false)
    private Goods goods;
}

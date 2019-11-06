package com.wjc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Orders extends BaseEntity{

    private String oid;
    private Integer uid;
    private String receiver;
    private String address;
    private String phone;
    private String code;
    private BigDecimal totalPrice;//total_price  -> totalPrice

    @TableField(exist = false)
    private List<OrderDetail> details;
}

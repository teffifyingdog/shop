package com.wjc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Address extends BaseEntity{
    private String receiver;
    private String address;
    private String phone;
    private String code;
    private Integer isdefault;
    private Integer uid;
}

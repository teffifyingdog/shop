package com.wjc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    protected Integer id;
    protected Integer status=1;
    protected Date createTime=new Date();
}

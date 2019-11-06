package com.wjc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "goods_images")
public class GoodsImage extends BaseEntity{
    private Integer gid;
    private String info;
    private String url;
    private Integer iscover = 0;
}

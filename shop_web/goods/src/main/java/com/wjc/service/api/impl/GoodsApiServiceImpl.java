package com.wjc.service.api.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjc.dao.GoodsApiMapper;
import com.wjc.dao.GoodsImgApiMapper;
import com.wjc.dao.SeckillMapper;
import com.wjc.entity.Goods;
import com.wjc.entity.GoodsImage;
import com.wjc.feign.ItemFeign;
import com.wjc.feign.SearchFeign;
import com.wjc.service.api.IGoodsApiService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GoodsApiServiceImpl implements IGoodsApiService {
    @Autowired
    private GoodsApiMapper goodsApiMapper;

    @Autowired
    private GoodsImgApiMapper goodsImgApiMapper;

    @Autowired
    private SearchFeign searchFeign;

    @Autowired
    private ItemFeign itemFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Goods> getGoodsList() {

        return goodsApiMapper.selectGoodsList();
    }

    @Override
    @Transactional
    public int insertAGood(Goods goods) {
//        System.out.println("goods = " + goods);
        int insert = goodsApiMapper.insert(goods);
        goodsImgApiMapper.insert(new GoodsImage(goods.getId(),null,goods.getCover(),1));
        for (String img : goods.getImgs()) {
            goodsImgApiMapper.insert(new GoodsImage(goods.getId(),null,img,0));
        }

        //将秒杀商品放入seckill表中
        System.out.println("goods.getSeckill = " + goods.getSeckill());


        if (goods.getSeckill()!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = goods.getSeckill().getStartTime();
            Date endTime = goods.getSeckill().getEndTime();
            if (format.format(startTime).endsWith(":00:00")&&format.format(endTime).endsWith(":00:00")&&startTime.before(endTime)&&startTime.after(new Date())) {
                goods.getSeckill().setGid(goods.getId());
                int insert_seckill = seckillMapper.insert(goods.getSeckill());
            }else
                System.out.println("error date");
        }

        /*突然过时
        //将插入的信息传到solr
        if (!searchFeign.insert(goods)) {
           throw new RuntimeException("insert_solr_error");
        }

        //生成静态页面
        if (!itemFeign.createHtml(goods)) {
            throw new RuntimeException("create html error");
        }
        */
        //传递商品对象到交换机中
        rabbitTemplate.convertAndSend("exchange",goods.getType()==1?"normal":"seckill",goods);
        return insert;
    }

    @Override
    public Goods queryGoodsById(Integer id) {
        //获得商品对象
        Goods goods = goodsApiMapper.selectById(id);
        //查询封面的地址放入商品对象中
        QueryWrapper<GoodsImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gid",goods.getId());
        queryWrapper.eq("iscover",1);
        GoodsImage goodsImage = goodsImgApiMapper.selectOne(queryWrapper);
        goods.setCover(goodsImage.getUrl());
        return goods;
    }

    @Override
    public List<Goods> queryListByTime(Date date) {
        List<Goods> goods = goodsApiMapper.selectListByTime(date);
//        System.out.println("goods = " + goods);
        return goods;
    }
}

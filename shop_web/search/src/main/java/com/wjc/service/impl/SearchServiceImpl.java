package com.wjc.service.impl;

import com.wjc.entity.Goods;
import com.wjc.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Goods> search(String keyword) {
        System.out.println("keyword = " + keyword);
        SolrQuery query = new SolrQuery();
        if (keyword==null||keyword==""){
            query.setQuery("*:*");
        }else {
            query.setQuery("title:"+keyword+" || description:"+keyword);
        }
        //进行分页的设置
        //limit ?,?
        query.setStart(0);
        query.setRows(20);

        //设置高亮
        query.setHighlight(true);
        query.setHighlightSimplePre("<font color='red'>");  //高亮的前缀
        query.setHighlightSimplePost("</font>");    //高亮的后缀
        query.addHighlightField("title");   //设置那些地方需要高亮的
        query.addHighlightField("description");     //设置那些地方需要高亮的
        query.setHighlightSnippets(1);  //高亮折叠次数,默认为1，要展示几段高亮内容
        query.setHighlightFragsize(20);  //高亮折叠大小，每个折叠的最大长度，默认100


        List<Goods> goods=new ArrayList<>();

        try {
            QueryResponse queryResponse = solrClient.query(query);
            //查询结果
            SolrDocumentList results = queryResponse.getResults();

            //获得高亮的集合
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();


            //总共有几条数据
//            long numFound = results.getNumFound();

            for (SolrDocument result : results) {
                Goods good = new Goods();
                good.setId(Integer.parseInt((String) result.getFieldValue("id")));
                good.setTitle((String) result.getFieldValue("title"));
                good.setDescription((String) result.getFieldValue("description"));
                good.setPrice(BigDecimal.valueOf((double) result.getFieldValue("price")));
                good.setGnum((Integer) result.getFieldValue("gnum"));
                good.setCover((String) result.getFieldValue("cover"));


                if (highlighting.containsKey(good.getId()+"")) {//if是为了防止查询所有而报空指针,为什么string类型的才行？，明明int也可以放进去
                    //将该商品的高亮添加到对象中
                    Map<String, List<String>> stringListMap = highlighting.get(good.getId() + "");
                    if (stringListMap.containsKey("title")) {//防止空指针？？？？
                        String title = stringListMap.get("title").get(0);
                        good.setTitle(title);
                    }
                    if (stringListMap.containsKey("description")) {
                        String des="";
                        for (int i=0;i < stringListMap.get("description").size();i++) {
                            String description = stringListMap.get("description").get(i);
                            des+=description;
                        }
                        good.setDescription(des);
                    }
                }
                goods.add(good);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goods;
    }
}

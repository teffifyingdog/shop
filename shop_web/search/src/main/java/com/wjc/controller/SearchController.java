package com.wjc.controller;

import com.wjc.entity.Goods;
import com.wjc.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/search")
@Component
public class SearchController {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ISearchService searchService;

    @RequestMapping("/insert")
    @ResponseBody
    public boolean insert(Goods goods){

        SolrInputDocument solrInputFields = new SolrInputDocument();
        System.out.println("goods = " + goods);
        solrInputFields.addField("title",goods.getTitle());
        solrInputFields.addField("description",goods.getDescription());
        solrInputFields.addField("gnum",goods.getGnum());
        solrInputFields.addField("price",goods.getPrice().doubleValue());
        solrInputFields.addField("id",goods.getId());
        solrInputFields.addField("cover",goods.getCover());

        try {
            solrClient.add(solrInputFields);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @RequestMapping("/search")
    public String search(String keyword, Model model){
        System.out.println("shit");
        List<Goods> goods=searchService.search(keyword);
        model.addAttribute("goodsList",goods);
        return "searchlist";
    }
}

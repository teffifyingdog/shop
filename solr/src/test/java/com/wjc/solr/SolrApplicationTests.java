package com.wjc.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void contextLoads() {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("title"," 豆 豆 豆 豆 ");
        document.addField("description","asd as撒大噶 豆 的发豆豆 啊沙发沙发沙发手动阀手动阀啊啊 豆 发的发发豆豆 啊沙发沙发沙发手动阀手动阀啊啊 豆 发的发生豆偶读豆    豆 ");
        document.addField("price",99.56);
        document.addField("gnum",99);
        document.addField("images","SDGSG");
        document.addField("id",4);

        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void delete(){
        try {
            solrClient.deleteById("*");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void query(){
        SolrQuery query = new SolrQuery("*:*");
        try {
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();
            for (SolrDocument result : results) {
                String id = (String) result.getFirstValue("id");
                System.out.println("result = " + id);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

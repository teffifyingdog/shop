package com.wjc.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wjc.entity.Goods;
import com.wjc.feign.GoodsFegin;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/goodsManager")
public class GoodsController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private GoodsFegin goodsFegin;

    private String coverPath="D:\\images\\cover";

    private String otherImgPath="D:\\images\\other";

    @RequestMapping("/list")
    public String getGoodsList(Model model){
        List<Goods> goods = goodsFegin.goodsList();
        model.addAttribute("goods",goods);
        return "goodslist";
    }

    @ResponseBody
    @RequestMapping("/uploadCover")
    public String uploadCover(MultipartFile file){
        File uploadFile = new File(coverPath);

        return upload(file, uploadFile);
    }

    @RequestMapping("/uploadOtherImg")
    @ResponseBody
    public String uploadOtherImg(MultipartFile file){
        File uploadFile = new File(otherImgPath);

        return upload(file, uploadFile);
    }

    @RequestMapping("/showCover")
    public void showCover(String filename, HttpServletResponse response){
        File file = new File(coverPath+"\\"+filename);
        showImg(response, file);
    }

    @RequestMapping("/showOtherImg")
    public void showOtherImg(String filename, HttpServletResponse response){
        File file = new File(otherImgPath+"\\"+filename);
        showImg(response, file);
    }

    @RequestMapping("/insert")
    public String insert(Goods goods){
        boolean flag=goodsFegin.insert(goods);
        return flag?"redirect:http://localhost:60000/back/goodsManager/list":"error";
    }

    private void showImg(HttpServletResponse response, File file) {
        try(
                FileInputStream in = new FileInputStream(file);
                ServletOutputStream out = response.getOutputStream();
                ) {
            IOUtils.copy(in,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String upload(MultipartFile file, File uploadFile) {
//        if (!uploadFile.exists()) {
//            if (!uploadFile.mkdirs()) {
//                throw new RuntimeException("创建文件上传目录失败");
//            }
//        }
        //用fastdfs上传图片到存储器中
        //前端连接直接到存储器中取图片
        StorePath storePath = null;
        try {
            uploadFile = new File(uploadFile, UUID.randomUUID().toString());
            storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "jpg",
                    null
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fullPath = storePath.getFullPath();
//        try (
//                InputStream in = file.getInputStream();
//                OutputStream out = new FileOutputStream(uploadFile);
//        ) {
//            IOUtils.copy(in, out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "{\"filename\":\"http://192.168.252.222:8080/" + fullPath + "\"}";
    }

}

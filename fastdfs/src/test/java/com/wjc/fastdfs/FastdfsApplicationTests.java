package com.wjc.fastdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastdfsApplicationTests {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    public void contextLoads() throws FileNotFoundException {
        String pic="D:\\DESK\\picture\\animal\\wallhaven-0jokwq_3840x2160.png";
        StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(pic),
                new File(pic).length(),
                "jpg",
                null
        );
        String fullPath = storePath.getFullPath();
        System.out.println("fullPath = " + fullPath);



    }

}

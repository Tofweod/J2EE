package com.octenexin.ecnu.util;

import com.octenexin.ecnu.EcnuApplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;



public class FileSaveUtil {

    /**
     * @param file 论文原始文件
     * @param projectId 论文所在项目id
     * absolute path in 'classes' folder, like /classes/project2/asdfbslsiv.pdf
     * */
    public static String savePaper(MultipartFile file, String projectId){

        try {

            String url= String.valueOf(EcnuApplication.class.getClassLoader().getResource(""));
            System.out.println(url);

            url=url.substring(url.indexOf("/")+1);
            url+="upload/";
            //fixed above, absolute path

            try{
                Files.createDirectory(Paths.get(url));
            }catch (FileAlreadyExistsException e){
                //do nothing
            }


            url+="project"+projectId;
            System.out.println(url);//file:/C:/Users/HP/Desktop/j2ee/target/classes/project1

            try{
                Files.createDirectory(Paths.get(url));
            }catch (FileAlreadyExistsException e){
                //do nothing
            }

            //如果想去掉 '-' 且转换小写
            //String uuidString = UUID.randomUUID().toString().replace("-", "").toLowerCase();

            String fileName=file.getOriginalFilename();

//            assert fileName != null;
//            int i=fileName.lastIndexOf('.');
//            String ext="";
//            if (i>0){
//                ext=fileName.substring(i);
//                fileName=fileName.substring(0,i);
//            }





            String filepath=url+"/"+fileName;


            try (FileOutputStream fileOutputStream = new FileOutputStream(filepath)) {
                byte[] bytes = file.getBytes();
                fileOutputStream.write(bytes);
            }

            return filepath;



        } catch (Exception e) {
            System.out.println("写入文件内容出错");
            e.printStackTrace();
        }

       throw new RuntimeException("control shouldn't reach here");

    }
}

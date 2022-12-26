package com.octenexin.ecnu.util;

import com.octenexin.ecnu.EcnuApplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;



public class FileSaveUtil {


    public static String getFileLoadRootUrl(){

        String rootUrl=CustomVarUtil.uploadUrl;
        System.out.println(rootUrl);

        String url= String.valueOf(EcnuApplication.class.getClassLoader().getResource(""));
        url=url.substring(url.indexOf("/")+1);

        url+=rootUrl;

        return url;//C:\...classes/resources/
    }

    /**
     * @param file 论文原始文件
     * @param projectId 论文所在项目id
     * absolute path in 'classes' folder, like /classes/project2/asdfbslsiv.pdf
     * */
    public static String savePaper(MultipartFile file, String projectId){

        try {

            String url= getFileLoadRootUrl();
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


            String filepath=url+"/"+file.getOriginalFilename();


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

    /**
     * delete a paper by url in db
     * delete a folder
     * we can add it later
     * */
    public static void deletePaper(String u){
        try {

            String url= getFileLoadRootUrl();
            //fixed above, absolute path

            String projectName=u.substring(0,u.indexOf('/'));


            url+=projectName;
            System.out.println(url);//file:/C:/Users/HP/Desktop/j2ee/target/classes/project1

            try{
                Files.deleteIfExists(Paths.get(url));
            }catch (IOException e){
                e.printStackTrace();
            }

            return;


        } catch (Exception e) {
            System.out.println("删除文件内容出错");
            e.printStackTrace();
        }

        throw new RuntimeException("control shouldn't reach here");
    }
}

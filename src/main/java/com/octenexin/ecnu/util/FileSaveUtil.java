package com.octenexin.ecnu.util;

import com.octenexin.ecnu.EcnuApplication;

import com.octenexin.ecnu.pojo.Paper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
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

    public static String getFileSize(String paperUrl){
        String url=FileSaveUtil.getFileLoadRootUrl();//...resources/

        // 从uploads中传输文件到响应流中
        File file = new File(url+paperUrl);

        long len=file.length();

        //B,KB,MB,GB

        String[] arr= new String[]{"B", "KB", "MB", "GB"};
        int i=0;
        for(;i<3;i++){
           if(len>=1024){
               len>>=10;
           }else{
               break;
           }
        }

        return len+arr[i];
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

            deleteDir(url);//递归删除

            return;


        } catch (Exception e) {
            System.out.println("删除文件内容出错");
            e.printStackTrace();
        }

        throw new RuntimeException("control shouldn't reach here");
    }

    public static boolean deleteDir(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (String child : children) {
                deleteDir(path + "/" + child);
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}

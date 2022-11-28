package com.octenexin.ecnu.util;

import com.octenexin.ecnu.EcnuApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class FileLoadUtil {


    public static String getEmailTemplate(String captcha) {
        String emailTemplet = System.getProperty("emailTemplet");
        emailTemplet = emailTemplet.replace("[[$para0]]", String.valueOf(captcha.charAt(0)))
                .replace("[[$para1]]", String.valueOf(captcha.charAt(1)))
                .replace("[[$para2]]", String.valueOf(captcha.charAt(2)))
                .replace("[[$para3]]", String.valueOf(captcha.charAt(3)));
        return emailTemplet;
    }


    /**
     * @param name
     * absolute path in 'classes' folder, no / ahead
     * */
    public static void loadEmailTemplate(String name){

        try {
            String url= Objects.requireNonNull(EcnuApplication.class.getClassLoader().getResource(name)).getFile();

            url = url.substring(1);

            String encoding = "UTF-8";
            File file = new File(url);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                StringBuilder sb = new StringBuilder();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //System.out.println(lineTxt);
                    sb.append(lineTxt);
                }
                System.setProperty("emailTemplet", sb.toString());
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
}

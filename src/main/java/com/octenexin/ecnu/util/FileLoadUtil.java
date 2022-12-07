package com.octenexin.ecnu.util;

import com.octenexin.ecnu.EcnuApplication;
import com.octenexin.ecnu.pojo.Project;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class FileLoadUtil {


    public static String getEmailTemplate(String captcha) {
        String emailTemplate = System.getProperty("emailTemplate");
        emailTemplate = emailTemplate.replace("[[$para0]]", String.valueOf(captcha.charAt(0)))
                .replace("[[$para1]]", String.valueOf(captcha.charAt(1)))
                .replace("[[$para2]]", String.valueOf(captcha.charAt(2)))
                .replace("[[$para3]]", String.valueOf(captcha.charAt(3)));
        return emailTemplate;
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
                System.setProperty("emailTemplate", sb.toString());
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
    
    
    /**
     * 生成xlsx文档
     */
    public static XSSFWorkbook generateXLSX(List<Project> projects){
        // 设置日期格式
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Sheet1");
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("项目名称");
        header.createCell(1).setCellValue("项目负责人学工号");
        header.createCell(2).setCellValue("项目其他人员信息");
        header.createCell(3).setCellValue("项目经费");
        header.createCell(4).setCellValue("项目摘要");
        header.createCell(5).setCellValue("项目论文题目");
        header.createCell(6).setCellValue("项目类型");
        header.createCell(7).setCellValue("项目状态");
        header.createCell(8).setCellValue("起始时间");
        header.createCell(9).setCellValue("截止时间");
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(project.getProjectName());
            row.createCell(1).setCellValue(project.getProjectChargePersonId());
            row.createCell(2).setCellValue(project.getProjectOtherPeopleInfo());
            row.createCell(3).setCellValue(project.getProjectFundsUp());
            row.createCell(4).setCellValue(project.getProjectAbout());
//            此处论文标题获取未实现
//            row.createCell(5).setCellValue("论文标题");
            row.createCell(6).setCellValue(IdManageUtils.getProjectClass(project.getProjectClassId()).getProjectClass());
            row.createCell(7).setCellValue(IdManageUtils.getProjectState(project.getProjectStateId()).getProjectState());
            row.createCell(8).setCellValue(df.format(project.getProjectStartTime()));
            row.createCell(9).setCellValue(df.format(project.getProjectEndTime()));
        }
        
       return book;
    }
}

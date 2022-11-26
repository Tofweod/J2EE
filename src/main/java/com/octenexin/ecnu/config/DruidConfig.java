package com.octenexin.ecnu.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource(){
        DruidDataSource source=new DruidDataSource();
        return source;
    }

    //background watch
    @Bean
    public ServletRegistrationBean<?> statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean=new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");

        HashMap<String,String> initParas=new HashMap<>();
        initParas.put("loginUsername","admin");//fixed para key
        initParas.put("loginPassword","123456");//fixed para key

        initParas.put("allow","");

        bean.setInitParameters(initParas);

        return bean;
    }

    @Bean
    public FilterRegistrationBean<?> webStatFilter(){
        FilterRegistrationBean<WebStatFilter> bean=new FilterRegistrationBean<>();

        bean.setFilter(new WebStatFilter());

        HashMap<String,String> initParas=new HashMap<>();
        initParas.put("exclusions","*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");//fixed para key

        bean.setInitParameters(initParas);

        return bean;

    }
}

package com.yx.tangpoetry;

import com.alibaba.druid.pool.DruidDataSource;
import com.yx.tangpoetry.Web.WebController;
import com.yx.tangpoetry.analyze.dao.AnalyzeDao;
import com.yx.tangpoetry.analyze.dao.impl.AnalyzeDaoImpl;
import com.yx.tangpoetry.analyze.service.AnalyzeService;
import com.yx.tangpoetry.analyze.service.impl.AnalyzeServiceImpl;
import com.yx.tangpoetry.config.ConfigProperties;
import com.yx.tangpoetry.config.ObjectFactory;
import com.yx.tangpoetry.crawler.Crawler;
import com.yx.tangpoetry.crawler.common.Page;
import com.yx.tangpoetry.crawler.parse.DataPageParse;
import com.yx.tangpoetry.crawler.parse.DocumentParse;
import com.yx.tangpoetry.crawler.pipeline.DatabasePipeline;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.time.LocalDateTime;


/**
 * htmlUnit库
 * 抽象模型：OOA,OOD,OOP
 * 抽象处理流程（采集，解析，清洗）
 * 调度器
 * Author:Sophie
 * Created: 2019/3/10
 */
@Data
public class tangshi {
    private static final Logger LOGGER= LoggerFactory.getLogger(tangshi.class);
    public static void main(String[] args) {
        WebController webController=ObjectFactory.getInstance().getObject(WebController.class);
        LOGGER.info("Web Server launch ...");
        webController.launch();
        //启动爬虫
        if (args.length==1 && args[0].equals("run-crawler")) {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            LOGGER.info("Crawler started ...");
            crawler.start();
        }

//        Spark.get("/hello",(req,resp)->{
//            return "Hello World Crawler"+LocalDateTime.now().toString();
//        });



//
//        ConfigProperties configProperties=new ConfigProperties();
//
//        final Page page = new Page(configProperties.getCrawlerBase(),configProperties.getCrawlerPath(),configProperties.isCrawlerDetail());
//        Crawler crawler = new Crawler();
//
//        crawler.addParse(new DocumentParse());
//        crawler.addParse(new DataPageParse());
//
//        DruidDataSource dataSource=new DruidDataSource();
//        dataSource.setUsername(configProperties.getDbUsername());
//        dataSource.setPassword(configProperties.getDbPassword());
//        dataSource.setDriverClassName(configProperties.getDbDriverClass());
//        dataSource.setUrl(configProperties.getDbUrl());
//
//        AnalyzeDao analyzeDao=new AnalyzeDaoImpl(dataSource);
////
//        AnalyzeService analyzeService=new AnalyzeServiceImpl(analyzeDao);
//        analyzeService.analyzeAuthorCount().forEach(System.out::println);
//
//        System.out.println(analyzeDao.queryAllPoetryInfo().get(0));
//        analyzeService.analyzeWordCloud().forEach(System.out::println);
//
//        System.out.println("测试一");
//        analyzeDao.analyzeAuthorCount().forEach(System.out::println);
//
//        System.out.println("测试二");
//        analyzeDao.queryAllPoetryInfo().forEach(System.out::println);
////
//        crawler.addPipeline(new DatabasePipeline(dataSource));
//
//        crawler.addPage(page);

//        crawler.start();

    }

}

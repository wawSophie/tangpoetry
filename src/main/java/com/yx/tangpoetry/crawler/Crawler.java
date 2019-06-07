package com.yx.tangpoetry.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.yx.tangpoetry.crawler.common.Page;
import com.yx.tangpoetry.crawler.parse.Parse;
import com.yx.tangpoetry.crawler.pipeline.Pipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author:Sophie
 * Created: 2019/4/11
 */
public class Crawler {
    private final Logger logger=LoggerFactory.getLogger(Crawler.class);

    /*
    *放置文档页面（超链接）
    * 未被采集和解析的页面
    * Page htmlPage dataSet
     */
    private final Queue<Page> docQueue=new LinkedBlockingDeque<>();
    /*
    *放置详情页面
    * 这个page已经被处理完了，数据在dataSet里面
     */
    private final Queue<Page> detailQueue=new LinkedBlockingDeque<>();
    /*
    采集器
     */
    private final WebClient webClient;
    /*
    所有的解析器
     */
    private final List<Parse> parseList=new LinkedList<>();
    /*
    所有的清洗器
     */
    private final List<Pipeline> pipelineList=new LinkedList<>();
    /*
    线程调度器
     */
    private final ExecutorService executorService;

    public Crawler(){
        this.webClient=new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);
        this.executorService=Executors.newFixedThreadPool(8, new ThreadFactory() {
            private final AtomicInteger id=new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread=new Thread(r);
                thread.setName("Crawler-Thread-"+id.getAndIncrement());
                return thread;
            }
        });
    }
    /*
    开始爬虫
     */
    public void start(){
        //爬取
        //解析
        //清洗
        this.executorService.submit(this::parse);//方法引用
        this.executorService.submit(this::pipeline);
    }


    /**
     * 解析
     */
    private void parse(){
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Parse occur exception {} .",e.getMessage());
            }
            //出队列，里面有元素就可以返回一个page元素；
            final Page page=this.docQueue.poll();
            if (page==null){
                continue;
            }
            //多线程实现解析
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
//                        System.out.println(page.getUrl());
                        //采集
                        HtmlPage htmlPage=Crawler.this.webClient.getPage(page.getUrl());//匿名内部类通过类名访问
                        page.setHtmlPage(htmlPage);
                        //解析
                        for (Parse parse:Crawler.this.parseList){
                            parse.parse(page);
                        }
                        if (page.isDetail()){
                            Crawler.this.detailQueue.add(page);
                        }else {
                            Iterator<Page> iterator=page.getSubPage().iterator();
                            while (iterator.hasNext()){
                                Page subPage=iterator.next();
//                        System.out.println(subPage);
                                Crawler.this.docQueue.add(subPage);
                                iterator.remove();
                            }
                        }
                    } catch (IOException e) {
                        logger.error("Parse task occur exception {} .",e.getMessage());
                    }
                }
            });

        }
    }

    private void pipeline(){
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Parse occur exception {} .",e.getMessage());
            }
            final Page page=this.detailQueue.poll();
            if (page==null){
                continue;
            }
            //多线程实现清洗
           this.executorService.submit(new Runnable() {
               @Override
               public void run() {
                   for (Pipeline pipeline:Crawler.this.pipelineList){
                       pipeline.pipeline(page);
                   }
               }
           });
        }
    }

    public void addPage(Page page){
        this.docQueue.add(page);
    }
    public void addParse(Parse parse){
        this.parseList.add(parse);
    }
    public void addPipeline(Pipeline pipeline){
        this.pipelineList.add(pipeline);
    }



    /*
    停止爬虫
     */
    public void stop(){
        if (this.executorService!=null && !this.executorService.isShutdown()){
            this.executorService.shutdown();
        }
        logger.info("Crawler stopped ...");
    }

    ///shiwenv_45c396367f59.aspx

}

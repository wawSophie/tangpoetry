package com.yx.tangpoetry.Web;

import com.google.gson.Gson;
import com.yx.tangpoetry.analyze.model.AuthorCount;
import com.yx.tangpoetry.analyze.model.WordCount;
import com.yx.tangpoetry.analyze.service.AnalyzeService;
import com.yx.tangpoetry.config.ObjectFactory;
import com.yx.tangpoetry.crawler.Crawler;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.List;

/**
 * 1、Sparkjava  框架完成Web API开发
 * 2、servlet 技术实现Web API开发
 * 3、Java-Httpd 实现Web API（纯java语言实现的web服务器）
 *     Socket  Http协议要非常清楚
 * Author:Sophie
 * Created: 2019/3/23
 */
public class WebController {
    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    // http://127.0.0.1:4567/
    ///analyze/author_count
    private List<AuthorCount> analyzeAuthorCount() {
        return analyzeService.analyzeAuthorCount();
    }
    private List<WordCount> analyzeWordCloud(){
        return analyzeService.analyzeWordCloud();
    }
    //转换器
    public void launch(){
        ResponseTransformer transformer=new JSONResponseTransformer();
        //前端静态文件的目录
        Spark.staticFileLocation("/static");
        //服务端接口
        Spark.get("/analyze/author_count",
                (((request, response) -> analyzeAuthorCount())),
                transformer);
        Spark.get("analyze/word_cloud",
                (((request, response) -> analyzeWordCloud())),
                transformer);



        Spark.get("/crawler/stop",(((request, response) ->{
            Crawler crawler=ObjectFactory.getInstance().getObject(Crawler.class);
            crawler.stop();
            return "爬虫停止";
        })));
    }
    //对象变字符串
    public static class JSONResponseTransformer implements ResponseTransformer{
        private Gson gson=new Gson();
        @Override
        public String render(Object o) throws Exception {
            return gson.toJson(o);
        }
    }

//
//    public static void main(String[] args) {
//        //gson和java中的序列化比较像（字符串转成buffer数组，buffer数组可以转为字符串对象）
//        Gson gson=new Gson();
//        WordCount wordCount=new WordCount();
//        wordCount.setWord("java");
//        wordCount.setCount(10);
//        System.out.println( gson.toJson(wordCount));
//        String str="{\"word\":\"java\",\"count\":10}";
//        WordCount wordCount1=gson.fromJson(str,WordCount.class);
//        System.out.println(wordCount1);
//    }
}

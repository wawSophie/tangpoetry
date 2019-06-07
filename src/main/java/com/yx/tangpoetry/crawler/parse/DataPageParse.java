package com.yx.tangpoetry.crawler.parse;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.yx.tangpoetry.crawler.common.Page;
//import com.yx.tangpoetry.crawler.common.PoetryInfo;

import java.io.IOException;

/**
 * 详情页的解析
 * Author:Sophie
 * Created: 2019/3/17
 */
public class DataPageParse implements Parse {
    public void parse(final Page page) {
        if (!page.isDetail()) {
            return;
        }
//        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
        HtmlPage htmlPage = page.getHtmlPage();
        HtmlElement body = htmlPage.getBody();
        //标题
        String titlePath = "//div[@class='cont']/h1/text()";
        DomText titleDom = (DomText) body.getByXPath(titlePath).get(0);
        String title = titleDom.asText();
//        System.out.println(title);
        //朝代
        String dynastyPath = "//div[@class='cont']/p/a[1]";
//            body.getByXPath(authorPath).forEach(
//                    o->{
//                        HtmlAnchor anchor=(HtmlAnchor)o;
//                        System.out.println(anchor.asText());
//                    }
//                                );
        HtmlAnchor anchor = (HtmlAnchor) body.getByXPath(dynastyPath).get(0);
        String dynasty = anchor.asText();
//        System.out.println(dynasty);

        //作者
        String authorPath = "//div[@class='cont']/p/a[2]";
        HtmlAnchor authorDom = (HtmlAnchor) body.getByXPath(authorPath).get(0);
        String author = authorDom.asText();
//        System.out.println(author);

        //内容
        String contentPath = "//div[@class='cont']//div[@class='contson']";
        HtmlDivision contentDom = (HtmlDivision) body.getByXPath(contentPath).get(0);
        String content = contentDom.asText();
//        System.out.println(content);



        page.getDataSet().putData("title",title);
        page.getDataSet().putData("dynasty",dynasty);

        page.getDataSet().putData("author",author);
        page.getDataSet().putData("content",content);

        page.getDataSet().putData("url",page.getUrl());
        //由清洗器去决定是否要加元素

    }
}

//==========================================================================================================


//        HtmlDivision division= (HtmlDivision) page.getHtmlPage().getElementById("contson4809b5e7a16a");
//        String content=division.asText();
//        page.getDataSet().putData("正文",content);
//获取网页
//        HtmlPage htmlPage=page.getHtmlPage();
//        HtmlElement body=htmlPage.getBody();
//
//        String titlePath="//div[@class='sons']//div[@class='cont']/h1/text()";
//        DomText titleDom= (DomText) body.getByXPath(titlePath).get(0);
//        System.out.println(titleDom.asText());
//        Object o=body.getByXPath(titlePath).get(0);
//        System.out.println(o.getClass().getName());

//        //得到body体
//        HtmlElement body=htmlPage.getBody();

//标题
//属性class,转成text
//        String titlePath="//div[@class='cont']/h1/text()";
//        DomText titleDom= (DomText) body.getByXPath(titlePath).get(0);
//        String title=titleDom.asText();
//        System.out.println(title);
//
//        //朝代
//        String dynastyPath="//div[@class='cont'/p/a[1]]";
//        HtmlAnchor dynastyDom= (HtmlAnchor) body.getByXPath(dynastyPath).get(0);
//        String dynasty=dynastyDom.asText();
//        System.out.println(dynasty);

//作者
//        String authorPath="//div[@class='cont'/p[@class='source'][1]/a[2]/text()";
////        HtmlAnchor authorDom= (HtmlAnchor) body.getByXPath(authorPath).get(0);
////        String author=authorDom.asText();
////        System.out.println(author);
//
//        body.getByXPath(authorPath)
//                .forEach(o->{
//                    System.out.println(o.getClass().getName());
//                });

//内容
//        String contentPath="//div[@class='cont']//div[@class='contson']";
//        HtmlDivision contentDom= (HtmlDivision) body.getByXPath(contentPath).get(0);
//        String content=contentDom.asText();
//        System.out.println(content);


//}

package com.yx.tangpoetry.crawler.parse;

import com.gargoylesoftware.htmlunit.html.*;
import com.yx.tangpoetry.crawler.common.Page;

import java.util.List;

/**
 * 非详情页的解析
 * Author:Sophie
 * Created: 2019/3/17
 */
public class DocumentParse implements Parse {

    public void parse(final Page page){
        if (page.isDetail()){
            return;
        }
        //原子执行
//        final AtomicInteger count=new AtomicInteger(0);
        HtmlPage htmlPage=page.getHtmlPage();
        /*
        方法一
         */
        List<HtmlElement> htmlElement=htmlPage.getBody()
                .getElementsByAttribute("div","class","typecont");
        for (HtmlElement htmlElements:htmlElement){
//            System.out.println(htmlElements.asText());
            HtmlDivision htmlDivision = (HtmlDivision)htmlElements;
            DomNodeList<HtmlElement> domNodeList=htmlDivision.getElementsByTagName("a");
            for (  HtmlElement domNodeLists:domNodeList){
//                System.out.println(domNodeLists.asXml());
                 String path=domNodeLists.getAttribute("href");
                 Page subPage=new Page(page.getBase(),path,true);
                 page.getSubPage().add(subPage);
            }
        }

        /*
        方法二
//         */
//        htmlPage.getBody()
//                .getElementsByAttribute("div","class","typecont")
//                .forEach(new Consumer<HtmlElement>() {
//                    @Override
//                    public void accept(HtmlElement htmlElement) {
//
//                        DomNodeList<HtmlElement> nodeList=htmlElement.getElementsByTagName("a");
//                        nodeList.forEach(
//                                aNode->{
//                                    String path=aNode.getAttribute("href");
////                                    count.getAndIncrement();//添加
////                                    System.out.println(path);
//                                    Page subPage=new Page(
//                                            page.getBase(),
//                                            path,
//                                            true
//                                    );
//                                    page.getSubPage().add(subPage);
//
//                                }
//                        );
//                    }
//                });
//        System.out.println("总共："+count.get()+"地址");
    }
}

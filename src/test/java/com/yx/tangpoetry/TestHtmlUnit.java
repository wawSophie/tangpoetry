package com.yx.tangpoetry;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.net.MalformedURLException;


/**
 * Xml:可扩展的标记语言
 * Author:Sophie
 * Created: 2019/3/17
 */
public class TestHtmlUnit {
    public static void main(String[] args) {
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setJavaScriptEnabled(false);
            HtmlPage htmlPage = webClient.getPage("https://so.gushiwen.org/shiwenv_45c396367f59.aspx");
            HtmlElement body = htmlPage.getBody();
            String text=body.asText();
            System.out.println(text);
//            String text=Body.asText();//取文本
//            String text=Body.asXml();//取结点
//            System.out.println(text);
//            HtmlDivision division = (HtmlDivision) htmlPage.getElementById("contson4809b5e7a16a");
//            System.out.println(division.getClass().getName());
//            String titlePath="//div[@class='cont']/h1/text()";
//            DomText titleDom= (DomText) body.getByXPath(titlePath).get(0);
//            String title=titleDom.asText();
//            System.out.println(title);
//
//            String authorPath="//div[@class='cont']/p/a[1]";
//            body.getByXPath(authorPath).forEach(
//                    o->{
//                        HtmlAnchor anchor=(HtmlAnchor)o;
//                        System.out.println(anchor.asText());
//                    }
////                                );
//            HtmlAnchor anchor= (HtmlAnchor) body.getByXPath(authorPath).get(0);
//            String author=anchor.asText();
//            System.out.println(author);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


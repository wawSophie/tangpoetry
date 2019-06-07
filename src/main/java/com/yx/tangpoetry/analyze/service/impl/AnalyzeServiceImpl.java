package com.yx.tangpoetry.analyze.service.impl;

import com.yx.tangpoetry.analyze.dao.AnalyzeDao;
import com.yx.tangpoetry.analyze.model.AuthorCount;
import com.yx.tangpoetry.analyze.model.WordCount;
import com.yx.tangpoetry.analyze.service.AnalyzeService;
import com.yx.tangpoetry.crawler.common.PoetryInfo;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.util.*;

/**
 * Author:Sophie
 * Created: 2019/4/18
 */
public class AnalyzeServiceImpl implements AnalyzeService {


    private AnalyzeDao analyzeDao;
    public AnalyzeServiceImpl(AnalyzeDao analyzeDao){this.analyzeDao=analyzeDao;}

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        /**
         * 此处结果并未排序
         * 排序方式
         * 1、DAO层SQL排序
         * 2、Service层进行数据排序
         */
        List<AuthorCount> authorCounts=analyzeDao.analyzeAuthorCount();
        Collections.sort(authorCounts, new Comparator<AuthorCount>() {
            @Override
            public int compare(AuthorCount o1, AuthorCount o2) {
                //降序
                return -1*(o1.getCount()-o2.getCount());
//                return o1.getCount().compareTo(o2.getCount());

            }
        });
        return authorCounts;
    }

    @Override
    public List<WordCount> analyzeWordCloud() {
        /**
         * 查询出所有的数据
         * 取出title content
         * 分词  词性是w的不要，空的不要，一个词的不要
         * 统计 k-v k是词 v是词频
         */
        Map<String,Integer> map=new HashMap<>();
        List<PoetryInfo> poetryInfos=analyzeDao.queryAllPoetryInfo();
        for (PoetryInfo poetryInfo:poetryInfos){
            //ArrayList<>()是并发修改的，不能用for循环去过滤并删除
            List<Term> terms=new ArrayList<>();
            String title=poetryInfo.getTitle();
            String content=poetryInfo.getContent();
            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());

            //使用迭代器过滤并删除
            Iterator<Term> iterator=terms.iterator();
            while (iterator.hasNext()){
                Term term=iterator.next();
                if (term.getNatureStr()==null || term.getNatureStr().equals("w")){
                    iterator.remove();
                    continue;
                }
                if (term.getRealName().length()<2){
                    iterator.remove();
                    continue;
                }
                //统计
                String realName=term.getRealName();
                int count=0;
                if (map.containsKey(realName)){
                     count = map.get(realName) + 1;
                }else {
                    count=1;
                }
                map.put(realName,count);
            }

        }

        List<WordCount> wordCounts=new ArrayList<>();
        for (Map.Entry<String,Integer> entry:map.entrySet()){
            WordCount wordCount=new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());
            wordCounts.add(wordCount);
        }
        return wordCounts;
    }

//    public static void main(String[] args) {
//        Result result=NlpAnalysis.parse("还作江南会，翻疑梦里逢。");
//        List<Term> terms=result.getTerms();
//        for (Term term:terms){
//            System.out.println(term.toString());
//
//        }

    }


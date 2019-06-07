package com.yx.tangpoetry.analyze.service;

import com.yx.tangpoetry.analyze.model.AuthorCount;
import com.yx.tangpoetry.analyze.model.WordCount;

import java.util.List;

/**
 * 分析每个诗人的创作数量->查询结果满足
 * 分析每个诗文中的词频->查询结果进行分词，分组统计
 * Author:Sophie
 * Created: 2019/3/23
 */
public interface AnalyzeService {
    /**
     * 分析唐诗中的创作数量
     * @return
     */
    List<AuthorCount> analyzeAuthorCount();
    /**
     * 词云分析
     * @return
     */
    List<WordCount> analyzeWordCloud();

}

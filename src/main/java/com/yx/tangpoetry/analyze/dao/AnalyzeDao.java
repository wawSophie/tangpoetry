package com.yx.tangpoetry.analyze.dao;

import com.yx.tangpoetry.analyze.model.AuthorCount;
import com.yx.tangpoetry.crawler.common.PoetryInfo;
import lombok.Data;
import java.util.List;
/**
 * 查询
 * Author:Sophie
 * Created: 2019/3/23
 */
public interface AnalyzeDao {
   /**
    * 分析唐诗中作者的创作数量
    * @return
    */
   public List<AuthorCount> analyzeAuthorCount();

   /**
    * 查询所有的诗文，提供给业务层进行分析
    * @return
    */
   List<PoetryInfo> queryAllPoetryInfo();



}

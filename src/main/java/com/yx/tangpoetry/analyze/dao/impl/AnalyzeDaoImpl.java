package com.yx.tangpoetry.analyze.dao.impl;

import com.yx.tangpoetry.analyze.dao.AnalyzeDao;
import com.yx.tangpoetry.analyze.model.AuthorCount;
import com.yx.tangpoetry.crawler.common.PoetryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:Sophie
 * Created: 2019/3/23
 */
public class AnalyzeDaoImpl implements AnalyzeDao {
    private final Logger logger=LoggerFactory.getLogger(AnalyzeDaoImpl.class);
    //准备数据源
    private final DataSource dataSource;

    public AnalyzeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        List<AuthorCount>  datas=new ArrayList<>();
        String sql="select count(*) as count ,author from poetry_info group by author;";
        try (Connection connection=dataSource.getConnection();
             PreparedStatement statement=connection.prepareStatement(sql);
             ResultSet rs=statement.executeQuery())
        {
            while (rs.next()){
                AuthorCount authorCount=new AuthorCount();
                authorCount.setAuthor(rs.getString("author"));
                authorCount.setCount(rs.getInt("count"));
                datas.add(authorCount);
            }
        }catch (SQLException e){
            logger.info("Database query occur exception {} .",e.getMessage());
        }
        return datas;
    }

    @Override
    public List<PoetryInfo> queryAllPoetryInfo() {
        List<PoetryInfo> datas=new ArrayList<>();
        String sql=" select title,dynasty,author,content from poetry_info;";
        try (Connection connection=dataSource.getConnection();
        PreparedStatement statement=connection.prepareStatement(sql);
        ResultSet rs=statement.executeQuery()){

            while (rs.next()){
                PoetryInfo poetryInfo=new PoetryInfo();
                poetryInfo.setTitle(rs.getString("title"));
                poetryInfo.setDynasty(rs.getString("dynasty"));
                poetryInfo.setAuthor(rs.getString("author"));
                poetryInfo.setContent(rs.getString("content"));

                //ORM mybatis Spring-Data-JDBC hibernate
                datas.add(poetryInfo);
            }
        }catch (SQLException e){
            logger.info("Database query occur exception {} .",e.getMessage());
        }
        return datas;
    }
}

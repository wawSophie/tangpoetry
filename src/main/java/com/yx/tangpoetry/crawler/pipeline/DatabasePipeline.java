package com.yx.tangpoetry.crawler.pipeline;

import com.yx.tangpoetry.crawler.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import com.yx.tangpoetry.crawler.common.PoetryInfo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author:Sophie
 * Created: 2019/4/15
 */
public class DatabasePipeline implements Pipeline {
    private final Logger logger=LoggerFactory.getLogger(DatabasePipeline.class);
    private final DataSource dataSource;

    public DatabasePipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void pipeline(final Page page) {
        String title=(String)page.getDataSet().getData("title");
        String dynasty=(String)page.getDataSet().getData("dynasty");
        String author=(String)page.getDataSet().getData("author");
        String content=(String)page.getDataSet().getData("content");
        String sql ="insert into poetry_info(title, dynasty, author, content) VALUES (?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement=connection.prepareStatement(sql)
        ) {
            statement.setString(1,title);
            statement.setString(2,dynasty);
            statement.setString(3,author);
            statement.setString(4,content);
            statement.executeUpdate();//执行更新

        } catch (SQLException e) {
            logger.info("Database insert occur exception {} .",e.getMessage());
        }
    }
}

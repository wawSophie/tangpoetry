package com.yx.tangpoetry.crawler.pipeline;

import com.yx.tangpoetry.crawler.common.Page;

import java.util.Map;

/**
 * 控制台管道
 * Author:Sophie
 * Created: 2019/4/11
 */
public class ConsolePipeline implements Pipeline {
    @Override
    public void pipeline(final Page page) {
        Map<String,Object> data=page.getDataSet().getData();
        //存储
        System.out.println(data);
    }

}

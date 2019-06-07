package com.yx.tangpoetry.crawler.pipeline;

import com.yx.tangpoetry.crawler.common.Page;

/**
 * Author:Sophie
 * Created: 2019/4/11
 */
public interface Pipeline {
    /**
     * 管道处理数据
     * @param page
     */
    void pipeline(final Page page);

}

package com.yx.tangpoetry.crawler.common;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * data把DOM解析，清洗之后存储的数据
 * Author:Sophie
 * Created: 2019/3/17
 */
@ToString
public class DataSet {

    private Map<String ,Object > data=new HashMap<>();

    public void putData(String key,Object value){
        this.data.put(key, value);
    }
    public Object getData(String key){
        return this.data.get(key);
    }

    public Map<String, Object> getData() {
        return new HashMap<>(this.data);
    }
}

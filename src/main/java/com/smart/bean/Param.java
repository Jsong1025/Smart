package com.smart.bean;

import com.smart.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 *
 * @author jsong
 */
public class Param {

    private Map<String , Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取long类型参数
     */
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有参数
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }
}

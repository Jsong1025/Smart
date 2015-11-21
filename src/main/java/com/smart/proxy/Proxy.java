package com.smart.proxy;

/**
 * 代理接口
 *
 * @author jsong
 */
public interface Proxy {

    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain chain) throws Throwable;

}

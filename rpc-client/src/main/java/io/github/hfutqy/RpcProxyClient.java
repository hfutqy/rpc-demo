package io.github.hfutqy;

import java.lang.reflect.Proxy;

/**
 * @author qiyu
 * @date 2019-10-10
 */
public class RpcProxyClient {

    /**
     * 客户端代理,动态代理实现
     * 加final表示入参对象只会使用不会更改，更改入参对象会报错
     *
     * @param interfaceClass
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    public <T> T clientProxy(final Class<T> interfaceClass, final String host, final int port) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RemoteInvocationHandler(host, port));
    }

}

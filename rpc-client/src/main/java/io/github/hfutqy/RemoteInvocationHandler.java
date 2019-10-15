package io.github.hfutqy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author qiyu
 * @date
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private int port;

    RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("begin: " + host + " : " + port);

        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);

        RpcNetTransport rpcNetTransport = new RpcNetTransport(host, port);
        return rpcNetTransport.send(request);
    }
}

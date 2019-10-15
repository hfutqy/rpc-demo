package io.github.hfutqy;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        // 通过动态代理(接口)，获取远程实现
        IHelloService iHelloService = rpcProxyClient.clientProxy(IHelloService.class, "localhost", 8123);
        System.out.println(iHelloService.hello("qiyu"));
    }
}

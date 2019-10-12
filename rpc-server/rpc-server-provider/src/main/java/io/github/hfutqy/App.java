package io.github.hfutqy;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        // 发布我们的服务
        IHelloService helloService = new HelloService();
        RpcProxyServer proxyServer = new RpcProxyServer();
        proxyServer.publisher(helloService, 8123);
    }
}

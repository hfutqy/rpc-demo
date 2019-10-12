package io.github.hfutqy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qiyu
 * @date 2019-10-09
 */
public class RpcProxyServer {

    /**
     * 缓存线程池，一个可以灵活伸缩的线程池
     * 为什么要用线程池去执行请求，因为请求打进来要避免阻塞（而不是直接socket.getInputStream),即NIO
     */
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    /**
     * @param service 我需要发布出去的服务
     * @param port    我要暴露的端口号
     * 没有ip？本机的ip。多网卡情况需要制定ip避免访问地址出错
     */
    public void publisher(Object service, int port) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("等待连接");
                // 获得一个远程连接
                Socket socket = serverSocket.accept();
                System.out.println("连接成功");
                // 如果进行到下一步，说明有客户端连接进来
                executorService.execute(new ProcessorHandler(socket, service));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

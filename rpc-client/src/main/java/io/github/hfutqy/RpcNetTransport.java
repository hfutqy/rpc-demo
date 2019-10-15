package io.github.hfutqy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author qiyu
 * @date 2019/10/15
 */
public class RpcNetTransport {

    private String host;
    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket() {
        System.out.println("开始构建一个客户端连接");
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public Object send(RpcRequest request) {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        Object result = null;
        try {
            // 构建客户端的请求， 把request写入到服务到
            socket = newSocket();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            // 得到服务端的返回结果(正常情况发送请求得到结果是同步返回的，这时候就需要异步netty了)
            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
}
